package com.vpu.mp.service.shop.doctor;

import com.vpu.mp.dao.shop.doctor.DoctorCommentReplyDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 医师评价回复
 * @author 孔德成
 * @date 2020/8/26 14:17
 */
@Service
public class DoctorCommentReplyService extends ShopBaseService {
    @Autowired
    private DoctorCommentReplyDao doctorCommentReplyDao;



}
