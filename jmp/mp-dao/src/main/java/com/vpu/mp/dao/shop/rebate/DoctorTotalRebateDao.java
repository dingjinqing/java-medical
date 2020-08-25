package com.vpu.mp.dao.shop.rebate;

import com.vpu.mp.common.foundation.util.BigDecimalUtil;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.DoctorTotalRebateRecord;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

import static com.vpu.mp.db.shop.Tables.DOCTOR_TOTAL_REBATE;

/**
 * @author yangpengcheng
 * @date 2020/8/25
 **/
@Repository
public class DoctorTotalRebateDao extends ShopBaseDao {

    /**
     * 更新
     * @param doctorId
     * @param money
     */
    public void updateDoctorTotalRebate(Integer doctorId, BigDecimal money){
        DoctorTotalRebateRecord record=db().selectFrom(DOCTOR_TOTAL_REBATE).where(DOCTOR_TOTAL_REBATE.DOCTOR_ID.eq(doctorId)).fetchOne();
        if(record==null){
            DoctorTotalRebateRecord recordInsert=db().newRecord(DOCTOR_TOTAL_REBATE);
            recordInsert.setDoctorId(doctorId);
            recordInsert.setTotalMoney(money);
            recordInsert.setFinalMoney(money);
            recordInsert.insert();
        }else {
            record.setTotalMoney(BigDecimalUtil.add(record.getTotalMoney(),money));
            record.setFinalMoney(BigDecimalUtil.add(record.getFinalMoney(),money));
            record.setUpdateTime(DateUtils.getLocalDateTime());
            record.update();
        }
    }
}
