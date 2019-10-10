package com.vpu.mp.service.shop.user.user;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import static com.vpu.mp.db.shop.Tables.USER_LOGIN_RECORD;

import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.UserLoginRecordRecord;

/**
 * 
 * @author zhaojianqiang
 *
 *         2019年10月10日 下午3:32:40
 */
@Service
public class UserLoginRecordService extends ShopBaseService {

	public void userLoginRecord(Integer userId, UserLoginRecordRecord record) {
		logger().info("记录小程序登录");
		UserLoginRecordRecord res = db().selectFrom(USER_LOGIN_RECORD).where(USER_LOGIN_RECORD.USER_ID.eq(userId))
				.and(USER_LOGIN_RECORD.CREATE_TIME.gt(DSL.currentTimestamp())).fetchAny();
		// 有记录更新登陆次数，没有记录加记录 一小时一条数据
		if (res == null) {
			record.setCount(1);
			int insert = record.insert();
			logger().info("插入小程序登录"+insert);
		} else {
			res.setCount(res.getCount() + 1);
			if (record.getLat() != null && (!record.getLat().equals(res.getLat()))) {
				res.setLat(record.getLat());
				res.setLng(record.getLng());
			}
			int update = res.update();
			logger().info("更新小程序登录"+update);
		}
	}

}
