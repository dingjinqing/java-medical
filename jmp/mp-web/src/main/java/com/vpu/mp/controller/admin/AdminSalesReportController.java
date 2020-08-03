package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.report.MedicalSalesReportParam;
import com.vpu.mp.service.shop.report.MedicalSalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 销售报表
 * @author 孔德成
 * @date 2020/7/31 14:55
 */
@RestController
public class AdminSalesReportController extends AdminBaseController{

    @Autowired
    private MedicalSalesReportService medicalSalesReportService;

    /**
     * 医药销售报表
     */
    @PostMapping("/api/admin/report/sales/medical")
    public JsonResult medicalSalesReport(@RequestBody MedicalSalesReportParam param) {
        return success(medicalSalesReportService.medicalSalesReport(param));
    }
}
