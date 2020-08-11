package com.vpu.mp.dao.shop.message;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;

import com.vpu.mp.db.shop.tables.UserMessage;
import com.vpu.mp.db.shop.tables.records.InquiryOrderRecord;
import com.vpu.mp.db.shop.tables.records.UserAnnouncementRecord;
import com.vpu.mp.db.shop.tables.records.UserMessageRecord;
import com.vpu.mp.service.pojo.shop.message.UserMessageParam;
import com.vpu.mp.service.pojo.shop.message.UserMessageVo;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionUnReadInfoVo;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryOrderConstant;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.service.pojo.shop.message.UserMessageConstant.*;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.*;

/**
 * @author 赵晓东
 * @description 用户消息dao层
 * @create 2020-07-23 15:35
 **/

@Repository
public class MessageDao extends ShopBaseDao {

    /**
     * 消息接收入库
     * @param userMessageParam 用户消息入参
     */
    public void addMessage(UserMessageParam userMessageParam){
        UserMessageRecord messageRecord = db().newRecord(USER_MESSAGE);
        FieldsUtil.assign(userMessageParam, messageRecord);
        //系统消息
        if (0 == userMessageParam.getMessageType()){
            FieldsUtil.assign(userMessageParam, messageRecord);
            messageRecord.setMessageName("系统公告");
            messageRecord.setMessageRelevanceId(0);
        }
        //订单消息
        if (0 == userMessageParam.getMessageType()){
            messageRecord.setMessageName("订单消息");
        }
        //会话消息
        if (0 == userMessageParam.getMessageType()){
            messageRecord.setMessageName("医师会话");
        }
        messageRecord.insert();
    }

    /**
     * 消息已读状态变更
     * @param messageId 消息id
     */
    public void changeMessageStatus(Integer messageId){
        UserMessageRecord messageRecord = db().select().from(USER_MESSAGE)
            .where(USER_MESSAGE.MESSAGE_ID.eq(messageId))
            .fetchOneInto(UserMessageRecord.class);
        messageRecord.setMessageStatus(USER_MESSAGE_STATUS_ALREADY_READ);
        messageRecord.update();
    }


