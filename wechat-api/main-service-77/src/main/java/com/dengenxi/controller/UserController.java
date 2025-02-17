package com.dengenxi.controller;

import com.dengenxi.bo.ModifyUserBO;
import com.dengenxi.grace.result.GraceJSONResult;
import com.dengenxi.service.UserService;
import com.dengenxi.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        UserVO userVO = userService.modifyUserInfo(modifyUserBO);
        return GraceJSONResult.ok(userVO);
    }

    /**
     * 根据用户id查询用户数据
     *
     * @param userId 用户id
     * @return 用户数据
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-17 18:59:22
     */
    @PostMapping("/get")
    public GraceJSONResult get(@RequestParam("userId") String userId) {
        return GraceJSONResult.ok(userService.getUserInfo(userId, false));
    }

}
