package com.sefonsoft.oa.domain.bizopports;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by liwenyi
 * Date: 2020/3/24
 * Desc:
 */
@Data
public class BizOpportsExport {

    private  Long id;
    
    private Long contactId;
    
    /**
     * 客户名称
     */
    @ApiModelProperty(value="单位名称")
    private String customerName;

    /**
     * 企业性质名称
     */
    @ApiModelProperty(value="单位性质名称")
    private String entername;
    /**
     * 可手动输入，默认可选内容：政府、旅游、金融、交通、能源等
     */
    @ApiModelProperty(value="所属行业")
    private String industry;
    /**
     * 省市区
     */
    @ApiModelProperty(value="省市区")
    private String ssq;

    /**
     * 单位地址
     */
    @ApiModelProperty(value="单位地址")
    private String address;
    /**
     * 网址
     */
    @ApiModelProperty(value="网址")
    private String website;

    /**
     * 邮编
     */
    @ApiModelProperty(value="邮编")
    private String zipCode;

    /**
     * 电话
     */
    @ApiModelProperty(value="单位电话")
    private String customerTelephone;
    /**
     * 传真
     */
    @ApiModelProperty(value="传真")
    private String fax;


    /**
     * 联系人
     */
    @ApiModelProperty(value="联系人")
    private String contactName;

    /**
     * 性别，1：男，2：女
     */
    @ApiModelProperty(value="性别：1男，2女")
    private Integer sex;
    /**
     * 联系人所属部门
     */
    @ApiModelProperty(value="联系人所属部门")
    private String contactDeptName;

    /**
     * 职务
     */
    @ApiModelProperty("客户联系人职务")
    private String job;
    /**
     * 联系电话
     */
    @ApiModelProperty(value="联系人电话")
    private String contactTelephone;
    /**
     * 联系邮箱
     */
    @ApiModelProperty(value="联系邮箱")
    private String contactEmail;

    /**
     * 办公座机
     */
    @ApiModelProperty("办公座机")
    private String officePlane;
    /**
     * 毕业学校
     */
    @ApiModelProperty("毕业学校")
    private String university;
    /**
     * 专业
     */
    @ApiModelProperty("专业")
    private String major;
    /**
     * 家庭状况
     */
    @ApiModelProperty("家庭状况")
    private String familyInfo;
    /**
     * 生日
     */
    @ApiModelProperty("生日")
    private String birthday;
    /**
     * 其他
     */
    @ApiModelProperty("其他")
    private String other;

    /**
     * 商机id
     */
    @ApiModelProperty(value="商机id")
    private String bizId;

    /**
     * 商机名
     */
    @ApiModelProperty(value="商机名")
    private String name;

    /**
     * 创建人id
     */
    @ApiModelProperty(value="创建人id")
    private String createId;

    /**
     * 创建人名字
     */
    @ApiModelProperty(value="创建人名字")
    private String createName;

    /**
     * 相关大区
     */
    @ApiModelProperty(value="提交人大区")
    private String createDeptName;

    /**
     * 工号，关联sys_employee表employee_id
     */
    @ApiModelProperty(value="工号，关联sys_employee表employee_id")
    private String employeeId;

    /**
     * 相关销售
     */
    @ApiModelProperty(value="相关销售")
    private String employeeName;

    /**
     * 相关大区
     */
    @ApiModelProperty(value="相关大区")
    private String employeeDeptName;

    /**
     * 商机状态名
     */
    @ApiModelProperty(value="商机状态名")
    private String stateName;

    /**
     * 关键词
     */
    @ApiModelProperty(value="关键词")
    private String keywords;
    /**
     * 商机简述
     */
    @ApiModelProperty(value="商机简述")
    private String desc;
    
    
    
    public BizOpportsDTO toBizOpportsDTO() {
      BizOpportsDTO bto = new BizOpportsDTO();

      bto.setId(this.id);
      bto.setBizId(bizId);
      bto.setContactJob(this.job);
      bto.setContactName(this.contactName);
      bto.setCreateId(this.createId);
      bto.setCreateName(this.createName);
      bto.setContactTelphone(this.contactTelephone);
      bto.setDeptName(this.createDeptName);
      bto.setDesc(this.desc);
      bto.setEmployeeId(this.employeeId);
      bto.setEmployeeName(this.employeeName);
      bto.setKeywords(this.keywords);
      bto.setName(this.name);
      bto.setState(getState(this.stateName));
      bto.setStateName(this.stateName);
      return bto;
    }
    
    
    
    public static Integer getState(String name) {
      
      if (name == null) {
        return null;
      }
      
      if ("未立项".equals(name)) {
        return 1;
      }
      if ("已立项".equals(name)) {
        return 2;
      }
      if ("立项审核中".equals(name)) {
        return 3;
      }
       throw new IllegalArgumentException();
    }
}
