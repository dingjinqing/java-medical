package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertProductVo;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertSelectParam;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertSelectVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.shop.market.integralconvert.IntegralConvertService;
import com.vpu.mp.service.shop.member.ScoreCfgService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 积分兑换
 * @author 王帅
 */
@Component
public class IntegralProcessorDao extends IntegralConvertService {

    @Autowired
    private ScoreCfgService scoreCfgService;
    
    /**
     * 活动详情
     * @param activityId
     * @return
     */
    public IntegralConvertSelectVo getDetail(Integer activityId) {
        return selectOne(new IntegralConvertSelectParam(activityId));
        
    }

    public void orderCheck(OrderBeforeParam param, IntegralConvertSelectVo activityInfo) throws MpException {
        if (activityInfo == null ||
            param.getDate().after(activityInfo.getEndTime()) ||
            DelFlag.DISABLE_VALUE.equals(activityInfo.getDelFlag()) ||
            BaseConstant.ACTIVITY_STATUS_DISABLE.equals(activityInfo.getStatus()) ||
            activityInfo.getGoodsId() == null ||
            CollectionUtils.isEmpty(activityInfo.getProductVo())) {
            logger().error("积分兑换活动停用");
            throw new MpException(JsonResultCode.CODE_ORDER_ACTIVITY_DISABLE);
        }
        //此次购买数量
        int currentNum = 0;
        Map<Integer, IntegralConvertProductVo> productMap = activityInfo.getProductVo().stream().collect(Collectors.toMap(IntegralConvertProductVo::getPrdId, Function.identity()));
        for (OrderBeforeParam.Goods goods : param.getGoods()) {
            //累加
            currentNum += goods.getGoodsNumber();

            if(productMap.get(goods.getProductId()) == null) {
                logger().error("积分兑换规格不存在");
                throw new MpException(JsonResultCode.CODE_ORDER_GOODS_NOT_EXIST);
            }
            if(productMap.get(goods.getProductId()).getStock().intValue() < goods.getGoodsNumber()) {
                logger().error("积分兑换活动库存不足");
                throw new MpException(JsonResultCode.CODE_ORDER_ACTIVITY_GOODS_OUT_OF_STOCK);
            }
        }
       /* if(activityInfo.getMaxExchangeNum() != 0) {
            if(currentNum > activityInfo.getMaxExchangeNum() || currentNum + getUserExchangeCount(param.getWxUserInfo().getUserId(), activityInfo.getId()) > activityInfo.getMaxExchangeNum()) {
                throw new MpException(JsonResultCode.CODE_ORDER_ACTIVITY_NUMBER_LIMIT);
            }
        }*/
    }

    public void orderInit(OrderBeforeParam param, IntegralConvertSelectVo activityInfo) throws MpException {
        //不使用优惠券
        param.setCouponSn(StringUtils.EMPTY);
        //积分兑换比例
        Map<Integer, IntegralConvertProductVo> productMap = activityInfo.getProductVo().stream().collect(Collectors.toMap(IntegralConvertProductVo::getScore, Function.identity()));
        for (OrderBeforeParam.Goods goods : param.getGoods()) {
            IntegralConvertProductVo prd = productMap.get(goods.getProductId());
            //实际价格 = 兑换价格 + 积分(积分换算成对应金额)
            goods.setProductPrice(
                BigDecimalUtil.add(
                    prd.getMoney(),
                    BigDecimalUtil.multiply(new BigDecimal(prd.getScore()), new BigDecimal(scoreCfgService.getScoreProportion()))));

        }
    }
}
