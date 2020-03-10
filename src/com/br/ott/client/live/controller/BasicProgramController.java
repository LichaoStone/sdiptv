package com.br.ott.client.live.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.br.ott.Global;
import com.br.ott.base.BaseController;
import com.br.ott.client.common.DictCache;
import com.br.ott.client.common.service.OperaLogService;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.client.live.entity.BasicProgram;
import com.br.ott.client.live.entity.Channel;
import com.br.ott.client.live.service.BasicProgramService;
import com.br.ott.client.live.service.ChannelService;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.Feedback;
import com.br.ott.common.util.ObjectUtil;
import com.br.ott.common.util.StringUtil;
import com.br.ott.common.util.UploadFile;

/* 
 *  
 *  文件名：BasicProgramController.java
 *  创建人：pananz
 *  创建时间：2014-7-4 - 下午7:48:07
 */
@Controller
@RequestMapping(value = "/bprogram")
public class BasicProgramController extends BaseController {

	@Autowired
	private BasicProgramService basicprogramService;

	@Autowired
	private OperaLogService operaLogService;

	@Autowired
	private ChannelService channelService;

	private static final String BUSI_NAME = "节目管理";

	@RequestMapping(value = "findbprograms", method = RequestMethod.GET)
	public String findBasicProgram(HttpServletRequest request, BasicProgram p,
			Model model) {
		p.setCurrentPage(getPageNo(request));
		p.setShowCount(getPageRow(request));
		Map<String, Dictionary> ptypes = DictCache
				.getDictValueList(DicType.JMLX);
		Map<String, Dictionary> ttypes = DictCache
				.getDictValueList(DicType.LXFL);
		List<BasicProgram> list = basicprogramService.findBasicProgramByPage(p);
		model.addAttribute("ptypes", ptypes);
		model.addAttribute("ttypes", ttypes);
		model.addAttribute("list", list);
		model.addAttribute("p", p);
		list = null;
		return "live/listBasicProgram";
	}

	@RequestMapping(value = "toAddBProgram", method = RequestMethod.GET)
	public String toAddBProgram(HttpServletRequest request, Model model) {
		if (null != request.getParameter("name")) {
			model.addAttribute("name", request.getParameter("name"));
		}
		// 获取字典类型信息
		Map<String, Dictionary> ptypes = DictCache
				.getDictValueList(DicType.JMLX);
		Map<String, Dictionary> ttypes = DictCache
				.getDictValueList(DicType.LXFL);
		Map<String, Dictionary> cProviders = DictCache
				.getDictValueList(DicType.LRTGS);
		Map<String, Dictionary> languages = DictCache
				.getDictValueList(DicType.YUYAN);
		Map<String, Dictionary> bitrates = DictCache
				.getDictValueList(DicType.MALV);
		Map<String, Dictionary> origins = DictCache
				.getDictValueList(DicType.JMZYCD);
		Map<String, Dictionary> ztypes = DictCache
				.getDictValueList(DicType.ZSLX);
		// 获取频道类型
		Channel channel = new Channel();
		channel.setSecondNode("0");
		channel.setOrderColumn("id");
		channel.setStatus("1");
		List<Channel> channels = channelService.findChannelByCond(channel);
		model.addAttribute("channels", channels);
		channels = null;
		// List<ProductType> alltypes =
		// productTypeService.findChildPTypeById(JMZYLX);
		// //获取节目一级类型集合
		// List<ProductType> yjtypes =
		// productTypeService.findProductTypeByPid(JMZYLX);
		// //定义二级类型map ptypesmap
		// Map<String, List> ejtypemaps = new LinkedHashMap<String, List>();
		// for (ProductType ptype : yjtypes) {
		// List<ProductType> ejtypes = new ArrayList();
		// for (ProductType pttype : alltypes) {
		// if(ptype.getId().equals(pttype.getParentId())){
		// ejtypes.add(pttype);
		// }
		// }
		// ejtypemaps.put(ptype.getId(), ejtypes);
		// ejtypes = null;
		// }
		model.addAttribute("ptypes", ptypes);
		model.addAttribute("ttypes", ttypes);
		model.addAttribute("cProviders", cProviders);
		model.addAttribute("languages", languages);
		model.addAttribute("bitrates", bitrates);
		model.addAttribute("origins", origins);
		model.addAttribute("ztypes", ztypes);
		return "live/basicProgramInfo";
	}

