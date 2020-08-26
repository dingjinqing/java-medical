package com.vpu.mp.dao.shop.store;

import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import com.vpu.mp.service.pojo.shop.store.store.StoreVo;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.tables.Store.STORE;
/**
 * @author chenjie
 * @date 2020年08月24日
 */
@Repository
public class StoreDao extends ShopBaseDao {
    public StoreBasicVo getStoreById (Integer storeId) {
        return db().selectFrom(STORE).where(STORE.STORE_ID.eq(storeId)).fetchAnyInto(StoreBasicVo.class);
    }
}
