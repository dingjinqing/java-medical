package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.MpAuthShop.MP_AUTH_SHOP;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.JsonObject;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.main.tables.MpAuthShop;
import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.db.main.tables.records.MpVersionRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.trade.WxpayConfigParam;
import com.vpu.mp.service.pojo.shop.config.trade.WxpaySearchParam;
import com.vpu.mp.service.saas.image.SystemImageService;
import com.vpu.mp.service.wechat.api.WxOpenAccountService;
import com.vpu.mp.service.wechat.bean.ma.MpWxMaOpenCommitExtInfo;
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
		record.setLastUploadTime(Timestamp.valueOf(LocalDateTime.now()));
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
		return db().fetchAny(MP_AUTH_SHOP, MP_AUTH_SHOP.APP_ID.eq(appId));
	}

	/**
	 * 通过shopId得到小程序信息
	 * 
	 * @param shopId
	 * @return
	 */
	public MpAuthShopRecord getAuthShopByShopId(Integer shopId) {
		MpAuthShopRecord fetchAny = db().fetchAny(MP_AUTH_SHOP, MP_AUTH_SHOP.SHOP_ID.eq((shopId)));
		fetchAny.setQrcodeUrl(image.imageUrl(fetchAny.getQrcodeUrl())); 
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
		String[] httpsDomains = { domainConfig.imageUrl("/", "https") };
		String[] wssDomains = { domainConfig.imageUrl("/", "wss") };
		WxOpenMaService maService = this.getMaServiceByAppId(appId);
		WxOpenMaDomainResult result = maService.modifyDomain(action, Arrays.asList(httpsDomains),
				Arrays.asList(wssDomains), Arrays.asList(httpsDomains), Arrays.asList(httpsDomains));
		String noNewDomainCode = "85017";
		if (result.isSuccess() || noNewDomainCode.equals(result.getErrcode())) {
			// 85017 : 没有新增域名，请确认小程序已经添加了域名或该域名是否没有在第三方平台添加
			maService.setWebViewDomain(action, Arrays.asList(httpsDomains));
			mp.setIsModifyDomain((byte) 1);
			mp.update();
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

		/**
		 * TODO: add setNavigateToMiniProgramAppIdList
		 * extInfo.setNavigateToMiniProgramAppIdList(navigateToMiniProgr++amAppIdList);
		 */

		JsonObject params = new JsonObject();
		params.addProperty("template_id", templateId);
		params.addProperty("user_version", version.getUserVersion());
		params.addProperty("user_desc", version.getUserDesc());
		params.addProperty("ext_json", Util.toJson(extInfo));
		String response = maService.post(WxOpenMaService.API_CODE_COMMIT, Util.toJson(params));
		WxOpenResult result = WxMaGsonBuilder.create().fromJson(response, WxOpenResult.class);
		operateLog(mp, MpOperateLogService.OP_TYPE_UPLOAD_CODE, result);
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
		MpAuthShopRecord mp = this.getAuthShopByAppId(appId);
		List<String> testers = StringUtils.isBlank(mp.getTester()) ? new ArrayList<>()
				: Util.parseJson(mp.getTester(), new TypeReference<List<String>>() {
				});
		WxOpenMaService maService = this.getMaServiceByAppId(appId);
		WxOpenResult result = maService.bindTester(wechatId);
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
			logger().debug("appId" + appId +"获取小程序二维码失败");
			e.printStackTrace();
		} catch (Exception e) {
			logger().debug("appId" + appId + "头像上传又拍云失败");
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
			mp.setCategory(Util.toJson(result.getPageList()));
			mp.update();
		}
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
		if (StringUtils.isBlank(mp.getCategory())) {
			categoryList = Util.parseJson(mp.getCategory(), new TypeReference<List<WxOpenMaCategory>>() {
			});
		} else {
			WxOpenMaCategoryListResult category = this.getCategory(appId);
			categoryList = category.getCategoryList();
		}

		String pagePath = "pages/bottom/bottom";
		audit.setPagePath(pagePath);
		audit.setFirstClass(categoryList.get(0).getFirstClass());
		audit.setTag(categoryList.get(0).getFirstClass());
		WxOpenMaSubmitAuditMessage submitAuditMessage = new WxOpenMaSubmitAuditMessage();
		List<WxOpenMaSubmitAudit> itemList = new ArrayList<>();
		itemList.add(audit);
		submitAuditMessage.setItemList(itemList);
		WxOpenMaSubmitAuditResult result = maService.submitAudit(submitAuditMessage);
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
		WxOpenMaService maService = this.getMaServiceByAppId(appId);
		WxOpenResult result = maService.releaesAudited();
		operateLog(mp, MpOperateLogService.OP_TYPE_PUBLISH_CODE, result);
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
		if (mp.getAuditId() != null && mp.getAuditState().equals(AUDIT_STATE_AUDITING)) {
			WxOpenMaService maService = this.getMaServiceByAppId(appId);
			WxOpenMaQueryAuditResult result = maService.getAuditStatus(mp.getAuditId().longValue());
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
		return null;
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

}
