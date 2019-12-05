package com.vpu.mp.service.shop.order.action.base;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.pojo.shop.config.trade.GoodsPackageParam;
import com.vpu.mp.service.pojo.shop.member.address.AddressInfo;
import com.vpu.mp.service.pojo.shop.member.address.UserAddressVo;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.calculate.UniteMarkeingtRecalculateBo;
import com.vpu.mp.service.pojo.wxapp.cart.activity.OrderCartProductBo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeVo;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.base.BaseMarketingBaseVo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.coupon.OrderCouponVo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.member.OrderMemberVo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.process.DefaultMarketingProcess;
import com.vpu.mp.service.pojo.wxapp.order.must.OrderMustVo;
import com.vpu.mp.service.shop.config.TradeService;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.goods.GoodsDeliverTemplateService;
import com.vpu.mp.service.shop.member.AddressService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.user.user.UserLoginRecordService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 王帅
 */
@Component
public class Calculate extends ShopBaseService {

    @Autowired
    private GoodsDeliverTemplateService shippingFeeTemplate;
    @Autowired
    private CouponService coupon;
    @Autowired
    private UserCardService userCard;
    @Autowired
    private TradeService trade;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private UserLoginRecordService userLoginRecordService;


    /**
     * 计算订单商品折扣金额
     *
     * @param mbv   营销活动
     * @param bos   商品
     * @param mType 活动类型
     * @return 折扣总额
     */
    public BigDecimal calculateOrderGoodsDiscount(BaseMarketingBaseVo mbv, List<OrderGoodsBo> bos, Byte mType) {
        logger().info("计算订单商品折扣金额start,营销活动:{},商品:{},活动类型（0会员卡，1优惠卷）：{}", mbv, bos, mType);
        if (mbv == null || CollectionUtils.isEmpty(mbv.getBos()) || !mbv.checkRatio()) {
            return BigDecimal.ZERO;
        }
        // 累计折扣金额
        BigDecimal discountPrica = BigDecimal.ZERO;
        for (int i = 0, lenght = mbv.getBos().size(); i < lenght; i++) {
            //该商品行折扣金额(向下取整)
            BigDecimal tdPrica;
            if (i == lenght - 1) {
                //最后一个商品通过减法计算
                tdPrica = BigDecimalUtil.subtrac(mbv.getTotalDiscount(), discountPrica);
            } else {
                //该商品行折扣金额(向下取整)
                tdPrica = BigDecimalUtil.multiply(mbv.getBos().get(i).getDiscountedTotalPrice(), mbv.getRatio(), RoundingMode.FLOOR);
            }
            //保存记录
            saveRecord(mbv.getBos().get(i), mType);
            //设置折扣信息
            BigDecimal currentDiscount = setOrderGoodsDiscountInfo(tdPrica, mbv.getBos().get(i));
            // 累计折扣金额++
            discountPrica = BigDecimalUtil.add(discountPrica, currentDiscount);
        }
        logger().info("计算订单商品折扣金额end,折扣金额：{}", discountPrica);
        return discountPrica;
    }

    /**
     * 商品总数量
     */
    public static final int BY_TYPE_TOLAL_NUMBER = 0;
    /**
     * 商品总价格
     */
    public static final int BY_TYPE_TOLAL_PRICE = 1;

