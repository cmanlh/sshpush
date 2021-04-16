package com.lifeonwalden.sshpush.process;

import com.beust.jcommander.JCommander;
import com.lifeonwalden.sshpush.bean.HostInfo;
import com.lifeonwalden.sshpush.bean.ParamPassword;
import com.lifeonwalden.sshpush.bean.PushInfo;

import java.util.List;

public interface AuthProcessor {
    static void process(PushInfo pushInfo) {
        List<HostInfo> hostInfoList = pushInfo.getHost();
        if (null == hostInfoList || hostInfoList.isEmpty()) {
            throw new RuntimeException("Please provide host info.");
        }

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
