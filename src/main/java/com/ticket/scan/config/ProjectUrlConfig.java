package com.ticket.scan.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "project")
public class ProjectUrlConfig {

	/**
	 * 微信公众平台授权url
	 */
	public String wechatAuthorizeUrl;

	public String getWechatAuthorizeUrl() {
		return wechatAuthorizeUrl;
	}

	public void setWechatAuthorizeUrl(String wechatAuthorizeUrl) {
		this.wechatAuthorizeUrl = wechatAuthorizeUrl;
	}
}
