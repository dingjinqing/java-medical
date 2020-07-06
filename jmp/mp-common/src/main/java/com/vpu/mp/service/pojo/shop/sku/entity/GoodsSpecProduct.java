package com.vpu.mp.service.pojo.shop.sku.entity;

import cn.hutool.core.util.StrUtil;
import com.vpu.mp.service.pojo.shop.goods.MedicalGoodsConstant;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Data
public class GoodsSpecProduct {
    private Integer prdId;
    private Integer goodsId;
    private String prdSn;
    private BigDecimal prdPrice;
    private BigDecimal prdMarketPrice;
    private BigDecimal prdCostPrice;
    private Integer prdNumber;
    private String prdCodes;
    private String prdSpecs;
    private String prdDesc;
    private String prdImg;
    private BigDecimal prdWeight;
    private Byte delFlag;
    private Timestamp createTime;

    public void calculatePrdSpecs(Map<String, Spec> specNameMap) {
        // 没有规格描述可以使用
        if (StrUtil.isBlank(prdDesc)) {
            return;
        }
        String[] specKVs = prdDesc.split(MedicalGoodsConstant.PRD_DESC_SPLITER);
        if (specKVs.length < 1) {
            throw new IllegalArgumentException("规格组描述信息错误："+prdDesc);
        }


        for (String specKV : specKVs) {
            String[] split = specKV.split(MedicalGoodsConstant.SPEC_NAME_VAL_SPLITER);
            if (split.length != 2) {
                throw new IllegalArgumentException(String.format("规格组%s名值信息错误：%s",prdDesc,specKV));
            }

        }

    }
}
