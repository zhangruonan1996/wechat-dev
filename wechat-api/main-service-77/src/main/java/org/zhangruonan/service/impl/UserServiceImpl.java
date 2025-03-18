package org.zhangruonan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.zhangruonan.base.BaseInfoProperties;
import org.zhangruonan.bo.ModifyUserBO;
import org.zhangruonan.exceptions.GraceException;
import org.zhangruonan.feign.FileMicroServiceFeign;
import org.zhangruonan.grace.result.ResponseStatusEnum;
import org.zhangruonan.mapper.UserMapper;
import org.zhangruonan.pojo.User;
import org.zhangruonan.service.UserService;
import org.zhangruonan.vo.UserVO;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-16 23:18:38
 */
@Service
public class UserServiceImpl extends BaseInfoProperties implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Resource
    private FileMicroServiceFeign fileMicroServiceFeign;

    /**
     * 修改用户信息
     *
     * @param userBO
     * @return 最新用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-16 23:27:33
     */
    @Override
    @Transactional
    public UserVO modifyUserInfo(ModifyUserBO userBO) {

        String wechatNum = userBO.getWechatNum();

        User pendingUser = new User();

        String userId = userBO.getUserId();

        if (StringUtils.isBlank(userId)) {
            GraceException.display(ResponseStatusEnum.USER_INFO_UPDATED_ERROR);
        }

        if (StringUtils.isNotBlank(wechatNum)) {
            String isExist = redis.get(REDIS_USER_ALREADY_UPDATE_WECHAT_NUM + ":" + userId);
            if (StringUtils.isNotBlank(isExist)) {
                GraceException.display(ResponseStatusEnum.WECHAT_NUM_ALREADY_MODIFIED_ERROR);
            } else {
                // 修改微信二维码
                String wechatNumUrl = getQrCodeUrl(wechatNum, userId);
                pendingUser.setWechatNumImg(wechatNumUrl);
            }
        }

        BeanUtils.copyProperties(userBO, pendingUser);
        pendingUser.setId(userId);
        pendingUser.setUpdatedTime(LocalDateTime.now());

        userMapper.updateById(pendingUser);

        // 如果用户修改微信号，则只能修改一次
        if (StringUtils.isNotBlank(wechatNum)) {
            redis.setByDays(REDIS_USER_ALREADY_UPDATE_WECHAT_NUM + ":" + userId, userId, 365);
        }

        UserVO userVO = getUserInfo(userBO.getUserId(), true);

        return userVO;

    }

    /**
     * 根据用户id查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-17 18:51:56
     */
    @Override
    public User queryById(String userId) {
        return userMapper.selectById(userId);
    }

    /**
     * 获取最新用户信息
     *
     * @param userId 用户id
     * @return 最新用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-17 18:53:18
     */
    public UserVO getUserInfo(String userId, Boolean needToken) {
        // 查询数据库获取最新的用户信息
        User latestUser = queryById(userId);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(latestUser, userVO);

        if (needToken) {
            String uToken = TOKEN_USER_PREFIX + SYMBOL_DOT + UUID.randomUUID();
            // 本方式只能限制用户在一台设备进行登录
            // redis.set(REDIS_USER_TOKEN + ":" + user.getId(), uToken);
            // 本方式允许用户在多端多设备进行登录
            redis.set(REDIS_USER_TOKEN + ":" + uToken, userId);
            userVO.setUserToken(uToken);
        }

        return userVO;
    }

    /**
     * 更新用户头像
     *
     * @param userId 用户id
     * @param face 用户头像
     * @return 更新后的用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 00:01:56
     */
    @Override
    public UserVO updateFace(String userId, String face) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(face)) {
            GraceException.display(ResponseStatusEnum.USER_UPDATE_ERROR);
        }
        ModifyUserBO modifyUserBO = new ModifyUserBO();
        modifyUserBO.setUserId(userId);
        modifyUserBO.setFace(face);
        // 更新用户信息
        this.modifyUserInfo(modifyUserBO);
        // 返回最新用户信息
        UserVO userVO = getUserInfo(userId, true);
        return userVO;
    }

    /**
     * 更新用户朋友圈背景图
     *
     * @param userId 用户id
     * @param friendCircleBg 朋友圈背景图
     * @return 最新用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 20:03:43
     */
    @Override
    public UserVO updateFriendCircleBg(String userId, String friendCircleBg) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(friendCircleBg)) {
            GraceException.display(ResponseStatusEnum.USER_UPDATE_ERROR);
        }
        ModifyUserBO modifyUserBO = new ModifyUserBO();
        modifyUserBO.setUserId(userId);
        modifyUserBO.setFriendCircleBg(friendCircleBg);
        // 更新用户信息
        this.modifyUserInfo(modifyUserBO);
        // 返回最新用户信息
        UserVO userVO = getUserInfo(userId, true);
        return userVO;
    }

    /**
     * 更新用户聊天背景图
     *
     * @param userId 用户id
     * @param chatBg 聊天背景图地址
     * @return 最新用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 20:17:13
     */
    @Override
    public UserVO updateChatBg(String userId, String chatBg) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(chatBg)) {
            GraceException.display(ResponseStatusEnum.USER_UPDATE_ERROR);
        }
        UserVO userVO = commonDealUpdateUserInfo(userId, chatBg);
        return userVO;
    }

    /**
     * 根据手机号或者微信号查询用户
     *
     * @param queryString 手机号或微信号
     * @return 匹配的用户数据
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-15 11:48:30
     */
    @Override
    public User getUserByWechatNumOrMobile(String queryString, HttpServletRequest request) {
        // 如果用户未传递任何参数，直接抛出异常返回错误信息
        if (queryString == null || queryString.isEmpty()) {
            GraceException.display(ResponseStatusEnum.FAILED);
        }
        // 构造查询条件并从数据库查询数据
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getWechatNum, queryString).or().eq(User::getMobile, queryString);
        User friend = userMapper.selectOne(lambdaQueryWrapper);
        // 如果查询的用户不存在，抛出异常返回错误信息
        if (friend == null) {
            GraceException.display(ResponseStatusEnum.FRIEND_NOT_EXIST_ERROR);
        }
        // 不能添加自己为好友
        String myId = request.getHeader(HEADER_USER_ID);
        if (myId.equals(friend.getId())) {
            GraceException.display(ResponseStatusEnum.CAN_NOT_ADD_SELF_FRIEND_ERROR);
        }
        return friend;
    }

    private UserVO commonDealUpdateUserInfo(String userId, String chatBg) {
        ModifyUserBO modifyUserBO = new ModifyUserBO();
        modifyUserBO.setUserId(userId);
        modifyUserBO.setChatBg(chatBg);
        // 更新用户信息
        this.modifyUserInfo(modifyUserBO);
        // 返回最新用户信息
        UserVO userVO = getUserInfo(userId, true);
        return userVO;
    }

    /**
     * 获取用户二维码
     *
     * @param wechatNum 微信号
     * @param userId 用户id
     * @return 用户二维码
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 19:27:01
     */
    private String getQrCodeUrl(String wechatNum, String userId) {
        try {
            return fileMicroServiceFeign.generatorQrCode(wechatNum, userId);
        } catch (Exception e) {
            return null;
        }
    }
}
