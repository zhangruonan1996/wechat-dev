package com.dengenxi.service;

import com.dengenxi.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-18 19:59:38
 */
public interface FileService {

    /**
     * 上传头像（保存到本地）
     *
     * @param file
     * @param userId
     * @param request
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 20:00:37
     */
    void uploadFace1(MultipartFile file, String userId, HttpServletRequest request) throws IOException;

    /**
     * 上传头像
     *
     * @param file
     * @param userId
     * @param request
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:27:22
     */
    UserVO uploadFace(MultipartFile file, String userId, HttpServletRequest request) throws Exception;

    /**
     * 生成二维码
     *
     * @param wechatNum 微信号
     * @param userId 用户id
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 19:00:17
     */
    String generatorQrCode(String wechatNum, String userId) throws Exception;

    /**
     * 上传朋友圈背景图
     *
     * @param file 朋友圈背景图文件
     * @param userId 用户id
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 19:54:05
     */
    UserVO uploadFriendCircleBg(MultipartFile file, String userId) throws Exception;

    /**
     * 上传聊天背景图
     *
     * @param file 聊天背景图文件
     * @param userId 用户id
     * @return 最新用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 20:13:12
     */
    UserVO uploadChatBg(MultipartFile file, String userId) throws Exception;
}
