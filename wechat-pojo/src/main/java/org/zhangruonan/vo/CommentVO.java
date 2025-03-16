package org.zhangruonan.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 13:18:28
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {

    private String commentId;
    private String belongUserId;
    private String friendCircleId;

    private String fatherId;
    private String commentUserId;
    private String commentUserNickname;
    private String commentUserFace;
    private String commentContent;

    private String replyedUserNickname;

    private LocalDateTime createdTime;

}
