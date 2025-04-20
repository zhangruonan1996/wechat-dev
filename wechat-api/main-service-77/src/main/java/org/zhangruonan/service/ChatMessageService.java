package org.zhangruonan.service;

import org.zhangruonan.netty.ChatMsg;
import org.zhangruonan.utils.PagedGridResult;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-04-20 12:10:59
 */
public interface ChatMessageService {

    /**
     * 保存聊天消息
     *
     * @param chatMsg
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-04-20 12:12:08
     */
    void saveMsg(ChatMsg chatMsg);

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
     * @date 2025-04-20 17:24:21
     */
    PagedGridResult queryChatMsgList(String senderId, String receiverId, Integer page, Integer pageSize);

    /**
     * 标记语音聊天信息的签收已读
     *
     * @param msgId 消息id
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-04-20 17:40:56
     */
    void updateMessageSignRead(String msgId);
}
