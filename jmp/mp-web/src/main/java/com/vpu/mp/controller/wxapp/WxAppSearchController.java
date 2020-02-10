package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.overview.hotwords.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 小程序-搜索历史相关
 * @author liangchen
 * @date 2020.02.10
 */
@RestController
@RequestMapping("/api/wxapp/search")
public class WxAppSearchController extends WxAppBaseController {
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

}
