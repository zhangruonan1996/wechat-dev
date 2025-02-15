package com.dengenxi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 11:30:11
 */
@RestController
@RequestMapping("/file")
public class HelloController {

    @GetMapping("/hello")
    public Object hello() {
        return "你好，小爱";
    }

}
