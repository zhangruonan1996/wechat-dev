package org.zhangruonan.service.impl;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.bo.FriendCircleBO;
import org.zhangruonan.mapper.FriendCircleMapper;
import org.zhangruonan.pojo.FriendCircle;
import org.zhangruonan.service.FriendCircleService;

import java.time.LocalDateTime;

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
}
