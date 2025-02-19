package com.dengenxi.controller;

import com.dengenxi.grace.result.GraceJSONResult;
import com.dengenxi.service.FileService;
import com.dengenxi.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-18 19:58:10
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传用户头像
     *
     * @param file 用户头像
     * @param userId 用户id
     * @param request 本次请求对象
     * @return 最新用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 18:59:37
     */
    @PostMapping("/uploadFace")
    public GraceJSONResult uploadFace(@RequestParam("file") MultipartFile file, String userId, HttpServletRequest request) throws Exception {
        UserVO userVO = fileService.uploadFace(file, userId, request);
        return GraceJSONResult.ok(userVO);
    }

    /**
     * 生成二维码
     *
     * @param wechatNum
     * @param userId
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 18:59:27
     */
    @PostMapping("/generatorQrCode")
    public String generatorQrCode(String wechatNum, String userId) throws Exception {
        String qrCodeUrl = fileService.generatorQrCode(wechatNum, userId);
        return qrCodeUrl;
    }

    /**
     * 上传朋友圈背景图
     *
     * @param file 朋友圈背景图文件
     * @param userId 用户id
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 19:53:42
     */
    @PostMapping("/uploadFriendCircleBg")
    public GraceJSONResult uploadFace(@RequestParam("file") MultipartFile file, String userId) throws Exception {
        UserVO userVO = fileService.uploadFriendCircleBg(file, userId);
        return GraceJSONResult.ok(userVO);
    }

}
