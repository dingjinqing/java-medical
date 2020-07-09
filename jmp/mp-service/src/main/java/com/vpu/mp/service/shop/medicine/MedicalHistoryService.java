package com.vpu.mp.service.shop.medicine;


import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.shop.medicalHistory.MedicalHistoryDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;

import com.vpu.mp.service.pojo.shop.medicalHistory.MedicalHistoryListParam;
import com.vpu.mp.service.pojo.shop.medicalHistory.MedicalHistoryListVo;
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
     * 分页查询病历表字段
     * @return PageResult<MedicalHistoryListVo>
     */
    public PageResult<MedicalHistoryListVo> listPageResult(MedicalHistoryListParam medicalHistoryListParam){
        return medicalHistoryDao.getListPageResult(medicalHistoryListParam);
    }
}
