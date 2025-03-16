package org.zhangruonan.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zhangruonan.bo.CommentBO;
import org.zhangruonan.grace.result.GraceJSONResult;
import org.zhangruonan.service.CommentService;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 13:16:55
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 创建评论消息
     *
     * @param commentBO 评论消息
     * @param request 本次请求对象
     * @return 评论消息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 13:52:02
     */
    @PostMapping("/create")
    public GraceJSONResult createComment(@RequestBody CommentBO commentBO, HttpServletRequest request) {
        return GraceJSONResult.ok(commentService.createComment(commentBO));
    }

    /**
     * 查询某条朋友圈的所有评论消息
     *
     * @param friendCircleId 朋友圈id
     * @return 某条朋友圈的所有评论消息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 13:51:38
     */
    @PostMapping("/query")
    public GraceJSONResult queryComment(String friendCircleId) {
        return GraceJSONResult.ok(commentService.queryAllComment(friendCircleId));
    }

}
