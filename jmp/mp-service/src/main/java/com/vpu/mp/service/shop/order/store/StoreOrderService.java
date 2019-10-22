package com.vpu.mp.service.shop.order.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vpu.mp.db.main.tables.records.UserRecord;
import com.vpu.mp.db.shop.Tables;
import com.vpu.mp.db.shop.tables.StoreOrder;
import com.vpu.mp.db.shop.tables.records.StoreOrderRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.invoice.InvoiceVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import com.vpu.mp.service.pojo.wxapp.store.StorePayOrderInfo;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.store.service.ServiceOrderService;
import com.vpu.mp.service.shop.store.store.StoreService;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.vpu.mp.db.shop.tables.Invoice.INVOICE;
import static com.vpu.mp.db.shop.tables.MemberCard.MEMBER_CARD;
import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.db.shop.tables.StoreOrder.STORE_ORDER;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserCard.USER_CARD;
import static com.vpu.mp.service.pojo.shop.market.form.FormConstant.MAPPER;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_WAIT_PAY;
import static com.vpu.mp.service.pojo.shop.payment.PayCode.PAY_CODE_BALANCE_PAY;
import static com.vpu.mp.service.pojo.shop.payment.PayCode.PAY_CODE_WX_PAY;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;

/**
 * Table:TABLE
 *
 * @author 王帅
 *
 */
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

	public final StoreOrder TABLE = STORE_ORDER;
    public static final BigDecimal HUNDRED = new BigDecimal(100);
    public static final BigDecimal TEN = new BigDecimal(10);
    public static final BigDecimal ONE = new BigDecimal(1);
    public static final BigDecimal ZERO = new BigDecimal(0);

	/**
	 * 	买单订单综合查询
	 * @param param
	 * @return
	 */
	public PageResult<StoreOrderListInfoVo> getPageList(StoreOrderPageListQueryParam param){
		SelectWhereStep<? extends Record> select = db().select(TABLE.ORDER_ID,TABLE.ORDER_SN,TABLE.ORDER_STATUS,TABLE.STORE_ID,TABLE.PAY_TIME,TABLE.MONEY_PAID,TABLE.PAY_CODE,TABLE.PAY_NAME,USER.USERNAME)
				.from(TABLE).leftJoin(USER)
				.on(USER.USER_ID.eq(TABLE.USER_ID));
		buildOptionsStore(select,param);
		PageResult<StoreOrderListInfoVo> result = getPageResult(select,param.getCurrentPage(),param.getPageRows(),StoreOrderListInfoVo.class);
		return result;
	}

	/**
	 * 	构造买单订单查询条件
	 * @param select
	 * @param param
	 * @return
	 */
	 public SelectWhereStep<?> buildOptionsStore(SelectWhereStep<?> select, StoreOrderPageListQueryParam param) {
		//自增id排序
		select.orderBy(TABLE.ORDER_ID);

		select.where(TABLE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));

         select.where(TABLE.ORDER_STATUS.in(OrderConstant.STORE_STATUS_PAY,OrderConstant.STORE_STATUS_RETURN));

         if(!StringUtils.isEmpty(param.getOrderSn())) {
			select.where(TABLE.ORDER_SN.like(param.getOrderSn()));
		}
		if(param.getUserName() != null) {
			select.where(USER.USERNAME.like(likeValue(param.getUserName())));
		}
		if(param.getPayTimeStart() != null ) {
			select.where(TABLE.PAY_TIME.ge(param.getPayTimeStart()));
		}
		if(param.getPayTimeEnd() != null ) {
			select.where(TABLE.PAY_TIME.le(param.getPayTimeEnd()));
		}
		if(param.getStoreId() != null ) {
			select.where(TABLE.STORE_ID.eq(param.getStoreId()));
		}
		if(param.getOrderStatus()!= null && param.getOrderStatus().length != 0) {
			select.where(TABLE.ORDER_STATUS.in(param.getOrderStatus()));
		}
         return select;
     }

	 public StoreOrderInfoVo get(String orderSn) {
		 return db().select(TABLE.ORDER_ID,TABLE.ORDER_SN,TABLE.ORDER_STATUS,TABLE.STORE_ID,TABLE.PAY_TIME,TABLE.MONEY_PAID,TABLE.PAY_CODE,TABLE.PAY_NAME,
				 TABLE.MEMBER_CARD_BALANCE,TABLE.MEMBER_CARD_REDUCE,TABLE.SCORE_DISCOUNT,TABLE.USE_ACCOUNT,TABLE.ORDER_AMOUNT,TABLE.MONEY_PAID,TABLE.ADD_MESSAGE,TABLE.CURRENCY,
				 USER.USERNAME,
				 STORE.STORE_NAME,
				 INVOICE.TYPE,INVOICE.TITLE,INVOICE.TAXNUMBER.as("taxNumber"),INVOICE.COMPANYADDRESS.as("companyAddress"))
			.from(TABLE)
			.leftJoin(USER).on(USER.USER_ID.eq(TABLE.USER_ID))
			.leftJoin(STORE).on(STORE.STORE_ID.eq(TABLE.STORE_ID))
			.leftJoin(INVOICE).on(INVOICE.USER_ID.eq(TABLE.USER_ID))
			.where(TABLE.ORDER_SN.eq(orderSn))
			.fetchOneInto(StoreOrderInfoVo.class);
	}

    /**
     * Create store order.
     *
     * @param userInfo    the user info 用户信息
     * @param invoiceInfo the invoice info 发票信息
     * @param orderInfo   the order info 门店订单信息
     */
    public void createStoreOrder(UserRecord userInfo, InvoiceVo invoiceInfo, StorePayOrderInfo orderInfo) {
        // 会员卡折扣金额
        BigDecimal cardDiscount = ZERO;
        StorePojo storePojo = store.getStore(orderInfo.getStoreId());
        Objects.requireNonNull(storePojo, "店铺不存在");
        if (storePojo.getDelFlag().equals(BYTE_ONE)) {
            // todo 该门店已删除
            throw new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST);
        }
        String cardNo = orderInfo.getCardNo();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(cardNo)) {
            Record2<BigDecimal, BigDecimal> record2 = db().select(USER_CARD.MONEY, MEMBER_CARD.DISCOUNT).from(USER_CARD).leftJoin(MEMBER_CARD)
                .on(Tables.USER_CARD.CARD_ID.eq(Tables.MEMBER_CARD.ID)).where(USER_CARD.CARD_NO.eq(cardNo)).fetchAny();
            // 会员卡折扣
            BigDecimal discount = record2.getValue(MEMBER_CARD.DISCOUNT);
            // 会员卡余额
            BigDecimal money = record2.getValue(USER_CARD.MONEY);

            if (discount != null) {
                // 计算会员卡折扣金额
                cardDiscount = orderInfo.getOrderAmount().multiply((ONE.subtract(discount.divide(TEN))));
            }
            // 会员卡抵扣金额
            if (orderInfo.getCardChargeDis().compareTo(money) > 0) {
                // todo 会员卡余额不足
                throw new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST);
            }
            // 积分抵扣金额(积分数除以100就是积分抵扣金额数)
            if (orderInfo.getScoreDis().multiply(HUNDRED).intValue() > userInfo.getScore()) {
                // todo 积分不足，无法下单
                throw new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST);
            }
            // 余额抵扣金额
            if (orderInfo.getAccountDis().compareTo(userInfo.getAccount()) > 0) {
                // todo 余额不足，无法下单
                throw new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST);
            }
            // 应付金额
            BigDecimal moneyPaid = orderInfo.getOrderAmount()
                .subtract(cardDiscount)
                .subtract(orderInfo.getScoreDis())
                .subtract(orderInfo.getAccountDis())
                .subtract(orderInfo.getCardChargeDis())
                .setScale(2, RoundingMode.HALF_UP);
            if (orderInfo.getTotalPrice().compareTo(ZERO) < 0 || orderInfo.getTotalPrice().compareTo(moneyPaid) != 0) {
                // todo 应付金额计算错误
                throw new BusinessException(JsonResultCode.CODE_DATA_NOT_EXIST);
            }
            StoreOrderRecord orderRecord = new StoreOrderRecord();
            orderRecord.setStoreId(orderInfo.getStoreId());
            // 生成订单编号
            orderRecord.setOrderSn(orderService.generateOrderSn());
            orderRecord.setUserId(userInfo.getUserId());
            orderRecord.setOrderStatus(ORDER_WAIT_PAY);
            orderRecord.setOrderStatusName("未支付");
            orderRecord.setInvoiceId(invoiceInfo.getId());
            try {
                orderRecord.setInvoiceDetail(MAPPER.writeValueAsString(invoiceInfo));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            orderRecord.setAddMessage(orderInfo.getRemark());
            orderRecord.setPayCode(moneyPaid.compareTo(ZERO) > 0 ? PAY_CODE_WX_PAY : PAY_CODE_BALANCE_PAY);
            orderRecord.setPayName("");
            orderRecord.setMoneyPaid(moneyPaid);
            orderRecord.setMemberCardNo(orderInfo.getCardNo());
            orderRecord.setMemberCardRedunce(cardDiscount);
            orderRecord.setMemberCardBalance(orderInfo.getCardChargeDis());
            orderRecord.setScoreDiscount(orderInfo.getScoreDis());
            orderRecord.setUseAccount(orderInfo.getAccountDis());
            orderRecord.setOrderAmount(orderInfo.getOrderAmount());
            orderRecord.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        }
    }
}
