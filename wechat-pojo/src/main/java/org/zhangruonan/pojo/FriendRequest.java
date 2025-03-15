package org.zhangruonan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 好友申请表
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-15 12:41:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("friend_request")
public class FriendRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 添加好友发起请求的用户id
     */
    private String myId;

    /**
     * 要添加的朋友的id
     */
    private String friendId;

    /**
     * 好友的备注名
     */
    private String friendRemark;

    /**
     * 请求的留言，验证消息
     */
    private String verifyMessage;

    /**
     * 请求被好友审核的状态
     * 0-待审核
     * 1-已添加
     * 2-已过期
     */
    private Integer verifyStatus;

    /**
     * 发起请求的时间
     */
    private LocalDateTime requestTime;

}
