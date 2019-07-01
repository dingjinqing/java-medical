package com.vpu.mp.controller.admin;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jooq.Record;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.saas.shop.ShopVersionService.VersionConfig;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminRoleController extends AdminBaseController {

	@ResponseBody
	@PostMapping(value = "/admin/account/shop/select")
	public JsonResult shopSelect(@RequestParam("subUsername") String subUsername,
			@RequestParam("isSubLogin") Boolean isSubLogin, @RequestParam("user_name") String userName,
			HttpServletRequest httpRequest) {
		return null;
//		String lange = request.getParameter("lang");
//		String token = request.getHeader("token");
//		String subSysId = null;
//		// 从redis获取信息
//		Map<Object, Object> map1 = adminAuth.getTokenInfo(token);
//		String sysId = map1.get("sys_id").toString();
//		String accountId = map1.get("account_id").toString();
//		if (isSubLogin) {
//			// 子账户
//			subSysId = sysId;
//		}
//		// 当前账号下的店铺列表处理
//		List<Map<String, Object>> shopList = saas.shop.getRoleShopList(adminAuth.sysIdNew(isSubLogin, sysId, subSysId),
//				adminAuth.subAccountIdNew(isSubLogin, accountId)).intoMaps();
//		if (shopList.size() == 0) {
//			return JsonResult.fail(lange, JsonResultCode.CODE_NO_SHOP);
//		}
//		for (Map<String, Object> shop : shopList) {
//			Timestamp expireTime = saas.shop.renew.getShopRenewExpireTime(Util.getInteger(shop.get("shop_id")));
//			int expireStatus = 1;
//			if (expireTime != null) {
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(expireTime);
//				cal.set(Calendar.HOUR, 0);
//				cal.set(Calendar.MINUTE, 0);
//				cal.set(Calendar.SECOND, 0);
//				if (cal.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
//					expireStatus = 0;
//				}
//			}
//			shop.put("expire_time", expireTime);
//			shop.put("expire_time_status", expireStatus);
//		}
//
//		// 文章相关处理
//		Record article = saas.article.noReadArticle(adminAuth.sysId());
//
//		System.out.println(shopList);
//		Map<String, Object> map = new HashMap(4);
//		// map.put("title", "选择店铺");
//		map.put("data_list", shopList);
//		// map.put("user", adminAuth.userInfo(httpRequest.getHeader("token")));
//		map.put("is_article", article != null ? 1 : 0);
//		map.put("article", article == null ? null : article.intoMap());
//		map.put("version_array", saas.shop.version.getVersionMap());
//		return JsonResult.success(lange, map);
	}

	@ResponseBody
	@RequestMapping(value = "/admin/account/shop/switch")
	public JsonResult switchShop(@RequestParam("shop_id") Integer shopId) {
		return null;
//		String lange = request.getParameter("lang");
//		VersionConfig config = saas.shop.version.mergeVersion(shopId);
//		String token = request.getHeader("token");
//		if (config == null) {
//			return JsonResult.fail(lange, JsonResultCode.CODE_SHOP_NO_VERSION_INFO);
//		}
//		if (!adminAuth.switchShopLoginByToken(shopId,token)) {
//			return JsonResult.fail(lange, JsonResultCode.CODE_FAIL);
//		}
//		// TODO: 需添加登录记录
//
//		// 跳转到首页
//		return JsonResult.success(lange, JsonResultCode.CODE_FAIL);
	}
}
