package com.vpu.mp.service.pojo.shop.goods.goods;

import lombok.Data;

import java.util.List;

/**
 * 订单批量更新商品，规格库存和销量参数
 * @author 李晓冰
 * @date 2019年11月18日
 */
@Data
public class BatchUpdateGoodsNumAndSaleNumForOrderParam {
    private Integer goodsId;
    private Integer goodsNum;
    private Integer saleNum;
    private List<ProductNumInfo> productsInfo;
    @Data
    public class ProductNumInfo{
        private Integer prdId;
        private Integer prdNum;
    }
}
