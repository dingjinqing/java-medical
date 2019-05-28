package com.vpu.mp.controller.admin;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jooq.Record;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.saas.article.ArticleService.ArticleListQueryParam;
import com.vpu.mp.service.saas.shop.ShopVersionService.VersionConfig;
import com.vpu.mp.support.LineConvertHump;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminRoleController extends AdminBaseController {

	@RequestMapping(value = "/admin/account/shop/select")
	public ModelAndView shopSelect() {

		// 当前账号下的店铺列表处理
		List<Map<String, Object>> shopList = saas.shop.getRoleShopList(adminAuth.sysId(), adminAuth.subAccountId())
				.intoMaps();
		if (shopList.size() == 0) {
			return redirect("/admin/login");
		}
		for (Map<String, Object> shop : shopList) {
			Date expireTime = saas.shop.renew.getShopRenewExpireTime(Util.getInteger(shop.get("shop_id")));
			int expireStatus = 1;
			if (expireTime != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(expireTime);
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				if (cal.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
					expireStatus = 0;
				}
			}
			shop.put("expire_time", expireTime);
			shop.put("expire_time_status", expireStatus);
		}

		// 文章相关处理
		Record article = saas.article.noReadArticle(adminAuth.sysId());

		System.out.println(shopList);
		ModelMap model = new ModelMap();
		model.addAttribute("title", "选择店铺");
		model.addAttribute("data_list", shopList);
		model.addAttribute("user", adminAuth.userInfo());
		model.addAttribute("is_article", article != null ? 1 : 0);
		model.addAttribute("article", article == null ? new Object() : article.intoMap());
		model.addAttribute("version_array", saas.shop.version.getVersionMap());
		return view("admin/toggle_shop", model);
	}
	
	@RequestMapping(value = "/admin/account/shop/switch")
	public ModelAndView switchShop(@RequestParam("shop_id") Integer shopId) {

		VersionConfig config = saas.shop.version.mergeVersion(shopId);
		if(config == null) {
			ModelMap model = new ModelMap();
			model.addAttribute("message", "该店铺暂无版本信息，部分功能将无法使用，请联系管理员！");
			return redirect("/admin/account/shop/select",model);
		}
		if(!adminAuth.switchShopLogin(shopId)) {
			return this.showMessage("切换店铺失败，你不具有此店铺权限！");
		}
		// TODO: 需添加登录记录

		// 跳转到首页
		return redirect("/admin/index");
	}
}
