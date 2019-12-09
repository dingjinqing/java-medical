package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsDetailMpParam;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李晓冰
 * @date 2019年12月09日
 */
@RestController
public class WxAppGoodsSearchController extends WxAppBaseController{
    public JsonResult getGoodsDetailInfo(@RequestBody GoodsDetailMpParam param) {
        Integer userId = wxAppAuth.user().getUserId();
        param.setUserId(userId);
        param.setFromPage(EsGoodsConstant.GOODS_DETAIL_PAGE);

        return null;
    }
}
