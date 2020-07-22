package com.vpu.mp.service.shop.im;

import com.vpu.mp.common.pojo.shop.table.ImSessionDo;
import com.vpu.mp.common.pojo.shop.table.ImSessionItemDo;
import com.vpu.mp.dao.shop.session.ImSessionDao;
import com.vpu.mp.dao.shop.session.ImSessionItemDao;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.medical.im.param.ImSessionSendMsgParam;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionItemRenderVo;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionRenderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会话处理service
 * @author 李晓冰
 * @date 2020年07月21日
 */
@Service
public class ImSessionService extends ShopBaseService {
    @Autowired
    private ImSessionDao imSessionDao;
    @Autowired
    private ImSessionItemDao imSessionItemDao;
    @Autowired
    private JedisManager jedisManager;

    /**
     * 恢复已有的聊天记录,目前没有做成分页的
     * @param sessionId
     * @return
     */
    public ImSessionRenderVo renderSession(Integer sessionId) {
        ImSessionDo imSessionDo = imSessionDao.getById(sessionId);
        Integer doctorId = imSessionDo.getDoctorId();
        List<ImSessionItemDo> imSessionItemDos = imSessionItemDao.getBySessionId(sessionId);
        ImSessionRenderVo imSessionRenderVo = new ImSessionRenderVo();
        imSessionRenderVo.setLimitTime(imSessionDo.getLimitTime());

        List<ImSessionItemRenderVo> sessionItemRenderVos = imSessionItemDos.stream().sorted(Comparator.comparing(ImSessionItemDo::getSendTime)).map(item -> {
            ImSessionItemRenderVo itemVo = new ImSessionItemRenderVo();
            itemVo.setMessage(item.getMessage());
            itemVo.setSendTime(item.getSendTime());
            itemVo.setDoctor(doctorId.equals(item.getFormId()));
            return itemVo;
        }).collect(Collectors.toList());
        imSessionRenderVo.setSessionItemRenderVos(sessionItemRenderVos);
        return imSessionRenderVo;
    }

    /**
     * 将消息发送，存储至redis
     */
    public void sendMsg(ImSessionSendMsgParam sendMsgParam){
        String sessionKey = getSessionKey(getShopId(), sendMsgParam.getPatientId(), sendMsgParam.getFromId(), sendMsgParam.getToId());
        jedisManager.rpush(sessionKey,sendMsgParam.getMsg());
    }

    /**
     * 拉取对方发送的消息内容
     * @param patientId 患者id
     * @param pullFromId 要拉取的信息的对方 医生拉取的时候就是用户的id，如果是用户的话就是医师的id
     * @param selfId 自己的id，医师id或用户id
     * @return
     */
    public List<String> pullMsg(Integer patientId,Integer pullFromId,Integer selfId){
        String sessionKey = getSessionKey(getShopId(),patientId,pullFromId,selfId);
        String sessionBakKey = getSessionKeyBak(getShopId(),patientId,pullFromId,selfId);
        List<String> list = jedisManager.getList(sessionKey);
        jedisManager.cleanList(sessionKey);
        jedisManager.rpush(sessionBakKey,list);
        return list;
    }
    /**
     * 判断信息
     * @param sessionId
     * @return
     */
    public boolean sessionExist(Integer sessionId) {
        return imSessionDao.getById(sessionId) !=null;
    }


    private String getSessionKey(Integer dbId, Integer patientId, Integer fromId, Integer toId) {
        return String.format(JedisKeyConstant.IM_SESSION_ITEM_LIST_KEY,dbId,patientId,fromId,toId);
    }

    private String getSessionKeyBak(Integer dbId, Integer patientId, Integer fromId, Integer toId){
        return String.format(JedisKeyConstant.IM_SESSION_ITEM_LIST_KEY_BAK,dbId,patientId,fromId,toId);
    }
}
