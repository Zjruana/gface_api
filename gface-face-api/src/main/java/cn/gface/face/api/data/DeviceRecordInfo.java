package cn.gface.face.api.data;

import lombok.Data;

@Data
public class DeviceRecordInfo {
    private int id;
    private int confidence;
    private String deviceCode;
    private String icCard;
    private String imageBase64;
    private String picUrl;
    private String name;
    private int occlusion;
    private String personId;
    private int personType;
    private String qrCode;
    private long recordTime;
    private int recordType;
    private int degree;
    private float temperature;
    private String hsjl; //  "0":未知 ,"1":未查询到记录 ,"20210913核酸阴性..."
    private String ymjl; // 0未知，1，未接种，2，接种未完成 3，接种完成
    private String xcjl;

    private int guokangCode;
    private String guokangCardId;
    private int guokangStatus;
    private String guokangCardName;
    private String guokangOverCity;

    private String idCardNo;
}
