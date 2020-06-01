package com.vpu.mp.service.shop.distribution;

import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.records.UserRebatePriceRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.data.DistributionConstant;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.distribution.DistributionParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributionStrategyParam;
import com.vpu.mp.service.pojo.shop.distribution.DistributorLevelVo;
import com.vpu.mp.service.pojo.shop.distribution.RebateRatioVo;
import com.vpu.mp.service.pojo.shop.distribution.UserDistributionVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.distribution.BaseGoodsVo;
import com.vpu.mp.service.pojo.wxapp.distribution.GoodsRebateChangePriceVo;
import com.vpu.mp.service.pojo.wxapp.distribution.RebateGoodsCfgParam;
import com.vpu.mp.service.pojo.wxapp.distribution.RebateGoodsCfgVo;
import com.vpu.mp.service.pojo.wxapp.distribution.ShareUserInfoParam;
import com.vpu.mp.service.pojo.wxapp.distribution.ShareUserInfoVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsDetailMpParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import com.vpu.mp.service.shop.config.DistributionConfigService;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.DISTRIBUTION_STRATEGY;
import static com.vpu.mp.db.shop.Tables.DISTRIBUTOR_LEVEL;
import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.Tables.GOODS_REBATE_PRICE;
import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_DETAIL;
import static com.vpu.mp.db.shop.Tables.USER_REBATE_PRICE;

/**
 * @Author 常乐
 * @Date 2020-03-04
 */
@Service
public class MpDistributionGoodsService extends ShopBaseService {

    @Autowired
    public DistributionConfigService distributionConf;

    @Autowired
    public UserRebatePriceService userRebatePrice;

    @Autowired
    public DistributorLevelService distributorLevel;

    @Autowired
    protected DomainConfig domainConfig;

    public RebateRatioVo goodsRebateInfo(Integer goodsId,Integer catId,Integer sortId,Integer userId){
        //获取用户分销等级
        UserDistributionVo distributionLevel = this.userDistributionLevel(userId);
        DistributionStrategyParam goodsRebateStrategy = this.distributionStrategyInfo(goodsId,catId,sortId);
        if(goodsRebateStrategy == null){
            return null;
        }else{
            RebateRatioVo userRebateRatio = this.getUserRebateRatio(userId, distributionLevel, goodsRebateStrategy);
            return userRebateRatio;
        }
    }

