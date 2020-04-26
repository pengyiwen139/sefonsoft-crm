package com.sefonsoft.oa.domain.project;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：PengYiWen
 * @date ：Created in 2020/1/6 20:42
 * @description：导入联系人DTO
 * @modified By：
 */
@Data
@Accessors(chain = true)
public class ImportContactDTO implements Serializable {

    private Long id;

    private int importType;
    private String employeeId;
    private String customerId;
    private String contactName;
    private String contactDeptName;
    private String tel;
    private String operatorId;
    private Date lastDate;
}
