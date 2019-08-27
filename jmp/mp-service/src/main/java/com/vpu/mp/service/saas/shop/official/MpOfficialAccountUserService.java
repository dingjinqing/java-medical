package com.vpu.mp.service.saas.shop.official;

import com.vpu.mp.db.main.tables.MpOfficialAccountUser;
import com.vpu.mp.db.main.tables.records.MpOfficialAccountUserRecord;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.MainBaseService;

import java.util.List;

import static com.vpu.mp.db.main.tables.MpOfficialAccountUser.MP_OFFICIAL_ACCOUNT_USER;

@Service
public class MpOfficialAccountUserService extends MainBaseService {

    public List<MpOfficialAccountUserRecord> getAccountUserListByUnionIds(List<String> unionIds) {
        return db().select(MP_OFFICIAL_ACCOUNT_USER.OPENID,MP_OFFICIAL_ACCOUNT_USER.UNIONID,MP_OFFICIAL_ACCOUNT_USER.APP_ID)
            .from(MP_OFFICIAL_ACCOUNT_USER)
            .where(MP_OFFICIAL_ACCOUNT_USER.UNIONID.in(unionIds))
            .and(MP_OFFICIAL_ACCOUNT_USER.SUBSCRIBE.eq((byte)1))
            .fetchInto(MP_OFFICIAL_ACCOUNT_USER);
    }
}
