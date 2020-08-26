package com.vpu.mp.dao.shop.doctor;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.pojo.shop.table.DoctorCommentReplyDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.DoctorCommentReplyRecord;
import com.vpu.mp.service.pojo.shop.doctor.comment.reply.DoctorCommentReplyAddParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.Tables.DOCTOR_COMMENT_REPLY;

/**
 * 医师评价回复
 * @author 孔德成
 * @date 2020/8/25 15:27
 */
@Component
public class DoctorCommentReplyDao extends ShopBaseDao {


    /**
     * 根据评价id查询医师回复
     * @param ids
     * @return
     */
    public Map<Integer, List<DoctorCommentReplyDo>> mapDoctorReplyByIds(List<Integer> ids) {
        return db().select().from(DOCTOR_COMMENT_REPLY)
                .where(DOCTOR_COMMENT_REPLY.COMMENT_ID.in(ids))
                .and(DOCTOR_COMMENT_REPLY.IS_DELETE.eq(DelFlag.NORMAL_VALUE))
                .fetchGroups(DOCTOR_COMMENT_REPLY.COMMENT_ID, DoctorCommentReplyDo.class);
    }

    public void save(DoctorCommentReplyAddParam param) {
        DoctorCommentReplyRecord record = db().newRecord(DOCTOR_COMMENT_REPLY, param);
        record.insert();
    }

    public void deleteById(Integer id) {
        db().update(DOCTOR_COMMENT_REPLY).set(DOCTOR_COMMENT_REPLY.IS_DELETE,DelFlag.DISABLE_VALUE).where(DOCTOR_COMMENT_REPLY.ID.eq(id)).execute();
    }
}
