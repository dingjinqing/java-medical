/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.ReturnOrderBak;
import org.jooq.Record2;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * 退回订单表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReturnOrderBakRecord extends UpdatableRecordImpl<ReturnOrderBakRecord> {

    private static final long serialVersionUID = -293676874;

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.ret_id</code>.
     */
    public void setRetId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.ret_id</code>.
     */
    public Integer getRetId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.order_id</code>.
     */
    public void setOrderId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.order_id</code>.
     */
    public Integer getOrderId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.order_sn</code>.
     */
    public void setOrderSn(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.order_sn</code>.
     */
    public String getOrderSn() {
        return (String) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.return_order_sn</code>. 退款单号
     */
    public void setReturnOrderSn(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.return_order_sn</code>. 退款单号
     */
    public String getReturnOrderSn() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.shop_id</code>. 店铺id
     */
    public void setShopId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.shop_id</code>. 店铺id
     */
    public Integer getShopId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.goods_id</code>.
     */
    public void setGoodsId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.goods_id</code>.
     */
    public Integer getGoodsId() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.refund_status</code>. 1是审核中，2是通过审核，3退货没通过审核，4买家提交物流 或 仅退款申请，5：退款退货成功，6是拒绝退款退货,7 撤销退款、退货
     */
    public void setRefundStatus(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.refund_status</code>. 1是审核中，2是通过审核，3退货没通过审核，4买家提交物流 或 仅退款申请，5：退款退货成功，6是拒绝退款退货,7 撤销退款、退货
     */
    public Byte getRefundStatus() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.money</code>. 退款商品金额
     */
    public void setMoney(BigDecimal value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.money</code>. 退款商品金额
     */
    public BigDecimal getMoney() {
        return (BigDecimal) get(8);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.shipping_fee</code>. 退运费金额
     */
    public void setShippingFee(BigDecimal value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.shipping_fee</code>. 退运费金额
     */
    public BigDecimal getShippingFee() {
        return (BigDecimal) get(9);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.return_type</code>. 退款类型,0:只退款，1:退货又退款
     */
    public void setReturnType(Byte value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.return_type</code>. 退款类型,0:只退款，1:退货又退款
     */
    public Byte getReturnType() {
        return (Byte) get(10);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.reason_type</code>. 退款/退货原因类型，0：协商一致退款，1：未按约定时间发货，2：缺货，3：拍错/多拍/不想要，4：其他
     */
    public void setReasonType(Byte value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.reason_type</code>. 退款/退货原因类型，0：协商一致退款，1：未按约定时间发货，2：缺货，3：拍错/多拍/不想要，4：其他
     */
    public Byte getReasonType() {
        return (Byte) get(11);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.reason_desc</code>. 退款/退货描述
     */
    public void setReasonDesc(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.reason_desc</code>. 退款/退货描述
     */
    public String getReasonDesc() {
        return (String) get(12);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.shipping_type</code>. 快递类型
     */
    public void setShippingType(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.shipping_type</code>. 快递类型
     */
    public String getShippingType() {
        return (String) get(13);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.shipping_no</code>. 快递单号
     */
    public void setShippingNo(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.shipping_no</code>. 快递单号
     */
    public String getShippingNo() {
        return (String) get(14);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.goods_images</code>. 商品图片
     */
    public void setGoodsImages(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.goods_images</code>. 商品图片
     */
    public String getGoodsImages() {
        return (String) get(15);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.voucher_images</code>. 凭证图片
     */
    public void setVoucherImages(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.voucher_images</code>. 凭证图片
     */
    public String getVoucherImages() {
        return (String) get(16);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.phone</code>. 电话号码
     */
    public void setPhone(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.phone</code>. 电话号码
     */
    public String getPhone() {
        return (String) get(17);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.apply_time</code>. 退货且退货提交审核时间，对应refund_status=1
     */
    public void setApplyTime(Timestamp value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.apply_time</code>. 退货且退货提交审核时间，对应refund_status=1
     */
    public Timestamp getApplyTime() {
        return (Timestamp) get(18);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.apply_pass_time</code>. 审核通过时间，对应refund_status=2
     */
    public void setApplyPassTime(Timestamp value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.apply_pass_time</code>. 审核通过时间，对应refund_status=2
     */
    public Timestamp getApplyPassTime() {
        return (Timestamp) get(19);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.apply_not_pass_time</code>. 审核未通过时间，对应refund_status=3
     */
    public void setApplyNotPassTime(Timestamp value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.apply_not_pass_time</code>. 审核未通过时间，对应refund_status=3
     */
    public Timestamp getApplyNotPassTime() {
        return (Timestamp) get(20);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.shipping_or_refund_time</code>. 只退款时为退款申请时间，退货又退款时为提交物流信息时间，对应refund_status=4
     */
    public void setShippingOrRefundTime(Timestamp value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.shipping_or_refund_time</code>. 只退款时为退款申请时间，退货又退款时为提交物流信息时间，对应refund_status=4
     */
    public Timestamp getShippingOrRefundTime() {
        return (Timestamp) get(21);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.refund_success_time</code>. 退款成功时间，对应refund_status=5
     */
    public void setRefundSuccessTime(Timestamp value) {
        set(22, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.refund_success_time</code>. 退款成功时间，对应refund_status=5
     */
    public Timestamp getRefundSuccessTime() {
        return (Timestamp) get(22);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.refund_refuse_time</code>. 退款拒绝时间，对应refund_status=6
     */
    public void setRefundRefuseTime(Timestamp value) {
        set(23, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.refund_refuse_time</code>. 退款拒绝时间，对应refund_status=6
     */
    public Timestamp getRefundRefuseTime() {
        return (Timestamp) get(23);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.refund_cancel_time</code>. 退款撤销时间，对应refund_status=7
     */
    public void setRefundCancelTime(Timestamp value) {
        set(24, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.refund_cancel_time</code>. 退款撤销时间，对应refund_status=7
     */
    public Timestamp getRefundCancelTime() {
        return (Timestamp) get(24);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.apply_not_pass_reason</code>. 审核未通过原因
     */
    public void setApplyNotPassReason(String value) {
        set(25, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.apply_not_pass_reason</code>. 审核未通过原因
     */
    public String getApplyNotPassReason() {
        return (String) get(25);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.refund_refuse_reason</code>. 退款拒绝原因
     */
    public void setRefundRefuseReason(String value) {
        set(26, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.refund_refuse_reason</code>. 退款拒绝原因
     */
    public String getRefundRefuseReason() {
        return (String) get(26);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.return_address</code>. 退货地址
     */
    public void setReturnAddress(String value) {
        set(27, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.return_address</code>. 退货地址
     */
    public String getReturnAddress() {
        return (String) get(27);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.merchant_telephone</code>. 商家电话
     */
    public void setMerchantTelephone(String value) {
        set(28, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.merchant_telephone</code>. 商家电话
     */
    public String getMerchantTelephone() {
        return (String) get(28);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.consignee</code>. 收货人
     */
    public void setConsignee(String value) {
        set(29, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.consignee</code>. 收货人
     */
    public String getConsignee() {
        return (String) get(29);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.zip_code</code>. 邮编
     */
    public void setZipCode(String value) {
        set(30, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.zip_code</code>. 邮编
     */
    public String getZipCode() {
        return (String) get(30);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.currency</code>. 币种
     */
    public void setCurrency(String value) {
        set(31, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.currency</code>. 币种
     */
    public String getCurrency() {
        return (String) get(31);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(32, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(32);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.update_time</code>. 最后修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(33, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.update_time</code>. 最后修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(33);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.is_auto_return</code>. 0否；1是
     */
    public void setIsAutoReturn(Byte value) {
        set(34, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.is_auto_return</code>. 0否；1是
     */
    public Byte getIsAutoReturn() {
        return (Byte) get(34);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.return_source</code>. 售后发起来源：0商家手动发起，1用户主动申请，2订单异常系统自动发起
     */
    public void setReturnSource(Byte value) {
        set(35, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.return_source</code>. 售后发起来源：0商家手动发起，1用户主动申请，2订单异常系统自动发起
     */
    public Byte getReturnSource() {
        return (Byte) get(35);
    }

    /**
     * Setter for <code>mini_main.b2c_return_order_bak.return_source_type</code>. 售后发起来源类型：0改价失败自动售后，1微信支付失败，2活动自动售后
     */
    public void setReturnSourceType(Byte value) {
        set(36, value);
    }

    /**
     * Getter for <code>mini_main.b2c_return_order_bak.return_source_type</code>. 售后发起来源类型：0改价失败自动售后，1微信支付失败，2活动自动售后
     */
    public Byte getReturnSourceType() {
        return (Byte) get(36);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<Integer, Integer> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ReturnOrderBakRecord
     */
    public ReturnOrderBakRecord() {
        super(ReturnOrderBak.RETURN_ORDER_BAK);
    }

    /**
     * Create a detached, initialised ReturnOrderBakRecord
     */
    public ReturnOrderBakRecord(Integer retId, Integer orderId, String orderSn, String returnOrderSn, Integer shopId, Integer userId, Integer goodsId, Byte refundStatus, BigDecimal money, BigDecimal shippingFee, Byte returnType, Byte reasonType, String reasonDesc, String shippingType, String shippingNo, String goodsImages, String voucherImages, String phone, Timestamp applyTime, Timestamp applyPassTime, Timestamp applyNotPassTime, Timestamp shippingOrRefundTime, Timestamp refundSuccessTime, Timestamp refundRefuseTime, Timestamp refundCancelTime, String applyNotPassReason, String refundRefuseReason, String returnAddress, String merchantTelephone, String consignee, String zipCode, String currency, Timestamp createTime, Timestamp updateTime, Byte isAutoReturn, Byte returnSource, Byte returnSourceType) {
        super(ReturnOrderBak.RETURN_ORDER_BAK);

        set(0, retId);
        set(1, orderId);
        set(2, orderSn);
        set(3, returnOrderSn);
        set(4, shopId);
        set(5, userId);
        set(6, goodsId);
        set(7, refundStatus);
        set(8, money);
        set(9, shippingFee);
        set(10, returnType);
        set(11, reasonType);
        set(12, reasonDesc);
        set(13, shippingType);
        set(14, shippingNo);
        set(15, goodsImages);
        set(16, voucherImages);
        set(17, phone);
        set(18, applyTime);
        set(19, applyPassTime);
        set(20, applyNotPassTime);
        set(21, shippingOrRefundTime);
        set(22, refundSuccessTime);
        set(23, refundRefuseTime);
        set(24, refundCancelTime);
        set(25, applyNotPassReason);
        set(26, refundRefuseReason);
        set(27, returnAddress);
        set(28, merchantTelephone);
        set(29, consignee);
        set(30, zipCode);
        set(31, currency);
        set(32, createTime);
        set(33, updateTime);
        set(34, isAutoReturn);
        set(35, returnSource);
        set(36, returnSourceType);
    }
}
