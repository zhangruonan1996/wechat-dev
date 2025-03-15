package org.zhangruonan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.zhangruonan.pojo.Friendship;
import org.zhangruonan.pojo.User;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-16 23:21:24
 */
@Mapper
public interface FriendshipMapper extends BaseMapper<Friendship> {
}
