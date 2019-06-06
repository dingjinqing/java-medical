package com.vpu.mp.controller.admin;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.XcxCustomerPageRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.shop.decoration.MpDecorationService.PageListQueryParam;
import com.vpu.mp.support.LineConvertHump;

@Controller
public class AdminMpDecorationController extends AdminBaseController {
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/admin/manage/decorate/page")
	public ModelAndView decorationFirstPage(@RequestParam("id") Integer pageId) {
		
		XcxCustomerPageRecord page = shop().mpDecoration.getPageById(pageId);
		String content = Util.toJSON(shop().mpDecoration.filterPageContent(page.getPageContent()));
		page.setPageContent(content);
		
		Map<String, Object> versionNumberMap = saas.shop.version.versionNumShow("decorate_num", this.shopId());
		Map<String, Object> self = (Map<String, Object>) versionNumberMap.get("self");
		self.put("use", String.format("%d", shop().mpDecoration.getPageCount()));

		ShopRecord shop = saas.shop.getShopById(shopId());
		
		ModelMap model = new ModelMap();
		model.addAttribute("title", "自定义页面装修");
		model.addAttribute("page", page.intoMap());
		model.addAttribute("shop", shop.intoMap());
		model.addAttribute("province", saas.region.province.getAll().intoMaps());
		model.addAttribute("version", self);
		model.addAttribute("version_mod", shop().mpDecoration.getVersionModules());
		model.addAttribute("page_cat_list", shop().pageClassification.getClassificationMap());
		return view("admin/mp_decorate", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/admin/manage/decorate/list")
	public ModelAndView getList(@LineConvertHump PageListQueryParam param) {
		if(param.del != null) {
			shop().mpDecoration.removeRow(param.del);
		}
		if(param.index != null) {
			shop().mpDecoration.setIndex(param.index);
		}
		if(this.isAjax() && param.pageId !=null && param.catId !=null) {
			shop().mpDecoration.setPageCatId(param.pageId, param.catId);
			return this.json(JsonResult.success());
		}
		
		Map<String, Object> versionNumberMap = saas.shop.version.versionNumShow("decorate_num", this.shopId());
		Map<String, Object> self = (Map<String, Object>) versionNumberMap.get("self");
		self.put("use", String.format("%d", shop().mpDecoration.getPageCount()));

		PageResult page = this.shop().mpDecoration.getPageList(param);

		ModelMap model = new ModelMap();
		model.addAttribute("title", "页面装修列表");
		model.addAttribute("data_list", page.dataList);
		model.addAttribute("page", page.page);
		model.addAttribute("version", versionNumberMap);
		model.addAttribute("input_map", this.inputMap());
		model.addAttribute("cat_list", shop().pageClassification.getClassificationMap());
		return view("admin/shop_decorate_list", model);
	}
}
