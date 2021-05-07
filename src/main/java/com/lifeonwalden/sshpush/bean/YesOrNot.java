package com.lifeonwalden.sshpush.bean;

import com.beust.jcommander.Parameter;

public class YesOrNot {

    @Parameter(names = "-confirm", description = "Yes(Y) Or Not(n)", password = true, echoInput = true)
    private String yesOrNot = "Y";

    public String getYesOrNot() {
        return yesOrNot;
    }

    public YesOrNot setYesOrNot(String yesOrNot) {
        this.yesOrNot = yesOrNot;
        return this;
    }
}
