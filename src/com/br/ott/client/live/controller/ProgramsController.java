package com.br.ott.client.live.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.br.ott.Global;
import com.br.ott.base.BaseController;
import com.br.ott.client.SessionUtil;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.live.entity.Channel;
import com.br.ott.client.live.entity.Programs;
import com.br.ott.client.live.service.ChannelService;
import com.br.ott.client.live.service.ProgramsService;
import com.br.ott.common.util.DateUtil;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.JsonUtils;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;
import com.br.ott.common.util.UploadFile;

/* 
 *  
 *  文件名：ProgramsController.java
 *  创建人：pananz
 *  创建时间：2014-7-4 - 下午7:48:07
 */
@Controller
@RequestMapping(value = "/programs")
public class ProgramsController extends BaseController {
	private static Logger log = Logger.getLogger(ProgramsController.class);

	@Autowired
	private ProgramsService programsService;
	@Autowired
	private OperaLogService operaLogService;
	@Autowired
	private ChannelService channelService;

	private static final String BUSI_NAME = "节目单管理";

	@RequestMapping(value = "findPrograms", method = RequestMethod.GET)
	public String findPrograms(HttpServletRequest request, Programs p,
			Model model) {
		Date date = new Date();
		log.debug("节目单分页查询请求进入时间：" + DateUtil.toString(date));
		p.setCurrentPage(getPageNo(request));
		p.setShowCount(getPageRow(request));
		List<Programs> list = programsService.findProgramsByPage(p);
		model.addAttribute("list", list);
		model.addAttribute("p", p);
		log.debug("节目单分页查询完成后时间：" + DateUtil.dateDiff(date, new Date()));
		Channel channel = new Channel();
		channel.setStatus("0");
		channel.setParentId("0");
		List<Channel> channels = channelService.findChannelByCond(channel);
		model.addAttribute("channels", channels);
		if (StringUtil.isNotEmpty(p.getChannelId())) {
			Channel cc = channelService.getChannelById(p.getChannelId());
			if (cc != null) {
				p.setAreaId(cc.getParentId());
				Channel c = new Channel();
				c.setAreaId(cc.getParentId());
				List<Channel> csList = channelService.findChannelByCond(c);
				model.addAttribute("csList", csList);
				csList = null;
			}

		}
		channels = null;
		list = null;
		log.debug("频道查询完成后时间：" + DateUtil.dateDiff(date, new Date()));
		return "live/listPrograms";
	}

	@SuppressWarnings({ "static-access" })
	@RequestMapping(value = "checkLiveProgram")
	public void checkNameList(HttpServletResponse response,
			HttpServletRequest request) {
		try {
			String jsonString = StringUtil.requestGetStreamToString(request);
			log.debug("同步请求信息：" + jsonString);
			if (StringUtil.isEmpty(jsonString)) {
				writeAjaxResult(response, "");
			}
			Object[] arr = JsonUtils.getObjectArrayFromJson(jsonString);
			Map<String, String> map = new HashMap<String, String>();
			for (Object name : arr) {
				Programs pro = programsService
						.checkLiveProgram(name.toString());
				if (pro != null) {
					map.put(name.toString(), pro.getChannelId());
				} else {
					map.put(name.toString(), "");
				}
			}
			JSONObject json = new JSONObject();
			writeAjaxResult(response, json.fromObject(map).toString());
		} catch (Exception e) {
			log.debug("同步请求处理异常" + e.getMessage());
			writeAjaxResult(response, "{}");
		}
	}

	@RequestMapping(value = "toPrograms", method = RequestMethod.GET)
	public String toPrograms(HttpServletRequest request, Model model) {
		String id = request.getParameter("cid");
		Programs p = new Programs();
		if (StringUtil.isNotEmpty(id)) {
			p = programsService.getProgramsById(id);
			model.addAttribute("sourceUrl", Global.SERVER_SOURCE_URL);
		}
		Channel channel = new Channel();
		channel.setStatus("0");
		channel.setParentId("0");
		List<Channel> channels = channelService.findChannelByCond(channel);
		model.addAttribute("channels", channels);
		channels = null;
		if (StringUtil.isNotEmpty(p.getChannelId())) {
			Channel cc = channelService.getChannelById(p.getChannelId());
			if (cc != null) {
				p.setAreaId(cc.getParentId());
				Channel c = new Channel();
				c.setAreaId(cc.getParentId());
				c.setStatus("1");
				List<Channel> csList = channelService.findChannelByCond(c);
				model.addAttribute("csList", csList);
				csList = null;
			}

		}
		model.addAttribute("p", p);
		return "live/programsInfo";
	}

