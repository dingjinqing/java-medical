package com.vpu.mp.service.saas.overview;

import com.vpu.mp.db.main.tables.ShopAccount;
import com.vpu.mp.db.main.tables.ShopChildAccount;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.shop.overview.BindUnBindOfficialParam;

/**
 * @Author:liufei
 * @Date:2019/7/15
 * @Description: 概览
 */
public class OverviewService extends BaseService {
    /**
     * 绑定解绑
     * @param param
     * @return
     */
    public int bindUnBindOfficial(BindUnBindOfficialParam param){
        /** 更新主账户 */
        if(param.getIsSubAccount() == 0){
            return db().update(ShopAccount.SHOP_ACCOUNT)
                    .set(ShopAccount.SHOP_ACCOUNT.IS_BIND,param.getIsBind())
                    .where(ShopAccount.SHOP_ACCOUNT.SYS_ID.eq(param.getSysId()))
                    .execute();
        /** 更新子账户 */
        }else if(param.getIsSubAccount() == 1){
            return db().update(ShopChildAccount.SHOP_CHILD_ACCOUNT)
                    .set(ShopChildAccount.SHOP_CHILD_ACCOUNT.IS_BIND,param.getIsBind())
                    .where(ShopChildAccount.SHOP_CHILD_ACCOUNT.ACCOUNT_ID.eq(param.getAccountId()))
                    .execute();
        }else {
            return -1;
        }
    }
}
