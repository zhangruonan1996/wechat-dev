package com.dengenxi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dengenxi.base.BaseInfoProperties;
import com.dengenxi.bo.ModifyUserBO;
import com.dengenxi.exceptions.GraceException;
import com.dengenxi.grace.result.ResponseStatusEnum;
import com.dengenxi.mapper.UserMapper;
import com.dengenxi.pojo.User;
import com.dengenxi.service.UserService;
import com.dengenxi.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-16 23:18:38
 */
@Service
public class UserServiceImpl extends BaseInfoProperties implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 修改用户信息
     *
     * @param userBO
     * @return 最新用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-16 23:27:33
     */
    @Override
    @Transactional
    public UserVO modifyUserInfo(ModifyUserBO userBO) {

        String wechatNum = userBO.getWechatNum();

        User pendingUser = new User();

        String userId = userBO.getUserId();

        if (StringUtils.isBlank(userId)) {
            GraceException.display(ResponseStatusEnum.USER_INFO_UPDATED_ERROR);
        }

        if (StringUtils.isNotBlank(wechatNum)) {
            String isExist = redis.get(REDIS_USER_ALREADY_UPDATE_WECHAT_NUM + ":" + userId);
            if (StringUtils.isNotBlank(isExist)) {
                GraceException.display(ResponseStatusEnum.WECHAT_NUM_ALREADY_MODIFIED_ERROR);
            }
        }

        BeanUtils.copyProperties(userBO, pendingUser);
        pendingUser.setId(userId);
        pendingUser.setUpdatedTime(LocalDateTime.now());

        userMapper.updateById(pendingUser);

        // 如果用户修改微信号，则只能修改一次
        if (StringUtils.isNotBlank(wechatNum)) {
            redis.setByDays(REDIS_USER_ALREADY_UPDATE_WECHAT_NUM + ":" + userId, userId, 365);
        }

        UserVO userVO = getUserInfo(userBO.getUserId(), true);

        return userVO;

    }

    /**
     * 根据用户id查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-17 18:51:56
     */
    @Override
    public User queryById(String userId) {
        return userMapper.selectById(userId);
    }

    /**
     * 获取最新用户信息
     *
     * @param userId 用户id
     * @return 最新用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-17 18:53:18
     */
    public UserVO getUserInfo(String userId, Boolean needToken) {
        // 查询数据库获取最新的用户信息
        User latestUser = queryById(userId);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(latestUser, userVO);

        if (needToken) {
            String uToken = TOKEN_USER_PREFIX + SYMBOL_DOT + UUID.randomUUID();
            redis.set(REDIS_USER_TOKEN + ":" + userId, uToken);
            userVO.setUserToken(uToken);
        }

        return userVO;
    }
}
