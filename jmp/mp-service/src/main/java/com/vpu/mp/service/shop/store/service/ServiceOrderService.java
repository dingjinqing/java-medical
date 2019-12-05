package com.vpu.mp.service.shop.store.service;

import com.vpu.mp.db.shop.tables.records.ServiceOrderRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.Assert;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.IncrSequenceUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.card.CardConsumpData;
import com.vpu.mp.service.pojo.shop.member.card.MemberCardPojo;
import com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum;
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.pojo.shop.payment.PaymentVo;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceParam;
import com.vpu.mp.service.pojo.shop.store.service.order.*;
import com.vpu.mp.service.pojo.wxapp.store.ServiceOrderTran;
import com.vpu.mp.service.pojo.wxapp.store.SubmitReservationParam;
import com.vpu.mp.service.shop.member.AccountService;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;
import com.vpu.mp.service.shop.payment.PaymentService;
import com.vpu.mp.service.shop.user.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.*;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.vpu.mp.db.shop.tables.ServiceOrder.SERVICE_ORDER;
import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.db.shop.tables.StoreService.STORE_SERVICE;
import static com.vpu.mp.db.shop.tables.UserCard.USER_CARD;
import static com.vpu.mp.service.foundation.util.BigDecimalUtil.BIGDECIMAL_ZERO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_TP_NORMAL;
import static com.vpu.mp.service.pojo.shop.payment.PayCode.PAY_CODE_BALANCE_PAY;
import static com.vpu.mp.service.shop.store.store.StoreReservation.HH_MM_FORMATTER;
import static org.apache.commons.lang3.math.NumberUtils.*;

/**
 * @author 王兵兵
 * <p>
 * 2019年7月17日
 * <p>
 * 预约（门店服务订单）
 */
@Service
@Slf4j
public class ServiceOrderService extends ShopBaseService {
    /**
     * 门店服务
     */
    @Autowired
    public StoreServiceService storeService;

    /**
     * The User service.
     */
    @Autowired
    public UserService userService;

    /**
     * The User card dao service.
     */
    @Autowired
    public UserCardDaoService userCardDaoService;

    /**
     * The Account service.余额管理
     */
    @Autowired
    public AccountService accountService;

    /**
     * The User card service.
     */
    @Autowired
    public MemberCardService memberCardService;

    /**
     * The Payment service.
     */
    @Autowired
    public PaymentService paymentService;

    /**
     * The User card service.
     */
    @Autowired
    public UserCardService userCardService;

    /**
     * 订单状态 0：待付款，1：待服务，2：已取消，3：已完成
     */
    public static final Byte ORDER_STATUS_WAIT_PAY = 0;
    public static final Byte ORDER_STATUS_WAIT_SERVICE = 1;
    public static final Byte ORDER_STATUS_CANCELED = 2;
    public static final Byte ORDER_STATUS_FINISHED = 3;


    /**
     * 订单状态 0：待付款，1：待服务，2：已取消，3：已完成
     */
    public static final String ORDER_STATUS_NAME_WAIT_PAY = "待付款";
    public static final String ORDER_STATUS_NAME_WAIT_SERVICE = "待服务";
    public static final String ORDER_STATUS_NAME_CANCELED = "已取消";
    public static final String ORDER_STATUS_NAME_FINISHED = "已完成";

    /**
     * 预约订单创建创建类型 0用户创建 1后台
     */
    public static final Byte ORDER_TYPE_USER_CREATE = 0;
    public static final Byte ORDER_TYPE_ADMIN_CREATE = 1;

    /**
     * 核销方式 0是店家核销 1是用户
     */
    public static final Byte VERIFY_TYPE_ADMIN = 0;
    public static final Byte VERIFY_TYPE_USER = 1;

    /**
     * 核销支付方式 0门店买单 1会员卡 2余额
     */
    public static final byte VERIFY_PAY_TYPE_STORE = 0;
    public static final byte VERIFY_PAY_TYPE_MEMBER_CARD = 1;
    public static final byte VERIFY_PAY_TYPE_ACCOUNT = 2;

