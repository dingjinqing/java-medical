package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.controller.BaseController;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.auth.AdminAuth;
import com.vpu.mp.service.foundation.MenuUtil;
import com.vpu.mp.service.foundation.MenuUtil.Menu;
import com.vpu.mp.service.foundation.MenuUtil.MenuIndex;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.shop.ShopApplication;

/**
 * 
 * @author 新国
 *
 */
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

	@Override
	protected ModelMap globalModelMap() {
		ModelMap model = new ModelMap();
		model.addAttribute("global_title", "微铺宝小程序商家后台");
		if (adminAuth.isLogin()) {
			List<Menu> menuList = saas.shop.menu.getRoleMenu(adminAuth.roleId());
			Integer topIndex = Util.convert(request.getParameter("top_index"), Integer.class, null);
			Integer subIndex = Util.convert(request.getParameter("sub_index"), Integer.class, null);
			MenuIndex menuIndex = MenuUtil.getMenuIndex(menuList, request.getRequestURI(),topIndex,subIndex);
			model.addAttribute("login_user", adminAuth.user());
			model.addAttribute("user_role_id", adminAuth.roleId());
			model.addAttribute("top_index", menuIndex.getTopIndex());
			model.addAttribute("sub_index", menuIndex.getSubIndex());
			model.addAttribute("shop_flag", getShopFlag());
			model.addAttribute("is_grasp_shop", isGraspShop());
			model.addAttribute("menuList", menuList);
			model.addAttribute("leftMenuList", menuList.get(menuIndex.getTopIndex()).getSubMenu());
			model.addAttribute("isShowGoodsImport", isShowGoodsImport());
			model.addAttribute("logo", new Object());
		}
		return model;
	}

	protected Integer isGraspShop() {
		if(adminAuth.shopId() == 0) {
			return 0;
		}
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


	protected Byte getShopFlag() {
		ShopRecord shop = saas.shop.getShopById(adminAuth.shopId());
		return shop == null ? 0 : shop.getShopFlag();
	}
	
	/**
	 * 得到当前登录店铺
	 * @return
	 */
	protected ShopApplication shop() {
		return saas.getShopApp(adminAuth.shopId());
	}
	
	/**
	 * 得到登录店铺ID
	 * @return
	 */
	protected Integer shopId() {
		return adminAuth.shopId();
	}
}
