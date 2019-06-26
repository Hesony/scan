package com.ticket.scan.controller;

import com.ticket.scan.config.ProjectUrlConfig;
import com.ticket.scan.models.result.wechat.OAuth2AccessTokenResult;
import com.ticket.scan.service.impl.WechatServiceImpl;
import com.ticket.scan.utils.JsSignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping(value = "/wechat")
public class WechatController {

	@Autowired
	private ProjectUrlConfig projectUrlConfig;

	@Autowired
	private WechatServiceImpl wechatService;

	@RequestMapping(value = "/authorize")
	public String authorize(@RequestParam("returnUrl") String returnUrl) {
		log.info("核销微信认证请求开始，重定向地址为[{}]", returnUrl);
		String url = projectUrlConfig.getWechatAuthorizeUrl() + "/wechat/userInfo";
		String redirectUrl = wechatService.oauth2buildAuthorizationUrl(url, "snsapi_userinfo", returnUrl);
		log.info("获取code请求地址为:[{}]", redirectUrl);
		return "redirect:" + redirectUrl;
	}

	@RequestMapping(value = "/userInfo")
	public String userInfo(@RequestParam("code") String code,
						   @RequestParam("state") String returnUrl) {
		log.info("获取到微信code值为[{}]", code);
		OAuth2AccessTokenResult oAuth2AccessTokenResult;
		oAuth2AccessTokenResult =wechatService.oauth2getAccessToken(code);
		//获取openid
		String openid = oAuth2AccessTokenResult.getOpenId();
		String oAuth2AccessToken = oAuth2AccessTokenResult.getAccessToken();
		String param = "?openid=" + openid + "&oAuth2AccessToken=" + oAuth2AccessToken;
		log.info("openid为：[{}]", openid);
		log.info("获取用户信息后重定向地址为：[{}{}]", returnUrl, param);
		return "redirect:" + returnUrl + param ;
	}

	/**
	 *调用微信js扫一扫接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/scan")
	public String scan(HttpServletRequest request,Map<String, String> map, Model model) {
		String openid = request.getParameter("openid");
		String oAuth2AccessToken = request.getParameter("oAuth2AccessToken");
		String url = request.getRequestURL().toString() + "?openid=" + openid + "&oAuth2AccessToken=" + oAuth2AccessToken;
		Map<String, String> resultMap = new HashMap<>(16);
		log.info("url={}", url);
		resultMap = JsSignUtil.sign(url);
		map.put("appId", resultMap.get("appId"));
		map.put("nonceStr", resultMap.get("nonceStr"));
		map.put("timestamp", resultMap.get("timestamp"));
		map.put("signature", resultMap.get("signature"));
		map.put("openid", openid);//新增返回openid
		model.addAllAttributes(map);
		return "wechat/scan";
	}


}