    /**
     * 获取订单商品总数量、总价格
     *
     * @param bos              bos
     * @param discountType     折扣类型
     * @param defaultMarketing 临时存储默认营销信息
     * @return BigDecimal[0->数量，1->价格] （上面有常量）
     */
    public BigDecimal[] getTolalNumberAndPriceByType(List<OrderGoodsBo> bos, Byte discountType, DefaultMarketingProcess defaultMarketing) {
        BigDecimal[] tolalNumberAndPrice = new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO};
        for (OrderGoodsBo bo : bos) {
            //会员卡 或 优惠卷-> one
            if (OrderConstant.D_T_MEMBER_CARD.equals(discountType) || OrderConstant.D_T_COUPON.equals(discountType)) {
                //加价购 或 满折满减 与 one 不共存
                if (bo.getPurchasePriceId() != null || bo.getStraId() != null) {
                    continue;
                }

                if (OrderConstant.D_T_MEMBER_CARD.equals(discountType) &&
                    defaultMarketing != null &&
                    OrderConstant.D_T_MEMBER_CARD.equals(defaultMarketing.getType()) &&
                    !CardConstant.MCARD_TP_LIMIT.equals(defaultMarketing.getCard().getCardType())) {
                    //会员卡 and
                    // 临时存储默认营销信息!=null and
                    // 临时存储默认营销信息==card and
                    // 非限次卡
                    if (!userCard.isContainsProduct(defaultMarketing.getCard().getCardId(), bo)) {
                        //校验该卡是否可用该商品
                        continue;
                    }
                }
            }
            if (OrderConstant.D_T_MEMBER_CARD.equals(discountType)) {
                //会员卡初始化支持商品
                if (CollectionUtils.isEmpty(defaultMarketing.getCard().getBos())) {
                    defaultMarketing.getCard().setBos(new ArrayList<>());
                }
                defaultMarketing.getCard().getBos().add(bo);
            }
            //数量
            tolalNumberAndPrice[BY_TYPE_TOLAL_NUMBER] = BigDecimalUtil.add(tolalNumberAndPrice[0], BigDecimal.valueOf(bo.getGoodsNumber()));
            //价格
            tolalNumberAndPrice[BY_TYPE_TOLAL_PRICE] = BigDecimalUtil.add(tolalNumberAndPrice[1], bo.getDiscountedTotalPrice());
        }
        return tolalNumberAndPrice;
    }

    /**
     * 计算优惠卷折扣,设置默认优惠卷
     *
     * @param param 参数
     * @param vo    vo
     */
    public void calculateCoupon(OrderBeforeParam param, OrderBeforeVo vo) {
        logger().info("获取可用优惠卷start");
        if (vo.getDefaultMemberCard() == null || !CardConstant.MCARD_TP_LIMIT.equals(vo.getDefaultMemberCard().getCardType())) {
            logger().info("该次下单可以用优惠卷，准备获取优惠卷");
            //可用优惠卷
            List<OrderCouponVo> coupons = coupon.getValidCoupons(param.getWxUserInfo().getUserId());
            if (CollectionUtils.isEmpty(coupons)) {
                return;
            }
            for (OrderCouponVo temp : coupons) {
                for (OrderGoodsBo bo : param.getBos()) {
                    if (coupon.isContainsProduct(temp, bo)) {
                        logger().info("该商品可用使用该优惠卷");
                        if (temp.getBos() == null) {
                            temp.setBos(new ArrayList<>());
                        }
                        //优惠卷设置商品
                        temp.getBos().add(bo);
                    }
                }
            }
            for (Iterator<OrderCouponVo> i = coupons.iterator(); i.hasNext(); ) {
                OrderCouponVo temp = i.next();
                if (temp.getBos() != null) {
                    //获取该优惠卷对应商品的总价、总数
                    BigDecimal[] tolalNumberAndPrice = getTolalNumberAndPriceByType(temp.getBos(), OrderConstant.D_T_COUPON, null);
                    if (BigDecimalUtil.compareTo(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_PRICE], null) == 1) {
                        //获取折扣金额
                        BigDecimal discountAmount = coupon.getDiscountAmount(temp, tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_PRICE]);
                        logger().info("优惠卷折扣金额为{}", discountAmount);
                        //折扣金额 > 0
                        if (BigDecimalUtil.compareTo(discountAmount, null) > 0) {
                            temp.setTotalDiscount(discountAmount);
                            temp.setTotalGoodsNumber(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_NUMBER]);
                            temp.setTotalPrice(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_PRICE]);
                            temp.setIdentity(temp.getCouponSn());
                            //折扣
                            BigDecimal ratio = temp.initRatio();
                            //
                            if (ratio.compareTo(BigDecimal.ZERO) == -1 || ratio.compareTo(BigDecimal.ONE) == 1) {
                                logger().error("订单结算优惠券计算ratio数据非法,信息为:", param.getWxUserInfo(), temp.getCouponSn());
                                //数据异常不影响正常流程，不使用该优惠券
                                i.remove();
                            }
                        } else {
                            logger().info("优惠券折扣金额不大于0删除");
                            i.remove();
                        }
                    }
                } else {
                    logger().info("优惠券无匹配商品删除");
                    i.remove();
                }
            }
            //再次校验
            if (CollectionUtils.isEmpty(coupons)) {
                vo.setCouponSn(null);
                logger().info("无可用优惠券");
                return;
            }
            if (OrderConstant.DEFAULT_COUPON_OR_ORDER_SN.equals(param.getCouponSn())) {
                //默认选择
                vo.setCouponSn(coupons.get(0).getCouponSn());
                vo.setDefaultCoupon(coupons.get(0));
            } else if (StringUtils.isBlank(param.getCouponSn())) {
                //不选择优惠券
            } else {
                //选择指定优惠券
                for (OrderCouponVo coupon : coupons) {
                    if (coupon.getCouponSn().equals(param.getCouponSn())) {
                        vo.setCouponSn(param.getCouponSn());
                        vo.setDefaultCoupon(coupon);
                    }
                }
            }
            vo.setCoupons(coupons);
            logger().info("获取可以优惠卷列表end,列表：{}，此次选择：{}", vo.getCoupons(), vo.getDefaultCoupon());
        }
        logger().info("获取可以优惠卷列表end,列表：{}，此次选择：{}", vo.getCoupons(), vo.getDefaultCoupon());
    }

    /**
     * 获取会员卡列表
     *
     * @param param
     * @param vo
     */
    public void calculateCardInfo(OrderBeforeParam param, OrderBeforeVo vo) {
        logger().info("获取可用会员卡列表start");
        //会员卡折扣
        if (!StringUtils.isBlank(param.getMemberCardNo())) {
            /**使用会员卡，其中cardNo==0为使用默认会员卡*/
            OrderMemberVo card = userCard.userCardDao.getValidByCardNo(param.getMemberCardNo());
            if (card != null && CardConstant.MCARD_TP_LIMIT.equals(card.getCardType())) {
                //限次卡
                List<OrderMemberVo> validCardList = userCard.getValidCardList(param.getWxUserInfo().getUserId(), param.getBos(), param.getStoreId(), Lists.newArrayList(card));
                vo.setDefaultMemberCard(card);
                vo.setMemberCards(validCardList);
            } else {
                //普通卡、等级卡（或者没有传入cardNo）
                OrderMemberVo defaultCard = card;
                if (OrderConstant.DEFAULT_COUPON_OR_ORDER_SN.equals(param.getMemberCardNo())) {
                    defaultCard = userCard.userCardDao.getOrderGradeCard(param.getWxUserInfo().getUserId());
                }
                List<OrderMemberVo> validCardList = userCard.getValidCardList(param.getWxUserInfo().getUserId(), param.getBos(), param.getStoreId(), defaultCard == null ? Lists.newArrayList() : Lists.newArrayList(defaultCard));
                defaultCard = defaultCard != null ? defaultCard : (CollectionUtils.isEmpty(validCardList) ? null : validCardList.get(0));
                vo.setDefaultMemberCard(defaultCard);
                vo.setMemberCards(validCardList);
            }
        } else {
            //不使用会员卡
            List<OrderMemberVo> validCardList = userCard.getValidCardList(param.getWxUserInfo().getUserId(), param.getBos(), param.getStoreId(), null);
            vo.setMemberCards(validCardList);
        }
        logger().info("获取可用会员卡列表end,列表：{}，此次选择：{}", vo.getMemberCards(), vo.getDefaultMemberCard());
    }

    /**
     * 设置商品行折扣信息前保存记录
     *
     * @param bo            业务数据
     * @param marketingType 营销类型
     */
    public void saveRecord(OrderGoodsBo bo, Byte marketingType) {
        bo.setDiscountDetail(
            new StringBuilder().append("marketingType:").append(marketingType).
                append(",old_discounted_goods_price:").append(bo.getDiscountedGoodsPrice()).
                append(",old_discounted_total_price:").append(bo.getDiscountedTotalPrice()).
                toString()
        );
    }

    /**
     * 设置商品行折扣信息
     *
     * @param totalDiscountPrica
     * @param bo                 业务对象
     * @return 此次折扣金额
     */
    public BigDecimal setOrderGoodsDiscountInfo(BigDecimal totalDiscountPrica, OrderGoodsBo bo) {
        logger().info("setOrderGoodsDiscountInfo设置商品行折扣信息:totalDiscountPrica->{},bo->{}", totalDiscountPrica, bo);
        //商品单价折扣金额
        BigDecimal perDiscount = BigDecimalUtil.divide(totalDiscountPrica, BigDecimal.valueOf(bo.getGoodsNumber()));
        //新折后单价
        BigDecimal newDiscountedGoodsPrice = BigDecimalUtil.subtrac(bo.getDiscountedGoodsPrice(), perDiscount);
        //折后单价小于0（eg:商品10.00,优惠卷减20）时,折扣金额为商品折后单价
        if (newDiscountedGoodsPrice.compareTo(BigDecimal.ZERO) == -1) {
            perDiscount = bo.getDiscountedGoodsPrice();
            newDiscountedGoodsPrice = BigDecimal.ZERO.setScale(2);
            totalDiscountPrica = BigDecimalUtil.multiply(perDiscount, BigDecimal.valueOf(bo.getGoodsNumber()));
        }
        //商品最后一个Sku单价折扣金额(退款使用，有时退不完)
        BigDecimal lastSkuReduce = BigDecimalUtil.subtrac(
            totalDiscountPrica,
            BigDecimalUtil.multiply(BigDecimal.valueOf(bo.getGoodsNumber() - 1),
                perDiscount)
        );
        //数据库记录修改前信息
        //赋值
        bo.setDiscountedGoodsPrice(newDiscountedGoodsPrice);
        bo.setDiscountedTotalPrice(BigDecimalUtil.subtrac(bo.getDiscountedTotalPrice(), totalDiscountPrica));
        return totalDiscountPrica;
    }

    /**
     * 计算运费
     *
     * @param districtCode     区县编号
     * @param bos              bos
     * @param storeId          门店id
     * @param noCalculateGoods 不参与计算的商品
     * @return 运费
     */
    public BigDecimal calculateShippingFee(Integer districtCode, List<OrderGoodsBo> bos, Integer storeId, List<Integer> noCalculateGoods) {
        logger().info("计算运费start");
        BigDecimal result = BigDecimal.ZERO;
        //处理过程中局部内部类
        @NoArgsConstructor
        @Getter
        @Setter
        class Total {
            private List<OrderGoodsBo> bos = Lists.newArrayList();
            private Integer totalNumber = 0;
            private BigDecimal totalPrice = BigDecimal.ONE;
            private BigDecimal totalWeight = BigDecimal.ZERO.setScale(3);
        }
        Map<Integer, Total> totalMaps = Maps.newHashMap();
        for (OrderGoodsBo bo : bos) {
            //TODO 检查加价购换购商品是否走运费计算

            if (totalMaps.get(bo.getDeliverTemplateId()) == null) {
                totalMaps.put(bo.getDeliverTemplateId(), new Total());
            }
            Total total = totalMaps.get(bo.getDeliverTemplateId());
            total.getBos().add(bo);
            total.setTotalNumber(total.getTotalNumber() + bo.getGoodsNumber());
            total.setTotalPrice(total.getTotalPrice().add(bo.getGoodsPrice()));
            total.setTotalWeight(total.getTotalWeight().add(bo.getGoodsWeight()));
            bo.getDeliverTemplateId();
        }

        for (Map.Entry<Integer, Total> entry : totalMaps.entrySet()) {
            Integer templateId = entry.getKey();
            Total total = entry.getValue();
            BigDecimal shippingFeeByTemplate = null;
            try {
                if (districtCode == null || districtCode.equals(0)) {
                    total.getBos().forEach(x -> {
                        x.setIsShipping(OrderConstant.NO);
                    });
                } else {
                    shippingFeeByTemplate = shippingFeeTemplate.getShippingFeeByTemplate(districtCode, templateId, total.getTotalNumber(), total.getTotalPrice(), total.getTotalWeight());
                    result = BigDecimalUtil.add(result, shippingFeeByTemplate);
                    total.getBos().forEach(x -> {
                        x.setIsShipping(OrderConstant.YES);
                    });
                }
            } catch (MpException e) {
                total.getBos().forEach(x -> {
                    x.setIsShipping(OrderConstant.NO);
                });
            }
        }
        logger().info("计算运费end");
        return result;
    }

    /**
     * 计算运费
     *
     * @param userId      用户id
     * @param lat         经度
     * @param lng         纬度
     * @param goodsId     商品id
     * @param templateId  模板
     * @param totalNumber 要购买的商品数量
     * @param goodsPrice  单个商品价格
     * @param goodWeight  单个商品重量
     * @return 运费
     * @author kdc
     */
    public BigDecimal calculateShippingFee(Integer userId, String lat, String lng, Integer goodsId, Integer templateId, Integer totalNumber, BigDecimal goodsPrice, BigDecimal goodWeight) {
        logger().debug("开始计算运费，输入 userId{},lat:{},lng:{},goodsId:{},templateId:{},totalNumber:{},goodsPrice:{},goodsWeight:{}", userId, lat, lng, goodsId, templateId, totalNumber, goodsPrice, goodWeight);

        AddressInfo userAddress = addressService.getGeocoderAddressInfo(lat, lng);
        logger().debug("封装用户地址信息：{}", userAddress);
        Integer districtCode = addressService.getUserAddressDistrictId(userAddress);
        logger().debug("获取用户地址区域code:{}", districtCode);
        if (districtCode == null) {
            logger().debug("获取用户最近订单地址");
            UserAddressVo lastOrderAddress = orderInfoService.getLastOrderAddress(userId);
            if (lastOrderAddress == null) {
                logger().debug("获取用户最近登录地址");
                Integer userLoginRecordDistrictCode = userLoginRecordService.getUserLoginRecordDistrictCode(userId);
                if (userLoginRecordDistrictCode != null) {
                    districtCode = userLoginRecordDistrictCode;
                }
            } else {
                districtCode = lastOrderAddress.getDistrictCode();
            }
        }
        logger().debug("用户地址code:{}", districtCode);
        BigDecimal shippingFeeByTemplate = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimalUtil.multiply(goodsPrice, BigDecimal.valueOf(totalNumber));
        BigDecimal totalWeight = BigDecimalUtil.multiply(goodWeight, BigDecimal.valueOf(totalNumber));
        try {
            shippingFeeByTemplate = shippingFeeTemplate.getShippingFeeByTemplate(districtCode, templateId, totalNumber, totalPrice, totalWeight);
        } catch (MpException | NullPointerException e) {
            logger().debug("获取商品运费信息失败");
            e.printStackTrace();
        }
        return shippingFeeByTemplate;
    }

    /**
     * 下单校验必填信息
     *
     * @param orderGoods goods
     * @return
     */
    public OrderMustVo getOrderMust(List<OrderGoodsBo> orderGoods) {
        OrderMustVo must = new OrderMustVo();
        //初始化赋值
        must.init(trade);
        if (OrderConstant.NO == must.isCheck()) {
            return must;
        }
        //规则(This will never be null)
        GoodsPackageParam rule = trade.getOrderRequireGoodsPackage();

        if (rule.getAddGoods() != null) {
            //goodsId校验
            ArrayList<Integer> goodsIds = Lists.newArrayList(rule.getAddGoods());
            goodsIds.retainAll(orderGoods.stream().map(OrderGoodsBo::getGoodsId).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(goodsIds)) {
                return must.show();
            }
        }

        if (rule.getAddCate() != null) {
            //cateId校验
            ArrayList<Integer> cateIds = Lists.newArrayList(rule.getAddCate());
            cateIds.retainAll(orderGoods.stream().map(OrderGoodsBo::getCatId).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(cateIds)) {
                return must.show();
            }
        }

        if (rule.getAddSort() != null) {
            //sortId校验
            ArrayList<Integer> sortId = Lists.newArrayList(rule.getAddSort());
            sortId.retainAll(orderGoods.stream().map(OrderGoodsBo::getSortId).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(sortId)) {
                return must.show();
            }
        }

        if (rule.getAddBrand() != null) {
            //brandId校验
            ArrayList<Integer> brandIds = Lists.newArrayList(rule.getAddBrand());
            brandIds.retainAll(orderGoods.stream().map(OrderGoodsBo::getBrandId).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(brandIds)) {
                return must.show();
            }
        }
        return must.hide();
    }

    /**
     * 获取商品退款退货配置
     *
     * @param orderGoods 商品
     * @return map<goodsid, no / ys>
     */
    public Map<Integer, Byte> getGoodsReturnCfg(List<OrderGoodsBo> orderGoods) {
        return Maps.newHashMap();
    }

    /**
     * 设置商品退款退货配置
     *
     * @param orderGoods 商品
     * @param goodsType  商品类型
     * @param posFlag    pos标识
     */
    public void setGoodsReturnCfg(List<OrderGoodsBo> orderGoods, String goodsType, Byte posFlag) {
        Byte isCanReturn = null;
        Map<Integer, Byte> goodsReturnCfg = null;
        if (Lists.newArrayList(OrderInfoService.orderTypeToArray(goodsType)).contains(OrderConstant.GOODS_TYPE_GIVE_GIFT)) {
            isCanReturn = OrderConstant.IS_CAN_RETURN_N;
        } else if (posFlag != null && OrderConstant.YES == posFlag) {
            isCanReturn = OrderConstant.IS_CAN_RETURN_Y;
        }
        if (isCanReturn == null) {
            goodsReturnCfg = getGoodsReturnCfg(orderGoods);
        }
        for (OrderGoodsBo bo : orderGoods) {
            bo.setIsCanReturn(isCanReturn != null ? isCanReturn : goodsReturnCfg.get(bo.getGoodsId()));
        }
    }

    public UniteMarkeingtRecalculateBo uniteMarkeingtRecalculate(OrderBeforeParam.Goods goods, OrderCartProductBo.OrderCartProduct uniteMarkeingt){
        //TODO 分销改价

        //首单特惠
        if(uniteMarkeingt != null && uniteMarkeingt.getFirstSpecialPrice() != null) {
            return UniteMarkeingtRecalculateBo.create(uniteMarkeingt.getFirstSpecialPrice(), BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL, uniteMarkeingt.getFirstSpecialId());
        }

        //限时降价

        //会员价
        if(uniteMarkeingt != null && uniteMarkeingt.getGradeCardPrice() != null){
            return UniteMarkeingtRecalculateBo.create(uniteMarkeingt.getGradeCardPrice(), BaseConstant.ACTIVITY_TYPE_MEMBER_GRADE, null);
        }

        return UniteMarkeingtRecalculateBo.create(goods.getProductPrice(), BaseConstant.ACTIVITY_TYPE_GENERAL, null);
    }

}
