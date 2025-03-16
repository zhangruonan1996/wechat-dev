package org.zhangruonan.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 11:10:10
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FriendCircleBO {

    private String userId;

    private String words;

    private String images;

    private String video;

    private LocalDateTime publishTime;

}
