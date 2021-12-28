package cn.gface.face.api.data;

import lombok.Data;

import java.io.Serializable;

/**
 * 批量导入的用户详细信息返回给前端
 */
@Data
public class TBBatchUploadPersonnelInfo implements Serializable {
    private String name;
    private String mobilePhone;
    private String icCard;
    private String faceUrl;
    private String remark;
    private String deviceGroup;
    private String openDoorPassword;
    private String jobNumber;
    private String idCardNo;
    private String endTime;
    private String imageUrl;

}
