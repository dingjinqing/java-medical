package com.vpu.mp.service.shop.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.vpu.mp.db.shop.tables.Payment;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.pojo.shop.config.trade.*;
import com.vpu.mp.service.shop.logistics.LogisticsService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.service.pojo.shop.config.trade.TradeConstant.*;
import static com.vpu.mp.service.pojo.shop.market.form.FormConstant.MAPPER;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;

/**
 * The type Trade service.
 *
 * @author liufei
 * @date 2019 /7/8
 */
@Slf4j
@Service
public class TradeService extends BaseShopConfigService {

    @Autowired
    LogisticsService logisticsService;

    /**
     * 支付配置项
     * <p>
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
     * <p>
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
     * 用户对单笔订单可申请一次延长收货时间，申请后可延长3天,默认延长3天,上限为30天
     */
    final public static String K_EXTEND_RECEIVE_DAYS = "extend_receive_days";
    /**
     * 发票展示设置,开关开启，用户在购买时可以使用发票功能
     */
    final public static String K_INVOICE = "invoice";
    /**
     * 服务条款设置
     * <p>
     * 服务条款设置开关开启，结算页会展示服务条款，用户需勾选“同意”才可继续下单
     */
    final public static String K_SERVICE_TERMS = "service_terms";
    /**
     * 服务条款名称，展示在结算页的服务条款名称
     */
    final public static String K_SERVICE_NAME = "service_name";
    /**
     * 服务条款配置内容
     */
    final public static String K_SERVICE_DOCUMENT = "service_document";
    /**
     * 服务条款设置首次下单是否默认勾选
     */
    final public static String K_SERVICE_CHOOSE = "service_choose";
    /**
     * 下单必填信息设置
     * <p>
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
    final public static String K_ORDER_REQUIRE_GOODS_PACKAGE = "order_require_goods_package";

    /**
     * 微信物流助手
     * <p>
     * 微信物流助手对接配置
     * <p>
     * 开关打开，已发货商品物流信息将展示在小程序前端订单页面，用户可直接查看物流信息。
     * 开关关闭，用户在小程序端查看物流信息时将自动跳转至“快递100”小程序。
     */
    final public static String K_SHIPPING_EXPRESS = "shipping_express";
    /**
     * 发货地址，json格式如下：
     * {"province_code":"610000","city_code":"610100","district_code":"610116","address":"西直门"}
     */
    final public static String K_SHOP_ADDRESS = "shop_address";

    /**
     * Gets express.
     *
     * @return the express
     */
    public Byte getExpress() {
        return this.get(K_EXPRESS, Byte.class, BYTE_ZERO);
    }

    /**
     * Sets express.
     *
     * @param express the express
     * @return the express
     */
    public int setExpress(Byte express) {
        assert (express == (byte) 0 || express == (byte) 1);
        return this.set(K_EXPRESS, express, Byte.class);
    }

    /**
     * Gets fetch.
     *
     * @return the fetch
     */
    public Byte getFetch() {
        return this.get(K_FETCH, Byte.class, BYTE_ZERO);
    }

    /**
     * Sets fetch.
     *
     * @param fetch the fetch
     * @return the fetch
     */
    public int setFetch(Byte fetch) {
        assert (fetch == (byte) 0 || fetch == (byte) 1);
        return this.set(K_FETCH, fetch, Byte.class);
    }

    /**
     * Gets drawback days.
     *
     * @return the drawback days
     */
    public Integer getDrawbackDays() {
        return this.get(K_DRAWBACK_DAYS, Integer.class, 0);
    }

    /**
     * Sets drawback days.
     *
     * @param drawbackDays the drawback days
     * @return the drawback days
     */
    public int setDrawbackDays(Integer drawbackDays) {
        return drawbackDays != null ? this.set(K_DRAWBACK_DAYS, drawbackDays, Integer.class) : -1;
    }

    /**
     * Gets order timeout days.
     *
     * @return the order timeout days
     */
    public Integer getOrderTimeoutDays() {
        return this.get(K_ORDER_TIMEOUT_DAYS, Integer.class, 0);
    }

