package com.vpu.mp.service.shop.user.user;

import com.vpu.mp.db.shop.tables.User;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record4;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MpOfficialAccountUserRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;

import java.util.List;

import static com.vpu.mp.db.shop.tables.MpOfficialAccountUser.MP_OFFICIAL_ACCOUNT_USER;
import static com.vpu.mp.db.shop.tables.User.USER;

@Service
public class MpOfficialAccountUserByShop extends ShopBaseService{

	/**
	 * 这个的店铺库的。注意
	 * @param appId
	 * @param record
	 * @param unionId
	 * @param openId
	 */
	public void addOrUpdateUser(String appId, MpOfficialAccountUserRecord record, String unionId, String openId) {
		MpOfficialAccountUserRecord fetchAny = MP_OFFICIAL_ACCOUNT_USER.newRecord();
		if (StringUtils.isEmpty(unionId)) {
			// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
			fetchAny = db().selectFrom(MP_OFFICIAL_ACCOUNT_USER)
					.where(MP_OFFICIAL_ACCOUNT_USER.APP_ID.eq(appId).and(MP_OFFICIAL_ACCOUNT_USER.OPENID.eq(openId)))
					.fetchAny();
		} else {
			fetchAny = db().selectFrom(MP_OFFICIAL_ACCOUNT_USER).where(MP_OFFICIAL_ACCOUNT_USER.APP_ID.eq(appId)
					.and(MP_OFFICIAL_ACCOUNT_USER.OPENID.eq(openId)).and(MP_OFFICIAL_ACCOUNT_USER.UNIONID.eq(unionId)))
					.fetchAny();
		}
		if (fetchAny == null) {
			// 插入
			db().executeInsert(record);
		} else {
			// 更新
			record.setRecId(fetchAny.getRecId());
			db().executeUpdate(record);
		}
	}

	public List<Record4<Integer,String,String,String>> getAccountUserListByUserIds(List<Integer> userIds){
	    return db().select(
	            USER.USER_ID,
                MP_OFFICIAL_ACCOUNT_USER.OPENID,
                MP_OFFICIAL_ACCOUNT_USER.UNIONID,
                MP_OFFICIAL_ACCOUNT_USER.APP_ID)
            .from(MP_OFFICIAL_ACCOUNT_USER)
            .leftJoin(USER).on(USER.WX_UNION_ID.eq(MP_OFFICIAL_ACCOUNT_USER.UNIONID))
            .where(MP_OFFICIAL_ACCOUNT_USER.SUBSCRIBE.eq((byte)1))
            .and(USER.USER_ID.in(userIds))
            .fetch();
    }

}
