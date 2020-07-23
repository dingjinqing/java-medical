package com.vpu.mp.service.shop.message;

import com.vpu.mp.dao.shop.message.MessageDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
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
        List<UserMessageVo> userMessageVos = messageDao.showMessage(userId);
        return userMessageVos;
    }

}
