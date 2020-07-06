package com.vpu.mp.service.pojo.shop.label.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Data
@NoArgsConstructor
public class GoodsLabelCoupleVal {
    private Integer labelId;
    private Integer gtaId;
    private Byte type;



    public GoodsLabelCoupleVal(Integer labelId, Integer gtaId, Byte type) {
        this.labelId = labelId;
        this.gtaId = gtaId;
        this.type = type;
    }

    public static List<GoodsLabelCoupleVal> generateCouples(List<Integer> labelIds, Integer gtaId, Byte type) {
         List<GoodsLabelCoupleVal> labelCouples = new ArrayList<>(labelIds.size());

        for (Integer labelId : labelIds) {
            labelCouples.add(new GoodsLabelCoupleVal(labelId,gtaId,type));
        }
        return labelCouples;
    }
}
