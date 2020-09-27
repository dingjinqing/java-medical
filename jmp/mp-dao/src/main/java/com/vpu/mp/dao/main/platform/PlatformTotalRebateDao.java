package com.vpu.mp.dao.main.platform;

import com.vpu.mp.common.foundation.util.BigDecimalUtil;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.main.table.PlatformTotalRebateDo;
import com.vpu.mp.dao.foundation.base.MainBaseDao;
import com.vpu.mp.db.main.tables.records.PlatformTotalRebateRecord;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.main.Tables.PLATFORM_TOTAL_REBATE;

/**
 * @author yangpengcheng
 * @date 2020/9/27
 **/
@Repository
public class PlatformTotalRebateDao extends MainBaseDao {

    /**
     * 新增
     * @param platformTotalRebateDo
     */
    public void savePlatFormTotalRebate(PlatformTotalRebateDo platformTotalRebateDo){
        PlatformTotalRebateRecord record=db().selectFrom(PLATFORM_TOTAL_REBATE).where(PLATFORM_TOTAL_REBATE.SHOP_ID.eq(platformTotalRebateDo.getShopId())).fetchOne();
        if(record==null){
            PlatformTotalRebateRecord recordInsert=db().newRecord(PLATFORM_TOTAL_REBATE);
            FieldsUtil.assign(platformTotalRebateDo,recordInsert);
            recordInsert.insert();
        }else {
            record.setTotalMoney(BigDecimalUtil.add(record.getTotalMoney(),platformTotalRebateDo.getTotalMoney()));
            record.setFinalMoney(BigDecimalUtil.add(record.getFinalMoney(),platformTotalRebateDo.getFinalMoney()));
            record.update();
        }

    }

}
