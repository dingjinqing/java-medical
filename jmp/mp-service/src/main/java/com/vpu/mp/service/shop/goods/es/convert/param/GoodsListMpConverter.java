package com.vpu.mp.service.shop.goods.es.convert.param;

import com.vpu.mp.service.foundation.es.annotation.EsSearch;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchName;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.es.FieldProperty;
import com.vpu.mp.service.pojo.shop.goods.es.Operator;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * GoodsListMpConverter
 * @author 卢光耀
 * @date 2019/11/14 5:41 下午
 *
*/
public class GoodsListMpConverter implements EsParamConvertInterface {

    private GoodsListMpParam param;


    @Override
    public EsSearchParam convert(Object object, Integer shopId) {
        if( object instanceof GoodsListMpParam ){
            param = (GoodsListMpParam)object;
           return assemblyEsSearchParam(param,shopId);
        }else{
            return null;
        }
    }

    private EsSearchParam assemblyEsSearchParam(GoodsListMpParam param,@NotNull Integer shopId){
        List<FieldProperty> propertyList = new ArrayList<>();
        EsSearchParam searchParam = new EsSearchParam();
        if( null != param.getPageRows() ){
            searchParam.setPageRows(param.getPageRows());
        }
        if( null != param.getCurrentPage() ){
            searchParam.setCurrentPage(param.getCurrentPage());
        }
        if( null != shopId ){
            propertyList.add(new FieldProperty(EsSearchName.SHOP_ID,shopId));
        }

        if( param.getRecommendType().equals((byte)1) ){
            //手动推荐
            searchParam.setQueryByPage(false);
            propertyList.add(new FieldProperty(EsSearchName.GOODS_ID,param.getGoodsItems()));
        }else if(param.getRecommendType().equals((byte)0)){
            //自动推荐
            searchParam.setPageRows(param.getGoodsNum());
            searchParam.setQueryByPage(true);
            /* 是否已售罄*/
            if( !param.getSoldOutGoodsShow() ){
                propertyList.add(new FieldProperty(EsSearchName.IS_ON_SALE,1));
                propertyList.add(new FieldProperty(EsSearchName.GOODS_NUMBER,0, Operator.GT));
            }
            /* 活动商品*/
            if( param.getGoodsType() != null && param.getGoodsType() != 0){
                propertyList.add(new FieldProperty(EsSearchName.GOODS_TYPE,param.getGoodsType()));
            }
            /* 关键词*/
            if( param.getGoodsType() != null && param.getGoodsType() != 0){
                propertyList.add(new FieldProperty(EsSearchName.GOODS_TYPE,param.getGoodsType()));
            }
            /* 商品范围*/
            if( StringUtils.isNotBlank(param.getGoodsArea()) ){
                String goodsArea = param.getGoodsArea();
                if( !CollectionUtils.isEmpty(param.getGoodsAreaData()) ){
                    if ( (GoodsListMpParam.BRAND_AREA).equals(goodsArea) ){
                        propertyList.add(new FieldProperty(EsSearchName.BRAND_ID,param.getGoodsAreaData()));
                    }else if( (GoodsListMpParam.CAT_AREA).equals(goodsArea) ){
                        propertyList.add(new FieldProperty(EsSearchName.FULL_CAT_ID,param.getGoodsAreaData()));
                    }else if( (GoodsListMpParam.LABEL_AREA).equals(goodsArea) ){
                        propertyList.add(new FieldProperty(EsSearchName.GOODS_LABEL,param.getGoodsAreaData()));
                    }else if( (GoodsListMpParam.SORT_AREA).equals(goodsArea) ){
                        propertyList.add(new FieldProperty(EsSearchName.FULL_SORT_ID,param.getGoodsAreaData()));
                    }
                }
            }
        }



        if( !propertyList.isEmpty() ){
            searchParam.setSearchList(propertyList);
        }
        return searchParam;
    }
}
