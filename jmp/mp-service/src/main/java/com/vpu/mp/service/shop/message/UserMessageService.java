package com.vpu.mp.service.shop.message;

import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.dao.shop.doctor.DoctorDao;
import com.vpu.mp.dao.shop.message.MessageDao;
import com.vpu.mp.dao.shop.order.OrderGoodsDao;
import com.vpu.mp.dao.shop.prescription.PrescriptionDao;
import com.vpu.mp.dao.shop.session.ImSessionDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.message.DoctorMainShowParam;
import com.vpu.mp.service.pojo.shop.message.DoctorMessageCountVo;
import com.vpu.mp.service.pojo.shop.message.UserMessageParam;
import com.vpu.mp.service.pojo.shop.message.UserMessageVo;
import com.vpu.mp.service.pojo.wxapp.medical.im.param.ImSessionUnReadMessageInfoParam;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionUnReadInfoVo;
import com.vpu.mp.service.shop.im.ImSessionService;
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
    private ImSessionService imSessionService;

    @Autowired
    private PrescriptionDao prescriptionDao;

    @Autowired
    private DoctorDao doctorDao;


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

    public void addUserImSessionMessage(){

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
        String hospitalCode = doctorDao.selectDoctorCodeByDoctorId(doctorId);
        Boolean existAlreadyReadContinuedPrescription = prescriptionDao.isExistAlreadyReadContinuedPrescription(hospitalCode, doctorMainShowParam.getLastReadOrderGoodsTime());
        doctorMessageCountVo.setAlreadyPrescription(existAlreadyReadContinuedPrescription);
        // 判断是否有未读已开具消息
        Boolean existAlreadyReadPrescription = prescriptionDao.isExistAlreadyReadPrescription(hospitalCode, doctorMainShowParam.getLastReadPrescriptionTime());
        doctorMessageCountVo.setAlreadyOrderInfoCount(existAlreadyReadPrescription);
        // 判断是否有未读我的问诊消息
        ImSessionUnReadMessageInfoParam imSessionUnReadMessageInfoParam = new ImSessionUnReadMessageInfoParam();
        imSessionUnReadMessageInfoParam.setDoctorId(doctorId);
        List<ImSessionUnReadInfoVo> unReadMessageInfo = imSessionService.getUnReadMessageInfo(imSessionUnReadMessageInfoParam);
        Boolean existAlreadyReadImSession = !unReadMessageInfo.isEmpty();
        doctorMessageCountVo.setAlreadyImSessionCount(existAlreadyReadImSession);
        // 待问诊记录
        doctorMessageCountVo.setNotImSessionCount(messageDao.countDoctorImMessageNum(doctorId));
        // 待开方记录
        doctorMessageCountVo.setNotOrderInfoCount(messageDao.countDoctorOrderMessageNum());
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
