package com.vpu.mp.service.shop.order.inquiry;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.*;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderRefundListDo;
import com.vpu.mp.common.pojo.shop.table.UserDo;
import com.vpu.mp.dao.shop.UserDao;
import com.vpu.mp.dao.shop.department.DepartmentDao;
import com.vpu.mp.dao.shop.order.InquiryOrderDao;
import com.vpu.mp.dao.shop.refund.InquiryOrderRefundListDao;
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
import com.vpu.mp.service.pojo.wxapp.image.ImageSimpleVo;
import com.vpu.mp.service.pojo.wxapp.medical.im.param.ImSessionNewParam;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryOrderConstant;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryOrderListParam;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryOrderOnParam;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryToPayParam;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.vo.InquiryOrderDetailVo;
import com.vpu.mp.service.pojo.wxapp.pay.base.WebPayVo;
import com.vpu.mp.service.shop.doctor.DoctorService;
import com.vpu.mp.service.shop.im.ImSessionService;
import com.vpu.mp.service.shop.order.refund.ReturnMethodService;
import com.vpu.mp.service.shop.order.trade.TradesRecordService;
import com.vpu.mp.service.shop.patient.PatientService;
import com.vpu.mp.service.shop.payment.MpPaymentService;
import com.vpu.mp.service.shop.payment.PaymentRecordService;
import com.vpu.mp.service.shop.user.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangpengcheng
 */
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
    @Autowired
    private ImSessionService imSessionService;
    @Autowired
    private UserDao userDao;

    /**
     * 问询订单列表
     * @param param
     * @return
     */
    public PageResult<InquiryOrderDo> getInquiryOrderList(InquiryOrderListParam param){
        return inquiryOrderDao.getInquiryOrderList(param);
    }

    /**
     * 订单id获得订单
     * @param orderId
     * @return
     */
    public InquiryOrderDetailVo getDetailByOrderId(Integer orderId){
        InquiryOrderDo inquiryOrderDo=inquiryOrderDao.getByOrderId(orderId);
        UserDo userDo=userDao.getUserById(inquiryOrderDo.getUserId());
        InquiryOrderDetailVo inquiryOrderDetailVo=new InquiryOrderDetailVo();
        FieldsUtil.assign(inquiryOrderDo,inquiryOrderDetailVo);
        inquiryOrderDetailVo.setUserMobile(userDo.getMobile());
        inquiryOrderDetailVo.setUserName(userDo.getUsername());
        return inquiryOrderDetailVo;
    }

    /**
     * 订单号获得订单
     * @param orderSn
     * @return
     */
    public InquiryOrderDetailVo getDetailByOrderSn(String orderSn){
        InquiryOrderDo inquiryOrderDo=inquiryOrderDao.getByOrderSn(orderSn);
        InquiryOrderDetailVo inquiryOrderDetailVo=new InquiryOrderDetailVo();
        FieldsUtil.assign(inquiryOrderDo,inquiryOrderDetailVo);
        inquiryOrderDetailVo.setPatientAge(DateUtils.getAgeByBirthDay(inquiryOrderDetailVo.getPatientBirthday()));
        return inquiryOrderDetailVo;
    }

    /**
     * 更改问诊状态
     * @param param
     */
    public void updateOrder(InquiryOrderOnParam param){
        InquiryOrderDo inquiryOrderDo=inquiryOrderDao.getByOrderSn(param.getOrderSn());
        FieldsUtil.assign(param,inquiryOrderDo);
        inquiryOrderDo.setOrderStatus(param.getOrderStatus());
        inquiryOrderDao.update(inquiryOrderDo);
        //更新会话状态修改为进行中
        if(param.getOrderStatus().equals(InquiryOrderConstant.ORDER_RECEIVING)){
            imSessionService.updateSessionToGoingOn(param.getSessionId());
        }
        //更新会话状态为关闭
        if(param.getOrderStatus().equals(InquiryOrderConstant.ORDER_FINISHED)){
            imSessionService.closeImSession(param.getSessionId());
        }
    }
    public void insert(InquiryOrderDo inquiryOrderDo){
        int orderId=inquiryOrderDao.save(inquiryOrderDo);
        inquiryOrderDo.setOrderId(orderId);
    }

    public List<InquiryOrderDo> getCanceledToPaidCloseOrder(){
        return inquiryOrderDao.getCanceledToPaidCloseOrder();
    }
    public List<InquiryOrderDo> getCanceledToWaitingCloseOrder(){
        return inquiryOrderDao.getCanceledToWaitingCloseOrder();
    }

    /**
     * 问诊支付回调完成
     * @param order
     * @param paymentRecord
     * @throws MpException
     */
    public void inquiryOrderFinish(InquiryOrderDo order, PaymentRecordRecord paymentRecord)  {
        logger().info("问诊订单-支付完成(回调)-开始");
        order.setOrderStatus(InquiryOrderConstant.ORDER_TO_RECEIVE);
        order.setPaySn(paymentRecord==null?"":paymentRecord.getPaySn());
        order.setPayTime(DateUtils.getLocalDateTime());
        //更新问诊订单状态为待接诊
        inquiryOrderDao.update(order);
        //添加会话问诊
        ImSessionNewParam imSessionNewParam=new ImSessionNewParam();
        FieldsUtil.assign(order,imSessionNewParam);
        imSessionService.insertNewSession(imSessionNewParam);
        logger().info("问诊订单-支付完成(回调)-结束");

    }

    /**
     * @param orderStatus
     * @return
     */
    public boolean isSameInquiry(Byte orderStatus){
        boolean flag=false;
        //相同用户，相同患者发起的相同科室、医生的问诊订单的状态为待支付，待接诊，接诊中
        if(orderStatus.equals(InquiryOrderConstant.ORDER_TO_PAID)||orderStatus.equals(InquiryOrderConstant.ORDER_TO_RECEIVE)||orderStatus.equals(InquiryOrderConstant.ORDER_RECEIVING)){
            flag=true;
        }
        return flag;
    }
    /**
     * 支付微信接口
     * @param param
     * @return
     */
    public WebPayVo payInquiryOrder(InquiryToPayParam param){
        logger().info("创建问诊订单-开始");
        WebPayVo vo = new WebPayVo();
//        InquiryOrderDo orderDo=inquiryOrderDao.getOrderByParams(param);
        //存在相同未完成的订单
//        if(isSameInquiry(orderDo.getOrderStatus())){
//            vo.setOrderSn(orderDo.getOrderSn());
//            return vo;
//        }
        //支付类型
        String payCode = InquiryOrderConstant.PAY_CODE_WX_PAY;
        InquiryOrderDo inquiryOrderDo=new InquiryOrderDo();
        String orderSn=saveInquiryOrder(param,payCode,inquiryOrderDo);
        UserRecord userRecord=userService.getUserByUserId(param.getUser().getUserId());

        //微信支付接口
//        try {
//            vo = mpPaymentService.wxUnitOrder(param.getClientIp(), InquiryOrderConstant.GOODS_NAME, orderSn, param.getOrderAmount(), userRecord.getWxOpenid());
//        } catch (WxPayException e) {
//            logger().error("微信预支付调用接口失败WxPayException，订单号：{},异常：{}", orderSn, e);
//            throw new BusinessException(JsonResultCode.CODE_ORDER_WXPAY_UNIFIEDORDER_FAIL);
//        }catch (Exception e) {
//            logger().error("微信预支付调用接口失败Exception，订单号：{},异常：{}", orderSn, e.getMessage());
//            throw new BusinessException(JsonResultCode.CODE_ORDER_WXPAY_UNIFIEDORDER_FAIL);
//        }
        logger().debug("微信支付接口调用结果：{}", vo);
        // 更新记录微信预支付id：prepayid
//        inquiryOrderDao.updatePrepayId(orderSn,vo.getResult().getPrepayId());
        vo.setOrderSn(orderSn);
        InquiryOrderDo orderInfo=inquiryOrderDao.getByOrderSn(orderSn);
        inquiryOrderFinish(orderInfo,new PaymentRecordRecord());
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
        inquiryOrderDo.setPatientBirthday(patientOneParam.getBirthday());
        inquiryOrderDo.setPatientIdentityCode(patientOneParam.getIdentityCode());
        inquiryOrderDo.setPatientIdentityType(patientOneParam.getIdentityType());
        List<ImageSimpleVo> imageList=payParam.getImageList();
        String imageUrl=Util.toJson(imageList);
//        String imageUrl=imageList.stream().collect(Collectors.joining(","));
        inquiryOrderDo.setImageUrl(imageUrl);
        inquiryOrderDo.setDescriptionDisease(payParam.getDescriptionDisease());
        inquiryOrderDao.save(inquiryOrderDo);
        return orderSn;
    }

    /**
     * 退款
     * @param inquiryOrderOnParam
     * @return
     */
    public JsonResult refund( InquiryOrderOnParam inquiryOrderOnParam) {
        InquiryOrderDo inquiryOrderDo=inquiryOrderDao.getByOrderSn(inquiryOrderOnParam.getOrderSn());
        try {
            refundInquiryOrder(inquiryOrderDo);
        } catch (MpException e) {
            JsonResult jsonResult=new JsonResult();
            jsonResult.result(null,e.getErrorCode(),null);
            return  jsonResult;
        }
        return JsonResult.success();
    }

    /**
     * 退款调用
     * @param order
     * @throws MpException
     */
    public void refundInquiryOrder(InquiryOrderDo order)throws MpException{
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
//            returnMethodService.refundByApi(order.getPayCode(),payRecord.getTradeNo(), refundSn,BigDecimalUtil.multiply(payRecord.getTotalFee(), new BigDecimal(Byte.valueOf(OrderConstant.TUAN_FEN_RATIO).toString())).intValue(),BigDecimalUtil.multiply(order.getOrderAmount(), new BigDecimal(Byte.valueOf(OrderConstant.TUAN_FEN_RATIO).toString())).intValue() );
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
        //取消未接诊过期的会话
        List<String> orderSnList=new ArrayList<>();
        orderSnList.add(order.getOrderSn());
        imSessionService.batchCancelSession(orderSnList);
        //交易记录
        tradesRecord.addRecord(order.getOrderAmount(),order.getOrderSn(),order.getUserId(), TradesRecordService.TRADE_CONTENT_MONEY, RecordTradeEnum.TYPE_CASH_REFUND.val(),RecordTradeEnum.TRADE_FLOW_OUT.val(),TradesRecordService.TRADE_STATUS_ARRIVAL);
    }


}
