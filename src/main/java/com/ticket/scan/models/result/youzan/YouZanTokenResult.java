package com.ticket.scan.models.result.youzan;

import com.ticket.scan.models.data.YouzanTokenData;
import com.ticket.scan.models.model.YouzanCommenResult;

public class YouZanTokenResult extends YouzanCommenResult{

	private YouzanTokenData data;

	public YouzanTokenData getData() {
		return data;
	}

	public void setData(YouzanTokenData data) {
		this.data = data;
	}


}
