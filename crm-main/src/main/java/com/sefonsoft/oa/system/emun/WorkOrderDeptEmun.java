package com.sefonsoft.oa.system.emun;

/**
 * 派工单部门类型枚举
 * @author Aron
 */
public enum WorkOrderDeptEmun {
    AS_DEPT("BM0006","销售管理部"),
    BS_DEPT("BM0035","售前管理部");

    private String deptId;
    private String message;

    WorkOrderDeptEmun(String deptId, String message) {
        this.deptId = deptId;
        this.message = message;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getMessage() {
        return message;
    }

}