package com.br.ott.common.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

/* 
 *  
 *  文件名：UploadApiUtil.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方网络科技有限公司
 *  创建人：pananz
 *  创建时间：2012-11-15 - 下午5:24:36
 */
public class UploadApiUtil {
	private static final String CHARSET = "utf-8"; // 编码格式
	private static final int blockSize = 1024 * 1024;
	private static Logger log = Logger.getLogger(UploadApiUtil.class);

	/**
	 * 调用公用上传测试接口方法 创建人：pananz 创建时间：2012-11-15 - 下午5:25:37
	 * 
	 * @param url
	 *            接口地址
	 * @param params
	 *            JSON格式参数{"MId":"1","bigimage":"","city":"106001"}
	 * @param headersstr
	 *            HEADER 参数("userId:123,userToken:235")
	 * @return 返回类型：HashMap<String,Object>
	 * @exception throws
	 */
	public static HashMap<String, Object> uploadFile(String url,
			MultipartFile file, Map<String, String> headers) {
		if (StringUtil.isEmpty(url))
			return null;
		DataOutputStream output = null;
		DataInputStream in = null;
		URL urlAddress = null;
		HttpURLConnection conn = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			// 请求url
			urlAddress = new URL(url);

			// 打开链接
			conn = (HttpURLConnection) urlAddress.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setChunkedStreamingMode(blockSize);

			Set<Entry<String, String>> set = headers.entrySet();
			for (Entry<String, String> entry : set) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
			conn.setRequestProperty("User-Agent", "IPTV");
			conn.setRequestProperty("Charset", CHARSET);
			conn.setRequestProperty("Content-Type", "multipart/form-data;file="
					+ file.getOriginalFilename());
			in = new DataInputStream(file.getInputStream());
			output = new DataOutputStream(conn.getOutputStream());
			byte[] bytes = new byte[blockSize];
			int len = 0;
			while ((len = in.read(bytes, 0, blockSize)) > 0) {
				output.write(bytes, 0, len);
			}
			output.flush();
			// 提取结果
			// int responseCode = conn.getResponseCode();
			String returnCode = conn.getHeaderField("ERRORCODE");
			String returnMsg = conn.getHeaderField("ERRORMESSAGE");
			String fileUrl = conn.getHeaderField("FILEURL");

			map.put("returnCode", returnCode);
			map.put("returnMsg", StringUtil.decodeformBase64(returnMsg));
			map.put("fileUrl", StringUtil.decodeformBase64(fileUrl));
		} catch (MalformedURLException e) {
			// URL地址错误
			log.error("上传文件的URL地址错误");
			map.put("returnMsg", "UploadApiUtil URL地址错误");
		} catch (SocketTimeoutException e) {
			// 请求超时
			log.error("上传文件请求超时");
			map.put("returnMsg", "UploadApiUtil 请求超时");
		} catch (IOException e) {
			// 网络连接失败
			log.error("上传文件的网络连接失败");
			map.put("returnMsg", "UploadApiUtil 网络连接失败");
		} catch (ArrayIndexOutOfBoundsException e) {
			// 公用请求参数异常！
			log.error("上传文件的公用请求参数异常");
			map.put("returnMsg", "UploadApiUtil 公用请求参数异常！");
		} finally {
			if (conn != null)
				conn.disconnect();
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			urlAddress = null;
		}
		return map;
	}

	public static HashMap<String, Object> uploadSoft(String url,
			MultipartFile file, Map<String, String> headers) {
		if (StringUtil.isEmpty(url))
			return null;
		DataOutputStream output = null;
		DataInputStream in = null;
		URL urlAddress = null;
		HttpURLConnection conn = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			// 请求url
			urlAddress = new URL(url);

			// 打开链接
			conn = (HttpURLConnection) urlAddress.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setChunkedStreamingMode(blockSize);

			Set<Entry<String, String>> set = headers.entrySet();
			for (Entry<String, String> entry : set) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
			conn.setRequestProperty("User-Agent", "IPTV");
			conn.setRequestProperty("Charset", CHARSET);
			conn.setRequestProperty("Content-Type", "multipart/form-data;file="
					+ file.getOriginalFilename());
			in = new DataInputStream(file.getInputStream());
			output = new DataOutputStream(conn.getOutputStream());
			byte[] bytes = new byte[blockSize];
			int len = 0;
			while ((len = in.read(bytes, 0, blockSize)) > 0) {
				output.write(bytes, 0, len);
			}
			output.flush();
			// 提取结果
			int responseCode = conn.getResponseCode();
			String returnCode = conn.getHeaderField("ERRORCODE");
			String returnMsg = conn.getHeaderField("ERRORMESSAGE");
			String fileUrl = conn.getHeaderField("FILEURL");
			String packageName = conn.getHeaderField("PACKAGENAME");
			String sourceType = conn.getHeaderField("SOURCETYPE");

			map.put("returnCode", returnCode);
			map.put("returnMsg", StringUtil.decodeformBase64(returnMsg));
			map.put("fileUrl", StringUtil.decodeformBase64(fileUrl));
			map.put("packageName", StringUtil.decodeformBase64(packageName));
			map.put("sourceType", StringUtil.decodeformBase64(sourceType));
			log.info(returnCode + ":" + StringUtil.decodeformBase64(returnMsg)
					+ ":" + responseCode + ":"
					+ StringUtil.decodeformBase64(fileUrl) + "packagenName==="
					+ StringUtil.decodeformBase64(packageName) + "sourceType"
					+ StringUtil.decodeformBase64(sourceType));
		} catch (MalformedURLException e) {
			// URL地址错误
			log.error("上传文件的URL地址错误");
			map.put("returnMsg", "UploadApiUtil URL地址错误");
		} catch (SocketTimeoutException e) {
			// 请求超时
			log.error("上传文件请求超时");
			map.put("returnMsg", "UploadApiUtil 请求超时");
		} catch (IOException e) {
			// 网络连接失败
			log.error("上传文件的网络连接失败");
			map.put("returnMsg", "UploadApiUtil 网络连接失败");
		} catch (ArrayIndexOutOfBoundsException e) {
			// 公用请求参数异常！
			log.error("上传文件的公用请求参数异常");
			map.put("returnMsg", "UploadApiUtil 公用请求参数异常！");
		} finally {
			if (conn != null)
				conn.disconnect();
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			urlAddress = null;
		}
		return map;
	}

	/**
	 * 传输文件
	 * 
	 * 创建人：pananz 创建时间：2015-12-14
	 * 
	 * @param @param url
	 * @param @param filePath
	 * @param @param headers
	 * @param @return 返回类型：HashMap<String,Object>
	 * @exception throws
	 */
	public static HashMap<String, Object> uploadSource(String url,
			String filePath, Map<String, String> headers) {
		if (StringUtil.isEmpty(url))
			return null;
		FileInputStream fis = null;
		DataInputStream in = null;
		DataOutputStream output = null;
		URL urlAddress = null;
		HttpURLConnection conn = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			urlAddress = new URL(url);
			conn = (HttpURLConnection) urlAddress.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setChunkedStreamingMode(blockSize);

			Set<Entry<String, String>> set = headers.entrySet();
			for (Entry<String, String> entry : set) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
			conn.setRequestProperty("User-Agent", "IPTV");
			conn.setRequestProperty("Charset", CHARSET);
			conn.setRequestProperty("Content-Type", "multipart/form-data;file="
					+ headers.get("fileName"));
			fis = new FileInputStream(filePath);
			in = new DataInputStream(fis);
			output = new DataOutputStream(conn.getOutputStream());
			byte[] bytes = new byte[blockSize];
			int len = 0;
			while ((len = in.read(bytes, 0, blockSize)) > 0) {
				output.write(bytes, 0, len);
			}
			output.flush();
			// 提取结果
			int responseCode = conn.getResponseCode();
			String returnCode = conn.getHeaderField("ERRORCODE");
			String returnMsg = conn.getHeaderField("ERRORMESSAGE");
			String fileUrl = conn.getHeaderField("FILEURL");

			map.put("returnCode", returnCode);
			map.put("returnMsg", StringUtil.decodeformBase64(returnMsg));
			log.info(returnCode + ":" + StringUtil.decodeformBase64(returnMsg)
					+ "responseCode:" + responseCode + "fileUrl===>" + fileUrl);
		} catch (MalformedURLException e) {
			// URL地址错误
			log.error("上传文件的URL地址错误");
			map.put("returnMsg", "UploadApiUtil URL地址错误");
		} catch (SocketTimeoutException e) {
			// 请求超时
			log.error("上传文件请求超时");
			map.put("returnMsg", "UploadApiUtil 请求超时");
		} catch (IOException e) {
			// 网络连接失败
			log.error("上传文件的网络连接失败");
			map.put("returnMsg", "UploadApiUtil 网络连接失败");
		} catch (ArrayIndexOutOfBoundsException e) {
			// 公用请求参数异常！
			log.error("上传文件的公用请求参数异常");
			map.put("returnMsg", "UploadApiUtil 公用请求参数异常！");
		} finally {
			if (conn != null) {
				conn.disconnect();
			}

			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
			urlAddress = null;
		}
		return map;
	}
}
