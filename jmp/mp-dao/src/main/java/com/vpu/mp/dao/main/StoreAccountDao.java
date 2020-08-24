package com.vpu.mp.dao.main;

import com.vpu.mp.dao.foundation.base.MainBaseDao;
import com.vpu.mp.service.pojo.shop.auth.StoreAuthConstant;
import com.vpu.mp.service.pojo.shop.auth.StoreLoginParam;
import com.vpu.mp.service.pojo.shop.store.account.StoreAccountVo;
import org.springframework.stereotype.Repository;
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
}
