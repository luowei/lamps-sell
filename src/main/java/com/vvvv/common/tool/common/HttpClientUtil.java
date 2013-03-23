package com.vvvv.common.tool.common;

import java.io.IOException;
import java.util.List;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * @className:HttpClientUtil.java
 * @classDescription:HttpClient工具类//待完善模拟登录，cookie,证书登录
 * @author:xiayingjie
 * @createTime:2011-8-31
 */

public class HttpClientUtil {

	public static String CHARSET_ENCODING = "UTF-8";
	// private static String
	// USER_AGENT="Mozilla/4.0 (compatible; MSIE 6.0; Win32)";//ie6
	public static String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Win32)";// ie7

	// private static String
	// USER_AGENT="Mozilla/4.0 (compatible; MSIE 8.0; Win32)";//ie8

	/**
	 * 获取DefaultHttpClient对象
	 * 
	 * @param charset
	 *            字符编码
	 * @return DefaultHttpClient对象
	 */
	private static DefaultHttpClient getDefaultHttpClient(final String charset) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		// 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
		httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT);
		httpclient.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, charset == null ? CHARSET_ENCODING : charset);
		// 浏览器兼容性
		httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		// 定义重试策略
		httpclient.setHttpRequestRetryHandler(requestRetryHandler);
		return httpclient;
	}

	/**
	 * 异常自动恢复处理, 使用HttpRequestRetryHandler接口实现请求的异常恢复
	 */
	private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
		// 自定义的恢复策略
		public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
			// 设置恢复策略，在发生异常时候将自动重试3次
			if (executionCount >= 3) {
				// 如果连接次数超过了最大值则停止重试
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				// 如果服务器连接失败重试
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				// 不要重试ssl连接异常
				return false;
			}
			HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
			boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
			if (!idempotent) {
				// 重试，如果请求是考虑幂等
				return true;
			}
			return false;
		}
	};

	/**
	 * 使用ResponseHandler接口处理响应，HttpClient使用ResponseHandler会自动管理连接的释放，解决了对连接的释放管理
	 */
	private static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		// 自定义响应处理
		public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String charset = EntityUtils.getContentCharSet(entity) == null ? CHARSET_ENCODING : EntityUtils.getContentCharSet(entity);
				return new String(EntityUtils.toByteArray(entity), charset);
			} else {
				return null;
			}
		}
	};

	/**
	 * 使用post方法获取相关的数据
	 * 
	 * @param url
	 * @param paramsList
	 * @return
	 */
	public static String post(String url, List<NameValuePair> paramsList) {
		return httpRequest(url, paramsList, "POST", null);
	}

	/**
	 * 使用post方法并且通过代理获取相关的数据
	 * 
	 * @param url
	 * @param paramsList
	 * @param proxy
	 * @return
	 */
	public static String post(String url, List<NameValuePair> paramsList, HttpHost proxy) {
		return httpRequest(url, paramsList, "POST", proxy);
	}

	/**
	 * 使用get方法获取相关的数据
	 * 
	 * @param url
	 * @param paramsList
	 * @return
	 */
	public static String get(String url, List<NameValuePair> paramsList) {
		return httpRequest(url, paramsList, "GET", null);
	}

	/**
	 * 使用get方法并且通过代理获取相关的数据
	 * 
	 * @param url
	 * @param paramsList
	 * @param proxy
	 * @return
	 */
	public static String get(String url, List<NameValuePair> paramsList, HttpHost proxy) {
		return httpRequest(url, paramsList, "GET", proxy);
	}

	/**
	 * 提交数据到服务器
	 * 
	 * @param url
	 * @param params
	 * @param authenticated
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String httpRequest(String url, List<NameValuePair> paramsList, String method, HttpHost proxy) {
		String responseStr = null;
		// 判断输入的值是是否为空
		if (null == url || "".equals(url)) {
			return null;
		}
		// 创建HttpClient实例
		DefaultHttpClient httpclient = getDefaultHttpClient(CHARSET_ENCODING);

		String formatParams = null;
		// 将参数进行utf-8编码
		if (null != paramsList && paramsList.size() > 0) {
			formatParams = URLEncodedUtils.format(paramsList, CHARSET_ENCODING);
		}
		// 如果代理对象不为空则设置代理
		if (null != proxy) {
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		try {
			// 如果方法为Get
			if ("GET".equalsIgnoreCase(method)) {
				if (formatParams != null) {
					url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams) : (url.substring(0, url.indexOf("?") + 1) + formatParams);
				}
				HttpGet hg = new HttpGet(url);
				responseStr = httpclient.execute(hg, responseHandler);

				// 如果方法为Post
			} else if ("POST".equalsIgnoreCase(method)) {
				HttpPost hp = new HttpPost(url);
				if (formatParams != null) {
					StringEntity entity = new StringEntity(formatParams);
					entity.setContentType("application/x-www-form-urlencoded");
					hp.setEntity(entity);
				}
				responseStr = httpclient.execute(hp, responseHandler);
			}

		} catch (Exception e) {
			System.out.println("httpClient:" + url);
			System.out.println(DateUtil.datetime());
			e.printStackTrace();
			return null;
		}
		return responseStr;
	}

	/**
	 * 获取图片的字节数组
	 * 
	 * @createTime 2011-11-24
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static byte[] getImg(String url) throws ClientProtocolException, IOException {
		byte[] bytes = null;
		// 创建HttpClient实例
		DefaultHttpClient httpclient = getDefaultHttpClient(CHARSET_ENCODING);
		// 获取url里面的信息
		HttpGet hg = new HttpGet(url);
			HttpResponse hr = httpclient.execute(hg);
			bytes = EntityUtils.toByteArray(hr.getEntity());
		// 转换内容为字节
		return bytes;
	}

	public static void main(String[] args) throws ClientProtocolException {
		byte b[];
		try {
			b = getImg("http://.jpg");
		} catch (IOException e) {
			System.out.println("error");
		}
	}

	// public static void main(String[] args) {
	//		
	// String songName = "s";
	// String singer = "张信哲";
	// String url = "http://picdown.vvvv.cn:9010/picsearch?title="
	// + EncodeUtil.urlEncode(songName)
	// + "&artist="
	// + EncodeUtil.urlEncode(singer)
	// +
	// "&filename=&auto=1&x=76&y=76&maxcount=1&s=s500&v=v1.0&imei=&mid=&uid=wap";
	// // 获取相关图片源文件
	// String picSource = HttpClientUtil.get(url, null);
	// Document doc = Dom4jUtil.stringToDoc(picSource);
	// // 获取ip
	// String ip = ((Element) doc.selectSingleNode("tt_song_list"))
	// .attributeValue("ip");
	//
	// // 获取相关加密
	// Node nd = doc.selectSingleNode("tt_song_list/tt_songinfo/pic");
	// if (null != nd) {
	// Element e = (Element) nd;
	// String uid0 = e.attributeValue("uid0");
	// String uid1 = e.attributeValue("uid1");
	// String uid2 = e.attributeValue("uid2");
	// String uid3 = e.attributeValue("uid3");
	//
	// System.out.println(picSource);
	// System.out.println(ip);
	// System.out.println(uid0);
	// System.out.println(uid1);
	// System.out.println(uid2);
	// System.out.println(uid3);
	// PicDown pdNew = new PicDown();
	// pdNew.setIp(ip);
	// pdNew.setUid0(uid0);
	// pdNew.setUid1(uid1);
	// pdNew.setUid2(uid2);
	// pdNew.setUid3(uid3);
	//
	// System.out.println(pdNew.getDownUrl());
	//			
	//
	// }
	//
	// // HttpHost hh=null;//new HttpHost("97.87.24.113", 80);
	// // List<NameValuePair>pl=new ArrayList<NameValuePair>();
	// // HttpClientUtil.CHARSET_ENCODING="GBK";
	//
	// // List<NameValuePair> paramsList=new ArrayList<NameValuePair>();
	// // // paramsList.add(new BasicNameValuePair("uid","123213123"));
	// // paramsList.add(new BasicNameValuePair("username","hww"));
	// // paramsList.add(new BasicNameValuePair("password","123"));
	// // paramsList.add(new BasicNameValuePair("type","0"));
	// // String
	// //
	// json=HttpClientUtil.post("http://192.168.5.20:8080/ttus/local/xlocallogin.do",
	// // paramsList);
	// // System.out.println(json);
	//
	// }

}
