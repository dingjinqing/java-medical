package com.vpu.mp.service.pojo.wxapp.cart;

import lombok.Data;


import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/11/8 16:30
 */
@Data
public class WxAppRemoveCartProductsParam {

    @NotNull
    private List<Integer> recIds;
}
