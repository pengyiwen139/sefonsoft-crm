package com.sefonsoft.oa.system.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：Aron
 * @version : 1.0
 * @description：生成客户编号
 * @date ：2019/11/14
 */
public class UtilKhMethod {

    public static final String PROJECT_PREFIX = "SFWY";


    /*
     * 生成前缀统一编号(根据数据库查出的最大编号生成)
     */
    public static String getRandomCode(String maxCode, String prefix) {
        String customerCode="";
        String tableTime = "";
        // 时间字符串产生方式
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        // 组合流水号前一部分，时间字符串，如：20191114
        String time = format.format(new Date());
        if (ObjTool.isNotNull(maxCode)) {
            tableTime = maxCode.substring(2, 10);
        }

        if(!time.equals(tableTime) || "".equals(tableTime)){
            customerCode = prefix +time+ "0001";
        }else{
            // 截取字符串最后四位，结果:0001
            String uidEnd = maxCode.substring(2, 14);
            long endNum = Long.parseLong(uidEnd);
            // 结果201911140003
            long tmpNum = endNum + 1;
            customerCode = prefix + tmpNum;
        }

        return customerCode;
    }

    /*
     * 根据最大项目编号生成项目(新规则：SFWY-201912-0001)
     */
    public static String getProjectFlowCode(String maxCode, String prefix) {
        String newMaxCode = "";
        String zeroSerfix = "-0001";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String nowYearMonth = sdf.format(new Date());

        //新规则生成的项目编号必须是十六位数
        if (ObjTool.isNotNull(maxCode) && maxCode.length() == 16) {
            String firstCut = maxCode.substring(4, 5);
            String secondCut = maxCode.substring(11, 12);
            //第五个字符和第十二个字符必须是-字符串
            if ("-".equals(firstCut) && "-".equals(secondCut)) {
                String pre = maxCode.substring(0, 4);
                String date = maxCode.substring(5, 11);
                String serfix = maxCode.substring(12, 16);
                //必须是以SFWY开头，且日期截取字符串必须是20开头
                if (prefix.equals(pre)) {
                    if (date.startsWith("20")) {
                        //此时，才认为maxCode是当前方法生成的项目编号,开始对maxCode字符串进行分割+1
                        if (nowYearMonth.equals(date)) {
                            long newFlow = Long.parseLong(serfix) + 1;
                            String numFlow = "";
                            if (newFlow < 10) {
                                numFlow = "000" + newFlow;
                            } else if (newFlow >= 10 && newFlow < 100) {
                                numFlow = "00" + newFlow;
                            } else if (newFlow >= 100 && newFlow < 1000) {
                                numFlow = "0" + newFlow;
                            } else {
                                numFlow = newFlow + "";
                            }

                            return prefix + "-" + date + "-" + numFlow;
                        } else {
                            return prefix + "-" + nowYearMonth + zeroSerfix;
                        }
                    }
                }
            }
        }
        //自定义生成项目编号
        String yearMonth = sdf.format(new Date());
        newMaxCode = prefix + "-" + yearMonth + zeroSerfix;
        return newMaxCode;
    }


    /**
     * 测试kehu编号生成方法
     */
    public static void main(String[] args) {
    }

}