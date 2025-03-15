package org.zhangruonan.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-15 16:25:22
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ContactsVO implements Serializable {

    private String friendshipId;
    private String mySelfId;
    private String myFriendId;
    private String myFriendFace;
    private String myFriendNickname;
    private String myFriendRemark;
    private String chatBg;
    private Integer isMsgIgnore;
    private Integer isBlack;

}
