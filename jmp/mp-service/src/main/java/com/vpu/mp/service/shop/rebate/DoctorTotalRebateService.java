package com.vpu.mp.service.shop.rebate;

import com.vpu.mp.dao.shop.rebate.DoctorTotalRebateDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.rebate.DoctorTotalRebateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yangpengcheng
 * @date 2020/8/27
 **/
@Service
public class DoctorTotalRebateService extends ShopBaseService {
    @Autowired
    private DoctorTotalRebateDao doctorTotalRebateDao;

    /**
     * 根据医师id查询
     * @param doctorId
     * @return
     */
    public DoctorTotalRebateVo getRebateByDoctorId(Integer doctorId){
        DoctorTotalRebateVo doctorTotalRebateVo=doctorTotalRebateDao.getRebateByDoctorId(doctorId);
        return doctorTotalRebateVo;
    }
}
