package org.zhangruonan.service;

import jakarta.servlet.http.HttpServletRequest;
import org.zhangruonan.bo.CommentBO;
import org.zhangruonan.vo.CommentVO;

import java.util.List;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 11:08:43
 */
public interface CommentService {

    /**
     * 创建一条评论
     *
     * @param commentBO 评论信息
     * @return 创建的评论信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 13:24:01
     */
    CommentVO createComment(CommentBO commentBO);

    /**
     * 查询某条朋友圈的全部评论
     *
     * @param friendCircleId 朋友圈id
     * @return 某条朋友圈的全部评论
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 13:37:12
     */
    List<CommentVO> queryAllComment(String friendCircleId);

    /**
     * 删除某条评论
     *
     * @param commentUserId 评论用户id
     * @param commentId 评论id
     * @param friendCircleId 朋友圈id
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 17:11:56
     */
    void deleteComment(String commentUserId, String commentId, String friendCircleId);

    /**
     * 根据朋友圈id删除评论信息
     *
     * @param friendCircleId 朋友圈id
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 17:32:12
     */
    void deleteByFriendCircleId(String friendCircleId);
}
