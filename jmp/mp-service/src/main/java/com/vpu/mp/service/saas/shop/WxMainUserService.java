package com.vpu.mp.service.saas.shop;

import static com.vpu.mp.db.main.tables.User.USER;
import static com.vpu.mp.db.main.tables.UserDetail.USER_DETAIL;

import java.util.Map;

import org.jooq.SortField;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.UserDetail;
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
	public void syncMainUser(Integer shopId, Integer userId,com.vpu.mp.db.shop.tables.records.UserRecord infoRecord) {
		UserMainVo info = infoRecord.into(UserMainVo.class);
		logger().info("User同步开始到主库,shopId:"+shopId+" userId:"+userId);
		UserRecord record = db().selectFrom(USER).where(USER.SHOP_ID.eq(shopId).and(USER.USER_ID.eq(userId))).fetchAny();
		if (record != null) {
			// 更新
			logger().info("更新");
			info.setId(record.getId());
			info.setShopId(shopId);
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
	public void syncMainUserDetail(Integer shopId, Integer userId,com.vpu.mp.db.shop.tables.records.UserDetailRecord infoRecord) {
		UserDetailMainVo info=infoRecord.into(UserDetailMainVo.class);
		logger().info("UserDetail同步开始到主库,shopId:"+shopId+" userId:"+userId);
		UserDetailRecord record = db().selectFrom(USER_DETAIL).where(USER_DETAIL.SHOP_ID.eq(shopId).and(USER_DETAIL.USER_ID.eq(userId)))
				.fetchAny();
		if (record != null) {
			// 更新
			logger().info("更新");
			info.setId(record.getId());
			info.setShopId(shopId);
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
	
	/**
	 * Map类型更新User
	 * @param map
	 * @param shopId
	 * @param userId
	 */
	public void updateMainUser(Map map,Integer shopId, Integer userId) {
		db().update(USER).set(map).where(USER.SHOP_ID.eq(shopId).and(USER.USER_ID.eq(userId))).execute();
	}
	
	/**
	 * Map类型更新UserDeatil
	 * @param map
	 * @param shopId
	 * @param userId
	 */
	public void updateMainUserDetail(Map map,Integer shopId, Integer userId) {
		db().update(USER).set(map).where(USER.SHOP_ID.eq(shopId).and(USER.USER_ID.eq(userId))).execute();
	}
}
