package com.sefonsoft.oa.service.project;

import com.sefonsoft.oa.dao.customer.CustomerInfoDao;
import com.sefonsoft.oa.dao.project.*;
import com.sefonsoft.oa.dao.sysdepartment.SysDepartmentDao;
import com.sefonsoft.oa.dao.user.SysUserDao;
import com.sefonsoft.oa.domain.project.*;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.entity.contact.ContactInfo;
import com.sefonsoft.oa.entity.customer.CustomerInfo;
import com.sefonsoft.oa.entity.project.ProjectCheckInfo;
import com.sefonsoft.oa.entity.project.ProjectSaleRef;
import com.sefonsoft.oa.service.customer.CustomerInfoService;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.ExcelMappingBeanUtil;
import com.sefonsoft.oa.system.utils.ObjTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.sefonsoft.oa.system.constant.ProductConstant.*;
import static com.sefonsoft.oa.system.constant.ProjectConstant.*;
import static com.sefonsoft.oa.system.constant.ResponseMsgConstant.NO_PARAM;

@Service
public class ImportProjectServiceImpl extends Response<Object> implements ImportProjectService{

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private ProjectInfoDao projectInfoDao;

    @Resource
    private ProjectSaleRefDao projectSaleRefDao;

    @Resource
    private ProjectCheckInfoDao projectCheckInfoDao;

    @Resource
    private ProductProjectRefDao productProjectRefDao;

    @Resource
    private ProjectAmountDevideRefDao projectAmountDevideRefDao;

    @Resource
    private CustomerInfoDao customerInfoDao;

    @Resource
    private CustomerInfoService customerInfoService;

