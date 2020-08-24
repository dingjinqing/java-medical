package com.vpu.mp.dao.shop.rebate;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.InquiryOrderRebateRecord;
import com.vpu.mp.service.pojo.shop.rebate.InquiryOrderRebateConstant;
import com.vpu.mp.service.pojo.shop.rebate.InquiryOrderRebateParam;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.tables.InquiryOrderRebate.INQUIRY_ORDER_REBATE;


/**
 * @author yangpengcheng
 * @date 2020/8/24
 **/
@Repository
public class InquiryOrderRebateDao extends ShopBaseDao {

    /**
     * 问诊返利add
     * @param param
     */
    public void addInquiryOrderRebate(InquiryOrderRebateParam param){
        InquiryOrderRebateRecord rebateRecord=db().newRecord(INQUIRY_ORDER_REBATE);
        FieldsUtil.assign(param,rebateRecord);
        rebateRecord.insert();
    }

    /**
     * 更改返利状态
     * @param orderSn
     */
    public void updateStatus(String orderSn){
        db().update(INQUIRY_ORDER_REBATE).set(INQUIRY_ORDER_REBATE.STATUS, InquiryOrderRebateConstant.REBATED).where(INQUIRY_ORDER_REBATE.ORDER_SN.eq(orderSn));
    }
}
