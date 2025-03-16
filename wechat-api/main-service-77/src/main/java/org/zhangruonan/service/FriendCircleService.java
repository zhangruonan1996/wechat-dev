package org.zhangruonan.service;

import jakarta.servlet.http.HttpServletRequest;
import org.zhangruonan.bo.FriendCircleBO;
import org.zhangruonan.pojo.FriendCircleLiked;
import org.zhangruonan.utils.PagedGridResult;

import java.util.List;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 11:05:01
 */
public interface FriendCircleService {

    /**
     * 发表朋友圈
     *
     * @param friendCircleBO 朋友圈内容
     * @param request        本次请求对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 11:13:48
     */
    void publish(FriendCircleBO friendCircleBO, HttpServletRequest request);

    /**
     * 分页查询朋友圈图文列表
     *
     * @param userId   用户id
     * @param page     当前页（默认为1）
     * @param pageSize 每页显示数量（默认为10）
     * @return 分页数据
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 11:44:11
     */
    PagedGridResult queryList(String userId, Integer page, Integer pageSize);

    /**
     * 点赞朋友圈
     *
     * @param friendCircleId 朋友圈id
     * @param request 本次请求对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 12:19:21
     */
    void like(String friendCircleId, HttpServletRequest request);

    /**
     * 取消点赞朋友圈
     *
     * @param friendCircleId 朋友圈id
     * @param request 本次请求对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 12:19:53
     */
    void unlike(String friendCircleId, HttpServletRequest request);

    /**
     * 根据朋友圈id查询点赞的朋友列表
     *
     * @param friendCircleId 朋友圈id
     * @return 点赞过的朋友列表
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 13:14:11
     */
    List<FriendCircleLiked> queryLikedFriends(String friendCircleId);

    /**
     * 判断用户是否点赞过朋友圈
     *
     * @param friendCircleId 朋友圈id
     * @param userId 用户id
     * @return 是否点赞过朋友圈
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 13:05:38
     */
    Boolean doILike(String friendCircleId, String userId);

    /**
     * 删除朋友圈
     *
     * @param friendCircleId 朋友圈id
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 17:19:15
     */
    void delete(String friendCircleId, HttpServletRequest request);
}
