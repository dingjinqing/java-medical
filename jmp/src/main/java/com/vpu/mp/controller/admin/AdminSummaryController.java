package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.summary.portrait.PortraitParam;
import com.vpu.mp.service.pojo.shop.summary.visit.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * 概况
 *
 * @author 郑保乐
 * @date 2019-07-11
 */
@RestController
public class AdminSummaryController extends AdminBaseController {

    private static final String HEAD_LANG = "V-Lang";

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
        return success(shop().distribution.getVisitDistribution(param));
    }

    @PostMapping("/api/admin/summary/visit/retain")
    public JsonResult getVisitDistribution(@Valid @RequestBody VisitStatisticsParam param) {
        return success(shop().retain.getAccessRetain(param));
    }

    @PostMapping("/api/admin/summary/visit/page")
    public JsonResult getVisitPage(@Valid @RequestBody VisitPageParam param, @RequestHeader(HEAD_LANG) String lang) {
        PageVisitVo vo = shop().page.getPageVisit(param);
        List<PageVisitVoItem> list = vo.getList();
        list.forEach(i-> i.setPageName(translatePage(lang, i.getPageName(), i.getPageName())));
        return success();
    }

    @PostMapping("/api/admin/summary/portrait/portrait")
    public JsonResult getUserPortrait(@Valid @RequestBody PortraitParam param) {
        return success(shop().portrait.getPortrait(param));
    }

    /**
     * 转换语言
     */
    private String translatePage(String language, String message, String defaultMessage) {
        language = StringUtils.isBlank(language) ? "zh_CN" : language;
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("static/i18n/page");
        source.setDefaultEncoding("UTF-8");
        MessageSourceAccessor accessor = new MessageSourceAccessor(source);
        String[] languages = language.split("_");
        Locale locale = new Locale(languages[0], languages[1]);
        return accessor.getMessage(message, defaultMessage, locale);
    }
}

