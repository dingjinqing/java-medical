package com.vpu.mp.service.saas.shop;

import com.vpu.mp.common.foundation.util.DateUtil;
import com.vpu.mp.db.main.tables.records.ChargeRenewRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import com.vpu.mp.service.pojo.shop.auth.AdminTokenAuthInfo;
import com.vpu.mp.service.pojo.shop.auth.ChargeRenewAddParam;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static com.vpu.mp.db.main.tables.ChargeRenew.CHARGE_RENEW;

/**
 * @author: 王兵兵
 * @create: 2020-05-06 15:11
 **/
@Service
public class ShopApplyService extends MainBaseService {

    /**
     * 记录申请信息
     *
     * @param param
     * @param authInfo
     * @return -1表示今天已经申请过了
     */
    public Byte insertChargeRenew(ChargeRenewAddParam param, AdminTokenAuthInfo authInfo) {
        if (
            db().fetchExists(CHARGE_RENEW, CHARGE_RENEW.APPLY_ID.eq(authInfo.subAccountId == 0 ? authInfo.sysId : authInfo.subAccountId).and(CHARGE_RENEW.APPLY_TYPE.eq(param.getApplyType())).and(CHARGE_RENEW.CREATED.ge(Timestamp.valueOf(DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL_BEGIN, DateUtil.getLocalDateTime())))))
        ) {
            return -1;
        } else {
            ChargeRenewRecord chargeRenewRecord = db().newRecord(CHARGE_RENEW);
            chargeRenewRecord.setApplyId(authInfo.subAccountId == 0 ? authInfo.sysId : authInfo.subAccountId);
            chargeRenewRecord.setApplyName(authInfo.userName);
            chargeRenewRecord.setSysId(authInfo.sysId);
            chargeRenewRecord.setShopId(authInfo.loginShopId);
            chargeRenewRecord.setShopName(saas.shop.getShopById(authInfo.loginShopId).getShopName());
            chargeRenewRecord.setCreated(DateUtil.getLocalDateTime());
            chargeRenewRecord.setApplyMod(param.getApplyMod());
            chargeRenewRecord.setApplyType(param.getApplyType());
            chargeRenewRecord.insert();
            return 1;
        }
    }
}
