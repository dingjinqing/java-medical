package com.vpu.mp.service.pojo.wxapp.order.Inquiry;


public class InquiryOrderConstant {
    //订单状态
    //待支付
    public final static Byte ORDER_TO_PAID=0;
    //待接诊
    public final static Byte ORDER_TO_WAITING=1;
    //已取消
    public final static Byte ORDER_TO_CANCELED=2;
    //已完成
    public final static Byte ORDER_TO_FINISHED=3;

    //超时时间
    public final static int EXPIRY_TIME_HOUR=24;

    /**微信支付*/
    public final static String PAY_CODE_WX_PAY = "wxpay";

    //订单号前缀
    public final static String INQUIRY_ORDER_SN_PREFIX="I";

    //商品名称
    public final static String GOODS_NAME="问诊咨询";
}
