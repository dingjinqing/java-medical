package com.vpu.mp.service.shop.im;

import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.common.pojo.shop.table.ImSessionDo;
import com.vpu.mp.common.pojo.shop.table.ImSessionItemDo;
import com.vpu.mp.dao.shop.session.ImSessionDao;
import com.vpu.mp.dao.shop.session.ImSessionItemDao;
import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.department.DepartmentSimpleVo;
import com.vpu.mp.service.pojo.shop.patient.PatientSimpleInfoVo;
import com.vpu.mp.service.pojo.wxapp.medical.im.ImSessionConstant;
import com.vpu.mp.service.pojo.wxapp.medical.im.base.ImSessionItemBase;
import com.vpu.mp.service.pojo.wxapp.medical.im.condition.ImSessionCondition;
import com.vpu.mp.service.pojo.wxapp.medical.im.param.ImSessionNewParam;
import com.vpu.mp.service.pojo.wxapp.medical.im.param.ImSessionPageListParam;
import com.vpu.mp.service.pojo.wxapp.medical.im.param.ImSessionPullMsgParam;
import com.vpu.mp.service.pojo.wxapp.medical.im.param.ImSessionSendMsgParam;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionItemRenderVo;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionListVo;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionRenderVo;
import com.vpu.mp.service.shop.department.DepartmentService;
import com.vpu.mp.service.shop.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 会话处理service
 * @author 李晓冰
 * @date 2020年07月21日
 */
@Service
public class ImSessionService extends ShopBaseService {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    PatientService patientService;

    @Autowired
    private ImSessionDao imSessionDao;
    @Autowired
    private ImSessionItemDao imSessionItemDao;
    @Autowired
    private JedisManager jedisManager;

    /**
     * 分页查询会话信息
     * @param param 分页条件
     * @return 分页结果
     */
    public PageResult<ImSessionListVo> pageList(ImSessionPageListParam param) {
        PageResult<ImSessionListVo> pageResult = imSessionDao.pageList(param);
        List<ImSessionListVo> dataList = pageResult.getDataList();
        List<Integer> departmentIds = new ArrayList<>(dataList.size() / 2);
        List<Integer> patientIds = new ArrayList<>(dataList.size());
        for (ImSessionListVo imSession : dataList) {
            departmentIds.add(imSession.getDepartmentId());
            patientIds.add(imSession.getPatientId());
        }

        Map<Integer, String> departmentIdMap = departmentService.listDepartmentInfo(departmentIds).stream().collect(Collectors.toMap(DepartmentSimpleVo::getId, DepartmentSimpleVo::getName, (x1, x2) -> x2));
        Map<Integer, String> patientIdMap = patientService.listPatientInfo(patientIds).stream().collect(Collectors.toMap(PatientSimpleInfoVo::getId, PatientSimpleInfoVo::getName, (x1, x2) -> x2));

        for (ImSessionListVo imSession : dataList) {
            imSession.setDepartmentName(departmentIdMap.get(imSession.getDepartmentId()));
            imSession.setPatientName(patientIdMap.get(imSession.getDepartmentId()));
        }

        return pageResult;
    }

    /**
     * 恢复已有的聊天记录,目前没有做成分页的
     * @param sessionId 会话id
     * @return 会话聊天内容信息
     */
    public ImSessionRenderVo renderSession(Integer sessionId) {
        ImSessionDo imSessionDo = imSessionDao.getById(sessionId);
        Integer doctorId = imSessionDo.getDoctorId();
        List<ImSessionItemDo> imSessionItemDos = imSessionItemDao.getBySessionId(sessionId);
        ImSessionRenderVo imSessionRenderVo = new ImSessionRenderVo();
        imSessionRenderVo.setLimitTime(imSessionDo.getLimitTime());

        List<ImSessionItemRenderVo> sessionItemRenderVos = imSessionItemDos.stream().sorted(Comparator.comparing(ImSessionItemDo::getSendTime)).map(item -> {
            ImSessionItemRenderVo itemVo = new ImSessionItemRenderVo();
            itemVo.setDoctor(doctorId.equals(item.getFormId()));
            itemVo.setMessage(item.getMessage());
            itemVo.setType(item.getType());
            itemVo.setSendTime(item.getSendTime());
            return itemVo;
        }).collect(Collectors.toList());
        imSessionRenderVo.setSessionItemRenderVos(sessionItemRenderVos);
        return imSessionRenderVo;
    }

