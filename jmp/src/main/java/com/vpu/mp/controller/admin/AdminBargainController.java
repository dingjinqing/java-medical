package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainPageListQueryParam;

/**
 * @author 王兵兵
 *
 * 2019年7月24日
 */
@RestController
public class AdminBargainController extends AdminBaseController {

	/**
	 * 砍价活动分页查询列表
	 * @return
	 */
	@PostMapping(value = "/api/admin/market/bargain/list")
	public JsonResult getBargainPageList(@RequestBody @Valid BargainPageListQueryParam param) {
		return success(shop().bargainService.getPageList(param));
	}
}
