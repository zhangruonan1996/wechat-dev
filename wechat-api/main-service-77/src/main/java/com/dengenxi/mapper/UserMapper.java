package com.dengenxi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dengenxi.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-16 23:21:24
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
