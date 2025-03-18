package org.zhangruonan.netty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-18 20:06:38
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatMsg {

    /**
     * 发送者的用户id
     */
    private String senderId;
    /**
     * 接收者的用户id
     */
    private String receiverId;
    /**
     * 聊天内容
     */
    private String msg;
    /**
     * 消息类型，见枚举 MsgTypeEnum.java
     */
    private Integer msgType;
    private String msgId;

    /**
     * 消息的聊天时间，既是发送者的发送时间，又是接收者的接收时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime chatTime;
    /**
     * 标记存储数据库，用于历史展示。每超过一分钟，则显示聊天时间，前端可以控制时间长短
     */
    private Integer showMsgDateTimeFlag;

    /**
     * 视频地址
     */
    private String videoPath;
    /**
     * 视频宽度
     */
    private String videoWidth;
    /**
     * 视频高度
     */
    private String videoHeight;
    /**
     * 视频时间
     */
    private String videoTimes;

    /**
     * 语音地址
     */
    private String voicePath;
    /**
     * 语音时长
     */
    private String speakVoiceDuration;
    /**
     * 语音消息标记是否已读未读
     * true：已读
     * false：未读
     */
    private Boolean isRead;

    /**
     * 聊天类型
     * 1：私聊
     * 2：群聊
     */
    private Integer communicationType;

    /**
     * 用于标记当前接收者是否在线
     */
    private Boolean isReceiverOnLine;
}
