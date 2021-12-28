package cn.gface.face.api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

@Component
public class StaticVariable {
    /**
     * 配置项 .properties 统一导入
     */
    // 文件保存主路径
    public static String SAVE_FILE_FOLDER;
    // tomcat 静态资源路径
    public static String TOMCAT_STATIC_PATH;
    /**
     * 图片路径配置
     */
    // 保存设备端人脸的照片
    public static String PATH_DEVICE_FACE_IMG = "/device_img/face_img";
    // 保存设备端身份证的照片
    public static String PATH_DEVICE_ID_CARD_IMG = "/device_img/id_card_img";
    // 保存客户端上传的照片
    public static String PATH_WEB_FACE_IMG = "/web_img/face_img";
    // 服务器IP地址 http://IP:端口
    private static String SERVER_IP;

    /**
     * 服务器配置
     */

    public static String getServerIp() {
//        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
//            // windows 系统自动获取 IP+端口
//            try {
//                MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
//                Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"), Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
//                String port = objectNames.iterator().next().getKeyProperty("port");
//
//                SERVER_IP = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port;
//            } catch (MalformedObjectNameException | UnknownHostException e) {
//                return SERVER_IP;
//            }
//        }
        return SERVER_IP;
    }

    @Value("${save.file.path}")
    public void setSaveFileFolder(String filePath) {
        SAVE_FILE_FOLDER = filePath;
    }

    @Value("${tomcat.static.path}")
    public void setTomcatStaticPath(String tomcatStaticPath) {
        TOMCAT_STATIC_PATH = tomcatStaticPath;
    }

    @Value("${server.url}")
    public void setServerUrl(String serverUrl) {
        SERVER_IP = serverUrl;
    }

}
