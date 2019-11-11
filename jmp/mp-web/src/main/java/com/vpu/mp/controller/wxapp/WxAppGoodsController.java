package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsCommendParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsDetailMpParam;
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
//        Integer userId = wxAppAuth.user().getUserId();
		param.setUserId(123);
		return success(shop().goodsMp.getGoodsDetailMp(param));
	}

	/**
	 * 好物圈分享，测试用
	 * @param param
	 * @return
	 */
	@PostMapping("/api/wxapp/goods/commend")
	public JsonResult shippingRecommendGoods(@RequestBody GoodsCommendParam param) {
		return success(shop().goodsMp.goodsRecommend.shippingRecommendGoods(param.getList()));
	}

}
