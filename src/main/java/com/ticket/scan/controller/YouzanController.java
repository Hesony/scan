package com.ticket.scan.controller;

import com.ticket.scan.service.impl.YouzanServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/youzan")
@Slf4j
public class YouzanController {

	@Autowired
	private YouzanServiceImpl youzanService;

	/**
	 * 有赞店铺授权
	 * @param code
	 */
	@RequestMapping(value = "/notify")
	@ResponseBody
	public void youzanNotify(@RequestParam("code") String code) {
		log.info("[有赞授权店铺code为:{}]", code);
		//将获取的code入库用来获取店铺id和accecctoken
		youzanService.getAccessToken(code);
	}

	/**
	 * 核销电子券码
	 * @param scancode
	 * @param openid
	 * @param model
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/verifyticket")
	public String doscan(@RequestParam("scancode") String scancode,
						 @RequestParam("openid") String openid,
						 Model model, Map<String, String> map) {
		log.info("核销的二维码为：[{}]", scancode);
		//todo: 1.判断openid是否在店铺下存在；2.若存在根据openid获取店铺id以此获取店铺的最新token

		//todo:调用核销service

		return "";
	}
}
