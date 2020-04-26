package com.sefonsoft.oa.domain.contract.vo;

import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.MIN_PARAM;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sefonsoft.oa.domain.common.AccessRule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同列表查询参数
 *
 */
@ApiModel("合同列表查询参数")
public class ContractListQueryVO  implements Serializable {

  private static final long serialVersionUID = -3885576463416358737L;

  /**
   * 关键字，模糊匹配
   */
  @ApiModelProperty("合同名称、合同编号")
  private String kw;
  
  
  /**
   * 销售人员
   */
  @ApiModelProperty("销售工号")
  private String employeeId;


  /**
   * 签订日期上界
   */
  @ApiModelProperty("签订日期上界")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
  private Date contractDateFrom;

  /**
   * 签订日期下界
   */
  @ApiModelProperty("签订日期下界")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
  private Date contractDateTo;

  /**
   * 年份
   */
  @ApiModelProperty("年份")
  private String year;
  /**
   * 上下半年
   */
  @ApiModelProperty("上下半年，上半年1，下半年2")
  private String halfYear;
  /**
   * 季度
   */
  @ApiModelProperty("季度，1一季度，2二季度，3三季度，4四季度")
  private String quarter;
  /**
   * 月份
   */
  @ApiModelProperty("月份，1一月，2二月......")
  private String month;
  
  
  @ApiModelProperty("部门")
  private List<String> dataDeptIdList;
  
  
  /**
   * 页数
   */
  @ApiModelProperty(value="页数",example="1")
  @Min(value = 0, message = "page" + MIN_PARAM + 0)
  private int page = 1;

  /**
   * 每页个数
   */
  @ApiModelProperty(value="每页个数",example="10")
  @Min(value = 0, message = "rows" + MIN_PARAM + 0)
  private int rows = 10;

  @ApiModelProperty(hidden = true)
  @JsonIgnore
  AccessRule accessRule;
  
  // getter setter


  
  
  /**
   * @return the contractDateFrom
   */
  public Date getContractDateFrom() {
    return contractDateFrom;
  }

  /**
   * @return the employeeId
   */
  public String getEmployeeId() {
    return employeeId;
  }

  /**
   * @param employeeId the employeeId to set
   */
  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  /**
   * @return the kw
   */
  public String getKw() {
    return kw;
  }

  /**
   * @param kw the kw to set
   */
  public void setKw(String kw) {
    this.kw = kw;
  }

  /**
   * @param contractDateFrom the contractDateFrom to set
   */
  public void setContractDateFrom(Date contractDateFrom) {
    this.contractDateFrom = contractDateFrom;
  }

  /**
   * @return the contractDateTo
   */
  public Date getContractDateTo() {
    return contractDateTo;
  }

  /**
   * @param contractDateTo the contractDateTo to set
   */
  public void setContractDateTo(Date contractDateTo) {
    this.contractDateTo = contractDateTo;
  }

  /**
   * @return the year
   */
  public String getYear() {
    return year;
  }

  /**
   * @param year the year to set
   */
  public void setYear(String year) {
    this.year = year;
  }

  /**
   * @return the halfYear
   */
  public String getHalfYear() {
    return halfYear;
  }

  /**
   * @param halfYear the halfYear to set
   */
  public void setHalfYear(String halfYear) {
    this.halfYear = halfYear;
  }

  /**
   * @return the quarter
   */
  public String getQuarter() {
    return quarter;
  }

  /**
   * @param quarter the quarter to set
   */
  public void setQuarter(String quarter) {
    this.quarter = quarter;
  }

  /**
   * @return the month
   */
  public String getMonth() {
    return month;
  }

  /**
   * @param month the month to set
   */
  public void setMonth(String month) {
    this.month = month;
  }

  /**
   * @return the page
   */
  public int getPage() {
    return page - 1;
  }

  /**
   * @param page the page to set
   */
  public void setPage(int page) {
    this.page = page;
  }

  /**
   * @return the rows
   */
  public int getRows() {
    return rows;
  }

  /**
   * @param rows the rows to set
   */
  public void setRows(int rows) {
    this.rows = rows;
  }

  /**
   * @return the accessRule
   */
  public AccessRule getAccessRule() {
    return accessRule;
  }

  /**
   * @param accessRule the accessRule to set
   */
  public void setAccessRule(AccessRule accessRule) {
    this.accessRule = accessRule;
  }

  /**
   * @return the dataDeptIdList
   */
  public List<String> getDataDeptIdList() {
    return dataDeptIdList;
  }

  /**
   * @param dataDeptIdList the dataDeptIdList to set
   */
  public void setDataDeptIdList(List<String> dataDeptIdList) {
    this.dataDeptIdList = dataDeptIdList;
  }
  
  
  
}
