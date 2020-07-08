package com.vpu.mp.common.pojo.shop.table.goods;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年07月08日
 */
@Data
public class GoodsPageListCondition {

    private List<Integer> goodsIdsLimit;

    private String goodsName;
    private String goodsSn;
    private Integer brandId;
    private Integer sortId;
    /**
     * 在查询规格时该字段表示对规格价格进行过滤
     */
    private BigDecimal lowShopPrice;
    private BigDecimal highShopPrice;
    private Byte isOnSale;
    private Byte isSaleOut;

    /**药品信息*/
    private Byte isMedical;

    private List<GoodsSortItem> pageSortItems;
}
