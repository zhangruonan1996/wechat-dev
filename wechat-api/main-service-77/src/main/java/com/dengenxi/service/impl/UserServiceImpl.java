package com.dengenxi.service.impl;

import com.dengenxi.base.BaseInfoProperties;
import com.dengenxi.bo.ModifyUserBO;
import com.dengenxi.exceptions.GraceException;
import com.dengenxi.grace.result.ResponseStatusEnum;
import com.dengenxi.mapper.UserMapper;
import com.dengenxi.pojo.User;
import com.dengenxi.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-16 23:27:33
     */
    @Override
    @Transactional
    public void modifyUserInfo(ModifyUserBO userBO) {
        User pendingUser = new User();

        String userId = userBO.getUserId();
        if (StringUtils.isBlank(userId)) {
            GraceException.display(ResponseStatusEnum.USER_INFO_UPDATED_ERROR);
        }

        BeanUtils.copyProperties(userBO, pendingUser);
        pendingUser.setId(userId);
        pendingUser.setUpdatedTime(LocalDateTime.now());

        userMapper.updateById(pendingUser);
    }
}
