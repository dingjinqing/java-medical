package com.vpu.mp.service.shop.market.sharereward;

import com.vpu.mp.db.shop.tables.AttendShareUser;
import com.vpu.mp.db.shop.tables.ShareAward;
import com.vpu.mp.db.shop.tables.ShareAwardReceive;
import com.vpu.mp.db.shop.tables.ShareAwardRecord;
import com.vpu.mp.db.shop.tables.records.ShareAwardRecordRecord;
import com.vpu.mp.db.shop.tables.records.ShareRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.market.sharereward.ShareRule;
import com.vpu.mp.service.pojo.wxapp.market.shareReward.ShareParam;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

import static com.vpu.mp.db.shop.Tables.SHARE_RECORD;
import static com.vpu.mp.service.shop.store.store.StoreWxService.BYTE_TWO;
import static org.apache.commons.lang3.math.NumberUtils.*;

/**
 * @author liufei
 * @date 1/9/20
 */
@Slf4j
@Service
public class WxShareRewardService extends ShopBaseService {
    public static ShareAward AWARD = ShareAward.SHARE_AWARD.as("AWARD");
    public static ShareAwardRecord AWARD_RECORD = ShareAwardRecord.SHARE_AWARD_RECORD.as("AWARD_RECORD");
    public static ShareAwardReceive AWARD_RECEIVE = ShareAwardReceive.SHARE_AWARD_RECEIVE.as("AWARD_RECEIVE");
    public static AttendShareUser ATTEND = AttendShareUser.ATTEND_SHARE_USER.as("ATTEND");

    @Autowired
    private ShareRewardService shareReward;

    // 添加分享记录，同时参与分享有礼活动，两张表b2c_share_record和b2c_share_award_record
    public void addShareRecord(ShareParam param) {
        ShareRecordRecord recordRecord = shareRecord(param);
        int activityId = param.getActivityId();
        int userId = param.getUserId();
        int goodId = param.getGoodId();
        // 活动可用性校验/分享的商品有效性校验
        shareReward.activityAvailable(activityId, goodId);

        Condition exist = AWARD_RECORD.SHARE_ID.eq(activityId).and(AWARD_RECORD.USER_ID.eq(userId)).and(AWARD_RECORD.GOODS_ID.eq(goodId));
        if (shareReward.aRecordIsExist(exist)) {
            // 需要更新活动现在处于等级
            int count = recordRecord.getCount();
            Map<Byte, ShareRule> rules = shareReward.getShareRules(activityId);
            count = updateAwardStatus(rules.get(BYTE_ZERO), count);
            count = updateAwardStatus(rules.get(BYTE_ONE), count);
            count = updateAwardStatus(rules.get(BYTE_TWO), count);
        } else {
            ShareAwardRecordRecord record = db().newRecord(AWARD_RECORD);
            record.setShareId(activityId);
            record.setUserId(userId);
            record.setGoodsId(goodId);
            record.insert();
        }
    }

    public int updateAwardStatus(ShareRule rule, int count) {
        if (Objects.isNull(rule)) {
            return -1;
        }
        if (count <= 0) {

        }
        if (count >= rule.getInviteNum()) {
            log.info("满足分享奖励规则，修改奖品领取状态，修改活动所处规则等级");

        }
        return count - rule.getInviteNum();
    }

    // 分享没有礼，仅仅是单纯的记录分享记录
    public ShareRecordRecord shareRecord(ShareParam param) {
        int activityId = param.getActivityId();
        int userId = param.getUserId();
        int activityType = param.getActivityType();
        Condition exist = SHARE_RECORD.ACTIVITY_ID.eq(activityId).and(SHARE_RECORD.USER_ID.eq(userId)).and(SHARE_RECORD.ACTIVITY_TYPE.eq(activityType));
        if (recordIsExist(exist)) {
            autoincrementCount(exist);
        } else {
            ShareRecordRecord record = db().newRecord(SHARE_RECORD);
            record.setActivityId(activityId);
            record.setUserId(userId);
            record.setActivityType(activityType);
            record.setCount(INTEGER_ZERO);
            record.insert();
        }
        return getRecordRecord(exist);
    }

    public void autoincrementCount(Condition condition) {
        db().update(SHARE_RECORD).set(SHARE_RECORD.COUNT, SHARE_RECORD.COUNT.add(INTEGER_ONE)).where(condition).execute();
    }

    public ShareRecordRecord getRecordRecord(Condition condition) {
        return db().selectFrom(SHARE_RECORD).where(condition).fetchOne();
    }


    public boolean recordIsExist(Condition condition) {
        return db().fetchExists(SHARE_RECORD, condition);
    }

}
