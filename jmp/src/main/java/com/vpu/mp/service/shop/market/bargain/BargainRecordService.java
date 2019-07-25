package com.vpu.mp.service.shop.market.bargain;

import static com.vpu.mp.db.shop.tables.BargainRecord.BARGAIN_RECORD;

import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;

/**
 * @author 王兵兵
 *
 * 2019年7月25日
 */
@Service
public class BargainRecordService extends ShopBaseService {
	
	/**
	 * 状态：正在砍价 
	 */
	public static final byte STATUS_IN_PROCESS = 0;
	/**
	 *  状态：砍价成功 
	 */
	public static final byte STATUS_SUCCESS = 1;
	/**
	 *  状态：砍价失败 
	 */
	public static final byte STATUS_FAILED = 2;

	/**
	 * 根据状态取发起砍价的数量
	 * @param bargainId
	 * @param status
	 * @return
	 */
	public Integer getBargainRecordNumberByStatus(int bargainId,byte status) {
		return db().selectCount().from(BARGAIN_RECORD).where(BARGAIN_RECORD.STATUS.eq(status)).and(BARGAIN_RECORD.BARGAIN_ID.eq(bargainId)).and(BARGAIN_RECORD.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchOne().into(Integer.class);
	}
	
	/**
	 * 某活动的发起砍价数量
	 * @param bargainId
	 * @return
	 */
	public Integer getBargainRecordNumber(int bargainId) {
		return db().selectCount().from(BARGAIN_RECORD).where(BARGAIN_RECORD.BARGAIN_ID.eq(bargainId)).and(BARGAIN_RECORD.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchOne().into(Integer.class);
	}
}
