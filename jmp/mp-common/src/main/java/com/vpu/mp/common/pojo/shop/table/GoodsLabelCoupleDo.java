package com.vpu.mp.common.pojo.shop.table;

import lombok.Data;

/**
 * 标签关联表
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Data
public class GoodsLabelCoupleDo {
    private Integer labelId;
    private Integer gtaId;
    private Byte type;
}
