package cn.gface.face.api.web.dto;

import java.io.Serializable;

public class BaseResultDevice implements Serializable {
    public static final int STATUS_SUCCESS = 200;
    public static final int STATUS_FAIL = 500;
    private int resultCode;
    private String message;
    private Object data;

    public static BaseResultDevice success() {
        return createBaseResult(STATUS_SUCCESS, "成功", null);
    }

    public static BaseResultDevice success(String message) {
        return createBaseResult(STATUS_SUCCESS, message, null);
    }

    public static BaseResultDevice success(String message, Object data) {
        return createBaseResult(STATUS_SUCCESS, message, data);
    }

    public static BaseResultDevice fail() {
        return createBaseResult(STATUS_FAIL, "失败", null);
    }

    public static BaseResultDevice fail(String message) {
        return createBaseResult(STATUS_FAIL, message, null);
    }

    public static BaseResultDevice fail(int resultCode, String message) {
        return createBaseResult(resultCode, message, null);
    }

    public static BaseResultDevice createBaseResult(int resultCode, String message, Object data) {
        BaseResultDevice baseResult = new BaseResultDevice();
        baseResult.setResultCode(resultCode);
        baseResult.setMessage(message);
        baseResult.setData(data);
        return baseResult;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
