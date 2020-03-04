package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.db.shop.tables.records.IntegralMallDefineRecord;
import com.vpu.mp.db.shop.tables.records.IntegralMallProductRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertProductVo;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertSelectParam;
import com.vpu.mp.service.pojo.shop.market.integralconvert.IntegralConvertSelectVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.integral.IntegralMallMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.integral.IntegralMallPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.shop.market.integralconvert.IntegralConvertService;
import com.vpu.mp.service.shop.member.ScoreCfgService;
import com.vpu.mp.service.shop.member.ScoreService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.IntegralMallDefine.INTEGRAL_MALL_DEFINE;
import static com.vpu.mp.db.shop.tables.IntegralMallProduct.INTEGRAL_MALL_PRODUCT;
import static com.vpu.mp.db.shop.tables.IntegralMallRecord.INTEGRAL_MALL_RECORD;

/**
 * @author 李晓冰
 * @date 2020年03月03日
 */
@Service
public class IntegralMallProcessorDao extends IntegralConvertService {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ScoreCfgService scoreCfgService;

    /**
     * 获取积分兑换商品活动信息
     * @param activityId 活动id
     * @param userId 用户id
     * @return IntegralMallMpVo
     */
    public IntegralMallMpVo getIntegralMallInfoForDetail(Integer activityId, Integer userId) {
        IntegralMallMpVo vo = new IntegralMallMpVo();
        vo.setActivityId(activityId);
        vo.setActivityType(BaseConstant.ACTIVITY_TYPE_INTEGRAL);
        vo.setActState(BaseConstant.ACTIVITY_STATUS_CAN_USE);

        IntegralMallDefineRecord integralMallRecord = getRecordByIdDao(activityId);
        int userJoinNum = getUserJoinTime(activityId, userId);
        Timestamp now = DateUtil.getLocalDateTime();
        if (integralMallRecord == null) {
            logger().debug("小程序-商品详情-积分兑换商品信息-活动已删除");
            vo.setActState(BaseConstant.ACTIVITY_STATUS_NOT_HAS);
            return vo;
        } else if (BaseConstant.ACTIVITY_STATUS_DISABLE.equals(integralMallRecord.getStatus())) {
            logger().debug("小程序-商品详情-积分兑换商品信息-已停用");
            vo.setActState(BaseConstant.ACTIVITY_STATUS_DISABLE);
        } else if (now.compareTo(integralMallRecord.getStartTime()) < 0) {
            logger().debug("小程序-商品详情-积分兑换商品信息-活动未开始");
            vo.setStartTime((integralMallRecord.getStartTime().getTime() - now.getTime()) / 1000);
            vo.setEndTime((integralMallRecord.getEndTime().getTime() - now.getTime()) / 1000);
            vo.setActState(BaseConstant.ACTIVITY_STATUS_NOT_START);
        } else if (now.compareTo(integralMallRecord.getEndTime()) > 0) {
            logger().debug("小程序-商品详情-积分兑换商品信息-活动已结束");
            vo.setActState(BaseConstant.ACTIVITY_STATUS_END);
        } else {
            vo.setEndTime((integralMallRecord.getEndTime().getTime() - now.getTime()) / 1000);
            if (userJoinNum > integralMallRecord.getMaxExchangeNum()&&integralMallRecord.getMaxExchangeNum()!=0) {
                logger().debug("小程序-商品详情-积分兑换商品信息-用户参与达到上限");
                vo.setActState(BaseConstant.ACTIVITY_STATUS_MAX_COUNT_LIMIT);
            }
        }
        if (integralMallRecord.getMaxExchangeNum() == 0) {
            vo.setMaxExchangeNum(-1);
        } else {
            vo.setMaxExchangeNum(integralMallRecord.getMaxExchangeNum()-userJoinNum);
        }

        vo.setUserScore(scoreService.getUserScore(userId));
        vo.setRedeemNum(getUserJoinTime(activityId, null));

        List<IntegralMallProductRecord> integralPrdInfo = getIntegralPrdInfo(activityId);
        List<IntegralMallPrdMpVo> prds = integralPrdInfo.stream().map(x -> x.into(IntegralMallPrdMpVo.class)).collect(Collectors.toList());
        vo.setIntegralMallPrdMpVos(prds);
        return vo;
    }

    /**
     * 根据活动id获取活动信息
     * @param activityId 活动id
     * @return IntegralMallDefineRecord
     */
    private IntegralMallDefineRecord getRecordByIdDao(Integer activityId) {
        return db().selectFrom(INTEGRAL_MALL_DEFINE)
            .where(INTEGRAL_MALL_DEFINE.ID.eq(activityId).and(INTEGRAL_MALL_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))).fetchAny();
    }

    /**
     * 获取用户已兑换某一活动的商品总数量
     * @param activityId 活动id
     * @param userId 用户id 当id为null表示查询所有已参与的情况
     * @return 参与数量
     */
    private int getUserJoinTime(Integer activityId, Integer userId) {
        Condition condition = INTEGRAL_MALL_RECORD.INTEGRAL_MALL_DEFINE_ID.eq(activityId);
        if (userId!=null){
            condition =condition.and(INTEGRAL_MALL_RECORD.USER_ID.eq(userId));
        }
        Integer sum = db().select(DSL.sum(INTEGRAL_MALL_RECORD.NUMBER)).where(condition).fetchOneInto(Integer.class);
        return sum== null ? 0 : sum;
    }

    /**
     * 获取活动对应的商品规格信息
     * @param activityId 活动id
     * @return List<IntegralMallProductRecord>
     */
    private List<IntegralMallProductRecord> getIntegralPrdInfo(Integer activityId){
      return  db().selectFrom(INTEGRAL_MALL_PRODUCT).where(INTEGRAL_MALL_PRODUCT.INTEGRAL_MALL_DEFINE_ID.eq(activityId))
            .fetchInto(IntegralMallProductRecord.class);
    }

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
        //
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
        if(activityInfo.getMaxExchangeNum() != 0) {
            if(currentNum > activityInfo.getMaxExchangeNum() || currentNum + getUserJoinTime(param.getWxUserInfo().getUserId(), activityInfo.getId()) > activityInfo.getMaxExchangeNum()) {
                logger().error("积分兑换活动超出兑换次数");
                throw new MpException(JsonResultCode.CODE_ORDER_ACTIVITY_NUMBER_LIMIT);
            }
        }
    }

    public void orderInit(OrderBeforeParam param, IntegralConvertSelectVo activityInfo) throws MpException {
        //不使用优惠券
        param.setCouponSn(StringUtils.EMPTY);
        //免运费
        param.setIsFreeShippingAct(OrderConstant.YES);
        //禁用货到付款、积分支付
        param.getPaymentList().remove(OrderConstant.PAY_CODE_SCORE_PAY);
        param.getPaymentList().remove(OrderConstant.PAY_CODE_COD);
        //计算价格
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
