package com.dengenxi.service.impl;

import cn.hutool.core.util.DesensitizedUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dengenxi.base.BaseInfoProperties;
import com.dengenxi.enums.Sex;
import com.dengenxi.feign.FileMicroServiceFeign;
import com.dengenxi.mapper.UserMapper;
import com.dengenxi.pojo.User;
import com.dengenxi.service.UserService;
import com.dengenxi.utils.LocalDateUtils;
import com.dengenxi.utils.SnowUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 20:24:35
 */
@Service
public class UserServiceImpl extends BaseInfoProperties implements UserService {

    private static final String USER_DEFAULT_AVATAR = "https://sns-avatar-qc.xhscdn.com/avatar/61cedc079b65e05ce4e2070a.jpg?imageView2/2/w/540/format/webp|imageMogr2/strip2";

    @Resource
    private UserMapper userMapper;

    @Resource
    private FileMicroServiceFeign fileMicroServiceFeign;

    /**
     * 根据手机号查询用户数据
     *
     * @param mobile 手机号码
     * @return 查询到的用户数据
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 21:01:39
     */
    @Override
    public User queryByMobile(String mobile) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getMobile, mobile);
        return userMapper.selectOne(lambdaQueryWrapper);
    }

    /**
     * 创建用户信息，并且返回创建的用户对象
     *
     * @param mobile 手机号码
     * @param nickname 用户昵称
     * @return 创建的用户对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-15 21:02:54
     */
    @Override
    @Transactional
    public User createUser(String mobile, String nickname) {
        User user = new User();
        // 设置id
        user.setId(SnowUtils.getSnowflakeNextIdStr());
        // 设置手机号
        user.setMobile(mobile);
        // 生成微信号
        String uuid = UUID.randomUUID().toString();
        String[] uuidStr = uuid.split("-");
        String wechatNum = "wx" + uuidStr[0] + uuidStr[1];
        user.setWechatNum(wechatNum);
        // 设置微信二维码
        String wechatNumUrl = getQrCodeUrl(wechatNum, TEMP_STRING);
        user.setWechatNumImg(wechatNumUrl);
        // 设置默认昵称
        if (StringUtils.isBlank(nickname)) {
            user.setNickname("用户" + DesensitizedUtil.mobilePhone(mobile));
        } else {
            user.setNickname(nickname);
        }
        // 设置默认姓名
        user.setRealName("");
        // 设置性别
        user.setSex(Sex.secret.type);
        // 设置默认头像
        user.setFace(USER_DEFAULT_AVATAR);
        // 设置默认朋友圈背景图
        user.setFriendCircleBg(USER_DEFAULT_AVATAR);
        // 设置邮箱
        user.setEmail("");
        // 设置默认生日
        user.setBirthday(LocalDateUtils.parseLocalDate("2005-04-18", LocalDateUtils.DATE_PATTERN));
        // 设置默认国家
        user.setCountry("中国");
        // 设置省
        user.setProvince("");
        // 设置市
        user.setCity("");
        // 设置区域
        user.setDistrict("");
        // 设置创建时间
        user.setCreatedTime(LocalDateTime.now());
        // 设置更新时间
        user.setUpdatedTime(LocalDateTime.now());
        // 插入到数据库
        userMapper.insert(user);
        return user;
    }

    /**
     * 获取用户微信二维码
     *
     * @param wechatNum 微信号
     * @param userId 用户id
     * @return 用户微信二维码
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 19:36:02
     */
    private String getQrCodeUrl(String wechatNum, String userId) {
        try {
            return fileMicroServiceFeign.generatorQrCode(wechatNum, userId);
        } catch (Exception e) {
            return null;
        }
    }

}
