package com.sefonsoft.oa.domain.project;

import com.sefonsoft.oa.system.annotation.CellMapping;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (ProjectInfo)实体类
 *
 * @author PengYiWen
 * @since 2019-12-03 15:32:44
 */
@ApiModel("机会项目表实体类")
@Data
public class ProjectInfoImportDTO implements Serializable {

    private static final long serialVersionUID = -7685687515804237471L;

    @CellMapping(name="项目编号", length = 16)
    private String projectId;

    @CellMapping(name="省份", length = 16)
    private String province;

    @CellMapping(name="城市", length = 16)
    private String city;

    @CellMapping(name="行业", length = 32)
    private String industry;

    @CellMapping(name="立项时间")
    private String setupDate;

    @CellMapping(name="立项人")
    private String employeeName;

    @CellMapping(name="项目名称", length = 64)
    private String projectName;

    @CellMapping(name = "最终客户", length = 64)
    private String customerName;

    @CellMapping(name="预计签约时间")
    private String estimateTime;

    @CellMapping(name="签约概率", length = 4)
    private String estimateSuccess;


    @CellMapping(name = "客户部门", length = 32)
    private String contactDeptName;

    @CellMapping(name = "客户联系人", length = 20)
    private String contactName;

    @CellMapping(name = "客户联系方式", length = 13)
    private String contactTel;

    @CellMapping(name = "合作伙伴", length = 64)
    private String partnerName;

    @CellMapping(name = "合作伙伴部门", length = 32)
    private String partnerContactDeptName;

    @CellMapping(name = "合作伙伴联系人", length = 20)
    private String partnerContactName;

    @CellMapping(name = "合作伙伴联系方式", length = 13)
    private String partnerContactTel;

    @CellMapping(name = "预计合同金额(万元)")
    private String estimateMoney;

    @CellMapping(name = "UE数量", integerValueFlag = true)
    private String UEcount;

    @CellMapping(name = "UE单价", doubleValueFlag = true)
    private String UEprice;

    @CellMapping(name = "UE小计", doubleValueFlag = true)
    private String UEsumPrice;

    @CellMapping(name = "BE数量", integerValueFlag = true)
    private String BEcount;

    @CellMapping(name = "BE单价", doubleValueFlag = true)
    private String BEprice;

    @CellMapping(name = "BE小计", doubleValueFlag = true)
    private String BEsumPrice;

    @CellMapping(name = "ME数量", integerValueFlag = true)
    private String MEcount;

    @CellMapping(name = "ME单价", doubleValueFlag = true)
    private String MEprice;

    @CellMapping(name = "ME小计", doubleValueFlag = true)
    private String MEsumPrice;

    @CellMapping(name = "MINER数量", integerValueFlag = true)
    private String MINERcount;

    @CellMapping(name = "MINER单价", doubleValueFlag = true)
    private String MINERPrice;

    @CellMapping(name = "MINER小计", doubleValueFlag = true)
    private String MINERsumPrice;

    @CellMapping(name = "HADOOP数量", integerValueFlag = true)
    private String HADOOPcount;

    @CellMapping(name = "HADOOP单价", doubleValueFlag = true)
    private String HADOOPPrice;

    @CellMapping(name = "HADOOP小计", doubleValueFlag = true)
    private String HADOOPsumPrice;

    @CellMapping(name = "GOVERN数量", integerValueFlag = true)
    private String GOVERNcount;

    @CellMapping(name = "GOVERN单价", doubleValueFlag = true)
    private String GOVERNPrice;

    @CellMapping(name = "GOVERN小计", doubleValueFlag = true)
    private String GOVERNsumPrice;

    @CellMapping(name = "ETL数量", integerValueFlag = true)
    private String ETLcount;

    @CellMapping(name = "ETL单价", doubleValueFlag = true)
    private String ETLPrice;

    @CellMapping(name = "ETL小计", doubleValueFlag = true)
    private String ETLsumPrice;

    @CellMapping(name = "实施服务数量", integerValueFlag = true)
    private String doServiceCount;

    @CellMapping(name = "实施服务单价", doubleValueFlag = true)
    private String doServicePrice;

    @CellMapping(name = "实施服务小计", doubleValueFlag = true)
    private String doServiceSumPrice;

    @CellMapping(name = "维保服务数量", integerValueFlag = true)
    private String protectServiceCount;

    @CellMapping(name = "维保服务单价", doubleValueFlag = true)
    private String protectServicePrice;

    @CellMapping(name = "维保服务小计", doubleValueFlag = true)
    private String protectServiceSumPrice;

    @CellMapping(name = "其他产品数量", integerValueFlag = true)
    private String otherServiceCount;

    @CellMapping(name = "其他产品单价", doubleValueFlag = true)
    private String otherServicePrice;

    @CellMapping(name = "其他产品小计", doubleValueFlag = true)
    private String otherServiceSumPrice;

    @CellMapping(name = "BC数量", integerValueFlag = true)
    private String BCCount;

    @CellMapping(name = "BC单价", doubleValueFlag = true)
    private String BCPrice;

    @CellMapping(name = "BC小计", doubleValueFlag = true)
    private String BCSumPrice;

    @CellMapping(name = "客户项目阶段", length = 32)
    private String customerProjectPhase;

    @CellMapping(name = "业绩分割比例")
    private String resultCutShares;

    @CellMapping(name = "是否重大关键")
    private String hasImportant;

    @CellMapping(name = "所属区域")
    private String deptName;

    private String deptId;

    private String employeeId;

    /**
    最后操作时间 
    */
    private Date lastTime;
        
    /**
    创建时间 
    */
    private Date createTime;

}