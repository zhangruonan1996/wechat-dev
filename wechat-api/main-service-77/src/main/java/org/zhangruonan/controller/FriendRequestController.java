package org.zhangruonan.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zhangruonan.bo.NewFriendRequestBO;
import org.zhangruonan.grace.result.GraceJSONResult;
import org.zhangruonan.pojo.FriendRequest;
import org.zhangruonan.service.FriendRequestService;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-15 12:38:39
 */
@RestController
@RequestMapping("/friendRequest")
public class FriendRequestController {

    @Autowired
    private FriendRequestService friendRequestService;

    /**
     * 发起好友申请
     *
     * @param bo 请求参数
     * @return 发起结果反馈
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 13:16:21
     */
    @PostMapping("/add")
    public GraceJSONResult addFriendRequest(@Valid @RequestBody NewFriendRequestBO bo) {
        friendRequestService.addNewFriendRequest(bo);
        return GraceJSONResult.ok();
    }

}
