package com.ticket.scan.constants;

public class YouZanConstants {


	/**获取或者刷新token( 令牌 ) */

	public static final String ACCESS_TOKEN_URL = "https://open.youzanyun.com/auth/token";

	public static final String SHOP_INFO_URL = "https://open.youzanyun.com/api/youzan.shop.get/3.0.0?access_token=%s";

	/**获取电子卡券信息 url */

	public static final String GET_VERIFYTICKET_URL = "https://open.youzanyun.com/api/youzan.trade.virtualticket.get/3.0.0?access_token=%s&code=%s";

	/** 电子卡券单个码券核销 url */

	public static final String VERIFYTICKET_URL = "https://open.youzanyun.com/api/youzan.trade.virtualticket.verifyticket/3.0.0?access_token=%s&ticket_code=%s";

	/** 交易订单详情4.0接口 url */

	public static final String TRADE_URL = "https://open.youzanyun.com/api/youzan.trade.get/4.0.0?access_token=%s&tid=%s";
}
