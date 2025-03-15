package org.zhangruonan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.mapper.FriendshipMapper;
import org.zhangruonan.pojo.Friendship;
import org.zhangruonan.service.FriendshipService;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-15 16:13:50
 */
@Service
@Slf4j
public class FriendshipServiceImpl extends BaseInfoProperties implements FriendshipService {

    @Resource
    private FriendshipMapper friendshipMapper;

    /**
     * 获取朋友关系
     *
     * @param friendId 朋友id
     * @param request 本次请求对象
     * @return 朋友关系
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 16:16:14
     */
    @Override
    public Friendship getFriendship(String friendId, HttpServletRequest request) {
        String myId = request.getHeader(HEADER_USER_ID);
        LambdaQueryWrapper<Friendship> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Friendship::getMyId, myId);
        lambdaQueryWrapper.eq(Friendship::getFriendId, friendId);
        return friendshipMapper.selectOne(lambdaQueryWrapper);
    }
}
