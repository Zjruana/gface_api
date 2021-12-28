package cn.gface.face.api.web.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class UserPersonnelRequestEntity implements Serializable {
    private String name;
    private String mobilePhone;
    private String interviewerName;
    private int personType;
    private String temp;
    private String beginTime;
    private String endTime;
    private String icCard;
    private String idCardNo;
    private int reasonVisit;
    private String deviceLocation;
    private int leaveStatus;
    private int status;
    private int sex;
    private int page;
    private int limit;
    private String sort;
    private String imageUrl;
    private String remark;
    private String deviceCode;
    private String deviceGroup;
    private String personId;
    private String openDoorPassword; // 开门密码
    private String jobNumber; // 工号

    public void setImageUrl(String imageUrl) {
        if (imageUrl == null) {
            this.imageUrl = imageUrl;
            return;
        }
        Pattern compile = Pattern.compile("(?<=/static).*");
        Matcher matcher = compile.matcher(imageUrl);
        String s = "";
        while (matcher.find()) {
            s = matcher.group();
        }
        this.imageUrl = s;
    }
}
