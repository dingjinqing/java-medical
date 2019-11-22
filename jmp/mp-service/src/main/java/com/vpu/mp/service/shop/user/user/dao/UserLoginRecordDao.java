package com.vpu.mp.service.shop.user.user.dao;

import static com.vpu.mp.db.shop.Tables.USER_LOGIN_RECORD;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;

/**
* @author 黄壮壮
* @Date: 2019年11月22日
* @Description:
*/
@Service
public class UserLoginRecordDao extends ShopBaseService {
	/**
	 * 获取从该时间开始登录的用户Id
	 */
	public List<Integer> getUserIdFromLoginStartTime(Timestamp time) {
		return db().selectFrom(USER_LOGIN_RECORD)
				.where(USER_LOGIN_RECORD.CREATE_TIME.ge(time))
				.groupBy(USER_LOGIN_RECORD.USER_ID).fetch().getValues(USER_LOGIN_RECORD.USER_ID, Integer.class);
	}
	
	/**
	 * 获取从该时间之前登录的用户Id
	 */
	public List<Integer> getUserIdUtilToLoginEndTime(Timestamp time) {
		return db().selectFrom(USER_LOGIN_RECORD)
				.where(USER_LOGIN_RECORD.CREATE_TIME.le(time))
				.groupBy(USER_LOGIN_RECORD.USER_ID).fetch().getValues(USER_LOGIN_RECORD.USER_ID, Integer.class);
	}

	
}
