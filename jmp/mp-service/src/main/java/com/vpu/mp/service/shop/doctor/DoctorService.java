package com.vpu.mp.service.shop.doctor;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Joiner;
import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestConstant;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestResult;
import com.vpu.mp.common.pojo.shop.table.DoctorDo;
import com.vpu.mp.common.pojo.shop.table.UserDo;
import com.vpu.mp.dao.foundation.transactional.DbTransactional;
import com.vpu.mp.dao.foundation.transactional.DbType;
import com.vpu.mp.dao.shop.UserDao;
import com.vpu.mp.dao.shop.department.DepartmentDao;
import com.vpu.mp.dao.shop.doctor.DoctorDao;
import com.vpu.mp.dao.shop.doctor.DoctorDepartmentCoupleDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.department.DepartmentListVo;
import com.vpu.mp.service.pojo.shop.doctor.*;
import com.vpu.mp.service.pojo.shop.patient.UserPatientParam;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.shop.department.DepartmentService;
import com.vpu.mp.service.shop.title.TitleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chenjie
 */
@Service
public class DoctorService extends ShopBaseService {
    /**自动推荐最大数量*/
    public static final int RECOMMEND_MAX_NUM = 10;
    @Autowired
    protected DoctorDao doctorDao;
    @Autowired
    protected DoctorDepartmentCoupleDao doctorDepartmentCoupleDao;
    @Autowired
    protected DepartmentDao departmentDao;
    @Autowired
    public DepartmentService departmentService;
    @Autowired
    public TitleService titleService;
    @Autowired
    public UserDao userDao;
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

    public DoctorOneParam getDoctorByCode(String hospitalCode) {
        return doctorDao.getDoctorByHospitalCode(hospitalCode);
    }

    /**
     * 更新/新增医师
     * @param doctor
     */
    public void synchroDoctor(DoctorOneParam doctor) {
        if(getDoctorByCode(doctor.getHospitalCode()) == null) {
            insertDoctor(doctor);
        } else {
            DoctorOneParam oldDepartment = getDoctorByCode(doctor.getHospitalCode());
            doctor.setId(oldDepartment.getId());
            updateDoctor(doctor);
        }
    }

    public void fetchDoctor(String json) {
        List<DoctorFetchOneParam> doctorFetchList = Util.parseJson(json, new TypeReference<List<DoctorFetchOneParam>>() {
        });
        List<DoctorFetchOneParam> doctorFetchListNew = listDoctorFetch(doctorFetchList);
        for (DoctorFetchOneParam list : doctorFetchListNew) {
            DoctorOneParam doctor = new DoctorOneParam();
            doctor.setName(list.getDoctorName());
            doctor.setCertificateCode(list.getCertificateCode());
            doctor.setHospitalCode(list.getDoctorCode());
            doctor.setProfessionalCode(list.getProfessionalCode());
            doctor.setUrl(list.getDocUrl());
            doctor.setMobile(list.getDocPhone());
            doctor.setSex((list.getDoctorSex() == 1) ? (byte)0:(byte)1);
            doctor.setTitleId(titleService.getTitleIdNew(list.getPositionCode()));

            List<String> result = Arrays.asList(list.getDepartCode().split(","));
            List<Integer> departmentIds = new ArrayList<>();
            for (String code : result) {
                departmentIds.add(departmentService.getDepartmentIdNew(code));
            }
            String departmentStr = Joiner.on(",").join(departmentIds);
            doctor.setDepartmentIdsStr(departmentStr);
            synchroDoctor(doctor);
        }
    }

    public List<DoctorFetchOneParam> listDoctorFetch(List<DoctorFetchOneParam> doctorFetchList){
        Map<String, List<DoctorFetchOneParam>> doctorCodeMap = doctorFetchList.stream().collect(Collectors.groupingBy(DoctorFetchOneParam::getDoctorCode));

        List<DoctorFetchOneParam> doctorList = new ArrayList<>();
        doctorCodeMap.forEach((k, v) -> {
            List<String> departmentCodes = new ArrayList<>();
            for (DoctorFetchOneParam doctor : v) {
                if (doctor.getState() > 1) {
                    continue;
                }
                departmentCodes.add(doctor.getDepartCode());
            }
            int number = v.size();
            DoctorFetchOneParam newDoctor = new DoctorFetchOneParam();
            FieldsUtil.assign(v.get(number-1),newDoctor);
            newDoctor.setDepartCode(Joiner.on(",").join(departmentCodes));
            doctorList.add(newDoctor);
        });
        return doctorList;
    }

