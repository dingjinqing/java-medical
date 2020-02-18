package com.vpu.mp.service.shop.market.fullcut;

import com.vpu.mp.db.shop.tables.records.MrkingStrategyConditionRecord;
import com.vpu.mp.db.shop.tables.records.MrkingStrategyRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.fullcut.*;
import com.vpu.mp.service.pojo.shop.member.card.ValidUserCardBean;
import com.vpu.mp.service.pojo.wxapp.market.fullcut.MrkingStrategyGoodsListParam;
import com.vpu.mp.service.pojo.wxapp.market.fullcut.MrkingStrategyGoodsListVo;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import com.vpu.mp.service.shop.goods.GoodsService;
import jodd.util.StringUtil;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.MrkingStrategy.MRKING_STRATEGY;
import static com.vpu.mp.db.shop.tables.MrkingStrategyCondition.MRKING_STRATEGY_CONDITION;

/**
 * @author: 王兵兵
 * @create: 2019-08-09 18:47
 **/
@Primary
@Service
public class MrkingStrategyService extends ShopBaseService {

    /**
     * 启用状态
     */
    public static final byte STATUS_NORMAL = 1;
    /**
     * 停用状态
     */
    public static final byte STATUS_DISABLED = 0;

    @Autowired
    private ShopCommonConfigService shopCommonConfigService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 新建满折满减活动
     *
     */
    public void addMrkingStrategy(MrkingStrategyAddParam param){
        this.transaction(()->{
            MrkingStrategyRecord record = db().newRecord(MRKING_STRATEGY);
            assign(param,record);
            record.insert();
            Integer id = record.getId();
            for(MrkingStrategyCondition condition : param.getConditionAddParams()){
                MrkingStrategyConditionRecord conditionRecord = new MrkingStrategyConditionRecord();
                assign(condition,conditionRecord);
                conditionRecord.setStrategyId(id);
                db().executeInsert(conditionRecord);
            }
        });
    }

