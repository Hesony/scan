package com.ticket.scan.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	//获取当前时间并且格式化
	public static String nowTime() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}

	public static void main(String[] args) {
		System.out.println(DateUtil.nowTime());
	}
}
