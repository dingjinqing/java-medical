package com.vpu.mp.service.pojo.wxapp.goods.label;

import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabel;

/**
 * 商品标签
 * @author 李晓冰
 * @date 2019年12月09日
 */
public class GoodsLabelMpVo {
    private Integer id;
    private String name;

    public GoodsLabelMpVo() {
    }

    public GoodsLabelMpVo(EsGoodsLabel esGoodsLabel){
        this.id = esGoodsLabel.getId();
        this.name = esGoodsLabel.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
