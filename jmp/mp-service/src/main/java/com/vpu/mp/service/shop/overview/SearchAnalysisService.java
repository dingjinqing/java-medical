package com.vpu.mp.service.shop.overview;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.overview.searchanalysis.SearchHistoryParam;
import com.vpu.mp.service.pojo.shop.overview.searchanalysis.SearchHistoryVo;
import com.vpu.mp.service.pojo.shop.overview.searchanalysis.SearchHotWordsVo;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.SEARCH_HISTORY;
/**
 * 搜索统计
 * 
 * @author liangchen
 * @date 2019年7月23日
 */
@Service
public class SearchAnalysisService extends ShopBaseService {
	
	/**
	 * 搜索历史统计
	 * 
	 * @param param
	 * @return List<OverviewSearchAnalysisHistoryVo>
	 */
	public List<SearchHistoryVo> getSearchHistory(SearchHistoryParam param) {
		
		String startTime = param.getStartTime();
		String endTime = param.getEndTime();
		List<SearchHistoryVo> searchHistoryVos =
            db().select(SEARCH_HISTORY.HOT_WORDS,
						DSL.sum(SEARCH_HISTORY.SEARCH_COUNT).as("searchCount"))
				.from(SEARCH_HISTORY)
				.where(SEARCH_HISTORY.CREATE_TIME.between(Timestamp.valueOf(startTime), Timestamp.valueOf(endTime)))
				.and(SEARCH_HISTORY.IS_HOT_WORDS.eq((byte)0))
				.groupBy(SEARCH_HISTORY.HOT_WORDS)
				.orderBy(DSL.sum(SEARCH_HISTORY.SEARCH_COUNT).as("searchCount").desc())
				.fetchInto(SearchHistoryVo.class);
		
		return searchHistoryVos;
				
	}
	/**
	 * 搜索热词统计
	 * 
	 * @param param
	 * @return List<OverviewSearchAnalysisHotVo>
	 */
	public List<SearchHotWordsVo> getHotSearchHistory(SearchHistoryParam param) {
		
		String startTime = param.getStartTime();
		String endTime = param.getEndTime();
		List<SearchHotWordsVo> searchHotWordsVos = db().select(SEARCH_HISTORY.HOT_WORDS,
						DSL.sum(SEARCH_HISTORY.SEARCH_COUNT).as("searchCount"))
				.from(SEARCH_HISTORY)
				.where(SEARCH_HISTORY.CREATE_TIME.between(Timestamp.valueOf(startTime), Timestamp.valueOf(endTime)))
				.and(SEARCH_HISTORY.IS_HOT_WORDS.eq((byte)1))
				.groupBy(SEARCH_HISTORY.HOT_WORDS)
				.orderBy(DSL.sum(SEARCH_HISTORY.SEARCH_COUNT).as("searchCount").desc())
				.fetchInto(SearchHotWordsVo.class);
		
		return searchHotWordsVos;
				
	}
}
