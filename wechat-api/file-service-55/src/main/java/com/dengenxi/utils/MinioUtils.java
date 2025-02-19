package com.dengenxi.utils;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * MinIO工具类
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-02-18 21:39:54
 */
@Slf4j
public class MinioUtils {

    private static MinioClient minioClient;

    private static String endpoint;
    private static String fileHost;
    private static String bucketName;
    private static String accessKey;
    private static String secretKey;

    private static final String SEPERATOR = "/";

    public MinioUtils() {
    }

    public MinioUtils(String endpoint, String fileHost, String bucketName, String accessKey, String secretKey) {
        MinioUtils.endpoint = endpoint;
        MinioUtils.fileHost = fileHost;
        MinioUtils.bucketName = bucketName;
        MinioUtils.accessKey = accessKey;
        MinioUtils.secretKey = secretKey;
        createMinioClient();
    }

    /**
     * 创建基于Java端的MinioClient
     *
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 21:45:08
     */
    public void createMinioClient() {
        try {
            if (null == minioClient) {
                log.info("开始创建 MinioClient");
                minioClient = MinioClient
                        .builder()
                        .endpoint(endpoint)
                        .credentials(accessKey, secretKey)
                        .build();
                createBucket(bucketName);
                log.info("创建完毕 MinioClient");
            }
        } catch (Exception e) {
            log.error("Minio服务器异常：{}", e);
        }
    }


    /************************************* Operate Bucket Start *************************************/

    /**
     * 获取上传文件前缀路径
     *
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 21:46:26
     */
    public static String getBasisUrl() {
        return endpoint + SEPERATOR + bucketName + SEPERATOR;
    }


    /**
     * 启动SpringBoot容器的时候初始化Bucket
     *
     * @param bucketName 存储桶名称
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 21:48:21
     */
    public static void createBucket(String bucketName) throws Exception {
        if (!bucketExists(bucketName)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 判断Bucket是否存在
     *
     * @param bucketName 存储桶名称
     * @return 是否存在（true：存在 false：不存在）
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 21:49:54
     */
    public static Boolean bucketExists(String bucketName) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取Bucket的策略
     *
     * @param bucketName 存储桶名称
     * @return 存储桶的策略
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 21:50:42
     */
    public static String getBucketPolicy(String bucketName) throws Exception {
        String bucketPolicy = minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).build());
        return bucketPolicy;
    }

    /**
     * 获取所有的Bucket列表
     *
     * @return 所有的Bucket列表
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 21:52:00
     */
    public static List<Bucket> getAllBuckets() throws Exception {
        return minioClient.listBuckets();
    }

    /**
     * 根据bucketName获取其相关信息
     *
     * @param bucketName 存储桶名称
     * @return bucket相关信息
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 21:53:08
     */
    public static Optional<Bucket> getBucket(String bucketName) throws Exception {
        return getAllBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 根据存储桶名称删除Bucket
     *
     * @param bucketName 存储桶名称
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 21:54:06
     */
    public static void removeBucket(String bucketName) throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    /************************************* Operate Bucket End *************************************/


    /************************************* Operate Files Start *************************************/

    /**
     * 判断文件是否存在
     *
     * @param bucketName
     * @param objectName
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 21:56:06
     */
    public static Boolean isObjectExists(String bucketName, String objectName) {
        boolean exist = true;
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    /**
     * 判断文件夹是否存在
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件夹
     * @return 是否存在（true：是 false：否）
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:02:29
     */
    public static Boolean isFolderExists(String bucketName, String objectName) {
        boolean exist = false;
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(objectName).recursive(false).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.isDir() && objectName.equals(item.objectName())) {
                    exist = true;
                }
            }
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    /**
     * 根据文件前置查询文件
     *
     * @param bucketName 存储桶名称
     * @param prefix 前缀
     * @param recursive 是否使用递归查询
     * @return MinioItem列表
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:06:43
     */
    public static List<Item> getAllObjectsByPrefix(String bucketName, String prefix, Boolean recursive) throws Exception {
        List<Item> list = new ArrayList<>();
        Iterable<Result<Item>> objectsIterator = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build());
        if (objectsIterator != null) {
            for (Result<Item> o : objectsIterator) {
                Item item = o.get();
                list.add(item);
            }
        }
        return list;
    }

