package com.vpu.mp.service.shop.order.Inquiry;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.BigDecimalUtil;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderRefundListDo;
import com.vpu.mp.dao.shop.department.DepartmentDao;
import com.vpu.mp.dao.shop.order.InquiryOrderDao;
import com.vpu.mp.dao.shop.refund.InquiryOrderRefundListDao;
import com.vpu.mp.db.shop.tables.InquiryOrder;
import com.vpu.mp.db.shop.tables.records.*;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.IncrSequenceUtil;
import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.pojo.wxapp.order.Inquiry.InquiryOrderConstant;
import com.vpu.mp.service.pojo.wxapp.order.Inquiry.InquiryOrderListParam;
import com.vpu.mp.service.pojo.wxapp.order.Inquiry.InquiryOrderOnParam;
import com.vpu.mp.service.pojo.wxapp.order.Inquiry.InquiryToPayParam;
import com.vpu.mp.service.pojo.wxapp.pay.base.WebPayVo;
import com.vpu.mp.service.shop.doctor.DoctorService;
import com.vpu.mp.service.shop.order.refund.ReturnMethodService;
import com.vpu.mp.service.shop.order.trade.TradesRecordService;
import com.vpu.mp.service.shop.patient.PatientService;
import com.vpu.mp.service.shop.payment.MpPaymentService;
import com.vpu.mp.service.shop.payment.PaymentRecordService;
import com.vpu.mp.service.shop.user.user.UserService;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InquiryOrderService extends ShopBaseService {
    @Autowired
    private InquiryOrderDao inquiryOrderDao;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private MpPaymentService mpPaymentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentRecordService paymentRecord;
    @Autowired
    private ReturnMethodService returnMethodService;
    @Autowired
    private TradesRecordService tradesRecord;
    @Autowired
    private InquiryOrderRefundListDao inquiryOrderRefundListDao;

    /*
     *问询订单列表
     */
    public PageResult<InquiryOrderDo> getInquiryOrderList(InquiryOrderListParam param){
        return inquiryOrderDao.getInquiryOrderList(param);
    }
    /*
     *订单id获得订单
     */
    public InquiryOrderDo getByOrderId(Integer orderId){
        InquiryOrderRecord record=inquiryOrderDao.getByOrderId(orderId);
        InquiryOrderDo inquiryOrderDo=new InquiryOrderDo();
        FieldsUtil.assign(record,inquiryOrderDo);
        return inquiryOrderDo;
    }
    /*
    *订单号获得订单
     */
    public InquiryOrderRecord getByOrderSn(String orderSn){
        return inquiryOrderDao.getByOrderSn(orderSn);
    }

    public void update(InquiryOrderOnParam param){
        InquiryOrderRecord record=inquiryOrderDao.getByOrderId(param.getOrderId());
        FieldsUtil.assign(param,record);
        inquiryOrderDao.update(record);
    }

    public Result<InquiryOrderRecord> getCanceledToPaidCloseOrder(){
        return inquiryOrderDao.getCanceledToPaidCloseOrder();
    }
    public Result<InquiryOrderRecord> getCanceledToWaitingCloseOrder(){
        return inquiryOrderDao.getCanceledToWaitingCloseOrder();
    }
    /**
     * 问诊支付回调完成
     * @param
     * @return
     */
    public void inquiryOrderFinish(InquiryOrderRecord order, PaymentRecordRecord paymentRecord) throws MpException {
        logger().info("问诊订单-支付完成(回调)-开始");
        order.setOrderStatus(InquiryOrderConstant.ORDER_TO_RECEIVE);
        order.setPaySn(paymentRecord==null?"":paymentRecord.getPaySn());
        order.setPayTime(DateUtils.getLocalDateTime());
        inquiryOrderDao.update(order);
        logger().info("问诊订单-支付完成(回调)-结束");

    }
    /**
     * 支付微信接口
     * @param param
     * @return
     */
    public WebPayVo payInquiryOrder(InquiryToPayParam param){
        logger().info("创建问诊订单-开始");
        //支付类型
        String payCode = InquiryOrderConstant.PAY_CODE_WX_PAY;
        InquiryOrderDo inquiryOrderDo=new InquiryOrderDo();
        String orderSn=saveInquiryOrder(param,payCode,inquiryOrderDo);
        UserRecord userRecord=userService.getUserByUserId(param.getUser().getUserId());
        WebPayVo vo = new WebPayVo();
        //微信支付接口
        try {
            vo = mpPaymentService.wxUnitOrder(param.getClientIp(), InquiryOrderConstant.GOODS_NAME, orderSn, param.getOrderAmount(), userRecord.getWxOpenid());
        } catch (WxPayException e) {
            logger().error("微信预支付调用接口失败WxPayException，订单号：{},异常：{}", orderSn, e);
            throw new BusinessException(JsonResultCode.CODE_ORDER_WXPAY_UNIFIEDORDER_FAIL);
        }catch (Exception e) {
            logger().error("微信预支付调用接口失败Exception，订单号：{},异常：{}", orderSn, e.getMessage());
            throw new BusinessException(JsonResultCode.CODE_ORDER_WXPAY_UNIFIEDORDER_FAIL);
        }
        logger().debug("微信支付接口调用结果：{}", vo);
        // 更新记录微信预支付id：prepayid
        inquiryOrderDao.updatePrepayId(orderSn,vo.getResult().getPrepayId());
        vo.setOrderSn(orderSn);
        logger().debug("微信支付创建订单结束");
        return vo;
    }
    private String saveInquiryOrder(InquiryToPayParam payParam, String payCode, InquiryOrderDo inquiryOrderDo){
        String orderSn = IncrSequenceUtil.generateOrderSn(InquiryOrderConstant.INQUIRY_ORDER_SN_PREFIX);
        PatientOneParam patientOneParam=patientService.getOneInfo(payParam.getPatientId());
        DepartmentOneParam department=departmentDao.getOneInfo(payParam.getDepartmentId());
        DoctorOneParam doctor = doctorService.getOneInfo(payParam.getDoctorId());
        inquiryOrderDo.setOrderSn(orderSn);
        inquiryOrderDo.setOrderAmount(payParam.getOrderAmount());
        inquiryOrderDo.setUserId(payParam.getUser().getUserId());
        inquiryOrderDo.setPatientId(payParam.getPatientId());
        inquiryOrderDo.setDoctorId(payParam.getDoctorId());
        inquiryOrderDo.setDoctorName(doctor.getName());
        inquiryOrderDo.setDepartmentId(department.getId());
        inquiryOrderDo.setDepartmentName(department.getName());
        inquiryOrderDo.setOrderStatus(InquiryOrderConstant.ORDER_TO_PAID);
        inquiryOrderDo.setPayCode(payCode);
        inquiryOrderDo.setPatientName(patientOneParam.getName());
        inquiryOrderDo.setPatientSex(patientOneParam.getSex());
        inquiryOrderDo.setPatientMobile(patientOneParam.getMobile());
        inquiryOrderDo.setPatientIdentityCode(patientOneParam.getIdentityCode());
        inquiryOrderDo.setPatientIdentityType(patientOneParam.getIdentityType());
        inquiryOrderDo.setImageUrl(payParam.getImagUrl());
        inquiryOrderDo.setDescriptionDisease(payParam.getDescriptionDisease());
        inquiryOrderDao.save(inquiryOrderDo);
        return orderSn;
    }
    /*
     *退款
     */
    public JsonResult refund( InquiryOrderOnParam inquiryOrderOnParam) {
        InquiryOrderRecord record=inquiryOrderDao.getByOrderId(inquiryOrderOnParam.getOrderId());
        try {
            refundInquiryOrder(record);
        } catch (MpException e) {
            JsonResult jsonResult=new JsonResult();
            jsonResult.result(null,JsonResultCode.CODE_ORDER_RETURN_EXIST_WX_REFUND_FAIL_ORDER,null);
            return  jsonResult;
        }
        return JsonResult.success();
    }
    /*
    *退款调用
     */
    public void refundInquiryOrder(InquiryOrderRecord order)throws MpException{
        boolean successFlag=true;
        if(InquiryOrderConstant.PAY_CODE_WX_PAY.equals(order.getPayCode())){
            //退款流水号
            String refundSn = IncrSequenceUtil.generateOrderSn(InquiryOrderConstant.INQUIRY_ORDER_SN_PREFIX);
            //支付记录
            PaymentRecordRecord payRecord = paymentRecord.getPaymentRecordByOrderSn(order.getOrderSn());
            if(payRecord == null) {
                logger().error("wxPayRefund 微信支付记录未找到 order_sn={}",order.getOrderSn());
                throw new MpException(JsonResultCode.CODE_ORDER_RETURN_WXPAYREFUND_NO_RECORD);
            }
            //微信金额单为为分需单位换算
            returnMethodService.refundByApi(order.getPayCode(),payRecord.getTradeNo(), refundSn,BigDecimalUtil.multiply(payRecord.getTotalFee(), new BigDecimal(Byte.valueOf(OrderConstant.TUAN_FEN_RATIO).toString())).intValue(),BigDecimalUtil.multiply(order.getOrderAmount(), new BigDecimal(Byte.valueOf(OrderConstant.TUAN_FEN_RATIO).toString())).intValue() );
        }
        //退款记录
        InquiryOrderRefundListDo refundListDo=new InquiryOrderRefundListDo();
        refundListDo.setOrderSn(order.getOrderSn());
        refundListDo.setMoneyAmout(order.getOrderAmount());
        refundListDo.setUserId(order.getUserId());
        refundListDo.setIsSuccess(successFlag?InquiryOrderConstant.REFUND_SUCCESS:InquiryOrderConstant.ORDER_TO_PAID);
        inquiryOrderRefundListDao.save(refundListDo);
        //更新状态
        order.setOrderStatus(InquiryOrderConstant.ORDER_REFUND);
        order.setCancelledTime(DateUtils.getLocalDateTime());
        inquiryOrderDao.update(order);
        //交易记录
        tradesRecord.addRecord(order.getOrderAmount(),order.getOrderSn(),order.getUserId(), TradesRecordService.TRADE_CONTENT_MONEY, RecordTradeEnum.TYPE_CASH_REFUND.val(),RecordTradeEnum.TRADE_FLOW_OUT.val(),TradesRecordService.TRADE_STATUS_ARRIVAL);
    }


}
