package org.zhangruonan.service;

import jakarta.servlet.http.HttpServletRequest;
import org.zhangruonan.bo.CommentBO;
import org.zhangruonan.vo.CommentVO;

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

}
