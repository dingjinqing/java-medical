package com.vpu.mp.service.pojo.shop.config.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * @Author:liufei
 * @Date:2019/7/10
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetrunConfigParam {
    @JsonProperty(value = "auto_return")
    public Byte autoReturn;
    @Pattern(regexp = "")
    @JsonProperty(value = "auto_return_time")
    public Byte autoReturnTime;
    @JsonProperty(value = "return_money_days")
    public Byte returnMoneyDays;
    @JsonProperty(value = "return_address_days")
    public Byte returnAddressDays;
    @JsonProperty(value = "return_shopping_days")
    public Byte returnShoppingDays;
    @JsonProperty(value = "is_refund_coupon")
    public Byte isReturnCoupon;
    @JsonProperty(value = "business_adress")
    public ReturnBusinessAdressParam businessAddress;
    @JsonProperty(value = "return_change_goods_status")
    public Byte returnChangeGoodsStatus;
    @JsonProperty(value = "order_return_goods_package")
    public ReturnPackageParam orderReturnGoodsPackage;
}