    @Resource
    private SysDepartmentDao sysDepartmentDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Response importProjects(MultipartFile file, LoginUserDTO loginUserDTO, HttpSession session) throws IOException, IllegalAccessException, ParseException, InstantiationException {
        Date date = new Date();
        ExcelMappingBeanUtil excelMappingBeanUtil = new ExcelMappingBeanUtil();
        List<ProjectInfoImportDTO> list = excelMappingBeanUtil.parse(ProjectInfoImportDTO.class, file, session);
        Object sessionAttributeLong = session.getAttribute("EXCEL_CELL_TOO_LONG");
        if (ObjTool.isNotNull(sessionAttributeLong)) {
            session.removeAttribute("EXCEL_CELL_TOO_LONG");
            return failure(sessionAttributeLong.toString());
        }
        Object sessionAttributeDouble = session.getAttribute("EXCEL_CELL_DOUBLE_TYPE_ERROR");
        if (ObjTool.isNotNull(sessionAttributeDouble)) {
            session.removeAttribute("EXCEL_CELL_DOUBLE_TYPE_ERROR");
            return failure(sessionAttributeDouble.toString());
        }
        Object sessionAttributeInteger = session.getAttribute("EXCEL_CELL_INTEGER_TYPE_ERROR");
        if (ObjTool.isNotNull(sessionAttributeInteger)) {
            session.removeAttribute("EXCEL_CELL_INTEGER_TYPE_ERROR");
            return failure(sessionAttributeInteger.toString());
        }


        //check
        Response response = checkProjects(list, loginUserDTO, session);
        if (ObjTool.isNotNull(response)) {
            return response;
        }
        //transform
        if (ObjTool.isNotNull(list)) {
            for (int j = 0; j < list.size(); j++){
                ProjectInfoImportDTO bean = list.get(j);
                ProjectInfoInsertDTO projectInfoInsertDTO = new ProjectInfoInsertDTO();
                //组装项目地址
                String location = bean.getProvince() + "/" + bean.getCity();
                //获取项目立项人编号
                projectInfoInsertDTO.setEmployeeId(bean.getEmployeeId());
                //获取是否是重要项目数据
                String hasImportant = bean.getHasImportant();
                if (ObjTool.isNotNull(hasImportant) && hasImportant.contains("是")) {
                    projectInfoInsertDTO.setHasImportant(1);
                } else {
                    projectInfoInsertDTO.setHasImportant(2);
                }
                //获取业绩分隔数据,如果字符串为空，则传当前立项人的数据进去
                List<ProjectAmountDevideRefInsertDTO> projectAmountDevideRefs = getResultCutSharesByStr(bean, bean.getEmployeeId(), loginUserDTO.getUserId());
                //获取关联产品
                List<ProductProjectRefInsertDTO> productList = getProductsRefByStr(bean, loginUserDTO.getUserId(), session, j+1);

                //获取签约概率
                String estimateSuccess = bean.getEstimateSuccess();
                if (ObjTool.isNotNull(estimateSuccess)) {
                    double dou = Double.parseDouble(estimateSuccess);
                    if (ObjTool.isNotNull(dou)) {
                        Double d = dou * 100;
                        Integer i = d.intValue();
                        if (ObjTool.isNotNull(i)) {
                            projectInfoInsertDTO.setEstimateSuccess(i);
                        }
                    }
                }
                //客户项目阶段
                String customerProjectPhase = bean.getCustomerProjectPhase();
                if (ObjTool.isNotNull(customerProjectPhase)) {
                    //去掉数字
                    customerProjectPhase = customerProjectPhase.replaceAll("\\d+", "");
                    projectInfoInsertDTO.setCustomerProjectPhase(customerProjectPhase);
                }

                //预估金额
                String estimateMoney = bean.getEstimateMoney();
                if (ObjTool.isNotNull(estimateMoney)) {
                    projectInfoInsertDTO.setEstimateMoney(new BigDecimal(estimateMoney));
                } else {
                    projectInfoInsertDTO.setEstimateMoney(new BigDecimal(0));
                }

                projectInfoInsertDTO.setProjectId(bean.getProjectId())
                        .setLocation(location)
                        .setIndustry(bean.getIndustry())
                        .setEmployeeId(bean.getEmployeeId())
                        .setProjectName(bean.getProjectName())
//                        .setCustomerName(bean.getCustomerName())
//                        .setContactDeptName(bean.getContactDeptName())
//                        .setContactName(bean.getContactName())
//                        .setContactTel(bean.getContactTel())
//                        .setPartnerName(bean.getPartnerName())
//                        .setPartnerContactDeptName(bean.getPartnerContactDeptName())
//                        .setPartnerContactName(bean.getPartnerContactName())
//                        .setPartnerContactTel(bean.getPartnerContactTel())
                        .setProjectAmountDevideRefInsertDTOList(projectAmountDevideRefs)
                        .setSpstageId(2)
                        .setOperator(loginUserDTO.getUserId())
                        .setProsId(PROJECT_STATUS_SETUP)
                        .setProductRefInsertDTOList(productList)
                        .setHasSimilarType(HAS_NO_SIMILAR_TYPE)
                        .setOverTimeFlag(OVER_TIME_FLAG_NO)
                        .setTotalInvestment(new BigDecimal(0))
                        .setEstimateTime(format.parse(bean.getEstimateTime()))
                        .setLastTime(new Date())
                        .setCreateTime(new Date());

                //处理是否插入客户和联系人的业务逻辑
                //处理成功后返回的是客户编号，合作伙伴编号，客户联系人编号，合作伙伴联系人编号
                //若其中没有需要导入的联系人信息，则联系人信息为空
                HashMap<String, Object> customerContactMapInfo = insertCustomerContactInfo(bean, loginUserDTO);
//                if ((!ObjTool.isNotNull(customerContactMapInfo)) || customerContactMapInfo.size() == 0) {
//                    return failure("成功导入前"+ j + "个项目，但" + (j + 1) + "行的客户信息有误，请修正后，从第"+(j+1)+"行项目数据开始重新导入");
//                }
                Object newCustomerIdObj = customerContactMapInfo.get("newCustomerId");
                Object newContactIdObj = customerContactMapInfo.get("newContactId");
                Object newPartnerIdObj = customerContactMapInfo.get("newPartnerId");
                Object newPartnerContactIdObj = customerContactMapInfo.get("newPartnerContactId");
                String newCustomerId = null;
                Long newContactId = null;
                String newPartnerId = null;
                Long newPartnerContactId = null;
                if (ObjTool.isNotNull(newCustomerIdObj)) {
                    newCustomerId = (String) newCustomerIdObj;
                }
                if (ObjTool.isNotNull(newContactIdObj)) {
                    newContactId = (Long) newContactIdObj;
                }
                if (ObjTool.isNotNull(newPartnerIdObj)) {
                    newPartnerId = (String) newPartnerIdObj;
                }
                if (ObjTool.isNotNull(newPartnerContactIdObj)) {
                    newPartnerContactId = (Long) newPartnerContactIdObj;
                }
                projectInfoInsertDTO.setImportType(1).setCustomerId(newCustomerId).setContactId(newContactId).setPartnerId(newPartnerId).setPartnerContactId(newPartnerContactId);
                //客户与合作伙伴的关联关系处理完成

                int insertProject = projectInfoDao.insert(projectInfoInsertDTO);
                //插入项目与负责人信息
                boolean insertMiner = projectSaleRefDao.insert(new ProjectSaleRef(null, bean.getProjectId(), projectInfoInsertDTO.getEmployeeId(), null, loginUserDTO.getUserId(), date, date));
                //插入业绩分隔
                int insertAmountDevides = projectAmountDevideRefDao.batchInsert(projectAmountDevideRefs);
                //插入已经审核通过的信息，审核人默认为当前操作的人
                int insertProjectCheck = projectCheckInfoDao.insert(new ProjectCheckInfo(null, bean.getProjectId(), loginUserDTO.getUserId(), null, PROJECT_AGREE_CHECK_STATUS, format.parse(bean.getSetupDate()), format.parse(bean.getSetupDate())
                ));
                //新增项目的关联产品信息
                int insertProducts = 0;
                if (ObjTool.isNotNull(productList)) {
                    insertProducts = productProjectRefDao.batchInsert(productList);
                }
                if (insertProject > 0 && insertMiner && insertAmountDevides > 0 && insertProjectCheck > 0) {
                    //除了产品信息没判断是否添加成功外，其他的都添加成功了
                    if ((ObjTool.isNotNull(productList) && insertProducts > 0) || (!ObjTool.isNotNull(productList))) {
                        //如果有产品信息且添加成功，或者没产品信息，则表示已经添加成功了，否则，则视为添加失败
                    } else {
                        return new Response<>().failure("成功导入前"+ j + "个项目，但" + (j + 1) + "行的项目编号为" + bean.getProjectId() + "的产品信息有误，请修正后从第"+(j+1)+"行项目数据开始重新导入");
                    }
                } else {
                    if ((insertProject <= 0) || (insertProjectCheck<=0)) {
                        return new Response<>().failure("成功导入前"+ j + "个项目，但" + (j + 1) + "行的项目编号为" + bean.getProjectId() + "的项目主体信息有误，请修正后从第"+(j+1)+"行项目数据开始重新导入");
                    } else if (!insertMiner) {
                        return new Response<>().failure("成功导入前"+ j + "个项目，但" + (j + 1) + "行的项目编号为" + bean.getProjectId() + "的项目立项人有误，请修正后从第"+(j+1)+"行项目数据开始重新导入");
                    } else if (insertAmountDevides <= 0) {
                        return new Response<>().failure("成功导入前"+ j + "个项目，但" + (j + 1) + "行的项目编号为" + bean.getProjectId() + "的业绩分隔信息有误，请修正后从第"+(j+1)+"行项目数据开始重新导入");
                    }
                }
            }
        }
        return new Response<>().success();
    }

