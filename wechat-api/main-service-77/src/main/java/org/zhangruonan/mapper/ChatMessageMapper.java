package org.zhangruonan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.zhangruonan.pojo.ChatMessage;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-04-20 12:10:12
 */
@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {
}
