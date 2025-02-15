package com.dengenxi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 11:23:28
 */
@RestController
@RequestMapping("/auth")
public class HelloController {

    @GetMapping("/hello")
    public Object hello() {
        return "你好，邓恩熙";
    }

}
