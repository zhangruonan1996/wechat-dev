package com.dengenxi.service;

import com.dengenxi.pojo.User;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 20:24:17
 */
public interface UserService {

    /**
     * 根据手机号查询用户数据
     *
     * @param mobile 手机号码
     * @return 查询到的用户数据
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 21:00:15
     */
    User queryByMobile(String mobile);

    /**
     * 创建用户信息，并且返回创建的用户对象
     *
     * @param mobile 手机号
     * @param nickname 用户昵称
     * @return 创建的用户对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 21:02:26
     */
    User createUser(String mobile, String nickname);

}
