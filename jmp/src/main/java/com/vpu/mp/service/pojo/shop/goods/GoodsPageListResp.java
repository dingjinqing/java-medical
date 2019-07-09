package com.vpu.mp.service.pojo.shop.goods;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCouple;

/**
 * @author 李晓冰
 * @date 2019年07月09日
 */
@Data
public class GoodsPageListResp {
    private Integer goodsId;
    private String goodsName;
    private BigDecimal shopPrice;
    private String goodsImg;
    private String goodsSn;
    private Short catId;
    private String catName;
    private String sortName;
    private String brandName;
    private Integer goodsNumber;
    private Integer goodsSaleNum;
    private List<GoodsLabelCouple> goodsLabelCouples;
}
