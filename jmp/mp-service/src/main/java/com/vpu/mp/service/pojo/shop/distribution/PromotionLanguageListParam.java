package com.vpu.mp.service.pojo.shop.distribution;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class PromotionLanguageListParam {
	private String promotionLanguage;
	private Timestamp startCreateTime;
	private Timestamp endCreateTime;
	private Timestamp startUpdateTime;
	private Timestamp endUpdateTime;
	
	private Integer currentPage;
	private Integer pageRows;
	
}
