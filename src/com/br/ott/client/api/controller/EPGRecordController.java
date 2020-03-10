package com.br.ott.client.api.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.ott.base.BaseApiController;
import com.br.ott.client.api.service.EPGRecordService;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.client.live.service.ProgramsService;
import com.br.ott.common.exception.OTTException;
import com.br.ott.common.util.JedisUtil;
import com.br.ott.common.util.MD5Util;
import com.br.ott.common.util.StringUtil;
import com.br.ott.common.util.id.CreateIdentityId;

/* 
 *  
 *  文件名：EPGRecordController.java
 *  版权：BoRuiCubeNet. Copyright 2012-2012,All rights reserved
 *  公司名称：青岛博瑞立方科技有限公司
 *  创建人：pananz
 *  创建时间：2015-4-22 - 下午2:58:27
 */
@Controller
@RequestMapping(value = "/api")
public class EPGRecordController extends BaseApiController {
	private static final Logger logger = Logger
			.getLogger(EPGRecordController.class);
	@Autowired
	private EPGRecordService recordService;

	@Autowired
	private ProgramsService programsService;

	/**
	 * EPG录制软件获取录制信息接口
	 * 
	 * 1． EPG录制软件向博瑞业务管理系统请求获取需要录制的节目列表信息。 2．
	 * 博瑞业务管理系统收到参数后根据参数进行判断并且提取相应需要录制的数据给EPG录制软件 3．
	 * EPG录制软件接收到博瑞业务管理系统返回的数据后解析数据并且根据数据录制的业务逻辑处理
	 * 
	 * 
	 * 创建人：pananz 创建时间：2015-4-22 - 下午2:59:39
	 * 
	 * @param request
	 * @param response
	 * @return 返回类型：String
	 * 
	 *         timeTable 根节点 item 二级节点，为需要录制的频道和时间节点 属性name为频道，date为录制日期 prgItem
	 *         三级节点 需要录制的节目节点 time 四级节点 需要录制的节目播放时间 name四级节点 需要录制的节目名称
	 * 
	 * @exception throws
	 */
	@RequestMapping(value = "getRecEpgList")
	public void getRecEpgList(HttpServletRequest request,
			HttpServletResponse response) {
		String cid = request.getParameter("cid");
		try {
			String xmlStr = recordService.findProgramsByCid(cid);
			writeJson(response, xmlStr);
		} catch (OTTException e) {
			e.printStackTrace();
		}
	}

	/**
	 * EPG录制软件录制完成通知接口
	 * 
	 * 1.EPG录制软件向博瑞业务管理系统提交当前节目录制情况。
	 * 2.博瑞业务管理系统收到参数后根据参数进行判断并且将相关节目录制状态录入系统数据库进行保存。 3.
	 * 博瑞业务管理系统同步将录制成功的信息写入redis服务器 创建人：pananz 创建时间：2015-4-22 - 下午3:01:15
	 * 
	 * 
	 * Redis写入功能 例： "0007ed2888644d639d129aefbe1e7412": { "id":
	 * "0007ed2888644d639d129aefbe1e7412", "path":
	 * "movies/201312/1204/Will.It.Snow.for.Christmas.E14.720p.HDTV.x264-kurama.ts"
	 * , "size": "0", "server": "[5001, 5002]" } key：为文件ID，可以是一个哈希类型的字符串
	 * id：等同与key path：本文件所存在的相对路径 server：拥有此文件的有哪几台服务器(以后台的数字ID代表服务器)
	 * 
	 * @param request
	 *            Get传输的参数 K： 频道id varchar(8) 必填 Lss ：资源服务器id Lsrp：资源存放的根目录
	 *            Post传输的参数： "source":"http://3803.test:3803/1111" 文件服务器地址
	 *            "file":
	 *            "/mnt/data/record_data/[801]/20150416T225500-20150416T230000/dump.ts"
	 *            文件物理存放位置 "videotime":"22:55-23:00" 录制的节目时间段
	 *            "videoname":"TESTTEST" 录制的节目名称 "videodate":"20150416" 录制日志
	 *            "recordtime":288
	 * @param response
	 * @return 返回类型：String response 返回结果根节点 result 二级节点，返回博瑞业务管理系统录入信息的结果描述
	 *         resultCode二级节点，返回博瑞业务管理系统录入信息的结果值
	 * @exception throws
	 */
	@RequestMapping(value = "epgRecResult")
	public @ResponseBody
	String epgRecResult(HttpServletRequest request, HttpServletResponse response) {
		// GET参数
		String cid = request.getParameter("k");
		String lss = request.getParameter("lss");
		String lsrp = request.getParameter("lsrp");
		// POST参数
		String source = request.getParameter("source");
		String file = request.getParameter("file");
		String videotime = request.getParameter("videotime");
		String videoname = request.getParameter("videoname");
		String videodate = request.getParameter("videodate");
		String recordtime = request.getParameter("recordtime");

		logger.debug("GET参数k=" + cid + ", lss=" + lss + " ,lsrp=" + lsrp
				+ ", POST参数source=" + source + ", file=" + file
				+ ", videotime=" + videotime + ", videoname=" + videoname
				+ ", videodate=" + videodate + ", recordtime=" + recordtime);
		if (StringUtil.isEmpty("videotime")) {
			// POST参数videotime为空
			logger.debug("POST参数videotime为空");
			return "3001";
		}
		try {
			String start = videotime.split("-")[0];
			String playTime = videodate + start;
			Programs programs = programsService.getProgramsByCidAndName(cid,
					videoname, playTime);
			if (programs == null) {
				// 查询不到该节目单信息
				logger.debug("查询不到该节目单信息");
				return "3002";
			}

			String path = file.substring(lsrp.length(), file.length());
			JedisUtil jutil = new JedisUtil("122.228.113.253", 9733, 100000,
					"02E4F70D-4292-39E4-CFB4-38DFB70B68FB", 1);
			HashMap<String, String> hmap = new HashMap<String, String>();
			String createId = CreateIdentityId.getInstance().createId();
			String playId = MD5Util.getMD5(createId).toLowerCase();
			logger.debug("生成随机数：" + createId + "加密后成的ID为：" + playId);
			hmap.put("id", playId);
			hmap.put("path", path);
			hmap.put("size", "0");
			hmap.put("server", "[" + lss + "]");
			String redisId = jutil.hmset(playId, hmap);
			if (StringUtil.isNotEmpty(redisId)) {
				programs.setServerId(lss);
				programs.setRootDir(lsrp);
				programs.setSourceServer(source);
				programs.setFileAddr(file);
				programs.setPlayId(playId);
				programsService.updateProgramSource(programs);

			}
		} catch (Exception e) {
			// 回调业务出错
			logger.debug("回调业务出错");
			return "4001";
		}
		return "200";
	}
}
