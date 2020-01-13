package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.promotion.FullShipPromotion;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.FreeShipping.FREE_SHIPPING;
import static com.vpu.mp.db.shop.tables.FreeShippingRule.FREE_SHIPPING_RULE;

/**
 * 满包邮dao
 * @author 李晓冰
 * @date 2020年01月13日
 */
@Service
public class FreeShipProcessorDao extends ShopBaseService {

    /**
     * 获取满包邮信息
     * @param goodsId 商品ID
     * @param catId 平台分类ID
     * @param sortId 商家分类ID
     * @param now 时间
     */
    public List<FullShipPromotion> getFreeShipProcessorForDetail(Integer goodsId, Integer catId, Integer sortId, Timestamp now){

        Condition timeCondition = FREE_SHIPPING.EXPIRE_TYPE.eq(BaseConstant.ACTIVITY_IS_FOREVER).or(FREE_SHIPPING.EXPIRE_TYPE.eq(BaseConstant.ACTIVITY_NOT_FOREVER)
            .and(FREE_SHIPPING.START_TIME.le(now).and(FREE_SHIPPING.END_TIME.ge(now))));

        Condition pointCondition = buildCondition(goodsId,catId,sortId);

        Map<Integer, List<FullShipPromotion>> fullShipMap = db().select(FREE_SHIPPING.ID, FREE_SHIPPING_RULE.SHIPPING_ID.as("promotion_id"),
            FREE_SHIPPING_RULE.CON_TYPE, FREE_SHIPPING_RULE.MONEY, FREE_SHIPPING_RULE.NUM).from(FREE_SHIPPING).innerJoin(FREE_SHIPPING_RULE).on(FREE_SHIPPING_RULE.SHIPPING_ID.eq(FREE_SHIPPING.ID))
            .where(FREE_SHIPPING.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(FREE_SHIPPING.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(timeCondition).and(pointCondition))
            .orderBy(FREE_SHIPPING.LEVEL.desc(), FREE_SHIPPING.CREATE_TIME.desc())
            .fetchGroups(FREE_SHIPPING.ID, FullShipPromotion.class);

        return fullShipMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    private Condition buildCondition(Integer goodsId,Integer catId,Integer sortId){
        Condition condition = DSL.noCondition();

        if (goodsId != null) {
            condition = condition.or(DslPlus.findInSet(goodsId,FREE_SHIPPING.RECOMMEND_GOODS_ID));
        }

        if (catId != null) {
            condition = condition.or(DslPlus.findInSet(catId,FREE_SHIPPING.RECOMMEND_CAT_ID));
        }

        if (sortId != null) {
            condition = condition.or(DslPlus.findInSet(sortId,FREE_SHIPPING.RECOMMEND_SORT_ID));
        }

        condition = FREE_SHIPPING.TYPE.eq(BaseConstant.GOODS_AREA_TYPE_ALL).or(condition);

        return condition;
    }
}
