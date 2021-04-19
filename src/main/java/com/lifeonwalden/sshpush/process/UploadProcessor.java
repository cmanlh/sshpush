package com.lifeonwalden.sshpush.process;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.lifeonwalden.sshpush.bean.PushStep;

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
        channel.put(step.getSrc(), step.getTarget());
        channel.disconnect();
    }
}
