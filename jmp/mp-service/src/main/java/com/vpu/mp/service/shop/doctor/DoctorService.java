package com.vpu.mp.service.shop.doctor;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.shop.doctor.DoctorDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorListParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService extends ShopBaseService {
    @Autowired
    protected DoctorDao doctorDao;
//    public DepartmentService departmentService;
    public static final int ZERO = 0;

    public PageResult<DoctorOneParam> getDoctorList(DoctorListParam param) {
        PageResult<DoctorOneParam> doctorList = doctorDao.getDoctorList(param);
        for (DoctorOneParam list : doctorList.dataList) {
            List<DepartmentOneParam> departmentList = doctorDao.getDepartmentsByDoctorId(list.getId());
            list.setDepartmentList(departmentList);
        }

        return doctorList;
    }

    public Integer insertDoctor(DoctorOneParam param) {
        transaction(() -> {
            int doctorId = doctorDao.insertDoctor(param);
            param.setId(doctorId);
        });
        return param.getId();
    }

    public Integer updateDoctor(DoctorOneParam param) {
        transaction(() -> {
            int doctorId = doctorDao.updateDoctor(param);
            param.setId(doctorId);
        });
        return param.getId();
    }

    public DoctorOneParam getOneInfo(Integer doctorId) {
        return doctorDao.getOneInfo(doctorId);
    }
}
