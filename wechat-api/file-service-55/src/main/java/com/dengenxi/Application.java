package com.dengenxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 11:29:27
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient  // 开启服务的注册和发现功能
@EnableFeignClients("com.dengenxi.feign")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
