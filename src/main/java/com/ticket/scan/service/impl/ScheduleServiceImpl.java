package com.ticket.scan.service.impl;

import com.ticket.scan.config.WechatAccountConfig;
import com.ticket.scan.dao.AuthorityTokenInfo;
import com.ticket.scan.dao.mapper.AuthorityTokenInfoMapper;
import com.ticket.scan.models.data.YouzanTokenData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @定时任务获取token等基础信息
 * @author xiaonan.he
 */
@Slf4j
@Component
public class ScheduleServiceImpl implements InitializingBean {

	@Autowired
	private WechatServiceImpl wechatService;
	@Autowired
	private YouzanServiceImpl youzanService;
	@Autowired
	private AuthorityTokenInfoMapper mapper;

	private final ScheduledThreadPoolExecutor STPE_WX_TOKEN = new ScheduledThreadPoolExecutor(1);

	private final ScheduledThreadPoolExecutor STPE_WX_TICKET = new ScheduledThreadPoolExecutor(1);

	private final ScheduledThreadPoolExecutor STPE_YZ = new ScheduledThreadPoolExecutor(1);

	/** 配置刷新时间 单位：（秒） */
	private static long wechatPeriod = 3600;

	/** 配置刷新时间 单位：（小时） */
	private static long youZanPeriod = 1;

	@Override
	public void afterPropertiesSet() throws Exception {
		STPE_WX_TOKEN.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				/**
				 * 定时获取accessToken并存到内存
				 */
				log.info("[获取获取到最新的accessToken开始...]");
				try {
					String accessTokenNew = wechatService.getAccessToken();
					wechatAccessToken = accessTokenNew;
					log.info("[微信accessToken为：{}]", wechatAccessToken);
				}catch (Exception e) {
					log.error("[获取获取到最新的微信accessToken异常]");
				}

				log.info("[获取获取到最新的微信ticket开始...]");
				try {
					String ticketNew = wechatService.getJsapiTicket(wechatAccessToken);
					ticket = ticketNew;
					log.info("[JsapiTicket为: {}]", ticket);
				}catch (Exception e) {
					log.error("[获取获取到最新的微信ticket异常]");
				}
			}
		},0, wechatPeriod, TimeUnit.SECONDS);

		STPE_YZ.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				//定时刷新token和refresh_token token有效期7天，refresh有效期28天
				List<AuthorityTokenInfo> selectList = null;
				try {
					log.info("有赞开始刷新所有店铺token");
					selectList = mapper.selectAllAcceccToken();
					Map<String, YouzanTokenData> tokenDataTmp = null;
					if (null != selectList && selectList.size() > 0) {
						tokenDataTmp = new ConcurrentHashMap<>(32);
						for (AuthorityTokenInfo authorityTokenInfo: selectList) {
							String refresh_token = authorityTokenInfo.getRefreshToken();
							YouzanTokenData youzanTokenData = youzanService.refreshAccessToken(refresh_token);
							if (youzanTokenData.getAuthority_id() != null) {
								tokenDataTmp.put(youzanTokenData.getAuthority_id(), youzanTokenData);
							}
						}
					}
					if (null != tokenDataTmp && tokenDataTmp.size() > 0) {
						YOUZAN_TOKEN_MAP = tokenDataTmp;
					}
					log.info("刷新所有店铺token结束, size:{}", YOUZAN_TOKEN_MAP.size());
				} catch (Exception e) {
					log.error("刷新所有店铺token异常, exception:{}", e.getMessage());
				}
			}
		}, 0, youZanPeriod, TimeUnit.HOURS);

	}

	private static String wechatAccessToken;

	private static String ticket;

	private static Map<String, YouzanTokenData> YOUZAN_TOKEN_MAP = new ConcurrentHashMap<>(32);

	public  Map<String, YouzanTokenData> getYouzanTokenMap() {
		return YOUZAN_TOKEN_MAP;
	}



	public  String getWechatAccessToken() {
		return wechatAccessToken;
	}

	public  String getTicket() {
		return ticket;
	}

	public  void setTicket(String ticket) {
		ScheduleServiceImpl.ticket = ticket;
	}

}
