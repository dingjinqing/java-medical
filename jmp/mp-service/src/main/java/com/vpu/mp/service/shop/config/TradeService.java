package com.vpu.mp.service.shop.config;

import com.vpu.mp.db.shop.tables.Payment;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.pojo.shop.config.trade.OrderProcessParam;
import com.vpu.mp.service.pojo.shop.config.trade.PaymentConfigParam;
import com.vpu.mp.service.pojo.shop.config.trade.PaymentConfigVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.management.Descriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;

/**
 * @author liufei
 * @date 2019/7/8
 */
@Slf4j
@Service
public class TradeService extends BaseShopConfigService {
    /**
     * 支付配置项
     *
     * 会员卡余额支付
     */
    final public static String K_CARD_FIRST = "card_first";
    /**
     * 余额支付
     */
    final public static String K_BALANCE_FIRST = "balance_first";
    /**
     * 积分支付
     */
    final public static String K_SCORE_FIRST = "score_first";

    /**
     * 交易流程配置项
     *
     * 是否启用快递
     */
    final public static String K_EXPRESS = "express";
    /**
     * 是否启用自提
     */
    final public static String K_FETCH = "fetch";
    /**
     * 拍下未付款订单12小时10分钟内未付款，自动取消订单
     * cancel_time保存形式为分钟，例如：730
     */
    final public static String K_CANCEL_TIME = "cancel_time";
    /**
     * 发货后drawback_days天，自动确认收货
     */
    final public static String K_DRAWBACK_DAYS = "drawback_days";
    /**
     * 确认收货后order_timeout_days天，订单完成
     */
    final public static String K_ORDER_TIMEOUT_DAYS = "order_timeout_days";
    /**
     * 申请延长收货配置,开关开启，用户可在前端申请延长收货时间
     */
    final public static String K_EXTEND_RECEIVE_GOODS = "extend_receive_goods";
    /**
     * 用户对单笔订单可申请一次延长收货时间，申请后可延长3天
     */
    final public static String K_EXTEND_RECEIVE_DAYS = "extend_receive_days";
    /**
     * 发票展示设置,开关开启，用户在购买时可以使用发票功能
     */
    final public static String K_INVOICE = "invoice";
    /**
     * 服务条款设置
     *
     * 服务条款设置开关开启，结算页会展示服务条款，用户需勾选“同意”才可继续下单
     */
    final public static String K_SERVICE_TERMS = "service_terms";
    /**
     * 服务条款名称，展示在结算页的服务条款名称
     */
    final public static String K_SERVICE_NAME = "service_name";
    /**
     * 服务条款设置首次下单是否默认勾选
     */
    final public static String K_SERVICE_CHOOSE = "service_choose";
    /**
     * 下单必填信息设置
     *
     * 下单人真实姓名
     */
    final public static String K_ORDER_REAL_NAME = "order_real_name";
    /**
     * 下单人身份证号码
     */
    final public static String K_ORDER_CID = "order_cid";
    /**
     * 收货人真实姓名
     */
    final public static String K_CONSIGNEE_REAL_NAME = "consignee_real_name";
    /**
     * 收货人身份证号码
     */
    final public static String K_CONSIGNEE_CID = "consignee_cid";
    /**
     * 自定义信息
     */
    final public static String K_CUSTOM = "custom";
    /**
     * 自定义信息标题：限制输入不超过6个字
     */
    final public static String K_CUSTOM_TITLE = "custom_title";
    /**
     * 选择下单需要填写必填信息的商品，json格式如下
     * {"add_goods":"","add_cate":"","add_sort":"","add_label":"","add_brand":""}
     */
    final public static String K_ORDER_REQUEIRE_GOODS_PACKAGE = "order_require_goods_package";

    /**
     * 微信物流助手
     *
     * 微信物流助手对接配置
     * <P>
     * 开关打开，已发货商品物流信息将展示在小程序前端订单页面，用户可直接查看物流信息。
     * 开关关闭，用户在小程序端查看物流信息时将自动跳转至“快递100”小程序。
     */
    final public static String K_SHIPPING_EXPRESSS = "shipping_express";
    /**
     * 发货地址，json格式如下：
     * {"province_code":"610000","city_code":"610100","district_code":"610116","address":"西直门"}
     */
    final public static String K_SHOP_ADDRESS = "shop_address";

