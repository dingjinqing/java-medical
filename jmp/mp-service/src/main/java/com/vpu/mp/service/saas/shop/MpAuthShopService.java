package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.MpAuthShop.MP_AUTH_SHOP;
import static com.vpu.mp.db.main.tables.ShopRenew.SHOP_RENEW;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vpu.mp.service.pojo.saas.shop.mp.MpAuditStateVo;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.Record13;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.main.tables.MpAuthShop;
import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.db.main.tables.records.MpDeployHistoryRecord;
import com.vpu.mp.db.main.tables.records.MpVersionRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.shop.mp.MpAuthShopListParam;
import com.vpu.mp.service.pojo.saas.shop.mp.MpAuthShopListVo;
import com.vpu.mp.service.pojo.saas.shop.mp.MpDeployQueryParam;
import com.vpu.mp.service.pojo.shop.config.trade.WxpayConfigParam;
import com.vpu.mp.service.pojo.shop.config.trade.WxpaySearchParam;
import com.vpu.mp.service.saas.image.SystemImageService;
import com.vpu.mp.service.shop.decoration.AppletsJumpService;
import com.vpu.mp.service.wechat.api.WxOpenAccountService;
import com.vpu.mp.service.wechat.bean.ma.MpWxMaOpenCommitExtInfo;
import com.vpu.mp.service.wechat.bean.open.MaWxPlusInListInner;
import com.vpu.mp.service.wechat.bean.open.MaWxPlusInResult;
import com.vpu.mp.service.wechat.bean.open.WxOpenGetResult;

import cn.binarywang.wx.miniapp.util.json.WxMaGsonBuilder;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenMaService;
import me.chanjar.weixin.open.bean.WxOpenCreateResult;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizationInfo;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizerInfo;
import me.chanjar.weixin.open.bean.ma.WxOpenMaCategory;
import me.chanjar.weixin.open.bean.ma.WxOpenMaSubmitAudit;
import me.chanjar.weixin.open.bean.message.WxOpenMaSubmitAuditMessage;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import me.chanjar.weixin.open.bean.result.WxOpenMaCategoryListResult;
import me.chanjar.weixin.open.bean.result.WxOpenMaDomainResult;
import me.chanjar.weixin.open.bean.result.WxOpenMaPageListResult;
import me.chanjar.weixin.open.bean.result.WxOpenMaQueryAuditResult;
import me.chanjar.weixin.open.bean.result.WxOpenMaSubmitAuditResult;
import me.chanjar.weixin.open.bean.result.WxOpenResult;

/**
 * 
 * @author 新国
 *
 */
@Service

public class MpAuthShopService extends MainBaseService {

	@Autowired
	protected DomainConfig domainConfig;

	@Autowired
	protected SystemImageService image;
	
	

	public static final Byte AUTH_OK = 1;
	public static final Byte AUTH_CANCEL = 0;

	public static final Byte AUDIT_STATE_NO_SUBMIT = 0;
	public static final Byte AUDIT_STATE_AUDITING = 1;
	public static final Byte AUDIT_STATE_AUDIT_SUCCESS = 2;
	public static final Byte AUDIT_STATE_AUDIT_FAILED = 3;

	public static final Integer WX_AUTH_STATUS_PASSED = 0;
	public static final Integer WX_AUTH_STATUS_AUDIT_FAILED = 1;
	public static final Integer WX_AUTH_STATUS_AUDITING = 2;

	/**
	 * 添加小程序信息
	 * 
	 * @param appId
	 * @param shopId
	 * @return
	 * @throws WxErrorException
	 */
	public MpAuthShopRecord addMpAuthAccountInfo(String appId, Integer shopId) throws WxErrorException {

		WxOpenAuthorizerInfoResult authInfo = open().getWxOpenComponentService().getAuthorizerInfo(appId);
		MpAuthShopRecord record = db().newRecord(MP_AUTH_SHOP);
		WxOpenAuthorizationInfo authorizationInfo = authInfo.getAuthorizationInfo();
		WxOpenAuthorizerInfo authorizerInfo = authInfo.getAuthorizerInfo();
		record.setAppId(authorizationInfo.getAuthorizerAppid());
		record.setShopId((shopId));
		record.setNickName(authorizerInfo.getNickName());
		record.setUserName(authorizerInfo.getUserName());
		record.setAlias(authorizerInfo.getAlias());
		record.setVerifyTypeInfo(authorizerInfo.getVerifyTypeInfo().toString());
		record.setHeadImg(authorizerInfo.getHeadImg());
		record.setFuncInfo(Util.toJson(authorizationInfo.getFuncInfo()));
		record.setOpenCard(authorizerInfo.getBusinessInfo().get("open_card").byteValue());
		record.setOpenPay(authorizerInfo.getBusinessInfo().get("open_pay").byteValue());
		record.setIsAuthOk((byte) 1);
		record.setAuthorizationInfo(Util.toJson(authorizationInfo));
		record.setAuthorizerInfo(Util.toJson(authorizerInfo));
		record.setPrincipalName(authorizerInfo.getPrincipalName());
		record.setQrcodeUrl(getMpQrCode(appId, authorizerInfo));
		if (this.getAuthShopByAppId(appId) == null) {
			record.insert();
			// TODO: log operation
		} else {
			record.update();
			// TODO: log operation
		}
		return record;
    }
    /**
     * 更新小程序信息
     *
     * @param appId 小程序id
     * @return 是否更新成功
     */
    public WxOpenResult updateAppInfo(String appId) throws WxErrorException {
        String updateSuccessMsg="更新授信息";
        String updateErrorMsg="database has no data";

        MpAuthShopRecord record = db().selectFrom(MP_AUTH_SHOP).where(MP_AUTH_SHOP.APP_ID.eq(appId)).fetchAny();
        WxOpenResult wxOpenResult = new WxOpenResult();
        if (record == null) {
            wxOpenResult.setErrcode("1");
            wxOpenResult.setErrmsg(updateErrorMsg);
            return wxOpenResult;
        }
        WxOpenAuthorizerInfoResult authInfo = open().getWxOpenComponentService().getAuthorizerInfo(appId);
        WxOpenAuthorizationInfo authorizationInfo = authInfo.getAuthorizationInfo();
        WxOpenAuthorizerInfo authorizerInfo = authInfo.getAuthorizerInfo();

        record.setNickName(authorizerInfo.getNickName());
        record.setUserName(authorizerInfo.getUserName());
        record.setAlias(authorizerInfo.getAlias());
        record.setVerifyTypeInfo(authorizerInfo.getVerifyTypeInfo().toString());
        record.setHeadImg(authorizerInfo.getHeadImg());
        record.setFuncInfo(Util.toJson(authorizationInfo.getFuncInfo()));
        record.setOpenCard(authorizerInfo.getBusinessInfo().get("open_card").byteValue());
        record.setOpenPay(authorizerInfo.getBusinessInfo().get("open_pay").byteValue());
        record.setIsAuthOk((byte) 1);
        record.setAuthorizationInfo(Util.toJson(authorizationInfo));
        record.setAuthorizerInfo(Util.toJson(authorizerInfo));
        record.setPrincipalName(authorizerInfo.getPrincipalName());
        record.setQrcodeUrl(getMpQrCode(appId, authorizerInfo));

        record.update();

        wxOpenResult.setErrcode("0");
        wxOpenResult.setErrmsg(updateSuccessMsg);

        operateLog(record,MpOperateLogService.OP_TYPE_UPDATE_MP,wxOpenResult);

        return wxOpenResult;
    }

