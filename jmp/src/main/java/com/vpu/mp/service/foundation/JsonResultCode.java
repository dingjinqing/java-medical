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
	/**
	 * 0：成功 负数：系统错误
	 * 
	 * 其他返回码：6位数字，前两位为分类，后四位为该分类下的错误码 ;
	 * 
	 * @serialField 10开头：账号相关，登录、登出、角色等
	 * @serialField 11开头：图片
	 * @serialField 12开头：订单
	 * @serialField 13开头：商品
	 * @serialField 14开头：营销
	 * @serialField 15开头：用户
	 * @serialField 16开头：文章_分类
	 */

	// 公共码
	CODE_SUCCESS(0, JsonResultMessage.MSG_SUCCESS),
	CODE_FAIL(-1, JsonResultMessage.MSG_FAIL),
	CODE_PARAM_ERROR(-3, JsonResultMessage.MSG_PARAM_ERROR),

	// 账号
	CODE_ACCOUNT_OR_PASSWORD_INCRRECT(100001, JsonResultMessage.MSG_ACCOUNT_OR_PASSWORD_INCRRECT),
	CODE_ACCOUNT_MODILE_APPLIED(100002, JsonResultMessage.MSG_ACCOUNT_MODILE_APPLIED),
	CODE_ACCOUNT_MODILE_REGISTERED(100003, JsonResultMessage.MSG_ACCOUNT_MODILE_REGISTERED),
	CODE_ACCOUNT_LOGIN_EXPIRED(100004, JsonResultMessage.MSG_ACCOUNT_LOGIN_EXPIRED),
	CODE_ACCOUNT_ROLE__AUTH_INSUFFICIENT(100005, JsonResultMessage.MSG_ACCOUNT_ROLE__AUTH_INSUFFICIENT),
	CODE_ACCOUNT_ROLE__SHOP_SELECT(100006, JsonResultMessage.MSG_ACCOUNT_ROLE__SHOP_SELECT),
	CODE_ACCOUNT_NAME_NOT_NULL(100007, JsonResultMessage.MSG_ACCOUNT_NAME_NOT_NULL),
	CODE_ACCOUNT_ISSUBLOGIN_NOT_NULL(100007, JsonResultMessage.MSG_ACCOUNT_ISSUBLOGIN_NOT_NULL),
	CODE_ACCOUNT_MODILE_NOT_NULL(100008,JsonResultMessage.MSG_ACCOUNT_MODILE_NOT_NULL),
	CODE_ACCOUNT_SAME(100009, JsonResultMessage.MSG_ACCOUNT_SAME),

	// 图片
	CODE_IMGAE_UPLOAD_FAILED(110001, JsonResultMessage.MSG_IMGAE_UPLOAD_FAILED),
	CODE_IMGAE_FORMAT_INVALID(110002, JsonResultMessage.MSG_IMGAE_FORMAT_INVALID),
	CODE_IMGAE_CROP_FAILED(110003, JsonResultMessage.MSG_IMGAE_CROP_FAILED),
	CODE_IMGAE_UPLOAD_GT_5M(110004, JsonResultMessage.MSG_IMGAE_UPLOAD_GT_5M),
	CODE_IMGAE_UPLOAD_EQ_WIDTH(110005, JsonResultMessage.MSG_IMGAE_UPLOAD_EQ_WIDTH),
	CODE_IMGAE_UPLOAD_EQ_HEIGHT(110006, JsonResultMessage.MSG_IMGAE_UPLOAD_EQ_HEIGHT),

	// 订单

    // 商品
    GOODS_BRAND_NAME_EXIST(130011, JsonResultMessage.GOODS_BRAND_NAME_EXIST),
    GOODS_SORT_NAME_EXIST(130021,JsonResultMessage.GOODS_SORT_NAME_EXIST),
	
	//文章_分类
	CODE_ARTICLE_CATEGORY_IS_EXIST(16001, JsonResultMessage.ARTICLE_CATEGORY_IS_EXIST),
	CODE_ARTICLE_CATEGORY_CATEGORYNAME_ISNULL(16001, JsonResultMessage.ARTICLE_CATEGORY_CATEGORYNAME_ISNULL),
	CODE_ARTICLE_CATEGORY_CATEGORYID_ISNULL(16003, JsonResultMessage.ARTICLE_CATEGORY_CATEGORYID_ISNULL),
	CODE_ARTICLE_TITLE_ISNULL(16004, JsonResultMessage.ARTICLE_TITLE_ISNULL),
	CODE_ARTICLE_ARTICLEID_ISNULL(16005, JsonResultMessage.ARTICLE_ARTICLEID_ISNULL),
	CODE_ARTICLE_CATEGORY_UPDATE_FAILED(16006,JsonResultMessage.ARTICLE_CATEGORY_UPDATE_FAILED)
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
