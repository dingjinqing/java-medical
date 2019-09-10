package com.vpu.mp.service.pojo.shop.goods.goods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelListVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年07月16日
 */
@Getter
@Setter
public class GoodsVo extends Goods {

    @JsonIgnore
    private List<Integer> goodsLabels;
    private List<GoodsLabelListVo> goodsLabelListVos;
    private String brandName;
    private String sortName;
    private String goodsPageName;
    private String goodsImgPath;
    private List<String> goodsImgsPath;

    @Override
    public List<Integer> getGoodsLabels() {
        return goodsLabels;
    }

    @Override
    public void setGoodsLabels(List<Integer> goodsLabels) {
        this.goodsLabels = goodsLabels;
    }
}