	/**
	 * appId是否授权成功
	 * 
	 * @param appId
	 * @return
	 */
	public Boolean isAuthOk(String appId) {
		MpAuthShopRecord mp = getAuthShopByAppId(appId);
		return (mp != null && mp.getIsAuthOk().equals(AUTH_OK));
	}

	/**
	 * 店铺是否授权成功
	 * 
	 * @param shopId
	 * @return
	 */
	public Boolean isAuthOk(Integer shopId) {
		MpAuthShopRecord mp = getAuthShopByShopId(shopId);
		return (mp != null && mp.getIsAuthOk().equals(AUTH_OK));
	}

	/**
	 * 获取小程序服务
	 * 
	 * @param shopId
	 * @return
	 */
	public WxOpenMaService getMaServiceByShopId(Integer shopId) {
		MpAuthShopRecord mp = getAuthShopByShopId(shopId);
		assert (mp != null && mp.getIsAuthOk().equals(AUTH_OK));
		return open().getWxOpenComponentService().getWxMaServiceByAppid(mp.getAppId());
	}

	/**
	 * 获取小程序服务
	 * 
	 * @param appId
	 * @return
	 */
	public WxOpenMaService getMaServiceByAppId(String appId) {
		MpAuthShopRecord mp = getAuthShopByAppId(appId);
		assert (mp != null && mp.getIsAuthOk().equals(AUTH_OK));
		return open().getWxOpenComponentService().getWxMaServiceByAppid(mp.getAppId());
	}

	/**
	 * 获取小程序服务
	 * 
	 * @param appId
	 * @return
	 */
	public WxOpenMaService getMaServiceByMpAppId(MpAuthShopRecord mp) {
		if (mp != null && mp.getIsAuthOk().equals(AUTH_OK)) {
			throw new IllegalArgumentException("Miniprogram is not authed!");
		}
		return open().getWxOpenComponentService().getWxMaServiceByAppid(mp.getAppId());
	}

	/**
	 * 通过appId得到小程序信息
	 * 
	 * @param appId
	 * @return
	 */
	public MpAuthShopRecord getAuthShopByAppId(String appId) {
		MpAuthShopRecord fetchAny = db().fetchAny(MP_AUTH_SHOP, MP_AUTH_SHOP.APP_ID.eq(appId));
		return fetchAny;
	}

	
	/**
	 * 通过appId得到小程序信息，返回的图片带url
	 * 
	 * @param appId
	 * @return
	 */
	public MpAuthShopRecord getAuthShopByAppIdAddURL(String appId) {
		MpAuthShopRecord fetchAny = db().fetchAny(MP_AUTH_SHOP, MP_AUTH_SHOP.APP_ID.eq(appId));
		if(fetchAny!=null) {
			fetchAny.setQrcodeUrl(image.imageUrl(fetchAny.getQrcodeUrl())); 
			fetchAny.setTestQrPath(image.imageUrl(fetchAny.getTestQrPath()));			
		}
		return fetchAny;
	}
	/**
	 * 通过shopId得到小程序信息
	 * 
	 * @param shopId
	 * @return
	 */
	public MpAuthShopRecord getAuthShopByShopId(Integer shopId) {
		MpAuthShopRecord fetchAny = db().fetchAny(MP_AUTH_SHOP, MP_AUTH_SHOP.SHOP_ID.eq((shopId)));
		return fetchAny;
	}
	
	
	/**
	 * 通过shopId得到小程序信息
	 * 
	 * @param shopId
	 * @return
	 */
	public MpAuthShopRecord getAuthShopByShopIdAddURL(Integer shopId) {
		MpAuthShopRecord fetchAny = db().fetchAny(MP_AUTH_SHOP, MP_AUTH_SHOP.SHOP_ID.eq((shopId)));
		if(fetchAny!=null) {
			fetchAny.setQrcodeUrl(image.imageUrl(fetchAny.getQrcodeUrl())); 
			fetchAny.setTestQrPath(image.imageUrl(fetchAny.getTestQrPath()));			
		}
		return fetchAny;
	}

