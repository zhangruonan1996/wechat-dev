package com.dengenxi.controller;

import com.dengenxi.base.BaseInfoProperties;
import com.dengenxi.grace.result.GraceJSONResult;
import com.dengenxi.tasks.SmsTask;
import com.dengenxi.utils.IPUtil;
import com.dengenxi.utils.MyInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 18:38:30
 */
@RestController
@RequestMapping("/passport")
@Slf4j
public class PassportController extends BaseInfoProperties {

    @Resource
    private SmsTask smsTask;

    @GetMapping("/getSmsCode")
    public GraceJSONResult smsTask(String mobile, HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(mobile)) {
            return GraceJSONResult.error();
        }

        // 获取用户的手机号/IP
        String userIP = IPUtil.getRequestIp(request);
        // 限制该用户的手机号/IP，在60秒内只能获得一次验证码
        redis.setnx60s(MOBILE_SMSCODE + ":" + userIP, mobile);

        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        smsTask.sendSmsInTask(MyInfo.getMobile(), code);

        // 把验证码存入redis，用于后续的注册/登录的校验
        redis.set(MOBILE_SMSCODE + ":" + mobile, code, 60);

        return GraceJSONResult.ok();
    }

}
