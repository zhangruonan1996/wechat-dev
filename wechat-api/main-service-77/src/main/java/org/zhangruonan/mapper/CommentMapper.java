package org.zhangruonan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zhangruonan.pojo.Comment;
import org.zhangruonan.vo.CommentVO;

import java.util.List;
import java.util.Map;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 11:09:08
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 查询朋友圈的评论
     *
     * @param map
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 13:39:09
     */
    List<CommentVO> getFriendCircleComments(@Param("paramMap") Map<String, Object> map);

}
