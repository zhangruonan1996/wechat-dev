package org.zhangruonan.tasks;

import org.zhangruonan.utils.SMSUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 18:11:59
 */
@Component
@Slf4j
public class SmsTask {

    @Resource
    private SMSUtils smsUtils;

    @Async
    public void sendSmsInTask(String mobile, String code) throws Exception {
        // smsUtils.sendSMS(mobile, code);
        log.info("异步任务中所发送的验证码为：{}", code);
    }

}
