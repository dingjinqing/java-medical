package com.vpu.mp.service.foundation.data;

/**
 * 
 * @author 新国
 *
 */
public class JsonResultMessage {
	public static final String MSG_SUCCESS = "common.success";
	public static final String MSG_FAIL = "common.fail";
	public static final String MSG_PARAM_ERROR = "common.param.error";

	// 账号

	public static final String MSG_ACCOUNT_OR_PASSWORD_INCRRECT = "account.accountOrPassword.incrrect";
	public static final String MSG_ACCOUNT_MODILE_APPLIED = "account.mobile.applied";
	public static final String MSG_ACCOUNT_MODILE_REGISTERED = "account.mobile.registered";
	public static final String MSG_ACCOUNT_MODILE_NOT_NULL = "account.mobile.notNull";
	public static final String MSG_ACCOUNT_LOGIN_EXPIRED = "account.login.expired";
	public static final String MSG_ACCOUNT_ROLE__AUTH_INSUFFICIENT = "account.role.auth.insufficient";
	public static final String MSG_ACCOUNT_ROLE__SHOP_SELECT = "account.role.shop.select";
	public static final String MSG_ACCOUNT_NAME_NOT_NULL = "account.name.notNull";
	public static final String MSG_ACCOUNT_ISSUBLOGIN_NOT_NULL = "account.isSubLogin.notNull";
	public static final String MSG_ACCOUNT_SAME = "account.name.same";
	public static final String MSG_MOBILE_SAME = "account.mobile.same";
	public static final String MSG_ACCOUNT_SHOP_NULL = "account.shop.null";
	public static final String MSG_ACCOUNT_SHOP_EXPIRE = "account.shop.expire";
	public static final String MSG_ACCOUNT_SHOPAVATAR_NOT_NULL = "account.shopAvatar.notNull";
	public static final String MSG_ACCOUNT_ACCOUNTNAME_NOT_NULL = "account.accountName.notNull";
	public static final String MSG_ACCOUNT_PASSWD_NO_SAME = "account.passwd.noSame";
	public static final String MSG_ACCOUNT_OLD_PASSWD_ERROR = "account.account.oldPasswd.error";
	public static final String MSG_ACCOUNT_OLD_NEW_PASSWD_NO_SAME = "account.oldAndNew.passwd.noSame";
	public static final String MSG_ACCOUNT_PASSWD_NOT_NULL = "account.passwd.notNull";
	public static final String MSG_ACCOUNT_NEWPASSWD_NOT_NULL = "account.newpasswd.notNull";
	public static final String MSG_ACCOUNT_CONFNEWPASSWD_NOT_NULL = "account.confnewpasswd.notNull";
	public static final String MSG_ACCOUNT_PASSWD_LENGTH_LIMIT = "account.passwd.length.limit";
	public static final String MSG_ACCOUNT_USERNAME_NOT_NULL = "account.username.not.null";
	public static final String MSG_ACCOUNT_SHOPID_NOT_NULL = "account.shopId.not.null";
	public static final String MSG_ACCOUNT_PASSWD_ERROR = "account.passwd.error";
	public static final String MSG_CODE_ACCOUNT_SHOP_ROLE_INSUFFICIENT = "account.shop.role.insufficient";
	public static final String MSG_CODE_ACCOUNT_SHOP_ROLE_OCCUPY = "account.shop.role.occupy";
	public static final String MSG_CODE_ACCOUNT_ASSIGNED_ROLE = "account.assigned.role";
	public static final String MSG_CODE_ACCOUNT_SELECT_SHOP = "account.select.shop";
	public static final String MSG_CODE_NEED_PRIVILEGEPASS = "account.need.privilegePass";
	public static final String MSG_CODE_ACCOUNT_SYSID_IS_NULL = "account.sysId.is.null";
	public static final String MSG_CODE_ACCOUNT_VERSIN_NO_POWER = "account.version.no.power";
	public static final String MSG_ACCOUNT_SHOPTYPE_REGISTERED = "account.shoptype.notNull";
	
	// 图片

	public static final String MSG_IMGAE_UPLOAD_FAILED = "image.upload.failed";
	public static final String MSG_IMGAE_FORMAT_INVALID = "image.format.invalid";
	public static final String MSG_IMGAE_CROP_FAILED = "image.crop.failed";
	public static final String MSG_IMGAE_UPLOAD_GT_5M = "image.upload.gt5m";
	public static final String MSG_IMGAE_UPLOAD_EQ_WIDTH = "image.upload.eqWidth";
	public static final String MSG_IMGAE_UPLOAD_EQ_HEIGHT = "image.upload.eqHeight";

