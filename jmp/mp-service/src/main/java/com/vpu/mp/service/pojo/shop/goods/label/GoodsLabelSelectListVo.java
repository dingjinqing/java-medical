package com.vpu.mp.service.pojo.shop.goods.label;

import com.vpu.mp.db.shop.tables.records.GoodsLabelRecord;
import lombok.Data;


/**
 * 商品标签下拉列表vo
 * @author 李晓冰
 * @date 2019年11月25日
 */
@Data
public class GoodsLabelSelectListVo {
    private Integer id;
    private String name;
}