    public Byte getExpress() {
        return this.get(K_EXPRESS, Byte.class, BYTE_ZERO);
    }

    public int setExpress(Byte express) {
        assert (express == (byte) 0 || express == (byte) 1);
        return this.set(K_EXPRESS, express, Byte.class);
    }

    public Byte getFetch() {
        return this.get(K_FETCH, Byte.class, BYTE_ZERO);
    }

    public int setFetch(Byte fetch) {
        assert (fetch == (byte) 0 || fetch == (byte) 1);
        return this.set(K_FETCH, fetch, Byte.class);
    }

    public Integer getDrawbackDays() {
        return this.get(K_DRAWBACK_DAYS, Integer.class, 0);
    }

    public int setDrawbackDays(Integer drawbackDays) {
        return drawbackDays != null ? this.set(K_DRAWBACK_DAYS, drawbackDays, Integer.class) : -1;
    }

    public Integer getOrderTimeoutDays() {
        return this.get(K_ORDER_TIMEOUT_DAYS, Integer.class, 0);
    }

    public int setOrderTimeoutDays(Integer orderTimeoutDays) {
        return orderTimeoutDays != null ? this.set(K_ORDER_TIMEOUT_DAYS, orderTimeoutDays, Integer.class) : -1;
    }

    public Byte getCardFirst() {
        return this.get(K_CARD_FIRST, Byte.class, BYTE_ZERO);
    }

    public Byte getBalanceFirst() {
        return this.get(K_BALANCE_FIRST, Byte.class, BYTE_ZERO);
    }

    public Byte getScoreFirst() {
        return this.get(K_SCORE_FIRST, Byte.class, BYTE_ZERO);
    }

    public int setCardFirst(Byte cardFirst) {
        assert (cardFirst == (byte) 0 || cardFirst == (byte) 1);
        return this.set(K_CARD_FIRST, cardFirst, Byte.class);
    }

    public int setBalanceFirst(Byte balanceFirst) {
        assert (balanceFirst == (byte) 0 || balanceFirst == (byte) 1);
        return this.set(K_BALANCE_FIRST, balanceFirst, Byte.class);
    }

    public int setScoreFirst(Byte scoreFirst) {
        assert (scoreFirst == (byte) 0 || scoreFirst == (byte) 1);
        return this.set(K_SCORE_FIRST, scoreFirst, Byte.class);
    }

    public Integer getCancelTime() {
        return this.get(K_CANCEL_TIME, Integer.class, 0);
    }
    public int setCancelTime(Integer cancelTime) {
        assert (cancelTime >= 0);
        return this.set(K_CANCEL_TIME, cancelTime, Integer.class);
    }

    public Byte getExtendReceiveGoods() {
        return this.get(K_EXTEND_RECEIVE_GOODS, Byte.class, BYTE_ZERO);
    }
    public int setExtendReceiveGoods(Byte extendReceiveGoods) {
        assert (extendReceiveGoods == (byte) 0 || extendReceiveGoods == (byte) 1);
        return this.set(K_EXTEND_RECEIVE_GOODS, extendReceiveGoods, Byte.class);
    }

    public Integer getExtendReceiveDays() {
        return this.get(K_EXTEND_RECEIVE_DAYS, Integer.class, 0);
    }
    public int setExtendReceiveDays(Integer extendReceiveDays) {
        assert (extendReceiveDays >= 0);
        return this.set(K_EXTEND_RECEIVE_DAYS, extendReceiveDays, Integer.class);
    }

    public Byte getInvoice() {
        return this.get(K_INVOICE, Byte.class, BYTE_ZERO);
    }
    public int setInvoice(Byte invoice) {
        assert (invoice == (byte) 0 || invoice == (byte) 1);
        return this.set(K_INVOICE, invoice, Byte.class);
    }

