package com.vpu.mp.service.pojo.shop.goods.sort;

import lombok.Data;

/**
 * @author 李晓冰
 * @date 2019年11月21日
 */
@Data
public class GoodsNormalSortVo {
    private Integer sortId;
    private String sortName;
    private Integer parentId;
    private Short level;
}
