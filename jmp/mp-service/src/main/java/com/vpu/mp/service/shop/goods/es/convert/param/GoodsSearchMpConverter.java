package com.vpu.mp.service.shop.goods.es.convert.param;

import com.google.common.collect.Lists;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchName;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.es.FieldProperty;
import com.vpu.mp.service.pojo.shop.goods.es.Operator;
import com.vpu.mp.service.pojo.wxapp.goods.search.GoodsSearchMpParam;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class GoodsSearchMpConverter implements EsParamConvertInterface  {
    private GoodsSearchMpParam param;


    @Override
    public EsSearchParam convert(Object object, Integer shopId) {
        if( object instanceof GoodsSearchMpParam ){
            param = (GoodsSearchMpParam)object;
            return assemblyEsSearchParam(param,shopId);
        }else{
            return null;
        }
    }

    private EsSearchParam assemblyEsSearchParam(GoodsSearchMpParam param,Integer shopId){
        List<FieldProperty> propertyList = new ArrayList<>();
        EsSearchParam searchParam = new EsSearchParam();
        searchParam.setQueryByPage(true);
        searchParam.setCurrentPage(param.getCurrentPage());
        searchParam.setPageRows(param.getPageRows());
        propertyList.add(new FieldProperty(EsSearchName.IS_ON_SALE,1));
        if( null != shopId ){
            propertyList.add(new FieldProperty(EsSearchName.SHOP_ID,shopId));
        }
        //关键字查询支持：
        //1.商品名称（模糊查询）
        //2.商品品牌（精确查询）
        if( StringUtils.isNotBlank(param.getKeyWords()) ){
            propertyList.add(new FieldProperty(EsSearchName.KEY_WORDS,param.getKeyWords()));
//            propertyList.add(new FieldProperty(EsSearchName.BRAND_NAME,param.getKeyWords()));
        }
        if( null != param.getMinPrice() ){
            propertyList.add(new FieldProperty(EsSearchName.SHOW_PRICE,param.getMinPrice(),Operator.GTE));
        }
        if( null != param.getMaxPrice() ){
            propertyList.add(new FieldProperty(EsSearchName.SHOW_PRICE,param.getMaxPrice(),Operator.LTE));
        }
        if( !CollectionUtils.isEmpty(param.getSortIds()) ){
            propertyList.add(new FieldProperty(EsSearchName.FULL_SORT_ID,param.getSortIds()));
        }
        if( !CollectionUtils.isEmpty(param.getBrandIds()) ){
            propertyList.add(new FieldProperty(EsSearchName.BRAND_ID,param.getBrandIds()));
        }
        if( !CollectionUtils.isEmpty(param.getActivityTypes()) ){
            propertyList.add(new FieldProperty(EsSearchName.GOODS_TYPE,param.getActivityTypes()));
        }
        if( !CollectionUtils.isEmpty( param.getLabelIds()) ){
            propertyList.add(new FieldProperty(EsSearchName.GOODS_ID,param.getGoodsIds()));
        }
        if( !propertyList.isEmpty() ){
            searchParam.setSearchList(propertyList);
        }
        return searchParam;
    }

}
