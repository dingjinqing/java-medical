package com.vpu.mp.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vpu.mp.db.main.tables.pojos.Shop;
import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.saas.shop.ShopService.ShopListQueryParam;
import com.vpu.mp.support.LineConvertHump;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class SystemShopController extends SystemBaseController {

	@RequestMapping(value = "/system/shop/list")
	public ModelAndView shopList(@LineConvertHump ShopListQueryParam param) {
		PageResult result = saas.shop.getPageList(param);

		String[] shopFlagList = new String[] { "店+", "欧派", "寺库" };
		ModelMap model = new ModelMap();
		model.addAttribute("title", "店铺列表");
		model.addAttribute("data_list", result.dataList);
		model.addAttribute("page", result.page);
		model.addAttribute("account",
				param.sysId == null ? new Object() : saas.shop.accout.getAccountInfoForID(param.sysId).intoMap());
		model.addAttribute("version_array", saas.shop.version.getVersionMap());
		model.addAttribute("version_list", saas.shop.version.getAllVersion().intoMaps());
		model.addAttribute("nav_type", 0);
		model.addAttribute("shop_flag_list", shopFlagList);
		return view("system/shop_list", model);
	}

	@GetMapping(value = "/system/shop/add")
	public ModelAndView showAddShop(@RequestParam("sys_id") Integer sysId) {
		ShopAccountRecord accountInfo = saas.shop.accout.getAccountInfoForID(sysId);
		if (accountInfo == null) {
			return this.showMessage("当前账号不存在！");
		}
		ModelMap model = new ModelMap();
		model.addAttribute("title", "添加店铺");
		model.addAttribute("shop", new Object());
		model.addAttribute("nav_type", 2);
		model.addAttribute("shop_account", accountInfo.intoMap());
		model.addAttribute("act_url", "/system/shop/add?sys_id=" + sysId);
		model.addAttribute("version_list", saas.shop.version.getAllVersion().intoMaps());
		return view("system/shop_info", model);
	}

	@PostMapping(value = "/system/shop/add")
	public ModelAndView addShop(@LineConvertHump Shop shop) {
		saas.shop.addShop(shop);
		return redirect("/system/shop/list");

	}
	
	@GetMapping(value = "/system/shop/edit")
	public ModelAndView showUpdateShop(@RequestParam("shop_id") Integer shopId) {
		ShopRecord shopInfo = saas.shop.getShopById(shopId);
		if (shopInfo == null) {
			return this.showMessage("店铺不存在！");
		}
		ShopAccountRecord accountInfo = saas.shop.accout.getAccountInfoForID(shopInfo.getSysId());
		ModelMap model = new ModelMap();
		model.addAttribute("title", "编辑店铺");
		model.addAttribute("shop", new Object());
		model.addAttribute("nav_type", 3);
		model.addAttribute("shop_account", accountInfo.intoMap());
		model.addAttribute("act_url", "/system/shop/edit?shop_id=" + shopId);
		model.addAttribute("version_list", saas.shop.version.getAllVersion().intoMaps());
		return view("system/shop_info", model);
	}
	
	@PostMapping(value = "/system/shop/edit")
	public ModelAndView updateShop(@LineConvertHump Shop shop) {
		saas.shop.updateShop(shop);
		 return showMessage("更新店铺成功");
	}

	@PostMapping(value = "/system/shop/check/mobile")
	@ResponseBody
	public JsonResult checkMobile(@RequestParam String mobile) {
		if (saas.shop.hasMobile(mobile)) {
			return JsonResult.fail("手机号已注册");
		}
		return JsonResult.success("手机号可以注册");
	}
}
