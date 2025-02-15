package com.dengenxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 11:29:27
 */
@SpringBootApplication
@EnableDiscoveryClient  // 开启服务的注册和发现功能
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
