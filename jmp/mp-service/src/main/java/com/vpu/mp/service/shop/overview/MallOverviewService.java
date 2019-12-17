package com.vpu.mp.service.shop.overview;

import com.vpu.mp.db.shop.tables.*;
import com.vpu.mp.db.shop.tables.records.CardExamineRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.WxShoppingListConfig;
import com.vpu.mp.service.pojo.shop.member.card.ActiveAuditParam;
import com.vpu.mp.service.pojo.shop.overview.*;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import com.vpu.mp.service.shop.config.WxShoppingListConfigService;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.distribution.DistributorCheckService;
import com.vpu.mp.service.shop.goods.GoodsCommentService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.member.CardVerifyService;
import com.vpu.mp.service.shop.member.dao.CardDaoService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.refund.ReturnOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.vpu.mp.db.shop.tables.DeliverFeeTemplate.DELIVER_FEE_TEMPLATE;
import static com.vpu.mp.db.shop.tables.RecommendGoods.RECOMMEND_GOODS;
import static com.vpu.mp.db.shop.tables.Sort.SORT;
import static com.vpu.mp.db.shop.tables.XcxCustomerPage.XCX_CUSTOMER_PAGE;
import static com.vpu.mp.service.foundation.util.BigDecimalUtil.divideWithOutCheck;
import static com.vpu.mp.service.pojo.shop.overview.OverviewConstant.STRING_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.sum;

/**
 * author liufei
 * date 2019/7/15
 * 商城概览service
 */
@Service
@Slf4j
public class MallOverviewService extends ShopBaseService {
    @Autowired
    public WxShoppingListConfigService shoppingListConfigService;

    @Autowired
    public ShopCommonConfigService shopCommonConfigService;

    @Autowired
    public CouponService couponService;

    @Autowired
    public DistributorCheckService distributorCheckService;

    @Autowired
    private OrderInfoService orderInfo;

    @Autowired
    public ReturnOrderService returnOrderService;

    @Autowired
    public GoodsCommentService goodsCommentService;

    @Autowired
    public GoodsService goodsService;

    @Autowired
    public CardVerifyService cardVerifyService;

    @Autowired
    public CardDaoService cardDaoService;

    public static final List<Byte> RECENT_DATE = new ArrayList<Byte>() {{
        add(Byte.valueOf("1"));
        add(Byte.valueOf("2"));
        add(Byte.valueOf("7"));
        add(Byte.valueOf("30"));
        add(Byte.valueOf("90"));
    }};
    /**
     * 获取数据展示数据，
     * @param param 1：当天数据；2表示昨天: 7：近一周数据；30：近一个月数据；90：近三个月数据
     */
    public DataDemonstrationVo dataDemonstration(DataDemonstrationParam param){
        byte screenTime = param.getScreeningTime();
        if (!RECENT_DATE.contains(screenTime)) {
            screenTime = 1;
        }
        return getDataDemonstration(screenTime);
    }
    private DataDemonstrationVo getDataDemonstration(byte screeningTime){
        DataDemonstrationVo vo = new DataDemonstrationVo();
        Condition orderInfoTime = OrderInfo.ORDER_INFO.CREATE_TIME.
                compare(Comparator.GREATER_OR_EQUAL,Util.getEarlyTimeStamp(new Date(),(-screeningTime+1)));
        Condition userLoginRecordTime = UserLoginRecord.USER_LOGIN_RECORD.CREATE_TIME.
                compare(Comparator.GREATER_OR_EQUAL,Util.getEarlyTimeStamp(new Date(),(-screeningTime+1)));
        Condition payOrderCon = OrderInfo.ORDER_INFO.ORDER_STATUS.compare(Comparator.GREATER_OR_EQUAL,(byte)3);
        vo.setUserVisitNum(db().fetchCount(UserLoginRecord.USER_LOGIN_RECORD,userLoginRecordTime));
        vo.setPaidOrderNum(db().fetchCount(OrderInfo.ORDER_INFO,orderInfoTime.and(payOrderCon)));
        vo.setOrderUserNum(db().selectDistinct(count(OrderInfo.ORDER_INFO.USER_ID))
                .from(OrderInfo.ORDER_INFO).where(orderInfoTime).execute());
        vo.setOrderNum(db().fetchCount(OrderInfo.ORDER_INFO,orderInfoTime));
        vo.setTotalPaidSum(db().select(sum(OrderInfo.ORDER_INFO.MONEY_PAID))
                .from(OrderInfo.ORDER_INFO).where(orderInfoTime.and(payOrderCon))
                .execute());
        vo.setPaidUserNum(db().selectDistinct(count(OrderInfo.ORDER_INFO.USER_ID))
                .from(OrderInfo.ORDER_INFO).where(orderInfoTime.and(payOrderCon))
                .execute());

        BigDecimal orderNum = new BigDecimal(vo.getOrderNum());
        BigDecimal userVisitNum = new BigDecimal(vo.getUserVisitNum());
        BigDecimal paidNum = new BigDecimal(vo.getPaidOrderNum());
        vo.setUv2order(divideWithOutCheck(orderNum, userVisitNum));
        vo.setUv2paid(divideWithOutCheck(paidNum, userVisitNum));
        vo.setOrder2paid(divideWithOutCheck(paidNum, orderNum));
        return vo;
    }