	/**
	 * 设置小程序支付配置
	 * 
	 * @param appId
	 * @param mchId       商户号
	 * @param key         支付密钥
	 * @param certContent 证书内容
	 * @param keyContent  私钥内容
	 * @return
	 */
	public int setPaymentInfo(String appId, String mchId, String key, String certContent, String keyContent) {
		return db().update(MP_AUTH_SHOP).set(MP_AUTH_SHOP.PAY_MCH_ID, mchId).set(MP_AUTH_SHOP.PAY_KEY, key)
				.set(MP_AUTH_SHOP.PAY_CERT_CONTENT, certContent).set(MP_AUTH_SHOP.PAY_KEY_CONTENT, keyContent)
				.where(MP_AUTH_SHOP.APP_ID.eq(appId)).execute();
	}

	/**
	 * 更新微信支付配置
	 * 
	 * @param wxpayConfigParam
	 * @return
	 */
	public int udpateWxpayConfig(WxpayConfigParam wxpayConfigParam) {
		return db().update(MpAuthShop.MP_AUTH_SHOP)
				.set(MpAuthShop.MP_AUTH_SHOP.PAY_MCH_ID, wxpayConfigParam.getPayMchId())
				.set(MpAuthShop.MP_AUTH_SHOP.PAY_KEY, wxpayConfigParam.getPayKey())
				.set(MpAuthShop.MP_AUTH_SHOP.PAY_CERT_CONTENT, wxpayConfigParam.getPayCertContent())
				.set(MpAuthShop.MP_AUTH_SHOP.PAY_KEY_CONTENT, wxpayConfigParam.getPayKeyContent())
				.where(MpAuthShop.MP_AUTH_SHOP.APP_ID.eq(wxpayConfigParam.getAppId())).execute();
	}

	/**
	 * 根据appid检测MpAuthShop表中数据存在性
	 * 
	 * @param appId
	 * @return true存在，false不存在
	 */
	public boolean checkAuthShopExist(String appId) {
		Condition conditionAuthShop = MpAuthShop.MP_AUTH_SHOP.APP_ID.eq(appId);
		return db().fetchCount(MpAuthShop.MP_AUTH_SHOP, conditionAuthShop) > 0;
	}

	/**
	 * 查询微信支付配置
	 * 
	 * @return
	 */
	public WxpayConfigParam getWxpayConfig(WxpaySearchParam wxpaySearchParam) {
		List<WxpayConfigParam> wxpayConfigParams = db()
				.select(MpAuthShop.MP_AUTH_SHOP.APP_ID, MpAuthShop.MP_AUTH_SHOP.PAY_MCH_ID,
						MpAuthShop.MP_AUTH_SHOP.PAY_KEY, MpAuthShop.MP_AUTH_SHOP.PAY_CERT_CONTENT,
						MpAuthShop.MP_AUTH_SHOP.PAY_KEY_CONTENT)
				.from(MpAuthShop.MP_AUTH_SHOP).where(MpAuthShop.MP_AUTH_SHOP.APP_ID.eq(wxpaySearchParam.getAppId()))
				.fetchInto(WxpayConfigParam.class);
		return wxpayConfigParams != null && !wxpayConfigParams.isEmpty() ? wxpayConfigParams.get(0) : null;
	}

	/**
	 * 设置小程序服务器域名和 业务域名
	 * 
	 * @param appId
	 * @return
	 * @throws WxErrorException
	 */
	public WxOpenMaDomainResult modifyDomain(String appId) throws WxErrorException {
		MpAuthShopRecord mp = this.getAuthShopByAppId(appId);
		String action = "add";
		String[] httpsDomains = { domainConfig.mainUrl("", "https") };
		String[] wssDomains = { domainConfig.mainUrl("", "wss") };
		WxOpenMaService maService = this.getMaServiceByAppId(appId);
		WxOpenMaDomainResult result=new WxOpenMaDomainResult();
		String noNewDomainCode = "85017";
		try {
			result = maService.modifyDomain(action, Arrays.asList(httpsDomains),
					Arrays.asList(wssDomains), Arrays.asList(httpsDomains), Arrays.asList(httpsDomains));
		} catch (WxErrorException e) {
			//logger().debug(e.getMessage(),e);
			//没有新增域名，请确认小程序已经添加了域名或该域名是否没有在第三方平台添加
			logger().debug("appId:"+appId+"修改域名modifyDomain失败："+e.getError().getErrorCode()+"  "+e.getError().getErrorMsg());
			WxOpenResult fromJson =null;
			if(noNewDomainCode.equals(String.valueOf(e.getError().getErrorCode()))) {
				String setWebViewDomain = maService.setWebViewDomain(action, Arrays.asList(httpsDomains));
				fromJson = WxMaGsonBuilder.create().fromJson(setWebViewDomain, WxOpenResult.class);
				if(fromJson.isSuccess()) {
					mp.setIsModifyDomain((byte) 1);
					mp.update();
				}else {
					logger().debug("appId:"+appId+"修改域名setWebViewDomain失败"+fromJson.getErrcode()+"  "+fromJson.getErrmsg());
				}
			}
			operateLog(mp, MpOperateLogService.OP_TYPE_MODIFY_DOMAIN, fromJson);
			result.setErrcode(fromJson.getErrcode());
			result.setErrmsg(fromJson.getErrmsg());
			return result;
		}
		if (result.isSuccess()) {
			mp.setIsModifyDomain((byte) 1);
			mp.update();				
		}else {
			logger().debug("appId:"+appId+"修改域名modifyDomain失败"+result.getErrcode()+"  "+result.getErrmsg());
		}
		operateLog(mp, MpOperateLogService.OP_TYPE_MODIFY_DOMAIN, result);
		return result;
	}

