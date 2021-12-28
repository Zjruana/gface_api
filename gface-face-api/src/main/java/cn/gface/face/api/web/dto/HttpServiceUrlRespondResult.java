package cn.gface.face.api.web.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gan on 2021/1/17.
 * describe：
 */
@NoArgsConstructor
@Data
public class HttpServiceUrlRespondResult {

    /**
     * data : {"deviceCod":"D904DC6069B9"}
     * code : 200
     * message : 设置服务器地址成功http://192.168.0.14:8080/api/v1/
     */

    @JsonProperty("data")
    private DataDTO data;
    @JsonProperty("code")
    private int code;
    @JsonProperty("message")
    private String message;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        /**
         * deviceCode : D904DC6069B9
         */

        @JsonProperty("deviceCode")
        private String deviceCode;
    }
}