    /**
     * Sets order timeout days.
     *
     * @param orderTimeoutDays the order timeout days
     * @return the order timeout days
     */
    public int setOrderTimeoutDays(Integer orderTimeoutDays) {
        return orderTimeoutDays != null ? this.set(K_ORDER_TIMEOUT_DAYS, orderTimeoutDays, Integer.class) : -1;
    }

    /**
     * Gets card first.
     *
     * @return the card first
     */
    public Byte getCardFirst() {
        return this.get(K_CARD_FIRST, Byte.class, BYTE_ZERO);
    }

    /**
     * Gets balance first.
     *
     * @return the balance first
     */
    public Byte getBalanceFirst() {
        return this.get(K_BALANCE_FIRST, Byte.class, BYTE_ZERO);
    }

    /**
     * Gets score first.
     *
     * @return the score first
     */
    public Byte getScoreFirst() {
        return this.get(K_SCORE_FIRST, Byte.class, BYTE_ZERO);
    }

    /**
     * Sets card first.
     *
     * @param cardFirst the card first
     * @return the card first
     */
    public int setCardFirst(Byte cardFirst) {
        assert (cardFirst == (byte) 0 || cardFirst == (byte) 1);
        return this.set(K_CARD_FIRST, cardFirst, Byte.class);
    }

    /**
     * Sets balance first.
     *
     * @param balanceFirst the balance first
     * @return the balance first
     */
    public int setBalanceFirst(Byte balanceFirst) {
        assert (balanceFirst == (byte) 0 || balanceFirst == (byte) 1);
        return this.set(K_BALANCE_FIRST, balanceFirst, Byte.class);
    }

    /**
     * Sets score first.
     *
     * @param scoreFirst the score first
     * @return the score first
     */
    public int setScoreFirst(Byte scoreFirst) {
        assert (scoreFirst == (byte) 0 || scoreFirst == (byte) 1);
        return this.set(K_SCORE_FIRST, scoreFirst, Byte.class);
    }

    /**
     * Gets cancel time.
     *
     * @return the cancel time
     */
    public Integer getCancelTime() {
        return this.get(K_CANCEL_TIME, Integer.class, 0);
    }

    /**
     * Sets cancel time.
     *
     * @param cancelTime the cancel time
     * @return the cancel time
     */
    public int setCancelTime(Integer cancelTime) {
        assert (cancelTime >= 0);
        return this.set(K_CANCEL_TIME, cancelTime, Integer.class);
    }

    /**
     * Gets extend receive goods.
     *
     * @return the extend receive goods
     */
    public Byte getExtendReceiveGoods() {
        return this.get(K_EXTEND_RECEIVE_GOODS, Byte.class, BYTE_ZERO);
    }

    /**
     * Sets extend receive goods.
     *
     * @param extendReceiveGoods the extend receive goods
     * @return the extend receive goods
     */
    public int setExtendReceiveGoods(Byte extendReceiveGoods) {
        assert (extendReceiveGoods == (byte) 0 || extendReceiveGoods == (byte) 1);
        return this.set(K_EXTEND_RECEIVE_GOODS, extendReceiveGoods, Byte.class);
    }

    /**
     * Gets extend receive days.
     *
     * @return the extend receive days
     */
    public Integer getExtendReceiveDays() {
        return this.get(K_EXTEND_RECEIVE_DAYS, Integer.class, 3);
    }

    /**
     * Sets extend receive days.
     *
     * @param extendReceiveDays the extend receive days
     * @return the extend receive days
     */
    public int setExtendReceiveDays(Integer extendReceiveDays) {
        assert (extendReceiveDays > 0);
        extendReceiveDays = extendReceiveDays > 30 ? 30 : extendReceiveDays;
        return this.set(K_EXTEND_RECEIVE_DAYS, extendReceiveDays, Integer.class);
    }

    /**
     * Gets invoice.
     *
     * @return the invoice
     */
    public Byte getInvoice() {
        return this.get(K_INVOICE, Byte.class, BYTE_ZERO);
    }

    /**
     * Sets invoice.
     *
     * @param invoice the invoice
     * @return the invoice
     */
    public int setInvoice(Byte invoice) {
        assert (invoice == (byte) 0 || invoice == (byte) 1);
        return this.set(K_INVOICE, invoice, Byte.class);
    }

