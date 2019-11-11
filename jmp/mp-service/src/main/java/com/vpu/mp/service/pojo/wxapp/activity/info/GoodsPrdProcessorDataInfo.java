package com.vpu.mp.service.pojo.wxapp.activity.info;

import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2019年11月04日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsPrdProcessorDataInfo extends ProcessorDataInfo {
    /*******小程序商品列表********/
    /**是否是默认规格0否1是*/
    private Boolean defaultPrd;
    /**最小值*/
    private BigDecimal minPrice;
    /**最大值*/
    private BigDecimal maxPrice;

    /******商品详情处使用********/
    private Integer prdId;
    private Integer prdNumber;
    private BigDecimal prdPrice;
    private BigDecimal prdRealPrice;
    private BigDecimal prdLinePrice;
    private BigDecimal prdMarketPrice;
    private String prdSpecs;
    private String prdDesc;
    private String prdImg;

    public GoodsPrdMpVo convertToGoodsPrdMpVo(){
        GoodsPrdMpVo vo = new GoodsPrdMpVo();
        vo.setPrdId(this.prdId);
        vo.setPrdNumber(this.prdNumber);
        vo.setPrdRealPrice(this.prdRealPrice);
        vo.setPrdLinePrice(this.prdLinePrice);
        vo.setPrdSpecs(this.prdSpecs);
        vo.setPrdDesc(this.prdDesc);
        vo.setPrdImg(this.prdImg);
        return vo;
    }
}
