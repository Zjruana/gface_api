package cn.gface.face.api.web.dto;

import lombok.Data;

@Data
public class DeviceDeviceRequestEntity {
    private String deviceCode;
    private int deviceUpdateState;
    private long lastUserUpdateTime;
    private long lastDeviceConfigUpdateTime;
    private String versionName;
    private long lastUpdateTime;
    private String publicKey;

}
