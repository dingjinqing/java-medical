package com.vpu.mp.service.shop.user.user;

import org.springframework.stereotype.Service;
import static com.vpu.mp.db.shop.tables.UserDetail.USER_DETAIL;

import com.vpu.mp.db.shop.tables.records.UserDetailRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;

@Service
public class UserDetailService extends ShopBaseService {
	
	public UserDetailRecord getUserDetailByUserId(Integer userId) {
		return db().fetchAny(USER_DETAIL,USER_DETAIL.USER_ID.eq(userId));
	}
	
	public void updateRow(UserDetailRecord userDetailRecord) {
		userDetailRecord.update();
		//TODO 更新主库的
	}
}
