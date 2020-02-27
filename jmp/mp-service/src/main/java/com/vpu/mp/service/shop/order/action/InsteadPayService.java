package com.vpu.mp.service.shop.order.action;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.SubOrderInfoRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.insteadpay.InsteadPay;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.pay.instead.InsteadPayParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.pay.instead.InsteadPayVo;
import com.vpu.mp.service.pojo.wxapp.order.CreateOrderVo;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.pojo.wxapp.pay.base.WebPayVo;
import com.vpu.mp.service.shop.order.OrderReadService;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.sub.SubOrderService;
import com.vpu.mp.service.shop.order.trade.OrderPayService;
import com.vpu.mp.service.shop.payment.MpPaymentService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 好友代付
 * @author 王帅
 */
public class InsteadPayService extends ShopBaseService implements IorderOperate<OrderOperateQueryParam, InsteadPayParam> {

    @Autowired
    private OrderInfoService orderInfo;

    @Autowired
    private SubOrderService subOrderService;

    @Autowired
    private OrderReadService orderReadService;

    @Autowired
    private MpPaymentService pay;

    @Autowired
    private OrderPayService orderPay;

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Override
    public OrderServiceCode getServiceCode() {
        return OrderServiceCode.INSTEAD_PAY;
    }

    @Override
    public Object query(OrderOperateQueryParam param) throws MpException {
        logger().info("代付query start:");
        OrderInfoRecord order = orderInfo.getOrderByOrderSn(param.getOrderSn());
        if(order == null) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_NOT_EXIST, null);
        }
        if(!order.getOrderStatus().equals(OrderConstant.ORDER_WAIT_PAY)) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_OPERATE_NO_INSTANCEOF, null);
        }
        if(order.getOrderStatus() >= OrderConstant.ORDER_WAIT_DELIVERY) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_PAY_WAY_INSTEAD_PAY_FINISH, null);
        }
        //vo
        InsteadPayVo vo = new InsteadPayVo();
        //获取已付金额
        BigDecimal amountPaid = orderInfo.getOrderFinalAmount(order.into(OrderListInfoVo.class), true);
        //待支付金额
        BigDecimal waitPayMoney = BigDecimalUtil.subtrac(order.getInsteadPayMoney(), order.getMoneyPaid());
        //校验
        if(BigDecimalUtil.compareTo(waitPayMoney, null) < 1) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_PAY_WAY_INSTEAD_PAY_FINISH, null);
        }
        //代付配置
        InsteadPay cfg = Util.parseJson(order.getInsteadPay(), InsteadPay.class);
        if(cfg == null) {
            logger().error("代付订单支付页面代付配置为null");
            return ExecuteResult.create(JsonResultCode.CODE_PARAM_ERROR, null);
        }
        if(BigDecimalUtil.compareTo(waitPayMoney, OrderConstant.CENT) == 0) {
            //1分随便付
            vo.setIsShowEdit(OrderConstant.NO);
            vo.setMoneyPaid(waitPayMoney);
        }else if(param.getWxUserInfo().getUserId().equals(order.getUserId())) {
            //自己付
            vo.setIsShowEdit(OrderConstant.YES);
            vo.setMoneyPaid(BigDecimalUtil.BIGDECIMAL_ZERO);
        }else if(order.getInsteadPayNum() == 1) {
            //单人付
            vo.setIsShowEdit(OrderConstant.NO);
            vo.setMoneyPaid(waitPayMoney);
        }else {
            //多人付
            //订单实际金额
            BigDecimal orderAmount = BigDecimalUtil.addOrSubtrac(
                BigDecimalUtil.BigDecimalPlus.create(amountPaid, BigDecimalUtil.Operator.subtrac),
                BigDecimalUtil.BigDecimalPlus.create(order.getMoneyPaid(), BigDecimalUtil.Operator.add),
                BigDecimalUtil.BigDecimalPlus.create(order.getInsteadPayMoney())
            );
            //代付金额三阶梯
            List<BigDecimal> threeStages = new ArrayList<>(3);
            //一阶段
            threeStages.add(0, BigDecimalUtil.BIGDECIMAL_ZERO);
            //二阶段
            threeStages.add(1, BigDecimalUtil.BIGDECIMAL_ZERO);
            //三阶段
            threeStages.add(2, BigDecimalUtil.BIGDECIMAL_ZERO);

            vo.setThreeStages(threeStages);
            vo.setIsShowEdit(OrderConstant.YES);
            vo.setMoneyPaid(BigDecimalUtil.BIGDECIMAL_ZERO);
        }
        vo.setMessage(order.getUserId().equals(param.getWxUserInfo().getUserId()) ?
            (order.getInsteadPayNum() == 0 ? cfg.getOrderUserMessageMultiple() : cfg.getOrderUserMessageSingle()) :
            (order.getInsteadPayNum() == 0 ? cfg.getInsteadPayMessageMultiple() : cfg.getInsteadPayMessageSingle()));
        vo.setAmountPaid(amountPaid);
        vo.setWaitPayMoney(waitPayMoney);
        vo.setOrder(orderReadService.mpGet(new OrderParam(param.getOrderSn())));
        logger().info("代付query end:");
        return vo;
    }

    @Override
    public ExecuteResult execute(InsteadPayParam param) {
        logger().info("代付execute start:{}", param.toString());
        OrderInfoRecord order = orderInfo.getOrderByOrderSn(param.getOrderSn());
        ExecuteResult check = check(param, order);
        if(check != null && !check.isSuccess()) {
            return check;
        }
        AtomicReference<SubOrderInfoRecord> subOrderRef = null;
        transaction(()->{
            subOrderRef.set(subOrderService.create(order.getOrderSn(), param.getMoneyPaid(), param.getMessage(), param.getWxUserInfo().getUserId(), ""));
        });
        SubOrderInfoRecord subOrder = subOrderRef.get();
        WebPayVo webPayVo = null;
        try {
            webPayVo = pay(param, order, subOrder);
        } catch (MpException e) {
            return ExecuteResult.create(e.getErrorCode(), null);
        }
        webPayVo.setOrderSn(subOrder.getSubOrderSn());
        subOrderService.updatePrepayId(subOrder, webPayVo);
        return ExecuteResult.create(new CreateOrderVo(subOrder.getSubOrderSn(), webPayVo));
    }

    private WebPayVo pay(InsteadPayParam param, OrderInfoRecord order, SubOrderInfoRecord subOrder) throws MpException {
        logger().info("微信预支付调用接口start");
        WebPayVo webPayVo = null;
        try {
            webPayVo = pay.wxUnitOrder(param.getClientIp(), orderPay.getGoodsNameForPay(order, orderGoodsService.getOrderGoods(order.getOrderSn()).into(OrderGoodsBo.class)), subOrder.getSubOrderSn(), subOrder.getMoneyPaid(), param.getWxUserInfo().getWxUser().getOpenId());
        } catch (WxPayException e) {
            logger().error("微信预支付调用接口失败WxPayException，订单号：{},异常：{}", order.getOrderSn(), e);
            throw new MpException(JsonResultCode.CODE_ORDER_WXPAY_UNIFIEDORDER_FAIL);
        }catch (Exception e) {
            logger().error("微信预支付调用接口失败Exception，订单号：{},异常：{}", order.getOrderSn(), e.getMessage());
            throw new MpException(JsonResultCode.CODE_ORDER_WXPAY_UNIFIEDORDER_FAIL);
        }
        logger().info("微信预支付调用接口end");
        return webPayVo;
    }

    /**
     * 校验
     * @param param
     * @param order
     * @return
     */
    private ExecuteResult check(InsteadPayParam param, OrderInfoRecord order) {
        if(order == null) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_NOT_EXIST, null);
        }
        if(!order.getOrderStatus().equals(OrderConstant.ORDER_WAIT_PAY)) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_OPERATE_NO_INSTANCEOF, null);
        }
        if(order.getOrderStatus() >= OrderConstant.ORDER_WAIT_DELIVERY) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_PAY_WAY_INSTEAD_PAY_FINISH, null);
        }
        //留言校验
        if(StringUtils.isNotBlank(param.getMessage()) && param.getMessage().length() > 20) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_PAY_WAY_INSTEAD_PAY_FINISH, "留言长度限制20字以内");
        }
        //待支付金额
        BigDecimal waitPayMoney = BigDecimalUtil.subtrac(order.getInsteadPayMoney(), order.getMoneyPaid());
        //校验
        if(BigDecimalUtil.compareTo(waitPayMoney, null) < 1) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_PAY_WAY_INSTEAD_PAY_FINISH, null);
        }
        //校验
        if(BigDecimalUtil.compareTo(waitPayMoney, OrderConstant.CENT) == -1) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_PAY_WAY_INSTEAD_PAY_FINISH, "支付金额错误");
        }
        //超待支付校验
        if (BigDecimalUtil.compareTo(param.getMoneyPaid(), waitPayMoney) == 1) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_PAY_WAY_INSTEAD_PAY_FINISH, "支付金额不能大于{$waitPayMoney}元");
        }
        //单人付校验(非自己)
        if(NumberUtils.SHORT_ONE.equals(order.getInsteadPayNum()) && param.getWxUserInfo().getUserId().equals(order.getUserId()) && BigDecimalUtil.compareTo(param.getMoneyPaid(), waitPayMoney) != 0) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_PAY_WAY_INSTEAD_PAY_FINISH, "支付金额应为{$waitPayMoney}元");
        }
        return null;
    }


}
