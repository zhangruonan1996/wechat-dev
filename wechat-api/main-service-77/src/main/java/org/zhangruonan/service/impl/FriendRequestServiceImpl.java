package org.zhangruonan.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.bo.NewFriendRequestBO;
import org.zhangruonan.enums.FriendRequestVerifyStatus;
import org.zhangruonan.mapper.FriendRequestMapper;
import org.zhangruonan.pojo.FriendRequest;
import org.zhangruonan.service.FriendRequestService;

import java.time.LocalDateTime;

/**
 * 好友请求服务实现类
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-15 13:17:29
 */
@Service
public class FriendRequestServiceImpl extends BaseInfoProperties implements FriendRequestService {

    @Resource
    private FriendRequestMapper friendRequestMapper;

    /**
     * 添加新的好友申请
     *
     * @param bo 好友申请参数
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 13:20:25
     */
    @Override
    @Transactional
    public void addNewFriendRequest(NewFriendRequestBO bo) {
        // 先删除之前的记录
        LambdaQueryWrapper<FriendRequest> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(FriendRequest::getMyId, bo.getMyId());
        lambdaQueryWrapper.eq(FriendRequest::getFriendId, bo.getFriendId());
        friendRequestMapper.delete(lambdaQueryWrapper);
        // 添加新的记录
        FriendRequest peddingFriendRequest = new FriendRequest();
        // 拷贝数据
        BeanUtils.copyProperties(bo, peddingFriendRequest);
        // 设置id
        peddingFriendRequest.setId(IdUtil.getSnowflakeNextIdStr());
        // 设置默认审核状态为待审核
        peddingFriendRequest.setVerifyStatus(FriendRequestVerifyStatus.WAIT.type);
        // 设置请求时间
        peddingFriendRequest.setRequestTime(LocalDateTime.now());
        // 将数据插入到数据库
        friendRequestMapper.insert(peddingFriendRequest);
    }
}
