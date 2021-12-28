package cn.gface.face.api.web.dto;

import lombok.Data;

@Data
public class UserDeviceRequestEntity {
    private int page; // 页码
    private int limit; // 页数
    private String sort; // 排序
    private String beginTime;
    private String endTime;
    private String id;
    private String deviceCode;
    private String deviceName;
    private String createTime;
    private int openType;
    private String location;
    private int status;
    private String remark;
    private String vendors;
    private String deviceGroup;
    private String deviceIP;
}
