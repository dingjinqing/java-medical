package com.vpu.mp.service.shop.user.cart.dao;

import static com.vpu.mp.db.shop.Tables.USER_CART_RECORD;

import java.sql.Timestamp;
import java.util.List;

import org.jooq.Result;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.UserCartRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;

/**
* @author 黄壮壮
*/
@Service
public class UserCartDao extends ShopBaseService {
	/**
	 * 在时间之后有加购行为的用户id列表
	 */
	public List<Integer> getUserIdFromCartStartTime(Timestamp time) {
		return db().select(USER_CART_RECORD.USER_ID).from(USER_CART_RECORD).where(USER_CART_RECORD.CREATE_TIME.ge(time))
				.groupBy(USER_CART_RECORD.USER_ID).fetchInto(Integer.class);
	}

	/**
	 * 在时间之前有加购行为的用户Id列表
	 */
	public List<Integer> getUserIdUtilToCartEndTime(Timestamp time) {
		 return db().select(USER_CART_RECORD.USER_ID).from(USER_CART_RECORD).where(USER_CART_RECORD.CREATE_TIME.le(time))
				.groupBy(USER_CART_RECORD.USER_ID).fetchInto(Integer.class);
	}
}
