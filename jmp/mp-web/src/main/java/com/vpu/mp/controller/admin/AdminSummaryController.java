package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.summary.portrait.PortraitParam;
import com.vpu.mp.service.pojo.shop.summary.portrait.ProvinceParam;
import com.vpu.mp.service.pojo.shop.summary.visit.VisitDistributionParam;
import com.vpu.mp.service.pojo.shop.summary.visit.VisitPageParam;
import com.vpu.mp.service.pojo.shop.summary.visit.VisitStatisticsParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 概况
 *
 * @author 郑保乐
 * @date 2019-07-11
 */
@RestController
public class AdminSummaryController extends AdminBaseController {

    @GetMapping("/api/admin/summary/test")
    public void testAddDailyVisit() {
        shop().amount.addTestDailyVisit();
    }

    @PostMapping("/api/admin/summary/visit/amount")
    public JsonResult getVisitStatistics(@Valid @RequestBody VisitStatisticsParam param) {
        return success(shop().amount.getVisitStatistics(param));
    }

    @PostMapping("/api/admin/summary/visit/distribution")
    public JsonResult getVisitDistribution(@Valid @RequestBody VisitDistributionParam param) {
        return i18nSuccess(shop().distribution.getVisitDistribution(param));
    }

    @PostMapping("/api/admin/summary/source/distribution")
    public JsonResult getSourceAnalysis(@Valid @RequestBody VisitDistributionParam param) {
        return success(shop().distribution.getSourceAnalysis(param));
    }

    @GetMapping("/api/admin/summary/source/selectoption")
    public JsonResult getSelectOption() {
        return i18nSuccess(shop().distribution.getSelectOption());
    }

    @PostMapping("/api/admin/summary/visit/retain")
    public JsonResult getVisitDistribution(@Valid @RequestBody VisitStatisticsParam param) {
        return success(shop().retain.getAccessRetain(param));
    }

    @PostMapping("/api/admin/summary/visit/page")
    public JsonResult getVisitPage(@Valid @RequestBody VisitPageParam param) {
        return i18nSuccess(shop().page.getPageVisit(param));
    }

    @PostMapping("/api/admin/summary/portrait/portrait")
    public JsonResult getUserPortrait(@Valid @RequestBody PortraitParam param) {
        return success(shop().portrait.getPortrait(param));
    }

    @PostMapping("/api/admin/summary/portrait/province")
    public JsonResult getProvincePortrait(@Valid @RequestBody ProvinceParam param) {
        return success(shop().portrait.getProvincePortrait(param));
    }
}

