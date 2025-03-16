package org.zhangruonan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.bo.FriendCircleBO;
import org.zhangruonan.exceptions.GraceException;
import org.zhangruonan.grace.result.ResponseStatusEnum;
import org.zhangruonan.mapper.FriendCircleLikedMapper;
import org.zhangruonan.mapper.FriendCircleMapper;
import org.zhangruonan.pojo.Comment;
import org.zhangruonan.pojo.FriendCircle;
import org.zhangruonan.pojo.FriendCircleLiked;
import org.zhangruonan.pojo.User;
import org.zhangruonan.service.CommentService;
import org.zhangruonan.service.FriendCircleService;
import org.zhangruonan.service.UserService;
import org.zhangruonan.utils.PagedGridResult;
import org.zhangruonan.vo.CommentVO;
import org.zhangruonan.vo.FriendCircleVO;
import org.zhangruonan.vo.UserVO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 11:04:54
 */
@Service
@Slf4j
public class FriendCircleServiceImpl extends BaseInfoProperties implements FriendCircleService {

    @Resource
    private FriendCircleMapper friendCircleMapper;

    @Resource
    private UserService userService;

    @Resource
    private FriendCircleLikedMapper friendCircleLikedMapper;

    @Resource
    private CommentService commentService;

    /**
     * 发表朋友圈
     *
     * @param friendCircleBO 朋友圈内容
     * @param request        本次请求对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 11:14:20
     */
    @Override
    @Transactional
    public void publish(FriendCircleBO friendCircleBO, HttpServletRequest request) {
        // 获取请求用户id
        String userId = request.getHeader(HEADER_USER_ID);
        // 设置发表用户id
        friendCircleBO.setUserId(userId);
        // 设置发表时间
        friendCircleBO.setPublishTime(LocalDateTime.now());

        // 新建一个朋友圈对象
        FriendCircle friendCircle = new FriendCircle();
        // 拷贝数据
        BeanUtils.copyProperties(friendCircleBO, friendCircle);

        // 保存数据到数据库
        friendCircleMapper.insert(friendCircle);
    }

    /**
     * 分页查询朋友圈图文列表
     *
     * @param userId   用户id
     * @param page     当前页（默认为1）
     * @param pageSize 每页显示数量（默认为10）
     * @return 分页数据
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 11:44:43
     */
    @Override
    public PagedGridResult queryList(String userId, Integer page, Integer pageSize) {
        // 如果未传递用户id，直接返回错误
        if (StringUtils.isBlank(userId)) {
            GraceException.display(ResponseStatusEnum.FAILED);
        }
        // 构建分页对象
        Page<FriendCircle> pageInfo = new Page<>(page, pageSize);
        // 构建查询条件
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        // 查询数据库数据
        friendCircleMapper.queryFriendCircleList(pageInfo, map);

        PagedGridResult pagedGridResult = setterPagedGridPlus(pageInfo);

        List<FriendCircleVO> list = (List<FriendCircleVO>) pagedGridResult.getRows();
        for (FriendCircleVO vo : list) {
            // 取出朋友圈id
            String friendCircleId = vo.getFriendCircleId();
            // 根据朋友圈id查询点赞过的朋友列表
            List<FriendCircleLiked> likedFriends = queryLikedFriends(friendCircleId);
            vo.setLikedFriends(likedFriends);
            // 判断用户是否点赞过朋友圈
            Boolean res = doILike(friendCircleId, userId);
            vo.setDoILike(res);

            List<CommentVO> commentList = commentService.queryAllComment(friendCircleId);
            vo.setCommentList(commentList);
        }

        return pagedGridResult;
    }

    /**
     * 点赞朋友圈
     *
     * @param friendCircleId 朋友圈id
     * @param request 本次请求对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 12:20:20
     */
    @Override
    @Transactional
    public void like(String friendCircleId, HttpServletRequest request) {
        // 获取当前请求用户id
        String userId = request.getHeader(HEADER_USER_ID);
        // 参数校验
        if (StringUtils.isBlank(userId)) {
            GraceException.display(ResponseStatusEnum.FAILED);
        }

        // 先判断是否已经点赞过，不能重复点赞
        LambdaQueryWrapper<FriendCircleLiked> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(FriendCircleLiked::getFriendCircleId, friendCircleId);
        lambdaQueryWrapper.eq(FriendCircleLiked::getLikedUserId, userId);
        FriendCircleLiked dbFriendCircleLiked = friendCircleLikedMapper.selectOne(lambdaQueryWrapper);
        if (dbFriendCircleLiked != null) {
            GraceException.display(ResponseStatusEnum.FAILED);
        }

        // 根据朋友圈的主键查询朋友圈信息
        FriendCircle friendCircle = selectFriendCircleById(friendCircleId);
        // 根据用户主键id查询用户数据
        User user = userService.queryById(userId);

        // 构建点赞对象
        FriendCircleLiked friendCircleLiked = new FriendCircleLiked();
        friendCircleLiked.setFriendCircleId(friendCircleId);
        friendCircleLiked.setBelongUserId(friendCircle.getUserId());
        friendCircleLiked.setLikedUserId(userId);
        friendCircleLiked.setLikedUserName(user.getNickname());
        friendCircleLiked.setCreatedTime(LocalDateTime.now());

        // 数据库插入点赞信息
        friendCircleLikedMapper.insert(friendCircleLiked);

        // 点赞过后朋友圈的点赞数累加1
        redis.increment(REDIS_FRIEND_CIRCLE_LIKED_COUNTS + ":" + friendCircleId, 1);

        // 标记哪个用户点赞过朋友圈
        redis.setnx(REDIS_DOES_USER_LIKE_FRIEND_CIRCLE + ":" + friendCircleId + ":" + userId, userId);
    }

