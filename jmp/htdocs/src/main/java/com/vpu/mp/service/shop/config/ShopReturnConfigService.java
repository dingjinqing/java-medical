package com.vpu.mp.service.shop.config;

import com.vpu.mp.service.pojo.shop.config.ShopCommonCfgInfo;
import com.vpu.mp.service.pojo.shop.config.trade.RetrunConfigParam;
import com.vpu.mp.service.pojo.shop.config.trade.ReturnBusinessAdressParam;
import com.vpu.mp.service.pojo.shop.config.trade.ReturnPackageParam;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @Author:liufei
 * @Date:2019/7/10
 * @Description:
 */
@Service
@Scope("prototype")
public class ShopReturnConfigService extends BaseShopConfigService {

    /**
     * 自动退款退货设置开关
     */
    final public static String K_AUTO_RETURN = "auto_return";

    /**
     * 自动退款退货设置开关开启时间
     */
    final public static String K_AUTO_RETURN_TIME = "auto_return_time";

    /**
     * 买家发起仅退款申请后，商家在return_money_days日内未处理，系统将自动退款。
     */
    final public static String K_RETURN_MONEY_DAYS = "return_money_days";

    /**
     * 商家已发货，买家发起退款退货申请，商家在return_address_days日内未处理，系统将默认同意退款退货，并自动向买家发送商家的默认收货地址。
     */
    final public static String K_RETURN_ADDRESS_DAYS = "return_address_days";

    /**
     * 买家已提交物流信息，商家在return_address_days日内未处理，系统将默认同意退款退货，并自动退款给买家。
     */
    final public static String K_RETURN_SHOPPING_DAYS = "return_shopping_days";

    /**
     * 是否退优惠卷
     */
    final public static String K_IS_REFUND_COUPON = "is_refund_coupon";

    /**
     * 商家默认收货地址(包括收件人，收件电话，邮编，退货地址)
     */
    final public static String K_BUSINESS_ADDRESS = "business_address";

    /**
     * 退换货配置选项，可退换(0)，还是不可退还(1)
     */
    final public static String K_RETURN_CHANGE_GOODS_STATUS = "return_change_goods_status";

    /**
     * 退换货配置（包括商品，平台分类，商家分类，商品品牌，商品标签）
     */
    final public static String K_ORDER_RETURN_GOODS_PACKAGE = "order_return_goods_package";

    public Byte getAutoReturn() {
        return this.get(K_AUTO_RETURN, Byte.class, (byte)0);
    }

    public int setAutoReturn(Byte autoReturn) {
        assert(autoReturn ==(byte)0 || autoReturn == (byte)1);
        return this.set(K_AUTO_RETURN, autoReturn,Byte.class);
    }

    public Timestamp getAutoReturnTime() {
        return this.get(K_AUTO_RETURN_TIME, Timestamp.class,null);
    }

    public int setAutoReturnTime(Timestamp autoReturnTime) {
        return autoReturnTime !=null ? this.set(K_AUTO_RETURN_TIME, autoReturnTime,Timestamp.class) : -1;
    }

    public Byte getReturnMoneyDays() {
        return this.get(K_RETURN_MONEY_DAYS, Byte.class, (byte)0);
    }

    public int setReturnMoneyDays(Byte returnMoneyDays) {
        return returnMoneyDays != null ? this.set(K_RETURN_MONEY_DAYS, returnMoneyDays,Byte.class) : -1;
    }

    public Byte getReturnAddressDays() {
        return this.get(K_RETURN_ADDRESS_DAYS, Byte.class, (byte)0);
    }

    public int setReturnAddressDays(Byte returnAddressDays) {
        return returnAddressDays !=null ? this.set(K_RETURN_ADDRESS_DAYS, returnAddressDays,Byte.class) : -1;
    }

    public Byte getReturnShoppingDays() {
        return this.get(K_RETURN_SHOPPING_DAYS, Byte.class, (byte)0);
    }

