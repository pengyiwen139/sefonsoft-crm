package com.sefonsoft.oa.domain.project.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;

import com.sefonsoft.oa.domain.bizopports.BizOpportsDTO;
import com.sefonsoft.oa.domain.bizopports.BizOpportsExport;
import com.sefonsoft.oa.domain.customer.vo.ExportCustomerInfoQueryVo;
import com.sefonsoft.oa.domain.project.ProductProjectRefInsertDTO;
import com.sefonsoft.oa.domain.project.ProjectAmountDevideRefInsertDTO;
import com.sefonsoft.oa.domain.project.ProjectInfoInsertDTO;
import com.sefonsoft.oa.domain.project.SalesStage;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.system.utils.StringUtils;

/**
 * 项目导入导出
 */
public class ProjectExcelVO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -589928362422093293L;
  
  
  public static final Map<String, ProductStaticInfo> PRDS = new LinkedHashMap<>();
  
  static {
    PRDS.put("ue", new ProductStaticInfo(1,  "ue", "UE"));
    PRDS.put("uegis", new ProductStaticInfo(2,  "uegis", "UE-GIS"));
    PRDS.put("uew3d", new ProductStaticInfo(3,  "uew3d", "UE-W3D"));
    PRDS.put("ueai", new ProductStaticInfo(4,  "ueai", "UE-AI"));
    PRDS.put("be", new ProductStaticInfo(5,  "be", "BE"));
    PRDS.put("me", new ProductStaticInfo(6,  "me", "ME"));
    PRDS.put("miner", new ProductStaticInfo(7,  "miner", "Miner"));
    PRDS.put("ds", new ProductStaticInfo(8,  "ds", "DS"));
    PRDS.put("hadoop", new ProductStaticInfo(10, "hadoop", "Hadoop"));
    PRDS.put("govern", new ProductStaticInfo(9,  "govern", "Govern"));
    PRDS.put("cdb", new ProductStaticInfo(11, "cdb", "CellularDB"));
    PRDS.put("ae", new ProductStaticInfo(12, "ae", "AE"));
    PRDS.put("etl", new ProductStaticInfo(13, "etl", "ETL"));
    PRDS.put("实施服务", new ProductStaticInfo(15, "实施服务", "实施服务"));
    PRDS.put("维保服务", new ProductStaticInfo(16, "维保服务", "维保服务"));
    PRDS.put("其他产品", new ProductStaticInfo(17, "其他产品", "其他"));
    PRDS.put("bc", new ProductStaticInfo(14, "bc", "BC"));
  };
  
  public static final List<String> PROJ_HEADERS = newProjHeaders();
  
  private static final List<String> newProjHeaders() {
    
    List<String> ret = new ArrayList<String>();
    
    for (String h : Arrays.asList(
        "项目编号",
        "项目名称",
        "省/市/区县",
        "行业",
        "立项时间",
        "销售负责人",
        "销售负责人工号",
        "销售负责人所属大区",
        "总投资金额（万元）",
        "客户项目阶段（万元）",
        "销售阶段",
        "预计签约时间",
        "签约概率（%）",
        "预计合同金额（万元）")) {
      ret.add(h);
    }
    // prds
    
    for(Entry<String, ?> prd : PRDS.entrySet()) {
      ret.add(prd.getKey() + "数量（个）");
      ret.add(prd.getKey() + "单价（万元）");
      ret.add(prd.getKey() + "小计（万元）");
    }
    
    for (String h : Arrays.asList(
        "业绩分割比例（%）",
        "是否终止跟进",
        "是否已签单",
        // "备注",
        "是否重大项目"
        )) {
      ret.add(h);
    }
    return Collections.unmodifiableList(ret);
  }
      
  public static final List<String> BIZ_HEADERS = Arrays.asList(
      "商机ID",
      "商机名称",
      "提交人",
      "提交人工号",
      "提交人所属大区",
      "商机状态",
      "销售商品",
      "商机概述");
  
  public static final List<String> CUST_HEADERS = Arrays.asList(
      "客户名称 ",
      "省/市/区县   ",
      "单位性质 ",
      "所属行业 ",
      "单位地址 ",
      "座机",
      "邮编",
      "网址",
      "传真",
      "姓名 ",
      "性别",
      "部门",
      "职务",
      "手机号码 ",
      "邮箱",
      "办公座机 ",
      "毕业学校 ",
      "专业   ",
      "生日(年/月/日)",
      "家庭状况 ",
      "其他");
  
  public static final List<String> PROJ_APPLY_HEADERS =  Arrays.asList(
      "项目总情况",
      "最终用户关系分析",
      "参与集成商情况分析",
      "竞争对手情况分析",
      "项目运作策略"
      );
  
  
  
  private String projectId;
  private String projectName;
  private String location;
  private String industry;
  private Date checkTime;
  private String employeeName;
  private String employeeId;
  private String deptName;
  private String customerProjectPhase;
  private String spstageName;
  private Date estimateTime;
  private Integer estimateSuccess;
  private BigDecimal estimateMoney;
  private BigDecimal totalInvestment;
  // 产品;
  private ProductExcelVO ue;
  
  private ProductExcelVO ueGis;
  
  private ProductExcelVO ueW3d;
  
  private ProductExcelVO ueAi;
  
  private ProductExcelVO be;
  
  private ProductExcelVO me;
  
  private ProductExcelVO miner;
  
  private ProductExcelVO ds;
  
  private ProductExcelVO govern;
  
  private ProductExcelVO hdp;
  
  private ProductExcelVO cdb;
  
  private ProductExcelVO ae;
  
  private ProductExcelVO etl;
  
  private ProductExcelVO bc;
  
  private ProductExcelVO deploy;
  
  private ProductExcelVO srv;
  
  private ProductExcelVO other;
  
  // 业务分割;
  private String dvi;
  private String projectSituation;
  private String hasImportant;
  
  // 商机;
  private BizOpportsExport biz;
  
  // 客户;
  private ExportCustomerInfoQueryVo cust;
  
  // 合作伙伴;
  private ExportCustomerInfoQueryVo patn;
  
  // 申请信息
  private ProjectExcelApplyVO apply;

  
  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  public Date getCheckTime() {
    return checkTime;
  }

  public void setCheckTime(Date checkTime) {
    this.checkTime = checkTime;
  }

  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(String employeeId) {
    this.employeeId = employeeId;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public String getCustomerProjectPhase() {
    return customerProjectPhase;
  }

  public void setCustomerProjectPhase(String customerProjectPhase) {
    this.customerProjectPhase = customerProjectPhase;
  }

  public String getSpstageName() {
    return spstageName;
  }

  public void setSpstageName(String spstageName) {
    this.spstageName = spstageName;
  }

  public Date getEstimateTime() {
    return estimateTime;
  }

  public void setEstimateTime(Date estimateTime) {
    this.estimateTime = estimateTime;
  }

  public Integer getEstimateSuccess() {
    return estimateSuccess;
  }

  public void setEstimateSuccess(Integer estimateSuccess) {
    this.estimateSuccess = estimateSuccess;
  }

  public BigDecimal getEstimateMoney() {
    return estimateMoney;
  }

  public void setEstimateMoney(BigDecimal estimateMoney) {
    this.estimateMoney = estimateMoney;
  }

  public ProductExcelVO getUe() {
    return ue;
  }

  public void setUe(ProductExcelVO ue) {
    this.ue = ue;
  }

  public ProductExcelVO getUeGis() {
    return ueGis;
  }

  public void setUeGis(ProductExcelVO ueGis) {
    this.ueGis = ueGis;
  }

  public ProductExcelVO getUeW3d() {
    return ueW3d;
  }

  public void setUeW3d(ProductExcelVO ueW3d) {
    this.ueW3d = ueW3d;
  }

  public ProductExcelVO getUeAi() {
    return ueAi;
  }

  public void setUeAi(ProductExcelVO ueAi) {
    this.ueAi = ueAi;
  }

  public ProductExcelVO getBe() {
    return be;
  }

  public void setBe(ProductExcelVO be) {
    this.be = be;
  }

  public ProductExcelVO getMe() {
    return me;
  }

  public void setMe(ProductExcelVO me) {
    this.me = me;
  }

  public ProductExcelVO getMiner() {
    return miner;
  }

  public void setMiner(ProductExcelVO miner) {
    this.miner = miner;
  }

  public ProductExcelVO getDs() {
    return ds;
  }

  public void setDs(ProductExcelVO ds) {
    this.ds = ds;
  }

  public ProductExcelVO getGovern() {
    return govern;
  }

  public void setGovern(ProductExcelVO govern) {
    this.govern = govern;
  }

  public ProductExcelVO getHdp() {
    return hdp;
  }

  public void setHdp(ProductExcelVO hdp) {
    this.hdp = hdp;
  }

  public ProductExcelVO getCdb() {
    return cdb;
  }

  public void setCdb(ProductExcelVO cdb) {
    this.cdb = cdb;
  }

  public ProductExcelVO getAe() {
    return ae;
  }

  public void setAe(ProductExcelVO ae) {
    this.ae = ae;
  }

  public ProductExcelVO getEtl() {
    return etl;
  }

  public void setEtl(ProductExcelVO etl) {
    this.etl = etl;
  }

  public ProductExcelVO getBc() {
    return bc;
  }

  public void setBc(ProductExcelVO bc) {
    this.bc = bc;
  }

  public ProductExcelVO getDeploy() {
    return deploy;
  }

  public void setDeploy(ProductExcelVO deploy) {
    this.deploy = deploy;
  }

  public ProductExcelVO getSrv() {
    return srv;
  }

  public void setSrv(ProductExcelVO srv) {
    this.srv = srv;
  }

  public ProductExcelVO getOther() {
    return other;
  }

  public void setOther(ProductExcelVO other) {
    this.other = other;
  }

  public String getDvi() {
    return dvi;
  }

  public void setDvi(String dvi) {
    this.dvi = dvi;
  }
  
  public String getProjectSituation() {
    return projectSituation;
  }

  public void setProjectSituation(String projectSituation) {
    this.projectSituation = projectSituation;
  }

  public String getHasImportant() {
    return hasImportant;
  }

  public void setHasImportant(String hasImportant) {
    this.hasImportant = hasImportant;
  }

  public BizOpportsExport getBiz() {
    return biz;
  }

  public void setBiz(BizOpportsExport biz) {
    this.biz = biz;
  }

  public ExportCustomerInfoQueryVo getCust() {
    return cust;
  }

  public void setCust(ExportCustomerInfoQueryVo cust) {
    this.cust = cust;
  }

  public ExportCustomerInfoQueryVo getPatn() {
    return patn;
  }

  public void setPatn(ExportCustomerInfoQueryVo patn) {
    this.patn = patn;
  }
  
  
  
  
  

  public ProjectExcelApplyVO getApply() {
    return apply;
  }

  public void setApply(ProjectExcelApplyVO apply) {
    this.apply = apply;
  }

  public BigDecimal getTotalInvestment() {
    return totalInvestment;
  }

  public void setTotalInvestment(BigDecimal totalInvestment) {
    this.totalInvestment = totalInvestment;
  }

  public ProjectInfoInsertDTO getProjectInfo() {
    ProjectInfoInsertDTO projInfo = new ProjectInfoInsertDTO();
    projInfo.setProjectId(projectId);
    projInfo.setProjectName(projectName);
    
    // 业绩分割
    String[] dviArr = dvi.split(";");
    List<ProjectAmountDevideRefInsertDTO> amountDevideList = Arrays.asList(dviArr).stream()
        .map(dviProp -> {
          String[] dvivvv = dviProp.split(",");
          String empId = dvivvv[1];
          Integer perDvi = Integer.parseInt(dvivvv[2]);
          ProjectAmountDevideRefInsertDTO padrvo = new ProjectAmountDevideRefInsertDTO(projectId, 
              empId, perDvi, 
              null, null, null, null);
          return padrvo;
        }).collect(Collectors.toList());
    projInfo.setProjectAmountDevideRefInsertDTOList(amountDevideList);
    
    projInfo.setCustomerId(cust.getCustomerId());
    projInfo.setCustomerProjectPhase(customerProjectPhase);
    projInfo.setContactId(cust.getContactId());
    projInfo.setEmployeeId(employeeId);
    projInfo.setTotalInvestment(totalInvestment);
    projInfo.setEstimateSuccess(estimateSuccess);
    projInfo.setEstimateTime(estimateTime);
    projInfo.setHasImportant("是".equals(hasImportant) ? 1 : 2);
    projInfo.setImportType(1);
    projInfo.setIndustry(industry);
    projInfo.setLocation(location);
    if (patn != null) {
      projInfo.setPartnerId(patn.getCustomerId());
      projInfo.setPartnerContactId(patn.getContactId());
    }
    
    // 产品
    List<ProductProjectRefInsertDTO> productRefInsertDTOLis = Arrays.asList(ue, ueGis, ueW3d, 
        ueAi, be, me, miner, ds, govern, hdp, cdb, ae, etl, bc, deploy, srv, other)
        .stream()
        .filter(p -> p != null)
        .filter(p -> p.getSaleCount() > 0)
        .map(evo -> {
          ProductProjectRefInsertDTO dto = new ProductProjectRefInsertDTO(evo.getId(), 
              evo.getSaleCount(), evo.getAmount(), null, null, null, null);
          return dto;
        }).collect(Collectors.toList());
    projInfo.setProductRefInsertDTOList(productRefInsertDTOLis);

    projInfo.setProjectSituation(projectSituation);
    projInfo.setProsId(2);
    projInfo.setSpstageId(SalesStage.valueOf(
        StringUtils.deleteChar(spstageName, '%')).code());
    
    
    this.estimateMoney = PRDS.keySet().stream().map(this::getPrd).filter(prd -> prd != null && prd.getSaleCount() > 0)
      .map(prd -> prd.getAmount().multiply(new BigDecimal(prd.getSaleCount())))
      .reduce(new BinaryOperator<BigDecimal>() {
        
        @Override
        public BigDecimal apply(BigDecimal t, BigDecimal u) {
          return t.add(u);
        }
      }).orElse(BigDecimal.ZERO);
    projInfo.setEstimateMoney(estimateMoney);
    
    projInfo.setImportType(1);
    
    // 申请信息
    projInfo.setProjectSituation(apply.getProjectSituation());
    projInfo.setUserRelationAnalysis(apply.getUserRelationAnalysis());
    projInfo.setIntegratorSituation(apply.getIntegratorSituation());
    projInfo.setCompeteOpponentAnalysis(apply.getCompeteOpponentSnalysis());
    projInfo.setProjectRunStrategy(apply.getProjectRunStrategy());
    
    return projInfo;
  }
  public CustomerInfo getCustomerInfo() {
    
    CustomerInfo customerInfo = cust.toCustomerInfo();
    
    customerInfo.setEnterId(getEnterId(customerInfo.getEntername()));
    customerInfo.setImportType(1);
    
    return customerInfo;
    
  }
  public CustomerInfo getPattnerInfo() {
    
    if (patn == null) {
      return null;
    }
    
    CustomerInfo customerInfo = patn.toCustomerInfo();// new CustomerInfo();
    
    customerInfo.setImportType(1);
    
    //    1   最终用户
    //    2   合作伙伴
    //    3   最终用户&合作伙伴
    customerInfo.setEnterId(getEnterId(customerInfo.getEntername()));
    
    return customerInfo;
    
  }
  public BizOpportsDTO getBizOpportInfo() {
    
    BizOpportsDTO bizInfo = biz.toBizOpportsDTO();
    
    bizInfo.setImportType(1);
    
    
    return bizInfo;
    
  }
  
  public Integer getEnterId(String name) {
    if (name == null) {
      return 1;
    }
    if (name.equals("最终用户")) {
     return 1; 
    }
    if (name.equals("合作伙伴")) {
      return 2;
    }
    else return 3;
  }

  
  
  public void setPrd(String name, ProductExcelVO prd) {
    
    switch (name.toLowerCase()) {
    case "ue":
      ue = prd;
      prd.setId(1);
      break;
    case "uegis":
      ueGis = prd;
      prd.setId(2);
      break;
    case "uew3d":
      ueW3d = prd;
      prd.setId(3);
      break;
    case "ueai":
      ueAi = prd;
      prd.setId(4);
      break;
    case "be":
      be = prd;
      prd.setId(5);
      break;
    case "me":
      me = prd;
      prd.setId(6);
      break;
    case "miner":
      miner = prd;
      prd.setId(7);
      break;
    case "ds":
      ds = prd;
      prd.setId(8);
      break;
    case "govern":
      govern = prd;
      prd.setId(9);
      break;
    case "hadoop":
      hdp = prd;
      prd.setId(10);
      break;
    case "cdb":
      cdb = prd;
      prd.setId(11);
      break;
    case "ae":
      ae = prd;
      prd.setId(12);
      break;
    case "etl":
      etl = prd;
      prd.setId(13);
      break;
    case "bc":
      bc = prd;
      prd.setId(14);
      break;
    case "实施服务":
      deploy = prd;
      prd.setId(15);
      break;
    case "维保服务":
      srv = prd;
      prd.setId(16);
      break;
    case "其他产品":
      other = prd;
      prd.setId(17);
      break;
    default:
      System.err.println("不支持的产品类型" + name);
      throw new IllegalArgumentException();
    }
  }
  
public ProductExcelVO getPrd(String name) {
    
    switch (name.toLowerCase()) {
    case "ue":
      return ue;
    case "uegis":
      return ueGis;
    case "uew3d":
      return ueW3d;
    case "ueai":
      return ueAi;
    case "be":
      return be;
    case "me":
      return me;
    case "miner":
      return miner;
    case "ds":
      return ds;
    case "govern":
      return govern;
    case "hadoop":
      return hdp;
    case "cdb":
      return cdb;
    case "ae":
      return ae;
    case "etl":
      return etl;
    case "bc":
      return bc;
    case "实施服务":
      return deploy;
    case "维保服务":
      return srv;
    case "其他产品":
      return other;
    default:
      System.err.println("不支持的产品类型" + name);
      throw new IllegalArgumentException();
    }
  }
  
  public List<String> getPrds() {
    return Arrays.asList("");
  }

  public void apply(Workbook workbook, Row row) {
    
     int i = 0;
     i= applyProjectInfo(workbook, row, 0);
     i= applyBizOpports(row, i, biz);
     i= applyCustomerInfo(row, i, cust);
     i= applyCustomerInfo(row, i, patn);
     i= applyApply(row, i, apply);
  }
  
  
  private int applyProjectInfo(Workbook workbook, Row row, int i) {
    
    DataFormat df = workbook.createDataFormat();
    
    short idx = df.getFormat("yyyy/MM/dd");
    
    CellStyle style = workbook.createCellStyle();
    style.setDataFormat(idx);
    
    // 项目编号
    row.createCell(i++).setCellValue(projectId);
    // 项目名称
    row.createCell(i++).setCellValue(projectName);
    // 国家/省/市
    row.createCell(i++).setCellValue(location);
    // 行业
    row.createCell(i++).setCellValue(industry);
    
    // 立项时间
    Cell checkTimeCell = row.createCell(i++);
    checkTimeCell.setCellValue(checkTime);
    checkTimeCell.setCellStyle(style);
    
    //  销售负责人
    row.createCell(i++).setCellValue(employeeName);
    
    // 销售负责人工号
    row.createCell(i++).setCellValue(employeeId);
    
    // 销售负责人所属大区
    row.createCell(i++).setCellValue(deptName);
    // 总投资金额
    row.createCell(i++).setCellValue(totalInvestment == null ? "0" : 
        totalInvestment.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
    
    // 客户项目状态
    row.createCell(i++).setCellValue(customerProjectPhase);
    // 销售状态
    row.createCell(i++).setCellValue(spstageName);
    
    // 预计签约时间
    Cell estimateTimeCell = row.createCell(i++);
    estimateTimeCell.setCellValue(estimateTime);
    estimateTimeCell.setCellStyle(style);
    
    // 签约概率
    row.createCell(i++).setCellValue(estimateSuccess);
    // 预计合同金额(万元)
    row.createCell(i++).setCellValue(estimateMoney == null ? null : estimateMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
    
    // 产品
    for ( String prda : PRDS.keySet()) {
      
      ProductExcelVO prd = getPrd(prda);
      
      if (prd != null && prd.getSaleAmount().compareTo(BigDecimal.ZERO) > 0) {
        // 数量
        row.createCell(i++).setCellValue(prd.getSaleCount());
        // 单价
        row.createCell(i++).setCellValue(prd.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
        // 小计
        row.createCell(i++).setCellValue(prd.getSaleAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
      } else {
        row.createCell(i++);
        row.createCell(i++);
        row.createCell(i++);
      }
    }
    
    // 业绩分割
    row.createCell(i++).setCellValue(dvi);
    
    // 是否终止跟进 TODO
    row.createCell(i++);
    
    // 是否已签单
    row.createCell(i++);
    
    // 备注
    // row.createCell(i++).setCellValue(projectSituation);
    
    // 是否重大关键
    row.createCell(i++).setCellValue(hasImportant);
    return i;
  }
  
  private int applyBizOpports(Row row, int i, BizOpportsExport biz) {
    
    // yes，测试环境中有可能为空
    if (biz == null) {
      biz = new BizOpportsExport();
    }
    
    // 商机ID
    row.createCell(i++).setCellValue(biz.getBizId());
    // 商机名称
    row.createCell(i++).setCellValue(biz.getName());
    // 提交人
    row.createCell(i++).setCellValue(biz.getEmployeeName());
    // 提交人工号
    row.createCell(i++).setCellValue(biz.getEmployeeId());
    // 提交人所属大区
    row.createCell(i++).setCellValue(biz.getEmployeeDeptName());
    // 商机状态
    row.createCell(i++).setCellValue(biz.getStateName());
    // 销售商品
    row.createCell(i++).setCellValue(biz.getKeywords());
    // 商机概述
    row.createCell(i++).setCellValue(biz.getDesc());
    return i;
  }
  
  private int applyCustomerInfo(Row row, int i, ExportCustomerInfoQueryVo vo) {
    // 客户名称
    row.createCell(i++).setCellValue(vo.getCustomerName());
    // 省/市/区县
    row.createCell(i++).setCellValue(vo.getProvincial());
    // 单位性质
    row.createCell(i++).setCellValue(vo.getEnterName());
    // 所属行业
    row.createCell(i++).setCellValue(vo.getIndustry());
    // 单位地址
    row.createCell(i++).setCellValue(vo.getAddress());
    // 座机
    row.createCell(i++).setCellValue(vo.getTelephone());
    // 邮编
    row.createCell(i++).setCellValue(vo.getZipCode());
    // 网址
    row.createCell(i++).setCellValue(vo.getWebsite());
    // 传真
    row.createCell(i++).setCellValue(vo.getFax());
    // 姓名
    row.createCell(i++).setCellValue(vo.getContactName());
    // 性别
    row.createCell(i++).setCellValue(vo.getGender());
    // 部门
    row.createCell(i++).setCellValue(vo.getDeptName());
    // 职务
    row.createCell(i++).setCellValue(vo.getJob());
    // 手机号码
    row.createCell(i++).setCellValue(vo.getContactTelphone());
    // 邮箱
    row.createCell(i++).setCellValue(vo.getContactEmail());
    // 办公座机
    row.createCell(i++).setCellValue(vo.getOfficePlane());
    // 毕业学校
    row.createCell(i++).setCellValue(vo.getUniversity());
    // 专业
    row.createCell(i++).setCellValue(vo.getMajor());
    // 生日(年/月/日)
    row.createCell(i++).setCellValue(vo.getBirthday() == null ? null :
      vo.getBirthday().replace('-', '/'));
    // 家庭状况
    row.createCell(i++).setCellValue(vo.getFamilyInfo());
    // 其他
    row.createCell(i++).setCellValue(vo.getOther());
    return i;
  }
  
  private int applyApply(Row row, int i, ProjectExcelApplyVO biz) {
    
    
    // yes，就是这么做
    // 生产数据是脏的
    if (biz == null) {
      biz = new ProjectExcelApplyVO();
    }
    
    
    // 项目总情况
    row.createCell(i++).setCellValue(biz.getProjectSituation());
    // 最终用户关系分析
    row.createCell(i++).setCellValue(biz.getUserRelationAnalysis());
    // 参与集成商情况分析
    row.createCell(i++).setCellValue(biz.getIntegratorSituation());
    // 竞争对手情况分析
    row.createCell(i++).setCellValue(biz.getCompeteOpponentSnalysis());
    // 项目运作策略
    row.createCell(i++).setCellValue(biz.getProjectRunStrategy());
    return i;
  }

  public static void applyHeader(Sheet sheet) {
    
    Row th = sheet.createRow(0);
    sheet.createRow(1);
    
    int i = 0;
    
    th.createCell(i).setCellValue("项目");
    i = applyProjectInfoHeader(sheet, i);
    
    
    th.createCell(i).setCellValue("商机");
    i = applyBizOpportsHeader(sheet, i);
    
    th.createCell(i).setCellValue("最终客户及联系人");
    i = applyCustomerInfoHeader(sheet, i);
    
    th.createCell(i).setCellValue("合作伙伴及联系人");
    i = applyCustomerInfoHeader(sheet, i);
    
    th.createCell(i).setCellValue("申请信息");
    
    i = applyApplyHeader(sheet, i);
  }

  private static int applyCustomerInfoHeader(Sheet sheet, int i) {
    
    Row row = sheet.getRow(1);
    
    int from = i;
    for (String h : CUST_HEADERS) {
      row.createCell(i++).setCellValue(h);
      
    }
    CellRangeAddress craPatn = new CellRangeAddress(0, 0, from, i - 1);
    sheet.addMergedRegion(craPatn);
    return i;
  }

  private static int applyBizOpportsHeader(Sheet sheet, int i) {
    
    Row row = sheet.getRow(1);
    
    int from = i;
    for (String h : BIZ_HEADERS) {
      row.createCell(i++).setCellValue(h);
      
    }
    CellRangeAddress craPatn = new CellRangeAddress(0, 0, from, i - 1);
    sheet.addMergedRegion(craPatn);
    return i;
  }

  private static int applyProjectInfoHeader(Sheet sheet, int i) {
    
    Row row = sheet.getRow(1);
    
    int from = i;
    for (String h : PROJ_HEADERS) {
      row.createCell(i++).setCellValue(h);
    }
    
    CellRangeAddress craPatn = new CellRangeAddress(0, 0, from, i - 1);
    sheet.addMergedRegion(craPatn);
    
    return i;
  }
  
  private static int applyApplyHeader(Sheet sheet, int i) {
    Row row = sheet.getRow(1);
    
    int from = i;
    for (String h : PROJ_APPLY_HEADERS) {
      row.createCell(i++).setCellValue(h);
    }
    
    CellRangeAddress craPatn = new CellRangeAddress(0, 0, from, i - 1);
    sheet.addMergedRegion(craPatn);
    
    return i;
  }

  public static int prdCellLength() {
    return PROJ_HEADERS.size();
  }

  public static int bizCellLength() {
    return BIZ_HEADERS.size();
  }

  public static int custCellLength() {
    return CUST_HEADERS.size();
  }

  public boolean containsAnyPrd() {
    return PRDS.keySet().stream().map(this::getPrd).anyMatch(prd -> 
        prd != null 
        && prd.getSaleCount() > 0 
        && prd.getAmount().compareTo(BigDecimal.ZERO) > 0);
  }

  public static int applyCellLength() {
    return PROJ_APPLY_HEADERS.size();
  }
  
  
}
