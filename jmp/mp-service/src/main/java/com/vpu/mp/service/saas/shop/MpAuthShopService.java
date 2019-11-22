package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.MpAuthShop.MP_AUTH_SHOP;
import static com.vpu.mp.db.main.tables.MpOfficialAccountUser.MP_OFFICIAL_ACCOUNT_USER;
import static com.vpu.mp.db.main.tables.Shop.SHOP;
import static com.vpu.mp.db.main.tables.ShopRenew.SHOP_RENEW;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Record11;
import org.jooq.Record13;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
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
import com.vpu.mp.db.main.tables.records.BackProcessRecord;
import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.db.main.tables.records.MpDeployHistoryRecord;
import com.vpu.mp.db.main.tables.records.MpOfficialAccountUserRecord;
import com.vpu.mp.db.main.tables.records.MpVersionRecord;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant.TaskJobEnum;
import com.vpu.mp.service.pojo.saas.shop.ShopMpListParam;
import com.vpu.mp.service.pojo.saas.shop.ShopMpListVo;
import com.vpu.mp.service.pojo.saas.shop.mp.MpAuditStateVo;
import com.vpu.mp.service.pojo.saas.shop.mp.MpAuthShopListParam;
import com.vpu.mp.service.pojo.saas.shop.mp.MpAuthShopListVo;
import com.vpu.mp.service.pojo.saas.shop.mp.MpDeployQueryParam;
import com.vpu.mp.service.pojo.saas.shop.mp.MpOperateVo;
import com.vpu.mp.service.pojo.saas.shop.mp.MpVersionVo;
import com.vpu.mp.service.pojo.saas.shop.officeAccount.MpOfficeAccountListVo;
import com.vpu.mp.service.pojo.shop.config.trade.WxpayConfigParam;
import com.vpu.mp.service.pojo.shop.market.message.BatchUploadCodeParam;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.market.message.RabbitParamConstant;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateConfig;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateData;
import com.vpu.mp.service.pojo.shop.user.user.WxUserInfo;
import com.vpu.mp.service.saas.image.SystemImageService;
import com.vpu.mp.service.saas.shop.official.message.MpOfficialAccountMessageService;
import com.vpu.mp.service.shop.ShopApplication;
import com.vpu.mp.service.shop.decoration.AppletsJumpService;
import com.vpu.mp.service.wechat.api.WxOpenAccountService;
import com.vpu.mp.service.wechat.bean.ma.MpWxMaOpenCommitExtInfo;
import com.vpu.mp.service.wechat.bean.ma.WxContentTemplate;
import com.vpu.mp.service.wechat.bean.open.MaWxPlusInListInner;
import com.vpu.mp.service.wechat.bean.open.MaWxPlusInResult;
import com.vpu.mp.service.wechat.bean.open.WxOpenGetResult;

