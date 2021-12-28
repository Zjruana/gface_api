package cn.gface.face.api.web.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterUserRequestEntity implements Serializable {
    private String username;
    private String password;
}
