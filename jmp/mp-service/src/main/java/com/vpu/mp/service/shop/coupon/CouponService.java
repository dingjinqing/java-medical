package com.vpu.mp.service.shop.coupon;

import static com.vpu.mp.db.shop.Tables.CUSTOMER_AVAIL_COUPONS;
import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.lambda.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MrkingVoucherRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.coupon.CouponGetDetailParam;
import com.vpu.mp.service.pojo.shop.coupon.CouponListParam;
import com.vpu.mp.service.pojo.shop.coupon.CouponListVo;
import com.vpu.mp.service.pojo.shop.coupon.CouponParam;
import com.vpu.mp.service.pojo.shop.coupon.CouponView;
import com.vpu.mp.service.pojo.shop.coupon.hold.CouponHoldListParam;
import com.vpu.mp.service.pojo.shop.coupon.hold.CouponHoldListVo;
import com.vpu.mp.service.pojo.wxapp.coupon.AvailCouponDetailParam;
import com.vpu.mp.service.pojo.wxapp.coupon.AvailCouponDetailVo;
import com.vpu.mp.service.pojo.wxapp.coupon.AvailCouponParam;
import com.vpu.mp.service.pojo.wxapp.coupon.AvailCouponVo;


/**
 * 优惠券管理
 *
 * @author 常乐
 * 2019年7月16日
 */
@Service

public class CouponService extends ShopBaseService {
    @Autowired
    public CouponGiveService couponGiveService;

    @Autowired
    public CouponHoldService couponHold;

    private String aliasCode;

    /**
     * 创建优惠券
     *
     * @param couponInfo
     * @return
     */
    public Boolean couponAdd(CouponParam couponInfo) {
        MrkingVoucherRecord record = new MrkingVoucherRecord();
        record.setSurplus(couponInfo.getTotalAmount());
        record.setAliasCode(this.generateAliasCode());
        if(couponInfo.getValidity( ) > 0) {
			Timestamp now = Timestamp.valueOf(LocalDateTime.now());
			Calendar cal = Calendar.getInstance();   
			cal.setTime(now);   
			cal.add(Calendar.HOUR,(couponInfo.getValidity() * 24 + couponInfo.getValidityHour()));
			cal.add(Calendar.MINUTE, couponInfo.getValidityMinute());
			Date date = cal.getTime(); 
        }
        this.assign(couponInfo, record);
        return db().executeInsert(record) > 0 ? true : false;
    }


    /**
     * 生成优惠券唯一活动吗
     *
     * @return
     */
    public String generateAliasCode() {
        do {
            int randomNum = (int) (Math.random() * 10000000) + 10000000;
            this.aliasCode = "b" + randomNum;
        } while (this.hasAliasCode(aliasCode) > 0);
        return aliasCode;
    }

    /**
     * 判断优惠券唯一活动码是否存在
     *
     * @return
     */
    public int hasAliasCode(String aliasCode) {
        int res = db().selectCount().from(MRKING_VOUCHER).where(MRKING_VOUCHER.ALIAS_CODE.eq(aliasCode)).fetchOne().into(Integer.class);
        return res;
    }

    /**
     * 获取优惠券分页列表
     *
     * @param param
     * @return
     */
    public PageResult<CouponListVo> getCouponList(CouponListParam param) {
        System.out.println(param);
        SelectJoinStep<Record> select = db().select().from(MRKING_VOUCHER);

        //条件查询
        SelectConditionStep<Record> sql = buildOptions(select, param);
        sql.orderBy(MRKING_VOUCHER.CREATE_TIME.desc());
        PageResult<CouponListVo> couponList = this.getPageResult(sql, param.getCurrentPage(), param.getPageRows(), CouponListVo.class);
        for (CouponListVo list : couponList.dataList) {
            int used = db().selectCount().from(CUSTOMER_AVAIL_COUPONS).where(CUSTOMER_AVAIL_COUPONS.ACT_ID.eq(list.getId())).and(CUSTOMER_AVAIL_COUPONS.IS_USED.eq((byte) 1)).fetchOne().into(Integer.class);
            list.setUsed(used);
        }
        return couponList;
    }

