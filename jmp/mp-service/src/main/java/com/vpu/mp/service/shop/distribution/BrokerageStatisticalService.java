package com.vpu.mp.service.shop.distribution;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.distribution.BrokerageListParam;
import com.vpu.mp.service.pojo.shop.distribution.BrokerageListVo;
import com.vpu.mp.service.pojo.shop.distribution.DistributorGroupListVo;
import com.vpu.mp.service.pojo.shop.distribution.DistributorLevelVo;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;
import static org.jooq.impl.DSL.sum;

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
		SelectJoinStep<? extends Record> select = db().select(USER.as(INVITE).USER_ID .as("partnerId"),USER.as(INVITE).USERNAME .as("distributorName"),USER.as(INVITE).MOBILE.as("distributorMobile"),
				USER.USER_ID,USER.USERNAME.as("orderUserName"),USER.MOBILE.as("userMobile"),USER_DETAIL.REAL_NAME,ORDER_GOODS_REBATE.ORDER_SN,ORDER_INFO.ORDER_AMOUNT,
				ORDER_INFO.MOBILE,USER.USERNAME,ORDER_GOODS_REBATE.REBATE_LEVEL,DISTRIBUTOR_GROUP.GROUP_NAME,
				ORDER_GOODS_REBATE.TOTAL_REBATE_MONEY,ORDER_INFO.CREATE_TIME,sum(ORDER_GOODS_REBATE.REAL_REBATE_MONEY).as("realRebateMoney"),ORDER_INFO.SETTLEMENT_FLAG)
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
	 * 佣金统计条件查询
	 * @param select
	 * @param param
	 */
	public void buildoptions(SelectJoinStep<? extends Record> select,BrokerageListParam param) {
	    //分销员id
	    if(param.getUserId() != null){
            select.where(USER.as(INVITE).USER_ID.eq(param.getUserId()));
        }
        //分销员昵称
		if(param.getDistributorName() != null) {
			select.where(USER.as(INVITE).USERNAME.contains(param.getDistributorName()));
		}
		//分销员手机号
		if(param.getDistributorMobile() != null) {
			select.where(USER.as(INVITE).MOBILE.contains(param.getDistributorMobile()));
		}
		//下单用户昵称
		if(param.getUsername() != null) {
			select.where(USER.USERNAME.contains(param.getUsername()));
		}
		//下单用户手机号
		if(param.getMobile() != null) {
			select.where(USER.MOBILE.contains(param.getMobile()));
		}
		//下单时间
		if(param.getStartCreateTime() != null && param.getEndCreateTime() != null) {
			select.where(ORDER_INFO.CREATE_TIME.ge(param.getStartCreateTime()).and(ORDER_INFO.CREATE_TIME.le(param.getEndCreateTime())));
		}
		//返利订单号
		if(param.getOrderSn() != null) {
			select.where(ORDER_INFO.ORDER_SN.contains(param.getOrderSn()));
		}
		//返利日期
		if(param.getStartRebateTime() != null && param.getEndRebateTime() != null) {
			select.where(ORDER_INFO.FINISHED_TIME.ge(param.getStartRebateTime()).and(ORDER_INFO.FINISHED_TIME.le(param.getEndRebateTime())));
		}
		//返利状态
		if(param.getSettlementFlag() != null) {
			select.where(ORDER_INFO.SETTLEMENT_FLAG.eq(param.getSettlementFlag()));
		}
		//分销员分组
		if(param.getDistributorGroup() != null) {
			select.where(USER.INVITE_GROUP.eq(param.getDistributorGroup()));
		}
		//返利关系 0：自购返利；1：直接返利；2：间接返利；
		if(param.getRebateLevel() != null) {
			select.where((ORDER_GOODS_REBATE.REBATE_LEVEL.eq(param.getRebateLevel())));
		}
		select.groupBy(USER.USER_ID,ORDER_GOODS_REBATE.ORDER_SN,ORDER_GOODS_REBATE.REBATE_LEVEL,ORDER_GOODS_REBATE.REBATE_USER_ID,
            USER.as(INVITE).USER_ID,USER.as(INVITE).USERNAME,USER.as(INVITE).MOBILE,USER_DETAIL.REAL_NAME,ORDER_INFO.ORDER_AMOUNT,ORDER_INFO.MOBILE,USER.USERNAME,ORDER_GOODS_REBATE.REBATE_LEVEL,
				ORDER_GOODS_REBATE.TOTAL_REBATE_MONEY,USER.USERNAME,USER.MOBILE,DISTRIBUTOR_GROUP.GROUP_NAME,ORDER_INFO.CREATE_TIME,ORDER_INFO.SETTLEMENT_FLAG);
		select.orderBy(ORDER_GOODS_REBATE.CREATE_TIME);
	}

	/**
	 * 分销员等级列表
	 * @return
	 */
	public List<DistributorLevelVo> getLevelList() {
        Result<Record> record = db().select().from(DISTRIBUTOR_LEVEL).fetch();
        if(record != null){
            return record.into(DistributorLevelVo.class);
        }else{
            return null;
        }
	}

	/**
	 * 分销员分组列表
	 * @return
	 */
	public List<DistributorGroupListVo> getGroupList() {
        Result<Record> record = db().select().from(DISTRIBUTOR_GROUP).where(DISTRIBUTOR_GROUP.DEL_FLAG.eq((byte) 0)).fetch();
        if(record != null){
            return record.into(DistributorGroupListVo.class);
        }else{
            return null;
        }
	}
}
