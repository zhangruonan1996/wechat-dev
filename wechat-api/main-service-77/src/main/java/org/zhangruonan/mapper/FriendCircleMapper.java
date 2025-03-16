package org.zhangruonan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zhangruonan.pojo.FriendCircle;
import org.zhangruonan.vo.FriendCircleVO;

import java.util.Map;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-16 11:05:29
 */
@Mapper
public interface FriendCircleMapper extends BaseMapper<FriendCircle> {

    /**
     * 分页查询朋友圈图文列表
     *
     * @return 朋友圈图文列表
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-16 11:58:05
     */
    Page<FriendCircleVO> queryFriendCircleList(@Param("page") Page<FriendCircle> page,
                                               @Param("paramMap") Map<String, Object> map);
}
