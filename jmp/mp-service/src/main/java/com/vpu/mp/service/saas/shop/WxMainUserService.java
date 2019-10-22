package com.vpu.mp.service.saas.shop;

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
	 * @param sendRecord
	 * @param type
	 */
	public void syncMainUser(UserRecord sendRecord, Byte type) {
		logger().info("User同步到主库");
		if (type == 1) {
			// 更新
			int executeUpdate = db().executeUpdate(sendRecord);
			logger().info("User同步到主库,结果"+executeUpdate);
		}
		if (type == 0) {
			// 插入
			int executeInsert = db().executeInsert(sendRecord);
			logger().info("User同步到主库,结果"+executeInsert);
		}
	}
	
	/**
	 * 同步到主库
	 * @param sendRecord
	 * @param type
	 */
	public void syncMainUserDetail(UserDetailRecord sendRecord, Byte type) {
		logger().info("UserDetail同步到主库");
		if (type == 1) {
			// 更新
			int executeUpdate = db().executeUpdate(sendRecord);
			logger().info("UserDetail同步到主库,结果"+executeUpdate);
		}
		if (type == 0) {
			// 插入
			int executeInsert = db().executeInsert(sendRecord);
			logger().info("UserDetail同步到主库,结果"+executeInsert);
		}
	}

}