	/**
	 * 上传代码
	 * 
	 * @param appId
	 * @param templateId
	 * @param userVersion
	 * @param userDesc
	 * @return
	 * @throws WxErrorException
	 */
	public WxOpenResult uploadCode(String appId, Integer templateId)
			throws WxErrorException {
		MpVersionRecord version = saas.shop.mpVersion.getRow(templateId);
		MpAuthShopRecord mp = this.getAuthShopByAppId(appId);
		WxOpenMaService maService = this.getMaServiceByAppId(appId);
		MpWxMaOpenCommitExtInfo extInfo = new MpWxMaOpenCommitExtInfo();

		extInfo.setExtAppid(appId);
		extInfo.addExt("main_host", domainConfig.getMainDomain());
		extInfo.addExt("image_host", domainConfig.getImageDomain());
		extInfo.addExt("shop_id", mp.getShopId().toString());
		extInfo.addExt("version", templateId.toString());

		AppletsJumpService appletsJumpService = saas.getShopApp(mp.getShopId()).appletsJump;
		/**
		 * TODO: add setNavigateToMiniProgramAppIdList
		 * extInfo.setNavigateToMiniProgramAppIdList(navigateToMiniProgr++amAppIdList);
		 */
		extInfo.setNavigateToMiniProgramAppIdList(appletsJumpService.getMpJumpAppIDList());
		
		//上传代码保存小程序跳转的提交的appid 版本号，appid ,状态
		appletsJumpService.saveMpJumpAppIDList(extInfo.getNavigateToMiniProgramAppIdList(), templateId);
		
		JsonObject params = new JsonObject();
		params.addProperty("template_id", templateId);
		params.addProperty("user_version", version.getUserVersion());
		params.addProperty("user_desc", version.getUserDesc());
		params.addProperty("ext_json", Util.toJson(extInfo));
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		JsonParser parser = new JsonParser();
		JsonElement je = parser.parse(params.toString());
		System.out.println(gson.toJson(je));
		String response = maService.post(WxOpenMaService.API_CODE_COMMIT, gson.toJson(je));
		WxOpenResult result = WxMaGsonBuilder.create().fromJson(response, WxOpenResult.class);
		if(result.isSuccess()) {
			MpAuthShopRecord upMp=MP_AUTH_SHOP.newRecord();
			upMp.setAppId(appId);
			upMp.setUploadState((byte) 1);
			upMp.setLastAuthTime(Timestamp.valueOf(LocalDateTime.now()));
			db().executeUpdate(upMp);
		}
		operateLog(mp, MpOperateLogService.OP_TYPE_UPLOAD_CODE, result);
		//更新申请发布小程序为已发布
		saas.shop.mpJumpVersion.updateMpJumpVersion(mp.getShopId());
		return result;
	}

	/**
	 * 记录操作日志
	 * 
	 * @param mp
	 * @param operateType
	 * @param result
	 */
	public void operateLog(MpAuthShopRecord mp, Byte operateType, WxOpenResult result) {
		String message = result.isSuccess() ? "" : "code:" + result.getErrcode() + ", message:" + result.getErrmsg();
		Byte operateState = result.isSuccess() ? MpOperateLogService.OP_STATE_SUCCESS
				: MpOperateLogService.OP_STATE_FAILED;
		saas.shop.mpOperateLog.log(mp.getAppId(), mp.getBindTemplateId(), operateType, operateState, message);
	}

	/**
	 * 绑定体验者
	 * 
	 * @param appId
	 * @param wechatId
	 * @return
	 * @throws WxErrorException
	 */
	public WxOpenResult bindTester(String appId, String wechatId) throws WxErrorException {
		logger().debug("绑定体验者开始···································");
		MpAuthShopRecord mp = this.getAuthShopByAppId(appId);
		List<String> testers = StringUtils.isBlank(mp.getTester()) ? new ArrayList<>()
				: Util.parseJson(mp.getTester(), new TypeReference<List<String>>() {
				});
		WxOpenMaService maService = this.getMaServiceByAppId(appId);
		WxOpenResult result = maService.bindTester(wechatId);
		logger().debug("绑定体验者"+result);
		logger().debug(result.getErrcode()+result.getErrmsg());
		if (result.isSuccess()) {
			testers.add(wechatId);
			mp.setTester(Util.toJson(testers));
			mp.update();
		}
		operateLog(mp, MpOperateLogService.OP_TYPE_ADD_TESTER, result);
		return result;
	}

	public String getMpQrCode(String appId, WxOpenAuthorizerInfo authorizerInfo) {
		//String qrcodeUrl = authorizerInfo.getQrcodeUrl();
		String path = "pages/bottom/bottom";
		String filename = appId + "_" + Util.md5(path) + ".jpg";
		String relativePath = "upload/saas/mp/app_code/" + filename;
		Boolean addImgeToUp=false;
		try {
			byte[] createWxaCodeBytes = open().getWxOpenComponentService().getWxMaServiceByAppid(appId).getQrcodeService().createWxaCodeBytes(path, 430, true, null, false);
			addImgeToUp = saas.sysImage.uploadToUpYunByByte(relativePath, createWxaCodeBytes);
		} catch (WxErrorException e) {
			logger().error("appId" + appId +"获取小程序二维码失败");
			e.printStackTrace();
		} catch (Exception e) {
			logger().error("appId" + appId + "头像上传又拍云失败");
			e.printStackTrace();
		}
		logger().debug("appId" + appId + "头像上传又拍云" + addImgeToUp);
		return relativePath;
	}

