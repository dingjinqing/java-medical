package com.vpu.mp.service.shop.coupon;

import static com.vpu.mp.db.shop.Tables.CUSTOMER_AVAIL_COUPONS;
import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;
import static com.vpu.mp.db.shop.Tables.USER;

import java.sql.Timestamp;
import java.util.List;

import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MrkingVoucherRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.coupon.CouponGetDetailParam;
import com.vpu.mp.service.pojo.shop.coupon.CouponGetDetailVo;
import com.vpu.mp.service.pojo.shop.coupon.CouponListParam;
import com.vpu.mp.service.pojo.shop.coupon.CouponListVo;
import com.vpu.mp.service.pojo.shop.coupon.CouponParam;

/**
 * 优惠券管理
 * @author 常乐
 * 2019年7月16日
 */
@Service

public class CouponService extends ShopBaseService{
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
		
		Timestamp nowDate = new Timestamp(System.currentTimeMillis());

		if(param.getNav() != null) {
			switch(param.getNav()) {
			//进行中
			case 1:
				sql = sql.and(MRKING_VOUCHER.START_TIME.le(nowDate)).and(MRKING_VOUCHER.END_TIME.ge(nowDate));
				break;
			//未开始
			case 2:
				sql = sql.and(MRKING_VOUCHER.START_TIME.ge(nowDate));
				break;
			//已过期
			case 3:
				sql = sql.and(MRKING_VOUCHER.END_TIME.le(nowDate));
				break;
			//已停用
			case 4:
				sql = sql.and(MRKING_VOUCHER.ENABLED.eq((byte) 0));
				break;
			}
		}
		return sql;
	}
	
	/**
	 * 获取单条优惠券信息
	 * @param couponId
	 * @return
	 */
	public List<CouponParam> getOneCouponInfo(Integer couponId) {
		List<CouponParam> couponInfo = db().select().from(MRKING_VOUCHER)
				.where(MRKING_VOUCHER.ID.eq(couponId))
				.fetch().into(CouponParam.class);
		return couponInfo;
	}
	
	/**
	 * 保存编辑信息
	 * @param param
	 * @return 
	 */
	public boolean saveCouponInfo(CouponParam param) {
		MrkingVoucherRecord record = new MrkingVoucherRecord();
		this.assign(param,record);
		return db().executeUpdate(record) > 0 ? true : false;
	}
	
	/**
	 * 停用优惠券
	 * @param couponId
	 * @return
	 */
	public boolean couponPause(Integer couponId) {
		int result = db().update(MRKING_VOUCHER)
				.set(MRKING_VOUCHER.ENABLED,(byte) 0)
				.where(MRKING_VOUCHER.ID.eq(couponId))
				.execute();
		return result > 0 ? true : false;
	}
	
	/**
	 * 删除优惠券（假删除）
	 * @param couponId
	 * @return
	 */
	public boolean couponDel(Integer couponId) {
		int result = db().update(MRKING_VOUCHER)
				.set(MRKING_VOUCHER.DEL_FLAG,(byte) 1)
				.where(MRKING_VOUCHER.ID.eq(couponId))
				.execute();
		return result > 0 ? true : false;
	}
	
	/**
	 * 优惠券领取明细分页列表
	 * @param param
	 * @return
	 */
	public PageResult<CouponGetDetailVo> getDetail(CouponGetDetailParam param) {
		SelectJoinStep<Record> select = db().select()
				.from(CUSTOMER_AVAIL_COUPONS 
						.leftJoin(MRKING_VOUCHER) 
						.on(CUSTOMER_AVAIL_COUPONS.ACT_ID .eq(MRKING_VOUCHER.ID))
						.leftJoin(USER) 
						.on(CUSTOMER_AVAIL_COUPONS.USER_ID.eq(USER.USER_ID)));
		SelectConditionStep<Record> sql = detailBuildOptions(select,param);
		sql.orderBy(CUSTOMER_AVAIL_COUPONS.USED_TIME);
		PageResult<CouponGetDetailVo> detailList = this.getPageResult(sql,param.getCurrentPage(),param.getPageRows(),CouponGetDetailVo.class);
		return detailList;		
	}
	
	/**
	 * 按条件查询领取明细
	 * @param select
	 * @param param
	 * @return
	 */
	public SelectConditionStep<Record> detailBuildOptions(SelectJoinStep<Record> select,CouponGetDetailParam param) {
		SelectConditionStep<Record> sql = select.where(CUSTOMER_AVAIL_COUPONS.ACT_ID .eq(param.getId()));
		if(param.getMobile() != null) {
			sql.and(USER.MOBILE.eq(param.getMobile()));
		}
		if(param.getUserName() != null) {
			sql.and(USER.USERNAME.eq(param.getUserName()));
		}
		if(param.getIsUsed() != null) {
			sql.and(CUSTOMER_AVAIL_COUPONS.IS_USED.eq(param.getIsUsed()));
		}
		return sql;
		
	}
}
