package com.vpu.mp.service.shop.message;

import com.vpu.mp.dao.shop.message.MessageDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.message.UserMessageParam;
import com.vpu.mp.service.pojo.shop.message.UserMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 赵晓东
 * @description
 * @create 2020-07-23 15:46
 **/

@Service
public class MessageService extends ShopBaseService {

    @Autowired
    private MessageDao messageDao;


    /**
     * 展示消息列表
     * @param userId 当前用户id
     * @return List<UserMessageVo>
     */
    public List<UserMessageVo> showUserMessage(Integer userId){
        return messageDao.showMessage(userId);
    }

    /**
     * 新增消息
     * @param userMessageParam 用户消息入参
     */
    public void addUserMessage(UserMessageParam userMessageParam){
        messageDao.addMessage(userMessageParam);
    }

    /**
     * 该用户未读消息总数
     * @param receiveId 消息接收者id
     * @return Integer
     */
    public Integer countMessage(Integer receiveId){
        return messageDao.countMessageNum(receiveId);
    }

    /**
     * 更改消息已读状态
     * @param messageId 消息id
     * @param status 消息状态
     */
    public void changeMessageStatus(Integer messageId, Byte status) {
        messageDao.changeMessageStatus(messageId, status);
    }

}