	/**
	 * 解除绑定
	 * 
	 * @param appId
	 * @param wechatId
	 * @return
	 * @throws WxErrorException
	 */
	public WxOpenResult unbindTester(String appId, String wechatId) throws WxErrorException {
		MpAuthShopRecord mp = this.getAuthShopByAppId(appId);
		List<String> testers = StringUtils.isBlank(mp.getTester()) ? new ArrayList<>()
				: Util.parseJson(mp.getTester(), new TypeReference<List<String>>() {
				});
		if (!testers.contains(wechatId)) {
			return successResult(wechatId);
		}
		WxOpenMaService maService = this.getMaServiceByAppId(appId);
		WxOpenResult result = maService.unbindTester(wechatId);
		if (result.isSuccess()) {
			testers.remove(wechatId);
			mp.setTester(Util.toJson(testers));
			mp.update();
		}
		operateLog(mp, MpOperateLogService.OP_TYPE_DEL_TESTER, result);
		return result;
	}

	/**
	 * 正确结果
	 * 
	 * @return
	 */
	public WxOpenResult successResult(String message) {
		WxOpenResult result = new WxOpenResult();
		result.setErrcode("0");
		result.setErrmsg(message);
		return result;
	}

	/**
	 * 自定义错误消息
	 * 
	 * @param message
	 * @return
	 */
	public WxOpenResult failResult(String message) {
		WxOpenResult result = new WxOpenResult();
		result.setErrcode("-2");
		result.setErrmsg(message);
		return result;
	}

	/**
	 * 得到体验码
	 * 
	 * @param appId
	 * @throws WxErrorException
	 * @throws IOException
	 * @throws Exception
	 */
	public WxOpenResult getTestQrCode(String appId) throws WxErrorException, IOException {
		MpAuthShopRecord mp = this.getAuthShopByAppId(appId);
		WxOpenMaService maService = this.getMaServiceByAppId(appId);
		String pagePath = "pages/bottom/bottom";
		File file = maService.getTestQrcode(pagePath, null);
		String relativePath = "upload/saas/qr/" + appId + ".jpg";
		try {
			image.uploadToUpYun(relativePath, file);
		} catch (Exception e) {
			operateLog(mp, MpOperateLogService.OP_TYPE_GET_TESTER_QR, failResult(e.getMessage()));
			return failResult(e.getMessage());
		}
		mp.setTestQrPath(relativePath);
		mp.update();
		operateLog(mp, MpOperateLogService.OP_TYPE_GET_TESTER_QR, successResult(relativePath));
		return successResult(relativePath);
	}

	/**
	 * 得到可选类目
	 * 
	 * @param appId
	 * @return
	 * @throws WxErrorException
	 */
	public WxOpenMaCategoryListResult getCategory(String appId) throws WxErrorException {
		WxOpenMaService maService = this.getMaServiceByAppId(appId);
		MpAuthShopRecord mp = this.getAuthShopByAppId(appId);
		WxOpenMaCategoryListResult result = maService.getCategoryList();
		if (result.isSuccess()) {
			mp.setCategory(Util.toJson(result.getCategoryList()));
			mp.update();
		}
		operateLog(mp, MpOperateLogService.OP_TYPE_GET_CATEGORY, result);
		return result;
	}

	/**
	 * 得到小程序页面配置列表
	 * 
	 * @param appId
	 * @return
	 * @throws WxErrorException
	 */
	public WxOpenMaPageListResult getPage(String appId) throws WxErrorException {
		MpAuthShopRecord mp = this.getAuthShopByAppId(appId);
		WxOpenMaService maService = this.getMaServiceByAppId(appId);
		WxOpenMaPageListResult result = maService.getPageList();
		if (result.isSuccess()) {
			mp.setPageCfg(Util.toJson(result.getPageList()));
			mp.update();
		}
		//更新部署日志
		updateDeployData(result.getPageList(), appId);
		operateLog(mp, MpOperateLogService.OP_TYPE_GET_PAGE_CFG, result);
		return result;
	}

	/**
	 * 提交审核
	 * 
	 * @param appId
	 * @return
	 * @throws WxErrorException
	 */
	public WxOpenMaSubmitAuditResult submitAudit(String appId) throws WxErrorException {
		MpAuthShopRecord mp = this.getAuthShopByAppId(appId);
		WxOpenMaService maService = this.getMaServiceByAppId(appId);

		WxOpenMaSubmitAudit audit = new WxOpenMaSubmitAudit();
		List<WxOpenMaCategory> categoryList = null;
		//获取授权小程序帐号的可选类目 first_class  first_id
		if (!StringUtils.isBlank(mp.getCategory())) {
			categoryList = Util.parseJson(mp.getCategory(), new TypeReference<List<WxOpenMaCategory>>() {
			});
		} else {
			WxOpenMaCategoryListResult category = this.getCategory(appId);
			categoryList = category.getCategoryList();
		}
		//获取小程序的第三方提交代码的页面配置  address
		List<String> pageCfg=new ArrayList<String>();
		pageCfg.add("pages/bottom/bottom");
		if (!StringUtils.isBlank(mp.getPageCfg())) {
			pageCfg = Util.parseJson(mp.getPageCfg(), new TypeReference<List<String>>() {
			});
		}else {
			WxOpenMaPageListResult page = this.getPage(appId);
			pageCfg=page.getPageList();
		}
		String pagePath = pageCfg.get(0);
		audit.setPagePath(pagePath);
		audit.setFirstClass(categoryList.get(0).getFirstClass());
		audit.setTag(categoryList.get(0).getFirstClass());
		audit.setTitle("首页");
		audit.setFirstId(categoryList.get(0).getFirstId());
		audit.setSecondClass(categoryList.get(0).getSecondClass());
		audit.setSecondId(categoryList.get(0).getSecondId());
		audit.setThirdId(categoryList.get(0).getThirdId());
		audit.setThirdClass(categoryList.get(0).getThirdClass());
		WxOpenMaSubmitAuditMessage submitAuditMessage = new WxOpenMaSubmitAuditMessage();
		List<WxOpenMaSubmitAudit> itemList = new ArrayList<>();
		itemList.add(audit);
		submitAuditMessage.setItemList(itemList);
		WxOpenMaSubmitAuditResult result = maService.submitAudit(submitAuditMessage);
		if(result.isSuccess()) {
			//更新数据库字段
			MpAuthShopRecord upMp = MP_AUTH_SHOP.newRecord();
			upMp.setAppId(appId);
			//审核中
			upMp.setAuditState((byte)1);
			upMp.setAuditId(result.getAuditId());
			upMp.setSubmitAuditTime(new Timestamp(System.currentTimeMillis()));
			db().executeUpdate(upMp);
		}
		operateLog(mp, MpOperateLogService.OP_TYPE_SUBMIT_AUDIT, result);
		return result;
	}

