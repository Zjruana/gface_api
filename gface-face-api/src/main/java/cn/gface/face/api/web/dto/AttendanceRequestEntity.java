package cn.gface.face.api.web.dto;

import lombok.Data;

@Data
public class AttendanceRequestEntity {
    private int page; // 页码
    private int limit; // 页数
    private String sort; // 排序
    private String userName;
    private String shiftType;
    private int attendanceStatus;
    private String attendanceTime;
    private String month;
    private String workName;
    private long dataTime;
}
