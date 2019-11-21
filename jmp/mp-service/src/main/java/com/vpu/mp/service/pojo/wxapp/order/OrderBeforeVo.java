package com.vpu.mp.service.pojo.wxapp.order;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.pojo.shop.member.address.UserAddressVo;

import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.payment.PaymentVo;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.coupon.OrderCouponVo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.member.OrderMemberVo;
import com.vpu.mp.service.pojo.wxapp.order.must.OrderMustVo;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author 王帅
 *
 */
@Data
@Builder(toBuilder = false)
public class OrderBeforeVo {
    /**商品*/
    private List<OrderGoodsBo> orderGoods;

    /** 指定本次结算所参加的唯一营销活动类型 {@link com.vpu.mp.service.foundation.data.BaseConstant} 下的ACTIVITY_TYPE**/
    private Byte activityType;
    /** 指定本次结算所参加的唯一营销活动类型 ID */
    private Integer activityId;

	private UserAddressVo address;
	private Byte[] expressList;
	private Byte deliverType;
	private String memberCardNo;
    private String couponSn;
    private OrderCouponVo defaultCoupon;
    private List<OrderCouponVo> coupons;
    /**TODO goodstype*/
    private Byte goodsType;
    private List<StorePojo> storeList;
    private OrderMemberVo defaultMemberCard;
    private List<OrderMemberVo> memberCards;
    private Map<String, PaymentVo> paymentList;
    /**必填信息*/
    private OrderMustVo must;
    /**服务条款_展示*/
    private Byte isShowserviceTerms;
    /**服务条款_首次是否默认勾选*/
    private Byte serviceChoose;
    /**服务条款_服务条款名称*/
    private String serviceName;
    /**积分支付规则_是否限制*/
    private Byte scorePayLimit;
    /**积分支付规则_每单支付的积分数量少于 score_pay_num 积分，不可使用积分支付*/
    private Integer scorePayNum;
    /**运费*/
    private BigDecimal shippingFee;
    /**订单金额*/
    private BigDecimal orderAmount;
    /**商品总量*/
    private Integer totalGoodsNumber;
    /**积分抵扣*/
    private BigDecimal scoreDiscount;
    /**余额抵扣金额*/
    private BigDecimal accountDiscount;
    /**会员卡抵扣金额*/
    private BigDecimal memberCardDiscount;
    /**会员卡折扣金额*/
    private BigDecimal memberCardReduce;
    /**折扣（优惠卷折扣）*/
    private BigDecimal discount;
    /**支付金额（微信）*/
    private BigDecimal moneyPaid;
    /**微信支付金额-运费*/
    private BigDecimal discountedMoney;
    /**用户积分*/
    private Integer userScore;
    /**用户余额*/
    private BigDecimal userAccount;
    /**会员卡余额*/
    private BigDecimal memberCardMoney;
    /**折后订单金额*/
    private BigDecimal moneyAfterDiscount;
    /**支付方式*/
    private Byte orderPayWay;
    /**是否兑换*/
    private Byte exchang;
    /**是否可以配送*/
    private Byte canShipping;
    /**积分最大抵用金额*/
    private BigDecimal scoreMaxDiscount;
    /**发票开关*/
    private Byte invoiceSwitch;
    /**
     * 拍下未付款订单12小时10分钟内未付款，自动取消订单
     * cancel_time保存形式为分钟，例如：730
     */
    private Integer cancelTime;
    /**TODO 自提时间*/
    private Timestamp[][] pickupDate;
    /**???*/
    @Builder.Default
    private BigDecimal promotionReduce = BigDecimalUtil.BIGDECIMAL_ZERO;
    /**???*/
    @Builder.Default
    private BigDecimal packageDiscount = BigDecimalUtil.BIGDECIMAL_ZERO;
    /**???*/
    @Builder.Default
    private BigDecimal grouperCheapReduce = BigDecimalUtil.BIGDECIMAL_ZERO;
    /**???*/
    @Builder.Default
    private BigDecimal preSaleDiscount = BigDecimalUtil.BIGDECIMAL_ZERO;
    /**???*/
    @Builder.Default
    private BigDecimal bkOrderMoney = BigDecimalUtil.BIGDECIMAL_ZERO;
    /**???*/
    @Builder.Default
    private Timestamp bkShippingTime;
    /**???*/
    private Byte bkReturnType;
    /*TODO 代付金额*/
    @Builder.Default
    private BigDecimal insteadPayMoney = BigDecimalUtil.BIGDECIMAL_ZERO;
    /**默认支付配置->会员卡余额*/
    private Byte isCardPay;
    /**默认支付配置->余额*/
    private Byte isBalancePay;
    /**默认支付配置->积分*/
    private Byte isScorePay;

    /**
     * 默认配送方式 0 ，1 ，2
     * @return
     */
    public Byte getDefaultDeliverType(){
        for (int i = 0, length = expressList.length ; i < length ; i++){
            if(expressList[i] == OrderConstant.YES){
                return (byte)i;
            }
        }
        return null;
    }

    public void intoRecord(OrderInfoRecord orderRecord){
        //支付方式
        orderRecord.setGoodsAmount(getTotalGoodsNumber().shortValue());
        orderRecord.setShippingFee(getShippingFee());
        orderRecord.setMoneyPaid(getMoneyPaid());
        orderRecord.setOrderAmount(getOrderAmount());
        orderRecord.setGrouperCheapReduce(getGrouperCheapReduce());
        orderRecord.setMemberCardReduce(getMemberCardReduce());
        orderRecord.setPromotionReduce(getPromotionReduce());
        orderRecord.setDiscount(getDiscount());
        orderRecord.setScoreDiscount(getScoreDiscount());
        orderRecord.setUseAccount(getAccountDiscount());
        orderRecord.setMemberCardBalance(getMemberCardDiscount());
        orderRecord.setPackageDiscount(getPackageDiscount());
        orderRecord.setPreSaleDiscount(getPreSaleDiscount());
        orderRecord.setOrderPayWay(getOrderPayWay());
        orderRecord.setBkOrderMoney(getBkOrderMoney());
        orderRecord.setBkShippingTime(getBkShippingTime());
        orderRecord.setBkReturnType(getBkReturnType());
        orderRecord.setInsteadPayMoney(getInsteadPayMoney());
        orderRecord.setExchang(getExchang());
        //orderRecord.setFreeShip(getFreeShip());
        //orderRecord.setFreeDetail(getFreeDetail());
        //orderRecord.setPosOrderAction(getPosOrderAction());
        if(getActivityId() != null && getActivityType() != null){
            //营销活动订单
            orderRecord.setActivityId(getActivityId());
        }
    }
}
