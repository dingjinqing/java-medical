package com.vpu.mp.service.pojo.shop.order;

import java.sql.Timestamp;

import com.vpu.mp.service.foundation.Page;

import lombok.Data;

@Data
public class OrderPageListQueryParam {
	public Page page;
	public String goodsName;
	public String orderSn;
	public Byte[] orderStatus;
	/**订单类型*/
	public Integer goodsType;
	/**收货人姓名*/
	public String consignee;
	public String mobile;
	/**下单时间开始*/
	public Timestamp createTimeStart;
	/**下单时间结束*/
	public Timestamp createTimeEnd;
	/**配送方式*/
	public Byte deliverType;
	/**买家昵称,会员表的username*/
	public String userName;
	/**买家来源*/
	public String source;
	/**标签,买家会员标签*/
	public Integer[] tagIds; 
	/**门店id*/
	public Integer storeId;
	/**核销自提码*/
	public String verifyCode;
	/**完成时间*/
	public Timestamp finishedTimeStart;
	public Timestamp finishedTimeEnd;
	/**国别、省、市、区*/
	public Integer countryCode;
	public Integer provinceCode;
	public Integer cityCode;
	public Integer districtCode;
}
