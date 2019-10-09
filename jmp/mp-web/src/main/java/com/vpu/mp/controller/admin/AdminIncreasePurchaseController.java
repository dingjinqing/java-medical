package com.vpu.mp.controller.admin;

import static com.vpu.mp.service.foundation.excel.AbstractExcelDisposer.LANGUAGE_TYPE_EXCEL;

import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.increasepurchase.AddPurchaseParam;
import com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseDetailParam;
import com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseShowParam;
import com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseStatusParam;
import com.vpu.mp.service.pojo.shop.market.increasepurchase.RedemptionDetailParam;
import com.vpu.mp.service.pojo.shop.market.increasepurchase.RedemptionOrderParam;
import com.vpu.mp.service.pojo.shop.market.increasepurchase.UpdatePriorityParam;
import com.vpu.mp.service.pojo.shop.market.increasepurchase.UpdatePurchaseParam;

/**
 * @author liufei
 * @date 2019/8/14
 * @description
 */
@RestController
public class AdminIncreasePurchaseController extends AdminBaseController {
    /**
     * 加价购分页条件查询
     */
    @PostMapping("/api/admin/market/increasepurchase/selectbypage")
    public JsonResult selectByPage(@RequestBody @Validated PurchaseShowParam param) {
        return success(shop().increaseService.selectByPage(param));
    }


    /**
     * 添加加价购活动
     *
     * @param param 加价购活动详情参数
     */
    @PostMapping("/api/admin/market/increasepurchase/addincreasepurchase")
    public JsonResult addIncreasePurchase(@RequestBody @Validated AddPurchaseParam param) {
        shop().increaseService.addIncreasePurchase(param);
        return success();
    }

    /**
     * 更新加价购活动
     *
     * @param param 加价购活动详情参数
     */
    @PostMapping("/api/admin/market/increasepurchase/updateincreasepurchase")
    public JsonResult updateIncreasePurchase(@RequestBody @Validated UpdatePurchaseParam param) {
        shop().increaseService.updateIncreasePurchase(param);
        return success();
    }

    /**
     * 获取加价购活动详细信息
     *
     * @param param 加价购活动id
     */
    @PostMapping("/api/admin/market/increasepurchase/getpurchasedetail")
    public JsonResult getPurchaseDetail(@RequestBody @Validated PurchaseDetailParam param) {
        return success(shop().increaseService.getPurchaseDetail(param));
    }

    /**
     * 停用/启用/删除加价购活动
     *
     * @param param 活动id和被修改的状态值
     */
    @PostMapping("/api/admin/market/increasepurchase/changethestatus")
    public JsonResult changeTheStatus(@RequestBody @Validated PurchaseStatusParam param) {
        shop().increaseService.changeTheStatus(param);
        return success();
    }

    /**
     * 分享,获取小程序二维码
     */
    @PostMapping("/api/admin/market/increasepurchase/share")
    public JsonResult share(@RequestBody @Validated PurchaseStatusParam param) {
        return success(shop().increaseService.share(param));
    }

    /**
     * 查看换购订单列表
     *
     * @param param 加价购活动id和筛选条件
     * @return 分页数据
     */
    @PostMapping("/api/admin/market/increasepurchase/getredemptionorderlist")
    public JsonResult getRedemptionOrderList(@RequestBody @Validated RedemptionOrderParam param) {
        return success(shop().increaseService.getRedemptionOrderList(param));
    }

    /**
     * 查看换购明细
     *
     * @param param 加价购活动id和筛选条件
     * @return 分页数据
     */
    @PostMapping("/api/admin/market/increasepurchase/getredemptiondetail")
    public JsonResult getRedemptionDetail(@RequestBody @Validated RedemptionDetailParam param) {
        return success(shop().increaseService.getRedemptionDetail(param));
    }

    /**
     * 换购订单列表导出
     *
     * @param param 筛选条件
     */
    @PostMapping("/api/admin/market/increasepurchase/exportorderlist")
    public void exportOrderList(@RequestBody RedemptionOrderParam param, HttpServletResponse response) {
        String fileName = Util.translateMessage(getLang(), JsonResultMessage.REDEMPTION_ORDER_EXCEL,LANGUAGE_TYPE_EXCEL) + DateUtil.getLocalDateTime().toString();
        export2Excel(shop().increaseService.exportOrderList(param),response,fileName);
    }
    /**
     * 换购明细导出
     *
     * @param param 筛选条件
     */
    @PostMapping("/api/admin/market/increasepurchase/exportorderdetail")
    public void exportOrderDetail(@RequestBody RedemptionDetailParam param, HttpServletResponse response) {
        String fileName = Util.translateMessage(getLang(), JsonResultMessage.REDEMPTION_DETAIL_EXCEL,LANGUAGE_TYPE_EXCEL) + DateUtil.getLocalDateTime().toString();
        export2Excel(shop().increaseService.exportOrderDetail(param),response,fileName);
    }

    /**
     * 活动优先级修改
     */
    @PostMapping("/api/admin/market/increasepurchase/updatepriority")
    public JsonResult updatePriority(@RequestBody UpdatePriorityParam param) {
        shop().increaseService.updatePriority(param);
        return success();
    }
}
