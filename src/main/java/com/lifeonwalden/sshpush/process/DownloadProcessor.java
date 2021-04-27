package com.lifeonwalden.sshpush.process;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.lifeonwalden.sshpush.bean.PushStep;

import java.io.File;
import java.util.Map;

public interface DownloadProcessor {
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
        download(channel, step.getSrc(), step.getTarget());
        channel.disconnect();

    }

    static void download(ChannelSftp channel, String src, String target) throws SftpException {
        if (!channel.stat(src).isDir()) {
            channel.get(src, target);

            System.out.printf("Downloaded file %s\n", src);
        } else {
            java.io.File file = new File(target);
            if (file.exists() && file.isFile()) {
                throw new RuntimeException("Invalid download target : ".concat(target));
            }

            String targetFolder = file.getAbsolutePath().concat(File.pathSeparator).concat(new File(src).getName());
            new File(targetFolder).mkdirs();

            for (Object l : channel.ls(src)) {
                ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) l;
                System.out.println(entry.getFilename());
            }
        }
    }
}
