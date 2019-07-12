package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.summary.VisitStatisticsParam;
import com.vpu.mp.service.pojo.shop.summary.VisitStatisticsVo;
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
        VisitStatisticsVo vo = shop().amount.getVisitStatistics(param);
        return success(vo);
    }
}
