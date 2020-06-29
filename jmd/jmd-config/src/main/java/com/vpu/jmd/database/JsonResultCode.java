package com.vpu.jmd.database;

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
    CODE_SUCCESS(0, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_SUCCESS),
    CODE_FAIL(-1, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_FAIL),
    CODE_PARAM_ERROR(-3, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_PARAM_ERROR),
    CODE_API_NO_RESUBMIT(-4, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_API_NO_RESUBMIT),

    // 账号
    CODE_ACCOUNT_OR_PASSWORD_INCRRECT(100001, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_OR_PASSWORD_INCRRECT),
    CODE_ACCOUNT_MODILE_APPLIED(100002, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_MODILE_APPLIED),
    CODE_ACCOUNT_MODILE_REGISTERED(100003, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_MODILE_REGISTERED),
    CODE_ACCOUNT_LOGIN_EXPIRED(100004, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_LOGIN_EXPIRED),
    CODE_ACCOUNT_ROLE__AUTH_INSUFFICIENT(100005, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_ROLE__AUTH_INSUFFICIENT),
    CODE_ACCOUNT_ROLE__SHOP_SELECT(100006, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_ROLE__SHOP_SELECT),
    CODE_ACCOUNT_NAME_NOT_NULL(100007, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_NAME_NOT_NULL),
    CODE_ACCOUNT_ISSUBLOGIN_NOT_NULL(100007, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_ISSUBLOGIN_NOT_NULL),
    CODE_ACCOUNT_MODILE_NOT_NULL(100008, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_MODILE_NOT_NULL),
    CODE_ACCOUNT_SAME(100009, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_SAME),
    CODE_MOBILE_SAME(100010, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MOBILE_SAME),
    CODE_ACCOUNT_SHOP_NULL(100011, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_SHOP_NULL),
    CODE_ACCOUNT_SHOP_EXPRIRE(100012, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_SHOP_EXPIRE),
    CODE_ACCOUNT_PASSWD_NO_SAME(100013, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_PASSWD_NO_SAME),
    CODE_ACCOUNT_OLD_PASSWD_ERROR(100014, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_OLD_PASSWD_ERROR),
    CODE_ACCOUNT_OLD_NEW_PASSWD_NO_SAME(100015, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_OLD_NEW_PASSWD_NO_SAME),
    CODE_MSG_ACCOUNT_PASSWD_NOT_NULL(100016, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_PASSWD_NOT_NULL),
    CODE_MSG_ACCOUNT_NEWPASSWD_NOT_NULL(100017, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_NEWPASSWD_NOT_NULL),
    CODE_ACCOUNT_CONFNEWPASSWD_NOT_NULL(100018, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_CONFNEWPASSWD_NOT_NULL),
    CODE_ACCOUNT_SHOPAVATAR_NOT_NULL(100019, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_SHOPAVATAR_NOT_NULL),
    CODE_ACCOUNT_ACCOUNTNAME_NOT_NULL(100020, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_ACCOUNTNAME_NOT_NULL),
    CODE_ACCOUNT_PASSWD_LENGTH_LIMIT(100021, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_PASSWD_LENGTH_LIMIT),
    CODE_ACCOUNT_USERNAME_NOT_NULL(100022, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_USERNAME_NOT_NULL),
    CODE_ACCOUNT_SHOPID_NOT_NULL(100023, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_SHOPID_NOT_NULL),
    CODE_ACCOUNT_PASSWD_ERROR(100024, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_PASSWD_ERROR),
    CODE_ACCOUNT_SHOP_ROLE_INSUFFICIENT(100025, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CODE_ACCOUNT_SHOP_ROLE_INSUFFICIENT),
    CODE_ACCOUNT_SHOP_ROLE_OCCUPY(100026, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CODE_ACCOUNT_SHOP_ROLE_OCCUPY),
    CODE_ACCOUNT_ASSIGNED_ROLE(100027, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CODE_ACCOUNT_ASSIGNED_ROLE),
    CODE_ACCOUNT_SELECT_SHOP(100028, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CODE_ACCOUNT_SELECT_SHOP),
    CODE_ACCOUNT_NEED_PRIVILEGEPASS(100029, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CODE_NEED_PRIVILEGEPASS),
    CODE_ACCOUNT_SYSID_IS_NULL(100030, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CODE_ACCOUNT_SYSID_IS_NULL),
    CODE_ACCOUNT_VERSIN_NO_POWER(10031, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CODE_ACCOUNT_VERSIN_NO_POWER),
    CODE_ACCOUNT_SHOPTYPE_REGISTERED(10032, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_SHOPTYPE_REGISTERED),
    CODE_ACCOUNT_SYTEM_LOGIN_EXPIRED(100033, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ACCOUNT_SYTEM_LOGIN_EXPIRED),
    CODE_ACCOUNT_ENNAME_ISNULL(100034, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CODE_ACCOUNT_ENNAME_ISNULL),
    CODE_ACCOUNT_MOBILE_SAME(100035, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CODE_ACCOUNT_MOBILE_SAME),
    CODE_ACCOUNT_ID_NOT(100036, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CODE_ACCOUNT_ID_NOT),
    CODE_SHOP_EXPIRE(100037, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CODE_SHOP_EXPIRE),
    CODE_SOME_MUST(100038, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CODE_SOME_MUST),


    // 图片
    CODE_IMGAE_UPLOAD_FAILED(110001, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_IMGAE_UPLOAD_FAILED),
    CODE_IMGAE_FORMAT_INVALID(110002, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_IMGAE_FORMAT_INVALID),
    CODE_IMGAE_CROP_FAILED(110003, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_IMGAE_CROP_FAILED),
    CODE_IMGAE_UPLOAD_GT_5M(110004, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_IMGAE_UPLOAD_GT_5M),
    CODE_IMGAE_UPLOAD_EQ_WIDTH(110005, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_IMGAE_UPLOAD_EQ_WIDTH),
    CODE_IMGAE_UPLOAD_EQ_HEIGHT(110006, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_IMGAE_UPLOAD_EQ_HEIGHT),
    MSG_IMGAE_UPLOAD_ET_TOTAL_SIZE(110007, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_IMGAE_UPLOAD_ET_TOTAL_SIZE),

    MSG_IMAGE_CATEGORY_IMGCATID_NOT_NULL(110010, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_IMAGE_CATEGORY_IMGCATID_NOT_NULL),
    MSG_IMAGE_CATEGORY_IMGCATPARENTID_NOT_NULL(110011, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_IMAGE_CATEGORY_IMGCATPARENTID_NOT_NULL),
    MSG_IMAGE_CATEGORY_IMGCATNAME_NOT_NULL(110012, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_IMAGE_CATEGORY_IMGCATNAME_NOT_NULL),

    CODE_VIDEO_UPLOAD_FAILED(110020, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_VIDEO_UPLOAD_FAILED),
    CODE_VIDEO_FORMAT_INVALID(110021, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_VIDEO_FORMAT_INVALID),
    MSG_VIDEO_CATEGORY_VIDEOCATID_NOT_NULL(110022, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_VIDEO_CATEGORY_VIDEOCATID_NOT_NULL),
    MSG_VIDEO_CATEGORY_VIDEOCATPARENTID_NOT_NULL(110023, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_VIDEO_CATEGORY_VIDEOCATPARENTID_NOT_NULL),
    MSG_VIDEO_CATEGORY_VIDEOCATNAME_NOT_NULL(110024, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_VIDEO_CATEGORY_VIDEOCATNAME_NOT_NULL),
    CODE_VIDEO_UPLOAD_GT_10M(110025, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_VIDEO_UPLOAD_GT_10M),

    // 订单
    CODE_ORDER(120001, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER),
    CODE_ORDER_OPERATE_NO_INSTANCEOF(120002, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_OPERATE_NO_INSTANCEOF),
    REFUND_REQUEST_PARAMETER_ERROR(120003, com.vpu.mp.service.foundation.data.JsonResultMessage.REFUND_REQUEST_PARAMETER_ERROR),
    CODE_ORDER_NOT_EXIST(120004, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_NOT_EXIST),
    CODE_ORDER_RETURN_RETURN_SHIPPING_FEE_EXCESS(120005, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_RETURN_SHIPPING_FEE_EXCESS),
    CODE_ORDER_RETURN_RETURN_SHIPPING_FEE_NOT_ZERO(120006, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_RETURN_SHIPPING_FEE_NOT_ZERO),
    REFUND_REQUEST_PARAMETER_TIME_ONE_YEAR(120007, com.vpu.mp.service.foundation.data.JsonResultMessage.REFUND_REQUEST_PARAMETER_TIME_ONE_YEAR),

    CODE_ORDER_RETURN_METHOD_REFLECT_ERROR(120020, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_METHOD_REFLECT_ERROR),
    CODE_ORDER_RETURN_WX_FAIL(120021, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_WX_FAIL),
    CODE_ORDER_RETURN_AFTER_RETURNAMOUNT_GREAT_THAN_ZERO(120022, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_AFTER_RETURNAMOUNT_GREAT_THAN_ZERO),
    CODE_ORDER_RETURNING_RETURN_METHOD_ERROR(120023, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURNING_RETURN_METHOD_ERROR),
    CODE_ORDER_RETURN_MANUAL_INCONSISTENT_AMOUNT(120024, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_MANUAL_INCONSISTENT_AMOUNT),
    CODE_ORDER_FINISH_RETURN_STATUS_ERROR(120025, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_FINISH_RETURN_STATUS_ERROR),
    CODE_ORDER_RETURN_ROLLBACK_NO_MPEXCEPTION(120026, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_ROLLBACK_NO_MPEXCEPTION),
    CODE_ORDER_RETURN_MONEY_EXCEEDED(120027, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_MONEY_EXCEEDED),
    CODE_ORDER_RETURN_STATUS_NOT_SATISFIED(120028, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_STATUS_NOT_SATISFIED),
    CODE_ORDER_RETURN_WXPAYREFUND_NO_RECORD(120029, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_WXPAYREFUND_NO_RECORD),
    CODE_ORDER_RETURN_WXPAYREFUND_ERROR(120030, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_WXPAYREFUND_ERROR),
    CODE_ORDER_RETURN_NOT_SUPPORT_RETURN_TYPE(120031, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_NOT_SUPPORT_RETURN_TYPE),
    CODE_ORDER_RETURN_NO_SELECT_GOODS(120032, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_NO_SELECT_GOODS),
    CODE_ORDER_RETURN_NOT_NULL_RETURNTYPE(120033, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_NOT_NULL_RETURNTYPE),
    CODE_ORDER_RETURN_NOT_NULL_RETURNMONEY(120034, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_NOT_NULL_RETURNMONEY),
    CODE_ORDER_RETURN_NOT_NULL_SHIPPINGFEE(120035, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_NOT_NULL_SHIPPINGFEE),
    CODE_ORDER_RETURN_GOODS_RETURN_COMPLETED(120036, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_GOODS_RETURN_COMPLETED),
    CODE_ORDER_RETURN_GOODS_RETURN_NUMBER_ERROR(120037, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_GOODS_RETURN_NUMBER_ERROR),
    CODE_ORDER_RETURN_GOODS_NO_CAN_RETURN(120039, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_GOODS_NO_CAN_RETURN),
    CODE_ORDER_RETURN_MANUAL_MONEY_ERROR(120040, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_MANUAL_MONEY_ERROR),
    CODE_ORDER_RETURN_OPERATION_NOT_SUPPORTED_BECAUSE_STATUS_ERROR(120041, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_OPERATION_NOT_SUPPORTED_BECAUSE_STATUS_ERROR),
    CODE_ORDER_CANCEL_NOT_CANCEL(120042, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_CANCEL_NOT_CANCEL),
    CODE_ORDER_CANCEL_FAIL(120043, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_CANCEL_FAIL),
    CODE_ORDER_CLOSE_NOT_CLOSE(120044, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_CLOSE_NOT_CLOSE),
    CODE_ORDER_CLOSE_FAIL(120045, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_CLOSE_FAIL),
    CODE_ORDER_RETURN_RETURN_ORDER_NOT_EXIST(120045, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_RETURN_ORDER_NOT_EXIST),
    CODE_ORDER_VERIFY_OPERATION_NOT_SUPPORTED(120046, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_VERIFY_OPERATION_NOT_SUPPORTED),
    CODE_ORDER_VERIFY_CODE_ERROR(120047, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_VERIFY_CODE_ERROR),
    CODE_ORDER_FINISH_OPERATION_NOT_SUPPORTED(120048, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_FINISH_OPERATION_NOT_SUPPORTED),
    CODE_ORDER_RECEIVE_OPERATION_NOT_SUPPORTED(120049, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RECEIVE_OPERATION_NOT_SUPPORTED),
    CODE_ORDER_REMIND_OPERATION_NOT_SUPPORTED(120050, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_REMIND_OPERATION_NOT_SUPPORTED),
    CODE_ORDER_REMIND_OPERATION_LIMIT(120051, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_REMIND_OPERATION_LIMIT),
    CODE_ORDER_REMIND_OPERATION_LIMIT_TODAY(120052, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_REMIND_OPERATION_LIMIT_TODAY),
    CODE_ORDER_EXTEND_RECEIVE_ONLY_ONE(120053, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_EXTEND_RECEIVE_ONLY_ONE),
    CODE_ORDER_EXTEND_RECEIVE_NO_SHIPPED(120054, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_EXTEND_RECEIVE_NO_SHIPPED),
    CODE_ORDER_EXTEND_RECEIVE_NOT_SUPPORTED(120055, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_EXTEND_RECEIVE_NOT_SUPPORTED),
    CODE_ORDER_EXTEND_RECEIVE_TIME_NOT_LT_AUTOTIME(120056, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_EXTEND_RECEIVE_TIME_NOT_LT_AUTOTIME),
    CODE_ORDER_EXTEND_RECEIVE_NOW_AUTOTIME_INTERVAL_GT_TWO_DAYS(120057, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_EXTEND_RECEIVE_NOW_AUTOTIME_INTERVAL_GT_TWO_DAYS),
    CODE_ORDER_EXTEND_RECEIVE_ADMIN_SET_MORE_TIME(120058, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_EXTEND_RECEIVE_ADMIN_SET_MORE_TIME),
    CODE_ORDER_DELETE_OPERATION_NOT_SUPPORTED(120059, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_DELETE_OPERATION_NOT_SUPPORTED),
    CODE_ORDER_RETURN_EXIST_WX_REFUND_FAIL_ORDER(120060, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_RETURN_EXIST_WX_REFUND_FAIL_ORDER),
    /**
     * 下单参数
     */
    /**商品或规格不存在*/
    CODE_ORDER_GOODS_NOT_EXIST(120060, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_GOODS_NOT_EXIST),
    /**{0}商品已下架*/
    CODE_ORDER_GOODS_NO_SALE(120061, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_GOODS_NO_SALE),
    /**{0}商品库存不足*/
    CODE_ORDER_GOODS_OUT_OF_STOCK(120062, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_GOODS_OUT_OF_STOCK),
    /**商品数量为0*/
    CODE_ORDER_GOODS_NO_ZERO(120063, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_GOODS_NO_ZERO),
    /**计算运费时运费模板,未找到可配送的区域*/
    CODE_ORDER_CALCULATE_SHIPPING_FEE_ERROR(120064, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_CALCULATE_SHIPPING_FEE_ERROR),
    /**快递订单必须含有地址信息*/
    CODE_ORDER_ADDRESS_NO_NULL(120065, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_ADDRESS_NO_NULL),
    /**会员卡失效*/
    CODE_ORDER_CARD_INVALID(120066, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_CARD_INVALID),
    /**优惠卷失效*/
    CODE_ORDER_COUPON_INVALID(120067, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_COUPON_INVALID),
    /**积分不足*/
    CODE_ORDER_SCORE_NOT_ENOUGH(120068, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_SCORE_NOT_ENOUGH),
    /**余额不足*/
    CODE_ORDER_ACCOUNT_NOT_ENOUGH(120069, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_ACCOUNT_NOT_ENOUGH),
    /**会员卡余额不足*/
    CODE_ORDER_CARD_NOT_ENOUGH(120070, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_CARD_NOT_ENOUGH),
    /**订单金额变更为{}，是否确认购买*/
    CODE_ORDER_AMOUNT_CHANGE(120071, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_AMOUNT_CHANGE),
    /**积分抵扣金额不能超过*/
    CODE_ORDER_SCORE_LIMIT(120072, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_SCORE_LIMIT),
    /**超出会员卡可兑换数量*/
    CODE_ORDER_MCARD_TP_LIMIT_LIMIT(120073, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_MCARD_TP_LIMIT_LIMIT),
    /**当前配送方式不支持*/
    CODE_ORDER_DELIVER_TYPE_NO_SUPPORT(120074, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_DELIVER_TYPE_NO_SUPPORT),
    /**不支持微信支付*/
    CODE_ORDER_PAY_WAY_NO_SUPPORT_WX(120075, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_PAY_WAY_NO_SUPPORT_WX),
    /**不支持货到付款**/
    CODE_ORDER_PAY_WAY_NO_SUPPORT_COD(120076, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_PAY_WAY_NO_SUPPORT_COD),
    /**不支持余额支付*/
    CODE_ORDER_PAY_WAY_NO_SUPPORT_ACCOUNT(120077, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_PAY_WAY_NO_SUPPORT_ACCOUNT),
    /**不支持积分支付*/
    CODE_ORDER_PAY_WAY_NO_SUPPORT_SCORE(120078, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_PAY_WAY_NO_SUPPORT_SCORE),
    /**不支持会员卡余额支付*/
    CODE_ORDER_PAY_WAY_NO_SUPPORT_CARD(120079, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_PAY_WAY_NO_SUPPORT_CARD),
    /**商品不存在*/
    CODE_ORDER_GOODS_NO_EXIST(120080, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_GOODS_NO_EXIST),
    /**库存不足*/
    CODE_ORDER_GOODS_LOW_STOCK(120081, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_GOODS_LOW_STOCK),
    /**redis锁获取失败（服务忙）*/
    CODE_ORDER_GOODS_GET_LOCK_FAIL(120082, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_GOODS_GET_LOCK_FAIL),
    /**更新库存失败*/
    CODE_ORDER_UPDATE_STOCK_FAIL(120083, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_UPDATE_STOCK_FAIL),
    /**下单数据库执行异常*/
    CODE_ORDER_DATABASE_ERROR(120084, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_DATABASE_ERROR),
    /**{名称}最小限购{数量}*/
    CODE_ORDER_GOODS_LIMIT_MIN(120085, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_GOODS_LIMIT_MIN),
    /**{名称}最大限购{数量}*/
    CODE_ORDER_GOODS_LIMIT_MAX(120086, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_GOODS_LIMIT_MAX),
    /**下单时赠品已经送完,请重新下单*/
    CODE_ORDER_GIFT_GOODS_ZERO(120087, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_GIFT_GOODS_ZERO),
    /**下单必填*/
    CODE_ORDER_MUST_NOT_NULL(120088, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_MUST_NOT_NULL),
    /**不支持好友代付*/
    CODE_ORDER_PAY_WAY_NO_SUPPORT_INSTEAD_PAY(120089, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_PAY_WAY_NO_SUPPORT_INSTEAD_PAY),
    /**代付类型错误*/
    CODE_ORDER_PAY_WAY_NO_SUPPORT_INSTEAD_PAY_WAY(120090, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_PAY_WAY_NO_SUPPORT_INSTEAD_PAY_WAY),
    /**代付金额为0*/
    CODE_ORDER_PAY_WAY_NO_SUPPORT_INSTEAD_PAY_MONEY_ZERO(120091, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_PAY_WAY_NO_SUPPORT_INSTEAD_PAY_MONEY_ZERO),
    /**代付已完成*/
    CODE_ORDER_PAY_WAY_INSTEAD_PAY_FINISH(120092, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_PAY_WAY_INSTEAD_PAY_FINISH),
    /**会员专享商品不可购买*/
    CODE_ORDER_EXCLUSIVE_GOODS_NO_BUY(120089, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_EXCLUSIVE_GOODS_NO_BUY),
    /**为预售商品，不支持现购*/
    CODE_ORDER_PRESALE_GOODS_NOT_SUPORT_BUY(120090, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_PRESALE_GOODS_NOT_SUPORT_BUY),
    /**订单支付金额异常，请检查是否有超付情况*/
    CODE_ORDER_MONEYPAID_ERROR_PLEASE_CHECK(120091, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_MONEYPAID_ERROR_PLEASE_CHECK),
    /**
     * 去支付
     */
    /**该订单非待支付订单*/
    CODE_ORDER_TOPAY_STATUS_NOT_WAIT_PAY(120100, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_TOPAY_STATUS_NOT_WAIT_PAY),
    /**不在尾款支付时间*/
    CODE_ORDER_TOPAY_BK_PAY_NOT_START(120101, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_TOPAY_BK_PAY_NOT_START),
    /**该订单已过期需重新下单*/
    CODE_ORDER_TOPAY_EXPIRED(120102, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_TOPAY_EXPIRED),

    /**
     * 发货
     */
    CODE_ORDER_SHIPPING_SHIPPINGNO_NOT_NULL(120200, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_SHIPPING_SHIPPINGNO_NOT_NULL),
    CODE_ORDER_SHIPPING_SHIPPINGID_NOT_NULL(120201, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_SHIPPING_SHIPPINGID_NOT_NULL),
    CODE_ORDER_SHIPPING_SHIPGOODS_NOT_NULL(120202, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_SHIPPING_SHIPGOODS_NOT_NULL),

    /**
     * 微信支付
     */
    /**订单不可以变为待发货*/
    CODE_ORDER_NOT_TO_WAIT_DELIVER(121000, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_NOT_TO_WAIT_DELIVER),

    /**
     * 下单活动校验
     */
    //活动停用
    CODE_ORDER_ACTIVITY_DISABLE(122001, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_ACTIVITY_DISABLE),
    //活动未开始
    CODE_ORDER_ACTIVITY_NO_START(122002, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_ACTIVITY_NO_START),
    //活动已结束
    CODE_ORDER_ACTIVITY_END(122003, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_ACTIVITY_END),
    //购买数量已达活动上限
    CODE_ORDER_ACTIVITY_NUMBER_LIMIT(122004, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_ACTIVITY_NUMBER_LIMIT),
    //活动库存不足
    CODE_ORDER_ACTIVITY_GOODS_OUT_OF_STOCK(122005, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_ACTIVITY_GOODS_OUT_OF_STOCK),
    //该商品不支持预售
    CODE_ORDER_GOODS_NOT_SUPORT_PRESALE(122011, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_GOODS_NOT_SUPORT_PRESALE),
    //限次卡兑换未选择商品
    CODE_ORDER_CARD_EXCHGE_NO_CHOOSE_GOODS(122012, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_CARD_EXCHGE_NO_CHOOSE_GOODS),
    //限次卡不可兑换商品
    CODE_ORDER_CARD_EXCHGE_NO_EXCHGE_GOODS(122013, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_CARD_EXCHGE_NO_EXCHGE_GOODS),
    //限次卡兑换商品次数超过限制
    CODE_ORDER_CARD_EXCHGE_NUMBER_LIMIT(122014, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_CARD_EXCHGE_NUMBER_LIMIT),

    // 商品
    GOODS_ID_IS_NULL(130001, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_ID_IS_NULL),
    GOODS_NAME_EXIST(130002, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_NAME_EXIST),
    GOODS_NAME_IS_NULL(130003, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_NAME_IS_NULL),
    GOODS_SN_EXIST(130004, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_SN_EXIST),
    GOODS_MAIN_IMG_IS_NULL(130004, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_MAIN_IMG_IS_NULL),
    //商品品牌
    GOODS_BRAND_NAME_EXIST(131001, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_BRAND_NAME_EXIST),
    GOODS_BRAND_NAME_IS_NULL(131002, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_BRAND_NAME_IS_NULL),
    GOODS_BRAND_ID_IS_NULL(131003, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_BRAND_ID_IS_NULL),
    GOODS_BRAND_CALSSIFY_NAME_EXIST(131004, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_BRAND_CALSSIFY_NAME_EXIST),
    GOODS_BRAND_CALSSIFY_NAME_IS_NULL(131005, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_BRAND_ALSSIFY_NAME_IS_NULL),
    GOODS_BRAND_CALSSIFY_ID_IS_NULL(131006, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_BRAND_ALSSIFY_ID_IS_NULL),
    //商品分类
    GOODS_SORT_NAME_EXIST(132001, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_SORT_NAME_EXIST),
    GOODS_SORT_NAME_IS_NULL(132002, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_SORT_NAME_IS_NULL),
    GOODS_SORT_ID_IS_NULL(132003, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_SORT_ID_IS_NULL),
    GOODS_RECOMMEND_SORT_CHILDREN_NOT_NULL(132004, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_RECOMMEND_SORT_CHILDREN_NOT_NULL),
    GOODS_LABEL_NAME_EXIST(133001, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_LABEL_NAME_EXIST),
    GOODS_LABEL_NOT_EXIST(133002, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_LABEL_NOT_EXIST),
    GOODS_LABEL_ID_NOT_NULL(133003, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_LABEL_ID_NOT_NULL),
    GOODS_LABEL_NAME_NOT_NULL(133004, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_LABEL_NAME_NOT_NULL),

    //商品规格
    GOODS_SPEC_PRD_SN_EXIST(135001, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_SPEC_PRD_SN_EXIST),
    GOODS_SPEC_NAME_REPETITION(135002, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_SPEC_NAME_REPETITION),
    GOODS_SPEC_VAL_REPETITION(135003, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_SPEC_VAL_REPETITION),
    GOODS_SPEC_ATTRIBUTE_SPEC_K_V_CONFLICT(135004, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_SPEC_ATTRIBUTE_SPEC_K_V_CONFLICT),
    GOODS_NUM_FETCH_LIMIT_NUM(135005, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_NUM_FETCH_LIMIT_NUM),
    GOODS_PRD_CODES_EXIST(135006, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_PRD_CODES_EXIST),

    GOODS_RECOMMEND_NAME_NOT_NULL(136001, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_RECOMMEND_NAME_NOT_NULL),
    GOODS_RECOMMEND_TYPE_NOT_NULL(136002, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_RECOMMEND_TYPE_NOT_NULL),
    GOODS_RECOMMEND_NAME_EXIST(136003, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_RECOMMEND_NAME_EXIST),
    GOODS_RECOMMEND_ID_NOT_EXIST(136004, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_RECOMMEND_ID_NOT_EXIST),
    GOODS_RECOMMEND_NOT_EXIST(136005, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_RECOMMEND_NOT_EXIST),
    GOODS_RECOMMEND_CHOOSE_TYPE_NOT_NULL(136006, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_RECOMMEND_CHOOSE_TYPE_NOT_NULL),
    GOODS_RECOMMEND_NUMBER_NOT_NULL(136007, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_RECOMMEND_NUMBER_NOT_NULL),
    GOODS_RECOMMEND_NO_RECOMMENDED_GOODS(136008, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_RECOMMEND_NO_RECOMMENDED_GOODS),

    //商品评论
    GOODS_COMMENT_NO_PERMISSION(137001, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_COMMENT_NO_PERMISSION),
    MIN_GOODS_NUM(137002, com.vpu.mp.service.foundation.data.JsonResultMessage.MIN_GOODS_NUM),

    //营销
    DISTRIBUTOR_GROUP_NAME_EXIST(141001, com.vpu.mp.service.foundation.data.JsonResultMessage.DISTRIBUTOR_GROUP_NAME_EXIST),
    /**
     * 营销-好友代付
     */
    INSTEAD_PAY_NOT_SET_PAY_WAY(142001, com.vpu.mp.service.foundation.data.JsonResultMessage.INSTEAD_PAY_NOT_SET_PAY_WAY),
    INSTEAD_PAY_NOT_SET_SINGLE_PAY_MESSAGE(142002, com.vpu.mp.service.foundation.data.JsonResultMessage.INSTEAD_PAY_NOT_SET_SINGLE_PAY_MESSAGE),
    INSTEAD_PAY_SINGLE_PAY_MESSAGE_TOO_LONG(142003, com.vpu.mp.service.foundation.data.JsonResultMessage.INSTEAD_PAY_SINGLE_PAY_MESSAGE_TOO_LONG),
    INSTEAD_PAY_NOT_SET_MULTIPLE_PAY_MESSAGE(142004, com.vpu.mp.service.foundation.data.JsonResultMessage.INSTEAD_PAY_NOT_SET_MULTIPLE_PAY_MESSAGE),
    INSTEAD_PAY_MULTIPLE_PAY_MESSAGE_TOO_LONG(142005, com.vpu.mp.service.foundation.data.JsonResultMessage.INSTEAD_PAY_MULTIPLE_PAY_MESSAGE_TOO_LONG),
    INSTEAD_PAY_NEED_AT_LEAST_THREE_PAY_RATIO(142006, com.vpu.mp.service.foundation.data.JsonResultMessage.INSTEAD_PAY_NEED_AT_LEAST_THREE_PAY_RATIO),
    INSTEAD_PAY_NEED_AT_LEAST_TWO_DOUBLE_PAY_RATIO(142007, com.vpu.mp.service.foundation.data.JsonResultMessage.INSTEAD_PAY_NEED_AT_LEAST_TWO_DOUBLE_PAY_RATIO),
    INSTEAD_PAY_VALUE_OVER_RANGE(142008, com.vpu.mp.service.foundation.data.JsonResultMessage.INSTEAD_PAY_VALUE_OVER_RANGE),
    INSTEAD_PAY_STATUS_IS_NULL(142009, com.vpu.mp.service.foundation.data.JsonResultMessage.INSTEAD_PAY_STATUS_IS_NULL),
    /**
     * 营销-多人拼团
     */
    GROUP_BUY_ADD_ACTIVITY_STOP_STATUS(143001, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_BUY_ADD_ACTIVITY_STOP_STATUS),
    GROUP_BUY_ACTIVITY_GOODS_OVERLAPPING(143002, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_BUY_ACTIVITY_GOODS_OVERLAPPING),
    GROUP_BUY_GROUPID_DOES_NOT_EXIST(143003, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_BUY_GROUPID_DOES_NOT_EXIST),
    GROUP_BUY_ACTIVITY_STATUS_DISABLE(143004, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_BUY_ACTIVITY_STATUS_DISABLE),
    GROUP_BUY_ACTIVITY_STATUS_NOTSTART(143005, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_BUY_ACTIVITY_STATUS_NOTSTART),
    GROUP_BUY_ACTIVITY_STATUS_END(143006, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_BUY_ACTIVITY_STATUS_END),
    GROUP_BUY_ACTIVITY_GROUP_STATUS_CANCEL(143007, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_BUY_ACTIVITY_GROUP_STATUS_CANCEL),
    GROUP_BUY_ACTIVITY_GROUP_OPEN_LIMIT_MAX(143008, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_BUY_ACTIVITY_GROUP_OPEN_LIMIT_MAX),
    GROUP_BUY_ACTIVITY_GROUP_JOIN_LIMIT_MAX(43009, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_BUY_ACTIVITY_GROUP_JOIN_LIMIT_MAX),
    GROUP_BUY_ACTIVITY_GROUP_EMPLOEES_MAX(143010, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_BUY_ACTIVITY_GROUP_EMPLOEES_MAX),
    GROUP_BUY_ACTIVITY_GROUP_JOINING(143011, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_BUY_ACTIVITY_GROUP_JOINING),
    GROUP_BUY_ACTIVITY_GROUP_STOCK_LIMIT(143012, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_BUY_ACTIVITY_GROUP_STOCK_LIMIT),
    GROUP_BUY_ACTIVITY_GROUP_INVENTORY_FAILED(143013, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_BUY_ACTIVITY_GROUP_INVENTORY_FAILED),
    GROUP_BUY_ACTIVITY_GROUP_SUCCESS(143014, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_BUY_ACTIVITY_GROUP_SUCCESS),
    GROUP_BUY_ACTIVITY_GROUP_CANCEL(143015, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_BUY_ACTIVITY_GROUP_CANCEL),
    /**
     * 营销-秒杀
     */
    SECKILL_CONFLICTING_ACT_TIME(144001, com.vpu.mp.service.foundation.data.JsonResultMessage.SECKILL_CONFLICTING_ACT_TIME),
    // 分享有礼
    SHARE_REWARD_COUPON_NUM_LIMIT(145001, com.vpu.mp.service.foundation.data.JsonResultMessage.SHARE_REWARD_COUPON_NUM_LIMIT),
    /**
     * 营销-砍价
     */
    BARGAIN_CONFLICTING_ACT_TIME(146001, com.vpu.mp.service.foundation.data.JsonResultMessage.BARGAIN_CONFLICTING_ACT_TIME),
    BARGAIN_NOT_YET_SUCCESSFUL(146002, com.vpu.mp.service.foundation.data.JsonResultMessage.BARGAIN_NOT_YET_SUCCESSFUL),
    BARGAIN_RECORD_ORDERED(146003, com.vpu.mp.service.foundation.data.JsonResultMessage.BARGAIN_RECORD_ORDERED),
    /**
     *营销-我的奖品
     */
    MY_PRIZE_ACTIVITY_RECEIVED(147003, com.vpu.mp.service.foundation.data.JsonResultMessage.MY_PRIZE_ACTIVITY_RECEIVED),
    MY_PRIZE_ACTIVITY_EXPIRED(147004, com.vpu.mp.service.foundation.data.JsonResultMessage.MY_PRIZE_ACTIVITY_EXPIRED),
    /**拼团抽奖活动 */
    GROUP_DRAW_FAIL(147005, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_DRAW_FAIL),
    ACTIVITY_NOT_EXIST(147006, com.vpu.mp.service.foundation.data.JsonResultMessage.ACTIVITY_NOT_EXIST),
    PRODUCT_NOT_EXIST(147007, com.vpu.mp.service.foundation.data.JsonResultMessage.PRODUCT_NOT_EXIST),
    INFORMATION_NOT_EXIST(147008, com.vpu.mp.service.foundation.data.JsonResultMessage.INFORMATION_NOT_EXIST),
    EVENT_IS_OVER(147009, com.vpu.mp.service.foundation.data.JsonResultMessage.EVENT_IS_OVER),
    EVENT_NOT_STARTED(147010, com.vpu.mp.service.foundation.data.JsonResultMessage.EVENT_NOT_STARTED),
    HAVE_UNPAID_ORDERS(147011, com.vpu.mp.service.foundation.data.JsonResultMessage.HAVE_UNPAID_ORDERS),
    PARTICIPATED_IN_EVENT(147012, com.vpu.mp.service.foundation.data.JsonResultMessage.PARTICIPATED_IN_EVENT),
    PARTICIPANTS_IS_MAX(147013, com.vpu.mp.service.foundation.data.JsonResultMessage.PARTICIPANTS_IS_MAX),
    GROUP_UPPER_LIMIT(147014, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_UPPER_LIMIT),
    GROUP_ONLY_ONE(147015, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_ONLY_ONE),

    /**
     * 幸运大抽奖
     */
    LOTTERY_ACTIVITY_FAIL(148001, com.vpu.mp.service.foundation.data.JsonResultMessage.LOTTERY_ACTIVITY_FAIL),
    LOTTERY_ACTIVITY_STOP(148002, com.vpu.mp.service.foundation.data.JsonResultMessage.LOTTERY_ACTIVITY_STOP),
    LOTTERY_ACTIVITY_NOT_BEGIN(148003, com.vpu.mp.service.foundation.data.JsonResultMessage.LOTTERY_ACTIVITY_NOT_BEGIN),
    LOTTERY_ACTIVITY_OUT_DATE(148004, com.vpu.mp.service.foundation.data.JsonResultMessage.LOTTERY_ACTIVITY_OUT_DATE),
    LOTTERY_SHARE_TIME_USE_UP(148005, com.vpu.mp.service.foundation.data.JsonResultMessage.LOTTERY_SHARE_TIME_USE_UP),
    LOTTERY_SCORE_LESS(148006, com.vpu.mp.service.foundation.data.JsonResultMessage.LOTTERY_SCORE_LESS),
    LOTTERY_SCORE_TIME_USE_UP(148007, com.vpu.mp.service.foundation.data.JsonResultMessage.LOTTERY_SCORE_TIME_USE_UP),
    LOTTERY_TIME_USE_UP(148008, com.vpu.mp.service.foundation.data.JsonResultMessage.LOTTERY_TIME_USE_UP),
    LOTTERY_SHARE_TIME_USE_UP_TIME(148009, com.vpu.mp.service.foundation.data.JsonResultMessage.LOTTERY_SHARE_TIME_USE_UP_TIME),

    /**
     * 瓜分积分
     */
    GROUP_INTEGRATION_INTE(148101, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_INTEGRATION_INTE),
    GROUP_INTEGRATION_TOTAL(148102, com.vpu.mp.service.foundation.data.JsonResultMessage.GROUP_INTEGRATION_TOTAL),

    /**
     * 营销-好友助力
     */
    FRIEND_PROMOTE_FAIL(149001, com.vpu.mp.service.foundation.data.JsonResultMessage.FRIEND_PROMOTE_FAIL),


    /**
     * 表单统计-反馈列表导出
     */
    FORM_FEED_FILE_NAME(149001, com.vpu.mp.service.foundation.data.JsonResultMessage.FORM_FEED_FILE_NAME),
    FORM_FEED_NICKNAME(149002, com.vpu.mp.service.foundation.data.JsonResultMessage.FORM_FEED_NICKNAME),
    FORM_FEED_MOBILE(149003, com.vpu.mp.service.foundation.data.JsonResultMessage.FORM_FEED_MOBILE),
    FORM_FEED_CREATE_TIME(149004, com.vpu.mp.service.foundation.data.JsonResultMessage.FORM_FEED_CREATE_TIME),
    FORM_STATISTICS_INEXISTENCE(149005, com.vpu.mp.service.foundation.data.JsonResultMessage.FORM_STATISTICS_INEXISTENCE),
    FORM_STATISTICS_UNPUBLISHED(149006, com.vpu.mp.service.foundation.data.JsonResultMessage.FORM_STATISTICS_UNPUBLISHED),
    FORM_STATISTICS_NOT_STARTED(149007, com.vpu.mp.service.foundation.data.JsonResultMessage.FORM_STATISTICS_NOT_STARTED),
    FORM_STATISTICS_EXPIRED(149008, com.vpu.mp.service.foundation.data.JsonResultMessage.FORM_STATISTICS_EXPIRED),
    FORM_STATISTICS_FAIL_SUBMIT_LIMIT(149009, com.vpu.mp.service.foundation.data.JsonResultMessage.FORM_STATISTICS_FAIL_SUBMIT_LIMIT),
    FORM_STATISTICS_DAY_SUBMIT_LIMIT(149010, com.vpu.mp.service.foundation.data.JsonResultMessage.FORM_STATISTICS_DAY_SUBMIT_LIMIT),
    FORM_STATISTICS_DELETE(149010, com.vpu.mp.service.foundation.data.JsonResultMessage.FORM_STATISTICS_DELETE),
    FORM_STATISTICS_CLOSE(149010, com.vpu.mp.service.foundation.data.JsonResultMessage.FORM_STATISTICS_CLOSE),
	/**
	 * 首单特惠
	 */
	FIRST_SPECIAL_NUMBER_LIMIT(140101, com.vpu.mp.service.foundation.data.JsonResultMessage.FIRST_SPECIAL_NUMBER_LIMIT),
	FIRST_SPECIAL_KIND_LIMIT(140101, com.vpu.mp.service.foundation.data.JsonResultMessage.FIRST_SPECIAL_KIND_LIMIT),

    /**
     * 消息推送
     */
    MESSAGE_TEMPLATE_NO_OPEN(140201, com.vpu.mp.service.foundation.data.JsonResultMessage.MESSAGE_TEMPLATE_NO_OPEN),

    /**
     * 打包一口价
     */

    PACKAGE_SALE_RULE_CHANGED(140301, com.vpu.mp.service.foundation.data.JsonResultMessage.PACKAGE_SALE_RULE_CHANGED),

    //预售
    PRESALE_CONFLICTING_ACT_TIME(140401, com.vpu.mp.service.foundation.data.JsonResultMessage.PRESALE_CONFLICTING_ACT_TIME),

    //团长申请已提交
    COMMUNITY_GROUP_HANDLER_APPLICATION_SUBMITTED(140501, com.vpu.mp.service.foundation.data.JsonResultMessage.COMMUNITY_GROUP_HANDLER_APPLICATION_SUBMITTED),


    //会员用户
    CODE_MEMBER_TAG_ADD_SUCCESS(150000, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_TAG_ADD_SUCCESS),
    CODE_MEMBER_TAG_NOT_LIMIT(15001, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_TAG_LENGTH_LIMIT),
    CODE_MEMBER_TAG_NOT_NULL(15002, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_TAG_NOT_NULL),
    CODE_MEMBER_TAG_NAME_EXIST(15003, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_TAG_NAME_EXIST),
    CODE_MEMBER_TAG_ID_NOT_NULL(15004, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_TAG_ID_NOT_NULL),
    CODE_MEMEBER_NOT_EXIST(15005, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_NOT_EXIST),
    // 会员积分
    CODE_MEMBER_SCORE_NOT_SAME(15006, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_SCORE_NOT_SAME),
    CODE_MEMBER_SCORE_ERROR(15007, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_SCORE_ERROR),
    CODE_MEMBER_SCORE_NOT_ENOUGH(15008, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_SCORE_NOT_ENOUGH),
    CODE_MEMBER_SCORE_NOT_NULL(15009, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_SCORE_NOT_NULL),
    MSG_MEMBER_SCORE_NOT_BE_NEGATIVE(15010, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_SCORE_NOT_BE_NEGATIVE),
    CODE_MEMBER_SCORE_EXPIRED(15011, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_SCORE_EXPIRED),
    // 会员余额
    CODE_MEMBER_ACCOUNT_UPDATE_FAIL(15101, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_ACCOUNT_UPDATE_FAIL),
    CODE_MEMBER_CARD_ACCOUNT_UPDATE_FAIL(15102, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_CARD_ACCOUNT_UPDATE_FAIL),
    CODE_MEMBER_CARD_SURPLUS_UPDATE_FAIL(15103, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_CARD_SURPLUS_UPDATE_FAIL),
    CODE_MEMBER_CARD_EXCHANGSURPLUS_UPDATE_FAIL(15104, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_CARD_EXCHANGSURPLUS_UPDATE_FAIL),
    //会员卡
    CODE_MEMBER_CARD_RIGHTS_EMPTY(15201, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_CARD_RIGHTS_EMPTY),
    CODE_MEMBER_CARD_ID_EMPTY(15202, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_CARD_ID_EMPTY),
    CODE_CARD_ACTIVATE_SUCCESS(15203, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_ACTIVATE_SUCCESS),
    CODE_CARD_ACTIVATE_FAIL(15204, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_ACTIVATE_FAIL),

    // 用户卡
    CODE_USER_CARD_NONE(15205, com.vpu.mp.service.foundation.data.JsonResultMessage.USER_CARD_NONE),
    CODE_MEMBER_CARD_DELETE(15206, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_MEMBER_CARD_DELETE),
    CODE_LIMIT_CARD_AVAIL_SEND_NONE(15207, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_LIMIT_CARD_AVAIL_SEND_NONE),
    CODE_LIMIT_CARD_AVAIL_SEND_ALL(15210, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_LIMIT_CARD_AVAIL_SEND_ALL),
    CODE_CARD_SEND_REPEAT(15208, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_SEND_REPEAT),
    CODE_CARD_GRADE_NONE(15209, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_GRADE_NONE),
    CODE_CARD_RECEIVE_FAIL(15211, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_RECEIVE_FAIL),
    CODE_CARD_RECEIVE_INVALID(15212, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_RECEIVE_INVALID),
    CODE_CARD_RECEIVE_NOCODE(15213, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_RECEIVE_NOCODE),
    CODE_CARD_RECEIVE_GENERATE(15214, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_RECEIVE_GENERATE),
    CODE_CARD_RECEIVE_ALREADYHAS(15215, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_RECEIVE_ALREADYHAS),
    CODE_CARD_RECEIVE_PWD(15216, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_RECEIVE_PWD),
    CODE_CARD_RECEIVE_GOTOLOOK(15217, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_RECEIVE_GOTOLOOK),
    CODE_CARD_RECEIVE_VALIDPWD(15218, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_RECEIVE_VALIDPWD),
    CODE_CARD_RECEIVE_VALIDCODE(15219, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_RECEIVE_VALIDCODE),
    CODE_CARD_NO(15220, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_CARD_NO),
    CODE_EXPLAIN_MUST(15221, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_EXPLAIN_MUST),
    CODE_NEED_ONE(15222, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_NEED_ONE),
    CODE_EXCEL_ERRO(15223, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_EXCEL_ERRO),
    CODE_EXCEL_EXAMPLE_USERNAME(15224, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_EXCEL_EXAMPLE_USERNAME),
    CODE_EXCEL_EXAMPLE_SEX(15225, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_EXCEL_EXAMPLE_SEX),
    CODE_EXCEL_EXAMPLE_PROVINCE(15226, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_EXCEL_EXAMPLE_PROVINCE),
    CODE_EXCEL_EXAMPLE_CITY(15227, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_EXCEL_EXAMPLE_CITY),
    CODE_EXCEL_EXAMPLE_DISTRICT(15228, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_EXCEL_EXAMPLE_DISTRICT),
    CODE_EXCEL_EXAMPLE_ADDRESS(15229, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_EXCEL_EXAMPLE_ADDRESS),
    CODE_EXCEL_EXAMPLE_MARRIAGE(15230, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_EXCEL_EXAMPLE_MARRIAGE),
    CODE_EXCEL_NEED_MOBILE(15231, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_EXCEL_NEED_MOBILE),
    CODE_EXCEL_SORRY(15232, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_EXCEL_SORRY),
    CODE_EXCEL_OK(15233, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_EXCEL_OK),
    CODE_EXCEL_READ_ERRO(15224, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_EXCEL_READ_ERRO),
    CODE_EXCEL_NUM_MAX(15225, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_EXCEL_NUM_MAX),
    CODE_EXCEL_NUM_MIN(15226, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_EXCEL_NUM_MIN),
    CODE_EXCEL_HAVE_SAME(15227, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_EXCEL_HAVE_SAME),
    CODE_CARD_RECEIVE_VALIDLINK(15228, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_CARD_RECEIVE_VALIDLINK),
    CODE_CARD_RECEIVE_BYSELF(15229, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_CARD_RECEIVE_BYSELF),
    MSG_CARD_BEFORE_START_TIME(15230, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_BEFORE_START_TIME),
    MSG_CARD_NOT_ACTIVE(15231, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_NOT_ACTIVE),
    MSG_CARD_ALREADY_EXPIRED(15232, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_ALREADY_EXPIRED),
    MSG_CARD_NOT_SUPPORT_GOODS(15233, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_NOT_SUPPORT_GOODS),
    MSG_CARD_ALLOW_TIME_OVER(15234, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_ALLOW_TIME_OVER),
    MSG_CARD_ADD_TIMES_OVER(15235, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_ADD_TIMES_OVER),
    MSG_CARD_PERIOD_ADD_TIMES_OVER(15236, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_PERIOD_ADD_TIMES_OVER),
    MSG_CARD_HAVE_RECEIVED(15237, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_HAVE_RECEIVED),
    MSG_CARD_NO_RECEIVED(15238, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_NO_RECEIVED),
    MSG_CARD_NORMAL(15239, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_NORMAL),
    MSG_CARD_NO_ABOLISHED(15240, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_NO_ABOLISHED),
    MSG_CARD_ALLOW_TIME_OVER_ALIAS(15241, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_ALLOW_TIME_OVER_ALIAS),
    MSG_CARD_ADD_TIMES_OVER_ALIAS(15242, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_ADD_TIMES_OVER_ALIAS),
    MSG_CARD_PERIOD_ADD_TIMES_OVER_ALIAS(15243, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_PERIOD_ADD_TIMES_OVER_ALIAS),
    MSG_CARD_EXAMINE_ING(15244, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_EXAMINE_ING),
    MSG_CARD_EXAMINE_PASS(15245, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_EXAMINE_PASS),
    MSG_CARD_EXAMINE_REFUSE(15246, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_EXAMINE_REFUSE),
    MSG_CARD_EXAMINE_SUBMIT_SUCCESS(0, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_EXAMINE_SUBMIT_SUCCESS),
    MSG_CARD_EXAMINE_AUTO_SUCCESS(0, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_EXAMINE_AUTO_SUCCESS),
    MSG_CARD_NOT_EXCHANG_GOODS(15247, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_CARD_NOT_EXCHANG_GOODS),
    //用户-地址,
    USER_ADDRESS_COUNT_MORE_THAN_MAX(153001, com.vpu.mp.service.foundation.data.JsonResultMessage.USER_ADDRESS_COUNT_MORE_THAN_MAX),
    USER_INVITED_MSG(153002, com.vpu.mp.service.foundation.data.JsonResultMessage.USER_INVITED_MSG),
    USER_INVITED_NOT_EACH_OTHER(153003, com.vpu.mp.service.foundation.data.JsonResultMessage.USER_INVITED_NOT_EACH_OTHER),
    //文章_分类
    CODE_ARTICLE_CATEGORY_IS_EXIST(16001, com.vpu.mp.service.foundation.data.JsonResultMessage.ARTICLE_CATEGORY_IS_EXIST),
    CODE_ARTICLE_CATEGORY_CATEGORYNAME_ISNULL(16001, com.vpu.mp.service.foundation.data.JsonResultMessage.ARTICLE_CATEGORY_CATEGORYNAME_ISNULL),
    CODE_ARTICLE_CATEGORY_CATEGORYID_ISNULL(16003, com.vpu.mp.service.foundation.data.JsonResultMessage.ARTICLE_CATEGORY_CATEGORYID_ISNULL),
    CODE_ARTICLE_TITLE_ISNULL(16004, com.vpu.mp.service.foundation.data.JsonResultMessage.ARTICLE_TITLE_ISNULL),
    CODE_ARTICLE_ARTICLEID_ISNULL(16005, com.vpu.mp.service.foundation.data.JsonResultMessage.ARTICLE_ARTICLEID_ISNULL),
    CODE_ARTICLE_CATEGORY_UPDATE_FAILED(16006, com.vpu.mp.service.foundation.data.JsonResultMessage.ARTICLE_CATEGORY_UPDATE_FAILED),

    //小程序管理
    CODE_PAGE_CLASSIFICATION_EXIST(170001, com.vpu.mp.service.foundation.data.JsonResultMessage.PAGE_CLASSIFICAIION_EXIST),
    CODE_PAGE_CLASSIFICATION_INSERT_FAILED(170002, com.vpu.mp.service.foundation.data.JsonResultMessage.PAGE_CLASSIFICAIION_INSERT_FAILED),
    CODE_PAGE_CLASSIFICATION_NOT_EXIST(170003, com.vpu.mp.service.foundation.data.JsonResultMessage.PAGE_CLASSIFICAIION_NOT_EXIST),
    CODE_PAGE_CLASSIFICATION_UPDATE_FAILED(170004, com.vpu.mp.service.foundation.data.JsonResultMessage.PAGE_CLASSIFICATION_UPDATE_FAILED),
    CODE_PAGE_CLASSIFICATION_DELETE_FAILED(170005, com.vpu.mp.service.foundation.data.JsonResultMessage.PAGE_CLASSIFICATION_DELETE_FAILED),
    CODE_APPLET_QR_CODE_GET_FAILED(170006, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_APPLET_QR_CODE_GET_FAILED),

    DECORATE_BOTTOM_ISNOTJSON(170006, com.vpu.mp.service.foundation.data.JsonResultMessage.DECORATE_BOTTOM_ISNOTJSON),
    DECORATE_STYLE_ISNOTJSON(170007, com.vpu.mp.service.foundation.data.JsonResultMessage.DECORATE_STYLE_ISNOTJSON),
    DECORATE_STYLE_PARAM_ID_NULL(170008, com.vpu.mp.service.foundation.data.JsonResultMessage.DECORATE_STYLE_PARAM_UPDATE_ID_NULL),
    DECORATE_STYLE_PARAM_VALUE_NULL(170009, com.vpu.mp.service.foundation.data.JsonResultMessage.DECORATE_STYLE_PARAM_UPDATE_VALUE_NULL),
    DECORATE_URL_ILLEGAL(170010, com.vpu.mp.service.foundation.data.JsonResultMessage.DECORATE_URL_ILLEGAL),

    WX_BINDING_MINI_NO_SAME(170011, com.vpu.mp.service.foundation.data.JsonResultMessage.BINDING_MINI_NO_SAME),
    WX_BINDING_MINI_HAVEBIND(170012, com.vpu.mp.service.foundation.data.JsonResultMessage.BINDING_MINI_HAVEBIND),

    WX_MA_APP_ID_NOT_AUTH(170013, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_MA_APP_ID_NOT_AUTH),
    WX_MA_TEMPLATE_ID_NOT_NULL(170014, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_MA_TEMPLATE_ID_NOT_NULL),
    WX_MA_PACKAGE_VERSION_NOT_NULL(170015, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_MA_PACKAGE_VERSION_NOT_NULL),
    WX_MA_NEED_AUTHORIZATION(170016, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_MA_NEED_AUTHORIZATION),

    WX_ERROR_EXCEPTION(170017, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_ERROR_EXCEPTION),
    WX_MA_NEED_UPLOADCODE(170018, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_MA_NEED_UPLOADCODE),
    WX_MA_NEED_AUDITING_CODE_SUCCESS(170019, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_MA_NEED_AUDITING_CODE_SUCCESS),
    WX_MA_SHOP_HAS_NO_AP(170020, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_MA_SHOP_HAS_NO_APP),
    WX_MA_FEATURE_NOT_OPEN(170021, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_MA_FEATURE_NOT_OPEN),
    WX_MA_ISSUBMERCHANT_ISNULL(170022, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_MA_ISSUBMERCHANT_ISNULL),
    WX_MA_TABLE_ISNULL(170023, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_MA_TABLE_ISNULL),
    WX_MP_NO_ACCESS(170024, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_MP_NO_ACCESS),
    WX_MA_HAVE_MP(170025, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_MA_HAVE_MP),
    WX_MP_NEED_CHOOSE_RIGHT(170026, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_MP_NEED_CHOOSE_RIGHT),
    WX_NO_REQUIRED(170027, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_NO_REQUIRED),
    WX_ONLY_ONE(170028, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_ONLY_ONE),
    WX_JOB_PROBLEM(170029, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_JOB_PROBLEM),

    WX_9300529(170030, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_9300529),
    WX_9300530(170031, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_9300530),
    WX_9300531(170032, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_9300531),
    WX_9300532(170033, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_9300532),
    SEARCHCFG_HOTWORDS_LIMIT(170034, com.vpu.mp.service.foundation.data.JsonResultMessage.SEARCHCFG_HOTWORDS_LIMIT),
    SEARCHCFG_TITLECUSTOM_NOTNULL(170035, com.vpu.mp.service.foundation.data.JsonResultMessage.SEARCHCFG_TITLECUSTOM_NOTNULL),
    WX_GETQRCODE_FAIL(170036, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_GETQRCODE_FAIL),
    WX_READQRCODE_FAIL(170037, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_READQRCODE_FAIL),
    WX_GETHEAD_FAIL(170038, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_GETHEAD_FAIL),
    WX_GETBG_FAIL(170039, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_GETBG_FAIL),
    WX_SHARESHOP(170040, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_SHARESHOP),
    WX_SCAN_QRSHOP(170041, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_SCAN_QRSHOP),
    WX_NO_LIVE_POWER(170042, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_NO_LIVE_POWER),
    WX_NO_LIVE_PLUS(170043, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_NO_LIVE_PLUS),
    WX_NO_TEMPID(170044, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_NO_TEMPID),
    WX_HAVE_AUDIT_SLIP(170045, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_HAVE_AUDIT_SLIP),
    WX_TEMPSAME(170046, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_TEMPSAME),
    WX_AUDIT_ING(170047, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_AUDIT_ING),
    /**
     * 小程序跳转链接
     */
    APPLETS_JUMP_LINK_REPETITION(171001, com.vpu.mp.service.foundation.data.JsonResultMessage.APPLETS_JUMP_LINK_REPETITION),
    APPLETS_JUMP_LINK_APPLY_ALL_AVAILABLE(171002, com.vpu.mp.service.foundation.data.JsonResultMessage.APPLETS_JUMP_LINK_APPLY_ALL_AVAILABLE),
    APPLETS_JUMP_LINK_APPLY_NO_REPETITION(171003, com.vpu.mp.service.foundation.data.JsonResultMessage.APPLETS_JUMP_LINK_APPLY_NO_REPETITION),
    APPLETS_JUMP_LINK_APPLY_SUCCESS(171004, com.vpu.mp.service.foundation.data.JsonResultMessage.APPLETS_JUMP_LINK_APPLY_SUCCESS),

    //门店管理
    CODE_STORE_GROUP_NAME_EXIST(180001, com.vpu.mp.service.foundation.data.JsonResultMessage.STORE_GROUP_NAME_EXIST),
    CODE_POS_SHOP_ID_EXIST(180002, com.vpu.mp.service.foundation.data.JsonResultMessage.STORE_POS_SHOP_ID_EXIST),
    CODE_DATA_NOT_EXIST(180003, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_DATA_NOT_EXIST),
    CODE_STORE_NOT_EXIST(180011, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_STORE_NOT_EXIST),
    CODE_STORE_SERVICE_NOT_EXIST(180012, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_STORE_SERVICE_NOT_EXIST),
    CODE_JACKSON_DESERIALIZATION_FAILED(180004, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_JACKSON_DESERIALIZATION_FAILED),
    CODE_JACKSON_SERIALIZATION_FAILED(180005, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_JACKSON_SERIALIZATION_FAILED),
    CODE_STORE_ALREADY_DEL(180006, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_STORE_ALREADY_DEL),
    CODE_USER_CARD_BALANCE_INSUFFICIENT(180007, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_USER_CARD_BALANCE_INSUFFICIENT),
    CODE_SCORE_INSUFFICIENT(180008, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_SCORE_INSUFFICIENT),
    CODE_BALANCE_INSUFFICIENT(180009, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_BALANCE_INSUFFICIENT),
    CODE_AMOUNT_PAYABLE_CALCULATION_FAILED(180010, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_AMOUNT_PAYABLE_CALCULATION_FAILED),
    CODE_DB_DATA_ABNORMAL(180013, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_DB_DATA_ABNORMAL),
    CODE_RESERVATION_UPPER_LIMIT(180014, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_RESERVATION_UPPER_LIMIT),
    CODE_DATA_ALREADY_EXIST(180015, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_DATA_ALREADY_EXIST),
    CODE_ORDER_AMOUNT_NOT_EQUALS_SERVICE_SUBSIST(180016, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_ORDER_AMOUNT_NOT_EQUALS_SERVICE_SUBSIST),
    CODE_WX_LOGISTICS_API_CALL_FAILED(180017, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_WX_LOGISTICS_API_CALL_FAILED),
    CODE_STORE_PAY_LOWER_SCORE_DOWN_CONFIG(180018, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_STORE_PAY_LOWER_SCORE_DOWN_CONFIG),
    CODE_STORE_PAY_HIGHER_SCORE_UP_CONFIG(180019, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_STORE_PAY_HIGHER_SCORE_UP_CONFIG),
    CODE_STORE_NEED_HAVE(180020, com.vpu.mp.service.foundation.data.JsonResultMessage.MSF_STORE_NEED_HAVE),

    /**
     * 售后管理
     */
    STORE_STORE_ID_NULL(181001, com.vpu.mp.service.foundation.data.JsonResultMessage.STORE_STORE_ID_NULL),
    STORE_TECHNICIAN_NAME_NULL(181002, com.vpu.mp.service.foundation.data.JsonResultMessage.STORE_TECHNICIAN_NAME_NULL),
    STORE_TECHNICIAN_TELEPHONE_ILLEGAL(181003, com.vpu.mp.service.foundation.data.JsonResultMessage.STORE_TECHNICIAN_TELEPHONE_ILLEGAL),
    STORE_TECHNICIAN_TELEPHONE_NULL(181004, com.vpu.mp.service.foundation.data.JsonResultMessage.STORE_TECHNICIAN_TELEPHONE_NULL),

    //门店预约
    CODE_SERVICE_ORDER_VERIFY_CODE_ERROR(182001, com.vpu.mp.service.foundation.data.JsonResultMessage.SERVICE_ORDER_VERIFY_CODE_ERROR),
    CODE_SERVICE_ORDER_VERIFY_BALANCE_IS_NULL(182002, com.vpu.mp.service.foundation.data.JsonResultMessage.SERVICE_ORDER_VERIFY_BALANCE_IS_NULL),
    CODE_SERVICE_ORDER_VERIFY_REASON_IS_NULL(182003, com.vpu.mp.service.foundation.data.JsonResultMessage.SERVICE_ORDER_VERIFY_REASON_IS_NULL),
    CODE_SERVICE_ORDER_VERIFY_INSUFFICIENT_BALANCEL(182004, com.vpu.mp.service.foundation.data.JsonResultMessage.SERVICE_ORDER_VERIFY_INSUFFICIENT_BALANCE),
    CODE_SERVICE_ORDER_CANCEL_REASON_IS_NULL(182005, com.vpu.mp.service.foundation.data.JsonResultMessage.SERVICE_ORDER_CANCEL_REASON_IS_NULL),
    CODE_SERVICE_ORDER_TECHNICIAN_IS_NULL(182006, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_SERVICE_ORDER_TECHNICIAN_IS_NULL),
    CODE_SERVICE_ORDER_TECHNICIAN_NO_SCHEDULE(182007, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_SERVICE_ORDER_TECHNICIAN_NO_SCHEDULE),
    CODE_SERVICE_ORDER_WRONG_SERVICE_DATE(182008, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_SERVICE_ORDER_WRONG_SERVICE_DATE),

    //基础配置
    CODE_CONFIG_PLEDGE_EXCEED(190001, com.vpu.mp.service.foundation.data.JsonResultMessage.CONFIG_PLEDGE_EXCEED),
    CODE_CONFIG_PLEDGE_NAME_NULL(190002, com.vpu.mp.service.foundation.data.JsonResultMessage.CONFIG_PLEDGE_NAME_NULL),
    CODE_CONFIG_PLEDGE_NAME_LENGTH(190003, com.vpu.mp.service.foundation.data.JsonResultMessage.CONFIG_PLEDGE_NAME_LENGTH),
    CODE_CONFIG_PLEDGE_CONTENT_NULL(190004, com.vpu.mp.service.foundation.data.JsonResultMessage.CONFIG_PLEDGE_CONTENT_NULL),
    CODE_CONFIG_PLEDGE_CONTENT_LENGTH(190005, com.vpu.mp.service.foundation.data.JsonResultMessage.CONFIG_PLEDGE_CONTENT_LENGTH),
    CODE_CONFIG_PLEDGE_LOGO_NULL(190006, com.vpu.mp.service.foundation.data.JsonResultMessage.CONFIG_PLEDGE_LOGO_NULL),
    AUTH_SHOP_NOT_EXIST(190007, com.vpu.mp.service.foundation.data.JsonResultMessage.AUTH_SHOP_NOT_EXIST),
    ORDER_PROCESS_CONFIG_UDPATE_FAILED(190008, com.vpu.mp.service.foundation.data.JsonResultMessage.ORDER_PROCESS_CONFIG_UDPATE_FAILED),
    WECAHT_PAY_CONFIG_UPDATE_DAILED(190009, com.vpu.mp.service.foundation.data.JsonResultMessage.WECAHT_PAY_CONFIG_UPDATE_DAILED),
    RETURN_CONFIG_UPDATE_FAILED(190010, com.vpu.mp.service.foundation.data.JsonResultMessage.RETURN_CONFIG_UPDATE_FAILED),
    PAYMENT_CONFIG_IS_NULL(190011, com.vpu.mp.service.foundation.data.JsonResultMessage.PAYMENT_CONFIG_IS_NULL),
    ORDER_PROCESS_CONFIG_IS_NULL(190012, com.vpu.mp.service.foundation.data.JsonResultMessage.ORDER_PROCESS_CONFIG_IS_NULL),
    RETURN_CONFIG_SELECT_FAILED(190013, com.vpu.mp.service.foundation.data.JsonResultMessage.RETURN_CONFIG_SELECT_FAILED),
    WXPAY_CONFIG_IS_NULL(190014, com.vpu.mp.service.foundation.data.JsonResultMessage.WXPAY_CONFIG_IS_NULL),
    CODE_CONFIG_A_NUM_GREATER(190015, com.vpu.mp.service.foundation.data.JsonResultMessage.CONFIG_A_NUM_GREATER),
    CODE_CONFIG_B_NUM_GREATER(190016, com.vpu.mp.service.foundation.data.JsonResultMessage.CONFIG_B_NUM_GREATER),
    CODE_CONFIG_UPDATE_FAILED(190017, com.vpu.mp.service.foundation.data.JsonResultMessage.CODE_CONFIG_UPDATE_FAILED),

    //概览
    OVERVIEW_MALL_DATADEMONSTRATION_GET_FAILED(200001, com.vpu.mp.service.foundation.data.JsonResultMessage.OVERVIEW_MALL_DATADEMONSTRATION_GET_FAILED),
    OVERVIEW_MALL_BING_UNBING_FAILED(200002, com.vpu.mp.service.foundation.data.JsonResultMessage.OVERVIEW_MALL_BING_UNBING_FAILED),
    OVERVIEW_MALL_TODOITEM_GET_FAILED(200003, com.vpu.mp.service.foundation.data.JsonResultMessage.OVERVIEW_MALL_TODOITEM_GET_FAILED),
    OVERVIEW_USER_ANALYSIS_RFM_NULL(200004, com.vpu.mp.service.foundation.data.JsonResultMessage.OVERVIEW_USER_ANALYSIS_RFM_NULL),
    OVERVIEW_YESTERDAY_ANALYSIS_DATA_NULL(200005, com.vpu.mp.service.foundation.data.JsonResultMessage.OVERVIEW_YESTERDAY_ANALYSIS_DATA_NULL),
    SOME_NO_AUTH(200006, com.vpu.mp.service.foundation.data.JsonResultMessage.SOME_NO_AUTH),

    //WxAppCode
    ERR_CODE_INVALID_SIGN(400001, com.vpu.mp.service.foundation.data.JsonResultMessage.ERR_CODE_INVALID_SIGN),
    ERR_CODE_TOKEN_ERROR(400002, com.vpu.mp.service.foundation.data.JsonResultMessage.ERR_CODE_TOKEN_ERROR),
    ERR_CODE_EXCEPTION(400003, com.vpu.mp.service.foundation.data.JsonResultMessage.ERR_CODE_EXCEPTION),
    ERR_CODE_OPERATION_FAILED(400004, com.vpu.mp.service.foundation.data.JsonResultMessage.ERR_CODE_OPERATION_FAILED),
    ERR_CODE_LOGIN_FAILED(400005, com.vpu.mp.service.foundation.data.JsonResultMessage.ERR_CODE_LOGIN_FAILED),
    ERR_CODE_PAY_FAILED(400006, com.vpu.mp.service.foundation.data.JsonResultMessage.ERR_CODE_PAY_FAILED),
    ERR_CODE_CODE_SING(2, com.vpu.mp.service.foundation.data.JsonResultMessage.ERR_CODE_CODE_SING),
    ERR_CODE_HAVE_SING(2, com.vpu.mp.service.foundation.data.JsonResultMessage.ERR_CODE_HAVE_SING),
    ERR_CODE_CODE_SING_ERRO(2, com.vpu.mp.service.foundation.data.JsonResultMessage.ERR_CODE_CODE_SING_ERRO),

    // 小程序-购物车401
    CODE_CART_GOODS_NO_LONGER_VALID(401001, com.vpu.mp.service.foundation.data.JsonResultMessage.CART_GOODS_NO_LONGER_VALID),
    CODE_CART_THERE_IS_STILL_INVENTORY(401002, com.vpu.mp.service.foundation.data.JsonResultMessage.CART_THERE_IS_STILL_INVENTORY),
    CODE_CART_MINIMUM_PURCHASE(401003, com.vpu.mp.service.foundation.data.JsonResultMessage.CART_MINIMUM_PURCHASE),
    CODE_CART_MAXIMUM_PURCHASE(401004, com.vpu.mp.service.foundation.data.JsonResultMessage.CART_MAXIMUM_PURCHASE),
    CART_GOODS_LIMIT_NUM_MAXMIN_REGEXP(401005, com.vpu.mp.service.foundation.data.JsonResultMessage.CART_GOODS_LIMIT_NUM_MAXMIN_REGEXP),
    CART_GOODS_LIMIT_NUM_MAX_REGEXP(401006, com.vpu.mp.service.foundation.data.JsonResultMessage.CART_GOODS_LIMIT_NUM_MAX_REGEXP),
    CART_GOODS_LIMIT_NUM_MIN_REGEXP(401007, com.vpu.mp.service.foundation.data.JsonResultMessage.CART_GOODS_LIMIT_NUM_MIN_REGEXP),

    /**微信支付错误信息
     *
     */
    /**微信预支付调用接口*/
    CODE_ORDER_WXPAY_UNIFIEDORDER_FAIL(410000, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_ORDER_WXPAY_UNIFIEDORDER_FAIL),
    /**无法获取prepay id*/
    CODE_WX_PAY_PREPAY_ID_IS_NULL(410001, com.vpu.mp.service.foundation.data.JsonResultMessage.MSG_WX_PAY_PREPAY_ID_IS_NULL),

    // 小程序-分享图片-下载海报错误信息
    WX_SHARE_ACTIVITY_DELETED(510001, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_SHARE_ACTIVITY_DELETED),
    WX_SHARE_GOODS_DELETED(510002, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_SHARE_GOODS_DELETED),
    WX_SHARE_PIC_ERROR(510003, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_SHARE_PIC_ERROR),
    WX_SHARE_QRCDOE_ERROR(510004, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_SHARE_QRCDOE_ERROR),
    WX_SHARE_USER_PIC_ERROR(510005, com.vpu.mp.service.foundation.data.JsonResultMessage.WX_SHARE_USER_PIC_ERROR),

    /**商品导入*/
    GOODS_EXCEL_IMPORT_WORKBOOK_CREATE_FAIL(610001, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_EXCEL_IMPORT_WORKBOOK_CREATE_FAIL),
    GOODS_EXCEL_IMPORT_SHEET_WRONG_INDEX(610002, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_EXCEL_IMPORT_SHEET_WRONG_INDEX),
    GOODS_EXCEL_IMPORT_SHEET_HEADER_WRONG_INDEX(610003, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_EXCEL_IMPORT_SHEET_HEADER_WRONG_INDEX),
    GOODS_EXCEL_IMPORT_SHEET_COLUMN_NOT_MAP_POJO(610004, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_EXCEL_IMPORT_SHEET_COLUMN_NOT_MAP_POJO),
    GOODS_EXCEL_UPLOAD_UPYUN_WRONG(610005, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_EXCEL_UPLOAD_UPYUN_WRONG),
    GOODS_EXCEL_IMPORT_NUM_OUT_OF_SIZE(610006, com.vpu.mp.service.foundation.data.JsonResultMessage.GOODS_EXCEL_IMPORT_NUM_OUT_OF_SIZE);

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
