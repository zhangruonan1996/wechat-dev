package org.zhangruonan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.bo.CommentBO;
import org.zhangruonan.exceptions.GraceException;
import org.zhangruonan.grace.result.ResponseStatusEnum;
import org.zhangruonan.mapper.CommentMapper;
import org.zhangruonan.pojo.Comment;
import org.zhangruonan.pojo.User;
import org.zhangruonan.service.CommentService;
import org.zhangruonan.service.UserService;
import org.zhangruonan.vo.CommentVO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 查询某条朋友圈的全部评论
     *
     * @param friendCircleId 朋友圈id
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 13:37:47
     */
    @Override
    public List<CommentVO> queryAllComment(String friendCircleId) {
        Map<String, Object> map = new HashMap<>();
        map.put("friendCircleId", friendCircleId);
        return commentMapper.getFriendCircleComments(map);
    }

    /**
     * 删除某条评论
     *
     * @param commentUserId 评论用户id
     * @param commentId 评论id
     * @param friendCircleId 朋友圈id
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 17:12:36
     */
    @Override
    public void deleteComment(String commentUserId, String commentId, String friendCircleId) {
        // 参数校验
        if (StringUtils.isEmpty(commentUserId) || StringUtils.isEmpty(commentId) || StringUtils.isEmpty(friendCircleId)) {
            GraceException.display(ResponseStatusEnum.FAILED);
        }
        // 构建删除条件
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getId, commentId);
        lambdaQueryWrapper.eq(Comment::getCommentUserId, commentUserId);
        lambdaQueryWrapper.eq(Comment::getFriendCircleId, friendCircleId);
        // 删除数据
        commentMapper.delete(lambdaQueryWrapper);
    }

    /**
     * 根据朋友圈id删除评论信息
     *
     * @param friendCircleId 朋友圈id
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 17:32:37
     */
    @Override
    public void deleteByFriendCircleId(String friendCircleId) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getFriendCircleId, friendCircleId);
        commentMapper.delete(lambdaQueryWrapper);
    }
}
