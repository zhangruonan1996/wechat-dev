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

}
