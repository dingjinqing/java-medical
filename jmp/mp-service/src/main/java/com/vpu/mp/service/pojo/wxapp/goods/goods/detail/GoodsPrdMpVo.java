package com.vpu.mp.service.pojo.wxapp.goods.goods.detail;

import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品规格
 * @author 李晓冰
 * @date 2019年11月08日
 */
@Data
public class GoodsPrdMpVo {
    private Integer prdId;
    private Integer prdNumber;
    private BigDecimal prdRealPrice;
    private BigDecimal prdLinePrice;
    private String prdSpecs;
    private String prdDesc;
    private String prdImg;

    public GoodsPrdMpVo() {
    }

    public GoodsPrdMpVo(GoodsSpecProductRecord record) {
        this.prdId = record.getPrdId();
        this.prdNumber = record.getPrdNumber();
        this.prdRealPrice = record.getPrdPrice();
        this.prdLinePrice = record.getPrdMarketPrice();
        this.prdSpecs = record.getPrdSpecs();
        this.prdDesc = record.getPrdDesc();
        this.prdImg = record.getPrdImg();
    }
}
