package cn.gface.face.api.data;

import cn.gface.face.api.utils.StaticVariable;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户详细信息
 */
@Data
public class TBPersonnelInfo implements Serializable {
    private String id;
    private String personId;
    private String name;
    private String mobilePhone;
    private String validDateEnd;
    private String validDateStart;
    private String icCard;
    private String faceUrl;
    private int sex;
    private String remark;
    private String deviceGroup;
    private String openDoorPassword;
    private String jobNumber;
    private String idCardNo;

//    public String getOpenDoorPassword() {
//        if (openDoorPassword == null) {
//            return null;
//        }
//        if (openDoorPassword.length() < 2) {
//            return openDoorPassword.substring(0, 1) + "*";
//        }
//        StringBuffer stringBuffer = new StringBuffer();
//        for (int i = 2; i < openDoorPassword.length(); i++) {
//            stringBuffer.append("*");
//        }
//        return openDoorPassword.substring(0, 1) + stringBuffer + openDoorPassword.substring(openDoorPassword.length() - 1);
//    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = StaticVariable.getServerIp() + StaticVariable.TOMCAT_STATIC_PATH + faceUrl;
    }
}
