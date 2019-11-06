package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.activity.info.GoodsPrdProcessorDataInfo;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record3;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;

/**
 * @author 李晓冰
 * @date 2019年11月04日
 */
public class GoodsPrdProcessorDao extends ShopBaseService {

    /**
     * 获取集合内商品id对应的规格价格信息
     * @param goodsIds 商品id集合
     * @return key: 商品id，value:{@link GoodsPrdProcessorDataInfo}
     */
    public Map<Integer, GoodsPrdProcessorDataInfo> getGoodsPrdInfo(List<Integer> goodsIds) {
        Map<Integer, List<Record3<Integer, BigDecimal,String>>> goodsPrdMap = db().select(GOODS_SPEC_PRODUCT.GOODS_ID, GOODS_SPEC_PRODUCT.PRD_PRICE,GOODS_SPEC_PRODUCT.PRD_DESC)
            .from(GOODS_SPEC_PRODUCT).where(GOODS_SPEC_PRODUCT.GOODS_ID.in(goodsIds))
            .orderBy(GOODS_SPEC_PRODUCT.PRD_PRICE.desc())
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(GOODS_SPEC_PRODUCT.GOODS_ID)));

        Map<Integer, GoodsPrdProcessorDataInfo> returnMap = new HashMap<>();

        goodsPrdMap.forEach((key,value)->{
            GoodsPrdProcessorDataInfo info = new GoodsPrdProcessorDataInfo();
            info.setDefaultPrd(value.size()==1&&StringUtils.isBlank(value.get(0).get(GOODS_SPEC_PRODUCT.PRD_DESC)));
            info.setMaxPrice(value.get(0).get(GOODS_SPEC_PRODUCT.PRD_PRICE));
            info.setMinPrice(value.get(value.size()-1).get(GOODS_SPEC_PRODUCT.PRD_PRICE));
            returnMap.put(key,info);
        });
        return returnMap;
    }
}
