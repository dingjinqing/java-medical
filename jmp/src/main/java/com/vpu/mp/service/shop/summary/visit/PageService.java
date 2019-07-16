package com.vpu.mp.service.shop.summary.visit;

import com.vpu.mp.db.shop.tables.records.MpVisitPageRecord;
import com.vpu.mp.service.pojo.shop.summary.PageVisitVo;
import com.vpu.mp.service.pojo.shop.summary.VisitDistributionParam;
import com.vpu.mp.service.pojo.shop.summary.VisitPageParam;
import org.jooq.Result;
import org.jooq.impl.DSL;

import javax.validation.constraints.NotBlank;

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
        Result<MpVisitPageRecord> result = getPageVisitResult(startDate, endDate);
        return null;
    }

    private Result<MpVisitPageRecord> getPageVisitResult(String startDate, String endDate) {
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
                .fetch().into(MP_VISIT_PAGE);
    }
}