	public static final String MSG_IMAGE_CATEGORY_IMGCATID_NOT_NULL = "image.category.imgCatId.notNull";
	public static final String MSG_IMAGE_CATEGORY_IMGCATPARENTID_NOT_NULL = "image.category.imgCatParentId.notNull";
	public static final String MSG_IMAGE_CATEGORY_IMGCATNAME_NOT_NULL = "image.category.imgCatName.notNull";

	/**
	 * 商品
	 */
	public static final String GOODS_ID_IS_NULL = "goods.id.is.null";
	
	/**
	 * 商品品牌
	 */
	public static final String GOODS_BRAND_NAME_EXIST = "goods.brand.name.exist";
	public static final String GOODS_BRAND_NAME_IS_NULL = "goods.brand.name.is.null";
	public static final String GOODS_BRAND_ID_IS_NULL = "goods.brand.id.is.null";
	public static final String GOODS_BRAND_CALSSIFY_NAME_EXIST = "goods.brand.classify.name.exist";
	public static final String GOODS_BRAND_ALSSIFY_NAME_IS_NULL = "goods.brand.classify.name.is.null";
	public static final String GOODS_BRAND_ALSSIFY_ID_IS_NULL = "goods.brand.classify.id.is.null";
	
	/**
	 * 商品分类
	 */
	public static final String GOODS_SORT_NAME_EXIST = "goods.sort.name.exist";
	public static final String GOODS_SORT_NAME_IS_NULL = "goods.sort.name.is.null";
	public static final String GOODS_SORT_ID_IS_NULL = "goods.sort.id.is.null";

	public static final String GOODS_LABEL_NAME_EXIST = "goods.label.name.exist";
	public static final String GOODS_LABEL_NOT_EXIST = "goods.label.not.exist";
	public static final String GOODS_LABEL_ID_NOT_NULL = "goods.label.id.notNull";
	public static final String GOODS_LABEL_NAME_NOT_NULL = "goods.label.name.notNull";
	public static final String GOODS_NAME_EXIST = "goods.name.exist";
	public static final String GOODS_SN_EXIST = "goods.sn.exist";
	public static final String GOODS_SPEC_PRD_SN_EXIST = "goods.spec.prd.sn.exist";
	public static final String GOODS_SPEC_NAME_REPETITION = "goods.spec.name.repetition";
	public static final String GOODS_SPEC_VAL_REPETITION = "goods.spec.val.repetition";
	public static final String GOODS_RECOMMEND_NAME_NOT_NULL = "goods.recommend.name.notNull";
	public static final String GOODS_RECOMMEND_TYPE_NOT_NULL = "goods.recommend.type.notNull";
	public static final String GOODS_RECOMMEND_NAME_EXIST = "goods.recommend.name.exist";
	public static final String GOODS_RECOMMEND_ID_NOT_EXIST = "goods.recommend.id.notexist";
	public static final String GOODS_RECOMMEND_NOT_EXIST = "goods.recommend.id.notexist";

	/**
	 * 营销
	 */
	public static final String DISTRIBUTOR_GROUP_NAME_EXIST = "distributor.group.name.exist";

    /**
     * 营销-砍价excel导出-发起砍价
     */
    public static final String STATUS_SUCCESS = "status.success";
    public static final String STATUS_FAIL = "status.fail";
    public static final String STATUS_IN_PROGRESS = "status.in_progress";
    public static final String BARGAIN_RECORD_LIST_FILENAME = "bargain.record.list.filename";
    public static final String BARGAIN_RECORD_LIST_ID = "bargain.record.list.id";
    public static final String BARGAIN_RECORD_LIST_GOODS_NAME = "bargain.record.list.goods_name";
    public static final String BARGAIN_RECORD_LIST_USERNAME= "bargain.record.list.username";
    public static final String BARGAIN_RECORD_LIST_MOBILE = "bargain.record.list.mobile";
    public static final String BARGAIN_RECORD_LIST_CREATE_TIME = "bargain.record.list.create_time";
    public static final String BARGAIN_RECORD_LIST_BARGAIN_MONEY = "bargain.record.list.bargain_money";
    public static final String BARGAIN_RECORD_LIST_SURPLUS_MONEY = "bargain.record.list.surplus_money";
    public static final String BARGAIN_RECORD_LIST_USER_NUMBER = "bargain.record.list.user_number";
    public static final String BARGAIN_RECORD_LIST_STATUS = "bargain.record.list.status";

    /**
     * 营销-砍价excel导出-帮忙砍价
     */
    public static final String BARGAIN_USER_LIST_FILENAME = "bargain.user.list.filename";
    public static final String BARGAIN_USER_LIST_ID = "bargain.user.list.id";
    public static final String BARGAIN_USER_LIST_USERNAME = "bargain.user.list.username";
    public static final String BARGAIN_USER_LIST_MOBILE = "bargain.user.list.mobile";
    public static final String BARGAIN_USER_LIST_CREATE_TIME = "bargain.user.list.create_time";
    public static final String BARGAIN_USER_LIST_BARGAIN_MONEY = "bargain.user.list.bargain_money";

