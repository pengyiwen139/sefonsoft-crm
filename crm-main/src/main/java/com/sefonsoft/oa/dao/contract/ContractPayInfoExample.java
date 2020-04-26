package com.sefonsoft.oa.dao.contract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ContractPayInfoExample {
  protected String orderByClause;

  protected boolean distinct;

  protected List<Criteria> oredCriteria;

  public ContractPayInfoExample() {
    oredCriteria = new ArrayList<Criteria>();
  }

  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  public String getOrderByClause() {
    return orderByClause;
  }

  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  public boolean isDistinct() {
    return distinct;
  }

  public List<Criteria> getOredCriteria() {
    return oredCriteria;
  }

  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  public Criteria or() {
    Criteria criteria = createCriteriaInternal();
    oredCriteria.add(criteria);
    return criteria;
  }

  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  public void clear() {
    oredCriteria.clear();
    orderByClause = null;
    distinct = false;
  }

  protected abstract static class GeneratedCriteria {
    protected List<Criterion> criteria;

    protected GeneratedCriteria() {
      super();
      criteria = new ArrayList<Criterion>();
    }

    public boolean isValid() {
      return criteria.size() > 0;
    }

    public List<Criterion> getAllCriteria() {
      return criteria;
    }

    public List<Criterion> getCriteria() {
      return criteria;
    }

    protected void addCriterion(String condition) {
      if (condition == null) {
        throw new RuntimeException("Value for condition cannot be null");
      }
      criteria.add(new Criterion(condition));
    }

    protected void addCriterion(String condition, Object value, String property) {
      if (value == null) {
        throw new RuntimeException("Value for " + property + " cannot be null");
      }
      criteria.add(new Criterion(condition, value));
    }

    protected void addCriterion(String condition, Object value1, Object value2, String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      criteria.add(new Criterion(condition, value1, value2));
    }

    protected void addCriterionForJDBCDate(String condition, Date value, String property) {
      if (value == null) {
        throw new RuntimeException("Value for " + property + " cannot be null");
      }
      addCriterion(condition, new java.sql.Date(value.getTime()), property);
    }

    protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
      if (values == null || values.size() == 0) {
        throw new RuntimeException("Value list for " + property + " cannot be null or empty");
      }
      List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
      Iterator<Date> iter = values.iterator();
      while (iter.hasNext()) {
        dateList.add(new java.sql.Date(iter.next().getTime()));
      }
      addCriterion(condition, dateList, property);
    }

    protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
    }

    public Criteria andIdIsNull() {
      addCriterion("id is null");
      return (Criteria) this;
    }

    public Criteria andIdIsNotNull() {
      addCriterion("id is not null");
      return (Criteria) this;
    }

    public Criteria andIdEqualTo(Integer value) {
      addCriterion("id =", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdNotEqualTo(Integer value) {
      addCriterion("id <>", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdGreaterThan(Integer value) {
      addCriterion("id >", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdGreaterThanOrEqualTo(Integer value) {
      addCriterion("id >=", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdLessThan(Integer value) {
      addCriterion("id <", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdLessThanOrEqualTo(Integer value) {
      addCriterion("id <=", value, "id");
      return (Criteria) this;
    }

    public Criteria andIdIn(List<Integer> values) {
      addCriterion("id in", values, "id");
      return (Criteria) this;
    }

    public Criteria andIdNotIn(List<Integer> values) {
      addCriterion("id not in", values, "id");
      return (Criteria) this;
    }

    public Criteria andIdBetween(Integer value1, Integer value2) {
      addCriterion("id between", value1, value2, "id");
      return (Criteria) this;
    }

    public Criteria andIdNotBetween(Integer value1, Integer value2) {
      addCriterion("id not between", value1, value2, "id");
      return (Criteria) this;
    }

    public Criteria andContractIdIsNull() {
      addCriterion("contract_id is null");
      return (Criteria) this;
    }

    public Criteria andContractIdIsNotNull() {
      addCriterion("contract_id is not null");
      return (Criteria) this;
    }

    public Criteria andContractIdEqualTo(String value) {
      addCriterion("contract_id =", value, "contractId");
      return (Criteria) this;
    }

    public Criteria andContractIdNotEqualTo(String value) {
      addCriterion("contract_id <>", value, "contractId");
      return (Criteria) this;
    }

    public Criteria andContractIdGreaterThan(String value) {
      addCriterion("contract_id >", value, "contractId");
      return (Criteria) this;
    }

    public Criteria andContractIdGreaterThanOrEqualTo(String value) {
      addCriterion("contract_id >=", value, "contractId");
      return (Criteria) this;
    }

    public Criteria andContractIdLessThan(String value) {
      addCriterion("contract_id <", value, "contractId");
      return (Criteria) this;
    }

    public Criteria andContractIdLessThanOrEqualTo(String value) {
      addCriterion("contract_id <=", value, "contractId");
      return (Criteria) this;
    }

    public Criteria andContractIdLike(String value) {
      addCriterion("contract_id like", value, "contractId");
      return (Criteria) this;
    }

    public Criteria andContractIdNotLike(String value) {
      addCriterion("contract_id not like", value, "contractId");
      return (Criteria) this;
    }

    public Criteria andContractIdIn(List<String> values) {
      addCriterion("contract_id in", values, "contractId");
      return (Criteria) this;
    }

    public Criteria andContractIdNotIn(List<String> values) {
      addCriterion("contract_id not in", values, "contractId");
      return (Criteria) this;
    }

    public Criteria andContractIdBetween(String value1, String value2) {
      addCriterion("contract_id between", value1, value2, "contractId");
      return (Criteria) this;
    }

    public Criteria andContractIdNotBetween(String value1, String value2) {
      addCriterion("contract_id not between", value1, value2, "contractId");
      return (Criteria) this;
    }

    public Criteria andAmountIsNull() {
      addCriterion("amount is null");
      return (Criteria) this;
    }

    public Criteria andAmountIsNotNull() {
      addCriterion("amount is not null");
      return (Criteria) this;
    }

    public Criteria andAmountEqualTo(BigDecimal value) {
      addCriterion("amount =", value, "amount");
      return (Criteria) this;
    }

    public Criteria andAmountNotEqualTo(BigDecimal value) {
      addCriterion("amount <>", value, "amount");
      return (Criteria) this;
    }

    public Criteria andAmountGreaterThan(BigDecimal value) {
      addCriterion("amount >", value, "amount");
      return (Criteria) this;
    }

    public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
      addCriterion("amount >=", value, "amount");
      return (Criteria) this;
    }

    public Criteria andAmountLessThan(BigDecimal value) {
      addCriterion("amount <", value, "amount");
      return (Criteria) this;
    }

    public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
      addCriterion("amount <=", value, "amount");
      return (Criteria) this;
    }

    public Criteria andAmountIn(List<BigDecimal> values) {
      addCriterion("amount in", values, "amount");
      return (Criteria) this;
    }

    public Criteria andAmountNotIn(List<BigDecimal> values) {
      addCriterion("amount not in", values, "amount");
      return (Criteria) this;
    }

    public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("amount between", value1, value2, "amount");
      return (Criteria) this;
    }

    public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
      addCriterion("amount not between", value1, value2, "amount");
      return (Criteria) this;
    }

    public Criteria andPayDateIsNull() {
      addCriterion("pay_date is null");
      return (Criteria) this;
    }

    public Criteria andPayDateIsNotNull() {
      addCriterion("pay_date is not null");
      return (Criteria) this;
    }

    public Criteria andPayDateEqualTo(Date value) {
      addCriterionForJDBCDate("pay_date =", value, "payDate");
      return (Criteria) this;
    }

    public Criteria andPayDateNotEqualTo(Date value) {
      addCriterionForJDBCDate("pay_date <>", value, "payDate");
      return (Criteria) this;
    }

    public Criteria andPayDateGreaterThan(Date value) {
      addCriterionForJDBCDate("pay_date >", value, "payDate");
      return (Criteria) this;
    }

    public Criteria andPayDateGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("pay_date >=", value, "payDate");
      return (Criteria) this;
    }

    public Criteria andPayDateLessThan(Date value) {
      addCriterionForJDBCDate("pay_date <", value, "payDate");
      return (Criteria) this;
    }

    public Criteria andPayDateLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("pay_date <=", value, "payDate");
      return (Criteria) this;
    }

    public Criteria andPayDateIn(List<Date> values) {
      addCriterionForJDBCDate("pay_date in", values, "payDate");
      return (Criteria) this;
    }

    public Criteria andPayDateNotIn(List<Date> values) {
      addCriterionForJDBCDate("pay_date not in", values, "payDate");
      return (Criteria) this;
    }

    public Criteria andPayDateBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("pay_date between", value1, value2, "payDate");
      return (Criteria) this;
    }

    public Criteria andPayDateNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("pay_date not between", value1, value2, "payDate");
      return (Criteria) this;
    }

    public Criteria andCreateTimeIsNull() {
      addCriterion("create_time is null");
      return (Criteria) this;
    }

    public Criteria andCreateTimeIsNotNull() {
      addCriterion("create_time is not null");
      return (Criteria) this;
    }

    public Criteria andCreateTimeEqualTo(Date value) {
      addCriterion("create_time =", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeNotEqualTo(Date value) {
      addCriterion("create_time <>", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeGreaterThan(Date value) {
      addCriterion("create_time >", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
      addCriterion("create_time >=", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeLessThan(Date value) {
      addCriterion("create_time <", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
      addCriterion("create_time <=", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeIn(List<Date> values) {
      addCriterion("create_time in", values, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeNotIn(List<Date> values) {
      addCriterion("create_time not in", values, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeBetween(Date value1, Date value2) {
      addCriterion("create_time between", value1, value2, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
      addCriterion("create_time not between", value1, value2, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreatorIsNull() {
      addCriterion("creator is null");
      return (Criteria) this;
    }

    public Criteria andCreatorIsNotNull() {
      addCriterion("creator is not null");
      return (Criteria) this;
    }

    public Criteria andCreatorEqualTo(String value) {
      addCriterion("creator =", value, "creator");
      return (Criteria) this;
    }

    public Criteria andCreatorNotEqualTo(String value) {
      addCriterion("creator <>", value, "creator");
      return (Criteria) this;
    }

    public Criteria andCreatorGreaterThan(String value) {
      addCriterion("creator >", value, "creator");
      return (Criteria) this;
    }

    public Criteria andCreatorGreaterThanOrEqualTo(String value) {
      addCriterion("creator >=", value, "creator");
      return (Criteria) this;
    }

    public Criteria andCreatorLessThan(String value) {
      addCriterion("creator <", value, "creator");
      return (Criteria) this;
    }

    public Criteria andCreatorLessThanOrEqualTo(String value) {
      addCriterion("creator <=", value, "creator");
      return (Criteria) this;
    }

    public Criteria andCreatorLike(String value) {
      addCriterion("creator like", value, "creator");
      return (Criteria) this;
    }

    public Criteria andCreatorNotLike(String value) {
      addCriterion("creator not like", value, "creator");
      return (Criteria) this;
    }

    public Criteria andCreatorIn(List<String> values) {
      addCriterion("creator in", values, "creator");
      return (Criteria) this;
    }

    public Criteria andCreatorNotIn(List<String> values) {
      addCriterion("creator not in", values, "creator");
      return (Criteria) this;
    }

    public Criteria andCreatorBetween(String value1, String value2) {
      addCriterion("creator between", value1, value2, "creator");
      return (Criteria) this;
    }

    public Criteria andCreatorNotBetween(String value1, String value2) {
      addCriterion("creator not between", value1, value2, "creator");
      return (Criteria) this;
    }

    public Criteria andCreatorNameIsNull() {
      addCriterion("creator_name is null");
      return (Criteria) this;
    }

    public Criteria andCreatorNameIsNotNull() {
      addCriterion("creator_name is not null");
      return (Criteria) this;
    }

    public Criteria andCreatorNameEqualTo(String value) {
      addCriterion("creator_name =", value, "creatorName");
      return (Criteria) this;
    }

    public Criteria andCreatorNameNotEqualTo(String value) {
      addCriterion("creator_name <>", value, "creatorName");
      return (Criteria) this;
    }

    public Criteria andCreatorNameGreaterThan(String value) {
      addCriterion("creator_name >", value, "creatorName");
      return (Criteria) this;
    }

    public Criteria andCreatorNameGreaterThanOrEqualTo(String value) {
      addCriterion("creator_name >=", value, "creatorName");
      return (Criteria) this;
    }

    public Criteria andCreatorNameLessThan(String value) {
      addCriterion("creator_name <", value, "creatorName");
      return (Criteria) this;
    }

    public Criteria andCreatorNameLessThanOrEqualTo(String value) {
      addCriterion("creator_name <=", value, "creatorName");
      return (Criteria) this;
    }

    public Criteria andCreatorNameLike(String value) {
      addCriterion("creator_name like", value, "creatorName");
      return (Criteria) this;
    }

    public Criteria andCreatorNameNotLike(String value) {
      addCriterion("creator_name not like", value, "creatorName");
      return (Criteria) this;
    }

    public Criteria andCreatorNameIn(List<String> values) {
      addCriterion("creator_name in", values, "creatorName");
      return (Criteria) this;
    }

    public Criteria andCreatorNameNotIn(List<String> values) {
      addCriterion("creator_name not in", values, "creatorName");
      return (Criteria) this;
    }

    public Criteria andCreatorNameBetween(String value1, String value2) {
      addCriterion("creator_name between", value1, value2, "creatorName");
      return (Criteria) this;
    }

    public Criteria andCreatorNameNotBetween(String value1, String value2) {
      addCriterion("creator_name not between", value1, value2, "creatorName");
      return (Criteria) this;
    }

    public Criteria andOperatorIsNull() {
      addCriterion("`operator` is null");
      return (Criteria) this;
    }

    public Criteria andOperatorIsNotNull() {
      addCriterion("`operator` is not null");
      return (Criteria) this;
    }

    public Criteria andOperatorEqualTo(String value) {
      addCriterion("`operator` =", value, "operator");
      return (Criteria) this;
    }

    public Criteria andOperatorNotEqualTo(String value) {
      addCriterion("`operator` <>", value, "operator");
      return (Criteria) this;
    }

    public Criteria andOperatorGreaterThan(String value) {
      addCriterion("`operator` >", value, "operator");
      return (Criteria) this;
    }

    public Criteria andOperatorGreaterThanOrEqualTo(String value) {
      addCriterion("`operator` >=", value, "operator");
      return (Criteria) this;
    }

    public Criteria andOperatorLessThan(String value) {
      addCriterion("`operator` <", value, "operator");
      return (Criteria) this;
    }

    public Criteria andOperatorLessThanOrEqualTo(String value) {
      addCriterion("`operator` <=", value, "operator");
      return (Criteria) this;
    }

    public Criteria andOperatorLike(String value) {
      addCriterion("`operator` like", value, "operator");
      return (Criteria) this;
    }

    public Criteria andOperatorNotLike(String value) {
      addCriterion("`operator` not like", value, "operator");
      return (Criteria) this;
    }

    public Criteria andOperatorIn(List<String> values) {
      addCriterion("`operator` in", values, "operator");
      return (Criteria) this;
    }

    public Criteria andOperatorNotIn(List<String> values) {
      addCriterion("`operator` not in", values, "operator");
      return (Criteria) this;
    }

    public Criteria andOperatorBetween(String value1, String value2) {
      addCriterion("`operator` between", value1, value2, "operator");
      return (Criteria) this;
    }

    public Criteria andOperatorNotBetween(String value1, String value2) {
      addCriterion("`operator` not between", value1, value2, "operator");
      return (Criteria) this;
    }

    public Criteria andOperatorNameIsNull() {
      addCriterion("operator_name is null");
      return (Criteria) this;
    }

    public Criteria andOperatorNameIsNotNull() {
      addCriterion("operator_name is not null");
      return (Criteria) this;
    }

    public Criteria andOperatorNameEqualTo(String value) {
      addCriterion("operator_name =", value, "operatorName");
      return (Criteria) this;
    }

    public Criteria andOperatorNameNotEqualTo(String value) {
      addCriterion("operator_name <>", value, "operatorName");
      return (Criteria) this;
    }

    public Criteria andOperatorNameGreaterThan(String value) {
      addCriterion("operator_name >", value, "operatorName");
      return (Criteria) this;
    }

    public Criteria andOperatorNameGreaterThanOrEqualTo(String value) {
      addCriterion("operator_name >=", value, "operatorName");
      return (Criteria) this;
    }

    public Criteria andOperatorNameLessThan(String value) {
      addCriterion("operator_name <", value, "operatorName");
      return (Criteria) this;
    }

    public Criteria andOperatorNameLessThanOrEqualTo(String value) {
      addCriterion("operator_name <=", value, "operatorName");
      return (Criteria) this;
    }

    public Criteria andOperatorNameLike(String value) {
      addCriterion("operator_name like", value, "operatorName");
      return (Criteria) this;
    }

    public Criteria andOperatorNameNotLike(String value) {
      addCriterion("operator_name not like", value, "operatorName");
      return (Criteria) this;
    }

    public Criteria andOperatorNameIn(List<String> values) {
      addCriterion("operator_name in", values, "operatorName");
      return (Criteria) this;
    }

    public Criteria andOperatorNameNotIn(List<String> values) {
      addCriterion("operator_name not in", values, "operatorName");
      return (Criteria) this;
    }

    public Criteria andOperatorNameBetween(String value1, String value2) {
      addCriterion("operator_name between", value1, value2, "operatorName");
      return (Criteria) this;
    }

    public Criteria andOperatorNameNotBetween(String value1, String value2) {
      addCriterion("operator_name not between", value1, value2, "operatorName");
      return (Criteria) this;
    }

    public Criteria andLastTimeIsNull() {
      addCriterion("last_time is null");
      return (Criteria) this;
    }

    public Criteria andLastTimeIsNotNull() {
      addCriterion("last_time is not null");
      return (Criteria) this;
    }

    public Criteria andLastTimeEqualTo(Date value) {
      addCriterion("last_time =", value, "lastTime");
      return (Criteria) this;
    }

    public Criteria andLastTimeNotEqualTo(Date value) {
      addCriterion("last_time <>", value, "lastTime");
      return (Criteria) this;
    }

    public Criteria andLastTimeGreaterThan(Date value) {
      addCriterion("last_time >", value, "lastTime");
      return (Criteria) this;
    }

    public Criteria andLastTimeGreaterThanOrEqualTo(Date value) {
      addCriterion("last_time >=", value, "lastTime");
      return (Criteria) this;
    }

    public Criteria andLastTimeLessThan(Date value) {
      addCriterion("last_time <", value, "lastTime");
      return (Criteria) this;
    }

    public Criteria andLastTimeLessThanOrEqualTo(Date value) {
      addCriterion("last_time <=", value, "lastTime");
      return (Criteria) this;
    }

    public Criteria andLastTimeIn(List<Date> values) {
      addCriterion("last_time in", values, "lastTime");
      return (Criteria) this;
    }

    public Criteria andLastTimeNotIn(List<Date> values) {
      addCriterion("last_time not in", values, "lastTime");
      return (Criteria) this;
    }

    public Criteria andLastTimeBetween(Date value1, Date value2) {
      addCriterion("last_time between", value1, value2, "lastTime");
      return (Criteria) this;
    }

    public Criteria andLastTimeNotBetween(Date value1, Date value2) {
      addCriterion("last_time not between", value1, value2, "lastTime");
      return (Criteria) this;
    }
  }

  /**
   *
   */
  public static class Criteria extends GeneratedCriteria {

    protected Criteria() {
      super();
    }
  }

  public static class Criterion {
    private String condition;

    private Object value;

    private Object secondValue;

    private boolean noValue;

    private boolean singleValue;

    private boolean betweenValue;

    private boolean listValue;

    private String typeHandler;

    public String getCondition() {
      return condition;
    }

    public Object getValue() {
      return value;
    }

    public Object getSecondValue() {
      return secondValue;
    }

    public boolean isNoValue() {
      return noValue;
    }

    public boolean isSingleValue() {
      return singleValue;
    }

    public boolean isBetweenValue() {
      return betweenValue;
    }

    public boolean isListValue() {
      return listValue;
    }

    public String getTypeHandler() {
      return typeHandler;
    }

    protected Criterion(String condition) {
      super();
      this.condition = condition;
      this.typeHandler = null;
      this.noValue = true;
    }

    protected Criterion(String condition, Object value, String typeHandler) {
      super();
      this.condition = condition;
      this.value = value;
      this.typeHandler = typeHandler;
      if (value instanceof List<?>) {
        this.listValue = true;
      } else {
        this.singleValue = true;
      }
    }

    protected Criterion(String condition, Object value) {
      this(condition, value, null);
    }

    protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
      super();
      this.condition = condition;
      this.value = value;
      this.secondValue = secondValue;
      this.typeHandler = typeHandler;
      this.betweenValue = true;
    }

    protected Criterion(String condition, Object value, Object secondValue) {
      this(condition, value, secondValue, null);
    }
  }
}