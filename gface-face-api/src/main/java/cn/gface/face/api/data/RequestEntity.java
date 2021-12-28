package cn.gface.face.api.data;

import lombok.Data;

import java.io.Serializable;

/**
 * dto数据对应存入, 装载任何前端传入的参数
 */
@Data
public class RequestEntity implements Serializable {
    private int page; // 页码
    private int limit; // 页数
    private String sort; // 排序
    private String id; // id
    private String name; // 姓名
    private String idCardNo; // 身份证
    private int personType; // 用户类型
    private String temp; // 体温
    private String beginTime; // 开始时间
    private String endTime; // 结束时间
    private int openType; // 开门方式
    private int reasonVisit; // 事由
    private String mobilePhone; // 手机号码
    private int sex; // 性别
    private String icCard; // 门禁卡号
    private String occlusion; // 佩戴口罩
    private String interviewerName; // 被访人姓名
    private int leaveStatus; // 是否离开
    private int status; // 设备状态
    private String deviceLocation; // 设备位置
    private String deviceCode; // 设备ID
    private String deviceId; // 设备ID
    private String deviceName; // 设备名
    private int deviceType; // 设备类型
    private String location; // 设备位置
    private String vendors; // 厂商
    private String imageUrl; // 照片
    private String remark; // 备注
    private String personId; // 员工ID
    private long createTime; // 创建时间
    private long heartbeatTime; // 心跳时间
    private String deviceCodes;
    private String title; // 特殊的title，标记于数据库查询
    private int operationType; // 操作类型
    private String validDateStart; // 开始时间
    private String validDateEnd; // 到期时间
    private String faceUrl; // 人脸照片
    private String faceBase64; // 人脸照片
    private long updateTime; // 更新时间
    private String openDoorPassword; // 开门密码
    private String jobNumber; // 工号
    private String userName; // 用户账号
    private String deviceGroup; // 组名称
    private String hsjl; //  "0":未知 ,"1":未查询到记录 ,"20210913核酸阴性..."
    private String ymjl; // 0未知，1，未接种，2，接种未完成 3，接种完成
    private String xcjl; // 行程记录
    private String serverUrl;
    //    private String id;
//    private String userName;
    private String password;
    private String role;
    //    private String name;
    private String phoneNumber;
    private String email;
    //    private int sex;
    private String profile;
    //    private String createTime;
    private String avatar;
    private int pid;
    private int count;
    //    private int page;
//    private int limit;
    private String checkPassword;
    private String newPassword;
    private String oldPassword;
    private String pUserName;
    private String deviceIP;


    private String guokangStatus; // 健康码
}
