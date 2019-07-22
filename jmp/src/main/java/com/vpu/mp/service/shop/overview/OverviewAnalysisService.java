package com.vpu.mp.service.shop.overview;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PropertiesUtil;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisDateParam;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisPageParam;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisPageVo;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisSelectParam;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisSelectVo;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisYesterdayVo;

import static com.vpu.mp.db.shop.Tables.MP_DAILY_VISIT;
import static com.vpu.mp.db.shop.Tables.MP_SUMMARY_TREND;
import static com.vpu.mp.db.shop.Tables.MP_VISIT_PAGE;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


/**
 * 概况统计
 * 
 * @author liangchen
 * @date 2019年7月15日
 */
@Service

public class OverviewAnalysisService extends ShopBaseService {

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
				.from(MP_DAILY_VISIT, MP_SUMMARY_TREND).where(MP_DAILY_VISIT.REF_DATE.eq(param.getDateYesterdayStr()))
				.and(MP_SUMMARY_TREND.REF_DATE.eq(param.getDateYesterdayStr())).fetchInto(OverviewAnalysisYesterdayVo.class);

		return overviewAnalysisYesterdayVos;

	}
	
	/**
	 *折线图综合概况统计
	 *@Param param
	 *@return
	 */
	public List<OverviewAnalysisSelectVo> getSelect(OverviewAnalysisSelectParam param) {
		
		TableField<?,?> numCondition = MP_SUMMARY_TREND.VISIT_TOTAL;
		
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
						.orderBy(MP_SUMMARY_TREND.REF_DATE.asc())
						.fetchInto(OverviewAnalysisSelectVo.class);
			
		}else if (param.getSessionCnt()!=null||param.getVisitPv()!=null ||param.getVisitUv()!=null 
				||param.getVisitUvNew()!=null||param.getStayTimeUv()!=null||param.getStayTimeSession()!=null) {
			overviewAnalysisSelectVos = 
					db().select(MP_DAILY_VISIT.REF_DATE,numCondition.as("num"))
						.from(MP_DAILY_VISIT)
						.where(MP_DAILY_VISIT.REF_DATE.between(param.getStartTime(), param.getEndTime()))
						.orderBy(MP_DAILY_VISIT.REF_DATE.asc())
						.fetchInto(OverviewAnalysisSelectVo.class);
		}else {
			overviewAnalysisSelectVos = 
					db().select(MP_SUMMARY_TREND.REF_DATE,numCondition.as("num"))
						.from(MP_SUMMARY_TREND)
						.where(MP_SUMMARY_TREND.REF_DATE.greaterOrEqual(param.getStartTime()))
						.orderBy(MP_SUMMARY_TREND.REF_DATE.asc())
						.fetchInto(OverviewAnalysisSelectVo.class);
		}
		
		return overviewAnalysisSelectVos;
	}
	
	/**
	 *页面访问次数统计
	 *@Param param
	 *@return
	 */
	private static final String PAGE_OTHER = "page.other";
	public List<OverviewAnalysisPageVo> getPageInfo(OverviewAnalysisPageParam param) {
		
		List<OverviewAnalysisPageVo> overviewAnalysisPageVos;
		overviewAnalysisPageVos = 
				db().select(MP_VISIT_PAGE.PAGE_PATH,
						DSL.sum(MP_VISIT_PAGE.PAGE_VISIT_PV).as("pageVisitPv"))
				.from(MP_VISIT_PAGE)
				.where(MP_VISIT_PAGE.REF_DATE.between(param.getStartTime(), param.getEndTime()))
				.groupBy(MP_VISIT_PAGE.PAGE_PATH)
				.fetchInto(OverviewAnalysisPageVo.class);
		int total = 
				db().select(
						DSL.sum(MP_VISIT_PAGE.PAGE_VISIT_PV).as("total"))
				.from(MP_VISIT_PAGE)
				.where(MP_VISIT_PAGE.REF_DATE.between(param.getStartTime(), param.getEndTime()))
				.fetchInto(Integer.class).get(0);
		for(OverviewAnalysisPageVo overviewAnalysisPageVo:overviewAnalysisPageVos) {
			overviewAnalysisPageVo.setPageName(pageNameOf(overviewAnalysisPageVo.getPagePath()));
			overviewAnalysisPageVo.setRate(((double)overviewAnalysisPageVo.getPageVisitPv()/(double)total));
		}
		System.out.println("*********************************************");
		overviewAnalysisPageVos.forEach((e) -> System.out.println(e));
		return overviewAnalysisPageVos;
	}

	
	 /**
     * 获取路径对应的页面名称
     */
    private String pageNameOf(String pagePath) {
        return Optional.ofNullable(pageMap().get(pagePath)).orElse(PAGE_OTHER);
    }

    /**
     * 路径和页面名称对应关系
     */
    private Map<String, String> pageMap() {
        return PropertiesUtil.toMap("visit/pages.properties");
    }
}