    /**
     * 处理是否插入客户和联系人的业务逻辑
     * 处理成功后返回的是客户编号，合作伙伴编号，客户联系人编号，合作伙伴联系人编号
     * 若其中没有需要导入的联系人信息，则联系人信息为空
     * @param bean
     * @param loginUserDTO
     * @return
     */
    private HashMap<String, Object> insertCustomerContactInfo(ProjectInfoImportDTO bean, LoginUserDTO loginUserDTO) {
        HashMap<String, Object> map = new HashMap<>();
        //获取当前项目的客户信息和合作伙伴信息
        String employeeId = bean.getEmployeeId();
        String industry = bean.getIndustry();
        String customerName = bean.getCustomerName();
        String contactTel = bean.getContactTel();
        String contactDeptName = bean.getContactDeptName();
        String contactName = bean.getContactName();
        //判断当前负责人是否有同名的客户,如果有，则不需要插入信息，如果没有,则需要插入信息
        CustomerInfo customerInfo = ifHasSameCustomerInThisPerson(employeeId, customerName, contactName);
        boolean insertCustomerFlag = false;

        String newCustomerId = "";
        Long newContactId = null;

        if (!(ObjTool.isNotNull(customerInfo))) {
            //如果相似客户为空，则需要插入客户和联系人；
            newContactId = insertProjectRefCustomer(industry, null, employeeId, customerName, contactName, contactDeptName, contactTel, loginUserDTO.getUserId(), newContactId);
            //查询客户最大编码，即为新增的客户编号
            newCustomerId = customerInfoService.getMaxCustomerId();
            if (ObjTool.isNotNull(newContactId)) {
                insertCustomerFlag = true;
            }
        } else if (ObjTool.isNotNull(customerInfo) && (!ObjTool.isNotNull(customerInfo.getContactList()))) {
            //如果相似客户不为空但联系人为空，则需要插入联系人
            newContactId = insertProjectRefCustomer(industry, customerInfo.getCustomerId(), employeeId, customerName, contactName, contactDeptName, contactTel, loginUserDTO.getUserId(), newContactId);
            newCustomerId = customerInfo.getCustomerId();
            if (ObjTool.isNotNull(newContactId)) {
                insertCustomerFlag = true;
            }
        } else {
            //相似客户和联系人都不为空,则不需插入其他信息，只需要获取到相应的客户编号和相应的客户联系人编号即可
            newCustomerId = customerInfo.getCustomerId();
            newContactId = customerInfo.getContactList().get(0).getId();
            insertCustomerFlag = true;
        }

        String newPartnerId = "";
        Long newPartnerContactId = null;
        String partnerName = bean.getPartnerName();
        String partnerContactName = bean.getPartnerContactName();
        String partnerContactDeptName = bean.getPartnerContactDeptName();
        String partnerContactTel = bean.getPartnerContactTel();
        boolean insertPartnerFlag = false;

        //如果合作伙伴
        if (ObjTool.isNotNull(partnerName)) {

            //判断当前负责人是否有同名的客户,如果有，则不需要插入信息，如果没有,则需要插入信息
            CustomerInfo partnerInfo = ifHasSameCustomerInThisPerson(employeeId, partnerName, partnerContactName);


            if (!(ObjTool.isNotNull(partnerInfo))) {
                //如果合作伙伴为空，则需要插入合作伙伴和联系人；
                newPartnerContactId = insertProjectRefCustomer(industry, null, employeeId, partnerName, partnerContactName, partnerContactDeptName, partnerContactTel, loginUserDTO.getUserId(), newPartnerContactId);
                newPartnerId = customerInfoService.getMaxCustomerId();
                if (ObjTool.isNotNull(newPartnerContactId)) {
                    insertPartnerFlag = true;
                }
            } else if (ObjTool.isNotNull(partnerInfo) && (!ObjTool.isNotNull(partnerInfo.getContactList()))) {
                //如果合作伙伴不为空但联系人为空，则需要插入联系人
                newPartnerContactId = insertProjectRefCustomer(industry, partnerInfo.getCustomerId(), employeeId, partnerName, partnerContactName, partnerContactDeptName, partnerContactTel, loginUserDTO.getUserId(), newPartnerContactId);
                newPartnerId = partnerInfo.getCustomerId();
                if (ObjTool.isNotNull(newPartnerContactId)) {
                    insertPartnerFlag = true;
                }
            } else {
                //客户和联系人都不为空,则不需插入其他信息，只需要获取到相应的客户编号和相应的客户联系人编号即可
                insertPartnerFlag = true;
                newPartnerId = partnerInfo.getCustomerId();
                newPartnerContactId = partnerInfo.getContactList().get(0).getId();
            }

        }

            if (ObjTool.isNotNull(newCustomerId)) {
                map.put("newCustomerId", newCustomerId);
            }
            if (ObjTool.isNotNull(newContactId)) {
                map.put("newContactId", newContactId);
            }
            if (ObjTool.isNotNull(newPartnerId)) {
                map.put("newPartnerId", newPartnerId);
            }
            if (ObjTool.isNotNull(newPartnerContactId)) {
                map.put("newPartnerContactId", newPartnerContactId);
            }

        return map;
    }

