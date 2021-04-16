package com.lifeonwalden.sshpush.bean;

import com.beust.jcommander.Parameter;

public class ParamConfig {
    @Parameter(names = "-c", description = "Config File name")
    private String config;

    public String getConfig() {
        return config;
    }

    public ParamConfig setConfig(String config) {
        this.config = config;
        return this;
    }
}
