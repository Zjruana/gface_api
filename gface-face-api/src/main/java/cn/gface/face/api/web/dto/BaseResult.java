package cn.gface.face.api.web.dto;

import java.io.Serializable;

/**
 * <p>Titile: BaseResult </p>
 * <p>Description: 自定义返回结果 </p>
 *
 * @author ganzhiyong
 * @version 1.0.0
 * @date 2019/11/15 18:33
 */
public class BaseResult implements Serializable {
    public static final int STATUS_SUCCESS = 200;
    public static final int STATUS_FAIL = 500;
    private int status;
    private String message;
    private Object data;

    public static BaseResult success() {
        return createBaseResult(STATUS_SUCCESS, "成功", null);
    }

    public static BaseResult success(String message) {
        return createBaseResult(STATUS_SUCCESS, message, null);
    }

    public static BaseResult success(String message, Object data) {
        return createBaseResult(STATUS_SUCCESS, message, data);
    }

    public static BaseResult fail() {
        return createBaseResult(STATUS_FAIL, "失败", null);
    }

    public static BaseResult fail(String message) {
        return createBaseResult(STATUS_FAIL, message, null);
    }

    public static BaseResult fail(int status, String message) {
        return createBaseResult(status, message, null);
    }

    public static BaseResult createBaseResult(int status, String message, Object data) {
        BaseResult baseResult = new BaseResult();
        baseResult.setStatus(status);
        baseResult.setMessage(message);
        baseResult.setData(data);
        return baseResult;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
