package com.lifeonwalden.sshpush.bean;

import java.util.List;

public class PushInfo {
    private List<HostInfo> host;

    private List<PushStep> step;

    public List<HostInfo> getHost() {
        return host;
    }

    public PushInfo setHost(List<HostInfo> host) {
        this.host = host;
        return this;
    }

    public List<PushStep> getStep() {
        return step;
    }

    public PushInfo setStep(List<PushStep> step) {
        this.step = step;
        return this;
    }
}
