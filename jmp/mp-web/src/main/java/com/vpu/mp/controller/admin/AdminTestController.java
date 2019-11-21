package com.vpu.mp.controller.admin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.wechat.OpenPlatform;
import com.vpu.mp.service.wechat.bean.open.WxOpenMaSubScribeGeKeywordResult;
import com.vpu.mp.service.wechat.bean.open.WxOpenMaSubScribeGetCategoryResult;
import com.vpu.mp.service.wechat.bean.open.WxOpenMaSubScribeGetTemplateListResult;
import com.vpu.mp.service.wechat.bean.open.WxOpenMaSubScribeGetTemplateTitleResult;
import com.vpu.mp.service.wechat.bean.open.WxOpenMaSubscribeAddTemplateResult;

import me.chanjar.weixin.open.bean.result.WxOpenResult;

/**
 * 
 * @author lixinguo
 *
 */
@RestController
public class AdminTestController extends AdminBaseController {

	@Autowired
	protected OpenPlatform open;

	@RequestMapping(value = "/api/admin/test/addtemplate")
	public JsonResult addtemplate() throws Exception {
		String appId = "wxbb38922409fdaa24";
		String tid = "274";
		int[] kidList = { 1, 3, 4 };
		String sceneDesc = "模板描述";
		WxOpenMaSubscribeAddTemplateResult result = open.getMaExtService().addTemplate(appId, tid, kidList, sceneDesc);
		return success(result);
	}

	@RequestMapping(value = "/api/admin/test/deltemplate")
	public JsonResult deltemplate() throws Exception {
		String appId = "wxbb38922409fdaa24";
		String priTmplId = this.request.getParameter("priTmplId");
		WxOpenResult result = open.getMaExtService().delTemplate(appId, priTmplId);
		return success(result);
	}
	
	@RequestMapping(value = "/api/admin/test/getCategory")
	public JsonResult getCategory() throws Exception {
		String appId = "wxbb38922409fdaa24";
		WxOpenMaSubScribeGetCategoryResult result = open.getMaExtService().getTemplateCategory(appId);
		return success(result);
	}
	
	@RequestMapping(value = "/api/admin/test/getKeywords")
	public JsonResult getPubTemplateKeyWordsById() throws Exception {
		String appId = "wxbb38922409fdaa24";
		String tid = this.request.getParameter("tid");
		WxOpenMaSubScribeGeKeywordResult result = open.getMaExtService().getPubTemplateKeyWordsById(appId, tid);
		return success(result);
	}
	
	@RequestMapping(value = "/api/admin/test/getTitleList")
	public JsonResult getPubTemplateTitleList() throws Exception {
		String appId = "wxbb38922409fdaa24";
		String ids = this.request.getParameter("ids");
		int start = 0;
		int limit =30;
		WxOpenMaSubScribeGetTemplateTitleResult result = open.getMaExtService().getPubTemplateTitleList(appId, ids, start, limit);
		return success(result);
	}

	@RequestMapping(value = "/api/admin/test/getTemplateList")
	public JsonResult getTemplateList() throws Exception {
		String appId = "wxbb38922409fdaa24";
		WxOpenMaSubScribeGetTemplateListResult result = open.getMaExtService().getTemplateList(appId);
		return success(result);
	}
		
	@RequestMapping(value = "/api/admin/test/send")
	public JsonResult send() throws Exception {
		String appId = "wxbb38922409fdaa24";
		String toUser =  this.request.getParameter("toUser");
		String templateId =  this.request.getParameter("templateId");
		String page= this.request.getParameter("page");
		
		Map<String, Map<String, String>> data = new LinkedHashMap<>();
		Map<String, String> v1 = new LinkedHashMap<>();
		v1.put("value", "活动内容");
		data.put("thing1", v1);
		
		Map<String, String> v2 = new LinkedHashMap<>();
		v2.put("value", "活动地址");
		data.put("thing6", v2);
		
		WxOpenResult result = open.getMaExtService().sendTemplate(appId, toUser, templateId, page, data);
		return success(result);
	}
}
