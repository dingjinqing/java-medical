package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.saas.shop.mp.MpAuthShopVo;

/**
 * 
 * @author lixinguo
 *
 */
@RestController
public class AdminWechatApiController extends AdminBaseController {
	
	/**
	 * 得到小程序信息
	 * 
	 * @param param
	 * @return
	 */
	@GetMapping("/api/admin/mp/get")
	public JsonResult getMp() {
		MpAuthShopRecord record = saas.shop.mp.getAuthShopByShopId(this.shopId());
		if (record == null) {
			return fail(JsonResultCode.CODE_PARAM_ERROR);
		}
		return success(record.into(MpAuthShopVo.class));
	}
	
	
}