    public Byte getServiceTerms() {
        return this.get(K_SERVICE_TERMS, Byte.class, BYTE_ZERO);
    }
    public int setServiceTerms(Byte serviceTerms) {
        assert (serviceTerms == (byte) 0 || serviceTerms == (byte) 1);
        return this.set(K_SERVICE_TERMS, serviceTerms, Byte.class);
    }

    public String getServiceName() {
        return this.get(K_SERVICE_NAME, String.class, "");
    }
    public int setServiceName(String serviceName) {
        assert (serviceName != null);
        return this.set(K_SERVICE_NAME, serviceName, String.class);
    }

    public Byte getServiceChoose() {
        return this.get(K_SERVICE_CHOOSE, Byte.class, BYTE_ZERO);
    }
    public int setServiceChoose(Byte serviceChoose) {
        assert (serviceChoose == (byte) 0 || serviceChoose == (byte) 1);
        return this.set(K_SERVICE_CHOOSE, serviceChoose, Byte.class);
    }

    public Byte getOrderRealName() {
        return this.get(K_ORDER_REAL_NAME, Byte.class, BYTE_ZERO);
    }
    public int setOrderRealName(Byte orderRealName) {
        assert (orderRealName == (byte) 0 || orderRealName == (byte) 1);
        return this.set(K_ORDER_REAL_NAME, orderRealName, Byte.class);
    }

    public Byte getOrderCid() {
        return this.get(K_ORDER_CID, Byte.class, BYTE_ZERO);
    }
    public int setOrderCid(Byte orderCid) {
        assert (orderCid == (byte) 0 || orderCid == (byte) 1);
        return this.set(K_ORDER_CID, orderCid, Byte.class);
    }

    public Byte getConsigneeRealName() {
        return this.get(K_CONSIGNEE_REAL_NAME, Byte.class, BYTE_ZERO);
    }
    public int setConsigneeRealName(Byte consigneeRealName) {
        assert (consigneeRealName == (byte) 0 || consigneeRealName == (byte) 1);
        return this.set(K_CONSIGNEE_REAL_NAME, consigneeRealName, Byte.class);
    }

    public Byte getConsigneeCid() {
        return this.get(K_CONSIGNEE_CID, Byte.class,BYTE_ZERO);
    }
    public int setConsigneeCid(Byte consigneeCid) {
        assert (consigneeCid == (byte) 0 || consigneeCid == (byte) 1);
        return this.set(K_CONSIGNEE_CID, consigneeCid, Byte.class);
    }

    public Byte getCustom() {
        return this.get(K_CUSTOM, Byte.class, BYTE_ZERO);
    }
    public int setCustom(Byte custom) {
        assert (custom == (byte) 0 || custom == (byte) 1);
        return this.set(K_CUSTOM, custom, Byte.class);
    }

    public String getCustomTitle() {
        return this.get(K_CUSTOM_TITLE, String.class, "");
    }
    public int setCustomTitle(String customTitle) {
        assert (customTitle != null);
        return this.set(K_CUSTOM_TITLE, customTitle, String.class);
    }

    public String getOrderRequeireGoodsPackage() {
        return this.get(K_ORDER_REQUEIRE_GOODS_PACKAGE, String.class, "");
    }
    public int setOrderRequeireGoodsPackage(String orderRequireGoodsPackage) {
        assert (orderRequireGoodsPackage != null );
        return this.set(K_ORDER_REQUEIRE_GOODS_PACKAGE, orderRequireGoodsPackage, String.class);
    }

    public Byte getShippingExpresss() {
        return this.get(K_SHIPPING_EXPRESSS, Byte.class, BYTE_ZERO);
    }
    public int setShippingExpresss(Byte shippingExpress) {
        assert (shippingExpress == (byte) 0 || shippingExpress == (byte) 1);
        return this.set(K_SHIPPING_EXPRESSS, shippingExpress, Byte.class);
    }

    public String getShopAddress() {
        return this.get(K_SHOP_ADDRESS, String.class, "");
    }
    public int setShopAddress(String shopAddress) {
        assert (shopAddress != null);
        return this.set(K_SHOP_ADDRESS, shopAddress, String.class);
    }


