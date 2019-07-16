package com.vpu.mp.service.shop.overview;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisDateParam;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisSelectParam;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisYesterdayVo;
import static com.vpu.mp.db.shop.Tables.MP_DAILY_VISIT;
import static com.vpu.mp.db.shop.Tables.MP_SUMMARY_TREND;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 概况统计
 * 
 * @author liangchen
 * @date 2019年7月15日
 */
public class OverviewAnalysisService extends BaseService {

	/**
	 * 查询昨日概况
	 * 
	 * @param param
	 * @return List<OverviewAnalysisYesterdayVo>
	 */
	
	public List<OverviewAnalysisYesterdayVo> yesterdayAnalysis(OverviewAnalysisDateParam param) {

		List<OverviewAnalysisYesterdayVo> overviewAnalysisYesterdayVos = db()
				.select(MP_DAILY_VISIT.SESSION_CNT, MP_DAILY_VISIT.VISIT_PV, MP_DAILY_VISIT.VISIT_UV,
						MP_DAILY_VISIT.VISIT_UV_NEW, MP_SUMMARY_TREND.SHARE_PV, MP_SUMMARY_TREND.SHARE_UV)
				.from(MP_DAILY_VISIT, MP_SUMMARY_TREND).where(MP_DAILY_VISIT.REF_DATE.eq(param.getDateNowStr()))
				.and(MP_SUMMARY_TREND.REF_DATE.eq(param.getDateYesterdayStr())).fetchInto(OverviewAnalysisYesterdayVo.class);

		return overviewAnalysisYesterdayVos;

	}
	
	/**
	 *折线图综合概况统计
	 *@Param param
	 *@return
	 */
	public void selectSessionCnt(OverviewAnalysisSelectParam param) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");                
	    Calendar c = Calendar.getInstance();           
	    c.add(Calendar.DATE, - 7);           
	    Date time = c.getTime();         
	    String preDay = sdf.format(time);  
	}
}
