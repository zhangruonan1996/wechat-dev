package org.zhangruonan.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zhangruonan.enums.YesOrNo;
import org.zhangruonan.grace.result.GraceJSONResult;
import org.zhangruonan.service.FriendshipService;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-15 16:12:05
 */
@RestController
@RequestMapping("/friendship")
public class FriendshipController {

    @Resource
    private FriendshipService friendshipService;

    /**
     * 获取朋友关系
     *
     * @param friendId 好友id
     * @param request  本次请求对象
     * @return 朋友关系
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 16:23:19
     */
    @PostMapping("/getFriendship")
    public GraceJSONResult getFriendship(String friendId, HttpServletRequest request) {
        return GraceJSONResult.ok(friendshipService.getFriendship(friendId, request));
    }

    /**
     * 查询好友列表
     *
     * @param request 本次请求对象
     * @return 好友列表
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 16:23:42
     */
    @PostMapping("/queryMyFriends")
    public GraceJSONResult queryMyFriends(HttpServletRequest request) {
        return GraceJSONResult.ok(friendshipService.queryMyFriends(request, false));
    }

    /**
     * 修改好友备注
     *
     * @param friendId     好友id
     * @param friendRemark 备注
     * @param request      本次请求对象
     * @return 修改结果反馈
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 22:47:48
     */
    @PostMapping("/updateFriendRemark")
    public GraceJSONResult updateFriendRemark(String friendId, String friendRemark, HttpServletRequest request) {
        friendshipService.updateFriendRemark(friendId, friendRemark, request);
        return GraceJSONResult.ok();
    }

    /**
     * 将好友加入黑名单
     *
     * @param friendId 好友id
     * @param request 本次请求对象
     * @return 结果反馈
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 22:58:22
     */
    @PostMapping("/tobeBlack")
    public GraceJSONResult tobeBlack(String friendId, HttpServletRequest request) {
        friendshipService.updateBlackList(friendId, request, YesOrNo.YES);
        return GraceJSONResult.ok();
    }

    /**
     * 将好友移出黑名单
     *
     * @param friendId 好友id
     * @param request 本次请求对象
     * @return 结果反馈
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 22:58:28
     */
    @PostMapping("/moveOutBlack")
    public GraceJSONResult moveOutBlack(String friendId, HttpServletRequest request) {
        friendshipService.updateBlackList(friendId, request, YesOrNo.NO);
        return GraceJSONResult.ok();
    }

    /**
     * 获取当前用户的黑名单列表
     *
     * @param request 本次请求对象
     * @return 当前用户的黑名单列表
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 23:14:04
     */
    @PostMapping("/queryMyBlackList")
    public GraceJSONResult queryMyBlackList(HttpServletRequest request) {
        return GraceJSONResult.ok(friendshipService.queryMyFriends(request, true));
    }

    /**
     * 删除好友关系
     *
     * @param friendId 好友id
     * @param request 本次请求对象
     * @return 请求结果反馈
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 23:20:32
     */
    @PostMapping("/delete")
    public GraceJSONResult delete(String friendId, HttpServletRequest request) {
        friendshipService.delete(friendId, request);
        return GraceJSONResult.ok();
    }

    /**
     * 判断两个朋友之间的关系是否拉黑
     *
     * @param friendId1st 用户1id
     * @param friendId2nd 用户2id
     * @return 是否拉黑
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-21 19:28:57
     */
    @GetMapping("/isBlack")
    public GraceJSONResult isBlack(String friendId1st, String friendId2nd) {
        Boolean result = friendshipService.isBlackEachOther(friendId1st, friendId2nd);
        return GraceJSONResult.ok(result);
    }

}
