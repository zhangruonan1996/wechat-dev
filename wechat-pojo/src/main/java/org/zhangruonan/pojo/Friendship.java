package org.zhangruonan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.zhangruonan.enums.YesOrNo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 朋友关系表
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-15 12:40:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("friendship")
public class Friendship implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 自己的用户id
     */
    private String myId;

    /**
     * 朋友的id
     */
    private String friendId;

    /**
     * 好友的备注名
     */
    private String friendRemark;

    /**
     * 聊天背景，局部
     */
    private String chatBg;

    /**
     * 是否消息免打扰
     * 0-打扰，不忽略消息(默认)
     * 1-免打扰，忽略消息
     */
    private Integer isMsgIgnore = YesOrNo.NO.type;

    /**
     * 是否拉黑
     * 0-好友(默认)
     * 1-已拉黑
     */
    private Integer isBlack = YesOrNo.NO.type;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

}