    /**
     * 代办事项
     * @return
     */
    public ToDoItemVo toDoItem(){
        ToDoItemVo toDoItemVo = new ToDoItemVo();
        Condition orderStatus = OrderInfo.ORDER_INFO.ORDER_STATUS.eq((byte)3);
        Condition deliverType0 = OrderInfo.ORDER_INFO.DELIVER_TYPE.eq((byte)0);
        Condition deliverType1 = OrderInfo.ORDER_INFO.DELIVER_TYPE.eq((byte)1);
        Condition refundStatus = ReturnOrder.RETURN_ORDER.REFUND_STATUS.in((byte)1,(byte)4);
        toDoItemVo.setPendingOrder(db().fetchCount(OrderInfo.ORDER_INFO,orderStatus.and(deliverType1)));
        toDoItemVo.setToBeDelivered(db().fetchCount(OrderInfo.ORDER_INFO,orderStatus.and(deliverType0)));
        toDoItemVo.setRefunds(db().fetchCount(ReturnOrder.RETURN_ORDER,refundStatus));
        GoodsSpecProduct gsp = GoodsSpecProduct.GOODS_SPEC_PRODUCT.as("gsp");
        Goods g = Goods.GOODS.as("g");
        toDoItemVo.setSoldOutGoods(db().select(count(gsp.PRD_ID))
                .from(gsp)
                .leftJoin(g)
                .on(gsp.GOODS_ID.eq(g.GOODS_ID))
                .where(g.DEL_FLAG.eq((byte)0))
                .and(gsp.PRD_NUMBER.eq(0))
                .and(g.SOURCE.eq((byte)0)).execute());
        toDoItemVo.setProductEvaluationPr(db().fetchCount(CommentGoods.COMMENT_GOODS,CommentGoods.COMMENT_GOODS.DEL_FLAG.eq((byte)0)
                .and(CommentGoods.COMMENT_GOODS.FLAG.eq((byte)0))));
        toDoItemVo.setDistributorPr(db().fetchCount(DistributorApply.DISTRIBUTOR_APPLY,DistributorApply.DISTRIBUTOR_APPLY.STATUS.eq((byte)0)));
        toDoItemVo.setMembershipCardPr(db().fetchCount(CardExamine.CARD_EXAMINE,CardExamine.CARD_EXAMINE.STATUS.eq((byte)1)));
        toDoItemVo.setDistributionWithdrawalPr(db().fetchCount(DistributionWithdraw.DISTRIBUTION_WITHDRAW,DistributionWithdraw.DISTRIBUTION_WITHDRAW.STATUS.eq((byte)1)));
        toDoItemVo.setServiceEvaluationPr(db().fetchCount(CommentService.COMMENT_SERVICE,CommentService.COMMENT_SERVICE.FLAG.eq((byte)0)));
        return toDoItemVo;
    }

    /**
     * 店铺助手
     *
     * @param param the param
     * @return the shop assistant vo
     */
    public ShopAssistantVo shopAssistant(ShopAssistantParam param) {
        return ShopAssistantVo.builder()
            .dataShop(shopNav())
            .dataGoods(goodsNav(param))
            .dataOrder(orderNav(param))
            .dataMarket(marketNav(param))
            .build().ruleHandler();
    }

