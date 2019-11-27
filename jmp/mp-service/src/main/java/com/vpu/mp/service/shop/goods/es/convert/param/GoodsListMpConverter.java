package com.vpu.mp.service.shop.goods.es.convert.param;

import com.vpu.mp.service.pojo.shop.goods.es.EsSearchName;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.es.FieldProperty;
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
            if( param.getCurrentPage() != -1 ){
                searchParam.setQueryByPage(true);
            }
            searchParam.setCurrentPage(param.getCurrentPage());
        }
        if( null != shopId ){
            propertyList.add(new FieldProperty(EsSearchName.SHOP_ID,shopId));
        }

        if( !propertyList.isEmpty() ){
            searchParam.setSearchList(propertyList);
        }
        return searchParam;
    }
}
