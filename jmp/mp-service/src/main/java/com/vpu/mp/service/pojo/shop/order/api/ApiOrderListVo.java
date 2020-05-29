package com.vpu.mp.service.pojo.shop.order.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.vpu.mp.service.pojo.shop.order.invoice.InvoiceVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 王帅
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ApiOrderListVo {
    private Integer orderId;
    private String orderSn;
    private Byte orderStatus;
    private String consignee;
    private String countryName;
    private String provinceName;
    private String cityName;
    private String districtName;
    private String address;
    private String zipcode;
    private String completeAddress;
    private String mobile;
    private Integer goodsAmount;
    private BigDecimal discount;
    private BigDecimal useAccount;
    private BigDecimal scoreDiscount;
    private Byte isCod;
    private BigDecimal moneyPaid;
    private BigDecimal shippingFee;
    private Byte refundStatus;
    private String addMessage;
    private Byte shippingId;
    @JsonProperty("shipping_name")
    private String shippingName;
    private String shippingNo;
    private Timestamp shippingTime;
    private Byte deliverType;
    private BigDecimal promotionReduce;
    private BigDecimal memberCardReduce;
    private BigDecimal memberCardBalance;
    private BigDecimal discountAmount;
    /**
     * 用户手机号昵称
     */
    private String username;
    private String orderRealName;
    private String orderCid;
    private String consigneeRealName;
    private String consigneeCid;
    private Byte invoiceType;
    @JsonIgnore
    private Integer invoiceContent;
    @JsonIgnore
    private String invoiceTitle;
    private InvoiceVo invoiceInfo;
    @JsonProperty("order_goods_list")
    private List<?> orderGoodsInfo;
    @JsonProperty("return_info")
    private List<?> returnInfo;
}
