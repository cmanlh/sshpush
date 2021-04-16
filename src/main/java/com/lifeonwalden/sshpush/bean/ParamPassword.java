package com.lifeonwalden.sshpush.bean;

import com.beust.jcommander.Parameter;

public class ParamPassword {
    @Parameter(names = "-p", description = "Connection password", password = true)
    private String password;

    public String getPassword() {
        return password;
    }

    public ParamPassword setPassword(String password) {
        this.password = password;
        return this;
    }
}
