/*
 * 文 件 名:  DataEngine.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  cKF46827
 * 修改时间:  2011-8-29
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.br.ott.common.net;

import com.br.ott.common.base.IParamService;
import com.br.ott.common.exception.NotesException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * HTTP请求
 * 
 * @author cKF46827
 * @version [版本号, 2011-8-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class DataEngine implements IDataEngine {

    //private static LogUtil logUtil = new LogUtil(DataEngine.class);
    private Logger log = Logger.getLogger(DataEngine.class);

    /** 是否正在请求 */
    private boolean isLoding = false;

    /** URL */
    private String mUrl;

    /** JSON内容 */
    private String mXmlContent;
    
    /** 鉴权 */
    private String auth;

    /**
     * <默认构造函数>
     */
    public DataEngine(String mUrl, String auth) {
        this.mUrl = mUrl;
        this.auth = auth;
    }

    /**
     * <默认构造函数>
     */
    public DataEngine(String auth, IParamService iParamService) {
        this(iParamService.getUrl(), auth);

        if (null != iParamService.getParams()) {
            this.mXmlContent = new RequestParams(iParamService).getXmlContent();
        }
    }
    
    /**
     * <默认构造函数>
     */
    public DataEngine(String auth, IParamService iParamService, String mark) {
        this(iParamService.getUrl(), auth);

        if (null != iParamService.getParams()) {
            this.mXmlContent = iParamService.getParams().get("content").toString();
        }
    }

    @Override
    public void cancelRequest() {
    }

    @Override
    public String request() throws NotesException {

        // 设置正在请求
        this.isLoding = true;

        Response response = this.callMethod();

        if (NetConstants.URL_ERROR == response.getStatusCode()) {

            // 设置请求完毕
            this.isLoding = false;
            throw new NotesException(NetConstants.URL_ERROR, "URL地址错误");

        } else if (NetConstants.HTTP_TIMEOUT == response.getStatusCode()) {

            // 设置请求完毕
            this.isLoding = false;
            throw new NotesException(NetConstants.HTTP_TIMEOUT, "网络连接超时");

        } else if (NetConstants.CONNECT_ERROR == response.getStatusCode()) {

            // 设置请求完毕
            this.isLoding = false;
            throw new NotesException(NetConstants.CONNECT_ERROR, "网络连接失败");

        }

        // 设置请求完毕
        this.isLoding = false;
        System.out.println("OUT XML : "+response.getResult());
        return response.getResult();
    }

    /**
     * @return 是否正在请求
     */
    @Override
    public boolean isLoading() {
        return this.isLoding;
    }
    
    public Response callMethod() throws NotesException{
        return callMethod(StringUtils.isNotEmpty(this.auth));
    }

    /**
     * Http请求
     * 
     * @param url [请求地址]
     * @param jsonContent [json字符串]
     */
    public Response callMethod(Boolean isSendAuth) throws NotesException{

        Response response = null;
        DataOutputStream output = null;
        HttpURLConnection conn = null;
        try {

            // 请求url
            URL urlAddress = new URL(this.mUrl);
            log.info("url地址:" + this.mUrl);
            System.out.println("url地址:" + this.mUrl);
            // 打开链接
            conn = (HttpURLConnection)urlAddress.openConnection();
            conn.setConnectTimeout(NetConstants.REQUEST_TIMEOUT);
            conn.setReadTimeout(NetConstants.RESPONSE_TIMEOUT);

            // 是否有参数请求
            if (null != this.mXmlContent && !"".equals(this.mXmlContent)) {
                log.info("请求参数：" + this.mXmlContent);
                System.out.println("IN XML : "+this.mXmlContent);
                byte[] xmlByte = this.mXmlContent.getBytes("UTF-8");
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                if(isSendAuth){
                    conn.setRequestProperty("Authorization", this.auth);
                }
                conn.setRequestProperty("User-Agent", "webserver");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Charset", "utf-8");
                conn.setRequestProperty("Content-Length", xmlByte.length + "");
                conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
                output = new DataOutputStream(conn.getOutputStream());
                output.write(xmlByte);
                output.flush();
            } else {
                log.info("无请求参数");
            }

            // URL地址错误
            if (conn.getResponseCode() == NetConstants.URL_ERROR) {
                throw new MalformedURLException();
            }

            /* 设置response */
            response = this.setResponse(conn);

        } catch (MalformedURLException e) {// URL地址错误

            response = this.setResponse(NetConstants.URL_ERROR);

        } catch (SocketTimeoutException e) {// 请求超时

            response = this.setResponse(NetConstants.HTTP_TIMEOUT);

        } catch (IOException e) {// 网络连接失败

            response = this.setResponse(NetConstants.CONNECT_ERROR);

        } finally {
            try {
                if (null != output) {
                    output.close();
                }
            } catch (IOException e) {
            }
        }
        return response;
    }

    /**
     * 设置Response
     * 
     * @param conn URL连接
     * @return Response
     */
    private Response setResponse(HttpURLConnection conn) {
        Response response = new Response();
        try {
            response.setStatusCode(conn.getResponseCode());
            response.setResult(this.streamToString(conn.getInputStream()));
        } catch (IOException e) {
            log.error("设置Response错误！" , e);
        }
        return response;
    }

    /**
     * 设置Response
     * 
     * @param statusCode [状态码]
     * @param statusDescription [状态描述]
     * @return Response
     */
    private Response setResponse(int statusCode) {
        Response response = new Response();
        response.setStatusCode(statusCode);
        return response;
    }

    /**
     * 流转换成字符串
     * 
     * @param input [输入流]
     * @return [参数说明]
     * @return String [字符串]
     */
    private String streamToString(InputStream input) {

        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            String temp = br.readLine();
            while (null != temp && !"".equals(temp)) {
                sb.append(temp);
                temp = br.readLine();
            }
        } catch (IOException e) {
            log.error("返回流转换string错误！" , e);
            return null;
        }
        return sb.toString();
    }
}
