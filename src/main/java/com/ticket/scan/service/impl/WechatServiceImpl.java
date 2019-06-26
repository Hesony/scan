package com.ticket.scan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ticket.scan.config.WechatAccountConfig;
import com.ticket.scan.constants.WechatConstants;
import com.ticket.scan.models.result.wechat.OAuth2AccessTokenResult;
import com.ticket.scan.models.model.SnsapiUserinfo;
import com.ticket.scan.models.result.wechat.WechatAccessTokenResult;
import com.ticket.scan.models.result.wechat.WechatTicketResult;
import com.ticket.scan.service.WechatService;
import com.ticket.scan.utils.HttpRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WechatServiceImpl implements WechatService {

	@Autowired
	private WechatAccountConfig config;

	@Override
	public String getAccessToken() {
		WechatAccessTokenResult result = new WechatAccessTokenResult();
		String url = String .format(WechatConstants.GET_ACCESS_TOKEN_URL, config.getMpAppId(), config.getMpAppSecret());
		log.info(url);
		String resultParam = HttpRequestUtils.httpGet(url);
		result = JSONObject.parseObject(resultParam, WechatAccessTokenResult.class);
		return result.getAccess_token();
	}

	@Override
	public String getJsapiTicket(String accessToken) {
		WechatTicketResult result = new WechatTicketResult();
		String url = String.format(WechatConstants.GET_JSAPI_TICKET_URL, accessToken);
		String resultParam = HttpRequestUtils.httpGet(url);
		result = JSONObject.parseObject(resultParam, WechatTicketResult.class);
		return result.getTicket();
	}

	@Override
	public String oauth2buildAuthorizationUrl(String redirectURI, String scope, String state) {
		return String.format(WechatConstants.CONNECT_OAUTH2_AUTHORIZE_URL, config.getMpAppId(),redirectURI, scope, state);
	}

	@Override
	public OAuth2AccessTokenResult oauth2getAccessToken(String code) {
		String url =  String.format(WechatConstants.OAUTH2_ACCESS_TOKEN_URL, config.getMpAppId(),
				config.getMpAppSecret(), code);
		OAuth2AccessTokenResult oAuth2AccessTokenResult = new OAuth2AccessTokenResult();
		String result = HttpRequestUtils.httpGet(url);
		oAuth2AccessTokenResult = JSONObject.parseObject(result, OAuth2AccessTokenResult.class);
		return oAuth2AccessTokenResult;
	}


	@Override
	public SnsapiUserinfo getSnsapiUserinfo(String oAuth2AccessToken , String openid) {
		String url = String.format(WechatConstants.SNSAPI_USERINFO_URL, oAuth2AccessToken, openid);
		SnsapiUserinfo snsapiUserinfo;
		String result = HttpRequestUtils.httpGet(url);
		snsapiUserinfo = JSONObject.parseObject(result, SnsapiUserinfo.class);
		return snsapiUserinfo;
	}
}
