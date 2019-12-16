package com.vpu.mp.service.shop.distribution;

import static com.vpu.mp.db.shop.Tables.USER_DETAIL;
import static com.vpu.mp.db.shop.Tables.USER_TOTAL_FANLI;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.distribution.UserTotalFanliVo;
/**
 * @author 黄壮壮
 * 	用户返利服务
 */
@Service
public class UserTotalFanliService extends ShopBaseService {
	private static final String UserTotalFanliVo = null;

	public UserTotalFanliVo getUserRebate(Integer userId) {
		  return db().select(USER_DETAIL.USERNAME,USER_DETAIL.USER_AVATAR,USER_TOTAL_FANLI.asterisk())
			.from(USER_DETAIL)
			.leftJoin(USER_TOTAL_FANLI).on(USER_DETAIL.USER_ID.eq(USER_TOTAL_FANLI.USER_ID))
			//TODO .leftJoin(DISTRIBUTOR_LEVEL.on((DISTRIBUTOR_LEVEL.LEVEL_ID.eq(USER_TOTAL_FANLI.USER_ID)))
			.where(USER_DETAIL.USER_ID.eq(userId))
			.fetchAnyInto(UserTotalFanliVo.class);
	}
	
	/**
	 * 	累计获得佣金数
	 */
	public BigDecimal getTotalMoney(Integer userId) {
		UserTotalFanliVo userRebate = getUserRebate(userId);
		return userRebate != null ? userRebate.getTotalMoney(): BigDecimal.ZERO;
	}
}
