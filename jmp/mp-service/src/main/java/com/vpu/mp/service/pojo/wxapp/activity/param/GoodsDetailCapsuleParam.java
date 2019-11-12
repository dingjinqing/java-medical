package com.vpu.mp.service.pojo.wxapp.activity.param;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品详情处理器参数
 * @author 李晓冰
 * @date 2019年11月07日
 */
@Data
public class GoodsDetailCapsuleParam{
    private Integer userId;
    public Integer goodsId;
    public Integer catId;
    public Integer sortId;
    public Integer brandId;
}
