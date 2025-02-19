package com.dengenxi.feign;

import com.dengenxi.grace.result.GraceJSONResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-19 00:07:45
 */
@FeignClient(value = "main-service")
public interface UserInfoMicroServiceFeign {

    @PostMapping("/userinfo/updateFace")
    public GraceJSONResult updateFace(@RequestParam("userId") String userId, @RequestParam("face") String face);

    @PostMapping("/userinfo/updateFriendCircleBg")
    public GraceJSONResult updateFriendCircleBg(@RequestParam("userId") String userId, @RequestParam("friendCircleBg") String friendCircleBg);

}
