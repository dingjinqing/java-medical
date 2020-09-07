package com.vpu.mp.dao.main;

import com.vpu.mp.dao.foundation.base.MainBaseDao;
import com.vpu.mp.dao.foundation.database.DslPlus;
import com.vpu.mp.service.pojo.shop.auth.StoreAuthConstant;
import com.vpu.mp.service.pojo.shop.auth.StoreLoginParam;
import com.vpu.mp.service.pojo.shop.store.account.StoreAccountVo;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vpu.mp.db.main.Tables.STORE_ACCOUNT;

/**
 * @author chenjie
 * @date 2020年08月21日
 */
@Repository
public class StoreAccountDao extends MainBaseDao {
    /**
     * 根据登录信息获取对应信息
     * @param param
     * @return
     */
    public StoreAccountVo getStoreAccountInfo(StoreLoginParam param){
        return db().selectFrom(STORE_ACCOUNT)
            .where(STORE_ACCOUNT.SYS_ID.eq(param.getSysId()))
            .and(STORE_ACCOUNT.ACCOUNT_TYPE.eq(param.getStoreAccountType()))
            .and(STORE_ACCOUNT.DEL_FLAG.eq(StoreAuthConstant.DEL_NORMAL))
            .and(STORE_ACCOUNT.ACCOUNT_NAME.eq(param.getStoreUsername()).or(STORE_ACCOUNT.MOBILE.eq(param.getStoreUsername())))
            .fetchAnyInto(StoreAccountVo.class);
    }

    /**
     * 获取门店登录账号信息
     * @param storeAccountId
     * @return
     */
    public StoreAccountVo getOneInfo(Integer storeAccountId){
        return db().selectFrom(STORE_ACCOUNT)
            .where(STORE_ACCOUNT.ACCOUNT_ID.eq(storeAccountId))
            .fetchAnyInto(StoreAccountVo.class);
    }

    public int upateBind(Integer storeAccountId, String officalOpenId, byte bind) {
        return db().update(STORE_ACCOUNT).set(STORE_ACCOUNT.OFFICIAL_OPEN_ID, officalOpenId)
            .set(STORE_ACCOUNT.IS_BIND, bind).where(STORE_ACCOUNT.ACCOUNT_ID.eq(storeAccountId)).execute();

    }

    public int updateRowBind(Integer storeAccountId,byte bind) {
        return db().update(STORE_ACCOUNT).set(STORE_ACCOUNT.IS_BIND, bind)
            .where(STORE_ACCOUNT.ACCOUNT_ID.eq(storeAccountId)).execute();
    }

    /**
     * 获得绑定第三方公众号的账号
     * @param shopId
     * @param storeId
     * @return
     */
    public List<StoreAccountVo> getStoreAccountByBindThird(Integer shopId, Integer storeId) {
        return db().selectFrom(STORE_ACCOUNT)
            .where(STORE_ACCOUNT.SHOP_ID.eq(shopId).and(STORE_ACCOUNT.IS_BIND.eq((byte) 1)))
            .and(DslPlus.findInSet(storeId, STORE_ACCOUNT.STORE_LIST))
            .fetchInto(StoreAccountVo.class);
    }
}
