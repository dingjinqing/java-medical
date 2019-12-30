package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.shop.member.userImp.SetNoticeParam;



/**
 * 会员导入
 * @author zhaojianqiang
 * @time   下午1:41:04
 */
@RestController
@RequestMapping("/api")
public class UserImportController extends AdminBaseController {
	
	/**
	 * 设置用户导入通知
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/user/import/setnotice")
	public JsonResult setActivationNotice(SetNoticeParam param){
		JsonResultCode resCode = shop().member.userImportService.setActivationNotice(param);
		if(resCode.equals(JsonResultCode.CODE_SUCCESS)) {
			return success();
		}
		return fail(resCode);
	}

}
