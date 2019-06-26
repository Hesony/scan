package com.ticket.scan.models.result.youzan;

import com.ticket.scan.models.data.ShopInfoData;
import com.ticket.scan.models.model.YouzanCommenResult;

/**
 * 查询店铺信息返回结果
 */
public class ShopInfoResult extends YouzanCommenResult{

	private ShopInfoData data;

	public ShopInfoData getData() {
		return data;
	}

	public void setData(ShopInfoData data) {
		this.data = data;
	}
}
