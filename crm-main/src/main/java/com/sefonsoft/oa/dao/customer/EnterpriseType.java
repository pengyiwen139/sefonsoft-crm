package com.sefonsoft.oa.dao.customer;

import java.io.Serializable;

/**
 * enterprise_type
 *
 * @author
 */
public class EnterpriseType implements Serializable {
  /**
   * 主键
   */
  private Byte enterId;

  /**
   * 性质名称
   * 1最终用户；2合作伙伴;3最终用户&合作伙伴
   */
  private String enterName;

  private static final long serialVersionUID = 1L;

  public Byte getEnterId() {
    return enterId;
  }

  public void setEnterId(Byte enterId) {
    this.enterId = enterId;
  }

  public String getEnterName() {
    return enterName;
  }

  public void setEnterName(String enterName) {
    this.enterName = enterName;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null) {
      return false;
    }
    if (getClass() != that.getClass()) {
      return false;
    }
    EnterpriseType other = (EnterpriseType) that;
    return (this.getEnterId() == null ? other.getEnterId() == null : this.getEnterId().equals(other.getEnterId()))
        && (this.getEnterName() == null ? other.getEnterName() == null : this.getEnterName().equals(other.getEnterName()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getEnterId() == null) ? 0 : getEnterId().hashCode());
    result = prime * result + ((getEnterName() == null) ? 0 : getEnterName().hashCode());
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" [");
    sb.append("Hash = ").append(hashCode());
    sb.append(", enterId=").append(enterId);
    sb.append(", enterName=").append(enterName);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }
}