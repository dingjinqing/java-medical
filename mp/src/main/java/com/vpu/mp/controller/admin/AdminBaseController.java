package com.vpu.mp.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.controller.BaseController;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.auth.AdminAuth;

public class AdminBaseController extends BaseController {
	
	@Autowired
	protected AdminAuth adminAuth;

	/**
	 * 显示信息页面
	 * 
	 * @param message
	 * @param links
	 * @return
	 */
	protected ModelAndView showMessage(String message) {
		ModelMap model = new ModelMap();
		model.addAttribute("message", message);
		return redirect("/admin/message", model);
	}

	protected ModelAndView showMessage(String message, Object links) {
		ModelMap model = new ModelMap();
		model.addAttribute("message", message);
		model.addAttribute("links", links);
		return redirect("/admin/message", model);
	}

	protected ModelMap globalModelMap() {
		ModelMap model = new ModelMap();
		model.addAttribute("global_title", "微铺宝小程序商家后台");
		if (adminAuth.isLogin()) {
			model.addAttribute("login_user", adminAuth.user());
			model.addAttribute("user_role_id", adminAuth.roleId());
			model.addAttribute("top_index", getTopMenuIndex());
			model.addAttribute("sub_index", getSubMenuIndex());
			model.addAttribute("shop_flag", getShopFlag());
			model.addAttribute("is_grasp_shop", isGraspShop());
			model.addAttribute("isShowGoodsImport", isShowGoodsImport());
		}
		return model;
	}

	protected Integer isGraspShop() {
		ShopRecord shop = saas.shop.getShopById(adminAuth.shopId());
		Byte opai = 1, secoo = 2;
		return (shop.getShopFlag() == opai || shop.getShopFlag() == secoo) ? 1 : 0;
	}

	protected boolean isShowGoodsImport() {
		Integer[] showShopIds = { 6261249, 7963871, 4748160, 1398927, 2048756, 7893594 };
		for (Integer shopId : showShopIds) {
			if (shopId == adminAuth.shopId()) {
				return true;
			}
		}
		return false;
	}

	protected Integer getTopMenuIndex() {
		return 0;
	}

	protected Integer getSubMenuIndex() {
		return 0;
	}

	protected Byte getShopFlag() {
		ShopRecord shop = saas.shop.getShopById(adminAuth.shopId());
		return shop.getShopFlag();
	}
}
