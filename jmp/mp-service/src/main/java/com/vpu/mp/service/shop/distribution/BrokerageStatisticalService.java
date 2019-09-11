package com.vpu.mp.service.shop.distribution;

import static com.vpu.mp.db.shop.Tables.DISTRIBUTOR_GROUP;
import static com.vpu.mp.db.shop.Tables.DISTRIBUTOR_LEVEL;
import static com.vpu.mp.db.shop.Tables.ORDER_GOODS_REBATE;
import static com.vpu.mp.db.shop.Tables.ORDER_INFO;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_DETAIL;
import static org.jooq.impl.DSL.sum;

import java.util.List;

import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.distribution.BrokerageListParam;
import com.vpu.mp.service.pojo.shop.distribution.BrokerageListVo;
import com.vpu.mp.service.pojo.shop.distribution.DistributorGroupListVo;
import com.vpu.mp.service.pojo.shop.distribution.DistributorLevelVo;

/**
 * 佣金统计
 * @author 常乐
 * 2019年8月8日
 */
@Service
public class BrokerageStatisticalService extends ShopBaseService{
	/** 查询别名 **/
    private static final String INVITE = "n";
  
	public PageResult<BrokerageListVo> getbrokerageList(BrokerageListParam param) {
		SelectJoinStep<? extends Record> select = db().select(USER.as(INVITE).USERNAME .as("distributorName"),USER.as(INVITE).MOBILE.as("distributorMobile"),
				USER.USERNAME.as("orderUserName"),USER.MOBILE.as("userMobile"),USER_DETAIL.REAL_NAME,ORDER_GOODS_REBATE.ORDER_SN,ORDER_INFO.ORDER_AMOUNT,
				ORDER_INFO.MOBILE,USER.USERNAME,ORDER_GOODS_REBATE.REBATE_LEVEL,DISTRIBUTOR_GROUP.GROUP_NAME,
				ORDER_GOODS_REBATE.TOTAL_REBATE_MONEY,sum(ORDER_GOODS_REBATE.REAL_REBATE_MONEY).as("realRebateMoney"))
				.from(ORDER_GOODS_REBATE
				.leftJoin(ORDER_INFO).on(ORDER_GOODS_REBATE.ORDER_SN.eq(ORDER_INFO.ORDER_SN))
				.leftJoin(USER).on(ORDER_INFO.USER_ID.eq(USER.USER_ID))
				.leftJoin(USER.as(INVITE)).on(ORDER_GOODS_REBATE.REBATE_USER_ID.eq(USER.as(INVITE).USER_ID))
				.leftJoin(DISTRIBUTOR_GROUP).on(DISTRIBUTOR_GROUP.ID .eq(USER.INVITE_GROUP))
				.leftJoin(USER_DETAIL).on(ORDER_GOODS_REBATE.REBATE_USER_ID.eq(USER_DETAIL.USER_ID)));

				buildoptions(select,param);
		PageResult<BrokerageListVo> list = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), BrokerageListVo.class);
		return list;
		
	}
	
	/**
	 * 佣金统计列表条件查询
	 * @return 
	 * @return 
	 */
	public void buildoptions(SelectJoinStep<? extends Record> select,BrokerageListParam param) {
		
		if(param.getDistributorName() != null) {
			select.where(USER.as(INVITE).USERNAME.contains(param.getDistributorName()));
		}
		if(param.getDistributorMobile() != null) {
			select.where(USER.as(INVITE).MOBILE.contains(param.getDistributorMobile()));
		}
		if(param.getUsername() != null) {
			select.where(USER.USERNAME.contains(param.getUsername()));
		}
		if(param.getMobile() != null) {
			select.where(USER.MOBILE.contains(param.getMobile()));
		}
		if(param.getStartCreateTime() != null && param.getEndCreateTime() != null) {
			select.where(ORDER_INFO.CREATE_TIME.ge(param.getStartCreateTime()).and(ORDER_INFO.CREATE_TIME.le(param.getEndCreateTime())));
		}
		if(param.getOrderSn() != null) {
			select.where(ORDER_INFO.ORDER_SN.contains(param.getOrderSn()));
		}
		if(param.getStartRebateTime() != null && param.getEndRebateTime() != null) {
			select.where(ORDER_INFO.FINISHED_TIME.ge(param.getStartRebateTime()).and(ORDER_INFO.FINISHED_TIME.le(param.getEndRebateTime())));
		}
		if(param.getRebateStatus() != null) {
			select.where(ORDER_INFO.SETTLEMENT_FLAG.eq(param.getRebateStatus()));
		}
		if(param.getDistributorGroup() != null) {
			select.where(USER.INVITE_GROUP.eq(param.getDistributorGroup()));
		}
		if(param.getRebateLevel() != null) {
			select.where((ORDER_GOODS_REBATE.REBATE_LEVEL.eq(param.getRebateLevel())));
		}
		select.groupBy(ORDER_GOODS_REBATE.ORDER_SN,ORDER_GOODS_REBATE.REBATE_LEVEL,ORDER_GOODS_REBATE.REBATE_USER_ID,
				USER.as(INVITE).USERNAME,USER.as(INVITE).MOBILE,USER_DETAIL.REAL_NAME,ORDER_INFO.ORDER_AMOUNT,ORDER_INFO.MOBILE,USER.USERNAME,ORDER_GOODS_REBATE.REBATE_LEVEL,
				ORDER_GOODS_REBATE.TOTAL_REBATE_MONEY,USER.USERNAME,USER.MOBILE,DISTRIBUTOR_GROUP.GROUP_NAME);
		select.orderBy(ORDER_GOODS_REBATE.CREATE_TIME);
	}
	
	/**
	 * 分销员等级列表
	 * @return
	 */
	public List<DistributorLevelVo> getLevelList() {
		List<DistributorLevelVo> list = db().select().from(DISTRIBUTOR_LEVEL).fetch().into(DistributorLevelVo.class);
		return list;
	}
	
	/**
	 * 分销员分组列表
	 * @return
	 */
	public List<DistributorGroupListVo> getGroupList() {
		List<DistributorGroupListVo> list = db().select().from(DISTRIBUTOR_GROUP).fetch().into(DistributorGroupListVo.class);
		return list;
	}
}
