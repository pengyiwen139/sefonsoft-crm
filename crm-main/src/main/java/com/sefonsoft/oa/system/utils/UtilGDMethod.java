//package com.sefonsoft.oa.system.utils;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * @author ：liwenyi
// * @description：生成派工单编号
// * @date ：2019/3/18
// */
//public class UtilGDMethod {
//
//
//
//    public static final String PGD_PREFIX = "PGD";
//
//    /*
//     * 根据最大项目编号生成派工单编号(规则：PGD-20200318-0001)
//     */
//    public static String getProjectFlowCode(String maxCode) {
//        String newMaxCode = "";
//        String zeroSerfix = "0001";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String nowYearMonthDay = sdf.format(new Date());
//
//        //新规则生成的项目编号必须是十六位数
//        if (ObjTool.isNotNull(maxCode) && maxCode.length() == 17) {
//            String firstCut = maxCode.substring(3, 4);
//            String secondCut = maxCode.substring(12, 13);
//            //第四个字符和第十三个字符必须是-字符串
//            if ("-".equals(firstCut) && "-".equals(secondCut)) {
//                String pre = maxCode.substring(0, 3);
//                String date = maxCode.substring(4, 12);
//                String serfix = maxCode.substring(13, 17);
//                //必须是以PGD开头，且日期截取字符串必须是20开头
//                if (PGD_PREFIX.equals(pre)) {
//                    if (date.startsWith("20")) {
//                        //此时，才认为maxCode是当前方法生成的项目编号,开始对maxCode字符串进行分割+1
//                        if (nowYearMonthDay.equals(date)) {
//                            long newFlow = Long.parseLong(serfix) + 1;
//                            String numFlow = "";
//                            if (newFlow < 10) {
//                                numFlow = "000" + newFlow;
//                            } else if (newFlow >= 10 && newFlow < 100) {
//                                numFlow = "00" + newFlow;
//                            } else if (newFlow >= 100 && newFlow < 1000) {
//                                numFlow = "0" + newFlow;
//                            } else {
//                                numFlow = newFlow + "";
//                            }
//
//                            return PGD_PREFIX + "-" + date + "-" + numFlow;
//                        } else {
//                            return PGD_PREFIX + "-" + nowYearMonthDay +"-"+ zeroSerfix;
//                        }
//                    }
//                }
//            }
//        }
//        //自定义生成项目编号
//        newMaxCode = PGD_PREFIX + "-" + nowYearMonthDay +"-"+  zeroSerfix;
//        return newMaxCode;
//    }
//}