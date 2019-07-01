package com.vpu.mp.service.shop.order;

import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;

import org.jooq.Record1;
import org.jooq.SelectWhereStep;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;

import lombok.Data;

/**
 * 
 * @author 常乐
 * 2019年6月27日
 */
public class OrderService extends BaseService{
	
	@Data
	 public static class PageListQueryParam {
		 
		 public Integer page;
		 
		 public Integer orderId;
		 public String goodsName;
		 public String orderSn;
		 public String orderType;
		 public String consignee;
		 public String mobile;
	 };
	 
	 /**
	  * 订单列表
	  * @param param
	  * @return
	  */
	 public PageResult getPageList(PageListQueryParam param) {
		 SelectWhereStep<Record1<String>> select1 = db().select(ORDER_INFO.ORDER_SN).from(ORDER_INFO);
		 SelectWhereStep<?> select = this.buildOptions(select1, param);
		 select.orderBy(ORDER_INFO.ADD_TIME.desc());
		 return this.getPageResult(select);
	 }
	 
	 /**
	  * 条件查询
	  * @param select
	  * @param param
	  * @return
	  */
	 public SelectWhereStep<?> buildOptions(SelectWhereStep<?> select, PageListQueryParam param) {
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
