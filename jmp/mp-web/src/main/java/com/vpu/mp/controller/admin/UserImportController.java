package com.vpu.mp.controller.admin;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.userImp.SetNoticeParam;
import com.vpu.mp.service.pojo.shop.member.userImp.UserImportParam;

/**
 * 会员导入
 * 
 * @author zhaojianqiang
 * @time 下午1:41:04
 */
@RestController
@RequestMapping("/api")
public class UserImportController extends AdminBaseController {
	private static final String LANGUAGE_TYPE_EXCEL = "excel";

	/**
	 * 设置用户导入通知
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/user/import/setnotice")
	public JsonResult setActivationNotice(@RequestBody SetNoticeParam param) {
		JsonResultCode resCode = shop().member.userImportService.setActivationNotice(param);
		if (resCode.equals(JsonResultCode.CODE_SUCCESS)) {
			return success();
		}
		return fail(resCode);
	}

	/**
	 * 获取模板
	 * 
	 * @param response
	 */
	@PostMapping(value = "/admin/user/import/getTemplate")
	public void getTemplate(HttpServletResponse response) {
		Workbook workbook = shop().member.userImportService.getTemplate(getLang());
		String fileName = Util.translateMessage(getLang(), JsonResultMessage.GET_TEMPLATE_NAME, LANGUAGE_TYPE_EXCEL,
				"messages");
		export2Excel(workbook, fileName, response);
	}

	/**
	 * 上传文件
	 * @param file
	 * @return
	 */
	@PostMapping(value = "/admin/user/import/insert")
	public JsonResult importUser(UserImportParam param) {
		//multipart/form-data不加@RequestBody
		MultipartFile file = param.getFile();
		logger().info("上传文件:" + file.getName());
		ExcelTypeEnum checkFile = shop().member.userImportService.checkFile(file);
		if (checkFile == null) {
			return fail(JsonResultCode.CODE_EXCEL_ERRO);
		}
		shop().member.userImportService.insertUser(getLang(), param);
		return success();

	}
}
