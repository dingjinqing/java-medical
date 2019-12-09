package com.vpu.mp.service.pojo.wxapp.cart;

import com.vpu.mp.service.foundation.validator.ListValid;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/11/8 16:30
 */
@Getter
@Setter
public class WxAppRemoveCartProductsParam {


    @ListValid(min = 1)
    private List<Integer> recIds;
}
