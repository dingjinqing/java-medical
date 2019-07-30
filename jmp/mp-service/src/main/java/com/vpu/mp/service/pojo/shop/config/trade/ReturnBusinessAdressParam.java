package com.vpu.mp.service.pojo.shop.config.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author:liufei
 * @Date:2019/7/11
 * @Description:
 */
@Data
public class ReturnBusinessAdressParam {
    @JsonProperty(value = "consignee")
    private String consignee;
    @JsonProperty(value = "merchant_telephone")
    private String merchantTelephone;
    @JsonProperty(value = "zip_code")
    private String zipCode;
    @JsonProperty(value = "return_address")
    private String returnAddress;
}
