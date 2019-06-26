package com.ticket.scan.service;

import com.ticket.scan.models.result.wechat.OAuth2AccessTokenResult;
import com.ticket.scan.models.model.SnsapiUserinfo;

public interface WechatService {


	/**
	 * 获取access_token
	 * @return
	 */
	String getAccessToken();

	/**
	 * 获取签名ticket
	 */
	String getJsapiTicket(String accessToken);

	/**
	 * 构造oauth2授权的url连接
	 * @param redirectURI 用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
	 * @return url
	 */
	String oauth2buildAuthorizationUrl(String redirectURI, String scope, String state);

	/**
	 * 获取网页授权OAuth2AccessToken
	 * @param code
	 * @return
	 */
	OAuth2AccessTokenResult oauth2getAccessToken(String code);

	/**
	 * 获取微信用户信息
	 * @param oAuth2AccessToken
	 * @return
	 */
	SnsapiUserinfo getSnsapiUserinfo(String oAuth2AccessToken, String openid);
}