    /**
     * 获取与导入客户和导入联系人相同的客户和联系人，如果客户不相同则返回null，如果客户的联系人不相同，则只返回相同的客户，不返回相同的联系人
     * @param employeeId
     * @param customerName
     * @param contactName
     * @return
     */
    private CustomerInfo ifHasSameCustomerInThisPerson(String employeeId, String customerName, String contactName) {
        //获取数据库中负责人和客户名称都和插入信息相同的客户数据
        List<CustomerInfo> customerInfoList = customerInfoDao.getSameCustomerByUserIdAndCustomerName(employeeId, customerName);
        if (ObjTool.isNotNull(customerInfoList)) {
            //若有重复数据，则取第一条客户数据
            CustomerInfo customerInfo = customerInfoList.get(0);
            List<ContactInfo> contactList = customerInfo.getContactList();
            //判断取出的客户数据中的联系人数据是否和
            if (ObjTool.isNotNull(contactList)) {
                //只要导入的联系人名称为null或者客户中的联系人名称和导入的联系人名称不相同就【移除这条联系人信息】
                contactList.removeIf(contactInfo -> ((!ObjTool.isNotNull(contactName)) || (!contactInfo.getContactName().equals(contactName))));
                customerInfo.setContactList(contactList);
            }
            return customerInfo;
        }
        return null;
    }

