package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.User.USER;
import static com.vpu.mp.db.main.tables.UserDetail.USER_DETAIL;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.UserDetailRecord;
import com.vpu.mp.db.main.tables.records.UserRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;

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
	public void syncMainUser(UserRecord sendRecord, Integer shopId, Integer userId) {
		logger().info("User同步开始到主库,shopId:"+shopId+" userId:"+userId);
		UserRecord record = db().selectFrom(USER).where(USER.SHOP_ID.eq(shopId).and(USER.USER_ID.eq(userId))).fetchAny();
		if (record != null) {
			// 更新
			sendRecord.setId(record.getId());
			logger().info("同步更新user"+sendRecord);
			int executeUpdate = sendRecord.update();
			//int executeUpdate = db().executeUpdate(sendRecord);
			logger().info("更新User，结果" + executeUpdate);
		} else {
			// 插入
			sendRecord.setShopId(shopId);
			record=db().newRecord(USER);
			record = sendRecord.into(UserRecord.class);
			logger().info("同步插入user"+record);
			logger().info("测试值是否存在"+record.getUserId());
			int executeInsert = record.insert();
			logger().info("插入User，结果" + executeInsert);
		}

	}

	/**
	 * 同步到主库
	 * 
	 * @param sendRecord
	 * @param type
	 */
	public void syncMainUserDetail(UserDetailRecord sendRecord, Integer shopId, Integer userId) {
		logger().info("UserDetail同步开始到主库,shopId:"+shopId+" userId:"+userId);
		UserDetailRecord record = db().selectFrom(USER_DETAIL).where(USER_DETAIL.SHOP_ID.eq(shopId).and(USER_DETAIL.USER_ID.eq(userId)))
				.fetchAny();
		if (record != null) {
			// 更新
			sendRecord.setId(record.getId());
			logger().info("同步更新user"+sendRecord);
			int executeUpdate = sendRecord.update();
			//int executeUpdate = db().executeUpdate(sendRecord);
			logger().info("更新UserDetail，结果" + executeUpdate);
		} else {
			// 插入
			sendRecord.setShopId(shopId);
			record=db().newRecord(USER_DETAIL);
			record = sendRecord.into(UserDetailRecord.class);
			logger().info("同步插入UserDetail"+record);
			logger().info("测试值是否存在"+record.getUserId());
			int executeInsert = record.insert();
			logger().info("插入UserDetail，结果" + executeInsert);
		}
	}

}