    /**
     * 优惠券列表根据条件筛选
     *
     * @param select
     * @param param
     * @return
     */
    public SelectConditionStep<Record> buildOptions(SelectJoinStep<Record> select, CouponListParam param) {
        SelectConditionStep<Record> sql = select.where(MRKING_VOUCHER.DEL_FLAG.eq((byte) 0));
        if (param.getActName() != null) {
            sql = sql.and(MRKING_VOUCHER.ACT_NAME.eq(param.getActName()));
        }

        Timestamp nowDate = new Timestamp(System.currentTimeMillis());

        if (param.getNav() != null) {
            switch (param.getNav()) {
                //进行中
                case 1:
                    sql = sql.and(MRKING_VOUCHER.START_TIME.le(nowDate)).and(MRKING_VOUCHER.END_TIME.ge(nowDate)).and(MRKING_VOUCHER.ENABLED.eq((byte) 1));
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
                default:

            }
        }
        System.out.println(sql);
        return sql;
    }

    /**
     * 获取单条优惠券信息
     *
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
     * 获取单个优惠券信息
     *
     * @param couponId
     * @return
     */
    public MrkingVoucherRecord getOneCouponById(Integer couponId) {
        return db().selectFrom(MRKING_VOUCHER).where(MRKING_VOUCHER.ID.eq(couponId)).fetchOne();
    }


    /**
     * 保存编辑信息
     *
     * @param param
     * @return
     */
    public boolean saveCouponInfo(CouponParam param) {
        MrkingVoucherRecord record = new MrkingVoucherRecord();
        this.assign(param, record);
        return db().executeUpdate(record) > 0 ? true : false;
    }

    /**
     * 停用优惠券
     *
     * @param couponId
     * @return
     */
    public boolean couponPause(Integer couponId) {
        int result = db().update(MRKING_VOUCHER)
            .set(MRKING_VOUCHER.ENABLED, (byte) 0)
            .where(MRKING_VOUCHER.ID.eq(couponId))
            .execute();
        return result > 0 ? true : false;
    }

    /**
     * 删除优惠券（假删除）
     *
     * @param couponId
     * @return
     */
    public boolean couponDel(Integer couponId) {
        int result = db().update(MRKING_VOUCHER)
            .set(MRKING_VOUCHER.DEL_FLAG, (byte) 1)
            .where(MRKING_VOUCHER.ID.eq(couponId))
            .execute();
        return result > 0 ? true : false;
    }

    /**
     * 优惠券领取明细分页列表
     *
     * @param param
     * @return
     */
    public PageResult<CouponHoldListVo> getDetail(CouponGetDetailParam param) {
        CouponHoldListParam couponParam = new CouponHoldListParam();
        couponParam.setActId(param.getId());
        couponParam.setMobile(param.getMobile());
        couponParam.setUsername(param.getUserName());
        couponParam.setStatus(param.getIsUsed());
        couponParam.setCurrentPage(param.getCurrentPage());
        couponParam.setPageRows(param.getPageRows());
        return couponHold.getCouponHoldList(couponParam);

    }

