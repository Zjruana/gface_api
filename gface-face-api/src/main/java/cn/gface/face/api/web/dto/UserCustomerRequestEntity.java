package cn.gface.face.api.web.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserCustomerRequestEntity implements Serializable {

    //    private String id;
    private String userName;
    private String password;
    //    private String role;
    private String name;
    private String phoneNumber;
    private String email;
    private int sex;
    private String profile;
    private String createTime;
    private String avatar;
    private String pUserName;
    private int page;
    private int limit;
    private int count;
}
