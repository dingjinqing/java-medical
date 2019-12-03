package com.vpu.mp.service.pojo.shop.goods.label;




/**
 * 商品标签下拉列表vo
 * @author 李晓冰
 * @date 2019年11月25日
 */

public class GoodsLabelSelectListVo {
    private Integer id;
    private String name;

    public GoodsLabelSelectListVo() {
    }

    public GoodsLabelSelectListVo(Integer id, String name){
        this.id = id;
        this.name = name;
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
