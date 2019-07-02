package com.vpu.mp.controller.system;

import java.util.HashMap;
import java.util.Map;

import org.jooq.Result;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.db.main.tables.pojos.ShopAccount;
import com.vpu.mp.db.main.tables.records.DictCityRecord;
import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.saas.shop.ShopAccountService.ShopAccountListQueryParam;
import com.vpu.mp.support.LineConvertHump;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class SystemShopAccountController extends SystemBaseController {
	/**
	 * 查询店铺列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@PostMapping("/system/shop/account/list")
	public JsonResult getShopAccountList(@LineConvertHump ShopAccountListQueryParam param) {
		PageResult<HashMap> result = saas.shop.accout.getPageList(param);
		String lange = request.getParameter("lang");
		for (Map<String, Object> row : result.dataList) {
			row.put("shop_number", saas.shop.renew.getShopNumber((Integer) row.get("sys_id")));
			row.put("renew_money", saas.shop.renew.getRenewTotal((Integer) row.get("sys_id")));
			if (row.get("end_time") != null) {
				row.put("end_time", row.get("end_time").toString().substring(0, 10));
			}
		}
		Map<String, Object> map = new HashMap(3);
		// map.put("title", "商家账号列表");
		map.put("data_list", result.dataList);
		map.put("page", result.page);
		map.put("nav_type", 0);
		return JsonResult.success(lange, map);
	}

	@ResponseBody
	@PostMapping(value = "/system/shop/account/add")
	public JsonResult addShopAccount(@LineConvertHump ShopAccount account) {
		String lange = request.getParameter("lang");
		if (sysAuth.addShopAccountService(account)) {
			return JsonResult.success(lange, JsonResultCode.CODE_SUCCESS);
		} else {
			return JsonResult.fail(lange, JsonResultCode.CODE_ACCOUNT_SAME);
		}
	}

	@GetMapping(value = "/system/shop/account/edit/{sys_id}")
	public ModelAndView showEditShopAccount(@PathVariable("sys_id") Integer sysId) {
		ShopAccountRecord record = saas.shop.accout.getAccountInfoForID(sysId);
		if (record == null) {
			return this.showMessage("商家账号不存在");
		}
		ModelMap model = new ModelMap();
		model.addAttribute("title", "编辑商家账号");
		model.addAttribute("shop_account", record.intoMap());
		model.addAttribute("province", saas.region.province.getAll().intoMaps());
		model.addAttribute("city", saas.region.city.getCityList(110000).intoMaps());
		model.addAttribute("district", saas.region.district.getDistrictList(110100).intoMaps());
		model.addAttribute("act_url", "/system/shop/account/edit/" + sysId);
		model.addAttribute("nav_type", 3);
		return view("system/shop_account_info", model);
	}

	@PostMapping(value = "/system/shop/account/edit/{sys_id}")
	public ModelAndView updateShopAccount(@LineConvertHump ShopAccount account, @PathVariable("sys_id") Integer sysId) {
		if (!StringUtils.isEmpty(account.getPassword())) {
			account.setPassword(Util.md5(account.getPassword()));
		} else {
			account.setPassword(null);
		}
		saas.shop.accout.updateAccountInfo(account);
		return this.showMessage("更新商家账号成功");
	}

	@RequestMapping(value = "/system/user/address")
	@ResponseBody
	public JsonResult getRegionList(
			@RequestParam(value = "province_id", required = false, defaultValue = "0") Integer provinceId,
			@RequestParam(value = "city_id", required = false, defaultValue = "0") Integer cityId) {
		if (provinceId != 0) {
			Map<String, Object> data = new HashMap<String, Object>(2);
			Result<DictCityRecord> r = saas.region.city.getCityList(provinceId);
			cityId = r.size() > 0 ? r.get(0).getCityId() : 0;
			data.put("city", r.intoMaps());
			data.put("district", saas.region.district.getDistrictList(cityId).intoMaps());
			return JsonResult.success(null, data);
		}
		if (cityId != 0) {
			return JsonResult.success(null, saas.region.district.getDistrictList(cityId).intoMaps());
		}
		return JsonResult.fail("fail", null);
	}

}
