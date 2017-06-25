package com.tony.demo.microservice.mud.common.results;

/**
 * 差错场景响应构建器
 * <p>
 * Created by Tony on 12/06/2017.
 */
public class ResultBuild {

    public static Results failed(String errorMsg) {
        return new Results("9999", false, errorMsg);
    }

    public static Results failed(Exception e) {
        return new Results("9999", false, e.getMessage());
    }

    public static Results success() {
        return success("处理成功");
    }

    public static Results success(Object result) {
        return success("处理成功", result);
    }

    public static Results success(String message) {
        return success(message, null);
    }

    public static Results success(String message, Object result) {
        return new Results("0000", true, message, result);
    }

}
