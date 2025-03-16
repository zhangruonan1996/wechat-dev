package org.zhangruonan.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.zhangruonan.bo.FriendCircleBO;
import org.zhangruonan.grace.result.GraceJSONResult;
import org.zhangruonan.service.FriendCircleService;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 10:52:11
 */
@RestController
@RequestMapping("/friendCircle")
public class FriendCircleController {

    @Resource
    private FriendCircleService friendCircleService;

    /**
     * 发表朋友圈
     *
     * @return 发布结果反馈
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 11:11:39
     */
    @PostMapping("/publish")
    public GraceJSONResult publish(@RequestBody FriendCircleBO friendCircleBO, HttpServletRequest request) {
        friendCircleService.publish(friendCircleBO, request);
        return GraceJSONResult.ok();
    }

    /**
     * 分页查询朋友圈图文列表
     *
     * @param userId   用户id
     * @param page     当前页（默认为1）
     * @param pageSize 每页显示条数（默认为10）
     * @return 朋友圈列表分页数据
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 11:42:37
     */
    @PostMapping("/queryList")
    public GraceJSONResult queryList(String userId,
                                     @RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return GraceJSONResult.ok(friendCircleService.queryList(userId, page, pageSize));
    }

    /**
     * 点赞朋友的朋友圈
     *
     * @param friendCircleId 要进行点赞的朋友圈id
     * @param request 本次请求对象（用于获取请求用户id）
     * @return 点赞结果反馈
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 12:17:10
     */
    @PostMapping("/like")
    public GraceJSONResult like(String friendCircleId, HttpServletRequest request) {
        friendCircleService.like(friendCircleId, request);
        return GraceJSONResult.ok();
    }

    /**
     * 取消点赞朋友的朋友圈
     *
     * @param friendCircleId 要取消点赞朋友圈id
     * @param request 本次请求对象（用于获取请求用户id）
     * @return 取消点赞结果反馈
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 12:17:53
     */
    @PostMapping("/unlike")
    public GraceJSONResult unlike(String friendCircleId, HttpServletRequest request) {
        friendCircleService.unlike(friendCircleId, request);
        return GraceJSONResult.ok();
    }

}
