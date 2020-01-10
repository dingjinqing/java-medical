package com.vpu.mp.service.pojo.wxapp.goods.groupDraw;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 小程序拼团抽奖参团详情入参
 * 
 * @author zhaojianqiang
 * @time 下午2:18:35
 */
@Data
public class GroupDrawInfoParam {
	@JsonProperty(value = "group_draw_id")
	private Integer groupDrawId;
	
	@JsonProperty(value = "goods_id")
	private Integer goodsId;
	
	@JsonProperty(value = "group_id")
	private Integer groupId;
	
	private Map<String, String> options;
}
