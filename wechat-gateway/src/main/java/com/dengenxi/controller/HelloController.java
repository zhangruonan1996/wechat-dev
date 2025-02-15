package com.dengenxi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 12:58:22
 */
@RestController
@RequestMapping("/gateway")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "你好，恩熙宝宝";
    }

}
