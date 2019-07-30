package com.vpu.mp.service.pojo.shop.goods;

import com.vpu.mp.service.pojo.saas.category.SysCatevo;
import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrandVo;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabel;
import com.vpu.mp.service.pojo.shop.goods.sort.Sort;
import lombok.Data;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年07月29日
 */
@Data
public class GoodsInitialVo {

    List<GoodsBrandVo> goodsBrands;

    List<GoodsLabel> goodsLabels;

    List<Sort> goodsSorts;

    List<SysCatevo> sysCates;
}
