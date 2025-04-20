package org.zhangruonan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-04-20 12:04:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chat_message")
public class ChatMessage {

    /**
     * 主键ID
     */
    @TableId(type = IdType.NONE)
    private String id;

    /**
     * 发送者的用户id
     */
    private String senderId;

    /**
     * 接受者的用户id
     */
    private String receiverId;

    /**
     * 消息接受者的类型，可以作为扩展字段
     */
    private Integer receiverType;

    /**
     * 聊天内容
     */
    private String msg;

    /**
     * 消息类型，有文字类、图片类、视频类...等，详见枚举类
     */
    private Integer msgType;

    /**
     * 消息的聊天时间，既是发送者的发送时间、又是接受者的接受时间
     */
    private LocalDateTime chatTime;

    /**
     * 标记存储数据库，用于历史展示。每超过1分钟，则显示聊天时间，前端可以控制时间长短(扩展字段)
     */
    private Integer showMsgDateTimeFlag;

    /**
     * 视频地址
     */
    private String videoPath;

    /**
     * 视频宽度
     */
    private Integer videoWidth;

    /**
     * 视频高度
     */
    private Integer videoHeight;

    /**
     * 视频时间
     */
    private Integer videoTimes;

    /**
     * 语音地址
     */
    private String voicePath;

    /**
     * 语音时长
     */
    private Integer speakVoiceDuration;

    /**
     * 语音消息标记是否已读未读，true: 已读，false: 未读
     */
    private Boolean isRead;

}
