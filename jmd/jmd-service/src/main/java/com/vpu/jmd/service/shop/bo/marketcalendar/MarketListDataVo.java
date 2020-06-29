package com.vpu.jmd.service.shop.bo.marketcalendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author zhaojianqiang
 * 2020年4月22日下午5:13:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketListDataVo {
	private List<MarketListData> data;
	private Date currentDate;
	private List<String> yearList;
}
