package com.vpu.mp.service.shop.coupon;

import com.vpu.mp.db.shop.tables.MrkingVoucher;
import com.vpu.mp.db.shop.tables.records.MrkingVoucherRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.coupon.*;
import com.vpu.mp.service.pojo.shop.coupon.hold.CouponHoldListParam;
import com.vpu.mp.service.pojo.shop.coupon.hold.CouponHoldListVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.coupon.*;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.coupon.OrderCouponVo;
import jodd.util.StringUtil;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.vpu.mp.db.shop.Tables.CUSTOMER_AVAIL_COUPONS;
import static com.vpu.mp.db.shop.Tables.MRKING_VOUCHER;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;

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

    /**可用会员卡*/
    public static final byte COUPON_IS_USED_STATUS_AVAIL = 0;

    /**普通优惠券*/
    public static final byte COUPON_TYPE_NORMAL = 0;

    /**优惠类型 0:减价;1打折*/
    public static final byte COUPON_TYPE_ = 0;

    /**
     * 创建优惠券
     *
     * @param couponInfo
     * @return
     */
    public Boolean couponAdd(CouponParam couponInfo) {
        System.out.println(couponInfo);
        MrkingVoucherRecord record = new MrkingVoucherRecord();
        record.setSurplus(couponInfo.getTotalAmount());
        record.setAliasCode(this.generateAliasCode());
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
            int randomNum = new Random().nextInt(10000000) + 10000000;
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
        SelectJoinStep<Record> select = db().select().from(MRKING_VOUCHER);

        //条件查询
        SelectConditionStep<Record> sql = buildOptions(select, param);
        sql.orderBy(MRKING_VOUCHER.CREATE_TIME.desc());
        PageResult<CouponListVo> couponList = this.getPageResult(sql, param.getCurrentPage(), param.getPageRows(), CouponListVo.class);
        for (CouponListVo list : couponList.dataList) {
            int used = db().selectCount().from(CUSTOMER_AVAIL_COUPONS).where(CUSTOMER_AVAIL_COUPONS.ACT_ID.eq(list.getId())).and(CUSTOMER_AVAIL_COUPONS.IS_USED.eq((byte) 1)).fetchOne().into(Integer.class);
            list.setUsed(used);
            if(list.getValidityType().equals(BaseConstant.COUPON_VALIDITY_TYPE_FIXED)){
                list.setCurrentState(Util.getActStatus(list.getEnabled(),list.getStartTime(),list.getEndTime()));
            }else {
                list.setCurrentState(Util.getActStatus(list.getEnabled(),list.getStartTime(),list.getEndTime(),BaseConstant.COUPON_VALIDITY_TYPE_FLEXIBLE));
            }
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
        SelectConditionStep<Record> sql = select.where(MRKING_VOUCHER.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
        if (param.getActName() != null) {
            sql = sql.and(MRKING_VOUCHER.ACT_NAME.contains(param.getActName()));
        }

        Timestamp nowDate = new Timestamp(System.currentTimeMillis());

        if (param.getNav() != null && param.getNav() > 0) {
            switch (param.getNav()) {
                //进行中
                case 1:
                    sql = sql.and((MRKING_VOUCHER.START_TIME.le(nowDate)).and(MRKING_VOUCHER.END_TIME.ge(nowDate)).or(MRKING_VOUCHER.VALIDITY_TYPE.eq(BaseConstant.COUPON_VALIDITY_TYPE_FLEXIBLE)))
                            .and(MRKING_VOUCHER.ENABLED.eq(BaseConstant.COUPON_ENABLED_NORMAL));
                    break;
                //未开始
                case 2:
                    sql = sql.and(MRKING_VOUCHER.START_TIME.ge(nowDate)).and(MRKING_VOUCHER.ENABLED.eq(BaseConstant.COUPON_ENABLED_NORMAL)).and(MRKING_VOUCHER.ENABLED.eq(BaseConstant.COUPON_ENABLED_NORMAL)).and(MRKING_VOUCHER.VALIDITY_TYPE.eq(BaseConstant.COUPON_VALIDITY_TYPE_FIXED));
                    break;
                //已过期
                case 3:
                    sql = sql.and(MRKING_VOUCHER.END_TIME.le(nowDate)).and(MRKING_VOUCHER.ENABLED.eq(BaseConstant.COUPON_ENABLED_NORMAL)).and(MRKING_VOUCHER.VALIDITY_TYPE.eq(BaseConstant.COUPON_VALIDITY_TYPE_FIXED));
                    break;
                //已停用
                case 4:
                    sql = sql.and(MRKING_VOUCHER.ENABLED.eq(BaseConstant.COUPON_ENABLED_DISABLED));
                    break;
                default:

            }
        }
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
            .set(MRKING_VOUCHER.ENABLED, BaseConstant.COUPON_ENABLED_DISABLED)
            .where(MRKING_VOUCHER.ID.eq(couponId))
            .execute();
        return result > 0 ? true : false;
    }

    /**
     * 启用优惠券
     *
     * @param couponId
     * @return
     */
    public boolean couponOpen(Integer couponId) {
        int result = db().update(MRKING_VOUCHER)
                .set(MRKING_VOUCHER.ENABLED, BaseConstant.COUPON_ENABLED_NORMAL)
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
            .set(MRKING_VOUCHER.DEL_FLAG, DelFlag.DISABLE_VALUE)
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
     * 根据id批量获取优惠卷信息
     *
     * @param ids
     * @return
     */
    public List<CouponView> getCouponViewByIds(List<Integer> ids) {
        return db().select(MRKING_VOUCHER.ID, MRKING_VOUCHER.ACT_NAME,MRKING_VOUCHER.ACT_CODE, MRKING_VOUCHER.DENOMINATION,
                MRKING_VOUCHER.USE_CONSUME_RESTRICT, MRKING_VOUCHER.LEAST_CONSUME, MRKING_VOUCHER.SURPLUS,
                MRKING_VOUCHER.VALIDITY_TYPE, MRKING_VOUCHER.START_TIME, MRKING_VOUCHER.END_TIME, MRKING_VOUCHER.VALIDITY,
                MRKING_VOUCHER.VALIDITY_HOUR, MRKING_VOUCHER.VALIDITY_MINUTE,MRKING_VOUCHER.RANDOM_MAX,MRKING_VOUCHER.RANDOM_MIN)
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
    		select.where(CUSTOMER_AVAIL_COUPONS.IS_USED.eq(isUsed).and(CUSTOMER_AVAIL_COUPONS.END_TIME.ge(now)));
    	}else {  //已过期状态
    		//根据有效 开始时间、结束时间判断
            select.where(CUSTOMER_AVAIL_COUPONS.END_TIME.le(now));
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
     * 获取各状态下优惠券数量
     * @param userId
     */
    public AvailCouponListVo getEachStatusNum(Integer userId,AvailCouponListVo red){
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        System.out.println(userId);
        //未使用
        Integer unused = db().selectCount().from(CUSTOMER_AVAIL_COUPONS).where(CUSTOMER_AVAIL_COUPONS.USER_ID.eq(userId).and(CUSTOMER_AVAIL_COUPONS.IS_USED.eq((byte) 0)
                .and(CUSTOMER_AVAIL_COUPONS.END_TIME.gt(now))))
                .fetch().get(0).into(Integer.class);
        //已使用
        Integer used = db().selectCount().from(CUSTOMER_AVAIL_COUPONS).where(CUSTOMER_AVAIL_COUPONS.USER_ID.eq(userId).and(CUSTOMER_AVAIL_COUPONS.IS_USED.eq((byte) 1)
                .and(CUSTOMER_AVAIL_COUPONS.END_TIME.gt(now))))
                .fetch().get(0).into(Integer.class);
        //已过期
        Integer expire = db().selectCount().from(CUSTOMER_AVAIL_COUPONS).where(CUSTOMER_AVAIL_COUPONS.USER_ID.eq(userId).and(CUSTOMER_AVAIL_COUPONS.END_TIME.le(now)))
                .fetch().get(0).into(Integer.class);

        red.setUnusedNum(unused);
        red.setUsedNum(used);
        red.setExpiredNum(expire);
        return red;
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
     * 获取商品可展示的最紧密的优惠券
     * 现根据商品act_code类型排序voucher是减金额，discount打折，目前逻辑是先金额，金额减的多的靠前，然后再是打折
     * @param goodsId
     */
    public MrkingVoucherRecord getGoodsCouponFirst(Integer goodsId, Integer catId, Integer sortId, Byte suit) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        // 时间限时条件拼接
        // 第一种指定开始结束时间优惠券
        Condition dateCondition = MRKING_VOUCHER.END_TIME.ge(now);
        // 第二种从领取日指定可使用时间
        Condition limitTimeCondition = MRKING_VOUCHER.VALIDITY.gt(0).or(MRKING_VOUCHER.VALIDITY_HOUR.gt(0)).or(MRKING_VOUCHER.VALIDITY_MINUTE.gt(0));
        Condition timeCondition = dateCondition.or(limitTimeCondition);

        // 优惠券指定的关系条件过滤
        Condition recommendNullCondition = MRKING_VOUCHER.RECOMMEND_GOODS_ID.isNull().and(MRKING_VOUCHER.RECOMMEND_CAT_ID.isNull()).and(MRKING_VOUCHER.RECOMMEND_SORT_ID.isNull());
        Condition recommendCondition = DslPlus.findInSet(goodsId, MRKING_VOUCHER.RECOMMEND_GOODS_ID);
        if (catId != null) {
            recommendCondition = recommendCondition.or(DslPlus.findInSet(catId, MRKING_VOUCHER.RECOMMEND_CAT_ID));
        }
        if (sortId != null) {
            recommendCondition = recommendCondition.or(DslPlus.findInSet(sortId, MRKING_VOUCHER.RECOMMEND_SORT_ID));
        }
        recommendCondition = recommendNullCondition.or(recommendCondition);

        // 优惠券基本条件过滤
        Condition otherCondition = MRKING_VOUCHER.ENABLED.eq(BaseConstant.COUPON_ENABLED_NORMAL).and(MRKING_VOUCHER.SURPLUS.gt(0))
            .and(MRKING_VOUCHER.TYPE.eq(BaseConstant.COUPON_TYPE_NORMAL)).and(MRKING_VOUCHER.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        if (suit != null) {
            otherCondition.and(MRKING_VOUCHER.SUIT_GOODS.eq(suit));
        }
        // 现根据商品act_code类型排序voucher是减金额，discount打折，目前逻辑是先金额，金额减的多的靠前，然后再是打折
        MrkingVoucherRecord mrkingVoucherRecord =
            db().selectFrom(MRKING_VOUCHER).where(timeCondition.and(otherCondition).and(recommendCondition))
            .orderBy(MRKING_VOUCHER.ACT_CODE.desc(),MRKING_VOUCHER.DENOMINATION,MRKING_VOUCHER.CREATE_TIME.desc()).fetchAny();

        return mrkingVoucherRecord;
    }

    /**
     * 获取所有可用给的优惠卷
     * @param isHasStock 是否限制库存
     * @return
     */
    public List<CouponAllVo> getCouponAll(Boolean isHasStock) {
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        SelectConditionStep<Record6<Integer, String, String, Byte, Integer, Byte>> couponAllVos = db()
                .select(MRKING_VOUCHER.ID, MRKING_VOUCHER.ACT_NAME, MRKING_VOUCHER.ALIAS_CODE, MRKING_VOUCHER.TYPE, MRKING_VOUCHER.SURPLUS, MRKING_VOUCHER.LIMIT_SURPLUS_FLAG)
                .from(MRKING_VOUCHER)
//                .(MRKING_VOUCHER.TYPE.eq(BaseConstant.COUPON_TYPE_NORMAL))
                .where(MRKING_VOUCHER.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
                .and(MRKING_VOUCHER.START_TIME.le(nowTime).and(MRKING_VOUCHER.END_TIME.gt(nowTime))
                .or(MRKING_VOUCHER.VALIDITY.gt(0).or(MRKING_VOUCHER.VALIDITY_HOUR.gt(0)).or(MRKING_VOUCHER.VALIDITY_MINUTE.gt(0))));
        if (isHasStock){
            couponAllVos.and(MRKING_VOUCHER.LIMIT_SURPLUS_FLAG.eq(BaseConstant.COUPON_LIMIT_SURPLUS_FLAG_UNLIMITED).or(MRKING_VOUCHER.SURPLUS.gt(0)));
        }
        return couponAllVos.fetchInto(CouponAllVo.class);
    }

    /**
     * 获取用户可用优惠券列表
     * 王帅
     * @param userId
     * @return 当前用户可用优惠卷
     */
    public List<OrderCouponVo> getValidCoupons(Integer userId){
        Timestamp now = DateUtil.getSqlTimestamp();
        return db().select(CUSTOMER_AVAIL_COUPONS.ID, CUSTOMER_AVAIL_COUPONS.COUPON_SN, CUSTOMER_AVAIL_COUPONS.TYPE, CUSTOMER_AVAIL_COUPONS.AMOUNT, CUSTOMER_AVAIL_COUPONS.START_TIME,
            CUSTOMER_AVAIL_COUPONS.END_TIME, CUSTOMER_AVAIL_COUPONS.IS_USED, CUSTOMER_AVAIL_COUPONS.LIMIT_ORDER_AMOUNT,
            MRKING_VOUCHER.ACT_NAME, MRKING_VOUCHER.RECOMMEND_GOODS_ID, MRKING_VOUCHER.RECOMMEND_CAT_ID, MRKING_VOUCHER.RECOMMEND_PRODUCT_ID, MRKING_VOUCHER.RECOMMEND_SORT_ID).
            from(CUSTOMER_AVAIL_COUPONS).
            leftJoin(MRKING_VOUCHER).on(CUSTOMER_AVAIL_COUPONS.ACT_ID.eq(MRKING_VOUCHER.ID)).
            where(CUSTOMER_AVAIL_COUPONS.USER_ID.eq(userId).and(CUSTOMER_AVAIL_COUPONS.IS_USED.eq((COUPON_IS_USED_STATUS_AVAIL)).and(CUSTOMER_AVAIL_COUPONS.START_TIME.le(now)).and(CUSTOMER_AVAIL_COUPONS.END_TIME.greaterThan(now)))).
            orderBy(CUSTOMER_AVAIL_COUPONS.END_TIME.desc()).
            fetchInto(OrderCouponVo.class);
    }

    /**
     * 获取优惠券列
     * 王帅
     * @param couponSn no
     * @return 优惠卷
     */
    public OrderCouponVo getValidCoupons(String couponSn){
        Timestamp now = DateUtil.getSqlTimestamp();
        return db().select(CUSTOMER_AVAIL_COUPONS.ID, CUSTOMER_AVAIL_COUPONS.COUPON_SN, CUSTOMER_AVAIL_COUPONS.COUPON_SN, CUSTOMER_AVAIL_COUPONS.TYPE, CUSTOMER_AVAIL_COUPONS.AMOUNT, CUSTOMER_AVAIL_COUPONS.START_TIME,
            CUSTOMER_AVAIL_COUPONS.END_TIME, CUSTOMER_AVAIL_COUPONS.IS_USED, CUSTOMER_AVAIL_COUPONS.LIMIT_ORDER_AMOUNT,
            MRKING_VOUCHER.ACT_NAME, MRKING_VOUCHER.RECOMMEND_GOODS_ID, MRKING_VOUCHER.RECOMMEND_CAT_ID, MRKING_VOUCHER.RECOMMEND_PRODUCT_ID, MRKING_VOUCHER.RECOMMEND_SORT_ID).
            from(CUSTOMER_AVAIL_COUPONS).
            leftJoin(MRKING_VOUCHER).on(CUSTOMER_AVAIL_COUPONS.ACT_ID.eq(MRKING_VOUCHER.ID)).
            where(CUSTOMER_AVAIL_COUPONS.COUPON_SN.eq(couponSn).and(CUSTOMER_AVAIL_COUPONS.IS_USED.eq((COUPON_IS_USED_STATUS_AVAIL)).and(CUSTOMER_AVAIL_COUPONS.START_TIME.le(now)).and(CUSTOMER_AVAIL_COUPONS.END_TIME.greaterThan(now)))).
            fetchOneInto(OrderCouponVo.class);
    }

    /**
     * 判断该product是否可以使用该优惠卷
     * 王帅
     * @param coupon 优惠卷
     * @param bo bo
     * @return 是否可以使用
     */
    public boolean isContainsProduct(OrderCouponVo coupon, OrderGoodsBo bo){
        logger().info("判断该product是否可以使用该优惠卷strat,优惠卷：{}，商品：{}", coupon, bo);
        //全部为空为全部商品
        if(StringUtils.isBlank(new StringBuilder().append(coupon.getRecommendGoodsId()).append(coupon.getRecommendCatId()).append(coupon.getRecommendSortId()).append(coupon.getRecommendProductId()).toString())){
            return true;
        }
        if(StringUtil.isNotBlank(coupon.getRecommendGoodsId())){
            return Arrays.asList(coupon.getRecommendGoodsId().split(",")).contains(bo.getGoodsId().toString());
        }
        if(StringUtil.isNotBlank(coupon.getRecommendCatId())){
            return Arrays.asList(coupon.getRecommendCatId().split(",")).contains(bo.getCatId().toString());
        }
        if(StringUtil.isNotBlank(coupon.getRecommendSortId())){
            return Arrays.asList(coupon.getRecommendSortId().split(",")).contains(bo.getSortId().toString());
        }
        //crm相关
        if(StringUtil.isNotBlank(coupon.getRecommendProductId())){
            return Arrays.asList(coupon.getRecommendProductId().split(",")).contains(bo.getProductId().toString());
        }
        return true;
    }

    /**
     * 王帅
     * @param coupon 优惠卷
     * @param totalPrice 商品总价
     * @return 折扣价格
     */
    public BigDecimal getDiscountAmount(OrderCouponVo coupon, BigDecimal totalPrice){
        logger().info("优惠券折扣金额计算（CouponService）start");
        if(OrderConstant.T_CAC_TYPE_REDUCTION == coupon.getType()){
            //代金券
            return BigDecimalUtil.compareTo(coupon.getLimitOrderAmount(), totalPrice) < 1 ? coupon.getAmount() : BigDecimal.ZERO;
        }else if(OrderConstant.T_CAC_TYPE_DISCOUNT == coupon.getType()){
            //打折券 return = totalPrice * (1 - coupon.getAmount /10)
            return BigDecimalUtil.compareTo(coupon.getLimitOrderAmount(), totalPrice) < 1 ?
                BigDecimalUtil.multiplyOrDivide(
                    BigDecimalUtil.BigDecimalPlus.create(totalPrice, BigDecimalUtil.Operator.multiply),
                    BigDecimalUtil.BigDecimalPlus.create(BigDecimalUtil.addOrSubtrac(BigDecimalUtil.BigDecimalPlus.create(BigDecimal.ONE, BigDecimalUtil.Operator.subtrac),
                        BigDecimalUtil.BigDecimalPlus.create(BigDecimalUtil.multiplyOrDivide(BigDecimalUtil.BigDecimalPlus.create(coupon.getAmount(), BigDecimalUtil.Operator.Divide),
                            BigDecimalUtil.BigDecimalPlus.create(BigDecimal.TEN, null)), null)
                    ), null))
            : BigDecimal.ZERO;
        }
        return BigDecimal.ZERO;
    }

    /**
     * Get small inventory coupon map.获取库存偏小的优惠券
     *
     * @param num the num
     * @return the map
     */
    public Map<Integer, String> getSmallInventoryCoupon(Integer num) {
        return db().select(MRKING_VOUCHER.ID, MrkingVoucher.MRKING_VOUCHER.ACT_NAME)
            .from(MRKING_VOUCHER)
            .where(MRKING_VOUCHER.SURPLUS.lessOrEqual(num))
            .and(MRKING_VOUCHER.ENABLED.eq(BYTE_ONE))
            .and(MRKING_VOUCHER.DEL_FLAG.eq(BYTE_ZERO))
            .and(MRKING_VOUCHER.CREATE_TIME.add(MRKING_VOUCHER.VALIDATION_CODE).greaterThan(Timestamp.valueOf(LocalDateTime.now())))
            .orderBy(MRKING_VOUCHER.SURPLUS, MRKING_VOUCHER.CREATE_TIME)
            .limit(5)
            .fetchMap(MRKING_VOUCHER.ID, MRKING_VOUCHER.ACT_NAME);
    }
    /**发放*/
    private static final Byte COUPON_GIVE = 0;
    /**领取*/
    private static final Byte COUPON_RECEIVE = 1;

    /**
     * 发放/领取优惠券后更新优惠券表发放/领取的数量
     * @author liangchen
     * @param accessMode 0:发放,1:领取
     * @param couponArray 优惠券数组
     */
    public void updateCouponGiveOrReceiveNum(Byte accessMode,String[] couponArray){
        //遍历优惠券
        for(String couponId : couponArray){
            //更新优惠券人数和数量
            selectCouponNum(accessMode,Integer.valueOf(couponId));
        }
    }

    /**
     * 查询数量变化
     * @author liangchen
     * @param accessMode 0:发放,1:领取
     * @param couponId 当前操作的优惠券id
     */
    private void selectCouponNum(Byte accessMode, Integer couponId){
        //查询人数
        short peopleNum = db().select(DSL.countDistinct(CUSTOMER_AVAIL_COUPONS.USER_ID))
                .from(CUSTOMER_AVAIL_COUPONS)
                .where(CUSTOMER_AVAIL_COUPONS.ACT_ID.eq(couponId))
                .and(CUSTOMER_AVAIL_COUPONS.DEL_FLAG.eq(BYTE_ZERO))
                .and(CUSTOMER_AVAIL_COUPONS.ACCESS_MODE.eq(accessMode))
                .fetchOneInto(short.class);
        //查询数量
        short couponNum = db().select(DSL.count(CUSTOMER_AVAIL_COUPONS.USER_ID))
                .from(CUSTOMER_AVAIL_COUPONS)
                .where(CUSTOMER_AVAIL_COUPONS.ACT_ID.eq(couponId))
                .and(CUSTOMER_AVAIL_COUPONS.DEL_FLAG.eq(BYTE_ZERO))
                .and(CUSTOMER_AVAIL_COUPONS.ACCESS_MODE.eq(accessMode))
                .fetchOneInto(short.class);
        updateCouponNum(accessMode,couponId,peopleNum,couponNum);
    }

    /**
     * 更改数量变化
     * @author liangchen
     * @param accessMode 0:发放,1:领取
     * @param couponId 当前操作的优惠券id
     * @param peopleNum 最新的人数
     * @param couponNum 最新的数量
     */
    private void updateCouponNum(Byte accessMode, Integer couponId, short peopleNum, short couponNum){
        //更新发放数量
        if (accessMode.equals(COUPON_GIVE)){
            db().update(MRKING_VOUCHER)
                .set(MRKING_VOUCHER.GIVEOUT_PERSON,peopleNum)
                .set(MRKING_VOUCHER.GIVEOUT_AMOUNT,couponNum)
                .where(MRKING_VOUCHER.ID.eq(couponId))
                .execute();
        }
        //更新领取数量
        if (accessMode.equals(COUPON_RECEIVE)){
            db().update(MRKING_VOUCHER)
                .set(MRKING_VOUCHER.RECEIVE_PERSON,peopleNum)
                .set(MRKING_VOUCHER.RECEIVE_AMOUNT,couponNum)
                .where(MRKING_VOUCHER.ID.eq(couponId))
                .execute();
        }
    }
}
