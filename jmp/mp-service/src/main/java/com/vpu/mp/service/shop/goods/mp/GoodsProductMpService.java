package com.vpu.mp.service.shop.goods.mp;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.goods.Goods;
import com.vpu.mp.service.pojo.shop.goods.goods.mp.GoodsProductT;
import com.vpu.mp.service.pojo.shop.goods.goods.mp.GoodsT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;

/**
 * @author 李晓冰
 * @date 2019年10月15日
 * 小程序和装修内商品规格处理类
 */
@Service
public class GoodsProductMpService extends ShopBaseService {

    /**
     * 获取商品规格中的最高价和最低价
     * @param goodsIds 商品id集合
     * @return  Map<Integer,GoodsT> key:商品id value:{@link GoodsT} 内含maxPrice 和minPrice defaultPrd
     */
    public Map<Integer,GoodsT> getGoodsPrdPriceInfo(List<Integer> goodsIds){

        Map<Integer, List<GoodsProductT>> goodsIdProductMap = db().select(GOODS_SPEC_PRODUCT.GOODS_ID, GOODS_SPEC_PRODUCT.PRD_ID, GOODS_SPEC_PRODUCT.PRD_PRICE, GOODS_SPEC_PRODUCT.PRD_DESC)
            .from(GOODS_SPEC_PRODUCT).where(GOODS_SPEC_PRODUCT.GOODS_ID.in(goodsIds)).fetchGroups(GOODS_SPEC_PRODUCT.GOODS_ID, GoodsProductT.class);

        Map<Integer,GoodsT> retGoodsIdPriceMap = new HashMap<>(goodsIdProductMap.size());

        for (Map.Entry<Integer, List<GoodsProductT>> entry : goodsIdProductMap.entrySet()) {
            GoodsT goodsT = new GoodsT();
            List<GoodsProductT> goodsProductTList = entry.getValue();

            if (goodsProductTList.size() == 1 && StringUtils.isBlank(goodsProductTList.get(0).getPrdDesc())) {
                goodsT.setMaxPrice(goodsProductTList.get(0).getPrdPrice());
                goodsT.setMinPrice(goodsProductTList.get(0).getPrdPrice());
                goodsT.setDefaultPrd((byte) 1);
            } else {
                GoodsT priceT = calculateMaxMinPrice(goodsProductTList);
                goodsT.setDefaultPrd((byte) 0);
                goodsT.setMinPrice(priceT.getMinPrice());
                goodsT.setMaxPrice(priceT.getMaxPrice());
            }
            retGoodsIdPriceMap.put(entry.getKey(),goodsT);
        }
        return retGoodsIdPriceMap;
    }

    private GoodsT calculateMaxMinPrice(List<GoodsProductT> goodsProductTList){
        BigDecimal maxPrice = BigDecimal.valueOf(-1);
        BigDecimal minPrice = BigDecimal.valueOf(Double.MAX_VALUE);

        for (GoodsProductT goodsProductT : goodsProductTList) {
            if (maxPrice.compareTo(goodsProductT.getPrdPrice()) <= 0) {
                maxPrice = goodsProductT.getPrdPrice();
            }
            if (minPrice.compareTo(goodsProductT.getPrdPrice()) >= 0) {
                minPrice = goodsProductT.getPrdPrice();
            }
        }
        GoodsT goodsT=new GoodsT();
        goodsT.setMaxPrice(maxPrice);
        goodsT.setMinPrice(minPrice);

        return goodsT;
    }
}
