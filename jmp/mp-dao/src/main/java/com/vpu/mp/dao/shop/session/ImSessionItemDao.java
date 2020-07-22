package com.vpu.mp.dao.shop.session;

import com.vpu.mp.common.pojo.shop.table.ImSessionItemDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.ImSessionItemRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.IM_SESSION_ITEM;

/**
 * 会话处理详情Dao
 * @author 李晓冰
 * @date 2020年07月21日
 */
@Repository
public class ImSessionItemDao extends ShopBaseDao {

    /**
     * 根据sessionId获取对应的会话详情信息
     * @param sessionId
     * @return
     */
    public List<ImSessionItemDo> getBySessionId(Integer sessionId) {
        return db().selectFrom(IM_SESSION_ITEM).where(IM_SESSION_ITEM.IM_SESSION_ID.eq(sessionId))
            .fetchInto(ImSessionItemDo.class);
    }

    public void batchInsert(List<ImSessionItemDo> imSessionItemDos) {
        List<ImSessionItemRecord> records = new ArrayList<>(imSessionItemDos.size());
        for (ImSessionItemDo imSessionItemDo : imSessionItemDos) {
            ImSessionItemRecord record =new ImSessionItemRecord();
            record.setImSessionId(imSessionItemDo.getImSessionId());
            record.setFormId(imSessionItemDo.getFormId());
            record.setToId(imSessionItemDo.getToId());
            record.setMessage(imSessionItemDo.getMessage());
            record.setType(imSessionItemDo.getType());
            record.setSendTime(imSessionItemDo.getSendTime());
            records.add(record);
        }
        db().batchInsert(records).execute();
    }
}