import cn.binarywang.wx.miniapp.util.json.WxMaGsonBuilder;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
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

	@Autowired
	protected  MpOfficialAccountMessageService accountMessageService;


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

        operateLogGlobal(record, MpOperateLogService.OP_TYPE_UPDATE_MP, wxOpenResult, WxContentTemplate.WX_UPDATE_MP_SUCCESS.code, new String[] {});
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
	 * @return true存在，false不存在
	 */
	public boolean checkAuthShopExist(String appId) {
		Condition conditionAuthShop = MpAuthShop.MP_AUTH_SHOP.APP_ID.eq(appId);
        return db().fetchExists(MpAuthShop.MP_AUTH_SHOP, conditionAuthShop);
	}

	/**
	 * 查询微信支付配置
	 */
    public WxpayConfigParam getWxpayConfig(String appId) {
        Optional<WxpayConfigParam> optionalInto = db()
            .select(MP_AUTH_SHOP.APP_ID, MP_AUTH_SHOP.PAY_MCH_ID,
                MP_AUTH_SHOP.PAY_KEY, MP_AUTH_SHOP.PAY_CERT_CONTENT,
                MP_AUTH_SHOP.PAY_KEY_CONTENT)
            .from(MP_AUTH_SHOP).where(MP_AUTH_SHOP.APP_ID.eq(appId))
            .fetchOptionalInto(WxpayConfigParam.class);
        return optionalInto.orElse(new WxpayConfigParam());
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
			logger().debug(e.getMessage(),e);
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
			result.setErrcode(fromJson.getErrcode());
			result.setErrmsg(fromJson.getErrmsg());
			operateLogGlobal(mp, MpOperateLogService.OP_TYPE_MODIFY_DOMAIN, result, WxContentTemplate.WX_MODIFY_DOMAIN_SUCCESS.code, new String[] {});
			return result;
		}
		if (result.isSuccess()) {
			mp.setIsModifyDomain((byte) 1);
			mp.update();
		}else {
			logger().debug("appId:"+appId+"修改域名modifyDomain失败"+result.getErrcode()+"  "+result.getErrmsg());
		}
		operateLogGlobal(mp, MpOperateLogService.OP_TYPE_MODIFY_DOMAIN, result, WxContentTemplate.WX_MODIFY_DOMAIN_SUCCESS.code, new String[] {});
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

		ShopRecord sRecord = saas.shop.getShopById(mp.getShopId());
		extInfo.setExtAppid(appId);
		extInfo.addExt("main_host", domainConfig.getMainDomain());
		extInfo.addExt("image_host", domainConfig.getImageDomain());
		extInfo.addExt("shop_id", mp.getShopId().toString());
		extInfo.addExt("version", templateId.toString());
		extInfo.addExt("currency", sRecord.getCurrency());
		extInfo.addExt("shopLanguage", sRecord.getShopLanguage());

		AppletsJumpService appletsJumpService = saas.getShopApp(mp.getShopId()).appletsJump;
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
		String response = maService.post(WxOpenMaService.API_CODE_COMMIT, gson.toJson(je));
		logger().info("发送的信息");
		logger().info(gson.toJson(je));
		WxOpenResult result = WxMaGsonBuilder.create().fromJson(response, WxOpenResult.class);
		logger().info("https://api.weixin.qq.com/wxa/commit返回信息");
		logger().info(result.toString());
		if(result.isSuccess()) {
			MpAuthShopRecord upMp=MP_AUTH_SHOP.newRecord();
			upMp.setAppId(appId);
			upMp.setUploadState((byte) 1);
			upMp.setBindTemplateId(templateId);
			upMp.setLastAuthTime(Timestamp.valueOf(LocalDateTime.now()));
			db().executeUpdate(upMp);
		}
		operateLogGlobal(mp, MpOperateLogService.OP_TYPE_UPLOAD_CODE, result, WxContentTemplate.WX_UPLOAD_CODE_SUCCESS.code, new String[] {});
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
	 * 记录操作日志多语言
	 *
	 * @param mp
	 * @param operateType
	 * @param result
	 */
	public void operateLogGlobal(MpAuthShopRecord mp, Byte operateType, WxOpenResult result,Integer templateIds, String... datas) {
		Byte operateState = result.isSuccess() ? MpOperateLogService.OP_STATE_SUCCESS
				: MpOperateLogService.OP_STATE_FAILED;
		saas.shop.mpOperateLog.insertRecord(mp.getBindTemplateId(), operateType, mp.getAppId(), operateState, templateIds, datas);
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
		//绑定测试者
		operateLogGlobal(mp, MpOperateLogService.OP_TYPE_ADD_TESTER, result, WxContentTemplate.WX_BIND_TESTER_SUCCESS.code, new String[] {wechatId});
		return result;
	}

	public String getMpQrCode(String appId, WxOpenAuthorizerInfo authorizerInfo) {
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
		operateLogGlobal(mp, MpOperateLogService.OP_TYPE_DEL_TESTER, result, WxContentTemplate.WX_DEL_TESTER_SUCCESS.code, new String[] {wechatId});
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
		WxOpenResult result=new WxOpenResult();
		try {
			image.uploadToUpYun(relativePath, file);
		} catch (Exception e) {
			result.setErrcode("-2");
			result.setErrmsg(e.getMessage());
			operateLogGlobal(mp, MpOperateLogService.OP_TYPE_GET_TESTER_QR, result, WxContentTemplate.WX_GET_TESTER_QR_FAIL.code, new String[] {"-2",e.getMessage()});
			return failResult(e.getMessage());
		}
		mp.setTestQrPath(relativePath);
		mp.update();
		operateLogGlobal(mp, MpOperateLogService.OP_TYPE_GET_TESTER_QR, result, WxContentTemplate.WX_GET_TESTER_QR_SUCCESS.code, new String[] {});
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
		operateLogGlobal(mp, MpOperateLogService.OP_TYPE_GET_CATEGORY, result, WxContentTemplate.WX_GET_CATEGORY_SUCCESS.code, new String[] {});
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
		operateLogGlobal(mp, MpOperateLogService.OP_TYPE_GET_PAGE_CFG, result, WxContentTemplate.WX_GET_PAGE_CFG_SUCCESS.code, new String[] {});
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
		operateLogGlobal(mp, MpOperateLogService.OP_TYPE_SUBMIT_AUDIT, result, WxContentTemplate.WX_SUBMIT_AUDIT_SUCCESS.code, new String[] {});
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
			operateLogGlobal(mp, MpOperateLogService.OP_TYPE_PUBLISH_CODE, result, WxContentTemplate.WX_PUBLISH_CODE_SUCCESS.code, new String[] {});
			return result;
		}
		result.setErrcode(JsonResultCode.WX_MA_NEED_AUDITING_CODE_SUCCESS.toString());
		//请等待代码审核成功或未上传代码
		result.setErrmsg(JsonResultMessage.WX_MA_NEED_AUDITING_CODE_SUCCESS);
		operateLogGlobal(mp, MpOperateLogService.OP_TYPE_PUBLISH_CODE, result, WxContentTemplate.WX_PUBLISH_CODE_SUCCESS.code, new String[] {result.getErrcode(),result.getErrmsg()});
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
	public JsonResultCode batchUploadCodeAndApplyAudit(Integer templateId){

		MpVersionRecord row = saas.shop.mpVersion.getRow(templateId);
		List<Byte> asList = Arrays.asList(TaskJobsConstant.STATUS_NEW,TaskJobsConstant.STATUS_EXECUTING);
		int recId = saas.shop.backProcessService.insertByInfo(row.into(MpVersionVo.class), this.getClass().getName(), 0);
		Boolean boolean1 = saas.taskJobMainService.assertHasStatusTaskJob(TaskJobEnum.BATCH_UPLOAD.getExecutionType(), asList);
		if(boolean1) {
			//已经有相同任务在运行，当前任务停止
			saas.shop.backProcessService.fail(recId, "已经有相同任务在运行，当前任务停止");
			return JsonResultCode.WX_ONLY_ONE;
		}
		MpAuthShopListParam param = new MpAuthShopListParam();
		param.setIsAuthOk((byte) 1);
		param.setAuditState((byte) 1);
		List<MpAuthShopListVo> mpList = getCanSubmitAuditMps(param,param.buildOptionByUpload()).fetchInto(MpAuthShopListVo.class);
		if(mpList.size()==0) {
			//没有要提交的
			saas.shop.backProcessService.fail(recId, "没有符合要求的小程序");
			return JsonResultCode.WX_NO_REQUIRED;
		}else {
			Integer currentUseTemplateId = saas.shop.mpVersion.getCurrentUseTemplateId(null,row.getPackageVersion());
			BatchUploadCodeParam param1=new BatchUploadCodeParam();
			param1.setList(mpList);
			param1.setRecId(recId);
			param1.setTemplateId(currentUseTemplateId);
			param1.setPackageVersion(row.getPackageVersion());
			Integer mainId = saas.taskJobMainService.dispatchImmediately(param1,BatchUploadCodeParam.class.getName(),0,TaskJobEnum.BATCH_UPLOAD.getExecutionType());
			logger().debug("获取的任务id为"+mainId);
			saas.shop.backProcessService.updateProcessId(recId, mainId);
			return JsonResultCode.CODE_SUCCESS;
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
			if(result.getStatus().equals(2)) {
				//状态成功没有返回reason
				operateLogGlobal(mp, MpOperateLogService.OP_TYPE_REFRESH_AUDIT_STATE, result, WxContentTemplate.WX_REFRESH_AUDIT_STATE_SUCCESSON2.code, new String[] {String.valueOf(result.getStatus())});
			}else {
				operateLogGlobal(mp, MpOperateLogService.OP_TYPE_REFRESH_AUDIT_STATE, result, WxContentTemplate.WX_REFRESH_AUDIT_STATE_SUCCESS.code, new String[] {String.valueOf(result.getStatus()),result.getReason()});				
			}
			return result;
		}
		//尚未上传代码
		result.setErrcode(JsonResultCode.WX_MA_NEED_UPLOADCODE.toString());
		result.setErrmsg(JsonResultMessage.WX_MA_NEED_UPLOADCODE);
		operateLogGlobal(mp, MpOperateLogService.OP_TYPE_REFRESH_AUDIT_STATE, result, WxContentTemplate.WX_REFRESH_AUDIT_STATE_FAIL.code, new String[] {result.getErrcode(),result.getErrmsg()});
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
			return latestAuditStatus;
		}
		return latestAuditStatus;
	}

	/**
	 * 绑定同一主体的小程序和公众号到开放平台账号
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
        SelectConditionStep<Record13<String, Integer, String, String, Byte, String, Byte, Timestamp, Integer, Byte, Byte, Integer, Timestamp>> select =getCanSubmitAuditMps(param,param.buildOption());
        return this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), MpAuthShopListVo.class);
    }


    public SelectConditionStep<Record13<String, Integer, String, String, Byte, String, Byte, Timestamp, Integer, Byte, Byte, Integer, Timestamp>> getCanSubmitAuditMps(MpAuthShopListParam param, Condition condition) {
    	  String shopFieldName=SHOP_RENEW.SHOP_ID.getName();
          String expireFieldName=SHOP_RENEW.EXPIRE_TIME.getName();

          Table<Record2<Integer, Timestamp>> nested =
              db().select(SHOP_RENEW.SHOP_ID.as(shopFieldName),
                  DSL.max(SHOP_RENEW.EXPIRE_TIME).as(expireFieldName))
                  .from(SHOP_RENEW).groupBy(SHOP_RENEW.SHOP_ID).asTable("nested");

          Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

          if (param.getShopState() != null && param.getShopState() == 0) {
              condition = condition.and(nested.field(expireFieldName, Timestamp.class).lt(Timestamp.valueOf(LocalDateTime.now())));
          }

          if (param.getShopState() != null && param.getShopState() == 1) {
              condition = condition.and(nested.field(expireFieldName, Timestamp.class).ge(Timestamp.valueOf(LocalDateTime.now())));
          }

           return db().select(MP_AUTH_SHOP.APP_ID, MP_AUTH_SHOP.SHOP_ID, MP_AUTH_SHOP.NICK_NAME, MP_AUTH_SHOP.HEAD_IMG,
              MP_AUTH_SHOP.IS_AUTH_OK, MP_AUTH_SHOP.VERIFY_TYPE_INFO, MP_AUTH_SHOP.OPEN_PAY, MP_AUTH_SHOP.LAST_AUTH_TIME,
              MP_AUTH_SHOP.BIND_TEMPLATE_ID, MP_AUTH_SHOP.AUDIT_STATE, MP_AUTH_SHOP.PUBLISH_STATE,
              DSL.when(nested.field(expireFieldName, Timestamp.class).lt(timestamp), 0).otherwise(1).as("shopState"),
              MP_AUTH_SHOP.CREATE_TIME)
              .from(MP_AUTH_SHOP).leftJoin(nested).on(nested.field(shopFieldName, Integer.class).eq(MP_AUTH_SHOP.SHOP_ID))
              .where(condition);
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
    	Map<String, String> map=new HashMap<String, String>(0);
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


	/**
	 * 更新部署日志
	 */
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

	/**
	 * 设置支付方式
	 */
	public WxOpenResult setSubMerchant(MpDeployQueryParam param) {
		MpAuthShopRecord mp = this.getAuthShopByAppId(param.getAppId());
		WxOpenResult wxOpenResult = new WxOpenResult();
		wxOpenResult.setErrcode(String.valueOf(JsonResultCode.CODE_FAIL));
		wxOpenResult.setErrmsg(String.valueOf(JsonResultMessage.MSG_FAIL));
		if (mp == null) {
			return wxOpenResult;
		}
		// TODO 生成证书
		/**
		 * "0"：微信直连支付 "1"：微铺宝子商户支付 "2"：通联子商户支付 "3"：微信国际融合钱包支付
		 */
		if (param.getIsSubMerchant() == null) {
			// TODO 返回字段为空
			wxOpenResult.setErrcode(String.valueOf(JsonResultCode.WX_MA_ISSUBMERCHANT_ISNULL));
			wxOpenResult.setErrmsg(String.valueOf(JsonResultMessage.WX_MA_ISSUBMERCHANT_ISNULL));
			return wxOpenResult;
		}
		switch (param.getIsSubMerchant()) {
		case 0:
			// 微信直连支付
			wxOpenResult.setErrcode(String.valueOf(JsonResultCode.WX_MA_FEATURE_NOT_OPEN));
			wxOpenResult.setErrmsg(JsonResultMessage.WX_MA_FEATURE_NOT_OPEN);
			operateLogGlobal(mp, MpOperateLogService.OP_TYPE_SETTING_SUB_MERCHANT, wxOpenResult, WxContentTemplate.WX_FEATURE_NOT_OPEN.code, new String[] {String.valueOf(param.getIsSubMerchant())});
			break;
		case 1:
			// 微铺宝子商户支付
			wxOpenResult.setErrcode(String.valueOf(JsonResultCode.WX_MA_FEATURE_NOT_OPEN));
			wxOpenResult.setErrmsg(JsonResultMessage.WX_MA_FEATURE_NOT_OPEN);
			operateLogGlobal(mp, MpOperateLogService.OP_TYPE_SETTING_SUB_MERCHANT, wxOpenResult, WxContentTemplate.WX_FEATURE_NOT_OPEN.code, new String[] {String.valueOf(param.getIsSubMerchant())});
			break;
		case 2:
			// 通联子商户支付
			if (StringUtils.isEmpty(param.getUnion_pay_app_id()) || StringUtils.isEmpty(param.getUnion_pay_cus_id())
					|| StringUtils.isEmpty(param.getUnion_pay_app_key())) {
				wxOpenResult.setErrcode(String.valueOf(JsonResultCode.WX_MA_TABLE_ISNULL));
				wxOpenResult.setErrmsg(JsonResultMessage.WX_MA_TABLE_ISNULL);
				operateLogGlobal(mp, MpOperateLogService.OP_TYPE_SETTING_SUB_MERCHANT, wxOpenResult, WxContentTemplate.WX_TABLE_ISNULL.code, new String[] {String.valueOf(param.getIsSubMerchant())});
				return wxOpenResult;
			}
			int execute = db().update(MP_AUTH_SHOP).set(MP_AUTH_SHOP.UNION_PAY_APP_ID, param.getUnion_pay_app_id())
					.set(MP_AUTH_SHOP.UNION_PAY_CUS_ID, param.getUnion_pay_cus_id())
					.set(MP_AUTH_SHOP.UNION_PAY_APP_KEY, param.getUnion_pay_app_key())
					.set(MP_AUTH_SHOP.IS_SUB_MERCHANT, param.getIsSubMerchant().byteValue()).execute();
			if (execute > 0) {
				wxOpenResult.setErrcode(String.valueOf(JsonResultCode.CODE_SUCCESS));
				wxOpenResult.setErrmsg(JsonResultMessage.MSG_SUCCESS);
			}
			break;
		case 3:
			// 微信国际融合钱包支付 [, 'merchant_category_code', 'fee_type']
			if (StringUtils.isEmpty(param.getMerchant_category_code()) || StringUtils.isEmpty(param.getFee_type())) {
				wxOpenResult.setErrcode(String.valueOf(JsonResultCode.WX_MA_TABLE_ISNULL));
				wxOpenResult.setErrmsg(JsonResultMessage.WX_MA_TABLE_ISNULL);
				operateLogGlobal(mp, MpOperateLogService.OP_TYPE_SETTING_SUB_MERCHANT, wxOpenResult, WxContentTemplate.WX_TABLE_ISNULL.code,new String[] {String.valueOf(param.getIsSubMerchant())});
				return wxOpenResult;
			}
			int execute2 = db().update(MP_AUTH_SHOP)
					.set(MP_AUTH_SHOP.MERCHANT_CATEGORY_CODE, param.getMerchant_category_code())
					.set(MP_AUTH_SHOP.FEE_TYPE, param.getFee_type())
					.set(MP_AUTH_SHOP.IS_SUB_MERCHANT, param.getIsSubMerchant().byteValue()).execute();
			if (execute2 > 0) {
				wxOpenResult.setErrcode(String.valueOf(JsonResultCode.CODE_SUCCESS));
				wxOpenResult.setErrmsg(JsonResultMessage.MSG_SUCCESS);
			}
			break;

		default:
			break;
		}
		if(wxOpenResult.getErrcode().equals(String.valueOf(JsonResultCode.CODE_SUCCESS))) {
			operateLogGlobal(mp, MpOperateLogService.OP_TYPE_SETTING_SUB_MERCHANT, wxOpenResult, WxContentTemplate.WX_SETTING_SUB_MERCHANT_SUCCESS.code, new String[] {String.valueOf(param.getIsSubMerchant())});
		}
		return wxOpenResult;
	}

	/**
	 * 将抛错信息存入log表
	 * @param act
	 * @param result
	 */
	public void erroInsert(MpDeployQueryParam param,WxOpenResult result){
		MpAuthShopRecord mp = this.getAuthShopByAppId(param.getAppId());
		Byte operateType=null;
		Integer templateIds=2000;
		String[] datas=null;
		switch (param.getAct()) {
		case MpDeployQueryParam.ACT_ADD_TESTER: {
			//绑定体验者
			operateType=MpOperateLogService.OP_TYPE_ADD_TESTER;
			templateIds=WxContentTemplate.WX_BIND_TESTER_FAIL.code;
			datas=new String[] {param.getWechatId(),result.getErrcode(),result.getErrmsg()};
			break;
		}

		case MpDeployQueryParam.ACT_DEL_TESTER: {
			// 删除体验者
			operateType=MpOperateLogService.OP_TYPE_DEL_TESTER;
			templateIds=WxContentTemplate.WX_DEL_TESTER_FAIL.code;
			datas=new String[] {param.getWechatId(),result.getErrcode(),result.getErrmsg()};
			break;
		}

		case MpDeployQueryParam.ACT_GET_CATEGORY: {
			//获取可选类目
			operateType=MpOperateLogService.OP_TYPE_GET_CATEGORY;
			templateIds=WxContentTemplate.WX_GET_CATEGORY_FAIL.code;
			datas=new String[] {result.getErrcode(),result.getErrmsg()};
			break;
		}
		case MpDeployQueryParam.ACT_GET_PAGE_CFG: {
			//获取页面配置
			operateType=MpOperateLogService.OP_TYPE_GET_PAGE_CFG;
			templateIds=WxContentTemplate.WX_GET_PAGE_CFG_FAIL.code;
			datas=new String[] {result.getErrcode(),result.getErrmsg()};
			break;
		}
		case MpDeployQueryParam.ACT_GET_TESTER_QR: {
			//获取体验者二维码
			operateType=MpOperateLogService.OP_TYPE_GET_TESTER_QR;
			templateIds=WxContentTemplate.WX_GET_NO_RECORD_FAIL.code;
			datas=new String[] {result.getErrcode(),result.getErrmsg()};
			break;
		}
		case MpDeployQueryParam.ACT_MODIFY_DOMAIN: {
			// 设置服务器域名
			operateType=MpOperateLogService.OP_TYPE_MODIFY_DOMAIN;
			templateIds=WxContentTemplate.WX_MODIFY_DOMAIN_FAIL.code;
			datas=new String[] {result.getErrcode(),result.getErrmsg()};
			break;
		}
		case MpDeployQueryParam.ACT_PUBLISH_CODE: {
			//发布代码
			operateType=MpOperateLogService.OP_TYPE_PUBLISH_CODE;
			templateIds=WxContentTemplate.WX_PUBLISH_CODE_FAIL.code;
			datas=new String[] {result.getErrcode(),result.getErrmsg()};
			break;
		}
		case MpDeployQueryParam.ACT_SUBMIT_AUDIT: {
			//提交审核
			operateType=MpOperateLogService.OP_TYPE_SUBMIT_AUDIT;
			templateIds=WxContentTemplate.WX_SUBMIT_AUDIT_FAIL.code;
			datas=new String[] {result.getErrcode(),result.getErrmsg()};
			break;
		}
		case MpDeployQueryParam.ACT_UPDATE_MP: {
			//更新小程序信息
			operateType=MpOperateLogService.OP_TYPE_UPDATE_MP;
			templateIds=WxContentTemplate.WX_UPDATE_MP_FAIL.code;
			datas=new String[] {result.getErrcode(),result.getErrmsg()};
			break;
		}
		case MpDeployQueryParam.ACT_REFRESH_AUDIT_STATE: {
			// 刷新审核状态
			operateType=MpOperateLogService.OP_TYPE_REFRESH_AUDIT_STATE;
			templateIds=WxContentTemplate.WX_REFRESH_AUDIT_STATE_FAIL.code;
			datas=new String[] {result.getErrcode(),result.getErrmsg()};
			break;
		}
		case MpDeployQueryParam.ACT_UPLOAD_AUDIT: {
			//上传代码并提交审核   一键提交审核
			operateType=MpOperateLogService.OP_TYPE_UPLOAD_AUDIT;
			templateIds=WxContentTemplate.WX_UPLOAD_AUDIT_FAIL.code;
			datas=new String[] {result.getErrcode(),result.getErrmsg()};
			break;
		}
		case MpDeployQueryParam.ACT_UPLOAD_CODE: {
			//上传代码
			operateType=MpOperateLogService.OP_TYPE_UPLOAD_CODE;
			templateIds=WxContentTemplate.WX_UPLOAD_CODE_FAIL.code;
			datas=new String[] {String.valueOf(param.getIsSubMerchant()),result.getErrcode(),result.getErrmsg()};
			break;
		}
		case MpDeployQueryParam.SETTING_SUB_MERCHANT: {
			//设置支付方式
			operateType=MpOperateLogService.OP_TYPE_SETTING_SUB_MERCHANT;
			templateIds=WxContentTemplate.WX_SETTING_SUB_MERCHANT_FAIL.code;
			datas=new String[] {result.getErrcode(),result.getErrmsg()};
			break;
		}
		default: {
			operateType=MpOperateLogService.OP_STATE_FAILED;
			templateIds=WxContentTemplate.WX_ERROE.code;
			datas=new String[] {param.getAct(),result.getErrcode(),result.getErrmsg()};
			break;
		}
		}
		if(result.getErrcode().equals("500")) {
			//error错误
			operateType=MpOperateLogService.OP_STATE_FAILED;
			templateIds=WxContentTemplate.WX_ERROE.code;
			datas=new String[] {param.getAct(),result.getErrcode(),result.getErrmsg()};
		}
		operateLogGlobal(mp, operateType, result, templateIds, datas);
	}


	public Integer updateRow(MpAuthShopRecord authShopByShopId) {
		return db().executeUpdate(authShopByShopId);
	}





	/**
	 * 微信回调执行的函数/wechat/notify/app/event/{appId}/callback
	 * @param inMessage
	 * @param appId
	 * @return
	 */
	public WxMpXmlOutMessage AppEventHandler(WxMpXmlMessage inMessage,String appId) {
		/*
		 * WebAppAudit::class, OfficialAccountMessage::class, MessageTrans::class,
		 */
		// 小程序有审核结果通知
		WebAppAudit(inMessage, appId);
		WxMpXmlOutTextMessage wxMessage = OfficialAccountMessage(inMessage, appId);
		WxMpXmlOutMessage messageTrans = MessageTrans(inMessage);
		if(wxMessage==null) {
			logger().debug("准备返回客服消息");
			return messageTrans;
		}
		return wxMessage;
	}


	/**
	 * 小程序有审核结果通知
	 * @param inMessage
	 * @param appId
	 */
	public void WebAppAudit(WxMpXmlMessage inMessage,String appId) {
		processAuditEvent(inMessage, appId);
	}

	public void processAuditEvent(WxMpXmlMessage inMessage, String appId) {
		// processAuditEvent($appId, $message['Event'], $message['Reason']);
		if (inMessage.getMsgType().equals("event") && (inMessage.getEvent().equals("weapp_audit_success")||inMessage.getEvent().equals("weapp_audit_fail"))) {
			logger().debug("小程序有审核结果通知"+inMessage.getEvent());
			MpAuthShopRecord mpRecord = getAuthShopByAppId(appId);
			WxOpenResult wxOpenResult=new WxOpenResult();
			if(mpRecord!=null) {
				Integer bindTemplateId = mpRecord.getBindTemplateId();
				if(inMessage.getEvent().equals("weapp_audit_success")) {
					mpRecord.setAuditState((byte) 2);
					mpRecord.setAuditOkTime(new Timestamp(System.currentTimeMillis()));
					db().executeUpdate(mpRecord);
					wxOpenResult.setErrcode("0");
					operateLogGlobal(mpRecord, MpOperateLogService.OP_TYPE_AUDIT_SUCCESS, wxOpenResult, WxContentTemplate.WX_AUDIT_SUCCESS.code,new String[] {});
					//审核自动发布代码
					try {
						//发布审核成功代码
						publishAuditSuccessCode(appId);
						//调用更新小程序跳转appid可用状态
						AppletsJumpService appletsJumpService = saas.getShopApp(mpRecord.getShopId()).appletsJump;
						appletsJumpService.updateMpJumpAppIDList(bindTemplateId);
						//return true
					} catch (WxErrorException e) {
						e.printStackTrace();
					}

				}else {
					mpRecord.setAuditState((byte) 3);
					mpRecord.setAuditFailReason(inMessage.getReason());
					db().executeUpdate(mpRecord);
					operateLogGlobal(mpRecord, MpOperateLogService.OP_TYPE_AUDIT_FAILED, wxOpenResult, WxContentTemplate.WX_AUDIT_FAIL.code,new String[] {inMessage.getReason()});
				}
			}
		}

	}

	public WxMpXmlOutTextMessage OfficialAccountMessage(WxMpXmlMessage inMessage,String appId) {
		WxMpXmlOutTextMessage wxMessage = processMessage(inMessage, appId);
		return wxMessage;
	}
	/**
	 * 处理公众号消息
	 * @param inMessage
	 * @param appId
	 * @return
	 */
	public WxMpXmlOutTextMessage processMessage(WxMpXmlMessage inMessage,String appId) {
		MpOfficeAccountListVo officeAccountByAppId = saas.shop.officeAccount.getOfficeAccountByAppId(appId);
		WxMpXmlOutTextMessage process=null;
		if (officeAccountByAppId != null) {
			// 是公众号
			try {
				process = processSubscribeEvent(inMessage, appId, officeAccountByAppId);
			} catch (WxErrorException e) {
				logger().debug(e.getMessage(),e);
			}
			if(StringUtils.isEmpty(process.getToUserName())) {
				process=null;
			}
		}else {
			logger().debug("processMessage方法接收的appid "+appId+"在数据库中不存在");
		}
		return process;
	}

	public WxMpXmlOutTextMessage processSubscribeEvent(WxMpXmlMessage inMessage,String appId,MpOfficeAccountListVo officeAccountByAppId) throws WxErrorException {
		//subscribe（订阅）
		WxMpXmlOutTextMessage message = WxMpXmlOutMessage.TEXT().build();
		if(StringUtils.isNotEmpty(inMessage.getEvent())&&inMessage.getEvent().equals("subscribe")) {
			logger().debug("开始绑定公众号");
			//公众号获取用户信息
			WxMpUser userInfo = open().getWxOpenComponentService().getWxMpServiceByAppid(appId).getUserService().userInfo(inMessage.getFromUser());
			logger().debug("用户Openid"+userInfo.getOpenId()+"开始绑定公众号"+appId);
			if(userInfo!=null) {
				MpOfficialAccountUserRecord record=MP_OFFICIAL_ACCOUNT_USER.newRecord();
				record.setOpenid(userInfo.getOpenId());
				record.setAppId(appId);
				record.setSysId(officeAccountByAppId.getSysId());
				record.setSubscribe(userInfo.getSubscribe() ? (byte) 1 : (byte) 0);
				record.setNickname(userInfo.getNickname());
				record.setSex(userInfo.getSex().byteValue());
				record.setLanguage(userInfo.getLanguage());
				record.setCity(userInfo.getCity());
				record.setProvince(userInfo.getProvince());
				record.setCountry(userInfo.getCountry());
				record.setHeadimgurl(userInfo.getHeadImgUrl());
				record.setSubscribeTime(new Timestamp(userInfo.getSubscribeTime() * 1000L));
				record.setUnionid(userInfo.getUnionId());
				saas.shop.officeAccount.addOrUpdateUser(appId, record, userInfo.getUnionId(), userInfo.getOpenId());
				//得到公众号关联的小程序
				Result<MpAuthShopRecord> officialAccountMps = getOfficialAccountMps(appId);
				boolean parseAccountInfo = saas.shop.account.parseAccountInfo(appId, inMessage.getEventKey(), record.getOpenid());
				logger().debug("'parseAccountInfo result "+parseAccountInfo);
				if(parseAccountInfo) {
					logger().debug("用户Openid"+userInfo.getOpenId()+"组装响应消息 欢迎关注， 您可在这里及时接收新订单提醒");
					//packageResponseMsg 组装响应消息 欢迎关注， 您可在这里及时接收新订单提醒'
				    message.setToUserName(userInfo.getOpenId());
				    message.setFromUserName(inMessage.getToUser());
				    message.setContent("欢迎关注，您可在这里及时接收新订单提醒");
				    message.setCreateTime(System.currentTimeMillis() / 1000L);
				    return message;
				}else {
					logger().debug("用户Openid"+userInfo.getOpenId()+"为你精心准备了关注礼品，快来点击查看吧!");
					for(MpAuthShopRecord authShopRecord:officialAccountMps) {
						Record shop = saas.shop.getShop(authShopRecord.getShopId());
						String shopName = shop.get(SHOP.SHOP_NAME);
						String firest="为你精心准备了关注礼品，快来点击查看吧!";
						String page="pages/auth/auth";//String page="pages/auth/auth";pages/index/index
						String content="点击进入小程序";
						//List<WxMpTemplateData> keywordValues = fexMessage(record, firest, shopName, content, page, null);
						ShopApplication shopApp = saas.getShopApp(authShopRecord.getShopId());
						WxUserInfo info=WxUserInfo.builder().mpAppId(appId).mpOpenId(userInfo.getOpenId()).maAppId(authShopRecord.getAppId()).build();
						String[][] data = new String[][] { {firest,"#173177"},{shopName,"#173177"},{Util.getdate("YYYY-MM-dd HH:mm:ss"),"#173177"},{content,"#173177"},{null,"#173177"}};
						RabbitMessageParam param = RabbitMessageParam.builder()
								.mpTemplateData(
										MpTemplateData.builder().config(MpTemplateConfig.PUSHMSG).data(data).build())
								.page(page).shopId(authShopRecord.getShopId())
								.type(RabbitParamConstant.Type.MP_TEMPLE_TYPE).build();
						shopApp.wechatMessageTemplateService.sendMpMessage(param, info);
						saas.taskJobMainService.dispatchImmediately(param, RabbitMessageParam.class.getName(), authShopRecord.getShopId(), TaskJobEnum.SEND_MESSAGE.getExecutionType());
					}
				}
				return message;
			}
		}
		//取消订阅
		if(StringUtils.isNotEmpty(inMessage.getEvent())&&inMessage.getEvent().equals("unsubscribe")) {
			logger().debug("开始解绑");
			WxMpUser userInfo = open().getWxOpenComponentService().getWxMpServiceByAppid(appId).getUserService().userInfo(inMessage.getFromUser());
			logger().debug("用户Openid"+userInfo.getOpenId()+"解绑公众号"+appId);
			if(userInfo!=null) {
				MpOfficialAccountUserRecord record=MP_OFFICIAL_ACCOUNT_USER.newRecord();
				record.setAppId(appId);
				record.setOpenid(userInfo.getOpenId());
				record.setSubscribe((byte)0);
				saas.shop.officeAccount.addOrUpdateUser(appId, record, userInfo.getUnionId(), userInfo.getOpenId());
				logger().debug("用户Openid"+userInfo.getOpenId()+"解绑公众号完成");
				return message;
			}
		}
		return message;
	}


	/**
	 * 组装List<WxMpTemplateData>的信息
	 * @param record
	 * @param firest
	 * @param shopName
	 * @param content
	 * @param page
	 * @param remark
	 * @return
	 */
	private List<WxMpTemplateData> fexMessage(MpOfficialAccountUserRecord record,String firest,String shopName,String content,String page,String remark) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("first", firest);
		map.put("keyword1", shopName);
		map.put("keyword2", Util.getdate("YYYY-MM-dd HH:mm:ss"));
		map.put("keyword3", content);
		map.put("remark", remark);
		List<WxMpTemplateData> keywordValuesDatas=new ArrayList<WxMpTemplateData>();
		Set<String> keySets = map.keySet();
		for(String  keySet:keySets) {
			WxMpTemplateData data=new WxMpTemplateData();
			data.setName(keySet);
			data.setValue(map.get(keySet));
			data.setColor("#173177");
			keywordValuesDatas.add(data);
		}
		return keywordValuesDatas;
	}



	/**
	 * 得到公众号关联的小程序
	 * @param appId
	 * @return
	 */
	public Result<MpAuthShopRecord> getOfficialAccountMps(String appId) {
		return db().selectFrom(MP_AUTH_SHOP).where(MP_AUTH_SHOP.LINK_OFFICIAL_APP_ID.eq(appId)).fetch();
	}


	/**
	 * 消息都转发给客服
	 * @param inMessage
	 * @return
	 */
	public WxMpXmlOutMessage MessageTrans(WxMpXmlMessage inMessage) {
		WxMpXmlOutTextMessage build = WxMpXmlOutMessage.TEXT().build();
		build.setToUserName(inMessage.getFromUser());
		build.setFromUserName(inMessage.getToUser());
		build.setCreateTime((System.currentTimeMillis() / 1000L));
		build.setMsgType(WxConsts.XmlMsgType.TRANSFER_CUSTOMER_SERVICE);
		logger().debug("\n 发给客服的报文：\n{}",build.toXml().toString());
		return build;
	}

	/**
	 * 终止 任务
	 * @param recId
	 * @return
	 */
	public Boolean stopBatchUpload(Integer recId) {
		BackProcessRecord row = saas.shop.backProcessService.getRow(recId);
		if(row==null) {
			return false;
		}
		if(row.getProcessId()==0) {
			return false;
		}
		//判断任务是否可以终止
		if(saas.taskJobMainService.assertExecuting(row.getProcessId())) {
			//更改任务状态为 3 ，终止
            saas.taskJobMainService.updateTaskJobStatus(row.getProcessId(), TaskJobsConstant.STATUS_TERMINATION);
			return true;
		}
		return false;
	}
	
	/**店铺发布列表
	 * 
	 * @param param
	 * @return 
	 * @return
	 */
	public PageResult<ShopMpListVo> getShopMpList(ShopMpListParam param) {
		SelectJoinStep<Record11<String, Integer, Timestamp, String, Timestamp, Timestamp, Integer, Byte, String, String, Byte>> selectFrom = db()
				.select(MP_AUTH_SHOP.APP_ID, MP_AUTH_SHOP.SHOP_ID, MP_AUTH_SHOP.CREATE_TIME, MP_AUTH_SHOP.NICK_NAME,
						MP_AUTH_SHOP.PUBLISH_TIME, MP_AUTH_SHOP.LAST_UPLOAD_TIME, MP_AUTH_SHOP.BIND_TEMPLATE_ID,
						MP_AUTH_SHOP.OPEN_PAY, MP_AUTH_SHOP.PRINCIPAL_NAME, SHOP.SHOP_TYPE, SHOP.IS_ENABLED)
				.from(MP_AUTH_SHOP, SHOP);
		buildOptionsMp(param, selectFrom);
		selectFrom.where(MP_AUTH_SHOP.SHOP_ID.eq(SHOP.SHOP_ID));
		selectFrom.orderBy(SHOP.CREATED.desc());
		selectFrom.orderBy(MP_AUTH_SHOP.SHOP_ID.desc());
		selectFrom.orderBy(MP_AUTH_SHOP.CREATE_TIME.desc());
		PageResult<ShopMpListVo> pageResult = this.getPageResult(selectFrom, param.getCurrentPage(), param.getPageRows(), ShopMpListVo.class);
		for(ShopMpListVo vo:pageResult.dataList) {
			vo.setRenewMoney(saas.shop.renew.getShopRenewTotal(vo.getShopId()));
			Timestamp expireTime = saas.shop.renew.getShopRenewExpireTime(vo.getShopId());
			vo.setExpireTime(expireTime);
			String expireStatus = "1";
			if (expireTime != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(expireTime);
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				if (cal.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
					expireStatus = "0";
				}
			}
			vo.setShopExpireStatus(expireStatus);
			List<MpOperateVo> operateLog = saas.shop.mpOperateLog.getOperateLog(vo.getAppId());
			System.out.println(operateLog);
			if(operateLog.size()>0) {
				MpOperateVo mpOperateVo = operateLog.get(0);
				vo.setStartTime(mpOperateVo.getCreateTime());
				vo.setTemplateId(mpOperateVo.getTemplateId());
				vo.setUserVersion(mpOperateVo.getUserVersion());
				MpOperateVo mpOperateVo2 = operateLog.get(operateLog.size()-1);
				vo.setLastUploadTime(mpOperateVo2.getCreateTime());
				vo.setBindTemplateId(mpOperateVo2.getTemplateId());
				vo.setBindUserVersion(mpOperateVo2.getUserVersion());
			}
		}
		return pageResult;
	}

	
	private void buildOptionsMp(ShopMpListParam param,SelectJoinStep<?> selectFrom) {
		if(StringUtils.isNotEmpty(param.getKeywords())) {
			selectFrom.where(MP_AUTH_SHOP.SHOP_ID.like(likeValue(param.getKeywords())).or(MP_AUTH_SHOP.NICK_NAME.like(likeValue(param.getKeywords())).or(SHOP.SHOP_NAME.like(likeValue(param.getKeywords())))));
		}
		if(StringUtils.isNotEmpty(param.getShopType())) {
			selectFrom.where(SHOP.SHOP_TYPE.eq(param.getShopType()));
		}
		if(param.getOpenPay()!=null) {
			selectFrom.where(MP_AUTH_SHOP.OPEN_PAY.eq(param.getOpenPay()));
		}
		if(param.getIsEnabled()!=null) {
			selectFrom.where(SHOP.IS_ENABLED.eq(param.getIsEnabled()));
		}
	}
}
