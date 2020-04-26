package com.sefonsoft.oa.domain.project.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sefonsoft.oa.domain.common.AccessRule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("项目查询条件")
public class ProjectQueryListVO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 8824957121142761116L;

  @ApiModelProperty("工号")
  private String employeeId;

  @ApiModelProperty("项目名称")
  private String projectName;

  @ApiModelProperty("成功率范围")
  @Min(value = 0, message = "成功率范围不符合要求")
  private int rateFrom = 0;

  @ApiModelProperty("成功率范围")
  @Max(value = 100, message = "成功率范围不符合要求")
  private int rateTo = 100;

  /**
   * 审核状态,1未审核，2审核通过，0已驳回
   */
  @ApiModelProperty("审核状态数组,1未审核，2审核通过，0已驳回")
  private List<Integer> checkStatuses;

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

  @ApiModelProperty(value = "部门编号列表")
  private List<String> dataDeptIdList;

  @ApiModelProperty("是否重大项目,1是，2否(展示项)")
  private Integer hasImportant;

  @Min(value = 1)
  private int page = 1;

  @Max(value = 1000)
  private int rows = 10;

  @ApiModelProperty(hidden = true)
  @JsonIgnore
  private AccessRule accessRule;
  
  public static final int SCOPE_ALL = 1;
  public static final int SCOPE_DEPT = 2;
  public static final int SCOPE_ME = 3;
  
  @ApiModelProperty("范围  1 所有， 2 本部门")
  private int scope = SCOPE_ALL;

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
   * @return the projectName
   */
  public String getProjectName() {
    return projectName;
  }

  /**
   * @param projectName the projectName to set
   */
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  /**
   * @return the rateFrom
   */
  public int getRateFrom() {
    return rateFrom;
  }

  /**
   * @param rateFrom the rateFrom to set
   */
  public void setRateFrom(int rateFrom) {
    this.rateFrom = rateFrom;
  }

  /**
   * @return the rateTo
   */
  public int getRateTo() {
    return rateTo;
  }

  /**
   * @param rateTo the rateTo to set
   */
  public void setRateTo(int rateTo) {
    this.rateTo = rateTo;
  }

  /**
   * @return the checkStatuses
   */
  public List<Integer> getCheckStatuses() {
    return checkStatuses;
  }

  /**
   * @param checkStatuses the checkStatuses to set
   */
  public void setCheckStatuses(List<Integer> checkStatuses) {
    this.checkStatuses = checkStatuses;
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

  /**
   * @return the hasImportant
   */
  public Integer getHasImportant() {
    return hasImportant;
  }

  /**
   * @param hasImportant the hasImportant to set
   */
  public void setHasImportant(Integer hasImportant) {
    this.hasImportant = hasImportant;
  }

  /**
   * @return the page
   */
  public int getPage() {
    return page == 0 ? 0 : page - 1;
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
   * @return the scope
   */
  public int getScope() {
    return scope;
  }

  /**
   * @param scope the scope to set
   */
  public void setScope(int scope) {
    this.scope = scope;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((checkStatuses == null) ? 0 : checkStatuses.hashCode());
    result = prime * result + ((dataDeptIdList == null) ? 0 : dataDeptIdList.hashCode());
    result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
    result = prime * result + ((halfYear == null) ? 0 : halfYear.hashCode());
    result = prime * result + ((hasImportant == null) ? 0 : hasImportant.hashCode());
    result = prime * result + ((month == null) ? 0 : month.hashCode());
    result = prime * result + page;
    result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
    result = prime * result + ((quarter == null) ? 0 : quarter.hashCode());
    result = prime * result + rateFrom;
    result = prime * result + rateTo;
    result = prime * result + rows;
    result = prime * result + ((year == null) ? 0 : year.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ProjectQueryListVO other = (ProjectQueryListVO) obj;
    if (checkStatuses == null) {
      if (other.checkStatuses != null) {
        return false;
      }
    } else if (!checkStatuses.equals(other.checkStatuses)) {
      return false;
    }
    if (dataDeptIdList == null) {
      if (other.dataDeptIdList != null) {
        return false;
      }
    } else if (!dataDeptIdList.equals(other.dataDeptIdList)) {
      return false;
    }
    if (employeeId == null) {
      if (other.employeeId != null) {
        return false;
      }
    } else if (!employeeId.equals(other.employeeId)) {
      return false;
    }
    if (halfYear == null) {
      if (other.halfYear != null) {
        return false;
      }
    } else if (!halfYear.equals(other.halfYear)) {
      return false;
    }
    if (hasImportant == null) {
      if (other.hasImportant != null) {
        return false;
      }
    } else if (!hasImportant.equals(other.hasImportant)) {
      return false;
    }
    if (month == null) {
      if (other.month != null) {
        return false;
      }
    } else if (!month.equals(other.month)) {
      return false;
    }
    if (page != other.page) {
      return false;
    }
    if (projectName == null) {
      if (other.projectName != null) {
        return false;
      }
    } else if (!projectName.equals(other.projectName)) {
      return false;
    }
    if (quarter == null) {
      if (other.quarter != null) {
        return false;
      }
    } else if (!quarter.equals(other.quarter)) {
      return false;
    }
    if (rateFrom != other.rateFrom) {
      return false;
    }
    if (rateTo != other.rateTo) {
      return false;
    }
    if (rows != other.rows) {
      return false;
    }
    if (year == null) {
      if (other.year != null) {
        return false;
      }
    } else if (!year.equals(other.year)) {
      return false;
    }
    return true;
  }

}
