package org.zhangruonan.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zhangruonan.bo.NewFriendRequestBO;
import org.zhangruonan.grace.result.GraceJSONResult;
import org.zhangruonan.pojo.FriendRequest;
import org.zhangruonan.service.FriendRequestService;

import java.util.List;

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

    /**
     * 获取好友申请列表
     *
     * @param request 本次请求对象
     * @param page 当前页（不传默认为1）
     * @param pageSize 每页显示的数量（不传默认为10）
     * @return 好友申请列表分页数据
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 13:35:50
     */
    @PostMapping("/queryNew")
    public GraceJSONResult queryNewFriendRequest(HttpServletRequest request,
                                                 @RequestParam(defaultValue = "1", name = "page") Integer page,
                                                 @RequestParam(defaultValue = "10", name = "pageSize") Integer pageSize) {
        return GraceJSONResult.ok(friendRequestService.queryNewFriendRequest(request, page, pageSize));
    }

}
