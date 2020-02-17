package com.vpu.mp.service.shop.order.action.base;

import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;
import com.vpu.mp.service.pojo.shop.config.message.MessageConfigVo;
import com.vpu.mp.service.pojo.shop.config.message.MessageTemplateConfigConstant;
import com.vpu.mp.service.pojo.shop.express.ExpressVo;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.market.message.RabbitParamConstant;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateConfig;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateData;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateData;
import com.vpu.mp.service.shop.config.message.MessageConfigService;
import com.vpu.mp.service.shop.express.ExpressService;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import com.vpu.mp.service.shop.user.message.maConfig.SubcribeTemplateCategory;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 订单操作发送模板消息
 */
@Service
public class OrderOperateSendMessage extends ShopBaseService {

    @Autowired
    private ExpressService express;

    @Autowired
    private OrderGoodsService orderGoods;

    @Autowired
    private MessageConfigService messageConfig;

    /**
     * 发货模板消息
     * @param order
     * @param recordList
     */
    public void send(OrderInfoRecord order, ArrayList<OrderGoodsRecord> recordList) {
        logger().info("发货模板消息start");
        //商品名称
        String goodsName = getGoodsName(recordList);
        //快递公司名称
        ExpressVo expressVo = express.get(order.getShippingId());
        String shippingName = expressVo == null ? "other" : expressVo.getShippingName();
        //小程序数据
        String[][] maData = new String[][] { { goodsName }, { order.getOrderSn() }, { shippingName }, { order.getShippingNo() }, { order.getCompleteAddress() }, { Util.getdate(DateUtil.DATE_FORMAT_FULL) }};
        //公众号数据
        String[][] mpData = null;
        if(isSendMp(MessageTemplateConfigConstant.ORDER_SEND)) {
            mpData = new String[][] { { "亲，宝贝已经启程了，好想快点来到你身边" }, { order.getOrderSn() }, { shippingName }, { order.getShippingNo() }, {StringUtils.EMPTY}};
        }
        RabbitMessageParam param = RabbitMessageParam.builder()
            .maTemplateData(MaTemplateData.builder().config(SubcribeTemplateCategory.ORDER_DELIVER).data(maData).build())
            .mpTemplateData(MpTemplateData.builder().config(MpTemplateConfig.ORDER_DELIVER).data(mpData).build())
            .page("pages/orderinfo/orderinfo?order_sn=" + order.getOrderSn())
            .shopId(getShopId())
            .userIdList(Collections.singletonList(order.getUserId()))
            .type(RabbitParamConstant.Type.MA_SUBSCRIBEMESSAGE_TYPE).build();
        saas.taskJobMainService.dispatchImmediately(param, RabbitMessageParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
        logger().info("发货模板消息end");
    }

    /**
     * 退货状态为终态时发送模板消息
     * @param returnOrder
     * @param returnGoods
     */
    public void send(ReturnOrderRecord returnOrder, Result<ReturnOrderGoodsRecord> returnGoods) {
        logger().info("退款操作消息推送start");
        //TODO 子单需要推送到主单用户
        // 跳转到退款页面
        String page = "pages/returnorder/returnorder?order_sn=" + returnOrder.getOrderSn() + "&ret_id=" + returnOrder.getRetId();
        //商品名称
        String goodsName = getReturnGoodsName(returnGoods);
        //金额
        String money = BigDecimalUtil.add(returnOrder.getMoney(), returnOrder.getShippingFee()).toString();
        //申请时间
        String applyTime = DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL, OrderConstant.RT_ONLY_MONEY == returnOrder.getReturnType() ? returnOrder.getShippingOrRefundTime() :returnOrder.getApplyTime());
        //参数
        RabbitMessageParam param;
        if(returnOrder.getRefundStatus() == OrderConstant.REFUND_STATUS_FINISH) {
            //TODO 成功(积分兑换特殊处理)
            //小程序数据
            String[][] maData = new String[][] { { money }, { returnOrder.getOrderSn() }, { applyTime }, { goodsName }, { "卖家已同意退款,将原路退回到你的账户" }, { Util.getdate(DateUtil.DATE_FORMAT_FULL) }};
            //公众号数据
            String[][] mpData = null;
            if(isSendMp(MessageTemplateConfigConstant.STATUS_RETURN_MONEY)) {
                mpData = new String[][] { { "退款" }, { returnOrder.getReasonDesc() }, { money }, { StringUtils.EMPTY }};
            }
            //参数
            param = RabbitMessageParam.builder()
                .maTemplateData(MaTemplateData.builder().config(SubcribeTemplateCategory.REFUND_RESULT).data(maData).build())
                .mpTemplateData(MpTemplateData.builder().config(MpTemplateConfig.ORDER_REFUND).data(mpData).build())
                .page(page)
                .shopId(getShopId())
                .userIdList(Collections.singletonList(returnOrder.getUserId()))
                .type(RabbitParamConstant.Type.MA_SUBSCRIBEMESSAGE_TYPE)
                .build();
        }else if(returnOrder.getRefundStatus() == OrderConstant.REFUND_STATUS_AUDIT_NOT_PASS || returnOrder.getRefundStatus() == OrderConstant.REFUND_STATUS_REFUSE) {
            //失败
            //拒绝申请/退款原因
            String refuseReason = returnOrder.getApplyNotPassReason() != null ? returnOrder.getApplyNotPassReason() : returnOrder.getRefundRefuseReason();
            //小程序数据
            String[][] maData = new String[][] { { money }, { returnOrder.getOrderSn() }, { applyTime }, { goodsName }, { refuseReason }, { Util.getdate(DateUtil.DATE_FORMAT_FULL) }};
            //公众号数据
            String[][] mpData = null;
            if(isSendMp(MessageTemplateConfigConstant.FAIL_RETURN_MONEY)) {
                mpData = new String[][] { { "您的退款审核被拒绝" }, { returnOrder.getOrderSn() }, { applyTime }, { money }, { refuseReason }, { "具体信息请查看详情" }};
            }
            //参数：
            param = RabbitMessageParam.builder()
                .maTemplateData(MaTemplateData.builder().config(SubcribeTemplateCategory.REFUND_RESULT).data(maData).build())
                .mpTemplateData(MpTemplateData.builder().config(MpTemplateConfig.ORDER_REFUND_FAIL).data(mpData).build())
                .page(page)
                .shopId(getShopId())
                .userIdList(Collections.singletonList(returnOrder.getUserId()))
                .type(RabbitParamConstant.Type.MA_SUBSCRIBEMESSAGE_TYPE)
                .build();
        }else {
            logger().info("此次退款操作无消息推送end");
            return;
        }
        saas.taskJobMainService.dispatchImmediately(param, RabbitMessageParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
        logger().info("退款操作消息推送end");
    }

    /**
     * TODO 订单支付成功模板消息
     * @param order
     */
    public void send(OrderInfoRecord order, Result<OrderGoodsRecord> goods) {
        logger().info("订单支付成功模板消息start");
        //商品名称
        String goodsName = getGoodsName(goods);
        //公众号数据
        String[][] mpData = null;
        if(isSendMp(MessageTemplateConfigConstant.ORDER_SUCCESS_PAY)) {
            mpData = new String[][] { { "亲，宝贝已经启程了，好想快点来到你身边" }, { order.getOrderSn() }, {  }, { order.getShippingNo() }, {StringUtils.EMPTY}};
        }
        RabbitMessageParam param = RabbitMessageParam.builder()
            .mpTemplateData(MpTemplateData.builder().config(MpTemplateConfig.ORDER_WXPAY_SUCCESS).data(mpData).build())
            .page("pages/orderinfo/orderinfo?order_sn=" + order.getOrderSn())
            .shopId(getShopId())
            .userIdList(Collections.singletonList(order.getUserId()))
            .type(RabbitParamConstant.Type.MA_SUBSCRIBEMESSAGE_TYPE).build();
        saas.taskJobMainService.dispatchImmediately(param, RabbitMessageParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
        logger().info("订单支付成功模板消息end");
    }

    /**
     * TODO 订单未支付提醒模板消息
     * @param order
     */
    public void send(OrderInfoRecord order) {
        logger().info("订单未支付提醒模板消息start");
        //公众号数据
        String[][] mpData = null;
        if(isSendMp(MessageTemplateConfigConstant.ORDER_NO_PAY)) {
            mpData = new String[][] { { "亲，宝贝已经启程了，好想快点来到你身边" }, { order.getOrderSn() }, {  }, { order.getShippingNo() }, {StringUtils.EMPTY}};
        }
        RabbitMessageParam param = RabbitMessageParam.builder()
            .mpTemplateData(MpTemplateData.builder().config(MpTemplateConfig.ORDER_NOPAY_NOTIFY).data(mpData).build())
            .page("pages/orderinfo/orderinfo?order_sn=" + order.getOrderSn())
            .shopId(getShopId())
            .userIdList(Collections.singletonList(order.getUserId()))
            .type(RabbitParamConstant.Type.MA_SUBSCRIBEMESSAGE_TYPE).build();
        saas.taskJobMainService.dispatchImmediately(param, RabbitMessageParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
        logger().info("订单未支付提醒模板消息end");
    }

    /**
     * 取货成功通知模板消息
     * @param order
     */
    public void sendSelfPickupSuccess(OrderInfoRecord order) {
        logger().info("订单取货成功通知模板消息start");
        //公众号数据
        String[][] mpData = null;
        if(isSendMp(MessageTemplateConfigConstant.SUCCESS_GET_GOODS)) {
            mpData = new String[][] {{"您好，您的订单已经取货完成"}, {order.getOrderSn()}, {DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL, order.getConfirmTime())}, {"感谢您的使用"}};
        }
        RabbitMessageParam param = RabbitMessageParam.builder()
            .mpTemplateData(MpTemplateData.builder().config(MpTemplateConfig.ORDER_SELFPICKUP_SUCCESS).data(mpData).build())
            .page("pages/orderinfo/orderinfo?order_sn=" + order.getOrderSn())
            .shopId(getShopId())
            .userIdList(Collections.singletonList(order.getUserId()))
            .type(RabbitParamConstant.Type.MA_SUBSCRIBEMESSAGE_TYPE).build();
        saas.taskJobMainService.dispatchImmediately(param, RabbitMessageParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
        logger().info("订单取货成功通知模板消息end");
    }

    /**
     * 用户收货消息模板
     * @param order
     */
    public void sendReceived(OrderInfoRecord order) {
        logger().info("订单收货模板消息start");
        //TODO 查询配置是否发送模板消息
        //公众号数据
        String[][] mpData = null;
        if(isSendMp(MessageTemplateConfigConstant.GET_GOODS)) {
            mpData = new String[][] {{"亲，您买的宝贝已确认收货"}, {order.getOrderSn()}, {getGoodsName(orderGoods.getByOrderId(order.getOrderId()))}, {DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL, order.getCreateTime())}, {DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL, order.getShippingTime())}, {DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL, order.getConfirmTime())}, {"感谢您的支持与厚爱"}};
        }
        RabbitMessageParam param = RabbitMessageParam.builder()
            .mpTemplateData(MpTemplateData.builder().config(MpTemplateConfig.ORDER_RECEIVED).data(mpData).build())
            .page("pages/orderinfo/orderinfo?order_sn=" + order.getOrderSn())
            .shopId(getShopId())
            .userIdList(Collections.singletonList(order.getUserId()))
            .type(RabbitParamConstant.Type.MA_SUBSCRIBEMESSAGE_TYPE).build();
        saas.taskJobMainService.dispatchImmediately(param, RabbitMessageParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
        logger().info("订单收货模板消息end");
    }


    private String getGoodsName(List<OrderGoodsRecord> orderGoods) {
        return getString(orderGoods.get(0).getGoodsName(), orderGoods.stream().mapToInt(OrderGoodsRecord::getGoodsNumber).sum(), orderGoods.size());
    }

    private String getReturnGoodsName(List<ReturnOrderGoodsRecord> orderGoods) {
        return getString(orderGoods.get(0).getGoodsName(), orderGoods.stream().mapToInt(ReturnOrderGoodsRecord::getGoodsNumber).sum(), orderGoods.size());
    }

    private String getString(String goodsName, int sum, int size) {
        StringBuilder result = new StringBuilder(goodsName);
        if(result.length() > 32){
            result.substring(0, 32);
        }
        result.append(size == 1 ? StringUtils.EMPTY : "等").append(sum).append("件");
        return result.toString();
    }

    private boolean isSendMp(Integer id) {
        MessageConfigVo messageConfig = this.messageConfig.getMessageConfig(id);
        if(messageConfig != null && messageConfig.getOpenMp().equals(OrderConstant.YES)) {
            return true;
        }
        return false;
    }

}
