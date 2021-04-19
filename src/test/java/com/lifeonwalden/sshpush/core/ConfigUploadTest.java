package com.lifeonwalden.sshpush.core;

import com.alibaba.fastjson.JSON;
import com.lifeonwalden.sshpush.bean.HostInfo;
import com.lifeonwalden.sshpush.bean.PushInfo;
import com.lifeonwalden.sshpush.bean.PushStep;
import com.lifeonwalden.sshpush.process.AuthProcessor;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ConfigUploadTest {

    @Test
    public void parse() {
        try {
            PushInfo pushInfo = JSON.parseObject(ConfigUploadTest.class.getResourceAsStream("/exec.json"), PushInfo.class);

            Assert.assertNotNull(pushInfo.getHost());
            Assert.assertTrue(pushInfo.getHost().size() == 1);
            HostInfo host = pushInfo.getHost().get(0);
            Assert.assertEquals("localhost", host.getId());
            Assert.assertEquals("127.0.0.1", host.getHost());
            Assert.assertEquals("test", host.getUser());

            Assert.assertNotNull(pushInfo.getStep());
            Assert.assertTrue(pushInfo.getStep().size() == 1);
            PushStep step = pushInfo.getStep().get(0);
            Assert.assertEquals("localhost", step.getHostId());
            Assert.assertEquals("RX", step.getAction());
            Assert.assertEquals("ls", step.getExec());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkpassword(){
        PushInfo pushInfo = null;
        try {
            pushInfo = JSON.parseObject(ConfigUploadTest.class.getResourceAsStream("/exec.json"), PushInfo.class);
            AuthProcessor.process(pushInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
