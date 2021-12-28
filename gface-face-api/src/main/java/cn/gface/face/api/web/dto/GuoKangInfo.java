package cn.gface.face.api.web.dto;

import lombok.Data;

@Data
public class GuoKangInfo {
    private int id;
    private int code;
    private String cardId;
    private int status; // 1绿码，2黄码，3红码
    private String cardName;
    private String overCity;
}
