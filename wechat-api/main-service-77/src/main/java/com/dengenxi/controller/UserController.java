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

    /**
     * 更新用户头像
     *
     * @param userId 用户id
     * @param face 用户头像
     * @return 更新结果反馈
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 00:01:04
     */
    @PostMapping("/updateFace")
    public GraceJSONResult updateFace(@RequestParam("userId") String userId, @RequestParam("face") String face) {
        UserVO userVO = userService.updateFace(userId, face);
        return GraceJSONResult.ok(userVO);
    }

    /**
     * 更新用户朋友圈背景图
     *
     * @param userId 用户id
     * @param friendCircleBg 朋友圈背景图地址
     * @return 最新用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 20:02:42
     */
    @PostMapping("/updateFriendCircleBg")
    public GraceJSONResult updateFriendCircleBg(@RequestParam("userId") String userId, @RequestParam("friendCircleBg") String friendCircleBg) {
        UserVO userVO = userService.updateFriendCircleBg(userId, friendCircleBg);
        return GraceJSONResult.ok(userVO);
    }

}
