package cn.gface.face.api.web.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRecordRequestEntity implements Serializable {

    private int page; // 页码
    private int limit; // 页数
    private String sort; // 排序
    private String id; // id
    private String name; // 姓名
    private String icCard; // 门禁卡号
    private String occlusion; // 佩戴口罩
    private String temp; // 体温
    private String beginTime; // 开始时间
    private String endTime; // 结束时间
    private int openType; // 开门方式
    private String guokangStatus; // 健康码
    private String title; // 特殊的title，标记于数据库查询
    private String deviceName; // 设备名
    private String hsjl; //  "0":未知 ,"1":未查询到记录 ,"20210913核酸阴性..."
    private String ymjl; // 0未知，1，未接种，2，接种未完成 3，接种完成
    private String xcjl; // 行程记录
}
