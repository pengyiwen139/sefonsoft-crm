package com.sefonsoft.oa.system.annotation;


import java.lang.annotation.*;

/**
 * @author xielf
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OpLog {

  Module module();

  Type opType();

  /**
   * 模块
   */
  enum Module{

    /***/
    CUSTOMER("客户信息"),
    CUSTOMER_PROPERTIES("客户资源池"),
    PROJECT("项目"),
    BIZ_OPPORTS("商机"),
    CONTRACT("合同"),
    EXCPECT_CONTRACT("合同预测");

    private String moduleName;


    public String getModuleName() {
      return moduleName;
    }

    public void setModuleName(String moduleName) {
      this.moduleName = moduleName;
    }


    Module(String moduleName) {
      this.moduleName = moduleName;
    }
  }

  /**
   * 操作类型
   */
  enum Type{

    /**
     * 1 增
     * 2 删
     * 3 改
     * 4 导入
     * 5 导出
     *
     */
    ADD(1,"增加"),
    DELETE(2,"删除"),
    UPDATE(3,"修改"),
    IMPORT(4,"导入"),
    EXPORT(5,"导出");

    private Integer opType;
    private String opName;

    public Integer getOpType() {
      return opType;
    }

    public void setOpType(Integer opType) {
      this.opType = opType;
    }

    public String getOpName() {
      return opName;
    }

    public void setOpName(String opName) {
      this.opName = opName;
    }

    Type(Integer opType, String opName) {
      this.opType = opType;
      this.opName = opName;
    }
  }
}
