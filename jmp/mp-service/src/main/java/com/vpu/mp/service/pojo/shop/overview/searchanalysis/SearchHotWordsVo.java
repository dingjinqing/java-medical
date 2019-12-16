package com.vpu.mp.service.pojo.shop.overview.searchanalysis;

import lombok.Data;

/**
 * @author liangchen
 * @date 2019年7月26日
 */
@Data
public class SearchHotWordsVo {

	private String hotWords;
	private Integer searchCount;

    /** 开始日期 */
    private String startTime;
    /** 结束日期 */
    private String endTime;
}
