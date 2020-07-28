package com.vpu.mp.service.shop.order.action;

import com.beust.jcommander.internal.Lists;
import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.BigDecimalUtil;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.dao.shop.order.OrderMedicalHistoryDao;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.IncrSequenceUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.market.freeshipping.FreeShippingVo;
import com.vpu.mp.service.pojo.shop.market.insteadpay.InsteadPay;
import com.vpu.mp.service.pojo.shop.market.presale.PreSaleVo;
import com.vpu.mp.service.pojo.shop.market.presale.PresaleConstant;
import com.vpu.mp.service.pojo.shop.member.address.UserAddressVo;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.card.ValidUserCardBean;
import com.vpu.mp.service.pojo.shop.member.card.create.CardFreeship;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.calculate.UniteMarkeingtRecalculateBo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.payment.PaymentVo;
import com.vpu.mp.service.pojo.shop.prescription.config.PrescriptionConstant;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import com.vpu.mp.service.pojo.wxapp.cart.activity.OrderCartProductBo;
import com.vpu.mp.service.pojo.wxapp.order.CreateOrderBo;
import com.vpu.mp.service.pojo.wxapp.order.CreateOrderVo;
import com.vpu.mp.service.pojo.wxapp.order.CreateParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam.Goods;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeVo;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.fullreduce.OrderFullReduce;
import com.vpu.mp.service.pojo.wxapp.order.marketing.packsale.OrderPackageSale;
import com.vpu.mp.service.pojo.wxapp.order.marketing.presale.OrderPreSale;
import com.vpu.mp.service.pojo.wxapp.order.must.OrderMustVo;
import com.vpu.mp.service.pojo.wxapp.pay.base.WebPayVo;
import com.vpu.mp.service.shop.activity.dao.PreSaleProcessorDao;
import com.vpu.mp.service.shop.activity.factory.OrderCreateMpProcessorFactory;
import com.vpu.mp.service.shop.activity.processor.GiftProcessor;
import com.vpu.mp.service.shop.card.wxapp.WxCardExchangeService;
import com.vpu.mp.service.shop.config.InsteadPayConfigService;
import com.vpu.mp.service.shop.config.ShopReturnConfigService;
import com.vpu.mp.service.shop.config.TradeService;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.distribution.OrderGoodsRebateService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.market.freeshipping.FreeShippingService;
import com.vpu.mp.service.shop.member.AddressService;
import com.vpu.mp.service.shop.member.BaseScoreCfgService;
import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.order.action.base.Calculate;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.atomic.AtomicOperation;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.invoice.InvoiceService;
import com.vpu.mp.service.shop.order.must.OrderMustService;
import com.vpu.mp.service.shop.order.trade.OrderPayService;
import com.vpu.mp.service.shop.payment.PaymentService;
import com.vpu.mp.service.shop.prescription.UploadPrescriptionService;
import com.vpu.mp.service.shop.store.store.StoreService;
import com.vpu.mp.service.shop.user.cart.CartService;
import jodd.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.Record3;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.vpu.mp.service.pojo.shop.order.OrderConstant.DELIVER_TYPE_COURIER;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.NO;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.YES;

/**
 * 下单逻辑处理
 *
 * @author: ws
 * @create: 2019-10-23 16:15
 **/
@Component
public class CreateService extends ShopBaseService implements IorderOperate<OrderBeforeParam, CreateParam> {

    @Autowired
    private OrderInfoService orderInfo;

    @Autowired
    private AddressService address;

    @Autowired
    private TradeService tradeCfg;

    @Autowired
    private ShopReturnConfigService returnCfg;

    @Autowired
    private UserCardService userCard;

    @Autowired
    private GoodsSpecProductService goodsSpecProduct;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderGoodsService orderGoods;

    @Autowired
    private StoreService store;

    @Autowired
    private Calculate calculate;

    @Autowired
    private PaymentService payment;

    @Autowired
    private BaseScoreCfgService scoreCfg;

    @Autowired
    private MemberService member;

    @Autowired
    private InvoiceService invoice;

    @Autowired
    private CouponService coupon;

    @Autowired
    private OrderMustService must;

    @Autowired
    private OrderPayService orderPay;

    @Autowired
    private AtomicOperation atomicOperation;

    @Autowired
    private CartService cart;

    @Autowired
    private GiftProcessor giftProcessor;

    @Autowired
    private FreeShippingService freeShippingService;

    @Autowired
    private InsteadPayConfigService insteadPayConfig;

    @Autowired
    private PreSaleProcessorDao preSaleProcessorDao;

    @Autowired
    private OrderGoodsRebateService orderGoodsRebate;

    @Autowired
    private WxCardExchangeService  cardExchange;
    @Autowired
    private UploadPrescriptionService uploadPrescriptionService;

    /**
     * 营销活动processorFactory
     */
   @Autowired
    private OrderCreateMpProcessorFactory marketProcessorFactory;
    @Autowired
    private OrderMedicalHistoryDao orderMedicalHistoryDao;


    @Override
    public OrderServiceCode getServiceCode() {
        return OrderServiceCode.CREATE;
    }

    /**
     * 订单创建前，结算页面数据查询
     */
    @Override
    public Object query(OrderBeforeParam param) throws MpException {
        OrderBeforeVo result = init(param);
        // process
        processQueryParam(param, result);
        return result;
    }

    @Override
    public ExecuteResult execute(CreateParam param) {
        logger().info("下单start");
        //初始化好友代付配置
        param.setInsteadPayCfg(insteadPayConfig.getInsteadPayConfig());
        //vo
        CreateOrderVo createVo = new CreateOrderVo();
        //初始化bo
        CreateOrderBo orderBo;
        //order goods
        List<OrderGoodsBo> orderGoodsBos;
        //OrderBeforeVo
        OrderBeforeVo orderBeforeVo = OrderBeforeVo.builder().deliverType(param.getDeliverType()).build();
        //订单入库后数据
        OrderInfoRecord orderAfterRecord = null;

        //order before data ready
        try {
            orderBo = processPrepairCreateOrder(param, orderBeforeVo);
        } catch (MpException e) {
            return ExecuteResult.create(e.getErrorCode(), e.getErrorResult(),  e.getCodeParam());
        }
        //生成orderSn
        String orderSn = IncrSequenceUtil.generateOrderSn(OrderConstant.ORDER_SN_PREFIX);
        AtomicBoolean isAddLock = new AtomicBoolean(false);
        try{
            //record入库
            transaction(()->{
                //初始化订单（赋值部分数据）
                OrderInfoRecord order = orderInfo.addRecord(orderSn, param, orderBo, orderBo.getOrderGoodsBo(), orderBeforeVo);
                //普通营销活动处理
                processNormalActivity(order, orderBo, orderBeforeVo);
                //计算其他数据（需关联去其他模块）
                setOtherValue(param,order, orderBo, orderBeforeVo);
                //商品退款退货配置
                calculate.setReturnCfg(orderBo.getOrderGoodsBo(), orderBo.getOrderType(), order);
                //订单类型拼接(支付有礼)
                order.setGoodsType(OrderInfoService.getGoodsTypeToInsert(orderBo.getOrderType()));
                //保存营销活动信息 订单状态以改变（该方法不要在并发情况下出现临界资源）
                marketProcessorFactory.processSaveOrderInfo(param,order);
                //订单入库,以上只有orderSn，无法获取orderId
                order.store();
                order.refresh();
                addOrderGoodsRecords(order, orderBo.getOrderGoodsBo());
                addOrderMedicalHistory(param,order);
                //支付系统金额
                orderPay.payMethodInSystem(order, order.getUseAccount(), order.getScoreDiscount(), order.getMemberCardBalance());
                //必填信息
                must.addRecord(param.getMust());
                orderBo.setOrderId(order.getOrderId());
                //待发货、拼团中、预售支付定金或支付完成
                isAddLock.set(processEffective(param, orderBo, order));
            });
            orderAfterRecord = orderInfo.getRecord(orderBo.getOrderId());
            createVo.setOrderSn(orderAfterRecord.getOrderSn());
        } catch (DataAccessException e) {
            logger().error("下单捕获mp异常", e);
            Throwable cause = e.getCause();
            if (cause instanceof MpException) {
                return ExecuteResult.create(((MpException) cause).getErrorCode(), ((MpException) cause).getErrorResult(), ((MpException) cause).getCodeParam());
            } else {
                return ExecuteResult.create(JsonResultCode.CODE_ORDER_DATABASE_ERROR, e.getMessage());
            }
        } catch (Exception e) {
            logger().error("下单捕获mp异常", e);
            return ExecuteResult.create(JsonResultCode.CODE_ORDER, null);
        } finally {
            if(isAddLock.get()) {
                //释放锁
                logger().info("释放锁{}",orderSn);
                atomicOperation.releaseLocks();
            }
        }
        //购物车删除
        if(OrderConstant.CART_Y.equals(param.getIsCart())){
            cart.removeCartByProductIds(param.getWxUserInfo().getUserId(), param.getProductIds());
        }
        //TODO 欧派、嗨购、CRM、自动同步订单微信购物单
        try {
            WebPayVo continuePay = orderPay.isContinuePay(orderAfterRecord,
                    orderAfterRecord.getOrderSn(),
                    orderAfterRecord.getMoneyPaid(),
                    orderPay.getGoodsNameForPay(orderAfterRecord, orderBo.getOrderGoodsBo()),
                    param.getClientIp(),
                    param.getWxUserInfo().getWxUser().getOpenId(),
                    param.getActivityType());
            createVo.setWebPayVo(continuePay);
            return ExecuteResult.create(createVo);
        } catch (MpException e) {
            return ExecuteResult.create(e.getErrorCode(), null);
        }
    }

