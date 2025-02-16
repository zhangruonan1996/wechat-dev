package com.dengenxi.controller;

import com.dengenxi.base.BaseInfoProperties;
import com.dengenxi.bo.RegistLoginBO;
import com.dengenxi.grace.result.GraceJSONResult;
import com.dengenxi.pojo.User;
import com.dengenxi.service.PassportService;
import com.dengenxi.vo.UserVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * 注册
     *
     * @param registLoginBO
     * @param request
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 20:14:36
     */
    @PostMapping("/regist")
    public GraceJSONResult regist(@Validated @RequestBody RegistLoginBO registLoginBO, HttpServletRequest request) {
        UserVO userVO = passportService.regist(registLoginBO, request);
        return GraceJSONResult.ok(userVO);
    }

    /**
     * 登录
     *
     * @param registLoginBO
     * @param request
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-16 18:34:11
     */
    @PostMapping("/login")
    public GraceJSONResult login(@Validated @RequestBody RegistLoginBO registLoginBO, HttpServletRequest request) {
        UserVO userVO = passportService.login(registLoginBO, request);
        return GraceJSONResult.ok(userVO);
    }

}
