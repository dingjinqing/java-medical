package com.vpu.mp.service.pojo.wxapp.store;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

/**
 * @author liufei
 * @date 10/18/19
 */
@Data
public class StoreInfoParam {
    @PositiveOrZero
    @JsonAlias({"store_id", "storeId"})
    public Integer storeId;
    public Integer userId;
    /**
     * The Order info.订单信息
     * "{"store_id":"55","card_no":"47481606403254132","order_amount":"0.01","card_dis":"0.01","invoice":62,"score_dis":0,"total_price":"0.00",
     * "openid":"o-2MM5ANXgJHG_NBG5G-WX-KPjKI","form_id":"the formId is a mock one"}"
     */
    public String orderInfo;
}
