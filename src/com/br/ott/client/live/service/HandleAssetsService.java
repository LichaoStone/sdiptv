package com.br.ott.client.live.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.br.ott.Global;

import com.br.ott.client.common.ChannelCache;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.client.live.mapper.HandleAssetsMapper;
import com.br.ott.client.live.mapper.ProgramsMapper;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.FileUtil;

/**
 * 文件名：HandleAssetsService.java，跟内容提供商百途资产对接类 版权：BoRuiCubeNet. Copyright
 * 2014-2015,All rights reserved 公司名称：青岛博瑞立方科技有限公司 创建人：lizhenghg 创建时间：2016-12-20
 */
@Service
public class HandleAssetsService {

	@Autowired
	private HandleAssetsMapper handleAssetsMapper;

	@Autowired
	private ProgramsMapper programsMapper;
	
	private static Logger logger = Logger.getLogger(HandleAssetsService.class);

	/**
	 * 每2分钟处理内容提供商提供的直播文件(TXT)情况
	 * 
	 * 创建人：lizhenghg 创建时间：2017-01-18
	 * 
	 * @return 返回类型：void
	 * @throws DocumentException 
	 * @throws RemoteException 
	 * 
	 * @exception try
	 *                {}catch
	 */
	public void readTxtForZB(){
		String newFilePath = null;
		InputStream input = null;
		InputStreamReader reader = null;
		BufferedReader buf = null;
		boolean isExist = false;
		File file = null;
		String newFileName = null;
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			//判断本地是否存在.TXT文件，存在的话把该文件copy到backup文件夹，然后删掉该文件夹下所有文件，再进行文件解析
			file = new File(Global.ZHIBO_PROGRAM_DOWNLOADPATH);
			if (!(file.exists() && file.isDirectory()))
				file.mkdir();
			String[] files = file.list();
			if (files != null && files.length > 0) {
				for (String f : files) {
					if ("TXT".equals(f.substring(f.indexOf(".") + 1))) {
						isExist = true;
						logger.error("存在.TXT文件-->");
						logger.error("开始进行文件备份和删除操作-->");
					    File oldFile = new File(Global.ZHIBO_PROGRAM_DOWNLOADPATH + f);
					    newFileName = DateUtil.getTimeSequence() + ".txt";
					    newFilePath = Global.ZHIBO_PROGRAM_DOWNLOADPATH + "backup" + File.separator + DateUtil.getCurrentDate();
					    File directory = new File(newFilePath);
					    if(!directory.exists() || !directory.isDirectory())
					    	directory.mkdir();
					    newFilePath = newFilePath + File.separator + newFileName;
					    File newFile = new File(newFilePath);
					    //复制文件
					    FileUtil.copyByDiffereEnctype(oldFile, newFile, "UTF-8");
					    //删掉Global.ZHIBO_PROGRAM_DOWNLOADPATH保存空间里所有存在的文件
					    String[] fileNames = new File(Global.ZHIBO_PROGRAM_DOWNLOADPATH).list();
					    File deleteFile = null;
					    for (String fName : fileNames) {
					    	if((deleteFile = new File(Global.ZHIBO_PROGRAM_DOWNLOADPATH + fName)).isFile()){
					    		deleteFile.delete();
					    		logger.error("删除文件：" + fName + "-->");
					    	}
					    }
					    logger.error("结束文件备份和删除操作-->");
					    break;
				    }
				}
			}
			if (!isExist)
				return;
			logger.error("正在解析TXT文件-->");
			input = new FileInputStream(newFilePath);
			reader = new InputStreamReader(input, "UTF-8");
			buf = new BufferedReader(reader);
			String result = null;
			String channelId = null;
			String date1 = null;
			String date2 = null;
			boolean flag = false;
			
			Programs parent = null;
			Programs son = null;
			ArrayList<Programs> pmList = new ArrayList<Programs>();
			
			while ((result = buf.readLine()) != null) {
				result = result.trim();
				if (StringUtils.isNotBlank(result)) {
					//读取到了频道名
					if (result.indexOf("Channel:") != -1) {
						flag = false;
						//获取到该频道的频道id
						channelId = ChannelCache.getIdByName(result = result.substring(result.indexOf(":") + 1));
						if(channelId == null) channelId = ChannelCache.getIdByOtherName(result);
						if (channelId != null) {
							flag = true;
							parent = new Programs();
							parent.setChannelId(channelId);
						} else {
							Map<String, String> content = new HashMap<String, String>();
							content.put("filename", "频道名称：" + result);
							content.put("isDeal", "-1");
							content.put("error", "本地数据库不存在该频道");
							this.handleAssetsMapper.addPrograms_log(content);
							content = null;
						}
						continue;
					}
					//读取非频道名内容，前提是先读取了频道名
					if (flag) {
						if (result.indexOf("Date:") != -1) {
							date1 = result.substring(result.indexOf(":") + 1);   //20170117
							date2 = date1.substring(0, 4) + "-" + date1.substring(4, 6) + "-" + date1.substring(6, 8);  //2017-01-17
							//一旦读取到频道Id跟日期，先删除该频道该日期的所有记录
							this.programsMapper.delProgramsByCidAndDate(channelId, date2);
							continue;
						}
						if (result.indexOf("|") == 9) {
							son = new Programs();
							son.setPlayTime(date1 + result.substring(result.indexOf("-") - 4, result.indexOf("-")) + "00");  //playTime=20170117012300
							int timeLongth = 0;
							int hour1 = Integer.parseInt(result.substring(result.indexOf("-") - 4, result.indexOf("-") - 2));
							int min1 = Integer.parseInt(result.substring(result.indexOf("-") - 2, result.indexOf("-")));
							int hour2 = Integer.parseInt(result.substring(result.indexOf("-") + 1, result.indexOf("-") + 3));
							int min2 = Integer.parseInt(result.substring(result.indexOf("-") + 3, result.indexOf("-") + 5));
							if (hour2 == 0)
								hour2 = 24;
							if ((hour1 == 0 && hour2 == 24) || hour1 == hour2)
								timeLongth = min2 - min1;
							else {
								if (hour2 == 24)
									timeLongth = 60 - min1 + min2 + (hour2 - 1 - hour1) * 60 ;
								else if (hour2 > hour1)
									timeLongth = 60 - min1 + min2 + (hour2 - 1 - hour1) * 60 ;
								else
									timeLongth = 60 - min1 + min2 + (hour2 + 23 - hour1) * 60 ;
							}
							son.setTimeLongth(String.valueOf(timeLongth));
							String channelName = result.substring(10);
							String queue = "0";
							if (channelName.lastIndexOf("）") == channelName.length() - 1 || channelName.lastIndexOf(")") == channelName.length() - 1)
								queue = channelName.substring((channelName.lastIndexOf("（") == -1 ? channelName.lastIndexOf("(") : channelName.lastIndexOf("（")) + 1, channelName.lastIndexOf("）") == -1 ? channelName.lastIndexOf(")") : channelName.lastIndexOf("）"));							
							if ("0".equals(queue)) {
								son.setQueue(queue);
								son.setChannelName(channelName);
							} else {
								if (isNumber(queue)) {									
									son.setChannelName(channelName.substring(0, channelName.lastIndexOf("（") == -1 ? channelName.lastIndexOf("(") : channelName.lastIndexOf("（")));
									son.setQueue(queue);
								} else {
									son.setChannelName(channelName);
									son.setQueue("0");
								}
							}
							son.setStatus("1");
							son.setChannelId(parent.getChannelId());
							pmList.add(son);
						}
					}
				}
			}
			if (CollectionUtils.isNotEmpty(pmList)) {
				paramsMap.put("pmList", pmList);
				//一次性进行数据库的插入
				this.handleAssetsMapper.addProgramsByMap(paramsMap);
				pmList = null;
			}
			logger.error("文件解析结束-->");
		} catch (Exception e) {
			logger.error("读取直播文件" + newFileName + "失败：" + e.getMessage());
			HashMap<String, String> content = new HashMap<String, String>();
			content.put("filename", newFileName == null ? "" : "文件名:" + newFileName);
			content.put("isDeal", "-1");
			content.put("error", e.getMessage());
			this.handleAssetsMapper.addPrograms_log(content);
			content = null;
		} finally {
			try {
				if(null != paramsMap)
					paramsMap = null;
				if(null != input)
					input.close();
				if(reader != null)
					reader.close();
				if(buf != null)
					buf.close();
			} catch (IOException e) {}
		}
	}
	
	/**
	 * 判断传入字符串是否整数
	 * @author lizhenghg 创建时间：2016-12-20
     * @param content
	 * @return 返回类型：boolean
	 */
	public static boolean isNumber(String content) {
		boolean flag = true;
		if(StringUtils.isBlank(content))
			return (flag = false);
		char[] ch = content.toCharArray();
		for (int i = 0, n = ch.length; i < n; i++) {
			if (!Character.isDigit(ch[i])) {
				flag = false;
				break;
			}
		}
		return flag;
	}
}
