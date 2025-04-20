package org.zhangruonan.service;

import org.zhangruonan.netty.ChatMsg;

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

}
