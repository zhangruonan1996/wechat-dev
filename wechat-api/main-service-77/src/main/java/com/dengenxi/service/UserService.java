package com.dengenxi.service;

import com.dengenxi.bo.ModifyUserBO;

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
    void modifyUserInfo(ModifyUserBO userBO);

}
