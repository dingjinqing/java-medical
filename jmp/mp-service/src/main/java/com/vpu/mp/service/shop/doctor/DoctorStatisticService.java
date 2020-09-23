package com.vpu.mp.service.shop.doctor;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.DoctorSummaryTrendDo;
import com.vpu.mp.dao.shop.doctor.DoctorDepartmentCoupleDao;
import com.vpu.mp.dao.shop.doctor.DoctorSummaryTrendDao;
import com.vpu.mp.service.pojo.saas.shop.ShopListInfoVo;
import com.vpu.mp.service.pojo.shop.doctor.*;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticConstant;
import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.service.shop.ShopApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.service.shop.task.overview.GoodsStatisticTaskService.TYPE_LIST_1;

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

    @Autowired
    private SaasApplication saas;

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

    /**
     * 获取医师统计数据
     * @param param
     * @return
     */
    public PageResult<DoctorStatisticListVo> getDoctorSummaryList(DoctorStatisticParam param) {
        if (param.getDepartmentId() != null) {
            List<Integer> departmentIds = new ArrayList<>();
            departmentIds.add(param.getDepartmentId());
            List<Integer> doctorIds = doctorDepartmentCoupleDao.getDoctorIdsByDepartmentIds(departmentIds);
            param.setDoctorIds(doctorIds);
        }
        PageResult<DoctorStatisticListVo> doctorList = new PageResult<>();
        if (param.getType() != null && param.getType()>0) {
            doctorList = doctorSummaryTrendDao.getDoctorListForType(param);
        } else {
            doctorList = doctorSummaryTrendDao.getDoctorListForCustomize(param);
        }
        for (DoctorStatisticListVo doctor:doctorList.getDataList()) {
            List<String> departmentList = doctorDepartmentCoupleDao.getDepartmentNamesByDoctorId(doctor.getDoctorId());
            doctor.setDepartmentNames(departmentList);
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

    public void doctorStatistics() {
        List<ShopListInfoVo> result = saas.shopService.getShopListInfo();
        result.forEach((r) -> {
            ShopApplication shop = saas.getShopApp(r.getShopId());
            List<DoctorOneParam> allDoctors = shop.doctorService.getAllDoctor();
            allDoctors.forEach((d)->{
                shop.doctorTaskService.insertDoctorStatistic(d.getId());
            });
            if (allDoctors.size() > 0) {
                LocalDateTime today = LocalDate.now().atStartOfDay();
                Date refDate = Date.valueOf(today.minusDays(1).toLocalDate());
                DoctorStatisticAllMinMaxVo doctorStatisticAllMinMaxVo = new DoctorStatisticAllMinMaxVo();
                doctorStatisticAllMinMaxVo.setOneMinMax(shop.doctorStatisticService.getMinMaxStatisticData(refDate, (byte) 1));
                doctorStatisticAllMinMaxVo.setWeekMinMax(shop.doctorStatisticService.getMinMaxStatisticData(refDate, (byte) 7));
                doctorStatisticAllMinMaxVo.setMonthMinMax(shop.doctorStatisticService.getMinMaxStatisticData(refDate, (byte) 30));
                doctorStatisticAllMinMaxVo.setSeasonMinMax(shop.doctorStatisticService.getMinMaxStatisticData(refDate, (byte) 90));
                TYPE_LIST_1.forEach((t) -> {
                    DoctorStatisticMinMaxVo doctorStatisticMinMax = shop.doctorTaskService.getMinMaxByType(doctorStatisticAllMinMaxVo, t);
                    shop.doctorTaskService.updateDoctorStatisticScore(t, refDate, doctorStatisticMinMax);
                });
            }
        });
    }
}