    /**
     * 门店服务预约列表分页查询
     *
     * @param
     * @return StorePageListVo
     */
    public PageResult<ServiceOrderListQueryVo> getPageList(ServiceOrderListQueryParam param) {
        SelectWhereStep<? extends Record> select =
            db().select(SERVICE_ORDER.STORE_ID, SERVICE_ORDER.ORDER_ID, SERVICE_ORDER.ORDER_SN, SERVICE_ORDER.USER_ID, SERVICE_ORDER.SUBSCRIBER, STORE_SERVICE.SERVICE_NAME, SERVICE_ORDER.MOBILE, SERVICE_ORDER.SERVICE_DATE, SERVICE_ORDER.SERVICE_PERIOD, SERVICE_ORDER.TECHNICIAN_NAME, STORE_SERVICE.SERVICE_SUBSIST, SERVICE_ORDER.ADD_MESSAGE).
                from(SERVICE_ORDER).
                leftJoin(STORE_SERVICE).on(SERVICE_ORDER.SERVICE_ID.eq(STORE_SERVICE.ID));
        select = this.buildOptions(select, param);
        select.where(SERVICE_ORDER.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(SERVICE_ORDER.STORE_ID.eq(param.getStoreId())).orderBy(SERVICE_ORDER.CREATE_TIME.desc());
        return getPageResult(select, param.getCurrentPage(), param.getPageRows(), ServiceOrderListQueryVo.class);
    }

    /**
     * 门店服务预约列表计数
     *
     * @param
     * @return Integer
     */
    public Integer getCountData(ServiceOrderListQueryParam param) {
        SelectWhereStep<? extends Record> select =
            db().selectCount().
                from(SERVICE_ORDER).
                leftJoin(STORE_SERVICE).on(SERVICE_ORDER.SERVICE_ID.eq(STORE_SERVICE.ID));
        select = this.buildOptions(select, param);
        select.where(SERVICE_ORDER.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(SERVICE_ORDER.STORE_ID.eq(param.getStoreId()));
        return select.fetchOne(0, Integer.class);
    }

    /**
     * 门店服务预约的条件查询
     *
     * @param select
     * @param param
     * @return
     */
    public SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends Record> select, ServiceOrderListQueryParam param) {
        if (param == null) {
            return select;
        }

        if (param.getOrderStatus() != null && param.getOrderStatus() > (byte) -1) {
            select.where(SERVICE_ORDER.ORDER_STATUS.eq(param.getOrderStatus()));
        }

        if (!StringUtils.isEmpty(param.getMobile())) {
            select.where(SERVICE_ORDER.MOBILE.contains(param.getMobile()));
        }

        if (!StringUtils.isEmpty(param.getServiceDateStart())) {
            select.where(SERVICE_ORDER.SERVICE_DATE.gt(param.getServiceDateStart()));
        }

        if (!StringUtils.isEmpty(param.getServiceDateEnd())) {
            select.where(SERVICE_ORDER.SERVICE_DATE.lt(param.getServiceDateEnd()));
        }

        if (!StringUtils.isEmpty(param.getTechnicianName())) {
            select.where(SERVICE_ORDER.TECHNICIAN_NAME.contains(param.getTechnicianName()));
        }

        if (!StringUtils.isEmpty(param.getKeywords())) {
            select.where(SERVICE_ORDER.SUBSCRIBER.contains(param.getKeywords()).or(STORE_SERVICE.SERVICE_NAME.contains(param.getKeywords())));
        }
        return select;
    }

    public ServiceOrderDetailVo getServiceOrderDetail(String orderSn) {
        Record vo =
            db().select(
                SERVICE_ORDER.ORDER_ID, SERVICE_ORDER.SERVICE_ID, SERVICE_ORDER.STORE_ID, SERVICE_ORDER.ORDER_SN, SERVICE_ORDER.ORDER_STATUS, SERVICE_ORDER.ORDER_STATUS_NAME, SERVICE_ORDER.SUBSCRIBER,
                SERVICE_ORDER.MOBILE, SERVICE_ORDER.TECHNICIAN_NAME, SERVICE_ORDER.SERVICE_DATE, SERVICE_ORDER.SERVICE_PERIOD, SERVICE_ORDER.ADD_MESSAGE,
                SERVICE_ORDER.ADMIN_MESSAGE, SERVICE_ORDER.ORDER_AMOUNT, SERVICE_ORDER.MONEY_PAID, SERVICE_ORDER.FINISHED_TIME, SERVICE_ORDER.VERIFY_TYPE,
                SERVICE_ORDER.VERIFY_CODE, SERVICE_ORDER.VERIFY_ADMIN, SERVICE_ORDER.TYPE, SERVICE_ORDER.VERIFY_PAY, SERVICE_ORDER.CREATE_TIME,
                STORE_SERVICE.SERVICE_NAME, STORE_SERVICE.SERVICE_PRICE, STORE_SERVICE.SERVICE_SUBSIST, STORE_SERVICE.SERVICE_IMG
            ).
                from(SERVICE_ORDER).leftJoin(STORE_SERVICE).on(SERVICE_ORDER.SERVICE_ID.eq(STORE_SERVICE.ID)).
                where(SERVICE_ORDER.ORDER_SN.eq(orderSn)).fetchOne();
        if (vo == null) {
            return null;
        } else {
            return vo.into(ServiceOrderDetailVo.class);
        }
    }

    public Boolean addServiceOrderAdminMessage(ServiceOrderAdminMessageParam param) {
        return db().update(SERVICE_ORDER).set(SERVICE_ORDER.ADMIN_MESSAGE, param.getAdminMessage()).where(SERVICE_ORDER.ORDER_SN.eq(param.getOrderSn())).execute() > 0 ? true : false;
    }

    private static final String PREFIX = "S";

    /**
     * 生成门店服务订单号
     */
    public String generateOrderSn() {
        return IncrSequenceUtil.generateOrderSn(PREFIX);
    }

    /**
     * 判断该orderSn是否已存在
     *
     * @param orderSn
     * @return
     */
    public Boolean hasOrderSn(String orderSn) {
        List<Integer> ids = db().select(SERVICE_ORDER.ORDER_ID).from(SERVICE_ORDER).where(SERVICE_ORDER.ORDER_SN.eq(orderSn)).fetch().into(Integer.class);
        if (ids.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String generateVerifyCode() {
        Random random = new Random(2);
        List<Integer> ids;
        String verifyCode;
        do {
            int randomStr = random.nextInt(999999) % (900000) + 100000;
            verifyCode = String.valueOf(randomStr);
            ids = db().select(SERVICE_ORDER.VERIFY_CODE).from(SERVICE_ORDER).where(SERVICE_ORDER.VERIFY_CODE.eq(verifyCode)).fetch().into(Integer.class);
        } while (ids.size() > 0);
        return verifyCode;
    }

    public BigDecimal getServiceMoneyPaid(Integer serviceId) {
        StoreServiceParam storeService = db().select(STORE_SERVICE.SERVICE_PRICE, STORE_SERVICE.SERVICE_SUBSIST).from(STORE_SERVICE).where(STORE_SERVICE.ID.eq(serviceId)).fetchOne().into(StoreServiceParam.class);
        return storeService.getServicePrice().subtract(storeService.getServiceSubsist());
    }

    /**
     * 后台添加服务预约
     *
     * @param
     * @return
     */
    public Boolean addServiceOrder(ServiceOrderAddParam param) {
        param.setOrderSn(generateOrderSn());
        param.setVerifyCode(generateVerifyCode());
        param.setType(ORDER_TYPE_ADMIN_CREATE);
        param.setMoneyPaid(getServiceMoneyPaid(param.getServiceId()));
        ServiceOrderRecord record = new ServiceOrderRecord();
        this.assign(param, record);
        return db().executeInsert(record) > 0 ? true : false;
    }

    /**
     * Pay method verify.下单前支付方式校验
     * todo 门店相关支付业务暂不确定是否参与交易配置
     *
     * @param param the param
     */
    public void payMethodVerify(ServiceOrderRecord param) {
        // 余额抵扣金额
        BigDecimal balance = param.getUseAccount();
        BigDecimal moneyPaid = param.getMoneyPaid();
        Map<String, PaymentVo> pays = paymentService.getSupportPayment();
        if (Objects.nonNull(balance) && balance.compareTo(BIGDECIMAL_ZERO) > INTEGER_ZERO) {
            if (BYTE_ZERO.equals(pays.get(PAY_CODE_BALANCE_PAY).getEnabled())) {
                log.debug("暂不支持使用余额支付");
                throw new BusinessException(JsonResultCode.CODE_FAIL);
            }
        }
    }

    /**
     * Check before create service order tran.订单创建前置校验
     * 事务中抽离逻辑判断和实际的db操作，减少事务执行时间
     *
     * @param param the param
     * @return the service order tran
     */
    public ServiceOrderTran checkBeforeCreate(ServiceOrderRecord param) {
        String orderSn = generateOrderSn();
        // 二次验证前端计算的应付金额是否正确,
        BigDecimal moneyPaidSecondCheck = param.getOrderAmount();
        // 订单总金额
        BigDecimal orderAmount = param.getOrderAmount();
        if (orderAmount.compareTo(storeService.getStoreService(param.getServiceId()).getServiceSubsist()) > INTEGER_ZERO) {
//            验证是否与服务定金相等
            throw new BusinessException(JsonResultCode.CODE_ORDER_AMOUNT_NOT_EQUALS_SERVICE_SUBSIST);
        }
        // 余额抵扣金额
        BigDecimal balance = param.getUseAccount();
        // 会员卡抵扣金额
        BigDecimal cardDis = param.getMemberCardBalance();
        // 优惠券抵扣金额
        BigDecimal couponDis = param.getDiscount();
        AccountParam accountParam = null;
        CardConsumpData cardConsumpData = null;
        if (BigDecimalUtil.greaterThanZero(balance)) {
            // 用户余额校验
            BigDecimal userBalance = Optional.ofNullable(userService.getUserByUserId(param.getUserId()))
                .orElseThrow(() -> new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST, "User: " + param.getUserId())).getAccount();
            if (userBalance.compareTo(balance) < 0) {
                // 余额不足，无法下单
                log.error("用户余额[{}]不足(实际抵扣金额[{}])，无法下单", userBalance, balance);
                throw new BusinessException(JsonResultCode.CODE_BALANCE_INSUFFICIENT);
            }
            // 创建会员余额变动事件
            accountParam = new AccountParam() {{
                setAccount(userBalance);
                setUserId(param.getUserId());
                // 更新金额  区分正负号， 这里置为负号，表示扣减
                setAmount(param.getUseAccount().negate());
                setOrderSn(orderSn);
                setPayment(PAY_CODE_BALANCE_PAY);
                // 支付类型，0：充值，1：消费
                setIsPaid(BYTE_ONE);
                setRemark(orderSn);
            }};
            moneyPaidSecondCheck = moneyPaidSecondCheck.subtract(balance);
        }
        String cardNo = param.getMemberCardNo();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(cardNo) && BigDecimalUtil.greaterThanZero(cardDis)) {
            // 验证会员卡有效性
            if (!userCardDaoService.checkStoreValidCard(param.getUserId(), param.getStoreId(), cardNo)) {
                log.error("会员卡【{}】无效", cardNo);
                throw new BusinessException(JsonResultCode.CODE_ORDER_CARD_INVALID);
            }
            // 会员卡余额（门店服务不涉及会员卡折扣）
            BigDecimal money = userCardService.getSingleField(USER_CARD.MONEY, USER_CARD.CARD_NO.eq(cardNo));
            // 会员卡抵扣金额大于会员卡余额
            if (cardDis.compareTo(money) > 0) {
                // 会员卡余额不足
                log.error("会员卡余额[{}]不足(实际抵扣金额[{}])，无法下单", money, cardDis);
                throw new BusinessException(JsonResultCode.CODE_USER_CARD_BALANCE_INSUFFICIENT);
            }
            // 增加会员卡消费记录
            UserCardParam userCardParam = userCardDaoService.getUserCardInfo(param.getMemberCardNo());
            // 创建会员卡消费变动事件
            cardConsumpData = new CardConsumpData()
                .setUserId(param.getUserId())
                // 会员卡更新金额，区分正负号，这里是负号，意为扣减
                .setMoney(param.getMemberCardBalance().negate())
                .setCardNo(param.getMemberCardNo())
                .setCardId(userCardParam.getCardId())
                .setReason(orderSn)
                // 消费类型 :门店只支持普通卡0
                .setType(MCARD_TP_NORMAL);
            moneyPaidSecondCheck = moneyPaidSecondCheck.subtract(cardDis);
        }
        if (!INTEGER_ZERO.equals(param.getCouponId())) {
            // todo 根据优惠券规则计算优惠券抵扣金额
            if (BigDecimalUtil.greaterThanZero(couponDis)) {
                moneyPaidSecondCheck = orderAmount.subtract(couponDis);
            }
        }
        if (moneyPaidSecondCheck.compareTo(param.getMoneyPaid()) != INTEGER_ZERO) {
            // 应付金额计算错误
            log.error("应付金额[{}]计算错误(前端计算的应付金额为[{}])", moneyPaidSecondCheck, param.getMoneyPaid());
            throw new BusinessException(JsonResultCode.CODE_AMOUNT_PAYABLE_CALCULATION_FAILED);
        }
        log.debug("应付金额[{}]校验成功(前端计算的应付金额为[{}])", moneyPaidSecondCheck, param.getMoneyPaid());
        param.setOrderSn(orderSn);
        param.setVerifyCode(generateVerifyCode());
        param.setOrderStatus(ORDER_STATUS_WAIT_PAY);
        param.setOrderStatusName(ORDER_STATUS_NAME_WAIT_PAY);
        param.setMoneyPaid(moneyPaidSecondCheck);
        log.debug("创建门店服务订单: {}", param);
        return ServiceOrderTran.builder().account(accountParam).cardConsumpData(cardConsumpData).serviceOrder(param).build();
    }

    /**
     * Create service order.创建门店服务预约订单
     *
     * @param param the param
     */
    public String createServiceOrder(ServiceOrderTran param) {
        AccountParam account = param.getAccount();
        CardConsumpData userCard = param.getCardConsumpData();
        ServiceOrderRecord order = param.getServiceOrder();
        // 创建服务订单记录
        db().executeInsert(order);
        // 创建余额，会员卡消费记录
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
        return order.getOrderSn();
    }

    /**
     * 校验核销码
     *
     * @param orderSn
     * @param verifyCode
     * @return
     */
    public Boolean checkVerifyCode(String orderSn, String verifyCode) {
        if (!StringUtils.isBlank(orderSn) && !StringUtils.isBlank(verifyCode)) {
            String trueCode = db().select(SERVICE_ORDER.VERIFY_CODE).from(SERVICE_ORDER).where(SERVICE_ORDER.ORDER_SN.eq(orderSn)).fetchOne().into(String.class);
            return verifyCode.equals(trueCode);
        }
        return false;
    }

    /**
     * 服务预约订单修改
     *
     * @param param
     * @return
     */
    public Boolean serviceOrderUpdate(ServiceOrderUpdateParam param) {
        ServiceOrderRecord record = new ServiceOrderRecord();
        assign(param, record);
        if (INTEGER_ZERO.equals(param.getOrderId())) {
            return db().update(SERVICE_ORDER).set(record).where(SERVICE_ORDER.ORDER_SN.eq(param.getOrderSn())).execute() > 0 ? true : false;
        }
        return db().executeUpdate(record) > 0 ? true : false;
    }

    /**
     * 更新服务预约订单状态
     */
    public void updateServiceOrderStatus(String orderSn, Byte status, String statusName) {
        db().update(SERVICE_ORDER).set(SERVICE_ORDER.ORDER_STATUS, status).set(SERVICE_ORDER.ORDER_STATUS_NAME, statusName).where(SERVICE_ORDER.ORDER_SN.eq(orderSn)).execute();
    }

    /**
     * 使用会员卡核销预约服务订单
     */
    public void userCardServiceOrderCharge(ServiceOrderChargeParam param, int adminUser) throws MpException {
        MemberCardPojo memberCard = saas.getShopApp(getShopId()).member.card.getMemberCardInfoById(param.getCardId());

        /** 会员卡核销的门店服务订单，交易类型认为是会员卡支付 */
        Byte tradeType = RecordTradeEnum.TYPE_CRASH_MEMBER_CARD_PAY.val();
        Byte tradeFlow = RecordTradeEnum.TRADE_FLOW_IN.val();

        /** 会员卡交易的参数 */
        CardConsumpData cardConsumpData = new CardConsumpData();
        cardConsumpData.setType(memberCard.getCardType());
        cardConsumpData.setCardNo(param.getCardNo());
        cardConsumpData.setCardId(param.getCardId());
        cardConsumpData.setOrderSn(param.getOrderSn());
        cardConsumpData.setReason(param.getReason());
        /** 消费人暂时记录为后台操作人员 */
        cardConsumpData.setUserId(adminUser);
        /** 国际化语言 */
        String language = "";

        TradeOptParam tradeOpt = TradeOptParam
            .builder()
            .adminUserId(adminUser)
            .tradeFlow(tradeFlow)
            .tradeType(tradeType)
            .build();

        if (CardConstant.MCARD_TP_LIMIT.equals(memberCard.getCardType())) {
            /** 负数为消费次数 */
            cardConsumpData.setCount(-param.getReduce().intValue());
            saas.getShopApp(getShopId()).member.card.updateMemberCardSurplus(cardConsumpData, tradeOpt, language);
        } else if (MCARD_TP_NORMAL.equals(memberCard.getCardType())) {
            /** 负数为消费金额 */
            cardConsumpData.setMoney(param.getReduce().negate());
            saas.getShopApp(getShopId()).member.card.updateMemberCardAccount(cardConsumpData, tradeOpt, language);
        }
    }

    /**
     * 使用会员卡核销预约服务订单
     */
    public void accountServiceOrderCharge(ServiceOrderChargeParam param, int adminUser) throws MpException {
        /** 余额核销的门店服务订单，交易类型认为是余额支付 */
        Byte tradeType = RecordTradeEnum.TYPE_CRASH_ACCOUNT_PAY.val();
        Byte tradeFlow = RecordTradeEnum.TRADE_FLOW_IN.val();

        AccountParam accountData = new AccountParam();
        accountData.setAccount(userService.getUserByUserId(param.getUserId()).getAccount());
        accountData.setAmount(param.getBalance().negate());
        accountData.setUserId(param.getUserId());
        accountData.setRemark(param.getReason());
        accountData.setOrderSn(param.getOrderSn());
        /** 国际化语言 */
        String language = "";
        /** 余额核销的门店服务订单，交易类型认为是余额支付 */
    	TradeOptParam tradeOpt = TradeOptParam.builder()
    			.adminUserId(adminUser)
            	.tradeType(tradeType)
            	.tradeFlow(tradeFlow)
            	.build();
        saas.getShopApp(getShopId()).member.account.addUserAccount(accountData, tradeOpt);
    }


    /**
     * 获取用户最新的预约服务
     *
     * @param userId
     * @return
     */
    public StoreUserCertVo getUserLastOrderInfo(Integer userId) {
        Result<Record> fetch = db().select(SERVICE_ORDER.asterisk(), STORE_SERVICE.SERVICE_NAME, STORE_SERVICE.SERVICE_PRICE,
            STORE_SERVICE.SERVICE_SUBSIST, STORE_SERVICE.SERVICE_IMG, STORE.STORE_NAME, STORE.LATITUDE,
            STORE.LONGITUDE, STORE.ADDRESS, STORE.DISTRICT_CODE).from(SERVICE_ORDER).leftJoin(STORE_SERVICE)
            .on(SERVICE_ORDER.SERVICE_ID.eq(STORE_SERVICE.ID)).leftJoin(STORE)
            .on(SERVICE_ORDER.STORE_ID.eq(STORE.STORE_ID)).where(SERVICE_ORDER.USER_ID.eq(userId))
            .and(SERVICE_ORDER.DEL_FLAG.eq((byte) 0)).orderBy(SERVICE_ORDER.ORDER_ID.desc()).fetch();
        if (fetch.size() == 0) {
            return null;
        } else {
            return fetch.get(0).into(StoreUserCertVo.class);
        }
    }

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String REGEX = "-";

    /**
     * Gets order list info.获取门店服务订单列表
     *
     * @param serviceId    the service id
     * @param technicianId the technician id
     * @param date         the date
     * @param orderStatus  the order status
     * @return the order list info
     */
    public List<ServiceOrderListQueryVo> getOrderListInfo(Integer serviceId, Integer technicianId, LocalDate date, Byte orderStatus) {
        SelectConditionStep<ServiceOrderRecord> condition = db().selectFrom(SERVICE_ORDER)
            .where(SERVICE_ORDER.SERVICE_ID.eq(serviceId))
            .and(SERVICE_ORDER.SERVICE_DATE.eq(date.format(DATE_TIME_FORMATTER)))
            .and(SERVICE_ORDER.ORDER_STATUS.eq(orderStatus))
            .and(SERVICE_ORDER.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        if (Objects.nonNull(technicianId)) {
            condition.and(SERVICE_ORDER.TECHNICIAN_ID.eq(technicianId));
        }
        return condition.fetchInto(ServiceOrderListQueryVo.class);
    }

    /**
     * Check max num of reservations long.
     * 判定预约数量是否超过同一时间段内可预约的最多人数(针对不需要技师的预约服务而言),不用传technicianId
     * 判定预约数量是否超过同一时间段内技师单时段服务数量(针对需要技师的预约服务而言), 需要传technicianId
     *
     * @param serviceId    the service id
     * @param technicianId the technician id
     * @param date         the date
     * @param startPeriod  the start period
     * @param endPeriod    the end period
     * @return the long
     */
    public long checkMaxNumOfReservations(Integer serviceId, Integer technicianId, LocalDate date, LocalTime startPeriod, LocalTime endPeriod) {
        List<ServiceOrderListQueryVo> orderListQueryVos = getOrderListInfo(serviceId, technicianId, date, ORDER_STATUS_WAIT_SERVICE);
        if (CollectionUtils.isEmpty(orderListQueryVos)) {
            return LONG_ZERO;
        }
        long num = orderListQueryVos.stream().filter(Objects::nonNull).filter((e) -> {
            String[] periodTime = e.getServicePeriod().split(REGEX);
            return LocalTime.parse(periodTime[0], HH_MM_FORMATTER).isBefore(endPeriod) && LocalTime.parse(periodTime[1], HH_MM_FORMATTER).isAfter(startPeriod);
        }).count();
        log.debug("日期【{}】时间段[{}-{}]内已预约人数为：{}", date, startPeriod, endPeriod, num);
        return num;
    }

    /**
     * Check reservation num boolean.检测该服务已预约数量是否达到上限
     *
     * @param param the param
     * @return the boolean true表示可以继续接收预约订单, 否表示预约人数已达上限
     */
    public boolean checkReservationNum(SubmitReservationParam param) {
        int serviceId = param.getServiceId();
        // 门店服务信息
        StoreServiceParam service = storeService.getStoreService(serviceId);
        Assert.notNull(service, JsonResultCode.CODE_STORE_SERVICE_NOT_EXIST);
        Integer num = BYTE_ONE.equals(service.getServiceType()) ? service.getTechServicesNumber() : service.getServicesNumber();
        // 如果未设置预约数量上限，或者为0，表示无上限
        if (Objects.isNull(num) || INTEGER_ZERO.equals(num)) {
            num = Integer.MAX_VALUE;
        }
        // 校验给定时间段内的服务预约数量
        LocalDate date = LocalDate.parse(param.getServiceDate(), DATE_TIME_FORMATTER);
        String[] period = param.getServicePeriod().split(REGEX);
        log.debug("预约时间段参数为：{}", Arrays.toString(period));
        return checkMaxNumOfReservations(serviceId, param.getTechnicianId(), date, LocalTime.parse(period[0], HH_MM_FORMATTER), LocalTime.parse(period[1], HH_MM_FORMATTER)) < num;
    }

    /**
     * Gets recent order info.获取指定用户最近的一个服务预约订单信息
     *
     * @param userId the user id
     */
    public RecentOrderInfo getRecentOrderInfo(Integer userId) {
        return db().select(SERVICE_ORDER.USER_ID, SERVICE_ORDER.SUBSCRIBER, SERVICE_ORDER.MOBILE)
            .from(SERVICE_ORDER)
            .where(SERVICE_ORDER.USER_ID.eq(userId)).orderBy(SERVICE_ORDER.CREATE_TIME.desc())
            .limit(INTEGER_ONE)
            .fetchOneInto(RecentOrderInfo.class);
    }

    /**
     * Update service order.更新服务订单信息
     *
     * @param orderId the order id
     * @param map     the map
     */
    public void updateServiceOrder(Integer orderId, Map<Field<?>, ?> map) {
        db().update(SERVICE_ORDER).set(map).where(SERVICE_ORDER.ORDER_ID.eq(orderId)).execute();
    }

    /**
     * Update single field.
     *
     * @param <T>     the type parameter
     * @param orderId the order id
     * @param field   the field
     * @param value   the value
     */
    public <T> void updateSingleField(Integer orderId, Field<T> field, T value) {
        db().update(SERVICE_ORDER).set(field, value).where(SERVICE_ORDER.ORDER_ID.eq(orderId)).execute();
    }

    /**
     * Update single field.
     *
     * @param <T>     the type parameter
     * @param orderSn the order sn
     * @param field   the field
     * @param value   the value
     */
    public <T> void updateSingleField(String orderSn, Field<T> field, T value) {
        db().update(SERVICE_ORDER).set(field, value).where(SERVICE_ORDER.ORDER_SN.eq(orderSn)).execute();
    }

    /**
     * Update single field.
     *
     * @param <T>     the type parameter
     * @param orderId the order id
     * @param field   the field
     * @param value   the value
     */
    public <T> void updateSingleField(Integer orderId, Field<T> field, Field<T> value) {
        db().update(SERVICE_ORDER).set(field, value).where(SERVICE_ORDER.ORDER_ID.eq(orderId)).execute();
    }

    /**
     * Select single field t.
     *
     * @param <T>     the type parameter
     * @param orderId the order id
     * @param field   the field
     * @return the t
     */
    public <T> T selectSingleField(Integer orderId, Field<T> field) {
        return db().select(field).from(SERVICE_ORDER).where(SERVICE_ORDER.ORDER_ID.eq(orderId)).fetchOne(field);
    }


    /**
     * Select single field t.
     *
     * @param <T>     the type parameter
     * @param orderSn the order sn
     * @param field   the field
     * @return the t
     */
    public <T> T selectSingleField(String orderSn, Field<T> field) {
        return db().select(field).from(SERVICE_ORDER).where(SERVICE_ORDER.ORDER_SN.eq(orderSn)).fetchOne(field);
    }

    /**
     * Get record service order record.
     *
     * @param orderSn the order sn
     * @return the service order record
     */
    public ServiceOrderRecord getRecord(String orderSn) {
        return db().selectFrom(SERVICE_ORDER).where(SERVICE_ORDER.ORDER_SN.eq(orderSn)).fetchOne();
    }

}
