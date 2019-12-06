package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.db.shop.tables.records.BargainRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.tables.Bargain.BARGAIN;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
public class BargainProcessorDao extends ShopBaseService {

    /**
     * 获取商品集合内的砍价信息
     * @param goodsIds 商品id集合
     * @param date 日期
     * @return  List<BargainRecord>
     */
    public Map<Integer, BargainRecord> getGoodsBargainListInfo(List<Integer> goodsIds, Timestamp date){
        return db().select(BARGAIN.ID, BARGAIN.GOODS_ID, BARGAIN.BARGAIN_TYPE, BARGAIN.FLOOR_PRICE, BARGAIN.EXPECTATION_PRICE)
            .from(BARGAIN)
            .where(BARGAIN.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(BARGAIN.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(BARGAIN.GOODS_ID.in(goodsIds))
            .and(BARGAIN.START_TIME.lt(date))
            .and(BARGAIN.END_TIME.gt(date))
            .fetchMap(BARGAIN.GOODS_ID, BargainRecord.class);
    }
}
