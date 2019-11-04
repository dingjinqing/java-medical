package com.vpu.mp.service.shop.user.footprint;

import com.vpu.mp.db.shop.tables.records.FootprintRecordRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.wxapp.footprint.FootprintListVo;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import static com.vpu.mp.db.shop.Tables.FOOTPRINT_RECORD;
import static com.vpu.mp.db.shop.Tables.GOODS;

/**
 * 足迹
 * @author 孔德成
 * @date 2019/11/4 11:12
 */
@Service
public class FootprintServie extends ShopBaseService {


    /**
     * 添加足迹记录
     * @param userId 用户id
     * @param goodsId 商品id
     *
     */
    public void addFootprint(Integer userId,Integer goodsId){
        Timestamp nowDate = DateUtil.getLocalTimeDate();

        Integer count = db().selectCount().from(FOOTPRINT_RECORD)
                .where(FOOTPRINT_RECORD.USER_ID.eq(userId)).and(FOOTPRINT_RECORD.GOODS_ID.eq(goodsId))
                .and(FOOTPRINT_RECORD.CREATE_TIME.eq(nowDate)).fetchOne().component1();
        if (count!=null&&count>0){
            db().update(FOOTPRINT_RECORD).set(FOOTPRINT_RECORD.COUNT,FOOTPRINT_RECORD.COUNT.add(1))
                    .where(FOOTPRINT_RECORD.USER_ID.eq(userId))
                    .and(FOOTPRINT_RECORD.GOODS_ID.eq(goodsId)).and(FOOTPRINT_RECORD.CREATE_TIME.eq(nowDate)).execute();
        }else {
            FootprintRecordRecord footprint = db().newRecord(FOOTPRINT_RECORD);
            footprint.setCount(1);
            footprint.setUserId(userId);
            footprint.setGoodsId(goodsId);
            footprint.insert();
        }
    }

    /**
     * 获取足迹记录
     * @param userId  用户id1
     * @param keyWord 关键字
     * @param currentPages 翻页信息
     * @param pageRows
     * @return FootprintListVo
     */
    public PageResult<FootprintListVo> getFootprintPage(Integer userId, String keyWord,Integer currentPages, Integer pageRows){
        Timestamp timestamp = DateUtil.geTimeStampPlus(-3, ChronoUnit.MONTHS);
        SelectConditionStep<? extends Record> select = db().select(GOODS.GOODS_ID,GOODS.GOODS_NAME)
                .from(FOOTPRINT_RECORD).leftJoin(GOODS).on(GOODS.GOODS_ID.eq(FOOTPRINT_RECORD.GOODS_ID))
                .where(FOOTPRINT_RECORD.USER_ID.eq(userId))
                .and(GOODS.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
                .and(FOOTPRINT_RECORD.CREATE_TIME.le(timestamp));
        if (!StringUtils.isBlank(keyWord)){
            select.and(GOODS.GOODS_NAME.like(likeValue(keyWord)));
        }
        select.orderBy(FOOTPRINT_RECORD.UPDATE_TIME.desc());
        PageResult<FootprintListVo> pageResult = getPageResult(select, currentPages, pageRows, FootprintListVo.class);
        return pageResult;
    }

}
