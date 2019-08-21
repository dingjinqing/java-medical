package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.MpOfficialAccount.MP_OFFICIAL_ACCOUNT;
import static com.vpu.mp.db.main.tables.ShopAccount.SHOP_ACCOUNT;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectOnConditionStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.MpOfficialAccountRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.shop.officeAccount.MpOAPayManageParam;
import com.vpu.mp.service.pojo.saas.shop.officeAccount.MpOfficeAccountListParam;
import com.vpu.mp.service.pojo.saas.shop.officeAccount.MpOfficeAccountListVo;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizationInfo;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizerInfo;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;

/**
 * 公众号相关
 * 
 * @author zhaojianqiang
 *
 *         2019年8月21日 上午10:07:55
 */
@Service
public class ShopOfficialAccount extends MainBaseService {

	/**
	 * 获取数据库公众号列表
	 * 
	 * @param oaListParam
	 * @return
	 */
	public PageResult<MpOfficeAccountListVo> getPageList(MpOfficeAccountListParam oaListParam) {
		SelectOnConditionStep<Record> select = db().select(MP_OFFICIAL_ACCOUNT.asterisk()).from(MP_OFFICIAL_ACCOUNT)
				.innerJoin(SHOP_ACCOUNT).on(MP_OFFICIAL_ACCOUNT.SYS_ID.eq(SHOP_ACCOUNT.SYS_ID));

		select.where(MP_OFFICIAL_ACCOUNT.SYS_ID.eq(oaListParam.getSysId()));
		select.orderBy(MP_OFFICIAL_ACCOUNT.CREATE_TIME.desc());

		PageResult<MpOfficeAccountListVo> pageResult = this.getPageResult(select, oaListParam.getCurrentPage(),
				oaListParam.getPageRows(), MpOfficeAccountListVo.class);
		return pageResult;
	}

	/**
	 * 单个公众号的信息
	 * 
	 * @param appId
	 * @param sysId
	 * @return
	 */
	public MpOfficeAccountListVo getOfficeAccountByAppIdAndsysId(String appId, Integer sysId) {
		List<MpOfficeAccountListVo> fetch = db().select(MP_OFFICIAL_ACCOUNT.asterisk()).from(MP_OFFICIAL_ACCOUNT)
				.innerJoin(SHOP_ACCOUNT).on(MP_OFFICIAL_ACCOUNT.SYS_ID.eq(SHOP_ACCOUNT.SYS_ID))
				.where(MP_OFFICIAL_ACCOUNT.SYS_ID.eq(sysId).and(MP_OFFICIAL_ACCOUNT.APP_ID.eq(appId)))
				.fetchInto(MpOfficeAccountListVo.class);
		return fetch != null && !fetch.isEmpty() ? fetch.get(0) : null;
	}
	
	public Result<MpOfficialAccountRecord> getSamePrincipalOffice(String principalName) {
		return db().fetch(MP_OFFICIAL_ACCOUNT,
				MP_OFFICIAL_ACCOUNT.PRINCIPAL_NAME.eq(principalName).and(MP_OFFICIAL_ACCOUNT.IS_AUTH_OK.eq((byte) 1)));
	}

	/**
	 * 提现配置
	 * 
	 * @param oaParam
	 * @return
	 */
	public Integer updatePayInfo(MpOAPayManageParam oaParam) {
		MpOfficialAccountRecord newRecord = MP_OFFICIAL_ACCOUNT.newRecord();
		FieldsUtil.assignNotNull(oaParam, newRecord);
		return db().executeUpdate(newRecord);
	}

	public MpOfficeAccountListVo getOfficeAccountByAppId(String appId) {
		List<MpOfficeAccountListVo> fetch = db().select(MP_OFFICIAL_ACCOUNT.asterisk()).from(MP_OFFICIAL_ACCOUNT)
				.innerJoin(SHOP_ACCOUNT).on(MP_OFFICIAL_ACCOUNT.SYS_ID.eq(SHOP_ACCOUNT.SYS_ID))
				.where((MP_OFFICIAL_ACCOUNT.APP_ID.eq(appId))).fetchInto(MpOfficeAccountListVo.class);
		return fetch != null && !fetch.isEmpty() ? fetch.get(0) : null;
	}

