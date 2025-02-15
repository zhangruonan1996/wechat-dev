package com.dengenxi.controller;

import com.dengenxi.SmsTask;
import com.dengenxi.utils.MyInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 11:23:28
 */
@RestController
@RequestMapping("/auth")
public class HelloController {

    private final SmsTask smsTask;

    public HelloController(SmsTask smsTask) {
        this.smsTask = smsTask;
    }

    @GetMapping("/hello")
    public Object hello() {
        return "你好，邓恩熙";
    }

    @GetMapping("smsTask")
    public Object smsTask() throws Exception {
        smsTask.sendSmsInTask(MyInfo.getMobile(), "1314");
        return "Send Sms In Task Ok";
    }

}
