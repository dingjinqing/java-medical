package com.vpu.mp.service.shop.market.bargain;

import static com.vpu.mp.db.shop.tables.Bargain.BARGAIN;
import static com.vpu.mp.db.shop.tables.Goods.GOODS;

import java.sql.Timestamp;

import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainPageListQueryParam;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainPageListQueryVo;

/**
 * @author 王兵兵
 *
 * 2019年7月24日
 */
@Service
public class BargainService extends ShopBaseService  {
	
	/**
	 * 启用状态 
	 */
	public static final byte STATUS_NORMAL = 1;
	/**
	 * 停用状态 
	 */
	public static final byte STATUS_DISABLED = 0;
	
	/**
	 * 砍价活动列表分页查询
	 * @param StoreListQueryParam
	 * @return StorePageListVo
	 */
	public PageResult<BargainPageListQueryVo> getPageList(BargainPageListQueryParam param) {
		SelectWhereStep<? extends Record> select = db().select(
				BARGAIN.BARGAIN_NAME,BARGAIN.BARGAIN_TYPE,BARGAIN.START_TIME,BARGAIN.END_TIME,BARGAIN.STATUS,BARGAIN.GOODS_ID,BARGAIN.STOCK,
				GOODS.GOODS_NAME,GOODS.GOODS_NUMBER
				).
				from(BARGAIN).
				leftJoin(GOODS).on(BARGAIN.GOODS_ID.eq(GOODS.GOODS_ID));
		if(param.getState() > 0) {
			/** 状态过滤*/
			Timestamp now = Util.getLocalDateTime();
			switch(param.getState()) {
			case (byte)1:
				select.where(BARGAIN.STATUS.eq(STATUS_NORMAL)).and(BARGAIN.START_TIME.lt(now)).and(BARGAIN.END_TIME.gt(now));
				break;
			case (byte)2:
				select.where(BARGAIN.STATUS.eq(STATUS_NORMAL)).and(BARGAIN.START_TIME.gt(now));
				break;
			case (byte)3:
				select.where(BARGAIN.STATUS.eq(STATUS_NORMAL)).and(BARGAIN.END_TIME.lt(now));
				break;
			case (byte)4:
				select.where(BARGAIN.STATUS.eq(STATUS_DISABLED));
			break;
			}
		}
		select.where(BARGAIN.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(BARGAIN.CREATE_TIME.desc());
		return getPageResult(select,param.getCurrentPage(),param.getPageRows(),BargainPageListQueryVo.class);
	}
}
