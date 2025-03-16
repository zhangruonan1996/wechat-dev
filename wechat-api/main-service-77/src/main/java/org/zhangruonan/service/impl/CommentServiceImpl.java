package org.zhangruonan.service.impl;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.bo.CommentBO;
import org.zhangruonan.mapper.CommentMapper;
import org.zhangruonan.pojo.Comment;
import org.zhangruonan.pojo.User;
import org.zhangruonan.service.CommentService;
import org.zhangruonan.service.UserService;
import org.zhangruonan.vo.CommentVO;

import java.time.LocalDateTime;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 11:08:51
 */
@Service
@Slf4j
public class CommentServiceImpl extends BaseInfoProperties implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserService userService;

    /**
     * 创建一条评论
     *
     * @param commentBO 评论信息
     * @return 创建的评论信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 13:24:50
     */
    @Override
    @Transactional
    public CommentVO createComment(CommentBO commentBO) {
        // 创建一个Comment对象
        Comment peddingComment = new Comment();
        // 将前端传递过来的数据拷贝到Comment对象中
        BeanUtils.copyProperties(commentBO, peddingComment);
        // 设置评论时间
        peddingComment.setCreatedTime(LocalDateTime.now());
        // 插入到数据库
        commentMapper.insert(peddingComment);
        // 将最新评论数据返回给前端
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(peddingComment, commentVO);

        User user = userService.queryById(commentBO.getCommentUserId());
        commentVO.setCommentUserNickname(user.getNickname());
        commentVO.setCommentUserFace(user.getFace());
        commentVO.setCommentId(peddingComment.getId());
        return commentVO;
    }
}
