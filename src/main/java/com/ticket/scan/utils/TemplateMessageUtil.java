package com.ticket.scan.utils;


import com.ticket.scan.constants.WechatConstants;
import com.ticket.scan.service.impl.ScheduleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class TemplateMessageUtil {

	@Autowired
	private ScheduleServiceImpl scheduleService;
	private static TemplateMessageUtil templateMessageUtil;

	@PostConstruct
	public void init() {
		templateMessageUtil = this;
		templateMessageUtil.scheduleService = scheduleService;
	}
	/** 发送核销成功模板消息 */
	public static void sendVerifyTemplate(String openid, String title, String price) {
//		String url = String.format(WeiXinConstants.TEMPLATE_URL, "20_9pITBTOCjs5oGqX8tg8TjXVysSXNSQrsBEXRQ9XvQ6mNf5SsRUizk2rxIViIaxrblEj35bjaaZLwtbRzjOplhUyyJQMJrENy-z0ml3Ec6zke56sCV8KQJMFBG1eus6xP5NK2KFqVBMiJtRi7UTYjACABAF");
		String url = String.format(WechatConstants.TEMPLATE_URL, templateMessageUtil.scheduleService.getWechatAccessToken());

		String templeateId = "ciRna7QwULD8Nz5lyQPwIDJgvtpmxB7hBZEAMrxrY2I";
		String time = DateUtil.nowTime();
		String param = "{\n" +
				"           \"touser\":\"" + openid + "\",\n" +
				"           \"template_id\":\"" + templeateId + "\",\n" +
				"           \"data\":{\n" +
				"                   \"first\": {\n" +
				"                       \"value\":\"核销成功\",\n" +
				"                       \"color\":\"#173177\"\n" +
				"                   },\n" +
				"                   \"keyword1\":{\n" +
				"                       \"value\":\"" + time + "\",\n" +
				"                       \"color\":\"#173177\"\n" +
				"                   },\n" +
				"                   \"keyword2\": {\n" +
				"                       \"value\":\"" + title + "\",\n" +
				"                       \"color\":\"#173177\"\n" +
				"                   },\n" +
				"                   \"keyword3\": {\n" +
				"                       \"value\":\"" + price + "\",\n" +
				"                       \"color\":\"#173177\"\n" +
				"                   },\n" +
				"                   \"remark\":{\n" +
				"                       \"value\":\"祝您生活愉快！\",\n" +
				"                       \"color\":\"#173177\"\n" +
				"                   }\n" +
				"           }\n" +
				"       }";
		log.info("{}核销成功发送模板内容：{}", openid, param);
		HttpRequestUtils.httpPost(url, param);
	}

//	public static void main(String[] args) {
//		TemplateMessageUtil.sendVerifyTemplate("o7tpZ1ElPKLXTFq-J83zoytxFb7w", "测试商品", "1.4");
//	}
}
