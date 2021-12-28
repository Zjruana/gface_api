package cn.gface.face.api.data;

import cn.gface.face.api.utils.StaticVariable;
import lombok.Data;

@Data
public class DeviceAccessVerificationConfigInfo {
    private String deviceCode;
    private String accessEnable;
    private String timeout;
    private String timeoutHandlerFlag;
    private String accessUrl;

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = StaticVariable.getServerIp() + StaticVariable.TOMCAT_STATIC_PATH + accessUrl;
    }
}
