package org.zhangruonan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.zhangruonan.pojo.FriendRequest;
import org.zhangruonan.vo.NewFriendsVO;

import java.util.Map;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-15 13:09:02
 */
@Mapper
public interface FriendRequestMapper extends BaseMapper<FriendRequest> {

    IPage<NewFriendsVO> queryNewFriendList(@Param("page") IPage<NewFriendsVO> page,
                                           @Param("paramMap") Map<String, Object> paramMap);

}
