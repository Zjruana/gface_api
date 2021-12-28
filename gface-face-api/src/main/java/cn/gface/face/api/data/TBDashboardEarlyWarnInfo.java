package cn.gface.face.api.data;

import cn.gface.face.api.utils.StaticVariable;
import lombok.Data;

import java.io.Serializable;

/**
 * 预警信息弹出
 */
@Data
public class TBDashboardEarlyWarnInfo implements Serializable {
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
    private boolean readMsg; // 前端是否已读该消息
    private boolean newMsg; // 是否新消息
    private int degree; // 严重程度，1红色预警，2黄色预警，-1一般预警

    public void setPicUrl(String picUrl) {
        this.picUrl = StaticVariable.getServerIp() + StaticVariable.TOMCAT_STATIC_PATH + picUrl;
    }
}
