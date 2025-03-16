package org.zhangruonan.service;

import jakarta.servlet.http.HttpServletRequest;
import org.zhangruonan.bo.FriendCircleBO;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 11:05:01
 */
public interface FriendCircleService {

    /**
     * 发表朋友圈
     *
     * @param friendCircleBO 朋友圈内容
     * @param request 本次请求对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 11:13:48
     */
    void publish(FriendCircleBO friendCircleBO, HttpServletRequest request);
}
