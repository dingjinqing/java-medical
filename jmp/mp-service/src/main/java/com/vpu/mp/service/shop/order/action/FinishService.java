package com.vpu.mp.service.shop.order.action;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.card.ScoreJson;
import com.vpu.mp.service.pojo.shop.member.data.ScoreData;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum;
import com.vpu.mp.service.pojo.shop.operation.RemarkTemplate;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.shop.member.ScoreService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.operation.RecordTradeService;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.action.base.OrderOperationJudgment;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.record.OrderActionService;
import com.vpu.mp.service.shop.order.refund.ReturnOrderService;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.comparator.Comparators;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import static com.vpu.mp.service.foundation.util.BigDecimalUtil.BIGDECIMAL_ZERO;
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

        OrderInfoVo order = orderInfo.getByOrderId(param.getOrderId(), OrderInfoVo.class);

        //查询订单订单是否存在退款中订单
        Map<Integer, Integer> returningCount = returnOrder.getOrderCount(new Integer[]{order.getOrderId()}, OrderConstant.REFUND_STATUS_AUDITING, OrderConstant.REFUND_STATUS_AUDIT_PASS, OrderConstant.REFUND_STATUS_APPLY_REFUND_OR_SHIPPING);

        if (!OrderOperationJudgment.mpIsFinish(order, returningCount.get(order.getOrderId()))) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_FINISH_OPERATION_NOT_SUPPORTED, null);
        }


        transaction(() -> {
            //TODO 分销订单添加返利记录

            //TODO 返利金额

            orderInfo.setOrderstatus(order.getOrderSn(), OrderConstant.ORDER_FINISHED);

            //TODO 更新分销员等级

            //TODO 订单完成赠送积分
            orderSendScore(order);
            //TODO 更新分销员等级

            //TODO 更新分销员等级
        });
        //action操作
        orderAction.addRecord(order, param, OrderConstant.ORDER_RECEIVED, "完成订单");
        //TODO 操作记录 b2c_record_admin_action  需要测试记录
        record.insertRecord(Arrays.asList(new Integer[]{RecordContentTemplate.ORDER_FINISH.code}), new String[]{param.getOrderSn()});
        return null;
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
                    logger().info("支付完成送积分:会员卡id[{}],每满[{}]元,送[{}]积分;订单金额[{}],赠送积分[{}]", order.getMemberCardId(), scoreJson.getGoodsMoney(), scoreJson.getPerGetScores(), payMoney, sendScore);
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
                sendScore = index > -1 ? scoreJson.getGetScores().get(index) : BIGDECIMAL_ZERO;
                logger().debug("支付完成送积分:会员卡id[{}],满[{}]元,送[{}]积分;订单金额[{}],赠送积分[{}]", order.getMemberCardId(), scoreJson.getGoodsMoney(), scoreJson.getPerGetScores(), payMoney, sendScore);
            }
            if (BigDecimalUtil.compareTo(sendScore, BIGDECIMAL_ZERO) > 0) {
                sendScore(order.getOrderSn(), sendScore.intValue(), order.getUserId());
            }
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

}
