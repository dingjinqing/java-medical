package com.vpu.mp.service.shop.member.dao;

import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;

import static org.jooq.impl.DSL.sum;
import static com.vpu.mp.db.shop.Tables.USER_SCORE;

/**
* @author 黄壮壮
* @Date: 2019年10月12日
* @Description: 积分Dao层
*/
@Service
public class ScoreDaoService extends ShopBaseService {
	// 0:未使用 1:已使用 2：已过期 3：已退款
	private final Byte STATUS_UNUSERD=0;
	private final Byte STATUS_USING = 1;
	private final Byte STATUS_USED = 2;
	private final Byte STATUS_EXPIRED = 3; 
	/**
	 * 获取用户累积获得积分
	 * @param id
	 * @return
	 */
	public Integer getUserTotalScore(Integer id) {
		 return db().select(sum(USER_SCORE.SCORE))
				 	.from(USER_SCORE)
				 	.where(USER_SCORE.USER_ID.eq(id))
				 	.and(USER_SCORE.STATUS.notIn(STATUS_EXPIRED))
				 	.and(USER_SCORE.SCORE.greaterThan(0))
				 	.fetchAnyInto(Integer.class);
	}

}