	@RequestMapping(value = "addPrograms")
	public @ResponseBody
	Feedback addPrograms(HttpServletRequest request, Programs p) {
		boolean flag = false;
		try {
			p.setOperator(SessionUtil.getLoginName());
			programsService.addPrograms(p);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("增加节目单失败");
		}
		if (flag) {
			operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
					"新增节目单名称为：" + p.getName());
		}
		return Feedback.success("增加节目单成功");
	}

	@RequestMapping(value = "updatePrograms")
	public @ResponseBody
	Feedback updatePrograms(HttpServletRequest request, Programs p) {
		try {
			p.setOperator(SessionUtil.getLoginName());
			programsService.updatePrograms(p);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改节目单失败");
		}
		try {
			Programs old = programsService.getProgramsById(p.getId());
			String diffStr = ObjectUtil.getDiffColumnDescript(old, p);
			if (null != diffStr) {
				// 写入系统操作日志
				operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
						BUSI_NAME, diffStr);
			}
		} catch (Exception e) {

		}
		return Feedback.success("修改节目单成功");
	}

	private static final long MAX_IMG = 2097152;

	@RequestMapping(value = "uploadProgramsFile", method = RequestMethod.POST)
	public void uploadProgramsFile(MultipartHttpServletRequest file,
			HttpServletResponse response, HttpServletRequest request) {
		String fileId = request.getParameter("fileId");
		String channelId = request.getParameter("channelId");
		MultipartFile imgFile = file.getFile(fileId);
		if (imgFile != null) {
			if (imgFile.getSize() > MAX_IMG) {
				writeAjaxResult(response, "{\"result\":0,\"msg\":\"图片过大\"}");
				return;
			}
		}
		try {
			String imgUrl = UploadFile.uploadFileToServer(imgFile,
					Long.valueOf(channelId), Global.LIVE_IMG_URL);
			log.debug("imgUrl return is: " + imgUrl);
			writeAjaxResult(response, "{\"result\":1,\"msg\":\"" + imgUrl
					+ "\"}");
		} catch (Exception e) {
			writeAjaxResult(response, "{\"result\":0,\"msg\":\"上传出错\"}");
		}
	}

	@RequestMapping(value = "importPrograms", method = RequestMethod.POST)
	public void importPrograms(MultipartHttpServletRequest msr,
			HttpServletResponse response, HttpServletRequest request) {
		String msg = "";
		try {
			MultipartFile file = msr.getFile("filePrograms");
			String name = file.getOriginalFilename();
			String[] arr = name.split("_");
			if (arr.length != 2) {
				writeAjaxResult(response, "{\"msg\":\"文件名称格式不正确\"}");
			}
			String playDate = DateUtil.parseDate3(arr[1]);
			log.debug("fileName is: " + name + " playDate is: " + playDate);
			msg = programsService.addProgramsList(file.getInputStream(),
					playDate, request);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("============" + e.getMessage());
			msg = "读取文件出错";
		}
		writeAjaxResult(response, "{\"msg\":\"" + msg + "\"}");
	}

	@RequestMapping(value = "delProgramsList", method = RequestMethod.POST)
	public @ResponseBody
	Feedback delProgramsList(@RequestParam("ids") String ids,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(ids)) {
			return Feedback.fail("节目单删除失败,请选择要删除的节目单");
		}
		try {
			String[] arr = ids.split(",");
			programsService.delProgramsList(Arrays.asList(arr));
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("节目单删除失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_DELETE, request, BUSI_NAME,
					"删除节目单编号为：" + ids);
		}
		return Feedback.success("节目单删除成功");
	}

	@RequestMapping(value = "programsUnusual", method = RequestMethod.POST)
	public @ResponseBody
	Feedback programsUnusual(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("节目单置为异常失败,请选择要置为异常的节目单");
		}
		try {
			programsService.changePrograms("2", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("节目单为异常失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"置为异常节目单编号为：" + id);
		}
		return Feedback.success("节目单置为异常成功");
	}

	@RequestMapping(value = "programsNormal", method = RequestMethod.POST)
	public @ResponseBody
	Feedback programsNormal(@RequestParam("id") String id,
			HttpServletRequest request) {
		boolean flag = false;
		if (StringUtil.isEmpty(id)) {
			return Feedback.fail("节目异常恢复失败,请选择要异常恢复的节目");
		}
		try {
			programsService.changePrograms("1", id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("节目单异常恢复失败");
		}
		if (flag) {
			// 写入系统操作日志
			operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request, BUSI_NAME,
					"异常恢复节目单编号为：" + id);
		}
		return Feedback.success("节目单异常恢复成功");
	}

	@RequestMapping(value = "toCrawlPrograms", method = RequestMethod.GET)
	public String toCrawlPrograms(HttpServletRequest request, Model model) {
		return "live/crawlPrograms";
	}

	/**
	 * 抓取节目单
	 * 
	 * 创建人：pananz 创建时间：2015-5-11 - 下午4:35:00
	 * 
	 * @param request
	 * @return 返回类型：Feedback
	 * @exception throws
	 */
	@RequestMapping(value = "crawlPrograms", method = RequestMethod.POST)
	public @ResponseBody
	Feedback crawlPrograms(HttpServletRequest request) {
		try {
			String startDate = request.getParameter("startDate");
			String count = request.getParameter("count");
			String type = request.getParameter("type");
			String createFile = request.getParameter("createFile");
			Date start = DateUtil.formatDate(startDate);
			programsService.updatePrograms(type, start, count, createFile);
			return Feedback.success("抓取节目成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("抓取节目失败");
		}

	}
}
