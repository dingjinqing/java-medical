package com.vpu.mp.service.shop.overview;

import java.sql.Timestamp;
import java.util.List;

import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.overview.searchanalysis.OverviewSearchAnalysisHistoryParam;
import com.vpu.mp.service.pojo.shop.overview.searchanalysis.OverviewSearchAnalysisHistoryVo;
import com.vpu.mp.service.pojo.shop.overview.searchanalysis.OverviewSearchAnalysisHotVo;

import static com.vpu.mp.db.shop.Tables.SEARCH_HISTORY;
/**
 * 搜索统计
 * 
 * @author liangchen
 * @date 2019年7月23日
 */
@Service
public class OverviewSearchAnalysisService extends ShopBaseService {
	
	/**
	 * 搜索历史统计
	 * 
	 * @param param
	 * @return List<OverviewSearchAnalysisHistoryVo>
	 */
	public List<OverviewSearchAnalysisHistoryVo> getSearchHistory(OverviewSearchAnalysisHistoryParam param) {
		
		String startTime = param.getStartTime();
		String endTime = param.getEndTime();
		List<OverviewSearchAnalysisHistoryVo> overviewSearchAnalysisHistoryVos = db()
				.select(SEARCH_HISTORY.HOT_WORDS,
						DSL.sum(SEARCH_HISTORY.SEARCH_COUNT).as("searchCount"))
				.from(SEARCH_HISTORY)
				.where(SEARCH_HISTORY.CREATE_TIME.between(Timestamp.valueOf(startTime), Timestamp.valueOf(endTime)))
				.and(SEARCH_HISTORY.IS_HOT_WORDS.eq((byte)0))
				.groupBy(SEARCH_HISTORY.HOT_WORDS)
				.orderBy(DSL.sum(SEARCH_HISTORY.SEARCH_COUNT).as("searchCount").desc())
				.fetchInto(OverviewSearchAnalysisHistoryVo.class);
		
		return overviewSearchAnalysisHistoryVos;
				
	}
	/**
	 * 搜索热词统计
	 * 
	 * @param param
	 * @return List<OverviewSearchAnalysisHotVo>
	 */
	public List<OverviewSearchAnalysisHotVo> getHotSearchHistory(OverviewSearchAnalysisHistoryParam param) {
		
		String startTime = param.getStartTime();
		String endTime = param.getEndTime();
		List<OverviewSearchAnalysisHotVo> overviewSearchAnalysisHotVos = db()
				.select(SEARCH_HISTORY.HOT_WORDS,
						DSL.sum(SEARCH_HISTORY.SEARCH_COUNT).as("searchCount"))
				.from(SEARCH_HISTORY)
				.where(SEARCH_HISTORY.CREATE_TIME.between(Timestamp.valueOf(startTime), Timestamp.valueOf(endTime)))
				.and(SEARCH_HISTORY.IS_HOT_WORDS.eq((byte)1))
				.groupBy(SEARCH_HISTORY.HOT_WORDS)
				.orderBy(DSL.sum(SEARCH_HISTORY.SEARCH_COUNT).as("searchCount").desc())
				.fetchInto(OverviewSearchAnalysisHotVo.class);
		
		return overviewSearchAnalysisHotVos;
				
	}
}
