package com.vpu.mp.service.shop.order.action.base;

import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;
import com.vpu.mp.service.pojo.shop.express.ExpressVo;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.market.message.RabbitParamConstant;
import com.vpu.mp.service.pojo.shop.user.message.MaTemplateData;
import com.vpu.mp.service.shop.express.ExpressService;
import com.vpu.mp.service.shop.user.message.maConfig.SubcribeTemplateCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

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
        String[][] maData = new String[][] { { goodsName }, { order.getOrderSn() }, { shippingName }, {order.getShippingNo()} };
        RabbitMessageParam param = RabbitMessageParam.builder()
            .maTemplateData(MaTemplateData.builder().config(SubcribeTemplateCategory.ORDER_DELIVER).data(maData).build())
            .page("pages/orderinfo/orderinfo?order_sn=" + order.getOrderSn())
            .shopId(getShopId())
            .userIdList(Arrays.asList(order.getUserId()))
            .type(RabbitParamConstant.Type.MA_SUBSCRIBEMESSAGE_TYPE).build();
        saas.taskJobMainService.dispatchImmediately(param, RabbitMessageParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.SEND_MESSAGE.getExecutionType());
        logger().info("发货模板消息end");
    }
}