    private void addOrderMedicalHistory(CreateParam param, OrderInfoRecord order) {
        if (order.getOrderAuditType().equals(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_CREATE)){
            logger().info("待开方订单保存患者信息");
            param.getPatientDiagnose().setOrderId(order.getOrderId());
            orderMedicalHistoryDao.save(param.getPatientDiagnose());
        }
    }

    private CreateOrderBo processPrepairCreateOrder(CreateParam param, OrderBeforeVo orderBeforeVo) throws MpException {
        CreateOrderBo orderBo;
        List<OrderGoodsBo> orderGoodsBos;//初始化paramGoods
        initParamGoods(param);
        //init
        orderBo = initCreateOrderBo(param);
        //校验
        checkCreateOrderBo(orderBo, param);
        //设置规格和商品信息、基础校验规格与商品
        processParamGoods(param, param.getWxUserInfo().getUserId(), param.getStoreId());
        //TODO 营销相关 活动校验或活动参数初始化
        marketProcessorFactory.processInitCheckedOrderCreate(param);
        //下架商品校验
        checkGoodsIsOnSale(param.getGoods());
        if(null != param.getActivityId() && null != param.getActivityType()) {
            //活动生成ordergodos;
            orderGoodsBos = initOrderGoods(param, param.getGoods(), param.getStoreId());
        }else {
            //TODO (统一入口处理)普通商品下单，不指定唯一营销活动时的订单处理（需要考虑首单特惠、限时降价、会员价、赠品、满折满减直接下单）
            //初始化订单商品
            orderGoodsBos = initOrderGoods(param, param.getGoods(), param.getWxUserInfo().getUserId(), param.getMemberCardNo(), param.createOrderCartProductBo(), param.getStoreId());
        }
        //生成订单商品后校验必填项
        checkMust(orderGoodsBos, param);

        orderBo.setOrderGoodsBo(orderGoodsBos);

        //处理orderBeforeVo
        orderBeforeVo.setAddress(orderBo.getAddress());
        processOrderBeforeVo(param, orderBeforeVo, orderBo.getOrderGoodsBo());
        //校验
        checkOrder(orderBeforeVo, orderBo, param);
        return orderBo;
    }

    private OrderBeforeVo init(OrderBeforeParam param) throws MpException {
        OrderBeforeVo result = OrderBeforeVo.builder().build();
        // 地址
        result.setAddress(getDefaultAddress(param.getWxUserInfo().getUserId(), param.getAddressId()));
        // 支持的配送方式
        result.setExpressList(getExpressList());
        //初始化paramGoods
        initParamGoods(param);
        return result;
    }

    /**
     * 订单商品入库（存在分销则分销商品更新recid）
     * @param order
     * @param orderGoodsBo
     */
    private void addOrderGoodsRecords(OrderInfoRecord order, List<OrderGoodsBo> orderGoodsBo) {
        orderGoodsRebate.save(orderGoodsBo, orderGoods.addRecords(order, orderGoodsBo));
    }

    private void checkMust(List<OrderGoodsBo> orderGoodsBos, CreateParam param) throws MpException {
        OrderMustVo orderMust = calculate.getOrderMust(orderGoodsBos);
        if(orderMust.getIsShow() == NO) {
            param.setMust(null);
            return;
        }
        if(param.getMust() == null) {
            throw new MpException(JsonResultCode.CODE_ORDER_MUST_NOT_NULL);
        }
        if(orderMust.getOrderRealName() == YES && StringUtils.isBlank(param.getMust().getOrderRealName())) {
            throw new MpException(JsonResultCode.CODE_ORDER_MUST_NOT_NULL);
        }
        if(orderMust.getConsigneeCid() == YES && StringUtils.isBlank(param.getMust().getConsigneeCid())) {
            throw new MpException(JsonResultCode.CODE_ORDER_MUST_NOT_NULL);
        }
        if(orderMust.getConsigneeRealName() == YES && StringUtils.isBlank(param.getMust().getConsigneeRealName())) {
            throw new MpException(JsonResultCode.CODE_ORDER_MUST_NOT_NULL);
        }
        if(orderMust.getCustom() == YES && StringUtils.isBlank(param.getMust().getCustom())) {
            throw new MpException(JsonResultCode.CODE_ORDER_MUST_NOT_NULL);
        }
        if(orderMust.getOrderCid() == YES && StringUtils.isBlank(param.getMust().getOrderCid())) {
            throw new MpException(JsonResultCode.CODE_ORDER_MUST_NOT_NULL);
        }
    }

    /**
     * 初始化
     * @param param CreateParam
     * @return CreateOrderBo
     */
    private CreateOrderBo initCreateOrderBo(CreateParam param) throws MpException {
        logger().info("初始化CreateOrderBo,start-end");
        ArrayList<Byte> type = new ArrayList<Byte>();
        if(null != param.getActivityId() && null != param.getActivityType()) {
            type.add(param.getActivityType());
        }
        return CreateOrderBo.builder().
            //地址
            address(address.get(param.getAddressId(), param.getWxUserInfo().getUserId())).
            //门店
            store(param.getStoreId() == null ? null : store.getStore(param.getStoreId())).
            //支付方式
            payment(param.getOrderPayWay() == null || Byte.valueOf(OrderConstant.PAY_WAY_FRIEND_PAYMENT).equals(param.getOrderPayWay()) ? null : payment.getPaymentInfo(OrderConstant.MP_PAY_CODE_TO_STRING[param.getOrderPayWay()])).
            //发票
            invoice(param.getInvoiceId() == null ? null : invoice.get(param.getInvoiceId())).
            //TODO 自提时间

            // 会员卡
                currentMember(StringUtil.isBlank(param.getMemberCardNo()) ? null : userCard.userCardDao.getValidByCardNo(param.getMemberCardNo())).
            //优惠卷
                currentCupon(StringUtil.isBlank(param.getCouponSn()) ? null : coupon.getValidCoupons(param.getCouponSn())).
            orderType(type).
            build();
    }

    /**
     * 校验
     * @param bo 业务对象
     * @param param 参数
     * @throws MpException 见throw语句
     */
    public void checkCreateOrderBo(CreateOrderBo bo, CreateParam param) throws MpException {
        logger().info("校验checkCreateOrderBo,start");
        //非送礼 非门店 校验地址
        if(bo.getOrderType().contains(BaseConstant.ACTIVITY_TYPE_GIVE_GIFT) && bo.getStore() == null && bo.getAddress() == null){
            throw new MpException(JsonResultCode.CODE_ORDER_ADDRESS_NO_NULL);
        }
        //会员卡失效
        if(StringUtil.isNotBlank(param.getMemberCardNo()) && bo.getCurrentMember() == null) {
            throw new MpException(JsonResultCode.CODE_ORDER_CARD_INVALID);
        }
        //优惠卷失效
        if(StringUtil.isNotBlank(param.getCouponSn()) && bo.getCurrentCupon() == null) {
            throw new MpException(JsonResultCode.CODE_ORDER_COUPON_INVALID);
        }
        //好友代付校验
        if(param.getOrderPayWay().equals(OrderConstant.PAY_WAY_FRIEND_PAYMENT)) {
            InsteadPay cfg = param.getInsteadPayCfg();
            if(Boolean.FALSE.equals(cfg.getStatus())) {
                //不支持好友代付
                throw new MpException(JsonResultCode.CODE_ORDER_PAY_WAY_NO_SUPPORT_INSTEAD_PAY);
            }
            if(param.getInsteadPayNum() == null) {
                //代付类型错误
                throw new MpException(JsonResultCode.CODE_ORDER_PAY_WAY_NO_SUPPORT_INSTEAD_PAY_WAY);
            }else if(param.getInsteadPayNum() == 0 && Boolean.FALSE.equals(cfg.getMultiplePay())) {
                //多人
                throw new MpException(JsonResultCode.CODE_ORDER_PAY_WAY_NO_SUPPORT_INSTEAD_PAY_WAY);
            }else if(param.getInsteadPayNum() == 1 && Boolean.FALSE.equals(cfg.getSinglePay())) {
                //单人
                throw new MpException(JsonResultCode.CODE_ORDER_PAY_WAY_NO_SUPPORT_INSTEAD_PAY_WAY);
            }
            if(BigDecimalUtil.compareTo(param.getOrderAmount(), null) <= 0) {
                throw new MpException(JsonResultCode.CODE_ORDER_PAY_WAY_NO_SUPPORT_INSTEAD_PAY_MONEY_ZERO);
            }
            param.setInsteadPayCfg(cfg);
        }
        logger().info("校验checkCreateOrderBo,end");
    }

