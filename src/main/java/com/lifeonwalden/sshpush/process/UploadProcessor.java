package com.lifeonwalden.sshpush.process;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.lifeonwalden.sshpush.bean.PushStep;

import java.io.File;
import java.util.Map;

public interface UploadProcessor {
    static void process(PushStep step, Map<String, Session> sessionMap) throws JSchException, SftpException {
        Session session = sessionMap.get(step.getHostId());
        if (null == session) {
            throw new RuntimeException("Not found host ".concat(step.getHostId()));
        }

        if (!session.isConnected()) {
            session.connect();
        }

        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        upload(channel, step.getSrc(), step.getTarget());
        channel.disconnect();
    }

    static void upload(ChannelSftp channel, String src, String target) throws SftpException {
        java.io.File file = new File(src);
        if (!file.exists()) {
            throw new RuntimeException("Not found : ".concat(src));
        }

        if (file.isFile()) {
            channel.put(src, target);

            System.out.printf("Uploaded file %s\n", src);
        } else {
            channel.cd(target);
            try {
                if (channel.ls(file.getName()).isEmpty()) {
                    channel.mkdir(file.getName());
                }
            } catch (SftpException e) {
                if (ChannelSftp.SSH_FX_NO_SUCH_FILE == e.id) {
                    channel.mkdir(file.getName());
                } else {
                    throw e;
                }
            }

            String targetSubFolder = null;

            if (target.endsWith("/")) {
                targetSubFolder = target.concat(file.getName()).concat("/");
            } else {
                targetSubFolder = target.concat("/").concat(file.getName()).concat("/");
            }

            for (File subFile : file.listFiles()) {
                upload(channel, subFile.getAbsolutePath(), targetSubFolder);
            }
        }
    }
}
