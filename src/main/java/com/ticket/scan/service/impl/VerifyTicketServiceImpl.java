package com.ticket.scan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ticket.scan.constants.YouZanConstants;
import com.ticket.scan.utils.HttpRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VerifyTicketServiceImpl {

	@Autowired
	private ScheduleServiceImpl scheduleService;

	public void getTickets(String scancode, String token) {
		try {
			String url = String.format(YouZanConstants.GET_VERIFYTICKET_URL, scheduleService.getYouZanAccessToken(), scancode);
			String resultJson = HttpRequestUtils.httpGet(url);
			YouZanResult result = JSONObject.parseObject(resultJson, YouZanResult.class);
			// TODO: 2019/5/14  
		} catch (Exception e) {
			log.error("获取电子券码异常");
		}
	}

}
