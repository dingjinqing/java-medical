package com.vpu.mp.service.pojo.shop.market.channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangronggang
 * @date 2019年8月16日
 * 单个渠道日访问数量
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PerChannelDayInfo {
	private int pvAll;
	private int uvAll;
	private int pvNew;
	private int uvNew;
	private int pvOld;
	private int uvOld;
}

