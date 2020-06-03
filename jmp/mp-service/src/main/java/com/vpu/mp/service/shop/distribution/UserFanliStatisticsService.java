package com.vpu.mp.service.shop.distribution;


import com.vpu.mp.db.shop.tables.UserFanliStatistics;
import com.vpu.mp.db.shop.tables.records.UserFanliStatisticsRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 商品返利统计表
 * @author 王帅
 */
@Service
public class UserFanliStatisticsService extends ShopBaseService {

    UserFanliStatistics TABLE = UserFanliStatistics.USER_FANLI_STATISTICS;

    public void update(Integer userId, Integer inviteId, Byte level, BigDecimal currentMoney, BigDecimal totalMoney) {
        UserFanliStatisticsRecord record = db().selectFrom(TABLE).where(TABLE.USER_ID.eq(userId).and(TABLE.REBATE_LEVEL.eq(level).and(TABLE.FANLI_USER_ID.eq(inviteId)))).fetchOne();
        //TODO OrderNumber需重新计算
        if(record == null) {
            UserFanliStatisticsRecord insert = db().newRecord(TABLE);
            insert.setUserId(userId);
            insert.setFanliUserId(inviteId);
            insert.setOrderNumber(1);
            insert.setRebateLevel(level);
            insert.setTotalFanliMoney(currentMoney);
            insert.setTotalCanFanliMoney(totalMoney);
            insert.insert();
        }else {
            record.setOrderNumber(record.getOrderNumber() + 1);
            record.setTotalFanliMoney(BigDecimalUtil.add(record.getTotalFanliMoney(), currentMoney));
            record.setTotalCanFanliMoney(BigDecimalUtil.add(record.getTotalCanFanliMoney(), totalMoney));
            record.update();
        }
    }
}

