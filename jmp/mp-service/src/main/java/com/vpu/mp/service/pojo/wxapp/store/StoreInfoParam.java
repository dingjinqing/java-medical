package com.vpu.mp.service.pojo.wxapp.store;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * @author liufei
 * @date 10/18/19
 */
@Data
@ScriptAssert(lang = "javascript", script = "_this.userId == null || _this.userId >= 0", message = "userId可空,但上传时必须为大于等于0的整数")
public class StoreInfoParam {
    @PositiveOrZero
    @JsonAlias({"store_id", "storeId"})
    public Integer storeId;
    @NotNull(groups = {StorePayOrder.class})
    public Integer userId;

    // 以下字段为门店买单所需入参
    /**
     * The Order info.订单信息
     * "{"store_id":"55","card_no":"47481606403254132","order_amount":"0.01","card_dis":"0.01","invoice":62,"score_dis":0,"total_price":"0.00",
     * "openid":"o-2MM5ANXgJHG_NBG5G-WX-KPjKI","form_id":"the formId is a mock one"}"
     */
    @Valid
    @NotNull(groups = {StoreConfirmPay.class})
    public StorePayOrderInfo orderInfo;
    /**
     * The Applet request source.小程序请求来源; 0:微信小程序 ,1:支付宝小程序...
     */
    public Byte appletRequestSource;

    /**
     * 客户端ip
     */
    private String clientIp;
}
