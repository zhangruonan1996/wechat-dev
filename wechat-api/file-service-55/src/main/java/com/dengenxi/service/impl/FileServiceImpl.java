package com.dengenxi.service.impl;

import com.dengenxi.exceptions.GraceException;
import com.dengenxi.feign.UserInfoMicroServiceFeign;
import com.dengenxi.grace.result.GraceJSONResult;
import com.dengenxi.grace.result.ResponseStatusEnum;
import com.dengenxi.service.FileService;
import com.dengenxi.utils.JsonUtils;
import com.dengenxi.utils.MinioConfig;
import com.dengenxi.utils.MinioUtils;
import com.dengenxi.utils.QrCodeUtils;
import com.dengenxi.vo.UserVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-18 19:59:47
 */
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private MinioConfig minioConfig;

    @Resource
    private UserInfoMicroServiceFeign userInfoMicroServiceFeign;

    /**
     * 上传头像
     *
     * @param file
     * @param userId
     * @param request
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 20:00:52
     */
    @Override
    public void uploadFace1(MultipartFile file, String userId, HttpServletRequest request) throws IOException {
        // 获取文件原始名称
        String filename = file.getOriginalFilename();
        // 获取文件后缀名
        String suffixName = filename.substring(filename.lastIndexOf("."));
        // 文件的新名称
        String newFileName = userId + suffixName;
        // 设置文件存储路径
        String rootPath = "D:\\data\\upload" + File.separator;

        String filePath = rootPath + File.separator + "face" + File.separator + newFileName;
        File newFile = new File(filePath);
        // 判断目录文件所在的目录是否存在
        if (!newFile.getParentFile().exists()) {
            // 如果目录文件所在目录不存在，则创建父级目录
            newFile.getParentFile().mkdirs();
        }
        // 将文件写入磁盘
        file.transferTo(newFile);
    }

    /**
     * 上传头像
     *
     * @param file    头像文件
     * @param userId  用户id
     * @param request 本次请求对象
     * @return 头像地址
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:27:41
     */
    @Override
    public UserVO uploadFace(MultipartFile file, String userId, HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(userId)) {
            GraceException.display(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }
        // 获取文件原始名称
        String filename = file.getOriginalFilename();
        if (StringUtils.isBlank(filename)) {
            GraceException.display(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        filename = "face" + "/" + userId + "/" + filename;
        MinioUtils.putObject(minioConfig.getBucketName(), filename, file.getInputStream());

        String faceUrl = minioConfig.getFileHost() + "/" + minioConfig.getBucketName() + "/" + filename;

        // 微服务远程调用更新用户头像到数据库
        GraceJSONResult graceJSONResult = userInfoMicroServiceFeign.updateFace(userId, faceUrl);
        Object data = graceJSONResult.getData();

        String json = JsonUtils.objectToJson(data);
        UserVO userVO = JsonUtils.jsonToPojo(json, UserVO.class);

        return userVO;
    }

    /**
     * 生成二维码
     *
     * @param wechatNum 微信号
     * @param userId    用户id
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 19:00:40
     */
    @Override
    public String generatorQrCode(String wechatNum, String userId) throws Exception {
        // 构建map对象
        Map<String, String> map = new HashMap<>();
        map.put("wechatNumber", wechatNum);
        map.put("userId", userId);

        // 将对象转换为json字符串，用于存储到二维码中
        String data = JsonUtils.objectToJson(map);

        // 生成二维码
        String qrCodePath = QrCodeUtils.generateQRCode(data);

        if (StringUtils.isNotBlank(qrCodePath)) {
            String uuid = UUID.randomUUID().toString();
            String fileName = "wechatNumber/" + userId + "/" + uuid + ".png";
            String imageQrCodeUrl = MinioUtils.uploadFile(minioConfig.getBucketName(), fileName, qrCodePath, true);
            return imageQrCodeUrl;
        }
        return null;
    }

    /**
     * 上传朋友圈背景图
     *
     * @param file 朋友圈背景图文件
     * @param userId 用户id
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 19:54:35
     */
    @Override
    public UserVO uploadFriendCircleBg(MultipartFile file, String userId) throws Exception {
        if (StringUtils.isBlank(userId)) {
            GraceException.display(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }
        // 获取文件原始名称
        String filename = file.getOriginalFilename();
        if (StringUtils.isBlank(filename)) {
            GraceException.display(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        filename = "friendCircleBg" + "/" + userId + "/" + dealWithoutFilename(filename);
        String imageUrl = MinioUtils.uploadFile(minioConfig.getBucketName(), filename, file.getInputStream(), true);

        // 微服务远程调用更新用户头像到数据库
        GraceJSONResult graceJSONResult = userInfoMicroServiceFeign.updateFriendCircleBg(userId, imageUrl);
        Object data = graceJSONResult.getData();

        String json = JsonUtils.objectToJson(data);
        UserVO userVO = JsonUtils.jsonToPojo(json, UserVO.class);

        return userVO;
    }

    /**
     * 上传聊天背景图
     *
     * @param file 聊天背景图文件
     * @param userId 用户id
     * @return 最新用户信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 20:13:46
     */
    @Override
    public UserVO uploadChatBg(MultipartFile file, String userId) throws Exception {
        if (StringUtils.isBlank(userId)) {
            GraceException.display(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }
        // 获取文件原始名称
        String filename = file.getOriginalFilename();
        if (StringUtils.isBlank(filename)) {
            GraceException.display(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        filename = "chatBg" + "/" + userId + "/" + dealWithoutFilename(filename);
        String imageUrl = MinioUtils.uploadFile(minioConfig.getBucketName(), filename, file.getInputStream(), true);

        // 微服务远程调用更新用户头像到数据库
        GraceJSONResult graceJSONResult = userInfoMicroServiceFeign.updateChatBg(userId, imageUrl);
        Object data = graceJSONResult.getData();

        String json = JsonUtils.objectToJson(data);
        UserVO userVO = JsonUtils.jsonToPojo(json, UserVO.class);

        return userVO;
    }

    /**
     * 生成新的文件名（带原文件名）
     *
     * @param filename 文件名
     * @return 新的文件名
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 19:52:40
     */
    private String dealWithFilename(String filename) {
        String suffixName = filename.substring(filename.lastIndexOf("."));
        String originFileName = filename.substring(0, filename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        return originFileName + "-" + uuid + suffixName;
    }

    /**
     * 生成新的文件名（不带原文件名）
     *
     * @param filename 文件名
     * @return 新的文件名
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 19:52:10
     */
    private String dealWithoutFilename(String filename) {
        String suffixName = filename.substring(filename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        return uuid + suffixName;
    }

}
