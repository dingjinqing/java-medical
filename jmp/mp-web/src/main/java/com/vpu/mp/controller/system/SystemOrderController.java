package com.vpu.mp.controller.system;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.service.pojo.saas.order.report.OrderBakSalesReportParam;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.OrderQueryVo;
import com.vpu.mp.service.pojo.shop.order.report.MedicalOrderReportVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 孔德成
 * @date 2020/8/20 16:11
 */
@RestController
public class SystemOrderController extends SystemBaseController {


    /**
     * 订单列表
     * @return
     */
    @PostMapping("/api/system/order/list")
    public JsonResult listOrder(@RequestBody OrderPageListQueryParam param){
        OrderQueryVo orderInfoPageList = saas.saasOrderService.getOrderInfoPageList(param);
        return success(orderInfoPageList);
    }

    /**
     * 医药销售报表
     */
    @PostMapping("/api/system/report/sales/order")
    public JsonResult medicalSalesReport(@RequestBody OrderBakSalesReportParam param) {
        return success(saas.saasOrderService.medicalSalesReport(param));
    }

    /**
     * 导出报表
     */
    @PostMapping("/api/system/report/sales/order/export")
    public void medicalSalesReportExport(@RequestBody OrderBakSalesReportParam param, HttpServletResponse response) {
        Workbook workbook = saas.saasOrderService.medicalSalesReportExport(param, getLang());
        String fileName = MedicalOrderReportVo.EXPORT_FILE_NAME + DateUtils.dateFormat(DateUtils.DATE_FORMAT_SHORT);
        export2Excel(workbook,fileName,response);
    }


}
