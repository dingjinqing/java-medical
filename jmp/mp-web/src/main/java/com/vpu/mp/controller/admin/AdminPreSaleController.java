package com.vpu.mp.controller.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.market.presale.OrderListParam;
import com.vpu.mp.service.pojo.shop.market.presale.PreSaleListParam;
import com.vpu.mp.service.pojo.shop.market.presale.PreSaleParam;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;

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
    public JsonResult getOrderList(@RequestBody @Valid OrderListParam param) {
        return success(shop().preSaleOrder.getOrderList(param));
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
        String pagePath = shop().preSale.sharePreSale(id);
        String code = shop().qrCode.getMpQrCode(QrCodeTypeEnum.DOWN_PAYMENT_INFO, pagePath);
        ShareQrCodeVo vo = new ShareQrCodeVo();
        vo.setImageUrl(code);
        vo.setPagePath(pagePath);
        return success(vo);
    }

    /**
     * 导出活动订单
     */
    @PostMapping("/order/export")
    public void exportOrderExcel(
        @RequestBody @Valid OrderListParam param, HttpServletResponse response) throws IOException {
        Workbook workbook = shop().preSaleOrder.exportOrderList(param, getLang());
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        String fileName =
            Util.translateMessage(getLang(),
                JsonResultMessage.PRESALE_ORDER_EXCEL, LANGUAGE_TYPE_EXCEL) + DateUtil.getLocalDateTime().toString();
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
        workbook.write(response.getOutputStream());
    }
}
