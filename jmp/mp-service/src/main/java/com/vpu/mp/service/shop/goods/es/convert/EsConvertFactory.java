package com.vpu.mp.service.shop.goods.es.convert;

import com.vpu.mp.service.shop.goods.es.convert.goods.EsGoodsConvertInterface;
import com.vpu.mp.service.shop.goods.es.convert.param.EsParamConvertInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * 转换器工厂类
 * @author 卢光耀
 * @date 2019/11/1 6:05 下午
 *
*/
public class EsConvertFactory {

    private static final Map<Class, EsParamConvertInterface> param_map = new HashMap<>();

    private static final Map<Class, EsGoodsConvertInterface> goods_map = new HashMap<>();

    public static EsParamConvertInterface getParamConvert(Class clz){
        EsParamConvertInterface imp = param_map.get(clz);
        if( imp == null ){
            try {
                imp = (EsParamConvertInterface)clz.newInstance();
                param_map.put(clz,imp);
            }catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return imp;
    }
    public static EsGoodsConvertInterface getGoodsConvert(Class clz){
        EsGoodsConvertInterface imp = goods_map.get(clz);
        if( imp == null ){
            try {
                imp = (EsGoodsConvertInterface)clz.newInstance();
                goods_map.put(clz,imp);
            }catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return imp;
    }

}
