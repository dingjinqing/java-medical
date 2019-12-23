package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.overview.hotwords.*;
import com.vpu.mp.service.pojo.shop.overview.searchanalysis.SearchHistoryParam;
import com.vpu.mp.service.pojo.shop.overview.searchanalysis.SearchHistoryVo;
import com.vpu.mp.service.pojo.shop.overview.searchanalysis.SearchHotWordsVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 搜索统计控制器
 * 
 * @author liangchen
 * @date  2019年7月15日
 */

@RestController
@RequestMapping("/api/admin/overview/search/analysis")
public class AdminOverviewSearchAnalysisController extends AdminBaseController{
	/**
	 *搜索历史统计
	 *@param param 查询时间段
	 *@return 10条搜索历史统计信息
	 */
	@PostMapping("/history")
	public JsonResult getSearchHistory(@RequestBody SearchHistoryParam param) {

		SearchHistoryVo searchHistoryVos = shop().overview.searchAnalysisService.getSearchHistory(param);
		
		return success(searchHistoryVos);
	}

	/**
	 *搜索热词统计
	 *@param param 查询时间段
	 *@return 10条搜索热词统计信息
	 */
	@PostMapping("/hot")
	public JsonResult getHotSearchHistory(@RequestBody SearchHistoryParam param) {
		SearchHotWordsVo searchHotWordsVos = shop().overview.searchAnalysisService.getHotSearchHistory(param);
		
		return success(searchHotWordsVos);
	}

    /**
     * 获得用户搜索热词
     * @param param 用户id和热词数量
     * @return 用户最新搜索的热词
     */
    @PostMapping("/userSearchHot")
    public JsonResult getUserSearchHots(@RequestBody UserIdAndNum param) {
        List<HotWords> result = shop().overview.hotWordsService.getUserSearchHots(param);

        return success(result);
    }

    /**
     * 添加热词
     * @param param 用户id和热词
     */
    @PostMapping("/addHotWords")
    public JsonResult addHotWords(@RequestBody UserIdAndWords param) {
        shop().overview.hotWordsService.addHotWords(param);

        return success();
    }

    /**
     * 热词排行榜（从高到低）
     * @param param 查找日期及是否热词标识
     * @return 热词及对应搜索次数
     */
    @PostMapping("/historyTop")
    public JsonResult getHistoryTop(@RequestBody DateAndWordsFlag param) {
        List<WordsAndCount> result = shop().overview.hotWordsService.getHistoryTop(param);

        return success(result);
    }

    /**
     * 清除用户搜索词
     * @param param 用户id
     */
    @PostMapping("/clearWords")
    public JsonResult clearUserHotWords(@RequestBody UserId param) {
        shop().overview.hotWordsService.clearUserHotWords(param);

        return success();
    }

    /**
     * 获取用户最近一次搜索的热词
     * @param param 用户id
     * @return 最近搜索热词
     */
    @PostMapping("/lastSearch")
    public JsonResult getUserLastSearch(@RequestBody UserId param) {
        HotWords result = shop().overview.hotWordsService.getUserLastSearch(param);

        return success(result);
    }
}
