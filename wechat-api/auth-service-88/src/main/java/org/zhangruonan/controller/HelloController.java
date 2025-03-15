package org.zhangruonan.controller;

import org.zhangruonan.tasks.SmsTask;
import org.zhangruonan.utils.MyInfo;
import jakarta.annotation.Resource;
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

    @Resource
    private SmsTask smsTask;

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
