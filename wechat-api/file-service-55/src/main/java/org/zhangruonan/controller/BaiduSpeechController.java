package org.zhangruonan.controller;

import com.baidu.aip.speech.AipSpeech;
import com.google.gson.JsonArray;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zhangruonan.config.BaiduAISDK;
import org.zhangruonan.grace.result.GraceJSONResult;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-20 00:14:28
 */
@RestController
@RequestMapping("/speech")
public class BaiduSpeechController {

    @PostMapping("/uploadVoice")
    public GraceJSONResult uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        byte[] pcmByteData = mp3ConvertToPcm(file.getInputStream());
        HashMap<String, Object> options = new HashMap<>();
        int dev_id = 1537;
        options.put("dev_id", dev_id);
        JSONObject jsonFromBaidu = basicByData(pcmByteData, "pcm", options);
        System.out.println(jsonFromBaidu);
        JSONArray jsonArray = jsonFromBaidu.getJSONArray("result");
        String result = jsonArray.getString(0);
        System.out.println(result);

        return GraceJSONResult.ok(result);
    }

    /**
     * MP3转PCM
     *
     * @param inputStream MP3输入流
     * @return PCM
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-20 21:20:25
     */
    public static byte[] mp3ConvertToPcm(InputStream inputStream) throws IOException {
        // 转换PCM
        AudioInputStream audioInputStream = getPcmAudioInputStream(inputStream);
        byte[] pcmBytes = IOUtils.toByteArray(audioInputStream);
        return pcmBytes;
    }

    /**
     * 获取PCM AudioInputStream数据
     *
     * @param inputStream MP3输入流
     * @return PCM输入流
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-20 21:19:23
     */
    private static AudioInputStream getPcmAudioInputStream(InputStream inputStream) {
        AudioInputStream audioInputStream = null;
        AudioFormat targetFormat = null;
        try {
            AudioInputStream in = null;
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(inputStream);
            AudioFormat baseFormat = in.getFormat();
            targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getFrameRate(),
                    false);
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioInputStream;
    }

    /**
     * 语音识别（来自文件）
     *
     * @param voiceData 语音文件
     * @param fileType  文件类型
     * @param options   选项
     * @return 识别结果
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-20 21:14:07
     */
    public static JSONObject basicByData(byte[] voiceData, String fileType, HashMap<String, Object> options) {
        AipSpeech client = getClient();
        return client.asr(voiceData, fileType, 16000, options);
    }

    /**
     * 获取AipSpeech对象
     *
     * @author qinhao
     * @email coderqin@foxmail.com
     * @date 2025-03-20 21:13:54
     */
    public static AipSpeech getClient() {
        AipSpeech client = new AipSpeech(BaiduAISDK.APP_ID,
                BaiduAISDK.API_KEY,
                BaiduAISDK.SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        return client;
    }

}
