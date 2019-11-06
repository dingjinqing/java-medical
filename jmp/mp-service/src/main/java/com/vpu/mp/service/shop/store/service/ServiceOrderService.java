package com.vpu.mp.service.shop.store.service;

import com.vpu.mp.db.shop.tables.records.ServiceOrderRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.card.CardConsumpData;
import com.vpu.mp.service.pojo.shop.member.card.MemberCardPojo;
import com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceParam;
import com.vpu.mp.service.pojo.shop.store.service.order.*;
import org.jooq.*;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.vpu.mp.db.shop.tables.ServiceOrder.SERVICE_ORDER;
import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.db.shop.tables.StoreService.STORE_SERVICE;
import static com.vpu.mp.service.shop.store.store.StoreWxService.HH_MM_FORMATTER;

/**
 * @author 王兵兵
 *
 * 2019年7月17日
 *
 * 预约（门店服务订单）
 */
@Service

public class ServiceOrderService extends ShopBaseService{

	/**
	 * 订单状态 0：待服务，1：已取消，2：已完成，3：待付款
	 */
	public static final Byte ORDER_STATUS_WAIT_SERVICE = 0;
	public static final Byte ORDER_STATUS_CANCELED = 1;
	public static final Byte ORDER_STATUS_FINISHED = 2;
	public static final Byte ORDER_STATUS_WAIT_PAY = 3;

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
	 * @param
	 * @return StorePageListVo
	 */
	public PageResult<ServiceOrderListQueryVo> getPageList(ServiceOrderListQueryParam param) {
        SelectWhereStep<? extends Record> select =
		db().select(SERVICE_ORDER.STORE_ID,SERVICE_ORDER.ORDER_SN,SERVICE_ORDER.USER_ID,SERVICE_ORDER.SUBSCRIBER,STORE_SERVICE.SERVICE_NAME,SERVICE_ORDER.MOBILE,SERVICE_ORDER.SERVICE_DATE,SERVICE_ORDER.SERVICE_PERIOD,SERVICE_ORDER.TECHNICIAN_NAME,STORE_SERVICE.SERVICE_SUBSIST,SERVICE_ORDER.ADD_MESSAGE).
		from(SERVICE_ORDER).
		leftJoin(STORE_SERVICE).on(SERVICE_ORDER.SERVICE_ID.eq(STORE_SERVICE.ID));
		select = this.buildOptions(select, param);
		select.where(SERVICE_ORDER.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(SERVICE_ORDER.STORE_ID.eq(param.getStoreId())).orderBy(SERVICE_ORDER.CREATE_TIME.desc());
		return getPageResult(select,param.getCurrentPage(),param.getPageRows(),ServiceOrderListQueryVo.class);
	}

    /**
	 * 门店服务预约列表计数
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
		return select.fetchOne(0,Integer.class);
	}

	/**
	 * 门店服务预约的条件查询
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends  Record> select, ServiceOrderListQueryParam param) {
		if (param == null) {
			return select;
		}

		if(param.getOrderStatus() != null && param.getOrderStatus() > (byte)-1){
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
				SERVICE_ORDER.ORDER_ID,SERVICE_ORDER.ORDER_SN,SERVICE_ORDER.ORDER_STATUS,SERVICE_ORDER.ORDER_STATUS_NAME,SERVICE_ORDER.SUBSCRIBER,
				SERVICE_ORDER.MOBILE,SERVICE_ORDER.TECHNICIAN_NAME,SERVICE_ORDER.SERVICE_DATE,SERVICE_ORDER.SERVICE_PERIOD,SERVICE_ORDER.ADD_MESSAGE,
				SERVICE_ORDER.ADMIN_MESSAGE,SERVICE_ORDER.ORDER_AMOUNT,SERVICE_ORDER.MONEY_PAID,SERVICE_ORDER.FINISHED_TIME,SERVICE_ORDER.VERIFY_TYPE,
				SERVICE_ORDER.VERIFY_CODE,SERVICE_ORDER.VERIFY_ADMIN,SERVICE_ORDER.TYPE,SERVICE_ORDER.VERIFY_PAY,SERVICE_ORDER.CREATE_TIME,
				STORE_SERVICE.SERVICE_NAME,STORE_SERVICE.SERVICE_PRICE,STORE_SERVICE.SERVICE_SUBSIST,STORE_SERVICE.SERVICE_IMG
				).
		from(SERVICE_ORDER).leftJoin(STORE_SERVICE).on(SERVICE_ORDER.SERVICE_ID.eq(STORE_SERVICE.ID)).
		where(SERVICE_ORDER.ORDER_SN.eq(orderSn)).fetchOne();
		if(vo == null) {
			return null;
		}else {
			return vo.into(ServiceOrderDetailVo.class);
		}
	}

	public Boolean addServiceOrderAdminMessage(ServiceOrderAdminMessageParam param) {
		return db().update(SERVICE_ORDER).set(SERVICE_ORDER.ADMIN_MESSAGE,param.getAdminMessage()).where(SERVICE_ORDER.ORDER_SN.eq(param.getOrderSn())).execute() > 0 ? true : false;
	}

	/**
	 *生成订单号
	 */
	public String generateOrderSn() {
		Random random = new Random();
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
        String date = sdf.format(new Date());
        String orderSn;
        do {
        	orderSn = "";
        	int randomStr = random.nextInt(9999)%(9000) + 1000;
        	orderSn = "S".concat(date).concat(String.valueOf(randomStr));
        }while(hasOrderSn(orderSn));
        return orderSn;
	}

	/**
	 * 判断该orderSn是否已存在
	 * @param orderSn
	 * @return
	 */
	public Boolean hasOrderSn(String orderSn) {
		List<Integer> ids = db().select(SERVICE_ORDER.ORDER_ID).from(SERVICE_ORDER).where(SERVICE_ORDER.ORDER_SN.eq(orderSn)).fetch().into(Integer.class);
		if(ids.size() > 0) {
			return true;
		}else {
			return false;
		}
	}

	public String generateVerifyCode() {
		Random random = new Random(2);
		List<Integer> ids;
		String verifyCode;
		do {
		int randomStr = random.nextInt(999999)%(900000) + 100000;
		verifyCode = String.valueOf(randomStr);
		ids = db().select(SERVICE_ORDER.VERIFY_CODE).from(SERVICE_ORDER).where(SERVICE_ORDER.VERIFY_CODE.eq(verifyCode)).fetch().into(Integer.class);
		}while(ids.size() > 0);
		return verifyCode;
	}

	public BigDecimal getServiceMoneyPaid(Integer serviceId) {
		StoreServiceParam storeService = db().select(STORE_SERVICE.SERVICE_PRICE,STORE_SERVICE.SERVICE_SUBSIST).from(STORE_SERVICE).where(STORE_SERVICE.ID.eq(serviceId)).fetchOne().into(StoreServiceParam.class);
		return storeService.getServicePrice().subtract(storeService.getServiceSubsist()) ;
	}

	/**
     * 后台添加服务预约
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
	 * 校验核销码
	 * @param orderSn
	 * @param verifyCode
	 * @return
	 */
	public Boolean checkVerifyCode(String orderSn,String verifyCode) {
		if(!StringUtils.isBlank(orderSn) && !StringUtils.isBlank(verifyCode)) {
			String trueCode = db().select(SERVICE_ORDER.VERIFY_CODE).from(SERVICE_ORDER).where(SERVICE_ORDER.ORDER_SN.eq(orderSn)).fetchOne().into(String.class);
			return verifyCode.equals(trueCode);
		}
		return false;
	}

    /**
	 * 服务预约订单修改
	 * @param param
	 * @return
	 */
	public Boolean serviceOrderUpdate(ServiceOrderUpdateParam param) {
		ServiceOrderRecord record = new ServiceOrderRecord();
	    assign(param, record);
	    return db().executeUpdate(record) > 0 ? true : false;
	}

    /**
     * 使用会员卡核销预约服务订单
     */
	public void userCardServiceOrderCharge(ServiceOrderChargeParam param,int adminUser) throws MpException{
        MemberCardPojo memberCard = saas.getShopApp(getShopId()).member.card.getMemberCardInfoById(param.getCardId());

        /** 会员卡核销的门店服务订单，交易类型认为是会员卡支付 */
        Byte tradeType = RecordTradeEnum.MEMBER_CARD_PAY.getValue();
        Byte tradeFlow = RecordTradeEnum.TRADE_FLOW_INCOME.getValue();

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
        String language="";
        if(memberCard.getCardType() == CardConstant.MCARD_TP_LIMIT){
            /** 负数为消费次数 */
            cardConsumpData.setCount(-param.getReduce().intValue());
            saas.getShopApp(getShopId()).member.card.updateMemberCardSurplus(cardConsumpData,adminUser,tradeType,tradeFlow,language);
        }else if(memberCard.getCardType() == CardConstant.MCARD_TP_NORMAL){
            /** 负数为消费金额 */
            cardConsumpData.setMoney(param.getReduce().negate());
            saas.getShopApp(getShopId()).member.card.updateMemberCardAccount(cardConsumpData,adminUser,tradeType,tradeFlow,language);
        }
    }

    /**
     * 使用会员卡核销预约服务订单
     */
    public void accountServiceOrderCharge(ServiceOrderChargeParam param,int adminUser) throws MpException{
        /** 余额核销的门店服务订单，交易类型认为是余额支付 */
        Byte tradeType = RecordTradeEnum.ACCOUNT_PAY.getValue();
        Byte tradeFlow = RecordTradeEnum.TRADE_FLOW_INCOME.getValue();

        AccountParam accountData = new AccountParam();
        accountData.setAccount(param.getAccount());
        accountData.setAmount(param.getBalance().negate());
        accountData.setUserId(param.getUserId());
        accountData.setRemark(param.getReason());
        accountData.setOrderSn(param.getOrderSn());
        /** 国际化语言 */
        String language = "";
        /** 余额核销的门店服务订单，交易类型认为是余额支付 */
        saas.getShopApp(getShopId()).member.account.addUserAccount(accountData,adminUser,tradeType,tradeFlow,language);
    }


    /**
     * 获取用户最新的预约服务
     * @param userId
     * @return
     */
    public Record getUserLastOrderInfo(Integer userId) {
		Result<Record> fetch = db().select(SERVICE_ORDER.asterisk(), STORE_SERVICE.SERVICE_NAME, STORE_SERVICE.SERVICE_PRICE,
				STORE_SERVICE.SERVICE_SUBSIST, STORE_SERVICE.SERVICE_IMG, STORE.STORE_NAME, STORE.LATITUDE,
				STORE.LONGITUDE, STORE.ADDRESS, STORE.DISTRICT_CODE).from(SERVICE_ORDER).leftJoin(STORE_SERVICE)
				.on(SERVICE_ORDER.SERVICE_ID.eq(STORE_SERVICE.ID)).leftJoin(STORE)
				.on(SERVICE_ORDER.STORE_ID.eq(STORE.STORE_ID)).where(SERVICE_ORDER.USER_ID.eq(userId))
				.and(SERVICE_ORDER.DEL_FLAG.eq((byte) 0)).orderBy(SERVICE_ORDER.ORDER_ID.desc()).fetch();
		if(fetch.size()==0) {
			return null;
		}else {
			return fetch.get(0);
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
        SelectConditionStep<Record11<Integer, String, Integer, String, String, String, String, String, String, BigDecimal, String>> condition = db().select(SERVICE_ORDER.STORE_ID, SERVICE_ORDER.ORDER_SN, SERVICE_ORDER.USER_ID, SERVICE_ORDER.SUBSCRIBER, STORE_SERVICE.SERVICE_NAME, SERVICE_ORDER.MOBILE, SERVICE_ORDER.SERVICE_DATE, SERVICE_ORDER.SERVICE_PERIOD, SERVICE_ORDER.TECHNICIAN_NAME, STORE_SERVICE.SERVICE_SUBSIST, SERVICE_ORDER.ADD_MESSAGE).
            from(SERVICE_ORDER)
            .where(SERVICE_ORDER.SERVICE_ID.eq(serviceId))
            .and(SERVICE_ORDER.SERVICE_DATE.eq(date.format(DATE_TIME_FORMATTER)))
            .and(SERVICE_ORDER.ORDER_STATUS.eq(orderStatus))
            .and(SERVICE_ORDER.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        if (Objects.nonNull(technicianId)) {
            condition.and(SERVICE_ORDER.TECHNICIAN_ID.eq(technicianId));
        }
        return condition.fetchInto(ServiceOrderListQueryVo.class);
    }

    // 判定预约数量是否超过同一时间段内可预约的最多人数(针对不需要技师的预约服务而言)
    public long checkMaxNumOfReservations(Integer serviceId, LocalDate date, LocalTime startPeriod, LocalTime endPeriod) {
        List<ServiceOrderListQueryVo> orderListQueryVos = getOrderListInfo(serviceId, null, date, ORDER_STATUS_WAIT_SERVICE);
        return orderListQueryVos.stream().filter((e) -> {
            String[] periodTime = e.getServicePeriod().split(REGEX);
            return LocalTime.parse(periodTime[0], HH_MM_FORMATTER).isBefore(endPeriod) && LocalTime.parse(periodTime[1], HH_MM_FORMATTER).isAfter(startPeriod);
        }).count();
    }

    public static void filterData(ServiceOrderListQueryVo vo) {

    }

    // 判定预约数量是否超过同一时间段内技师单时段服务数量
}
