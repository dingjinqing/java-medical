package com.vpu.mp.service.pojo.wxapp.goods.groupDraw;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小程序拼团抽奖参团详情
 * 
 * @author zhaojianqiang
 * @time 下午2:18:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDrawBotton {
	private Byte isToInvite;
	private Byte isJoinDraw;
	private Byte isOpenDraw;

}