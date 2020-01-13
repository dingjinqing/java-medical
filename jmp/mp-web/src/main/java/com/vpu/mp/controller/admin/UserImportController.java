package com.vpu.mp.controller.admin;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.userImp.SetNoticeJson;
import com.vpu.mp.service.pojo.shop.member.userImp.SetNoticeJsonVo;
import com.vpu.mp.service.pojo.shop.member.userImp.SetNoticeParam;
import com.vpu.mp.service.pojo.shop.member.userImp.UIGetListParam;
import com.vpu.mp.service.pojo.shop.member.userImp.UIGetListVo;
import com.vpu.mp.service.pojo.shop.member.userImp.UIGetNoActListParam;
import com.vpu.mp.service.pojo.shop.member.userImp.UIGetNoActListVo;
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
	private static final Byte ZERO = 0;

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
	 * 获取用户导入通知
	 * 
	 * @param param
	 * @return
	 */
	@GetMapping(value = "/admin/user/import/getnotice")
	public JsonResult getActivationNotice() {
		SetNoticeJson resCode = shop().member.userImportService.getActivationNotice();
		return success(new SetNoticeJsonVo(resCode.getExplain(),resCode.getScore(),resCode.getMrkingVoucherId()));
	}
	/**
	 * 获取模板
	 * 
	 * @param response
	 */
	@GetMapping(value = "/admin/user/import/getTemplate")
	public void getTemplate(HttpServletResponse response) {
		logger().info("开始获取模板");
		Workbook workbook = shop().member.userImportService.getTemplate(getLang());
		String fileName = Util.translateMessage(getLang(), JsonResultMessage.GET_TEMPLATE_NAME, LANGUAGE_TYPE_EXCEL,
				"messages");
		export2Excel(workbook, fileName, response);
		logger().info("结束获取模板");
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping(value = "/admin/user/import/insert")
	public JsonResult importUser(UserImportParam param) {
		// multipart/form-data不加@RequestBody
		MultipartFile file = param.getFile();
		logger().info("上传文件:" + file.getName());
		ExcelTypeEnum checkFile = shop().member.userImportService.checkFile(file);
		if (checkFile == null) {
			return fail(JsonResultCode.CODE_EXCEL_ERRO);
		}
		shop().member.userImportService.insertUser(getLang(), param);
		return success();

	}

	/**
	 * 会员导入列表
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/user/import/list")
	public JsonResult getList(@RequestBody UIGetListParam param) {
		PageResult<UIGetListVo> descList = shop().member.userImportService.descList(param);
		return success(descList);

	}

	/**
	 * 用户导入列表-未激活会员
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping(value = "/admin/user/import/list/noActive")
	public JsonResult listNoActive(@RequestBody UIGetNoActListParam param) {
		param.setIsActivate(ZERO);
		PageResult<UIGetNoActListVo> addGroupName = shop().member.userImportService.addGroupName(param);
		return success(addGroupName);
	}

	/**
	 * 下载失败数据
	 * 
	 * @param param
	 * @param response
	 */
	@PostMapping(value = "/admin/user/import/export")
	public void getErrorExcel(@RequestBody UIGetListParam param, HttpServletResponse response) {
		logger().info("开始下载失败数据");
		Workbook workbook = shop().member.userImportService.getErrorMsg(param.getBatchId(), getLang());
		String fileName = Util.translateMessage(getLang(), JsonResultMessage.EXPORT_TEMPLATE_NAME, LANGUAGE_TYPE_EXCEL,
				"messages");
		String dateFormat = DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL_NO_UNDERLINE);
		export2Excel(workbook, fileName + dateFormat, response);
		logger().info("结束下载失败数据");
	}

	/**
	 * 下载激活数据
	 * @param param
	 * @param response
	 */
	@PostMapping(value = "/admin/user/import/exportActivate")
	public void getexportActivate(@RequestBody UIGetListParam param, HttpServletResponse response) {
		logger().info("开始下载激活数据");
		Workbook workbook = shop().member.userImportService.getActiveExcel(param.getBatchId(), getLang());
		String fileName = Util.translateMessage(getLang(), JsonResultMessage.EXPORT_TEMPLATE_ACTIVE_NAME, LANGUAGE_TYPE_EXCEL,
				"messages");
		String dateFormat = DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL_NO_UNDERLINE);
		export2Excel(workbook, fileName + dateFormat, response);
		logger().info("结束下载激活数据");
	}
}
