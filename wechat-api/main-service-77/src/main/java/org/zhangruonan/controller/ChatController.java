package org.zhangruonan.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.grace.result.GraceJSONResult;

import java.util.Map;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-04-20 16:52:07
 */
@RestController
@RequestMapping("/chat")
public class ChatController extends BaseInfoProperties {

    @PostMapping("/getMyUnReadCounts")
    public GraceJSONResult getMyUnReadCounts(String myId) {
        Map<Object, Object> map = redis.hgetall(CHAT_MSG_LIST + ":" + myId);
        return GraceJSONResult.ok(map);
    }

    @PostMapping("/clearMyUnReadCounts")
    public GraceJSONResult clearMyUnReadCounts(String myId, String oppositeId) {
        redis.setHashValue(CHAT_MSG_LIST + ":" + myId, oppositeId, "0");
        return GraceJSONResult.ok();
    }

}
