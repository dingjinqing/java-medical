package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.form.FormDetailParam;
import com.vpu.mp.service.pojo.shop.market.increasepurchase.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/admin/market/increasepurchase/selectbypage")
    public JsonResult selectByPage(@RequestBody PurchaseShowParam param) {
        return success(shop().increaseService.selectByPage(param));
    }


    /**
     * 添加加价购活动
     *
     * @param param 加价购活动详情参数
     */
    @PostMapping("/admin/market/increasepurchase/addincreasepurchase")
    public JsonResult addIncreasePurchase(@RequestBody AddPurchaseParam param) {
        shop().increaseService.addIncreasePurchase(param);
        return success();
    }

    /**
     * 更新加价购活动
     *
     * @param param 加价购活动详情参数
     */
    @PostMapping("/admin/market/increasepurchase/updateincreasepurchase")
    public JsonResult updateIncreasePurchase(@RequestBody UpdatePurchaseParam param) {
        shop().increaseService.updateIncreasePurchase(param);
        return success();
    }

    /**
     * 获取加价购活动详细信息
     *
     * @param param 加价购活动id
     */
    @PostMapping("/admin/market/increasepurchase/getpurchasedetail")
    public JsonResult getPurchaseDetail(@RequestBody PurchaseDetailParam param) {
        return success(shop().increaseService.getPurchaseDetail(param));
    }

    /**
     * 停用/启用加价购活动
     *
     * @param param 活动id和被修改的状态值
     */
    @PostMapping("/admin/market/increasepurchase/changethestatus")
    public JsonResult changeTheStatus(@RequestBody PurchaseStatusParam param) {
        shop().increaseService.changeTheStatus(param);
        return success();
    }

    /**
     * 分享,获取小程序二维码
     */
    @PostMapping("/api/admin/formstatistics/shareForm")
    public JsonResult shareForm(@RequestBody @Validated FormDetailParam param) {
        return success(shop().formService.shareForm(param));
    }
}
