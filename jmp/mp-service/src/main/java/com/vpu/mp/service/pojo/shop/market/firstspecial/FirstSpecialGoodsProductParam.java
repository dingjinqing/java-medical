package com.vpu.mp.service.pojo.shop.market.firstspecial;

import java.math.BigDecimal;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.market.firstspecial.validated.FirstSpecialUpdateValidatedGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: 王兵兵
 * @create: 2019-08-16 18:01
 **/
@Data
public class FirstSpecialGoodsProductParam {

    /** b2c_first_special_product主键 */
    @NotNull(groups = {FirstSpecialUpdateValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
    private Integer id;

    /** 规格ID */
    private Integer productId;

    /** 修改后的规格价 */
    @NotNull(groups = {FirstSpecialUpdateValidatedGroup.class},message = JsonResultMessage.MSG_PARAM_ERROR)
    private BigDecimal prdPrice;
}
