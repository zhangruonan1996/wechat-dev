package com.dengenxi.service.impl;

import com.dengenxi.base.BaseInfoProperties;
import com.dengenxi.bo.RegistLoginBO;
import com.dengenxi.exceptions.GraceException;
import com.dengenxi.grace.result.ResponseStatusEnum;
import com.dengenxi.pojo.User;
import com.dengenxi.service.PassportService;
import com.dengenxi.service.UserService;
import com.dengenxi.tasks.SmsTask;
import com.dengenxi.utils.IPUtil;
import com.dengenxi.utils.MyInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 21:05:07
 */
@Service
public class PassportServiceImpl extends BaseInfoProperties implements PassportService {

    @Resource
    private SmsTask smsTask;

    @Resource
    private UserService userService;

    /**
     * 获取短信验证码
     *
     * @param mobile 手机号
     * @param request 本次请求对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 21:08:54
     */
    @Override
    public void smsTask(String mobile, HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(mobile)) {
            GraceException.display(ResponseStatusEnum.FAILED);
        }

        // 获取用户的手机号/IP
        String userIP = IPUtil.getRequestIp(request);
        // 限制该用户的手机号/IP，在60秒内只能获得一次验证码
        redis.setnx60s(MOBILE_SMSCODE + ":" + userIP, mobile);

        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        smsTask.sendSmsInTask(MyInfo.getMobile(), code);

        // 把验证码存入redis，用于后续的注册/登录的校验
        redis.set(MOBILE_SMSCODE + ":" + mobile, code, 60);
    }

    /**
     *
     *
     * @param registLoginBO
     * @param request
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 21:09:05
     */
    @Override
    public User regist(RegistLoginBO registLoginBO, HttpServletRequest request) {
        String mobile = registLoginBO.getMobile();
        String smsCode = registLoginBO.getSmsCode();
        String nickname = registLoginBO.getNickname();

        // 1. 从redis中获得验证码进行校验判断是否匹配
        String redisCode = redis.get(MOBILE_SMSCODE + ":" + mobile);
        if (StringUtils.isBlank(redisCode) || !redisCode.equalsIgnoreCase(smsCode)) {
            GraceException.display(ResponseStatusEnum.SMS_CODE_ERROR);
        }

        // 2. 根据mobile查询数据库,如果用户存在,则提示不能重复注册
        User dbUser = userService.queryByMobile(mobile);
        // 2.1 如果查询数据库中用户为空,则表示以用户没有注册过,则需要进行用户信息数据的入库
        if (dbUser == null) {
            dbUser = userService.createUser(mobile, nickname);
        } else {
            GraceException.display(ResponseStatusEnum.USER_ALREADY_EXIST_ERROR);
        }
        // 3. 用户注册成功后,删除redis中的短信验证码使其失效
        redis.del(MOBILE_SMSCODE + ":" + mobile);
        // 4. 返回用户数据给前端
        return dbUser;
    }

    /**
     * 登录
     *
     * @param registLoginBO
     * @param request
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-16 18:35:05
     */
    @Override
    public User login(RegistLoginBO registLoginBO, HttpServletRequest request) {
        String mobile = registLoginBO.getMobile();
        String code = registLoginBO.getSmsCode();

        // 从redis中获得验证码进行校验，判断是否匹配
        String redisCode = redis.get(MOBILE_SMSCODE + ":" + mobile);
        if (StringUtils.isBlank(redisCode) || !redisCode.equalsIgnoreCase(code)) {
            GraceException.display(ResponseStatusEnum.SMS_CODE_ERROR);
        }

        // 查询数据库
        User user = userService.queryByMobile(mobile);
        if (user == null) {
            // 如果用户不存在,返回错误信息
            GraceException.display(ResponseStatusEnum.USER_NOT_EXIST_ERROR);
        }

        // 用户登录成功后，立即作废当前验证码
        redis.del(MOBILE_SMSCODE + ":" + mobile);

        return user;
    }
}
