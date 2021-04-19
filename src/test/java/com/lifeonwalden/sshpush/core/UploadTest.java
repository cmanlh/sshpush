package com.lifeonwalden.sshpush.core;

import com.beust.jcommander.JCommander;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.lifeonwalden.sshpush.bean.ParamConfig;

public class UploadTest {
    public static void main(String[] args) {
        JSch jSch = new JSch();
        try {
            Session session = jSch.getSession("test", "127.0.0.1");
            ParamConfig arg = new ParamConfig();
            JCommander.newBuilder().addObject(arg).build().parse(new String[]{"-p"});
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            Channel channel = session.openChannel("shell");
            System.out.println(channel.isClosed());
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}
