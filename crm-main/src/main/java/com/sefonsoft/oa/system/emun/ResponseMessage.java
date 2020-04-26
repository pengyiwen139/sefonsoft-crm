package com.sefonsoft.oa.system.emun;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.*;

/**
 * 返回状态
 */
public enum ResponseMessage {
    PKEY_ERROR(100,ERROR_PKEY),
    PARAM_ERROR(101,ERROR_PARAM),
    NO_PARAM_ERROR(102, NO_PARAM),
    NO_RESPONSE_ERROR(103, NO_RESPONSE),
    MIN_NUM_ERROR(104, MIN_PARAM),
    MAX_NUM_ERROR(105, MAX_PARAM),
    NO_DATA_PERMISSION_ERROR(106, NO_DATA_PERMISSION),

    INSERT_ERROR(151, ERROR_INSERT),
    UPDATE_ERROR(152, ERROR_UPDATE),
    QUERY_ERROR(153,ERROR_QUERY),
    DELETE_ERROR(154,ERROR_DELETE),
    DELETE_RELATED_ERROR(155,ERROR_RELATED_DELETE),
    DELETEE_ERROR(156,ERROR_DELETEED),
    LOGIN_STATUS_ERROR(160, ERROR_LOGIN_STATUS),
    SIMILAR_PROJECT_WARN(161, WARN_SIMILAR_PROJECT),


    UNIQUE_DEPTID_ERROR(201,DEPTID_ERROR),
    UNIQUE_DEPTNAME_ERROR(202,DEPTNAME_ERROR),
    DELETE_DEPT_ERROR(203,DEPT_ERROR_DELETE),
    UNIQUE_EMPLOYEEID_ERROR(204,EMPLOYEEID_ERROR),
    DELETE_EMPEXIT_ERROR(205,DELETE_ERROR_EMPEXIT);



    private int code;
    private String message;

    ResponseMessage(int code, String message) {
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