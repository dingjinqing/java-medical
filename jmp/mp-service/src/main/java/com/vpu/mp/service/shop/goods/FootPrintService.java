package com.vpu.mp.service.shop.goods;

import org.springframework.stereotype.Service;
import static com.vpu.mp.db.shop.tables.FootprintRecord.FOOTPRINT_RECORD;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.vpu.mp.service.foundation.service.ShopBaseService;

/**
 * 足迹
 * 
 * @author zhaojianqiang
 *
 *         2019年10月12日 下午3:08:53
 */
@Service
public class FootPrintService extends ShopBaseService {

	/**
	 * 获得用户足迹数
	 * @param userId
	 * @return
	 */
	public Integer getfootPrintNum(Integer userId) {
		Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().minusMonths(3L));
		return db().selectCount().from(FOOTPRINT_RECORD)
				.where(FOOTPRINT_RECORD.USER_ID.eq(userId).and(FOOTPRINT_RECORD.UPDATE_TIME.gt(timestamp))).fetchOne()
				.into(Integer.class);
	}

}
