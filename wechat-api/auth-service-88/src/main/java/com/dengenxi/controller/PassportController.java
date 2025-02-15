package com.dengenxi.controller;

import com.dengenxi.base.BaseInfoProperties;
import com.dengenxi.bo.RegistLoginBO;
import com.dengenxi.exceptions.GraceException;
import com.dengenxi.grace.result.GraceJSONResult;
import com.dengenxi.grace.result.ResponseStatusEnum;
import com.dengenxi.pojo.User;
import com.dengenxi.service.PassportService;
import com.dengenxi.service.UserService;
import com.dengenxi.tasks.SmsTask;
import com.dengenxi.utils.IPUtil;
import com.dengenxi.utils.MyInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    private PassportService passportService;

    /**
     * 获取短信验证码
     *
     * @param mobile  手机号
     * @param request 本次请求对象
     * @return 请求结果反馈
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 21:08:08
     */
    @PostMapping("/getSMSCode")
    public GraceJSONResult smsTask(String mobile, HttpServletRequest request) throws Exception {
        passportService.smsTask(mobile, request);
        return GraceJSONResult.ok();
    }

    /**
     * @param registLoginBO
     * @param request
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 20:14:36
     */
    @PostMapping("/regist")
    public GraceJSONResult regist(@RequestBody RegistLoginBO registLoginBO, HttpServletRequest request) {
        User user = passportService.regist(registLoginBO, request);
        return GraceJSONResult.ok(user);
    }

}
