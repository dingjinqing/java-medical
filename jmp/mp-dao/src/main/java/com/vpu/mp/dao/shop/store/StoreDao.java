package com.vpu.mp.dao.shop.store;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import org.jooq.Condition;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vpu.mp.db.shop.tables.Store.STORE;

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
}
