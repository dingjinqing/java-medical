package com.vpu.mp.service.pojo.shop.goods.goods;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品和其下属规格
 * @author: 王兵兵
 * @create: 2019-09-19 17:16
 **/
@Data
public class GoodsAndProductsVo {
    private Integer goodsId;
    private String goodsName;
    /** 商品主图 */
    private String goodsImg;
    /** 商品库存 */
    private Integer goodsNumber;
    /** 商品价格 */
    private BigDecimal shopPrice;
    /** 商品所有下属规格 */
    private List<GoodsProductVo> products;

}
