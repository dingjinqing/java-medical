package com.vpu.mp.service.shop.order.inquiry;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.sql.Order;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.excel.ExcelFactory;
import com.vpu.mp.common.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.common.foundation.excel.ExcelWriter;
import com.vpu.mp.common.foundation.util.*;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderRefundListDo;
import com.vpu.mp.common.pojo.shop.table.UserDo;
import com.vpu.mp.dao.shop.UserDao;
import com.vpu.mp.dao.shop.department.DepartmentDao;
import com.vpu.mp.dao.shop.order.InquiryOrderDao;
import com.vpu.mp.dao.shop.refund.InquiryOrderRefundListDao;
import com.vpu.mp.db.shop.tables.records.PaymentRecordRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.IncrSequenceUtil;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;
import com.vpu.mp.service.pojo.shop.config.message.MessageTemplateConfigConstant;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.pojo.shop.maptemplate.*;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.market.message.maconfig.SubcribeTemplateCategory;
import com.vpu.mp.service.pojo.shop.message.MpTemplateConfig;
import com.vpu.mp.service.pojo.shop.message.MpTemplateData;
import com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.pojo.shop.user.message.MaSubscribeData;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateData;
import com.vpu.mp.service.pojo.wxapp.image.ImageSimpleVo;
import com.vpu.mp.service.pojo.wxapp.medical.im.param.ImSessionNewParam;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.*;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.vo.InquiryOrderDetailVo;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.vo.InquiryOrderStatisticsVo;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.vo.InquiryOrderTotalVo;
import com.vpu.mp.service.pojo.wxapp.pay.base.WebPayVo;
import com.vpu.mp.service.shop.doctor.DoctorService;
import com.vpu.mp.service.shop.im.ImSessionService;
import com.vpu.mp.service.shop.maptemplatesend.MapTemplateSendService;
import com.vpu.mp.service.shop.order.refund.ReturnMethodService;
import com.vpu.mp.service.shop.order.trade.TradesRecordService;
import com.vpu.mp.service.shop.patient.PatientService;
import com.vpu.mp.service.shop.payment.MpPaymentService;
import com.vpu.mp.service.shop.payment.PaymentRecordService;
import com.vpu.mp.service.shop.user.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.eval.BlankEval;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangpengcheng
 */
@Service
public class InquiryOrderService extends ShopBaseService {
    public static final String BLANK = "测试";
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
    @Autowired
    private MapTemplateSendService mapTemplateSendService;

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
        Byte prevStatus = inquiryOrderDo.getOrderStatus();
        inquiryOrderDo.setOrderStatus(param.getOrderStatus());
        //更新会话状态修改为进行中
        if(param.getOrderStatus().equals(InquiryOrderConstant.ORDER_RECEIVING)){
            inquiryOrderDo.setLimitTime(DateUtils.getTimeStampPlus(InquiryOrderConstant.EXPIRY_TIME_HOUR, ChronoUnit.HOURS));
            imSessionService.updateSessionToGoingOn(param.getSessionId());
        }
        //更新会话状态为关闭
        if(param.getOrderStatus().equals(InquiryOrderConstant.ORDER_FINISHED)){
            inquiryOrderDo.setFinishedTime(DateUtils.getLocalDateTime());
            imSessionService.closeImSession(param.getSessionId());
        }
        inquiryOrderDao.update(inquiryOrderDo);
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
     * 未完成的问诊
     * @param param
     * @return
     */
    public InquiryOrderDo getUndoneOrder(InquiryOrderParam param){
        List<InquiryOrderDo> list=inquiryOrderDao.getOrderByParams(param);
        List<InquiryOrderDo> retList=list.stream().filter(inquiryOrderDo -> {
            Byte orderStatus=inquiryOrderDo.getOrderStatus();
                if(orderStatus.equals(InquiryOrderConstant.ORDER_TO_RECEIVE)||orderStatus.equals(InquiryOrderConstant.ORDER_RECEIVING)) {
                    return true;
                }
                return false;
        }).collect(Collectors.toList());
        if(retList!=null&&retList.size()>0){
            return retList.get(0);
        }
        return null;
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

        //订阅消息/公众号消息
        List<Integer> userIds = new ArrayList<>();
        userIds.add(24);
        ConsultationAnswerParam consultationAnswerParam = ConsultationAnswerParam.builder().consultationContent(BLANK).doctorName(BLANK).patientName(BLANK).remark(BLANK).userIds(userIds).build();
        ConsultationSuccessParam consultationSuccessParam = ConsultationSuccessParam.builder().departmentName(BLANK).diseaseDetail(BLANK).doctorName(BLANK).patientName(BLANK).remark(BLANK).userIds(userIds).build();
        ConsultationOrderExpireParam consultationOrderExpireParam =ConsultationOrderExpireParam.builder().consultationStatus(BLANK).remark(BLANK).userIds(userIds).build();

        mapTemplateSendService.sendConsultationAnswerMessage(consultationAnswerParam);
        mapTemplateSendService.sendConsultationSuccessMessage(consultationSuccessParam);
        mapTemplateSendService.sendConsultationExprieMessage(consultationOrderExpireParam);

    }


