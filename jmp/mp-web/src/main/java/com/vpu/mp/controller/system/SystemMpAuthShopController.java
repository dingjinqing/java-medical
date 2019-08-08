package com.vpu.mp.controller.system;

import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.saas.shop.mp.*;
import com.vpu.mp.service.saas.shop.MpAuthShopService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 
 * @author lixinguo
 *
 */
@RestController
public class SystemMpAuthShopController extends SystemBaseController {

	/**
	 * 小程序模板版本分页
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/api/system/mp/version/list")
	public JsonResult list(@RequestBody MpVersionListParam param) {
		PageResult<MpVersionVo> pageList = saas.shop.mpVersion.getPageList(param);
		return success(pageList);
	}

	/**
	 * 同步小程序模板库
	 * 
	 * @return
	 * @throws WxErrorException
	 */
	@PostMapping("/api/system/mp/version/syn")
	public JsonResult synMpList() throws WxErrorException {
		return success(saas.shop.mpVersion.synMpVersionList());
	}
	
	/**
	 * 设置版本
	 * 
	 * @return
	 */
	@GetMapping("/api/system/mp/version/set/{templateId}")
	public JsonResult setVersion(@PathVariable Integer templateId) {
		saas.shop.mpVersion.setCurrentUseTemplateId(templateId);
		return success();
	}
	
	/**
	 * 批量提交审核
	 * 
	 * @return
	 */
	@GetMapping("/api/system/mp/version/batch")
	public JsonResult batchPublish() {

		return success();
	}

	/**
	 * 小程序模板发布
	 * 
	 * @param param
	 * @return
	 * @throws WxErrorException
	 * @throws IOException
	 */
	@PostMapping("/api/system/mp/publish")
	public JsonResult mpPublishAction(@RequestBody MpDeployQueryParam param) throws WxErrorException, IOException {
		MpAuthShopService mp = saas.shop.mp;
		if (!mp.isAuthOk(param.getAppId())) {
			return fail(JsonResultCode.WX_MA_APP_ID_NOT_AUTH);
		}
		WxOpenResult result = null;
		switch (param.getAct()) {
		case MpDeployQueryParam.ACT_ADD_TESTER: {
			result = mp.bindTester(param.getAppId(), param.getWechatId());
			break;
		}

		case MpDeployQueryParam.ACT_DEL_TESTER: {
			result = mp.unbindTester(param.getAppId(), param.getWechatId());
			break;
		}

		case MpDeployQueryParam.ACT_GET_CATEGORY: {
			result = mp.getCategory(param.getAppId());
			break;
		}
		case MpDeployQueryParam.ACT_GET_PAGE_CFG: {
			result = mp.getPage(param.getAppId());
			break;
		}
		case MpDeployQueryParam.ACT_GET_TESTER_QR: {
			result = mp.getTestQrCode(param.getAppId());
			break;
		}
		case MpDeployQueryParam.ACT_MODIFY_DOMAIN: {
			result = mp.modifyDomain(param.getAppId());
			break;
		}
		case MpDeployQueryParam.ACT_PUBLISH_CODE: {
			result = mp.publishAuditSuccessCode(param.getAppId());
			break;
		}
		case MpDeployQueryParam.ACT_SUBMIT_AUDIT: {
			result = mp.submitAudit(param.getAppId());
			break;
		}
		case MpDeployQueryParam.ACT_UPDATE_MP: {
			result = mp.refreshAppInfo(param.getAppId());
			break;
		}
		case MpDeployQueryParam.ACT_UPLOAD_AUDIT: {
			result = mp.uploadCodeAndApplyAudit(param.getAppId(), param.getTemplateId());
			break;
		}
		case MpDeployQueryParam.ACT_UPLOAD_CODE: {
			result = mp.uploadCode(param.getAppId(), param.getTemplateId());
			break;
		}
		default: {
			return fail(JsonResultCode.CODE_PARAM_ERROR);
		}
		}
		MpAuthShopVo vo = mp.getAuthShopByAppId(param.getAppId()).into(MpAuthShopVo.class);
		return result.isSuccess() ? success(vo) : fail(result.getErrmsg());
	}

	/**
	 * 得到小程序信息
	 * 
	 * @param  appId 小程序id
	 * @return
	 */
	@GetMapping("/api/system/mp/get/{appId}")
	public JsonResult getMp(@PathVariable String appId) {
		MpAuthShopRecord record = saas.shop.mp.getAuthShopByAppId(appId);
		if (record == null) {
			return fail(JsonResultCode.CODE_PARAM_ERROR);
		}
		return success(record.into(MpAuthShopVo.class));
	}


    /**
     *  获取小程序版本下拉列表
     * @return 下拉列表值
     */
    @GetMapping("/api/system/mp/version/user/version/list")
	public JsonResult getMpUserVersionList(){
        List<MpVersionVo> mpUserVersionList = saas.shop.mpVersion.getMpUserVersionList();

        return success(mpUserVersionList);
    }

    /**
     *  小程序版本操作日志分页列表
     * @param param  过滤信息，未使用templateId字段，而使用user_version字段是因为不确定这两个字段的对应关系
     *               是否一定是一对一
     * @return 分页结果值
     */
	@PostMapping("/api/system/mp/operate/log/list")
	public JsonResult logList(@RequestBody MpOperateListParam param) {
        PageResult<MpOperateVo> mpOperateVoPageResult = saas.shop.mpOperateLog.logList(param);
        return success(mpOperateVoPageResult);
    }

}
