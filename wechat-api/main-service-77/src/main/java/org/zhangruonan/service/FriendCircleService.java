package org.zhangruonan.service;

import jakarta.servlet.http.HttpServletRequest;
import org.zhangruonan.bo.FriendCircleBO;
import org.zhangruonan.utils.PagedGridResult;

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

}
