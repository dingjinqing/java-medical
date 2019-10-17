package com.vpu.mp.service.pojo.wxapp.cart;

import lombok.Data;
import reactor.util.annotation.NonNull;

/**
 *  删除购物车商品参数
 * @author 孔德成
 * @date 2019/10/17 11:04
 */
@Data
public class WxAppRemoveCartProductParam {

    private Integer userId;
    @NonNull
    private Integer recId;
}
