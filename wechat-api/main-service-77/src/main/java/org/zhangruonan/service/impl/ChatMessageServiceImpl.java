package org.zhangruonan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.mapper.ChatMessageMapper;
import org.zhangruonan.netty.ChatMsg;
import org.zhangruonan.pojo.ChatMessage;
import org.zhangruonan.service.ChatMessageService;
import org.zhangruonan.utils.PagedGridResult;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-04-20 12:11:13
 */
@Service
public class ChatMessageServiceImpl extends BaseInfoProperties implements ChatMessageService {

    @Resource
    private ChatMessageMapper chatMessageMapper;

    @Override
    @Transactional
    public void saveMsg(ChatMsg chatMsg) {

        ChatMessage chatMessage = new ChatMessage();
        BeanUtils.copyProperties(chatMsg, chatMessage);

        // 手动设置聊天信息的主键ID
        chatMessage.setId(chatMsg.getMsgId());

        chatMessageMapper.insert(chatMessage);

        String receiverId = chatMsg.getReceiverId();
        String senderId = chatMsg.getSenderId();

        // 通过redis累加信息接收者的对应记录
        redis.incrementHash(CHAT_MSG_LIST + ":" + receiverId, senderId, 1);

    }

    /**
     * 分页查询聊天记录
     *
     * @param senderId 发送者用户id
     * @param receiverId 接收者用户id
     * @param page 当前页
     * @param pageSize 每页显示的条数
     * @return 分页聊天记录
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-04-20 17:25:04
     */
    @Override
    public PagedGridResult queryChatMsgList(String senderId, String receiverId, Integer page, Integer pageSize) {
        // 校验当前页是否合法
        if (page == null || page < 1) {
            page = 1;
        }
        // 校验每页显示条数是否合法
        if (pageSize == null || pageSize < 1 || pageSize > 100) {
            pageSize = 20;
        }

        Page<ChatMessage> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<ChatMessage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ChatMessage::getSenderId, senderId).or().eq(ChatMessage::getReceiverId, receiverId);
        lambdaQueryWrapper.eq(ChatMessage::getSenderId, receiverId).or().eq(ChatMessage::getReceiverId, senderId);
        lambdaQueryWrapper.orderByDesc(ChatMessage::getChatTime);

        chatMessageMapper.selectPage(pageInfo, lambdaQueryWrapper);

        // 获得列表后需要倒着排序，聊天记录最新数据在最下方
        List<ChatMessage> list = pageInfo.getRecords();
        list = list.stream().sorted(Comparator.comparing(ChatMessage::getChatTime)).collect(Collectors.toList());

        pageInfo.setRecords(list);

        return setterPagedGridPlus(pageInfo);
    }
}
