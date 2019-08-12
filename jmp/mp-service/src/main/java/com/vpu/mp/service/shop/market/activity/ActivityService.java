package com.vpu.mp.service.shop.market.activity;

import com.vpu.mp.db.shop.tables.CouponActivity;
import com.vpu.mp.db.shop.tables.records.CouponActivityRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.activity.ActivityListParam;
import com.vpu.mp.service.pojo.shop.market.activity.ActivityListVo;
import com.vpu.mp.service.pojo.shop.market.activity.ActivityParam;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;

import static com.vpu.mp.service.pojo.shop.market.activity.ActivityListParam.*;
import static com.vpu.mp.service.pojo.shop.market.activity.ActivityListVo.*;

/**
 * 活动有礼
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
            shopDb().selectFrom(TABLE).where(TABLE.DEL_FLAG.eq((byte) 0));
        buildOptions(select, param);
        PageResult<ActivityListVo> result = getPageResult(select, param, ActivityListVo.class);
        transform(result);
        return result;
    }

    /**
     * 状态转换
     */
    private void transform(PageResult<ActivityListVo> result) {
        result.getDataList().forEach(vo -> {
            Byte status = vo.getStatus();
            if (DISABLE == status) {
                vo.setStatus(DISABLED);
            } else {
                Timestamp startDate = vo.getStartDate();
                Timestamp endDate = vo.getEndDate();
                if (startDate.after(Util.currentTimeStamp())) {
                    vo.setStatus(NOT_STARTED);
                } else if (endDate.after(Util.currentTimeStamp())) {
                    vo.setStatus(ONGOING);
                } else {
                    vo.setStatus(EXPIRED);
                }
            }
        });
    }

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
        shopDb().update(TABLE).set(TABLE.STATUS, DISABLE).where(TABLE.ID.eq(activityId)).execute();
    }

    /**
     * 启用活动
     */
    public void enableActivity(Integer id) {
        shopDb().update(TABLE).set(TABLE.STATUS, ABLE).where(TABLE.ID.eq(id)).execute();
    }

    /**
     * 删除活动
     */
    public void deleteActivity(Integer activityId) {
        shopDb().update(TABLE).set(TABLE.DEL_FLAG, (byte) 1).where(TABLE.ID.eq(activityId)).execute();
    }

    /**
     * 添加活动
     */
    public void addActivity(ActivityParam param) {
        validateParam(param);
        String couponId = "";
        Byte bgAction = 1;
        switch (param.getType()) {
            case COUPON:
                couponId = Util.listToString(param.getCouponId());
                bgAction = param.getBgAction();
                break;
            case DRAW:
                couponId = String.valueOf(param.getActivityId());
                break;
        }
        shopDb().insertInto(TABLE).columns(TABLE.NAME, TABLE.ACTION, TABLE.ACTIVITY_ACTION, TABLE.BG_ACTION,
            TABLE.START_DATE, TABLE.END_DATE, TABLE.MRKING_VOUCHER_ID, TABLE.TITLE, TABLE.STATUS,
            TABLE.CUSTOMIZE_IMG_PATH, TABLE.CUSTOMIZE_URL)
            .values(param.getName(), param.getAction(), param.getType(), bgAction, param.getStartDate(),
                param.getEndDate(), couponId, param.getTitle(), ABLE, param.getCustomizeImgUrl(),
                param.getCustomizePagePath()).execute();
    }

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
}
