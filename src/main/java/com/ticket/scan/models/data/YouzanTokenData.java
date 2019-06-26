package com.ticket.scan.models.data;

public class YouzanTokenData {
	/** access_token 的有效时长，时间戳 */
	private String expires;

	/** access_token 最终的访问范围 */
	private String scope;

	/** 用于调用 API 的 access_token */
	private String access_token;

	/** 授权主体id，即店铺id */
	private String authority_id;

	/** 用于延长 access_token 有效时间的刷新令牌（过期时间：28 天），在刷新后access_token会返回新的refresh_token */
	private String refresh_token;

	public String getExpires() {
		return expires;
	}

	public void setExpires(String expires) {
		this.expires = expires;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getAuthority_id() {
		return authority_id;
	}

	public void setAuthority_id(String authority_id) {
		this.authority_id = authority_id;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
}
