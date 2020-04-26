package com.sefonsoft.oa.service.report;

import com.sefonsoft.oa.dao.report.EmployeeReportDao;
import com.sefonsoft.oa.dao.report.EmployeeReportInfoDao;
import com.sefonsoft.oa.domain.report.EmployeeReportDetailDTO;
import com.sefonsoft.oa.domain.report.EmployeeReportListDTO;
import com.sefonsoft.oa.domain.report.EmployeeReportQueryDTO;
import com.sefonsoft.oa.entity.report.EmployeeReportInfo;
import com.sefonsoft.oa.system.utils.DateUtils;
import com.sefonsoft.oa.system.utils.ObjTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static com.sefonsoft.oa.system.constant.ReportConstant.DAY_TYPE_REPORT;
import static com.sefonsoft.oa.system.constant.ReportConstant.WEEK_TYPE_REPORT;

/**
 * 周日报表(EmployeeReportInfo)表服务实现类
 *
 * @author Aron
 * @since 2019-12-09 11:22:05
 */
@Service("employeeReportService")
@Transactional(rollbackFor = Exception.class)
public class EmployeeReportServiceImpl implements EmployeeReportService {
    @Resource
    private EmployeeReportDao employeeReportDao;

    @Resource
    private EmployeeReportInfoDao employeeReportInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public EmployeeReportInfo queryById(Long id) {
        return this.employeeReportDao.queryById(id);
    }

    @Override
    public Map<Integer,EmployeeReportListDTO> getCondition(EmployeeReportQueryDTO employeeReportQueryDTO)throws Exception{

        String employeeId = employeeReportQueryDTO.getEmployeeId();
        String year = employeeReportQueryDTO.getYear();
        String months = employeeReportQueryDTO.getMonth();
        int m = Integer.parseInt(months);
        //月份小于10
        if(m<10){
            months = "0"+months;
        }

        String monthStr = year+months;
        Integer month = Integer.valueOf(monthStr) *100;
        Date mouth = DateUtils.StringFormat(monthStr,"yyyyMM");
        //当月天数
        Integer days = DateUtils.getDayOfMonth(mouth);
        Map<Integer,EmployeeReportListDTO> employeeReportMap = new HashMap<Integer,EmployeeReportListDTO>();
        //当月到当前天数放每天日周报
        for (int i = 1; i <= days; i++) {
            Integer date = month + i;
            if (DateUtils.getCourrentDateTimeKey().intValue() < date.intValue()) {
                break;
            }
            Date mouth1 = DateUtils.StringFormat(date.toString(),"yyyyMMdd");

            String rpd = DateUtils.formatDateMin(mouth1,"yyyy-MM-dd");
            List<EmployeeReportDetailDTO> list = employeeReportInfoDao.getOneDayDetailList(rpd,employeeId);
            EmployeeReportListDTO reportList = new EmployeeReportListDTO();
            //日报
            List<EmployeeReportDetailDTO> dayList = new ArrayList<>();
            //周报
            List<EmployeeReportDetailDTO> weekList = new ArrayList<>();
            if (ObjTool.isNotNull(list)) {
                for (EmployeeReportDetailDTO detailDTO : list) {
                    Integer reportType = detailDTO.getReportType();
                    if (ObjTool.isNotNull(reportType) && reportType.equals(DAY_TYPE_REPORT)) {
                        dayList.add(detailDTO);
                    } else if (ObjTool.isNotNull(reportType) && reportType.equals(WEEK_TYPE_REPORT)) {
                        weekList.add(detailDTO);
                    }
                    if (ObjTool.isNotNull(dayList)) {
                        reportList.setDayReportList(dayList);
                    }
                    if (ObjTool.isNotNull(weekList)) {
                        reportList.setWeekReportList(weekList);
                    }
                    if (ObjTool.isNotNull(detailDTO.getDeptId()) && !ObjTool.isNotNull(reportList.getDeptId())) {
                        reportList.setDeptId(detailDTO.getDeptId());
                    }
                    if (ObjTool.isNotNull(detailDTO.getDeptName()) && !ObjTool.isNotNull(reportList.getDeptName())) {
                        reportList.setDeptName(detailDTO.getDeptName());
                    }
                    if (ObjTool.isNotNull(detailDTO.getEmployeeId()) && !ObjTool.isNotNull(reportList.getEmployeeId())) {
                        reportList.setEmployeeId(detailDTO.getEmployeeId());
                    }
                    if (ObjTool.isNotNull(detailDTO.getEmployeeName()) && !ObjTool.isNotNull(reportList.getEmployeeName())) {
                        reportList.setEmployeeName(detailDTO.getEmployeeName());
                    }
                    if (ObjTool.isNotNull(detailDTO.getReportTime()) && !ObjTool.isNotNull(reportList.getReportTime())) {
                        reportList.setReportTime(detailDTO.getReportTime());
                    }

                }
                employeeReportMap.put(date,reportList);

            }



            employeeReportMap.put(date,reportList);
        }

        return employeeReportMap;
    }


    @Override
    public List<EmployeeReportQueryDTO> noFillReport(EmployeeReportQueryDTO employeeReportQueryDTO) {

        return employeeReportDao.noFillReport(employeeReportQueryDTO);
    }

    @Override
    public int noFillReportTotal(EmployeeReportQueryDTO employeeReportQueryDTO) {

        return employeeReportDao.noFillReportTotal(employeeReportQueryDTO);
    }


    @Override
    public List<EmployeeReportQueryDTO> noFillReports(EmployeeReportQueryDTO employeeReportQueryDTO) {

        return employeeReportDao.noFillReports(employeeReportQueryDTO);
    }

    @Override
    public int noFillReportTotals(EmployeeReportQueryDTO employeeReportQueryDTO) {

        return employeeReportDao.noFillReportTotals(employeeReportQueryDTO);
    }
}