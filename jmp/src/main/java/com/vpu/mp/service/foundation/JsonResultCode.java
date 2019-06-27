package com.vpu.mp.service.foundation;

import lombok.Getter;
import lombok.ToString;

/**
 * 
 * @author 新国
 *
 */
@Getter
@ToString
public enum JsonResultCode {

	// 公共码
	CODE_SUCCESS(0, "api.code.success"),
	CODE_FAIL(-1, "api.code.fail"),
	CODE_PARAM_ERROR(-3, "api.code.param_error"),
	
	// 账号
	CODE_ACCOUNT_OR_PWD_ERROR(10001, "api.code.account_or_pwd_error"),
	
	
	// 图片
	CODE_IMGAE_UPLOAD_FAILED(10002, "api.code.image_upload_failed"),
	CODE_IMGAE_INVALID(10003, "api.code.image_invalid"),
	CODE_IMGAE_CROP_FAILED(10004, "api.code.image_crop_failed"),
	
	// 用户
	CODE_MODILE_HAS_APPLIED(10006, "api.code.mobile_has_applied"),
	CODE_MODILE_HAS_REGISTERED(10007, "api.code.mobile_has_registered"),
	
	CODE_LOGIN_EXPIRED(10008, "api.code.login_expired"),
	CODE_ROLE__NO_AUTH(10009, "api.code.role_no_auth"),
	CODE_ROLE__NO_SELECT_SHOP(10010, "api.code.role_no_select_shop"),
	
	
	// 添加其他
	;


	/**
	 * 得到返回码
	 */
	private int code;

	/**
	 * 返回信息
	 */
	private String message;

	private JsonResultCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
