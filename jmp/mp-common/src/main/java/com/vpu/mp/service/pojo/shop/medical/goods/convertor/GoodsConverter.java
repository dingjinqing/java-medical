package com.vpu.mp.service.pojo.shop.medical.goods.convertor;

import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.GoodsMedicalInfoDo;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsDo;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsPageListCondition;
import com.vpu.mp.common.pojo.shop.table.goods.GoodsSortItem;
import com.vpu.mp.service.pojo.shop.medical.goods.bo.GoodsMedicalExternalRequestItemBo;
import com.vpu.mp.service.pojo.shop.medical.goods.param.MedicalGoodsPageListParam;

import java.util.Collections;

/**
 * @author 李晓冰
 * @date 2020年07月08日
 */
public class GoodsConverter {
    public static final String ASC = "asc";

    public static GoodsPageListCondition convertPageListConditionFromPageListParam(MedicalGoodsPageListParam pageListParam){
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

    /**
     * 外部药品信息类转换为GoodsDo类
     * @param bo
     * @return
     */
    public static GoodsDo convertGoodsMedicalExternalRequestItemBoToGoodsDo(GoodsMedicalExternalRequestItemBo bo){
        GoodsDo goodsDo = new GoodsDo();
        goodsDo.setGoodsId(bo.getGoodsId());
        goodsDo.setGoodsSn(bo.getGoodsCode());
        goodsDo.setGoodsBarCode(bo.getGoodsBarCode());
        goodsDo.setGoodsName(bo.getGoodsCommonName());
        goodsDo.setShopPrice(bo.getGoodsPrice());
        goodsDo.setCostPrice(bo.getGoodsPrice());
        goodsDo.setMarketPrice(bo.getGoodsPrice());
        goodsDo.setGoodsNumber(bo.getGoodsNumber());
        goodsDo.setIsMedical(bo.getIsMedical());
        goodsDo.setUnit(bo.getGoodsBasicUnit());
        goodsDo.setStoreCode(bo.getStoreCode());
        goodsDo.setHisStatus(bo.getHisStatus());
        goodsDo.setStoreStatus(bo.getStoreStatus());
        if (BaseConstant.EXTERNAL_ITEM_STATE_DELETE.equals(bo.getState())) {
            goodsDo.setDelFlag(DelFlag.DISABLE_VALUE);
        }
        return goodsDo;
    }

    /**
     * 药店更新商品转换GoodsDo类
     * @param bo
     * @return
     */
    public static GoodsDo convertGoodsMedicalExternalRequestItemBoToGoodsDoForStore(GoodsMedicalExternalRequestItemBo bo){
        GoodsDo goodsDo = new GoodsDo();
        goodsDo.setGoodsId(bo.getGoodsId());
        goodsDo.setStoreStatus(bo.getStoreStatus());
        goodsDo.setMarketPrice(bo.getStorePrice());
        if (BaseConstant.EXTERNAL_ITEM_STATE_DELETE.equals(bo.getState())) {
            goodsDo.setDelFlag(DelFlag.DISABLE_VALUE);
        }
        return goodsDo;
    }

    public static GoodsMedicalInfoDo convertGoodsMedicalExternalRequestItemBoToGoodsMedicalInfoDo(GoodsMedicalExternalRequestItemBo bo) {
        GoodsMedicalInfoDo goodsMedicalInfoDo =new GoodsMedicalInfoDo();
        FieldsUtil.assign(bo,goodsMedicalInfoDo);
        if (BaseConstant.EXTERNAL_ITEM_STATE_DELETE.equals(bo.getState())) {
            goodsMedicalInfoDo.setIsDelete(DelFlag.DISABLE_VALUE);
        }

        return goodsMedicalInfoDo;
    }
}
