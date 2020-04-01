package com.vpu.mp.service.shop.distribution;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.pojo.shop.distribution.*;
import com.vpu.mp.service.shop.config.DistributionConfigService;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * @Author 常乐
 * @Date 2020-03-04
 */
@Service
public class MpDistributionGoodsService extends ShopBaseService {

    @Autowired
    DistributionConfigService distributionConf;

    public RebateRatioVo goodsRebateInfo(Integer goodsId,Integer catId,Integer sortId,Integer userId){
        //获取用户分销等级
        UserDistributionVo distributionLevel = this.userDistributionLevel(userId);
        DistributionStrategyParam goodsRebateStrategy = this.distributionStrategyInfo(goodsId,catId,sortId);
        RebateRatioVo userRebateRatio = this.getUserRebateRatio(userId, distributionLevel, goodsRebateStrategy);


        return userRebateRatio;
    }

    //分销配置
    public DistributionStrategyParam distributionStrategyInfo(Integer goodsId,Integer catId,Integer sortId){
        //分销配置
        DistributionParam distributionCfg = distributionConf.getDistributionCfg();

        //分销开关开启
        if(distributionCfg.getStatus() == 1){
            //返利策略
            DistributionStrategyParam goodsRebateStrategy = this.getGoodsRebateStrategy(goodsId, catId, sortId);
            return goodsRebateStrategy;
        }
        return null;
    }

    /**
     * 进行中的返利策略
     * @return
     */
    public List<DistributionStrategyParam> goingStrategy(){
        Timestamp nowDate = Util.currentTimeStamp();
        Result<Record> record = db().select().from(DISTRIBUTION_STRATEGY).where(DISTRIBUTION_STRATEGY.STATUS.eq((byte) 0))
            .and(DISTRIBUTION_STRATEGY.DEL_FLAG.eq((byte) 0))
            .and(DISTRIBUTION_STRATEGY.START_TIME.le(nowDate)).and(DISTRIBUTION_STRATEGY.END_TIME.ge(nowDate))
            .orderBy(DISTRIBUTION_STRATEGY.STRATEGY_LEVEL.desc()).fetch();
        if(record != null){
            return record.into(DistributionStrategyParam.class);
        }else{
            return null;
        }
    }

    /**
     * 商品返利策略
     * @param goodsId
     * @param catId
     * @param sortId
     * @return
     */
    public DistributionStrategyParam getGoodsRebateStrategy(Integer goodsId,Integer catId,Integer sortId){
        List<DistributionStrategyParam> distributionStrategyVos = this.goingStrategy();
        for (DistributionStrategyParam rebateStrategy:distributionStrategyVos){
            //根据返利策略等级优先级排序、全部商品适用
            if(rebateStrategy.getRecommendType() ==0){
                return rebateStrategy;
            }

            //适用商品判断,适用当前商品
            if(rebateStrategy.getRecommendGoodsId() != null){
                List<Integer> goodsIds = Util.stringToList(rebateStrategy.getRecommendGoodsId());
                if(goodsIds.contains(goodsId)){
                    return rebateStrategy;
                }
            }

            if(rebateStrategy.getRecommendCatId() != null){
                List<Integer> catIds = Util.stringToList(rebateStrategy.getRecommendCatId());
                if(catIds.contains(catId)){
                    return rebateStrategy;
                }
            }
            if(rebateStrategy.getRecommendSortId() != null){
                List<Integer> sortIds = Util.stringToList(rebateStrategy.getRecommendSortId());
                if(sortIds.contains(sortId)){
                    return rebateStrategy;
                }
            }
        }
        return null;
    }

    /**
     * 获取用户分销等级
     * @param userId
     * @return
     */
    public UserDistributionVo userDistributionLevel(Integer userId){
        Record record = db().select(USER.DISTRIBUTOR_LEVEL, USER.IS_DISTRIBUTOR, USER.INVITE_ID).from(USER).where(USER.USER_ID.eq(userId)).fetchOne();
        if(record != null){
            return record.into(UserDistributionVo.class);
        }else{
            return null;
        }
    }

    public RebateRatioVo getUserRebateRatio(Integer userId,UserDistributionVo distributionLevel,DistributionStrategyParam goodsRebateStrategy){
        List<Byte> levels = new ArrayList<Byte>(){
            {add((byte)2);add((byte)3);add((byte)4);add((byte)5);}
        };
//        if(!levels.contains(distributionLevel)){
//            //该用户非审核分销员
//        }

        //获取等级状态
        Record record = db().select().from(DISTRIBUTOR_LEVEL).where(DISTRIBUTOR_LEVEL.LEVEL_ID.eq(distributionLevel.getDistributorLevel())).fetchOne();
        DistributorLevelVo levelInfo = null;
        if(record != null){
             levelInfo = record.into(DistributorLevelVo.class);
        }

        if(levelInfo == null && levelInfo.getLevelStatus() != 1){
            //return 该分销员等级未开启
        }

        RebateRatioVo rebateRatio = new RebateRatioVo();
        if(levels.contains(distributionLevel.getDistributorLevel())){
            Byte level = distributionLevel.getDistributorLevel();
            if(level == 1){
                rebateRatio.setSelfPurchase(goodsRebateStrategy.getSelfPurchase());
                rebateRatio.setFirstRatio(goodsRebateStrategy.getFirstRatio());
                rebateRatio.setFanliRatio(goodsRebateStrategy.getFanliRatio());
                rebateRatio.setRebateRatio(goodsRebateStrategy.getRebateRatio());
            }
            if(level == 2){
                rebateRatio.setSelfPurchase(goodsRebateStrategy.getSelfPurchase());
                rebateRatio.setFirstRatio(goodsRebateStrategy.getFirstRatio_2());
                rebateRatio.setFanliRatio(goodsRebateStrategy.getFanliRatio_2());
                rebateRatio.setRebateRatio(goodsRebateStrategy.getRebateRatio_2());
            }
            if(level == 3){
                rebateRatio.setSelfPurchase(goodsRebateStrategy.getSelfPurchase());
                rebateRatio.setFirstRatio(goodsRebateStrategy.getFirstRatio_3());
                rebateRatio.setFanliRatio(goodsRebateStrategy.getFanliRatio_3());
                rebateRatio.setRebateRatio(goodsRebateStrategy.getRebateRatio_3());
            }
            if(level == 4){
                rebateRatio.setSelfPurchase(goodsRebateStrategy.getSelfPurchase());
                rebateRatio.setFirstRatio(goodsRebateStrategy.getFirstRatio_4());
                rebateRatio.setFanliRatio(goodsRebateStrategy.getFanliRatio_4());
                rebateRatio.setRebateRatio(goodsRebateStrategy.getRebateRatio_4());
            }
            if(level == 5){
                rebateRatio.setSelfPurchase(goodsRebateStrategy.getSelfPurchase());
                rebateRatio.setFirstRatio(goodsRebateStrategy.getFirstRatio_5());
                rebateRatio.setFanliRatio(goodsRebateStrategy.getFanliRatio_5());
                rebateRatio.setRebateRatio(goodsRebateStrategy.getRebateRatio_5());
            }
        }
        return rebateRatio;
    }
}
