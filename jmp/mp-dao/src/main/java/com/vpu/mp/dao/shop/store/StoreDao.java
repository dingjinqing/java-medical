package com.vpu.mp.dao.shop.store;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.pojo.shop.table.StoreDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import com.vpu.mp.service.pojo.wxapp.store.StoreConfigConstant;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.vpu.mp.db.shop.tables.Store.STORE;
import static com.vpu.mp.db.shop.tables.StoreGoods.STORE_GOODS;
import static com.vpu.mp.service.pojo.wxapp.store.StoreConfigConstant.*;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;

/**
 * @author chenjie
 * @date 2020年08月24日
 */
@Repository
public class StoreDao extends ShopBaseDao {

    public StoreBasicVo getStoreByNo(String storeNo) {
        return db().selectFrom(STORE).where(STORE.STORE_CODE.eq(storeNo)).fetchAnyInto(StoreBasicVo.class);
    }

    public List<StoreBasicVo> listStoreCodes() {
        Condition condition = STORE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE);
        return db().select(STORE.STORE_ID, STORE.STORE_CODE).from(STORE).where(condition).fetchInto(StoreBasicVo.class);
    }

    /**
     * 根据有货门店storeCode获取门店列表
     * @param stores 有货门店storeCode
     * @return List<StoreDo>
     */
    public List<StoreDo> getStoreOpen(List<String> stores) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String dateStringParse = sdf.format(date);
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
        if (stores != null) {
            logger().info("门店库存校验");
            select.and(STORE.STORE_CODE.in(stores));
        }
        select.and(STORE.AUTO_PICK.eq(STORE_AUTO_PICK_ENABLE));
        select.limit(15);
        return select.fetchInto(StoreDo.class);
    }
}
