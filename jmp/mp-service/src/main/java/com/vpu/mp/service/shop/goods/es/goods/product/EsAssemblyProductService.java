package com.vpu.mp.service.shop.goods.es.goods.product;

import com.beust.jcommander.internal.Lists;
import com.vpu.mp.service.shop.goods.es.goods.EsGoods;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsProduct;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EsAssemblyProductService {
    public List<EsGoodsProductEntity> getEsProduct(List<EsGoods> esGoodsList){
        List<EsGoodsProductEntity> result = Lists.newArrayList();
        if(CollectionUtils.isEmpty(esGoodsList) ){
            return result;
        }
        for( EsGoods esGoods: esGoodsList ){
            if (CollectionUtils.isEmpty(esGoods.getPrds())){
                return Lists.newArrayList();
            }
            for(EsGoodsProduct product: esGoods.getPrds()){
                EsGoodsProductEntity entity = new EsGoodsProductEntity();
                BeanUtils.copyProperties(esGoods,entity);
                entity.setPrdId(product.getPrdId());
                entity.setPrdCodes(product.getPrdCodes());
                entity.setPrdImg(product.getPrdImg());
                entity.setPrdRealPrice(product.getPrdRealPrice());
                entity.setPrdLinePrice(product.getPrdLinePrice());
                result.add(entity);
            }
        }
        return result;
    }
}
