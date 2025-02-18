package com.dengenxi.service.impl;

import com.dengenxi.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-18 19:59:47
 */
@Service
public class FileServiceImpl implements FileService {

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
    public void uploadFace(MultipartFile file, String userId, HttpServletRequest request) throws IOException {
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
}
