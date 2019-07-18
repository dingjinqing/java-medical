package com.vpu.mp.service.shop.store.service;

import static com.vpu.mp.db.shop.tables.ServiceOrder.SERVICE_ORDER;
import static com.vpu.mp.db.shop.tables.StoreService.STORE_SERVICE;

import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.DelFlag;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.store.service.ServiceOrderDetailVo;
import com.vpu.mp.service.pojo.shop.store.service.ServiceOrderListQueryParam;
import com.vpu.mp.service.pojo.shop.store.service.ServiceOrderListQueryVo;
import com.vpu.mp.service.pojo.shop.store.service.ServiceOrderUpdateParam;

/**
 * @author 王兵兵
 *
 * 2019年7月17日
 * 
 * 预约（门店服务订单）
 */
public class ServiceOrderService extends BaseService{
	/**
	 * 门店服务预约列表分页查询
	 * @param StoreListQueryParam
	 * @return StorePageListVo
	 */
	public PageResult<ServiceOrderListQueryVo> getPageList(ServiceOrderListQueryParam param) {
		SelectWhereStep<? extends Record> select = 
		db().select(SERVICE_ORDER.ORDER_SN,SERVICE_ORDER.SUBSCRIBER,STORE_SERVICE.SERVICE_NAME,SERVICE_ORDER.MOBILE,SERVICE_ORDER.SERVICE_DATE,SERVICE_ORDER.SERVICE_PERIOD,SERVICE_ORDER.TECHNICIAN_NAME,STORE_SERVICE.SERVICE_SUBSIST,SERVICE_ORDER.ADD_MESSAGE).
		from(SERVICE_ORDER).
		leftJoin(STORE_SERVICE).on(SERVICE_ORDER.SERVICE_ID.eq(STORE_SERVICE.ID));
		select = this.buildOptions(select, param);
		select.where(SERVICE_ORDER.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(SERVICE_ORDER.STORE_ID.eq(param.getStoreId())).orderBy(SERVICE_ORDER.CREATE_TIME.desc());
		return getPageResult(select,param.getCurrentPage(),param.getPageRows(),ServiceOrderListQueryVo.class);
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
	
	public Boolean addServiceOrderAdminMessage(ServiceOrderUpdateParam param) {
		return db().update(SERVICE_ORDER).set(SERVICE_ORDER.ADMIN_MESSAGE,param.getAdminMessage()).where(SERVICE_ORDER.ORDER_SN.eq(param.getOrderSn())).execute() > 0 ? true : false;
	}
}