    /**
     * 获取默认地址
     *  规则:默认地址>上次下单地址>null
     *
     * @param userId
     * @param addressId
     * @return
     */
    public UserAddressVo getDefaultAddress(Integer userId, Integer addressId) {
        UserAddressVo defaultAddress = null;
        if (addressId != null) {
            defaultAddress = address.get(addressId, userId);
        }
        // 输入地址无效
        if (defaultAddress == null) {
            //先查默认地址
            defaultAddress = address.getefaultAddress(userId);
            if(defaultAddress == null) {
                //上次下单地址
                defaultAddress = orderInfo.getLastOrderAddress(userId);
            }
        }
        return defaultAddress;
    }

    /**
     * 配送方式
     *
     * @return
     */
    public Byte[] getExpressList() {
        Byte[] expressList = new Byte[]{0 , 0 , 0};
        // 快递
        expressList[DELIVER_TYPE_COURIER] = tradeCfg.getExpress();
        // 自提
        expressList[OrderConstant.DELIVER_TYPE_SELF] = tradeCfg.getFetch();
        //TODO 同城配送
        // expressList[OrderConstant.DELIVER_TYPE_COURIER] =
        // trade.getCityExpressService();
        return expressList;
    }

    /**
     * init param goods
     * @param param
     */
    public void initParamGoods(OrderBeforeParam param) throws MpException {
        if(OrderConstant.CART_Y.equals(param.getIsCart())) {
            //购物车结算初始化商品
            param.setGoods(cart.getCartCheckedData(param.getWxUserInfo().getUserId(), param.getStoreId() == null ? NumberUtils.INTEGER_ZERO : param.getStoreId()));
        }
    }

    /**
     * 处理查询方法
     * @param param
     * @param vo
     * @throws MpException
     */
    public void processQueryParam(OrderBeforeParam param, OrderBeforeVo vo) throws MpException {

        //初始化所有可选支付方式
        param.setPaymentList(payment.getSupportPayment());
        //好友代付
        param.setInsteadPayCfg(insteadPayConfig.getInsteadPayConfig());
        //设置规格和商品信息、基础校验规格与商品
        processParamGoods(param, param.getWxUserInfo().getUserId(), param.getStoreId());
        //TODO 营销相关 活动校验或活动参数初始化
        marketProcessorFactory.processInitCheckedOrderCreate(param);
        //下架商品校验
        checkGoodsIsOnSale(param.getGoods());
        if(null != param.getActivityId() && null != param.getActivityType()) {
            //活动生成ordergodos;
            vo.setOrderGoods(initOrderGoods(param, param.getGoods(), param.getStoreId()));
        }else {
            //TODO (统一入口处理)普通商品下单，不指定唯一营销活动时的订单处理（需要考虑首单特惠、限时降价、会员价、赠品、满折满减直接下单）
            //初始化订单商品
            vo.setOrderGoods(initOrderGoods(param, param.getGoods(), param.getWxUserInfo().getUserId(), param.getMemberCardNo(), param.createOrderCartProductBo(), param.getStoreId()));
        }
        //据处理过的param和其他信息填充下单确认页返回信息
        processBeforeVoInfo(param,param.getWxUserInfo().getUserId(), param.getStoreId(),vo);
    }

    /**
     * 下架商品校验(因为有些活动下架仍可以下单，所以此类processInitCheckedOrderCreate中可以将goods.getGoodsInfo().getIsOnSale()字段设置为已上架)
     * @param goodsList
     * @throws MpException
     */
    private void checkGoodsIsOnSale(List<Goods> goodsList) throws MpException {
        for (Goods goods: goodsList) {
            if (!GoodsConstant.ON_SALE.equals(goods.getGoodsInfo().getIsOnSale())) {
                logger().error("checkGoodsAndProduct,商品已下架,id:" + goods.getGoodsInfo().getGoodsId());
                throw new MpException(JsonResultCode.CODE_ORDER_GOODS_NO_SALE, null , goods.getGoodsInfo().getGoodsName());
            }
        }
    }

    /**
     * 非营销商品处理（query/execute）
     * @param param
     * @param userId
     * @param storeId
     * @throws MpException
     */
    public void processParamGoods(OrderBeforeParam param, Integer userId, Integer storeId) throws MpException {
        logger().info("processParamGoods start");
        //规格信息,key proId
        Map<Integer, GoodsSpecProductRecord> productInfo = goodsSpecProduct.selectSpecByProIds(param.getProductIds(), storeId);
        //goods type,key goodsId
        Map<Integer, GoodsRecord> goods = goodsService.getGoodsToOrder(param.getGoodsIds());
        //赋值
        for (Goods temp : param.getGoods()) {
            temp.setProductInfo(productInfo.get(temp.getProductId()));
            temp.setGoodsInfo(goods.get(temp.getGoodsId()));
            //校验
            checkGoodsAndProduct(temp);
            //初始化商品价格
            temp.setProductPrice(temp.getProductInfo().getPrdPrice());
        }
        logger().info("processParamGoods end");
    }

    /**
     * 唯一性的营销活动下单
     * @param param
     * @param storeId
     * @throws MpException
     */
    private void processParamGoodsByMarket(OrderBeforeParam param, Integer storeId) throws MpException {
        //赋值
        for (Goods temp : param.getGoods()) {

        }
    }

    /**
     * 根据处理过的param和其他信息填充下单确认页返回信息
     * @param param
     * @param userId
     * @param storeId
     * @param vo
     */
    private void processBeforeVoInfo(OrderBeforeParam param, Integer userId, Integer storeId, OrderBeforeVo vo) throws MpException {
        vo.setInsteadPayCfg(param.getInsteadPayCfg());
        //默认选择配送方式
        vo.setDeliverType(Objects.isNull(param.getDeliverType()) ? vo.getDefaultDeliverType() : param.getDeliverType());
        //配送方式支持的门店列表（自提、同城配送）
        List<StorePojo>[] storeLists = store.filterExpressList(vo.getExpressList(), param.getProductIds(), vo.getAddress(), NO);
        //处理配送方式及门店信息;设置门店列表
        processExpressList(storeLists, vo);
        //计算金额相关、vo赋值
        processOrderBeforeVo(param, vo, vo.getOrderGoods());
        //赠品活动
        processBeforeUniteActivity(param, vo);
        //下单页面显示积分兑换金额时去除积分,结算不做此逻辑（只是为了展示方便）
        if(BaseConstant.ACTIVITY_TYPE_INTEGRAL.equals(param.getActivityType())) {
            vo.getOrderGoods().forEach(x-> x.setGoodsPrice(x.getGoodsScore() != null && x.getGoodsScore() > 0 ? BigDecimalUtil.subtrac(x.getDiscountedGoodsPrice(), BigDecimalUtil.divide(new BigDecimal(x.getGoodsScore()), new BigDecimal(vo.getScoreProportion()))) : x.getDiscountedGoodsPrice()));
        }

        // 积分使用规则
        setScorePayRule(vo);
        //支付方式
        if(param.getPaymentList() != null){
            vo.setPaymentList(param.getPaymentList());
        }else {
            vo.setPaymentList(payment.getSupportPayment());
        }
        //订单必填信息处理
        vo.setMust(calculate.getOrderMust(vo.getOrderGoods()));
        //服务条款
        vo.setTerm(calculate.getTermsofservice());
        //处方信息
        vo.setPrescriptionList(param.getPrescriptionList());
        vo.setOrderMedicalType(param.getOrderMedicalType());
        vo.setCheckPrescriptionStatus(param.getCheckPrescriptionStatus());
        vo.setOrderAuditType(param.getOrderAuditType());
        vo.setPatientInfo(param.getPatientInfo());
    }

    private void processBeforeUniteActivity(OrderBeforeParam param, OrderBeforeVo vo) {
        //TODO 送赠品(处理门店)
        getGifts(vo, param.getStoreId(), param.getWxUserInfo().getUserId(), vo.getOrderType());
    }

