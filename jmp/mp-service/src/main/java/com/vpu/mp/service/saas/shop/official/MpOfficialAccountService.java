package com.vpu.mp.service.saas.shop.official;

import org.springframework.stereotype.Service;

import static com.vpu.mp.db.main.tables.MpOfficialAccount.MP_OFFICIAL_ACCOUNT;

import com.vpu.mp.db.main.tables.records.MpOfficialAccountRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;

import me.chanjar.weixin.mp.api.WxMpService;

@Service
public class MpOfficialAccountService extends MainBaseService {


	/**
	 * 得到公众号的服务
	 * @param appId
	 * @return
	 */
	public WxMpService getOfficialAccountClient(String appId) {
		MpOfficialAccountRecord account = getOfficialAccountByAppid(appId);
		assert(account !=null &&account.getIsAuthOk() == (byte)1);
		return open.getWxOpenComponentService().getWxMpServiceByAppid(appId);
	}

	/**
	 * 
	 * @param appId
	 * @return
	 */
	public MpOfficialAccountRecord  getOfficialAccountByAppid(String appId){
		return db().fetchAny(MP_OFFICIAL_ACCOUNT,MP_OFFICIAL_ACCOUNT.APP_ID.eq(appId));
	}
	
	/**
	 * 判断是否授权
	 * @param appId
	 * @return
	 */
	public Boolean isAuthOk(String appId) {
		MpOfficialAccountRecord account = getOfficialAccountByAppid(appId);
		return (account !=null &&account.getIsAuthOk() == (byte)1);
	}
	   
}
