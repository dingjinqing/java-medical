package com.vpu.mp.dao.shop.rebate;

import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.DoctorWithdrawRecord;
import com.vpu.mp.service.pojo.shop.rebate.DoctorWithdrawListParam;
import com.vpu.mp.service.pojo.shop.rebate.DoctorWithdrawParam;
import com.vpu.mp.service.pojo.shop.rebate.DoctorWithdrawVo;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static com.vpu.mp.db.shop.Tables.DOCTOR;
import static com.vpu.mp.db.shop.tables.InquiryOrderRebate.INQUIRY_ORDER_REBATE;
import static org.jooq.impl.DSL.*;
import static com.vpu.mp.db.shop.Tables.DOCTOR_WITHDRAW;

/**
 * @author yangpengcheng
 * @date 2020/8/27
 **/
@Repository
public class DoctorWithdrawDao extends ShopBaseDao {

    /**
     * 添加提现申请
     * @param param
     */
    public void addDoctorWithdraw(DoctorWithdrawParam param){
        DoctorWithdrawRecord record=db().newRecord(DOCTOR_WITHDRAW);
        FieldsUtil.assign(param,record);
        record.insert();
    }

    /**
     * 更新状态
     * @param id
     * @param status
     * @param refuseDesc
     */
    public  void update(Integer id,Byte status,String refuseDesc){
        UpdateSetFirstStep<DoctorWithdrawRecord> update=db().update(DOCTOR_WITHDRAW);
        switch (status){
            case (byte)2:
                update.set(DOCTOR_WITHDRAW.STATUS,status).set(DOCTOR_WITHDRAW.REFUSE_TIME, DateUtils.getSqlTimestamp());
                break;
            case (byte)3:
                update.set(DOCTOR_WITHDRAW.STATUS,status).set(DOCTOR_WITHDRAW.CHECK_TIME, DateUtils.getSqlTimestamp());
                break;
            case (byte)4:
                update.set(DOCTOR_WITHDRAW.STATUS,status).set(DOCTOR_WITHDRAW.BILLING_TIME, DateUtils.getSqlTimestamp());
                break;
                default:
        }
        update.set(DOCTOR_WITHDRAW.REFUSE_DESC, refuseDesc).where(DOCTOR_WITHDRAW.ID.eq(id)).execute();
    }

    /**
     * 根据提现单号获取
     * @param orderSn
     * @return
     */
    public DoctorWithdrawVo getWithdrawByOrderSn(String orderSn){
        return db().select().from(DOCTOR_WITHDRAW).where(DOCTOR_WITHDRAW.ORDER_SN.eq(orderSn)).fetchAnyInto(DoctorWithdrawVo.class);
    }

    /**
     * 数目
     * @param doctorId
     * @return
     */
    public int count(Integer doctorId){
        if(doctorId==null){
            return db().selectCount().from(DOCTOR_WITHDRAW).execute();
        }
        return db().selectCount().from(DOCTOR_WITHDRAW).where(DOCTOR_WITHDRAW.DOCTOR_ID.eq(doctorId)).execute();
    }
    /**
     * 提现记录
     * @param param
     * @return
     */
    public PageResult<DoctorWithdrawVo> getPageList(DoctorWithdrawListParam param){
        SelectJoinStep<? extends Record> select=db().select(DOCTOR.NAME.as("doctorName"),DOCTOR.MOBILE,DOCTOR_WITHDRAW.asterisk()).from(DOCTOR_WITHDRAW);
        select.leftJoin(DOCTOR).on(DOCTOR.ID.eq(DOCTOR_WITHDRAW.DOCTOR_ID));
        select=buildOptions(select,param);
        select.orderBy(DOCTOR_WITHDRAW.CREATE_TIME.desc());
        PageResult<DoctorWithdrawVo> result=this.getPageResult(select,param.getCurrentPage(),param.getPageRows(),DoctorWithdrawVo.class);
        return result;

    }

    public SelectJoinStep<? extends Record> buildOptions(SelectJoinStep<? extends Record> select,DoctorWithdrawListParam param){
        if(param.getDoctorId()!=null){
            select.where(DOCTOR_WITHDRAW.DOCTOR_ID.eq(param.getDoctorId()));
        }
        if(StringUtils.isNotBlank(param.getDoctorName())){
            select.where(DOCTOR.NAME.like(this.likeValue(param.getDoctorName())));
        }
        if(param.getStartTime()!=null){
            select.where(DOCTOR_WITHDRAW.CREATE_TIME.ge(DateUtil.beginOfDay(param.getStartTime()).toTimestamp()));
        }
        if(param.getEndTime()!=null){
            select.where(DOCTOR_WITHDRAW.CREATE_TIME.le(DateUtil.beginOfDay(param.getEndTime()).toTimestamp()));
        }
        return select;
    }

    /**
     * 获取累计提现金额总数
     * @param doctorId
     * @param status
     * @return
     */
    public BigDecimal getWithdrawCashSum(Integer doctorId, Byte status, Timestamp startTime,Timestamp endTime){
        SelectConditionStep<? extends Record> step=db().select(sum(DOCTOR_WITHDRAW.WITHDRAW_CASH)).from(DOCTOR_WITHDRAW).where(DOCTOR_WITHDRAW.DOCTOR_ID.eq(doctorId));
        if(status!=null){
            step.and(DOCTOR_WITHDRAW.STATUS.eq(status));
        }
        if(startTime!=null){
            step.and(DOCTOR_WITHDRAW.CREATE_TIME.ge(startTime));
        }
        if(endTime!=null){
            step.and(DOCTOR_WITHDRAW.CREATE_TIME.le(endTime));
        }
        return step.fetchAnyInto(BigDecimal.class);
    }

}
