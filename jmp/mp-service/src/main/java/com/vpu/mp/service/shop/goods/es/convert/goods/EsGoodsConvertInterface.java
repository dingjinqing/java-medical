package com.vpu.mp.service.shop.goods.es.convert.goods;

import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import com.vpu.mp.service.shop.goods.es.EsGoods;

/**
 * esGoods转换
 * @author 卢光耀
 * @date 2019/11/6 2:32 下午
 *
*/
public interface EsGoodsConvertInterface<T> {

    /**
     * admin列表页vo
     * @param esGoods es查询出来的结果
     * @return
     */
    T convert(EsGoods esGoods);

}
