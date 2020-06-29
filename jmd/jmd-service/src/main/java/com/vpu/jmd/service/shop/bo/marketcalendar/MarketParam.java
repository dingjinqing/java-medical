package com.vpu.jmd.service.shop.bo.marketcalendar;

import com.vpu.mp.service.foundation.util.Page;
import lombok.Data;

/**
 * 列表的入参
 * @author zhaojianqiang
 * 2020年4月21日下午4:50:42
 */
@Data
public class MarketParam {
	private Integer currentPage;
	private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
