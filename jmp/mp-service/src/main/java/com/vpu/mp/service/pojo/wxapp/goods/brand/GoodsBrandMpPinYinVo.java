package com.vpu.mp.service.pojo.wxapp.goods.brand;

import lombok.Data;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月17日
 */
@Data
public class GoodsBrandMpPinYinVo {
    private String character;
    private List<GoodsBrandMpVo> goodsBrands;
}
