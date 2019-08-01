package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.MpAuthShop.MP_AUTH_SHOP;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.main.tables.MpAuthShop;
import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.trade.WxpayConfigParam;
import com.vpu.mp.service.pojo.shop.config.trade.WxpaySearchParam;
import com.vpu.mp.service.pojo.shop.image.UploadPath;
import com.vpu.mp.service.saas.image.SystemImageService;
import com.vpu.mp.service.wechat.ma.bean.MpWxMaOpenCommitExtInfo;

import cn.binarywang.wx.miniapp.util.json.WxMaGsonBuilder;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenMaService;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizationInfo;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizerInfo;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import me.chanjar.weixin.open.bean.result.WxOpenMaCategoryListResult;
import me.chanjar.weixin.open.bean.result.WxOpenMaDomainResult;
import me.chanjar.weixin.open.bean.result.WxOpenMaPageListResult;
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

	public MpAuthShopRecord addMpAuthAccountInfo(String appId, Integer shopId) throws WxErrorException {

		WxOpenAuthorizerInfoResult authInfo = open().getWxOpenComponentService().getAuthorizerInfo(appId);
		MpAuthShopRecord record = db().newRecord(MP_AUTH_SHOP);
		WxOpenAuthorizationInfo authorizationInfo = authInfo.getAuthorizationInfo();
		WxOpenAuthorizerInfo authorizerInfo = authInfo.getAuthorizerInfo();
		record.setAppId(authorizationInfo.getAuthorizerAppid());
		record.setShopId((shopId));
		record.setNickName(authorizerInfo.getNickName());
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
		return (mp != null && mp.getIsAuthOk() == (byte) 1);
	}

	/**
	 * 店铺是否授权成功
	 * 
	 * @param shopId
	 * @return
	 */
	public Boolean isAuthOk(Integer shopId) {
		MpAuthShopRecord mp = getAuthShopByShopId(shopId);
		return (mp != null && mp.getIsAuthOk() == (byte) 1);
	}

	/**
	 * 获取小程序服务
	 * 
	 * @param shopId
	 * @return
	 */
	public WxOpenMaService getMaServiceByShopId(Integer shopId) {
		MpAuthShopRecord mp = getAuthShopByShopId(shopId);
		assert (mp != null && mp.getIsAuthOk() == (byte) 1);
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
		assert (mp != null && mp.getIsAuthOk() == (byte) 1);
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
		return db().fetchAny(MP_AUTH_SHOP, MP_AUTH_SHOP.SHOP_ID.eq((shopId)));
	}

	/**
	 * 得到解析json后的对象
	 * 
	 * @param record
	 * @return
	 */
	public Map<String, Object> getParseMpInfo(MpAuthShopRecord record) {
		Map<String, Object> mp = record.intoMap();
		mp.put("tester", Util.parseJson((String) mp.getOrDefault("tester", "[]"), ArrayList.class));
		mp.put("category", Util.parseJson((String) mp.getOrDefault("category", "[]"), ArrayList.class));
		mp.put("page_cfg", Util.parseJson((String) mp.getOrDefault("page_cfg", "[]"), ArrayList.class));
		mp.put("func_info", Util.parseJson((String) mp.getOrDefault("func_info", "[]"), ArrayList.class));
		return mp;
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
		String action = "add";
		String[] httpsDomains = { domainConfig.imageUrl("/", "https") };
		String[] wssDomains = { domainConfig.imageUrl("/", "wss") };
		WxOpenMaService maService = this.getMaServiceByAppId(appId);
		WxOpenMaDomainResult result = maService.modifyDomain(action, Arrays.asList(httpsDomains),
				Arrays.asList(wssDomains), Arrays.asList(httpsDomains), Arrays.asList(httpsDomains));
		maService.setWebViewDomain(action, Arrays.asList(httpsDomains));
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
	public WxOpenResult uploadCode(String appId, Long templateId, String userVersion, String userDesc)
			throws WxErrorException {
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
		params.addProperty("user_version", userVersion);
		params.addProperty("user_desc", userDesc);
		params.addProperty("ext_json", Util.toJson(extInfo));
		String response = maService.post(WxOpenMaService.API_CODE_COMMIT, Util.toJson(params));
		return WxMaGsonBuilder.create().fromJson(response, WxOpenResult.class);
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
		return result;
	}

	public String getMpQrCode(String appId, WxOpenAuthorizerInfo authorizerInfo) {
		String qrcodeUrl = authorizerInfo.getQrcodeUrl();
		String path = "pages/bottom/bottom";
		String filename = appId + "_" + Util.md5(path) + ".jpg";
		String relativePath = "upload/saas/mp/app_code/" + filename;
		Boolean addImgeToUp = saas.sysImage.addImgeToUp(qrcodeUrl, relativePath);
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
			WxOpenResult result = new WxOpenResult();
			result.setErrcode("0");
			return result;
		}
		WxOpenMaService maService = this.getMaServiceByAppId(appId);
		WxOpenResult result = maService.unbindTester(wechatId);
		if (result.isSuccess()) {
			testers.remove(wechatId);
			mp.setTester(Util.toJson(testers));
			mp.update();
		}
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
	public String getTestQrCode(String appId) throws WxErrorException, IOException {
		WxOpenMaService maService = this.getMaServiceByAppId(appId);
		String pagePath = "pages/bottom/bottom";
		File file = maService.getTestQrcode(pagePath, null);
		String relativePath = "upload/saas/qr/" + appId + ".jpg";
		try {
			image.uploadToUpYun(relativePath, file);
		} catch (Exception e) {
			return null;
		}
		MpAuthShopRecord mp = this.getAuthShopByAppId(appId);
		mp.setTestQrPath(relativePath);
		mp.update();
		return relativePath;
	}

	/**
	 * 得到可选类目
	 * @param appId
	 * @return 
	 * @throws WxErrorException
	 */
	public WxOpenMaCategoryListResult getCategory(String appId) throws WxErrorException {
		WxOpenMaService maService = this.getMaServiceByAppId(appId);
		WxOpenMaCategoryListResult result = maService.getCategoryList();
		MpAuthShopRecord mp = this.getAuthShopByAppId(appId);
		mp.setCategory(Util.toJson(result));
		mp.update();
		return result;
	}
	
	/**
	 * 得到小程序页面配置列表
	 * @param appId
	 * @return
	 * @throws WxErrorException
	 */
	public WxOpenMaPageListResult getPage(String appId) throws WxErrorException {
		WxOpenMaService maService = this.getMaServiceByAppId(appId);
		WxOpenMaPageListResult result = maService.getPageList();
		MpAuthShopRecord mp = this.getAuthShopByAppId(appId);
		mp.setCategory(Util.toJson(result));
		mp.update();
		return result;
	}
	
}
