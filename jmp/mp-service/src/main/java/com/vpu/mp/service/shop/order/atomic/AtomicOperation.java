package com.vpu.mp.service.shop.order.atomic;

import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderGoodsRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单库存、销量更新规则
 * @author 王帅
 */
@Component
public class AtomicOperation extends ShopBaseService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsSpecProductService goodsSpecProduct;

    public void updateStockAndSales(OrderInfoRecord order, List<OrderGoodsBo> goodsBo) throws MpException {
        /*if(!(OrderConstant.PAY_CODE_COD.equals(order.getPayCode()) ||
            OrderConstant.PAY_CODE_BALANCE_PAY.equals(order.getPayCode()) ||
            (OrderConstant.PAY_CODE_SCORE_PAY.equals(order.getPayCode()) && BigDecimalUtil.compareTo(order.getMoneyPaid(), BigDecimal.ZERO) == 0))) {
            //非 --货到付款 余额付款 积分兑换且无需再支付时，生成订单时不加销量减库存
            return;
        }
        if(Boolean.FALSE) {
            //TOOD 营销
        }else {

        }*/
        //TODO 第三方对接erp
        List<Integer> goodsIds = goodsBo.stream().map(OrderGoodsBo::getGoodsId).collect(Collectors.toList());
        List<Integer> proIds = goodsBo.stream().map(OrderGoodsBo::getProductId).collect(Collectors.toList());
        //商品map
        Map<Integer, GoodsRecord> goodsRecord = goodsService.getGoodsByIds(goodsIds);
        //规格map
        Map<Integer, GoodsSpecProductRecord> productRecord =goodsSpecProduct.selectSpecByProIds(proIds);
        //TODO lxb
        for (OrderGoodsBo goodsRow : goodsBo) {
            //购买数量
            int num = goodsRow.getGoodsNumber();
            //规格
            GoodsSpecProductRecord product = productRecord.get(goodsRow.getProductId());
            //商品
            GoodsRecord goods = goodsRecord.get(goodsRow.getGoodsId());
            if(product == null || goods == null){
                //商品或规格row为bull
                throw new MpException(JsonResultCode.CODE_ORDER_GOODS_NO_EXIST);
            }else {
                if(product.getPrdNumber() < num) {
                    //库存不足
                    throw new MpException(JsonResultCode.CODE_ORDER_GOODS_LOW_STOCK);
                }
            }
            //lxb add
        }
        //商品库存更新

        //活动库存更新
    }
}
