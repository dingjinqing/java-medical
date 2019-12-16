package com.vpu.mp.service.saas.shop.official;

import static com.vpu.mp.db.main.tables.MpOfficialAccountUser.MP_OFFICIAL_ACCOUNT_USER;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.MpOfficialAccountUserRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.config.group.ShopRoleAddListVo;

@Service
public class MpOfficialAccountUserService extends MainBaseService {

    public List<MpOfficialAccountUserRecord> getAccountUserListByUnionIds(List<String> unionIds) {
        return db().select(MP_OFFICIAL_ACCOUNT_USER.OPENID,MP_OFFICIAL_ACCOUNT_USER.UNIONID,MP_OFFICIAL_ACCOUNT_USER.APP_ID)
            .from(MP_OFFICIAL_ACCOUNT_USER)
            .where(MP_OFFICIAL_ACCOUNT_USER.UNIONID.in(unionIds))
            .and(MP_OFFICIAL_ACCOUNT_USER.SUBSCRIBE.eq((byte)1))
            .fetchInto(MP_OFFICIAL_ACCOUNT_USER);
    }
    
    public MpOfficialAccountUserRecord getUser(String appId,String openId) {
    	return db().selectFrom(MP_OFFICIAL_ACCOUNT_USER).where(MP_OFFICIAL_ACCOUNT_USER.APP_ID.eq(appId).and(MP_OFFICIAL_ACCOUNT_USER.OPENID.eq(openId))).fetchAny();
    }
    
    public PageResult<ShopRoleAddListVo> getUserList(PageResult<ShopRoleAddListVo> accountRolePageList,String appId) {
    	for(ShopRoleAddListVo sAddListVo:accountRolePageList.dataList) {
    		if(sAddListVo.getOfficialOpenId()!=null) {
    			MpOfficialAccountUserRecord user = getUser(appId, sAddListVo.getOfficialOpenId());
    			sAddListVo.setOfficialNickName(user.getNickname());
    			sAddListVo.setHeadImgUrl(user.getHeadimgurl());    			
    		}
    	}
    	return accountRolePageList;
    }
    
    
    public MpOfficialAccountUserRecord getAccountUserListByRecid(Integer recId) {
        return db().select(MP_OFFICIAL_ACCOUNT_USER.OPENID,MP_OFFICIAL_ACCOUNT_USER.UNIONID,MP_OFFICIAL_ACCOUNT_USER.APP_ID)
            .from(MP_OFFICIAL_ACCOUNT_USER)
            .where(MP_OFFICIAL_ACCOUNT_USER.REC_ID.eq(recId))
            .and(MP_OFFICIAL_ACCOUNT_USER.SUBSCRIBE.eq((byte)1))
            .fetchAnyInto(MP_OFFICIAL_ACCOUNT_USER);
    }
    
    
    
    public void getOpenIdFromMpOpenId(String officialAccountAppId,String mpAppId) {
    	
    }
}
