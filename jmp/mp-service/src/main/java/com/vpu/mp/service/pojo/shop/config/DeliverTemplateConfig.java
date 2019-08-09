package com.vpu.mp.service.pojo.shop.config;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *
 * @author liangchen
 * @date 2019年8月8日
 */
@Data
public class DeliverTemplateConfig {
	
	/**
	 *  0：统一运费，1：满额包邮，
	 */
	@JsonProperty(value = "template_name")
	public Integer templateName;
	
	/**
	 * 包邮限制最低金额
	 */
	@JsonProperty(value = "fee_limit")
	public BigDecimal feeLimit;
	
	/**
	 * 运费
	 */
	@JsonProperty(value = "price")
	public BigDecimal price;
	
	
}
