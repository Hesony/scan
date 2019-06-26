package com.ticket.scan.utils;

import com.ticket.scan.service.impl.ScheduleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.spring5.context.SpringContextUtils;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 官方给的使用js的验证工具
 * @author Administrator
 *
 */
@Slf4j
public class JsSignUtil {

	@Autowired
	private ScheduleServiceImpl service;
	private static JsSignUtil jsSignUtil;

	@PostConstruct
	public void init() {
		jsSignUtil = this;
		jsSignUtil.service = this.service;
	}

	public static Map<String, String> sign(String url) {
		String jsapi_ticket = jsSignUtil.service.getTicket();

		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		//注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket +
				"&noncestr=" + nonce_str +
				"&timestamp=" + timestamp +
				"&url=" + url;
//		log.info("string1=" + string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
//		ret.put("appId", WeiXinConstants.APP_ID);

		log.info("1.ticket(原始)=" + jsapi_ticket + "\n" +
				"2.url=" + ret.get("url") +"\n" +
				"3.jsapi_ticket（处理后）=" + ret.get("jsapi_ticket") +"\n" +
				"4.nonceStr=" + ret.get("nonceStr") +"\n" +
				"5.signature=" + ret.get("signature") +"\n" +
				"6.timestamp=" + ret.get("timestamp"));
		return ret;
	}


	/**
	 * 随机加密
	 *
	 * @param hash
	 * @return
	 */
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	/**
	 * 产生随机串--由程序自己随机产生
	 *
	 * @return
	 */
	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 由程序自己获取当前时间
	 *
	 * @return
	 */
	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

}