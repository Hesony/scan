package com.ticket.scan.controller;

import com.ticket.scan.service.impl.RegisterServiceImpl;
import com.ticket.scan.utils.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/register")
public class RegisterController {

	@Autowired
	private RegisterServiceImpl registerService;

	@RequestMapping(value = "/register")
	public String register(@RequestParam("openid") String openid,
						@RequestParam("oAuth2AccessToken") String oAuth2AccessToken,
						Model model, Map<String, String> map) {
		map.put("openid", openid);
		map.put("oAuth2AccessToken", oAuth2AccessToken);
		model.addAllAttributes(map);
		return "register/register"; //跳转到注册页面
	}

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model, Map<String ,String> map) {
		String openid = request.getParameter("openid");
		String authorityId = request.getParameter("authority_id");
		String oAuth2AccessToken = request.getParameter("oAuth2AccessToken");
		log.info("核销员注册：[openid:{}|authority_id:{}|oAuth2AccessToken:{}]",openid,authorityId,oAuth2AccessToken);
		ResultVO resultVO = registerService.register(openid,oAuth2AccessToken,authorityId);
		if ("001".equals(resultVO.getCode())) {
			map.put("msg", resultVO.getMsg());
			model.addAllAttributes(map);
			return "youzan/error";
		}
		map.put("msg", resultVO.getMsg());
		model.addAllAttributes(map);
		return "youzan/success";
	}



}
