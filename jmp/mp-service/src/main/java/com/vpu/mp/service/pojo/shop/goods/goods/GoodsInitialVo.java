package com.vpu.mp.service.pojo.shop.goods.goods;

import com.vpu.mp.service.pojo.saas.category.SysCatevo;
import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrandSelectListVo;
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

    List<GoodsBrandSelectListVo> goodsBrands;

    List<GoodsLabel> goodsLabels;

    List<Sort> goodsSorts;

    List<SysCatevo> sysCates;
}
