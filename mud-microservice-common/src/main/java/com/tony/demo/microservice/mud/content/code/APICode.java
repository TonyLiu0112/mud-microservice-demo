package com.tony.demo.microservice.mud.content.code;

/**
 * 手机接口返回码规范
 * <p>
 * Created by Tony on 14/11/2016.
 */
public enum APICode {
    SUCCESS("0000", "0", "成功"),
    FAILED("9998", "", "失败"),
    INVALID_CODE("0001", "40029", "无效的code"),
    INVALID_OPENID("0002", "40003", "无效的openid"),
    NULL_DATA("0003", "", "未找到数据"),
    JOINED("0004", "", "用户已参与"),
    UN_OPS("0005", "", "已到每次操作上限"),
    UN_REPETITION("0006", "", "不可重复操作"),
    UNICODE_NULL("0007", "", "用户唯一标识符uniqueCode为空."),
    AUTHENTICATE_ERROR("0009", "", "认证错误"),
    UNKNOW("9999", "", "未知异常");

    private String code;
    private String wechatCode;
    private String message;

    APICode(String code, String wechatCode, String message) {
        this.code = code;
        this.wechatCode = wechatCode;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getWechatCode() {
        return wechatCode;
    }

    public String getMessage() {
        return message;
    }

    public static APICode get(String errcode) {
        for (APICode code : APICode.values()) {
            if (code.getWechatCode().equals(errcode))
                return code;
        }
        return UNKNOW;
    }
}
