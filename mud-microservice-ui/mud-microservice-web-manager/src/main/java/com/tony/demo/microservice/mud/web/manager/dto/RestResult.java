package com.tony.demo.microservice.mud.web.manager.dto;

/**
 * Created by Tony on 25/04/2017.
 */
public class RestResult {

    private Object results;

    private String code;

    private String msg;

    public Object getResults() {
        return results;
    }

    public void setResults(Object results) {
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
}
