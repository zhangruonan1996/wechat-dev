package org.zhangruonan.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 视频操作工具类
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-19 21:00:04
 */
@Slf4j
public class JcodecVideoUtil {

    // 存放截取视频某一帧的图片
    public static String videoFramesPath = "D:\\data";

    /**
     * 图片格式
     */
    private static final String FILE_EXT = "jpg";

    /**
     * 帧数
     */
    private static final int THUMB_FRAME = 5;

    /**
     * 获取指定视频的帧并保存为图片至指定目录
     *
     * @param videoFilePath 源视频文件路径
     * @param frameFilePath 截取帧的图片存放路径
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-19 21:03:04
     */
    public static void fetchFrame(String videoFilePath, String frameFilePath) {
        File videoFile = new File(videoFilePath);
        File frameFile = new File(frameFilePath);
        getFirstFrame(videoFile, frameFile);
    }

    /**
     * 获取指定视频的帧并保存为图片至指定目录
     *
     * @param videoFile
     * @param targetFile
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-19 21:04:44
     */
    public static void fetchFrame(MultipartFile videoFile, File targetFile) throws IOException {
        File file = new File(videoFile.getName());
        FileUtils.copyInputStreamToFile(videoFile.getInputStream(), file);
        getFirstFrame(file, targetFile);
    }

    /**
     * 获取指定视频的帧并保存为图片至指定目录
     *
     * @param videoFile 源视频文件
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-19 21:12:34
     */
    public static File fetchFrame(MultipartFile videoFile) {
        String originalFilename = videoFile.getOriginalFilename();
        File file = new File(originalFilename);
        File targetFile = null;
        try {
            FileUtils.copyInputStreamToFile(videoFile.getInputStream(), file);

            int i = originalFilename.lastIndexOf(".");
            String imageName;

            if (i > 0) {
                imageName = originalFilename.substring(0, i);
            } else {
                imageName = UUID.randomUUID().toString().replace("-", "");
            }
            imageName = imageName + ".jpg";
            targetFile = new File(imageName);
            getFirstFrame(file, targetFile);

        } catch (Exception e) {
            log.error("获取视频指定帧异常：", e);
        } finally {
            if (file.exists()) {
                file.delete();
            }
        }
        log.debug("视频文件 - 帧截取 - 处理结束");
        return targetFile;
    }

    /**
     * 获取第一帧缩略图
     *
     * @param videoFile 视频路径
     * @param targetFile 缩略图目标路径
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-19 21:07:06
     */
    public static void getFirstFrame(File videoFile, File targetFile) {
        try {
            Picture picture = FrameGrab.getFrameFromFile(videoFile, THUMB_FRAME);
            BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
            ImageIO.write(bufferedImage, FILE_EXT, targetFile);
        } catch (IOException | JCodecException e) {
            e.printStackTrace();
            log.error("截取第一帧缩略图异常：", e);
        }
    }

}