    /**
     * 营销、非营销处理后初始化orderGoods对象
     * @param param 结算参数
     * @param goods 购买商品列表
     * @param userId 会员id
     * @param memberCardNo 会员卡no
     * @param uniteMarkeingtBo 统一营销优惠信息
     * @param storeId   门店id
     * @throws MpException 校验异常
     * @return  bo
     */
    public List<OrderGoodsBo> initOrderGoods(OrderBeforeParam param, List<Goods> goods, Integer userId, String memberCardNo, OrderCartProductBo uniteMarkeingtBo, Integer storeId) throws MpException {
        logger().info("initOrderGoods开始");
        List<OrderGoodsBo> boList = new ArrayList<>(goods.size());
        for (Goods temp : goods) {
            //TODO 预售商品，不支持现购买 && !$storeId
            if(BaseConstant.ACTIVITY_TYPE_PRE_SALE.equals(temp.getGoodsInfo().getGoodsType())) {
                preSaleCheck(temp);
            }
            //非加价购改价(加价购唯一入口在购物车)
            if(!BaseConstant.ACTIVITY_TYPE_PURCHASE_GOODS.equals(temp.getCartType())) {
                //TODO 扫码构改规格信息(前面查规格时已经用门店规格信息覆盖商品规格信息)
                UniteMarkeingtRecalculateBo calculateResult = calculate.uniteMarkeingtRecalculate(temp, uniteMarkeingtBo.get(temp.getProductId()),userId);
                logger().info("calculateResult:{}", calculateResult);
                //数量限制
                goodsNumLimit(temp);
                temp.setGoodsPrice(calculateResult.getPrice());
                temp.setProductPrice(calculateResult.getPrice());
                temp.setGoodsPriceAction(calculateResult.getActivityType());
            }
            //TODO temp goodsprice 取规格
            boList.add(orderGoods.initOrderGoods(temp));
        }
        param.setBos(boList);
        return boList;
    }

    private void preSaleCheck(Goods temp) throws MpException {
        // key:商品id，value:List<Record3<Integer, Integer, BigDecimal>> PRESALE.ID, PRESALE.GOODS_ID, PRESALE_PRODUCT.PRESALE_PRICE
        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> goodsPreSaleListInfo = preSaleProcessorDao.getGoodsPreSaleListInfo(Lists.newArrayList(temp.getGoodsId()), DateUtils.getLocalDateTime());
        if(MapUtils.isNotEmpty(goodsPreSaleListInfo)) {
            //当前商品对应的预售信息
            List<Record3<Integer, Integer, BigDecimal>> preSaleInfos = goodsPreSaleListInfo.get(temp.getGoodsId());
            if(CollectionUtils.isNotEmpty(preSaleInfos)) {
                for (Record3<Integer, Integer, BigDecimal> info : preSaleInfos) {
                    PreSaleVo detail = preSaleProcessorDao.getDetail(info.get(1, Integer.class));
                    if(detail.getBuyType().equals(NO)) {
                        throw new MpException(JsonResultCode.CODE_ORDER_PRESALE_GOODS_NOT_SUPORT_BUY, "为预售商品，不支持现购", temp.getGoodsInfo().getGoodsName());
                    }
                }
            }

        }
    }

    /**
     * 商品限购
     * @param temp
     * @throws MpException
     */
    private void goodsNumLimit(Goods temp) throws MpException {
        //TODO 非加价购 && 非限次卡
        if(BaseConstant.ACTIVITY_TYPE_PURCHASE_GOODS.equals(temp.getCartType())) {
            return;
        }
        if (!Boolean.TRUE.equals(temp.getIsAlreadylimitNum())) {
            if (temp.getGoodsInfo().getLimitBuyNum() > 0 && temp.getGoodsNumber() < temp.getGoodsInfo().getLimitBuyNum()) {
                throw new MpException(JsonResultCode.CODE_ORDER_GOODS_LIMIT_MIN, "最小限购", temp.getGoodsInfo().getGoodsName(), temp.getGoodsInfo().getLimitBuyNum().toString());
            }
            if (temp.getGoodsInfo().getLimitMaxNum() > 0 && temp.getGoodsNumber() > temp.getGoodsInfo().getLimitMaxNum()) {
                throw new MpException(JsonResultCode.CODE_ORDER_GOODS_LIMIT_MAX, "最大限购", temp.getGoodsInfo().getGoodsName(), temp.getGoodsInfo().getLimitMaxNum().toString());
            }
        }
    }

    /**
     * 营销、非营销处理后初始化orderGoods对象
     * @param param 结算参数
     * @param goods 购买商品列表
     * @param storeId   门店id
     * @throws MpException 校验异常
     * @return  bo
     */
    public List<OrderGoodsBo> initOrderGoods(OrderBeforeParam param, List<Goods> goods,Integer storeId) throws MpException {
        logger().info("initOrderGoods开始");
        List<OrderGoodsBo> boList = new ArrayList<>(goods.size());
        for (Goods temp : goods) {
            goodsNumLimit(temp);
            OrderGoodsBo bo = orderGoods.initOrderGoods(temp);
            boList.add(bo);
        }
        param.setBos(boList);
        return boList;
    }

    /**
     * 校验商品和规格信息（商品已下架校验等待process处理checkAndInit后校验
     * @param goods
     * @throws MpException
     */
    public void checkGoodsAndProduct(Goods goods) throws MpException {
        if (goods.getGoodsInfo() == null || goods.getProductInfo() == null || goods.getGoodsInfo().getDelFlag() == DelFlag.DISABLE.getCode()) {
            logger().error("checkGoodsAndProduct,商品不存在");
            throw new MpException(JsonResultCode.CODE_ORDER_GOODS_NOT_EXIST, null, Util.toJson(goods));
        }
        if (goods.getGoodsNumber() > goods.getProductInfo().getPrdNumber()) {
            logger().error("checkGoodsAndProduct,库存不足,id:" + goods.getProductId());
            throw new MpException(JsonResultCode.CODE_ORDER_GOODS_OUT_OF_STOCK, null, goods.getGoodsInfo().getGoodsName());
        }
        if (goods.getGoodsNumber() == null || goods.getGoodsNumber() <= 0) {
            logger().error("checkGoodsAndProduct,商品数量不能为0,id:" + goods.getProductId());
            throw new MpException(JsonResultCode.CODE_ORDER_GOODS_NO_ZERO, null, goods.getGoodsInfo().getGoodsName());
        }
    }

    /**
     * 处理配送方式及门店信息;设置门店列表
     * @param storeLists 门店
     * @param vo vo
     */
    public void processExpressList(List<StorePojo>[] storeLists, OrderBeforeVo vo){

        //自提、同城快递处理
        for (int i = 1, length = vo.getExpressList().length ; i < length ; i++){
            if(CollectionUtils.isEmpty(storeLists[i])){
                //不支持该配送方式
                vo.getExpressList()[i] = NO;
            }else if(vo.getDeliverType() == i){
                /*
                 *   TODO 处理当前配送方式的门店列表
                 *   1.自提:根据前端传的经纬度排序（当前门店、距离）
                 *   2.同城配送：根据vo.getAddress()地址排序（当前门店、距离）
                 */
                vo.setStoreList(null);
            }
        }
    }

