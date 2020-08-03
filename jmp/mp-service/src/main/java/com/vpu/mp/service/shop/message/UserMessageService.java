package com.vpu.mp.service.shop.message;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.dao.shop.message.MessageDao;
import com.vpu.mp.dao.shop.order.OrderGoodsDao;
import com.vpu.mp.dao.shop.prescription.PrescriptionDao;
import com.vpu.mp.dao.shop.session.ImSessionDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.message.DoctorMainShowParam;
import com.vpu.mp.service.pojo.shop.message.DoctorMessageCountVo;
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
public class UserMessageService extends ShopBaseService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private OrderGoodsDao orderGoodsDao;

    @Autowired
    private ImSessionDao imSessionDao;

    @Autowired
    private PrescriptionDao prescriptionDao;


    /**
     * 展示消息列表
     *
     * @param userId 当前用户id
     * @return List<UserMessageVo>
     */
    public List<UserMessageVo> showUserMessage(Integer userId) {
        return messageDao.showMessage(userId);
    }

    /**
     * 新增消息
     *
     * @param userMessageParam 用户消息入参
     */
    public void addUserMessage(UserMessageParam userMessageParam) {
        messageDao.addMessage(userMessageParam);
    }

    /**
     * 该用户未读消息总数
     *
     * @param receiveId 消息接收者id
     * @return Integer
     */
    public Integer countMessage(Integer receiveId) {
        return messageDao.countMessageNum(receiveId);
    }

    /**
     * 更改消息已读状态
     *
     * @param messageId 消息id
     * @param status    消息状态
     */
    public void changeMessageStatus(Integer messageId, Byte status) {
        messageDao.changeMessageStatus(messageId, status);
    }

    /**
     * 医师首页展示未读消息计数
     * @param doctorId 医师id
     * @return DoctorMessageCountVo
     */
    public DoctorMessageCountVo countDoctorMessage(Integer doctorId, DoctorMainShowParam doctorMainShowParam) {
        // 将访问当前页面时间置入缓存中，如果存在上次缓存
        DoctorMessageCountVo doctorMessageCountVo = new DoctorMessageCountVo();
        // 根据缓存时间判断数据库中是否有未读新增数据
        // 根据时间判断是否有未读已续方消息
        Byte existOrderGoods = orderGoodsDao.isExistAlreadyReadOrderGoods(doctorMainShowParam.getLastReadOrderGoodsTime());
        doctorMessageCountVo.setAlreadyPrescription(existOrderGoods);
        // 判断是否有未读已开具消息
        Byte existOrderInfo = prescriptionDao.isExistAlreadyReadPrescription(doctorMainShowParam.getLastReadPrescriptionTime());
        doctorMessageCountVo.setAlreadyOrderInfoCount(existOrderInfo);
        // 判断是否有未读我的问诊消息
        Byte existChat = imSessionDao.isExistAlreadyReadImSession(doctorMainShowParam.getLastReadImSession());
        doctorMessageCountVo.setAlreadyImSessionCount(existChat);
        // 查询未读消息
        // 待会话记录
        doctorMessageCountVo.setNotImSessionCount(messageDao.countDoctorImMessageMum(doctorId, DelFlag.NORMAL_VALUE));
        // 待开方记录
        doctorMessageCountVo.setNotOrderInfoCount(messageDao.countDoctorOrderMessageMum());
        // 待续方记录
        Integer integer = orderGoodsDao.countAuditOrder();
        if (integer == null) {
            integer = 0;
        }
        doctorMessageCountVo.setNotOrderGoodsCount(integer);
        return doctorMessageCountVo;
    }

    /**
     * 根据消息id删除消息
     * @param messageId 消息id
     */
    public void deleteUserMessage(Integer messageId) {
        messageDao.deleteUserMessage(messageId);
    }

}