    public int setReturnShoppingDays(Byte returnShoppingDays) {
        return returnShoppingDays !=null ? this.set(K_RETURN_SHOPPING_DAYS, returnShoppingDays,Byte.class) : -1;
    }

    public Byte getIsReturnCoupon() {
        return this.get(K_IS_REFUND_COUPON, Byte.class, (byte)0);
    }

    public int setIsReturnCoupon(Byte isReturnCoupon) {
        assert(isReturnCoupon ==(byte)0 || isReturnCoupon == (byte)1);
        return this.set(K_IS_REFUND_COUPON, isReturnCoupon,Byte.class);
    }

    public ReturnBusinessAdressParam getBusinessAddress() {
        return this.getJsonObject(K_BUSINESS_ADDRESS, ReturnBusinessAdressParam.class, null);
    }

    public int setBusinessAddress(ReturnBusinessAdressParam businessAddress) {
        return businessAddress !=null ? this.setJsonObject(K_BUSINESS_ADDRESS, businessAddress) : -1;
    }

    public Byte getReturnChangeGoodsStatus() {
        return this.get(K_RETURN_CHANGE_GOODS_STATUS, Byte.class, (byte)0);
    }

    public int setReturnChangeGoodsStatus(Byte returnChangeGoodsStatus) {
        assert(returnChangeGoodsStatus ==(byte)0 || returnChangeGoodsStatus == (byte)1);
        return this.set(K_RETURN_CHANGE_GOODS_STATUS, returnChangeGoodsStatus,Byte.class);
    }

    public ReturnPackageParam getOrderReturnGoodsPackage() {
        return this.getJsonObject(K_ORDER_RETURN_GOODS_PACKAGE, ReturnPackageParam.class, null);
    }

    public int setOrderReturnGoodsPackage(ReturnPackageParam orderReturnGoodsPackage) {
        return orderReturnGoodsPackage !=null ? this.setJsonObject(K_ORDER_RETURN_GOODS_PACKAGE, orderReturnGoodsPackage) : -1;
    }

    public RetrunConfigParam getRetrunConfigParam() {
        RetrunConfigParam retrunConfigParam = new RetrunConfigParam();
        this.transaction(()->{
            retrunConfigParam.setAutoReturn(this.getAutoReturn());
            retrunConfigParam.setAutoReturnTime(this.getAutoReturnTime());
            retrunConfigParam.setBusinessAddress(this.getBusinessAddress());
            retrunConfigParam.setIsReturnCoupon(this.getIsReturnCoupon());
            retrunConfigParam.setOrderReturnGoodsPackage(this.getOrderReturnGoodsPackage());
            retrunConfigParam.setReturnAddressDays(this.getReturnAddressDays());
            retrunConfigParam.setReturnChangeGoodsStatus(this.getReturnChangeGoodsStatus());
            retrunConfigParam.setReturnMoneyDays(this.getReturnMoneyDays());
            retrunConfigParam.setReturnShoppingDays(this.getReturnShoppingDays());
        });
        return retrunConfigParam;
    }

    /**
     * 更新退换货配置
     *
     */
    public Boolean updateReturnConfig(RetrunConfigParam retrunConfigParam) {
        try {
            db().transaction(configuration -> {
                this.setAutoReturn(retrunConfigParam.getAutoReturn());
                this.setAutoReturnTime(retrunConfigParam.getAutoReturnTime());
                this.setReturnMoneyDays(retrunConfigParam.getReturnMoneyDays());
                this.setReturnAddressDays(retrunConfigParam.getReturnAddressDays());
                this.setReturnShoppingDays(retrunConfigParam.getReturnShoppingDays());
                this.setIsReturnCoupon(retrunConfigParam.getIsReturnCoupon());
                this.setBusinessAddress(retrunConfigParam.getBusinessAddress());
                this.setReturnChangeGoodsStatus(retrunConfigParam.getReturnChangeGoodsStatus());
                this.setOrderReturnGoodsPackage(retrunConfigParam.getOrderReturnGoodsPackage());
            });
        }
        catch(RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
