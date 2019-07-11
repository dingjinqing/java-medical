package com.vpu.mp.service.shop.config;

import static com.vpu.mp.db.shop.tables.Pledge.PLEDGE;

import java.sql.Timestamp;
import java.util.List;

import org.jooq.Record;
import org.jooq.SelectWhereStep;

import com.vpu.mp.db.shop.tables.records.PledgeRecord;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.shop.config.pledge.PledgeInfo;
import com.vpu.mp.service.pojo.shop.config.pledge.PledgeParam;
import com.vpu.mp.service.pojo.shop.config.pledge.PledgePojo;
import com.vpu.mp.service.pojo.shop.config.pledge.PledgeStateUpdateParam;

/**
 * PledgeService
 * @author: 卢光耀
 * @date: 2019-07-09 15:05
 *
*/
public class ShopPledgeService extends BaseService {


    public List<PledgeInfo> getPledgeList() {
        List<PledgeInfo> list = db()
                .select(PLEDGE.ID,PLEDGE.PLEDGE_NAME,PLEDGE.PLEDGE_LOGO,PLEDGE.PLEDGE_CONTENT,PLEDGE.STATE)
                .from(PLEDGE)
                .where(PLEDGE.DEL_FLAG.eq(PledgePojo.DELFLAG_NOT))
                .orderBy(PLEDGE.CREATE_TIME.asc())
                .fetch()
                .into(PledgeInfo.class);
        return list;
    }

    public boolean judgeInsertParam() {
        SelectWhereStep<? extends Record> select = db().select(PLEDGE.ID)
                .from(PLEDGE);
        select.where(PLEDGE.DEL_FLAG.eq(PledgePojo.DELFLAG_NOT));
        int count = db().fetchCount(select);
        return count > 20?Boolean.FALSE:Boolean.TRUE;
    }

    public int insertPledge(PledgeParam param) {
        PledgeRecord record = db().newRecord(PLEDGE,param);
        return record.insert();
    }

    public void updatePledge(PledgeParam param) {
        PledgeRecord record = db().newRecord(PLEDGE,param);
        record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        record.update();
    }


    public void updatePledgeState(PledgeStateUpdateParam param) {
          db().update(PLEDGE)
                .set(PLEDGE.STATE,param.getState())
                .set(PLEDGE.UPDATE_TIME,new Timestamp(System.currentTimeMillis()))
                .where(PLEDGE.ID.eq(param.getId()))
                .execute();
    }

    public void deletePledgeState(PledgeStateUpdateParam param) {
        db().update(PLEDGE)
                .set(PLEDGE.DEL_FLAG, PledgePojo.DELFLAG_DEL)
                .set(PLEDGE.UPDATE_TIME,new Timestamp(System.currentTimeMillis()))
                .where(PLEDGE.ID.eq(param.getId()))
                .execute();
    }
}
