package com.sefonsoft.oa.controller.report;

import com.sefonsoft.oa.controller.common.BaseController;
import com.sefonsoft.oa.system.response.Response;
import com.sefonsoft.oa.system.utils.DateUtils;

import java.util.Date;

/**
 * 周日报表(EmployeeReportInfo)表控制层
 *
 * @author Aron
 * @since 2019-12-09 11:22:06
 */
public class TController extends BaseController {





    public static void main(String[] args) throws Exception{

        String monthStr = "201912";
        Integer month = Integer.valueOf(monthStr) *100;

        Date mouth = DateUtils.StringFormat(monthStr,"yyyyMM");
        Integer days = DateUtils.getDayOfMonth(mouth);

        for (int i = 1; i <= days; i++) {
            Integer date = month + i;
            if (DateUtils.getCourrentDateTimeKey().intValue() < date.intValue()) {
                break;
            }
            Date mouth1 = DateUtils.StringFormat(date.toString(),"yyyyMMdd");
            System.out.println("mouth1:"+mouth1);
        }
    }

}