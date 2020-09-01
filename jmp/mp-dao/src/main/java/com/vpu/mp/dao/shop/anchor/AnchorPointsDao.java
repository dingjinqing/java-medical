package com.vpu.mp.dao.shop.anchor;

import com.vpu.mp.common.pojo.shop.table.AnchorPointsDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.AnchorPointsRecord;
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
}
