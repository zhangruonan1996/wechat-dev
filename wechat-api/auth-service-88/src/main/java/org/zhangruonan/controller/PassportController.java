package org.zhangruonan.controller;

import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.bo.RegistLoginBO;
import org.zhangruonan.grace.result.GraceJSONResult;
import org.zhangruonan.service.PassportService;
import org.zhangruonan.service.UserService;
import org.zhangruonan.vo.UserVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    @Autowired
    private UserService userService;

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

    /**
     * 一键注册登录接口，可以同时提供给用户做登录和注册使用调用
     *
     * @param registLoginBO
     * @param request
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-17 20:32:33
     */
    @PostMapping("registOrLogin")
    public GraceJSONResult registOrLogin(@Validated @RequestBody RegistLoginBO registLoginBO, HttpServletRequest request) {
        UserVO userVO = passportService.registOrLogin(registLoginBO, request);
        return GraceJSONResult.ok(userVO);
    }

    /**
     * 退出登录
     *
     * @param userId 用户编号
     * @param request 本次请求对象
     * @return 请求结果反馈
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-16 19:15:34
     */
    @PostMapping("/logout")
    public GraceJSONResult logout(@RequestParam String userId, HttpServletRequest request) {
        passportService.logout(userId, request);
        return GraceJSONResult.ok();
    }

}
