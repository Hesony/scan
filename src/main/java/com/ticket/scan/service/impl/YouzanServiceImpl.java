package com.ticket.scan.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ticket.scan.config.YouzanAccountConfig;
import com.ticket.scan.constants.YouZanConstants;
import com.ticket.scan.dao.AuthorityInfo;
import com.ticket.scan.dao.AuthorityTokenInfo;
import com.ticket.scan.dao.mapper.AuthorityInfoMapper;
import com.ticket.scan.dao.mapper.AuthorityTokenInfoMapper;
import com.ticket.scan.models.data.YouzanTokenData;
import com.ticket.scan.models.postParam.Code;
import com.ticket.scan.models.result.youzan.ShopInfoResult;
import com.ticket.scan.models.result.youzan.YouZanTokenResult;
import com.ticket.scan.service.YouzanService;
import com.ticket.scan.utils.HttpRequestUtils;
import com.ticket.scan.utils.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class YouzanServiceImpl implements YouzanService {

	@Autowired
	private YouzanAccountConfig config;
	@Autowired
	private AuthorityTokenInfoMapper authorityTokenInfoMapper;
	@Autowired
	private AuthorityInfoMapper authorityInfoMapper;
	@Autowired
	private ScheduleServiceImpl scheduleService;

	@Override
	public void getAccessToken(String code) {

		//根据code获取token并入库
		AuthorityTokenInfo authorityTokenInfo = getToken(code);

		//根据店铺ID新增或者更新店铺信息
		addAuthorityInfo(authorityTokenInfo.getAuthorityId());

	}


	@Override
	public YouzanTokenData refreshAccessToken(String refresh_token) {
		//刷新令牌逻辑
		Code code = new Code();
		code.setClient_id(config.getClientId());
		code.setClient_secret(config.getClientSecret());
		code.setAuthorize_type("refresh_token");
		code.setRefresh_token(refresh_token);
		String param = JSON.toJSONString(code);
		String result = HttpRequestUtils.httpPost(YouZanConstants.ACCESS_TOKEN_URL, param);
		YouZanTokenResult youZanTokenResult = JSONObject.parseObject(result, YouZanTokenResult.class);
		if (youZanTokenResult.getCode() != 200) {
			log.error("刷新令牌异常");
			return null;
		}

		//最新令牌信息入库
		AuthorityTokenInfo authorityTokenInfo = new AuthorityTokenInfo();
		authorityTokenInfo.setAuthorityId(youZanTokenResult.getData().getAuthority_id());
		authorityTokenInfo.setExpires(youZanTokenResult.getData().getExpires());
		authorityTokenInfo.setAccessToken(youZanTokenResult.getData().getAccess_token());
		authorityTokenInfo.setRefreshToken(youZanTokenResult.getData().getRefresh_token());
		authorityTokenInfoMapper.updateById(authorityTokenInfo);
		return youZanTokenResult.getData();
	}


	//获取token
	private AuthorityTokenInfo getToken(String code) {
		Code requestCode = new Code();
		requestCode.setCode(code);
		requestCode.setClient_id(config.getClientId());
		requestCode.setClient_secret(config.getClientSecret());
		requestCode.setAuthorize_type("authorization_code");
		String param = JSON.toJSONString(requestCode);
		String result = HttpRequestUtils.httpPost(YouZanConstants.ACCESS_TOKEN_URL, param);
		YouZanTokenResult youZanResult = JSONObject.parseObject(result, YouZanTokenResult.class);
		//如果返回码不为200说明返回报错
		if (youZanResult.getCode() != 200) {
			log.error(youZanResult.getMessage().toString());
			return null;
		}

		AuthorityTokenInfo info = new AuthorityTokenInfo();
		info.setAuthorityId(youZanResult.getData().getAuthority_id());
		info.setAccessToken(youZanResult.getData().getAccess_token());
		info.setRefreshToken(youZanResult.getData().getRefresh_token());
		info.setExpires(youZanResult.getData().getExpires());

		//如果已存在该店铺，则刷新店铺的token，否则插入新店铺id以及相关信息
		if (authorityTokenInfoMapper.selectById(info.getAuthorityId()) == 1) {
			authorityTokenInfoMapper.updateById(info);
			return info;
		}
		authorityTokenInfoMapper.insertByObject(info);

		return info;
	}

	//新增或者更新店铺信息
	private void addAuthorityInfo(String authorityId) {
		AuthorityTokenInfo authorityTokenInfo = authorityTokenInfoMapper.selectAcceccToken(authorityId);
		//todo:获取token流程可能需要修改
		String url = String.format(YouZanConstants.SHOP_INFO_URL, authorityTokenInfo.getAccessToken());
		ShopInfoResult result = JSONObject.parseObject(HttpRequestUtils.httpGet(url), ShopInfoResult.class);
		AuthorityInfo authorityInfo = new AuthorityInfo();

		BeanUtils.copyProperties(result.getData(), authorityInfo);

		//如果获取到的sid（店铺id已存在）则update最新数据，否则插入
		if (authorityInfoMapper.selectById(authorityInfo.getId()) == 1) {
			authorityInfoMapper.updateAuthorityInfo(authorityId);
			return;
		}
		authorityInfoMapper.insertByObject(authorityInfo);
	}

	/**
	 * 1.判断openid是否是核销员
	 * 2.如果是核销员则返回其店铺的token
	 * todo
	 * @param openid
	 * @return
	 */
	private ResultVO<String> ownAccessToken(String openid) {
		ResultVO<String> resultVO = new ResultVO<>();
		resultVO.setCode("000");
		Map<String, YouzanTokenData> map = scheduleService.getYouzanTokenMap();

		return resultVO;
	}


}
