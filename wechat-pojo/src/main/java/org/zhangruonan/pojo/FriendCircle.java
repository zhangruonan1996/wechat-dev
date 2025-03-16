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
 * 朋友圈
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 10:53:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("friend_circle")
public class FriendCircle implements Serializable {

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 发布朋友圈的用户id
     */
    private String userId;

    /**
     * 文字内容
     */
    private String words;

    /**
     * 图片内容（多个链接之间用逗号分隔）
     */
    private String images;

    /**
     * 单个视频的url
     */
    private String video;

    /**
     * 朋友圈发布时间
     */
    private LocalDateTime publishTime;

}
