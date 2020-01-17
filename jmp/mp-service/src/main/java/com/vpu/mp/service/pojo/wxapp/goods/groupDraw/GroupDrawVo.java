package com.vpu.mp.service.pojo.wxapp.goods.groupDraw;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsSmallVo;

import lombok.Data;

/**
 * 小程序拼团抽奖列表出参
 * 
 * @author zhaojianqiang
 * @time 下午2:18:35
 */
@Data
public class GroupDrawVo {
	private List<GoodsSmallVo> list;
	
	private GroupDrawInfoVo groupDraw;

}
