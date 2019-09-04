package com.vpu.mp.service.pojo.shop.config.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author:liufei
 * @Date:2019/7/11
 * @Description:
 */
@Data
public class ReturnBusinessAddressParam {
    /**
     * The Consignee.收件人
     */
    @JsonProperty(value = "consignee")
    private String consignee;
    /**
     * The Merchant telephone.收件电话
     */
    @JsonProperty(value = "merchant_telephone")
    private String merchantTelephone;
    /**
     * The Zip code.邮编
     */
    @JsonProperty(value = "zip_code")
    private String zipCode;
    /**
     * The Return address.退货地址
     */
    @JsonProperty(value = "return_address")
    private String returnAddress;
}
