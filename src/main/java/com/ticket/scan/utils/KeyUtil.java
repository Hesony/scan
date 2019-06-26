package com.ticket.scan.utils;

import java.util.Random;

public class KeyUtil {

	public static synchronized String genUniqueKey() {
		Random random = new Random();
		Integer number = random.nextInt(90) + 10;

		return System.currentTimeMillis() + String.valueOf(number);
	}

	public static void main(String[] args) {
		String a = KeyUtil.genUniqueKey();
		System.out.print(a + "||||" + System.currentTimeMillis());
	}
}
