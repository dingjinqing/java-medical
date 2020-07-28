package com.vpu.mp.service.shop.user;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.Tables.DOCTOR;

/**
 * @author 赵晓东
 * @description
 * @create 2020-07-28 15:33
 **/
@Service
public class AdminUserService extends ShopBaseService {

    /**
     * 得到当前医师
     * @param userId 用户id
     * @return String
     */
    public Integer getDoctorId(Integer userId){
        return db().select(DOCTOR.HOSPITAL_CODE).from(DOCTOR).where(DOCTOR.USER_ID.eq(userId)).fetchOneInto(Integer.class);
    }
}
