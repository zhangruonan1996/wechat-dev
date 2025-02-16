package com.dengenxi.service;

import com.dengenxi.bo.RegistLoginBO;
import com.dengenxi.pojo.User;
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
    User regist(RegistLoginBO registLoginBO, HttpServletRequest request);

    /**
     * 登录
     *
     * @param registLoginBO
     * @param request
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-16 18:34:53
     */
    User login(RegistLoginBO registLoginBO, HttpServletRequest request);

}
