package cn.gface.face.api.data;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户详细信息
 */
@Data
public class TBUserInfo implements Serializable {
    private int id;
    private String userName;
    //    private String password;
    private String role;
    private String name;
    private String phoneNumber;
    private String email;
    private int sex;
    private String profile;
    private String createTime;
    private String avatar;
    private String pUserName;
    private String serverUrl;
    private int pid;
    private int count;
    private int usedCount;

    public void setCreateTime(String createTime) {
        boolean contains = createTime.contains(".0");
        if (contains) {
            createTime = createTime.replace(".0", "");
        }
        this.createTime = createTime;
    }
}
