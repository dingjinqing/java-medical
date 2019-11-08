package com.vpu.mp.service.pojo.wxapp.goods.goods;

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
}
