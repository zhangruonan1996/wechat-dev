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

    @PostMapping("/create")
    public GraceJSONResult createComment(@RequestBody CommentBO commentBO, HttpServletRequest request) {
        return GraceJSONResult.ok(commentService.createComment(commentBO));
    }

}
