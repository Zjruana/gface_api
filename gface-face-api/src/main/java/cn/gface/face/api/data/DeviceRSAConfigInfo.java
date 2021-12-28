package cn.gface.face.api.data;

import lombok.Data;

@Data
public class DeviceRSAConfigInfo {
    private String serverPublicKey;
    private int rsaFlag;
}
