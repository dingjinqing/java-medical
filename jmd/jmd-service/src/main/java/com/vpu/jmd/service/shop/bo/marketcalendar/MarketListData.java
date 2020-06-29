package com.vpu.jmd.service.shop.bo.marketcalendar;

import lombok.Data;

import java.util.List;

/**
 *
 * @author zhaojianqiang 2020年4月22日下午4:04:37
 */
@Data
public class MarketListData {
	private String month;
	private List<MarketCalendarVo> data;
}
