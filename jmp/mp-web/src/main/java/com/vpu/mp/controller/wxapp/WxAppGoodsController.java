package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsDetailMpParam;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李晓冰
 * @date 2019年10月14日 小程序商品相关接口
 */
@RestController
public class WxAppGoodsController extends WxAppBaseController {

	@PostMapping("/api/wxapp/goods/detail")
	public JsonResult getGoodsDetailInfo(@RequestBody GoodsDetailMpParam param) {
        Integer userId = wxAppAuth.user().getUserId();
        param.setUserId(userId);
	    param.setFromPage(EsGoodsConstant.GOODS_DETAIL_PAGE);
		return success(shop().goodsMp.getGoodsDetailMp(param));
	}
}
