package com.vpu.mp.service.shop.goods.es.convert.param;

import com.vpu.mp.service.foundation.es.annotation.EsSearch;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchName;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.es.FieldProperty;
import com.vpu.mp.service.pojo.shop.goods.es.Operator;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpParam;

import java.util.ArrayList;
import java.util.List;

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

    private EsSearchParam assemblyEsSearchParam(GoodsListMpParam param,Integer shopId){
        List<FieldProperty> propertyList = new ArrayList<>();
        EsSearchParam searchParam = new EsSearchParam();
        if( null != param.getPageRows() ){
            searchParam.setPageRows(param.getPageRows());
        }
        if( null != param.getCurrentPage() ){
            searchParam.setCurrentPage(param.getCurrentPage());
        }
        if( null != param.getGoodsItems() && !param.getGoodsItems().isEmpty() ){
            searchParam.setQueryByPage(false);
        }else{
            searchParam.setPageRows(param.getGoodsNum());
            searchParam.setQueryByPage(true);
        }
        if( null != shopId ){
            propertyList.add(new FieldProperty(EsSearchName.SHOP_ID,shopId));
        }
        /**
         * Whether the show has been sold out of goods
         */
        if( !param.getSoldOutGoodsShow() ){
            propertyList.add(new FieldProperty(EsSearchName.IS_ON_SALE,1));
            propertyList.add(new FieldProperty(EsSearchName.GOODS_NUMBER,0, Operator.GT));
        }
        if( !propertyList.isEmpty() ){
            searchParam.setSearchList(propertyList);
        }
        return searchParam;
    }
}
