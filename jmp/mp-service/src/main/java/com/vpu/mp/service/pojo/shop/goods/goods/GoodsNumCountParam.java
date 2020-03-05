package com.vpu.mp.service.pojo.shop.goods.goods;

import lombok.Data;

/**
 * 统计商品数量
 * @author 李晓冰
 * @date 2020年01月15日
 */
@Data
public class GoodsNumCountParam {
    private Integer catId;
    private Integer sortId;
    private Integer labelId;
    private Integer brandId;
}
