package com.vpu.mp.service.shop.order.action;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.pojo.shop.market.insteadpay.InsteadPay;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.pay.instead.InsteadPayVo;
import com.vpu.mp.service.shop.config.InsteadPayConfig;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import com.vpu.mp.service.shop.order.action.base.IorderOperate;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 好友代付
 * @author 王帅
 */
public class InsteadPayService extends ShopBaseService implements IorderOperate<OrderOperateQueryParam,OrderOperateQueryParam> {

    @Autowired
    private OrderInfoService orderInfo;

    @Autowired
    private InsteadPayConfig insteadPayConfig;

    @Override
    public OrderServiceCode getServiceCode() {
        return OrderServiceCode.INSTEAD_PAY;
    }

    @Override
    public Object query(OrderOperateQueryParam param) throws MpException {
        OrderInfoRecord order = orderInfo.getOrderByOrderSn(param.getOrderSn());
        if(order == null) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_NOT_EXIST, null);
        }
        if(order.getOrderStatus().equals(OrderConstant.ORDER_WAIT_PAY)) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_OPERATE_NO_INSTANCEOF, null);
        }
        if(order.getOrderStatus().equals(OrderConstant.ORDER_WAIT_DELIVERY)) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_PAY_WAY_INSTEAD_PAY_FINISH, null);
        }
        //vo
        InsteadPayVo vo = new InsteadPayVo();
        //获取已付金额
        BigDecimal amountPaid = orderInfo.getOrderFinalAmount(order.into(OrderListInfoVo.class), true);
        //待支付金额
        BigDecimal waitPayMoney = BigDecimalUtil.subtrac(order.getInsteadPayMoney(), amountPaid);
        //校验
        if(BigDecimalUtil.compareTo(waitPayMoney, null) < 1) {
            return ExecuteResult.create(JsonResultCode.CODE_ORDER_PAY_WAY_INSTEAD_PAY_FINISH, null);
        }
        //代付配置
        InsteadPay cfg = this.insteadPayConfig.getInsteadPayConfig();
        if(BigDecimalUtil.compareTo(waitPayMoney, new BigDecimal("0.01")) == 0) {
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
            threeStages.add(InsteadPay.NOT_SET, BigDecimalUtil.BIGDECIMAL_ZERO);
            //二阶段
            threeStages.add(InsteadPay.ONE_WAY, BigDecimalUtil.BIGDECIMAL_ZERO);
            //三阶段
            threeStages.add(InsteadPay.TWO_WAY, BigDecimalUtil.BIGDECIMAL_ZERO);

            vo.setThreeStages(threeStages);
            vo.setIsShowEdit(OrderConstant.YES);
            vo.setMoneyPaid(BigDecimalUtil.BIGDECIMAL_ZERO);
        }
        vo.setMessage("");
        vo.setAmountPaid(amountPaid);
        vo.setWaitPayMoney(waitPayMoney);
        //TODO
        return vo;
    }

    @Override
    public ExecuteResult execute(OrderOperateQueryParam obj) {
        return null;
    }


}
