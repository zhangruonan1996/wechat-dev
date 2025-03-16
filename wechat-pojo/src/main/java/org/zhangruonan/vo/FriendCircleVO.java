package org.zhangruonan.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.zhangruonan.pojo.FriendCircleLiked;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 11:50:20
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FriendCircleVO implements Serializable {

    private String friendCircleId;
    private String userId;
    private String userNickname;
    private String userFace;
    private String words;
    private String images;
    private LocalDateTime publishTime;

    /**
     * 用于判断当前用户是否点赞过朋友圈
     */
    private Boolean doILike = false;
    /**
     * 点赞的朋友列表
     */
    private List<FriendCircleLiked> likedFriends = new ArrayList<>();

    /**
     * 朋友圈的评论列表
     */
    private List<CommentVO> commentList = new ArrayList<>();

}
