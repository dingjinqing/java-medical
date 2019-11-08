package com.vpu.mp.service.shop.order.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.vpu.mp.db.shop.tables.records.PaymentRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.order.invoice.InvoiceVo;
import com.vpu.mp.service.pojo.shop.payment.PaymentVo;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import com.vpu.mp.service.pojo.wxapp.order.CreateParam;
import com.vpu.mp.service.pojo.wxapp.order.marketing.member.OrderMemberVo;
import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.service.shop.order.action.base.Calculate;
import com.vpu.mp.service.shop.member.BaseScoreCfgService;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.invoice.InvoiceService;
import com.vpu.mp.service.shop.payment.PaymentService;
import com.vpu.mp.service.shop.store.store.StoreService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.member.address.UserAddressVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam.Goods;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeVo;
import com.vpu.mp.service.shop.config.TradeService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.member.AddressService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.info.OrderInfoService;

import static com.vpu.mp.service.pojo.shop.order.OrderConstant.no;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.yes;

/**
 * 下单逻辑处理
 *
 * @author: ws
 * @create: 2019-10-23 16:15
 **/
@Service
public class CreateService extends ShopBaseService implements IorderOperate<OrderBeforeParam, CreateParam> {

    @Autowired
    private OrderInfoService orderInfo;

    @Autowired
    private AddressService address;

    @Autowired
    private TradeService trade;

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

    @Override
    public OrderServiceCode getServiceCode() {
        return OrderServiceCode.CREATE;
    }

    /**
     * 订单创建前，结算页面数据查询
     */
    @Override
    public Object query(OrderBeforeParam param) throws MpException {
        OrderBeforeVo result = OrderBeforeVo.builder().
            // 地址
            address(getDefaultAddress(param.getWxUserInfo().getUserId(), param.getAddressId())).
            // 支持的配送方式
            expressList(getExpressList()).
        build();
        // goods info init
        initGoods(param.getGoods());
        // process
        processParam(param, result);

        return result;
    }

    @Override
    public CreateParam execute(CreateParam param) {
        initBo(param);
        return null;
    }

    private void initBo(CreateParam param) {
        //TODO Robust check 地址
        UserAddressVo userAddress = address.get(param.getAddressId(), param.getWxUserInfo().getUserId());
        //TODO Robust check 支付方式
        PaymentRecord paymentInfo = payment.getPaymentInfo(OrderConstant.MP_PAY_CODE_TO_STRING[param.getOrderPayWay()]);
        //TODO Robust check 门店
        StorePojo storeBo = this.store.getStore(param.getStoreId());
        //发票
        InvoiceVo invoiceBo = invoice.get(param.getInvoiceId());
        //TODO 自提时间
        //会员卡
        OrderMemberVo memberCard = userCard.userCardDao.getValidByCardNo(param.getMemberCardNo());
        //优惠卷
    }


    /**
     * 获取默认地址
     *
     * @param userId
     * @param addressId
     * @return
     */
    public UserAddressVo getDefaultAddress(Integer userId, Integer addressId) {
        UserAddressVo defaultAddress = null;
        if (addressId != null) {
            // 输入addressId
            defaultAddress = address.get(addressId, userId);
        }
        // 输入地址无效
        if (defaultAddress == null) {
            // 没输入addressId
            defaultAddress = orderInfo.getLastOrderAddress(userId);
            // TODO 更新经纬度
        }
        return defaultAddress;
    }

    /**
     * 配送方式
     *
     * @return
     */
    public byte[] getExpressList() {
        byte[] expressList = new byte[3];
        // 快递
        expressList[OrderConstant.DELIVER_TYPE_COURIER] = trade.getExpress();
        // 自提
        expressList[OrderConstant.DELIVER_TYPE_SELF] = trade.getFetch();
        //TODO 同城配送
        // expressList[OrderConstant.DELIVER_TYPE_COURIER] =
        // trade.getCityExpressService();
        return expressList;
    }

    /**
     * 初始化购买商品信息
     * @param goods
     * @return
     */
    public void initGoods(List<Goods> goods) {
        // TODO 以下参数为模拟参数
        for (Goods temp : goods) {
            // 商品参与的促销活动id
            temp.setStraId(0);
            // 购买价格id
            temp.setPurchasePriceId(0);
            // 购买规则id
            temp.setPurchasePriceRuleId(0);
            // ?crm?
            temp.setPromoteInfo(null);
        }
    }

