package com.dengenxi.controller;

import com.dengenxi.bo.ModifyUserBO;
import com.dengenxi.grace.result.GraceJSONResult;
import com.dengenxi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关接口
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-16 23:15:35
 */
@RestController
@RequestMapping("/userinfo")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 修改用户信息
     *
     * @param modifyUserBO
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-16 23:20:46
     */
    @PostMapping("/modify")
    public GraceJSONResult modify(@RequestBody ModifyUserBO modifyUserBO) {
        userService.modifyUserInfo(modifyUserBO);
        return GraceJSONResult.ok();
    }

}
