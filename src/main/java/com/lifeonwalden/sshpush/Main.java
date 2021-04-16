package com.lifeonwalden.sshpush;

import com.lifeonwalden.sshpush.bean.PushInfo;
import com.lifeonwalden.sshpush.parse.ConfigurationParse;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        if (null == args || args.length == 0) {
            System.err.println("Please provide configuration file path.");
            return;
        }

        try {
            Optional<PushInfo> pushInfo = ConfigurationParse.parse(new File(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