    /**
     * 校验项目信息是否合法
     * @param list
     * @return
     */
    private Response checkProjects(List<ProjectInfoImportDTO> list, LoginUserDTO loginUserDTO, HttpSession session) {
        if (!ObjTool.isNotNull(list)) {
            return failure("项目信息为空，请勿导入空数据");
        }
        for (int k = 0; k < list.size(); k++) {
            ProjectInfoImportDTO importDTO = list.get(k);
            String projectId = importDTO.getProjectId();
            String province = importDTO.getProvince();
            String city = importDTO.getCity();
            String industry = importDTO.getIndustry();
            String setupDate = importDTO.getSetupDate();
            String projectName = importDTO.getProjectName();
            String customerName = importDTO.getCustomerName();
            String employeeName = importDTO.getEmployeeName();
            String estimateSuccess = importDTO.getEstimateSuccess();
            String estimateTime = importDTO.getEstimateTime();
            String deptName = importDTO.getDeptName();
            if (!ObjTool.isNotNull(projectId)) {
                return failure("第"+(k+1)+"个项目的项目编号" + NO_PARAM);
            }
            if (!ObjTool.isNotNull(deptName)) {
                return failure("第"+(k+1)+"个项目的所属区域" + NO_PARAM);
            }
            if (!ObjTool.isNotNull(province)) {
                return failure("第"+(k+1)+"个项目的省份" + NO_PARAM);
            }
            if (!ObjTool.isNotNull(city)) {
                return failure("第"+(k+1)+"个项目的城市" + NO_PARAM);
            }
            if (!ObjTool.isNotNull(industry)) {
                return failure("第"+(k+1)+"个项目的行业" + NO_PARAM);
            }
            if (!ObjTool.isNotNull(setupDate)) {
                return failure("第"+(k+1)+"个项目的立项时间" + NO_PARAM);
            }
            if (!ObjTool.isNotNull(employeeName)) {
                return failure("第"+(k+1)+"个项目的立项人" + NO_PARAM);
            }
            if (!ObjTool.isNotNull(projectName)) {
                return failure("第"+(k+1)+"个项目的项目名称" + NO_PARAM);
            }
            if (!ObjTool.isNotNull(customerName)) {
                return failure("第"+(k+1)+"个项目的最终客户" + NO_PARAM);
            }
            if (!ObjTool.isNotNull(estimateSuccess)) {
                return failure("第"+(k+1)+"个项目的签约概率" + NO_PARAM);
            }
            if (!ObjTool.isNotNull(estimateTime)) {
                return failure("第"+(k+1)+"个项目的预计签约时间" + NO_PARAM);
            }

            List<String> deptIdList = sysDepartmentDao.queryDeptIdByDeptName(deptName);
            if (!ObjTool.isNotNull(deptIdList)) {
                return failure("第" + (k + 1) + "条项目中的所属区域【" + deptName + "】在系统中不存在，请修正后再导入");
            } else {
                String deptId = deptIdList.get(0);
                importDTO.setDeptId(deptId);
            }

            String userId = "";
            List<String> userIdList = sysUserDao.getUserIdByUserNameAndDeptId(importDTO.getEmployeeName(), importDTO.getDeptId());
            if (!ObjTool.isNotNull(userIdList)) {
                return failure("第" + (k + 1) + "条项目的立项人【" + importDTO.getEmployeeName() + "】在系统中暂无可用账号，请添加此人账号后再导入项目信息");
            } else {
                userId = userIdList.get(0);
            }
            int count = projectInfoDao.countByprojectId(projectId);
            if (count > 0) {
                return failure("第"+(k+1)+"条项目中的项目编号【"+ projectId +"】已在系统存在，请勿重复导入");
            }
            int countByProjectName = projectInfoDao.countByProjectName(projectName);
            if (countByProjectName > 0) {
                return failure("第"+(k+1)+"条项目中的项目名称【"+ projectName +"】已在系统存在，请勿重复导入");
            }
            if (!ObjTool.isNotNull(userId)) {
                return failure("第" + (k + 1) + "条项目的立项人【"+ importDTO.getEmployeeName() +"】在系统中暂无可用账号，请添加此人账号后再导入项目信息");
            } else {
                importDTO.setEmployeeId(userId);
            }
            try {
                Date parse = format.parse(setupDate);
            } catch (ParseException e) {
                return failure("第"+(k+1)+"条项目中的立项时间的格式错误，正确格式为2018/08/08");
            }
            try {
                Date parse = format.parse(importDTO.getEstimateTime());
            } catch (ParseException e) {
                return failure("第"+(k+1)+"条项目中的预估签约时间的格式错误，正确格式为2018/08/08");
            }
            if (ObjTool.isNotNull(importDTO.getResultCutShares())) {
                if ((!importDTO.getResultCutShares().contains("：")) || (!importDTO.getResultCutShares().contains("%"))) {
                    return failure("第"+(k+1)+"条项目中的业绩分隔比例的格式错误，请修改后重新导入");
                }
            }

            if (ObjTool.isNotNull(importDTO.getResultCutShares())) {
                String[] splitNameArray = importDTO.getResultCutShares().replace(" ", "").split("；");
                if (ObjTool.isNotNull(splitNameArray)) {
                    for (String splitStr : splitNameArray) {
                        splitStr = splitStr.replace("%", "");
                        //将数字和汉字分离
                        String userName = splitStr.split("：")[0];
                        List<String> splitUserIdList = sysUserDao.getUserIdListByUserName(userName);

                        if (!ObjTool.isNotNull(splitUserIdList)) {
                            return failure("第"+(k+1)+"条项目中的业绩分隔中的"+userName+"在系统中没有账号，请创建对应名称的账号后再试");
                        }
                    }
                }
            }

            if (ObjTool.isNotNull(importDTO.getHasImportant())) {
                if (!importDTO.getHasImportant().equals("是")) {
                    return failure("第"+(k+1)+"条项目中的是否重大关键的格式错误，请修改后重新导入");
                }
            }

            //获取关联产品
            List<ProductProjectRefInsertDTO> productList = getProductsRefByStr(importDTO, loginUserDTO.getUserId(), session, k+1);
            //校验产品是否填写符合规范（是否是填了个数没填名称）
            Object sessionAttributeProduct = session.getAttribute("EXCEL_PRODUCT_ERROR");
            if (ObjTool.isNotNull(sessionAttributeProduct)) {
                session.removeAttribute("EXCEL_PRODUCT_ERROR");
                return failure(sessionAttributeProduct.toString());
            }
        }
        return null;
    }

