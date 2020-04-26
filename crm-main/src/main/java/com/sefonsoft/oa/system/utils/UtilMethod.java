package com.sefonsoft.oa.system.utils;

import com.google.common.collect.ImmutableList;
import com.sefonsoft.oa.domain.user.LoginUserDTO;
import com.sefonsoft.oa.system.emun.Grade;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ：Aron
 * @version : 1.0
 * @description：生成部门编号
 * @date ：2019/11/6
 */
public class UtilMethod {


    public static final List<String> BUSINESS_LIST = Arrays.asList(
        "安监",
        "博物",
        "部委",
        "财政",
        "城管",
        "畜牧",
        "大数据局",
        "党政",
        "地理信息",
        "地铁",
        "地下管廊",
        "地震",
        "电网",
        "电子政务",
        "发改委",
        "法院",
        "法制办",
        "防震减灾",
        "扶贫",
        "港口",
        "高校",
        "工会",
        "工商",
        "工信部/工信厅",
        "工业经济",
        "公安",
        "公共资源",
        "公检法",
        "管委会",
        "广电",
        "规划局",
        "国企",
        "国土",
        "国资委",
        "海关",
        "航空",
        "航运",
        "核能",
        "环保",
        "环境",
        "机场",
        "机关事务管理局",
        "集成商",
        "纪检监察",
        "监狱",
        "检察院",
        "检验检疫",
        "建设",
        "建筑",
        "交警",
        "交通",
        "教育",
        "戒毒",
        "金融",
        "经信",
        "军工",
        "勘测院",
        "科技部",
        "科协",
        "科研",
        "空管",
        "口岸",
        "粮食局",
        "林业",
        "旅游",
        "煤监",
        "民航",
        "民生",
        "民政",
        "能源",
        "电力",
        "农业",
        "其它",
        "企业",
        "气象",
        "全行业",
        "人防",
        "人社",
        "商委",
        "商务",
        "设计院/研究院",
        "涉密",
        "审计",
        "生态合作部",
        "石化",
        "石油",
        "食药监",
        "水利",
        "水文",
        "水务",
        "税务",
        "司法",
        "体育",
        "通信",
        "统计",
        "投资",
        "退役军人",
        "网信办",
        "卫计委",
        "卫生疾控",
        "文旅",
        "无线电",
        "武警",
        "物流",
        "消防",
        "协会",
        "新闻传媒",
        "信访",
        "学校",
        "烟草",
        "研究院",
        "药监局",
        "医疗",
        "医院",
        "音视频",
        "应急",
        "邮政",
        "院校",
        "运营商",
        "证监会",
        "政法委",
        "政府",
        "政务",
        "知识产权",
        "制造",
        "质监",
        "园区",
        "住建",
        "自然",
        "组织"
    );

    /*
     * 把10002首位的1去掉的实现方法：
     * @param maxCode
     * @return 生成部门编号
     */
    public static String getDptCode(String maxCode) {
        String dptCode = "";
        String codePfix = "BM";
        if (maxCode != null && maxCode.contains(codePfix)) {
            // 截取字符串最后四位，结果:0001
            String uidEnd = maxCode.substring(2, 6);
            // 把String类型的0001转化为int类型的1
            int endNum = Integer.parseInt(uidEnd);
            // 结果10002
            int tmpNum = 10000 + endNum + 1;
            // 把10002首位的1去掉，再拼成1601260002字符串
            dptCode = codePfix + UtilMethod.subStr("" + tmpNum, 1);
        } else {
            dptCode = codePfix + "0001";

        }
        return dptCode;
    }
    /*
     * 把10002首位的1去掉的实现方法：
     * @param str
     * @param start
     * @return
     */
    public static String subStr(String str, int start) {
        if (str == null || str.equals("") || str.length() == 0) {
            return "";
        }
        if (start < str.length()) {
            return str.substring(start);
        } else {
            return "";
        }

    }

    /**
     * 根据已有流水编号生成+1的四位数字流水编号，若传null和0则默认生成0001
     * @param maxCode
     * @return
     */
    public static String createFlowNumId(String maxCode) {
        if (ObjTool.isNotNull(maxCode)) {
            // 把String类型的0001转化为int类型的1
            int endNum = Integer.parseInt(maxCode);
            // 结果10002
            int tmpNum = 10000 + endNum + 1;
            // 把10002首位的1去掉，再拼成1601260002字符串
            maxCode = UtilMethod.subStr("" + tmpNum, 1);
        } else {
            maxCode = "0001";
        }
        return maxCode;
    }


    public static List<String> generatorAvailableDeptIds(LoginUserDTO loginUserDTO) {

        //==是领导可以查询自己部门和数据授权部门的统计信息
        //是否查询指定的部门？？
        List dataDeptIdList = loginUserDTO.getDataDeptIdList();
//        if (!loginUserDTO.getGradeId().equals(Grade.LD.getCode())) {
//            return null;
//        }
        if(dataDeptIdList==null || dataDeptIdList.size()==0){
          return null;
        }
        //如果不是，返回授权的部门
        Set<String> set = new HashSet<>();
        set.addAll(dataDeptIdList);
        return ImmutableList.copyOf(set);

    }

    /**
     * 测试部门编号生成方法
     */
    public static void main(String[] args) {
        String maxCode = "0";
        String dptCode = createFlowNumId(maxCode);

        System.out.println(dptCode);
    }


}
