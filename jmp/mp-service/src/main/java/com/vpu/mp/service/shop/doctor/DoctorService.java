package com.vpu.mp.service.shop.doctor;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.shop.doctor.DoctorDao;
import com.vpu.mp.dao.shop.doctor.DoctorDepartmentCoupleDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.doctor.DoctorDepartmentOneParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorListParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService extends ShopBaseService {
    @Autowired
    protected DoctorDao doctorDao;
    @Autowired
    protected DoctorDepartmentCoupleDao doctorDepartmentCoupleDao;
//    public DepartmentService departmentService;
    public static final int ZERO = 0;

    public PageResult<DoctorOneParam> getDoctorList(DoctorListParam param) {
        PageResult<DoctorOneParam> doctorList = doctorDao.getDoctorList(param);
        for (DoctorOneParam list : doctorList.dataList) {
            List<String> departmentList = doctorDepartmentCoupleDao.getDepartmentNamesByDoctorId(list.getId());
            list.setDepartmentNames(departmentList);
        }

        return doctorList;
    }

    public Integer insertDoctor(DoctorOneParam param) {
        int doctorId = doctorDao.insertDoctor(param);
        param.setId(doctorId);
        setDoctorDepartmentCouples(doctorId,param.getDepartmentIds());
        return param.getId();
    }

    public Integer updateDoctor(DoctorOneParam param) {
        int doctorId = doctorDao.updateDoctor(param);
        param.setId(doctorId);
        setDoctorDepartmentCouples(doctorId,param.getDepartmentIds());
        return param.getId();
    }

    public DoctorOneParam getOneInfo(Integer doctorId) {
        DoctorOneParam doctorInfo = doctorDao.getOneInfo(doctorId);
        List<Integer> departmentIds = doctorDepartmentCoupleDao.getDepartmentIdsByDoctorId(doctorId);
        doctorInfo.setDepartmentIds(departmentIds);
        return doctorInfo;
    }

    public void setDoctorDepartmentCouples (Integer doctorId, List<Integer> departmentIds) {
        doctorDepartmentCoupleDao.deleteDepartmentByDoctor(doctorId);
        for (Integer departmentId : departmentIds) {
            DoctorDepartmentOneParam doctorDepartment = new DoctorDepartmentOneParam();
            doctorDepartment.setDoctorId(doctorId);
            doctorDepartment.setDepartmentId(departmentId);
            doctorDepartmentCoupleDao.insertDoctorDepartment(doctorDepartment);
        }
    }
}
