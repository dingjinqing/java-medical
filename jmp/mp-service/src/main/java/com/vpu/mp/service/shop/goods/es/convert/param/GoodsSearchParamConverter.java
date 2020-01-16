package com.vpu.mp.service.shop.goods.es.convert.param;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchName;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.es.FieldProperty;
import com.vpu.mp.service.pojo.shop.goods.es.Operator;
import com.vpu.mp.service.pojo.wxapp.goods.search.GoodsSearchMpParam;
import com.vpu.mp.service.pojo.wxapp.goods.search.GoodsSearchParam;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GoodsSearchParamConverter implements EsParamConvertInterface {

    private GoodsSearchParam param;
    @Override
    public EsSearchParam convert(Object object, Integer shopId) {
        if( object instanceof GoodsSearchParam){
            param = (GoodsSearchParam)object;
            return assemblyEsSearchParam(param,shopId);
        }else{
            return null;
        }
    }

    private EsSearchParam assemblyEsSearchParam(GoodsSearchParam param, Integer shopId) {
        List<FieldProperty> propertyList = new ArrayList<>();
        EsSearchParam searchParam = new EsSearchParam();

        searchParam.setQueryByPage(true);
        searchParam.setPageRows(param.getPageRows());
        searchParam.setCurrentPage(param.getCurrentPage());

        propertyList.add(new FieldProperty(EsSearchName.SHOP_ID,Objects.requireNonNull(shopId)));

        if( StringUtils.isNotBlank(param.getGoodsName()) ){
            propertyList.add(new FieldProperty(EsSearchName.GOODS_NAME,param.getGoodsName(), Operator.SIM));
        }

        Byte goodAreaType = Objects.requireNonNull(param.getGoodsAreaType());
        if( goodAreaType.equals(BaseConstant.GOODS_AREA_TYPE_SECTION)  ){
            if( !CollectionUtils.isEmpty(param.getCatIds()) ){
                FieldProperty property = new FieldProperty(EsSearchName.FULL_CAT_ID,param.getCatIds());
                property.setMust(false);
                propertyList.add(property);
            }
            if( !CollectionUtils.isEmpty(param.getGoodsIds()) ){
                FieldProperty property = new FieldProperty(EsSearchName.GOODS_ID,param.getGoodsIds());
                property.setMust(false);
                propertyList.add(property);
            }
            if( !CollectionUtils.isEmpty(param.getSortIds()) ){
                FieldProperty property = new FieldProperty(EsSearchName.FULL_SORT_ID,param.getSortIds());
                property.setMust(false);
                propertyList.add(property);
            }
        }


        Boolean showSoldOut = Objects.requireNonNull(param.getShowSoldOut());
        if( showSoldOut ){
            propertyList.add(new FieldProperty(EsSearchName.GOODS_NUMBER,0, Operator.GTE));
        }else{
            propertyList.add(new FieldProperty(EsSearchName.GOODS_NUMBER,0, Operator.GT));
        }
        searchParam.setSearchList(propertyList);
        return searchParam;

    }
}
