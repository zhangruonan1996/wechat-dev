package org.zhangruonan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zhangruonan.pojo.Friendship;
import org.zhangruonan.vo.ContactsVO;

import java.util.List;
import java.util.Map;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-16 23:21:24
 */
@Mapper
public interface FriendshipMapper extends BaseMapper<Friendship> {

    /**
     * 查询好友列表
     *
     * @param map 查询条件
     * @return 好友列表
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 16:29:43
     */
    List<ContactsVO> queryMyFriends(@Param("paramMap") Map<String, Object> map);
}
