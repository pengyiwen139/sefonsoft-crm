package com.sefonsoft.oa.system.emun;

/**
 * 立项审核状态枚举
 * @author Aron
 */
public enum CheckStatus {
    YJJ(-1,"立项专员已拒绝"),
    YBB(0,"大区总已驳回"),
    WSP(1,"大区总未审批"),
    DQZYSP(2,"大区总已审批"),
    YTG(3,"立项专员已审批");




    private int code;
    private String name;

    CheckStatus(int code, String name) {
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