    //分销配置
    public DistributionStrategyParam distributionStrategyInfo(Integer goodsId,Integer catId,Integer sortId){
        //分销配置
        DistributionParam distributionCfg = distributionConf.getDistributionCfg();

        //分销开关开启
        if(distributionCfg != null && distributionCfg.getStatus() == 1){
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
        return db().select().from(DISTRIBUTION_STRATEGY).where(DISTRIBUTION_STRATEGY.STATUS.eq((byte) 0))
            .and(DISTRIBUTION_STRATEGY.DEL_FLAG.eq((byte) 0))
            .and(DISTRIBUTION_STRATEGY.START_TIME.le(nowDate)).and(DISTRIBUTION_STRATEGY.END_TIME.ge(nowDate))
            .orderBy(DISTRIBUTION_STRATEGY.STRATEGY_LEVEL.desc()).fetchInto(DistributionStrategyParam.class);
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
        return getGoodsStrategy(goodsId, sortId, distributionStrategyVos);
    }

    /**
     * 获取商品返利逻辑
     * @param goodsId
     * @param sortId
     * @param distributionStrategyVos
     * @return
     */
    public DistributionStrategyParam getGoodsStrategy(Integer goodsId, Integer sortId, List<DistributionStrategyParam> distributionStrategyVos) {
        for (DistributionStrategyParam rebateStrategy:distributionStrategyVos){
            //根据返利策略等级优先级排序、全部商品适用
            if(DistributionConstant.ALL_GOODS.equals(rebateStrategy.getRecommendType())){
                return rebateStrategy;
            }

            //适用商品判断,适用当前商品
            if(rebateStrategy.getRecommendGoodsId() != null){
                List<Integer> goodsIds = Util.stringToList(rebateStrategy.getRecommendGoodsId());
                if(goodsIds.contains(goodsId)){
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

        //获取等级状态
        Record record = db().select().from(DISTRIBUTOR_LEVEL).where(DISTRIBUTOR_LEVEL.LEVEL_ID.eq(distributionLevel.getDistributorLevel())).fetchOne();
        DistributorLevelVo levelInfo = null;
        if(record != null){
             levelInfo = record.into(DistributorLevelVo.class);
        }

        if(levelInfo != null && levelInfo.getLevelStatus() != 1){
            //return 该分销员等级未开启
        }

        RebateRatioVo rebateRatio = new RebateRatioVo();
        if(levels.contains(distributionLevel.getDistributorLevel())){
            Byte level = distributionLevel.getDistributorLevel();
            rebateRatio.setSelfPurchase(goodsRebateStrategy.getSelfPurchase());
            getRebateRatio(goodsRebateStrategy, rebateRatio, level);
        }
        return rebateRatio;
    }

    private void getRebateRatio(DistributionStrategyParam goodsRebateStrategy, RebateRatioVo rebateRatio, Byte level) {
        if(level.equals(DistributionConstant.level_1)){
            rebateRatio.setRebateId(goodsRebateStrategy.getId());
            rebateRatio.setFirstRatio(goodsRebateStrategy.getFirstRatio());
            rebateRatio.setFanliRatio(goodsRebateStrategy.getFanliRatio());
            rebateRatio.setRebateRatio(goodsRebateStrategy.getRebateRatio());
        }
        if(level.equals(DistributionConstant.level_2)){
            rebateRatio.setRebateId(goodsRebateStrategy.getId());
            rebateRatio.setFirstRatio(goodsRebateStrategy.getFirstRatio_2());
            rebateRatio.setFanliRatio(goodsRebateStrategy.getFanliRatio_2());
            rebateRatio.setRebateRatio(goodsRebateStrategy.getRebateRatio_2());
        }
        if(level.equals(DistributionConstant.level_3)){
            rebateRatio.setRebateId(goodsRebateStrategy.getId());
            rebateRatio.setFirstRatio(goodsRebateStrategy.getFirstRatio_3());
            rebateRatio.setFanliRatio(goodsRebateStrategy.getFanliRatio_3());
            rebateRatio.setRebateRatio(goodsRebateStrategy.getRebateRatio_3());
        }
        if(level.equals(DistributionConstant.level_4)){
            rebateRatio.setRebateId(goodsRebateStrategy.getId());
            rebateRatio.setFirstRatio(goodsRebateStrategy.getFirstRatio_4());
            rebateRatio.setFanliRatio(goodsRebateStrategy.getFanliRatio_4());
            rebateRatio.setRebateRatio(goodsRebateStrategy.getRebateRatio_4());
        }
        if(level.equals(DistributionConstant.level_5)){
            rebateRatio.setRebateId(goodsRebateStrategy.getId());
            rebateRatio.setFirstRatio(goodsRebateStrategy.getFirstRatio_5());
            rebateRatio.setFanliRatio(goodsRebateStrategy.getFanliRatio_5());
            rebateRatio.setRebateRatio(goodsRebateStrategy.getRebateRatio_5());
        }
    }

    /**
     *
     * @param userInfo
     * @param goodsStrategy 返利策略
     * @param cfg 配置
     * @return
     */
    public RebateRatioVo getUserRebateRatio(UserRecord userInfo, DistributionStrategyParam goodsStrategy, DistributionParam cfg) {
        if(userInfo == null) {
            logger().info("分销员不存在");
            return null;
        }
        if(cfg.getJudgeStatus() == OrderConstant.YES && Byte.valueOf(OrderConstant.NO).equals(userInfo.getIsDistributor())) {
            logger().info("用户非审核分销员");
        }
        //异常分销等级重置
        if(userInfo.getDistributorLevel() < 1 || userInfo.getDistributorLevel() > 5) {
            userInfo.setDistributorLevel(DistributionConstant.level_1);
        }
        //等级详情
        DistributorLevelVo levelInfo = distributorLevel.getOneLevelInfo(userInfo.getDistributorLevel());
        if(levelInfo == null || levelInfo.getLevelStatus() == OrderConstant.NO) {
            logger().info("该分销员等级未开启，level：{}", userInfo.getDistributorLevel());
        }
        //该等级返利详情
        RebateRatioVo rebateRatio = new RebateRatioVo();
        getRebateRatio(goodsStrategy, rebateRatio, userInfo.getDistributorLevel());
        return rebateRatio;
    }

    /**
     * 商品分销改价页面信息
     * @param param
     * @return
     */
    public GoodsRebateChangePriceVo rebateGoodsCfg(RebateGoodsCfgParam param){
        //商品基本信息
        BaseGoodsVo goods = db().select(GOODS.GOODS_ID, GOODS.GOODS_NAME, GOODS.GOODS_IMG).from(GOODS).where(GOODS.GOODS_ID.eq(param.getGoodsId())).fetchOne().into(BaseGoodsVo.class);
        goods.setGoodsImg(domainConfig.imageUrl(goods.getGoodsImg()));

        //商品分销改价信息
        List<RebateGoodsCfgVo> rebatePrice = db().select(GOODS_SPEC_PRODUCT.PRD_ID, GOODS_SPEC_PRODUCT.PRD_PRICE, GOODS_SPEC_PRODUCT.PRD_DESC,
            GOODS_REBATE_PRICE.ADVISE_PRICE, GOODS_REBATE_PRICE.MIN_PRICE, GOODS_REBATE_PRICE.MAX_PRICE)
            .from(GOODS_SPEC_PRODUCT)
            .leftJoin(GOODS_REBATE_PRICE).on(GOODS_SPEC_PRODUCT.PRD_ID.eq(GOODS_REBATE_PRICE.PRODUCT_ID))
            .where(GOODS_SPEC_PRODUCT.GOODS_ID.eq(param.getGoodsId()))
            .and(GOODS_REBATE_PRICE.DEL_FLAG.eq((byte) 0))
            .fetch().into(RebateGoodsCfgVo.class);

        GoodsRebateChangePriceVo goodsRebateChangePriceVo = new GoodsRebateChangePriceVo();
        goodsRebateChangePriceVo.setGoods(goods);
        goodsRebateChangePriceVo.setRebatePrice(rebatePrice);
        return goodsRebateChangePriceVo;
    }

    /**
     * 保存/更新商品分销价
     * @param param
     */
    public void addRebatePrice(GoodsDetailMpBo goodsDetailMpBo,GoodsDetailMpParam param){
        UserRebatePriceRecord userRebatePrice = new UserRebatePriceRecord();
        long rebateTime1 = param.getRebateConfig().getRebateTime();
        int addTime = 24*60*60;
        long toTime = (rebateTime1 + addTime) * 1000;

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String rebateTime = simpleDateFormat.format(toTime);
        Timestamp rebateToTime = Timestamp.valueOf(rebateTime);

        Timestamp nowTime = Util.currentTimeStamp();

        //判断当前用户对该商品是否进行过分销改价
        param.getRebateConfig().getRebatePrice().forEach((prdId,prdPrice)->{
            Record record = db().select().from(USER_REBATE_PRICE).where(USER_REBATE_PRICE.USER_ID.eq(param.getUserId()))
                .and(USER_REBATE_PRICE.PRODUCT_ID.eq(prdId))
                .fetchOne();
            userRebatePrice.setUserId(param.getUserId());
            userRebatePrice.setGoodsId(param.getGoodsId());
            userRebatePrice.setProductId(prdId);
            userRebatePrice.setAdvicePrice(prdPrice);
            userRebatePrice.setExpireTime(rebateToTime);
//            添加商品分销价
            if(record == null) {
                db().executeInsert(userRebatePrice);
            }else{
                UserRebatePriceRecord rebateInfo = record.into(UserRebatePriceRecord.class);
                //更新商品分销价
               db().update(USER_REBATE_PRICE).set(userRebatePrice).where(USER_REBATE_PRICE.ID.eq(rebateInfo.getId())).execute();
            }
            Record record1 = db().select().from(USER_REBATE_PRICE).where(USER_REBATE_PRICE.USER_ID.eq(param.getUserId()))
                .and(USER_REBATE_PRICE.PRODUCT_ID.eq(prdId)).and(USER_REBATE_PRICE.EXPIRE_TIME.gt(nowTime))
                .fetchOne();
            System.out.println(param.getUserId());
            System.out.println(record1);
            if(record1 != null){
                for(GoodsPrdMpVo item : goodsDetailMpBo.getProducts()){
                    if(item.getPrdId().equals(prdId)){
                        System.out.println(123);
                        item.setPrdRealPrice(prdPrice);
                    }
                }
            }
        });
    }

    /**
     * 分销员信息
     * @param param
     * @return
     */
    public ShareUserInfoVo getShareUserInfo(ShareUserInfoParam param){
        ShareUserInfoVo info = db().select().from(USER).leftJoin(USER_DETAIL).on(USER.USER_ID.eq(USER_DETAIL.USER_ID)).where(USER.USER_ID.eq(param.getInviteId())).fetchOne().into(ShareUserInfoVo.class);
        return info;
    }
}
