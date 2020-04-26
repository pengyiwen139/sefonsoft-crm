package com.sefonsoft.oa.domain.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *项目详情页展示的客户信息
 *
 * @author Aron
 * @since 2019-11-14 10:23:10
 */
@Data
@ApiModel(value ="项目详情页展示的客户信息")
public class CustomerInfoInProjectDTO {

    @ApiModelProperty(value="主键")
    private Long id;
    /**
    * 客户编号
    */
    @ApiModelProperty(value="客户编号,不能为空")
    private String customerId;
    /**
    * 客户名称
    */
    @ApiModelProperty(value="客户名称,不能为空")
    private String customerName;
    /**
    * 国家
    */
    @ApiModelProperty(value="国家")
    private String countryNum;
    /**
    * 省份
    */
    @ApiModelProperty(value="省份")
    private String provinceNum;
    /**
    * 市
    */
    @ApiModelProperty(value="城市")
    private String cityNum;
    /**
     * 区县
     */
    @ApiModelProperty(value="区县")
    private String district;
    /**
    * 可手动输入，默认可选内容：政府、旅游、金融、交通、能源等
    */
    @ApiModelProperty(value="所属行业")
    private String industry;
    /**
    * 关联enterprise_type表enter_id
    */
    @ApiModelProperty(value="企业性质")
    private Integer enterId;
    /**
    * 是否上市,1上市,2未上市
    */
    @ApiModelProperty(value="是否上市")
    private Integer isListed;
    /**
    * 公司网址
    */
    @ApiModelProperty(value="公司网站")
    private String website;
    /**
    * 详细地址
    */
    @ApiModelProperty(value="具体详细地址")
    private String address;
    /**
    * 邮编
    */
    @ApiModelProperty(value="邮编")
    private String zipCode;
    /**
    * 电话
    */
    @ApiModelProperty(value="电话")
    private String telephone;
    /**
    * 传真
    */
    @ApiModelProperty(value="传真")
    private String fax;
    /**
    * 联系人
    */
    @ApiModelProperty(value="客户联系人,不能为空")
    private String contacts;
    /**
    * 所属部门
    */
    @ApiModelProperty(value="客户联系人所属部门,不能为空")
    private String contactsDepart;
    /**
    * 联系电话
    */
    @ApiModelProperty(value="客户联系人所属电话")
    private String contactsTelephone;
    /**
    * 联系邮箱
    */
    @ApiModelProperty(value="客户联系邮箱")
    private String contactsEmail;
    /**
    * 操作员,关联sys_user表user_id
    */
    @ApiModelProperty(value="操作员,不能为空")
    private String operator;
    /**
    * 最后操作时间
    */
    @ApiModelProperty(value="最后操作时间")
    private Date lastTime;
    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 客户列表
     */
    @ApiModelProperty(value="客户列表")
    List<CustomerNameIdDTO> customerInfoList;
}