package com.vpu.mp.service.shop.order.action;

import com.vpu.mp.db.shop.tables.records.FanliGoodsStatisticsRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.OrderGoodsRebateRecord;
import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.DistributionConstant;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.card.ScoreJson;
import com.vpu.mp.service.pojo.shop.member.data.AccountData;
import com.vpu.mp.service.pojo.shop.member.data.ScoreData;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum;
import com.vpu.mp.service.pojo.shop.operation.RemarkTemplate;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.wxapp.account.UserInfo;
import com.vpu.mp.service.shop.distribution.FanliGoodsStatisticsService;
import com.vpu.mp.service.shop.distribution.MpDistributionGoodsService;
import com.vpu.mp.service.shop.distribution.OrderGoodsRebateService;
import com.vpu.mp.service.shop.distribution.UserFanliStatisticsService;
import com.vpu.mp.service.shop.distribution.UserTotalFanliService;
import com.vpu.mp.service.shop.member.ScoreCfgService;
import com.vpu.mp.service.shop.member.ScoreService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.operation.RecordTradeService;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.action.base.OrderOperateSendMessage;
import com.vpu.mp.service.shop.order.action.base.OrderOperationJudgment;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.record.OrderActionService;
import com.vpu.mp.service.shop.order.refund.ReturnOrderService;
import com.vpu.mp.service.shop.user.user.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.comparator.Comparators;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.vpu.mp.db.shop.tables.UserScoreSet.USER_SCORE_SET;
import static com.vpu.mp.service.foundation.util.BigDecimalUtil.BIGDECIMAL_ZERO;
import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.CONDITION_ONE;
import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.CONDITION_ZERO;
import static com.vpu.mp.service.shop.member.ScoreCfgService.BUY;
import static com.vpu.mp.service.shop.member.ScoreCfgService.BUY_EACH;
import static java.math.BigDecimal.ZERO;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;

/**
 * @author 王帅 
 */
@Component
public class FinishService extends ShopBaseService implements IorderOperate<OrderOperateQueryParam,OrderOperateQueryParam> {
    @Autowired
    private OrderInfoService orderInfo;

    @Autowired
    public OrderGoodsService orderGoods;

    @Autowired
    OrderActionService orderAction;

    @Autowired
    public RecordAdminActionService record;

    @Autowired
    public ReturnOrderService returnOrder;

    @Autowired
    private UserCardService userCard;

    @Autowired
    public ScoreService scoreService;

    @Autowired
    private RecordTradeService recordMemberTrade;

    @Autowired
    public ScoreCfgService scoreCfgService;

    @Autowired
    private OrderGoodsRebateService orderGoodsRebate;

    @Autowired
    private FanliGoodsStatisticsService fanliGoodsStatistics;

    @Autowired
    private UserFanliStatisticsService userFanliStatistics;

    @Autowired
    private UserTotalFanliService userTotalFanli;

    @Autowired
    private UserService user;
    @Autowired
    private MpDistributionGoodsService mpDistributionGoods;

    @Autowired
    private OrderOperateSendMessage sendMessage;

    @Override
    public OrderServiceCode getServiceCode() {
        return OrderServiceCode.FINISH;
    }

    @Override
    public Object query(OrderOperateQueryParam param) throws MpException {
        return null;
    }

