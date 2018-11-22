package org.bqf.common.consts;

public enum FallbackResultCode implements BaseEnum {
    SUCCESS("8000000000", "成功"),
    SYSTEM_ERROR("8000099999", "系统内部错误"),
    PARA_VALIDATE_ERROR("8000000008", "参数校验失败"),

    FALLBACK_CONFIG_INVALID("8300000001", "配置格式错误");
    
    private String code;

    private String msg;

    FallbackResultCode(final String code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
