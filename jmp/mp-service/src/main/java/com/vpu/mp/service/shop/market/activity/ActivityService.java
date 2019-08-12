package com.vpu.mp.service.shop.market.activity;

import com.vpu.mp.db.shop.tables.CouponActivity;
import com.vpu.mp.db.shop.tables.records.CouponActivityRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.activity.ActivityListParam;
import com.vpu.mp.service.pojo.shop.market.activity.ActivityListVo;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;

import static com.vpu.mp.service.pojo.shop.market.activity.ActivityListParam.*;

/**
 * 活动有礼
 *
 * @author 郑保乐
 */
@Service
public class ActivityService extends ShopBaseService {

    private static CouponActivity TABLE = CouponActivity.COUPON_ACTIVITY;

    public PageResult<ActivityListVo> getPageList(ActivityListParam param) {
        SelectConditionStep<CouponActivityRecord> select =
            shopDb().selectFrom(TABLE).where(TABLE.DEL_FLAG.eq((byte) 0));
        buildOptions(select, param);
        return getPageResult(select, param, ActivityListVo.class);
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
                    select.and(TABLE.STATUS.eq((byte) 0));
                default:
                    throw new IllegalArgumentException("Unexpected status: " + status);
            }
        }
    }
}
