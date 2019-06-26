package com.ticket.scan.models.postParam;

/**
 * @author xiaonan.he
 * 通过code获取店铺accesstoken
 * 通过refresh_token刷新
 */
public class Code {
	private String client_id;
	private String client_secret;
	private String authorize_type;
	private String code;
	private String refresh_token;

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}

	public String getAuthorize_type() {
		return authorize_type;
	}

	public void setAuthorize_type(String authorize_type) {
		this.authorize_type = authorize_type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
}
