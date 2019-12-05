package com.vpu.mp.service.shop.order.store;

import com.vpu.mp.db.shop.tables.StoreOrder;
import com.vpu.mp.db.shop.tables.records.StoreOrderRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.IncrSequenceUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.member.card.CardConsumpData;
import com.vpu.mp.service.pojo.shop.member.card.ScoreJson;
import com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum;
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.invoice.InvoiceVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import com.vpu.mp.service.pojo.wxapp.store.StoreOrderTran;
import com.vpu.mp.service.pojo.wxapp.store.StorePayOrderInfo;
import com.vpu.mp.service.shop.member.AccountService;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.member.ScoreCfgService;
import com.vpu.mp.service.shop.member.ScoreService;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;
import com.vpu.mp.service.shop.payment.PaymentService;
import com.vpu.mp.service.shop.store.service.ServiceOrderService;
import com.vpu.mp.service.shop.store.store.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.*;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;

import static com.vpu.mp.db.shop.tables.Invoice.INVOICE;
import static com.vpu.mp.db.shop.tables.MemberCard.MEMBER_CARD;
import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.db.shop.tables.StoreOrder.STORE_ORDER;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserCard.USER_CARD;
import static com.vpu.mp.db.shop.tables.UserScoreSet.USER_SCORE_SET;
import static com.vpu.mp.service.foundation.util.BigDecimalUtil.BIGDECIMAL_ZERO;
import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.CONDITION_ONE;
import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.CONDITION_ZERO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_TP_NORMAL;
import static com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant.NO_USE_SCORE_STATUS;
import static com.vpu.mp.service.pojo.shop.payment.PayCode.PAY_CODE_BALANCE_PAY;
import static com.vpu.mp.service.pojo.shop.payment.PayCode.PAY_CODE_WX_PAY;
import static com.vpu.mp.service.pojo.wxapp.store.StoreConstant.WAIT_TO_PAY;
import static com.vpu.mp.service.pojo.wxapp.store.StoreConstant.WAIT_TO_PAY_NAME;
import static com.vpu.mp.service.shop.member.ScoreCfgService.BUY;
import static com.vpu.mp.service.shop.member.ScoreCfgService.BUY_EACH;
import static java.math.BigDecimal.*;
import static org.apache.commons.lang3.math.NumberUtils.*;

/**
 * Table:TABLE
 *
 * @author 王帅
 */
@Slf4j
@Service
public class StoreOrderService extends ShopBaseService {
    /**
     * 门店
     */
    @Autowired
    public StoreService store;

    /**
     * The Order service.
     */
    @Autowired
    public ServiceOrderService orderService;

    /**
     * The Member card service.会员卡
     */
    @Autowired
    public MemberCardService memberCardService;

    /**
     * The Score service.积分
     */
    @Autowired
    public ScoreService scoreService;

    /**
     * The Score cfg service.积分配置
     */
    @Autowired
    public ScoreCfgService scoreCfgService;
    /**
     * The User card dao service.
     */
    @Autowired
    public UserCardDaoService userCardDaoService;

    /**
     * The Payment service.
     */
    @Autowired
    public PaymentService paymentService;

    /**
     * The Account service.余额管理
     */
    @Autowired
    public AccountService accountService;

    public final StoreOrder TABLE = STORE_ORDER;
    public static final BigDecimal HUNDRED = new BigDecimal(100);

    /**
     * 买单订单综合查询
     *
     * @param param
     * @return
     */
    public PageResult<StoreOrderListInfoVo> getPageList(StoreOrderPageListQueryParam param) {
        SelectWhereStep<? extends Record> select = db().select(TABLE.ORDER_ID, TABLE.ORDER_SN, TABLE.ORDER_STATUS, TABLE.STORE_ID, TABLE.PAY_TIME, TABLE.MONEY_PAID, TABLE.PAY_CODE, TABLE.PAY_NAME, USER.USERNAME)
            .from(TABLE).leftJoin(USER)
            .on(USER.USER_ID.eq(TABLE.USER_ID));
        buildOptionsStore(select, param);
        PageResult<StoreOrderListInfoVo> result = getPageResult(select, param.getCurrentPage(), param.getPageRows(), StoreOrderListInfoVo.class);
        return result;
    }

