package com.vpu.mp.service.pojo.shop.member.ucard;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.foundation.util.I18N;

import lombok.Builder;
import lombok.Data;

/**
 * @author 黄壮壮
 * 	激活会员卡返回数据
 */
@Data
@Builder
public class ActivateCardVo {
	private Map<String,Object> data;
	private List<String> fields;
	
	@I18N(propertiesFileName = "member")
	private List<String> education;
	
	@I18N(propertiesFileName = "member")
	@JsonProperty("industry_info")
	private List<String>industryInfo;
	
	// 订阅消息模板id
	@JsonProperty("template_ids")
	private List<String> templateIds;
}
