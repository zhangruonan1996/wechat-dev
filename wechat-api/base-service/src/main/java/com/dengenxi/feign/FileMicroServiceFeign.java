package com.dengenxi.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-19 19:21:34
 */
@FeignClient(value = "file-service")
public interface FileMicroServiceFeign {

    @PostMapping("/file/generatorQrCode")
    public String generatorQrCode(@RequestParam("wechatNum") String wechatNum, @RequestParam("userId") String userId) throws Exception;

}
