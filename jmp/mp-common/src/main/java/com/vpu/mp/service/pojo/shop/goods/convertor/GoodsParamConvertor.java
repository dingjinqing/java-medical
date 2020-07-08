package com.vpu.mp.service.pojo.shop.goods.convertor;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsPageListCondition;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsSortItem;
import com.vpu.mp.service.pojo.shop.goods.param.MedicalGoodsPageListParam;

import java.util.Collections;

/**
 * @author 李晓冰
 * @date 2020年07月08日
 */
public class GoodsParamConvertor {
    public static final String ASC = "asc";

    public static GoodsPageListCondition converPageListConditionFromPageListParam(MedicalGoodsPageListParam pageListParam){
        GoodsPageListCondition goodsPageListCondition = new GoodsPageListCondition();
        FieldsUtil.assign(pageListParam,goodsPageListCondition);

        if (pageListParam.getOrderField() != null) {
            GoodsSortItem goodsSortItem = new GoodsSortItem();
           goodsSortItem.setColumnName(pageListParam.getOrderField());
            if (ASC.equals(pageListParam.getOrderDirection())) {
                goodsSortItem.setAsc(true);
            } else {
                goodsSortItem.setAsc(false);
            }
            goodsPageListCondition.setPageSortItems(Collections.singletonList(goodsSortItem));
        }
        return goodsPageListCondition;
    }
}
