package com.vpu.mp.dao.shop.goods.repository;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.GoodsSpecProductDo;
import com.vpu.mp.common.pojo.shop.table.SpecDo;
import com.vpu.mp.common.pojo.shop.table.SpecValDo;
import com.vpu.mp.dao.shop.goods.GoodsSpecProductDao;
import com.vpu.mp.dao.shop.goods.SpecDao;
import com.vpu.mp.dao.shop.goods.SpecValDao;
import com.vpu.mp.service.pojo.shop.sku.entity.GoodsSpecProduct;
import com.vpu.mp.service.pojo.shop.sku.entity.Spec;
import com.vpu.mp.service.pojo.shop.sku.entity.SpecVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.BinaryClient;

import javax.swing.plaf.SeparatorUI;
import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Repository
public class GoodsSpecProductRepository {
    @Autowired
    GoodsSpecProductDao goodsSpecProductDao;
    @Autowired
    SpecDao specDao;
    @Autowired
    SpecValDao specValDao;

    /**
     * 获取GoodsSpecProduct转GoodsSpecProductDo时忽略的字段
     * @return 需要忽略的字段
     */
    private static Set<String> getSkuAssignIgnoreFields(){
        Set<String> assignIgnoreField = new HashSet<>(1);
        assignIgnoreField.add("specs");
        return assignIgnoreField;
    }

    /**
     * 获取GoodsSpecProduct转GoodsSpecProductDo时忽略的字段
     * @return 需要忽略的字段
     */
    private static Set<String> getSpecAssignIgnoreFields(){
        Set<String> assignIgnoreField = new HashSet<>(1);
        assignIgnoreField.add("specVals");
        return assignIgnoreField;
    }

    /**
     * 插入sku
     * @param goodsSpecProducts
     */
    public void batchSkuInsert(List<GoodsSpecProduct> goodsSpecProducts,Integer goodsId){
        List<GoodsSpecProductDo> goodsSpecProductDos = new ArrayList<>(goodsSpecProducts.size());

        for (GoodsSpecProduct goodsSpecProduct : goodsSpecProducts) {
            goodsSpecProduct.setGoodsId(goodsId);
            GoodsSpecProductDo goodsSpecProductDo = new GoodsSpecProductDo();
            FieldsUtil.assignWithIgnoreField(goodsSpecProduct,goodsSpecProductDo,getSkuAssignIgnoreFields());
        }
        goodsSpecProductDao.batchInsert(goodsSpecProductDos);
    }

    /**
     * 批量插入规格组
     * @param specs 规格组
     */
    public void batchSpecInsert(List<Spec> specs,Integer goodsId) {
        List<SpecDo> specDos = new ArrayList<>(specs.size());

        Set<String> sepcAssignIgnoreFields = getSpecAssignIgnoreFields();
        for (Spec spec : specs) {
            spec.setGoodsId(goodsId);
            SpecDo specDo = new SpecDo();
            FieldsUtil.assignWithIgnoreField(spec,specDo,sepcAssignIgnoreFields);
            specDos.add(specDo);
        }
        specDao.batchInsert(specDos);

        List<SpecVal> specVals = new ArrayList<>(2);
        List<SpecValDo> specValDos = new ArrayList<>(2);
        // 设置specId，提取specVal准备入库
        for (int i = 0; i < specDos.size(); i++) {
            Spec spec = specs.get(i);
            spec.setSpecId(specDos.get(i).getSpecId());

            for (SpecVal specVal : spec.getSpecVals()) {
                specVals.add(specVal);
                specVal.setGoodsId(goodsId);
                specVal.setSpecId(spec.getSpecId());

                SpecValDo specValDo= new SpecValDo(spec.getSpecId(),goodsId,specVal.getSpecValName());
                specValDos.add(specValDo);
            }
        }

        specValDao.batchInsert(specValDos);
        for (int i = 0; i < specValDos.size(); i++) {
            specVals.get(i).setSpecValId(specValDos.get(i).getSpecValId());
        }
    }
}
