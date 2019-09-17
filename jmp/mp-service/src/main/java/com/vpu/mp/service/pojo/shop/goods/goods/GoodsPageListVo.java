package com.vpu.mp.service.pojo.shop.goods.goods;

import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelListVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年07月09日
 */
@Data
public class GoodsPageListVo {
    private Integer goodsId;
    private String goodsName;
    private BigDecimal shopPrice;
    private Byte source;
    private Byte goodsType;
    private String goodsImg;
    private String goodsSn;
    private Short catId;
    private String catName;
    private String sortName;
    private Integer sortId;
    private String brandName;
    private Integer goodsNumber;
    private Integer goodsSaleNum;
    private List<GoodsLabelListVo> goodsLabels;
    /**
     * 商品为默认规格时该值为规格id,否则为空
     */
    private Integer prdId;
    private String prdImg;
    private String prdDesc;
}
