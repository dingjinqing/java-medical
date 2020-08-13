package com.vpu.mp.dao.shop.session;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.ImSessionDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.ImSessionRecord;
import com.vpu.mp.service.pojo.wxapp.medical.im.condition.ImSessionCondition;
import com.vpu.mp.service.pojo.wxapp.medical.im.param.ImSessionPageListParam;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionListVo;
import org.jooq.Condition;
import org.jooq.SelectSeekStep2;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.IM_SESSION;

/**
 * 会话处理Dao
 * @author 李晓冰
 * @date 2020年07月21日
 */
@Repository
public class ImSessionDao extends ShopBaseDao {

    /**
     * 新增会话
     * @param imSessionDo 会话信息
     */
    public void insert(ImSessionDo imSessionDo){
        ImSessionRecord imSessionRecord = db().newRecord(IM_SESSION);
        FieldsUtil.assign(imSessionDo,imSessionRecord);
        imSessionRecord.insert();
        imSessionDo.setId(imSessionRecord.getId());
    }

    /**
     * 修改会话
     * @param imSessionDo 会话信息
     */
    public void update(ImSessionDo imSessionDo){
        ImSessionRecord imSessionRecord = db().newRecord(IM_SESSION);
        FieldsUtil.assign(imSessionDo,imSessionRecord);
        imSessionRecord.update();
    }

    /**
     * 批量修改会话
     * @param imSessionDos
     */
    public void batchUpdate(List<ImSessionDo> imSessionDos) {
        List<ImSessionRecord> imSessionRecords = new ArrayList<>();
        for (ImSessionDo imSessionDo : imSessionDos) {
            ImSessionRecord imSessionRecord = new ImSessionRecord();
            FieldsUtil.assign(imSessionDo,imSessionRecord);
            imSessionRecords.add(imSessionRecord);
        }
        db().batchUpdate(imSessionRecords).execute();
    }

    /**
     * 根据id获取对应的会话信息
     * @param id
     * @return
     */
    public ImSessionDo getById(Integer id) {
        return db().selectFrom(IM_SESSION).where(IM_SESSION.ID.eq(id).and(IM_SESSION.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchAnyInto(ImSessionDo.class);
    }

    /**
     * 根据orderSn获取对应的会话信息
     * @param orderSn
     * @return
     */
    public ImSessionDo getByOrderSn(String orderSn) {
        return db().selectFrom(IM_SESSION).where(IM_SESSION.ORDER_SN.eq(orderSn).and(IM_SESSION.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchAnyInto(ImSessionDo.class);
    }

    /**
     * 关闭会话session
     * @param imSessionId
     */
    public void updateSessionStatus(Integer imSessionId,Byte status,Byte weightFactor) {
        db().update(IM_SESSION).set(IM_SESSION.SESSION_STATUS, status)
            .set(IM_SESSION.WEIGHT_FACTOR,weightFactor)
            .where(IM_SESSION.ID.eq(imSessionId))
            .execute();
    }


    public void batchUpdateSessionEvaluateStatus(List<Integer> imSessionIds, Byte status) {
        db().update(IM_SESSION).set(IM_SESSION.EVALUATE_STATUS,status)
            .where(IM_SESSION.ID.in(imSessionIds))
            .execute();
    }

    public void batchUpdateSessionEvaluateStatus(List<Integer> imSessionIds, Byte newStatus,Byte oldStatus) {
        db().update(IM_SESSION)
            .set(IM_SESSION.EVALUATE_STATUS,newStatus)
            .where(IM_SESSION.ID.in(imSessionIds).and(IM_SESSION.EVALUATE_STATUS.eq(oldStatus)))
            .execute();
    }

    /**
     * 批量更新状态
     * @param imSessionIds 会话ids
     * @param status 状态
     */
    public void batchUpdateSessionStatus(List<Integer> imSessionIds, Byte status,Byte weightFactor) {
        db().update(IM_SESSION).set(IM_SESSION.SESSION_STATUS,status)
            .set(IM_SESSION.WEIGHT_FACTOR,weightFactor)
            .where(IM_SESSION.ID.in(imSessionIds))
            .execute();
    }

    /**
     * 分页查询会话列表信息
     * @param pageListParam 分页信息
     * @return 分页结果
     */
    public PageResult<ImSessionListVo> pageList(ImSessionPageListParam pageListParam) {
        Condition condition = IM_SESSION.IS_DELETE.eq(DelFlag.NORMAL_VALUE);
        if (pageListParam.getDoctorId() != null) {
            condition = condition.and(IM_SESSION.DOCTOR_ID.eq(pageListParam.getDoctorId()));
        }
        if (pageListParam.getSessionStatus() != null) {
            condition = condition.and(IM_SESSION.SESSION_STATUS.in(pageListParam.getSessionStatus()));
        }
        if (pageListParam.getUserId() != null) {
            condition =condition.and(IM_SESSION.USER_ID.eq(pageListParam.getUserId()));
        }

        SelectSeekStep2<ImSessionRecord, Byte, Timestamp> select = db().selectFrom(IM_SESSION).where(condition)
            .orderBy(IM_SESSION.WEIGHT_FACTOR.desc(), IM_SESSION.UPDATE_TIME.asc());
        return getPageResult(select,pageListParam.getCurrentPage(),pageListParam.getPageRows(),ImSessionListVo.class);
    }

    /**
     * 列出符合条件的会话
     * @param imSessionCondition 会话过滤条件
     * @return 会话列表
     */
    public List<ImSessionDo> listImSession(ImSessionCondition imSessionCondition){
        Condition condition = IM_SESSION.IS_DELETE.eq(DelFlag.NORMAL_VALUE);
        if (imSessionCondition.getStatus() != null) {
            condition =condition.and(IM_SESSION.SESSION_STATUS.eq(imSessionCondition.getStatus()));
        }

        if (imSessionCondition.getLessCreateTime() != null) {
            condition = condition.and(IM_SESSION.CREATE_TIME.le(imSessionCondition.getLessCreateTime()));
        }

        if (imSessionCondition.getLimitTime() != null) {
            condition = condition.and(IM_SESSION.LIMIT_TIME.le(imSessionCondition.getLimitTime()));
        }

        if (imSessionCondition.getOrderSns() != null) {
            condition = condition.and(IM_SESSION.ORDER_SN.in(imSessionCondition.getOrderSns()));
        }

        if (imSessionCondition.getDoctorId() != null) {
            condition = condition.and(IM_SESSION.DOCTOR_ID.eq(imSessionCondition.getDoctorId()));
        }

        if (imSessionCondition.getUserId() != null) {
            condition = condition.and(IM_SESSION.USER_ID.eq(imSessionCondition.getUserId()));
        }

        return db().selectFrom(IM_SESSION).where(condition).fetchInto(ImSessionDo.class);
    }

    /**
     * 根据会话id查询会话状态，订单号
     * @param sessionId
     * @return
     */
    public ImSessionDo getImSession(Integer sessionId) {
        return db().select().from(IM_SESSION)
            .where(IM_SESSION.ID.eq(sessionId)).fetchAnyInto(ImSessionDo.class);
    }

}
