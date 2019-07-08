package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpecProduct;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;

/**
 * @author 李晓冰
 * @date 2019年07月05日
 */
public class GoodsSpecProductService {

    static final String PRD_DESC_DELIMITER=";";//规格名值描述分割符
    static final String PRD_SPEC_DELIMITER="!!";//规格名值id分隔符
    static final String PRD_VAL_DELIMITER=":";//规格名和值分隔符

    static final String PRD_SPEC_ID_KEY=GoodsSpecService.PRD_SPEC_ID_KEY;//规格名值处理后map内id值的key名称

    protected void insert(DSLContext db, GoodsSpecProduct goodsSpecProduct){
        if (goodsSpecProduct.getPrdSn() == null) {
            goodsSpecProduct.setPrdSn(Util.UUID());
        }

        GoodsSpecProductRecord goodsSpecProductRecord = db.newRecord(GOODS_SPEC_PRODUCT, goodsSpecProduct);
        goodsSpecProductRecord.insert();
    }

    protected void insert(DSLContext db, List<GoodsSpecProduct> goodsSpecProducts, Map<String, Map<String, Integer>> goodsSpecsMap, Integer goodsId) {
        for (GoodsSpecProduct goodsSpecProduct : goodsSpecProducts) {
            goodsSpecProduct.setGoodsId(goodsId);

            if (goodsSpecProduct.getPrdSn() == null) {
                goodsSpecProduct.setPrdSn(Util.UUID());
            }

            String prdDescs = goodsSpecProduct.getPrdDesc();

            if (prdDescs != null && prdDescs.length() > 0) {

                StringBuilder sb = new StringBuilder();

                for (String prdDesc : prdDescs.split(PRD_DESC_DELIMITER)) {
                    String[] s = prdDesc.split(PRD_VAL_DELIMITER);

                    if (s == null || s.length < 2) {//安全检查，防止出现空指针，主要针对他人恶意接口调用。
                        continue;
                    }
                    String spec = s[0], specVal = s[1];
                    if (sb.length() != 0) {
                        sb.append(PRD_SPEC_DELIMITER);
                    }

                    Map<String, Integer> nameIdMap = goodsSpecsMap.get(spec);
                    sb.append(nameIdMap.get(PRD_SPEC_ID_KEY)).append(PRD_VAL_DELIMITER).append(nameIdMap.get(specVal));
                }

                goodsSpecProduct.setPrdSpecs(sb.toString());
            }

            GoodsSpecProductRecord goodsSpecProductRecord = db.newRecord(GOODS_SPEC_PRODUCT, goodsSpecProduct);
            goodsSpecProductRecord.insert();
        }
    }
}