    /**
     * 满折满减活动列表分页查询
     *
     */
    public PageResult<MrkingStrategyPageListQueryVo> getPageList(MrkingStrategyPageListQueryParam param) {
        SelectWhereStep<? extends Record> select = db().select(MRKING_STRATEGY.ID,MRKING_STRATEGY.ACT_NAME,MRKING_STRATEGY.TYPE,MRKING_STRATEGY.START_TIME,MRKING_STRATEGY.END_TIME, MRKING_STRATEGY.STATUS,MRKING_STRATEGY.STRATEGY_PRIORITY).
            from(MRKING_STRATEGY);
        if(param.getState() > 0) {
            /** 状态过滤*/
            Timestamp now = DateUtil.getLocalDateTime();
            switch(param.getState()) {
                case (byte)1:
                    select.where(MRKING_STRATEGY.STATUS.eq(STATUS_NORMAL)).and(MRKING_STRATEGY.START_TIME.lt(now)).and(MRKING_STRATEGY.END_TIME.gt(now));
                    break;
                case (byte)2:
                    select.where(MRKING_STRATEGY.STATUS.eq(STATUS_NORMAL)).and(MRKING_STRATEGY.START_TIME.gt(now));
                    break;
                case (byte)3:
                    select.where(MRKING_STRATEGY.STATUS.eq(STATUS_NORMAL)).and(MRKING_STRATEGY.END_TIME.lt(now));
                    break;
                case (byte)4:
                    select.where(MRKING_STRATEGY.STATUS.eq(STATUS_DISABLED));
                    break;
                default:
            }
        }
        select.where(MRKING_STRATEGY.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(MRKING_STRATEGY.CREATE_TIME.desc());
        PageResult<MrkingStrategyPageListQueryVo> res =  getPageResult(select,param.getCurrentPage(),param.getPageRows(),MrkingStrategyPageListQueryVo.class);
        if(res.dataList != null){
            for(MrkingStrategyPageListQueryVo mrkingStrategy : res.dataList){
                mrkingStrategy.setCondition(this.getMrkingStrategyCondition(mrkingStrategy.getId()));
                mrkingStrategy.setCurrentState(Util.getActStatus(mrkingStrategy.getStatus(),mrkingStrategy.getStartTime(),mrkingStrategy.getEndTime()));
            }
        }

        return res;
    }

    /**
     * 取某满折满减活动下的优惠规则
     *
     */
    public List<MrkingStrategyCondition> getMrkingStrategyCondition(Integer strategyId){
        return db().select(MRKING_STRATEGY_CONDITION.FULL_MONEY,MRKING_STRATEGY_CONDITION.REDUCE_MONEY,MRKING_STRATEGY_CONDITION.AMOUNT,MRKING_STRATEGY_CONDITION.DISCOUNT).
            from(MRKING_STRATEGY_CONDITION).
            where(MRKING_STRATEGY_CONDITION.STRATEGY_ID.eq(strategyId)).
            fetchInto(MrkingStrategyCondition.class);
    }

    /**
     * 取单个满折满减活动信息
     *
     */
    public MrkingStrategyVo getMrkingStrategyById(Integer id){
        MrkingStrategyRecord record = db().selectFrom(MRKING_STRATEGY).where(MRKING_STRATEGY.ID.eq(id)).fetchAny();
        MrkingStrategyVo res = record.into(MrkingStrategyVo.class);

        if(StringUtil.isNotEmpty(record.getRecommendGoodsId())){
            res.setRecommendGoodsIds(Util.splitValueToList(record.getRecommendGoodsId()));
            res.setRecommendGoods(saas().getShopApp(getShopId()).goods.selectGoodsViewList(Util.stringToList(record.getRecommendGoodsId())));
        }
        if(StringUtil.isNotEmpty(record.getCardId())){
            res.setCardIds(Util.splitValueToList(record.getCardId()));
            res.setMemberCards(saas().getShopApp(getShopId()).member.card.getMemberCardByCardIdsString(record.getCardId()));
        }
        if(StringUtil.isNotEmpty(record.getRecommendCatId())){
            res.setRecommendCatIds(Util.splitValueToList(record.getRecommendCatId()));
            res.setRecommendCat(saas.sysCate.getList(res.getRecommendCatIds()));
        }
        if(StringUtil.isNotEmpty(record.getRecommendBrandId())){
            res.setRecommendBrandIds(Util.splitValueToList(record.getRecommendBrandId()));
            res.setRecommendBrand(saas.getShopApp(getShopId()).goods.goodsBrand.getGoodsBrandVoById(res.getRecommendBrandIds()));
        }
        if(StringUtil.isNotEmpty(record.getRecommendSortId())){
            res.setRecommendSortIds(Util.splitValueToList(record.getRecommendSortId()));
            res.setRecommendSort(saas.getShopApp(getShopId()).goods.goodsSort.getListByIds(res.getRecommendSortIds()));
        }

        res.setCondition(this.getMrkingStrategyCondition(record.getId()));

        return res;
    }

    /**
     * 更新满折满减活动
     *
     */
    public void updateMrkingStrategy(MrkingStrategyUpdateParam param) {
        MrkingStrategyRecord record = new MrkingStrategyRecord();
        assign(param,record);
        db().executeUpdate(record);
    }

    /**
     * 删除满折满减活动
     *
     */
    public void delMrkingStrategy(Integer id) {
        db().update(MRKING_STRATEGY).
            set(MRKING_STRATEGY.DEL_FLAG,DelFlag.DISABLE.getCode()).
            where(MRKING_STRATEGY.ID.eq(id)).
            execute();
    }

    public MrkingStrategyGoodsListVo getWxAppGoodsList(MrkingStrategyGoodsListParam param,Integer userId){
        MrkingStrategyGoodsListVo vo = new MrkingStrategyGoodsListVo();

        //校验活动
        MrkingStrategyRecord MrkingStrategyAct = db().selectFrom(MRKING_STRATEGY).where(MRKING_STRATEGY.ID.eq(param.getStrategyId())).fetchAny();
        if(MrkingStrategyAct == null || MrkingStrategyAct.getDelFlag().equals(DelFlag.DISABLE_VALUE)){
            vo.setState((byte)1);
            return vo;
        }else if(MrkingStrategyAct.getStartTime().after(DateUtil.getLocalDateTime())){
            vo.setState((byte)2);
            return vo;
        }else if(MrkingStrategyAct.getEndTime().before(DateUtil.getLocalDateTime())){
            vo.setState((byte)3);
            return vo;
        }

        if(StringUtil.isNotEmpty(MrkingStrategyAct.getCardId())){
            //设置了持有会员卡才可以参与活动
            List<Integer> cardIds = Util.splitValueToList(MrkingStrategyAct.getCardId());
            List<ValidUserCardBean> cards = saas.getShopApp(getShopId()).userCard.userCardDao.getValidCardList(userId);
            List<Integer> validCardIds = cards.stream().map(ValidUserCardBean::getCardId).collect(Collectors.toList());
            validCardIds.removeAll(cardIds);

            if(validCardIds == null || validCardIds.size() == 0){
                vo.setState((byte)4);
            }
        }

//        if(MrkingStrategyAct.getActType().equals())
//        List<Integer> goodsIds = getMrkingStrategyGoodsIds(MrkingStrategyAct);

        Byte soldOutGoods = shopCommonConfigService.getSoldOutGoods();

        return vo;
    }

    private List<Integer> getMrkingStrategyGoodsIds(MrkingStrategyRecord record){
        List<Integer> res = new ArrayList<>();

        if(StringUtil.isNotEmpty(record.getRecommendGoodsId())){
            List<Integer> goodsIds = Util.splitValueToList(record.getRecommendGoodsId());
            res.removeAll(goodsIds);
            res.addAll(goodsIds);
        }

        List<Integer> goodsIds = goodsService.getOnShelfGoodsIdList(record.getRecommendCatId(),record.getRecommendSortId(),record.getRecommendBrandId());
        res.removeAll(goodsIds);
        res.addAll(goodsIds);

        return res;
    }



}
