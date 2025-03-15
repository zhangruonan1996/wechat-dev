package org.zhangruonan.service;

import jakarta.servlet.http.HttpServletRequest;
import org.zhangruonan.bo.NewFriendRequestBO;
import org.zhangruonan.utils.PagedGridResult;

/**
 * 好友请求服务类
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-15 13:17:18
 */
public interface FriendRequestService {

    /**
     * 添加新的好友申请
     *
     * @param bo 好友申请参数
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 13:19:44
     */
    void addNewFriendRequest(NewFriendRequestBO bo);

    /**
     * 分页查询用户请求列表
     *
     * @param request 当前请求对象
     * @param page 当前页（不传默认为1）
     * @param pageSize 每页显示的数量（不传默认为10）
     * @return 好友申请列表分页数据
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 13:39:25
     */
    PagedGridResult queryNewFriendRequest(HttpServletRequest request, Integer page, Integer pageSize);
}
