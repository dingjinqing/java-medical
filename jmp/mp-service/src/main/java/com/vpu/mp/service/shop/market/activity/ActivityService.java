package com.vpu.mp.service.shop.market.activity;

import com.vpu.mp.db.shop.tables.CouponActivity;
import com.vpu.mp.db.shop.tables.records.CouponActivityRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.activity.*;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.UpdateSetMoreStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.vpu.mp.db.shop.tables.Lottery.LOTTERY;
import static com.vpu.mp.db.shop.tables.MrkingVoucher.MRKING_VOUCHER;
import static com.vpu.mp.service.foundation.data.JsonResultMessage.ACTIVITY_TIME_RANGE_CONFLICT;
import static com.vpu.mp.service.pojo.shop.market.activity.ActivityListParam.*;
import static com.vpu.mp.service.pojo.shop.market.activity.ActivityListVo.*;

/**
 * 开屏有礼（活动有礼）
 *
 * @author 郑保乐
 */
@Service
public class ActivityService extends ShopBaseService {

    private static CouponActivity TABLE = CouponActivity.COUPON_ACTIVITY;

    /**
     * 列表查询
     */
    public PageResult<ActivityListVo> getPageList(ActivityListParam param) {
        SelectConditionStep<CouponActivityRecord> select =
            db().selectFrom(TABLE).where(TABLE.DEL_FLAG.eq((byte) 0));
        buildOptions(select, param);
        PageResult<ActivityListVo> result = getPageResult(select, param, ActivityListVo.class);
        transform(result);
        return result;
    }

    /**
     * 状态转换
     */
    private void transform(PageResult<ActivityListVo> result) {
        result.getDataList().forEach(vo -> vo.setStatus(getStatusOf(vo)));
    }

    /**
     * 获取活动的状态
     */
    private byte getStatusOf(ActivityListVo vo) {
        Byte status = vo.getStatus();
        if (DISABLE == status) {
            return DISABLED;
        } else {
            Timestamp startDate = vo.getStartDate();
            Timestamp endDate = vo.getEndDate();
            if (startDate.after(Util.currentTimeStamp())) {
                return NOT_STARTED;
            } else if (endDate.after(Util.currentTimeStamp())) {
                return ONGOING;
            } else {
                return EXPIRED;
            }
        }
    }

    /**
     * 查询条件
     */
    private void buildOptions(SelectConditionStep<CouponActivityRecord> select, ActivityListParam param) {
        Integer status = param.getStatus();
        if (null != status) {
            switch (status.byteValue()) {
                case NOT_STARTED:
                    select.and(TABLE.START_DATE.gt(Util.currentTimeStamp()));
                    break;
                case ONGOING:
                    select.and(TABLE.START_DATE.le(Util.currentTimeStamp()))
                        .and(TABLE.END_DATE.gt(Util.currentTimeStamp()));
                    break;
                case EXPIRED:
                    select.and(TABLE.END_DATE.lt(Util.currentTimeStamp()));
                    break;
                case DISABLED:
                    select.and(TABLE.STATUS.eq(DISABLE));
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected status: " + status);
            }
            if (DISABLED != status) {
                select.and(TABLE.STATUS.eq(ABLE));
            }
        }
    }

    /**
     * 停用活动
     */
    public void disableActivity(Integer activityId) {
        db().update(TABLE).set(TABLE.STATUS, DISABLE).where(TABLE.ID.eq(activityId)).execute();
    }

    /**
     * 启用活动
     */
    public void enableActivity(Integer id) {
        db().update(TABLE).set(TABLE.STATUS, ABLE).where(TABLE.ID.eq(id)).execute();
    }

    /**
     * 删除活动
     */
    public void deleteActivity(Integer activityId) {
        db().update(TABLE).set(TABLE.DEL_FLAG, (byte) 1).where(TABLE.ID.eq(activityId)).execute();
    }

    /**
     * 添加活动
     */
    public void addActivity(ActivityParam param) {
        validateParam(param);
        validateTimeRange(param);
        String couponId = "";
        String title = "";
        Byte bgAction = 1;
        switch (param.getType()) {
            case COUPON:
                couponId = Util.listToString(param.getCouponId());
                bgAction = param.getBgAction();
                title = param.getTitle();
                break;
            case DRAW:
                couponId = String.valueOf(param.getActivityId());
                break;
            default:
        }
        db().insertInto(TABLE).columns(TABLE.NAME, TABLE.ACTION, TABLE.ACTIVITY_ACTION, TABLE.BG_ACTION,
            TABLE.START_DATE, TABLE.END_DATE, TABLE.MRKING_VOUCHER_ID, TABLE.TITLE, TABLE.STATUS,
            TABLE.CUSTOMIZE_IMG_PATH, TABLE.CUSTOMIZE_URL)
            .values(param.getName(), param.getAction(), param.getType(), bgAction, param.getStartDate(),
                param.getEndDate(), couponId, title, ABLE, param.getCustomizeImgUrl(),
                param.getCustomizePagePath()).execute();
    }

