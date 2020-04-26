package com.sefonsoft.oa.entity.grade;

import lombok.Data;

import java.util.Date;

/**
 * @author ：PengYiWen
 * @date ：Created in 2019/11/11 10:08
 * @description：职系实体类
 * @modified By：
 */
@Data
public class GradeInfo {

    private Long id;
    private String gradeId;
    private String gradeName;
    private Date createTime;
    private String delFlag;
    private Date modifiedTime;

}
