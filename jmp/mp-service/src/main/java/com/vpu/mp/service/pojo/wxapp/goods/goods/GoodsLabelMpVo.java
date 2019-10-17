package com.vpu.mp.service.pojo.wxapp.goods.goods;

import lombok.Data;

/**
 * @author 李晓冰
 * @date 2019年10月14日
 * 小程序商品模块标签返回实体
 */
@Data
public class GoodsLabelMpVo {
    private Integer labelLen;
    private String name;
    private Integer listPattern;
    private Byte gtaId;
}
