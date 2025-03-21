package org.zhangruonan.service;

import jakarta.servlet.http.HttpServletRequest;
import org.zhangruonan.enums.YesOrNo;
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
    List<ContactsVO> queryMyFriends(HttpServletRequest request, Boolean needBlack);

    /**
     * 修改好友备注
     *
     * @param friendId 好友id
     * @param friendRemark 备注
     * @param request 本次请求对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 22:50:22
     */
    void updateFriendRemark(String friendId, String friendRemark, HttpServletRequest request);

    /**
     * 更新黑名单
     *
     * @param friendId 拉黑用户id
     * @param yesOrNo 是否拉黑
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 23:05:14
     */
    void updateBlackList(String friendId, HttpServletRequest request, YesOrNo yesOrNo);

    /**
     * 删除好友关系
     *
     * @param friendId 好友id
     * @param request 本次请求对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 23:21:13
     */
    void delete(String friendId, HttpServletRequest request);

    /**
     * 判断两个朋友之间的关系是否拉黑
     *
     * @param friendId1st 用户1id
     * @param friendId2nd 用户2id
     * @return 是否拉黑
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-21 19:30:10
     */
    Boolean isBlackEachOther(String friendId1st, String friendId2nd);
}
