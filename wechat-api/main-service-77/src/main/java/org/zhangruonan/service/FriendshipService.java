package org.zhangruonan.service;

import jakarta.servlet.http.HttpServletRequest;
import org.zhangruonan.pojo.Friendship;
import org.zhangruonan.vo.ContactsVO;

import java.util.List;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-15 16:13:35
 */
public interface FriendshipService {

    /**
     * 获取朋友关系
     *
     * @param friendId 朋友id
     * @param request 本次请求对象
     * @return 朋友关系
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 16:15:36
     */
    Friendship getFriendship(String friendId, HttpServletRequest request);

    /**
     * 查询好友列表
     *
     * @param request 本次请求对象
     * @return 好友列表
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 16:27:05
     */
    List<ContactsVO> queryMyFriends(HttpServletRequest request);
}
