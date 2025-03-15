package org.zhangruonan.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.bo.NewFriendRequestBO;
import org.zhangruonan.enums.FriendRequestVerifyStatus;
import org.zhangruonan.exceptions.GraceException;
import org.zhangruonan.grace.result.ResponseStatusEnum;
import org.zhangruonan.mapper.FriendRequestMapper;
import org.zhangruonan.pojo.FriendRequest;
import org.zhangruonan.service.FriendRequestService;
import org.zhangruonan.utils.PagedGridResult;
import org.zhangruonan.vo.NewFriendsVO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 分页查询用户请求列表
     *
     * @param request 当前请求对象
     * @param page 当前页（不传默认为1）
     * @param pageSize 每页显示的数量（不传默认为10）
     * @return 好友申请列表分页数据
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 13:40:27
     */
    @Override
    public PagedGridResult queryNewFriendRequest(HttpServletRequest request, Integer page, Integer pageSize) {
        // 从请求头取出用户id
        String userId = request.getHeader(HEADER_USER_ID);
        if (StringUtils.isBlank(userId)) {
            GraceException.display(ResponseStatusEnum.NO_AUTH);
        }
        Page<NewFriendsVO> pageInfo = new Page<>(page, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("mySelfId", userId);
        friendRequestMapper.queryNewFriendList(pageInfo, map);
        return setterPagedGridPlus(pageInfo);
    }
}