    /**
     * 构造买单订单查询条件
     *
     * @param select
     * @param param
     * @return
     */
    public SelectWhereStep<?> buildOptionsStore(SelectWhereStep<?> select, StoreOrderPageListQueryParam param) {
        //自增id排序
        select.orderBy(TABLE.ORDER_ID);

        select.where(TABLE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));

        select.where(TABLE.ORDER_STATUS.in(OrderConstant.STORE_STATUS_PAY, OrderConstant.STORE_STATUS_RETURN));

        if (!StringUtils.isEmpty(param.getOrderSn())) {
            select.where(TABLE.ORDER_SN.like(param.getOrderSn()));
        }
        if (param.getUserName() != null) {
            select.where(USER.USERNAME.like(likeValue(param.getUserName())));
        }
        if (param.getPayTimeStart() != null) {
            select.where(TABLE.PAY_TIME.ge(param.getPayTimeStart()));
        }
        if (param.getPayTimeEnd() != null) {
            select.where(TABLE.PAY_TIME.le(param.getPayTimeEnd()));
        }
        if (param.getStoreId() != null) {
            select.where(TABLE.STORE_ID.eq(param.getStoreId()));
        }
        if (param.getOrderStatus() != null && param.getOrderStatus().length != 0) {
            select.where(TABLE.ORDER_STATUS.in(param.getOrderStatus()));
        }
        return select;
    }

    public StoreOrderInfoVo get(String orderSn) {
        return db().select(TABLE.ORDER_ID, TABLE.ORDER_SN, TABLE.ORDER_STATUS, TABLE.STORE_ID, TABLE.PAY_TIME, TABLE.MONEY_PAID, TABLE.PAY_CODE, TABLE.PAY_NAME,
            TABLE.MEMBER_CARD_BALANCE, TABLE.MEMBER_CARD_REDUCE, TABLE.SCORE_DISCOUNT, TABLE.USE_ACCOUNT, TABLE.ORDER_AMOUNT, TABLE.MONEY_PAID, TABLE.ADD_MESSAGE, TABLE.CURRENCY,
            USER.USERNAME,
            STORE.STORE_NAME,
            INVOICE.TYPE, INVOICE.TITLE, INVOICE.TAXNUMBER.as("taxNumber"), INVOICE.COMPANYADDRESS.as("companyAddress"))
            .from(TABLE)
            .leftJoin(USER).on(USER.USER_ID.eq(TABLE.USER_ID))
            .leftJoin(STORE).on(STORE.STORE_ID.eq(TABLE.STORE_ID))
            .leftJoin(INVOICE).on(INVOICE.USER_ID.eq(TABLE.USER_ID))
            .where(TABLE.ORDER_SN.eq(orderSn))
            .fetchOneInto(StoreOrderInfoVo.class);
    }

    private static final String PREFIX = "D";

    /**
     * 生成门店买单订单号
     */
    public String generateOrderSn() {
        return IncrSequenceUtil.generateOrderSn(PREFIX);
    }

    /**
     * Check before create store order tran.
     *
     * @param userInfo    the user info
     * @param invoiceInfo the invoice info
     * @param orderInfo   the order info
     * @return the store order tran
     */
    public StoreOrderTran checkBeforeCreate(UserRecord userInfo, InvoiceVo invoiceInfo, StorePayOrderInfo orderInfo) {
        // 订单编号
        String orderSn = generateOrderSn();
        log.debug("门店买单订单编号:{}", orderSn);
        StorePojo storePojo = store.getStore(orderInfo.getStoreId());
        Objects.requireNonNull(storePojo, "店铺不存在");
        if (storePojo.getDelFlag().equals(BYTE_ONE)) {
            // 该门店已删除
            throw new BusinessException(JsonResultCode.CODE_STORE_ALREADY_DEL);
        }
        // 订单金额
        BigDecimal orderAmount = orderInfo.getOrderAmount();
        // 应付金额
        BigDecimal moneyPaid = orderAmount;
        // 会员卡余额抵扣金额
        BigDecimal cardAmount = orderInfo.getCardAmount();
        // 会员卡折扣抵扣金额
        BigDecimal cardDisAmount = orderInfo.getCardDisAmount();
        // 积分抵扣金额
        BigDecimal scoreAmount = orderInfo.getScoreAmount();
        // 余额抵扣金额
        BigDecimal balanceAmount = orderInfo.getBalanceAmount();
        AccountParam accountParam = null;
        CardConsumpData cardConsumpData = null;
        ScoreParam scoreParam = null;
        String cardNo = orderInfo.getCardNo();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(cardNo)) {
            // 验证会员卡有效性
            if (!userCardDaoService.checkStoreValidCard(userInfo.getUserId(), orderInfo.getStoreId(), cardNo)) {
                log.error("会员卡【{}】无效", cardNo);
                throw new BusinessException(JsonResultCode.CODE_ORDER_CARD_INVALID);
            }
            Record2<BigDecimal, BigDecimal> record2 = userCardDaoService.getCardBalAndDis(cardNo);
            // 会员卡折扣
            BigDecimal discount = record2.getValue(MEMBER_CARD.DISCOUNT);
            // 会员卡余额
            BigDecimal money = record2.getValue(USER_CARD.MONEY);
            if (BigDecimalUtil.greaterThanZero(discount)) {
                // 计算会员卡折扣金额
                BigDecimal cardDisAm = orderAmount.multiply((ONE.subtract(discount.divide(TEN, 2, RoundingMode.DOWN))));
                if (cardDisAm.compareTo(cardDisAmount) != 0) {
                    log.debug("会员卡折扣抵扣金额【{}】计算有误【前端计算结果为：{}】", cardDisAm, cardDisAmount);
                    throw new BusinessException(JsonResultCode.CODE_FAIL);
                }
                log.debug("会员卡折扣金额:{}", cardDisAmount);
                moneyPaid = moneyPaid.subtract(cardDisAmount).setScale(2, RoundingMode.UP);
            }
            // 会员卡余额抵扣金额
            if (BigDecimalUtil.greaterThanZero(cardAmount)) {
                if (cardAmount.compareTo(money) > 0) {
                    // 会员卡余额不足
                    log.error("会员卡余额[{}]不足(实际抵扣金额[{}])，无法下单", money, cardAmount);
                    throw new BusinessException(JsonResultCode.CODE_USER_CARD_BALANCE_INSUFFICIENT);
                }
                // 增加会员卡消费记录
                UserCardParam userCardParam = userCardDaoService.getUserCardInfo(cardNo);
                // 创建会员卡消费变动事件
                cardConsumpData = new CardConsumpData()
                    .setUserId(userInfo.getUserId())
                    // 会员卡更新金额，区分正负号，这里是负号，意为扣减
                    .setMoney(orderInfo.getCardAmount().negate())
                    .setCardNo(cardNo)
                    .setCardId(userCardParam.getCardId())
                    .setReason(orderSn)
                    // 消费类型 :门店只支持普通卡0
                    .setType(MCARD_TP_NORMAL);
                log.debug("会员卡余额抵扣金额:{}", cardAmount);
                moneyPaid = moneyPaid.subtract(cardAmount).setScale(2, RoundingMode.UP);
            }
        }
        // 积分抵扣金额(积分数除以100就是积分抵扣金额数)
        if (BigDecimalUtil.greaterThanZero(scoreAmount)) {
            int scoreValue = scoreAmount.multiply(HUNDRED).intValue();
            if (scoreValue > userInfo.getScore()) {
                // 积分不足，无法下单
                log.error("积分[{}]不足(实际抵扣金额[{}]，金额积分兑换率为1:100)，无法下单", userInfo.getScore(), scoreAmount);
                throw new BusinessException(JsonResultCode.CODE_SCORE_INSUFFICIENT);
            }
            scoreParam = new ScoreParam() {{
                setScoreDis(userInfo.getScore());
                setUserId(new Integer[]{userInfo.getUserId()});
                // 积分变动数额
                setScore(scoreValue);
                setOrderSn(orderSn);
                setRemark(orderSn);
            }};
            log.debug("积分抵扣金额:{}", scoreAmount);
            moneyPaid = moneyPaid.subtract(scoreAmount).setScale(2, RoundingMode.UP);
        }
        // 余额抵扣金额
        if (BigDecimalUtil.greaterThanZero(balanceAmount)) {
            if (balanceAmount.compareTo(userInfo.getAccount()) > 0) {
                // 余额不足，无法下单
                log.error("用户余额[{}]不足(实际抵扣金额[{}])，无法下单", userInfo.getAccount(), balanceAmount);
                throw new BusinessException(JsonResultCode.CODE_BALANCE_INSUFFICIENT);
            }
            // 创建会员余额变动事件
            accountParam = new AccountParam() {{
                setAccount(userInfo.getAccount());
                setUserId(userInfo.getUserId());
                // 更新金额  区分正负号， 这里置为负号，表示扣减
                setAmount(balanceAmount.negate());
                setOrderSn(orderSn);
                setPayment(PAY_CODE_BALANCE_PAY);
                // 支付类型，0：充值，1：消费
                setIsPaid(BYTE_ONE);
                setRemark(orderSn);
            }};
            log.debug("余额抵扣金额:{}", balanceAmount);
            moneyPaid = moneyPaid.subtract(balanceAmount).setScale(2, RoundingMode.UP);
        }
        // 应付金额
        log.debug("应付金额:{}", moneyPaid);
        if (Objects.isNull(orderInfo.getMoneyPaid()) || orderInfo.getMoneyPaid().compareTo(moneyPaid) != 0) {
            // 应付金额计算错误
            log.debug("应付金额【{}】计算有误【前端计算结果为：{}】", moneyPaid, orderInfo.getMoneyPaid());
            throw new BusinessException(JsonResultCode.CODE_AMOUNT_PAYABLE_CALCULATION_FAILED);
        }
        StoreOrderRecord orderRecord = new StoreOrderRecord();
        orderRecord.setStoreId(orderInfo.getStoreId());
        orderRecord.setOrderSn(orderSn);
        orderRecord.setUserId(userInfo.getUserId());
        orderRecord.setOrderStatus(WAIT_TO_PAY);
        orderRecord.setOrderStatusName(WAIT_TO_PAY_NAME);
        if (Objects.nonNull(invoiceInfo)) {
            orderRecord.setInvoiceId(invoiceInfo.getId());
            orderRecord.setInvoiceDetail(Util.toJson(invoiceInfo));
        }
        orderRecord.setAddMessage(orderInfo.getRemark());
        orderRecord.setPayCode(moneyPaid.compareTo(ZERO) > 0 ? PAY_CODE_WX_PAY : PAY_CODE_BALANCE_PAY);
        orderRecord.setPayName(paymentService.getPaymentInfo(orderRecord.getPayCode()).getPayName());
        orderRecord.setMoneyPaid(moneyPaid);
        orderRecord.setMemberCardNo(cardNo);
        orderRecord.setMemberCardRedunce(cardAmount);
        orderRecord.setScoreDiscount(scoreAmount);
        orderRecord.setUseAccount(balanceAmount);
        orderRecord.setOrderAmount(orderInfo.getOrderAmount());
        return StoreOrderTran.builder()
            .account(accountParam)
            .cardConsumpData(cardConsumpData)
            .scoreParam(scoreParam)
            .storeOrder(orderRecord)
            .build();
    }

    /**
     * Create store order.创建门店买单订单
     *
     * @param param the param
     * @return the string
     */
    public String createStoreOrder(StoreOrderTran param) {
        AccountParam account = param.getAccount();
        CardConsumpData userCard = param.getCardConsumpData();
        ScoreParam scoreParam = param.getScoreParam();
        StoreOrderRecord order = param.getStoreOrder();
        // 创建门店买单订单记录
        db().executeInsert(order);
        // 创建余额，会员卡，积分消费记录
        if (Objects.nonNull(account)) {
            try {
                TradeOptParam tradeOpt = TradeOptParam.builder()
                    .adminUserId(INTEGER_ZERO)
                    .tradeType(RecordTradeEnum.TYPE_CRASH_ACCOUNT_PAY.val())
                    .tradeFlow(RecordTradeEnum.TRADE_FLOW_OUT.val())
                    .build();
                accountService.addUserAccount(account, tradeOpt);
            } catch (MpException e) {
                log.debug("余额抵扣失败：{}", e.getMessage());
                throw new BusinessException(JsonResultCode.CODE_FAIL);
            }
            log.debug("用户余额[{}]抵扣(实际抵扣金额[{}])成功!", account.getAccount(), order.getUseAccount());
        }
        if (Objects.nonNull(userCard)) {
            TradeOptParam optParam = TradeOptParam.builder()
                .tradeFlow(RecordTradeEnum.TRADE_FLOW_OUT.val())
                .tradeType(RecordTradeEnum.TYPE_CRASH_MEMBER_CARD_PAY.val())
                .tradeSn(order.getOrderSn()).adminUserId(INTEGER_ZERO).build();
            try {
                memberCardService.updateMemberCardAccount(userCard, optParam, org.apache.commons.lang3.StringUtils.EMPTY);
            } catch (MpException e) {
                log.debug("会员卡余额抵扣失败：{}", e.getMessage());
                throw new BusinessException(JsonResultCode.CODE_FAIL);
            }
            log.debug("会员卡余额抵扣(实际抵扣金额[{}])成功!", userCard.getMoney());
        }
        if (Objects.nonNull(scoreParam)) {
            try {
                scoreService.updateMemberScore(scoreParam, INTEGER_ZERO, RecordTradeEnum.TYPE_SCORE_PAY.val(), RecordTradeEnum.TRADE_FLOW_OUT.val());
            } catch (MpException e) {
                log.debug("积分抵扣失败：{}", e.getMessage());
                throw new BusinessException(JsonResultCode.CODE_FAIL);
            }
            log.debug("积分抵扣(实际抵扣金额[{}])成功!", scoreParam.getScore());
        }
        return order.getOrderSn();
    }

    /**
     * Update prepay id by order sn.更新微信支付id
     *
     * @param orderSn  the order sn
     * @param prepayId the prepay id
     */
    public void updatePrepayIdByOrderSn(String orderSn, String prepayId) {
        db().update(TABLE).set(TABLE.PREPAY_ID, prepayId).where(TABLE.ORDER_SN.eq(orderSn)).execute();
    }

    /**
     * Update record.门店订单更新
     *
     * @param condition   the condition 更新条件
     * @param orderRecord the order record 更新内容
     */
    public void updateRecord(Condition condition, StoreOrderRecord orderRecord) {
        db().update(TABLE).set(orderRecord).where(condition).execute();
    }

    /**
     * Send score after pay done.支付完成送积分
     *
     * @param orderInfo the order info 门店订单信息
     */
    public void sendScoreAfterPayDone(StoreOrderRecord orderInfo) {
        String cardNo = orderInfo.getMemberCardNo();
        // 最终赠送积分值,为零不赠送
        int sendScore = 0;
        // 会员卡送积分逻辑
        if (org.apache.commons.lang3.StringUtils.isNotBlank(cardNo)) {
            // 获取会员卡购物送积分策略json数据,例如: {"offset":0,"goodsMoney":[100,200],"getScores":[1000,2000],"perGoodsMoney":1000,"perGetScores":2000}
            String buyScoreConfig = memberCardService.getSendScoreStrategy(cardNo);
            // 策略为空,不赠送积分
            if (org.apache.commons.lang3.StringUtils.isBlank(buyScoreConfig)) {
                giftScore(orderInfo.getOrderSn(), INTEGER_ZERO, orderInfo.getUserId());
                return;
            }
            ScoreJson scoreJson = Util.json2Object(buyScoreConfig, ScoreJson.class, false);
            BigDecimal totalMoney = orderInfo.getMoneyPaid().add(orderInfo.getUseAccount()).add(orderInfo.getMemberCardRedunce()).setScale(2, RoundingMode.DOWN);
            //0：购物满多少送多少积分；1：购物每满多少送多少积分
            if (BYTE_ONE.equals(scoreJson.getOffset())) {
                if (scoreJson.getPerGetScores().compareTo(ZERO) > 0 && scoreJson.getPerGoodsMoney().compareTo(ZERO) > 0) {
                    sendScore = totalMoney.divide(scoreJson.getPerGoodsMoney(), 0, RoundingMode.DOWN).multiply(scoreJson.getPerGetScores()).intValue();
                    log.debug("支付完成送积分:会员卡[{}],每满[{}]元,送[{}]积分;订单金额[{}],赠送积分[{}]", cardNo, scoreJson.getGoodsMoney(), scoreJson.getPerGetScores(), totalMoney, sendScore);
                }
            } else if (BYTE_ZERO.equals(scoreJson.getOffset())) {
                BigDecimal fullMoney = scoreJson.getGoodsMoney().stream().filter(e -> e.compareTo(totalMoney) < 0).max(Comparators.comparable()).orElse(BIGDECIMAL_ZERO);
                int index = Arrays.asList(scoreJson.getGoodsMoney()).indexOf(fullMoney);
                sendScore = index < scoreJson.getGetScores().size() ? scoreJson.getGetScores().get(index).intValue() : NumberUtils.INTEGER_ZERO;
                log.debug("支付完成送积分:会员卡[{}],满[{}]元,送[{}]积分;订单金额[{}],赠送积分[{}]", cardNo, scoreJson.getGoodsMoney(), scoreJson.getPerGetScores(), totalMoney, sendScore);
            }
            // 送积分
            giftScore(orderInfo.getOrderSn(), sendScore, orderInfo.getUserId());
        } else {
            // 非会员卡送积分逻辑
            BigDecimal totalMoney = orderInfo.getMoneyPaid().add(orderInfo.getUseAccount()).setScale(2, RoundingMode.DOWN);
//            购物送积分类型： 0： 购物满；1：购物每满
            byte scoreType = scoreCfgService.getScoreType();
//            购物满
            if (scoreType == CONDITION_ZERO) {
                Result<Record2<String, String>> record2s = scoreCfgService.getValFromUserScoreSet(BUY, totalMoney.toString());
                // 满...金额
                String setVal = record2s.getValue(0, USER_SCORE_SET.SET_VAL);
                // 送...积分
                String setVal2 = record2s.getValue(1, USER_SCORE_SET.SET_VAL2);
                if (org.apache.commons.lang3.StringUtils.isBlank(setVal2)) {
                    giftScore(orderInfo.getOrderSn(), INTEGER_ZERO, orderInfo.getUserId());
                    return;
                }
                sendScore = Integer.parseInt(setVal2);
                log.debug("支付完成送积分:非会员卡满[{}]元,送[{}]积分;订单金额[{}],赠送积分[{}]", setVal, setVal2, totalMoney, sendScore);
            } else if (scoreType == CONDITION_ONE) {
//                购物每满
                Result<Record2<String, String>> record2s = scoreCfgService.getValFromUserScoreSet(BUY_EACH);
                // 每满...金额
                String setVal = record2s.getValue(0, USER_SCORE_SET.SET_VAL);
                // 送...积分
                String setVal2 = record2s.getValue(1, USER_SCORE_SET.SET_VAL2);
                if (org.apache.commons.lang3.StringUtils.isBlank(setVal) || org.apache.commons.lang3.StringUtils.isBlank(setVal2)) {
                    giftScore(orderInfo.getOrderSn(), INTEGER_ZERO, orderInfo.getUserId());
                    return;
                }
                sendScore = totalMoney.divide(NumberUtils.createBigDecimal(setVal), 0, RoundingMode.DOWN).multiply(NumberUtils.createBigDecimal(setVal2)).intValue();
                log.debug("支付完成送积分:非会员卡每满[{}]元,送[{}]积分;订单金额[{}],赠送积分[{}]", setVal, setVal2, totalMoney, sendScore);
            } else {
                giftScore(orderInfo.getOrderSn(), INTEGER_ZERO, orderInfo.getUserId());
                return;
            }
            giftScore(orderInfo.getOrderSn(), sendScore, orderInfo.getUserId());
        }
    }

    /**
     * Gift score.赠送积分
     *
     * @param orderSn the order sn订单编号
     * @param score   the score积分值
     * @param userId  the user id用户id
     *                {@value com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant#NO_USE_SCORE_STATUS}
     */
    public void giftScore(String orderSn, Integer score, Integer... userId) {
        if (score <= 0) {
            return;
        }
        try {
            scoreService.updateMemberScore(
                new ScoreParam() {
                    {
                        setUserId(userId);
                        setScore(score);
                        setScoreStatus(NO_USE_SCORE_STATUS);
                        setDesc("score");
                        setOrderSn(orderSn);
                        setRemark("门店支付得积分");
                    }
                },
                INTEGER_ZERO,
                BYTE_ONE,
                BYTE_ZERO);
        } catch (MpException e) {
            log.error("门店买单支付送积分失败,原因如下:{}", e.getMessage());
            throw new BusinessException(JsonResultCode.CODE_FAIL);
        }
    }
}
