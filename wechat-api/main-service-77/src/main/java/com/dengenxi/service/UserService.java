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

    /**
     * 更新用户头像
     *
     * @param userId 用户id
     * @param face 用户头像
     * @return 更新后的用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 00:01:29
     */
    UserVO updateFace(String userId, String face);

    /**
     * 更新用户朋友圈背景图
     *
     * @param userId 用户id
     * @param friendCircleBg 朋友圈背景图
     * @return 最新用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 20:03:21
     */
    UserVO updateFriendCircleBg(String userId, String friendCircleBg);

    /**
     * 更新用户聊天背景图
     *
     * @param userId 用户id
     * @param chatBg 聊天背景图地址
     * @return 最新用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 20:16:34
     */
    UserVO updateChatBg(String userId, String chatBg);
}
