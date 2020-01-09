package com.vpu.mp.service.shop.market.fullcut;

import com.vpu.mp.db.shop.tables.records.MrkingStrategyConditionRecord;
import com.vpu.mp.db.shop.tables.records.MrkingStrategyRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsView;
import com.vpu.mp.service.pojo.shop.market.fullcut.*;
import jodd.util.StringUtil;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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
        SelectWhereStep<? extends Record> select = db().select(MRKING_STRATEGY.ID,MRKING_STRATEGY.ACT_NAME,MRKING_STRATEGY.TYPE,MRKING_STRATEGY.START_TIME,MRKING_STRATEGY.END_TIME, MRKING_STRATEGY.STATUS).
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

    /**
     * 判断商品是否参与满减活动了 活动处理后需删除
     * @param goodsId
     */
    @Deprecated
    public boolean getGoodsAct(Integer goodsId,Integer catId,Integer sortId){
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        MrkingStrategyRecord mrkingStrategyRecord =null;

        mrkingStrategyRecord = db().selectFrom(MRKING_STRATEGY)
            .where(MRKING_STRATEGY.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).and(MRKING_STRATEGY.START_TIME.le(now)).and(MRKING_STRATEGY.END_TIME.gt(now)))
            .and(
                DslPlus.findInSet(goodsId, MRKING_STRATEGY.RECOMMEND_GOODS_ID)
                    .or(DslPlus.findInSet(catId, MRKING_STRATEGY.RECOMMEND_CAT_ID))
                    .or(DslPlus.findInSet(sortId, MRKING_STRATEGY.RECOMMEND_SORT_ID))
            ).fetchAny();

        if (mrkingStrategyRecord == null) {
            mrkingStrategyRecord = db().selectFrom(MRKING_STRATEGY)
                .where(MRKING_STRATEGY.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).and(MRKING_STRATEGY.START_TIME.le(now)).and(MRKING_STRATEGY.END_TIME.gt(now)))
                .and(MRKING_STRATEGY.RECOMMEND_GOODS_ID.isNull()).and(MRKING_STRATEGY.RECOMMEND_CAT_ID.isNull()).and(MRKING_STRATEGY.RECOMMEND_SORT_ID.isNull())
                .fetchAny();
        }
        return mrkingStrategyRecord !=null;
    }
}
