package com.vpu.mp.controller.wxapp;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.wxapp.collection.CollectListParam;
import com.vpu.mp.service.pojo.wxapp.collection.CollectListVo;



@RestController
@RequestMapping("/api/wxapp")
public class WxAppCollectController extends WxAppBaseController{
	/**
	 * 商品收藏列表
	 * @return
	 */
	@PostMapping("/collect/list")
	public JsonResult collectList(@RequestBody CollectListParam param) {
		Integer userId = wxAppAuth.user().getUserId();

		PageResult<CollectListVo> list = shop().collect.collectList(param,userId);
		return this.success(list);
	}
}