    /**
     * 参数校验
     */
    private void validateParam(final ActivityParam param) {
        byte type = param.getType();
        switch (type) {
            case COUPON:
                Assert.notNull(param.getBgAction(), "Missing parameter bgAction");
                Assert.notNull(param.getCouponId(), "Missing parameter couponId");
                Assert.notEmpty(param.getCouponId(), "CouponId is empty");
                Assert.notNull(param.getTitle(), "Missing parameter title");
                break;
            case DRAW:
                Assert.notNull(param.getActivityId(), "Missing parameter activityId");
                break;
            case CUSTOMIZE:
                Assert.notNull(param.getCustomizeImgUrl(), "Missing parameter customizeImgUrl");
                Assert.notNull(param.getCustomizePagePath(), "Missing parameter customizePagePath");
                break;
            default:
                throw new IllegalArgumentException("Unexpected type: " + type);
        }
    }

    /**
     * 修改活动
     */
    public void updateActivity(ActivityParam param) {
        validateParam(param);
        validateTimeRange(param, true);
        ActivityListVo vo = getActivity(param.getId()).fetchOneInto(ActivityListVo.class);
        byte status = getStatusOf(vo);
        if (DISABLED == status) {
            throw new IllegalStateException("Activity has been disabled");
        }
        // 更新
        UpdateSetMoreStep<CouponActivityRecord> update = db().update(TABLE).set(TABLE.STATUS, vo.getStatus());
        // 活动名称
        update.set(TABLE.NAME, param.getName());
        if (ONGOING == status) {
            // 宣传语
            update.set(TABLE.TITLE, Optional.ofNullable(param.getTitle()).orElse(""));
        } else if (NOT_STARTED == status) {
            // 触发条件
            update.set(TABLE.ACTION, param.getAction());
            // 背景图
            update.set(TABLE.BG_ACTION, Optional.ofNullable(param.getBgAction()).orElse((byte) 1));
            // 自定义图片
            update.set(TABLE.CUSTOMIZE_IMG_PATH, param.getCustomizeImgUrl());
            // 自定义链接
            update.set(TABLE.CUSTOMIZE_URL, param.getCustomizePagePath());
            // 优惠券、抽奖活动
            String mrkingVoucherId = "";
            switch (param.getType()) {
                // 活动送券
                case COUPON:
                    mrkingVoucherId = Util.listToString(param.getCouponId());
                    break;
                // 幸运大抽奖
                case DRAW:
                    mrkingVoucherId = String.valueOf(param.getActivityId());
                    break;
            }
            update.set(TABLE.MRKING_VOUCHER_ID, mrkingVoucherId);
            // 活动时间范围
            update.set(TABLE.START_DATE, param.getStartDate());
            update.set(TABLE.END_DATE, param.getEndDate());
        }
        update.where(TABLE.ID.eq(param.getId()));
        update.execute();
    }

    /**
     * 检查该时段内是否有其它开屏有礼活动
     */
    private void validateTimeRange(ActivityParam param) {
        validateTimeRange(param, false);
    }

    /**
     * 检查该时段内是否有其它开屏有礼活动
     */
    private void validateTimeRange(ActivityParam param, boolean update) {
        Timestamp startDate = param.getStartDate();
        Timestamp endDate = param.getEndDate();
        SelectConditionStep<Record1<Integer>> condition = DSL.select(TABLE.ID).from(TABLE)
            .where(TABLE.STATUS.eq(ABLE).and(TABLE.START_DATE.ge(startDate)).and(TABLE.END_DATE.le(endDate)));
        if (update) {
            condition.and(TABLE.ID.ne(param.getId()));
        }
        boolean exists = db().fetchExists(condition);
        if (exists) {
            throw new IllegalArgumentException(ACTIVITY_TIME_RANGE_CONFLICT);
        }
    }

    /**
     * 获取活动明细
     */
    public ActivityVo getActivityDetail(Integer id) {
        ActivityVo vo = getActivity(id).fetchOneInto(ActivityVo.class);
        List<Integer> voucherIds = Util.stringToList(vo.getMrkingVoucherId());
        List<Voucher> vouchers = db().select(MRKING_VOUCHER.ID, MRKING_VOUCHER.USE_CONSUME_RESTRICT,
            MRKING_VOUCHER.LEAST_CONSUME, MRKING_VOUCHER.TOTAL_AMOUNT.minus(MRKING_VOUCHER.RECEIVE_AMOUNT).as(
                "available_quantity")).from(MRKING_VOUCHER).where(MRKING_VOUCHER.ID.in(voucherIds)).fetchInto(Voucher.class);
        vouchers.forEach(v -> v.setRestrict(v.getUseConsumeRestrict() == 1));
        Byte type = vo.getActivityAction();
        switch (type) {
            case COUPON:
                vo.setVouchers(vouchers);
                break;
            case DRAW:
                vo.setActivityId(Integer.valueOf(vo.getMrkingVoucherId()));
                break;
        }
        return vo;
    }

    /**
     * 获取活动
     */
    private SelectConditionStep<CouponActivityRecord> getActivity(Integer id) {
        return db().selectFrom(TABLE).where(TABLE.ID.eq(id));
    }

    /**
     * 获取可用的抽奖活动
     */
    public LotteryVo getAvailableLotteries() {
        List<Lottery> lotteries = db().select(LOTTERY.ID, LOTTERY.LOTTERY_NAME.as("name")).from(LOTTERY)
            .where(LOTTERY.STATUS.eq((byte) 0).and(LOTTERY.END_TIME.gt(Util.currentTimeStamp()))
                .and(LOTTERY.DEL_FLAG.eq((byte) 0)))
            .fetchInto(Lottery.class);
        return new LotteryVo(lotteries);
    }
}
