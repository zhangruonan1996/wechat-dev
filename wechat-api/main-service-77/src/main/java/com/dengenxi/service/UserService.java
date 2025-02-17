package com.dengenxi.service;

import com.dengenxi.bo.ModifyUserBO;
import com.dengenxi.pojo.User;
import com.dengenxi.vo.UserVO;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-16 23:18:28
 */
public interface UserService {

    /**
     * 修改用户信息
     *
     * @param userBO
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-16 23:21:12
     */
    UserVO modifyUserInfo(ModifyUserBO userBO);

    /**
     * 根据用户id查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-17 18:51:36
     */
    User queryById(String userId);

    /**
     * 获取最新用户信息
     *
     * @param userId 用户id
     * @param needToken 是否需要token
     * @return 最新用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-17 18:58:45
     */
    UserVO getUserInfo(String userId, Boolean needToken);

}
