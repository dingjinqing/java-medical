package com.vpu.mp.dao.shop.session;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.ImSessionDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.ImSessionRecord;
import com.vpu.mp.service.pojo.wxapp.medical.im.condition.ImSessionCondition;
import org.jooq.Condition;
import org.springframework.stereotype.Repository;

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
     * 根据id获取对应的会话信息
     * @param id
     * @return
     */
    public ImSessionDo getById(Integer id) {
        return db().selectFrom(IM_SESSION).where(IM_SESSION.ID.eq(id).and(IM_SESSION.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchAnyInto(ImSessionDo.class);
    }

    /**
     * 关闭会话session
     * @param imSessionId
     */
    public void updateSessionStatus(Integer imSessionId,Byte status) {
        db().update(IM_SESSION).set(IM_SESSION.SESSION_STATUS, status).where(IM_SESSION.ID.eq(imSessionId))
            .execute();
    }

    /**
     * 批量更新状态
     * @param imSessionIds 会话ids
     * @param status 状态
     */
    public void batchUpdateSessionStatus(List<Integer> imSessionIds, Byte status) {
        db().update(IM_SESSION).set(IM_SESSION.SESSION_STATUS,status).where(IM_SESSION.ID.in(imSessionIds))
            .execute();
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

        return db().selectFrom(IM_SESSION).where(condition).fetchInto(ImSessionDo.class);
    }
}
