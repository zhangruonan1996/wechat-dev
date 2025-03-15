package org.zhangruonan.vo;

import org.zhangruonan.utils.LocalDateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-16 18:59:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserVO {

    private String id;
    private String wechatNum;
    private String wechatNumImg;
    private String mobile;
    private String nickname;
    private String realName;
    private Integer sex;
    private String face;
    private String email;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = LocalDateUtils.DATE_PATTERN, timezone = LocalDateUtils.TIMEZONE_GMT8)
    private LocalDate birthday;
    private String country;
    private String province;
    private String city;
    private String district;
    private String chatBg;
    private String friendCircleBg;
    private String signature;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = LocalDateUtils.DATETIME_PATTERN, timezone = LocalDateUtils.TIMEZONE_GMT8)
    private LocalDateTime createdTime;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = LocalDateUtils.DATETIME_PATTERN, timezone = LocalDateUtils.TIMEZONE_GMT8)
    private LocalDateTime updatedTime;
    /**
     * 用户会话令牌token
     */
    private String userToken;

}
