package com.dengenxi.service;

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
    String uploadFace(MultipartFile file, String userId, HttpServletRequest request) throws Exception;
}
