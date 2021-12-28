package cn.gface.face.api.data;

import lombok.Data;

@Data
public class TBDeviceInfo {
    private String id;
    private String deviceCode;
    private String deviceIP;
    private String deviceGroup;
    private String deviceName;
    private String heartbeatTime;
    private String createTime;
    private int openType;
    private String location;
    private long status;
    private String remark;
    private String vendors;
}
