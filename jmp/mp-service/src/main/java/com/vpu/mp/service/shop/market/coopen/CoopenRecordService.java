package com.vpu.mp.service.shop.market.coopen;

import com.vpu.mp.db.shop.tables.CoopenActivityRecords;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.coopen.CoopenIssueListParam;
import com.vpu.mp.service.pojo.shop.market.coopen.CoopenIssueListVo;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.Tables.COOPEN_ACTIVITY;
import static com.vpu.mp.db.shop.tables.User.USER;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * 开屏有礼 - 优惠券发放明细
 *
 * @author 郑保乐
 */
@Service
public class CoopenRecordService extends ShopBaseService {

    private static final CoopenActivityRecords TABLE = CoopenActivityRecords.COOPEN_ACTIVITY_RECORDS;

    /**
     * 发放明细列表
     */
    public PageResult<CoopenIssueListVo> getIssuePageList(CoopenIssueListParam param) {

        SelectConditionStep<? extends Record> select =
            db().select(TABLE.MRKING_VOUCHER_ID,TABLE.RECEIVE_TIME,TABLE.COMMENT,TABLE.USER_ID, COOPEN_ACTIVITY.NAME, USER.USERNAME, USER.MOBILE)
                    .from(TABLE)
                .leftJoin(USER).on(USER.USER_ID.eq(TABLE.USER_ID))
                .leftJoin(COOPEN_ACTIVITY).on(COOPEN_ACTIVITY.ID.eq(TABLE.ACTIVITY_ID)).where();
        buildOptions(select, param);
        return getPageResult(select, param, CoopenIssueListVo.class);
    }

    private void buildOptions(SelectConditionStep<? extends Record> select, CoopenIssueListParam param) {
        String mobile = param.getMobile();
        String username = param.getUsername();
        Integer activityId = param.getActivityId();
        if (isNotEmpty(mobile)) {
            select.and(USER.MOBILE.like(likeValue( mobile)));
        }
        if (isNotEmpty(username)) {
            select.and(USER.USERNAME.like(likeValue(username)));
        }
        select.and(TABLE.ACTIVITY_ID.eq(activityId));
    }
}
