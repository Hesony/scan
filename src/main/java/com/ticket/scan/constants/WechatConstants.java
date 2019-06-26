package com.ticket.scan.constants;

public class WechatConstants {

	//获取微信access_token
	public static final String GET_ACCESS_TOKEN_URL =
			"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

	//获取jsapi_ticket票据
	public static final String GET_JSAPI_TICKET_URL =
			"https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

	//oauth2网页授权获取code
	public static final String CONNECT_OAUTH2_AUTHORIZE_URL =
			"https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";

	//code换取oauth2的access token
	public static final String OAUTH2_ACCESS_TOKEN_URL =
			"https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

	//拉取用户信息(需scope为 snsapi_userinfo)
	public static final String SNSAPI_USERINFO_URL =
			"https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

	//微信模板消息
	public static final String TEMPLATE_URL =
			"https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

}