    /**
     * 导入时插入客户和联系人或插入客户的联系人
     * @param customerId
     * @param employeeId
     * @param customerName
     * @param contactName
     * @param contactDeptName
     * @param tel
     * @param operatorId
     * @return 客户的联系人编号
     */
    private Long insertProjectRefCustomer(String industry, String customerId, String employeeId, String customerName, String contactName, String contactDeptName, String tel, String operatorId, Long newContactId) {
        int importType = 1;
        if (ObjTool.isNotNull(customerId)) {
            //只需要插入客户的联系人信息
            if (ObjTool.isNotNull(contactName)) {
                ImportContactDTO importContactDTO = new ImportContactDTO();
                importContactDTO.setImportType(importType)
                            .setEmployeeId(employeeId)
                        .setCustomerId(customerId)
                        .setContactName(contactName)
                        .setContactDeptName(contactDeptName)
                        .setTel(tel)
                        .setOperatorId(operatorId)
                        .setLastDate(new Date());

                customerInfoDao.importInsertContactInfo(importContactDTO);
                return importContactDTO.getId();
            }
        } else {
            //既需要插入客户，又需要插入客户的联系人
            //查询客户最大编码
            String maxCode = customerInfoService.getMaxCustomerId();
            //如果查询数据库没有客户记录，设置maxCode默认值
            if(null==maxCode){
                maxCode="KH202001010000";
            }
            //最大客户编码后判断是否有当天记录，有就BM+当天日期+4位流水+1
            String customerIdNew = customerInfoService.getKhCode(maxCode);
            boolean insertCustomerFlag = customerInfoDao.importInsertCustomerInfo(industry, tel, importType, customerIdNew, customerName, operatorId, new Date());
            boolean insertContactFlag = customerInfoDao.importInsertCustomerSaleRef(customerIdNew, employeeId, operatorId, new Date());
            if (insertCustomerFlag && insertContactFlag && ObjTool.isNotNull(contactName)) {

                ImportContactDTO importPartnerContactDTO = new ImportContactDTO();
                importPartnerContactDTO.setImportType(importType)
                        .setEmployeeId(employeeId)
                        .setCustomerId(customerIdNew)
                        .setContactName(contactName)
                        .setContactDeptName(contactDeptName)
                        .setTel(tel)
                        .setOperatorId(operatorId)
                        .setLastDate(new Date());

                customerInfoDao.importInsertContactInfo(importPartnerContactDTO);
                return importPartnerContactDTO.getId();
            }
        }
        return null;
    }