    /**
     * 根据朋友圈主键id查询朋友圈数据
     *
     * @param friendCircleId 朋友圈主键id
     * @return 朋友圈数据
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 12:25:20
     */
    private FriendCircle selectFriendCircleById(String friendCircleId) {
        return friendCircleMapper.selectById(friendCircleId);
    }

    /**
     * 取消点赞朋友圈
     *
     * @param friendCircleId 朋友圈id
     * @param request 本次请求对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 12:20:24
     */
    @Override
    @Transactional
    public void unlike(String friendCircleId, HttpServletRequest request) {
        // 获取请求用户id
        String userId = request.getHeader(HEADER_USER_ID);
        if (StringUtils.isBlank(userId)) {
            GraceException.display(ResponseStatusEnum.FAILED);
        }

        // 构建删除条件
        LambdaQueryWrapper<FriendCircleLiked> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(FriendCircleLiked::getFriendCircleId, friendCircleId);
        deleteWrapper.eq(FriendCircleLiked::getLikedUserId, userId);
        // 删除数据库数据
        friendCircleLikedMapper.delete(deleteWrapper);

        // 取消点赞过后朋友圈的点赞数累减1
        redis.decrement(REDIS_FRIEND_CIRCLE_LIKED_COUNTS + ":" + friendCircleId, 1);

        // 删除标记哪个用户点赞过朋友圈
        redis.del(REDIS_DOES_USER_LIKE_FRIEND_CIRCLE + ":" + friendCircleId + ":" + userId);

    }

    /**
     * 判断用户是否点赞过朋友圈
     *
     * @param friendCircleId 朋友圈id
     * @param userId 用户id
     * @return 用户是否点赞过朋友圈
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 13:06:07
     */
    @Override
    public Boolean doILike(String friendCircleId, String userId) {
        String isExist = redis.get(REDIS_DOES_USER_LIKE_FRIEND_CIRCLE + ":" + friendCircleId + ":" + userId);
        return StringUtils.isNotBlank(isExist);
    }

    /**
     * 删除朋友圈
     *
     * @param friendCircleId 朋友圈id
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 17:19:30
     */
    @Override
    @Transactional
    public void delete(String friendCircleId, HttpServletRequest request) {
        // 获取当前请求用户id
        String userId = request.getHeader(HEADER_USER_ID);
        // 参数校验
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(friendCircleId)) {
            GraceException.display(ResponseStatusEnum.FAILED);
        }
        // 构造删除条件
        LambdaQueryWrapper<FriendCircle> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(FriendCircle::getId, friendCircleId);
        deleteWrapper.eq(FriendCircle::getUserId, userId);

        // 删除朋友圈数据
        friendCircleMapper.delete(deleteWrapper);

        // 删除该条朋友圈对应的点赞记录
        LambdaQueryWrapper<FriendCircleLiked> likeWrapper = new LambdaQueryWrapper<>();
        likeWrapper.eq(FriendCircleLiked::getFriendCircleId, friendCircleId);
        friendCircleLikedMapper.delete(likeWrapper);

        // 删除该条朋友圈对应的评论信息
        LambdaQueryWrapper<Comment> commentWrapper = new LambdaQueryWrapper<>();
        commentWrapper.eq(Comment::getFriendCircleId, friendCircleId);
        commentService.deleteByFriendCircleId(friendCircleId);

        // 删除点赞过后朋友圈的点赞数
        redis.del(REDIS_FRIEND_CIRCLE_LIKED_COUNTS + ":" + friendCircleId);

        // 删除标记哪个用户点赞过朋友圈
        redis.allDel(REDIS_DOES_USER_LIKE_FRIEND_CIRCLE + ":" + friendCircleId);
    }

    /**
     * 根据朋友圈id查询点赞的朋友列表
     *
     * @param friendCircleId 朋友圈id
     * @return 点赞过的朋友列表
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 12:57:07
     */
    public List<FriendCircleLiked> queryLikedFriends(String friendCircleId) {
        LambdaQueryWrapper<FriendCircleLiked> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(FriendCircleLiked::getFriendCircleId, friendCircleId);
        return friendCircleLikedMapper.selectList(lambdaQueryWrapper);
    }
}
