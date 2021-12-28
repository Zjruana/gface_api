package cn.gface.face.api.web.dto;

import lombok.Data;

@Data
public class DeviceRecordRequestEntity {
    private int confidence;
    private String deviceCode;
    private String icCard;
    private IdCardInfo idCardInfo;
    private GuoKangInfo guokangCode;
    private String imageBase64;
    private String name;
    private int occlusion;
    private String personId;
    private String qrCode;
    private long recordTime;
    private int recordType;
    private float temperature;
    private String hsjl; //  "0":未知 ,"1":未查询到记录 ,"20210913核酸阴性..."
    private String ymjl; // 0未知，1，未接种，2，接种未完成 3，接种完成
    private String xcjl;
    private int personType; // 用户类型：0，固定用户 1，访客 2，黑名单
}
