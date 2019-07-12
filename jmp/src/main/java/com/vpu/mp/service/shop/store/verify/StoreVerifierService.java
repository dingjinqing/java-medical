package com.vpu.mp.service.shop.store.verify;

import static com.vpu.mp.db.shop.tables.OrderVerifier.ORDER_VERIFIER;
import static com.vpu.mp.db.shop.tables.User.USER;

import org.jooq.InsertValuesStep3;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.tools.StringUtils;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.DelFlag;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.store.verify.VerifierAddParam;
import com.vpu.mp.service.pojo.shop.store.verify.VerifierListQueryParam;
import com.vpu.mp.service.pojo.shop.store.verify.VerifierListVo;

/**
 * @author 王兵兵
 *
 * 2019年7月11日
 */
public class StoreVerifierService extends BaseService{
	/**
	 *	 门店核销员列表分页查询
	 * @param VerifierListQueryParam
	 * @return VerifierListVo
	 */
	public PageResult<VerifierListVo> getPageList(VerifierListQueryParam param) {
		SelectWhereStep<? extends Record> select = db().select(ORDER_VERIFIER.USER_ID,USER.USERNAME,USER.MOBILE,ORDER_VERIFIER.VERIFY_ORDERS)
				.from(ORDER_VERIFIER)
				.leftJoin(USER).on(ORDER_VERIFIER.USER_ID.eq(USER.USER_ID));
		select = this.buildOptions(select, param);
		select.where(ORDER_VERIFIER.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(ORDER_VERIFIER.CREATE_TIME);
		return getPageResult(select,param.getCurrentPage(),param.getPageRows(),VerifierListVo.class);
	}
	
	/**
	 *	 查询条件构建
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends  Record> select, VerifierListQueryParam param) {
		select.where(ORDER_VERIFIER.STORE_ID.eq(param.getStoreId()));
		if (!StringUtils.isEmpty(param.getMobile())) {
			select.where(USER.MOBILE.contains(param.getMobile()));
		}
		if (!StringUtils.isEmpty(param.getUsername())) {
			select.where(USER.USERNAME.contains(param.getUsername()));
		}
		return select;
	}
	
	/**
	 * 添加核销员
	 * @param VerifierAddParam
	 * @return
	 */
	public Boolean addVerifiers(VerifierAddParam param) {
		InsertValuesStep3<? extends Record,Integer,Integer,Integer>  step = db().insertInto(ORDER_VERIFIER,ORDER_VERIFIER.STORE_ID,ORDER_VERIFIER.USER_ID,ORDER_VERIFIER.VERIFY_ORDERS);
		Integer[] userIds = param.getUserIds();
		Integer storeId = param.getStoreId();
		for(int i = 0;i<userIds.length;i++) {
			step.values(storeId, userIds[i], 0);
		}
		return step.execute() > 0 ? true : false;
	}
	
}
