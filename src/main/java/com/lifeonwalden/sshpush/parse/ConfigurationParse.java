package com.lifeonwalden.sshpush.parse;

import com.alibaba.fastjson.JSON;
import com.lifeonwalden.sshpush.bean.PushInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

public interface ConfigurationParse {
    static Optional<PushInfo> parse(File config) throws IOException {
        if (!config.exists()) {
            System.err.printf("%s is not exists", config.getAbsolutePath());
            return Optional.empty();
        }

        if (config.isDirectory()) {
            System.err.printf("%s is directory", config.getAbsolutePath());

            return Optional.empty();
        }

        if (config.isFile()) {
            return Optional.of(JSON.parseObject(new FileInputStream(config), PushInfo.class));
        } else {
            System.err.printf("%s is not a file", config.getAbsolutePath());

            return Optional.empty();
        }
    }
}
