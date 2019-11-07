package com.vpu.mp.service.pojo.wxapp.activity.param;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品详情处理器参数
 * @author 李晓冰
 * @date 2019年11月07日
 */
@Getter
@Setter
public class GoodsDetailCapsuleParam extends GoodsBaseCapsuleParam{
    private Integer userId;
    public Integer goodsId;
    public Integer catId;
    public Integer sortId;
}
