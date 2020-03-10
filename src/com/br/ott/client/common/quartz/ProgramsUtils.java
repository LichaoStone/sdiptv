package com.br.ott.client.common.quartz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.br.ott.Global;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.FileUtil;
import com.br.ott.common.util.StringUtil;
import com.br.ott.common.util.UploadApiUtil;

/* 
 *  
 *  文件名：ProgramsUtils.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-5-11 - 下午2:21:53
 */
public class ProgramsUtils {
	private static final Logger logger = Logger.getLogger(ProgramsUtils.class);

	private static final String billSplit = "/";
	private static final String DATA_SPLIT = "#_#";

	/**
	 * 设置节目单内容格式 创建人：pananz 创建时间：2015-8-3
	 * 
	 * @param @param programs
	 * @param @return 返回类型：String
	 * @exception throws
	 */
	private static String getProgramContent(Programs programs) {
		StringBuffer sb = new StringBuffer();
		sb.append(programs.getChannelId()).append(DATA_SPLIT)
				.append(programs.getChannelName()).append(DATA_SPLIT)
				.append(programs.getName()).append(DATA_SPLIT)
				.append(programs.getPlayTime()).append(DATA_SPLIT)
				.append(programs.getTimeLongth()).append(DATA_SPLIT)
				.append(programs.getBasicName()).append(DATA_SPLIT)
				.append(programs.getQueue()).append("\r\n");
		return sb.toString();
	}

	/**
	 * 节目单数据写入文件中 创建人：pananz 创建时间：2015-8-3
	 * 
	 * @param @param type
	 * @param @param date
	 * @param @param programs 返回类型：void
	 * @exception throws
	 */
	public static void createDataFile(String type, Date date,
			List<Programs> programs, String url) {
		if (CollectionUtils.isEmpty(programs)) {
			return;
		}
		if (!Global.LIVE_ENABLE) {
			logger.debug("不进行写数据操作");
			return;
		}
		FileOutputStream fos = null;
		OutputStreamWriter writer = null;
		BufferedWriter bw = null;
		String dateDir = billSplit + DateUtil.getCurrentDate();
		String dataDir = Global.LIVE_DATA + dateDir;
		String firstName = DateUtil.getCurrentHHmm() + "-" + type;

		String filePath = dataDir + billSplit + firstName + "_"
				+ DateUtil.toString(date, "yyyyMMdd") + ".sql";
		String finalPath = dataDir + billSplit + firstName + "_"
				+ DateUtil.toString(date, "yyyyMMdd") + ".txt";

		if (!FileUtil.isDirExist(dataDir)) {
			FileUtil.createDir(dataDir);
		}
		if (!FileUtil.isFileExist(filePath)) {
			try {
				FileUtil.createFile(filePath);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("生成节目单文件出错：" + filePath);
				return;
			}
		}
		try {
			fos = new FileOutputStream(new File(filePath));
			writer = new OutputStreamWriter(fos, "utf-8");
			bw = new BufferedWriter(writer);
			int i = 0;
			for (Programs info : programs) {
				bw.write(getProgramContent(info));
				i++;
				if (i % 3000 == 0) {
					bw.flush();
					logger.info("先休息一会，喝点水，抽根烟，接着写.........");
					Thread.sleep(2000);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("写入节目单文件" + filePath + "时出错：" + e1.getMessage());
			return;
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
				if (writer != null) {
					writer.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (Global.HTTP_ENABLE) {
					logger.info("开始传送文件到服务器.............");
					// boolean bool = LinuxCmdUtils.scpFile(filePath, finalPath,
					// dateDir,scpUrl);
					// if (!bool) {
					// logger.error("传送文件失败");
					// }

					// 修改为http传输文件
					Map<String, String> headers = new HashMap<String, String>();
					headers.put("filePath", filePath);
					headers.put("dataDir", dataDir);
					headers.put("finalPath", finalPath);
					if (StringUtil.isEmpty(url)) {
						logger.error("无http传送地址........");
						return;
					}
					String[] urls = url.split(",");
					for (String httpUrl : urls) {
						HashMap<String, Object> map = UploadApiUtil
								.uploadSource(httpUrl, filePath, headers);
						logger.debug(httpUrl + "传送传回结果："
								+ map.get("returnCode"));
					}
				}
				logger.info("结束本次节目单写入");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("写入节目单文件" + filePath + "时,关闭文件操作流出错");
			}
		}
	}
	
}