	/**
	 * 发布审核成功代码
	 * 
	 * @param appId
	 * @return
	 * @throws WxErrorException
	 */
	public WxOpenResult publishAuditSuccessCode(String appId) throws WxErrorException {
		MpAuthShopRecord mp = this.getAuthShopByAppId(appId);
		WxOpenResult result=new WxOpenResult();
		if (mp.getAuditId() != null && mp.getAuditState().equals(AUDIT_STATE_AUDIT_SUCCESS)) {
			//审核成功的代码
			WxOpenMaService maService = this.getMaServiceByAppId(appId);
			result = maService.releaesAudited();
			if(result.isSuccess()) {
				//更新数据库
				updatePush(appId);
			}
			operateLog(mp, MpOperateLogService.OP_TYPE_PUBLISH_CODE, result);
			return result;
		}
		result.setErrcode(JsonResultCode.WX_MA_NEED_AUDITING_CODE_SUCCESS.toString());
		//请等待代码审核成功或未上传代码
		result.setErrmsg(JsonResultMessage.WX_MA_NEED_AUDITING_CODE_SUCCESS);
		return result;
	}

	/**
	 * 上传代码并提交审核
	 * 
	 * @param appId
	 * @param templateId
	 * @param userVersion
	 * @param userDesc
	 * @return
	 * @throws WxErrorException
	 */
	public WxOpenResult uploadCodeAndApplyAudit(String appId, Integer templateId)
			throws WxErrorException {
		WxOpenResult result = this.modifyDomain(appId);
		if (result.isSuccess()) {
			result = this.uploadCode(appId, templateId);
			if (result.isSuccess()) {
				result = this.submitAudit(appId);
			}
		}
		return result;
	}

	/**
	 * 批量上传代码并提交审核
	 * 
	 * @param templateId
	 * @param userVersion
	 * @param userDesc
	 * @throws WxErrorException
	 */
	public void batchUploadCodeAndApplyAudit(Integer templateId)
			throws WxErrorException {
		Result<MpAuthShopRecord> records = db().fetch(MP_AUTH_SHOP, MP_AUTH_SHOP.IS_AUTH_OK.eq(AUTH_OK));
		for (MpAuthShopRecord record : records) {
			this.uploadCodeAndApplyAudit(record.getAppId(), templateId);
		}
	}

	/**
	 * 更新小程序审核状态,只有审核中，才可以获取最后审核状态
	 * 
	 * @param appId
	 * @return
	 * @throws WxErrorException
	 */
	public WxOpenMaQueryAuditResult refreshAppInfo(String appId) throws WxErrorException {
		MpAuthShopRecord mp = this.getAuthShopByAppId(appId);
		WxOpenMaQueryAuditResult result=new WxOpenMaQueryAuditResult();
		if (mp.getAuditId() != null && mp.getAuditState().equals(AUDIT_STATE_AUDITING)) {
			WxOpenMaService maService = this.getMaServiceByAppId(appId);
			result = maService.getAuditStatus(mp.getAuditId());
			System.out.println(result);
			if (result.isSuccess()) {
				if (result.getStatus().equals(WX_AUTH_STATUS_PASSED)) {
					mp.setAuditState(AUDIT_STATE_AUDIT_SUCCESS);
					mp.setAuditOkTime(Timestamp.valueOf(LocalDateTime.now()));
					mp.update();
				} else if (result.getStatus().equals(WX_AUTH_STATUS_AUDIT_FAILED)) {
					mp.setAuditState(AUDIT_STATE_AUDIT_FAILED);
					mp.setAuditFailReason(result.getReason());
					mp.update();
				}
			}
			return result;
		}
		//尚未上传代码
		result.setErrcode(JsonResultCode.WX_MA_NEED_UPLOADCODE.toString());
		result.setErrmsg(JsonResultMessage.WX_MA_NEED_UPLOADCODE);
		return result;
	}

    /**
     *  根据店铺id获取小程序审核状态
     * @param shopId 店铺id
     * @return  小程序审核信息
     */
	public MpAuditStateVo getAppAuditInfo(Integer shopId){

        MpAuthShopRecord mp = this.getAuthShopByShopId(shopId);
        MpAuditStateVo mpAuditStateVo = new MpAuditStateVo();
        mpAuditStateVo.setAppId(mp.getAppId());
        mpAuditStateVo.setAuditId(mp.getAuditId());
        mpAuditStateVo.setAuditState(mp.getAuditState());
        mpAuditStateVo.setSubmitAuditTime(mp.getSubmitAuditTime());
        mpAuditStateVo.setAuditOkTime(mp.getAuditOkTime());
        mpAuditStateVo.setAuditFailReason(mp.getAuditFailReason());

        return mpAuditStateVo;
    }

	
	public WxOpenMaQueryAuditResult getLatestAuditStatus(String appId) throws WxErrorException {
		WxOpenMaService maService = this.getMaServiceByAppId(appId);
		WxOpenMaQueryAuditResult latestAuditStatus = maService.getLatestAuditStatus();
		if(latestAuditStatus.isSuccess()) {
			System.out.println(latestAuditStatus.getAuditId());
			return latestAuditStatus;
		}
		return latestAuditStatus;
	}

