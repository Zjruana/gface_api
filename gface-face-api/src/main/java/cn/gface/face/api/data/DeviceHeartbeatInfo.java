package cn.gface.face.api.data;

import lombok.Data;

@Data
public class DeviceHeartbeatInfo {
    private Object deviceCode;
    private Object deviceGroup;
    private String apkUrl;
    private String[] deviceCodes;
    private long lastUserUpdateTime;
    private long lastDeviceConfigUpdateTime;
    private int state;
}
