package com.vpu.mp.service.foundation.data;

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
	public static final String MSG_ACCOUNT_PASSWD_ERROR = "account.passwd.error";
	public static final String MSG_CODE_ACCOUNT_SHOP_ROLE_INSUFFICIENT = "account.shop.role.insufficient";
	public static final String MSG_CODE_ACCOUNT_SHOP_ROLE_OCCUPY = "account.shop.role.occupy";
	public static final String MSG_CODE_ACCOUNT_ASSIGNED_ROLE="account.assigned.role";
	public static final String MSG_CODE_ACCOUNT_SELECT_SHOP="account.select.shop";
	public static final String MSG_CODE_NEED_PRIVILEGEPASS ="account.need.privilegePass";
	public static final String MSG_CODE_ACCOUNT_SYSID_IS_NULL = "account.sysId.is.null";
	public static final String MSG_CODE_ACCOUNT_VERSIN_NO_POWER = "account.version.no.power";
	
	// 图片
	
	final public static String MSG_IMGAE_UPLOAD_FAILED = "image.upload.failed";
	final public static String MSG_IMGAE_FORMAT_INVALID = "image.format.invalid";
	final public static String MSG_IMGAE_CROP_FAILED = "image.crop.failed";
	final public static String MSG_IMGAE_UPLOAD_GT_5M = "image.upload.gt5m";
	final public static String MSG_IMGAE_UPLOAD_EQ_WIDTH = "image.upload.eqWidth";
	final public static String MSG_IMGAE_UPLOAD_EQ_HEIGHT = "image.upload.eqHeight";

	final public static String MSG_IMAGE_CATEGORY_IMGCATID_NOT_NULL="image.category.imgCatId.notNull";
	final public static String MSG_IMAGE_CATEGORY_IMGCATPARENTID_NOT_NULL="image.category.imgCatParentId.notNull";
	final public static String MSG_IMAGE_CATEGORY_IMGCATNAME_NOT_NULL="image.category.imgCatName.notNull";

	// 商品
    final public static String GOODS_ID_IS_NULL="goods.id.is.null";
	    //商品品牌
    final public static String GOODS_BRAND_NAME_EXIST="goods.brand.name.exist";
    final public static String GOODS_BRAND_NAME_IS_NULL="goods.brand.name.is.null";
    final public static String GOODS_BRAND_ID_IS_NULL="goods.brand.id.is.null";
    final public static String GOODS_BRAND_CALSSIFY_NAME_EXIST="goods.brand.classify.name.exist";
    final public static String GOODS_BRAND_ALSSIFY_NAME_IS_NULL="goods.brand.classify.name.is.null";
    final public static String GOODS_BRAND_ALSSIFY_ID_IS_NULL="goods.brand.classify.id.is.null";
        //商品分类
    final public static String GOODS_SORT_NAME_EXIST="goods.sort.name.exist";
    final public static String GOODS_SORT_NAME_IS_NULL="goods.sort.name.is.null";
    final public static String GOODS_SORT_ID_IS_NULL="goods.sort.id.is.null";


    final public static String GOODS_LABEL_NAME_EXIST="goods.label.name.exist";
    final public static String GOODS_LABEL_NOT_EXIST="goods.label.not.exist";
    final public static String GOODS_LABEL_ID_NOT_NULL="goods.label.id.notNull";
    final public static String GOODS_LABEL_NAME_NOT_NULL="goods.label.name.notNull";
    final public static String GOODS_NAME_EXIST="goods.name.exist";
    final public static String GOODS_SN_EXIST="goods.sn.exist";
    final public static String GOODS_SPEC_PRD_SN_EXIST="goods.spec.prd.sn.exist";
    final public static String GOODS_SPEC_NAME_REPETITION="goods.spec.name.repetition";
    final public static String GOODS_SPEC_VAL_REPETITION="goods.spec.val.repetition";
    final public static String GOODS_RECOMMEND_NAME_NOT_NULL="goods.recommend.name.notNull";
    final public static String GOODS_RECOMMEND_TYPE_NOT_NULL="goods.recommend.type.notNull";
    final public static String GOODS_RECOMMEND_NAME_EXIST="goods.recommend.name.exist";
    final public static String GOODS_RECOMMEND_ID_NOT_EXIST="goods.recommend.id.notexist";
    final public static String GOODS_RECOMMEND_NOT_EXIST="goods.recommend.id.notexist";
    
    //营销
    final public static String DISTRIBUTOR_GROUP_NAME_EXIST="distributor.group.name.exist";
    
    //文章_分类
    
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
	final public static String DECORATE_URL_ILLEGAL="applets.url.illegal";


	/**
	 * 	门店管理
	 */
	final public static String STORE_GROUP_NAME_EXIST="store.group.name.exist";
	final public static String STORE_POS_SHOP_ID_EXIST="store.pos.shop.id.exist";
	
	/**
	 * 售后管理
	 */
	final public static String STORE_STORE_ID_NULL="store.store.id.null";
	final public static String STORE_TECHNICIAN_NAME_NULL="store.technician.name.null";
	final public static String STORE_TECHNICIAN_TELEPHONE_ILLEGAL="store.tenchnician.telephone.illegal";
	final public static String STORE_TECHNICIAN_TELEPHONE_NULL="store.tenchnician.telephone.null";
	
	/**
	 * 	门店预约
	 */
	final public static String SERVICE_ORDER_VERIFY_CODE_ERROR="store.service.order.verify.code.error";
	final public static String SERVICE_ORDER_VERIFY_BALANCE_IS_NULL="store.service.order.verify.balance.is.null";
	final public static String SERVICE_ORDER_VERIFY_REASON_IS_NULL="store.service.order.verify.reason.is.null";
	final public static String SERVICE_ORDER_VERIFY_INSUFFICIENT_BALANCE="store.service.order.verify.insufficient.balance";
	final public static String SERVICE_ORDER_CANCEL_REASON_IS_NULL="store.service.order.verify.cancel_reason.is.null";

	/**
	 * 基础配置
	 */
	final public static String CONFIG_PLEDGE_EXCEED = "config.pledge.numbers.exceed";
    final public static String CONFIG_PLEDGE_NAME_NULL = "config.pledge.name.null";
    final public static String CONFIG_PLEDGE_NAME_LENGTH = "config.pledge.name.length";
    final public static String CONFIG_PLEDGE_CONTENT_NULL = "config.pledge.content.null";
    final public static String CONFIG_PLEDGE_CONTENT_LENGTH = "config.pledge.content.length";
    final public static String CONFIG_PLEDGE_LOGO_NULL = "config.pledge.logo.null";

	final public static String AUTH_SHOP_NOT_EXIST = "config.auth.shop.appId.not.exist";
	final public static String ORDER_PROCESS_CONFIG_UDPATE_FAILED = "config.order.process.config.update.failed";
	final public static String WECAHT_PAY_CONFIG_UPDATE_DAILED = "config.wechat.pay.config.update.failed";
	final public static String RETURN_CONFIG_UPDATE_FAILED = "config.return.config.update.failed";
	final public static String PAYMENT_CONFIG_IS_NULL = "config.payment.config.is.null";
	final public static String ORDER_PROCESS_CONFIG_IS_NULL = "config.order.process.config.is.null";
	final public static String RETURN_CONFIG_IS_NULL = "config.return.config.is.null";
	final public static String WXPAY_CONFIG_IS_NULL = "wxpay.config.is.null";
	final public static String CONFIG_A_NUM_GREATER = "config.a.num.greater";
	final public static String CONFIG_B_NUM_GREATER = "config.b.num.greater";


	/**
	 *  会员管理
	 */
	final public static String MSG_MEMBER_TAG_LENGTH_LIMIT="member.tag.length.limit";
	final public static String MSG_MEMBER_TAG_NOT_NULL="member.tag.notnull";
	final public static String MSG_MEMBER_TAG_NAME_EXIST="member.tag.name.exists";
	final public static String MSG_MEMBER_TAG_ADD_SUCCESS = "member.tag.add.success";
	final public static String MSG_MEMBER_TAG_ID_NOT_NULL = "member.tag.id.notnull";

	final public static String MSG_MEMBER_NOT_EXIST = "member.not.exist";
	final public static String MSG_SCORE_NOT_SAME = "member.score.not.same";
	final public static String MSG_MEMBER_SCORE_ERROR = "member.score.error";
	final public static String MSG_MEMBER_SCORE_NOT_ENOUGH = "member.score.not.enough";
	final public static String MSG_MEMBER_SCORE_NOT_NULL = "member.score.not.null";
	final public static String MSG_MEMBER_SCORE_NOT_BE_NEGATIVE="member.score.not.be.negative";

	/**
	 * 会员余额
	 */
	final public static String MSG_MEMBER_ACCOUNT_UPDATE_FAIL="member.account.update.fail";
	
	/**
	 *  概览
	 */
	final public static String OVERVIEW_MALL_DATADEMONSTRATION_GET_FAILED = "overview.mall.datademonstration.get.failed";
	final public static String OVERVIEW_MALL_BING_UNBING_FAILED = "overview.mall.bind.unbind.failed";
	final public static String OVERVIEW_MALL_TODOITEM_GET_FAILED = "overview.mall.todoitem.get.failed";
	
	/**
	 * 订单
	 */
	final public static String MSG_ORDER = "order";
	final public static String MSG_ORDER_REMARK_ORDERSN_NOT_NULL = "order_remark_ordersn_not_null";
	final public static String MSG_ORDER_REMARK_NOT_NULL = "order_remark_not_null";
	final public static String MSG_ORDER_REMARK_TYPE_NOT_NULL = "order_remark_type_not_null";

}
