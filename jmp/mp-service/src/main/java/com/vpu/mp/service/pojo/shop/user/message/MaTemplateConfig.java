package com.vpu.mp.service.pojo.shop.user.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MaTemplateConfig {
	protected String id;
	protected String title;
	protected String content;
	protected Integer[] keywordIds;
	protected Integer emphasisKeywordSn;

}
