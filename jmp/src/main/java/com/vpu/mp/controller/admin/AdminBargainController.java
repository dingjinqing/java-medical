package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainAddParam;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainPageListQueryParam;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainPageListQueryVo;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainUpdateParam;
import com.vpu.mp.service.shop.market.bargain.BargainRecordService;

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
		PageResult<BargainPageListQueryVo>  res = shop().bargain.getPageList(param);
		for(BargainPageListQueryVo vo : res.dataList) {
			vo.setSuccessNumber(shop().bargain.bargainRecord.getBargainRecordNumberByStatus(vo.getId(), BargainRecordService.STATUS_SUCCESS));
			vo.setBargainUserNumber(shop().bargain.bargainRecord.getBargainRecordNumber(vo.getId()));
		}
		return success(res);
	}
	
	/**
	 *添加 砍价活动
	 * @return
	 */
	@PostMapping(value = "/api/admin/market/bargain/add")
	public JsonResult addBargain(@RequestBody @Valid BargainAddParam param) {
		if(shop().bargain.addBargain(param)) {
			return success();
		}else {
			return fail();
		}
	}
	
	/**
	 *更新  砍价活动
	 * @return
	 */
	@PostMapping(value = "/api/admin/market/bargain/update")
	public JsonResult updateBargain(@RequestBody @Valid BargainUpdateParam param) {
		if(shop().bargain.updateBargain(param)) {
			return success();
		}else {
			return fail();
		}
	}
}
