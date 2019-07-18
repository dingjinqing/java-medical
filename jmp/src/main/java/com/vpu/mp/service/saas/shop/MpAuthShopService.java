package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.MpAuthShop.MP_AUTH_SHOP;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import com.vpu.mp.db.main.tables.records.MpAuthShopRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Util;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenMaService;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizationInfo;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizerInfo;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 新国
 *
 */
@Service
@Scope("prototype")
public class MpAuthShopService extends BaseService {

	public MpAuthShopRecord addMpAuthAccountInfo(String appId, Integer shopId) throws WxErrorException {

		WxOpenAuthorizerInfoResult authInfo = open().getWxOpenComponentService().getAuthorizerInfo(appId);
		logger().debug("authInfo:", authInfo);
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
	 * 获取小程序服务
	 * 
	 * @param shopId
	 * @return
	 */
	public WxOpenMaService getMpPayClientByShopId(Integer shopId) {
		MpAuthShopRecord mp = getAuthShopByShopId(shopId);
		if (mp == null || mp.getIsAuthOk() == (byte) 1) {
			return null;
		}
		return open().getWxOpenComponentService().getWxMaServiceByAppid(mp.getAppId());
	}

	/**
	 * 获取小程序服务
	 * 
	 * @param appId
	 * @return
	 */
	public WxOpenMaService getMpPayClientByAppId(String appId) {
		MpAuthShopRecord mp = getAuthShopByAppId(appId);
		if (mp == null || mp.getIsAuthOk() == (byte) 1) {
			return null;
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
		return db().update(MP_AUTH_SHOP)
				.set(MP_AUTH_SHOP.PAY_MCH_ID, mchId)
				.set(MP_AUTH_SHOP.PAY_KEY, key)
				.set(MP_AUTH_SHOP.PAY_CERT_CONTENT, certContent)
				.set(MP_AUTH_SHOP.PAY_KEY_CONTENT, keyContent)
				.where(MP_AUTH_SHOP.APP_ID.eq(appId))
				.execute();
	}


}
