package com.vpu.mp.service.pojo.shop.store.service;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月18日
 */
@Data
public class ServiceOrderCountingDataVo {
	private Integer all;
	private Integer waitPay;
	private Integer waitService;
	private Integer cancelled;
	private Integer finished;
}