	/**
	 * 添加微信公众号授权
	 * 
	 * @return
	 */
	public MpOfficialAccountRecord addMpOfficialAccountInfo(Integer sysId,WxOpenAuthorizerInfoResult authorizerInfoResult) {
		WxOpenAuthorizationInfo authorizationInfo = authorizerInfoResult.getAuthorizationInfo();
		String authorizerAppid = authorizationInfo.getAuthorizerAppid();
		WxOpenAuthorizerInfo authorizerInfo = authorizerInfoResult.getAuthorizerInfo();
		MpOfficialAccountRecord officeRecord = MP_OFFICIAL_ACCOUNT.newRecord();
		// 授权方认证类型，-1代表未认证，0代表微信认证，1代表新浪微博认证，2代表腾讯微博认证，3代表已资质认证通过但还未通过名称认证，
		// 4代表已资质认证通过、还未通过名称认证，但通过了新浪微博认证，5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
		Integer verifyTypeInfo = authorizerInfo.getVerifyTypeInfo();

		// 授权方公众号类型，0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号
		Integer serviceTypeInfo = authorizerInfo.getServiceTypeInfo();
		// 二维码图片的URL，开发者最好自行也进行保存
		String qrcodeUrl = authorizerInfo.getQrcodeUrl();
		String mpQrCode = getMpQrCode(authorizerAppid, qrcodeUrl);
		officeRecord.setAppId(authorizerAppid);
		officeRecord.setNickName(authorizerInfo.getNickName());
		officeRecord.setUserName(authorizerInfo.getUserName());
		officeRecord.setAlias(authorizerInfo.getAlias());
		officeRecord.setVerifyTypeInfo(String.valueOf(verifyTypeInfo));
		officeRecord.setHeadImg(authorizerInfo.getHeadImg());
		officeRecord.setFuncInfo(Util.toJson(authorizationInfo.getFuncInfo()));
		officeRecord.setIsAuthOk((byte) 1);
		officeRecord.setLastAuthTime(new Timestamp(System.currentTimeMillis()));
		officeRecord.setOpenPay(authorizerInfo.getBusinessInfo().get("open_pay").byteValue());
		officeRecord.setOpenCard(authorizerInfo.getBusinessInfo().get("open_card").byteValue());
		officeRecord.setAuthorizerInfo(Util.toJson(authorizerInfo));
		officeRecord.setAuthorizationInfo(Util.toJson(authorizationInfo));
		officeRecord.setPrincipalName(authorizerInfo.getPrincipalName());
		// php上是 'account_type' => $verifyType == -1 ? ($serviceType == 2 ? 2 : 0) :($serviceType == 2 ? 3 : 1)
		officeRecord.setAccountType((byte) (verifyTypeInfo == -1 ? (serviceTypeInfo == 2 ? 2 : 0) : (serviceTypeInfo == 2 ? 3 : 1)));
		officeRecord.setSysId(sysId);
		officeRecord.setQrcodeUrl(mpQrCode);
		if (getOfficeAccountByAppId(authorizerAppid) == null) {
			// 插入
			System.out.println("插入");
			db().executeInsert(officeRecord);
		} else {
			// 更新
			System.out.println("更新");
			db().executeUpdate(officeRecord);
		}
		return officeRecord;

	}

	/**
	 * 绑定同一主体公众号和小程序到开放平台账号 ,个人账号不支持绑定
	 * 
	 * @param appId
	 */
	public void bindAllSamePrincipalOpenAppId(MpOfficialAccountRecord record) throws WxErrorException {
		System.out.println("进来"+record);
		Result<MpOfficialAccountRecord> samePrincipalMpApps = getSamePrincipalOffice(record.getPrincipalName());
		String openAppId = null;
		// 遍历所有主体相同的号，查找不为空的openAppId
		for (MpOfficialAccountRecord mShopRecord : samePrincipalMpApps) {
			if (!StringUtils.isEmpty(mShopRecord.getBindOpenAppId())) {
				openAppId = mShopRecord.getBindOpenAppId();
				break;
			}
		}
		for (MpOfficialAccountRecord mRecord : samePrincipalMpApps) {
			openAppId =saas.shop.mp.bindOpenAppId(false,mRecord.getAppId(), openAppId);
			if (!openAppId.equals(mRecord.getBindOpenAppId())) {
				// 更新数据库
				mRecord.setBindOpenAppId(openAppId);
				System.out.println("更新第三方 更新数据库");
				db().executeUpdate(mRecord);
			}
		}
	}

	public String getMpQrCode(String appId, String qrcodeUrl) {
		String path = "upload/mini_main/qrcode/";
		String filename = "qrcode_" + getDate("yyyyMMddHHmmss") + "_" + getRandom(10000, 99999) + ".png";
		String relativePath = "public/" + path + filename;
		Boolean addImgeToUp = false;
		try {
			addImgeToUp = saas.sysImage.addImgeToUp(qrcodeUrl, relativePath);
		} catch (Exception e) {
			logger().error("公众号" + appId + "头像上传又拍云失败");
			e.printStackTrace();
		}
		logger().debug("公众号" + appId + "头像上传又拍云" + addImgeToUp);
		return relativePath;
	}

	protected String getRandom(int min, int max) {
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return String.valueOf(s);
	}

	protected String getDate(String patter) {
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(patter);
		String date = dtf.format(localDate);
		return date;
	}

}