	/**
	 * 文章_分类
	 */
	public static final String ARTICLE_CATEGORY_IS_EXIST = "api.code.article_category_is_exist";
	public static final String ARTICLE_CATEGORY_CATEGORYNAME_ISNULL = "api.code.article_category_categoryName_isNull";
	public static final String ARTICLE_CATEGORY_CATEGORYID_ISNULL = "api.code.article_category_categoryId_isNull";
	public static final String ARTICLE_TITLE_ISNULL = "api.code.article_title_isNull";
	public static final String ARTICLE_ARTICLEID_ISNULL = "api.code.article_articleId_isNull";
	public static final String ARTICLE_CATEGORY_UPDATE_FAILED = "api.code_article_category_update_failed";

	/**
	 * 小程序管理
	 */
	public static final String PAGE_CLASSIFICAIION_EXIST = "applets.page.classification.exist";
	public static final String PAGE_CLASSIFICAIION_INSERT_FAILED = "applets.page.classification.insert.failed";
	public static final String PAGE_CLASSIFICAIION_NOT_EXIST = "applets.page.classification.not.exist";
	public static final String PAGE_CLASSIFICATION_UPDATE_FAILED = "applets.page.classification.update.failed";
	public static final String PAGE_CLASSIFICATION_DELETE_FAILED = "applets.page.classification.delete.failed";

	public static final String DECORATE_BOTTOM_ISNOTJSON = "applets.bottom.is_not_json";
	public static final String DECORATE_STYLE_ISNOTJSON = "applets.style.is_not_json";
	public static final String DECORATE_STYLE_PARAM_UPDATE_ID_NULL = "applets.style.param_update_id_null";
	public static final String DECORATE_STYLE_PARAM_UPDATE_VALUE_NULL = "applets.style.param_update_value_null";
	public static final String DECORATE_URL_ILLEGAL = "applets.url.illegal";

	public static final String BINDING_MINI_NO_SAME = "binding.mini.no.same";
	public static final String BINDING_MINI_HAVEBIND = "binding.mini.havebind";
	
	public static final String WX_MA_APP_ID_NOT_AUTH = "wx.ma.app_id.not.auth";
	public static final String WX_MA_TEMPLATE_ID_NOT_NULL = "wx.ma.app_template_id.not.null";
	public static final String WX_MA_PACKAGE_VERSION_NOT_NULL = "wx.ma.package_version.not.null";
	public static final String WX_MA_NEED_AUTHORIZATION = "wx.ma.neew.authorization";
	public static final String WX_ERROR_EXCEPTION = "wx.error.exception";
	public static final String WX_MA_NEED_UPLOADCODE = "wx.ma.need.uploadcode";
	public static final String WX_MA_NEED_AUDITING_CODE_SUCCESS = "wx.ma.need.auditing.success";
	
	/**
	 * 门店管理
	 */
	public static final String STORE_GROUP_NAME_EXIST = "store.group.name.exist";
	public static final String STORE_POS_SHOP_ID_EXIST = "store.pos.shop.id.exist";

	/**
	 * 售后管理
	 */
	public static final String STORE_STORE_ID_NULL = "store.store.id.null";
	public static final String STORE_TECHNICIAN_NAME_NULL = "store.technician.name.null";
	public static final String STORE_TECHNICIAN_TELEPHONE_ILLEGAL = "store.tenchnician.telephone.illegal";
	public static final String STORE_TECHNICIAN_TELEPHONE_NULL = "store.tenchnician.telephone.null";

	/**
	 * 门店预约
	 */
	public static final String SERVICE_ORDER_VERIFY_CODE_ERROR = "store.service.order.verify.code.error";
	public static final String SERVICE_ORDER_VERIFY_BALANCE_IS_NULL = "store.service.order.verify.balance.is.null";
	public static final String SERVICE_ORDER_VERIFY_REASON_IS_NULL = "store.service.order.verify.reason.is.null";
	public static final String SERVICE_ORDER_VERIFY_INSUFFICIENT_BALANCE = "store.service.order.verify.insufficient.balance";
	public static final String SERVICE_ORDER_CANCEL_REASON_IS_NULL = "store.service.order.verify.cancel_reason.is.null";

	/**
	 * 基础配置
	 */
	public static final String CONFIG_PLEDGE_EXCEED = "config.pledge.numbers.exceed";
	public static final String CONFIG_PLEDGE_NAME_NULL = "config.pledge.name.null";
	public static final String CONFIG_PLEDGE_NAME_LENGTH = "config.pledge.name.length";
	public static final String CONFIG_PLEDGE_CONTENT_NULL = "config.pledge.content.null";
	public static final String CONFIG_PLEDGE_CONTENT_LENGTH = "config.pledge.content.length";
	public static final String CONFIG_PLEDGE_LOGO_NULL = "config.pledge.logo.null";

