package cn.gface.face.api.data;

import cn.gface.face.api.utils.StaticVariable;
import lombok.Data;

import java.io.Serializable;

/**
 * 记录详细信息
 */
@Data
public class TBRecordInfo implements Serializable {
    private String id;
    private int confidence;
    private String deviceCode;
    private String deviceName;
    private String icCard;
    private String picUrl;
    private String name;
    private int occlusion;
    private String personId;
    private int personType;
    private String qrCode;
    private long recordTime;
    private int recordType;
    private float temperature;
    private String hsjl; //  "0":未知 ,"1":未查询到记录 ,"20210913核酸阴性..."
    private String ymjl; // 0未知，1，未接种，2，接种未完成 3，接种完成
    private String xcjl;
    private String idCardNo; // 身份证号
    private String guokangStatus; // 健康码

    public void setPicUrl(String picUrl) {
        this.picUrl = StaticVariable.getServerIp() + StaticVariable.TOMCAT_STATIC_PATH + picUrl;
    }
}