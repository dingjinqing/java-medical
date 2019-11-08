package com.vpu.mp.service.pojo.wxapp.goods.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李晓冰
 * @date 2019年10月14日
 * 小程序商品模块标签返回实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsLabelMpVo {
    private String name;
    private Short listPattern;
}