    /**
     * 取单个优惠券的基本信息
     *
     * @param id
     * @return
     */
    public CouponView getCouponViewById(int id) {
        return db().select(MRKING_VOUCHER.ID, MRKING_VOUCHER.ACT_CODE, MRKING_VOUCHER.ACT_NAME, MRKING_VOUCHER.DENOMINATION, MRKING_VOUCHER.LEAST_CONSUME, MRKING_VOUCHER.USE_CONSUME_RESTRICT, MRKING_VOUCHER.SURPLUS, MRKING_VOUCHER.VALIDITY_TYPE, MRKING_VOUCHER.VALIDITY, MRKING_VOUCHER.VALIDITY_HOUR, MRKING_VOUCHER.VALIDITY_MINUTE, MRKING_VOUCHER.START_TIME, MRKING_VOUCHER.END_TIME, MRKING_VOUCHER.RECOMMEND_GOODS_ID, MRKING_VOUCHER.RECOMMEND_CAT_ID, MRKING_VOUCHER.RECOMMEND_SORT_ID).from(MRKING_VOUCHER).where(MRKING_VOUCHER.ID.eq(id)).and(MRKING_VOUCHER.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)).fetchOne().into(CouponView.class);
    }

    /**
     * 根据id批量获取优惠价信息
     *
     * @param ids
     * @return
     */
    public List<CouponView> getCouponViewByIds(List<Integer> ids) {
        return db().select(MRKING_VOUCHER.ID, MRKING_VOUCHER.ACT_NAME, MRKING_VOUCHER.DENOMINATION, MRKING_VOUCHER.USE_CONSUME_RESTRICT, MRKING_VOUCHER.LEAST_CONSUME, MRKING_VOUCHER.SURPLUS, MRKING_VOUCHER.VALIDITY_TYPE, MRKING_VOUCHER.START_TIME, MRKING_VOUCHER.END_TIME, MRKING_VOUCHER.VALIDITY, MRKING_VOUCHER.VALIDITY_HOUR, MRKING_VOUCHER.VALIDITY_MINUTE)
            .from(MRKING_VOUCHER)
            .where(MRKING_VOUCHER.ID.in(ids)).and(MRKING_VOUCHER.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .fetchInto(CouponView.class);
    }

    /**
     * 用户优惠券列表
     *
     * @param param
     * @return
     */
    public PageResult<AvailCouponVo> getCouponByUser(AvailCouponParam param) {
        //某用户全部优惠券
    	SelectJoinStep<? extends Record> select = db().select(CUSTOMER_AVAIL_COUPONS.ID, CUSTOMER_AVAIL_COUPONS.COUPON_SN, CUSTOMER_AVAIL_COUPONS.TYPE, CUSTOMER_AVAIL_COUPONS.AMOUNT, CUSTOMER_AVAIL_COUPONS.START_TIME,
            CUSTOMER_AVAIL_COUPONS.END_TIME, CUSTOMER_AVAIL_COUPONS.IS_USED, CUSTOMER_AVAIL_COUPONS.LIMIT_ORDER_AMOUNT, MRKING_VOUCHER.ACT_NAME).from(CUSTOMER_AVAIL_COUPONS
            .leftJoin(MRKING_VOUCHER).on(CUSTOMER_AVAIL_COUPONS.ACT_ID.eq(MRKING_VOUCHER.ID)));
    	
    	//根据优惠券使用状态、过期状态条件筛选
    	MpBuildOptions(select, param);
    	SelectConditionStep<? extends Record> sql = select.where(CUSTOMER_AVAIL_COUPONS.USER_ID.eq(param.getUserId()));
    	PageResult<AvailCouponVo> list = getPageResult(sql, param.getCurrentPage(), param.getPageRows(), AvailCouponVo.class);
        return list;
    }
    
    /**
     * 用户优惠券状态筛选条件
     * @param select
     * @param param
     */
    public void MpBuildOptions(SelectJoinStep<? extends Record> select, AvailCouponParam param) {
    	Byte isUsed = param.getNav();
    	  Timestamp now = Timestamp.valueOf(LocalDateTime.now());
    	if(isUsed == 0 || isUsed == 1) {  //未使用、已使用状态
    		select.where(CUSTOMER_AVAIL_COUPONS.IS_USED.eq((byte) isUsed).and(MRKING_VOUCHER.END_TIME.ge(now)));
    	}else {  //已过期状态
    		//根据开始时间、结束时间判断
            Condition dateCondition = MRKING_VOUCHER.END_TIME.le(now).and(MRKING_VOUCHER.VALIDITY_TYPE.eq((byte) 0));
           //根据有效天数判断
            Condition limitTimeCondition = MRKING_VOUCHER.VALIDITY.gt(0).or(MRKING_VOUCHER.VALIDITY_HOUR.gt(0)).or(MRKING_VOUCHER.VALIDITY_MINUTE.gt(0));
            Condition timeCondition = dateCondition.or(limitTimeCondition);
            select.where(timeCondition);
    	}
    }

    /**
     * 优惠券详情
     *
     * @param param ：couponSn优惠券编码
     * @return
     */
    public AvailCouponDetailVo getCouponDetail(AvailCouponDetailParam param) {
        AvailCouponDetailVo detail = db().select(CUSTOMER_AVAIL_COUPONS.ID, CUSTOMER_AVAIL_COUPONS.COUPON_SN, CUSTOMER_AVAIL_COUPONS.TYPE, CUSTOMER_AVAIL_COUPONS.AMOUNT, CUSTOMER_AVAIL_COUPONS.START_TIME,
            CUSTOMER_AVAIL_COUPONS.END_TIME, CUSTOMER_AVAIL_COUPONS.IS_USED, CUSTOMER_AVAIL_COUPONS.LIMIT_ORDER_AMOUNT, MRKING_VOUCHER.ACT_NAME).from(CUSTOMER_AVAIL_COUPONS
            .leftJoin(MRKING_VOUCHER).on(CUSTOMER_AVAIL_COUPONS.ACT_ID.eq(MRKING_VOUCHER.ID)))
            .where(CUSTOMER_AVAIL_COUPONS.COUPON_SN.eq(param.couponSn))
            .fetchOne().into(AvailCouponDetailVo.class);
        return detail;
    }


    /**
     * 获得可使用的优惠券数
     *
     * @param userId
     * @return
     */
    public Integer getCanUseCouponNum(Integer userId) {
        Result<Record1<Integer>> record = db().selectCount().from(CUSTOMER_AVAIL_COUPONS)
            .where(CUSTOMER_AVAIL_COUPONS.USER_ID.eq(userId).and(CUSTOMER_AVAIL_COUPONS.IS_USED.eq((byte) 0)
                .and(CUSTOMER_AVAIL_COUPONS.END_TIME.gt(DateUtil.getSqlTimestamp()))))
            .fetch();
        return record.get(0).into(Integer.class);
    }

    /**
     * 获取商品可获取的最紧密的优惠券
     * 现根据商品act_code类型排序voucher是减金额，discount打折，目前逻辑是先金额，金额减的多的靠前，然后再是打折
     * @param goodsId
     */
    public MrkingVoucherRecord getGoodsCouponFirst(Integer goodsId, Integer catId, Integer sortId, Byte suit) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        // 时间限时条件拼接
        Condition dateCondition = MRKING_VOUCHER.START_TIME.le(now).and(MRKING_VOUCHER.END_TIME.ge(now));
        Condition limitTimeCondition = MRKING_VOUCHER.VALIDITY.gt(0).or(MRKING_VOUCHER.VALIDITY_HOUR.gt(0)).or(MRKING_VOUCHER.VALIDITY_MINUTE.gt(0));
        Condition timeCondition = dateCondition.or(limitTimeCondition);

        // 优惠券指定的关系条件过滤
        Condition recommendNullCondition = MRKING_VOUCHER.RECOMMEND_GOODS_ID.isNull().and(MRKING_VOUCHER.RECOMMEND_CAT_ID.isNull()).and(MRKING_VOUCHER.RECOMMEND_SORT_ID.isNull());
        Condition recommendCondition = DslPlus.findInSet(goodsId, MRKING_VOUCHER.RECOMMEND_GOODS_ID);
        if (catId != null) {
            recommendCondition = recommendCondition.or(DslPlus.findInSet(catId, MRKING_VOUCHER.RECOMMEND_CAT_ID));
        }
        if (sortId != null) {
            recommendCondition = recommendCondition.or(DslPlus.findInSet(sortId, MRKING_VOUCHER.RECOMMEND_CAT_ID));
        }
        recommendCondition = recommendNullCondition.or(recommendCondition);

        // 优惠券基本条件过滤
        Condition otherCondition = MRKING_VOUCHER.ENABLED.eq((byte) 1).and(MRKING_VOUCHER.SURPLUS.gt(0))
            .and(MRKING_VOUCHER.TYPE.eq((byte) 0)).and(MRKING_VOUCHER.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        if (suit != null) {
            otherCondition.and(MRKING_VOUCHER.SUIT_GOODS.eq(suit));
        }
        // 现根据商品act_code类型排序voucher是减金额，discount打折，目前逻辑是先金额，金额减的多的靠前，然后再是打折
        MrkingVoucherRecord mrkingVoucherRecord =
            db().selectFrom(MRKING_VOUCHER).where(timeCondition.and(otherCondition).and(recommendCondition))
            .orderBy(MRKING_VOUCHER.ACT_CODE.desc(),MRKING_VOUCHER.DENOMINATION,MRKING_VOUCHER.CREATE_TIME.desc()).fetchAny();

        return mrkingVoucherRecord;
    }

}
