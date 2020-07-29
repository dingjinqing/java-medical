package com.vpu.mp.service.shop.medicine;


import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.shop.medicalhistory.MedicalHistoryDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;

import com.vpu.mp.service.pojo.shop.medicalhistory.MedicalHistoryListParam;
import com.vpu.mp.service.pojo.shop.medicalhistory.MedicalHistoryListVo;
import com.vpu.mp.service.pojo.shop.medicalhistory.MedicalHistoryPageInfoParam;
import com.vpu.mp.service.pojo.shop.medicalhistory.MedicalHistoryPageInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 赵晓东
 * @description
 * @create 2020-07-07 10:29
 */
@Service
public class MedicalHistoryService extends ShopBaseService {

    @Autowired
    private MedicalHistoryDao medicalHistoryDao;

    /**
     * 查询病历表详情字段
     * @return PageResult<MedicalHistoryListVo>
     */
    public MedicalHistoryListVo getMedicalHistoryDetail(MedicalHistoryListParam medicalHistoryListParam){
        return medicalHistoryDao.getMedicalHistoryDetail(medicalHistoryListParam);
    }

    /**
     * 分页查询病历展示页面字段
     * @param medicalHistoryPageInfoParam 病历表分页入参
     * @return PageResult<MedicalHistoryPageInfoVo>
     */
    public PageResult<MedicalHistoryPageInfoVo> getMedicalHistoryPageInfo(MedicalHistoryPageInfoParam medicalHistoryPageInfoParam){
        return medicalHistoryDao.getMedicalHistoryPageInfo(medicalHistoryPageInfoParam);
    }
}