    /**
     * 拉取医师列表
     * @return
     */
    public JsonResult fetchExternalDoctor(){
        String appId = ApiExternalRequestConstant.APP_ID_HIS;
        Integer shopId = getShopId();
        String serviceName = ApiExternalRequestConstant.SERVICE_NAME_FETCH_DOCTOR_INFOS;

        Long lastRequestTime = saas().externalRequestHistoryService.getLastRequestTime(ApiExternalRequestConstant.APP_ID_HIS, shopId, ApiExternalRequestConstant.SERVICE_NAME_FETCH_DOCTOR_INFOS);
        DoctorExternalRequestParam param =new DoctorExternalRequestParam();
        param.setStartTime(lastRequestTime);

        ApiExternalRequestResult apiExternalRequestResult = saas().apiExternalRequestService.externalRequestGate(appId, shopId, serviceName, Util.toJson(param));

        // 数据拉取错误
        if (!ApiExternalRequestConstant.ERROR_CODE_SUCCESS.equals(apiExternalRequestResult.getError())) {
            JsonResult result = new JsonResult();
            result.setError(apiExternalRequestResult.getError());
            result.setMessage(apiExternalRequestResult.getMsg());
            result.setContent(apiExternalRequestResult.getData());
            return result;
        }

        fetchDoctor(apiExternalRequestResult.getData());

        return JsonResult.success();
    }

    public boolean isCodeExist(Integer doctorId,String code) {
        boolean flag = doctorDao.isCodeExist(doctorId,code);
        return flag;
    }

    /**
     * @Description
     * @Author 赵晓东
     * @Create 2020-07-22 14:51:11
     * 医师认证
     */
    /**
     * 医师认证
     * @param doctorAuthParam 当前用户姓名、手机号、医师医院唯一编码
     * @return 验证信息
     */
    public Integer doctorAuth(DoctorAuthParam doctorAuthParam){
        // 查询是否有当前医师信息
        DoctorDo doctorDo = doctorDao.doctorAuth(doctorAuthParam);
        // 如果医师存在
        if (doctorDo != null){
            // 修改user表中用户类型为医师
            UserDo userDo = userDao.updateDoctorAuth(doctorAuthParam.getUserId());
            // 修改doctor表中userId为当前用户
            doctorDo.setUserId(doctorAuthParam.getUserId());
            doctorDao.updateUserId(doctorDo);
            return doctorDo.getId();
        } else {
            return Integer.valueOf(DelFlag.NORMAL_VALUE);
        }
    }


    public List<DoctorConsultationOneParam> listRecommendDoctorForConsultation(UserPatientParam doctorParam) {
        List<Integer> doctorDepartments = doctorDepartmentCoupleDao.listHistoryDoctorDepartment(doctorParam);
        List<DoctorConsultationOneParam> historyDoctors = doctorDepartmentCoupleDao.listHistoryDoctor(doctorDepartments);
        if (historyDoctors.size() < RECOMMEND_MAX_NUM) {
            List<DoctorConsultationOneParam> historyDoctorMore = doctorDepartmentCoupleDao.listDoctorMore(doctorDepartments, 10 - historyDoctors.size());
            historyDoctors.addAll(historyDoctorMore);
        }
        return historyDoctors;
    }

    public List<DoctorConsultationOneParam> listDoctorForConsultation(DoctorConsultationParam doctorParam) {
        return doctorDepartmentCoupleDao.listDoctorForConsultation(doctorParam);
    }

    /**
     * 查询医师信息集合
     * @param doctorIds 医师id集合
     * @return
     */
    public List<DoctorSimpleVo> listDoctorSimpleInfo(List<Integer> doctorIds){
        return doctorDao.listDoctorSimpleInfo(doctorIds);
    }

    /**
     * 根据医师信息获取医师所属科室
     * @param doctorId 医师id
     * @return List<Department>
     */
    public List<DepartmentListVo> selectDepartmentsByDoctorId(Integer doctorId){
        return doctorDao.selectDepartmentsByDoctorId(doctorId);
    }

    /**
     * 根据医师职称id查询职称名
     * @param doctorOneParam 医师职称id
     * @return String
     */
    public String selectDoctorTitle(DoctorOneParam doctorOneParam){
        return doctorDao.selectDoctorTitle(doctorOneParam);
    }

}
