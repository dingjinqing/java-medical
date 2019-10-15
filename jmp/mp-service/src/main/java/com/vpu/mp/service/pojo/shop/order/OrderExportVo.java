package com.vpu.mp.service.pojo.shop.order;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.excel.annotation.ExcelColumn;
import com.vpu.mp.service.foundation.excel.annotation.ExcelSheet;
import lombok.Data;

/**
 * @author: 王兵兵
 * @create: 2019-10-15 11:27
 *
 * @description 订单导出所有可选的表头
 **/
@ExcelSheet
@Data
public class OrderExportVo {
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_ORDER_SN,columnIndex = 0)
    private String orderSn;  //订单号
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_ORDER_STATUS_NAME,columnIndex = 1)
    private String orderStatusName;  //订单状态
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_PAY_NAMES,columnIndex = 2)
    private String payNames;  //支付方式
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_ADD_TIME,columnIndex = 3)
    private String addTime;  //订单提交时间
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_PAY_TIME,columnIndex = 4)
    private String payTime;  //支付时间
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_CLOSE_TIME,columnIndex = 5)
    private String closedTime;  //订单关闭时间
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_CANCELLED_TIME,columnIndex = 6)
    private String cancelledTime;  //订单取消时间
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_FINISHED_TIME,columnIndex = 7)
    private String finishedTime;  //订单完成时间
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_IS_COD,columnIndex = 8)
    private String isCod;  //是否货到付款
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_CONSIGNEE,columnIndex = 9)
    private String consignee;  //收货人姓名
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_MOBILE,columnIndex = 10)
    private String mobile;  //手机号
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_COMPLETE_ADDRESS,columnIndex = 11)
    private String completeAddress;  //收货地址
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_PROVINCE_NAME,columnIndex = 12)
    private String provinceName;  //收货省份
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_CITY_NAME,columnIndex = 13)
    private String cityName;  //收货城市
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_DISTRICT_NAME,columnIndex = 14)
    private String districtName;  //收货地区
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_ZIPCODE,columnIndex = 15)
    private String zipcode;  //邮政编码
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_USER_NAME,columnIndex = 16)
    private String userName;  //下单人姓名
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_USER_MOBILE,columnIndex = 17)
    private String userMobile;  //下单人手机号
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_IS_NEW,columnIndex = 18)
    private String isNew;  //新老用户
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_USER_SOURCE,columnIndex = 19)
    private String userSource;  //下单人来源
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_USER_TAG,columnIndex = 20)
    private String userTag;  //下单人标签
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_ADD_MESSAGE,columnIndex = 21)
    private String addMessage;  //下单人留言
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_SHIPPING_TIME,columnIndex = 22)
    private String shippingTime;  //发货时间
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_SHIPPING_NAME,columnIndex = 23)
    private String shippingName;  //货运名称
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_SHIPPING_NO,columnIndex = 24)
    private String shippingNo;  //物流单号
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_DELIVER_TYPE_NAME,columnIndex = 25)
    private String deliverTypeName;  //配送类型
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_CONFIRM_TIME,columnIndex = 26)
    private String confirmTime;  //确认收货时间
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_STORE_ID,columnIndex = 27)
    private String storeId;  //门店ID
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_STORE_NAME,columnIndex = 28)
    private String storeName;  //门店名称
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_GOODS_NAME,columnIndex = 29)
    private String goodsName;  //商品名称
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_PRODUCT_SN,columnIndex = 30)
    private String productSn;  //商家编码
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_GOODS_NUMBER,columnIndex = 31)
    private String goodsNumber;  //商品数量
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_DISCOUNTED_GOODS_PRICE,columnIndex = 32)
    private String discountedGoodsPrice;  //实际售价
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_GOODS_ATTR,columnIndex = 33)
    private String goodsAttr;  //SKU属性
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_GOODS_PRICE,columnIndex = 34)
    private String goodsPrice;  //商品售价
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_MARKET_PRICE,columnIndex = 35)
    private String marketPrice;  //商品市场价
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_GOODS_SN,columnIndex = 36)
    private String goodsSn;  //商品货号
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_GOODS_ID,columnIndex = 37)
    private String goodsId;  //商品ID
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_SEND_NUMBER,columnIndex = 38)
    private String sendNumber;  //已发货数量
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_RETURN_NUMBER,columnIndex = 39)
    private String returnNumber;  //退货数量
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_SOURCE,columnIndex = 40)
    private String source;  //商品来源
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_PRD_COST_PRICE,columnIndex = 41)
    private String prdCostPrice;  //成本价
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_PRD_WEIGHT,columnIndex = 42)
    private String prdWeight;  //SKU重量
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_ORDER_AMOUNT,columnIndex = 43)
    private String orderAmount;  //订单总金额
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_DISCOUNT,columnIndex = 44)
    private String discount;  //优惠券优惠金额
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_SHIPPING_FEE,columnIndex = 45)
    private String shippingFee;  //邮费
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_SCORE_DISCOUNT,columnIndex = 46)
    private String scoreDiscount;  //积分抵扣金额
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_USE_ACCOUNT,columnIndex = 47)
    private String useAccount;  //使用账户余额
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_MONEY_PAID,columnIndex = 48)
    private String moneyPaid;  //微信支付金额
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_MEMBER_CARD_BALANCE,columnIndex = 49)
    private String memberCardBalance;  //使用会员卡余额
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_MEMBER_CARD_REDUCE,columnIndex = 50)
    private String memberCardReduce;  //会员卡抵扣金额
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_PROMOTION_REDUCE,columnIndex = 51)
    private String promotionReduce;  //满折满减优惠金额
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_RETURN_TIME,columnIndex = 52)
    private String returnTime;  //申请退货时间
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_RETURN_FINISH_TIME,columnIndex = 53)
    private String returnFinishTime;  //退货完成时间
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_RETURN_ORDER_MONEY,columnIndex = 54)
    private String returnOrderMoney;  //退款金额
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_RETURN_SHIPPING_FEE,columnIndex = 55)
    private String returnShippingFee;  //退运费金额
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_SELLER_REMARK,columnIndex = 56)
    private String sellerRemark;  //卖家备注
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_ORDER_REAL_NAME,columnIndex = 57)
    private String orderRealName;  //真实姓名
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_ORDER_CID,columnIndex = 58)
    private String orderCid;  //身份证号
    @ExcelColumn(columnName = JsonResultMessage.ORDER_EXPORT_COLUMN_CUSTOM,columnIndex = 59)
    private String custom;  //自定义下单必填信息

}
