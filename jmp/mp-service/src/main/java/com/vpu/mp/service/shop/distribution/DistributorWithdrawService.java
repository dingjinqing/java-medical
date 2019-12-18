package com.vpu.mp.service.shop.distribution;

import static com.vpu.mp.db.shop.Tables.DISTRIBUTION_WITHDRAW;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_DETAIL;

import java.math.BigDecimal;

import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.DistributionWithdrawRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.distribution.DistributorWithdrawDetailVo;
import com.vpu.mp.service.pojo.shop.distribution.DistributorWithdrawListParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributorWithdrawListVo;
import com.vpu.mp.service.pojo.shop.distribution.DistributorWithdrawSumDetailVo;

/**
 * 分销提现相关
 * @author 常乐
 * 2019年8月13日
 */
@Service
public class DistributorWithdrawService extends ShopBaseService{
	/**
	 * 分销提现审核列表
	 * @param param
	 * @return
	 */
	public PageResult<DistributorWithdrawListVo> getWithdrawList(DistributorWithdrawListParam param) {
		SelectJoinStep<? extends Record> select = db().select(DISTRIBUTION_WITHDRAW.ID,USER.USERNAME,USER.MOBILE,USER_DETAIL.REAL_NAME,DISTRIBUTION_WITHDRAW.CREATE_TIME,DISTRIBUTION_WITHDRAW.ORDER_SN,
					DISTRIBUTION_WITHDRAW.WITHDRAW_CASH,DISTRIBUTION_WITHDRAW.CHECK_TIME,DISTRIBUTION_WITHDRAW.STATUS,DISTRIBUTION_WITHDRAW.REFUSE_DESC,DISTRIBUTION_WITHDRAW.DESC,DISTRIBUTION_WITHDRAW.TYPE,
					DISTRIBUTION_WITHDRAW.WITHDRAW_NUM,DISTRIBUTION_WITHDRAW.WITHDRAW_USER_NUM)
				.from(DISTRIBUTION_WITHDRAW
				.leftJoin(USER).on(DISTRIBUTION_WITHDRAW.USER_ID.eq(USER.USER_ID)))
				.leftJoin(USER_DETAIL).on(DISTRIBUTION_WITHDRAW.USER_ID.eq(USER_DETAIL.USER_ID));
		buildOptions(select,param);
		PageResult<DistributorWithdrawListVo> pageList = this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), DistributorWithdrawListVo.class);
		return pageList;
	}


	/**
	 * 返利提现审核列表条件查询
	 */
	public void buildOptions(SelectJoinStep<? extends Record> select ,DistributorWithdrawListParam param) {
		//申请人id
		if(param.getUserId() != null) {
			select.where(DISTRIBUTION_WITHDRAW.USER_ID.eq(param.getUserId()));
		}
		//申请人昵称
		if(param.getUsername() != null) {
			select.where(USER.USERNAME.contains(param.getUsername()));
		}
		//手机号
		if(param.getMobile() != null) {
			select.where(USER.MOBILE.contains(param.getMobile()));
		}
		//真实姓名
		if(param.getRealName() != null) {
			select.where(USER_DETAIL.REAL_NAME.contains(param.getRealName()));
		}
		//提现单号
		if(param.getOrderSn() != null) {
			select.where(DISTRIBUTION_WITHDRAW.ORDER_SN.contains(param.getOrderSn()));
		}
		//申请时间
		if(param.getStartCreateTime() != null && param.getEndCreateTime() != null) {
			select.where(DISTRIBUTION_WITHDRAW.CREATE_TIME.ge(param.getStartCreateTime())).and(DISTRIBUTION_WITHDRAW.CREATE_TIME.le(param.getEndCreateTime()));
		}
		//提现金额
		if(param.getStartWithdrawCash() != null && param.getEndWithdrawCash() != null) {
			select.where(DISTRIBUTION_WITHDRAW.WITHDRAW_CASH.ge(param.getStartWithdrawCash())).and(DISTRIBUTION_WITHDRAW.WITHDRAW_CASH.le(param.getEndWithdrawCash()));
		}
		//处理状态
		if(param.getStatus() != null) {
			select.where(DISTRIBUTION_WITHDRAW.STATUS.eq(param.getStatus()));
		}
		select.orderBy(DISTRIBUTION_WITHDRAW.CREATE_TIME.desc());
	}
	
	/**
	 * 提现审核详情
	 * @param id
	 * @return
	 */
	public DistributorWithdrawDetailVo getWithdrawDetail(Integer id) {
		DistributorWithdrawDetailVo detail = db().select(USER.USER_ID,USER.USERNAME,USER.MOBILE,USER_DETAIL.REAL_NAME,DISTRIBUTION_WITHDRAW.CREATE_TIME,DISTRIBUTION_WITHDRAW.ORDER_SN,
				DISTRIBUTION_WITHDRAW.WITHDRAW_CASH,DISTRIBUTION_WITHDRAW.CHECK_TIME,DISTRIBUTION_WITHDRAW.STATUS,DISTRIBUTION_WITHDRAW.REFUSE_DESC,DISTRIBUTION_WITHDRAW.DESC,
				DISTRIBUTION_WITHDRAW.TYPE,DISTRIBUTION_WITHDRAW.WITHDRAW_USER_NUM,DISTRIBUTION_WITHDRAW.WITHDRAW_NUM,DISTRIBUTION_WITHDRAW.WITHDRAW,DISTRIBUTION_WITHDRAW.UPDATE_TIME)
			.from(DISTRIBUTION_WITHDRAW
			.leftJoin(USER).on(DISTRIBUTION_WITHDRAW.USER_ID.eq(USER.USER_ID)))
			.leftJoin(USER_DETAIL).on(DISTRIBUTION_WITHDRAW.USER_ID.eq(USER_DETAIL.USER_ID))
			.where(DISTRIBUTION_WITHDRAW.ID.eq(id))
			.fetchOne().into(DistributorWithdrawDetailVo.class);
		//获取当前用户ID
		int userId = db().select(DISTRIBUTION_WITHDRAW.USER_ID).from(DISTRIBUTION_WITHDRAW).where(DISTRIBUTION_WITHDRAW.ID.eq(id)).fetchOne().into(Integer.class);

		DistributorWithdrawListParam param = new DistributorWithdrawListParam();
		param.setUserId(userId);
		//当前用户其他提现记录
		PageResult<DistributorWithdrawListVo> otherWithdrawRecord = this.getWithdrawList(param);
		detail.setUserWithdrawList(otherWithdrawRecord);
		return detail;
	}
	
	/**
	 * 根据userid获取分销提现
	 * @param userId
	 * @return
	 */
	public DistributionWithdrawRecord getWithdrawByUserId(Integer userId) {
		return db().selectFrom(DISTRIBUTION_WITHDRAW).where(DISTRIBUTION_WITHDRAW.USER_ID.eq(userId)).fetchAny();
	}
	
	/**
	 * 获取已提现金额
	 * @return 
	 */
	public BigDecimal getDoneWithDraw(Integer userId) {
		return db().select(DSL.sum(DISTRIBUTION_WITHDRAW.WITHDRAW_CASH))
					.from(DISTRIBUTION_WITHDRAW)
					.where(DISTRIBUTION_WITHDRAW.USER_ID.eq(userId))
					.and(DISTRIBUTION_WITHDRAW.STATUS.eq((byte)4))
					.fetchOptionalInto(BigDecimal.class)
					.orElse(BigDecimal.ZERO);
	}
	
	/**
	 * 提现记录
	 * @param param
	 * @return
	 */
	public DistributorWithdrawSumDetailVo withdrawList(DistributorWithdrawListParam param) {
	
		PageResult<DistributorWithdrawListVo> data = getWithdrawList(param);
		BigDecimal doneWithDraw = getDoneWithDraw(param.getUserId());
		return DistributorWithdrawSumDetailVo
				.builder()
				.data(data)
				.withdrawCrash(doneWithDraw)
				.build();
	}
}
