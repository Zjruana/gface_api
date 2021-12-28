package cn.gface.face.api.web.dto;

import lombok.Data;

@Data
public class IdCardInfo {
    private String sex;
    private String name;
    private String birth;
    private String nation;
    private String address;
    private String country;
    private String idCardNo;
    private String validityTime;
    private String idCardPicBase64;
    private String issuingAuthority;
}
