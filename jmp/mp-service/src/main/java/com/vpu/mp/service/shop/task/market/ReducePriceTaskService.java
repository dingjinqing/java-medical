package com.vpu.mp.service.shop.task.market;

import com.vpu.mp.db.shop.tables.records.ReducePriceRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.market.reduceprice.ReducePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Stream;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.ReducePrice.REDUCE_PRICE;
import static com.vpu.mp.db.shop.tables.ReducePriceGoods.REDUCE_PRICE_GOODS;
import static java.util.stream.Collectors.toList;

/**
 * 监控限时降价活动导致的goods表goodsType变化
 * @author: 王兵兵
 * @create: 2019-12-03 16:44
 **/
@Service
public class ReducePriceTaskService  extends ShopBaseService {
    @Autowired
    private GoodsService goodsService;

    public void monitorGoodsType(){
        //目前Goods表里还是限时降价类型的商品
        List<Integer> pastReducePriceGoodsIdList = getPastReducePriceGoodsId();
        //在活动有效期内的限时降价记录的ID列表
        List<Integer> currentReducePriceGoodsIdList = getCurrentReducePriceGoodsIdList();

        //求差集
        List<Integer> changeToNormalGoodsIds = Util.diffList(pastReducePriceGoodsIdList,currentReducePriceGoodsIdList);
        List<Integer> changeToActGoodsIds = Util.diffList(currentReducePriceGoodsIdList,pastReducePriceGoodsIdList);

        if(changeToNormalGoodsIds != null && changeToNormalGoodsIds.size() > 0){
            //活动已失效，将goodsType改回去
            goodsService.changeToNormalType(changeToNormalGoodsIds);
            //TODO 记录变动
        }
        if(changeToActGoodsIds != null && changeToActGoodsIds.size() > 0){
            //有新的活动生效，商品goodsType标记活动类型
            changeToReducePriceType(changeToActGoodsIds);
            //TODO 记录变动
        }
    }

    /**
     * 当前有效的限时降价活动下属的goodsId列表
     * @return
     */
    private List<Integer> getCurrentReducePriceGoodsIdList(){
        List<ReducePriceRecord> reducePriceRecordList =  db().select().from(REDUCE_PRICE).where(REDUCE_PRICE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(REDUCE_PRICE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(REDUCE_PRICE.START_TIME.lt(DateUtil.getLocalDateTime())).and(REDUCE_PRICE.END_TIME.gt(DateUtil.getLocalDateTime()))).fetchInto(ReducePriceRecord.class);
        List<Integer> reducePriceIds = new ArrayList<>();
        reducePriceRecordList.forEach(reducePriceRecord -> {
            if(isValidPeriodReducePriceAct(reducePriceRecord)){
                reducePriceIds.add(reducePriceRecord.getId());
            }
        });
        if(reducePriceIds.size() > 0){
            List<Integer> goodsIds = db().selectDistinct(REDUCE_PRICE_GOODS.GOODS_ID).from(REDUCE_PRICE_GOODS).where(REDUCE_PRICE_GOODS.REDUCE_PRICE_ID.in(reducePriceIds)).fetchInto(Integer.class);
            return goodsIds;
        }
        return Collections.emptyList();
    }

    /**
     * 判断限时降价活动当前是否有效
     * @param reducePriceRecord
     * @return
     */
    private boolean isValidPeriodReducePriceAct(ReducePriceRecord reducePriceRecord){
        if(ReducePriceService.PERIOD_ACTION_NORMAL.equals(reducePriceRecord.getPeriodAction())){
            //不按周期重复
            if(reducePriceRecord.getStartTime().before(DateUtil.getLocalDateTime()) && reducePriceRecord.getEndTime().after(DateUtil.getLocalDateTime())){
                return true;
            }
        }else{
            String []periodString = reducePriceRecord.getPointTime().split("@");
            Timestamp periodStart = DateUtil.convertToTimestamp(DateUtil.dateFormat(DateUtil.DATE_FORMAT_SIMPLE) + " " + periodString[0] + ":00");
            Timestamp periodEnd = DateUtil.convertToTimestamp(DateUtil.dateFormat(DateUtil.DATE_FORMAT_SIMPLE) + " " + periodString[1] + ":00");
            Calendar calendar = Calendar.getInstance();
            if(DateUtil.getLocalDateTime().after(periodStart) && DateUtil.getLocalDateTime().before(periodEnd)){
                if(ReducePriceService.PERIOD_ACTION_EVERY_DAY.equals(reducePriceRecord.getPeriodAction())){
                    //按天重复
                    return true;
                }else {
                    List<Integer> days = Util.valueOf(reducePriceRecord.getExtendTime().split("@"));
                    if(ReducePriceService.PERIOD_ACTION_EVERY_MONTH.equals(reducePriceRecord.getPeriodAction())){
                        //按月重复
                        if(days.contains(calendar.get(Calendar.DATE))){
                            return true;
                        }
                    }else if(ReducePriceService.PERIOD_ACTION_EVERY_WEEK.equals(reducePriceRecord.getPeriodAction())){
                        //按周重复
                        if(days.contains(calendar.get(Calendar.DAY_OF_WEEK) - 1)){
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    /**
     * 当前所有goodsType还是限时降价的商品ID（某些商品可能已经过期，需要更新回普通商品）
     * @return
     */
    private List<Integer> getPastReducePriceGoodsId(){
        return db().select(GOODS.GOODS_ID).from(GOODS).where(GOODS.GOODS_TYPE.eq(BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE)).fetchInto(Integer.class);
    }

    /**
     * 批量将活动商品改成限时降价商品
     * @param goodsIds
     */
    private void changeToReducePriceType(List<Integer> goodsIds){
        //比限时降价优先级高的活动，不覆盖goodsType是这些的商品（1、3、5、10）
        List<Byte> highPriorityAct = Stream.of(BaseConstant.ACTIVITY_TYPE_GROUP_BUY,BaseConstant.ACTIVITY_TYPE_BARGAIN,BaseConstant.ACTIVITY_TYPE_SEC_KILL,BaseConstant.ACTIVITY_TYPE_PRE_SALE).collect(toList());
        db().update(GOODS).set(GOODS.GOODS_TYPE, BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE).where(GOODS.GOODS_TYPE.notIn(highPriorityAct).and(GOODS.GOODS_ID.in(goodsIds))).execute();
    }

}
