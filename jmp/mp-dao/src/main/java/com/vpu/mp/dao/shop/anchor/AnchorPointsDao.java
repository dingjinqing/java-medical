package com.vpu.mp.dao.shop.anchor;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.AnchorPointsDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.AnchorPointsRecord;
import com.vpu.mp.service.pojo.shop.anchor.AnchorPointsListParam;
import com.vpu.mp.service.pojo.shop.anchor.AnchorPointsListVo;
import com.vpu.mp.service.pojo.shop.anchor.AnchorPointsReportVo;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.Tables.ANCHOR_POINTS;
import static org.jooq.impl.DSL.date;

/**
 * @author 孔德成
 * @date 2020/8/31 11:59
 */
@Repository
public class AnchorPointsDao extends ShopBaseDao {


    public void save(AnchorPointsDo anchorPointsDo) {
        AnchorPointsRecord anchorPointsRecord = db().newRecord(ANCHOR_POINTS, anchorPointsDo);
        anchorPointsRecord.insert();
    }

    public PageResult<AnchorPointsListVo> list(AnchorPointsListParam param) {
        SelectJoinStep<Record> select = db().select().from(ANCHOR_POINTS);
        buildSelect(param, select);
        select.orderBy(ANCHOR_POINTS.CREATE_TIME.desc());
        return getPageResult(select, param, AnchorPointsListVo.class);
    }

    private void buildSelect(AnchorPointsListParam param, SelectJoinStep<? extends Record> select) {
        if (param.getDevice()!=null&&param.getDevice().trim().length()>0) {
            select.where(ANCHOR_POINTS.DEVICE.eq(param.getDevice()));
        }
        if (param.getEvent()!=null&&param.getEvent().trim().length()>0){
            select.where(ANCHOR_POINTS.EVENT.eq(param.getEvent()));
        }
        if (param.getKey()!=null&&param.getKey().trim().length()>0){
            select.where(ANCHOR_POINTS.KEY.eq(param.getKey()));
        }
        if (param.getPage()!=null&&param.getPage().trim().length()>0){
            select.where(ANCHOR_POINTS.PAGE.eq(param.getPage()));
        }
        if (param.getPlatform()!=null&&param.getPlatform().trim().length()>0){
            select.where(ANCHOR_POINTS.PLATFORM.eq(param.getPlatform()));
        }
        if (param.getStoreId()!=null){
            select.where(ANCHOR_POINTS.STORE_ID.eq(param.getStoreId()));
        }
        if (param.getUserId()!=null){
            select.where(ANCHOR_POINTS.USER_ID.eq(param.getUserId()));
        }
        if (param.getValue()!=null&&param.getValue().trim().length()>0){
            select.where(ANCHOR_POINTS.VALUE.eq(param.getValue()));
        }
    }

    /**
     * 计数报表
     * @param param
     * @return
     */
    public Map<Date, List<AnchorPointsReportVo>> countReport(AnchorPointsListParam param) {
        return db().select(DSL.date(ANCHOR_POINTS.CREATE_TIME).as("date"), ANCHOR_POINTS.EVENT, ANCHOR_POINTS.KEY,
                ANCHOR_POINTS.VALUE, DSL.count(ANCHOR_POINTS.ID).as("count"))
                .from(ANCHOR_POINTS)
                .where(ANCHOR_POINTS.EVENT.eq(param.getEvent()))
                .and(ANCHOR_POINTS.KEY.eq(param.getKey()))
                .and(ANCHOR_POINTS.CREATE_TIME.between(param.getStartTime(), param.getEndTime()))
                .groupBy(date(ANCHOR_POINTS.CREATE_TIME), ANCHOR_POINTS.EVENT, ANCHOR_POINTS.KEY, ANCHOR_POINTS.VALUE)
                .fetchGroups(date(ANCHOR_POINTS.CREATE_TIME).as("date"), AnchorPointsReportVo.class);
    }

    /**
     *金额报表
     */
    public Map<Date, List<AnchorPointsReportVo>>   moneyReport(AnchorPointsListParam param){
        return  db().select(date(ANCHOR_POINTS.CREATE_TIME).as("date"), ANCHOR_POINTS.EVENT, ANCHOR_POINTS.KEY,
                ANCHOR_POINTS.DEVICE,DSL.sum(DSL.cast(ANCHOR_POINTS.VALUE, BigDecimal.class)).as("money"))
                .from(ANCHOR_POINTS)
                .where(ANCHOR_POINTS.EVENT.eq(param.getEvent()))
                .and(ANCHOR_POINTS.KEY.eq(param.getKey()))
                .groupBy(date(ANCHOR_POINTS.CREATE_TIME), ANCHOR_POINTS.EVENT, ANCHOR_POINTS.KEY, ANCHOR_POINTS.DEVICE)
                .fetchGroups(date(ANCHOR_POINTS.CREATE_TIME).as("date"), AnchorPointsReportVo.class);
    }

}
