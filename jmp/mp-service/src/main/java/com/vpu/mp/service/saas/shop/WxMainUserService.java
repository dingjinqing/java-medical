package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.User.USER;
import static com.vpu.mp.db.main.tables.UserDetail.USER_DETAIL;

import org.jooq.SortField;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.UserDetailRecord;
import com.vpu.mp.db.main.tables.records.UserRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.wxapp.account.UserDetailMainVo;
import com.vpu.mp.service.pojo.wxapp.account.UserMainVo;

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
	public void syncMainUser(Integer shopId, Integer userId,UserMainVo info) {
		logger().info("User同步开始到主库,shopId:"+shopId+" userId:"+userId);
		UserRecord record = db().selectFrom(USER).where(USER.SHOP_ID.eq(shopId).and(USER.USER_ID.eq(userId))).fetchAny();
		if (record != null) {
			// 更新
			logger().info("更新");
			info.setId(record.getId());
			UserRecord newRecord = db().newRecord(USER, info);
			int executeUpdate = newRecord.update();
			logger().info("更新User，结果" + executeUpdate);
		} else {
			// 插入
			logger().info("插入");
			info.setShopId(shopId);
			UserRecord newRecord = db().newRecord(USER, info);
			int insert = newRecord.insert();
			logger().info("插入User，结果" + insert);
		}

	}

	/**
	 * 同步到主库
	 * 
	 * @param sendRecord
	 * @param type
	 */
	public void syncMainUserDetail(Integer shopId, Integer userId,UserDetailMainVo info) {
		logger().info("UserDetail同步开始到主库,shopId:"+shopId+" userId:"+userId);
		UserDetailRecord record = db().selectFrom(USER_DETAIL).where(USER_DETAIL.SHOP_ID.eq(shopId).and(USER_DETAIL.USER_ID.eq(userId)))
				.fetchAny();
		if (record != null) {
			// 更新
			logger().info("更新");
			info.setId(record.getId());
			UserDetailRecord newRecord = db().newRecord(USER_DETAIL, info);
			int executeUpdate = newRecord.update();
			logger().info("更新UserDetail，结果" + executeUpdate);
		} else {
			// 插入
			logger().info("插入");
			info.setShopId(shopId);
			UserDetailRecord newRecord = db().newRecord(USER_DETAIL, info);
			int insert = newRecord.insert();
			logger().info("插入UserDetail，结果" + insert);
		}
	}
	

}