    @Override
    public ExecuteResult execute(OrderOperateQueryParam param) {
        OrderInfoRecord orderRecord = orderInfo.getRecord(param.getOrderId());
        OrderInfoVo order = orderRecord.into(OrderInfoVo.class);

        //查询订单订单是否存在退款中订单
        Map<Integer, Integer> returningCount = returnOrder.getOrderCount(new Integer[]{order.getOrderId()}, OrderConstant.REFUND_STATUS_AUDITING, OrderConstant.REFUND_STATUS_AUDIT_PASS, OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING);

        if (!OrderOperationJudgment.mpIsFinish(order, returningCount.get(order.getOrderId()))) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_FINISH_OPERATION_NOT_SUPPORTED, null);
        }

        transaction(() -> {
            //分销返利
            finishRebate(orderRecord, param.getIsMp());
            //update order
            orderInfo.setOrderstatus(order.getOrderSn(), OrderConstant.ORDER_FINISHED);
            //订单完成赠送积分
            orderSendScore(order);
            //新增收支
        });
        //分销升级
        //action操作
        orderAction.addRecord(order, param, OrderConstant.ORDER_RECEIVED, "完成订单");
        //TODO 操作记录 b2c_record_admin_action  需要测试记录
        this.record.insertRecord(Arrays.asList(new Integer[]{RecordContentTemplate.ORDER_FINISH.code}), new String[]{param.getOrderSn()});
        return null;
    }

    private void finishRebate(OrderInfoRecord order, Byte isMp) throws MpException {
        if(!order.getFanliType().equals(DistributionConstant.REBATE_ORDER) ||
            order.getSettlementFlag().equals(OrderConstant.YES) ||
            !order.getTkOrderType().equals(OrderConstant.TK_NORMAL)) {
            logger().info("完成订单时不满足返利条件");
        }
        //goods
        Result<OrderGoodsRecord> goods = orderGoods.getByOrderId(order.getOrderId());
        //TODO 分销记录(prdId重复)
        Map<Integer, Result<OrderGoodsRebateRecord>> rebateRecords = orderGoodsRebate.get(order.getOrderSn()).intoGroups(OrderGoodsRebateRecord::getProductId);
        //需要更新的记录
        ArrayList<OrderGoodsRebateRecord> updateRecords = new ArrayList<>();
        //商品返利统计
        ArrayList<FanliGoodsStatisticsRecord> statisticsRecords = new ArrayList<>();
        //返利汇总
        Map<Integer, BigDecimal> collect = new HashMap<>();
        //该订单返利总计
        BigDecimal total = BIGDECIMAL_ZERO;
        //
        ArrayList<Integer> updateLevel = new ArrayList<>();
        for (OrderGoodsRecord one: goods) {
            if(OrderConstant.YES == one.getIsGift()) {
                //赠品不参与
                continue;
            }

            //返利记录
            Result<OrderGoodsRebateRecord> records = rebateRecords.get(one.getProductId());
            if(CollectionUtils.isEmpty(records)) {
                continue;
            }
            //该商品返利金额
            BigDecimal realRebateMoney = BIGDECIMAL_ZERO;
            //实际返利数量
            Integer rebateNumber = one.getGoodsNumber() - one.getReturnNumber();
            for (OrderGoodsRebateRecord record : records) {
                if(one.getReturnNumber() > 0) {
                    record.setRealRebateMoney(BigDecimalUtil.multiply(record.getRebateMoney(), new BigDecimal(rebateNumber)));
                    updateRecords.add(record);
                    realRebateMoney = realRebateMoney.add(record.getRealRebateMoney());
                }else {
                    record.setRealRebateMoney(record.getRebateMoney());
                    realRebateMoney = realRebateMoney.add(record.getTotalRebateMoney());
                }
                collect.putIfAbsent(record.getRebateUserId(), BIGDECIMAL_ZERO);
                collect.computeIfPresent(record.getRebateUserId(), (k, v) -> v.add(record.getRealRebateMoney()));
            }
            //总计
            total = total.add(realRebateMoney);
            //商品返利统
            statisticsRecords.add(fanliGoodsStatistics.createRecord(realRebateMoney, one, rebateNumber));

            for (Map.Entry<Integer, BigDecimal> entry: collect.entrySet()) {
                if(BigDecimalUtil.compareTo(entry.getValue(), BIGDECIMAL_ZERO) < 1) {
                    continue;
                }
                //判断当前分销等级
                Byte level = entry.getKey().equals(order.getUserId()) ? DistributionConstant.REBATE_LEVEL_0 : entry.getKey().equals(order.getFanliUserId()) ? DistributionConstant.REBATE_LEVEL_1 : DistributionConstant.REBATE_LEVEL_2;
                //更改分销员数据汇总表
                userFanliStatistics.update(order.getUserId(), entry.getKey(), level, entry.getValue(), total);
                //更新分销员统计信息表
                userTotalFanli.updateTotalRebate(entry.getKey(), entry.getValue(), user.getInviteCount(entry.getKey()));
                //返利增加余额
                addRebateAccount(entry.getKey(), order.getOrderSn(), entry.getValue(), order.getUserId());
                //消息推送
                sendMessage.rebate(entry.getKey(), order.getOrderSn(), entry.getValue(), order.getUserId());
            }
            db().batchUpdate(updateRecords).execute();

        }
        //更新
        db().batchUpdate(updateRecords).execute();
        db().batchUpdate(statisticsRecords).execute();
        //更新分销员等级
        updateUserLevel(updateLevel, isMp);
    }

    private void updateUserLevel(ArrayList<Integer> updateLevel, Byte isMp) {
    }

    /**
     * 自动任务:完成订单
     */
    public void autoFinishOrders() {
        Result<OrderInfoRecord> orders = orderInfo.autoFinishOrders();
        for (OrderInfoRecord order : orders) {
            OrderOperateQueryParam param = new OrderOperateQueryParam();
            param.setAction(Integer.valueOf(OrderServiceCode.FINISH.ordinal()).byteValue());
            param.setIsMp(OrderConstant.IS_MP_AUTO);
            param.setOrderId(order.getOrderId());
            param.setOrderSn(order.getOrderSn());
            ExecuteResult result = execute(param);
            if (result == null || result.isSuccess()) {
                logger().info("订单自动任务,完成成功,orderSn:{}", order.getOrderSn());
            } else {
                logger().error("订单自动任务,完成失败,orderSn:{},错误信息{}{}", order.getOrderSn(), result.getErrorCode().toString(), result.getErrorParam());
            }
        }
    }

    private void orderSendScore(OrderInfoVo order) throws MpException {
        if (order.getMemberCardId() > 0) {
            MemberCardRecord card = userCard.memberCardService.getCardById(order.getMemberCardId());
            if (StringUtils.isBlank(card.getBuyScore())) {
                return;
            }
            ScoreJson scoreJson = Util.parseJson(card.getBuyScore(), ScoreJson.class);
            //订单金额
            BigDecimal payMoney = BigDecimalUtil.addOrSubtrac(BigDecimalUtil.BigDecimalPlus.create(order.getMoneyPaid(), BigDecimalUtil.Operator.add),
                BigDecimalUtil.BigDecimalPlus.create(order.getMemberCardBalance(), BigDecimalUtil.Operator.add),
                BigDecimalUtil.BigDecimalPlus.create(order.getUseAccount(), BigDecimalUtil.Operator.subtrac),
                BigDecimalUtil.BigDecimalPlus.create(order.getShippingFee())
            );
            BigDecimal sendScore = null;
            //0：购物满多少送多少积分；1：购物每满多少送多少积分
            if (BYTE_ONE.equals(scoreJson.getOffset())) {
                if (scoreJson.getPerGetScores().compareTo(ZERO) > 0 && scoreJson.getPerGoodsMoney().compareTo(ZERO) > 0) {
                    sendScore = BigDecimalUtil.multiply(new BigDecimal(BigDecimalUtil.divide(payMoney, scoreJson.getPerGoodsMoney()).intValue()),
                        scoreJson.getPerGetScores());
                    logger().info("支付完成送积分:会员卡id[{}],每满[{}]元,送[{}]积分;订单金额[{}],赠送积分[{}]", order.getMemberCardId(), scoreJson.getPerGoodsMoney(), scoreJson.getPerGetScores(), payMoney, sendScore);
                }
            } else if (BYTE_ZERO.equals(scoreJson.getOffset())) {
                BigDecimal currentRule = scoreJson.getGoodsMoney().stream().filter(e -> e.compareTo(payMoney) <= 0).max(Comparators.comparable()).orElse(BIGDECIMAL_ZERO);
                int index = -1;
                for (int i = 0, length = scoreJson.getGoodsMoney().size(); i < length; i++) {
                    if (scoreJson.getGoodsMoney().get(i).compareTo(currentRule) == 0) {
                        index = i;
                        break;
                    }
                }
                if(index == -1) {
                    return;
                }
                sendScore = scoreJson.getGetScores().get(index);
                logger().debug("支付完成送积分:会员卡id[{}],满[{}]元,送[{}]积分;订单金额[{}],赠送积分[{}]", order.getMemberCardId(), currentRule, sendScore, payMoney, sendScore);
            }
            if (BigDecimalUtil.compareTo(sendScore, BIGDECIMAL_ZERO) > 0) {
                sendScore(order.getOrderSn(), sendScore.intValue(), order.getUserId());
            }
        }else {
            if(BYTE_ZERO.equals(scoreCfgService.getShoppingScore())) {
                return;
            }
            // 非会员卡送积分逻辑
            //订单金额
            BigDecimal payMoney = BigDecimalUtil.addOrSubtrac(BigDecimalUtil.BigDecimalPlus.create(order.getMoneyPaid(), BigDecimalUtil.Operator.add),
                BigDecimalUtil.BigDecimalPlus.create(order.getUseAccount(), BigDecimalUtil.Operator.subtrac),
                BigDecimalUtil.BigDecimalPlus.create(order.getShippingFee())
            );
            int sendScore = 0;
            //购物送积分类型： 0： 购物满；1：购物每满
            byte scoreType = scoreCfgService.getScoreType();
            //购物满
            if (scoreType == CONDITION_ZERO) {
                Result<Record2<String, String>> record2s = scoreCfgService.getValFromUserScoreSet(BUY, payMoney.toString());
                if(CollectionUtils.isEmpty(record2s)) {
                    return;
                }
                // 满...金额
                String setVal = record2s.getValue(0, USER_SCORE_SET.SET_VAL);
                // 送...积分
                String setVal2 = record2s.getValue(0, USER_SCORE_SET.SET_VAL2);
                if (org.apache.commons.lang3.StringUtils.isBlank(setVal2)) {
                    return;
                }
                sendScore = Integer.parseInt(setVal2);
                logger().info("支付完成送积分:非会员卡满[{}]元,送[{}]积分;订单金额[{}],赠送积分[{}]", setVal, setVal2, payMoney, sendScore);
            } else if (scoreType == CONDITION_ONE) {
                //购物每满
                Result<Record2<String, String>> record2s = scoreCfgService.getValFromUserScoreSet(BUY_EACH, payMoney.toString());
                if(CollectionUtils.isEmpty(record2s)) {
                    return;
                }
                // 每满...金额
                String setVal = record2s.getValue(0, USER_SCORE_SET.SET_VAL);
                // 送...积分
                String setVal2 = record2s.getValue(0, USER_SCORE_SET.SET_VAL2);
                if (org.apache.commons.lang3.StringUtils.isBlank(setVal) || org.apache.commons.lang3.StringUtils.isBlank(setVal2)) {
                    return;
                }
                sendScore = BigDecimalUtil.divide(payMoney, new BigDecimal(setVal)).intValue() * Integer.valueOf(setVal2);
                logger().debug("支付完成送积分:非会员卡每满[{}]元,送[{}]积分;订单金额[{}],赠送积分[{}]", setVal, setVal2, payMoney, sendScore);
            }
            sendScore(order.getOrderSn(), sendScore, order.getUserId());
        }
    }

    /**
     * 订单完成送积分
     * @param orderSn
     * @param score
     * @param userId
     * @throws MpException
     */
    public void sendScore(String orderSn, Integer score, Integer userId) throws MpException {
        if (score <= 0) {
            return;
        }
        ScoreData scoreData = ScoreData.newBuilder().
            userId(userId).
            orderSn(orderSn).
            //积分
            score(score).
            remarkCode(RemarkTemplate.ORDER_FINISH_SEND_SCORE.code).
            remarkData(orderSn).
            // remark("下单："+order.getOrderSn()).
            //后台处理时为操作人id为0
            adminUser(0).
            //积分
            tradeType(RecordTradeEnum.TYPE_SCORE_PAY.val()).
            //资金流量-收入
            tradeFlow(RecordTradeEnum.TRADE_FLOW_OUT.val()).
            //积分变动是否来自退款
           isFromRefund(RecordTradeEnum.IS_FROM_REFUND_N.val()).build();
        //调用退积分接口
        recordMemberTrade.updateUserEconomicData(scoreData);
    }

    /**
     * 返利增加
     * @param userId
     * @param orderSn
     * @param money
     * @param orderUserId
     * @throws MpException 见具体方法
     */
    public void addRebateAccount(Integer userId, String orderSn, BigDecimal money, Integer orderUserId) throws MpException {
        logger().info("返利增加用户余额start:{}", money);
        if(BigDecimalUtil.compareTo(money, null) == 0) {
            return;
        }
        UserInfo userInfo = user.getUserInfo(orderUserId);
        AccountData accountData = AccountData.newBuilder().
            userId(userId).
            orderSn(orderSn).
            //下单金额
            amount(money.negate()).
            remarkCode(RemarkTemplate.ORDER_REBATE.code).
            remarkData(userInfo.getUsername()).
            payment("fanli").
            //支付类型
            isPaid(RecordTradeEnum.UACCOUNT_RECHARGE.val()).
            //后台处理时为操作人id为0
            adminUser(0).
            //用户余额支付
            tradeType(RecordTradeEnum.TYPE_CRASH_REBATE.val()).
            //资金流量-支出
            tradeFlow(RecordTradeEnum.TRADE_FLOW_OUT.val()).build();
        //调用退余额接口
        recordMemberTrade.updateUserEconomicData(accountData);
        logger().info("返利增加用户余额end:{}", money);
    }
}