    /**
     * 店铺相关统计信息
     */
    private AssiDataShop shopNav() {
        WxShoppingListConfig shoppingListConfig = shoppingListConfigService.getShoppingListConfig();
        return new AssiDataShop.Builder()
            //  店铺首页 0：已完成店铺首页装修，否未装修店铺首页
            .setHomePageConf(shopPageConfig())
            //  好物圈 0: 已开启好物圈，否未开启
            .setShopRecommendConf(Byte.valueOf(shoppingListConfig.getEnabeldWxShoppingList()))
            .setShopRecommendLink(shoppingListConfig.getWxShoppingRecommend())
            //  客服 0: 已开启客服，否未开启
            .setCustomServiceConf(shopCommonConfigService.getCustomService() + shopCommonConfigService.getReturnService() > 0 ? BYTE_ZERO : BYTE_ONE)
            .build().ruleHandler();
    }

    /**
     * 商品相关统计信息
     */
    private AssiDataGoods goodsNav(ShopAssistantParam param) {
        return AssiDataGoods.builder()
            // 运费模板设置
            .shipTemplateConf(db().fetchCount(DELIVER_FEE_TEMPLATE) > 0 ? BYTE_ZERO : BYTE_ONE)
            // 商品添加
            .goodsConf(db().fetchCount(Goods.GOODS, Goods.GOODS.DEL_FLAG.eq(BYTE_ZERO)) > 0 ? BYTE_ZERO : BYTE_ONE)
            // 商品库存偏小
            .goodsStoreConf(goodsService.smallCommodityInventory(param.getStoreSizeNum()))
            //  滞销商品
            .goodsUnsalableConf(goodsService.unsalableGoods())
            //  商品评价审核逾期
            .goodsComment(goodsCommentService.reviewOverdue(param.getCommentOver()))
            //  推荐商品
            .goodsRecommend(db().fetchCount(RECOMMEND_GOODS))
            // 商家分类
            .shopSort(db().fetchCount(SORT))
            .build().ruleHandler();
    }

    /**
     * 订单相关统计信息
     */
    private AssiDataOrder orderNav(ShopAssistantParam param) {
        return AssiDataOrder.builder()
            //  发货逾期
            .deliver(orderInfo.overdueDelivery(param.getDeliverOver()))
            //  退款申请逾期
            .refund(returnOrderService.refundOverdue(param.getRefundOver()))
            .build().ruleHandler();
    }

    /**
     * 营销相关统计信息
     */
    private AssiDataMarket marketNav(ShopAssistantParam param) {
        return AssiDataMarket.builder()
            //  分销审核超时
            .examine(distributorCheckService.distributionReviewTimeout(param.getApplyOver()))
            // 会员卡激活审核超时
            .member(buildMemberVo(param.getExamineOver()))
            //  优惠券库存不足
            .voucher(couponService.getSmallInventoryCoupon(param.getCouponSizeNum()))
            .build().ruleHandler();
    }

    private Map<String, String> buildMemberVo(int examineOver) {
        CardExamineRecord cardExamineRecord = cardVerifyService.getLastRecordCanNull(new ActiveAuditParam() {{
            setExamineOver(Timestamp.valueOf(LocalDateTime.now().minusDays(examineOver)));
        }});
        if (Objects.isNull(cardExamineRecord)) {
            return new HashMap<String, String>(3) {{
                put("card_num", STRING_ZERO);
            }};
        }
        Integer cardId = cardExamineRecord.getCardId();
        return new HashMap<String, String>(3) {{
            put("card_id", String.valueOf(cardId));
            put("card_name", cardDaoService.getCardById(cardId).getCardName());
            put("card_num", cardVerifyService.getUndealUserNum(cardId).toString());
        }};
    }

    /**
     * 店铺首页 0：已完成店铺首页装修，否未装修店铺首页
     */
    private Byte shopPageConfig() {
        String shopPage = db().select(DSL.concat(XCX_CUSTOMER_PAGE.PAGE_CONTENT, XCX_CUSTOMER_PAGE.PAGE_PUBLISH_CONTENT)).from(XCX_CUSTOMER_PAGE)
            .where(XCX_CUSTOMER_PAGE.PAGE_TYPE.eq(BYTE_ONE))
            .and(XCX_CUSTOMER_PAGE.SHOP_ID.eq(getShopId()))
            .fetchOneInto(String.class);
        return StringUtils.isNoneBlank(shopPage) ? BYTE_ZERO : BYTE_ONE;
    }
}
