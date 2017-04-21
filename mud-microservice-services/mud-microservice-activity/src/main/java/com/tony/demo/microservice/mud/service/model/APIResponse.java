package com.tony.demo.microservice.mud.service.model;

/**
 * Open API 响应对象
 * <p>
 * Created by Tony on 14/11/2016.
 */
public class APIResponse<T> {

    private String code;
    private String msg;
    private T results;

    public APIResponse() {
    }

    public APIResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public APIResponse(String code, String msg, T results) {
        this.code = code;
        this.msg = msg;
        this.results = results;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
