package com.vpu.mp.service.pojo.shop.goods.sort;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import lombok.Data;

/**
 * @author 李晓冰
 * @date 2019年11月21日
 */
@Data
public class GoodsNormalSortUpdateParam{
    private Integer sortId;
    private String sortName;
    private Integer parentId;
    private Integer oldParentId;
    private Short level;
    private String sortImg;
    private String imgLink;
    private Short first;
    private Byte type  = GoodsConstant.NORMAL_SORT;
}
