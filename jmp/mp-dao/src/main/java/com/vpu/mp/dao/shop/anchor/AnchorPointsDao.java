package com.vpu.mp.dao.shop.anchor;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.AnchorPointsDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.AnchorPointsRecord;
import com.vpu.mp.service.pojo.shop.anchor.AnchorPointsListParam;
import com.vpu.mp.service.pojo.shop.anchor.AnchorPointsListVo;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.Tables.ANCHOR_POINTS;

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

    private void buildSelect(AnchorPointsListParam param, SelectJoinStep<Record> select) {
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
}