    /**
     * Gets service terms.
     *
     * @return the service terms
     */
    public Byte getServiceTerms() {
        return this.get(K_SERVICE_TERMS, Byte.class, BYTE_ZERO);
    }

    /**
     * Sets service terms.
     *
     * @param serviceTerms the service terms
     * @return the service terms
     */
    public int setServiceTerms(Byte serviceTerms) {
        assert (serviceTerms == (byte) 0 || serviceTerms == (byte) 1);
        return this.set(K_SERVICE_TERMS, serviceTerms, Byte.class);
    }

    /**
     * Gets service name.
     *
     * @return the service name
     */
    public String getServiceName() {
        return this.get(K_SERVICE_NAME, String.class, "");
    }

    /**
     * Sets service name.
     *
     * @param serviceName the service name
     * @return the service name
     */
    public int setServiceName(String serviceName) {
        assert (serviceName != null);
        return this.set(K_SERVICE_NAME, serviceName, String.class);
    }

    /**
     * Gets service document.
     *
     * @return the service document
     */
    public String getServiceDocument() {
        return this.get(K_SERVICE_DOCUMENT, String.class, "");
    }

    /**
     * Sets service document.
     *
     * @param serviceDocument the service document
     * @return the service document
     */
    public int setServiceDocument(String serviceDocument) {
        assert (serviceDocument != null);
        return this.set(K_SERVICE_DOCUMENT, serviceDocument, String.class);
    }

    /**
     * Gets service choose.
     *
     * @return the service choose
     */
    public Byte getServiceChoose() {
        return this.get(K_SERVICE_CHOOSE, Byte.class, BYTE_ZERO);
    }

    /**
     * Sets service choose.
     *
     * @param serviceChoose the service choose
     * @return the service choose
     */
    public int setServiceChoose(Byte serviceChoose) {
        assert (serviceChoose == (byte) 0 || serviceChoose == (byte) 1);
        return this.set(K_SERVICE_CHOOSE, serviceChoose, Byte.class);
    }

    /**
     * Gets order real name.
     *
     * @return the order real name
     */
    public Byte getOrderRealName() {
        return this.get(K_ORDER_REAL_NAME, Byte.class, BYTE_ZERO);
    }

    /**
     * Sets order real name.
     *
     * @param orderRealName the order real name
     * @return the order real name
     */
    public int setOrderRealName(Byte orderRealName) {
        assert (orderRealName == (byte) 0 || orderRealName == (byte) 1);
        return this.set(K_ORDER_REAL_NAME, orderRealName, Byte.class);
    }

    /**
     * Gets order cid.
     *
     * @return the order cid
     */
    public Byte getOrderCid() {
        return this.get(K_ORDER_CID, Byte.class, BYTE_ZERO);
    }

    /**
     * Sets order cid.
     *
     * @param orderCid the order cid
     * @return the order cid
     */
    public int setOrderCid(Byte orderCid) {
        assert (orderCid == (byte) 0 || orderCid == (byte) 1);
        return this.set(K_ORDER_CID, orderCid, Byte.class);
    }

    /**
     * Gets consignee real name.
     *
     * @return the consignee real name
     */
    public Byte getConsigneeRealName() {
        return this.get(K_CONSIGNEE_REAL_NAME, Byte.class, BYTE_ZERO);
    }

    /**
     * Sets consignee real name.
     *
     * @param consigneeRealName the consignee real name
     * @return the consignee real name
     */
    public int setConsigneeRealName(Byte consigneeRealName) {
        assert (consigneeRealName == (byte) 0 || consigneeRealName == (byte) 1);
        return this.set(K_CONSIGNEE_REAL_NAME, consigneeRealName, Byte.class);
    }

    /**
     * Gets consignee cid.
     *
     * @return the consignee cid
     */
    public Byte getConsigneeCid() {
        return this.get(K_CONSIGNEE_CID, Byte.class, BYTE_ZERO);
    }

