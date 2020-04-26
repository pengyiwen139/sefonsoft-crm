package com.sefonsoft.oa.system.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liwenyi
 * Date: 2020/3/26
 * Desc:
 */
public class UtilSJMethod {

    public static Object object = new Object();

    public static AtomicInteger sjNumber = new AtomicInteger();
    public static int dataInt;

    public static final String SJ_PREFIX = "SJ";

    public static String getSJnumber(String maxSJ) {

        synchronized (object) {
            //获取当天值
            Integer courrentDateTimeKey = DateUtils.getCourrentDateTimeKey();
            String sjPrefix = SJ_PREFIX + courrentDateTimeKey;
            //获取当天当前最大值
            //String maxSJ = bizOpportsDao.getMaxSJ(sjPrefix);
            //当天没有直接生成新的单号
            if (StringUtils.isEmpty(maxSJ) && courrentDateTimeKey != dataInt) {
                dataInt = courrentDateTimeKey;
                sjNumber.set(0);
                return sjPrefix + StringUtils.autoGenericNumber(4, sjNumber.incrementAndGet());
            }
            if (StringUtils.isEmpty(maxSJ) && courrentDateTimeKey == dataInt) {
                return sjPrefix + StringUtils.autoGenericNumber(4, sjNumber.incrementAndGet());
            }
            String number = maxSJ.substring(10, 14);
            //校验流水号是否合法
            if (!StringUtils.isNumber(number) && courrentDateTimeKey != dataInt) {
                dataInt = courrentDateTimeKey;
                sjNumber.set(0);
                return SJ_PREFIX + StringUtils.autoGenericNumber(4, sjNumber.incrementAndGet());
            }

            if (!StringUtils.isNumber(number) && courrentDateTimeKey == dataInt) {
                return SJ_PREFIX + StringUtils.autoGenericNumber(4, sjNumber.incrementAndGet());
            }

            //系统重启初始化参数
            int anInt = Integer.parseInt(number);
            if (dataInt != courrentDateTimeKey) {
                dataInt = courrentDateTimeKey;
                sjNumber.set(anInt);
            }
            if (anInt > sjNumber.get()) {
                sjNumber.set(anInt);
            }
            //正常单号
            return sjPrefix + StringUtils.autoGenericNumber(4, sjNumber.incrementAndGet());
        }
    }
}
