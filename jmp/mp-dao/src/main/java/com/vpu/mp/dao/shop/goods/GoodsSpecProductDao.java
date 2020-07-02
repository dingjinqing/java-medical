package com.vpu.mp.dao.shop.goods;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.goods.dao.GoodsSpecProductDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Repository
public class GoodsSpecProductDao extends ShopBaseDao {
    /**
     * assign方法基础忽略字段
     */
    Set<String> assignIgnoreFieldsBase = new HashSet<String>(){{
        add("specDos");
    }};

    /**
     * 商品sku新增
     * @param goodsSpecProductDo sku数据
     * @return sku id
     */
    public Integer insert(GoodsSpecProductDo goodsSpecProductDo){
        GoodsSpecProductRecord goodsSpecProductRecord = new GoodsSpecProductRecord();
        FieldsUtil.assignWithIgnoreField(goodsSpecProductRecord,goodsSpecProductDo,assignIgnoreFieldsBase);
        db().executeInsert(goodsSpecProductRecord);
        return goodsSpecProductRecord.getPrdId();
    }
}
