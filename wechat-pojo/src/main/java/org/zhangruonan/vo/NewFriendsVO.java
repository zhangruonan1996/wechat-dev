package org.zhangruonan.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-15 12:41:00
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewFriendsVO implements Serializable {

    private String friendRequestId;
    private String myFriendId;
    private String myFriendFace;
    private String myFriendNickname;
    private String verifyMessage;
    private Integer verifyStatus;
    private LocalDateTime requestTime;
    private Boolean isTouched = false;

}
