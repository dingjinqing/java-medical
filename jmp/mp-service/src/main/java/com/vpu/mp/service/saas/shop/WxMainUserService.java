package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.User.USER;
import static com.vpu.mp.db.main.tables.UserDetail.USER_DETAIL;

import java.sql.ResultSet;

import org.jooq.Field;
import org.jooq.Row;
import org.jooq.Table;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.UserDetailRecord;
import com.vpu.mp.db.main.tables.records.UserRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;

@Service
public class WxMainUserService extends MainBaseService {
	public static final Byte SYCUPDATE = 1;
	public static final Byte SYCINSERT = 0;

	/**
	 * 同步到主库
	 * 
	 * @param sendRecord
	 * @param type
	 */
	public void syncMainUser(com.vpu.mp.db.shop.tables.records.UserRecord sendRecord, Integer shopId, Integer userId) {
		logger().info("User同步开始到主库,shopId:"+shopId+" userId:"+userId);
		UserRecord record = db().selectFrom(USER).where(USER.SHOP_ID.eq(shopId).and(USER.USER_ID.eq(userId))).fetchAny();
		if (record != null) {
			// 更新
			int executeUpdate = sendRecord.update();
			logger().info("更新User，结果" + executeUpdate);
		} else {
			// 插入
			sendRecord.setShopId(shopId);
			UserRecord into = sendRecord.into(USER);
			//copy重新set值，不set插入时候不插入。疑似jooq的bug
			UserRecord copy = into.copy();
			int executeInsert = db().executeInsert(copy);
			logger().info("插入User，结果" + executeInsert);
		}

	}

	/**
	 * 同步到主库
	 * 
	 * @param sendRecord
	 * @param type
	 */
	public void syncMainUserDetail(com.vpu.mp.db.shop.tables.records.UserDetailRecord sendRecord, Integer shopId, Integer userId) {
		logger().info("UserDetail同步开始到主库,shopId:"+shopId+" userId:"+userId);
		UserDetailRecord record = db().selectFrom(USER_DETAIL).where(USER_DETAIL.SHOP_ID.eq(shopId).and(USER_DETAIL.USER_ID.eq(userId)))
				.fetchAny();
		if (record != null) {
			// 更新
			int executeUpdate = sendRecord.update();
			logger().info("更新UserDetail，结果" + executeUpdate);
		} else {
			// 插入
			sendRecord.setShopId(shopId);
			UserDetailRecord into = sendRecord.into(USER_DETAIL);
			UserDetailRecord copy = into.copy();
			int executeInsert = db().executeInsert(copy);
			logger().info("插入UserDetail，结果" + executeInsert);
		}
	}

}
