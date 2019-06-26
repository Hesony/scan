package com.ticket.scan.utils;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

@Slf4j
public class HttpRequestUtils {

	/**
	 * http连接相关参数
	 */
	private static HttpParams httpParams;

	/**
	 * http线程池管理
	 */
	private static PoolingClientConnectionManager poolingClientConnectionManager;

	/**
	 * 连接超时时间
	 */
	private static int connectTimeout = 2000;
	/**
	 * 读取超时时间
	 */
	private static int soTimeout = 8000;

	/**
	 * 连接池中连接请求执行被阻塞的超时时间
	 */
	public static long connManagerTimeout = 5000L;

	/**
	 * 连接池里的最大连接数
	 */
	private static int maxTotal = 100;

	/**
	 * 每个路由的默认最大连接数
	 */
	private static int maxPerRoute = 40;

	/**
	 * http失败重试次数
	 */
	private static int exceptionRetryTimes = 0;

	/**
	 * http失败等待时间
	 */
	private static int exceptionWaitTime = 4000;

	protected static HttpClient getHttpClient() {
		if (poolingClientConnectionManager == null || httpParams == null) {
			initTimeLimit();
			initConnectionParams();
		}

		HttpClient httpClient = new DefaultHttpClient(
				poolingClientConnectionManager, httpParams);

		return httpClient;
	}

	/**
	 * 针对超时时间及重试次数，做一个最大数限制，防止参数误填造成http阻塞 超时最大60秒，重试次数最大10次
	 */
	protected static void initTimeLimit() {
		exceptionRetryTimes = exceptionRetryTimes > 10 ? 10
				: exceptionRetryTimes;
		exceptionWaitTime = exceptionWaitTime > 60 * 1000 ? 60 * 1000
				: exceptionWaitTime;

		soTimeout = soTimeout > 60 * 1000 ? 60 * 1000 : soTimeout;
		connectTimeout = connectTimeout > 60 * 1000 ? 60 * 1000
				: connectTimeout;
	}

	protected static void initConnectionParams() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory
				.getSocketFactory()));

		httpParams = new BasicHttpParams();
		httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				connectTimeout);
		httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
		httpParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		httpParams.setParameter(ClientPNames.CONN_MANAGER_TIMEOUT,
				connManagerTimeout);

		poolingClientConnectionManager = new PoolingClientConnectionManager(
				schemeRegistry);

		poolingClientConnectionManager.setMaxTotal(maxTotal);
		poolingClientConnectionManager.setDefaultMaxPerRoute(maxPerRoute);

		log.info("初始化参数：[connectTimeout=" + connectTimeout + "][soTimeout="
				+ soTimeout + "][maxTotal=" + maxTotal + "][maxPerRoute="
				+ maxPerRoute + "][exceptionRetryTimes=" + exceptionRetryTimes
				+ "][exceptionWaitTime=" + exceptionWaitTime + "]");

	}

	/**
	 * post请求
	 *
	 * @param url
	 *            url地址
	 * @param jsonParam
	 *            参数
	 * @param noNeedResponse
	 *            不需要返回结果
	 * @return
	 */
	public static String httpPost(String url, String jsonParam,
								  boolean noNeedResponse) {
		// post请求返回结果
		//logger.info("语音电话请求参数:{},url:{}",jsonParam,url);
		String resultStr = "";
		HttpClient httpClient = getHttpClient();
		HttpPost method = new HttpPost(url);
		try {
			if (null != jsonParam) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(jsonParam, "utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				method.setEntity(entity);
			}
			HttpResponse result = httpClient.execute(method);
			url = URLDecoder.decode(url, "UTF-8");
			/** 请求发送成功，并得到响应 **/
			if (result != null) {
				/** 读取服务器返回过来的json字符串数据 **/
				resultStr = EntityUtils.toString(result.getEntity(), "UTF-8");
			}
		} catch (IOException e) {
			log.error("post请求提交失败:" + url, e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		log.info("resultStr:{}",resultStr);
		return resultStr;
	}

	/**
	 * 发送 POST 请求（HTTP），参数为 Key-Value 形式
	 * @param url API接口url
	 * @param params 参数map
	 * @return
	 */
	public static String httpPost(String url, Map<String, Object> params) {
		String resultStr = "";
		HttpClient httpClient = getHttpClient();
		HttpPost method = new HttpPost(url);
		try {
			if(null!=params&&!params.isEmpty()){
				List<NameValuePair> pairList = new ArrayList<>(params.size());
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
							.getValue().toString());
					pairList.add(pair);
				}
				method.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
			}
			HttpResponse result = httpClient.execute(method);
			if (result != null) {
				/** 读取服务器返回过来的json字符串数据 **/
				resultStr = EntityUtils.toString(result.getEntity(), "UTF-8");
			}
		} catch (IOException e) {
			log.error("post请求提交失败:" + url, e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		log.info("resultStr:{}",resultStr);
		return resultStr;
	}


	/**
	 * 发送get请求
	 *
	 * @param url
	 *            路径
	 * @return
	 */
	public static String httpGet(String url) {
		// get请求返回结果
		String strResult = "";
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			// 发送get请求
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);

			/** 请求发送成功，并得到响应 **/
			if (response != null) {
				/** 读取服务器返回过来的json字符串数据 **/
				strResult = EntityUtils.toString(response.getEntity(),"UTF-8");
				url = URLDecoder.decode(url, "UTF-8");
			} else {
				log.error("get请求提交失败:" + url);
			}
		} catch (IOException e) {
			log.error("get请求提交失败:" + url, e);
		}
		return strResult;
	}

	/**
	 * httpPost
	 *
	 * @param url
	 *            路径
	 * @param jsonParam
	 *            参数
	 * @return
	 */
	public static String httpPost(String url, String jsonParam) {
		return httpPost(url, jsonParam, true);
	}
}
