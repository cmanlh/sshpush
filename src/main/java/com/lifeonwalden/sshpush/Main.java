package com.lifeonwalden.sshpush;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.JCommander;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.lifeonwalden.sshpush.bean.HostInfo;
import com.lifeonwalden.sshpush.bean.PushInfo;
import com.lifeonwalden.sshpush.bean.PushStep;
import com.lifeonwalden.sshpush.bean.YesOrNot;
import com.lifeonwalden.sshpush.constant.ActionType;
import com.lifeonwalden.sshpush.parse.ConfigurationParse;
import com.lifeonwalden.sshpush.process.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        if (null == args || args.length == 0) {
            System.err.println("Please provide configuration file path.");
            return;
        }

        Map<String, Session> sessionMap = new HashMap<>();
        try {
            Optional<PushInfo> pushInfoOption = ConfigurationParse.parse(new File(args[0]));
            if (pushInfoOption.isPresent()) {
                PushInfo pushInfo = pushInfoOption.get();
                AuthProcessor.process(pushInfo);

                if (null != pushInfo.getHost() && !pushInfo.getHost().isEmpty()) {
                    JSch jSch = new JSch();
                    for (HostInfo hostInfo : pushInfo.getHost()) {
                        Session session = null;
                        if (hostInfo.getPort() > 0) {
                            session = jSch.getSession(hostInfo.getUser(), hostInfo.getHost(), hostInfo.getPort());
                        } else {
                            session = jSch.getSession(hostInfo.getUser(), hostInfo.getHost());
                        }
                        session.setConfig("StrictHostKeyChecking", "no");
                        session.setPassword(hostInfo.getPassword());
                        session.connect();
                        sessionMap.put(hostInfo.getId(), session);
                    }
                }

                YesOrNot yesOrNot = new YesOrNot();
                System.out.println("Want to ignore some steps ? Yes(Y) or Not(N)");
                JCommander.newBuilder().addObject(yesOrNot).build().parse("-confirm");

                for (PushStep step : pushInfo.getStep()) {
                    if ("Y".equalsIgnoreCase(yesOrNot.getYesOrNot())) {
                        if (step.isSkip()) {
                            continue;
                        }
                    }

                    if (ActionType.UPLOAD.name().equalsIgnoreCase(step.getAction())) {
                        UploadProcessor.process(step, sessionMap);
                    } else if (ActionType.DOWNLOAD.name().equalsIgnoreCase(step.getAction())) {
                        DownloadProcessor.process(step, sessionMap);
                    } else if (ActionType.RX.name().equalsIgnoreCase(step.getAction())) {
                        RxProcessor.process(step, sessionMap);
                    } else if (ActionType.LX.name().equalsIgnoreCase(step.getAction())) {
                        LxProcessor.process(step, sessionMap);
                    } else {
                        System.err.printf("Invalid action : %s", JSON.toJSONString(step));

                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            sessionMap.values().forEach(session -> session.disconnect());
        }
    }

    private static void printStepInfo(PushStep step) {
        if (ActionType.UPLOAD.name().equalsIgnoreCase(step.getAction())) {
            System.out.printf("\nUpload [ %s ] to [ %s ] @ %s\n", step.getSrc(), step.getTarget(), step.getHostId());
        } else if (ActionType.DOWNLOAD.name().equalsIgnoreCase(step.getAction())) {
            System.out.printf("\nDownload to [ %s ] from [ %s ] @ %s\n", step.getTarget(), step.getSrc(), step.getHostId());
        } else if (ActionType.RX.name().equalsIgnoreCase(step.getAction())) {
            System.out.printf("\nRX [ %s ] @ %s\n", step.getExec(), step.getHostId());
        } else if (ActionType.LX.name().equalsIgnoreCase(step.getAction())) {
            System.out.printf("\nLX [ %s ] @ %s\n", step.getExec(), step.getHostId());
        } else {
            throw new RuntimeException("Invalid action : %s".concat(JSON.toJSONString(step)));
        }
    }
}
