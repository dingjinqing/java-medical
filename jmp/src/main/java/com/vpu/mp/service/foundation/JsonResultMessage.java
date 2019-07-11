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
	final public static String MSG_MOBILE_SAME="account.mobile.same";
	final public static String MSG_ACCOUNT_SHOP_NULL="account.shop.null";
	final public static String MSG_ACCOUNT_SHOP_EXPIRE="account.shop.expire";
	final public static String MSG_ACCOUNT_SHOPAVATAR_NOT_NULL="account.shopAvatar.notNull";
	final public static String MSG_ACCOUNT_ACCOUNTNAME_NOT_NULL="account.accountName.notNull";
	final public static String MSG_ACCOUNT_PASSWD_NO_SAME="account.passwd.noSame";
	final public static String MSG_ACCOUNT_OLD_PASSWD_ERROR="account.account.oldPasswd.error";
	final public static String MSG_ACCOUNT_OLD_NEW_PASSWD_NO_SAME="account.oldAndNew.passwd.noSame";
	final public static String MSG_ACCOUNT_PASSWD_NOT_NULL="account.passwd.notNull";
	final public static String MSG_ACCOUNT_NEWPASSWD_NOT_NULL="account.newpasswd.notNull";
	final public static String MSG_ACCOUNT_CONFNEWPASSWD_NOT_NULL="account.confnewpasswd.notNull";
	final public static String MSG_ACCOUNT_PASSWD_LENGTH_LIMIT="account.passwd.length.limit";
	final public static String MSG_ACCOUNT_USERNAME_NOT_NULL="account.username.not.null";
	final public static String MSG_ACCOUNT_SHOPID_NOT_NULL="account.shopId.not.null";

	// 图片
	
	final public static String MSG_IMGAE_UPLOAD_FAILED = "image.upload.failed";
	final public static String MSG_IMGAE_FORMAT_INVALID = "image.format.invalid";
	final public static String MSG_IMGAE_CROP_FAILED = "image.crop.failed";
	final public static String MSG_IMGAE_UPLOAD_GT_5M = "image.upload.gt5m";
	final public static String MSG_IMGAE_UPLOAD_EQ_WIDTH = "image.upload.eqWidth";
	final public static String MSG_IMGAE_UPLOAD_EQ_HEIGHT = "image.upload.eqHeight";

	// 商品
	
    final public static String GOODS_BRAND_NAME_EXIST="goods.brand.name.exist";
    final public static String GOODS_SORT_NAME_EXIST="goods.sort.name.exist";
    final public static String GOODS_LABEL_NAME_EXIST="goods.label.name.exist";
    final public static String GOODS_NAME_EXIST="goods.name.exist";
    final public static String GOODS_SN_EXIST="goods.sn.exist";
    final public static String GOODS_SPEC_PRD_SN_EXIST="goods.spec.prd.sn.exist";
    final public static String GOODS_SPEC_NAME_REPETITION="goods.spec.name.repetition";
    final public static String GOODS_SPEC_VAL_REPETITION="goods.spec.val.repetition";
    
    final public static String	ARTICLE_CATEGORY_IS_EXIST =  "api.code.article_category_is_exist";
	final public static String	ARTICLE_CATEGORY_CATEGORYNAME_ISNULL = "api.code.article_category_categoryName_isNull";
	final public static String	ARTICLE_CATEGORY_CATEGORYID_ISNULL = "api.code.article_category_categoryId_isNull";
	final public static String	ARTICLE_TITLE_ISNULL = "api.code.article_title_isNull";
	final public static String	ARTICLE_ARTICLEID_ISNULL = "api.code.article_articleId_isNull";
	final public static String	ARTICLE_CATEGORY_UPDATE_FAILED = "api.code_article_category_update_failed";

	//小程序管理

	final public static String PAGE_CLASSIFICAIION_EXIST="applets.page.classification.exist";
	final public static String PAGE_CLASSIFICAIION_INSERT_FAILED="applets.page.classification.insert.failed";
	final public static String PAGE_CLASSIFICAIION_NOT_EXIST="applets.page.classification.not.exist";
	final public static String PAGE_CLASSIFICATION_UPDATE_FAILED="applets.page.classification.update.failed";
	final public static String PAGE_CLASSIFICATION_DELETE_FAILED="applets.page.classification.delete.failed";

	final public static String DECORATE_BOTTOM_ISNOTJSON="applets.bottom.is_not_json";
	final public static String DECORATE_STYLE_ISNOTJSON="applets.style.is_not_json";
	final public static String DECORATE_STYLE_PARAM_UPDATE_ID_NULL="applets.style.param_update_id_null";
	final public static String DECORATE_STYLE_PARAM_UPDATE_VALUE_NULL="applets.style.param_update_value_null";


	/**
	 * 	门店管理
	 */
	final public static String STORE_GROUP_NAME_EXIST="store.group.name.exist";
	final public static String STORE_POS_SHOP_ID_EXIST="store.pos.shop.id.exist";

	/**
	 * 基础配置
	 */
	final public static String CONFIG_PLEDGE_EXCEED = "config.pledge.numbers.exceed";
    final public static String CONFIG_PLEDGE_NAME_NULL = "config.pledge.name.null";
    final public static String CONFIG_PLEDGE_NAME_LENGTH = "config.pledge.name.length";
    final public static String CONFIG_PLEDGE_CONTENT_NULL = "config.pledge.content.null";
    final public static String CONFIG_PLEDGE_CONTENT_LENGTH = "config.pledge.content.length";
    final public static String CONFIG_PLEDGE_LOGO_NULL = "config.pledge.logo.null";

	final public static String AUTH_SHOP_NOT_EXIST = "auth.shop.appId.not.exist";
	final public static String ORDER_PROCESS_CONFIG_UDPATE_FAILED = "order.process.config.update.failed";
	final public static String WECAHT_PAY_CONFIG_UPDATE_DAILED = "wechat.pay.config.update.failed";

	/**
	 *  会员管理
	 */
	final public static String MSG_MEMBER_TAG_LENGTH_LIMIT="member.tag.length.limit";
}
