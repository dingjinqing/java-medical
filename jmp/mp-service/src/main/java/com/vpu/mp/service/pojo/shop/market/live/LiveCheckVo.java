package com.vpu.mp.service.pojo.shop.market.live;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 直播列表校验的一些出参数
 * 
 * @author zhaojianqiang
 * @time 下午2:00:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveCheckVo {
	private Boolean isAuthLive;
	private Byte auditState;
	private Boolean hasLiveFunc;
}
