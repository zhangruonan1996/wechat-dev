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
 * 评论
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 11:00:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comment")
public class Comment implements Serializable {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 评论的朋友圈归属哪个用户
     */
    private String belongUserId;

    /**
     * 父级评论id
     */
    private String fatherId;

    /**
     * 评论的朋友圈id
     */
    private String friendCircleId;

    /**
     * 发布评论的用户id
     */
    private String commentUserId;

    /**
     * 发布评论内容
     */
    private String commentContent;

    /**
     * 留言时间
     */
    private LocalDateTime createdTime;

}
