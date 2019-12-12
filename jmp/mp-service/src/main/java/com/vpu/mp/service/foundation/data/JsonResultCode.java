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
	 * @serialField 40开头：WxAppCode
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
	CODE_ACCOUNT_SYTEM_LOGIN_EXPIRED(100033, JsonResultMessage.MSG_ACCOUNT_SYTEM_LOGIN_EXPIRED),
	CODE_ACCOUNT_ENNAME_ISNULL(100034, JsonResultMessage.MSG_CODE_ACCOUNT_ENNAME_ISNULL),



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

	CODE_VIDEO_UPLOAD_FAILED(110010, JsonResultMessage.MSG_VIDEO_UPLOAD_FAILED),
	CODE_VIDEO_FORMAT_INVALID(110011, JsonResultMessage.MSG_VIDEO_FORMAT_INVALID),
	MSG_VIDEO_CATEGORY_VIDEOCATID_NOT_NULL(110012,JsonResultMessage.MSG_VIDEO_CATEGORY_VIDEOCATID_NOT_NULL),
	MSG_VIDEO_CATEGORY_VIDEOCATPARENTID_NOT_NULL(110013,JsonResultMessage.MSG_VIDEO_CATEGORY_VIDEOCATPARENTID_NOT_NULL),
	MSG_VIDEO_CATEGORY_VIDEOCATNAME_NOT_NULL(110014,JsonResultMessage.MSG_VIDEO_CATEGORY_VIDEOCATNAME_NOT_NULL),
	CODE_VIDEO_UPLOAD_GT_10M(110004, JsonResultMessage.MSG_VIDEO_UPLOAD_GT_10M),

	// 订单
	CODE_ORDER(120001, JsonResultMessage.MSG_ORDER),
	CODE_ORDER_OPERATE_NO_INSTANCEOF(120002, JsonResultMessage.MSG_ORDER_OPERATE_NO_INSTANCEOF),
    REFUND_REQUEST_PARAMETER_ERROR(120003, JsonResultMessage.REFUND_REQUEST_PARAMETER_ERROR),
	CODE_ORDER_NOT_EXIST(120004, JsonResultMessage.MSG_ORDER_NOT_EXIST),
	CODE_ORDER_RETURN_RETURN_SHIPPING_FEE_EXCESS(120005, JsonResultMessage.MSG_ORDER_RETURN_RETURN_SHIPPING_FEE_EXCESS),
	CODE_ORDER_RETURN_RETURN_SHIPPING_FEE_NOT_ZERO(120006, JsonResultMessage.MSG_ORDER_RETURN_RETURN_SHIPPING_FEE_NOT_ZERO),

	CODE_ORDER_RETURN_METHOD_REFLECT_ERROR(120020, JsonResultMessage.MSG_ORDER_RETURN_METHOD_REFLECT_ERROR),
	CODE_ORDER_RETURN_WX_FAIL(120021, JsonResultMessage.MSG_ORDER_RETURN_WX_FAILL),
	CODE_ORDER_RETURN_AFTER_RETURNAMOUNT_GREAT_THAN_ZERO(120022, JsonResultMessage.MSG_ORDER_RETURN_AFTER_RETURNAMOUNT_GREAT_THAN_ZERO),
	CODE_ORDER_RETURNING_RETURN_METHOD_ERROR(120023, JsonResultMessage.MSG_ORDER_RETURNING_RETURN_METHOD_ERROR),
	CODE_ORDER_RETURN_MANUAL_INCONSISTENT_AMOUNT(120024, JsonResultMessage.MSG_ORDER_RETURN_MANUAL_INCONSISTENT_AMOUNT),
	CODE_ORDER_FINISH_RETURN_STATUS_ERROR(120025, JsonResultMessage.MSG_ORDER_FINISH_RETURN_STATUS_ERROR),
	CODE_ORDER_RETURN_ROLLBACK_NO_MPEXCEPTION(120026, JsonResultMessage.MSG_ORDER_RETURN_ROLLBACK_NO_MPEXCEPTION),
	CODE_ORDER_RETURN_MONEY_EXCEEDED(120027, JsonResultMessage.MSG_ORDER_RETURN_MONEY_EXCEEDED),
	CODE_ORDER_RETURN_STATUS_NOT_SATISFIED(120028, JsonResultMessage.MSG_ORDER_RETURN_STATUS_NOT_SATISFIED),
	CODE_ORDER_RETURN_WXPAYREFUND_NO_RECORD(120029, JsonResultMessage.MSG_ORDER_RETURN_WXPAYREFUND_NO_RECORD),
	CODE_ORDER_RETURN_WXPAYREFUND_ERROR(120030, JsonResultMessage.MSG_ORDER_RETURN_WXPAYREFUND_ERROR),
	CODE_ORDER_RETURN_NOT_SUPPORT_RETURN_TYPE(120031, JsonResultMessage.MSG_ORDER_RETURN_NOT_SUPPORT_RETURN_TYPE),
	CODE_ORDER_RETURN_NO_SELECT_GOODS(120032, JsonResultMessage.MSG_ORDER_RETURN_NO_SELECT_GOODS),
	CODE_ORDER_RETURN_NOT_NULL_RETURNTYPE(120033, JsonResultMessage.MSG_ORDER_RETURN_NOT_NULL_RETURNTYPE),
	CODE_ORDER_RETURN_NOT_NULL_RETURNMONEY(120034, JsonResultMessage.MSG_ORDER_RETURN_NOT_NULL_RETURNMONEY),
	CODE_ORDER_RETURN_NOT_NULL_SHIPPINGFEE(120035, JsonResultMessage.MSG_ORDER_RETURN_NOT_NULL_SHIPPINGFEE),
	CODE_ORDER_RETURN_GOODS_RETURN_COMPLETED(120036, JsonResultMessage.MSG_ORDER_RETURN_GOODS_RETURN_COMPLETED),
	CODE_ORDER_RETURN_GOODS_RETURN_NUMBER_ERROR(120037, JsonResultMessage.MSG_ORDER_RETURN_GOODS_RETURN_NUMBER_ERROR),
	CODE_ORDER_RETURN_GOODS_NO_CAN_RETURN(120039, JsonResultMessage.MSG_ORDER_RETURN_GOODS_NO_CAN_RETURN),
	CODE_ORDER_RETURN_MANUAL_MONEY_ERROR(120040, JsonResultMessage.MSG_ORDER_RETURN_MANUAL_MONEY_ERROR),
	CODE_ORDER_RETURN_OPERATION_NOT_SUPPORTED_BECAUSE_STATUS_ERROR(120041, JsonResultMessage.MSG_ORDER_RETURN_OPERATION_NOT_SUPPORTED_BECAUSE_STATUS_ERROR),
	CODE_ORDER_CANCEL_NOT_CANCEL(120042, JsonResultMessage.MSG_ORDER_CANCEL_NOT_CANCEL),
	CODE_ORDER_CANCEL_FAIL(120043, JsonResultMessage.MSG_ORDER_CANCEL_FAIL),
	CODE_ORDER_CLOSE_NOT_CLOSE(120044, JsonResultMessage.MSG_ORDER_CLOSE_NOT_CLOSE),
	CODE_ORDER_CLOSE_FAIL(120045, JsonResultMessage.MSG_ORDER_CLOSE_FAIL),
	CODE_ORDER_RETURN_RETURN_ORDER_NOT_EXIST(120045, JsonResultMessage.MSG_ORDER_RETURN_RETURN_ORDER_NOT_EXIST),
	CODE_ORDER_VERIFY_OPERATION_NOT_SUPPORTED(120046, JsonResultMessage.MSG_ORDER_VERIFY_OPERATION_NOT_SUPPORTED),
	CODE_ORDER_VERIFY_CODE_ERROR(120047, JsonResultMessage.MSG_ORDER_VERIFY_CODE_ERROR),
	CODE_ORDER_FINISH_OPERATION_NOT_SUPPORTED(120048, JsonResultMessage.MSG_ORDER_FINISH_OPERATION_NOT_SUPPORTED),
	CODE_ORDER_RECEIVE_OPERATION_NOT_SUPPORTED(120049, JsonResultMessage.MSG_ORDER_RECEIVE_OPERATION_NOT_SUPPORTED),
	CODE_ORDER_REMIND_OPERATION_NOT_SUPPORTED(120050, JsonResultMessage.MSG_ORDER_REMIND_OPERATION_NOT_SUPPORTED),
	CODE_ORDER_REMIND_OPERATION_LIMIT(120051, JsonResultMessage.MSG_ORDER_REMIND_OPERATION_LIMIT),
	CODE_ORDER_REMIND_OPERATION_LIMIT_TODAY(120052, JsonResultMessage.MSG_ORDER_REMIND_OPERATION_LIMIT_TODAY),
	CODE_ORDER_EXTEND_RECEIVE_ONLY_ONE(120053, JsonResultMessage.MSG_ORDER_EXTEND_RECEIVE_ONLY_ONE),
	CODE_ORDER_EXTEND_RECEIVE_NO_SHIPPED(120054, JsonResultMessage.MSG_ORDER_EXTEND_RECEIVE_NO_SHIPPED),
	CODE_ORDER_EXTEND_RECEIVE_NOT_SUPPORTED(120055, JsonResultMessage.MSG_ORDER_EXTEND_RECEIVE_NOT_SUPPORTED),
	CODE_ORDER_EXTEND_RECEIVE_TIME_NOT_LT_AUTOTIME(120056, JsonResultMessage.MSG_ORDER_EXTEND_RECEIVE_TIME_NOT_LT_AUTOTIME),
	CODE_ORDER_EXTEND_RECEIVE_NOW_AUTOTIME_INTERVAL_GT_TWO_DAYS(120057, JsonResultMessage.MSG_ORDER_EXTEND_RECEIVE_NOW_AUTOTIME_INTERVAL_GT_TWO_DAYS),
	CODE_ORDER_EXTEND_RECEIVE_ADMIN_SET_MORE_TIME(120058, JsonResultMessage.MSG_ORDER_EXTEND_RECEIVE_ADMIN_SET_MORE_TIME),
	CODE_ORDER_DELETE_OPERATION_NOT_SUPPORTED(120059, JsonResultMessage.MSG_ORDER_DELETE_OPERATION_NOT_SUPPORTED),
    /**
     * 下单参数
     */
	/**商品或规格不存在*/
	CODE_ORDER_GOODS_NOT_EXIST(120060, JsonResultMessage.MSG_ORDER_GOODS_NOT_EXIST),
	/**{0}商品已下架*/
	CODE_ORDER_GOODS_NO_SALE(120061, JsonResultMessage.MSG_ORDER_GOODS_NO_SALE),
	/**{0}商品库存不足*/
	CODE_ORDER_GOODS_OUT_OF_STOCK(120062, JsonResultMessage.MSG_ORDER_GOODS_OUT_OF_STOCK),
	/**商品数量为0*/
	CODE_ORDER_GOODS_NO_ZERO(120063, JsonResultMessage.MSG_ORDER_GOODS_NO_ZERO),
    /**计算运费时运费模板,未找到可配送的区域*/
    CODE_ORDER_CALCULATE_SHIPPING_FEE_ERROR(120064, JsonResultMessage.MSG_ORDER_CALCULATE_SHIPPING_FEE_ERROR),
    /**快递订单必须含有地址信息*/
    CODE_ORDER_ADDRESS_NO_NULL(120065, JsonResultMessage.MSG_ORDER_ADDRESS_NO_NULL),
    /**会员卡失效*/
    CODE_ORDER_CARD_INVALID(120066, JsonResultMessage.MSG_ORDER_CARD_INVALID),
    /**优惠卷失效*/
    CODE_ORDER_COUPON_INVALID(120067, JsonResultMessage.MSG_ORDER_COUPON_INVALID),
    /**积分不足*/
    CODE_ORDER_SCORE_NOT_ENOUGH(120068, JsonResultMessage.MSG_ORDER_SCORE_NOT_ENOUGH),
    /**余额不足*/
    CODE_ORDER_ACCOUNT_NOT_ENOUGH(120069, JsonResultMessage.MSG_ORDER_ACCOUNT_NOT_ENOUGH),
    /**会员卡余额不足*/
    CODE_ORDER_CARD_NOT_ENOUGH(120070, JsonResultMessage.MSG_ORDER_CARD_NOT_ENOUGH),
    /**订单金额变更为{}，是否确认购买*/
    CODE_ORDER_AMOUNT_CHANGE(120071, JsonResultMessage.MSG_ORDER_AMOUNT_CHANGE),
    /**积分抵扣金额不能超过*/
    CODE_ORDER_SCORE_LIMIT(120072, JsonResultMessage.MSG_ORDER_SCORE_LIMIT),
    /**超出会员卡可兑换数量*/
    CODE_ORDER_MCARD_TP_LIMIT_LIMIT(120073, JsonResultMessage.MSG_ORDER_MCARD_TP_LIMIT_LIMIT),
    /**当前配送方式不支持*/
    CODE_ORDER_DELIVER_TYPE_NO_SUPPORT(120074, JsonResultMessage.MSG_ORDER_DELIVER_TYPE_NO_SUPPORT),
    /**不支持微信支付*/
    CODE_ORDER_PAY_WAY_NO_SUPPORT_WX(120075, JsonResultMessage.MSG_ORDER_PAY_WAY_NO_SUPPORT_WX),
    /**不支持货到付款**/
    CODE_ORDER_PAY_WAY_NO_SUPPORT_COD(120076, JsonResultMessage.MSG_ORDER_PAY_WAY_NO_SUPPORT_COD),
    /**不支持余额支付*/
    CODE_ORDER_PAY_WAY_NO_SUPPORT_ACCOUNT(120077, JsonResultMessage.MSG_ORDER_PAY_WAY_NO_SUPPORT_ACCOUNT),
    /**不支持积分支付*/
    CODE_ORDER_PAY_WAY_NO_SUPPORT_SCORE(120078, JsonResultMessage.MSG_ORDER_PAY_WAY_NO_SUPPORT_SCORE),
    /**不支持会员卡余额支付*/
    CODE_ORDER_PAY_WAY_NO_SUPPORT_CARD(120079, JsonResultMessage.MSG_ORDER_PAY_WAY_NO_SUPPORT_CARD),
    /**商品不存在*/
    CODE_ORDER_GOODS_NO_EXIST(120080, JsonResultMessage.MSG_ORDER_GOODS_NO_EXIST),
    /**库存不足*/
    CODE_ORDER_GOODS_LOW_STOCK(120081, JsonResultMessage.MSG_ORDER_GOODS_LOW_STOCK),
    /**redis锁获取失败（服务忙）*/
    CODE_ORDER_GOODS_GET_LOCK_FAIL(120082, JsonResultMessage.MSG_ORDER_GOODS_GET_LOCK_FAIL),
    /**更新库存失败*/
    CODE_ORDER_UPDATE_STOCK_FAIL(120083, JsonResultMessage.MSG_ORDER_UPDATE_STOCK_FAIL),
    /**下单数据库执行异常*/
    CODE_ORDER_DATABASE_ERROR(120084, JsonResultMessage.MSG_ORDER_DATABASE_ERROR),
    /**{名称}最小限购{数量}*/
    CODE_ORDER_GOODS_LIMIT_MIN(120085, JsonResultMessage.MSG_ORDER_GOODS_LIMIT_MIN),
    /**{名称}最大限购{数量}*/
    CODE_ORDER_GOODS_LIMIT_MAX(120086, JsonResultMessage.MSG_ORDER_GOODS_LIMIT_MAX),

    /**
     * 去支付
     */
    /**该订单非待支付订单*/
    CODE_ORDER_TOPAY_STATUS_NOT_WAIT_PAY(120100, JsonResultMessage.MSG_ORDER_TOPAY_STATUS_NOT_WAIT_PAY),
    /**不在尾款支付时间*/
    CODE_ORDER_TOPAY_BK_PAY_NOT_START(120101, JsonResultMessage.MSG_ORDER_TOPAY_BK_PAY_NOT_START),
    /**该订单已过期需重新下单*/
    CODE_ORDER_TOPAY_EXPIRED(120102, JsonResultMessage.MSG_ORDER_TOPAY_EXPIRED),

    /**
     * 发货
     */
    CODE_ORDER_SHIPPING_SHIPPINGNO_NOT_NULL(120200, JsonResultMessage.MSG_ORDER_SHIPPING_SHIPPINGNO_NOT_NULL),
    CODE_ORDER_SHIPPING_SHIPPINGID_NOT_NULL(120201, JsonResultMessage.MSG_ORDER_SHIPPING_SHIPPINGID_NOT_NULL),
    CODE_ORDER_SHIPPING_SHIPGOODS_NOT_NULL(120202, JsonResultMessage.MSG_ORDER_SHIPPING_SHIPGOODS_NOT_NULL),

    /**
     * 微信支付相关
     */
    CODE_ORDER_NOT_TO_WAIT_DELIVER(121000, JsonResultMessage.MSG_ORDER_NOT_TO_WAIT_DELIVER),


    // 商品
    GOODS_ID_IS_NULL(130001,JsonResultMessage.GOODS_ID_IS_NULL),
    GOODS_NAME_EXIST(130002,JsonResultMessage.GOODS_NAME_EXIST),
    GOODS_NAME_IS_NULL(130003,JsonResultMessage.GOODS_NAME_IS_NULL),
    GOODS_SN_EXIST(130004,JsonResultMessage.GOODS_SN_EXIST),
    GOODS_MAIN_IMG_IS_NULL(130004,JsonResultMessage.GOODS_MAIN_IMG_IS_NULL),
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

    //商品规格
    GOODS_SPEC_PRD_SN_EXIST(135001,JsonResultMessage.GOODS_SPEC_PRD_SN_EXIST),
    GOODS_SPEC_NAME_REPETITION(135002,JsonResultMessage.GOODS_SPEC_NAME_REPETITION),
    GOODS_SPEC_VAL_REPETITION(135003,JsonResultMessage.GOODS_SPEC_VAL_REPETITION),
    GOODS_SPEC_ATTRIBUTE_SPEC_K_V_CONFLICT(135004,JsonResultMessage.GOODS_SPEC_ATTRIBUTE_SPEC_K_V_CONFLICT),

    GOODS_RECOMMEND_NAME_NOT_NULL(136001,JsonResultMessage.GOODS_RECOMMEND_NAME_NOT_NULL),
    GOODS_RECOMMEND_TYPE_NOT_NULL(136002,JsonResultMessage.GOODS_RECOMMEND_TYPE_NOT_NULL),
    GOODS_RECOMMEND_NAME_EXIST(136003,JsonResultMessage.GOODS_RECOMMEND_NAME_EXIST),
    GOODS_RECOMMEND_ID_NOT_EXIST(136004,JsonResultMessage.GOODS_RECOMMEND_ID_NOT_EXIST),
    GOODS_RECOMMEND_NOT_EXIST(136005,JsonResultMessage.GOODS_RECOMMEND_NOT_EXIST),
    GOODS_RECOMMEND_CHOOSE_TYPE_NOT_NULL(136006,JsonResultMessage.GOODS_RECOMMEND_CHOOSE_TYPE_NOT_NULL),
    GOODS_RECOMMEND_NUMBER_NOT_NULL(136007,JsonResultMessage.GOODS_RECOMMEND_NUMBER_NOT_NULL),

    //商品评论
    GOODS_COMMENT_NO_PERMISSION(137001,JsonResultMessage.GOODS_COMMENT_NO_PERMISSION),

    //营销
    DISTRIBUTOR_GROUP_NAME_EXIST(141001,JsonResultMessage.DISTRIBUTOR_GROUP_NAME_EXIST),
    /**
     * 营销-好友代付
     */
    INSTEAD_PAY_NOT_SET_PAY_WAY(142001,JsonResultMessage.INSTEAD_PAY_NOT_SET_PAY_WAY),
    INSTEAD_PAY_NOT_SET_SINGLE_PAY_MESSAGE(142002,JsonResultMessage.INSTEAD_PAY_NOT_SET_SINGLE_PAY_MESSAGE),
    INSTEAD_PAY_SINGLE_PAY_MESSAGE_TOO_LONG(142003,JsonResultMessage.INSTEAD_PAY_SINGLE_PAY_MESSAGE_TOO_LONG),
    INSTEAD_PAY_NOT_SET_MULTIPLE_PAY_MESSAGE(142004,JsonResultMessage.INSTEAD_PAY_NOT_SET_MULTIPLE_PAY_MESSAGE),
    INSTEAD_PAY_MULTIPLE_PAY_MESSAGE_TOO_LONG(142005,JsonResultMessage.INSTEAD_PAY_MULTIPLE_PAY_MESSAGE_TOO_LONG),
    INSTEAD_PAY_NEED_AT_LEAST_THREE_PAY_RATIO(142006,JsonResultMessage.INSTEAD_PAY_NEED_AT_LEAST_THREE_PAY_RATIO),
    INSTEAD_PAY_NEED_AT_LEAST_TWO_DOUBLE_PAY_RATIO(142007,JsonResultMessage.INSTEAD_PAY_NEED_AT_LEAST_TWO_DOUBLE_PAY_RATIO),
    INSTEAD_PAY_VALUE_OVER_RANGE(142008,JsonResultMessage.INSTEAD_PAY_VALUE_OVER_RANGE),
    INSTEAD_PAY_STATUS_IS_NULL(142009,JsonResultMessage.INSTEAD_PAY_STATUS_IS_NULL),
	/**
	 * 营销-多人拼团
	 */
	GROUP_BUY_ADD_ACTIVITY_STOP_STATUS(143001,JsonResultMessage.GROUP_BUY_ADD_ACTIVITY_STOP_STATUS),
	GROUP_BUY_ACTIVITY_GOODS_OVERLAPPING(143002,JsonResultMessage.GROUP_BUY_ACTIVITY_GOODS_OVERLAPPING),
	GROUP_BUY_GROUPID_DOES_NOT_EXIST(143003,JsonResultMessage.GROUP_BUY_GROUPID_DOES_NOT_EXIST),
	GROUP_BUY_ACTIVITY_STATUS_DISABLE(143004,JsonResultMessage.GROUP_BUY_ACTIVITY_STATUS_DISABLE),
	GROUP_BUY_ACTIVITY_STATUS_NOTSTART(143005,JsonResultMessage.GROUP_BUY_ACTIVITY_STATUS_NOTSTART),
	GROUP_BUY_ACTIVITY_STATUS_END(143005,JsonResultMessage.GROUP_BUY_ACTIVITY_STATUS_END),
	GROUP_BUY_ACTIVITY_GROUP_STATUS_CANCEL(143005,JsonResultMessage.GROUP_BUY_ACTIVITY_GROUP_STATUS_CANCEL),
	GROUP_BUY_ACTIVITY_GROUP_OPEN_LIMIT_MAX(143005,JsonResultMessage.GROUP_BUY_ACTIVITY_GROUP_OPEN_LIMIT_MAX),
	GROUP_BUY_ACTIVITY_GROUP_JOIN_LIMIT_MAX(143005,JsonResultMessage.GROUP_BUY_ACTIVITY_GROUP_JOIN_LIMIT_MAX),
	GROUP_BUY_ACTIVITY_GROUP_EMPLOEES_MAX(143005,JsonResultMessage.GROUP_BUY_ACTIVITY_GROUP_EMPLOEES_MAX),
    /**
     * 营销-秒杀
     */
    SECKILL_CONFLICTING_ACT_TIME(144001,JsonResultMessage.SECKILL_CONFLICTING_ACT_TIME),
    // 分享有礼
    SHARE_REWARD_COUPON_NUM_LIMIT(145001, JsonResultMessage.SHARE_REWARD_COUPON_NUM_LIMIT),
    /**
     * 营销-砍价
     */
    BARGAIN_CONFLICTING_ACT_TIME(146001,JsonResultMessage.BARGAIN_CONFLICTING_ACT_TIME),

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
	CODE_MEMBER_CARD_ACCOUNT_UPDATE_FAIL(15102,JsonResultMessage.MSG_MEMBER_CARD_ACCOUNT_UPDATE_FAIL),
	CODE_MEMBER_CARD_SURPLUS_UPDATE_FAIL(15103,JsonResultMessage.MSG_MEMBER_CARD_SURPLUS_UPDATE_FAIL),
	CODE_MEMBER_CARD_EXCHANGSURPLUS_UPDATE_FAIL(15104,JsonResultMessage.MSG_MEMBER_CARD_EXCHANGSURPLUS_UPDATE_FAIL),
	//会员卡
	CODE_MEMBER_CARD_RIGHTS_EMPTY(15201,JsonResultMessage.MSG_MEMBER_CARD_RIGHTS_EMPTY),
	CODE_MEMBER_CARD_ID_EMPTY(15202,JsonResultMessage.MSG_MEMBER_CARD_ID_EMPTY),
	CODE_CARD_ACTIVATE_SUCCESS(15203,JsonResultMessage.MSG_CARD_ACTIVATE_SUCCESS),
	CODE_CARD_ACTIVATE_FAIL(15204,JsonResultMessage.CARD_ACTIVATE_FAIL),

	// 用户卡
	CODE_USER_CARD_NONE(15205, JsonResultMessage.USER_CARD_NONE),
	CODE_MEMBER_CARD_DELETE(15206, JsonResultMessage.MSG_MEMBER_CARD_DELETE),
	CODE_LIMIT_CARD_AVAIL_SEND_NONE(15207, JsonResultMessage.MSG_LIMIT_CARD_AVAIL_SEND_NONE),
	CODE_LIMIT_CARD_AVAIL_SEND_ALL(15210, JsonResultMessage.MSG_LIMIT_CARD_AVAIL_SEND_ALL),
	CODE_CARD_SEND_REPEAT(15208, JsonResultMessage.MSG_CARD_SEND_REPEAT),
	CODE_CARD_GRADE_NONE(15209, JsonResultMessage.MSG_CARD_GRADE_NONE),
	CODE_CARD_RECEIVE_FAIL(15211, JsonResultMessage.MSG_CARD_RECEIVE_FAIL),
	CODE_CARD_RECEIVE_INVALID(15212, JsonResultMessage.MSG_CARD_RECEIVE_INVALID),
	CODE_CARD_RECEIVE_NOCODE(15213, JsonResultMessage.MSG_CARD_RECEIVE_NOCODE),
	CODE_CARD_RECEIVE_GENERATE(15214, JsonResultMessage.MSG_CARD_RECEIVE_GENERATE),
	CODE_CARD_RECEIVE_ALREADYHAS(15215, JsonResultMessage.MSG_CARD_RECEIVE_ALREADYHAS),
	CODE_CARD_RECEIVE_PWD(15216, JsonResultMessage.MSG_CARD_RECEIVE_PWD),
	CODE_CARD_RECEIVE_GOTOLOOK(15217, JsonResultMessage.MSG_CARD_RECEIVE_GOTOLOOK),
	CODE_CARD_RECEIVE_VALIDPWD(15218, JsonResultMessage.MSG_CARD_RECEIVE_VALIDPWD),
	CODE_CARD_RECEIVE_VALIDCODE(15219, JsonResultMessage.MSG_CARD_RECEIVE_VALIDCODE),


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
    CODE_APPLET_QR_CODE_GET_FAILED(170006, JsonResultMessage.CODE_APPLET_QR_CODE_GET_FAILED),

	DECORATE_BOTTOM_ISNOTJSON(170006,JsonResultMessage.DECORATE_BOTTOM_ISNOTJSON),
	DECORATE_STYLE_ISNOTJSON(170007,JsonResultMessage.DECORATE_STYLE_ISNOTJSON),
	DECORATE_STYLE_PARAM_ID_NULL(170008,JsonResultMessage.DECORATE_STYLE_PARAM_UPDATE_ID_NULL),
	DECORATE_STYLE_PARAM_VALUE_NULL(170009,JsonResultMessage.DECORATE_STYLE_PARAM_UPDATE_VALUE_NULL),
	DECORATE_URL_ILLEGAL(170010,JsonResultMessage.DECORATE_URL_ILLEGAL),

	WX_BINDING_MINI_NO_SAME(170011,JsonResultMessage.BINDING_MINI_NO_SAME),
	WX_BINDING_MINI_HAVEBIND(170012,JsonResultMessage.BINDING_MINI_HAVEBIND),

	WX_MA_APP_ID_NOT_AUTH(170013,JsonResultMessage.WX_MA_APP_ID_NOT_AUTH),
	WX_MA_TEMPLATE_ID_NOT_NULL(170014,JsonResultMessage.WX_MA_TEMPLATE_ID_NOT_NULL),
	WX_MA_PACKAGE_VERSION_NOT_NULL(170015,JsonResultMessage.WX_MA_PACKAGE_VERSION_NOT_NULL),
	WX_MA_NEED_AUTHORIZATION(170016,JsonResultMessage.WX_MA_NEED_AUTHORIZATION),

	WX_ERROR_EXCEPTION(170017,JsonResultMessage.WX_ERROR_EXCEPTION),
	WX_MA_NEED_UPLOADCODE(170018,JsonResultMessage.WX_MA_NEED_UPLOADCODE),
	WX_MA_NEED_AUDITING_CODE_SUCCESS(170019,JsonResultMessage.WX_MA_NEED_AUDITING_CODE_SUCCESS),
    WX_MA_SHOP_HAS_NO_AP(170020,JsonResultMessage.WX_MA_SHOP_HAS_NO_APP),
    WX_MA_FEATURE_NOT_OPEN(170021,JsonResultMessage.WX_MA_FEATURE_NOT_OPEN),
    WX_MA_ISSUBMERCHANT_ISNULL(170022,JsonResultMessage.WX_MA_ISSUBMERCHANT_ISNULL),
    WX_MA_TABLE_ISNULL(170023,JsonResultMessage.WX_MA_TABLE_ISNULL),
    WX_MP_NO_ACCESS(170024,JsonResultMessage.WX_MP_NO_ACCESS),
    WX_MA_HAVE_MP(170025,JsonResultMessage.WX_MA_HAVE_MP),
    WX_MP_NEED_CHOOSE_RIGHT(170026,JsonResultMessage.WX_MP_NEED_CHOOSE_RIGHT),
    WX_NO_REQUIRED(170027,JsonResultMessage.WX_NO_REQUIRED),
    WX_ONLY_ONE(170028,JsonResultMessage.WX_ONLY_ONE),
    WX_JOB_PROBLEM(170029,JsonResultMessage.WX_JOB_PROBLEM),

    WX_9300529(170030, JsonResultMessage.WX_9300529),
    WX_9300530(170031, JsonResultMessage.WX_9300530),
    WX_9300531(170032, JsonResultMessage.WX_9300531),
    WX_9300532(170033, JsonResultMessage.WX_9300532),
    SEARCHCFG_HOTWORDS_LIMIT(170034, JsonResultMessage.SEARCHCFG_HOTWORDS_LIMIT),
    SEARCHCFG_TITLECUSTOM_NOTNULL(170035, JsonResultMessage.SEARCHCFG_TITLECUSTOM_NOTNULL),
    WX_GETQRCODE_FAIL(170036, JsonResultMessage.WX_GETQRCODE_FAIL),
    WX_READQRCODE_FAIL(170037, JsonResultMessage.WX_READQRCODE_FAIL),
    WX_GETHEAD_FAIL(170038, JsonResultMessage.WX_GETHEAD_FAIL),
    WX_GETBG_FAIL(170039, JsonResultMessage.WX_GETBG_FAIL),
    WX_SHARESHOP(170040, JsonResultMessage.WX_SHARESHOP),
    WX_SCAN_QRSHOP(170041, JsonResultMessage.WX_SCAN_QRSHOP),

	//门店管理
	CODE_STORE_GROUP_NAME_EXIST(180001,JsonResultMessage.STORE_GROUP_NAME_EXIST),
	CODE_POS_SHOP_ID_EXIST(180002,JsonResultMessage.STORE_POS_SHOP_ID_EXIST),
    CODE_DATA_NOT_EXIST(180003, JsonResultMessage.CODE_DATA_NOT_EXIST),
    CODE_STORE_NOT_EXIST(180011, JsonResultMessage.CODE_STORE_NOT_EXIST),
    CODE_STORE_SERVICE_NOT_EXIST(180012, JsonResultMessage.CODE_STORE_SERVICE_NOT_EXIST),
    CODE_JACKSON_DESERIALIZATION_FAILED(180004, JsonResultMessage.CODE_JACKSON_DESERIALIZATION_FAILED),
    CODE_JACKSON_SERIALIZATION_FAILED(180005, JsonResultMessage.CODE_JACKSON_SERIALIZATION_FAILED),
    CODE_STORE_ALREADY_DEL(180006, JsonResultMessage.CODE_STORE_ALREADY_DEL),
    CODE_USER_CARD_BALANCE_INSUFFICIENT(180007, JsonResultMessage.CODE_USER_CARD_BALANCE_INSUFFICIENT),
    CODE_SCORE_INSUFFICIENT(180008, JsonResultMessage.CODE_SCORE_INSUFFICIENT),
    CODE_BALANCE_INSUFFICIENT(180009, JsonResultMessage.CODE_BALANCE_INSUFFICIENT),
    CODE_AMOUNT_PAYABLE_CALCULATION_FAILED(180010, JsonResultMessage.CODE_AMOUNT_PAYABLE_CALCULATION_FAILED),
    CODE_DB_DATA_ABNORMAL(180013, JsonResultMessage.CODE_DB_DATA_ABNORMAL),
    CODE_RESERVATION_UPPER_LIMIT(180014, JsonResultMessage.CODE_RESERVATION_UPPER_LIMIT),
    CODE_DATA_ALREADY_EXIST(180015, JsonResultMessage.CODE_DATA_ALREADY_EXIST),
    CODE_ORDER_AMOUNT_NOT_EQUALS_SERVICE_SUBSIST(180016, JsonResultMessage.CODE_ORDER_AMOUNT_NOT_EQUALS_SERVICE_SUBSIST),
    CODE_WX_LOGISTICS_API_CALL_FAILED(180017, JsonResultMessage.CODE_WX_LOGISTICS_API_CALL_FAILED),

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
    RETURN_CONFIG_SELECT_FAILED(190013, JsonResultMessage.RETURN_CONFIG_SELECT_FAILED),
	WXPAY_CONFIG_IS_NULL(190014,JsonResultMessage.WXPAY_CONFIG_IS_NULL),
	CODE_CONFIG_A_NUM_GREATER(190015,JsonResultMessage.CONFIG_A_NUM_GREATER),
	CODE_CONFIG_B_NUM_GREATER(190016,JsonResultMessage.CONFIG_B_NUM_GREATER),
    CODE_CONFIG_UPDATE_FAILED(190017,JsonResultMessage.CODE_CONFIG_UPDATE_FAILED),

	//概览
	OVERVIEW_MALL_DATADEMONSTRATION_GET_FAILED(200001,JsonResultMessage.OVERVIEW_MALL_DATADEMONSTRATION_GET_FAILED),
	OVERVIEW_MALL_BING_UNBING_FAILED(200002,JsonResultMessage.OVERVIEW_MALL_BING_UNBING_FAILED),
	OVERVIEW_MALL_TODOITEM_GET_FAILED(200003,JsonResultMessage.OVERVIEW_MALL_TODOITEM_GET_FAILED),
    OVERVIEW_USER_ANALYSIS_RFM_NULL(200004,JsonResultMessage.OVERVIEW_USER_ANALYSIS_RFM_NULL),
    OVERVIEW_YESTERDAY_ANALYSIS_DATA_NULL(200005,JsonResultMessage.OVERVIEW_YESTERDAY_ANALYSIS_DATA_NULL),

    //WxAppCode
	ERR_CODE_INVALID_SIGN(400001,JsonResultMessage.ERR_CODE_INVALID_SIGN),
	ERR_CODE_TOKEN_ERROR(400002,JsonResultMessage.ERR_CODE_TOKEN_ERROR),
	ERR_CODE_EXCEPTION(400003,JsonResultMessage.ERR_CODE_EXCEPTION),
	ERR_CODE_OPERATION_FAILED(400004,JsonResultMessage.ERR_CODE_OPERATION_FAILED),
	ERR_CODE_LOGIN_FAILED(400005,JsonResultMessage.ERR_CODE_LOGIN_FAILED),
	ERR_CODE_PAY_FAILED(400006,JsonResultMessage.ERR_CODE_PAY_FAILED),
	ERR_CODE_CODE_SING(2,JsonResultMessage.ERR_CODE_CODE_SING),
	ERR_CODE_HAVE_SING(2,JsonResultMessage.ERR_CODE_HAVE_SING),
	ERR_CODE_CODE_SING_ERRO(2,JsonResultMessage.ERR_CODE_CODE_SING_ERRO),

	// 小程序-购物车401
	CODE_CART_GOODS_NO_LONGER_VALID(401001,JsonResultMessage.CART_GOODS_NO_LONGER_VALID),
	CODE_CART_THERE_IS_STILL_INVENTORY(401002,JsonResultMessage.CART_THERE_IS_STILL_INVENTORY),
	CODE_CART_MINIMUM_PURCHASE(401003,JsonResultMessage.CART_MINIMUM_PURCHASE),
	CODE_CART_MAXIMUM_PURCHASE(401004,JsonResultMessage.CART_MAXIMUM_PURCHASE),

    /**微信支付错误信息
     *
     */
    /**微信预支付调用接口*/
    CODE_ORDER_WXPAY_UNIFIEDORDER_FAIL(410000, JsonResultMessage.MSG_ORDER_WXPAY_UNIFIEDORDER_FAIL),
    /**无法获取prepay id*/
    CODE_WX_PAY_PREPAY_ID_IS_NULL(410001, JsonResultMessage.MSG_WX_PAY_PREPAY_ID_IS_NULL);



	/**
	 * 得到返回码
	 */
	private int code;

	/**
	 * 返回信息
	 */
	private String message;


	private JsonResultCode(int code, String message,Object... asgs) {
		this.code = code;
		this.message = message;
	}
}
