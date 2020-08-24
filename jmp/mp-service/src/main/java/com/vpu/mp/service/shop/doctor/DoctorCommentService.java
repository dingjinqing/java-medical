package com.vpu.mp.service.shop.doctor;

import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.DoctorCommentDo;
import com.vpu.mp.dao.shop.doctor.DoctorCommentDao;
import com.vpu.mp.dao.shop.patient.PatientDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.doctor.DoctorSortParam;
import com.vpu.mp.service.pojo.shop.doctor.comment.DoctorCommentAddParam;
import com.vpu.mp.service.pojo.shop.doctor.comment.DoctorCommentConstant;
import com.vpu.mp.service.pojo.shop.doctor.comment.DoctorCommentListParam;
import com.vpu.mp.service.pojo.shop.doctor.comment.DoctorCommentListVo;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.shop.im.ImSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 添加医师评价
 * @author 孔德成
 * @date 2020/8/12 15:20
 */
@Service
public class DoctorCommentService extends ShopBaseService {

    @Autowired
    private DoctorCommentDao doctorCommentDao;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private ImSessionService imSessionService;
    @Autowired
    private PatientDao patientDao;


    /**
     * 添加评价
     * @param param
     */
    public void addComment(DoctorCommentAddParam param) {
        //保存
        DoctorCommentDo doctorCommentDo = doctorCommentDao.getByImSessionId(param.getImSessionId());
        if (doctorCommentDo!=null){
            doctorCommentDo.setCreateTime(DateUtil.date().toTimestamp());
            doctorCommentDo.setAuditStatus(DoctorCommentConstant.CHECK_COMMENT_PASS);
            doctorCommentDao.update(doctorCommentDo);
            //更新会话
            imSessionService.updateSessionEvaluateStatusToAlready(param.getImSessionId());
        }else {
            param.setAuditStatus(DoctorCommentConstant.CHECK_COMMENT_PASS);
            doctorCommentDao.save(param);
        }
        //更新医师评价
        BigDecimal avgCommentStar = doctorCommentDao.getAvgCommentStar(param.getDoctorId());
        DoctorSortParam param1 =new DoctorSortParam();
        param1.setAvgCommentStar(Optional.ofNullable(avgCommentStar).orElse(BigDecimal.ZERO));
        param1.setDoctorId(param.getDoctorId());
        doctorService.updateAvgCommentStar(param1);
    }

    /**
     * 添加评价
     */
    public void addDefaultComment(Integer doctorId,Integer userId,Integer patientId,String orderSn,Integer imSessionId) {
        //保存
        PatientOneParam oneInfo = patientDao.getOneInfo(patientId);
        DoctorCommentAddParam param =new DoctorCommentAddParam();
        param.setAuditStatus(DoctorCommentConstant.CHECK_COMMENT_PASS);
        param.setDoctorId(doctorId);
        param.setUserId(userId);
        param.setPatientId(patientId);
        param.setIsAnonymou(BaseConstant.YES);
        param.setUserName(oneInfo.getName());
        param.setOrderSn(orderSn);
        param.setCommNote(DoctorCommentConstant.DOCTOR_DEFAULT_COMMENT);
        param.setImSessionId(imSessionId);
        doctorCommentDao.save(param);
        //更新医师评价
        BigDecimal avgCommentStar = doctorCommentDao.getAvgCommentStar(param.getDoctorId());
        DoctorSortParam param1 =new DoctorSortParam();
        param1.setAvgCommentStar(Optional.ofNullable(avgCommentStar).orElse(BigDecimal.ZERO));
        param1.setDoctorId(param.getDoctorId());
        doctorService.updateAvgCommentStar(param1);
    }

    /**
     * 医师评价列表
     * @param param
     * @return
     */
    public PageResult<DoctorCommentListVo> listDoctorComment(DoctorCommentListParam param) {
        PageResult<DoctorCommentListVo> pageResult = doctorCommentDao.listDoctorComment(param);
        pageResult.getDataList().forEach(item->{
            item.setCommNoteLength(item.getCommNote().length());
            if (item.getIsAnonymou().equals(BaseConstant.YES)){
                item.setUserName(DoctorCommentConstant.DOCTOR_COMMENT_ANONYMOU_NAME);
            }
        });

        return pageResult;
    }
}
