package com.vpu.mp.service.pojo.wxapp.activity.info;

import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会员专享信息
 * @author 李晓冰
 * @date 2019年10月31日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExclusiveProcessorDataInfo extends ProcessorDataInfo {
    public ExclusiveProcessorDataInfo() {
        dataType = GoodsConstant.ACTIVITY_TYPE_MEMBER_EXCLUSIVE;
    }

    public ExclusiveProcessorDataInfo(Integer gctaId, Byte type){
        this();
        this.gctaId = gctaId;
        this.type = type;
    }

    private Integer gctaId;
    private Byte type;
}
