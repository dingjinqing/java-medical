package com.vpu.mp.service.shop.market.prize;

import com.vpu.mp.db.shop.tables.records.PrizeRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.goods.spec.ProductSmallInfoVo;
import com.vpu.mp.service.pojo.wxapp.market.prize.PrizeRecordParam;
import com.vpu.mp.service.pojo.wxapp.market.prize.PrizeRecordVo;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsMpVo;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.order.goods.OrderGoodsService;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.PRIZE_RECORD;
import static com.vpu.mp.service.pojo.wxapp.market.prize.PrizeConstant.PRIZE_STATUS_EXPIRE;
import static com.vpu.mp.service.pojo.wxapp.market.prize.PrizeConstant.PRIZE_STATUS_RECEIVED;
import static com.vpu.mp.service.pojo.wxapp.market.prize.PrizeConstant.PRIZE_STATUS_UNCLAIMED;

/**
 * 我的奖品
 * @author 孔德成
 * @date 2020/1/3 9:25
 */
@Service
public class PrizeRecordService extends ShopBaseService {

    @Autowired
    private OrderGoodsService orderGoodsService;
    @Autowired
    private GoodsService  goodsService;


    /**
     * 增加奖品记录
     * @param userId
     * @param actId
     * @param recordId
     * @param type
     * @param prdId
     * @param day
     */
    public PrizeRecordRecord savePrize(Integer userId,Integer actId,Integer recordId,Byte type,Integer prdId,Integer day){
        PrizeRecordRecord prizeRecord =db().newRecord(PRIZE_RECORD);
        prizeRecord.setUserId(userId);
        prizeRecord.setActivityId(actId);
        prizeRecord.setRecordId(recordId);
        prizeRecord.setActivityType(type);
        prizeRecord.setPrdId(prdId);
        prizeRecord.setExpiredDay(day);
        prizeRecord.setExpiredTime(DateUtil.getTimeStampPlus(day, ChronoUnit.DAYS));
        prizeRecord.setPrizeStatus(PRIZE_STATUS_UNCLAIMED);
        prizeRecord.insert();
        return prizeRecord;
    }

    /**
     * 查询奖品记录
     * @param userId
     * @param param
     * @return
     */
    public PageResult<PrizeRecordVo> getList(Integer userId,PrizeRecordParam param){
        SelectConditionStep<Record> select = db().select().from(PRIZE_RECORD).where(PRIZE_RECORD.USER_ID.eq(userId))
                .and(PRIZE_RECORD.PRIZE_STATUS.eq(param.getStatus()));
        PageResult<PrizeRecordVo> pageResult = getPageResult(select, param, PrizeRecordVo.class);
        List<String> orderSns = pageResult.getDataList().stream().map(PrizeRecordVo::getOrderSn).collect(Collectors.toList());
        Map<String, List<OrderGoodsMpVo>> orderGoodsMap = orderGoodsService.getByOrderGoodsSns(orderSns);
        pageResult.getDataList().forEach(prizeRecord ->{
            List<OrderGoodsMpVo> orderGoodsMpVos = orderGoodsMap.get(prizeRecord.getOrderSn());
            if (!prizeRecord.getPrizeStatus().equals(PRIZE_STATUS_UNCLAIMED)||orderGoodsMpVos==null){
                ProductSmallInfoVo product= goodsService.getProductVoInfoByProductId(prizeRecord.getPrdId());
                OrderGoodsMpVo orderGoodsMpVo = new OrderGoodsMpVo();
                orderGoodsMpVo.setProductId(prizeRecord.getPrdId());
                orderGoodsMpVo.setGoodsAttr(product.getPrdDesc());
                orderGoodsMpVo.setGoodsImg(product.getGoodsImg());
                orderGoodsMpVo.setGoodsName(product.getGoodsName());
                orderGoodsMpVo.setGoodsId(product.getGoodsId());
                prizeRecord.setOrderGoodsMpVo(orderGoodsMpVo);
            }else {
                prizeRecord.setOrderGoodsMpVo(orderGoodsMpVos.get(0));
            }
        } );
        return pageResult;
    }

    /**
     *
     * @param userId
     * @param activityId
     */
    public void checkedPrize(Integer userId, Integer activityId) {
        PrizeRecordRecord prizeRecord = getById(activityId);

    }

    /**
     * 修改记录为已领取
     * @param id
     * @param orderSn
     * @return
     */
    public int updateReceivedPrize(Integer id, String orderSn){
        return db().update(PRIZE_RECORD)
                .set(PRIZE_RECORD.PRIZE_STATUS,PRIZE_STATUS_RECEIVED)
                .set(PRIZE_RECORD.ORDER_SN,orderSn)
                .where(PRIZE_RECORD.ID.eq(id)).execute();
    }

    public PrizeRecordRecord getById(Integer id){
        return db().selectFrom(PRIZE_RECORD).where(PRIZE_RECORD.ID.eq(id)).fetchOne();
    }

    /**
     * 定时过期的奖品
     */
    public void closePrizeGoods() {
        Timestamp localDateTime = DateUtil.getLocalDateTime();
        db().update(PRIZE_RECORD).set(PRIZE_RECORD.PRIZE_STATUS,PRIZE_STATUS_EXPIRE)
                .where(PRIZE_RECORD.PRIZE_STATUS.eq(PRIZE_STATUS_UNCLAIMED))
                .and(PRIZE_RECORD.EXPIRED_TIME.lt(localDateTime)).execute();

    }

}
