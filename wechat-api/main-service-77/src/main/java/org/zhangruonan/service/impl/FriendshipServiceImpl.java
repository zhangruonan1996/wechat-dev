package org.zhangruonan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.exceptions.GraceException;
import org.zhangruonan.grace.result.ResponseStatusEnum;
import org.zhangruonan.mapper.FriendshipMapper;
import org.zhangruonan.pojo.Friendship;
import org.zhangruonan.service.FriendshipService;
import org.zhangruonan.vo.ContactsVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 查询好友列表
     *
     * @param request 本次请求对象
     * @return 好友列表
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 16:27:37
     */
    @Override
    public List<ContactsVO> queryMyFriends(HttpServletRequest request) {
        String myId = request.getHeader(HEADER_USER_ID);
        Map<String, Object> map = new HashMap<>();
        map.put("myId", myId);
        List<ContactsVO> list = friendshipMapper.queryMyFriends(map);
        return list;
    }

    /**
     * 修改好友备注
     *
     * @param friendId 好友id
     * @param friendRemark 备注
     * @param request 本次请求对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 22:50:48
     */
    @Override
    @Transactional
    public void updateFriendRemark(String friendId, String friendRemark, HttpServletRequest request) {
        // 参数校验
        if (StringUtils.isBlank(friendId) || StringUtils.isBlank(friendRemark)) {
            GraceException.display(ResponseStatusEnum.FAILED);
        }
        // 获取当前请求用户id
        String myId = request.getHeader(HEADER_USER_ID);
        // 构造更新条件
        LambdaUpdateWrapper<Friendship> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Friendship::getMyId, myId);
        lambdaUpdateWrapper.eq(Friendship::getFriendId, friendId);
        lambdaUpdateWrapper.set(Friendship::getFriendRemark, friendRemark);
        // 更新数据库结果
        friendshipMapper.update(lambdaUpdateWrapper);
    }
}