    public void processParam(OrderBeforeParam param, OrderBeforeVo vo) throws MpException {
        if (Boolean.FALSE) {
            // 营销
        } else {
            // 未参与营销时进入
            if (Boolean.TRUE) {
                // 非购物车
                if (Boolean.FALSE) {
                    // 送礼 或 批量换购
                } else {
                    //单次购买
                    purchase(param, param.getWxUserInfo().getUserId(), param.getStoreId(), vo);
                }
            }
        }
    }

    public void purchase(OrderBeforeParam param, Integer userId, Integer storeId, OrderBeforeVo vo) throws MpException {
        //TODO 返利信息
        Boolean isNewUser = orderInfo.isNewUser(userId, true);
        //规格信息,key proId
        Map<Integer, GoodsSpecProductRecord> productInfo = goodsSpecProduct.selectSpecByProIds(param.getProductIds(), storeId);
        //goods type,key goodsId
        Map<Integer, Byte> goodsTypes = goodsService.getGoodsType(param.getGoodsIds());
        //赋值
        for (Goods temp : param.getGoods()) {
            GoodsSpecProductRecord product = productInfo.get(temp.getProductId());
            Byte goodsType = goodsTypes.get(temp.getGoodsId());
            if (product == null || goodsType == null) {
                //商品规格不存在（逻辑上不会出现）
                throw new MpException(JsonResultCode.CODE_ORDER);
            }
            //方便计算，临时存储
            temp.setProductInfo(product);
            //规格价格
            temp.setProductPrice(product.getPrdPrice());
            //规格数量
            temp.setProductNumbers(product.getPrdNumber());
            //商品goodsType
            temp.setGoodsType(goodsType);
            //首单特惠处理
            if (isNewUser) {
                //TODO
                temp.setIsFirstSpecial(yes);
                temp.setIsFirstSpecial(no);
            }
        }
        // 初始化
        List<OrderGoodsBo> orderGoodsBos = initOrderGoods(param, param.getGoods(), userId, param.getMemberCardNo(), storeId);
        //默认选择
        vo.setDeliverType(Objects.isNull(param.getDeliverType()) ? vo.getDefaultDeliverType() : param.getDeliverType());
        //配送方式支持的门店列表（自提、同城配送）
        List<StorePojo>[] storeLists = store.filterExpressList(vo.getExpressList(), param.getProductIds(), vo.getAddress(), no);
        //处理配送方式及门店信息;设置门店列表
        processExpressList(storeLists, vo);
        //计算金额相关、vo赋值
        processVo( param, vo, orderGoodsBos);
        //支付方式
        vo.setPaymentList(getSupportPayment());
        //服务条款
        setServiceTerms(vo);
        // 积分使用规则
        setScorePayRule(vo);
        //订单必填信息处理
        vo.setMust(calculate.checkGoodsCfg(vo.getOrderGoods()));
    }

    /**
     * @param goods
     * @param userId       会员id
     * @param memberCardNo 会员卡no
     * @param storeId      门店id
     * @throws MpException
     * @return
     */
    public List<OrderGoodsBo> initOrderGoods(OrderBeforeParam param, List<Goods> goods, Integer userId, String memberCardNo, Integer storeId) throws MpException {
        logger().info("initOrderGoods开始");
        // 会员卡类型
        Byte cardType = userCard.getCardType(memberCardNo);
        // 会员等级
        String userGrade = userCard.getUserGrade(userId);
        //
        List<OrderGoodsBo> boList = new ArrayList<>(goods.size());
        //
        Map<Integer, GoodsRecord> goodsRecords = goodsService.getGoodsToOrder(param.getGoodsIds());
        for (Goods temp : goods) {
            GoodsRecord goodsRecord = goodsRecords.get(temp.getGoodsId());
            //TODO 去别的class写：不满足需要推出11111
            //分销返利（return）
            //首单特惠（return）、
            //会员等级->限时降价/等级会员卡专享价格/商品价格（三取一）return
            //限时降价

            //商品校验
            checkGoodsAndProduct(goodsRecord, temp);
            //会员专享校验

            //预售商品，不支持现购买

            //非加价购 && 非限次卡，限购

            //price 副本

            //if else 加价购-straid

            //非加价购 复制 11111分支

            //判断副本与实际计算价格大小、

            //初始化ordergoods

            //TODO temp goodsprice 取规格
            boList.add(orderGoods.initOrderGoods(temp, goodsRecord));
        }
        return boList;
    }

