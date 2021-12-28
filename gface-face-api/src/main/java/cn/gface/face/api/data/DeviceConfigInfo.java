package cn.gface.face.api.data;

import lombok.Data;

import java.util.Date;

@Data
public class DeviceConfigInfo {
    private String deviceCode;
    private int readIdCardEnable;
    private int relayInterval;
    private int RelayDefaultMode;
    private int recognitionThreshold;
    private String deviceName;
    private long heartbeatInterval;
    private int livenessType;
    private int captureModeEnable;
    private int detectionTemperatureEnable;
    private int temperatureUnit;
    private float alarmTemperature;
    private int detectionOcclusionEnable;
    private long updateTime;
    private long serverTime;

    public void setServerTime(long serverTime) {
        this.serverTime = new Date().getTime();
    }
}
