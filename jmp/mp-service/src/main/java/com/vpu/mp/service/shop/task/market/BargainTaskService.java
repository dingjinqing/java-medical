package com.vpu.mp.service.shop.task.market;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.jedis.data.DBOperating;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.es.EsDataUpdateMqService;
import com.vpu.mp.service.shop.market.bargain.BargainRecordService;
import jodd.util.StringUtil;
import org.jooq.Record2;
import org.jooq.Record4;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.Bargain.BARGAIN;
import static com.vpu.mp.db.shop.tables.BargainRecord.BARGAIN_RECORD;
import static java.util.stream.Collectors.toList;

/**
 * @author: 王兵兵
 * @create: 2019-12-09 17:07
 **/
@Service
public class BargainTaskService extends ShopBaseService {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private EsDataUpdateMqService esDataUpdateMqService;

    /**
     * 监控goodsType
     */
    public void monitorGoodsType(){
        //目前Goods表里还是砍价类型的商品
        List<Integer> pastBargainGoodsIdList = getPastBargainGoodsId();
        //在活动有效期内的砍价记录的ID列表
        List<Integer> currentBargainGoodsIdList = getCurrentBargainGoodsIdList();

        //求差集
        List<Integer> changeToNormalGoodsIds = Util.diffList(pastBargainGoodsIdList,currentBargainGoodsIdList);
        List<Integer> changeToActGoodsIds = Util.diffList(currentBargainGoodsIdList,pastBargainGoodsIdList);

        if(changeToNormalGoodsIds != null && changeToNormalGoodsIds.size() > 0){
            //活动已失效，将goodsType改回去
            goodsService.changeToNormalType(changeToNormalGoodsIds);
            //异步更新ES
            esDataUpdateMqService.addEsGoodsIndex(changeToNormalGoodsIds,getShopId(), DBOperating.UPDATE);
            //TODO 记录变动
        }

        if(changeToActGoodsIds != null && changeToActGoodsIds.size() > 0){
            //有新的活动生效，商品goodsType标记活动类型
            changeToBargainType(changeToActGoodsIds);
            //刷新砍价库存
            updateBargainGoodsStock(changeToActGoodsIds);
            //异步更新ES
            esDataUpdateMqService.addEsGoodsIndex(changeToActGoodsIds,getShopId(), DBOperating.UPDATE);
            //TODO 记录变动
        }
    }

    /**
     * 关闭已经失效的砍价记录
     */
    public void closeBargainRecord(){
        //活动已失效，但砍价状态还在进行中的发起记录
        List<Record4<Integer, Integer,Integer,String>> records = db().select(BARGAIN_RECORD.ID,BARGAIN_RECORD.USER_ID,BARGAIN.ID,BARGAIN.REWARD_COUPON_ID).from(BARGAIN_RECORD.leftJoin(BARGAIN).on(BARGAIN_RECORD.BARGAIN_ID.eq(BARGAIN.ID))).where(
            (BARGAIN.DEL_FLAG.eq(DelFlag.DISABLE_VALUE)
            .or(BARGAIN.STATUS.eq(BaseConstant.ACTIVITY_STATUS_DISABLE))
            .or(BARGAIN.START_TIME.gt(DateUtil.getLocalDateTime()))
            .or(BARGAIN.END_TIME.lt(DateUtil.getLocalDateTime())))
            .and(BARGAIN_RECORD.STATUS.eq(BargainRecordService.STATUS_IN_PROCESS))
        ).fetch();
        if(records != null && records.size() > 0){
            //更新砍价记录状态为失败
            List<Integer> recordIdList = records.stream().map(Record4::value1).collect(toList());
            db().update(BARGAIN_RECORD).set(BARGAIN_RECORD.STATUS,BargainRecordService.STATUS_FAILED).where(BARGAIN_RECORD.ID.in(recordIdList)).execute();

            //过滤不用发优惠券的发起记录
            records = records.stream().filter(record -> StringUtil.isNotEmpty(record.value4())).collect(Collectors.toList());
            //按照砍价活动ID分组
            Map<Integer,List<Record4<Integer, Integer,Integer,String>>> couponBargainRecords = records.stream().collect(Collectors.groupingBy(Record4::value3));

            if(couponBargainRecords != null && !couponBargainRecords.isEmpty()){
                couponBargainRecords.forEach((bargainId,recordList) -> {
                    //发放优惠券
                    CouponGiveQueueParam newParam = new CouponGiveQueueParam(
                        getShopId(), recordList.stream().map(Record4::value2).collect(toList()), bargainId, recordList.get(0).value4().split(","), BaseConstant.ACCESS_MODE_ISSUE, BaseConstant.GET_SOURCE_ACT);
                    //队列异步发放
                    saas.taskJobMainService.dispatchImmediately(newParam, CouponGiveQueueParam.class.getName(), getShopId(), TaskJobsConstant.TaskJobEnum.GIVE_COUPON.getExecutionType());
                });
            }
        }
    }
    
    private List<Integer> getPastBargainGoodsId(){
        return db().select(GOODS.GOODS_ID).from(GOODS).where(GOODS.GOODS_TYPE.eq(BaseConstant.ACTIVITY_TYPE_BARGAIN)).fetchInto(Integer.class);
    }

    private List<Integer> getCurrentBargainGoodsIdList(){
        return db().select(BARGAIN.GOODS_ID).from(BARGAIN).where(
            BARGAIN.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)
            .and(BARGAIN.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(BARGAIN.START_TIME.lt(DateUtil.getLocalDateTime()))
            .and(BARGAIN.END_TIME.gt(DateUtil.getLocalDateTime()))
        ).fetchInto(Integer.class);
    }

    /**
     * 批量将活动商品改成砍价商品
     * @param goodsIds
     */
    private void changeToBargainType(List<Integer> goodsIds){
        //比砍价优先级高的活动，不覆盖goodsType是这些的商品（5、10）
        List<Byte> highPriorityAct = Stream.of(BaseConstant.ACTIVITY_TYPE_SEC_KILL,BaseConstant.ACTIVITY_TYPE_PRE_SALE).collect(toList());
        db().update(GOODS).set(GOODS.GOODS_TYPE, BaseConstant.ACTIVITY_TYPE_BARGAIN).where(GOODS.GOODS_TYPE.notIn(highPriorityAct).and(GOODS.GOODS_ID.in(goodsIds))).execute();
    }

    /**
     * 刷新砍价库存
     * @param goodsIds
     */
    private void updateBargainGoodsStock(List<Integer> goodsIds){
        Result<Record2<Integer, Integer>> records = db().select(BARGAIN.ID,GOODS.GOODS_NUMBER).from(
            BARGAIN.innerJoin(GOODS).on(BARGAIN.GOODS_ID.eq(GOODS.GOODS_ID).and(BARGAIN.STOCK.gt(GOODS.GOODS_NUMBER)))
        ).where(
            BARGAIN.DEL_FLAG.eq(DelFlag.NORMAL_VALUE)
            .and(BARGAIN.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(BARGAIN.START_TIME.lt(DateUtil.getLocalDateTime()))
            .and(BARGAIN.END_TIME.gt(DateUtil.getLocalDateTime()))
            .and(BARGAIN.GOODS_ID.in(goodsIds))
        ).fetch();
        if(records != null && records.isNotEmpty()){
            records.forEach(r->{
                db().update(BARGAIN).set(BARGAIN.STOCK,r.value2()).where(BARGAIN.ID.eq(r.value1())).execute();
            });
        }
    }
}
