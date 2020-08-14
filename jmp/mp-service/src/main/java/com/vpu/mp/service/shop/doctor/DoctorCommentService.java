package com.vpu.mp.service.shop.doctor;

import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.DoctorCommentDo;
import com.vpu.mp.dao.shop.doctor.DoctorCommentDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.doctor.DoctorSortParam;
import com.vpu.mp.service.pojo.shop.doctor.comment.DoctorCommentAddParam;
import com.vpu.mp.service.pojo.shop.doctor.comment.DoctorCommentConstant;
import com.vpu.mp.service.pojo.shop.doctor.comment.DoctorCommentListParam;
import com.vpu.mp.service.pojo.shop.doctor.comment.DoctorCommentListVo;
import com.vpu.mp.service.shop.im.ImSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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


    /**
     * 添加评价
     * @param param
     */
    public void addComment(DoctorCommentAddParam param) {
        //保存
        DoctorCommentDo doctorCommentDo = doctorCommentDao.getByImSessionId(param.getImSessionId());
        if (doctorCommentDo!=null){
            doctorCommentDo.setCreateTime(DateUtil.date().toTimestamp());
            doctorCommentDao.update(doctorCommentDo);
        }else {
            param.setAuditStatus(DoctorCommentConstant.CHECK_COMMENT_NOT_CHECK);
            doctorCommentDao.save(param);
        }
        //更新会话
        imSessionService.updateSessionEvaluateStatusToAlready(param.getImSessionId());
        //更新医师评价
        BigDecimal avgCommentStar = doctorCommentDao.getAvgCommentStar(param.getDoctorId());
        DoctorSortParam param1 =new DoctorSortParam();
        param1.setAvgCommentStar(avgCommentStar);
        param1.setDoctorId(param.getDoctorId());
        doctorService.updateAvgCommentStar(param1);
    }

    /**
     * 添加评价
     */
    public void addDefaultComment(Integer doctorId,Integer userId,String orderSn,Integer imSessionId) {
        //保存
        DoctorCommentAddParam param =new DoctorCommentAddParam();
        param.setAuditStatus(DoctorCommentConstant.CHECK_COMMENT_NOT_CHECK);
        param.setDoctorId(doctorId);
        param.setUserId(userId);
        param.setIsAnonymou(BaseConstant.YES);
        param.setOrderSn(orderSn);
        param.setCommNote(DoctorCommentConstant.DOCTOR_DEFAULT_COMMENT);
        param.setImSessionId(imSessionId);
        doctorCommentDao.save(param);
        //更新医师评价
        BigDecimal avgCommentStar = doctorCommentDao.getAvgCommentStar(param.getDoctorId());
        DoctorSortParam param1 =new DoctorSortParam();
        param1.setAvgCommentStar(avgCommentStar);
        param1.setDoctorId(param.getDoctorId());
        doctorService.updateAvgCommentStar(param1);
    }

    /**
     * 医师评价列表
     * @param param
     * @return
     */
    public PageResult<DoctorCommentListVo> listDoctorComment(DoctorCommentListParam param) {
        return doctorCommentDao.listDoctorComment(param);
    }
}
