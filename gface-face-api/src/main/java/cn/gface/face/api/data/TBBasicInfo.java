package cn.gface.face.api.data;

import lombok.Data;

@Data
public class TBBasicInfo {
    private String id;
    private String title;
    private String remark;
    private String timestamp;
    private String type;
    private int status;
}
