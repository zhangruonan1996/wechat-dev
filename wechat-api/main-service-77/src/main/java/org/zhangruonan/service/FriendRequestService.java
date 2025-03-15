package org.zhangruonan.service;

import org.zhangruonan.bo.NewFriendRequestBO;

/**
 * 好友请求服务类
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-15 13:17:18
 */
public interface FriendRequestService {

    /**
     * 添加新的好友申请
     *
     * @param bo 好友申请参数
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 13:19:44
     */
    void addNewFriendRequest(NewFriendRequestBO bo);

}
