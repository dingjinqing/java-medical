package com.vpu.mp.service.saas.shop;

import org.springframework.stereotype.Service;
import static com.vpu.mp.db.main.tables.User.USER;
import static com.vpu.mp.db.main.tables.UserDetail.USER_DETAIL;
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
		logger().info("User同步开始到主库");
		UserRecord record = USER.newRecord();
		record = db().selectFrom(USER).where(USER.SHOP_ID.eq(shopId).and(USER.USER_ID.eq(userId))).fetchAny();
		if (record != null) {
			// 更新
			sendRecord.setId(record.getId());
			int executeUpdate = db().executeUpdate(sendRecord);
			logger().info("更新UserDetail，结果" + executeUpdate);
		} else {
			// 插入
			sendRecord.setShopId(shopId);
			int executeInsert = db().executeInsert(sendRecord);
			logger().info("插入UserDetail，结果" + executeInsert);
		}

	}

	/**
	 * 同步到主库
	 * 
	 * @param sendRecord
	 * @param type
	 */
	public void syncMainUserDetail(UserDetailRecord sendRecord, Integer shopId, Integer userId) {
		logger().info("UserDetail开始同步到主库");
		UserDetailRecord record = USER_DETAIL.newRecord();
		record = db().selectFrom(USER_DETAIL).where(USER_DETAIL.SHOP_ID.eq(shopId).and(USER_DETAIL.USER_ID.eq(userId)))
				.fetchAny();
		if (record != null) {
			// 更新
			sendRecord.setId(record.getId());
			int executeUpdate = db().executeUpdate(sendRecord);
			logger().info("更新UserDetail，结果" + executeUpdate);
		} else {
			// 插入
			sendRecord.setShopId(shopId);
			int executeInsert = db().executeInsert(sendRecord);
			logger().info("插入UserDetail，结果" + executeInsert);
		}
	}

}
