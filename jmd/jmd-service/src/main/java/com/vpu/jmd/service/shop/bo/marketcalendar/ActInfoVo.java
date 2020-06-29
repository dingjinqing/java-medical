package com.vpu.jmd.service.shop.bo.marketcalendar;

import com.vpu.mp.service.foundation.util.PageResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author zhaojianqiang 2020年4月22日下午3:14:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActInfoVo {
	private MarketVo actInfo;
	private PageResult<MarketVo> list;
}
