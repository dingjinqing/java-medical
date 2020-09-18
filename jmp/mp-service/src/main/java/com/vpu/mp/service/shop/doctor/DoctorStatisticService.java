package com.vpu.mp.service.shop.doctor;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.DoctorSummaryTrendDo;
import com.vpu.mp.dao.shop.doctor.DoctorDepartmentCoupleDao;
import com.vpu.mp.dao.shop.doctor.DoctorSummaryTrendDao;
import com.vpu.mp.service.pojo.shop.doctor.DoctorStatisticListVo;
import com.vpu.mp.service.pojo.shop.doctor.DoctorStatisticMinMaxVo;
import com.vpu.mp.service.pojo.shop.doctor.DoctorStatisticParam;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenjie
 * @date 2020年09月15日
 */
@Service
public class DoctorStatisticService {

    @Autowired
    protected DoctorSummaryTrendDao doctorSummaryTrendDao;

    @Autowired
    public DoctorService doctorService;

    @Autowired
    public DoctorDepartmentCoupleDao doctorDepartmentCoupleDao;

    /**
     * 统计医师业绩数据
     * @param param
     */
    public void statisticDoctor(DoctorStatisticParam param) {
        DoctorSummaryTrendDo doctorSummary = getDoctorSummary(param);
        DoctorSummaryTrendDo hasStatisticInfo = getDoctorStatistic(param);
        if (hasStatisticInfo != null) {
            doctorSummary.setId(hasStatisticInfo.getId());
            updateDoctorStatistic(doctorSummary);
        } else {
            insertDoctorStatistic(doctorSummary);
        }
    }

    public DoctorSummaryTrendDo getDoctorSummary(DoctorStatisticParam param) {
        DoctorSummaryTrendDo doctorSummaryTrendDo = doctorService.getDoctorStatisData(param);
        doctorSummaryTrendDo.setRefDate(param.getRefDate());
        doctorSummaryTrendDo.setType(param.getType());
        doctorSummaryTrendDo.setDoctorId(param.getDoctorId());
        return doctorSummaryTrendDo;
    }

    /**
     * 查询记录
     *
     * @param param
     * @return
     */
    public DoctorSummaryTrendDo getDoctorStatistic(DoctorStatisticParam param) {
        if (StatisticConstant.TYPE_TODAY.equals(param.getType())) {
            LocalDateTime today = LocalDate.now().atStartOfDay();
            param.setStartTime(Timestamp.valueOf(today));
            param.setEndTime(Timestamp.valueOf(today.plusDays(1)));
            return getDoctorSummary(param);
        } else {
            return doctorSummaryTrendDao.getDoctorStatistic(param);
        }
    }

    /**
     * 添加记录
     *
     * @param param
     * @return
     */
    public void insertDoctorStatistic(DoctorSummaryTrendDo param) {
        doctorSummaryTrendDao.insertDoctorStatistic(param);
    }

    /**
     * 更新记录
     *
     * @param param
     * @return
     */
    public void updateDoctorStatistic(DoctorSummaryTrendDo param) {
        doctorSummaryTrendDao.updateDoctorStatistic(param);
    }

    public PageResult<DoctorStatisticListVo> getDoctorList(DoctorStatisticParam param) {
        if (param.getDepartmentId() != null) {
            List<Integer> departmentIds = new ArrayList<>();
            departmentIds.add(param.getDepartmentId());
            List<Integer> doctorIds = doctorDepartmentCoupleDao.getDoctorIdsByDepartmentIds(departmentIds);
            param.setDoctorIds(doctorIds);
        }
        PageResult<DoctorStatisticListVo> doctorList = new PageResult<>();
        if (param.getType()>0) {
            doctorList = doctorSummaryTrendDao.getDoctorListForType(param);
        } else {
            doctorList = doctorSummaryTrendDao.getDoctorListForCustomize(param);
        }

        return doctorList;
    }

    public DoctorStatisticMinMaxVo getMinMaxStatisticData(Date refDate, Byte type){
        return doctorSummaryTrendDao.getMinMaxStatisticData(refDate,type);
    }

    public DoctorSummaryTrendDo getOneInfo(Date refDate, Byte type, Integer doctorId){
        return doctorSummaryTrendDao.getOneInfo(refDate,type,doctorId);
    }

    public void updateDoctorStatisticScore(Byte type,Date refDate, DoctorStatisticMinMaxVo doctorStatisticMinMax) {
        doctorSummaryTrendDao.updateDoctorStatisticConsultationScore(type,refDate,doctorStatisticMinMax);
        doctorSummaryTrendDao.updateDoctorStatisticInquiryScore(type,refDate,doctorStatisticMinMax);
    }
}