    /**
     * 通过MultipartFile，上传文件
     *
     * @param bucketName 存储桶
     * @param file 文件
     * @param objectName 对象名
     * @param contentType 文件类型
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:07:37
     */
    public static ObjectWriteResponse putObject(String bucketName, MultipartFile file, String objectName, String contentType) throws Exception {
        InputStream inputStream = file.getInputStream();
        return minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(objectName).contentType(contentType)
                        .stream(inputStream, inputStream.available(), -1).build());
    }

    /**
     * 通过MultipartFile，上传文件
     *
     * @param bucketName 存储桶
     * @param file 文件
     * @param objectName 对象名
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:08:56
     */
    public static ObjectWriteResponse putObject(String bucketName, MultipartFile file, String objectName) throws Exception {
        InputStream inputStream = file.getInputStream();
        return minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(objectName)
                        .stream(inputStream, inputStream.available(), -1).build());
    }

    /**
     * 上传本地文件
     *
     * @param bucketName 存储桶
     * @param objectName 对象名称
     * @param filePath 文件路径(网络)
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:09:53
     */
    public static ObjectWriteResponse putObject(String bucketName, String objectName, String filePath)
            throws Exception {
        URLConnection urlConnection = new URL(filePath).openConnection();
        return minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .contentType(urlConnection.getContentType())
                        .stream(urlConnection.getInputStream(), urlConnection.getContentLength(), -1)
                        .build());
    }

    /**
     * 上传本地文件
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param fileName 本地文件路径
     * @param needUrl 是否需要文件url
     * @return 文件url
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-19 19:07:12
     */
    public static String uploadFile(String bucketName, String objectName, String fileName, Boolean needUrl)
            throws Exception {
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .filename(fileName)
                        .build());
        if (needUrl) {
            String imageUrl = fileHost + "/" + bucketName + "/" + objectName;
            return imageUrl;
        }
        return "";
    }

    /**
     * 连接参数
     *
     * @param bucketName 存储桶
     * @param fileAbsName 文件绝对路径
     * @param connection 链接对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:10:51
     */
    public static ObjectWriteResponse putObject(String bucketName, String fileAbsName, URLConnection connection) throws Exception {
        InputStream inputStream = connection.getInputStream();
        return minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(fileAbsName).contentType(connection.getContentType())
                        .stream(inputStream, inputStream.available(), -1).build());
    }

    /**
     * 通过流上传文件
     *
     * @param bucketName 存储桶
     * @param objectName 文件对象
     * @param inputStream 文件流
     * @return
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:11:14
     */
    public static ObjectWriteResponse putObject(String bucketName, String objectName,
                                                InputStream inputStream)
            throws Exception {
        return minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                        inputStream, inputStream.available(), -1).build());
    }

    /**
     * 创建文件夹或目录
     *
     * @param bucketName 存储桶
     * @param objectName 目录路径
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:11:33
     */
    public static ObjectWriteResponse putDirObject(String bucketName, String objectName)
            throws Exception {
        return minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                        new ByteArrayInputStream(new byte[]{}), 0, -1).build());
    }

    /**
     * 拷贝文件
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件名称
     * @param srcBucketName 目标bucket名称
     * @param srcObjectName 目标文件名称
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:11:50
     */
    public static ObjectWriteResponse copyObject(String bucketName, String objectName,
                                                 String srcBucketName, String srcObjectName)
            throws Exception {
        return minioClient.copyObject(
                CopyObjectArgs.builder()
                        .source(CopySource.builder().bucket(bucketName).object(objectName).build())
                        .bucket(srcBucketName)
                        .object(srcObjectName)
                        .build());
    }

    /**
     * 文件下载
     *
     * @param bucketName 桶名称
     * @param request 请求
     * @param response 请求响应
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:12:20
     */
    public static void downloadFile(String bucketName, String originalName,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        try {
            InputStream file = getObject(bucketName, originalName);
            //文件名乱码处理
            String useragent = request.getHeader("USER-AGENT").toLowerCase();
            if (useragent.contains("msie") || useragent.contains("like gecko") || useragent.contains("trident")) {
                originalName = URLEncoder.encode(originalName, StandardCharsets.UTF_8.displayName());
            } else {
                originalName = new String(originalName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + originalName);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = file.read(buffer)) > 0) {
                servletOutputStream.write(buffer, 0, len);
            }
            servletOutputStream.flush();
            file.close();
            servletOutputStream.close();
        } catch (Exception e) {
            System.err.println(String.format("下载文件:%s异常", originalName));
        }
    }

    /**
     * 获取文件流
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:12:46
     */
    public static InputStream getObject(String bucketName, String objectName) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 断点下载
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件名称
     * @param offset 起始字节的位置
     * @param length 要读取的长度
     * @return 流
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:15:12
     */
    public InputStream getObject(String bucketName, String objectName, long offset, long length)
            throws Exception {
        return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).offset(offset).length(length).build());
    }

    /**
     * 获取路径下文件列表
     *
     * @param bucketName bucket名称
     * @param prefix 文件名称
     * @param recursive 是否递归查找，如果是false,就模拟文件夹结构查找
     * @return 二进制流
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:13:07
     */
    public static Iterable<Result<Item>> listObjects(String bucketName, String prefix, boolean recursive) {
        return minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build());
    }

    /**
     * 获取路径下文件列表
     *
     * @param bucketName bucket名称
     * @param recursive 是否递归查找，如果是false,就模拟文件夹结构查找
     * @return 二进制流
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:13:26
     */
    public static Iterable<Result<Item>> listObjects(String bucketName, boolean recursive) {
        return minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).recursive(recursive).build());
    }

    /**
     * 删除文件
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件名称
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:13:44
     */
    public static void removeObject(String bucketName, String objectName)
            throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 批量删除文件
     *
     * @param bucketName 存储桶名称
     * @param keys 需要删除的文件列表
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:14:04
     */
    public static void removeObjects(String bucketName, List<String> keys) {
        List<DeleteObject> objects = new LinkedList<>();
        keys.forEach(s -> {
            objects.add(new DeleteObject(s));
            try {
                removeObject(bucketName, s);
            } catch (Exception e) {
                System.err.println("批量删除失败!");
            }
        });
    }

    /**
     * 生成预览链接，最大7天有效期
     * 如果想永久有效，在 minio 控制台设置仓库访问规则总几率
     *
     * @param bucketName 存储桶名称
     * @param object 文件名称
     * @param contentType 预览类型 image/gif", "image/jpeg", "image/jpg", "image/png", "application/pdf
     * @param validTime 有效时间 不能超过7天
     * @param timeUnit 单位 时 分 秒 天
     * @return 预览链接地址
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:15:54
     */
    public static String getPreviewUrl(String bucketName, String object, String contentType, Integer validTime, TimeUnit timeUnit) {
        Map<String, String> reqParams = null;
        if (contentType != null) {
            reqParams = new HashMap<>();
            reqParams.put("response-content-type", contentType != null ? contentType : "application/pdf");
        }
        String url = null;
        try {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(object)
                            .expiry(validTime, timeUnit)
                            .extraQueryParams(reqParams)
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 文件列表
     *
     * @param limit 最大条数
     * @param bucketName 存储桶名称
     * @return 文件列表
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:18:41
     */
    public List<Item> listObjects(int limit, String bucketName) {
        List<Item> objects = new ArrayList<>();
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .maxKeys(limit)
                        .includeVersions(true)
                        .build());
        try {
            for (Result<Item> result : results) {
                objects.add(result.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }

    /**
     * 网络文件转储 minio
     *
     * @param httpUrl 文件地址
     * @param bucketName 存储桶名称
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:19:43
     */
    public static void netToMinio(String httpUrl, String bucketName) {
        int i = httpUrl.lastIndexOf(".");
        String substring = httpUrl.substring(i);
        URL url;
        try {
            url = new URL(httpUrl);
            URLConnection urlConnection = url.openConnection();
            // agent 模拟浏览器
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            // 临时文件转储
            File tempFile = File.createTempFile(UUID.randomUUID().toString().replace("-", ""), substring);
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            // 上传minio
            putObject(bucketName, tempFile.getAbsolutePath(), tempFile.getName());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件转字节数组
     *
     * @param path 文件路径
     * @return byte[] 字节数组
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:20:06
     */
    public byte[] fileToBytes(String path) {
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            fis = new FileInputStream(path);
            int temp;
            byte[] bt = new byte[1024 * 10];
            while ((temp = fis.read(bt)) != -1) {
                bos.write(bt, 0, temp);
            }
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(fis)) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bos.toByteArray();
    }

    /**
     * 将URLDecoder编码转成UTF8
     *
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:20:23
     */
    public static String getUtf8ByURLDecoder(String str) throws UnsupportedEncodingException {
        String url = str.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        return URLDecoder.decode(url, "UTF-8");
    }

    /**
     * 下载并压缩 Minio 桶中的文件，并通过 HTTP 响应输出
     *
     * @param bucketName 存储桶名称
     * @param response HTTP 响应对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:20:55
     */
    public static void downloadMinioFileToZip(String bucketName, HttpServletResponse response) {
        downloadMinioFileToZip(bucketName, "", response);
    }

    /**
     * 下载并压缩 Minio 桶中的文件，并通过 HTTP 响应输出
     *
     * @param bucketName 存储桶名称
     * @param folderPath 文件夹路径（可为空）
     * @param response HTTP 响应对象
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-02-18 22:21:17
     */
    public static void downloadMinioFileToZip(String bucketName, String folderPath, HttpServletResponse response) {
        try {
            // 如果 folderPath 为空，列出整个桶中的文件
            if (folderPath == null || folderPath.isEmpty()) {
                // 根目录
                folderPath = "";
            }

            // 设置 HTTP 响应头
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment;filename=" + bucketName + ".zip");

            // 创建 ZipOutputStream，将文件写入 response 的输出流
            try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {

                // 列出文件夹中的所有对象
                Iterable<Result<Item>> results = minioClient.listObjects(
                        ListObjectsArgs.builder().bucket(bucketName).prefix(folderPath).recursive(true).build()
                );

                // 下载并压缩文件夹中的所有对象
                for (Result<Item> result : results) {
                    Item item = result.get();
                    String objectName = item.objectName();

                    log.info("找到对象: {}", objectName);

                    // 跳过目录对象，确保只处理实际文件
                    if (objectName.endsWith("/")) {
                        continue;
                    }

                    // 为每个对象创建一个新的 ZipEntry（压缩包中的文件条目）
                    ZipEntry zipEntry = new ZipEntry(objectName);
                    zipOut.putNextEntry(zipEntry);

                    // 从 MinIO 获取对象输入流
                    try (InputStream stream = minioClient.getObject(
                            GetObjectArgs.builder().bucket(bucketName).object(objectName).build())) {

                        // 将文件数据写入压缩包
                        byte[] buf = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = stream.read(buf)) != -1) {
                            zipOut.write(buf, 0, bytesRead);
                        }

                        // 关闭当前文件条目
                        zipOut.closeEntry();
                        log.info("文件压缩成功: {}", objectName);

                    } catch (Exception e) {
                        log.error("下载并压缩文件时发生错误: {}", e.getMessage(), e);
                    }
                }

                log.info("所有文件已成功压缩并通过响应输出。");

            } catch (IOException e) {
                log.error("创建压缩包时发生错误: {}", e.getMessage(), e);
            }

        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("发生错误: {}", e.getMessage(), e);
        }
    }

}
