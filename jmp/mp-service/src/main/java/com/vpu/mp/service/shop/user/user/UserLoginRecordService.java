package com.vpu.mp.service.shop.user.user;

import static com.vpu.mp.db.shop.Tables.USER_LOGIN_RECORD;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.UserLoginRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.pojo.wxapp.account.UserLoginRecordVo;

/**
 * 
 * @author zhaojianqiang
 *
 *         2019年10月10日 下午3:32:40
 */
@Service
public class UserLoginRecordService extends ShopBaseService {

	public void userLoginRecord(Integer userId, UserLoginRecordVo vo) {
		logger().info("记录小程序登录");
		UserLoginRecordRecord res = db().selectFrom(USER_LOGIN_RECORD).where(USER_LOGIN_RECORD.USER_ID.eq(userId))
				.and(USER_LOGIN_RECORD.CREATE_TIME.gt(DateUtil.getLocalTimeDateBySelf("yyyy-MM-dd HH:00:00"))).fetchAny();
		// 有记录更新登陆次数，没有记录加记录 一小时一条数据
		UserLoginRecordRecord record2=db().newRecord(USER_LOGIN_RECORD);
		FieldsUtil.assignNotNull(vo, record2);
		if (res == null) {
			record2.setCount(1);
			int insert = record2.insert();
			logger().info("插入小程序登录"+insert);
		} else {
			res.setCount(res.getCount() + 1);
			if (vo.getLat() != null && (!vo.getLat().equals(res.getLat()))) {
				res.setLat(vo.getLat());
				res.setLng(vo.getLng());
			}
			int update = res.update();
			logger().info("更新小程序登录"+update);
		}
	}

}