    /**
     * Sets consignee cid.
     *
     * @param consigneeCid the consignee cid
     * @return the consignee cid
     */
    public int setConsigneeCid(Byte consigneeCid) {
        assert (consigneeCid == (byte) 0 || consigneeCid == (byte) 1);
        return this.set(K_CONSIGNEE_CID, consigneeCid, Byte.class);
    }

    /**
     * Gets custom.
     *
     * @return the custom
     */
    public Byte getCustom() {
        return this.get(K_CUSTOM, Byte.class, BYTE_ZERO);
    }

    /**
     * Sets custom.
     *
     * @param custom the custom
     * @return the custom
     */
    public int setCustom(Byte custom) {
        assert (custom == (byte) 0 || custom == (byte) 1);
        return this.set(K_CUSTOM, custom, Byte.class);
    }

    /**
     * Gets custom title.
     *
     * @return the custom title
     */
    public String getCustomTitle() {
        return this.get(K_CUSTOM_TITLE, String.class, "");
    }

    /**
     * Sets custom title.
     *
     * @param customTitle the custom title
     * @return the custom title
     */
    public int setCustomTitle(String customTitle) {
        assert (customTitle != null);
        return this.set(K_CUSTOM_TITLE, customTitle, String.class);
    }

    /**
     * Gets order requeire goods package.
     *
     * @return the order requeire goods package
     */
    public GoodsPackageParam getOrderRequireGoodsPackage() {
        return this.getJsonObject(K_ORDER_REQUIRE_GOODS_PACKAGE, new TypeReference<GoodsPackageParam>() {
        }, new GoodsPackageParam());
    }

    /**
     * Sets order requeire goods package.
     *
     * @param orderRequireGoodsPackage the order require goods package
     * @return the order requeire goods package
     */
    public int setOrderRequireGoodsPackage(GoodsPackageParam orderRequireGoodsPackage) {
        assert (orderRequireGoodsPackage != null);
        return this.setJsonObject(K_ORDER_REQUIRE_GOODS_PACKAGE, orderRequireGoodsPackage);
    }

    /**
     * Gets shipping express.
     *
     * @return the shipping express
     */
    public Byte getShippingExpress() {
        return this.get(K_SHIPPING_EXPRESS, Byte.class, BYTE_ZERO);
    }

    /**
     * Sets shipping express.
     *
     * @param shippingExpress the shipping express
     * @return the shipping express
     */
    public int setShippingExpress(Byte shippingExpress) {
        assert (shippingExpress == (byte) 0 || shippingExpress == (byte) 1);
        return this.set(K_SHIPPING_EXPRESS, shippingExpress, Byte.class);
    }

    /**
     * Gets shop address.
     *
     * @return the shop address
     */
    public String getShopAddress() {
        return this.get(K_SHOP_ADDRESS, String.class, "");
    }

    /**
     * Sets shop address.
     *
     * @param shopAddress the shop address
     * @return the shop address
     */
    public int setShopAddress(String shopAddress) {
        assert (shopAddress != null);
        return this.set(K_SHOP_ADDRESS, shopAddress, String.class);
    }


    /**
     * 更新支付方式开关
     */
    public void updatePayment(Map<String, Byte> basicConfig) {
        basicConfig.forEach((k, v) -> {
            db().update(Payment.PAYMENT).set(Payment.PAYMENT.ENABLED, v).where(Payment.PAYMENT.PAY_CODE.eq(k)).execute();
        });
    }

