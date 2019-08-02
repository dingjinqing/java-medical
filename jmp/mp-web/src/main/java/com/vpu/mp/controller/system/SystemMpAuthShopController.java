package com.vpu.mp.controller.system;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.saas.shop.mp.MpAuthShopVo;
import com.vpu.mp.service.pojo.saas.shop.mp.MpDeployQueryParam;
import com.vpu.mp.service.pojo.saas.shop.mp.MpVersionListParam;
import com.vpu.mp.service.pojo.saas.shop.mp.MpVersionVo;
import com.vpu.mp.service.saas.shop.MpAuthShopService;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenResult;

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
	 * 小程序模板发布
	 * @param param
	 * @return
	 * @throws WxErrorException
	 * @throws IOException
	 */
	@PostMapping("/api/system/mp/publish")
	public JsonResult mpPublishAction(@RequestBody MpDeployQueryParam param) throws WxErrorException, IOException {
		MpAuthShopService mp = saas.shop.mp;
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

}
