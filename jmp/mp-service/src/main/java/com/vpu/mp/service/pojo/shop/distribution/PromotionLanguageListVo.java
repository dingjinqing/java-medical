package com.vpu.mp.service.pojo.shop.distribution;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class PromotionLanguageListVo {
	private String title;
	private String  PromotionLanguage;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Byte isBlock;
}