    /**
     * 新增待接诊会话
     * @param param 新增会话信息
     * @return 会话id
     */
    public Integer insertNewSession(ImSessionNewParam param) {
        ImSessionDo imSessionDo = new ImSessionDo();
        imSessionDo.setDoctorId(param.getDoctorId());
        imSessionDo.setDepartmentId(param.getDepartmentId());
        imSessionDo.setUserId(param.getUserId());
        imSessionDo.setPatientId(param.getPatientId());
        imSessionDo.setOrderSn(param.getOrderSn());
        imSessionDo.setSessionStatus(ImSessionConstant.SESSION_READY_TO_START);

        imSessionDao.insert(imSessionDo);
        return imSessionDo.getId();
    }

    /**
     * 会话状态修改为进行中
     * @param sessionId 会话id
     * @return
     */
    public void updateSessionToGoingOn(Integer sessionId) {
        ImSessionDo imSessionDo = imSessionDao.getById(sessionId);
        imSessionDo.setSessionStatus(ImSessionConstant.SESSION_ON);
        imSessionDo.setLimitTime(DateUtils.getTimeStampPlus(ImSessionConstant.CLOSE_LIMIT_TIME, ChronoUnit.HOURS));
        imSessionDao.update(imSessionDo);
    }

    /**
     * 批量取消未接诊过期的会话
     */
    public void batchCancelSession(List<String> orderSns) {
        ImSessionCondition cancelCondition = new ImSessionCondition();
        cancelCondition.setOrderSns(orderSns);
        List<ImSessionDo> imSessionDos = imSessionDao.listImSession(cancelCondition);
        List<Integer> sessionIds = new ArrayList<>(imSessionDos.size());
        Integer shopId = getShopId();

        for (ImSessionDo imSessionDo : imSessionDos) {
            // 删除并入库用户聊天信息
            dumpAndDeleteSessionReadyAndBakToDb(shopId, imSessionDo.getId(), imSessionDo.getDepartmentId(), imSessionDo.getPatientId(), imSessionDo.getUserId(), imSessionDo.getDoctorId());
            // 删除并入库医师聊天信息
            dumpAndDeleteSessionReadyAndBakToDb(shopId, imSessionDo.getId(), imSessionDo.getDepartmentId(), imSessionDo.getPatientId(), imSessionDo.getDoctorId(), imSessionDo.getUserId());
            sessionIds.add(imSessionDo.getId());
        }

        imSessionDao.batchUpdateSessionStatus(sessionIds, ImSessionConstant.SESSION_CANCEL);
    }

    /**
     * 批量关闭到时间的会话
     */
    public void batchCloseSession(List<String> orderSns) {
        ImSessionCondition cancelCondition = new ImSessionCondition();
        cancelCondition.setOrderSns(orderSns);
        List<ImSessionDo> imSessionDos = imSessionDao.listImSession(cancelCondition);
        Integer shopId = getShopId();
        List<Integer> sessionIds = new ArrayList<>(imSessionDos.size());
        for (ImSessionDo imSessionDo : imSessionDos) {
            // 删除并入库用户聊天信息
            dumpAndDeleteSessionReadyAndBakToDb(shopId, imSessionDo.getId(), imSessionDo.getDepartmentId(), imSessionDo.getPatientId(), imSessionDo.getUserId(), imSessionDo.getDoctorId());
            // 删除并入库医师聊天信息
            dumpAndDeleteSessionReadyAndBakToDb(shopId, imSessionDo.getId(), imSessionDo.getDepartmentId(), imSessionDo.getPatientId(), imSessionDo.getDoctorId(), imSessionDo.getUserId());
            sessionIds.add(imSessionDo.getId());
        }
        imSessionDao.batchUpdateSessionStatus(sessionIds, ImSessionConstant.SESSION_END);
    }

