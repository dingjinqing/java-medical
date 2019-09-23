package com.vpu.mp.service.shop.order.store;

import static com.vpu.mp.db.shop.tables.StoreOrder.STORE_ORDER;
import static com.vpu.mp.db.shop.tables.User.USER;

import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.StoreOrder;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderPageListQueryParam;

/**
 * Table:STORE_ORDER
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
		SelectWhereStep<? extends Record> select = db().select(TABLE.ORDER_ID,TABLE.ORDER_SN,TABLE.ORDER_STATUS,STORE_ORDER.STORE_ID,STORE_ORDER.PAY_TIME,STORE_ORDER.MONEY_PAID,STORE_ORDER.PAY_CODE,STORE_ORDER.PAY_NAME,USER.USERNAME)
				.from(STORE_ORDER).leftJoin(USER)
				.on(USER.USER_ID.eq(STORE_ORDER.USER_ID));
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
		select.orderBy(STORE_ORDER.ORDER_ID);
		
		if(!StringUtils.isEmpty(param.getOrderSn())) {
			select.where(STORE_ORDER.ORDER_SN.eq(param.getOrderSn()));
		}
		if(param.getUserName() != null) {
			select.where(USER.USERNAME.like(likeValue(param.getUserName())));
		}
		if(param.getPayTimeStart() != null ) {
			select.where(STORE_ORDER.PAY_TIME.ge(param.getPayTimeStart()));
		}
		if(param.getPayTimeEnd() != null ) {
			select.where(STORE_ORDER.PAY_TIME.le(param.getPayTimeEnd()));
		}
		if(param.getStoreId() != null ) {
			select.where(STORE_ORDER.STORE_ID.eq(param.getStoreId()));
		}
		if(param.getOrderStatus()!= null && param.getOrderStatus().length != 0) {
			select.where(STORE_ORDER.ORDER_STATUS.in(param.getOrderStatus()));
		}
		return select; 
	 }
}
