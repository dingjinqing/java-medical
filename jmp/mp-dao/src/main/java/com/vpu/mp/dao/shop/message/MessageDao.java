package com.vpu.mp.dao.shop.message;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.Message;
import com.vpu.mp.db.shop.tables.records.MessageRecord;
import com.vpu.mp.service.pojo.shop.message.UserMessageParam;
import com.vpu.mp.service.pojo.shop.message.UserMessageVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
        MessageRecord messageRecord = db().newRecord(Message.MESSAGE);
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
        List<UserMessageVo> userMessageVos = db().selectFrom(Message.MESSAGE)
            .where(Message.MESSAGE.MESSAGE_STATUS.eq((byte) 3)
                .and(Message.MESSAGE.RECEIVER_ID.eq(userId))
                .and(Message.MESSAGE.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchInto(UserMessageVo.class);
        //未读消息
        List<UserMessageVo> willMessages = db().selectFrom(Message.MESSAGE)
            .where(Message.MESSAGE.MESSAGE_STATUS.eq((byte) 1)
                .and(Message.MESSAGE.RECEIVER_ID.eq(userId))
                .and(Message.MESSAGE.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchInto(UserMessageVo.class);
        //已读消息
        List<UserMessageVo> alreadyMessages = db().selectFrom(Message.MESSAGE)
            .where(Message.MESSAGE.MESSAGE_STATUS.ne((byte) 1)
                .and(Message.MESSAGE.RECEIVER_ID.eq(userId))
                .and(Message.MESSAGE.MESSAGE_STATUS.ne((byte) 3))
                .and(Message.MESSAGE.IS_DELETE.eq(DelFlag.NORMAL_VALUE)))
            .fetchInto(UserMessageVo.class);
        list.addAll(userMessageVos);
        list.addAll(willMessages);
        list.addAll(alreadyMessages);
        return list;
    }

}
