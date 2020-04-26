package com.sefonsoft.oa.entity.bizopports;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by liwenyi
 * Date: 2020/2/20
 * Desc: (biz_opports)实体类
 */
@ApiModel(value = "商机实体类")
@Data
public class BizOpports implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Long id;

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
    private String deptName;

    /**
     * 客户编号,索引，关联customer_info表customer_id
     */
    @ApiModelProperty(value="客户编号,索引，关联customer_info表customer_id")
    private String customerId;
    /**
     * 客户名称
     */
    @ApiModelProperty(value="客户名称")
    private String customerName;
    /**
     * 联系人ID
     */
    @ApiModelProperty(value="联系人ID")
    private Long contactId;

    /**
     * 联系人姓名
     */
    @ApiModelProperty(value="联系人姓名")
    private String contactName;
    /**
     * 联系人职务
     */
    @ApiModelProperty(value="联系人职务")
    private String contactJob;
    /**
     * 联系人电话
     */
    @ApiModelProperty(value="联系人电话")
    private String contactTelphone;
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
    /**
     * 操作时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date modifiedTime;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

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
     * 推荐销售id
     */
    @ApiModelProperty(value="推荐销售id")
    private String referencesId;

    /**
     * 推荐销售名字
     */
    @ApiModelProperty(value="推荐销售名字")
    private String referencesName;

    /**
     * 状态：1 未立项，2 已立项，3立项审核中
     */
    @ApiModelProperty(value="状态：1 未立项，2 已立项，3立项审核中")
    private Integer state;

    /**
     * 商机状态名
     */
    @ApiModelProperty(value="商机状态名")
    private String stateName;

    /**
     * 是否查询属于个人的商机
     */
    @ApiModelProperty(value="是否查询属于个人的商机")
    private boolean isPersonal;

    /**
     * 是否查询未立项的商机
     */
    @ApiModelProperty(value="是否查询未立项的商机")
    private boolean isNotProject;

    /**
     * 当前用户的数据角色下的部门编号列表
     */
    @ApiModelProperty(value="当前用户的数据角色下的部门编号列表")
    private List<String> dataDeptIdList;

    /**
     * 查询范围 ：1 全部，2大区
     */
    @ApiModelProperty(value="查询范围 ：1 全部，2大区")
    private Integer scope;

    /**
     * 是否导入
     * 0非导入数据；1是导入数据
     */
    @ApiModelProperty(value="是否导入 ：0非导入数据；1是导入数据")
    private long importType;
}
