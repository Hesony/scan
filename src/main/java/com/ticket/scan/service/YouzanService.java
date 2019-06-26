package com.ticket.scan.service;

import com.ticket.scan.models.data.YouzanTokenData;
public interface YouzanService {


	/**
	 * 通过code获取有赞店铺信息，包括accessToken
	 * @param code
	 * @return
	 */
	void getAccessToken(String code);

	/**
	 * 通过refresh_token刷新accessToken
	 * @param refresh_token
	 * @return
	 */
	YouzanTokenData refreshAccessToken(String refresh_token);



}
