package com.vpu.mp.dao.shop.store;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.pojo.shop.table.StoreDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.vpu.mp.db.shop.tables.Store.STORE;
/**
 * @author chenjie
 * @date 2020年08月24日
 */
@Repository
public class StoreDao extends ShopBaseDao {

    /**
     * 已关店
     */
    private static final Byte STORE_BUSINESS_CLOSE = 0;
    /**
     * 在营业
     */
    private static final Byte STORE_BUSINESS_OPENING = 1;
    /**
     * 每天营业
     */
    private static final Byte STORE_BUSINESS_OPEN_EVERYDAY = 1;
    /**
     * 工作日营业
     */
    private static final Byte STORE_BUSINESS_WORKDAY = 0;
    /**
     * 周六
     */
    private static final int SATURDAY = 7;
    /**
     * 周日
     */
    private static final int SUNDAY = 1;

    public StoreBasicVo getStoreByNo(String storeNo) {
        return db().selectFrom(STORE).where(STORE.STORE_CODE.eq(storeNo)).fetchAnyInto(StoreBasicVo.class);
    }

    public List<StoreBasicVo> listStoreCodes() {
        Condition condition = STORE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE);
        return db().select(STORE.STORE_ID, STORE.STORE_CODE).from(STORE).where(condition).fetchInto(StoreBasicVo.class);
    }

    /**
     * 查询当前在营业门店列表
     * @return StoreDo
     */
    public List<StoreDo> getStoreOpen() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date date = new Date();
            String dateStringParse = sdf.format(date);
            System.out.println(dateStringParse);
            Date today = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(today);
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            SelectConditionStep<Record> select = db().select().from(STORE)
                .where(STORE.BUSINESS_STATE.eq(STORE_BUSINESS_OPENING));
            // 如果不是工作日，查询每天营业的门店
            if (weekday == SATURDAY || weekday == SUNDAY) {
                select.and(STORE.BUSINESS_TYPE.eq(STORE_BUSINESS_WORKDAY));
            }
            // 查询未打烊门店
            select.and(STORE.OPENING_TIME.lt(dateStringParse));
            select.and(STORE.CLOSE_TIME.gt(dateStringParse));
            select.limit(10);
            return select.fetchInto(StoreDo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
