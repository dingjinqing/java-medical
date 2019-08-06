package com.vpu.mp.service.foundation.data;

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
	 * @serialField 17开头：小程序管理
	 * @serialField 18开头：门店管理
	 * @serialField 19开头：基础配置
	 * @serialField 20开头：概览
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
	CODE_MOBILE_SAME(100010, JsonResultMessage.MSG_MOBILE_SAME),
	CODE_ACCOUNT_SHOP_NULL(100011, JsonResultMessage.MSG_ACCOUNT_SHOP_NULL),
	CODE_ACCOUNT_SHOP_EXPRIRE(100012, JsonResultMessage.MSG_ACCOUNT_SHOP_EXPIRE),
	CODE_ACCOUNT_PASSWD_NO_SAME(100013, JsonResultMessage.MSG_ACCOUNT_PASSWD_NO_SAME),
	CODE_ACCOUNT_OLD_PASSWD_ERROR(100014, JsonResultMessage.MSG_ACCOUNT_OLD_PASSWD_ERROR),
	CODE_ACCOUNT_OLD_NEW_PASSWD_NO_SAME(100015, JsonResultMessage.MSG_ACCOUNT_OLD_NEW_PASSWD_NO_SAME),
	CODE_MSG_ACCOUNT_PASSWD_NOT_NULL(100016, JsonResultMessage.MSG_ACCOUNT_PASSWD_NOT_NULL),
	CODE_MSG_ACCOUNT_NEWPASSWD_NOT_NULL(100017, JsonResultMessage.MSG_ACCOUNT_NEWPASSWD_NOT_NULL),
	CODE_ACCOUNT_CONFNEWPASSWD_NOT_NULL(100018, JsonResultMessage.MSG_ACCOUNT_CONFNEWPASSWD_NOT_NULL),
	CODE_ACCOUNT_SHOPAVATAR_NOT_NULL(100019, JsonResultMessage.MSG_ACCOUNT_SHOPAVATAR_NOT_NULL),
	CODE_ACCOUNT_ACCOUNTNAME_NOT_NULL(100020, JsonResultMessage.MSG_ACCOUNT_ACCOUNTNAME_NOT_NULL),
	CODE_ACCOUNT_PASSWD_LENGTH_LIMIT(100021, JsonResultMessage.MSG_ACCOUNT_PASSWD_LENGTH_LIMIT),
	CODE_ACCOUNT_USERNAME_NOT_NULL(100022, JsonResultMessage.MSG_ACCOUNT_USERNAME_NOT_NULL),
	CODE_ACCOUNT_SHOPID_NOT_NULL(100023, JsonResultMessage.MSG_ACCOUNT_SHOPID_NOT_NULL),
	CODE_ACCOUNT_PASSWD_ERROR(100024, JsonResultMessage.MSG_ACCOUNT_PASSWD_ERROR),
	CODE_ACCOUNT_SHOP_ROLE_INSUFFICIENT(100025, JsonResultMessage.MSG_CODE_ACCOUNT_SHOP_ROLE_INSUFFICIENT),
	CODE_ACCOUNT_SHOP_ROLE_OCCUPY(100026, JsonResultMessage.MSG_CODE_ACCOUNT_SHOP_ROLE_OCCUPY),
	CODE_ACCOUNT_ASSIGNED_ROLE(100027, JsonResultMessage.MSG_CODE_ACCOUNT_ASSIGNED_ROLE),
	CODE_ACCOUNT_SELECT_SHOP(100028, JsonResultMessage.MSG_CODE_ACCOUNT_SELECT_SHOP),
	CODE_ACCOUNT_NEED_PRIVILEGEPASS(100029, JsonResultMessage.MSG_CODE_NEED_PRIVILEGEPASS),
	CODE_ACCOUNT_SYSID_IS_NULL(100030, JsonResultMessage.MSG_CODE_ACCOUNT_SYSID_IS_NULL),
	CODE_ACCOUNT_VERSIN_NO_POWER(10031, JsonResultMessage.MSG_CODE_ACCOUNT_VERSIN_NO_POWER),
	CODE_ACCOUNT_SHOPTYPE_REGISTERED(10032, JsonResultMessage.MSG_ACCOUNT_SHOPTYPE_REGISTERED),
	
	

	// 图片
	CODE_IMGAE_UPLOAD_FAILED(110001, JsonResultMessage.MSG_IMGAE_UPLOAD_FAILED),
	CODE_IMGAE_FORMAT_INVALID(110002, JsonResultMessage.MSG_IMGAE_FORMAT_INVALID),
	CODE_IMGAE_CROP_FAILED(110003, JsonResultMessage.MSG_IMGAE_CROP_FAILED),
	CODE_IMGAE_UPLOAD_GT_5M(110004, JsonResultMessage.MSG_IMGAE_UPLOAD_GT_5M),
	CODE_IMGAE_UPLOAD_EQ_WIDTH(110005, JsonResultMessage.MSG_IMGAE_UPLOAD_EQ_WIDTH),
	CODE_IMGAE_UPLOAD_EQ_HEIGHT(110006, JsonResultMessage.MSG_IMGAE_UPLOAD_EQ_HEIGHT),

	MSG_IMAGE_CATEGORY_IMGCATID_NOT_NULL(110007,JsonResultMessage.MSG_IMAGE_CATEGORY_IMGCATID_NOT_NULL),
	MSG_IMAGE_CATEGORY_IMGCATPARENTID_NOT_NULL(110008,JsonResultMessage.MSG_IMAGE_CATEGORY_IMGCATPARENTID_NOT_NULL),
	MSG_IMAGE_CATEGORY_IMGCATNAME_NOT_NULL(110009,JsonResultMessage.MSG_IMAGE_CATEGORY_IMGCATNAME_NOT_NULL),
	// 订单
	CODE_ORDER(120001, JsonResultMessage.MSG_ORDER),
	CODE_ORDER_OPERATE_NO_INSTANCEOF(120002, JsonResultMessage.MSG_ORDER_OPERATE_NO_INSTANCEOF),
    // 商品
    GOODS_ID_IS_NULL(130001,JsonResultMessage.GOODS_ID_IS_NULL),
        //商品品牌
    GOODS_BRAND_NAME_EXIST(131001, JsonResultMessage.GOODS_BRAND_NAME_EXIST),
    GOODS_BRAND_NAME_IS_NULL(131002, JsonResultMessage.GOODS_BRAND_NAME_IS_NULL),
    GOODS_BRAND_ID_IS_NULL(131003, JsonResultMessage.GOODS_BRAND_ID_IS_NULL),
    GOODS_BRAND_CALSSIFY_NAME_EXIST(131004, JsonResultMessage.GOODS_BRAND_CALSSIFY_NAME_EXIST),
    GOODS_BRAND_CALSSIFY_NAME_IS_NULL(131005, JsonResultMessage.GOODS_BRAND_ALSSIFY_NAME_IS_NULL),
    GOODS_BRAND_CALSSIFY_ID_IS_NULL(131006, JsonResultMessage.GOODS_BRAND_ALSSIFY_ID_IS_NULL),
        //商品分类
    GOODS_SORT_NAME_EXIST(132001,JsonResultMessage.GOODS_SORT_NAME_EXIST),
    GOODS_SORT_NAME_IS_NULL(132002,JsonResultMessage.GOODS_SORT_NAME_IS_NULL),
    GOODS_SORT_ID_IS_NULL(132003,JsonResultMessage.GOODS_SORT_ID_IS_NULL),

    GOODS_LABEL_NAME_EXIST(133001,JsonResultMessage.GOODS_LABEL_NAME_EXIST),
    GOODS_LABEL_NOT_EXIST(133002,JsonResultMessage.GOODS_LABEL_NOT_EXIST),
    GOODS_LABEL_ID_NOT_NULL(133003,JsonResultMessage.GOODS_LABEL_ID_NOT_NULL),
    GOODS_LABEL_NAME_NOT_NULL(133004,JsonResultMessage.GOODS_LABEL_NAME_NOT_NULL),
    GOODS_NAME_EXIST(134001,JsonResultMessage.GOODS_NAME_EXIST),
    GOODS_SN_EXIST(134002,JsonResultMessage.GOODS_SN_EXIST),
    GOODS_SPEC_PRD_SN_EXIST(135001,JsonResultMessage.GOODS_SPEC_PRD_SN_EXIST),
    GOODS_SPEC_NAME_REPETITION(135002,JsonResultMessage.GOODS_SPEC_NAME_REPETITION),
    GOODS_SPEC_VAL_REPETITION(135003,JsonResultMessage.GOODS_SPEC_VAL_REPETITION),
    GOODS_RECOMMEND_NAME_NOT_NULL(136001,JsonResultMessage.GOODS_RECOMMEND_NAME_NOT_NULL),
    GOODS_RECOMMEND_TYPE_NOT_NULL(136002,JsonResultMessage.GOODS_RECOMMEND_TYPE_NOT_NULL),
    GOODS_RECOMMEND_NAME_EXIST(136003,JsonResultMessage.GOODS_RECOMMEND_NAME_EXIST),
    GOODS_RECOMMEND_ID_NOT_EXIST(136004,JsonResultMessage.GOODS_RECOMMEND_ID_NOT_EXIST),
    GOODS_RECOMMEND_NOT_EXIST(136005,JsonResultMessage.GOODS_RECOMMEND_NOT_EXIST),
    
    //营销
    DISTRIBUTOR_GROUP_NAME_EXIST(141001,JsonResultMessage.DISTRIBUTOR_GROUP_NAME_EXIST),
    
	//会员用户
    CODE_MEMBER_TAG_ADD_SUCCESS(0,JsonResultMessage.MSG_MEMBER_TAG_ADD_SUCCESS),
	CODE_MEMBER_TAG_NOT_LIMIT(15001,JsonResultMessage.MSG_MEMBER_TAG_LENGTH_LIMIT),
	CODE_MEMBER_TAG_NOT_NULL(15002,JsonResultMessage.MSG_MEMBER_TAG_NOT_NULL),
	CODE_MEMBER_TAG_NAME_EXIST(15003,JsonResultMessage.MSG_MEMBER_TAG_NAME_EXIST),
	CODE_MEMBER_TAG_ID_NOT_NULL(15004,JsonResultMessage.MSG_MEMBER_TAG_ID_NOT_NULL),
	CODE_MEMEBER_NOT_EXIST(15005,JsonResultMessage.MSG_MEMBER_NOT_EXIST),
	// 会员积分
	CODE_MEMBER_SCORE_NOT_SAME(15006,JsonResultMessage.MSG_SCORE_NOT_SAME),
	CODE_MEMBER_SCORE_ERROR(15007,JsonResultMessage.MSG_MEMBER_SCORE_ERROR),
	CODE_MEMBER_SCORE_NOT_ENOUGH(15008,JsonResultMessage.MSG_MEMBER_SCORE_NOT_ENOUGH),
	CODE_MEMBER_SCORE_NOT_NULL(15009,JsonResultMessage.MSG_MEMBER_SCORE_NOT_NULL),
	MSG_MEMBER_SCORE_NOT_BE_NEGATIVE(15010,JsonResultMessage.MSG_MEMBER_SCORE_NOT_BE_NEGATIVE),
	// 会员余额
	CODE_MEMBER_ACCOUNT_UPDATE_FAIL(15101,JsonResultMessage.MSG_MEMBER_ACCOUNT_UPDATE_FAIL),

	//会员卡
	CODE_MEMBER_CARD_RIGHTS_EMPTY(15201,JsonResultMessage.MSG_MEMBER_CARD_RIGHTS_EMPTY),
		
	//文章_分类
	CODE_ARTICLE_CATEGORY_IS_EXIST(16001, JsonResultMessage.ARTICLE_CATEGORY_IS_EXIST),
	CODE_ARTICLE_CATEGORY_CATEGORYNAME_ISNULL(16001, JsonResultMessage.ARTICLE_CATEGORY_CATEGORYNAME_ISNULL),
	CODE_ARTICLE_CATEGORY_CATEGORYID_ISNULL(16003, JsonResultMessage.ARTICLE_CATEGORY_CATEGORYID_ISNULL),
	CODE_ARTICLE_TITLE_ISNULL(16004, JsonResultMessage.ARTICLE_TITLE_ISNULL),
	CODE_ARTICLE_ARTICLEID_ISNULL(16005, JsonResultMessage.ARTICLE_ARTICLEID_ISNULL),
	CODE_ARTICLE_CATEGORY_UPDATE_FAILED(16006,JsonResultMessage.ARTICLE_CATEGORY_UPDATE_FAILED),

	//小程序管理
	CODE_PAGE_CLASSIFICATION_EXIST(170001,JsonResultMessage.PAGE_CLASSIFICAIION_EXIST),
	CODE_PAGE_CLASSIFICATION_INSERT_FAILED(170002,JsonResultMessage.PAGE_CLASSIFICAIION_INSERT_FAILED),
	CODE_PAGE_CLASSIFICATION_NOT_EXIST(170003,JsonResultMessage.PAGE_CLASSIFICAIION_NOT_EXIST),
	CODE_PAGE_CLASSIFICATION_UPDATE_FAILED(170004,JsonResultMessage.PAGE_CLASSIFICATION_UPDATE_FAILED),
	CODE_PAGE_CLASSIFICATION_DELETE_FAILED(170005,JsonResultMessage.PAGE_CLASSIFICATION_DELETE_FAILED),

	DECORATE_BOTTOM_ISNOTJSON(170006,JsonResultMessage.DECORATE_BOTTOM_ISNOTJSON),
	DECORATE_STYLE_ISNOTJSON(170007,JsonResultMessage.DECORATE_STYLE_ISNOTJSON),
	DECORATE_STYLE_PARAM_ID_NULL(170008,JsonResultMessage.DECORATE_STYLE_PARAM_UPDATE_ID_NULL),
	DECORATE_STYLE_PARAM_VALUE_NULL(170009,JsonResultMessage.DECORATE_STYLE_PARAM_UPDATE_VALUE_NULL),
	DECORATE_URL_ILLEGAL(170010,JsonResultMessage.DECORATE_URL_ILLEGAL),
	
	WX_BINDING_MINI_NO_SAME(170011,JsonResultMessage.BINDING_MINI_NO_SAME),
	WX_BINDING_MINI_HAVEBIND(170012,JsonResultMessage.BINDING_MINI_HAVEBIND),

	WX_MA_APP_ID_NOT_AUTH(170013,JsonResultMessage.WX_MA_APP_ID_NOT_AUTH),

	//门店管理
	CODE_STORE_GROUP_NAME_EXIST(180001,JsonResultMessage.STORE_GROUP_NAME_EXIST),
	CODE_POS_SHOP_ID_EXIST(180002,JsonResultMessage.STORE_POS_SHOP_ID_EXIST),
	
	/**
	 * 售后管理
	 */
	STORE_STORE_ID_NULL(181001,JsonResultMessage.STORE_STORE_ID_NULL),
	STORE_TECHNICIAN_NAME_NULL(181002,JsonResultMessage.STORE_TECHNICIAN_NAME_NULL),
	STORE_TECHNICIAN_TELEPHONE_ILLEGAL(181003,JsonResultMessage.STORE_TECHNICIAN_TELEPHONE_ILLEGAL),
	STORE_TECHNICIAN_TELEPHONE_NULL(181004,JsonResultMessage.STORE_TECHNICIAN_TELEPHONE_NULL),
	
	//门店预约
	CODE_SERVICE_ORDER_VERIFY_CODE_ERROR(182001,JsonResultMessage.SERVICE_ORDER_VERIFY_CODE_ERROR),
	CODE_SERVICE_ORDER_VERIFY_BALANCE_IS_NULL(182002,JsonResultMessage.SERVICE_ORDER_VERIFY_BALANCE_IS_NULL),
	CODE_SERVICE_ORDER_VERIFY_REASON_IS_NULL(182003,JsonResultMessage.SERVICE_ORDER_VERIFY_REASON_IS_NULL),
	CODE_SERVICE_ORDER_VERIFY_INSUFFICIENT_BALANCEL(182004,JsonResultMessage.SERVICE_ORDER_VERIFY_INSUFFICIENT_BALANCE),
	CODE_SERVICE_ORDER_CANCEL_REASON_IS_NULL(182005,JsonResultMessage.SERVICE_ORDER_CANCEL_REASON_IS_NULL),

	//基础配置
	CODE_CONFIG_PLEDGE_EXCEED(190001,JsonResultMessage.CONFIG_PLEDGE_EXCEED),
    CODE_CONFIG_PLEDGE_NAME_NULL(190002,JsonResultMessage.CONFIG_PLEDGE_NAME_NULL),
    CODE_CONFIG_PLEDGE_NAME_LENGTH(190003,JsonResultMessage.CONFIG_PLEDGE_NAME_LENGTH),
    CODE_CONFIG_PLEDGE_CONTENT_NULL(190004,JsonResultMessage.CONFIG_PLEDGE_CONTENT_NULL),
    CODE_CONFIG_PLEDGE_CONTENT_LENGTH(190005,JsonResultMessage.CONFIG_PLEDGE_CONTENT_LENGTH),
    CODE_CONFIG_PLEDGE_LOGO_NULL(190006,JsonResultMessage.CONFIG_PLEDGE_LOGO_NULL),
	AUTH_SHOP_NOT_EXIST(190007,JsonResultMessage.AUTH_SHOP_NOT_EXIST),
	ORDER_PROCESS_CONFIG_UDPATE_FAILED(190008,JsonResultMessage.ORDER_PROCESS_CONFIG_UDPATE_FAILED),
	WECAHT_PAY_CONFIG_UPDATE_DAILED(190009,JsonResultMessage.WECAHT_PAY_CONFIG_UPDATE_DAILED),
	RETURN_CONFIG_UPDATE_FAILED(190010,JsonResultMessage.RETURN_CONFIG_UPDATE_FAILED),
	PAYMENT_CONFIG_IS_NULL(190011,JsonResultMessage.PAYMENT_CONFIG_IS_NULL),
	ORDER_PROCESS_CONFIG_IS_NULL(190012,JsonResultMessage.ORDER_PROCESS_CONFIG_IS_NULL),
    RETURN_CONFIG_IS_NULL(190013,JsonResultMessage.RETURN_CONFIG_IS_NULL),
	WXPAY_CONFIG_IS_NULL(190014,JsonResultMessage.WXPAY_CONFIG_IS_NULL),
	CODE_CONFIG_A_NUM_GREATER(190015,JsonResultMessage.CONFIG_A_NUM_GREATER),
	CODE_CONFIG_B_NUM_GREATER(190016,JsonResultMessage.CONFIG_B_NUM_GREATER),

	//概览
	OVERVIEW_MALL_DATADEMONSTRATION_GET_FAILED(200001,JsonResultMessage.OVERVIEW_MALL_DATADEMONSTRATION_GET_FAILED),
	OVERVIEW_MALL_BING_UNBING_FAILED(200002,JsonResultMessage.OVERVIEW_MALL_BING_UNBING_FAILED),
	OVERVIEW_MALL_TODOITEM_GET_FAILED(200003,JsonResultMessage.OVERVIEW_MALL_TODOITEM_GET_FAILED)
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
