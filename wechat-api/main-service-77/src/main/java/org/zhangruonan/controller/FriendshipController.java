package org.zhangruonan.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/getFriendship")
    public GraceJSONResult getFriendship(String friendId, HttpServletRequest request) {
        return GraceJSONResult.ok(friendshipService.getFriendship(friendId, request));
    }

}
