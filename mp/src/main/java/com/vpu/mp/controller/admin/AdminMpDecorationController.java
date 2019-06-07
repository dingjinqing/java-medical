package com.vpu.mp.controller.admin;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.shop.tables.records.XcxCustomerPageRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.shop.decoration.MpDecorationService.PageListQueryParam;
import com.vpu.mp.service.shop.decoration.MpDecorationService.PageStoreParam;
import com.vpu.mp.support.LineConvertHump;

@Controller
public class AdminMpDecorationController extends AdminBaseController {

	@RequestMapping(value = "/admin/manage/decorate/page")
	public ModelAndView decorationFirstPage(@RequestParam(value = "id", required = false) Integer pageId,
			@RequestParam(value = "template_id", required = false) Integer templateId,
			@RequestParam(value = "copy_id", required = false) Integer copyId) {
		XcxCustomerPageRecord page = null;
		if(pageId != null) {
			page = shop().mpDecoration.getPageById(pageId);
		}else if(templateId != null) {
			page = shop().mpDecoration.cloneTemplate(templateId);
		}else if(copyId != null) {
			page = shop().mpDecoration.copyDecoration(copyId);
		}else {
			page = shop().mpDecoration.getEmptyPage();
		}
		String content = Util.toJSON(shop().mpDecoration.filterPageContent(page.getPageContent()));
		page.setPageContent(content);

		Map<String, Object> version = shop().version.getDecorateNumConfig();

		ShopRecord shop = saas.shop.getShopById(shopId());

		ModelMap model = new ModelMap();
		model.addAttribute("title", "自定义页面装修");
		model.addAttribute("page", page.intoMap());
		model.addAttribute("shop", shop.intoMap());
		model.addAttribute("cat_list", new ArrayList<Object>());
		model.addAttribute("province", saas.region.province.getAll().intoMaps());
		model.addAttribute("version", version.get("self"));
		model.addAttribute("shop_style", Util.toJSON(saas.shop.getShopStyle(this.shopId())));
		model.addAttribute("version_mod", shop().mpDecoration.getVersionModules());
		model.addAttribute("page_cat_list", shop().pageClassification.getClassificationMap());
		return view("admin/mp_decorate", model);
	}

	@RequestMapping(value = "/admin/manage/decorate/list")
	public ModelAndView getList(@LineConvertHump PageListQueryParam param) {
		if (param.del != null) {
			shop().mpDecoration.removeRow(param.del);
		}
		if (param.index != null) {
			shop().mpDecoration.setIndex(param.index);
		}
		if (this.isAjax() && param.pageId != null && param.catId != null) {
			shop().mpDecoration.setPageCatId(param.pageId, param.catId);
			return this.json(JsonResult.success());
		}

		Map<String, Object> version = shop().version.getDecorateNumConfig();

		PageResult page = this.shop().mpDecoration.getPageList(param);

		ModelMap model = new ModelMap();
		model.addAttribute("title", "页面装修列表");
		model.addAttribute("data_list", page.dataList);
		model.addAttribute("page", page.page);
		model.addAttribute("version", version);
		model.addAttribute("cat_list", shop().pageClassification.getClassificationMap());
		return view("admin/shop_decorate_list", model);
	}

	/**
	 * 选择模板
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/frame/decorate/choose")
	public ModelAndView chooseTemplate() {
		ModelMap model = new ModelMap();
		model.addAttribute("template", saas.shop.decoration.getAll().intoMaps());
		return view("admin/page_template", model);
	}
	
	/**
	 * 更新或添加页面
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/admin/manage/decorate/update")
	public ModelAndView update(@LineConvertHump PageStoreParam page) {
		XcxCustomerPageRecord record = shop().mpDecoration.storePage(page);
		if(page.pageState == (byte)2) {
			// 获取二维码
//			String code = "TODO";
//			if(true) {
//				return json(new JsonResult(2,"error",null));
//			}else {
//				return json(new JsonResult(2,null,code));
//			}
		}
		return jsonSuccess(record.getPageId().intValue());
	}

}
