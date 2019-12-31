package com.vpu.mp.service.shop.market.payaward;

import com.vpu.mp.db.shop.tables.records.PayAwardPrizeRecord;
import com.vpu.mp.db.shop.tables.records.PayAwardRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.payaward.record.PayAwardRecordListParam;
import com.vpu.mp.service.pojo.shop.market.payaward.record.PayAwardRecordListVo;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.Tables.PAY_AWARD_PRIZE;
import static com.vpu.mp.db.shop.Tables.PAY_AWARD_RECORD;
import static com.vpu.mp.db.shop.Tables.USER;

/**
 * 支付有礼记录
 * @author 孔德成
 * @date 2019/8/14 19:10
 */
@Service
public class PayAwardRecordService  extends ShopBaseService {


    /**
     *  获取记录
     * @param param
     * @return
     */
    public PageResult<PayAwardRecordListVo> getPayRewardRecordList(PayAwardRecordListParam param){
        SelectConditionStep<? extends Record> where = db().select(USER.USER_ID,USER.USERNAME,USER.MOBILE,
                PAY_AWARD_RECORD.ORDER_SN,PAY_AWARD_RECORD.GIFT_TYPE,PAY_AWARD_RECORD.CREATE_TIME)
                .from(PAY_AWARD_RECORD)
                .leftJoin(USER).on(USER.USER_ID.eq(PAY_AWARD_RECORD.USER_ID))
                .where(PAY_AWARD_RECORD.AWARD_ID.eq(param.getId()));
        buildOptions(where,param);
        return getPageResult(where,param.getCurrentPage(),param.getPageRows(), PayAwardRecordListVo.class);
    }

    private void buildOptions(SelectConditionStep<? extends Record> where, PayAwardRecordListParam param) {
        if (param.getReceiveTimeBegin()!=null){
            where.and(PAY_AWARD_RECORD.CREATE_TIME.ge(param.getReceiveTimeBegin()));
        }
         if (param.getReceiveTimeEnd()!=null){
            where.and(PAY_AWARD_RECORD.CREATE_TIME.le(param.getReceiveTimeEnd()));
        }
         if (param.getMobile()!=null&&!param.getMobile().isEmpty()){
            where.and(USER.MOBILE.like(likeValue(param.getMobile())));
        }
         if (param.getUserName()!=null&&!param.getUserName().isEmpty()){
            where.and(USER.USERNAME.like(likeValue(param.getUserName())));
        }
         if (param.getAwardType()!=null){
            where.and(PAY_AWARD_RECORD.GIFT_TYPE.eq(param.getAwardType().byteValue()));
        }
    }

    /**
     * 查询支付有礼记录
     * @param orderSn
     * @return
     */
    public PayAwardRecordRecord getPayAwardRecordByOrderSn(String orderSn){
         return db().selectFrom(PAY_AWARD_RECORD).where(PAY_AWARD_RECORD.ORDER_SN.eq(orderSn)).fetchOne();
    }

    /**
     * 获取参于次数
     * @param userId 用户id
     * @param awardId
     * @return  参与次数
     */
    public Integer getJoinAwardCount(Integer userId, Integer awardId) {
        Integer integer = db().selectCount().from(PAY_AWARD_RECORD)
                .where(PAY_AWARD_RECORD.AWARD_ID.eq(awardId))
                .and(PAY_AWARD_RECORD.USER_ID.eq(userId)).fetchOneInto(Integer.class);
        return integer;
    }

    public Record2<Integer, Integer> getAwardSendNumber(Integer payAwardId, Integer prizeId) {
        return db().select(PAY_AWARD_PRIZE.SEND_NUM,PAY_AWARD_PRIZE.AWARD_NUMBER).from(PAY_AWARD_PRIZE)
                .where(PAY_AWARD_PRIZE.PAY_AWARD_ID.eq(payAwardId))
                .and(PAY_AWARD_PRIZE.ID.eq(prizeId)).fetchOne();
    }

    /**
     * 奖品是否是销量
     * @param payAwardId
     * @param prizeId
     * @return  是否可以发奖
     */
    public Boolean canSendAward(Integer payAwardId,Integer prizeId){
        Record1<Integer> count = db().selectCount().from(PAY_AWARD_PRIZE)
                .where(PAY_AWARD_PRIZE.PAY_AWARD_ID.eq(payAwardId))
                .and(PAY_AWARD_PRIZE.ID.eq(prizeId))
                .and(PAY_AWARD_PRIZE.SEND_NUM.lt(PAY_AWARD_PRIZE.AWARD_NUMBER)).fetchOne();
        return count != null && count.value1() > 0;
    }
    /**
     * 获取奖励信息
     * @param payAwardId
     * @param prizeId
     * @return
     */
    public PayAwardPrizeRecord getAwardInfo(Integer payAwardId, Integer prizeId){
        return  db().selectFrom(PAY_AWARD_PRIZE)
                .where(PAY_AWARD_PRIZE.PAY_AWARD_ID.eq(payAwardId))
                .and(PAY_AWARD_PRIZE.ID.eq(prizeId))
                .and(PAY_AWARD_PRIZE.SEND_NUM.lt(PAY_AWARD_PRIZE.AWARD_NUMBER)).fetchOne();

    }
    /**
     * 跟新奖品库存
     * @param payAwardId
     * @param prizeId
     * @return
     */
    public void updateAwardStock(Integer payAwardId, Integer prizeId){
        db().update(PAY_AWARD_PRIZE).set(PAY_AWARD_PRIZE.SEND_NUM,PAY_AWARD_PRIZE.SEND_NUM.add(1))
                .where(PAY_AWARD_PRIZE.PAY_AWARD_ID.eq(payAwardId))
                .and(PAY_AWARD_PRIZE.ID.eq(prizeId)).execute();
    }
}