    /**
     * 医师端显示待问诊数量
     * @param doctorId 医师id
     * @return Integer
     */
    public Integer countDoctorImMessageNum(Integer doctorId){
        return db().selectCount().from(INQUIRY_ORDER)
            .where(INQUIRY_ORDER.ORDER_STATUS.eq(InquiryOrderConstant.ORDER_TO_RECEIVE)
            .and(INQUIRY_ORDER.DOCTOR_ID.eq(doctorId))
            .and(INQUIRY_ORDER.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchOneInto(Integer.class);
    }

    /**
     * 医师端显示待开方数量
     * @return Integer
     */
    public Integer countDoctorOrderMessageNum(){
        return db().selectCount().from(ORDER_INFO)
            .where(ORDER_INFO.ORDER_STATUS.eq(ORDER_TO_AUDIT_OPEN))
            .and(ORDER_INFO.ORDER_AUDIT_TYPE.eq(MEDICAL_ORDER_AUDIT_TYPE_CREATE))
            .and(ORDER_INFO.ORDER_AUDIT_STATUS.eq(MEDICAL_AUDIT_DEFAULT))
            .and(ORDER_INFO.ORDER_MEDICAL_TYPE.eq(MEDICAL_TYPE_RX))
            .and(ORDER_INFO.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .fetchOneInto(Integer.class);
    }

    /**
     * 用户删除列表消息
     * @param messageId 消息id
     */
    public void deleteUserMessage(Integer messageId){
        db().update(USER_MESSAGE)
            .set(USER_MESSAGE.IS_DELETE, DelFlag.DISABLE_VALUE)
            .where(USER_MESSAGE.MESSAGE_ID.eq(messageId)).execute();
    }

    /**
     * 查询订单消息
     * @param userId 接收消息用户id
     * @return List<UserMessageVo>
     */
    public List<UserMessageVo> selectOrderUserMessage(Integer userId){
        return db().select().from(USER_MESSAGE)
            .where(USER_MESSAGE.MESSAGE_TYPE.eq(USER_MESSAGE_ORDER))
            .and(USER_MESSAGE.RECEIVER_ID.eq(userId))
            .and(USER_MESSAGE.IS_DELETE.eq(DelFlag.NORMAL_VALUE)).fetchInto(UserMessageVo.class);
    }

    /**
     * 查询会话消息
     * @param userId 接收消息用户ID
     * @return List<UserMessageVo>
     */
    public List<UserMessageVo> selectImSessionUserMessage(Integer userId){
        return db().select().from(USER_MESSAGE)
            .where(USER_MESSAGE.MESSAGE_TYPE.eq(USER_MESSAGE_CHAT))
            .and(USER_MESSAGE.RECEIVER_ID.eq(userId))
            .and(USER_MESSAGE.IS_DELETE.eq(DelFlag.NORMAL_VALUE)).fetchInto(UserMessageVo.class);
    }

    /**
     * 查询用户是否有历史会话记录
     * @param sessionId 会话id
     * @param userId 当前用户id
     * @return UserMessageVo
     */
    public UserMessageVo getImSessionBySessionId(Integer sessionId, Integer userId) {
        return db().select().from(USER_MESSAGE)
            .where(USER_MESSAGE.MESSAGE_RELEVANCE_ID.eq(sessionId))
            .and(USER_MESSAGE.MESSAGE_TYPE.eq(USER_MESSAGE_CHAT))
            .and(USER_MESSAGE.RECEIVER_ID.eq(userId))
            .and(USER_MESSAGE.IS_DELETE.eq(DelFlag.NORMAL_VALUE)).fetchAnyInto(UserMessageVo.class);
    }

    /**
     * 储存会话消息
     * @param userMessageParam 消息入参
     */
    public void saveMessage(UserMessageParam userMessageParam) {
        UserMessageRecord messageRecord = db().newRecord(USER_MESSAGE);
        FieldsUtil.assign(userMessageParam, messageRecord);
        messageRecord.insert();
    }

    /**
     * 更新会话消息
     * @param userMessageParam 消息入参
     */
    public void updateMessage(UserMessageParam userMessageParam) {
        UserMessageRecord messageRecord = db().newRecord(USER_MESSAGE);
        FieldsUtil.assign(userMessageParam, messageRecord);
        messageRecord.update();
    }

    /**
     * 查询用户订单消息
     * @param orderId
     * @return
     */
    public UserMessageVo selectOrderMessageByOrderId(Integer orderId, Integer userId) {
        return db().select().from(USER_MESSAGE)
            .where(USER_MESSAGE.MESSAGE_RELEVANCE_ID.eq(orderId))
            .and(USER_MESSAGE.MESSAGE_TYPE.eq(USER_MESSAGE_ORDER))
            .and(USER_MESSAGE.RECEIVER_ID.eq(userId))
            .and(USER_MESSAGE.IS_DELETE.eq(DelFlag.NORMAL_VALUE)).fetchAnyInto(UserMessageVo.class);
    }

    /**
     * 增量获取未读的公告消息
     * @param timestamp 上次打开公告消息时间戳
     * @return List<UserMessageVo>
     */
    public List<UserMessageVo> selectLastAnnouncement(Timestamp timestamp){
        return db().select().from(USER_MESSAGE)
            .where(USER_MESSAGE.MESSAGE_TIME.gt(timestamp))
            .and(USER_MESSAGE.MESSAGE_TYPE.eq(USER_MESSAGE_SYSTEM))
            .and(USER_MESSAGE.IS_DELETE.eq(DelFlag.NORMAL_VALUE)).fetchInto(UserMessageVo.class);
    }

    /**
     * 拉取该用户所有信息
     * @param userId 用户id
     * @return List<UserMessageVo>
     */
    public List<UserMessageVo> selectMainMessage(Integer userId) {
        return db().select().from(USER_MESSAGE)
            .where(USER_MESSAGE.RECEIVER_ID.eq(userId))
            .or(USER_MESSAGE.RECEIVER_ID.eq(USER_MESSAGE_RECEIVE_ANN))
            .and(USER_MESSAGE.IS_DELETE.eq(DelFlag.NORMAL_VALUE))
            .orderBy(USER_MESSAGE.CREATE_TIME.desc()).fetchInto(UserMessageVo.class);
    }

    /**
     * 查询用户未读公告数
     * @param userId 用户id
     * @return Integer
     */
    public Integer selectAnnouncementCount(Integer userId) {
        return db().selectCount().from(USER_ANNOUNCEMENT)
            .join(USER_MESSAGE).on(USER_ANNOUNCEMENT.MESSAGE_ID.eq(USER_MESSAGE.MESSAGE_ID))
            .and(USER_ANNOUNCEMENT.USER_ID.eq(userId))
            .and(USER_ANNOUNCEMENT.MESSAGE_STATUS.eq(USER_MESSAGE_STATUS_NOT_READ))
            .where(USER_MESSAGE.IS_DELETE.eq(DelFlag.NORMAL_VALUE)).fetchAnyInto(Integer.class);
    }

    /**
     * 查询用户未读订单消息数
     * @param userId 用户id
     * @return Integer
     */
    public Integer selectOrderCount(Integer userId) {
        return db().selectCount().from(USER_MESSAGE)
            .where(USER_MESSAGE.RECEIVER_ID.eq(userId))
            .and(USER_MESSAGE.MESSAGE_TYPE.eq(USER_MESSAGE_ORDER))
            .and(USER_MESSAGE.MESSAGE_STATUS.eq(USER_MESSAGE_STATUS_NOT_READ))
            .and(USER_MESSAGE.IS_DELETE.eq(DelFlag.NORMAL_VALUE)).fetchAnyInto(Integer.class);
    }

    /**
     * 查询未读会话消息数
     * @param userId 用户id
     * @return Integer
     */
    public Integer selectChatCount(Integer userId) {
        return db().selectCount().from(USER_MESSAGE)
            .where(USER_MESSAGE.RECEIVER_ID.eq(userId))
            .and(USER_MESSAGE.MESSAGE_TYPE.eq(USER_MESSAGE_CHAT))
            .and(USER_MESSAGE.MESSAGE_STATUS.eq(USER_MESSAGE_STATUS_NOT_READ))
            .and(USER_MESSAGE.IS_DELETE.eq(DelFlag.NORMAL_VALUE)).fetchAnyInto(Integer.class);
    }

    public void updateMessageStatus(Integer userId, Byte messageType) {
        db().update(USER_MESSAGE)
            .set(USER_MESSAGE.MESSAGE_STATUS, USER_MESSAGE_STATUS_ALREADY_READ)
            .where(USER_MESSAGE.MESSAGE_TYPE.eq(messageType))
            .and(USER_MESSAGE.RECEIVER_ID.eq(userId)).execute();
    }

}