	/**
	 * 绑定同一主体的小程序和公众号到开放平台账号
	 * TODO: 暂时只处理 小程序，以后需要加入公众号
	 * @param record
	 * @throws WxErrorException
	 */
	public void bindAllSamePrincipalOpenAppId(MpAuthShopRecord record) throws WxErrorException {
		Result<MpAuthShopRecord> samePrincipalMpApps = getSamePrincipalMpApps(record.getPrincipalName());
		String openAppId = null;
		// 遍历所有主体相同的号，查找不为空的openAppId
		for (MpAuthShopRecord mShopRecord : samePrincipalMpApps) {
			if (!StringUtils.isEmpty(mShopRecord.getBindOpenAppId())) {
				openAppId = mShopRecord.getBindOpenAppId();
				break;
			}
		}
		for (MpAuthShopRecord mRecord : samePrincipalMpApps) {
			openAppId = bindOpenAppId(true,mRecord.getAppId(), openAppId);
			if (!openAppId.equals(mRecord.getBindOpenAppId())) {
				// 更新数据库
				mRecord.setBindOpenAppId(openAppId);
				db().executeUpdate(mRecord);
			}
		}
	}

	/**
	 * 用接口绑定小程序或者公众号appId到开放平台账号
	 * 
	 * @param isMa     是否是小程序
	 * @param appId     小程序或者公众号appId
	 * @param openAppId 开放平台账号，为空需要新创建
	 * @return
	 * @throws WxErrorException
	 */
	public String bindOpenAppId(Boolean isMa,String appId, String openAppId) throws WxErrorException {
		WxOpenAccountService service = isMa ? open.getMaExtService() : open.getMpExtService();
		WxOpenGetResult result = null;
		try {
			result = service.getOpenAccount(appId);
			if (StringUtils.isEmpty(openAppId)) {
				return result.getOpenAppid();
			}

			if (!openAppId.equals(result.getOpenAppid())) {
				// 如果openAppId不相同，解绑原openAppId，并绑定新的openAppId
				service.unbindOpenAppId(appId, result.getOpenAppid());
				service.bindOpenAppId(appId, openAppId);
			}
			return openAppId;
		} catch (WxErrorException e) {
			if (e.getError().getErrorCode() == 89002) {

				// 该公众号/小程序未绑定微信开放平台帐号
				// 如果openAppId为空，则创建开放平台账号，否则绑定当前平台账号
				if (StringUtils.isEmpty(openAppId)) {
					WxOpenCreateResult openCreateResult = service.createOpenAccount(appId);
					return openCreateResult.getOpenAppid();
				} else {
					service.bindOpenAppId(appId, openAppId);
					return openAppId;
				}
			} else {
				throw new WxErrorException(e.getError(), e);
			}
		}
	}

	/**
	 * 查询主体名称相同的
	 * 
	 * @param principalName
	 * @return
	 */
	public Result<MpAuthShopRecord> getSamePrincipalMpApps(String principalName) {
		return db().fetch(MP_AUTH_SHOP,
				MP_AUTH_SHOP.PRINCIPAL_NAME.eq(principalName).and(MP_AUTH_SHOP.IS_AUTH_OK.eq((byte) 1)));
	}