    /**
     * 获取项目相关产品
     * @param bean
     * @return
     */
    private List<ProductProjectRefInsertDTO> getProductsRefByStr(ProjectInfoImportDTO bean, String loginUserId, HttpSession session,int j) {
        Date date = new Date();
        List<ProductProjectRefInsertDTO> list = new ArrayList<>();
        String projectId = bean.getProjectId();
        String uEcountStr = bean.getUEcount();
        String protectServiceCountStr = bean.getProtectServiceCount();
        String otherServiceCountStr = bean.getOtherServiceCount();
        String mineRcountStr = bean.getMINERcount();
        String mEcountStr = bean.getMEcount();
        String hadooPcountStr = bean.getHADOOPcount();
        String goverNcountStr = bean.getGOVERNcount();
        String etLcountStr = bean.getETLcount();
        String doServiceCountStr = bean.getDoServiceCount();
        String bEcountStr = bean.getBEcount();
        String bcCountStr = bean.getBCCount();
        if (ObjTool.isNotNull(uEcountStr)) {
            Integer uEcount = Integer.parseInt(uEcountStr);
            String uEprice = bean.getUEprice();
            if ((!ObjTool.isNotNull(uEcount)) || (!ObjTool.isNotNull(uEprice))) {
                session.setAttribute("EXCEL_PRODUCT_ERROR","第"+j+"个项目信息中的UE产品的价格填写有误,请修改后再导入");
                return list;
            }
            list.add(new ProductProjectRefInsertDTO(UE_ID, uEcount, new BigDecimal(uEprice), projectId, loginUserId, date, date));
        }
        if (ObjTool.isNotNull(protectServiceCountStr)) {
            Integer protectServiceCount = Integer.parseInt(protectServiceCountStr);
            String protectServicePrice= bean.getProtectServicePrice();
            if ((!ObjTool.isNotNull(protectServiceCount)) || (!ObjTool.isNotNull(protectServicePrice))) {
                session.setAttribute("EXCEL_PRODUCT_ERROR","第"+j+"个项目信息中的UE产品的价格填写有误,请修改后再导入");
                return list;
            }
            list.add(new ProductProjectRefInsertDTO(PROTECT_SERVICE_ID, protectServiceCount, new BigDecimal(protectServicePrice), projectId, loginUserId, date, date));
        }
        if (ObjTool.isNotNull(otherServiceCountStr)) {
            Integer otherServiceCount = Integer.parseInt(otherServiceCountStr);
            String otherServicePrice = bean.getOtherServicePrice();
            if ((!ObjTool.isNotNull(otherServiceCount)) || (!ObjTool.isNotNull(otherServicePrice))) {
                session.setAttribute("EXCEL_PRODUCT_ERROR","第"+j+"个项目信息中的其他服务价格填写有误,请修改后再导入");
                return list;
            }
            list.add(new ProductProjectRefInsertDTO(OTHER_SERVICE_ID, otherServiceCount, new BigDecimal(otherServicePrice), projectId, loginUserId, date, date));
        }
        if (ObjTool.isNotNull(mineRcountStr)) {
            Integer mineRcount = Integer.parseInt(mineRcountStr);
            String minerPrice = bean.getMINERPrice();
            if ((!ObjTool.isNotNull(minerPrice)) || (!ObjTool.isNotNull(mineRcount))) {
                session.setAttribute("EXCEL_PRODUCT_ERROR","第"+j+"个项目信息中的Miner产品的价格填写有误,请修改后再导入");
                return list;
            }
            list.add(new ProductProjectRefInsertDTO(MINER_ID, mineRcount, new BigDecimal(minerPrice), projectId, loginUserId, date, date));
        }
        if (ObjTool.isNotNull(mEcountStr)) {
            Integer mEcount = Integer.parseInt(mEcountStr);
            String mEprice = bean.getMEprice();
            if ((!ObjTool.isNotNull(mEcount)) || (!ObjTool.isNotNull(mEprice))) {
                session.setAttribute("EXCEL_PRODUCT_ERROR","第"+j+"个项目信息中的ME产品的价格填写有误,请修改后再导入");
                return list;
            }
            list.add(new ProductProjectRefInsertDTO(ME_ID, mEcount, new BigDecimal(mEprice), projectId, loginUserId, date, date));
        }
        if (ObjTool.isNotNull(hadooPcountStr)) {
            Integer hadooPcount = Integer.parseInt(hadooPcountStr);
            String hadoopPrice = bean.getHADOOPPrice();
            if ((!ObjTool.isNotNull(hadoopPrice)) || (!ObjTool.isNotNull(hadoopPrice))) {
                session.setAttribute("EXCEL_PRODUCT_ERROR","第"+j+"个项目信息中的Hadoop产品的价格填写有误,请修改后再导入");
                return list;
            }
            list.add(new ProductProjectRefInsertDTO(HADOOP_ID, hadooPcount, new BigDecimal(hadoopPrice), projectId, loginUserId, date, date));
        }
        if (ObjTool.isNotNull(goverNcountStr)) {
            Integer goverNcount = Integer.parseInt(goverNcountStr);
            String governPrice = bean.getGOVERNPrice();
            if ((!ObjTool.isNotNull(goverNcount)) || (!ObjTool.isNotNull(governPrice))) {
                session.setAttribute("EXCEL_PRODUCT_ERROR","第"+j+"个项目信息中的Govern产品的价格填写有误,请修改后再导入");
                return list;
            }
            list.add(new ProductProjectRefInsertDTO(GOVERN_ID, goverNcount, new BigDecimal(governPrice), projectId, loginUserId, date, date));
        }
        if (ObjTool.isNotNull(etLcountStr)) {
            Integer etLcount = Integer.parseInt(etLcountStr);
            String etlPrice = bean.getETLPrice();
            if ((!ObjTool.isNotNull(etLcount)) || (!ObjTool.isNotNull(etlPrice))) {
                session.setAttribute("EXCEL_PRODUCT_ERROR","第"+j+"个项目信息中的ETL产品的价格填写有误,请修改后再导入");
                return list;
            }
            list.add(new ProductProjectRefInsertDTO(ETL_ID, etLcount, new BigDecimal(etlPrice), projectId, loginUserId, date, date));
        }
        if (ObjTool.isNotNull(doServiceCountStr)) {
            Integer doServiceCount = Integer.parseInt(doServiceCountStr);
            String doServicePrice = bean.getDoServicePrice();
            if ((!ObjTool.isNotNull(doServiceCount)) || (!ObjTool.isNotNull(doServicePrice))) {
                session.setAttribute("EXCEL_PRODUCT_ERROR","第"+j+"个项目信息中的实施服务产品的价格填写有误,请修改后再导入");
                return list;
            }
            list.add(new ProductProjectRefInsertDTO(DO_SERVICE_ID, doServiceCount, new BigDecimal(doServicePrice), projectId, loginUserId, date, date));
        }
        if (ObjTool.isNotNull(bEcountStr)) {
            Integer bEcount = Integer.parseInt(bEcountStr);
            String bEsumPrice = bean.getBEsumPrice();
            if ((!ObjTool.isNotNull(bEsumPrice)) || (!ObjTool.isNotNull(bEcount))) {
                session.setAttribute("EXCEL_PRODUCT_ERROR","第"+j+"个项目信息中的BE产品的价格填写有误,请修改后再导入");
                return list;
            }
            list.add(new ProductProjectRefInsertDTO(BE_ID, bEcount, new BigDecimal(bEsumPrice), projectId, loginUserId, date, date));
        }
        if (ObjTool.isNotNull(bcCountStr)) {
            Integer bcCount = Integer.parseInt(bcCountStr);
            String bcPrice = bean.getBEprice();
            if ((!ObjTool.isNotNull(bcCount)) || (!ObjTool.isNotNull(bcPrice))) {
                session.setAttribute("EXCEL_PRODUCT_ERROR","第"+j+"个项目信息中的BC产品的价格填写有误,请修改后再导入");
                return list;
            }
            list.add(new ProductProjectRefInsertDTO(BC_ID, bcCount, new BigDecimal(bcPrice), projectId, loginUserId, date, date));
        }
        return list;
    }

