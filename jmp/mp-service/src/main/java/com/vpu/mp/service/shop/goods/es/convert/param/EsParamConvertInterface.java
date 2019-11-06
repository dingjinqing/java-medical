package com.vpu.mp.service.shop.goods.es.convert.param;

import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.es.Fact;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam;

/**
 * param转换接口
 * @author 卢光耀
 * @date 2019/11/1 5:23 下午
 *
*/
public interface EsParamConvertInterface {

    /**
     * GoodsPageListParam实现
     * @param shopId 门店id
     * @param param {@link GoodsPageListParam}
     * @return
     */
   EsSearchParam goodsPageConvert(GoodsPageListParam param,Integer shopId);


    /**
     * fact转化
     * @param name {@link com.vpu.mp.service.pojo.shop.goods.es.Fact}
     * @return
     */
    default Fact getFactByName(String name){
        return Fact.builder().name(name)
            .fieldName(name)
            .build();
    }
}
