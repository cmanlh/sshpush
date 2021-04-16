package com.lifeonwalden.sshpush.bean;

public class HostInfo {
    private String id;
    private String host;
    private int port;
    private String user;
    private String password;

    public String getId() {
        return id;
    }

    public HostInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getHost() {
        return host;
    }

    public HostInfo setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public HostInfo setPort(int port) {
        this.port = port;
        return this;
    }

    public String getUser() {
        return user;
    }

    public HostInfo setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public HostInfo setPassword(String password) {
        this.password = password;
        return this;
    }
}
