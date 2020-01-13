package com.vpu.mp.service.pojo.wxapp.goods.groupDraw;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 小程序拼团抽奖列表入参
 * 
 * @author zhaojianqiang
 * @time 下午2:18:35
 */
@Data
public class GroupDrawParam {
	@JsonProperty(value = "group_draw_id")
	private Integer groupDrawId;

}
