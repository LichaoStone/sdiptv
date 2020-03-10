package com.br.ott.client.common.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
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
import com.br.ott.client.common.entity.District;
import com.br.ott.client.common.service.DictionaryService;
import com.br.ott.client.dict.entity.Dictionary;
import com.br.ott.client.partner.entity.Partner;
import com.br.ott.client.partner.service.PartnerService;
import com.br.ott.common.util.Constants.DicType;
import com.br.ott.common.util.Constants.PartnerState;
import com.br.ott.common.util.PagedModelList;
import com.br.ott.common.util.StringUtil;
import com.br.ott.common.util.UploadFile;

/**
 * 公用控制器
 * 
 * @author 陈登民
 * 
 */
@Controller
@RequestMapping(value = "/common")
public class CommonController extends BaseController {
	private static final Logger logger = Logger
			.getLogger(CommonController.class);

	@Autowired
	private PartnerService partnerService;

	@Autowired
	private DictionaryService dictionaryService;

	private static final long MAX_IMG = 2097152;

	/**
	 * 上传文件
	 * 
	 * 创建人：pananz 创建时间：2015-6-8 - 上午11:01:42
	 * 
	 * @param file
	 * @param response
	 * @param request
	 *            返回类型：void
	 * @exception throws
	 */
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	public void uploadFile(MultipartHttpServletRequest file,
			HttpServletResponse response, HttpServletRequest request) {
		String fileId = request.getParameter("fileId");
		MultipartFile imgFile = file.getFile(fileId);
		if (imgFile != null) {
			if (imgFile.getSize() > MAX_IMG) {
				writeAjaxResult(response, "{\"result\":0,\"msg\":\"图片过大\"}");
				return;
			}
		}
		try {
			String ImgUrl = UploadFile.uploadFileToServer(imgFile, 99999999L,
					Global.LIVE_IMG_URL);
			writeAjaxResult(response, "{\"result\":1,\"msg\":\"" + ImgUrl
					+ "\"}");
		} catch (Exception e) {
			writeAjaxResult(response, "{\"result\":0,\"msg\":\"上传出错\"}");
		}
	}

	/**
	 * 切换省份
	 * 
	 * @return
	 */
	@RequestMapping(value = "changeCity")
	public @ResponseBody
	List<District> changeCity(HttpServletRequest request, Model model) {
		String proId = request.getParameter("proId");
		logger.debug("==切换省份，获取城市,省份ID：" + proId);
		return getCitys(proId);
	}

	/**
	 * 转到合作伙伴页面
	 * 
	 * @param request
	 * @param partner
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "partner", method = RequestMethod.GET)
	public String partner(Model model) {
		Map<String, Dictionary> mechantTypes = DictCache
				.getDictValueList(DicType.MERCHANT_TYPE);
		model.addAttribute("mechantTypes", mechantTypes);
		model.addAttribute("partner", new Partner());
		return "common/partner";
	}

	/**
	 * 合作伙伴AJAVA查询
	 * 
	 * @param request
	 * @param response
	 * @param partner
	 */
	@RequestMapping(value = "findPartner", method = RequestMethod.GET)
	public void findPartner(HttpServletRequest request,
			HttpServletResponse response, Partner partner) {
		int skip = 1;
		String page = request.getParameter("page");
		if (!StringUtil.isEmpty(page)) {
			skip = Integer.valueOf(page);
		}
		partner.setReadyOrPass(PartnerState.PARTNER_READY + ","
				+ PartnerState.PARTNER_PASS);
		PagedModelList<Partner> pml = partnerService.findPartner(partner, skip,
				getPageRow(request));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("pageCount", pml.getPageCount());
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(pml
				.getPagedModelList());
		jsonObject.put("result", jsonArray.toString());
		pml = null;
		writeJson(response, jsonObject);
	}

	/**
	 * 转到选择字典类型页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "dict", method = RequestMethod.GET)
	public String dict() {
		return "common/dict";
	}

	/**
	 * 字典类型树形结构输出
	 * 
	 * @param response
	 */
	@RequestMapping(value = "buildTreeDict", method = RequestMethod.POST)
	public void buildTreeDict(HttpServletResponse response) {
		writeAjaxResult(response, dictionaryService.buildTreeDict());
	}

	private static Random random = new Random();

	private Color getRandColor(int fc, int bc) {
		return getRandColor(fc, bc, fc);
	}

	private Color getRandColor(int fc, int bc, int interval) {
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - interval);
		int g = fc + random.nextInt(bc - interval);
		int b = fc + random.nextInt(bc - interval);
		return new Color(r, g, b);
	}

	/**
	 * 生成验证码
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "getVeCode", method = RequestMethod.GET)
	public void getVeCode(HttpServletResponse response,
			HttpServletRequest request) {
		try {
			int codeLength = 4;
			int mixTimes = 0;
			Color bgColor = getRandColor(200, 250);
			Color bfColor = new Color(0, 0, 0);
			boolean ifRandomColor = true;
			boolean ifMixColor = true;

			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			int width = 13 * codeLength + 6, height = 20;
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			g.setColor(bgColor);
			g.fillRect(0, 0, width, height);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
			g.setColor(new Color(33, 66, 99));
			g.drawRect(0, 0, width - 1, height - 1);
			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < mixTimes * codeLength / 10; i++) {
				if (ifMixColor) {
					g.setColor(getRandColor(160, 200));
				}
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}
			char c[] = new char[62];
			for (int i = 97, j = 0; i < 123; i++, j++) {
				c[j] = (char) i;
			}
			for (int o = 65, p = 26; o < 91; o++, p++) {
				c[p] = (char) o;
			}
			for (int m = 48, n = 52; m < 58; m++, n++) {
				c[n] = (char) m;
			}
			StringBuffer sRand = new StringBuffer();
			for (int i = 0; i < codeLength; i++) {
				int x = random.nextInt(62);
				String rand = String.valueOf(c[x]);
				sRand.append(rand);

				if (ifRandomColor)
					g.setColor(getRandColor(20, 110, 0));
				else
					g.setColor(bfColor);
				g.drawString(rand, 13 * i + 6, 16);
			}
			HttpSession session = request.getSession(true);
			session.setAttribute("rand", sRand.toString());
			g.dispose();
			ImageIO.write(image, "PNG", response.getOutputStream());
		} catch (Exception e) {
		}
	}

}
