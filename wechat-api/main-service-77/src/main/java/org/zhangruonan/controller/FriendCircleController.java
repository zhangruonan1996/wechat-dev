package org.zhangruonan.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