    /**
     * 将消息发送，存储至redis
     * @param sendMsgParam 会话消息
     */
    public void sendMsg(ImSessionSendMsgParam sendMsgParam) {
        String sessionKey = getSessionRedisKey(getShopId(), sendMsgParam.getDepartmentId(), sendMsgParam.getPatientId(), sendMsgParam.getFromId(), sendMsgParam.getToId());
        jedisManager.rpush(sessionKey, Util.toJson(sendMsgParam.getImSessionItem()));
    }

    /**
     * 拉取对方发送的消息内
     * @return
     */
    public List<ImSessionItemBase> pullMsg(ImSessionPullMsgParam param) {
        return dumpSessionReadyToBak(getShopId(), param.getDepartmentId(), param.getPatientId(), param.getPullFromId(), param.getSelfId());
    }

    /**
     * 关闭对话session
     * @param sessionId 会话id
     */
    public void closeImSession(Integer sessionId) {
        ImSessionDo imSessionDo = imSessionDao.getById(sessionId);
        if (!ImSessionConstant.SESSION_ON.equals(imSessionDo.getSessionStatus())) {
            return;
        }
        imSessionDao.updateSessionStatus(sessionId, ImSessionConstant.SESSION_END);
        Integer shopId = getShopId();

        // 删除并入库用户聊天信息
        dumpAndDeleteSessionReadyAndBakToDb(shopId, sessionId, imSessionDo.getDepartmentId(), imSessionDo.getPatientId(), imSessionDo.getUserId(), imSessionDo.getDoctorId());
        // 删除并入库医师聊天信息
        dumpAndDeleteSessionReadyAndBakToDb(shopId, sessionId, imSessionDo.getDepartmentId(), imSessionDo.getPatientId(), imSessionDo.getDoctorId(), imSessionDo.getUserId());
    }

    private ImSessionItemDo convertImSessionItemBasetoDo(ImSessionItemBase imSessionItemBase, Integer sessionId, Integer fromId, Integer toId) {
        ImSessionItemDo imSessionItemDo = new ImSessionItemDo();
        imSessionItemDo.setImSessionId(sessionId);
        imSessionItemDo.setFormId(fromId);
        imSessionItemDo.setToId(toId);
        imSessionItemDo.setMessage(imSessionItemBase.getMessage());
        imSessionItemDo.setType(imSessionItemBase.getType());
        imSessionItemDo.setSendTime(imSessionItemBase.getSendTime());
        return imSessionItemDo;
    }

    /**
     * 将待查看会话中的信息内容移动至已读会话记录列表内
     * @param shopId       店铺id
     * @param departmentId 科室id
     * @param patientId    患者id
     * @param fromId       发送者id
     * @param toId         接受者id
     * @return 待查看会话集合
     */
    private List<ImSessionItemBase> dumpSessionReadyToBak(Integer shopId, Integer departmentId, Integer patientId, Integer fromId, Integer toId) {
        String sessionKey = getSessionRedisKey(shopId, departmentId, patientId, fromId, toId);
        String sessionBakKey = getSessionRedisKeyBak(shopId, departmentId, patientId, fromId, toId);

        List<String> readyToReadList = jedisManager.getList(sessionKey);
        List<ImSessionItemBase> retVos = new ArrayList<>(readyToReadList.size());
        // 没有需要读的信息
        if (readyToReadList.size() == 0) {
            return retVos;
        }

        List<String> dumpList = new ArrayList<>(readyToReadList.size());

        Timestamp curTime = DateUtils.getLocalDateTime();
        for (String s : readyToReadList) {
            ImSessionItemBase vo = Util.parseJson(s, ImSessionItemBase.class);
            if (vo == null) {
                continue;
            }
            vo.setSendTime(curTime);
            retVos.add(vo);
            dumpList.add(Util.toJson(vo));
        }
        jedisManager.cleanList(sessionKey);
        jedisManager.rpush(sessionBakKey, dumpList);
        return retVos;
    }

