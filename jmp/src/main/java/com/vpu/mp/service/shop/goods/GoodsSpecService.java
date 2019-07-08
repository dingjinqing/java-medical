package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.shop.tables.records.SpecRecord;
import com.vpu.mp.db.shop.tables.records.SpecValsRecord;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpec;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecVal;
import org.jooq.impl.DefaultDSLContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.Tables.SPEC;
import static com.vpu.mp.db.shop.Tables.SPEC_VALS;

/**
 * @author 李晓冰
 * @date 2019年07月05日
 */
public class GoodsSpecService {

    static final String PRD_SPEC_ID_KEY="specId";//规格名值处理后map内id值的key名称

    /**
     * 	此服务目前只由goodsService使用，所以没有被ServiceContainer管理
     * @param db 由调用者传入db进行事务处理
     * @param goodsSpecs 内部包含了对应的specsVal值
     * @param goodsId
     */
    protected void insertSpecAndSpecVal(DefaultDSLContext db, List<GoodsSpec> goodsSpecs, Integer goodsId){
        for (GoodsSpec goodsSpec : goodsSpecs) {
            goodsSpec.setGoodsId(goodsId);
            SpecRecord specRecord = db.newRecord(SPEC, goodsSpec);
            specRecord.insert();

            goodsSpec.setSpecId(specRecord.getSpecId());
            
            for (GoodsSpecVal val : goodsSpec.getGoodsSpecVals()) {
                val.setGoodsId(goodsId);
                val.setSpecId(specRecord.getSpecId());
                SpecValsRecord specValsRecord = db.newRecord(SPEC_VALS, val);
                specValsRecord.insert();
                val.setSpecValId(specValsRecord.getSpecValId());
            }
        }
    }

    /**
     * 	在插入数据的基础上返回一个预处理的Map对象，供使用者快速通过规格名称字符串获取对应记录的id值。
     * @param db
     * @param goodsSpecs
     * @param goodsId
     * @return
     */
    protected Map<String, Map<String,Integer>> insertSpecAndSpecValWithPrepareResult(DefaultDSLContext db, List<GoodsSpec> goodsSpecs, Integer goodsId){
        insertSpecAndSpecVal(db,goodsSpecs,goodsId);
        Map<String, Map<String, Integer>> resultMap = prepareResultMap(goodsSpecs);
        return resultMap;
    }

    /**
     * 	预处理规格名和规格值，便于通过规格名称或规格值来获得对应记录在数据库中的唯一值。
     * @param goodsSpecs
     * @return
     */
    private   Map<String, Map<String,Integer>> prepareResultMap(List<GoodsSpec> goodsSpecs){
        // 产生 规格属性预处理数据
        Map<String, Map<String,Integer>> goodsSpecsMap = new HashMap<>();
        for (GoodsSpec goodsSpec : goodsSpecs) {

            Map<String,Integer> goodsSpecsValMap = new HashMap<>();
            goodsSpecsValMap.put(PRD_SPEC_ID_KEY, goodsSpec.getSpecId());

            for (GoodsSpecVal goodsSpecVal : goodsSpec.getGoodsSpecVals()) {
                goodsSpecsValMap.put(goodsSpecVal.getSpecValName(), goodsSpecVal.getSpecValId());
            }

            goodsSpecsMap.put(goodsSpec.getSpecName(), goodsSpecsValMap);
        }

        return goodsSpecsMap;
    }
}
