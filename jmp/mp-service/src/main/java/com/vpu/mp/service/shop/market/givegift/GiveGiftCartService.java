package com.vpu.mp.service.shop.market.givegift;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.givegift.record.GiveGiftRecordListParam;
import com.vpu.mp.service.pojo.shop.market.givegift.record.GiveGiftRecordListVo;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *  礼物车
 * @author 孔德成
 * @date 2019/8/16 18:40
 */
@Service
public class GiveGiftCartService extends ShopBaseService {

    @Autowired
    private OrderInfoService orderInfo;

    /**
     *  根据活动ID查询 送礼数量--已经交易的礼物车数量
     * @param activityId 活动id
     * @return 返回活动的数量
     */
    public Integer getSendGiftOrderCt(Integer activityId) {
         return orderInfo.getSendGiftOrderCt(activityId);
    }

    /**
     * 送礼明细列表
     * @param param  GiveGiftRecordListParam
     * @return PageResult<GiveGiftRecordListVo>
     */
    public PageResult<GiveGiftRecordListVo> giveGiftRecordList(GiveGiftRecordListParam param) {
        SelectConditionStep<? extends Record> selectConditionStep = orderInfo.giveGiftRecordList(param);

        PageResult<GiveGiftRecordListVo> pageResult = getPageResult(selectConditionStep, param.getCurrentPage(), param.getPageRows(), GiveGiftRecordListVo.class);
        return pageResult;
    }



}
