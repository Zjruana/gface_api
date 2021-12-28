package cn.gface.face.api.data;

import cn.gface.face.api.utils.StaticVariable;
import lombok.Data;

@Data
public class DeviceUserInfo {
    private String personId;
    private int operationType;
    private long updateTime;
    private String name;
    private String faceUrl;
    private byte[] faceBase64;
    private long validDateStart;
    private long validDateEnd;
    private String icCard;
    private String openDoorPassword;
    private String jobNumber;
    private String idCardNo;
    private int personType;

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = StaticVariable.getServerIp() + StaticVariable.TOMCAT_STATIC_PATH + faceUrl;
    }
}
