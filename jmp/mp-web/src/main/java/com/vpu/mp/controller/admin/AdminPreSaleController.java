package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.presale.OrderListParam;
import com.vpu.mp.service.pojo.shop.market.presale.PreSaleListParam;
import com.vpu.mp.service.pojo.shop.market.presale.PreSaleParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 定金膨胀
 *
 * @author 郑保乐
 */
@RestController
@RequestMapping("/api/admin/market/pre_sale")
public class AdminPreSaleController extends AdminBaseController {

    private static final String LANGUAGE_TYPE_EXCEL = "excel";

    /**
     * 活动列表
     */
    @PostMapping("/list")
    public JsonResult getPageList(@RequestBody PreSaleListParam param) {
        return success(shop().preSale.getPageList(param));
    }

    /**
     * 删除活动
     */
    @PostMapping("/delete/{id}")
    public JsonResult deletePreSale(@PathVariable Integer id) {
        shop().preSale.deletePreSale(id);
        return success();
    }

    /**
     * 禁用活动
     */
    @PostMapping("/disable/{id}")
    public JsonResult disablePreSale(@PathVariable Integer id) {
        shop().preSale.disablePreSale(id);
        return success();
    }

    /**
     * 启用活动
     */
    @PostMapping("/enable/{id}")
    public JsonResult enablePreSale(@PathVariable Integer id) {
        shop().preSale.enablePreSale(id);
        return success();
    }

    /**
     * 活动订单列表
     */
    @PostMapping("/order")
    public JsonResult getOrderList(@RequestBody @Valid MarketOrderListParam param) {
        return success(shop().preSaleOrder.getOrderList(param));
    }

    /**
     * 活动订单
     * 取将要导出的行数
     */
    @PostMapping("/order/export/rows")
    public JsonResult getActivityOrderExportTotalRows(@RequestBody @Valid MarketOrderListParam param) {
        return success(shop().readOrder.marketOrderInfo.getMarketOrderListSize(param, BaseConstant.ACTIVITY_TYPE_PRE_SALE));
    }

    /**
     * 活动订单
     * 订单导出
     */
    @PostMapping("/order/export")
    public void activityOrderExport(@RequestBody @Valid MarketOrderListParam param, HttpServletResponse response) {
        Workbook workbook =shop().preSaleOrder.exportOrderList(param,getLang());
        String fileName = Util.translateMessage(getLang(), JsonResultMessage.PRESALE_ORDER_LIST_FILENAME , OrderConstant.LANGUAGE_TYPE_EXCEL,OrderConstant.LANGUAGE_TYPE_EXCEL) + DateUtil.dateFormat(DateUtil.DATE_FORMAT_SHORT);
        export2Excel(workbook,fileName,response);
    }

    /**
     * 活动明细
     */
    @PostMapping("/detail")
    public JsonResult getDetail(@RequestBody @Valid OrderListParam param) {
        return success(shop().preSaleOrder.getPreSaleDetail(param));
    }

    /**
     * 创建活动
     */
    @PostMapping("/add")
    public JsonResult addPreSale(@RequestBody @Valid PreSaleParam param) {
        shop().preSale.addPreSale(param);
        return success();
    }

    /**
     * 编辑活动 - 查询明细
     */
    @PostMapping("/detail/{id}")
    public JsonResult getPreSale(@PathVariable Integer id) {
        return success(shop().preSale.getDetail(id));
    }

    /**
     * 编辑活动 - 更新
     */
    @PostMapping("/update")
    public JsonResult updatePreSale(@RequestBody @Valid PreSaleParam param) {
        shop().preSale.updatePreSale(param);
        return success();
    }

    /**
     * 活动分享
     */
    @PostMapping("/share/{id}")
    public JsonResult sharePreSale(@PathVariable Integer id) {
        return success(shop().preSale.getMpQrCode(id));
    }
}
