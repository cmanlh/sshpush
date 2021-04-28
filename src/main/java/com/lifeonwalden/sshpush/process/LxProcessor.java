package com.lifeonwalden.sshpush.process;

import com.jcraft.jsch.Session;
import com.lifeonwalden.sshpush.bean.PushStep;

import java.io.IOException;
import java.util.Map;

public interface LxProcessor {
    static void process(PushStep step, Map<String, Session> sessionMap) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();

        Process process = runtime.exec(step.getExec());
        int result = process.waitFor();

        if (0 == result) {
            System.out.println("Done!");
        } else {
            System.err.println("Failed!");
        }
    }
}