    /**
     * 校验
     *
     * @param goodsRecord
     * @param goods
     * @throws MpException
     */
    public void checkGoodsAndProduct(GoodsRecord goodsRecord, Goods goods) throws MpException {
        if (goodsRecord == null || goodsRecord.getDelFlag() == DelFlag.DISABLE.getCode()) {
            logger().error("checkGoodsAndProduct,商品不存在");
            throw new MpException(JsonResultCode.CODE_ORDER_GOODS_NOT_EXIST, goodsRecord.getGoodsName());
        }
        if (!GoodsConstant.ON_SALE.equals(goodsRecord.getIsOnSale())) {
            logger().error("checkGoodsAndProduct,商品已下架,id:" + goodsRecord.getGoodsId());
            throw new MpException(JsonResultCode.CODE_ORDER_GOODS_NO_SALE, null, goodsRecord.getGoodsName());
        }
        if (goods.getGoodsNumber() > goods.getProductNumbers()) {
            logger().error("checkGoodsAndProduct,库存不足,id:" + goods.getProductId());
            throw new MpException(JsonResultCode.CODE_ORDER_GOODS_NOT_EXIST, goodsRecord.getGoodsName());
        }
        if (goods.getGoodsNumber() == null || goods.getGoodsNumber() <= 0) {
            logger().error("checkGoodsAndProduct,商品数量不能为0,id:" + goods.getProductId());
            throw new MpException(JsonResultCode.CODE_ORDER_GOODS_NO_ZERO, goodsRecord.getGoodsName());
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
                vo.getExpressList()[i] = no;
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
     * 支付方式
     */
    public Map<String, PaymentVo> getSupportPayment(){
        return payment.getSupportPayment();
        //TODO
    }

    /**
     * 金额处理
     * @param param
     * @param vo
     * @param bos
     */
    public void processVo(OrderBeforeParam param, OrderBeforeVo vo, List<OrderGoodsBo> bos) {
        //积分抵扣金额
        BigDecimal scoreDiscount = param.getScoreDiscount();
        //余额抵扣金额
        BigDecimal useAccount = param.getBalance();
        //会员卡抵扣金额
        BigDecimal cardBalance = param.getCardBalance();
        //总价、总数量
        BigDecimal[] tolalNumberAndPrice = calculate.getTolalNumberAndPriceByType(bos, null, null);
        //处理会员卡
        calculate.calculateCardInfo(param, vo);
        //处理当前会员卡
        BigDecimal memberDiscount = calculate.calculateOrderGoodsDiscount(vo.getCurrentMemberCard(), bos, OrderConstant.D_T_MEMBER_CARD);
        //计算优惠卷折扣
        calculate.calculateCoupon(param, vo);
        //处理当前优惠卷
        BigDecimal couponDiscount = calculate.calculateOrderGoodsDiscount(vo.getCurrentCoupon(), bos, OrderConstant.D_T_COUPON);
        //计算运费
        try {
            vo.setShippingFee(calculate.calculateShippingFee(vo.getAddress().getDistrictCode(), bos, param.getStoreId(), null));
        } catch (MpException e) {
            logger().error("非配送区域,不可下单");
            vo.setCanShipping(no);
        } catch (Exception e) {
            logger().error("未输入地址,不可下单");
            vo.setCanShipping(no);
        }
        //TODO 包邮策略
        //freeDeliveryLogic();
        //折扣金额
        BigDecimal tolalDiscountAfterPrice = BigDecimalUtil.addOrSubtrac(
            BigDecimalUtil.BigDecimalPlus.create(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_PRICE], BigDecimalUtil.Operator.subtrac),
            BigDecimalUtil.BigDecimalPlus.create(memberDiscount, BigDecimalUtil.Operator.subtrac),
            BigDecimalUtil.BigDecimalPlus.create(couponDiscount, BigDecimalUtil.Operator.subtrac)
        );
        //折扣金额(使用大额优惠券，支付金额不为负的，等于运费金额)
        if(BigDecimalUtil.compareTo(tolalDiscountAfterPrice, BigDecimal.ZERO) < 0){
            tolalDiscountAfterPrice = BigDecimal.ZERO;
        }
        //TODO 同城配送运费

        //支付金额
        BigDecimal moneyPaid = BigDecimalUtil.addOrSubtrac(
            BigDecimalUtil.BigDecimalPlus.create(tolalDiscountAfterPrice, BigDecimalUtil.Operator.add),
            BigDecimalUtil.BigDecimalPlus.create(vo.getShippingFee(), BigDecimalUtil.Operator.subtrac),
            BigDecimalUtil.BigDecimalPlus.create(scoreDiscount, BigDecimalUtil.Operator.subtrac),
            BigDecimalUtil.BigDecimalPlus.create(useAccount, BigDecimalUtil.Operator.subtrac),
            BigDecimalUtil.BigDecimalPlus.create(cardBalance, null)
        );
        //支付金额(使用大额优惠券，支付金额不为负的，等于运费金额)
        if(BigDecimalUtil.compareTo(moneyPaid, BigDecimal.ZERO) < 0){
            moneyPaid = BigDecimal.ZERO;
        }
        //TODO 支付方式

        //折后订单金额
        BigDecimal moneyAfterDiscount = BigDecimalUtil.add(tolalDiscountAfterPrice, vo.getShippingFee());
        //TODO 积分抵扣比例 用户可使用积分抵扣订单折后金额的50% 不填默认积分抵扣订单折后金额的50%
        Integer scoreDiscountRatio = 50;
        BigDecimal scoreMaxDiscount = BigDecimalUtil.multiplyOrDivide(
            BigDecimalUtil.BigDecimalPlus.create(moneyAfterDiscount, BigDecimalUtil.Operator.multiply),
            BigDecimalUtil.BigDecimalPlus.create(BigDecimal.valueOf(scoreDiscountRatio), BigDecimalUtil.Operator.Divide),
            BigDecimalUtil.BigDecimalPlus.create(BigDecimal.valueOf(100), null)
        );
        //会员信息
        UserRecord user = member.getUserRecordById(param.getWxUserInfo().getUserId());
        //赋值
        vo.setOrderAmount(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_PRICE]);
        vo.setTotalGoodsNumber(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_NUMBER].intValue());
        vo.setScoreDiscount(scoreDiscount);
        vo.setUseAccount(useAccount);
        vo.setMemberCardBalance(cardBalance);
        vo.setMemberCardReduce(memberDiscount);
        vo.setDiscount(couponDiscount);
        vo.setMoneyPaid(moneyPaid);
        vo.setDiscountedMoney(BigDecimalUtil.subtrac(moneyPaid, vo.getShippingFee()));
        vo.setOrderGoods(bos);
        vo.setUserScore(user.getScore());
        vo.setUserBalance(user.getAccount());
        vo.setMemberCardMoney(vo.getCurrentMemberCard() == null ? BigDecimal.ZERO : vo.getCurrentMemberCard().getMoney());
        vo.setMoneyAfterDiscount(moneyAfterDiscount);
        //TODO
        vo.setOrderPayWay(param.getOrderPayWay());
        vo.setExchang(vo.getCurrentMemberCard() == null ? no : (CardConstant.MCARD_TP_LIMIT.equals(vo.getCurrentMemberCard().getCardType()) ? yes : no));
        vo.setCanShipping(vo.getCanShipping() == null ? yes : no);
        vo.setScoreMaxDiscount(scoreMaxDiscount);
        vo.setInvoiceSwitch(trade.getInvoice());
        vo.setCancelTime(trade.getCancelTime());
        vo.setActivityType(param.getActivityType());
        vo.setIsCardPay(trade.getCardFirst());
        vo.setIsScorePay(trade.getScoreFirst());
        vo.setIsBalancePay(trade.getBalanceFirst());
    }

    /**
     * 设置服务条款
     * @param vo vo
     */
    public void setServiceTerms(OrderBeforeVo vo){
        Byte serviceTerms = trade.getServiceTerms();
        Byte serviceChoose = null;
        String serviceName = null;
        if(serviceTerms.intValue() == no){
            serviceChoose = trade.getServiceChoose();
            serviceName = trade.getServiceName();
        }
        vo.setIsShowserviceTerms(serviceTerms);
        vo.setServiceName(serviceName);
        vo.setServiceChoose(serviceChoose);
    }

    /**
     * 积分使用规则
     * @param vo vo
     */
    public void setScorePayRule(OrderBeforeVo vo){
        //TODO 壮壮需要修改
        Byte scorePayLimit = scoreCfg.getScorePayLimit();
        Integer scorePayNum = null;
        if(scorePayLimit != null){
            scorePayNum = scoreCfg.getScorePayNum();
        }
        vo.setScorePayLimit(scorePayLimit);
        vo.setScorePayNum(scorePayNum);
    }

}
