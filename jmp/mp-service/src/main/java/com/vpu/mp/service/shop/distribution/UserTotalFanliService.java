package com.vpu.mp.service.shop.distribution;

import com.vpu.mp.db.shop.tables.UserTotalFanli;
import com.vpu.mp.db.shop.tables.records.UserTotalFanliRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.pojo.shop.distribution.UserTotalFanliVo;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.vpu.mp.db.shop.Tables.DISTRIBUTOR_LEVEL;
import static com.vpu.mp.db.shop.Tables.USER_DETAIL;
import static com.vpu.mp.db.shop.Tables.USER_TOTAL_FANLI;
/**
 * @author 黄壮壮
 * 	 用户返利服务
 */
@Service
public class UserTotalFanliService extends ShopBaseService {

    UserTotalFanli TABLE = USER_TOTAL_FANLI;

	public UserTotalFanliVo getUserRebate(Integer userId) {
		  UserTotalFanliVo res = db().select(USER_DETAIL.USERNAME,USER_DETAIL.USER_AVATAR,USER_TOTAL_FANLI.asterisk())
			.from(USER_DETAIL)
			.leftJoin(USER_TOTAL_FANLI).on(USER_DETAIL.USER_ID.eq(USER_TOTAL_FANLI.USER_ID))
			.leftJoin(DISTRIBUTOR_LEVEL).on(DSL.coerce(DISTRIBUTOR_LEVEL.LEVEL_ID,Integer.class).eq(USER_TOTAL_FANLI.USER_ID))
			.where(USER_DETAIL.USER_ID.eq(userId))
			.fetchAnyInto(UserTotalFanliVo.class);
		  return res;
	}
	
	/**
	 * 	累计获得佣金数
	 */
	public BigDecimal getTotalMoney(Integer userId) {
		UserTotalFanliVo userRebate = getUserRebate(userId);
		if(userRebate != null && userRebate.getTotalMoney() != null) {
			return userRebate.getTotalMoney();
		}else {
			return  BigDecimal.ZERO;
		}
	}

    /**
     *  @param userId
     * @param money
     * @param inviteCount
     */
    public void updateTotalRebate(Integer userId, BigDecimal money, int inviteCount) {
        UserTotalFanliRecord record = db().selectFrom(TABLE).where(TABLE.USER_ID.eq(userId).and(TABLE.USER_ID.eq(userId))).fetchOne();
        if(record == null) {
            UserTotalFanliRecord insert = db().newRecord(TABLE);
            insert.setUserId(userId);
            insert.setMobile("");
            insert.setSublayerNumber(inviteCount);
            insert.setTotalMoney(money);
            insert.insert();
        }else {
            record.setSublayerNumber(inviteCount);
            record.setTotalMoney(BigDecimalUtil.add(record.getTotalMoney(), money));
            record.update();
        }
    }
}
