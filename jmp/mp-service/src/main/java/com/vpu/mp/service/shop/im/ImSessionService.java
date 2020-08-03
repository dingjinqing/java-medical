package com.vpu.mp.service.shop.im;

import com.vpu.mp.common.foundation.data.ImSessionConstant;
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
import com.vpu.mp.service.pojo.shop.doctor.DoctorSimpleVo;
import com.vpu.mp.service.pojo.shop.patient.PatientSimpleInfoVo;
import com.vpu.mp.service.pojo.wxapp.medical.im.base.ImSessionItemBase;
import com.vpu.mp.service.pojo.wxapp.medical.im.bo.ImSessionItemBo;
import com.vpu.mp.service.pojo.wxapp.medical.im.condition.ImSessionCondition;
import com.vpu.mp.service.pojo.wxapp.medical.im.param.*;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionItemRenderVo;
import com.vpu.mp.service.pojo.wxapp.medical.im.vo.ImSessionListVo;
import com.vpu.mp.service.shop.department.DepartmentService;
import com.vpu.mp.service.shop.doctor.DoctorService;
import com.vpu.mp.service.shop.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.*;
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
    DoctorService doctorService;

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
        List<Integer> doctorIds = new ArrayList<>(dataList.size() / 2);
        List<Integer> patientIds = new ArrayList<>(dataList.size());

        Integer shopId = getShopId();
        Map<Integer, String> imSessionRedisKeyMap = new HashMap<>(dataList.size());
        for (ImSessionListVo imSession : dataList) {
            departmentIds.add(imSession.getDepartmentId());
            patientIds.add(imSession.getPatientId());
            doctorIds.add(imSession.getDoctorId());
            // 准备处理未读消息，医师端展示对应会话信息
            if (param.getDoctorId() != null) {
                imSessionRedisKeyMap.put(imSession.getId(), getSessionRedisKey(shopId, imSession.getId(), imSession.getUserId(), imSession.getDoctorId()));
            } else {
                imSessionRedisKeyMap.put(imSession.getId(), getSessionRedisKey(shopId, imSession.getId(), imSession.getDoctorId(), imSession.getUserId()));
            }
        }

        Map<Integer, String> departmentIdMap = departmentService.listDepartmentInfo(departmentIds).stream().collect(Collectors.toMap(DepartmentSimpleVo::getId, DepartmentSimpleVo::getName, (x1, x2) -> x2));
        Map<Integer, String> patientIdMap = patientService.listPatientInfo(patientIds).stream().collect(Collectors.toMap(PatientSimpleInfoVo::getId, PatientSimpleInfoVo::getName, (x1, x2) -> x2));
        Map<Integer, String> doctorIdMap = doctorService.listDoctorSimpleInfo(doctorIds).stream().collect(Collectors.toMap(DoctorSimpleVo::getId, DoctorSimpleVo::getName, (x1, x2) -> x2));

        for (ImSessionListVo imSession : dataList) {
            imSession.setDepartmentName(departmentIdMap.get(imSession.getDepartmentId()));
            imSession.setPatientName(patientIdMap.get(imSession.getPatientId()));
            imSession.setDoctorName(doctorIdMap.get(imSession.getDoctorId()));
            String sessionKey = imSessionRedisKeyMap.get(imSession.getId());
            Long listSize = jedisManager.getListSize(sessionKey);
            if (listSize != null) {
                imSession.setNewMsgNum(listSize);
            } else {
                imSession.setNewMsgNum(0L);
            }
        }

        return pageResult;
    }

    /**
     * 恢复已有的聊天记录,目前没有做成分页的
     * @param renderPageParam 会话内容请求参数
     * @return 会话聊天内容信息
     */
    public PageResult<ImSessionItemRenderVo> renderSession(ImSessionRenderPageParam renderPageParam) {
        Integer sessionId = renderPageParam.getSessionId();
        ImSessionDo imSessionDo = imSessionDao.getById(sessionId);

        Integer doctorId = imSessionDo.getDoctorId();
        PageResult<ImSessionItemDo> pageResult = imSessionItemDao.getBySessionItemPgaeList(renderPageParam);
        List<ImSessionItemDo> imSessionItemDos = pageResult.getDataList();
        Collections.reverse(imSessionItemDos);

        // 此处需要进行倒序排序
        List<ImSessionItemRenderVo> sessionItemRenderVos = imSessionItemDos.stream().map(item -> {
            ImSessionItemRenderVo itemVo = new ImSessionItemRenderVo();
            itemVo.setDoctor(doctorId.equals(item.getFromId()));
            itemVo.setMessage(item.getMessage());
            itemVo.setType(item.getType());
            itemVo.setSendTime(item.getSendTime());
            return itemVo;
        }).collect(Collectors.toList());
        PageResult<ImSessionItemRenderVo> retPageResult = new PageResult<>();
        retPageResult.setPage(pageResult.getPage());
        retPageResult.setDataList(sessionItemRenderVos);
        return retPageResult;
    }

    /**
     * 查询会话状态
     * @param sessionId 会话id
     * @return 会话状态码
     */
    public ImSessionDo getSessionInfoById(Integer sessionId) {
        return imSessionDao.getById(sessionId);
    }

    /**
     * 查询会话状态
     * @param orderSn
     * @return
     */
    public ImSessionDo getSessionInfoByOrderSn(String orderSn) {
        return imSessionDao.getByOrderSn(orderSn);
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
        imSessionDo.setSessionStatus(ImSessionConstant.SESSION_READY_TO_PAY);

        imSessionDao.insert(imSessionDo);
        String sessionRedisStatusKey = getSessionRedisStatusKey(getShopId(), imSessionDo.getId());
        jedisManager.set(sessionRedisStatusKey, ImSessionConstant.SESSION_READY_TO_PAY.toString());
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
        String sessionRedisStatusKey = getSessionRedisStatusKey(getShopId(), sessionId);
        jedisManager.set(sessionRedisStatusKey, ImSessionConstant.SESSION_ON.toString());
    }

    /**
     * 修改会话状态
     * @param orderSn
     * @param status
     */
    public void updateSessionStatus(String orderSn, Byte status) {
        ImSessionDo imSessionDo = imSessionDao.getByOrderSn(orderSn);
        if (imSessionDo == null) {
            return;
        }
        imSessionDao.updateSessionStatus(imSessionDo.getId(), status);

        String sessionRedisStatusKey = getSessionRedisStatusKey(getShopId(), imSessionDo.getId());
        if (jedisManager.exists(sessionRedisStatusKey)) {
            jedisManager.set(sessionRedisStatusKey, status.toString());
        }
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
            clearSessionRedisInfoAndDumpToDb(shopId, imSessionDo.getId(), imSessionDo.getUserId(), imSessionDo.getDoctorId());
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
            clearSessionRedisInfoAndDumpToDb(shopId, imSessionDo.getId(), imSessionDo.getUserId(), imSessionDo.getDoctorId());
            sessionIds.add(imSessionDo.getId());
        }
        imSessionDao.batchUpdateSessionStatus(sessionIds, ImSessionConstant.SESSION_END);
    }

    /**
     * 将消息发送，存储至redis
     * @param sendMsgParam 会话消息
     */
    public Byte sendMsg(ImSessionSendMsgParam sendMsgParam) {
        Integer shopId = getShopId();
        String sessionRedisStatusKey = getSessionRedisStatusKey(shopId, sendMsgParam.getSessionId());
        if (!jedisManager.exists(sessionRedisStatusKey)) {
            deleteAllSessionKey(shopId, sendMsgParam.getSessionId(), sendMsgParam.getFromId(), sendMsgParam.getToId());
            return ImSessionConstant.SESSION_CAN_NOT_USE;
        }

        String sessionKey = getSessionRedisKey(getShopId(), sendMsgParam.getSessionId(), sendMsgParam.getFromId(), sendMsgParam.getToId());
        jedisManager.rpush(sessionKey, Util.toJson(sendMsgParam.getImSessionItem()));

        return ImSessionConstant.SESSION_CAN_USE;
    }

    /**
     * 拉取对方发送的消息内
     * @return
     */
    public List<ImSessionItemBase> pullMsg(ImSessionPullMsgParam param) {
        String sessionRedisStatusKey = getSessionRedisStatusKey(getShopId(), param.getSessionId());
        if (!jedisManager.exists(sessionRedisStatusKey)) {
            return null;
        }
        return dumpSessionReadyToBak(getShopId(), param.getSessionId(), param.getPullFromId(), param.getSelfId());
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
        clearSessionRedisInfoAndDumpToDb(getShopId(), imSessionDo.getId(), imSessionDo.getUserId(), imSessionDo.getDoctorId());
        imSessionDao.updateSessionStatus(sessionId, ImSessionConstant.SESSION_END);
    }

    /**
     * 清空redis中的会话信息（将医生和患者未读信息全部dump到bak中），并入库
     * @param shopId    店铺id
     * @param sessionId 会话id
     * @param userId    用户id
     * @param doctorId  医师id
     */
    private void clearSessionRedisInfoAndDumpToDb(Integer shopId, Integer sessionId, Integer userId, Integer doctorId) {
        // dump医师发送的信息 对于list类型，如果长度为0则自动删除
        dumpSessionReadyToBak(shopId, sessionId, doctorId, userId);
        // dump用户发送的信息
        dumpSessionReadyToBak(shopId, sessionId, userId, doctorId);
        // dump所有bak信息并入库
        dumpSessionBakToDb(shopId, sessionId);

        String sessionRedisStatusKey = getSessionRedisStatusKey(shopId, sessionId);
        jedisManager.delete(sessionRedisStatusKey);
    }

    /**
     * 将待查看会话中的信息内容移动至已读会话记录列表内
     * @param shopId    店铺id
     * @param sessionId 会话id
     * @param fromId    发送者id
     * @param toId      接受者id
     * @return 待查看会话集合
     */
    private List<ImSessionItemBase> dumpSessionReadyToBak(Integer shopId, Integer sessionId, Integer fromId, Integer toId) {
        String sessionKey = getSessionRedisKey(shopId, sessionId, fromId, toId);
        String sessionBakKey = getSessionRedisKeyBak(shopId, sessionId);

        List<String> readyToReadList = jedisManager.getList(sessionKey);
        jedisManager.cleanList(sessionKey);
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
            ImSessionItemBo imSessionItemBo = new ImSessionItemBo(sessionId, fromId, toId, vo);
            dumpList.add(Util.toJson(imSessionItemBo));
        }
        jedisManager.rpush(sessionBakKey, dumpList);
        return retVos;
    }

    /**
     * 将已读会话记录信息的从redis清空并入库
     * @param shopId    店铺id
     * @param sessionId 会话id
     */
    private void dumpSessionBakToDb(Integer shopId, Integer sessionId) {
        String sessionBakKey = getSessionRedisKeyBak(shopId, sessionId);
        List<String> list = jedisManager.getList(sessionBakKey);
        jedisManager.cleanList(sessionBakKey);
        if (list.size() == 0) {
            return;
        }
        List<ImSessionItemDo> readyToDb = new ArrayList<>();
        for (String s : list) {
            ImSessionItemDo imSessionItemDo = Util.parseJson(s, ImSessionItemDo.class);
            if (imSessionItemDo == null) {
                continue;
            }
            readyToDb.add(imSessionItemDo);
        }
        imSessionItemDao.batchInsert(readyToDb);
    }

    /**
     * 删除所有无用key,避免医师结束了会话而此刻用户又发了消息而产生的垃圾key
     * @param shopId    店铺id
     * @param sessionId 会话id
     * @param id1       会话者的id
     * @param id2       会话者的id
     */
    private void deleteAllSessionKey(Integer shopId, Integer sessionId, Integer id1, Integer id2) {
        String msgKey1 = getSessionRedisKey(shopId, sessionId, id1, id2);
        String msgKey2 = getSessionRedisKey(shopId, sessionId, id2, id1);
        String sessionRedisKeyBak = getSessionRedisKeyBak(shopId, sessionId);
        jedisManager.delete(new String[]{msgKey1, msgKey2, sessionRedisKeyBak});
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
     * @param shopId    店铺id
     * @param sessionId 会话id
     * @param fromId    发送者id
     * @param toId      接受者id
     * @return
     */
    private String getSessionRedisKey(Integer shopId, Integer sessionId, Integer fromId, Integer toId) {
        return String.format(JedisKeyConstant.IM_SESSION_ITEM_LIST_KEY, shopId, sessionId, fromId, toId);
    }

    /**
     * 获取已查看会话记录redis key
     * @param shopId    店铺id
     * @param sessionId 会话id
     * @return
     */
    private String getSessionRedisKeyBak(Integer shopId, Integer sessionId) {
        return String.format(JedisKeyConstant.IM_SESSION_ITEM_LIST_KEY_BAK, shopId, sessionId);
    }

    /**
     * 获取标识会话是否可用的key
     * @param shopId    店铺id
     * @param sessionId 会话id
     * @return
     */
    private String getSessionRedisStatusKey(Integer shopId, Integer sessionId) {
        return String.format(JedisKeyConstant.IM_SESSION_STATUS, shopId, sessionId);
    }
}
