package com.lifeonwalden.sshpush.process;

import com.jcraft.jsch.Session;
import com.lifeonwalden.sshpush.bean.HostInfo;
import com.lifeonwalden.sshpush.bean.PushStep;

import java.util.Map;

public interface LxProcessor {
    static void process(PushStep step, Map<String, Session> sessionMap) {

    }
}
