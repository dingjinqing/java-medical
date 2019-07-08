package com.vpu.mp.controller.admin;

import java.sql.Timestamp;
import java.util.List;

import org.jooq.Record9;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.auth.ShopReq;
import com.vpu.mp.service.pojo.shop.auth.ShopSelectResp;

/**
 * 
 * @author 新国
 *
 */
@RequestMapping("/api")
@Controller
@RestController
public class AdminRoleController extends AdminBaseController {
	final String TOKEN = "V-Token";

	/**
	 * 查询店铺列表
	 * @return
	 */
	@PostMapping(value = "/admin/account/shop/select")
	public JsonResult shopSelect() {
		AdminTokenAuthInfo info = adminAuth.user();
		if (info == null) {
			return fail(JsonResultCode.CODE_ACCOUNT_LOGIN_EXPIRED);
		}
		// saas.shop.accout
		List<Record9<Integer, Integer, String, String, Timestamp, Byte, Byte, Byte, String>> shopList = saas.shop
				.getRoleShopList(info.getSysId(), info.getSubAccountId());
		if (shopList.size() == 0) {
			logger().info("用户sysId：" + info.getSysId() + "，店铺列表为空");
		}
		ShopSelectResp response = new ShopSelectResp();
		response.setDataList(saas.shop.getShopList(info, shopList));
		response.setVersionMap(saas.shop.version.getVersionMap());
		return success(response);

	}

	/**
	 * 切换店铺
	 * @param shopId
	 * @return
	 */
	@RequestMapping(value = "/admin/account/shop/switch")
	public JsonResult switchShop(@RequestBody ShopReq shopReq) {
		//更新redis
		if(adminAuth.switchShopLogin(shopReq.getShopId())){
			return success(JsonResultCode.CODE_SUCCESS);			
		}
		return fail(JsonResultCode.CODE_ACCOUNT_SHOP_NULL); 
	}
}
