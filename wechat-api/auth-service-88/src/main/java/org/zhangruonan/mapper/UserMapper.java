package org.zhangruonan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.zhangruonan.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 20:23:42
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
