package org.zhangruonan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 11:31:22
 */
@SpringBootApplication
@EnableDiscoveryClient  // 开启服务的注册和发现功能
@MapperScan(basePackages = "org.zhangruonan.mapper")
@EnableFeignClients("org.zhangruonan.feign")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
