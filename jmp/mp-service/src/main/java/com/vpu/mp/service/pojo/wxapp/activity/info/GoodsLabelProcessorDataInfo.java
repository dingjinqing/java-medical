package com.vpu.mp.service.pojo.wxapp.activity.info;

import com.vpu.mp.service.pojo.wxapp.goods.goods.list.GoodsLabelMpVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 李晓冰
 * @date 2019年11月04日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsLabelProcessorDataInfo extends ProcessorDataInfo {
    private String name;
    private Short listPattern;

    public GoodsLabelMpVo convertToGoodsLabelMpVo(){
        GoodsLabelMpVo vo =new GoodsLabelMpVo();
        vo.setName(this.name);
        vo.setListPattern(this.listPattern);
        return vo;
    }
}