    /**
     * 更新支付方式开关
     *
     * @param payCode 支付方式码
     * @param enabled 支付开关状态
     */
    public void updatePayment(String payCode, Byte enabled) {
        db().update(Payment.PAYMENT)
            .set(Payment.PAYMENT.ENABLED, enabled)
            .where(Payment.PAYMENT.PAY_CODE.eq(payCode))
            .execute();
    }

    /**
     * 更新默认支付配置
     */
    public void updateDefaultPayConf(PaymentConfigParam param) {
        try {
            this.transaction(() -> {
                if(param.getCardFirst()!=null){
                    this.setCardFirst(param.getCardFirst());
                }
                if(param.getBalanceFirst()!=null){
                    this.setBalanceFirst(param.getBalanceFirst());
                }
                if(param.getScoreFirst()!=null){
                    this.setScoreFirst(param.getScoreFirst());
                }
            });
        } catch (RuntimeException e) {
            log.error("更新默认支付配置事务执行失败！错误：{}", e.getMessage());
            throw new BusinessException(JsonResultCode.CODE_CONFIG_UPDATE_FAILED);
        }
    }

    /**
     * 查询支付方式开关
     */
    public List<PaymentConfigVo> getPaymentEnabled() {
        return db().select(Payment.PAYMENT.PAY_CODE, Payment.PAYMENT.PAY_NAME, Payment.PAYMENT.ENABLED)
            .from(Payment.PAYMENT).fetchInto(PaymentConfigVo.class);
    }

    /**
     * 查询默认支付配置
     */
    public Map<String, Byte> getDefaultPayConf() {
        Byte cardFirst = this.getCardFirst();
        Byte balanceFirst = this.getBalanceFirst();
        Byte scoreFirst = this.getScoreFirst();
        return new HashMap<String, Byte>(3) {
            {
                put(K_CARD_FIRST, cardFirst);
                put(K_BALANCE_FIRST, balanceFirst);
                put(K_SCORE_FIRST, scoreFirst);
            }
        };
    }

    private void updateInvoke(PropertyDescriptor descriptor, OrderProcessParam param){
        String fieldName = descriptor.getName();
        Object conf = null;
        try {
            conf = descriptor.getReadMethod().invoke(param);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("内省获取field[{}]字段属性值：{}",fieldName,e.getMessage());
        }
        if (conf != null) {
            Method method = null;
            String methodName = getMethodName(fieldName,"set");
            try {
                method = this.getClass().getMethod(methodName);
            } catch (NoSuchMethodException e) {
                log.error("field[{}]字段对应的setXXX方法[{}]不存在：{}",fieldName,methodName,e.getMessage());
                e.printStackTrace();
            }
            Assert.notNull(method,"Method方法实例获取为空");
            try {
                method.invoke(this,conf);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("field[{}]字段对应的setXXX方法执行失败：{}",fieldName,e.getMessage());
            }
        }
    }
    private String getMethodName(String fieldName,String args){
        StringBuilder stringBuilder = new StringBuilder(args);
        char[] temp = fieldName.toCharArray();
        temp[0] -= 32;
        return stringBuilder.append(temp).toString();
    }
    /**
     * 更新订单流程配置
     *
     * @param param 订单流程配置项信息
     */
    public void updateOrderProcess(OrderProcessParam param) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(param.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            Arrays.asList(descriptors).forEach((e) -> updateInvoke(e, param));
        } catch (IntrospectionException e) {
            log.error("内省获取bean[{}]信息失败：{}",param,e.getMessage());
        }


