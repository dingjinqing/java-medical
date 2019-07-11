package com.vpu.mp.service.shop.config;

import com.vpu.mp.service.pojo.shop.config.trade.RetrunConfigParam;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

/**
 * @Author:liufei
 * @Date:2019/7/10
 * @Description:
 */
public class ShopReturnConfigService extends BaseShopConfigService {

    /**
     * 自动退款退货设置开关
     */
    final public static String K_AUTO_RETURN = "auto_return";

    /**
     * 买家发起仅退款申请后，商家在auto_return_time日内未处理，系统将自动退款。
     */
    final public static String K_AUTO_RETURN_TIME = "auto_return_time";

    /**
     * 商家已发货，买家发起退款退货申请，商家在return_momey_days日内未处理，系统将默认同意退款退货，并自动向买家发送商家的默认收货地址。
     */
    final public static String K_RETURN_MONEY_DAYS = "return_money_days";

    /**
     * 买家已提交物流信息，商家在return_address_days日内未处理，系统将默认同意退款退货，并自动退款给买家。
     */
    final public static String K_RETURN_ADDRESS_DAYS = "return_address_days";

    /**
     * 商家同意退款退货，买家在return_shopping_days日内未提交物流信息，且商家未确认收货并退款，退款申请将自动完成。
     */
    final public static String K_RETURN_SHOPPING_DAYS = "return_shopping_days";

    /**
     * 是否退优惠卷
     */
    final public static String K_IS_REFUND_COUPON = "is_refund_coupon";

    /**
     * 商家默认收货地址(包括收件人，收件电话，邮编，退货地址)
     */
    final public static String K_BUSINESS_ADDRESS = "business_adress";

    /**
     * 退换货配置选项，可退换(0)，还是不可退还(1)
     */
    final public static String K_RETURN_CHANGE_GOODS_STATUS = "return_change_goods_status";

    /**
     * 退换货配置（包括商品，平台分类，商家分类，商品品牌，商品标签）
     */
    final public static String K_ORDER_RETURN_GOODS_PACKAGE = "order_return_goods_package";


    /**
     * 更新退换货配置
     *
     */
    public Boolean updateReturnConfig(RetrunConfigParam retrunConfigParam) {
        try {
            db().transaction(configuration -> {
                DSLContext db = DSL.using(configuration);
                this.set(db, K_AUTO_RETURN, retrunConfigParam.getAutoReturn(), Byte.class);
                this.set(db, K_AUTO_RETURN_TIME, retrunConfigParam.getAutoReturnTime(), String.class);
                this.set(db, K_RETURN_MONEY_DAYS, retrunConfigParam.getReturnMoneyDays(), String.class);
                this.set(db, K_RETURN_ADDRESS_DAYS, retrunConfigParam.getReturnAddressDays(), String.class);
                this.set(db, K_RETURN_SHOPPING_DAYS, retrunConfigParam.getReturnShoppingDays(), String.class);
                this.set(db, K_IS_REFUND_COUPON, retrunConfigParam.getIsReturnCoupon(), Byte.class);
                this.setJsonObject(db, K_BUSINESS_ADDRESS, retrunConfigParam.getBusinessAddress());
                this.set(db, K_RETURN_CHANGE_GOODS_STATUS, retrunConfigParam.getReturnChangeGoodsStatus(), String.class);
                this.setJsonObject(db, K_ORDER_RETURN_GOODS_PACKAGE, retrunConfigParam.getOrderReturnGoodsPackage());
            });
        }
        catch(RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
