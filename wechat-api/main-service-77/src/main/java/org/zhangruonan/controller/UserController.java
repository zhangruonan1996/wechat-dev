package org.zhangruonan.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.zhangruonan.bo.ModifyUserBO;
import org.zhangruonan.grace.result.GraceJSONResult;
import org.zhangruonan.pojo.User;
import org.zhangruonan.service.UserService;
import org.zhangruonan.vo.UserVO;
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

    /**
     * 更新用户聊天背景图
     *
     * @param userId 用户id
     * @param chatBg 聊天背景图地址
     * @return 最新用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 20:15:49
     */
    @PostMapping("/updateChatBg")
    public GraceJSONResult updateChatBg(@RequestParam("userId") String userId, @RequestParam("chatBg") String chatBg) {
        UserVO userVO = userService.updateChatBg(userId, chatBg);
        return GraceJSONResult.ok(userVO);
    }

    /**
     * 根据手机号或者微信号查询用户
     *
     * @param queryString 手机号或微信号
     * @return 用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 11:49:15
     */
    @PostMapping("/queryFriend")
    public GraceJSONResult queryFriend(@RequestParam("queryString") String queryString, HttpServletRequest request) {
        return GraceJSONResult.ok(userService.getUserByWechatNumOrMobile(queryString, request));
    }

}
