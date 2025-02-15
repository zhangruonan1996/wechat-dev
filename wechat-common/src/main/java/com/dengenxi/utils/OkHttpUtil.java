// package com.dengenxi.utils;
//
// import com.squareup.okhttp.OkHttpClient;
// import com.squareup.okhttp.Request;
// import com.squareup.okhttp.Response;
// import lombok.extern.slf4j.Slf4j;
// import org.wechat.grace.result.JSONResult;
//
// /**
//  *
//  *
//  * @author qinhao
//  * @email coderqin@foxmail.com
//  * @date 2025-02-15 13:56:29
//  */
// @Slf4j
// public class OkHttpUtil {
//
//     public static JSONResult get(String url) {
//         try {
//             OkHttpClient client = new OkHttpClient();
//             Request request = new Request.Builder()
//                     .get()
//                     .url(url)
//                     .build();
//             Response response = client.newCall(request).execute();
//             String res = response.body().string();
//             return JsonUtils.jsonToPojo(res, JSONResult.class);
//         } catch (Exception e) {
//             log.error("OkHttp get failed:", e);
//         }
//         return null;
//     }
//
// }
