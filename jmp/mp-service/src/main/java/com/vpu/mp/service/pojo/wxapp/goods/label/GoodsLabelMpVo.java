package com.vpu.mp.service.pojo.wxapp.goods.label;

import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabel;
import lombok.Data;

/**
 * 商品标签
 * @author 李晓冰
 * @date 2019年12月09日
 */
public class GoodsLabelMpVo {
    private Integer labelId;
    private String labelName;

    public GoodsLabelMpVo(EsGoodsLabel esGoodsLabel){
        this.labelId = esGoodsLabel.getId();
        this.labelName = esGoodsLabel.getName();
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
