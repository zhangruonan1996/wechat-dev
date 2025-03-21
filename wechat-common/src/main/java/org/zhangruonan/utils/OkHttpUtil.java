package org.zhangruonan.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.zhangruonan.grace.result.GraceJSONResult;
import org.zhangruonan.grace.result.JSONResult;

/**
 *
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-15 13:56:29
 */
@Slf4j
public class OkHttpUtil {

    public static GraceJSONResult get(String url) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .get()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            return JsonUtils.jsonToPojo(res, GraceJSONResult.class);
        } catch (Exception e) {
            log.error("OkHttp get failed:", e);
        }
        return null;
    }

}
