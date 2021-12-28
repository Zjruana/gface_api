package cn.gface.face.api.web.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUserRequestEntity implements Serializable {

    private String email;
    private String profile;
    private String name;
    private String phoneNumber;
    private int sex;
    private String checkPassword;
    private String newPassword;
    private String oldPassword;
    private String serverUrl;
}