    /**
     * 获取小程序授权列表
     *
     * @param param 过滤参数
     * @return 分页内容
     */
    public PageResult<MpAuthShopListVo> getAuthList(MpAuthShopListParam param) {

        String shopFieldName=SHOP_RENEW.SHOP_ID.getName();
        String expireFieldName=SHOP_RENEW.EXPIRE_TIME.getName();

        Table<Record2<Integer, Timestamp>> nested =
            db().select(SHOP_RENEW.SHOP_ID.as(shopFieldName),
                DSL.max(SHOP_RENEW.EXPIRE_TIME).as(expireFieldName))
                .from(SHOP_RENEW).groupBy(SHOP_RENEW.SHOP_ID).asTable("nested");

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        Condition condition = param.buildOption();
        if (param.getShopState() != null && param.getShopState() == 0) {
            condition = condition.and(nested.field(expireFieldName, Timestamp.class).lt(Timestamp.valueOf(LocalDateTime.now())));
        }

        if (param.getShopState() != null && param.getShopState() == 1) {
            condition = condition.and(nested.field(expireFieldName, Timestamp.class).ge(Timestamp.valueOf(LocalDateTime.now())));
        }

        SelectConditionStep<Record13<String, Integer, String, String, Byte, String, Byte, Timestamp, Integer, Byte, Byte, Integer, Timestamp>> select = db().select(MP_AUTH_SHOP.APP_ID, MP_AUTH_SHOP.SHOP_ID, MP_AUTH_SHOP.NICK_NAME, MP_AUTH_SHOP.HEAD_IMG,
            MP_AUTH_SHOP.IS_AUTH_OK, MP_AUTH_SHOP.VERIFY_TYPE_INFO, MP_AUTH_SHOP.OPEN_PAY, MP_AUTH_SHOP.LAST_AUTH_TIME,
            MP_AUTH_SHOP.BIND_TEMPLATE_ID, MP_AUTH_SHOP.AUDIT_STATE, MP_AUTH_SHOP.PUBLISH_STATE,
            DSL.when(nested.field(expireFieldName, Timestamp.class).lt(timestamp), 0).otherwise(1).as("shopState"),
            MP_AUTH_SHOP.CREATE_TIME)
            .from(MP_AUTH_SHOP).leftJoin(nested).on(nested.field(shopFieldName, Integer.class).eq(MP_AUTH_SHOP.SHOP_ID))
            .where(condition);

        return this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), MpAuthShopListVo.class);
    }

    /**
     * 	获得小程序发版版本  1 正常版本 2 好物推荐版本
     * @param appId
     * @return
     */
	public Byte getMpPackageVersion(String appId) {
		MaWxPlusInListInner plugin = getPlugin(appId);
		if(plugin!=null&&plugin.getStatus().equals("2")) {
			return 2;
		}
		return 1;

	}
	
	/**
	 * 	获得插件
	 * @param appId
	 * @return
	 */
	public MaWxPlusInListInner getPlugin(String appId) {
		WxOpenAccountService service =open.getMaExtService();
    	Map<String, String> map=new HashMap<String, String>();
    	map.put("action", "list");
    	MaWxPlusInResult plugInManage=null;
    	try {
			plugInManage = service.plugInManage(appId, map);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
    	//好物圈的appId
    	appId="wx56c8f077de74b07c";
    	if(plugInManage.getErrcode().equals("0")&&plugInManage.getErrmsg().equals("ok")) {
    		 List<MaWxPlusInListInner> pluginList = plugInManage.getPluginList();
    		for(MaWxPlusInListInner inner:pluginList) {
    			if(inner.getAppid().equals(appId)) {
    				return inner;
    			}
    		}
    	}
		return null;
	}
	
	
	//	更新部署日志
	public void updateDeployData(List<String> pageList,String appId) {
		Byte templateId = getMpPackageVersion(appId);
		Integer tempId=Integer.parseInt(templateId.toString());
		MpDeployHistoryRecord deployInfo = saas().deployHistoryService.getDeployInfo(appId, tempId);
		if(deployInfo!=null) {
			saas().deployHistoryService.addRow(appId, tempId);
		}
		saas().deployHistoryService.update(appId, tempId, pageList);
				
	}
	
	public void updatePush(String appId) {
		MpAuthShopRecord newRecord = MP_AUTH_SHOP.newRecord();
		newRecord.setAppId(appId);
		newRecord.setPublishState((byte) 1);
		newRecord.setPublishTime(new Timestamp(System.currentTimeMillis()));
		db().executeUpdate(newRecord);
		
	}

	//设置支付方式
	public WxOpenResult setSubMerchant(MpDeployQueryParam param) {
		MpAuthShopRecord mp = this.getAuthShopByAppId(param.getAppId());
		WxOpenResult wxOpenResult = new WxOpenResult();
		wxOpenResult.setErrcode(JsonResultMessage.MSG_FAIL);
		if (mp == null) {
			return wxOpenResult;
		}
		// TODO 生成证书
		/**
		 * "0"：微信直连支付 "1"：微铺宝子商户支付 "2"：通联子商户支付 "3"：微信国际融合钱包支付
		 */
		if (param.getIsSubMerchant() == null) {
			// TODO 返回字段为空
			wxOpenResult.setErrcode(JsonResultMessage.MSG_FAIL);
			return wxOpenResult;
		}
		switch (param.getIsSubMerchant()) {
		case 0:
			// 微信直连支付

			break;
		case 1:
			// 微铺宝子商户支付

			break;
		case 2:
			// 通联子商户支付
			if (StringUtils.isEmpty(param.getUnion_pay_app_id()) || StringUtils.isEmpty(param.getUnion_pay_cus_id())
					|| StringUtils.isEmpty(param.getUnion_pay_app_key())) {
				// TODO 返回字段为空
				wxOpenResult.setErrcode(JsonResultMessage.MSG_FAIL);
				return wxOpenResult;
			}
			int execute = db().update(MP_AUTH_SHOP).set(MP_AUTH_SHOP.UNION_PAY_APP_ID, param.getUnion_pay_app_id())
					.set(MP_AUTH_SHOP.UNION_PAY_CUS_ID, param.getUnion_pay_cus_id())
					.set(MP_AUTH_SHOP.UNION_PAY_APP_KEY, param.getUnion_pay_app_key())
					.set(MP_AUTH_SHOP.IS_SUB_MERCHANT, param.getIsSubMerchant().byteValue()).execute();
			if (execute > 0) {
				wxOpenResult.setErrcode(JsonResultMessage.MSG_SUCCESS);
			}
			break;
		case 3:
			// 微信国际融合钱包支付 [, 'merchant_category_code', 'fee_type']
			if (StringUtils.isEmpty(param.getMerchant_category_code()) || StringUtils.isEmpty(param.getFee_type())) {
				// TODO 返回字段为空
				wxOpenResult.setErrcode(JsonResultMessage.MSG_FAIL);
				return wxOpenResult;
			}
			int execute2 = db().update(MP_AUTH_SHOP)
					.set(MP_AUTH_SHOP.MERCHANT_CATEGORY_CODE, param.getMerchant_category_code())
					.set(MP_AUTH_SHOP.FEE_TYPE, param.getFee_type())
					.set(MP_AUTH_SHOP.IS_SUB_MERCHANT, param.getIsSubMerchant().byteValue()).execute();
			if (execute2 > 0) {
				wxOpenResult.setErrcode(JsonResultMessage.MSG_SUCCESS);
			}
			break;

		default:
			break;
		}
		return wxOpenResult;
	}
	
}
