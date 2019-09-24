package com.vpu.mp.service.shop.order.store;

import static com.vpu.mp.db.shop.tables.StoreOrder.STORE_ORDER;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.Invoice.INVOICE;
import static com.vpu.mp.db.shop.tables.Store.STORE;

import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.StoreOrder;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderPageListQueryParam;

/**
 * Table:TABLE
 * 
 * @author 王帅
 *
 */
@Service
public class StoreOrderService extends ShopBaseService {

	public final StoreOrder TABLE = STORE_ORDER;
	
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
				 TABLE.MEMBER_CARD_BALANCE,TABLE.MEMBER_CARD_REDUCE,TABLE.SCORE_DISCOUNT,TABLE.USE_ACCOUNT,TABLE.ORDER_AMOUNT,TABLE.MONEY_PAID,TABLE.ADD_MESSAGE,
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
}