	public static final String AUTH_SHOP_NOT_EXIST = "config.auth.shop.appId.not.exist";
	public static final String ORDER_PROCESS_CONFIG_UDPATE_FAILED = "config.order.process.config.update.failed";
	public static final String WECAHT_PAY_CONFIG_UPDATE_DAILED = "config.wechat.pay.config.update.failed";
	public static final String RETURN_CONFIG_UPDATE_FAILED = "config.return.config.update.failed";
	public static final String PAYMENT_CONFIG_IS_NULL = "config.payment.config.is.null";
	public static final String ORDER_PROCESS_CONFIG_IS_NULL = "config.order.process.config.is.null";
	public static final String RETURN_CONFIG_IS_NULL = "config.return.config.is.null";
	public static final String WXPAY_CONFIG_IS_NULL = "wxpay.config.is.null";
	public static final String CONFIG_A_NUM_GREATER = "config.a.num.greater";
	public static final String CONFIG_B_NUM_GREATER = "config.b.num.greater";

	/**
	 * 会员管理
	 */
	public static final String MSG_MEMBER_TAG_LENGTH_LIMIT = "member.tag.length.limit";
	public static final String MSG_MEMBER_TAG_NOT_NULL = "member.tag.notnull";
	public static final String MSG_MEMBER_TAG_NAME_EXIST = "member.tag.name.exists";
	public static final String MSG_MEMBER_TAG_ADD_SUCCESS = "member.tag.add.success";
	public static final String MSG_MEMBER_TAG_ID_NOT_NULL = "member.tag.id.notnull";

	public static final String MSG_MEMBER_NOT_EXIST = "member.not.exist";
	public static final String MSG_SCORE_NOT_SAME = "member.score.not.same";
	public static final String MSG_MEMBER_SCORE_ERROR = "member.score.error";
	public static final String MSG_MEMBER_SCORE_NOT_ENOUGH = "member.score.not.enough";
	public static final String MSG_MEMBER_SCORE_NOT_NULL = "member.score.not.null";
	public static final String MSG_MEMBER_SCORE_NOT_BE_NEGATIVE = "member.score.not.be.negative";

	/**
	 * 会员余额
	 */
	public static final String MSG_MEMBER_ACCOUNT_UPDATE_FAIL = "member.account.update.fail";

	/**
	 * 会员卡
	 */

	public static final String MSG_MEMBER_CARD_RIGHTS_EMPTY = "member.card.rights.empty";
	public static final String MSG_MEMBER_CARD_ID_EMPTY = "member.card.id.empty";
	/**
	 * 概览
	 */
	public static final String OVERVIEW_MALL_DATADEMONSTRATION_GET_FAILED = "overview.mall.datademonstration.get.failed";
	public static final String OVERVIEW_MALL_BING_UNBING_FAILED = "overview.mall.bind.unbind.failed";
	public static final String OVERVIEW_MALL_TODOITEM_GET_FAILED = "overview.mall.todoitem.get.failed";

	/**
	 * 订单
	 */
	public static final String MSG_ORDER = "order";
	public static final String MSG_ORDER_OPERATE_NO_INSTANCEOF = "order_operate_no_instanceof";
	public static final String MSG_ORDER_REMARK_ORDERSN_NOT_NULL = "order_remark_ordersn_not_null";
	public static final String MSG_ORDER_REMARK_NOT_NULL = "order_remark_not_null";
	public static final String MSG_ORDER_REMARK_TYPE_NOT_NULL = "order_remark_type_not_null";
	public static final String INVALID_MONEY_AMOUNT = "order.refund.invalid_money_amount";
	public static final String INVALID_ACCOUNT_OR_SCORE = "order.refund.invalid_account_or_score";
	public static final String INVALID_REFUND_AMOUNT = "order.refund.invalid_refund_amount";
	public static final String REFUND_ACCOUNT_LARGER_THAN_ACCOUNT_PAID = "order.refund.account_larger_than_paid";
	public static final String REFUND_SCORE_LARGER_THAN_SCORE_PAID = "order.refund.score_larger_than_paid";

	/**
	 * 虚拟订单
	 */
	public static final String ORDER_VIRTUAL_COUPONPACK_REFUND_SCORE = "order.virtual.couponpack.refund.score";

    /**
     * 开屏有礼
     */
    public static final String COUPON_ACTIVITY_TIME_RANGE_CONFLICT = "activity.coupon.time_range_conflict";
	

}
