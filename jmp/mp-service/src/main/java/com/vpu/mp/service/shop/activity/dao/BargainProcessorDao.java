package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.db.shop.tables.records.BargainRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.BargainProcessorDataInfo;
import com.vpu.mp.service.shop.market.bargain.BargainService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
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
     * @return key:商品id，value:{@link BargainProcessorDataInfo}
     */
    public Map<Integer, BargainProcessorDataInfo> getGoodsBargainListInfo(List<Integer> goodsIds, Timestamp date){
        List<BargainRecord> bargainRecords = new ArrayList<>(db().select(BARGAIN.ID, BARGAIN.GOODS_ID, BARGAIN.BARGAIN_TYPE, BARGAIN.FLOOR_PRICE, BARGAIN.EXPECTATION_PRICE)
            .from(BARGAIN)
            .where(BARGAIN.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(BARGAIN.STATUS.eq(GoodsConstant.USE_STATUS))
            .and(BARGAIN.GOODS_ID.in(goodsIds))
            .and(BARGAIN.START_TIME.lt(date))
            .and(BARGAIN.END_TIME.gt(date))
            .fetchInto(BARGAIN));

        Map<Integer, BargainProcessorDataInfo> returnMap = new HashMap<>(bargainRecords.size());
        bargainRecords.forEach(bargainRecord->{
            BargainProcessorDataInfo info = new BargainProcessorDataInfo();
            info.setDataId(bargainRecord.getId());
            info.setDataPrice(BargainService.BARGAIN_TYPE_FIXED == bargainRecord.getBargainType()?bargainRecord.getExpectationPrice():bargainRecord.getFloorPrice());
            returnMap.put(bargainRecord.getGoodsId(),info);
        });
        return returnMap;
    }
}
