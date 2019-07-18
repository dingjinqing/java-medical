package com.vpu.mp.service.pojo.shop.order;

import java.sql.Timestamp;

import com.vpu.mp.service.foundation.Page;

import lombok.Data;

/**
 * 
 * @author wangshuai
 */
@Data
public class StoreOrderPageListQueryParam {
	private Page page;
	private String orderSn;
	private Timestamp payTimeStart;
	private Timestamp payTimeEnd;
	private String userName;
	private Integer storeId;
	private Byte[] orderStatus;
}