    /**
     * 将已读会话记录信息的从redis清空并入库
     * @param shopId       店铺id
     * @param sessionId    会话id
     * @param departmentId 科室id
     * @param patientId    患者id
     * @param fromId       发送者id
     * @param toId         接受者id
     */
    private void dumpSessionBakToDb(Integer shopId, Integer sessionId, Integer departmentId, Integer patientId, Integer fromId, Integer toId) {
        String sessionBakKey = getSessionRedisKeyBak(shopId, departmentId, patientId, fromId, toId);
        List<String> list = jedisManager.getList(sessionBakKey);
        if (list.size() == 0) {
            return;
        }

        jedisManager.cleanList(sessionBakKey);
        List<ImSessionItemDo> readyToDb = new ArrayList<>();
        for (String s : list) {
            ImSessionItemBase imSessionItemBase = Util.parseJson(s, ImSessionItemBase.class);
            if (imSessionItemBase == null) {
                continue;
            }
            ImSessionItemDo imSessionItemDo = convertImSessionItemBasetoDo(imSessionItemBase, sessionId, fromId, toId);
            readyToDb.add(imSessionItemDo);
        }
        imSessionItemDao.batchInsert(readyToDb);
    }

    /**
     * 删除未读和已读的所有消息，并将已有消息入库
     * @param shopId       店铺id
     * @param sessionId    会话id
     * @param departmentId 科室id
     * @param patientId    患者id
     * @param fromId       发送者id
     * @param toId         接受者id
     */
    private void dumpAndDeleteSessionReadyAndBakToDb(Integer shopId, Integer sessionId, Integer departmentId, Integer patientId, Integer fromId, Integer toId) {
        dumpSessionReadyToBak(shopId, departmentId, patientId, fromId, toId);
        dumpSessionBakToDb(shopId, sessionId, departmentId, patientId, fromId, toId);
        String sessionRedisKey = getSessionRedisKey(shopId, departmentId, patientId, fromId, toId);
        String sessionRedisKeyBak = getSessionRedisKeyBak(shopId, departmentId, patientId, fromId, toId);

        jedisManager.delete(new String[]{sessionRedisKey, sessionRedisKeyBak});
    }

    /**
     * 判断信息
     * @param sessionId
     * @return
     */
    public boolean sessionExist(Integer sessionId) {
        return imSessionDao.getById(sessionId) != null;
    }

    /**
     * 获取待查看会话redis key
     * @param shopId       店铺id
     * @param departmentId 科室id
     * @param patientId    患者id
     * @param fromId       发送者id
     * @param toId         接受者id
     * @return
     */
    private String getSessionRedisKey(Integer shopId, Integer departmentId, Integer patientId, Integer fromId, Integer toId) {
        return String.format(JedisKeyConstant.IM_SESSION_ITEM_LIST_KEY, shopId, departmentId, patientId, fromId, toId);
    }

    /**
     * 获取已查看会话记录redis key
     * @param shopId       店铺id
     * @param departmentId 科室id
     * @param patientId    患者id
     * @param fromId       发送者id
     * @param toId         接受者id
     * @return
     */
    private String getSessionRedisKeyBak(Integer shopId, Integer departmentId, Integer patientId, Integer fromId, Integer toId) {
        return String.format(JedisKeyConstant.IM_SESSION_ITEM_LIST_KEY_BAK, shopId, departmentId, patientId, fromId, toId);
    }
}