    /**
     * 金额处理 赋值
     * @param param
     * @param vo
     * @param bos
     */
    public void processOrderBeforeVo(OrderBeforeParam param, OrderBeforeVo vo, List<OrderGoodsBo> bos) throws MpException {
        logger().info("金额处理赋值(processOrderBeforeVo),start");
        //积分兑换比
        Integer scoreProportion = scoreCfg.getScoreProportion();
        //积分抵扣金额()
        BigDecimal scoreDiscount =
            BigDecimalUtil.divide(new BigDecimal(param.getScoreDiscount() == null ? 0: param.getScoreDiscount()), new BigDecimal(scoreProportion));
        //余额抵扣金额
        BigDecimal useAccount = param.getBalance();
        //会员卡抵扣金额
        BigDecimal cardBalance = param.getCardBalance();
        //总价、总数量
        BigDecimal[] tolalNumberAndPrice = calculate.getTolalNumberAndPriceByType(bos, null, null);
        //预售处理
        OrderPreSale orderPreSale = calculate.calculatePreSale(param, bos, tolalNumberAndPrice, vo);
        //团长优惠
        BigDecimal grouperCheapReduce = param.getGoods().stream().map(Goods::getGrouperTotalReduce).reduce(BigDecimal.ZERO, BigDecimalUtil::add);
        BigDecimal preSaleDiscount = calculate.calculateOrderGoodsDiscount(orderPreSale, OrderConstant.D_T_FULL_PRE_SALE);
        //满折满减特殊处理
        List<OrderFullReduce> orderFullReduces = calculate.calculateFullReduce(param, bos);
        BigDecimal fullReduceDiscount = BigDecimal.ZERO;
        for (OrderFullReduce orderFullReduce: orderFullReduces) {
            fullReduceDiscount = fullReduceDiscount.add(calculate.calculateOrderGoodsDiscount(orderFullReduce, OrderConstant.D_T_FULL_REDUCE));
        }
        //打包一口价处理
        OrderPackageSale orderPackageSale  = calculate.calculatePackageSale(param, bos, tolalNumberAndPrice, vo);
        BigDecimal packageSaleDiscount = orderPackageSale != null ? orderPackageSale.getTotalDiscount() : BigDecimal.ZERO;
        //处理会员卡
        calculate.calculateCardInfo(param, vo);
        //处理当前会员卡
        BigDecimal memberDiscount;
        if (BaseConstant.ACTIVITY_TYPE_PACKAGE_SALE.equals(param.getActivityType()) && orderPackageSale != null) {
            //打包一口价禁用会员卡折扣
            memberDiscount = null;
        } else {
            memberDiscount = calculate.calculateOrderGoodsDiscount(vo.getDefaultMemberCard(), OrderConstant.D_T_MEMBER_CARD);
        }

        //处理优惠卷
        calculate.calculateCoupon(param, vo);
        //处理当前优惠卷
        BigDecimal couponDiscount = calculate.calculateOrderGoodsDiscount(vo.getDefaultCoupon(), OrderConstant.D_T_COUPON);
        //运费计算、包邮处理逻辑
        shippingFeeLogic(param, vo, bos, tolalNumberAndPrice);
        //折扣商品金额
        BigDecimal tolalDiscountAfterPrice = BigDecimalUtil.addOrSubtrac(
            BigDecimalUtil.BigDecimalPlus.create(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_PRICE], BigDecimalUtil.Operator.subtrac),
            BigDecimalUtil.BigDecimalPlus.create(memberDiscount, BigDecimalUtil.Operator.subtrac),
            BigDecimalUtil.BigDecimalPlus.create(couponDiscount, BigDecimalUtil.Operator.subtrac),
            BigDecimalUtil.BigDecimalPlus.create(fullReduceDiscount, BigDecimalUtil.Operator.subtrac),
            BigDecimalUtil.BigDecimalPlus.create(preSaleDiscount, BigDecimalUtil.Operator.subtrac),
            BigDecimalUtil.BigDecimalPlus.create(packageSaleDiscount,BigDecimalUtil.Operator.subtrac),
            BigDecimalUtil.BigDecimalPlus.create(grouperCheapReduce)

            );
        //折扣金额(使用大额优惠券，支付金额不为负的，等于运费金额)
        if(BigDecimalUtil.compareTo(tolalDiscountAfterPrice, BigDecimal.ZERO) < 0){
            tolalDiscountAfterPrice = BigDecimal.ZERO;
        }
        //TODO 同城配送运费
        //商品+运费
        BigDecimal goodsPricsAndShipping = BigDecimalUtil.add(tolalDiscountAfterPrice, vo.getShippingFee());
        //当前微信支付金额
        BigDecimal currentMoneyPaid = goodsPricsAndShipping;
        processOrderBeforeVo2(param, vo, bos, scoreProportion, scoreDiscount, useAccount, cardBalance, tolalNumberAndPrice, orderPreSale, grouperCheapReduce, preSaleDiscount, fullReduceDiscount, orderPackageSale, packageSaleDiscount, memberDiscount, couponDiscount, tolalDiscountAfterPrice, goodsPricsAndShipping, currentMoneyPaid);
        logger().info("金额处理赋值(processOrderBeforeVo),end");
    }

    private void processOrderBeforeVo2(OrderBeforeParam param, OrderBeforeVo vo, List<OrderGoodsBo> bos, Integer scoreProportion, BigDecimal scoreDiscount, BigDecimal useAccount, BigDecimal cardBalance, BigDecimal[] tolalNumberAndPrice, OrderPreSale orderPreSale, BigDecimal grouperCheapReduce, BigDecimal preSaleDiscount, BigDecimal fullReduceDiscount, OrderPackageSale orderPackageSale, BigDecimal packageSaleDiscount, BigDecimal memberDiscount, BigDecimal couponDiscount, BigDecimal tolalDiscountAfterPrice, BigDecimal goodsPricsAndShipping, BigDecimal currentMoneyPaid) throws MpException {
        //预售处理
        if(BaseConstant.ACTIVITY_TYPE_PRE_SALE.equals(param.getActivityType()) && orderPreSale != null && PresaleConstant.PRE_SALE_TYPE_SPLIT.equals(orderPreSale.getInfo().getPresaleType())){
            vo.setOrderPayWay(OrderConstant.PAY_WAY_DEPOSIT);
            if(BigDecimalUtil.compareTo(goodsPricsAndShipping, orderPreSale.getTotalPreSaleMoney()) > 0) {
                vo.setBkOrderMoney(BigDecimalUtil.subtrac(goodsPricsAndShipping, orderPreSale.getTotalPreSaleMoney()));
                currentMoneyPaid = orderPreSale.getTotalPreSaleMoney();
            }

            //打包一口价
        }else if(BaseConstant.ACTIVITY_TYPE_PACKAGE_SALE.equals(param.getActivityType()) && orderPackageSale != null){
            currentMoneyPaid = BigDecimalUtil.add(orderPackageSale.getTotalPrice(), vo.getShippingFee());
        }
        //当前微信支付金额(使用大额优惠券，支付金额不为负的，重算为0)
        if(BigDecimalUtil.compareTo(currentMoneyPaid, BigDecimal.ZERO) < 0){
            currentMoneyPaid = BigDecimal.ZERO;
        }
        //支付金额
        BigDecimal moneyPaid = BigDecimalUtil.addOrSubtrac(
            BigDecimalUtil.BigDecimalPlus.create(currentMoneyPaid, BigDecimalUtil.Operator.subtrac),
            BigDecimalUtil.BigDecimalPlus.create(scoreDiscount, BigDecimalUtil.Operator.subtrac),
            BigDecimalUtil.BigDecimalPlus.create(useAccount, BigDecimalUtil.Operator.subtrac),
            BigDecimalUtil.BigDecimalPlus.create(cardBalance, null)
        );
        //支付金额(小于0说明前端多付金额)
        if(BigDecimalUtil.compareTo(moneyPaid, BigDecimal.ZERO) < 0){
            throw new MpException(JsonResultCode.CODE_ORDER_MONEYPAID_ERROR_PLEASE_CHECK);
        }
        //好友代付
        if(param.getOrderPayWay() != null && param.getOrderPayWay().equals(OrderConstant.PAY_WAY_FRIEND_PAYMENT)) {
            vo.setOrderPayWay(OrderConstant.PAY_WAY_FRIEND_PAYMENT);
            vo.setInsteadPayMoney(moneyPaid);
            moneyPaid = BigDecimal.ZERO;
            vo.setInsteadPayNum(param.getInsteadPayNum());
        }

        //折后订单金额
        BigDecimal moneyAfterDiscount = BigDecimalUtil.add(tolalDiscountAfterPrice, vo.getShippingFee());
        //最大积分抵扣
        BigDecimal scoreMaxDiscount = BigDecimalUtil.multiplyOrDivideByMode(
            RoundingMode.HALF_DOWN,
            BigDecimalUtil.BigDecimalPlus.create(scoreCfg.getDiscount().equals(String.valueOf(YES)) ? moneyAfterDiscount : tolalDiscountAfterPrice, BigDecimalUtil.Operator.multiply),
            BigDecimalUtil.BigDecimalPlus.create(new BigDecimal(scoreCfg.getScoreDiscountRatio()), BigDecimalUtil.Operator.divide),
            BigDecimalUtil.BigDecimalPlus.create(new BigDecimal(100)));
        //会员信息
        UserRecord user = member.getUserRecordById(param.getWxUserInfo().getUserId());
        //赋值
        vo.setOrderAmount(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_PRICE]);
        vo.setTotalGoodsNumber(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_NUMBER].intValue());
        vo.setScoreDiscount(scoreDiscount != null ? scoreDiscount : BigDecimalUtil.BIGDECIMAL_ZERO);
        vo.setAccountDiscount(useAccount != null ? useAccount : BigDecimalUtil.BIGDECIMAL_ZERO);
        vo.setMemberCardDiscount(cardBalance  != null ? cardBalance : BigDecimalUtil.BIGDECIMAL_ZERO);
        vo.setMemberCardReduce(memberDiscount  != null ? memberDiscount : BigDecimalUtil.BIGDECIMAL_ZERO);
        vo.setPromotionReduce(fullReduceDiscount);
        vo.setDiscount(couponDiscount  != null ? couponDiscount : BigDecimalUtil.BIGDECIMAL_ZERO);
        vo.setMoneyPaid(moneyPaid  != null ? moneyPaid : BigDecimalUtil.BIGDECIMAL_ZERO);
        vo.setDiscountedMoney(BigDecimalUtil.subtrac(moneyPaid, vo.getShippingFee()));
        vo.setOrderGoods(bos);
        vo.setUserScore(user.getScore());
        vo.setUserAccount(user.getAccount());
        vo.setMemberCardMoney(vo.getDefaultMemberCard() == null ? BigDecimal.ZERO : vo.getDefaultMemberCard().getInfo().getMoney());
        vo.setMoneyAfterDiscount(moneyAfterDiscount);
        vo.setExchang(vo.getDefaultMemberCard() == null ? NO : (CardConstant.MCARD_TP_LIMIT.equals(vo.getDefaultMemberCard().getInfo().getCardType()) ? YES : NO));
        vo.setScoreMaxDiscount(scoreMaxDiscount);
        vo.setScoreProportion(scoreProportion);
        vo.setInvoiceSwitch(tradeCfg.getInvoice());
        vo.setCancelTime(tradeCfg.getCancelTime());
        vo.setActivityType(param.getActivityType());
        vo.setActivityId(param.getActivityId());
        vo.setIsCardPay(tradeCfg.getCardFirst());
        vo.setIsScorePay(tradeCfg.getScoreFirst());
        vo.setIsBalancePay(tradeCfg.getBalanceFirst());
        vo.setPreSaleDiscount(preSaleDiscount);
        vo.setTolalDiscountAfterPrice(tolalDiscountAfterPrice);
        vo.setInsteadPayCfg(param.getInsteadPayCfg());
        vo.setPackageDiscount(packageSaleDiscount);
        vo.setGrouperCheapReduce(grouperCheapReduce);
        // 积分使用规则
        setScorePayRule(vo);
    }

    /**
     * 运费计算、包邮处理逻辑
     * @param param
     * @param vo
     * @param bos
     * @param tolalNumberAndPrice
     */
    private void shippingFeeLogic(OrderBeforeParam param, OrderBeforeVo vo, List<OrderGoodsBo> bos, BigDecimal[] tolalNumberAndPrice) {
        //包邮策略
        if (vo.getDeliverType().equals(DELIVER_TYPE_COURIER)){
            if (param.getActivityType() == null || BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE.equals(param.getActivityType())) {
                List<Integer> goodsIds = fullPackage(vo.getAddress(), bos, tolalNumberAndPrice, param.getDate());
                bos.forEach(bo -> {
                    if (goodsIds.contains(bo.getGoodsId())) {
                        bo.setFreeShip((int) OrderConstant.YES);
                    }
                });
            }
        }
        //计算运费
        if(vo.getAddress() != null){
            //有可用地址的用户
            vo.setShippingFee(calculate.calculateShippingFee(vo.getAddress().getDistrictCode(), bos, param.getStoreId()));
            //判断是否可以发货
            vo.setCanShipping(isShipping(bos));
        }else{
            vo.setShippingFee(BigDecimal.ZERO);
            //判断是否可以发货
            vo.setCanShipping(NO);
        }
        //活动免运费
        activityFreeDelivery(vo, param.getIsFreeShippingAct());
        //会员卡免运费
        memberCardFreeDelivery(vo);
    }

    /**
     * 会员卡包邮
     * @param vo
     */
    private void memberCardFreeDelivery(OrderBeforeVo vo) {
        logger().info("卡包邮计算start");
        if(vo.getDefaultMemberCard() != null && vo.getDefaultMemberCard().getInfo() != null && BigDecimalUtil.compareTo(vo.getShippingFee(), null)>0) {
            ValidUserCardBean info = vo.getDefaultMemberCard().getInfo();
            if(info.getCardFreeShip() == null) {
                //无此权益
                logger().info("卡包邮计算,无此权益");
                return;
            } else if(info.getCardFreeShip().getType() == CardFreeship.shipType.SHIP_NOT_AVAIL.getType()) {
                //无此权益
                logger().info("卡包邮计算,无此权益");
                return;
            }else if (info.getCardFreeShip().getType() > CardFreeship.shipType.SHIP_IN_EFFECTTIME.getType()) {
                if(info.getCardFreeShip().getRemainNum() <= 0) {
                    logger().info("卡包邮计算,次数用完");
                    return;
                }
            }
            vo.setIsFreeshipCard(YES);
            vo.setFreeshipCardMoney(vo.getShippingFee());
            vo.setShippingFee(BigDecimal.ZERO);
        }
        logger().info("卡包邮计算end");
    }

    /**
     * 活动免运费
     * @param vo
     * @param isFreeShippingAct
     */
    private void activityFreeDelivery(OrderBeforeVo vo, Byte isFreeShippingAct) {
        if(isFreeShippingAct != null && isFreeShippingAct == YES) {
            vo.setShippingFee(BigDecimal.ZERO);
        }
    }



    /**
     * 校验
     * @param vo 结算vo
     * @param bo 下单业务对象
     * @param param 下单参数
     * @throws MpException 见throw语句
     */
    public void checkOrder(OrderBeforeVo vo, CreateOrderBo bo, CreateParam param) throws MpException {
        logger().info("vo:{};bo:{};",vo , bo);
        //地址超出配送范围,请重新选择商品后下单
        if(NO == vo.getCanShipping()) {
            throw new MpException(JsonResultCode.CODE_ORDER_CALCULATE_SHIPPING_FEE_ERROR);
        }
        //积分不足
        if(BigDecimalUtil.compareTo(vo.getScoreDiscount(), BigDecimal.valueOf(vo.getUserScore())) == 1){
            throw new MpException(JsonResultCode.CODE_ORDER_SCORE_NOT_ENOUGH);
        }
        //余额不足
        if(BigDecimalUtil.compareTo(vo.getAccountDiscount(), vo.getUserAccount()) == 1){
            throw new MpException(JsonResultCode.CODE_ORDER_ACCOUNT_NOT_ENOUGH);
        }
        //会员卡余额不足
        if(BigDecimalUtil.compareTo(vo.getMemberCardDiscount(), vo.getMemberCardMoney()) == 1){
            throw new MpException(JsonResultCode.CODE_ORDER_CARD_NOT_ENOUGH);
        }
        //订单金额变更，是否继续购买
        if(BigDecimalUtil.compareTo(param.getOrderAmount(), vo.getOrderAmount()) != 0){
            throw new MpException(JsonResultCode.CODE_ORDER_AMOUNT_CHANGE, null, vo.getOrderAmount().toString());
        }
        //积分抵扣金额不能超过
        if(BigDecimalUtil.compareTo(vo.getScoreDiscount(), vo.getScoreMaxDiscount()) == 1 && !BaseConstant.ACTIVITY_TYPE_INTEGRAL.equals(param.getActivityType())){
            throw new MpException(JsonResultCode.CODE_ORDER_SCORE_LIMIT);
        }
        //积分支付最小限制
        if(vo.getScorePayLimit().equals(YES) && param.getScoreDiscount() != null && param.getScoreDiscount() > 0 && param.getScoreDiscount() < vo.getScorePayNum()){
            throw new MpException(JsonResultCode.CODE_ORDER_SCORE_LIMIT);
        }
        //TODO 限次卡校验
        if(Boolean.FALSE){
            throw new MpException(JsonResultCode.CODE_ORDER_MCARD_TP_LIMIT_LIMIT);
        }
        //TODO 促销活动失效，无法下单

        //配送方式校验
        checkExpress(param.getDeliverType());
        //支付方式校验
        checkPayWay(param, vo);
        //*************医药************//
        /**
         * 校验药品
         */
        checkMedcail(param,bo);
    }

    /**
     * 校验药品
     * -审核订单得处方药品必须关联处方
     * @param param
     * @param bo
     */
    private void checkMedcail(CreateParam param, CreateOrderBo bo) throws MpException {
        //校验审核通过后处方药的关联处方
        if (OrderConstant.CHECK_ORDER_PRESCRIPTION_PASS.equals(param.getCheckPrescriptionStatus())){
            for (OrderGoodsBo goods : bo.getOrderGoodsBo()) {
                if (goods.getIsRx().equals(BaseConstant.YES) && goods.getPrescriptionInfo() == null) {
                    throw new MpException(JsonResultCode.MSG_ORDER_MEDICAL_PRESCRIPTION_CHECK);
                }
            }
        }
    }

    /**
     * 计算替他模块信息并赋值
     * @param order
     * @param orderBo
     * @param beforeVo
     */
    private void setOtherValue(CreateParam param, OrderInfoRecord order, CreateOrderBo orderBo, OrderBeforeVo beforeVo){
        Timestamp currentTime = DateUtils.getSqlTimestamp();
        //TODO 订单类型拼接(支付有礼)
        orderBo.getOrderType().addAll(orderGoods.getGoodsType(order, orderBo.getOrderGoodsBo(), order.getInsteadPayMoney()));//支付信息
        if(BigDecimalUtil.addOrSubtrac(
            BigDecimalUtil.BigDecimalPlus.create(beforeVo.getMoneyPaid(), BigDecimalUtil.Operator.add),
            BigDecimalUtil.BigDecimalPlus.create(beforeVo.getBkOrderMoney(), BigDecimalUtil.Operator.add),
            BigDecimalUtil.BigDecimalPlus.create(beforeVo.getInsteadPayMoney())).compareTo(BigDecimal.ZERO) == 0){
            logger().info("支付信息:余额支付");
            //非补款
            order.setPayCode(OrderConstant.PAY_CODE_BALANCE_PAY);
            //覆盖货到付款（小程序选择货到付款但是如果无需再支付时设置货到付款为否）
            logger().info("支付信息:货到付款");
            order.setIsCod(OrderConstant.IS_COD_NO);
        }else if(order.getOrderPayWay().equals(OrderConstant.PAY_WAY_FRIEND_PAYMENT)) {
            order.setPayCode(OrderConstant.PAY_CODE_WX_PAY);
        }else {
            if (orderBo.getPayment() != null) {
                logger().info("支付信息:{}", orderBo.getPayment());
                order.setPayCode(orderBo.getPayment().getPayCode());
                if(OrderConstant.PAY_CODE_COD.equals(orderBo.getPayment().getPayCode())){
                    logger().info("支付信息:货到付款");
                    order.setIsCod(OrderConstant.IS_COD_YES);
                }
            }
        }
        //订单状态
        makeOrderStatus(param, order, orderBo, beforeVo, currentTime);
        //设置支付过期时间(默认30minutes)
        Integer cancelTime = tradeCfg.getCancelTime();
        cancelTime = cancelTime < 1 ? OrderConstant.DEFAULT_AUTO_CANCEL_TIME : cancelTime;
        if(beforeVo.getOrderPayWay() != null && OrderConstant.PAY_WAY_FRIEND_PAYMENT == beforeVo.getOrderPayWay()) {
            //代付
            order.setExpireTime(DateUtils.getTimeStampPlus(1, ChronoUnit.DAYS));
            order.setInsteadPay(Util.toJson(beforeVo.getInsteadPayCfg()));
            order.setOrderUserMessage(beforeVo.getInsteadPayNum() == 0 ? beforeVo.getInsteadPayCfg().getOrderUserMessageMultiple() : beforeVo.getInsteadPayCfg().getOrderUserMessageSingle());
        }else {
            order.setExpireTime(DateUtils.getTimeStampPlus(cancelTime, ChronoUnit.MINUTES));
        }
        /**
         * 配置
         */
        //是否退优惠卷
        order.setIsRefundCoupon(returnCfg.getIsReturnCoupon());
        //order是否支持退款
        order.setReturnTypeCfg(orderBo.getOrderType().contains(BaseConstant.ACTIVITY_TYPE_GIVE_GIFT) ? OrderConstant.CFG_RETURN_TYPE_N : OrderConstant.CFG_RETURN_TYPE_Y);
        //发货后自动确认收货时间设置
        order.setReturnDaysCfg(tradeCfg.getDrawbackDays().byteValue());
        //确认收货后order_timeout_days天，订单完成
        order.setOrderTimeoutDays(tradeCfg.getOrderTimeoutDays().shortValue());
        //是否下单减库存
        order.setIsLock(Collections.disjoint(AtomicOperation.ACT_IS_LOCK,orderBo.getOrderType()) ? tradeCfg.getIsLock() : YES);
    }

    private void makeOrderStatus(CreateParam param, OrderInfoRecord order, CreateOrderBo orderBo, OrderBeforeVo beforeVo, Timestamp currentTime) {
        if(orderBo.getOrderType().contains(BaseConstant.ACTIVITY_TYPE_PRE_SALE)) {
            //预售
            if(order.getBkOrderPaid() != null && order.getBkOrderPaid().equals(OrderConstant.BK_PAY_FINISH)) {
                logger().info("订单状态:{}", OrderConstant.ORDER_WAIT_DELIVERY);
                order.setOrderStatus(OrderConstant.ORDER_WAIT_DELIVERY);
            }else {
                logger().info("订单状态:{}", OrderConstant.ORDER_WAIT_PAY);
                order.setOrderStatus(OrderConstant.ORDER_WAIT_PAY);
            }
            if(order.getBkOrderPaid() != null && order.getBkOrderPaid() != OrderConstant.BK_PAY_NO) {
                order.setPayTime(currentTime);
            }
        }else if(beforeVo.getOrderPayWay() != null && OrderConstant.PAY_WAY_FRIEND_PAYMENT == beforeVo.getOrderPayWay() && BigDecimalUtil.compareTo(beforeVo.getInsteadPayMoney(), BigDecimal.ZERO) == 1) {
            //代付（代付金额大于0）->待支付
            logger().info("订单状态:{}", OrderConstant.ORDER_WAIT_PAY);
            order.setOrderStatus(OrderConstant.ORDER_WAIT_PAY);
        }else {
            if (orderBo.getPayment() != null && OrderConstant.PAY_CODE_COD.equals(orderBo.getPayment().getPayCode())) {
                //货到付款 || 待支付=0
                if (orderBo.getOrderType().contains(BaseConstant.ACTIVITY_TYPE_GROUP_BUY) || orderBo.getOrderType().contains(BaseConstant.ACTIVITY_TYPE_GROUP_DRAW)) {
                    //拼团
                    logger().info("订单状态:{}", OrderConstant.ORDER_PIN_PAYED_GROUPING);
                    order.setOrderStatus(OrderConstant.ORDER_PIN_PAYED_GROUPING);
                } else {
                    logger().info("订单状态:{}", OrderConstant.ORDER_WAIT_DELIVERY);
                    order.setOrderStatus(OrderConstant.ORDER_WAIT_DELIVERY);
                    if (param.getOrderAuditType().equals(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT)) {
                        logger().info("订单支付成功->待审核状态");
                        //1审核
                        order.setOrderStatus(OrderConstant.ORDER_TO_AUDIT);
                    } else if (param.getOrderAuditType().equals(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_CREATE)) {
                        logger().info("订单支付成功->待开方状态");
                        //开方
                        order.setOrderStatus(OrderConstant.ORDER_TO_AUDIT_OPEN);
                    }
                }
                order.setPayTime(currentTime);
            } else if (BigDecimalUtil.compareTo(order.getMoneyPaid(), BigDecimal.ZERO) == 0) {
                //货到付款 || 待支付=0
                if (orderBo.getOrderType().contains(BaseConstant.ACTIVITY_TYPE_GROUP_BUY) || orderBo.getOrderType().contains(BaseConstant.ACTIVITY_TYPE_GROUP_DRAW)) {
                    //拼团
                    logger().info("订单状态:{}", OrderConstant.ORDER_PIN_PAYED_GROUPING);
                    order.setOrderStatus(OrderConstant.ORDER_PIN_PAYED_GROUPING);
                } else {
                    logger().info("订单状态:{}", OrderConstant.ORDER_WAIT_DELIVERY);
                    order.setOrderStatus(OrderConstant.ORDER_WAIT_DELIVERY);
                    if (param.getOrderAuditType().equals(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_AUDIT)) {
                        logger().info("订单支付成功->待审核状态");
                        //1审核
                        order.setOrderStatus(OrderConstant.ORDER_TO_AUDIT);
                    } else if (param.getOrderAuditType().equals(OrderConstant.MEDICAL_ORDER_AUDIT_TYPE_CREATE)) {
                        logger().info("订单支付成功->待开方状态");
                        //开方
                        order.setOrderStatus(OrderConstant.ORDER_TO_AUDIT_OPEN);
                    }
                }
                order.setPayTime(currentTime);
            } else {
                logger().info("订单状态:{}", OrderConstant.ORDER_WAIT_PAY);
                order.setOrderStatus(OrderConstant.ORDER_WAIT_PAY);
            }
        }
    }

    /**
     * 处理普通的营销活动（下单时以普通商品下单，不指定营销活动的）
     * eg 首单特惠、限时降价、会员价、赠品、满折满减
     * @param order
     * @param orderBo
     * @param beforeVo
     */
    private void processNormalActivity(OrderInfoRecord order, CreateOrderBo orderBo, OrderBeforeVo beforeVo){
        //TODO 分销
        //TODO 使用优惠券使用, CRM核销
        if(beforeVo.getDefaultCoupon() != null) {
            coupon.use(beforeVo.getDefaultCoupon().getInfo().getId(), order.getOrderSn());
        }
        //TODO 送赠品(处理门店)
        getGifts(beforeVo, order.getStoreId(), order.getUserId(), orderBo.getOrderType());
    }

    private void getGifts(OrderBeforeVo beforeVo, Integer storeId, Integer userId, List<Byte> orderType) {
        giftProcessor.getGifts(userId, beforeVo.getOrderGoods(), orderType);
        //赠品
        List<OrderGoodsBo> gifts = beforeVo.getOrderGoods().stream().filter(x -> x.getIsGift() != null && x.getIsGift() == YES).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty((gifts))) {
            return;
        }
        //赠品可配送处理
        if(beforeVo.getAddress() != null){
            //有可用地址的用户
            calculate.calculateShippingFee(beforeVo.getAddress().getDistrictCode(), gifts, storeId);
            //判断是否可以发货
            beforeVo.setCanShipping(isShipping(gifts));
        }else{
            beforeVo.setShippingFee(BigDecimal.ZERO);
            //判断是否可以发货
            beforeVo.setCanShipping(NO);
        }
    }

    /**
     * 积分使用规则
     * @param vo vo
     */
    public void setScorePayRule(OrderBeforeVo vo){
        //TODO 壮壮需要修改
        Byte scorePayLimit = scoreCfg.getScorePayLimit();
        Integer scorePayNum = null;
        if(NumberUtils.BYTE_ONE.equals(scorePayLimit)){
            scorePayNum = scoreCfg.getScorePayNum();
        }
        vo.setScorePayLimit(scorePayLimit);
        vo.setScorePayNum(scorePayNum);
    }

    /**
     * 判断是否可以发货
     * @param bos bos
     * @return no or yes
     */
    public Byte isShipping(List<OrderGoodsBo> bos){
        for (OrderGoodsBo bo: bos) {
            if(bo.getIsShipping() == NO){
                return NO;
            }
        }
        return YES;
    }

    /**
     *
     * @param deliverType 当前配送方式
     * @throws MpException 当前配送方式不支持
     */
    public void checkExpress(Byte deliverType) throws MpException {
        Byte[] expressList = getExpressList();
        if(expressList[deliverType] == NO){
            //当前配送方式不支持
            throw new MpException(JsonResultCode.CODE_ORDER_DELIVER_TYPE_NO_SUPPORT);
        }
    }
    /**
     *
     * @param param getOrderPayWay() 当前配支付方式
     * @throws MpException 当前支付方式不支持
     */
    public void checkPayWay(CreateParam param, OrderBeforeVo vo) throws MpException {
        Map<String, PaymentVo> supportPayment = payment.getSupportPayment();
        if(BigDecimalUtil.compareTo(vo.getMoneyPaid(), null) > 0 && OrderConstant.MP_PAY_CODE_WX_PAY.equals(param.getOrderPayWay()) && null == supportPayment.get(OrderConstant.MP_PAY_CODE_TO_STRING[param.getOrderPayWay()])){
            //wx
            throw new MpException(JsonResultCode.CODE_ORDER_PAY_WAY_NO_SUPPORT_WX);
        }
        if(OrderConstant.MP_PAY_CODE_COD.equals(param.getOrderPayWay()) && null == supportPayment.get(OrderConstant.MP_PAY_CODE_TO_STRING[param.getOrderPayWay()])){
            //货到付款
            throw new MpException(JsonResultCode.CODE_ORDER_PAY_WAY_NO_SUPPORT_COD);
        }
    }

    /**
     * 活动满包邮商品
     *  满包邮活动
     * @param address 地址
     * @param bos
     * @param tolalNumberAndPrice 总价
     * @param date 时间
     * @return  符合满包邮的商品
     */
    public List<Integer> fullPackage(UserAddressVo address, List<OrderGoodsBo> bos, BigDecimal[] tolalNumberAndPrice, Timestamp date){
        logger().info("满包邮活动开始---");
        List<FreeShippingVo> validFreeList = freeShippingService.getValidFreeList(date);
        logger().info("满包邮---有效活动{}个",validFreeList.size());
        if (validFreeList.size()==0){
            return new ArrayList<>();
        }
        List<Integer> goodsIds = bos.stream().map(OrderGoodsBo::getGoodsId).distinct().collect(Collectors.toList());
        logger().info("商品{}",Util.listToString(goodsIds));
        List<Integer> freeGoodsIds=new ArrayList<>();
        for (FreeShippingVo freeShip : validFreeList) {
            if (goodsIds.isEmpty()) {
                return goodsIds;
            }
            //全部商品
            if (freeShip.getType().equals(BaseConstant.GOODS_AREA_TYPE_ALL.intValue())) {
                boolean freeshipCondition = freeShippingService.checkedFreeshipCondition(address, tolalNumberAndPrice, freeShip.getRuleList());
                if (freeshipCondition){
                     goodsIds.addAll(freeGoodsIds);
                     return goodsIds;
                }
            }
            //部分商品
            List<Integer> rGoodsId = Util.stringToList(freeShip.getRecommendGoodsId());
            List<Integer> rCatIds  = Util.stringToList(freeShip.getRecommendCatId());
            List<Integer> rSortIds = Util.stringToList(freeShip.getRecommendSortId());
            List<OrderGoodsBo> sectionGoods=new ArrayList<>();
            bos.forEach(orderGoods->{
                if (rGoodsId.contains(orderGoods.getGoodsId())||rCatIds.contains(orderGoods.getCatId())||rSortIds.contains(orderInfo.getShopId())){
                    sectionGoods.add(orderGoods);
                }
            });
            BigDecimal[] sectionGoodsNumPrice = calculate.getTolalNumberAndPriceByType(sectionGoods, null, null);
            boolean freeshipCondition = freeShippingService.checkedFreeshipCondition(address, sectionGoodsNumPrice, freeShip.getRuleList());
            if (freeshipCondition){
                List<Integer> orderGoodsIds = sectionGoods.stream().map(OrderGoodsBo::getGoodsId).distinct().collect(Collectors.toList());
                goodsIds.removeAll(orderGoodsIds);
                freeGoodsIds.addAll(orderGoodsIds);
            }
        }
        return freeGoodsIds;
    }

    /**
     *
     * @param param
     * @param orderBo
     * @param order
     * @throws MpException
     * @return
     */
    private boolean processEffective(CreateParam param, CreateOrderBo orderBo, OrderInfoRecord order) throws MpException {
        if (!order.getOrderStatus().equals(OrderConstant.ORDER_WAIT_DELIVERY)) {
            if (!order.getOrderStatus().equals(OrderConstant.ORDER_PIN_PAYED_GROUPING)) {
                if (!BaseConstant.ACTIVITY_TYPE_PRE_SALE.equals(param.getActivityType()) || order.getBkOrderPaid() <= OrderConstant.BK_PAY_NO) {
                    if (order.getOrderStatus().equals(OrderConstant.ORDER_WAIT_PAY) && order.getIsLock().equals(YES)) {
                        logger().info("下单时待付款且配置为下单减库存或者为秒杀时调用更新库存方法");
                        //加锁
                        atomicOperation.addLock(orderBo.getOrderGoodsBo());
                        //下单减库存
                        marketProcessorFactory.processUpdateStock(param, order);
                        logger().info("加锁{}", order.getOrderSn());
                        atomicOperation.updateStockandSalesByActFilter(order, orderBo.getOrderGoodsBo(), true);
                        logger().info("更新成功{}", order.getOrderSn());
                        return true;
                    }
                } else {
                    logger().info("下单时待发货、拼团中、预售支付定金或支付完成减库存、调用Effective方法");
                    //加锁
                    atomicOperation.addLock(orderBo.getOrderGoodsBo());
                    //货到付款、余额、积分(非微信混合)付款，生成订单时修改活动状态
                    marketProcessorFactory.processOrderEffective(param, order);
                    //更新活动库存
                    marketProcessorFactory.processUpdateStock(param, order);
                    logger().info("加锁{}", order.getOrderSn());
                    atomicOperation.updateStockandSalesByActFilter(order, orderBo.getOrderGoodsBo(), true);
                    logger().info("更新成功{}", order.getOrderSn());

                    return true;
                }
            } else {
                logger().info("下单时待发货、拼团中、预售支付定金或支付完成减库存、调用Effective方法");
                //加锁
                atomicOperation.addLock(orderBo.getOrderGoodsBo());
                //货到付款、余额、积分(非微信混合)付款，生成订单时修改活动状态
                marketProcessorFactory.processOrderEffective(param, order);
                //更新活动库存
                marketProcessorFactory.processUpdateStock(param, order);
                logger().info("加锁{}", order.getOrderSn());
                atomicOperation.updateStockandSalesByActFilter(order, orderBo.getOrderGoodsBo(), true);
                logger().info("更新成功{}", order.getOrderSn());

                return true;
            }
        } else {
            logger().info("下单时待发货、拼团中、预售支付定金或支付完成减库存、调用Effective方法");
            //加锁
            atomicOperation.addLock(orderBo.getOrderGoodsBo());
            //货到付款、余额、积分(非微信混合)付款，生成订单时修改活动状态
            marketProcessorFactory.processOrderEffective(param, order);
            //更新活动库存
            marketProcessorFactory.processUpdateStock(param, order);
            logger().info("加锁{}", order.getOrderSn());
            atomicOperation.updateStockandSalesByActFilter(order, orderBo.getOrderGoodsBo(), true);
            logger().info("更新成功{}", order.getOrderSn());

            return true;
        }
        return false;
    }
}