    /**
     * 支付微信接口
     * @param param
     * @return
     */
    public WebPayVo payInquiryOrder(InquiryToPayParam param) throws MpException{
        logger().info("创建问诊订单-开始");
        WebPayVo vo = new WebPayVo();
        //支付类型
        String payCode = InquiryOrderConstant.PAY_CODE_WX_PAY;
        InquiryOrderDo inquiryOrderDo=new InquiryOrderDo();
        String orderSn=saveInquiryOrder(param,payCode,inquiryOrderDo);
        UserRecord userRecord=userService.getUserByUserId(param.getUser().getUserId());

        InquiryOrderDo orderInfo=inquiryOrderDao.getByOrderSn(orderSn);
        //临时添加支付回调，正式使用删除
        String test = "test";
        if (param.getDescriptionDisease().contains(test)||param.getOrderAmount().compareTo(BigDecimal.ZERO)<=0) {
            inquiryOrderFinish(orderInfo,new PaymentRecordRecord());
        } else {
            //微信支付接口
            try {
                vo = mpPaymentService.wxUnitOrder(param.getClientIp(), InquiryOrderConstant.GOODS_NAME, orderSn, param.getOrderAmount(), userRecord.getWxOpenid());
            } catch (WxPayException e) {
                logger().error("微信预支付调用接口失败WxPayException，订单号：{},异常：{}", orderSn, e);
                throw new BusinessException(JsonResultCode.CODE_ORDER_WXPAY_UNIFIEDORDER_FAIL);
            }catch (MpException e) {
                logger().error("微信预支付调用接口失败Exception，订单号：{},异常：{}", orderSn, e.getMessage());
                throw new MpException(JsonResultCode.CODE_ORDER_WXPAY_UNIFIEDORDER_FAIL);
            }
            logger().debug("微信支付接口调用结果：{}", vo);
            // 更新记录微信预支付id：prepayid
            inquiryOrderDao.updatePrepayId(orderSn,vo.getResult().getPrepayId());


            logger().debug("微信支付创建订单结束");
        }
        vo.setOrderSn(orderSn);
        return vo;
    }
    private String saveInquiryOrder(InquiryToPayParam payParam, String payCode, InquiryOrderDo inquiryOrderDo){
        if(StringUtils.isNotBlank(payParam.getOrderSn())){
            return payParam.getOrderSn();
        }
        Integer shopId=getShopId();
        String orderSn = IncrSequenceUtil.generateOrderSn(InquiryOrderConstant.INQUIRY_ORDER_SN_PREFIX);
        PatientOneParam patientOneParam=patientService.getOneInfo(payParam.getPatientId());
        DoctorOneParam doctor = doctorService.getOneInfo(payParam.getDoctorId());
        inquiryOrderDo.setOrderSn(orderSn);
        inquiryOrderDo.setShopId(shopId);
        inquiryOrderDo.setOrderAmount(payParam.getOrderAmount());
        inquiryOrderDo.setUserId(payParam.getUser().getUserId());
        inquiryOrderDo.setPatientId(payParam.getPatientId());
        inquiryOrderDo.setDoctorId(payParam.getDoctorId());
        inquiryOrderDo.setDoctorName(doctor.getName());
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
     * 退款调用,可多次退
     * @param inquiryOrderOnParam
     * @return
     */
    public void refund( InquiryOrderOnParam inquiryOrderOnParam) throws MpException{
        InquiryOrderDo inquiryOrderDo=inquiryOrderDao.getByOrderSn(inquiryOrderOnParam.getOrderSn());
        refundInquiryOrder(inquiryOrderDo, inquiryOrderOnParam.getRefundMoney(),inquiryOrderOnParam.getRefundReason());
    }

    /**
     * 医师退款调用
     * @param inquiryOrderOnParam
     * @throws MpException
     */
    public void doctorRefund(InquiryOrderOnParam inquiryOrderOnParam)throws MpException{
        InquiryOrderDo inquiryOrderDo=inquiryOrderDao.getByOrderSn(inquiryOrderOnParam.getOrderSn());
        refundInquiryOrder(inquiryOrderDo, inquiryOrderDo.getOrderAmount(),InquiryOrderConstant.REFUND_REASON_DOCTOR);

    }

    /**
     * 退款调用
     * @param order
     * @throws MpException
     */
    public void refundInquiryOrder(InquiryOrderDo order,BigDecimal refundMoney,String refundReason)throws MpException{
        logger().info("问诊订单退款-开始start,orderSn:{}"+order.getOrderSn());
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
            BigDecimal refundable=BigDecimalUtil.subtrac(order.getOrderAmount(),order.getRefundMoney());
            if(BigDecimalUtil.compareTo(refundMoney,refundable)>0){
                logger().error("orderSn:{},退款金额超出可退金额",order.getOrderSn());
                throw new MpException(JsonResultCode.INQUIRY_ORDER_REFUND_MONEY_EXCESS);
            }
            //微信金额单为为分需单位换算
            returnMethodService.refundByApi(order.getPayCode(),payRecord.getTradeNo(), refundSn,BigDecimalUtil.multiply(payRecord.getTotalFee(), new BigDecimal(Byte.valueOf(OrderConstant.TUAN_FEN_RATIO).toString())).intValue(),BigDecimalUtil.multiply(refundMoney, new BigDecimal(Byte.valueOf(OrderConstant.TUAN_FEN_RATIO).toString())).intValue() );
        }
        //退款记录
        InquiryOrderRefundListDo refundListDo=new InquiryOrderRefundListDo();
        refundListDo.setOrderSn(order.getOrderSn());
        refundListDo.setMoneyAmount(refundMoney);
        refundListDo.setRefundReason(refundReason);
        refundListDo.setUserId(order.getUserId());
        refundListDo.setIsSuccess(successFlag?InquiryOrderConstant.REFUND_SUCCESS:InquiryOrderConstant.REFUND_FAILED);
        inquiryOrderRefundListDao.save(refundListDo);
        //更新状态
        BigDecimal newRefundMoney=order.getRefundMoney().add(refundMoney);
        order.setRefundMoney(newRefundMoney);
        if(BigDecimalUtil.compareTo(order.getOrderAmount(),newRefundMoney)==0){
            //已退款
            order.setOrderStatus(InquiryOrderConstant.ORDER_REFUND);
        }else {
            //部分退款
            order.setOrderStatus(InquiryOrderConstant.ORDER_PART_REFUND);

        }
        inquiryOrderDao.update(order);
        //取消会话
        List<String> orderSnList=new ArrayList<>();
        orderSnList.add(order.getOrderSn());
        imSessionService.batchCancelSession(orderSnList);
        //交易记录
        tradesRecord.addRecord(refundMoney,order.getOrderSn(),order.getUserId(), TradesRecordService.TRADE_CONTENT_MONEY, RecordTradeEnum.TYPE_CASH_REFUND.val(),RecordTradeEnum.TRADE_FLOW_OUT.val(),TradesRecordService.TRADE_STATUS_ARRIVAL);
        logger().info("问诊订单退款-结束end,orderSn:{}"+order.getOrderSn());
    }

