package com.vpu.mp.dao.shop.doctor;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.DoctorCommentRecord;
import com.vpu.mp.service.pojo.shop.doctor.comment.DoctorCommentAddParam;
import com.vpu.mp.service.pojo.shop.doctor.comment.DoctorCommentListParam;
import com.vpu.mp.service.pojo.shop.doctor.comment.DoctorCommentListVo;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.tables.DoctorComment.DOCTOR_COMMENT;

/**
 * @author 孔德成
 * @date 2020/8/12 15:28
 */
@Repository
public class DoctorCommentDao extends ShopBaseDao {


    /**
     * 保存评价
     * @param param
     */
    public Integer save(DoctorCommentAddParam param) {
        DoctorCommentRecord record = db().newRecord(DOCTOR_COMMENT, param);
        return record.insert();
    }

    /**
     * 医师评价列表
     * @param param
     * @return
     */
    public PageResult<DoctorCommentListVo> listDoctorComment(DoctorCommentListParam param) {
        SelectConditionStep<Record> where = db().select().from(DOCTOR_COMMENT)
                .where(DOCTOR_COMMENT.DOCTOR_ID.eq(param.getDoctorId()));
        return getPageResult(where,param, DoctorCommentListVo.class);
    }
}
