package com.vpu.mp.service.shop.market.activity;

import com.vpu.mp.db.shop.tables.CouponActivityRecord;
import com.vpu.mp.db.shop.tables.records.MrkingVoucherRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.activity.ActivityIssueListParam;
import com.vpu.mp.service.pojo.shop.market.activity.ActivityIssueListVo;
import org.jooq.Record6;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.CouponActivity.COUPON_ACTIVITY;
import static com.vpu.mp.db.shop.tables.MrkingVoucher.MRKING_VOUCHER;
import static com.vpu.mp.db.shop.tables.User.USER;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * 发放明细
 *
 * @author 郑保乐
 */
@Service
public class ActivityIssueService extends ShopBaseService {

    private static final CouponActivityRecord TABLE = CouponActivityRecord.COUPON_ACTIVITY_RECORD;

    /**
     * 发放明细列表
     */
    public PageResult<ActivityIssueListVo> getIssuePageList(ActivityIssueListParam param) {
        SelectConditionStep<Record6<String, Timestamp, Integer, String, String, String>> select =
            shopDb().select(TABLE.MRKING_VOUCHER_ID,
                TABLE.RECEIVE_TIME, TABLE.USER_ID, COUPON_ACTIVITY.NAME, USER.USERNAME, USER.MOBILE).from(TABLE)
                .leftJoin(USER).on(USER.USER_ID.eq(TABLE.USER_ID))
                .leftJoin(COUPON_ACTIVITY).on(COUPON_ACTIVITY.ID.eq(TABLE.ACTIVITY_ID)).where();
        buildOptions(select, param);
        PageResult<ActivityIssueListVo> page = getPageResult(select, param, ActivityIssueListVo.class);
        // 本页所有优惠券id
        List<Integer> voucherIds = page.getDataList().stream()
            .flatMap(r -> Util.stringToList(r.getMrkingVoucherId()).stream())
            .collect(Collectors.toList());
        // 本页用到的优惠券
        List<MrkingVoucherRecord> vouchers =
            shopDb().select(MRKING_VOUCHER.ID, MRKING_VOUCHER.ACT_NAME).from(MRKING_VOUCHER)
                .where(MRKING_VOUCHER.ID.in(voucherIds)).fetchInto(MrkingVoucherRecord.class);
        page.getDataList().forEach(issue -> {
            // 本次领取的优惠券id list
            issue.setVoucherIds(Util.stringToList(issue.getMrkingVoucherId()));
            // 本次领取的优惠券名称 list
            issue.setVoucherNames(
                // 回 voucherIds 查找对应的优惠券名称
                issue.getVoucherIds().stream()
                    .map(vid -> vouchers.stream().filter(v -> v.getId().equals(vid)).findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Invalid voucherId: " + vid)).getActName())
                    .collect(Collectors.toList())
            );
        });
        return page;
    }

    private void buildOptions(SelectConditionStep<Record6<String, Timestamp, Integer, String, String, String>> select, ActivityIssueListParam param) {
        String mobile = param.getMobile();
        String username = param.getUsername();
        Integer activityId = param.getActivityId();
        if (isNotEmpty(mobile)) {
            select.and(USER.MOBILE.like(format("%s%%", mobile)));
        }
        if (isNotEmpty(username)) {
            select.and(USER.USERNAME.like(format("%s%%", username)));
        }
        select.and(TABLE.ACTIVITY_ID.eq(activityId));
    }
}
