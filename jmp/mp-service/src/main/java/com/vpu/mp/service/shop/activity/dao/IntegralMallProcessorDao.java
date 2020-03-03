package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.db.shop.tables.records.IntegralMallDefineRecord;
import com.vpu.mp.db.shop.tables.records.IntegralMallProductRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.integral.IntegralMallMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.integral.IntegralMallPrdMpVo;
import com.vpu.mp.service.shop.member.ScoreService;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.IntegralMallDefine.INTEGRAL_MALL_DEFINE;
import static com.vpu.mp.db.shop.tables.IntegralMallProduct.INTEGRAL_MALL_PRODUCT;
import static com.vpu.mp.db.shop.tables.IntegralMallRecord.INTEGRAL_MALL_RECORD;

/**
 * @author 李晓冰
 * @date 2020年03月03日
 */
@Service
public class IntegralMallProcessorDao extends ShopBaseService {

    @Autowired
    private ScoreService scoreService;

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
            int userJoinTime = getUserJoinTime(activityId, userId);
            if (userJoinTime > integralMallRecord.getMaxExchangeNum()) {
                logger().debug("小程序-商品详情-积分兑换商品信息-用户参与达到上限");
                vo.setActState(BaseConstant.ACTIVITY_STATUS_MAX_COUNT_LIMIT);
            }
        }

        vo.setMaxExchangeNum(integralMallRecord.getMaxExchangeNum());
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
        String fieldName = "count_num";
        BigDecimal bigDecimal = db().select(DSL.sum(INTEGRAL_MALL_RECORD.NUMBER).as(DSL.field(fieldName, BigDecimal.class))).from(INTEGRAL_MALL_RECORD)
            .where(condition).fetchAny(DSL.field(fieldName, BigDecimal.class));
        return bigDecimal.intValue();
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
}
