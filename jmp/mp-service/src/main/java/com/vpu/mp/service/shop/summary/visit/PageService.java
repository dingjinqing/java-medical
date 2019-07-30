package com.vpu.mp.service.shop.summary.visit;

import com.vpu.mp.db.shop.tables.records.MpVisitPageRecord;
import com.vpu.mp.service.foundation.util.PropertiesUtil;
import com.vpu.mp.service.pojo.shop.summary.visit.PageVisitVo;
import com.vpu.mp.service.pojo.shop.summary.visit.PageVisitVoItem;
import com.vpu.mp.service.pojo.shop.summary.visit.VisitPageParam;
import org.jooq.Result;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.vpu.mp.db.shop.tables.MpVisitPage.MP_VISIT_PAGE;

/**
 * 访问页面统计
 *
 * @author 郑保乐
 */
@Service
public class PageService extends BaseVisitService {

    private static final String PAGE_OTHER = "page.other";

    public PageVisitVo getPageVisit(VisitPageParam param) {
        String startDate = param.getStartDate();
        String endDate = param.getEndDate();
        SortField<?> sortField = param.getSortField();
        Result<MpVisitPageRecord> result = getPageVisitResult(startDate, endDate, sortField);
        Result<MpVisitPageRecord> records = result.into(MP_VISIT_PAGE);
        PageVisitVo vo = new PageVisitVo();
        List<PageVisitVoItem> items = records.map(r -> {
            PageVisitVoItem item = new PageVisitVoItem();
            item.setPagePath(r.getPagePath());
            item.setEntryPagePv(String.valueOf(r.getEntrypagePv()));
            item.setExitPagePv(String.valueOf(r.getExitpagePv()));
            item.setPageSharePv(String.valueOf(r.getPageSharePv()));
            item.setPageShareUv(String.valueOf(r.getPageShareUv()));
            item.setPageVisitPv(String.valueOf(r.getPageVisitPv()));
            item.setPageVisitUv(String.valueOf(r.getPageVisitUv()));
            item.setPageStayTimePv(r.getPageStaytimePv());
            item.setPageName(pageNameOf(r.getPagePath()));
            return item;
        });
        vo.setList(items);
        return vo;
    }

    private Result<MpVisitPageRecord> getPageVisitResult(
            String startDate, String endDate, SortField<?> orderBy) {
        return db().select(
                MP_VISIT_PAGE.PAGE_PATH,
                DSL.sum(MP_VISIT_PAGE.PAGE_VISIT_PV).as(MP_VISIT_PAGE.PAGE_VISIT_PV),
                DSL.sum(MP_VISIT_PAGE.PAGE_VISIT_UV).as(MP_VISIT_PAGE.PAGE_VISIT_UV),
                DSL.sum(MP_VISIT_PAGE.ENTRYPAGE_PV).as(MP_VISIT_PAGE.ENTRYPAGE_PV),
                DSL.sum(MP_VISIT_PAGE.EXITPAGE_PV).as(MP_VISIT_PAGE.EXITPAGE_PV),
                DSL.sum(MP_VISIT_PAGE.PAGE_SHARE_PV).as(MP_VISIT_PAGE.PAGE_SHARE_PV),
                DSL.sum(MP_VISIT_PAGE.PAGE_SHARE_UV).as(MP_VISIT_PAGE.PAGE_SHARE_UV),
                DSL.sum(MP_VISIT_PAGE.PAGE_STAYTIME_PV).as(MP_VISIT_PAGE.PAGE_STAYTIME_PV))
                .from(MP_VISIT_PAGE)
                .where(MP_VISIT_PAGE.REF_DATE.between(startDate).and(endDate))
                .groupBy(MP_VISIT_PAGE.PAGE_PATH)
                .orderBy(orderBy)
                .fetch().into(MP_VISIT_PAGE);
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
