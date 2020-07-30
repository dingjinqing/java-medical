package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionListParam;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionNoParam;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionOneParam;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionParam;
import com.vpu.mp.service.pojo.wxapp.cart.WxAppAddGoodsToCartParam;
import com.vpu.mp.service.pojo.wxapp.cart.WxAppBatchAddGoodsToCartParam;
import com.vpu.mp.service.pojo.wxapp.cart.WxAppCartGoodsResultVo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.shop.prescription.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 处方信息
 * @author 孔德成
 * @date 2020/7/7 15:32
 */
@RestController
@RequestMapping("/api/wxapp/prescription")
public class WxAppPrescriptionController extends WxAppBaseController  {

    @Autowired
    private PrescriptionService prescriptionService;

    /**
     * 获取处方类表
     */
    @PostMapping("/list")
    public JsonResult listPageResultWx(@RequestBody @Validated PrescriptionListParam param){
        Integer userId = wxAppAuth.user().getUserId();
        param.setUserId(userId);
        return success(prescriptionService.listPageResultWx(param));
    }

    /**
     * 处方详情
     * @return
     */
    @PostMapping("/details")
    public JsonResult getPrescriptionDetails(@RequestBody @Validated PrescriptionNoParam param){
        return success(prescriptionService.getInfoByPrescriptionNo(param.getPrescriptionCode()));
    }

    /**
     * 生成处方
     * @return
     */
    @PostMapping("/add")
    public JsonResult insertPrescription(@RequestBody PrescriptionOneParam prescriptionParam){
        Integer userId = wxAppAuth.user().getUserId();
        prescriptionParam.setUserId(userId);
        PrescriptionParam result=prescriptionService.insertPrescription(prescriptionParam);
        return success(result);
    }

    /**
     * 处方药列表
     * @return
     */
    @PostMapping("/goods/list")
    public JsonResult listPrescriptionGoodsList(@RequestBody @Validated PrescriptionNoParam param){
        return success(shop().prescriptionService.listGoodsByPrescriptionCode(param.getPrescriptionCode()));
    }

    /**
     *  添加商品到购物车
     * @param param
     * @return
     */
    @PostMapping("/cart/batch/add")
    public JsonResult addGoodsToCart(@RequestBody @Valid WxAppBatchAddGoodsToCartParam param){
        WxAppSessionUser user = wxAppAuth.user();
        WxAppCartGoodsResultVo cgr = shop().cart.addBatchGoodsToCart(param,user.getUserId());
        ResultMessage s = cgr.getResultMessage();
        if (s.getFlag()){
            return success();
        }
        JsonResult data = fail(s);
        String goodsName = shop().goodsService.getGoodsNameByPrdId(cgr.getPrdId());
        Object message = data.getMessage();
        Object msg = goodsName + message;
        data.setMessage(msg);
        return data;
    }
}
