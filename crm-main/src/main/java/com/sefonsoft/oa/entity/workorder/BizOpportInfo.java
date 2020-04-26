package com.sefonsoft.oa.entity.workorder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 创建工单时需要的商机的基本信息
 * @author ：nipengcheng
 * @version : 1.0
 * @description：
 * @date ：2020/3/2017:48
 */
@Data
@ApiModel("派工单商机基本信息")
public class BizOpportInfo {
    @ApiModelProperty("项目名称")
    private String projectName;
    @ApiModelProperty("联系人")
    private String contactName;
    @ApiModelProperty("联系人电话")
    private String contactTelphone;
    @ApiModelProperty("最终客户")
    private String customerName;
    @ApiModelProperty("立项编号")
    private String projectNumber;
    @ApiModelProperty("合作伙伴名称")
    private String partnerName;
}
