package com.vpu.mp.service.shop.order;

import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;

import org.jooq.Record1;
import org.jooq.SelectWhereStep;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;

/**
 * 
 * @author 常乐
 * 2019年6月27日
 */
public class OrderService extends BaseService{
	
	 
	 /**
	  * 订单列表 TODO: 返回参数需要修改 不能为Object，暂时为了编译正常
	  * @param param
	  * @return
	  */
	 public PageResult<Object> getPageList(OrderPageListQueryParam param) {
		 SelectWhereStep<Record1<String>> select1 = db().select(ORDER_INFO.ORDER_SN).from(ORDER_INFO);
		 SelectWhereStep<?> select = this.buildOptions(select1, param);
		 select.orderBy(ORDER_INFO.CREATE_TIME.desc());
		 return this.getPageResult(select,Object.class);
	 }
	 
	 /**
	  * 条件查询
	  * @param select
	  * @param param
	  * @return
	  */
	 public SelectWhereStep<?> buildOptions(SelectWhereStep<?> select, OrderPageListQueryParam param) {
		 if(param == null) {
			 return select;
		 }
		 
		 if(param.orderId != null && param.orderId > 0) {
			 select.where(ORDER_INFO.ORDER_ID.eq(param.orderId));
		 }
		 
		 return select;
	 }
	 
//	 /**
//	  * 订单数量
//	  * @return
//	  */
//	 public int getPageCount() {
//		 return db().fetchCount(ORDER_INFO);
//	 }
}