    /**
     * 更新默认支付配置
     *
     * @param param the param
     */
    public void updateDefaultPayConf(PaymentConfigParam param) {
        try {
            this.transaction(() -> {
                if (param.getCardFirst() != null) {
                    this.setCardFirst(param.getCardFirst());
                }
                if (param.getBalanceFirst() != null) {
                    this.setBalanceFirst(param.getBalanceFirst());
                }
                if (param.getScoreFirst() != null) {
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
     *
     * @return the payment enabled
     */
    public List<PaymentConfigVo> getPaymentEnabled() {
        return db().select(Payment.PAYMENT.PAY_CODE, Payment.PAYMENT.PAY_NAME, Payment.PAYMENT.ENABLED)
            .from(Payment.PAYMENT).fetchInto(PaymentConfigVo.class);
    }

    /**
     * 查询默认支付配置
     *
     * @return the default pay conf
     */
    public Map<String, Byte> getDefaultPayConf() {
        Byte cardFirst = this.getCardFirst();
        Byte balanceFirst = this.getBalanceFirst();
        Byte scoreFirst = this.getScoreFirst();
        return new HashMap<String, Byte>(3) {
			private static final long serialVersionUID = -8116540563458868251L;
			{
                put(K_CARD_FIRST, cardFirst);
                put(K_BALANCE_FIRST, balanceFirst);
                put(K_SCORE_FIRST, scoreFirst);
            }
        };
    }

    /**
     * 更新交易流程配置
     *
     * @param param 订单流程配置项信息
     *              {@value com.vpu.mp.service.pojo.shop.config.trade.TradeConstant#FIELD_CLAZZ}
     */
    public void updateOrderProcess(OrderProcessParam param) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(param.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            Arrays.stream(descriptors)
                .filter(des -> !FIELD_CLAZZ.equalsIgnoreCase(des.getName()))
                .forEach((e) -> updateInvoke(e, param, this));
        } catch (IntrospectionException e) {
            log.error("内省获取bean[{}]信息失败：{}", param, e.getMessage());
        }
    }

    /**
     * b2c_shop_cfg配置表配置项通用更新
     * 根据属性名称自动执行置取方法，避免了手动书写每个属性的置取方法
     *
     * @param descriptor 属性描述
     * @param param      更新入参对象实例
     * @param service    执行更新操作的service类实例
     */
    public static void updateInvoke(PropertyDescriptor descriptor, Object param, Object service) {
        String fieldName = descriptor.getName();
        Object conf = null;
        try {
            conf = descriptor.getReadMethod().invoke(param);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("内省获取field[{}]字段属性值：{}", fieldName, e.getMessage());
        }
        if (conf != null) {
            Method method = null;
            String methodName = getMethodName(fieldName, "set");
            try {
                //根据方法名称和方法所需参数类型获取Method实例对象
                method = service.getClass().getMethod(methodName, descriptor.getPropertyType());
            } catch (NoSuchMethodException e) {
                log.error("field[{}]字段对应的setXXX方法[{}]不存在：{}", fieldName, methodName, e.getMessage());
                e.printStackTrace();
            }
            Assert.notNull(method, "Method方法实例获取为空");
            try {
                method.invoke(service, conf);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("field[{}]字段对应的setXXX方法执行失败：{}", fieldName, e.getMessage());
            }
        }
    }

    /**
     * 根据字段名获取置取方法名称
     *
     * @param fieldName 字段名
     * @param args      参数决定得到的是set方法还是get方法
     * @return 置取方法名称
     */
    public static String getMethodName(String fieldName, String args) {
        StringBuilder stringBuilder = new StringBuilder(args);
        char[] temp = fieldName.toCharArray();
        temp[0] -= 32;
        return stringBuilder.append(temp).toString();
    }

    /**
     * 查询交易流程配置
     * {@value com.vpu.mp.service.pojo.shop.config.trade.TradeConstant#FIELD_CLAZZ}
     *
     * @return the order process config
     */
    public OrderProcessParam getOrderProcessConfig() {
        OrderProcessParam param = new OrderProcessParam();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(param.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            Arrays.stream(descriptors)
                .filter(des -> !FIELD_CLAZZ.equalsIgnoreCase(des.getName()))
                .forEach((e) -> selectInvoke(e, param, this));
        } catch (IntrospectionException e) {
            log.error("内省获取bean[{}]信息失败：{}", param, e.getMessage());
        }
        return param;
    }

    /**
     * b2c_shop_cfg配置表配置项查询
     * 根据属性名称自动执行置取方法，避免了手动书写每个属性的置取方法
     *
     * @param descriptor 属性描述
     * @param param      更新入参对象实例
     * @param service    执行查询操作的service类实例
     */
    public static void selectInvoke(PropertyDescriptor descriptor, Object param, Object service) {
        String fieldName = descriptor.getName();
        Method method = null;
        String methodName = getMethodName(fieldName, "get");
        try {
            method = service.getClass().getMethod(methodName);
        } catch (NoSuchMethodException e) {
            log.error("field[{}]字段对应的getXXX方法[{}]不存在：{}", fieldName, methodName, e.getMessage());
            e.printStackTrace();
        }
        Assert.notNull(method, "Method方法实例获取为空");
        Object conf = null;
        try {
            conf = method.invoke(service);
            log.debug("field[{}]字段对应的getXXX方法执行结果为：{}", fieldName, conf);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("field[{}]字段对应的getXXX方法执行失败：{}", fieldName, e.getMessage());
        }
        if (conf != null) {
            try {
                descriptor.getWriteMethod().invoke(param, conf);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("OrderProcessParam类中属性[{}]的set方法执行错误：{}", fieldName, e.getMessage());
            }
        }
    }

    /**
     * 服务条款配置
     *
     * @param document 服务条款配置内容
     *                 数据库保存内容格式为json，样例如下： { 	"document": "此处直接为html页面格式的字符串", 	"update_time": 1568859761 }
     * @throws JsonProcessingException the json processing exception
     *                                 {@value com.vpu.mp.service.pojo.shop.config.trade.TradeConstant#UPDATE_TIME}
     *                                 {@value com.vpu.mp.service.pojo.shop.config.trade.TradeConstant#DOCUMENT}
     */
    public void confTermsOfService(String document) throws JsonProcessingException {
        String serviceDocument = MAPPER.writeValueAsString(new HashMap<String, Object>(2) {
			private static final long serialVersionUID = 7869445296870051056L;
			{
                put(DOCUMENT, document);
                put(UPDATE_TIME, System.currentTimeMillis());
            }
        });
        this.setServiceDocument(serviceDocument);
    }

    /**
     * 查询服务条款配置
     */
    public String getTermsOfService() throws IOException {
        JsonNode node = MAPPER.readTree(this.getServiceDocument());
        return node.get(DOCUMENT).asText("");
    }

    /**
     * 绑定物流公司
     */
    public WxOpenResult bindAccount(BindAccountParam param) throws WxErrorException {
        String jsonParam = StringUtils.EMPTY;
        try {
            jsonParam = MAPPER.writeValueAsString(param);
        } catch (JsonProcessingException e) {
            log.error("对象BindAccountParam[{}]转json字符串失败：{}", param, e.getMessage());
            throw new BusinessException(JsonResultCode.CODE_FAIL);
        }
        return logisticsService.bindAccount(jsonParam);
    }

    /**
     * @throws WxErrorException 微信api调用异常
     *                          {@value com.vpu.mp.service.pojo.shop.config.trade.TradeConstant#DELIVERY_ID}
     *                          {@value com.vpu.mp.service.pojo.shop.config.trade.TradeConstant#DELIVERY_NAME}
     *                          {@value com.vpu.mp.service.pojo.shop.config.trade.TradeConstant#STATUS_CODE}
     */
    public List<LogisticsAccountInfo> combineAllLogisticsAccountInfo() throws WxErrorException {
        //已绑定账号物流公司列表，不包含未绑定物流公司
        List<LogisticsAccountInfo> accountInfos = logisticsService.getAllAccount();
        //目前支持的快递公司列表，只包含id和name，不含其他详细信息
        List<Map<String, String>> allDelivery = logisticsService.getAllDelivery();
        //组合上述两个列表信息得到返回果
        Map<String, String> temp = new HashMap<String, String>(allDelivery.size()) {
			private static final long serialVersionUID = 6286507281294185183L;
			{
                allDelivery.forEach(d -> put(d.get(DELIVERY_ID), d.get(DELIVERY_NAME)));
            }
        };
        accountInfos.forEach(info -> {
            String deliveryId = info.getDeliveryId();
            if (temp.containsKey(deliveryId)) {
                info.setDeliveryName(temp.get(deliveryId));
                temp.remove(deliveryId);
            }
        });
        temp.forEach((k, v) -> accountInfos.add(new LogisticsAccountInfo(k, v, STATUS_CODE)));
        return accountInfos;
    }
}
