package com.vpu.mp.service.shop.market.lottery;

import com.vpu.mp.db.shop.tables.records.LotteryPrizeRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.lottery.JoinLottery;
import com.vpu.mp.service.pojo.shop.market.lottery.prize.LotteryPrizeVo;
import com.vpu.mp.service.pojo.shop.market.lottery.record.LotteryRecordPageListParam;
import com.vpu.mp.service.pojo.shop.market.lottery.record.LotteryRecordPageListVo;
import com.vpu.mp.service.shop.member.MemberService;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectOnConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.tables.LotteryRecord.LOTTERY_RECORD;
import static com.vpu.mp.db.shop.tables.User.USER;

/**
 *  抽奖记录
 * @author 孔德成
 * @date 2019/8/6 9:34
 */
@Service
public class LotteryRecordService extends ShopBaseService {

    @Autowired
    private MemberService member;

    /**
     * 抽奖记录查询
     *
     * @param param LotteryRecordPageListParam
     * @return LotteryRecordPageListVo
     */
    public PageResult<LotteryRecordPageListVo> getLotteryRecordList(LotteryRecordPageListParam param) {
        SelectOnConditionStep<Record> select =  db().select(LOTTERY_RECORD.asterisk(), USER.USERNAME, USER.MOBILE)
                .from(LOTTERY_RECORD)
                .leftJoin(USER).on(USER.USER_ID.eq(LOTTERY_RECORD.USER_ID));
        buildSelect(select,param);
        select.orderBy(LOTTERY_RECORD.CREATE_TIME.desc());
        PageResult<LotteryRecordPageListVo> pageList = getPageResult(select, param.getCurrentPage(), param.getPageRows(), LotteryRecordPageListVo.class);
        pageList.getDataList().forEach(item -> {
            item.setLotteryPrize(Util.parseJson(item.getAwardInfo(), LotteryPrizeVo.class));
            item.setAwardInfo(null);
        });
        return pageList;
    }

    private void buildSelect(SelectOnConditionStep<Record> select, LotteryRecordPageListParam param){
        if (param.getLotteryId()!=null){
            select.where(LOTTERY_RECORD.LOTTERY_ID.eq(param.getLotteryId()));
        }
        if (param.getUsername() != null) {
            select.where(USER.USERNAME.like(likeValue(param.getUsername())));
        }
        if (param.getMobile() != null) {
            select.where(USER.MOBILE.like(prefixLikeValue(param.getMobile())));
        }
        if (param.getLotteryGrade() != null&&param.getLotteryGrade()>=0) {
            select.where(LOTTERY_RECORD.LOTTERY_GRADE.eq(param.getLotteryGrade()));
        }
        if (param.getLotterySource() != null&&param.getLotterySource()>=0) {
            select.where(LOTTERY_RECORD.LOTTERY_SOURCE.eq(param.getLotterySource()));
        }
        if (param.getChanceSource() != null&&param.getChanceSource()>=0) {
            select.where(LOTTERY_RECORD.CHANCE_SOURCE.eq(param.getChanceSource()));
        }
        if (param.getLotteryActId()!=null){
            select.where(LOTTERY_RECORD.LOTTERY_ACT_ID.eq(param.getLotteryActId()));

        }
    }



    /**
     * 获取用户已抽奖次数
     *
     * @param userId       会员id
     * @param lotteryId    活动id
     * @param chanceSource 1:free 2:share 3:score
     * @return 抽奖次数
     */
    public Integer getJoinLotteryNumber(Integer userId, Integer lotteryId, Byte chanceSource) {
        Condition condition = LOTTERY_RECORD.USER_ID.eq(userId)
                .and(LOTTERY_RECORD.LOTTERY_ID.eq(lotteryId));
        if (chanceSource > -1) {
            condition.and(LOTTERY_RECORD.CHANCE_SOURCE.eq(chanceSource));
        }
        return db().fetchCount(LOTTERY_RECORD, condition);
    }


    /**
     * 发送奖品
     * TODO: 2019/8/8  抽奖业务逻辑
     *
     * @param joinValid JoinLottery
     */
    public void sendAwardPresent(JoinLottery joinValid) {
        LotteryPrizeRecord lotteryPrize = joinValid.getLotteryPrize();
        //安慰奖
        if (lotteryPrize == null) {
            logger().info("安慰奖");
            //送积分
            return;
        }
        //选择奖类型
        switch (lotteryPrize.getLotteryType()) {
            case 0:
                logger().info("积分");
                //积分

                break;
            case 1:
                logger().info("用户余额");
                //用户余额

                break;
            case 2:
                logger().info("优惠卷");
                //2优惠券

                break;
            case 3:
                //3赠品

                break;
            case 4:
                //自定义

                break;
            default:
        }
    }


}
