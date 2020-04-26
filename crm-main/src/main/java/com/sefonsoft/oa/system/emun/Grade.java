package com.sefonsoft.oa.system.emun;

/**
 * 职系枚举
 * @author Aron
 */
public enum Grade {
    XS("XS0001","销售职系"),
    LD("ZX0000","领导职系"),
    SQ("SQ0001","售前职系"),
    JF("JF0001","交付职系"),
    DX("DX0001","电销职系");



    private String code;
    private String message;

    Grade(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}