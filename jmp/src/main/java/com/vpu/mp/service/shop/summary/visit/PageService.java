package com.vpu.mp.service.shop.summary.visit;

import com.vpu.mp.db.shop.tables.records.MpVisitPageRecord;
import com.vpu.mp.service.pojo.shop.summary.PageVisitVo;
import com.vpu.mp.service.pojo.shop.summary.PageVisitVoItem;
import com.vpu.mp.service.pojo.shop.summary.VisitPageParam;
import org.jooq.Result;
import org.jooq.SortField;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.MpVisitPage.MP_VISIT_PAGE;

/**
 * 访问页面统计
 *
 * @author 郑保乐
 */
public class PageService extends BaseVisitService {

    public PageVisitVo getPageVisit(VisitPageParam param) {
        String startDate = param.getStartDate();
        String endDate = param.getEndDate();
        SortField<?> sortField = param.getSortField();
        Result<MpVisitPageRecord> result = getPageVisitResult(startDate, endDate, sortField);
        List<MpVisitPageRecord> records = result.into(MpVisitPageRecord.class);
        PageVisitVo vo = new PageVisitVo();
        List<PageVisitVoItem> items = records.parallelStream().map(r -> {
            PageVisitVoItem item = new PageVisitVoItem();
            item.setEntryPagePv(String.valueOf(r.getEntrypagePv()));
            item.setExitPagePv(String.valueOf(r.getExitpagePv()));
            item.setPagePath(r.getPagePath());
            item.setPageSharePv(String.valueOf(r.getPageSharePv()));
            item.setPageShareUv(String.valueOf(r.getPageShareUv()));
            item.setPageVisitPv(String.valueOf(r.getPageVisitPv()));
            item.setPageVisitUv(String.valueOf(r.getPageVisitUv()));
            // TODO 页面名称
            return item;
        }).collect(Collectors.toList());
        vo.setList(items);
        return vo;
    }

    private Result<MpVisitPageRecord> getPageVisitResult(
            String startDate, String endDate, SortField<?> orderBy) {
        return db().select(
                MP_VISIT_PAGE.PAGE_PATH,
                DSL.sum(MP_VISIT_PAGE.PAGE_VISIT_PV),
                DSL.sum(MP_VISIT_PAGE.PAGE_VISIT_UV),
                DSL.sum(MP_VISIT_PAGE.ENTRYPAGE_PV),
                DSL.sum(MP_VISIT_PAGE.EXITPAGE_PV),
                DSL.sum(MP_VISIT_PAGE.PAGE_SHARE_PV),
                DSL.sum(MP_VISIT_PAGE.PAGE_SHARE_UV),
                DSL.sum(MP_VISIT_PAGE.PAGE_STAYTIME_PV))
                .from(MP_VISIT_PAGE)
                .where(MP_VISIT_PAGE.REF_DATE.between(startDate).and(endDate))
                .groupBy(MP_VISIT_PAGE.REF_DATE, MP_VISIT_PAGE.PAGE_PATH)
                .orderBy(orderBy)
                .fetch().into(MP_VISIT_PAGE);
    }
}
