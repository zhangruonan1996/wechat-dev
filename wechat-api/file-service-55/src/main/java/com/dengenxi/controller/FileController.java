package com.dengenxi.controller;

import com.dengenxi.grace.result.GraceJSONResult;
import com.dengenxi.service.FileService;
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

    @PostMapping("/uploadFace")
    public GraceJSONResult uploadFace(@RequestParam("file") MultipartFile file, String userId, HttpServletRequest request) throws IOException {
        fileService.uploadFace(file, userId, request);
        return GraceJSONResult.ok();
    }

}
