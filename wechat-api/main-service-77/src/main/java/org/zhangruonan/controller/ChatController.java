package org.zhangruonan.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.grace.result.GraceJSONResult;
import org.zhangruonan.service.ChatMessageService;
import org.zhangruonan.utils.PagedGridResult;

import java.util.Map;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-04-20 16:52:07
 */
@RestController
@RequestMapping("/chat")
public class ChatController extends BaseInfoProperties {

    @Resource
    private ChatMessageService chatMessageService;

    /**
     * 获取我的未读消息数
     *
     * @param myId 我的id
     * @return 未读消息数
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-04-20 17:17:07
     */
    @PostMapping("/getMyUnReadCounts")
    public GraceJSONResult getMyUnReadCounts(String myId) {
        Map<Object, Object> map = redis.hgetall(CHAT_MSG_LIST + ":" + myId);
        return GraceJSONResult.ok(map);
    }

    /**
     * 清空我的未读消息数
     *
     * @param myId 我的id
     * @param oppositeId 好友id
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-04-20 17:17:37
     */
    @PostMapping("/clearMyUnReadCounts")
    public GraceJSONResult clearMyUnReadCounts(String myId, String oppositeId) {
        redis.setHashValue(CHAT_MSG_LIST + ":" + myId, oppositeId, "0");
        return GraceJSONResult.ok();
    }

    /**
     * 分页获取聊天记录
     *
     * @param senderId 发送者id
     * @param receiverId 接收者id
     * @param page 当前页
     * @param pageSize 每页显示的条数
     * @return 分页聊天记录
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-04-20 17:19:16
     */
    @PostMapping("/list/{senderId}/{receiverId}")
    public GraceJSONResult chatList(@PathVariable("senderId") String senderId,
                                       @PathVariable("receiverId") String receiverId,
                                       Integer page,
                                       Integer pageSize) {
        PagedGridResult result = chatMessageService.queryChatMsgList(senderId, receiverId, page, pageSize);
        return GraceJSONResult.ok(result);
    }

    /**
     * 已读签收标记
     *
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-04-20 17:39:32
     */
    @PostMapping("/signRead/{msgId}")
    public GraceJSONResult signRead(@PathVariable String msgId) {
        chatMessageService.updateMessageSignRead(msgId);
        return GraceJSONResult.ok();
    }

}
