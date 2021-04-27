package com.lifeonwalden.sshpush.process;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.lifeonwalden.sshpush.bean.PushStep;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface RxProcessor {
    static void process(PushStep step, Map<String, Session> sessionMap) throws JSchException, SftpException, IOException {
        Session session = sessionMap.get(step.getHostId());
        if (null == session) {
            throw new RuntimeException("Not found host ".concat(step.getHostId()));
        }

        if (!session.isConnected()) {
            session.connect();
        }

        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(step.getExec());
        channel.setInputStream(null);
        channel.setOutputStream(System.out);
        channel.setErrStream(System.err);
        InputStream is = channel.getInputStream();
        channel.connect();

        byte[] buffer = new byte[1024];
        int len = 0;

        StringBuilder result = new StringBuilder();
        while ((len = is.read(buffer)) != -1) {
            result.append(new String(buffer, 0, len));
        }
        System.out.println(result);

        channel.disconnect();
    }
}
