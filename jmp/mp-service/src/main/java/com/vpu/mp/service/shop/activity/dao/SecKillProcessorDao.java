package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.SecKillProcessorDataInfo;
import org.jooq.Record3;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.SecKillDefine.SEC_KILL_DEFINE;
import static com.vpu.mp.db.shop.tables.SecKillProductDefine.SEC_KILL_PRODUCT_DEFINE;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
public class SecKillProcessorDao extends ShopBaseService {

    /**
     * 获取商品集合内的秒杀信息
     * @param goodsIds 商品id集合
     * @param date 日期
     * @return key:商品id，value:{@link SecKillProcessorDataInfo}
     */
    public  Map<Integer, SecKillProcessorDataInfo> getGoodsSecKillListInfo(List<Integer> goodsIds, Timestamp date){
        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> secKillInfos = db().select(SEC_KILL_DEFINE.SK_ID, SEC_KILL_DEFINE.GOODS_ID, SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE)
            .from(SEC_KILL_DEFINE).innerJoin(SEC_KILL_PRODUCT_DEFINE).on(SEC_KILL_DEFINE.SK_ID.eq(SEC_KILL_PRODUCT_DEFINE.SK_ID))
            .where(SEC_KILL_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(SEC_KILL_DEFINE.STATUS.eq(GoodsConstant.USE_STATUS))
            .and(SEC_KILL_DEFINE.END_TIME.gt(date))
            .and(SEC_KILL_DEFINE.GOODS_ID.in(goodsIds))
            .orderBy(SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE.asc())
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(SEC_KILL_DEFINE.GOODS_ID)));

        Map<Integer, SecKillProcessorDataInfo> returnMap = new HashMap<>();

        secKillInfos.forEach((goodsId,secKillPrds)->{
            Record3<Integer, Integer, BigDecimal> secKillPrd = secKillPrds.get(0);
            SecKillProcessorDataInfo info = new SecKillProcessorDataInfo();
            info.setDataId(secKillPrd.get(SEC_KILL_DEFINE.SK_ID));
            info.setDataPrice(secKillPrd.get(SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE));
            returnMap.put(goodsId,info);
        });
        return returnMap;
    }
}
