package com.vpu.mp.service.shop.goods;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商品获取展示价格通用service
 * @author 卢光耀
 * @date 2019/10/15 4:44 下午
 *
*/
@Service
public class GoodsPriceService extends ShopBaseService {

    public Map<Integer, BigDecimal> getShowPriceByGoodsIds(List<Integer> goodsIds){
        return null;
    }

}
