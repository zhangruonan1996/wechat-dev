package org.zhangruonan.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户表
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 20:23:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 微信号
     */
    private String wechatNum;

    /**
     * 微信号二维码
     */
    private String wechatNumImg;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别，1:男 0:女 2:保密
     */
    private Integer sex;

    /**
     * 用户头像
     */
    private String face;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区县
     */
    private String district;

    /**
     * 聊天背景
     */
    private String chatBg;

    /**
     * 朋友圈背景图
     */
    private String friendCircleBg;

    /**
     * 我的一句话签名
     */
    private String signature;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;


}
