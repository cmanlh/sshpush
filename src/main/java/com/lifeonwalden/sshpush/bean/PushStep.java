package com.lifeonwalden.sshpush.bean;

public class PushStep {
    private String hostId;
    private String action;
    private String exec;
    private String target;
    private String src;

    public String getHostId() {
        return hostId;
    }

    public PushStep setHostId(String hostId) {
        this.hostId = hostId;
        return this;
    }

    public String getAction() {
        return action;
    }

    public PushStep setAction(String action) {
        this.action = action;
        return this;
    }

    public String getExec() {
        return exec;
    }

    public PushStep setExec(String exec) {
        this.exec = exec;
        return this;
    }

    public String getTarget() {
        return target;
    }

    public PushStep setTarget(String target) {
        this.target = target;
        return this;
    }

    public String getSrc() {
        return src;
    }

    public PushStep setSrc(String src) {
        this.src = src;
        return this;
    }
}