	@RequestMapping(value = "addBProgram", method = RequestMethod.POST)
	public @ResponseBody
	Feedback addBProgram(HttpServletRequest request, BasicProgram p) {
		try {
			basicprogramService.addBasicProgram(p);
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("新增节目失败！");
		}
		try {
			operaLogService.addOperaLog(OPERA_TYPE_ADD, request, BUSI_NAME,
					"新增节目-" + p.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Feedback.success("新增节目成功！");
	}

	@RequestMapping(value = "toUpdateBProgram", method = RequestMethod.GET)
	public String toUpdateBProgram(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		BasicProgram bp = new BasicProgram();
		if (StringUtil.isNotEmpty(id)) {
			bp = basicprogramService.getBasicProgramById(id);
			model.addAttribute("sourceUrl", Global.SERVER_SOURCE_URL);
		}
		// 获取字典类型信息
		Map<String, Dictionary> ptypes = DictCache
				.getDictValueList(DicType.JMLX);
		Map<String, Dictionary> ttypes = DictCache
				.getDictValueList(DicType.LXFL);
		Map<String, Dictionary> cProviders = DictCache
				.getDictValueList(DicType.LRTGS);
		Map<String, Dictionary> languages = DictCache
				.getDictValueList(DicType.YUYAN);
		Map<String, Dictionary> bitrates = DictCache
				.getDictValueList(DicType.MALV);
		Map<String, Dictionary> origins = DictCache
				.getDictValueList(DicType.JMZYCD);
		Map<String, Dictionary> ztypes = DictCache
				.getDictValueList(DicType.ZSLX);
		// 获取频道类型
		Channel channel = new Channel();
		channel.setSecondNode("0");
		channel.setOrderColumn("id");
		channel.setStatus("1");
		List<Channel> channels = channelService.findChannelByCond(channel);
		model.addAttribute("channels", channels);
		model.addAttribute("ptypes", ptypes);
		model.addAttribute("ttypes", ttypes);
		model.addAttribute("cProviders", cProviders);
		model.addAttribute("languages", languages);
		model.addAttribute("bitrates", bitrates);
		model.addAttribute("origins", origins);
		model.addAttribute("ztypes", ztypes);
		model.addAttribute("p", bp);
		channels = null;
		return "live/basicProgramInfo";
	}

	@RequestMapping(value = "updateBProgram")
	public @ResponseBody
	Feedback updateBProgram(HttpServletRequest request, BasicProgram p) {
		try {
			BasicProgram old = basicprogramService.getBasicProgramById(p
					.getId());
			String diffStr = ObjectUtil.getDiffColumnDescript(old, p);
			basicprogramService.updateBasicProgram(p);
			if (null != diffStr) {
				// 写入系统操作日志
				operaLogService.addOperaLog(OPERA_TYPE_MODIFY, request,
						BUSI_NAME, diffStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Feedback.fail("修改节目失败");
		}
		return Feedback.success("修改节目成功");
	}

	@RequestMapping(value = "toBProgram", method = RequestMethod.GET)
	public String toBProgram(HttpServletRequest request, Model model) {
		String cid = request.getParameter("cid");
		String name = request.getParameter("name");
		BasicProgram bp = basicprogramService.findBasicProgramByName(cid, name);
		if (StringUtil.isNotEmpty(name)) {
			model.addAttribute("sourceUrl", Global.SERVER_SOURCE_URL);
		}
		// 获取字典类型信息
		Map<String, Dictionary> ptypes = DictCache
				.getDictValueList(DicType.JMLX);
		Map<String, Dictionary> ttypes = DictCache
				.getDictValueList(DicType.LXFL);
		Map<String, Dictionary> cProviders = DictCache
				.getDictValueList(DicType.LRTGS);
		Map<String, Dictionary> languages = DictCache
				.getDictValueList(DicType.YUYAN);
		Map<String, Dictionary> bitrates = DictCache
				.getDictValueList(DicType.MALV);
		Map<String, Dictionary> origins = DictCache
				.getDictValueList(DicType.JMZYCD);
		Map<String, Dictionary> ztypes = DictCache
				.getDictValueList(DicType.ZSLX);
		// 获取频道列表
		Channel channel = new Channel();
		channel.setSecondNode("0");
		channel.setOrderColumn("id");
		channel.setStatus("1");
		List<Channel> channels = channelService.findChannelByCond(channel);
		try {
			model.addAttribute("name", URLEncoder.encode(name, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		channels = null;
		model.addAttribute("channels", channels);
		model.addAttribute("ptypes", ptypes);
		model.addAttribute("ttypes", ttypes);
		model.addAttribute("cProviders", cProviders);
		model.addAttribute("languages", languages);
		model.addAttribute("bitrates", bitrates);
		model.addAttribute("origins", origins);
		model.addAttribute("ztypes", ztypes);
		model.addAttribute("p", bp);
		return "live/showbProgramInfo";
	}

	/**
	 * 验证输入的名称是否存在
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "findbProgramByName", method = RequestMethod.GET)
	public void findbProgramByName(HttpServletRequest request,
			HttpServletResponse response) {
		String name = request.getParameter("name");
		String oldName = request.getParameter("oldName");
		if (StringUtil.isEmpty(name) || oldName.equals(name)) {
			writeAjaxResult(response, String.valueOf(true));
			return;
		}
		boolean result = basicprogramService.findBProgramByName(
				request.getParameter("cid"), name);
		writeAjaxResult(response, String.valueOf(result));
	}

	private static final long MAX_IMG = 2097152;

	@RequestMapping(value = "uploadBasicProgramFile", method = RequestMethod.POST)
	public void uploadBasicProgramFile(MultipartHttpServletRequest file,
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
			String ImgUrl = UploadFile.uploadFileToServer(imgFile,
					Long.valueOf(channelId), Global.LIVE_IMG_URL);
			writeAjaxResult(response, "{\"result\":1,\"msg\":\"" + ImgUrl
					+ "\"}");
		} catch (Exception e) {
			e.printStackTrace();
			writeAjaxResult(response, "{\"result\":0,\"msg\":\"上传出错\"}");
		}
	}
}
