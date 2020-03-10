package com.br.ott;

/*
 * 文 件 名:  Global.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  cKF46827
 * 修改时间:  2011-8-30
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

import com.br.ott.common.util.ReadProperties;
import com.br.ott.common.util.StringUtil;

/**
 * <全局变量>
 * 
 * @author cKF46827
 * @version [版本号, 2011-8-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class Global {

	private Global() {
	}

	/**
	 * 当次请求发起时的系统时间
	 */
	public static final String SESSION_REQ_SYSTEM_TIME = "system_req_time";

	private static ReadProperties prop = new ReadProperties("config.properties");

	public static final String CURRENT_USER = "user";

	public static final String COMMUN_USER = "comm";
	/** project的路径 */
	public static String localPath = prop.getPara("localPath");
	/** 合作伙伴图片路径 */
	public static final String PARTNER_IMG_URL = prop.getPara("partner.img");

	/** 上传资源接口地址 */
	/** 资源WEB地址 */
	public static final String SERVER_SOURCE_URL = prop
			.getPara("server.source.url");
	/** 下载地址 */
	public static final String DOWNLOAD_SOURCE_URL = prop
			.getPara("download.source.url");
	/** 上传图片等地址 */
	public static final String SOURCE_UPLOAD_SOURCE_URL = prop
			.getPara("sourceUpload.source.url");
	/** 上传节目单地址 */
	public static final String PROGRAM_UPLOAD_SOURCE_URL = prop
			.getPara("programUpload.source.url");
	
	/** 是否与点播平台节目对接同步操作*/
	public static final boolean DBPLAY_ENABLE = StringUtil.isEmpty(prop
			.getPara("dbPlay.enable")) ? false : Boolean.valueOf(prop
			.getPara("dbPlay.enable"));
	
	/** 发送邮件服务器地址 */
	public static final boolean EMAIL_ENABLE = StringUtil.isEmpty(prop
			.getPara("email.enable")) ? false : Boolean.valueOf(prop
			.getPara("email.enable"));
	public static final String RECEIVE_EMAIL_ADRRESS = prop
			.getPara("receive.email.address");

	public static final String EMAIL_HOSTNAME = prop.getPara("email.hostName");
	public static final String SEND_EMAIL_ADRRESS = prop
			.getPara("send.email.adrress");
	public static final String SEND_EMAIL_PORT = prop
			.getPara("send.email.port");
	public static final String SEND_EMAIL_USERNAME = prop
			.getPara("send.email.userName");
	public static final String SEND_EMAIL_PASSWORD = prop
			.getPara("send.email.password");

	/** 邮箱内容 */
	public static final String SEND_EMAIL_EMAILCONTENT = prop
			.getPara("send.email.emailContent");
	/** 邮箱主题 */
	public static final String SEND_EMAIL_EMAILSUBJECT = prop
			.getPara("send.email.emailSubject");

	public static final String CONTENT_TYPE = "Content-Type";

	public static final String ERROR_CODE = "ERRORCODE";

	public static final String ERROR_MESSAGE = "ERRORMESSAGE";

	/**
	 * 通过KEY获取对应的值 创建人：陈登民 创建时间：2012-12-3 - 下午2:41:52
	 * 
	 * @param key
	 * @return 返回类型：String
	 * @exception throws
	 */
	public static String getProperties(String key) {
		try {
			return prop.getPara(key);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static final boolean PROXYIP_ENABLE = StringUtil.isEmpty(prop
			.getPara("proxyIP.enable")) ? false : Boolean.valueOf(prop
			.getPara("proxyIP.enable"));

	public static final boolean FTP_ENABLE = Boolean.valueOf(prop
			.getPara("ftp.enable"));
	public static final String FTP_HOSTNAME = prop.getPara("ldtv.ftp.hostname");
	public static final String FTP_ACCOUNT = prop.getPara("ftp.account");
	public static final String FTP_PASSWD = prop.getPara("ftp.passwd");

	public static final String MENU_FILTER = prop.getPara("menu.filter");

	public static final String LIVE_DATA = prop.getPara("live.data");
	public static final boolean LIVE_ENABLE = StringUtil.isEmpty(prop
			.getPara("live.enable")) ? false : Boolean.valueOf(prop
			.getPara("live.enable"));
	public static final boolean LIVE_AFTER = StringUtil.isEmpty(prop
			.getPara("live.after")) ? false : Boolean.valueOf(prop
			.getPara("live.after"));

	public static final String LIVE_TYPE = prop.getPara("live.type");
	public static final String LIVE_DATES = prop.getPara("live.dates");
	public static final String LIVE_IMG_URL = prop.getPara("live.img");

	public static final String KEY_BEFORE_FILTER = prop
			.getPara("key.before.filter");
	public static final String KEY_AFTER_FILTER = prop
			.getPara("key.after.filter");

	public static final String CODE_LICENSE_KEY = prop
			.getPara("code.license.key");

	public static final String WORD_AFTER_FILTER = prop
			.getPara("word.after.filter");
	public static final String WORD_BEFORE_FILTER = prop
			.getPara("word.before.filter");

	public static final String SOURCE_PATH = prop.getPara("source.path");
	public static final String SCP_URL = prop.getPara("scp.url");
	public static final String SCP_URL2 = prop.getPara("scp.url2");
	public static final boolean SCP_ENABLE = StringUtil.isEmpty(prop
			.getPara("scp.enable")) ? false : Boolean.valueOf(prop
			.getPara("scp.enable"));
	public static final boolean HTTP_ENABLE = StringUtil.isEmpty(prop
			.getPara("http.enable")) ? false : Boolean.valueOf(prop
			.getPara("http.enable"));

	public static final String SYNCGROUP_BOSS = prop.getPara("syncGroup.boss.url");
	
	public static final String SYNCGROUP_LAUNCHER = prop.getPara("syncGroup.launcher.url");
	
	static final public String ZHIBO_PROGRAM_DOWNLOADPATH = prop.getPara("zhibo_program.downloadPath");
}
