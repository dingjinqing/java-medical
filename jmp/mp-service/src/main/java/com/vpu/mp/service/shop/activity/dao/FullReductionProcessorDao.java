package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.database.DslPlus;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import org.jooq.Condition;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static com.vpu.mp.db.shop.Tables.MRKING_STRATEGY;

/**
 * @author 李晓冰
 * @date 2019年10月30日
 */
@Service
public class FullReductionProcessorDao extends ShopBaseService {

    /** 全部商品参与活动 */
    private static final byte ACT_TYPE_ALL_GOODS = 0;
    /** 指定条件参与活动 */
    private static final byte ACT_TYPE_POINT_CONDITION = 1;

    /**
     * 判断根据传入的条件是否存在对应的满折满减活动
     * @param goodsId 商品id
     * @param catId 平台分类id
     * @param sortId 商家分类id
     * @param brandId 品牌分类id
     * @param date 时间
     * @return true 存在对应的活动，false 不存在对应的活动
     */
    public boolean getIsFullReductionListInfo(Integer goodsId, Integer catId, Integer sortId, Integer brandId, Timestamp date) {
        Condition condition =MRKING_STRATEGY.ACT_TYPE.eq(ACT_TYPE_ALL_GOODS)
            .or(MRKING_STRATEGY.ACT_TYPE.eq(ACT_TYPE_POINT_CONDITION).and(
                DslPlus.findInSet(goodsId,MRKING_STRATEGY.RECOMMEND_GOODS_ID)
                .or(DslPlus.findInSet(catId,MRKING_STRATEGY.RECOMMEND_CAT_ID))
                .or(DslPlus.findInSet(sortId,MRKING_STRATEGY.RECOMMEND_SORT_ID))
                .or(DslPlus.findInSet(brandId,MRKING_STRATEGY.RECOMMEND_BRAND_ID))
            ));

        condition = MRKING_STRATEGY.DEL_FLAG.eq(DelFlag.NORMAL.getCode())
            .and(MRKING_STRATEGY.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(MRKING_STRATEGY.START_TIME.lt(date))
            .and(MRKING_STRATEGY.END_TIME.gt(date)).and(condition);

        int count = db().fetchCount(MRKING_STRATEGY, condition);
        return count>0;
    }
}
