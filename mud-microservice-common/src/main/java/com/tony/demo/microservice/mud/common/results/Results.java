package com.tony.demo.microservice.mud.common.results;

/**
 * 通用响应对象
 * <p>
 * Created by Tony on 12/06/2017.
 */
public class Results {

    /**
     * 响应码
     */
    private String code;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 消息
     */
    private String message;

    /**
     * 结果
     */
    private Object results;

    public Results(String code, boolean success, String message) {
        this(code, success, message, null);
    }

    public Results(String code, boolean success, String message, Object results) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.results = results;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResults() {
        return results;
    }

    public void setResults(Object results) {
        this.results = results;
    }
}
