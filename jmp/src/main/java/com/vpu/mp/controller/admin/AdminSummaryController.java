package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.summary.ChartXKeyYValue;
import com.vpu.mp.service.pojo.shop.summary.portrait.PortraitParam;
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
    public JsonResult getVisitDistribution(
            @Valid @RequestBody VisitDistributionParam param,
            @RequestHeader(value = HEAD_LANG, required = false) String lang) {
        String prefix = "source";
        VisitDistributionVo vo = shop().distribution.getVisitDistribution(param);
        List<VisitInfoItem> sessionCntVo = vo.getAccessSourceSessionCnt();
        sessionCntVo.forEach(i -> i.setName(translatePage(prefix, lang, i.getName(), i.getName())));
        vo.setAccessSourceSessionCnt(sessionCntVo);
        ChartXKeyYValue visitSourceVo = vo.getVisitSource();
        List<String> keys = visitSourceVo.getXAxis();
        List<String> realKeys = keys.stream().map(i -> translatePage(prefix, lang, i, i)).collect(Collectors.toList());
        visitSourceVo.setKeys(realKeys);
        vo.setVisitSource(visitSourceVo);
        return success(vo);
    }

    @PostMapping("/api/admin/summary/visit/retain")
    public JsonResult getVisitDistribution(@Valid @RequestBody VisitStatisticsParam param) {
        return success(shop().retain.getAccessRetain(param));
    }

    @PostMapping("/api/admin/summary/visit/page")
    public JsonResult getVisitPage(
            @Valid @RequestBody VisitPageParam param,
            @RequestHeader(value = HEAD_LANG, required = false) String lang) {
        PageVisitVo vo = shop().page.getPageVisit(param);
        List<PageVisitVoItem> list = vo.getList();
        list.forEach(i -> i.setPageName(translatePage("page", lang, i.getPageName(), i.getPageName())));
        vo.setList(list);
        return success(vo);
    }

    @PostMapping("/api/admin/summary/portrait/portrait")
    public JsonResult getUserPortrait(@Valid @RequestBody PortraitParam param) {
        return success(shop().portrait.getPortrait(param));
    }

    /**
     * 转换语言
     */
    private String translatePage(String prefix, String language, String message, String defaultMessage) {
        language = StringUtils.isBlank(language) ? "zh_CN" : language;
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setDefaultEncoding("UTF-8");
        source.setBasename("static/i18n/" + prefix);
        MessageSourceAccessor accessor = new MessageSourceAccessor(source);
        String[] languages = language.split("_");
        Locale locale = new Locale(languages[0], languages[1]);
        return accessor.getMessage(message, defaultMessage, locale);
    }
}

