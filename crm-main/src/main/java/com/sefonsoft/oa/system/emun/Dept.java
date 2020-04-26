package com.sefonsoft.oa.system.emun;

/**
 * 领导部门枚举
 * @author Aron
 */
public enum Dept {
    SF("BM0001","四方伟业"),
    DS("BM0002","董事会"),
    ZJ("BM0005","总经理"),
    XS("BM0006","销售管理部");




    private String code;
    private String message;

    Dept(String code, String message) {
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