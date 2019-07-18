package com.vpu.mp.service.pojo.shop.overview.analysis;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;
/**
 * @author liangchen
 * @date  2019年7月15日
 */

@Data
public class OverviewAnalysisDateParam {
    /***
     * 获得昨日时间（字符串格式精确到日）
     * @return
     */
	Date yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
	String dateYesterdayStr = simpleDateFormat.format(yesterday);
	
	 /***
     * 获得今日时间（字符串格式精确到日）
     * @return
     */
	Date now = new Date();
	String dateNowStr = simpleDateFormat.format(now);
	

}
