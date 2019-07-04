package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.auth.ShopLoginParam;

/**
 * 
 * @author 新国
 *
 */
@RestController
@RequestMapping("/api")
public class AdminLoginController extends AdminBaseController {

	@PostMapping(value = "/admin/login")
	public JsonResult login(@RequestBody @Valid ShopLoginParam param, BindingResult result) {
		if (result.hasErrors()) {
			return this.fail(result.getFieldError().getDefaultMessage());
		}
		AdminTokenAuthInfo info = adminAuth.login(param);
		if (info != null) {
			return this.success(info);
		} else {
			return this.fail(JsonResultCode.CODE_ACCOUNT_OR_PASSWORD_INCRRECT);
		}
	}


	@GetMapping(value = "/admin/logout")
	public JsonResult logout() {
		adminAuth.logout();
		return success(JsonResultCode.CODE_SUCCESS);
	}
	
	@GetMapping(value = "/admin/account/shop/switch/{shopId}")
	public JsonResult switchShop(@PathVariable Integer shopId ) {
		if(adminAuth.switchShopLogin(shopId)) {
			return success();
		}
		return fail(JsonResultCode.CODE_ACCOUNT_ROLE__AUTH_INSUFFICIENT);
	}

}
