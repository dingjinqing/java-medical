package com.vpu.mp.service.shop.goods.es.convert.param;

import com.vpu.mp.service.pojo.shop.goods.es.EsSearchName;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.es.FieldProperty;
import com.vpu.mp.service.pojo.shop.goods.es.Operator;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsListMpParam;
import com.vpu.mp.service.shop.goods.es.convert.exception.ParamConvertException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GoodsPageListParam to EsSearchParam
 * @author 卢光耀
 * @date 2019/11/1 5:30 下午
 *
*/
public class GoodsPageConvertEsParam implements EsParamConvertInterface {

    private GoodsPageListParam param;
    @Override
    public EsSearchParam convert(Object object,Integer shopId){
        if( object instanceof GoodsPageListParam){
            param = (GoodsPageListParam)object;
            return assemblyEsSearchParam(shopId);
        }else{
            throw new ParamConvertException("object is not instanceof GoodsPageListParam,can't convert");
        }
    }

    private EsSearchParam assemblyEsSearchParam(Integer shopId){
        EsSearchParam searchParam = new EsSearchParam();
        List<FieldProperty> propertyList = new ArrayList<>();
        if( param.getIsFactQuery() && param.getFactNameList() != null ){
            searchParam.setFactList(
                param.getFactNameList().stream().map(this::getFactByName).collect(Collectors.toList())
            );
        }
        if( null != shopId ){
            propertyList.add(new FieldProperty(EsSearchName.SHOP_ID,shopId));
        }
        if( param.getIsSaleOut() ){
            propertyList.add(new FieldProperty(EsSearchName.GOODS_NUMBER,0, Operator.EQ));
        }else{
            propertyList.add(new FieldProperty(EsSearchName.GOODS_NUMBER,0, Operator.GT));
        }
        if( null != param.getHighShopPrice() ){
            propertyList.add(new FieldProperty(EsSearchName.MAX_SPEC_PRD_PRICE,param.getHighShopPrice(),Operator.LTE));
        }
        if( null != param.getLowShopPrice() ){
            propertyList.add(new FieldProperty(EsSearchName.MIN_SPEC_PRD_PRICE,param.getLowShopPrice(),Operator.GTE));
        }
        if( null != param.getCatId() ){
            propertyList.add(new FieldProperty(EsSearchName.FULL_CAT_ID,param.getCatId()));
        }
        if( null != param.getSortId() ){
            propertyList.add(new FieldProperty(EsSearchName.FULL_SORT_ID,param.getSortId()));
        }
        if( null != param.getLabelId() ){
            propertyList.add(new FieldProperty(EsSearchName.GOODS_LABEL,param.getLabelId()));
        }
//        if( null != param.getSaleTimeStart() ){
//            //TODO 上架时间开始
//            propertyList.add(new FieldProperty(EsSearchName.C,shopId));
//
//        }
//        if( null != param.getSaleTimeEnd() ){
//            //TODO 上架时间截止
////            propertyList.add(new FieldProperty(EsSearchName.Sa,shopId));
//
//        }
        if( null != param.getBrandId() ){
            propertyList.add(new FieldProperty(EsSearchName.BRAND_ID,param.getBrandId()));
        }
        if( null != param.getSource() ){
            propertyList.add(new FieldProperty(EsSearchName.SOURCE,param.getSource()));
        }
        if( null != param.getGoodsType() ){
            propertyList.add(new FieldProperty(EsSearchName.GOODS_TYPE,param.getGoodsType()));
        }
        if(StringUtils.isNotBlank(param.getGoodsName()) ){
            propertyList.add(new FieldProperty(EsSearchName.GOODS_NAME,param.getGoodsName(),Operator.SIM));
        }
        if( null != param.getIsOnSale() ){
            propertyList.add(new FieldProperty(EsSearchName.IS_ON_SALE,param.getIsOnSale()));
        }
        if( null != param.getPageRows() ){
            searchParam.setPageRows(param.getPageRows());
        }
        if( null != param.getCurrentPage() ){
            searchParam.setCurrentPage(param.getCurrentPage());
        }
        if( !propertyList.isEmpty() ){
            searchParam.setSearchList(propertyList);
        }
        return searchParam;
    }
}
