package org.zhangruonan.service.impl;

import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.bo.RegistLoginBO;
import org.zhangruonan.exceptions.GraceException;
import org.zhangruonan.grace.result.ResponseStatusEnum;
import org.zhangruonan.pojo.User;
import org.zhangruonan.service.PassportService;
import org.zhangruonan.service.UserService;
import org.zhangruonan.tasks.SmsTask;
import org.zhangruonan.utils.IPUtil;
import org.zhangruonan.utils.MyInfo;
import org.zhangruonan.vo.UserVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
     * 注册
     *
     * @param registLoginBO
     * @param request
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 21:09:05
     */
    @Override
    public UserVO regist(RegistLoginBO registLoginBO, HttpServletRequest request) {
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

        // 设置用户分布式回话，保存用户的token令牌，存储到redis
        String uToken = TOKEN_USER_PREFIX + SYMBOL_DOT + UUID.randomUUID();
        // 本方式只能限制用户在一台设备进行登录
        // redis.set(REDIS_USER_TOKEN + ":" + user.getId(), uToken);
        // 本方式允许用户在多端多设备进行登录
        redis.set(REDIS_USER_TOKEN + ":" + uToken, dbUser.getId());

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(dbUser, userVO);
        userVO.setUserToken(uToken);

        // 4. 返回用户数据给前端
        return userVO;
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
    public UserVO login(RegistLoginBO registLoginBO, HttpServletRequest request) {
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

        // 设置用户分布式回话，保存用户的token令牌，存储到redis
        String uToken = TOKEN_USER_PREFIX + SYMBOL_DOT + UUID.randomUUID();
        // 本方式只能限制用户在一台设备进行登录
        // redis.set(REDIS_USER_TOKEN + ":" + user.getId(), uToken);
        // 本方式允许用户在多端多设备进行登录
        redis.set(REDIS_USER_TOKEN + ":" + uToken, user.getId());

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setUserToken(uToken);

        return userVO;
    }

    /**
     * 一键注册登录接口，可以同时提供给用户做登录和注册使用调用
     *
     * @param registLoginBO
     * @param request
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-17 20:36:27
     */
    @Override
    public UserVO registOrLogin(RegistLoginBO registLoginBO, HttpServletRequest request) {
        String mobile = registLoginBO.getMobile();
        String code = registLoginBO.getSmsCode();
        String nickname = registLoginBO.getNickname();

        // 从redis中获取验证码进行校验
        String redisCode = redis.get(MOBILE_SMSCODE + ":" + mobile);
        if (StringUtils.isBlank(redisCode) || !redisCode.equalsIgnoreCase(code)) {
            GraceException.display(ResponseStatusEnum.SMS_CODE_ERROR);
        }

        // 根据手机号查询数据库是否存在用户
        User user = userService.queryByMobile(mobile);
        if (user == null) {
            // 如果数据库没有该用户，则表示该用户未注册，需要进行用户注册
            user = userService.createUser(mobile, nickname);
        }

        // 作废redis验证码
        redis.del(MOBILE_SMSCODE + ":" + mobile);

        // 设置用户分布式会话，保存用户的token令牌，存储到redis
        String uToken = TOKEN_USER_PREFIX + SYMBOL_DOT + UUID.randomUUID();
        // 本方式只能限制用户在一台设备进行登录
        // redis.set(REDIS_USER_TOKEN + ":" + user.getId(), uToken);
        // 本方式允许用户在多端多设备进行登录
        redis.set(REDIS_USER_TOKEN + ":" + uToken, user.getId());

        // 返回用户数据给前端
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setUserToken(uToken);

        return userVO;
    }

    /**
     * 退出登录
     *
     * @param userId 用户id
     * @param request 本次请求对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-16 19:16:56
     */
    @Override
    public void logout(String userId, HttpServletRequest request) {
        // 清理用户的分布式会话
        redis.del(REDIS_USER_TOKEN + ":" + userId);
    }
}