    /**
     * 问诊订单统计报表查询
     * @param param
     * @return
     */
    public PageResult<InquiryOrderStatisticsVo> orderStatistics(InquiryOrderStatisticsParam param){
        beginAndEndOfDay(param);
        PageResult<InquiryOrderStatisticsVo> result=inquiryOrderDao.orderStatisticsPage(param);
        return result;
    }

    /**
     * 问诊订单统计报表导出
     * @param param
     * @param lang
     * @return
     */
    public Workbook orderStatisticsExport(InquiryOrderStatisticsParam param, String lang){
        beginAndEndOfDay(param);
        List<InquiryOrderStatisticsVo> list=inquiryOrderDao.orderStatistics(param);
        Workbook workbook= ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(lang,workbook);
        excelWriter.writeModelList(list,InquiryOrderStatisticsVo.class);
        return workbook;
    }

    /**
     * 报表总数total查询
     * @param param
     * @return
     */
    public InquiryOrderTotalVo orderStatisticsTotal(InquiryOrderStatisticsParam param){
        beginAndEndOfDay(param);
        InquiryOrderTotalVo inquiryOrderTotalVo=inquiryOrderDao.orderStatisticsTotal(param);
        return inquiryOrderTotalVo;
    }

    /**
     * 日期的时分秒开始和结束
     * @param param
     */
    public void beginAndEndOfDay(InquiryOrderStatisticsParam param){
        Timestamp startDate = param.getStartTime();
        Timestamp endDate = param.getEndTime();
        if (startDate != null ) {
            startDate = DateUtil.beginOfDay(startDate).toTimestamp();
            param.setStartTime(startDate);
        }if( endDate != null){
            endDate = DateUtil.endOfDay(endDate).toTimestamp();
            param.setEndTime(endDate);
        }
    }
}
