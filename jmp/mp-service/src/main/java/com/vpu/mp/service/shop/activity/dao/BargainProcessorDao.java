package com.vpu.mp.service.shop.activity.dao;

import com.google.common.base.Functions;
import com.vpu.mp.db.shop.tables.records.BargainRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.bargain.BargainMpVo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.BARGAIN_RECORD;
import static com.vpu.mp.db.shop.Tables.BARGAIN_USER_LIST;
import static com.vpu.mp.db.shop.tables.Bargain.BARGAIN;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
public class BargainProcessorDao extends ShopBaseService {
    /**
     * 获取商品集合内的砍价信息
     *
     * @param goodsIds 商品id集合
     * @param date     日期
     * @return List<BargainRecord>
     */
    public Map<Integer, BargainRecord> getGoodsBargainListInfo(List<Integer> goodsIds, Timestamp date) {
        return db().select(BARGAIN.ID, BARGAIN.GOODS_ID, BARGAIN.BARGAIN_TYPE, BARGAIN.FLOOR_PRICE, BARGAIN.EXPECTATION_PRICE)
            .from(BARGAIN)
            .where(BARGAIN.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(BARGAIN.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(BARGAIN.GOODS_ID.in(goodsIds)).and(BARGAIN.START_TIME.lt(date)).and(BARGAIN.END_TIME.gt(date))
            .fetchInto(BargainRecord.class).stream().collect(Collectors.toMap(BargainRecord::getGoodsId, Functions.identity(),(x1,x2)->x1));
    }

    /**
     * 小程序-商品详情-获取砍价详情
     * @param activityId
     * @return
     */
    public BargainMpVo getBargainInfo(Integer userId,Integer activityId) {
        BargainMpVo vo = new BargainMpVo();
        vo.setActivityId(activityId);
        vo.setActivityType(BaseConstant.ACTIVITY_TYPE_BARGAIN);

        Timestamp now = DateUtil.getLocalDateTime();

        BargainRecord bargainRecord = db().selectFrom(BARGAIN).where(BARGAIN.ID.eq(activityId).and(BARGAIN.DEL_FLAG.eq(DelFlag.NORMAL.getCode())))
            .fetchAny();

        Byte aByte = canApplyBargain(userId, now, bargainRecord);
        vo.setActState(aByte);

        // 活动不存在
        if (BaseConstant.ACTIVITY_STATUS_NOT_HAS.equals(vo.getActState())) {
            return vo;
        }
        // 活动未开始
        if (BaseConstant.ACTIVITY_STATUS_NOT_START.equals(aByte)) {
            vo.setStartTime(bargainRecord.getStartTime().getTime() - now.getTime());
        }
        vo.setEndTime(bargainRecord.getEndTime().getTime() - now.getTime());

        // 设置砍价展示价格
        vo.setBargainPrice(GoodsConstant.BARGAIN_TYPE_FIXED.equals(bargainRecord.getBargainType())?bargainRecord.getExpectationPrice():bargainRecord.getFloorPrice());
        vo.setBargainType(bargainRecord.getBargainType());
        // 砍价库存，处理器里面还需要判断商品数量是否足够
        vo.setStock(bargainRecord.getStock());
        // 砍价活动表 免运费为1 不免运费为0,正好和前端相反
        vo.setFreeShip((byte) (bargainRecord.getFreeFreight()==1?0:1));
        vo.setBargainJoinNum(getBargainJoinNum(activityId));
        return vo;
    }

    /**
     * 查询某一砍价活动的参与人数
     * @param activityId 活动id
     * @return 参与的人数
     */
    private Integer getBargainJoinNum(Integer activityId){
        return db().fetchCount(BARGAIN_RECORD.innerJoin(BARGAIN_USER_LIST).on(BARGAIN_RECORD.ID.eq(BARGAIN_USER_LIST.RECORD_ID)), BARGAIN_RECORD.BARGAIN_ID.eq(activityId));
    }

    /**
     * 判断用户是否可以发起砍价
     * @param userId 用户id
     * @param date 时间
     * @param bargainRecord 砍价详情
     * @return
     */
    public Byte canApplyBargain(Integer userId, Timestamp date, BargainRecord bargainRecord) {
        logger().debug("小程序-商品详情-砍价信息-是否可以发起砍价判断");
        if (date == null) {
            date = DateUtil.getLocalDateTime();
        }

        if (bargainRecord == null) {
            logger().debug("小程序-商品详情-砍价信息-活动不存在或已删除[activityId:{}]",bargainRecord.getId());
            return  BaseConstant.ACTIVITY_STATUS_NOT_HAS;
        }

        if (BaseConstant.ACTIVITY_STATUS_DISABLE.equals(bargainRecord.getStatus())) {
            logger().debug("小程序-商品详情-砍价信息-该活动未启用[activityId:{}]",bargainRecord.getId());
            return BaseConstant.ACTIVITY_STATUS_STOP;
        }

        if (bargainRecord.getStartTime().compareTo(date) > 0) {
            logger().debug("活动未开始[activityId:{}]",bargainRecord.getId());
            return BaseConstant.ACTIVITY_STATUS_NOT_START;
        }

        if (bargainRecord.getEndTime().compareTo(date) < 0) {
            logger().debug("活动已经结束[activityId:{}]", bargainRecord.getId());
            return BaseConstant.ACTIVITY_STATUS_END;
        }

        int bargainCount = db().fetchCount(BARGAIN_RECORD,BARGAIN_RECORD.DEL_FLAG.eq(DelFlag.NORMAL.getCode()).and(BARGAIN_RECORD.BARGAIN_ID.eq(bargainRecord.getId()))
            .and(BARGAIN_RECORD.USER_ID.eq(userId)).and(BARGAIN_RECORD.STATUS.eq((byte) 0)));
        if (bargainCount > 0) {
            logger().debug("用户存在正在砍价[activityId:{}]", bargainRecord.getId());
            return BaseConstant.ACTIVITY_STATUS_MAX_COUNT_LIMIT;
        }


        return BaseConstant.ACTIVITY_STATUS_CAN_USE;
    }


    //砍价下单

    /**
     * 处理砍价订单的价格
     * @param param
     */
    public void setOrderPrdBargainPrice(OrderBeforeParam param){
        //TODO
    }
}