    /**
     * 获取业绩分隔数据,如果字符串为空，则传当前立项人的数据进去
     * @return
     */
    private List<ProjectAmountDevideRefInsertDTO> getResultCutSharesByStr(ProjectInfoImportDTO projectInfoImportDTO, String userId, String loginUserId) {
        Date date = new Date();
        String resultCutShares = projectInfoImportDTO.getResultCutShares();
        String estimateMoney = projectInfoImportDTO.getEstimateMoney();
        List<ProjectAmountDevideRefInsertDTO> list = new ArrayList<>(1);
        if (ObjTool.isNotNull(resultCutShares) && ObjTool.isNotNull(estimateMoney)) {
            String[] splitNameArray = resultCutShares.replace(" ", "").split("；");
            if (ObjTool.isNotNull(splitNameArray)) {
                for (String splitStr : splitNameArray) {
                    splitStr = splitStr.replace("%", "");
                    //将数字和汉字分离
                    String userName = splitStr.split("：")[0];
                    List<String> splitUserIdList = sysUserDao.getUserIdListByUserName(userName);
                    String splitUserId = splitUserIdList.get(0);
                    String pert = splitStr.split("：")[1];
                    int percent = Integer.parseInt(pert);
                    list.add(new ProjectAmountDevideRefInsertDTO(projectInfoImportDTO.getProjectId(), splitUserId, percent, null, loginUserId, date, date));
                }
            }
        } else {
            //将立项人作为业绩分隔100%放入其中
            if (ObjTool.isNotNull(estimateMoney)) {
                list.add(new ProjectAmountDevideRefInsertDTO(projectInfoImportDTO.getProjectId(), userId, 100, new BigDecimal(estimateMoney), loginUserId, date, date));
            } else {
                list.add(new ProjectAmountDevideRefInsertDTO(projectInfoImportDTO.getProjectId(), userId, 100, null, loginUserId, date, date));
            }
        }
        return list;
    }

}


