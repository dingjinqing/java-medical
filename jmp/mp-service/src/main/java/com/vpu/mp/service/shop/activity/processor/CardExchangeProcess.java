package com.vpu.mp.service.shop.activity.processor;

import com.beust.jcommander.internal.Lists;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.ReturnOrderRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.card.CardConsumpData;
import com.vpu.mp.service.pojo.shop.member.card.dao.CardFullDetail;
import com.vpu.mp.service.pojo.shop.operation.RemarkTemplate;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.refund.OrderReturnGoodsVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailCapsuleParam;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GoodsDetailMpBo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.shop.card.wxapp.WxCardExchangeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王帅
 * 限次卡兑换
 */
@Service
public class CardExchangeProcess extends WxCardExchangeService implements Processor,GoodsDetailProcessor,CreateOrderProcessor {

    @Override
    public Byte getPriority() {
        return 1;
    }

    @Override
    public Byte getActivityType() {
        return BaseConstant.ACTIVITY_TYPE_EXCHANG_ORDER;
    }

    @Override
    public void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException {
        CardFullDetail cardDetail = userCard.memberCardService.getCardDetailByNo(param.getMemberCardNo());
        orderCheck(param,cardDetail);
        orderInit(param,cardDetail);
    }

    private void orderCheck(OrderBeforeParam param, CardFullDetail cardDetail) throws MpException {
        //校验是否可以兑换
        if(!CardUtil.canExchangGoods(cardDetail.getMemberCard().getIsExchang())) {
            throw new MpException(JsonResultCode.CODE_ORDER_CARD_EXCHGE_NO_EXCHGE_GOODS);
        }
        //校验是否可以兑换此商品
        if(CardUtil.isExchangPartGoods(cardDetail.getMemberCard().getIsExchang())) {
            List<String> exchangGoodsIds = Lists.newArrayList(cardDetail.getMemberCard().getExchangGoods());
            for (Integer goodsId: param.getGoodsIds()) {
                if(!exchangGoodsIds.contains(goodsId.toString())) {
                    throw new MpException(JsonResultCode.CODE_ORDER_CARD_EXCHGE_NO_EXCHGE_GOODS);
                }
            }
        }
        //剩余兑换商品次数校验/周期次数限制
        Integer numLimit = cardDetail.getUserCard().getExchangSurplus();
        if(numLimit < param.getGoods().stream().map(OrderBeforeParam.Goods::getGoodsNumber).reduce(0, Integer::sum)) {
            throw new MpException(JsonResultCode.CODE_ORDER_CARD_EXCHGE_NUMBER_LIMIT);
        }
    }

    private void orderInit(OrderBeforeParam param, CardFullDetail cardDetail) {
        //免运费
        if(CardUtil.isFreeShipping(cardDetail.getMemberCard().getExchangFreight())) {
            param.setIsFreeShippingAct(OrderConstant.YES);
        }
    }

    @Override
    public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
        //限次卡兑换不会存在待付款状态直接可以扣次数
        CardConsumpData cardConsumpData = new CardConsumpData();
        cardConsumpData.setUserId(order.getUserId());
        cardConsumpData.setCardId(param.getActivityId());
        cardConsumpData.setCardNo(order.getCardNo());
        cardConsumpData.setReasonId(RemarkTemplate.ORDER_LIMIT_EXCHGE_GOODS.code);
        cardConsumpData.setType(CardConstant.MCARD_TP_LIMIT);
        cardConsumpData.setOrderSn(order.getOrderSn());
        cardConsumpData.setExchangeCount((short)(-param.getBos().stream().filter(x-> x.getIsGift() != null && x.getIsGift() == OrderConstant.NO).map(OrderGoodsBo::getGoodsNumber).reduce(0, Integer::sum)));
        cardConsumpData.setPayment("");
        mCardSvc.updateMemberCardExchangeSurplus(cardConsumpData);
    }

    @Override
    public void processOrderEffective(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
        userCheckedGoodsSvc.removeGoodsAfterOrder(order.getCardNo());
    }

    @Override
    public void processUpdateStock(OrderBeforeParam param, OrderInfoRecord order) throws MpException {

    }

    @Override
    public void processReturn(ReturnOrderRecord returnOrderRecord, Integer activityId, List<OrderReturnGoodsVo> returnGoods) throws MpException {

    }

    @Override
    public void processGoodsDetail(GoodsDetailMpBo capsule, GoodsDetailCapsuleParam param) {

    }
}
