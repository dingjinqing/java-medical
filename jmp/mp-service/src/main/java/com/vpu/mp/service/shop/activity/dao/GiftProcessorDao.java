package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.market.gift.GiftVo;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.shop.market.gift.GiftService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * 赠品processor
 * @author 王帅
 */
@Service
public class GiftProcessorDao extends GiftService {

    /**
     *
     * @param userId
     * @param goodsBo
     */
    public void getGifts(Integer userId, List<OrderGoodsBo> goodsBo){

    }

    public List<GiftVo> getActiveActivity(){
        Timestamp now = DateUtil.getSqlTimestamp();
        return db().select(TABLE.ID, TABLE.NAME, TABLE.START_TIME, TABLE.END_TIME, TABLE.LEVEL, TABLE.STATUS, TABLE.GOODS_ID, TABLE.RULE, TABLE.EXPLAIN)
            .from(TABLE)
            .where(TABLE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())
                .and(TABLE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
                .and(TABLE.START_TIME.le(now))
                .and(TABLE.END_TIME.gt(now)))
            .orderBy(TABLE.LEVEL.desc())
            .fetchInto(GiftVo.class);
    }
}
