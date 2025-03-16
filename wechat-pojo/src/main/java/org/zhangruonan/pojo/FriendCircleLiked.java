package org.zhangruonan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 朋友圈点赞
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 10:58:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("friend_circle_liked")
public class FriendCircleLiked implements Serializable {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 朋友圈归属的用户id
     */
    private String belongUserId;

    /**
     * 点赞的朋友圈id
     */
    private String friendCircleId;

    /**
     * 点赞用户id
     */
    private String likedUserId;

    /**
     * 点赞用户昵称
     */
    private String likedUserName;

    /**
     * 点赞时间
     */
    private LocalDateTime createdTime;

}
