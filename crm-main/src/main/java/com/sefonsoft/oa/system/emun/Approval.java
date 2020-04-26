package com.sefonsoft.oa.system.emun;

/**
 * @author ：nipengcheng
 * @version : 1.0
 * @description：
 * @date ：2020/3/19
 */
public enum  Approval {
    NOT_APPROVED(1,"未审批"),
    PASS(2,"通过"),
    REJECT(3,"驳回");

    private int code;
    private String name;

    Approval(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