        /*if(param.getExpress()!=null) {
            this.setExpress(param.getExpress());
        }
        if(param.getFetch()!=null) {
            this.setFetch(param.getFetch());
        }
        if(param.getCancelTime()!=null) {
            this.setCancelTime(param.getCancelTime());
        }
        if(param.getDrawbackDays()!=null) {
            this.setDrawbackDays(param.getDrawbackDays());
        }
        if(param.getOrderTimeoutDays()!=null) {
            this.setOrderTimeoutDays(param.getOrderTimeoutDays());
        }
        if(param.getExtendReceiveGoods()!=null) {
            this.setExtendReceiveGoods(param.getExtendReceiveGoods());
        }
        if(param.getOrderTimeoutDays()!=null) {
            this.setExtendReceiveDays(param.getOrderTimeoutDays());
        }
        if(param.getInvoice()!=null) {
            this.setInvoice(param.getInvoice());
        }
        if(param.getServiceTerms()!=null) {
            this.setServiceTerms(param.getServiceTerms());
        }
        if(param.getServiceName()!=null) {
            this.setServiceName(param.getServiceName());
        }
        if(param.getServiceChoose()!=null) {
            this.setServiceChoose(param.getServiceChoose());
        }
        if(param.getOrderRealName()!=null) {
            this.setOrderRealName(param.getOrderRealName());
        }
        if(param.getOrderCid()!=null) {
            this.setOrderCid(param.getOrderCid());
        }
        if(param.getConsigneeRealName()!=null) {
            this.setConsigneeRealName(param.getConsigneeRealName());
        }
        if(param.getConsigneeCid()!=null) {
            this.setConsigneeCid(param.getConsigneeCid());
        }
        if(param.getCustom()!=null) {
            this.setCustom(param.getCustom());
        }
        if(param.getCustomTitle()!=null) {
            this.setCustomTitle(param.getCustomTitle());
        }
        if(param.getOrderRequireGoodsPackage()!=null) {
            this.setOrderRequeireGoodsPackage(param.getOrderRequireGoodsPackage());
        }
        if(param.getShippingExpress()!=null) {
            this.setShippingExpresss(param.getShippingExpress());
        }
        if(param.getShopAddress()!=null) {
            this.setShopAddress(param.getShopAddress());
        }*/
    }

    /**
     * 查询订单流程配置
     */
    public OrderProcessParam getOrderProcessConfig() {
        OrderProcessParam param = new OrderProcessParam();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(param.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            Arrays.asList(descriptors).forEach((e) -> selectInvoke(e, param));
        } catch (IntrospectionException e) {
            log.error("内省获取bean[{}]信息失败：{}",param,e.getMessage());
        }

        /*param.setExpress(this.getExpress());
        param.setFetch(this.getFetch());
        param.setDrawbackDays(this.getDrawbackDays());
        param.setOrderTimeoutDays(this.getOrderTimeoutDays());
        param.setCancelTime(this.getOrderTimeoutDays());
        param.setConsigneeCid(this.getConsigneeCid());
        param.setConsigneeRealName(this.getConsigneeRealName());
        param.setCustom(this.getCustom());
        param.setCustomTitle(this.getCustomTitle());
        param.setExtendReceiveDays(this.getExtendReceiveDays());
        param.setExtendReceiveGoods(this.getExtendReceiveGoods());
        param.setInvoice(this.getInvoice());
        param.setOrderCid(this.getOrderCid());
        param.setOrderRealName(this.getOrderRealName());
        param.setOrderRequireGoodsPackage(this.getOrderRequeireGoodsPackage());
        param.setServiceChoose(this.getServiceChoose());
        param.setServiceName(this.getServiceName());
        param.setServiceTerms(this.getServiceTerms());
        param.setShippingExpress(this.getShippingExpresss());
        param.setShopAddress(this.getShopAddress());*/
        return param;
    }
    private void selectInvoke(PropertyDescriptor descriptor, OrderProcessParam param){
        String fieldName = descriptor.getName();

        Method method = null;
        String methodName = getMethodName(fieldName,"get");
        try {
            method = this.getClass().getMethod(methodName);
        } catch (NoSuchMethodException e) {
            log.error("field[{}]字段对应的getXXX方法[{}]不存在：{}",fieldName,methodName,e.getMessage());
            e.printStackTrace();
        }
        Assert.notNull(method,"Method方法实例获取为空");
        Object conf = null;
        try {
            conf = method.invoke(this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("field[{}]字段对应的getXXX方法执行失败：{}",fieldName,e.getMessage());
        }
        if (conf != null) {
            try {
                descriptor.getWriteMethod().invoke(param,conf);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("OrderProcessParam类中属性[{}]的set方法执行错误：{}",fieldName,e.getMessage());
            }
        }
    }
}
