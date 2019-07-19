package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.summary.KeyValueChart;
import com.vpu.mp.service.pojo.shop.summary.portrait.PortraitParam;
import com.vpu.mp.service.pojo.shop.summary.portrait.ProvinceParam;
import com.vpu.mp.service.pojo.shop.summary.visit.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

