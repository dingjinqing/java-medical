package com.vpu.mp.service.shop.market.payreward;

import com.vpu.mp.db.shop.tables.records.CouponActivityRecord;
import com.vpu.mp.db.shop.tables.records.PayRewardRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.coupon.CouponVo;
import com.vpu.mp.service.pojo.shop.coupon.hold.CouponHoldListParam;
import com.vpu.mp.service.pojo.shop.coupon.hold.CouponHoldListVo;
import com.vpu.mp.service.pojo.shop.market.lottery.record.LotteryRecordPageListParam;
import com.vpu.mp.service.pojo.shop.market.lottery.record.LotteryRecordPageListVo;
import com.vpu.mp.service.pojo.shop.market.payreward.*;
import com.vpu.mp.service.pojo.shop.market.payreward.record.PayRewardLotteryParam;
import com.vpu.mp.service.shop.coupon.CouponHoldService;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.market.lottery.LotteryRecordService;
import org.jooq.Record7;
import org.jooq.SelectConditionStep;
import org.jooq.SelectSeekStep1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.db.shop.Tables.LOTTERY;
import static com.vpu.mp.service.pojo.shop.market.payreward.PayRewardParam.TYPE_SPLIT;

/**
 *
 * 支付有礼
 * @author 孔德成
 * @date 2019/8/12 17:57
 */
@Service
public class PayRewardService  extends ShopBaseService {
    private static final Byte STOP_STATUS = 1;
    private static final Byte USE_STATUS = 0;

    @Autowired
    CouponService coupon;
    @Autowired
    CouponHoldService couponHold;
    @Autowired
    LotteryRecordService lotteryRecord;

    /**
     * 添加
     * @param param 参数
     */
    public Boolean addPayReward(PayRewardParam param) {
        if (getNowPayReward()!=null){
            //还有正在进行的活动，新活动为停用状态
            param.setStatus(STOP_STATUS);
        }
        PayRewardRecord payReward= db().newRecord(PAY_REWARD,param);
        if (param.getType().equals(TYPE_SPLIT)){
            if (param.getCoupon()!=null){
                CouponActivityRecord couponActivity =db().newRecord(COUPON_ACTIVITY,param.getCoupon());
                couponActivity.setId(null);
                couponActivity.insert();
                payReward.setCouponIds(couponActivity.getId().toString());
            }else {
                return false;
            }
        }
        payReward.setId(null);
        return payReward.insert()>0;
    }

    /**
     * 获取当前进行的活动
     * @return
     */
    public PayRewardRecord getNowPayReward(){
        Timestamp nowTime =new Timestamp(System.currentTimeMillis());
        PayRewardRecord record = db().selectFrom(PAY_REWARD).where(PAY_REWARD.START_TIME.ge(nowTime).and(PAY_REWARD.END_TIME.le(nowTime))).fetchOne();
        return record;
    }

    /**
     *  删除
     * @param id 活动id
     */
    public void deletePayReward(Integer id) {
        db().update(PAY_REWARD).set(PAY_REWARD.IS_DELETE,DelFlag.DISABLE_VALUE).where(PAY_REWARD.ID.eq(id));
    }

    /**
     * 更新
     * @param param 参数
     */
    public void updatePayReward(PayRewardParam param) {
        if (param.getId()!=null){
            PayRewardRecord payReward= db().newRecord(PAY_REWARD,param);
            if (param.getType().equals(TYPE_SPLIT)){
                    CouponActivityRecord couponActivity =db().newRecord(COUPON_ACTIVITY,param.getCoupon());
                    couponActivity.insert();
                    payReward.setCouponIds(couponActivity.getId().toString());
            }
            payReward.insert();
        }
    }

    /**
     * 根据id获取活动
     * @param id
     * @return
     */
    public PayRewardVo getPayRewardId(Integer id) {
        PayRewardVo record = db().selectFrom(PAY_REWARD).where(PAY_REWARD.ID.eq(id)).fetchOne().into(PayRewardVo.class);
        if (record.getType().equals(TYPE_SPLIT)){
            Integer couponId = Integer.valueOf(record.getCouponIds());
            CouponVo couponVo = coupon.getOneCouponById(couponId).into(CouponVo.class);
            record.setCoupon(couponVo);
        }
        return record;
    }

    /**
     * 获取支付有礼列表
     * @param param
     * @return
     */
    public PageResult<PayRewardListVo> getPayRewardList(PayRewardListParam param) {
        SelectConditionStep<Record7<Integer, String, BigDecimal, Timestamp, Timestamp, Byte, Byte>> select = db()
                .select(PAY_REWARD.ID, PAY_REWARD.ACT_NAME, PAY_REWARD.DENOMINATION, PAY_REWARD.START_TIME, PAY_REWARD.END_TIME
                , PAY_REWARD.TYPE, PAY_REWARD.STATUS)
                .from(PAY_REWARD)
                .where(PAY_REWARD.IS_DELETE.eq(DelFlag.NORMAL_VALUE));
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        switch (param.getNavType()) {
            case 2:
                select.and(PAY_REWARD.START_TIME.lt(nowTime))
                        .and(PAY_REWARD.END_TIME.gt(nowTime))
                        .and(PAY_REWARD.STATUS.eq(USE_STATUS));
                break;
            case 3:
                select.and(PAY_REWARD.STATUS.eq(USE_STATUS))
                        .and(PAY_REWARD.START_TIME.gt(nowTime));
                break;
            case 4:
                select.and(PAY_REWARD.STATUS.gt(USE_STATUS))
                        .and(PAY_REWARD.END_TIME.lt(nowTime));
                break;
            case 5:
                select.and(PAY_REWARD.STATUS.eq(STOP_STATUS));
                break;
            default:
        }
        select.orderBy(PAY_REWARD.CREATE_TIME.desc());
        return getPageResult(select, param.getCurrentPage(), param.getPageRows(), PayRewardListVo.class);

    }

    /**
     *  停用启用
     * @param param
     */
    public void changeStatus(PayRewardIdParam param) {
        PayRewardRecord record  =db().newRecord(PAY_REWARD);
        record.setId(param.getId());
        record.refresh();
        if (USE_STATUS.equals(record.getStatus())){
            record.setStatus(STOP_STATUS);
        }else {
            PayRewardRecord nowPayReward = getNowPayReward();
            if (nowPayReward==null||param.getId().equals(nowPayReward.getId())){
                record.setStatus(USE_STATUS);
            }
        }
        record.update();
    }

    /**
     * 优惠券列表
     * @param param
     * @return
     */
    public PageResult<CouponHoldListVo> getCouponHoldList(CouponHoldListParam param){
        return couponHold.getCouponHoldList(param);
    }

    /**
     * 抽奖列表
     * @param param
     * @return
     */
    public PageResult<LotteryRecordPageListVo> getLotteryList(PayRewardLotteryParam param) {
        LotteryRecordPageListParam lotteryParam =new LotteryRecordPageListParam();
        lotteryParam.setLotterySource((byte) 2);

        /*
         * 页面参数
         */
        lotteryParam.setMobile(param.getMobile());
        lotteryParam.setUsername(param.getUsername());
        lotteryParam.setLotterySource(param.getLotterySource());
        lotteryParam.setLotteryGrade(param.getLotteryGrade());
        lotteryParam.setLotteryActId(param.getActId());
        return lotteryRecord.getLotteryRecordList(lotteryParam);
    }
}
