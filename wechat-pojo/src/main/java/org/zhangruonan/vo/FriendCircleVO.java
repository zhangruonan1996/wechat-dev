package org.zhangruonan.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import org.zhangruonan.pojo.Comment;
import org.zhangruonan.pojo.FriendCircleLiked;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 11:50:20
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FriendCircleVO implements Serializable {

    private String friendCircleId;
    private String userId;
    private String userNickname;
    private String userFace;
    private String words;
    private String images;
    private LocalDateTime publishTime;

    private List<FriendCircleLiked> likedFriends = new ArrayList<>();

    private List<Comment> commentList = new ArrayList<>();

}
