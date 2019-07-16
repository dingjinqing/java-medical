package com.vpu.mp.service.shop.overview;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisDateParam;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisSelectParam;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisSelectVo;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisYesterdayVo;

import static com.vpu.mp.db.shop.Tables.MP_DAILY_VISIT;
import static com.vpu.mp.db.shop.Tables.MP_SUMMARY_TREND;

import java.util.List;

import org.jooq.TableField;
import org.jooq.impl.DSL;


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
	public List<OverviewAnalysisSelectVo> getSelect(OverviewAnalysisSelectParam param) {
		
		TableField<?,?> numCondition = MP_DAILY_VISIT.SESSION_CNT;
		
		numCondition = param.getSessionCnt()!=null ? MP_DAILY_VISIT.SESSION_CNT: numCondition;
		numCondition = param.getVisitPv()!=null ? MP_DAILY_VISIT.VISIT_PV : numCondition;
		numCondition = param.getVisitUv()!=null ? MP_DAILY_VISIT.VISIT_UV: numCondition;
		numCondition = param.getSharePv()!=null ? MP_SUMMARY_TREND.SHARE_PV: numCondition;
		numCondition = param.getShareUv()!=null ? MP_SUMMARY_TREND.SHARE_UV: numCondition;
		numCondition = param.getVisitUvNew()!=null ? MP_DAILY_VISIT.VISIT_UV_NEW : numCondition;
		numCondition = param.getStayTimeUv()!=null ? MP_DAILY_VISIT.STAY_TIME_UV: numCondition;
		numCondition = param.getStayTimeSession()!=null ? MP_DAILY_VISIT.STAY_TIME_SESSION : numCondition;
		
		List<OverviewAnalysisSelectVo> overviewAnalysisSelectVos;
		if(param.getShareUv()!=null||param.getSharePv()!=null) {
			overviewAnalysisSelectVos = 
					db().select(MP_SUMMARY_TREND.REF_DATE,numCondition.as("num"))
						.from(MP_SUMMARY_TREND)
						.where(MP_SUMMARY_TREND.REF_DATE.between(param.getStartTime(), param.getEndTime()))
						.fetchInto(OverviewAnalysisSelectVo.class);
		} else if(param.getTotalSessionCnt()!=null){
			overviewAnalysisSelectVos = 
		//* 累计访问人数功能未实现 */
					db().select(DSL.sum(MP_DAILY_VISIT.SESSION_CNT).as("num"))
						.from(MP_DAILY_VISIT)
						.where(MP_DAILY_VISIT.REF_DATE.greaterOrEqual(param.getStartTime()))
						.fetchInto(OverviewAnalysisSelectVo.class);
			
		}else {
			overviewAnalysisSelectVos = 
					db().select(MP_DAILY_VISIT.REF_DATE,numCondition.as("num"))
						.from(MP_DAILY_VISIT)
						.where(MP_DAILY_VISIT.REF_DATE.between(param.getStartTime(), param.getEndTime()))
						.fetchInto(OverviewAnalysisSelectVo.class);
		}
		
		return overviewAnalysisSelectVos;
	}
	
	
}
