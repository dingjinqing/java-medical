package com.vpu.mp.service.shop.market.givegift;

import com.vpu.mp.db.shop.tables.records.GiveGiftActivityRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.givegift.GiveGiftListParam;
import com.vpu.mp.service.pojo.shop.market.givegift.GiveGiftListVo;
import com.vpu.mp.service.pojo.shop.market.givegift.GiveGiftParam;
import com.vpu.mp.service.pojo.shop.market.givegift.receive.GiveGiftReceiveListParam;
import com.vpu.mp.service.pojo.shop.market.givegift.receive.GiveGiftReceiveListVo;
import com.vpu.mp.service.pojo.shop.market.givegift.record.GiveGiftRecordListParam;
import com.vpu.mp.service.pojo.shop.market.givegift.record.GiveGiftRecordListVo;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * 我要送礼
 *
 * @author 孔德成
 * @date 2019/8/15 11:59
 */
@Service
public class GiveGiftService extends ShopBaseService {

    private static final byte USE_STATUS = 1;
    private static final byte STOP_STATUS = 0;

    @Autowired
    private GiveGiftCartService giveGiftCart;
    @Autowired
    private GiveGiftReceiveService giveGiftReceive;
    /**
     * 查询送礼活动列表
     *
     * @param param
     */
    public void getGiveGiftList(GiveGiftListParam param) {
        SelectConditionStep<? extends Record> select = db()
                .select(GIVE_GIFT_ACTIVITY.ACT_NAME, GIVE_GIFT_ACTIVITY.DUE_TIME_TYPE
                        , GIVE_GIFT_ACTIVITY.START_TIME, GIVE_GIFT_ACTIVITY.END_TIME
                        , GIVE_GIFT_ACTIVITY.LEVEL, GIVE_GIFT_ACTIVITY.STATUS)
                .from(GIVE_GIFT_ACTIVITY)
                .where(GIVE_GIFT_ACTIVITY.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
        buildParam(select, param);
        select.orderBy(GIVE_GIFT_ACTIVITY.LEVEL.desc(),GIVE_GIFT_ACTIVITY.CREATE_TIME.desc());

        PageResult<GiveGiftListVo> pageResult = getPageResult(select, param.getCurrentPage(), param.getPageRows(), GiveGiftListVo.class);
        pageResult.dataList.forEach(data->{
            //送礼人数
            Integer sendGiftOrderCt = giveGiftCart.getSendGiftOrderCt(data.getId());
            //收礼人数
            Integer getGiftOrderCt = giveGiftReceive.getGetGiftOrderCt(data.getId());
            data.setSendOrderNumber(sendGiftOrderCt);
            data.setGetOrderMunber(getGiftOrderCt);
        });

    }

    private void buildParam(SelectConditionStep<? extends Record> select, GiveGiftListParam param) {
        Timestamp nowTime =new Timestamp(System.currentTimeMillis());
        switch (param.getNavType()) {
            case 1:
                select.and(GIVE_GIFT_ACTIVITY.STATUS.eq(USE_STATUS))
                        .and(GIVE_GIFT_ACTIVITY.DUE_TIME_TYPE.eq((byte) 1)
                            .or(    GIVE_GIFT_ACTIVITY.DUE_TIME_TYPE.eq((byte) 0)
                                    .and(GIVE_GIFT_ACTIVITY.START_TIME.le(nowTime))
                                    .and(GIVE_GIFT_ACTIVITY.END_TIME.ge(nowTime))
                            )
                        );
                break;
            case 2:
                select.and(GIVE_GIFT_ACTIVITY.STATUS.eq(USE_STATUS))
                        .and(GIVE_GIFT_ACTIVITY.DUE_TIME_TYPE.eq((byte) 0))
                        .and(GIVE_GIFT_ACTIVITY.START_TIME.gt(nowTime));
                break;
            case 3:
                select.and(GIVE_GIFT_ACTIVITY.STATUS.eq(USE_STATUS))
                        .and(GIVE_GIFT_ACTIVITY.DUE_TIME_TYPE.eq((byte) 0))
                        .and(GIVE_GIFT_ACTIVITY.END_TIME.le(nowTime));
                break;
            case 4:
                select.and(GIVE_GIFT_ACTIVITY.STATUS.eq((byte) 0));
                break;
            default:

        }
    }


    /**
     * 获取活动详情
     *
     * @param id id
     * @return record
     */
    public GiveGiftActivityRecord getGiveGiftById(Integer id) {
        return db().selectFrom(GIVE_GIFT_ACTIVITY).where(GIVE_GIFT_ACTIVITY.ID.eq(id)).fetchOne();
    }

    /**
     * 添加送礼活动
     *
     * @param param 活动参数
     * @return 1 添加成功
     */
    public Integer addGiveGift(GiveGiftParam param) {
        GiveGiftActivityRecord giveGiftRecord = db().newRecord(GIVE_GIFT_ACTIVITY, param);
        giveGiftRecord.setId(null);
        return giveGiftRecord.insert();
    }


    /**
     * 跟新送礼活动
     *
     * @param param 活动参数
     * @return 1 跟新成功
     */
    public Integer updateGiveGift(GiveGiftParam param) {
        GiveGiftActivityRecord giveGiftRecord = db().newRecord(GIVE_GIFT_ACTIVITY, param);
        if (giveGiftRecord.getId() != null) {
            return giveGiftRecord.update();
        }
        return 0;
    }

    /**
     * 改变活动状态
     *
     * @param giveGiftId 活动id
     * @return 1 成功
     */
    public int changeGiveGift(Integer giveGiftId) {
        GiveGiftActivityRecord record = db().selectFrom(GIVE_GIFT_ACTIVITY).where(GIVE_GIFT_ACTIVITY.ID.eq(giveGiftId)).fetchOne();
        if (record != null) {
            if (record.getStatus() == STOP_STATUS) {
                return db().update(GIVE_GIFT_ACTIVITY).set(GIVE_GIFT_ACTIVITY.STATUS, USE_STATUS).execute();
            } else {
                return db().update(GIVE_GIFT_ACTIVITY).set(GIVE_GIFT_ACTIVITY.STATUS, STOP_STATUS).execute();
            }
        }
        return 0;
    }

    /**
     * 删除活动 (假删除)
     *
     * @param giveGiftId id
     * @return 1 成功
     */
    public int deleteGiveGift(Integer giveGiftId) {
        return db().update(GIVE_GIFT_ACTIVITY).set(GIVE_GIFT_ACTIVITY.DEL_FLAG, DelFlag.DISABLE_VALUE).execute();
    }


    public PageResult<GiveGiftRecordListVo> giveGiftRecordList(GiveGiftRecordListParam param) {
        return  giveGiftCart.giveGiftRecordList(param);
    }

    public PageResult<GiveGiftReceiveListVo> giveGiftReceiveList(GiveGiftReceiveListParam param) {
        return giveGiftReceive.giveGiftReceiveList(param);
    }
}
