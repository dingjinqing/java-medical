package com.vpu.mp.service.shop.config;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.pojo.shop.config.trade.GoodsPackageParam;
import com.vpu.mp.service.pojo.shop.config.trade.ReturnBusinessAddressParam;
import com.vpu.mp.service.pojo.shop.config.trade.ReturnConfigParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.Timestamp;
import java.util.Arrays;

import static com.vpu.mp.service.pojo.shop.config.trade.TradeConstant.BYTE_SEVEN;
import static com.vpu.mp.service.pojo.shop.config.trade.TradeConstant.FIELD_CLAZZ;
import static com.vpu.mp.service.shop.config.TradeService.selectInvoke;
import static com.vpu.mp.service.shop.config.TradeService.updateInvoke;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;

/**
 * @author liufei
 * @date 2019/7/10
 */
@Slf4j
@Service
public class ShopReturnConfigService extends BaseShopConfigService {

    /**
     * 自动退款退货设置开关，默认为0关闭，1开启
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
     * 商家同意退款退货，买家在7日内未提交物流信息，且商家未确认收货并退款，退款申请将自动完成。
     */
    final public static String K_RETURN_PASS_DAYS = "return_pass_days";

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
        return this.get(K_AUTO_RETURN, Byte.class, BYTE_ZERO);
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
        return this.get(K_RETURN_MONEY_DAYS, Byte.class, BYTE_ZERO);
    }

    public int setReturnMoneyDays(Byte returnMoneyDays) {
        return returnMoneyDays != null ? this.set(K_RETURN_MONEY_DAYS, returnMoneyDays,Byte.class) : -1;
    }

    public Byte getReturnAddressDays() {
        return this.get(K_RETURN_ADDRESS_DAYS, Byte.class, BYTE_ZERO);
    }

    public int setReturnAddressDays(Byte returnAddressDays) {
        return returnAddressDays !=null ? this.set(K_RETURN_ADDRESS_DAYS, returnAddressDays,Byte.class) : -1;
    }

    public Byte getReturnShoppingDays() {
        return this.get(K_RETURN_SHOPPING_DAYS, Byte.class, BYTE_ZERO);
    }

    public int setReturnShoppingDays(Byte returnShoppingDays) {
        return returnShoppingDays !=null ? this.set(K_RETURN_SHOPPING_DAYS, returnShoppingDays,Byte.class) : -1;
    }

    public Byte getReturnPassDays() {
        return this.get(K_RETURN_PASS_DAYS, Byte.class, BYTE_ZERO);
    }

    public int setReturnPassDays(Byte returnPassDays) {
        return returnPassDays != null ? this.set(K_RETURN_PASS_DAYS, returnPassDays, Byte.class) : -1;
    }

    public Byte getIsReturnCoupon() {
        return this.get(K_IS_REFUND_COUPON, Byte.class, BYTE_ZERO);
    }

    public int setIsReturnCoupon(Byte isReturnCoupon) {
        assert(isReturnCoupon ==(byte)0 || isReturnCoupon == (byte)1);
        return this.set(K_IS_REFUND_COUPON, isReturnCoupon,Byte.class);
    }

    public ReturnBusinessAddressParam getBusinessAddress() {
        return this.getJsonObject(K_BUSINESS_ADDRESS, ReturnBusinessAddressParam.class, null);
    }

    public int setBusinessAddress(ReturnBusinessAddressParam businessAddress) {
        return businessAddress !=null ? this.setJsonObject(K_BUSINESS_ADDRESS, businessAddress) : -1;
    }

    public Byte getReturnChangeGoodsStatus() {
        return this.get(K_RETURN_CHANGE_GOODS_STATUS, Byte.class, BYTE_ZERO);
    }

    public int setReturnChangeGoodsStatus(Byte returnChangeGoodsStatus) {
        assert(returnChangeGoodsStatus ==(byte)0 || returnChangeGoodsStatus == (byte)1);
        return this.set(K_RETURN_CHANGE_GOODS_STATUS, returnChangeGoodsStatus,Byte.class);
    }

    public GoodsPackageParam getOrderReturnGoodsPackage() {
        return this.getJsonObject(K_ORDER_RETURN_GOODS_PACKAGE, GoodsPackageParam.class, null);
    }

    public int setOrderReturnGoodsPackage(GoodsPackageParam orderReturnGoodsPackage) {
        return orderReturnGoodsPackage !=null ? this.setJsonObject(K_ORDER_RETURN_GOODS_PACKAGE, orderReturnGoodsPackage) : -1;
    }

    /**
     * 获取退换货配置项信息
     *
     * @return 退换货配置项信息
     * {@value com.vpu.mp.service.pojo.shop.config.trade.TradeConstant#FIELD_CLAZZ}
     */
    public ReturnConfigParam getReturnConfigParam() {
        ReturnConfigParam param = new ReturnConfigParam();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(param.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            Arrays.stream(descriptors)
                .filter(des -> !FIELD_CLAZZ.equalsIgnoreCase(des.getName()))
                .forEach((e) -> selectInvoke(e, param, this));
        } catch (IntrospectionException e) {
            log.error("内省获取bean[{}]信息失败：{}", param, e.getMessage());
            throw new BusinessException(JsonResultCode.RETURN_CONFIG_SELECT_FAILED);
        }
        return param;
    }

    /**
     * 查询退换货配置-商家默认收货地址配置信息
     */
    public ReturnBusinessAddressParam getDefaultAddress(){
        return this.getBusinessAddress();
    }

    /**
     * 	更新退换货配置
     * {@value com.vpu.mp.service.pojo.shop.config.trade.TradeConstant#FIELD_CLAZZ}
     */
    public void updateReturnConfig(ReturnConfigParam param) {
        prefixhandler(param);
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(param.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            Arrays.stream(descriptors)
                .filter(des -> !FIELD_CLAZZ.equalsIgnoreCase(des.getName()))
                .forEach((e) -> updateInvoke(e, param, this));
        } catch (IntrospectionException e) {
            log.error("内省获取bean[{}]信息失败：{}", param, e.getMessage());
            throw new BusinessException(JsonResultCode.RETURN_CONFIG_UPDATE_FAILED);
        }
    }

    /**
     * 退换货配置项数据预处理
     */
    private void prefixhandler(ReturnConfigParam param) {
        // 自动退款/退货设置为关闭状态0或者实际配置项填写0表示不设置，默认处理期限均为7天
        if (BYTE_ZERO.equals(param.getAutoReturn())) {
            param.setReturnAddressDays(BYTE_ZERO);
            param.setReturnMoneyDays(BYTE_ZERO);
            param.setReturnPassDays(BYTE_ZERO);
            param.setReturnShoppingDays(BYTE_ZERO);
        } else {
            param.setReturnAddressDays(BYTE_ZERO.equals(param.getReturnAddressDays()) ? BYTE_SEVEN : param.getReturnAddressDays());
            param.setReturnMoneyDays(BYTE_ZERO.equals(param.getReturnMoneyDays()) ? BYTE_SEVEN : param.getReturnMoneyDays());
            param.setReturnPassDays(BYTE_ZERO.equals(param.getReturnPassDays()) ? BYTE_SEVEN : param.getReturnPassDays());
            param.setReturnShoppingDays(BYTE_ZERO.equals(param.getReturnShoppingDays()) ? BYTE_SEVEN : param.getReturnShoppingDays());
        }

    }
}
