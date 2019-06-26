package com.ticket.scan.service.impl;

import com.ticket.scan.constants.CommonConstants;
import com.ticket.scan.dao.AuthorityInfo;
import com.ticket.scan.dao.StaffInfo;
import com.ticket.scan.dao.mapper.AuthorityInfoMapper;
import com.ticket.scan.dao.mapper.StaffInfoMapper;
import com.ticket.scan.models.model.SnsapiUserinfo;
import com.ticket.scan.utils.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 核销员注册实现
 */
@Service
public class RegisterServiceImpl {

	@Autowired
	private WechatServiceImpl wechatService;
	@Autowired
	private AuthorityInfoMapper authorityInfoMapper;
	@Autowired
	private StaffInfoMapper staffInfoMapper;

	public ResultVO<String> register(String openid, String oAuth2AccessToken, String authorityId) {
		ResultVO resultVO = new ResultVO();
		resultVO.setCode("000");
		resultVO.setMsg("注册成功");

		SnsapiUserinfo snsapiUserinfo = wechatService.getSnsapiUserinfo(oAuth2AccessToken, openid);
		AuthorityInfo authorityInfo = new AuthorityInfo();
		StaffInfo staffInfo = new StaffInfo();

		authorityInfo = authorityInfoMapper.selectAuthorityInfoById(authorityId);
		staffInfo = staffInfoMapper.selectByOpenid(openid);
		if (null == authorityInfo) {
			resultVO.setCode("001");
			resultVO.setMsg("该店铺(邀请码)不存在");
			return resultVO;
		}
		if (null != staffInfo) {
			resultVO.setCode("001");
			resultVO.setMsg("您已在该店铺下注册过核销员，详情联系管理员");
			return resultVO;
		}

		StaffInfo staffInfoNew = new StaffInfo();
		BeanUtils.copyProperties(snsapiUserinfo, staffInfoNew);
		staffInfoNew.setAuthorityId(authorityId);
		staffInfoNew.setStaff(CommonConstants.NOTYET);
		staffInfoMapper.insertByObject(staffInfoNew);
		return resultVO;
	}
}
