package com.vpu.mp.service.shop.market.bargain;

import static com.vpu.mp.db.shop.tables.BargainUserList.BARGAIN_USER_LIST;
import static com.vpu.mp.db.shop.tables.User.USER;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainUserListQueryParam;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainUserListQueryVo;

/**
 * @author 王兵兵
 *
 * 2019年7月26日
 */
@Service
public class BargainUserService extends ShopBaseService{

	public PageResult<BargainUserListQueryVo> getPageList(BargainUserListQueryParam param){
		SelectWhereStep<? extends Record> select = db().select(
				BARGAIN_USER_LIST.ID,USER.USERNAME,USER.MOBILE,BARGAIN_USER_LIST.CREATE_TIME,BARGAIN_USER_LIST.BARGAIN_MONEY
				).
				from(BARGAIN_USER_LIST).
				leftJoin(USER).on(BARGAIN_USER_LIST.USER_ID.eq(USER.USER_ID));
		select = this.buildOptions(select, param);
		select.where(BARGAIN_USER_LIST.RECORD_ID.eq(param.getRecordId())).orderBy(BARGAIN_USER_LIST.CREATE_TIME.desc());
		return getPageResult(select,param.getCurrentPage(),param.getPageRows(),BargainUserListQueryVo.class); 
	}
	
	public SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends  Record> select, BargainUserListQueryParam param) {
		if (param == null) {
			return select;
		}
		if(!StringUtils.isBlank(param.getUsername())) {
			select.where(USER.USERNAME.contains(param.getUsername()));
		}
		if(!StringUtils.isBlank(param.getMobile())) {
			select.where(USER.MOBILE.contains(param.getMobile()));
		}
		return select;
	}
}
