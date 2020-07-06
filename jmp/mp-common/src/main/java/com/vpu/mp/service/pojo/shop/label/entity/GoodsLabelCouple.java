package com.vpu.mp.service.pojo.shop.label.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Data
@NoArgsConstructor
public class GoodsLabelCouple {
    private Integer labelId;
    private Integer gtaId;
    private Byte type;



    public GoodsLabelCouple(Integer labelId, Integer gtaId, Byte type) {
        this.labelId = labelId;
        this.gtaId = gtaId;
        this.type = type;
    }
}
