package cn.gface.face.api.data;

import lombok.Data;

@Data
public class TBAttendanceInfo {
    private int id;
    private String workName;
    private String personId;
    private String toWorkTime;
    private String offWorkTime;
    private int beforeWorkTime;
    private int afterWorkTime;
    private int allowLate;
    private int allowRetreat;
    private String deviceGroup;
    private String name;
    private Long recordTime;
    private String note;

}
