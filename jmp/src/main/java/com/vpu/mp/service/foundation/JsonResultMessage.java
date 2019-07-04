package com.vpu.mp.service.foundation;

/**
 * 
 * @author 新国
 *
 */
public class JsonResultMessage {
	final public static String MSG_SUCCESS = "common.success";
	final public static String MSG_FAIL = "common.fail";
	final public static String MSG_PARAM_ERROR = "common.param.error";

	// 账号
		
	final public static String MSG_ACCOUNT_OR_PASSWORD_INCRRECT = "account.accountOrPassword.incrrect";
	final public static String MSG_ACCOUNT_MODILE_APPLIED = "account.mobile.applied";
	final public static String MSG_ACCOUNT_MODILE_REGISTERED = "account.mobile.registered";
	final public static String MSG_ACCOUNT_MODILE_NOT_NULL = "account.mobile.notNull";
	final public static String MSG_ACCOUNT_LOGIN_EXPIRED = "account.login.expired";
	final public static String MSG_ACCOUNT_ROLE__AUTH_INSUFFICIENT = "account.role.auth.insufficient";
	final public static String MSG_ACCOUNT_ROLE__SHOP_SELECT = "account.role.shop.select";
	final public static String MSG_ACCOUNT_NAME_NOT_NULL = "account.name.notNull";
	final public static String MSG_ACCOUNT_ISSUBLOGIN_NOT_NULL = "account.isSubLogin.notNull";
	final public static String MSG_ACCOUNT_SAME = "account.name.same";
	
	// 图片
	
	final public static String MSG_IMGAE_UPLOAD_FAILED = "image.upload.failed";
	final public static String MSG_IMGAE_FILE_INVALID = "image.file.invalid";
	final public static String MSG_IMGAE_CROP_FAILED = "image.crop.failed";

	// 商品
	
    final public static String GOODS_BRAND_NAME_EXIST="goods.brand.name.exist";
    final public static String GOODS_SORT_NAME_EXIST="goods.sort.name.exist";
    
    final public static String	ARTICLE_CATEGORY_IS_EXIST =  "api.code.article_category_is_exist";
	final public static String	ARTICLE_CATEGORY_CATEGORYNAME_ISNULL = "api.code.article_category_categoryName_isNull";
	final public static String	ARTICLE_CATEGORY_CATEGORYID_ISNULL = "api.code.article_category_categoryId_isNull";
	final public static String	ARTICLE_TITLE_ISNULL = "api.code.article_title_isNull";
	final public static String	ARTICLE_ARTICLEID_ISNULL = "api.code.article_articleId_isNull";
	final public static String	ARTICLE_CATEGORY_UPDATE_FAILED = "api.code_article_category_update_failed";
}
