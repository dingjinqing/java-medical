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
import com.vpu.mp.service.pojo.shop.express.ExpressVo;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.market.message.RabbitParamConstant;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateConfig;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateData;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateData;
import com.vpu.mp.service.shop.express.ExpressService;
import com.vpu.mp.service.shop.user.message.maConfig.SubcribeTemplateCategory;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 订单操作发送模板消息
 */
@Service
public class OrderOperateSendMessage extends ShopBaseService {

    @Autowired
    private ExpressService express;

    /**
     * 发货模板消息
     * @param order
     * @param recordList
     */
    public void send(OrderInfoRecord order, ArrayList<OrderGoodsRecord> recordList) {
        logger().info("发货模板消息start");
        //商品名称
        String goodsName = recordList.get(0).getGoodsName().length() > 20 ? recordList.get(0).getGoodsName().substring(0, 19) : recordList.get(0).getGoodsName();
        //快递公司名称
        ExpressVo expressVo = express.get(order.getShippingId());
        String shippingName = expressVo == null ? "other" : expressVo.getShippingName();
        //小程序数据
        String[][] maData = new String[][] { { goodsName }, { order.getOrderSn() }, { shippingName }, { order.getShippingNo() }, { order.getCompleteAddress() }, { Util.getdate(DateUtil.DATE_FORMAT_FULL) }};
        //公众号数据
        String[][] mpData = new String[][] { { "亲，宝贝已经启程了，好想快点来到你身边" }, { order.getOrderSn() }, { shippingName }, { order.getShippingNo() }, {StringUtils.EMPTY}};
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
        String goodsName = returnGoods.get(0).getGoodsName().length() > 20 ? returnGoods.get(0).getGoodsName().substring(0, 19) : returnGoods.get(0).getGoodsName();
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
            String[][] mpData = new String[][] { { StringUtils.EMPTY }, { returnOrder.getReasonDesc() }, { money }, { StringUtils.EMPTY }};
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
            String[][] mpData = new String[][] { { "您的退款审核被拒绝" }, { returnOrder.getOrderSn() }, { applyTime }, { money }, { refuseReason }, { "具体信息请查看详情" }};
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

}
