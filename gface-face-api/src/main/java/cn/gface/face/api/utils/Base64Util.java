package cn.gface.face.api.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;

@Component
public class Base64Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(Base64Util.class);


    /**
     * base64转图片路径
     *
     * @param filePath     StaticVariable.配置项
     * @param base64String base64 字符串
     * @param fileName     额外添加的名称,一般为 DeviceCode 或用户 ID
     * @return 文件保存路径 + 图片名称 (不含 ip + 端口 + tomcat静态资源映射路径) 如需返回给前端需加上: StaticVariable.getServerIp() + StaticVariable.TOMCAT_STATIC_PATH +
     */
    public static String getImgPath(String filePath, String base64String, String fileName) {
        //对字节数组字符串进行Base64解码并生成图片
        if (base64String == null || base64String.isEmpty()) return null;
        Decoder decoder = Base64.getMimeDecoder();
        Date date = new Date();
        OutputStream out = null;

        try {
            // 去除 data:image/jpg;base64,
            if (base64String.contains("data:image/jpg;base64,")) {
                base64String = base64String.replace("data:image/jpg;base64,", "");
            }
            if (base64String.contains("data:image/png;base64,")) {
                base64String = base64String.replace("data:image/png;base64,", "");
            }
            if (base64String.contains("data:image/mp4;base64,")) {
                base64String = base64String.replace("data:image/mp4;base64,", "");
            }
            if (base64String.contains("data:image/jpeg;base64,")) {
                base64String = base64String.replace("data:image/jpeg;base64,", "");
            }
            //Base64解码
            byte[] b = decoder.decode(base64String);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            // --目录生成-- 路径不存在则创建
            String saveFilePath = filePath + new SimpleDateFormat("/yyyy-MM-dd/").format(date); // 生成 /usr/local/gwebstatic/device_img/face_img/2021-12-12/
            File file = new File(StaticVariable.SAVE_FILE_FOLDER + saveFilePath);
            if (!file.exists()) LOGGER.info("目录创建: {} ,结果: {}", file.getPath(), file.mkdirs());
            // --目录生成完成--
            // --文件生成--
            String imgFileName = date.getTime() + "@" + fileName + "&" + RandomStringUtils.randomAlphanumeric(8) + ".jpeg"; // 图片名
            out = new FileOutputStream(file.getPath() + "/" + imgFileName);
            out.write(b);
            return saveFilePath + imgFileName; // 输出：/device_img/face_img/2021-12-12/1619161478414@951ed2cfa5a14bb0a02e6235baf6f180.jpeg
        } catch (Exception e) {
            LOGGER.error("Base64图片保存异常: {}", e.getMessage());
            return null;
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    LOGGER.error("Base64图片保存 flush close 异常: {}", e.getMessage());
                }
            }
        }

    }
}
