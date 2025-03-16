package org.zhangruonan.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 13:18:28
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentBO {

    private String belongUserId;
    private String friendCircleId;

    private String fatherId;

    private String commentUserId;
    private String commentContent;

}
