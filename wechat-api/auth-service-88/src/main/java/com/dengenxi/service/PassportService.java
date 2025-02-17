package com.dengenxi.service;

import com.dengenxi.bo.RegistLoginBO;
import com.dengenxi.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 21:04:56
 */
public interface PassportService {

    /**
     * 获取短信验证码
     *
     * @param mobile 手机号
     * @param request 本次请求对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 21:07:06
     */
    void smsTask(String mobile, HttpServletRequest request) throws Exception;

    /**
     * 注册
     *
     * @param registLoginBO
     * @param request
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 21:05:41
     */
    UserVO regist(RegistLoginBO registLoginBO, HttpServletRequest request);

    /**
     * 登录
     *
     * @param registLoginBO
     * @param request
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-16 18:34:53
     */
    UserVO login(RegistLoginBO registLoginBO, HttpServletRequest request);

    /**
     * 一键注册登录接口，可以同时提供给用户做登录和注册使用调用
     *
     * @param registLoginBO
     * @param request
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-17 20:35:52
     */
    UserVO registOrLogin(RegistLoginBO registLoginBO, HttpServletRequest request);

    /**
     * 退出登录
     *
     * @param userId 用户id
     * @param request 本次请求对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-16 19:16:39
     */
    void logout(String userId, HttpServletRequest request);
}
