package com.vpu.mp.dao.shop.message;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;

import com.vpu.mp.db.shop.tables.UserMessage;
import com.vpu.mp.db.shop.tables.records.UserMessageRecord;
import com.vpu.mp.service.pojo.shop.message.UserMessageParam;
import com.vpu.mp.service.pojo.shop.message.UserMessageVo;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryOrderConstant;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.service.pojo.shop.message.UserMessageConstant.USER_MESSAGE_STATUS_NOT_READ;
import static com.vpu.mp.service.pojo.shop.message.UserMessageConstant.USER_MESSAGE_STATUS_TOP;
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
     * 消息展示列表
     * @param userId 接收用户id
     * @return List<UserMessageVo>
     */
    public List<UserMessageVo> showMessage(int userId){
        List<UserMessageVo> list = new ArrayList<>();
        //置顶消息
        List<UserMessageVo> userMessageVos = db().selectFrom(USER_MESSAGE)
            .where(USER_MESSAGE.MESSAGE_STATUS.eq(USER_MESSAGE_STATUS_TOP)
                .and(USER_MESSAGE.RECEIVER_ID.eq(userId))
                .and(USER_MESSAGE.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchInto(UserMessageVo.class);
        //未读消息
        List<UserMessageVo> willMessages = db().selectFrom(USER_MESSAGE)
            .where(USER_MESSAGE.MESSAGE_STATUS.eq(USER_MESSAGE_STATUS_NOT_READ)
                .and(USER_MESSAGE.RECEIVER_ID.eq(userId))
                .and(USER_MESSAGE.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchInto(UserMessageVo.class);
        //已读消息
        List<UserMessageVo> alreadyMessages = db().selectFrom(USER_MESSAGE)
            .where(USER_MESSAGE.MESSAGE_STATUS.ne(USER_MESSAGE_STATUS_NOT_READ)
                .and(USER_MESSAGE.RECEIVER_ID.eq(userId))
                .and(USER_MESSAGE.MESSAGE_STATUS.ne(USER_MESSAGE_STATUS_TOP))
                .and(USER_MESSAGE.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchInto(UserMessageVo.class);
        list.addAll(userMessageVos);
        list.addAll(willMessages);
        list.addAll(alreadyMessages);
        return list;
    }

    /**
     * 查询该用户未读消息总数
     * @param receiveId 用户id
     * @return Integer
     */
    public Integer countMessageNum(Integer receiveId){
        List<Integer> integers = db().selectCount().from(USER_MESSAGE)
            .where(USER_MESSAGE.RECEIVER_ID.eq(receiveId)
                .and(USER_MESSAGE.MESSAGE_STATUS.eq(USER_MESSAGE_STATUS_NOT_READ))
                .and(USER_MESSAGE.IS_DELETE.eq(DelFlag.NORMAL_VALUE))).fetchInto(Integer.class);
        return integers.get(0);
    }

    /**
     * 消息已读状态变更
     * @param messageId 消息id
     */
    public void changeMessageStatus(Integer messageId, Byte status){
        UserMessageRecord messageRecord = db().select().from(USER_MESSAGE)
            .where(USER_MESSAGE.MESSAGE_ID.eq(messageId))
            .fetchOneInto(UserMessageRecord.class);
        messageRecord.setMessageStatus(status);
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

    public void saveUserImSessionItem(){

    }

}
