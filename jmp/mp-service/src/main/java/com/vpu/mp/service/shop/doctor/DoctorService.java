package com.vpu.mp.service.shop.doctor;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.shop.department.DepartmentDao;
import com.vpu.mp.dao.shop.doctor.DoctorDao;
import com.vpu.mp.dao.shop.doctor.DoctorDepartmentCoupleDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.doctor.DoctorDepartmentOneParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorListParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Arrays;

@Service
public class DoctorService extends ShopBaseService {
    @Autowired
    protected DoctorDao doctorDao;
    @Autowired
    protected DoctorDepartmentCoupleDao doctorDepartmentCoupleDao;
    @Autowired
    protected DepartmentDao departmentDao;
//    public DepartmentService departmentService;
    public static final int ZERO = 0;

    public PageResult<DoctorOneParam> getDoctorList(DoctorListParam param) {
        if (param.getDepartmentName() != null) {
            List<Integer> departmentIds = departmentDao.getDepartmentIdsByName(param.getDepartmentName());
            List<Integer> doctorIds = doctorDepartmentCoupleDao.getDoctorIdsByDepartmentIds(departmentIds);
            param.setDoctorIds(doctorIds);
        }
        PageResult<DoctorOneParam> doctorList = doctorDao.getDoctorList(param);
        for (DoctorOneParam list : doctorList.dataList) {
            List<String> departmentList = doctorDepartmentCoupleDao.getDepartmentNamesByDoctorId(list.getId());
            list.setDepartmentNames(departmentList);
        }

        return doctorList;
    }

    public Integer insertDoctor(DoctorOneParam param) {
        doctorDao.insertDoctor(param);
        setDoctorDepartmentCouples(param.getId(),param.getDepartmentIdsStr());
        return param.getId();
    }

    public Integer updateDoctor(DoctorOneParam param) {
        doctorDao.updateDoctor(param);
        if(param.getDepartmentIdsStr() != null){
            setDoctorDepartmentCouples(param.getId(),param.getDepartmentIdsStr());
        }
        return param.getId();
    }
    public Integer enableDoctor(DoctorOneParam param) {
        doctorDao.updateDoctor(param);
        return param.getId();
    }

    public DoctorOneParam getOneInfo(Integer doctorId) {
        DoctorOneParam doctorInfo = doctorDao.getOneInfo(doctorId);
        List<Integer> departmentIds = doctorDepartmentCoupleDao.getDepartmentIdsByDoctorId(doctorId);
        doctorInfo.setDepartmentIds(departmentIds);
        return doctorInfo;
    }

    public void setDoctorDepartmentCouples (Integer doctorId, String departmentIdsStr) {
        doctorDepartmentCoupleDao.deleteDepartmentByDoctor(doctorId);
        List<String> result = Arrays.asList(departmentIdsStr.split(","));
        for (String departmentIdStr : result) {
            Integer departmentId = Integer.parseInt(departmentIdStr);
            DoctorDepartmentOneParam doctorDepartment = new DoctorDepartmentOneParam();
            doctorDepartment.setDoctorId(doctorId);
            doctorDepartment.setDepartmentId(departmentId);
            doctorDepartmentCoupleDao.insertDoctorDepartment(doctorDepartment);
        }
    }
}
