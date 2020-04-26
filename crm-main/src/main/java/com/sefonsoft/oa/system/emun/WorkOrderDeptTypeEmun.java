package com.sefonsoft.oa.system.emun;

/**
 * 派工单部门关联配置类型
 * @author Aron
 */
public enum WorkOrderDeptTypeEmun {
    BF(1,"售前派工单"),
    AF(2,"交付派工单");

    private int code;
    private String message;

    WorkOrderDeptTypeEmun(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}