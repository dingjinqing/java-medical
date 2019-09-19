package com.vpu.mp.service.shop.market.freeshipping;

import static com.vpu.mp.db.main.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.FreeShipping.FREE_SHIPPING;
import static com.vpu.mp.db.shop.tables.FreeShippingRule.FREE_SHIPPING_RULE;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.main.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.FreeShippingRecord;
import com.vpu.mp.db.shop.tables.records.FreeShippingRuleRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.freeshipping.FreeShipQueryParam;
import com.vpu.mp.service.pojo.shop.market.freeshipping.FreeShippingGoodsRuleVo;
import com.vpu.mp.service.pojo.shop.market.freeshipping.FreeShippingParam;
import com.vpu.mp.service.pojo.shop.market.freeshipping.FreeShippingRuleVo;
import com.vpu.mp.service.pojo.shop.market.freeshipping.FreeShippingVo;

/**
 * 免邮费
 *
 * @author 孔德成
 * @date 2019/7/29 17:04
 */
@Service
public class FreeShippingService extends ShopBaseService {

    /**
     * 启用状态 0 启用 1 停用
     */
    private static final byte USE_STATUS = 1;
    private static final byte STOP_STATUS = 0;
    /**
     * 有效期 0 固定期限 1永久有效
     */
    private static final byte FIXED_EXPIRE = 0;
    private static final byte NEVER_EXPIRE  = 1;

    @Autowired
    public FreeShippingRuleService ruleService;



