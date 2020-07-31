package com.vpu.mp.service.shop.message;

import cn.hutool.json.JSON;
import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.dao.shop.message.MessageDao;
import com.vpu.mp.dao.shop.order.OrderGoodsDao;
import com.vpu.mp.dao.shop.order.OrderInfoDao;
import com.vpu.mp.dao.shop.session.ImSessionDao;
import com.vpu.mp.db.main.tables.OrderInfo;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.message.DoctorMessageCountParam;
import com.vpu.mp.service.pojo.shop.message.DoctorMessageCountVo;
import com.vpu.mp.service.pojo.shop.message.UserMessageParam;
import com.vpu.mp.service.pojo.shop.message.UserMessageVo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.sql.Timestamp;
import java.util.Date;
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

    @Autowired
    private OrderGoodsDao orderGoodsDao;

    @Autowired
    private OrderInfoDao orderInfoDao;

    @Autowired
    private ImSessionDao imSessionDao;


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
    public DoctorMessageCountVo countDoctorMessage(Integer doctorId, String token) {
        // 将访问当前页面时间置入缓存中，如果存在上次缓存
        Jedis jedis = new Jedis();
        String json = jedis.get(token);
        DoctorMessageCountVo doctorMessageCountVo = new DoctorMessageCountVo();
        WxAppSessionUser wxAppSessionUser = null;
        Timestamp doctorMainDate;
        // 根据缓存时间判断数据库中是否有未读新增数据
        if (!StringUtils.isBlank(json)) {
            wxAppSessionUser = Util.parseJson(json, WxAppSessionUser.class);
            assert wxAppSessionUser != null;
            doctorMainDate = wxAppSessionUser.getDoctorMainDate();
            // 根据时间判断是否有未读已续方消息
            Byte existOrderGoods = orderGoodsDao.isExistOrderGoods(doctorMainDate);
            doctorMessageCountVo.setAlreadyPrescription(existOrderGoods);
            // 判断是否有未读已开具消息
//            Byte existOrderInfo = orderInfoDao.isExistOrderInfo(doctorMainDate);
//            doctorMessageCountVo.setAlreadyOrderInfoCount(existOrderInfo);
            // 判断是否有未读我的问诊消息
            Byte existChat = imSessionDao.isExistChat(doctorMainDate);
            doctorMessageCountVo.setAlreadyImSessionCount(existChat);
        }
        // 储存当前时间至缓存中，用于下一次打开页面判断
        doctorMainDate = new Timestamp(System.currentTimeMillis());
        assert wxAppSessionUser != null;
        wxAppSessionUser.setDoctorMainDate(doctorMainDate);
        jedis.set(token, Util.toJson(wxAppSessionUser));
        // 查询未读消息
        doctorMessageCountVo.setNotImSessionCount(messageDao.countDoctorImMessageMum(doctorId, DelFlag.NORMAL_VALUE));
        doctorMessageCountVo.setNotOrderInfoCount(messageDao.countDoctorOrderMessageMum(DelFlag.NORMAL_VALUE));
        doctorMessageCountVo.setNotPrescription(messageDao.countOrderGoodsDoctorMessageMum(DelFlag.NORMAL_VALUE));
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
