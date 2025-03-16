package org.zhangruonan.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.bo.FriendCircleBO;
import org.zhangruonan.exceptions.GraceException;
import org.zhangruonan.grace.result.ResponseStatusEnum;
import org.zhangruonan.mapper.FriendCircleMapper;
import org.zhangruonan.pojo.FriendCircle;
import org.zhangruonan.service.FriendCircleService;
import org.zhangruonan.utils.PagedGridResult;
import org.zhangruonan.vo.FriendCircleVO;

import java.time.LocalDateTime;
import java.util.HashMap;
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
        return setterPagedGridPlus(pageInfo);
    }
}
