package com.br.ott.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import sun.net.www.protocol.http.HttpURLConnection;
import java.net.URL;

import com.br.ott.common.util.StringUtil;

/* 
 *  
 *  文件名：TestUpload.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方网络科技有限公司
 *  创建人：pananz
 *  创建时间：2012-11-16 - 上午11:05:04
*/

public class TestUpload {

	public static void main(String[] args) {
		try {
			URL url = new URL("http://localhost:8080/OTTAppSource/uploadServlet");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setChunkedStreamingMode(1024 * 1024);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			String fname = "e://MyPaginationJava.zip";
			File file = new File(fname);
			conn.setRequestProperty("Content-Type", "multipart/form-data;file=" + file.getName());

			conn.setRequestProperty("id", "999342266299");
			conn.setRequestProperty("fileName", file.getName());
			conn.setRequestProperty("secondsDir", "/product/app");
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();
			out.flush();
			out.close();
			int responseCode = conn.getResponseCode();
			String returnCode = conn.getHeaderField("ERRORCODE");
			String returnMsg = conn.getHeaderField("ERRORMESSAGE");
			String fileUrl = conn.getHeaderField("FILEURL");

			System.out.println(returnCode + ":" + StringUtil.decodeformBase64(returnMsg) + ":" + responseCode + ":"
					+ StringUtil.decodeformBase64(fileUrl));
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
	}

}
