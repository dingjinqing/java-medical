package com.vpu.mp.service.pojo.shop.order;

import lombok.Data;

@Data
public class OrderPageListQueryParam {
	 public Integer page;
	 public Integer orderId;
	 public String goodsName;
	 public String orderSn;
	 public String orderType;
	 public String consignee;
	 public String mobile;
}
