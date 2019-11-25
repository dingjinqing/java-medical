package com.vpu.mp.service.pojo.shop.goods.goods;

import com.vpu.mp.service.pojo.shop.goods.sort.GoodsSortSelectTreeVo;
import lombok.Data;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年11月22日
 */
@Data
public class GoodsFilterItemInitVo {
    private List<GoodsSortSelectTreeVo> goodsSorts;
}
