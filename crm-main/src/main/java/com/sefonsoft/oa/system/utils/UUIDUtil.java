package com.sefonsoft.oa.system.utils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * (UUID生成)
 * @author Aron
 */
public class UUIDUtil {

	private UUIDUtil() {
	}

	private static final UUIDUtil uuidUtil = new UUIDUtil();

	public static String uuid() {
		return uuidUtil.getUuId();
	}

	private String getUuId() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static final long MIN_VALUE = 0x8000000000000000L;

	public static final long MAX_VALUE = 0x7fffffffffffffffL;

	final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
			'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', '-', '_' };

	private static String toUnsignedString(long i, int shift) {
		char[] buf = new char[64];
		int charPos = 64;
		int radix = 1 << shift;
		long mask = radix - 1;
		do {
			buf[--charPos] = digits[(int) (i & mask)];
			i >>>= shift;
		} while (i != 0);
		return new String(buf, charPos, (64 - charPos));
	}

	// j为2的次方，如转成16进制就是4，32进制就是5...
	public static String getRand(long i, int j) {
		return toUnsignedString(i, j);
	}

	// 随机码＋时间戳＋随机码的生成
	public static Long getRand() {
		String str1, str2, str3;
		str1 = getRandStr(1);
		str3 = getRandStr(2);
		str2 = (new Date()).getTime() + "";
		// System.out.println(str1+str2+str3);
		return Long.parseLong(str1 + str2 + str3);
	}

	// 主键生成
	public static String getKey(Random r) {
//		return r.nextInt(1000000000) + "";
		 return getRand(getRand(),6);
	}

	// 生成指定长度的随机串
	public static String getRandStr(Integer length) {
		String str = "";
		while (str.length() != length) {
			str = (Math.random() + "").substring(2, 2 + length);
		}
		return str;
	}

//	public static void main(String[] args) {
//		
//		for (int i = 0; i < 100; i++) {
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//			long l=Long.valueOf(formatter.format(new Date()));
//			Random r = new Random(l);
//			System.out.println(getKey(r)+"-----"+l);
//		}
//	}
}