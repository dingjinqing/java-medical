package com.vpu.mp.service.shop.coupon;

import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;

import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;

import com.vpu.mp.db.shop.tables.records.MrkingVoucherRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.coupon.CouponListParam;
import com.vpu.mp.service.pojo.shop.coupon.CouponListVo;
import com.vpu.mp.service.pojo.shop.coupon.CouponParam;

/**
 * 优惠券管理
 * @author 常乐
 * 2019年7月16日
 */
public class CouponService extends BaseService{
	/**
	 * 创建优惠券
	 * @param couponInfo
	 * @return
	 */
	public Boolean couponAdd(CouponParam couponInfo) {
		MrkingVoucherRecord record = new MrkingVoucherRecord();
		this.assign(couponInfo,record);
		return db().executeInsert(record) > 0 ? true : false;
	}
	
	/**
	 * 获取优惠券分页列表
	 * @param param
	 * @return
	 */
	public PageResult<CouponListVo> getCouponList(CouponListParam param) {
		SelectJoinStep<Record> select = db().select().from(MRKING_VOUCHER);
		SelectConditionStep<Record> sql = buildOptions(select,param);
		sql.orderBy(MRKING_VOUCHER.CREATE_TIME.desc());
		PageResult<CouponListVo> couponList = this.getPageResult(sql,param.getCurrentPage(),param.getPageRows(),CouponListVo.class);
		return couponList;
	}
	
	/**
	 * 优惠券列表根据条件筛选
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectConditionStep<Record> buildOptions(SelectJoinStep<Record> select,CouponListParam param) {
		SelectConditionStep<Record> sql = select.where(MRKING_VOUCHER.DEL_FLAG.eq((byte) 0));
		if(param.getActName() != null) {
			sql = sql.and(MRKING_VOUCHER.ACT_NAME.eq(param.getActName()));
		}
		return sql;
	}
}
