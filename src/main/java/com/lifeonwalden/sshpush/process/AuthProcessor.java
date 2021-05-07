package com.lifeonwalden.sshpush.process;

import com.beust.jcommander.JCommander;
import com.lifeonwalden.sshpush.bean.HostInfo;
import com.lifeonwalden.sshpush.bean.ParamPassword;
import com.lifeonwalden.sshpush.bean.PushInfo;
import com.lifeonwalden.sshpush.bean.YesOrNot;

import java.util.List;

public interface AuthProcessor {
    static void process(PushInfo pushInfo) {
        List<HostInfo> hostInfoList = pushInfo.getHost();
        if (null == hostInfoList || hostInfoList.isEmpty()) {
            System.out.println("No host info provided.");
            return;
        }

        YesOrNot yesOrNot = new YesOrNot();
        System.out.println("Provide password for all host : Yes(Y) or Not(N)");
        JCommander.newBuilder().addObject(yesOrNot).build().parse("-confirm");

        if ("Y".equalsIgnoreCase(yesOrNot.isYesOrNot())) {
            ParamPassword pwd = new ParamPassword();
            System.out.printf("Please input the password");
            JCommander.newBuilder().addObject(pwd).build().parse("-p");

            for (HostInfo hostInfo : hostInfoList) {
                if (null == hostInfo.getPassword() || hostInfo.getPassword().isEmpty()) {
                    hostInfo.setPassword(pwd.getPassword());
                }
            }
        } else {
            for (HostInfo hostInfo : hostInfoList) {
                if (null == hostInfo.getPassword() || hostInfo.getPassword().isEmpty()) {
                    ParamPassword pwd = new ParamPassword();
                    System.out.printf("Please provide password for %s\n", hostInfo.getHost());
                    JCommander.newBuilder().addObject(pwd).build().parse("-p");

                    hostInfo.setPassword(pwd.getPassword());
                }
            }
        }
    }
}
