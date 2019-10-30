package com.vpu.mp.service.shop.overview;

import com.vpu.mp.db.shop.tables.*;
import com.vpu.mp.db.shop.tables.records.CardExamineRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.MrkingVoucherRecord;
import com.vpu.mp.db.shop.tables.records.XcxCustomerPageRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.WxShoppingListConfig;
import com.vpu.mp.service.pojo.shop.overview.*;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import com.vpu.mp.service.shop.config.WxShoppingListConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Record1;
import org.jooq.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

import static com.vpu.mp.db.shop.tables.MrkingVoucher.MRKING_VOUCHER;
import static com.vpu.mp.db.shop.tables.XcxCustomerPage.XCX_CUSTOMER_PAGE;
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

    /**
     * 获取数据展示数据，
     * @param param 1：当天数据；7：近一周数据；30：近一个月数据；90：近三个月数据
     * @return
     */
    public DataDemonstrationVo dataDemonstration(DataDemonstrationParam param){
        byte screenTime = param.getScreeningTime();
        if(!Arrays.asList((byte)1,7,30,90).contains(screenTime)){
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

        double orderNum = vo.getOrderNum();
        double userVisitNum = vo.getUserVisitNum();
        double paidNum = vo.getPaidOrderNum();
        vo.setUv2order(userVisitNum!=0&&orderNum!=0 ? orderNum/userVisitNum : 0.0);
        vo.setUv2paid(userVisitNum!=0&&paidNum!=0 ? paidNum/userVisitNum : 0.0);
        vo.setOrder2paid(paidNum!=0&&orderNum!=0 ? paidNum/orderNum : 0.0);
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
                .where(gsp.DEL_FLAG.eq((byte)0))
                .and(g.DEL_FLAG.eq((byte)0))
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
     * @param param
     * @param vo
     * @return
     */
    public ShopAssistantVo shopAssistant(ShopAssistantParam param,ShopAssistantVo vo){
        shopNav(param,vo);
        goodsNav(param,vo);
        orderNav(param,vo);
        marketNav(param,vo);
        return vo;
    }
    public ShopAssistantVo shopNav(ShopAssistantParam param, ShopAssistantVo vo){
        AssiDataShop dataShop = new AssiDataShop();
        //  店铺首页 0：已完成店铺首页装修，否未装修店铺首页
        XcxCustomerPageRecord xcxCustomerPage = db().selectFrom(XCX_CUSTOMER_PAGE)
            .where(XCX_CUSTOMER_PAGE.PAGE_TYPE.eq(BYTE_ONE))
            .and(XCX_CUSTOMER_PAGE.SHOP_ID.eq(getShopId()))
            .fetchOne();
        if (xcxCustomerPage != null) {
            boolean condi1 = StringUtils.isNotBlank(xcxCustomerPage.getPageContent());
            boolean condi2 = StringUtils.isNotBlank(xcxCustomerPage.getPagePublishContent());
            dataShop.setHomePageConf(condi1 || condi2 ? BYTE_ZERO : BYTE_ONE);
        } else {
            dataShop.setHomePageConf(BYTE_ONE);
            vo.totalPendingIncr();
        }
        //  好物圈 0: 已开启好物圈，否未开启
        WxShoppingListConfig shoppingListConfig = shoppingListConfigService.getShoppingListConfig();
        if (String.valueOf(BYTE_ZERO).equals(shoppingListConfig.getEnabeldWxShoppingList())) {
            dataShop.setShopRecommendConf(BYTE_ONE);
            vo.totalPendingIncr();
        }else{
            dataShop.setShopRecommendConf(BYTE_ZERO);
            dataShop.setShopRecommendLink(shoppingListConfig.getWxShoppingRecommend());
        }
        //  客服 0: 已开启客服，否未开启
        if (shopCommonConfigService.getCustomService().equals(BYTE_ONE) || shopCommonConfigService.getReturnService().equals(BYTE_ONE)) {
            dataShop.setCustomServiceConf(BYTE_ZERO);
        } else {
            dataShop.setCustomServiceConf(BYTE_ONE);
            vo.totalPendingIncr();
        }
        vo.setDataShop(dataShop);
        log.debug("shop走完了");
        return vo;
    }

    public ShopAssistantVo goodsNav(ShopAssistantParam param, ShopAssistantVo vo){
        AssiDataGoods dataGoods = new AssiDataGoods();
        ;
        /**  运费模板设置 */
        int deliverCount = db().fetchCount(DeliverFeeTemplate.DELIVER_FEE_TEMPLATE);
        dataGoods.setShipTemplateConf(deliverCount > 0 ? (byte) 0 : (byte) -1);
        if(deliverCount <= 0){vo.totalPendingIncr();}
        /**  商品添加 */
        int goodsCount = db().fetchCount(Goods.GOODS,Goods.GOODS.DEL_FLAG.eq((byte)0));
        dataGoods.setGoodsConf(goodsCount > 0 ? (byte) 0 : (byte) -1);
        if(goodsCount <= 0){vo.totalPendingIncr();}
        /**  商品库存偏小 */
        int goodsNum = db().fetchCount(Goods.GOODS,Goods.GOODS.DEL_FLAG.eq((byte)0)
                .and(Goods.GOODS.GOODS_NUMBER
                        .lessThan(param.getStoreSizeNum())));
        dataGoods.setGoodsStoreConf(goodsNum);
        if(goodsNum > 0){vo.totalPendingIncr();}
        /**  滞销商品 */
        OrderInfo oi = OrderInfo.ORDER_INFO.as("oi");
        OrderGoods og = OrderGoods.ORDER_GOODS.as("og");
        Select<? extends Record1<Integer>> select  = db().select(og.GOODS_ID).from(og).leftJoin(oi)
                .on(og.ORDER_ID.eq(oi.ORDER_ID))
                .where(oi.CREATE_TIME.lessOrEqual(Util.getEarlyTimeStamp(new Date(),-30)))
                .and(og.UPDATE_TIME.greaterOrEqual(Util.getEarlyTimeStamp(new Date(),-30)));
        int unsalableCount = db().fetchCount(Goods.GOODS,Goods.GOODS.DEL_FLAG.eq((byte)0).and(Goods.GOODS.GOODS_ID.notIn(select)));
        dataGoods.setGoodsUnsalableConf(unsalableCount);
        if(unsalableCount > 0){vo.totalPendingIncr();}
        /**  商品评价审核逾期 */
        int commCount = db().fetchCount(CommentGoods.COMMENT_GOODS,CommentGoods.COMMENT_GOODS.DEL_FLAG.eq((byte)0)
                .and(CommentGoods.COMMENT_GOODS.FLAG.eq((byte)0))
                .and(CommentGoods.COMMENT_GOODS.CREATE_TIME.lessThan(Util.getEarlyTimeStamp(new Date(),-param.getCommentOver()))));
        dataGoods.setGoodsComment(commCount);
        if(commCount > 0){vo.totalPendingIncr();}
        /**  推荐商品 */
        int recommCount = db().fetchCount(RecommendGoods.RECOMMEND_GOODS);
        dataGoods.setGoodsRecommend(recommCount);
        if(recommCount <= 0){vo.totalPendingIncr();}
        /**  商家分类 */
        int sortCount = db().fetchCount(Sort.SORT);
        dataGoods.setShopSort(sortCount);
        if(sortCount <= 0){vo.totalPendingIncr();}
        vo.setDataGoods(dataGoods);
        log.debug("goods走完了");
        return vo;
    }

    public ShopAssistantVo orderNav(ShopAssistantParam param, ShopAssistantVo vo){
        AssiDataOrder dataOrder = new AssiDataOrder();
        ;
        /**  发货逾期 */
        int deliverCount = db().fetchCount(OrderInfo.ORDER_INFO,OrderInfo.ORDER_INFO.ORDER_STATUS.eq((byte)3)
                .and(OrderInfo.ORDER_INFO.CREATE_TIME.lessThan(Util.getEarlyTimeStamp(new Date(),-param.getDeliverOver()))));
        dataOrder.setDeliver(deliverCount);
        if(deliverCount > 0){vo.totalPendingIncr();}
        /**  退款申请逾期 */
        int refundCount = db().fetchCount(OrderInfo.ORDER_INFO,OrderInfo.ORDER_INFO.REFUND_STATUS.in((byte)1,(byte)2,(byte)4)
                .and(OrderInfo.ORDER_INFO.CREATE_TIME.lessThan(Util.getEarlyTimeStamp(new Date(),-param.getRefundOver()))));
        dataOrder.setRefund(refundCount);
        if(refundCount > 0){vo.totalPendingIncr();}
        vo.setDataOrder(dataOrder);
        log.debug("order走完了");
        return vo;
    }

    public ShopAssistantVo marketNav(ShopAssistantParam param, ShopAssistantVo vo){
        AssiDataMarket dataMarket = new AssiDataMarket();
        ;
        /**  分销审核超时 */
        int disCount = db().fetchCount(DistributorApply.DISTRIBUTOR_APPLY,
                DistributorApply.DISTRIBUTOR_APPLY.CREATE_TIME.lessThan(Util.getEarlyTimeStamp(new Date(),-param.getApplyOver())));
        dataMarket.setExamine(disCount);
        if(disCount > 0){vo.totalPendingIncr();}
        /**  会员卡激活审核 */
        Map<String,String> memberMap = new HashMap<>(4);
        CardExamineRecord cardExamineRecord = new CardExamineRecord();
        List<CardExamine> cardExamineList = db().select(CardExamine.CARD_EXAMINE.CARD_ID)
                    .from(CardExamine.CARD_EXAMINE)
                    .where(CardExamine.CARD_EXAMINE.STATUS.eq((byte)1))
                    .and(CardExamine.CARD_EXAMINE.DEL_FLAG.eq((byte)0))
                    .and(CardExamine.CARD_EXAMINE.CREATE_TIME.lessThan(Util.getEarlyTimeStamp(new Date(),-param.getExamineOver())))
                    .orderBy(CardExamine.CARD_EXAMINE.CREATE_TIME.asc())
                    .fetchInto(CardExamine.class);
        if(cardExamineList!=null&&!cardExamineList.isEmpty()){
            int lastRecordCardId = cardExamineList.get(0).CARD_ID.get(cardExamineRecord);

            int cardNum = db().fetchCount(CardExamine.CARD_EXAMINE,CardExamine.CARD_EXAMINE.CARD_ID.eq(lastRecordCardId));
            List<MemberCard> memberCards = db().select(MemberCard.MEMBER_CARD.CARD_NAME)
                    .from(MemberCard.MEMBER_CARD)
                    .where(MemberCard.MEMBER_CARD.ID.eq(lastRecordCardId))
                    .fetchInto(MemberCard.class);
            String cardName = memberCards!=null&&!memberCards.isEmpty() ? memberCards.get(0).CARD_NAME.get(new MemberCardRecord()) : null;
            memberMap.put("card_id",String.valueOf(lastRecordCardId));
            memberMap.put("card_name",cardName);
            memberMap.put("card_num",String.valueOf(cardNum));
            dataMarket.setMember(memberMap);
            vo.totalPendingIncr();
        }else{
            memberMap.put("card_num","0");
            dataMarket.setMember(memberMap);
        }
        /**  优惠券 */
        MrkingVoucherRecord voucherRecord = new MrkingVoucherRecord();
        Map<Integer, String> voucher = db().select(MRKING_VOUCHER.ID, MRKING_VOUCHER.ACT_NAME)
            .from(MRKING_VOUCHER)
            .where(MRKING_VOUCHER.SURPLUS.lessOrEqual(param.getCouponSizeNum()))
            .and(MRKING_VOUCHER.ENABLED.eq((byte) 1))
            .and(MRKING_VOUCHER.DEL_FLAG.eq((byte) 0))
            .and(MRKING_VOUCHER.CREATE_TIME.add(MRKING_VOUCHER.VALIDATION_CODE).greaterThan(new Timestamp(System.currentTimeMillis())))
            .orderBy(MRKING_VOUCHER.SURPLUS, MRKING_VOUCHER.CREATE_TIME)
                .limit(5)
            .fetchMap(MRKING_VOUCHER.ID, MRKING_VOUCHER.ACT_NAME);
        dataMarket.setVoucher(voucher);
        if(!voucher.isEmpty()){vo.totalPendingIncr();}
        vo.setDataMarket(dataMarket);
        log.debug("market走完了");
        return vo;
    }
}