    /**
     * 获取商品使用满包邮规则
     *
     * @param goodsId
     */
    public void getGoodsFreeShipRule(Integer goodsId) {
        GoodsRecord goods = db().selectFrom(GOODS)
                .where(GOODS.GOODS_ID.eq(goodsId))
                .fetchOne();
        Timestamp date = new Timestamp(System.currentTimeMillis());
        Result<FreeShippingRecord> freeShippingList = db()
                .selectFrom(FREE_SHIPPING)
                .where(FREE_SHIPPING.EXPIRE_TYPE.eq(NEVER_EXPIRE)
                        .or(DSL.field(FREE_SHIPPING.EXPIRE_TYPE.eq(FIXED_EXPIRE)
                                .and(FREE_SHIPPING.START_TIME.lt(date)
                                        .and(FREE_SHIPPING.END_TIME.gt(date))))))
                .and(FREE_SHIPPING.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
                .and(FREE_SHIPPING.STATUS.eq(USE_STATUS))
                .fetch();
        freeShippingList.forEach(freeShip -> {
            Result<FreeShippingRuleRecord> ruleList = ruleService.getFreeShippingRule(freeShip.getId());
            if (freeShip.getType().equals(0)) {
                ruleList.forEach(freeShipRule -> {
                    FreeShippingGoodsRuleVo ruleVo = new FreeShippingGoodsRuleVo();
                    ruleVo.setAction(5);
                    ruleVo.setIdentityId(freeShipRule.getId());
                    ruleVo.setShippingId(freeShipRule.getShippingId());
                    ruleVo.setConType(freeShipRule.getConType());
                });

            } else {
                if (freeShip.getRecommendGoodsId().contains(goodsId.toString()) ||
                        (goods.getSortId() != null && freeShip.getRecommendSortId().contains(goods.getSortId().toString())) ||
                        (goods.getCatId() != null && freeShip.getRecommendCatId().contains(goods.getCatId().toString()))) {
                    ruleList.forEach(freeShipRule -> {
                        FreeShippingGoodsRuleVo ruleVo = new FreeShippingGoodsRuleVo();
                        ruleVo.setAction(5);
                        ruleVo.setIdentityId(freeShipRule.getId());
                        ruleVo.setShippingId(freeShipRule.getShippingId());
                        ruleVo.setConType(freeShipRule.getConType());
                    });
                }
            }
        });
    }


    /**
     * 根据规则获取活动
     *
     * @param ruleId
     * @return
     */
    public Record getInfoByRule(Integer ruleId) {
        return db()
                .select()
                .from(FREE_SHIPPING_RULE)
                .leftJoin(FREE_SHIPPING)
                .on(FREE_SHIPPING_RULE.SHIPPING_ID.eq(FREE_SHIPPING.ID))
                .where(FREE_SHIPPING_RULE.ID.eq(ruleId))
                .orderBy(FREE_SHIPPING.LEVEL.desc(), FREE_SHIPPING.CREATE_TIME.desc())
                .fetchOne();
    }


    /**
     * 新增满包邮
     *
     * @param param
     */
    public void addFreeShipping(FreeShippingParam param) {
        transaction(() -> {
            param.setId(null);
            FreeShippingRecord record = db().newRecord(FREE_SHIPPING,param);
            record.insert();
            param.getRuleList().forEach(rule -> {
                FreeShippingRuleRecord ruleRecord = db().newRecord(FREE_SHIPPING_RULE,rule);
                ruleRecord.setShippingId(record.getId());
                ruleRecord.insert();
            });
        });

    }

    /**
     * 更新满包邮
     *
     * @param param
     */
    public void updateFreeShipping(FreeShippingParam param) {
        //开启事务
        transaction(() -> {
            FreeShippingRecord record = db().newRecord(FREE_SHIPPING,param);
            record.update();
            List<Integer> ruleList = new ArrayList<>();
            //更新规则
            param.getRuleList().forEach(rule -> {
                FreeShippingRuleRecord ruleRecord = db().newRecord(FREE_SHIPPING_RULE,rule);
                ruleRecord.setShippingId(record.getId());
                if (ruleRecord.getId() == null) {
                    ruleRecord.insert();
                } else {
                    ruleRecord.update();
                }
                ruleList.add(ruleRecord.getId());
            });
            //删除多余的规则
            ruleService.deleteByid(record.getId(), ruleList);
        });

    }


    /**
     * 获取满包邮
     *
     * @param param
     * @return
     */
    public PageResult<FreeShippingVo> getFreeShippingList(FreeShipQueryParam param) {
        SelectConditionStep<Record> select = db()
                .select(FREE_SHIPPING.asterisk())
                .from(FREE_SHIPPING)
                .leftJoin(FREE_SHIPPING_RULE).on(FREE_SHIPPING_RULE.SHIPPING_ID.eq(FREE_SHIPPING.ID))
                .where(FREE_SHIPPING.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
        buildOption(select, param);
        PageResult<FreeShippingVo> result = getPageResult(select, param.getCurrentPage(), param.getPageRows(), FreeShippingVo.class);
        result.getDataList().forEach(freeShipping -> {
            List<FreeShippingRuleVo> ruleVoList = ruleService.getRuleListByFreeShippingId(freeShipping.getId()).into(FreeShippingRuleVo.class);
            freeShipping.setRuleList(ruleVoList);
        });
        return result;
    }

    private void buildOption(SelectConditionStep<Record> select, FreeShipQueryParam param) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (param.getNavType()!=null) {
            switch (param.getNavType()) {
                case 0:
                    //全部满包邮活动
                    break;
                case 1:
                    //进行中的
                    select.and(FREE_SHIPPING.EXPIRE_TYPE.eq(NEVER_EXPIRE))
                            .or(FREE_SHIPPING.EXPIRE_TYPE.eq(FIXED_EXPIRE)
                                    .and(FREE_SHIPPING.START_TIME.le(timestamp))
                                    .and(FREE_SHIPPING.END_TIME.ge(timestamp)))
                            .and(FREE_SHIPPING.STATUS.eq((USE_STATUS)));
                    break;
                case 2:
                    //未开始
                    select.and(FREE_SHIPPING.START_TIME.gt(timestamp))
                            .and(FREE_SHIPPING.STATUS.eq(USE_STATUS));
                    break;
                case 3:
                    //已过期
                    select.and(FREE_SHIPPING.END_TIME.lt(timestamp))
                            .and(FREE_SHIPPING.STATUS.eq(USE_STATUS));
                    break;
                case 4:
                    //已停用
                    select.and(FREE_SHIPPING.STATUS.eq(STOP_STATUS));
                    break;
                default:
            }
        }
    }

    public FreeShippingRecord getFreeShippingById(Integer id) {
        return db().selectFrom(FREE_SHIPPING).where(FREE_SHIPPING.ID.eq(id)).fetchOne();
    }

    /**
     * 改变状态
     *
     * @param id
     * @return
     */
    public void changeStatus(Integer id) {
        FreeShippingRecord record = getFreeShippingById(id);
        if (record.getStatus().equals(USE_STATUS)) {
            db().update(FREE_SHIPPING)
                    .set(FREE_SHIPPING.STATUS, STOP_STATUS)
                    .where(FREE_SHIPPING.ID.eq(id))
                    .execute();
        } else {
            db().update(FREE_SHIPPING)
                    .set(FREE_SHIPPING.STATUS, USE_STATUS)
                    .where(FREE_SHIPPING.ID.eq(id))
                    .execute();
        }
    }

    /**
     * 删除满包邮
     * @param id
     * @return
     */
    public int deleteFreeShipping(Integer id) {
        ruleService.deleteByid(id,null);
        return db().update(FREE_SHIPPING)
                .set(FREE_SHIPPING.DEL_FLAG, DelFlag.DISABLE_VALUE)
                .where(FREE_SHIPPING.ID.eq(id))
                .execute();
    }
}
