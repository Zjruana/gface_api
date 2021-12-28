package cn.gface.face.api.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeviceUserRequestEntity {
    private List<User> data;
    private String deviceCode;
    private long lastUpdateTime;

    @Data
    public static class User {
        private int faceState;
        private String name;
        private String personId;
        private String validDateEnd;
        private String validDateStart;
    }
}