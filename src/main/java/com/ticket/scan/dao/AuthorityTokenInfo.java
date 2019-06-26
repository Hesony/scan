package com.ticket.scan.dao;

/**
 * 店铺token信息表
 */
public class AuthorityTokenInfo {
	/** 店铺 ID*/
	private String authorityId;
	/** 令牌 */
	private String accessToken;
	/** 过期时间 */
	private String expires;
	/** 刷新令牌 */
	private String refreshToken;

	public String getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getExpires() {
		return expires;
	}

	public void setExpires(String expires) {
		this.expires = expires;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
