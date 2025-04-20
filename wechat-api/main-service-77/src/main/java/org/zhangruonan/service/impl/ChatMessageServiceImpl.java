package org.zhangruonan.service.impl;

import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.mapper.ChatMessageMapper;
import org.zhangruonan.netty.ChatMsg;
import org.zhangruonan.pojo.ChatMessage;
import org.zhangruonan.service.ChatMessageService;

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

    }
}
