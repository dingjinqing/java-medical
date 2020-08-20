package com.vpu.mp.dao.shop.refund;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderRefundListDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.InquiryOrderRefundListRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.INQUIRY_ORDER_REFUND_LIST;

/**
 * @author 杨鹏程
 * @date 2020/7/23
 **/
@Repository
public class InquiryOrderRefundListDao extends ShopBaseDao {

    public int save(InquiryOrderRefundListDo param){
        InquiryOrderRefundListRecord record=db().newRecord(INQUIRY_ORDER_REFUND_LIST);
        FieldsUtil.assign(param,record);
        record.insert();
        param.setId(record.getId());
        return record.getId();
    }

    /**
     * 获取退款记录
     * @param orderSn
     * @return
     */
    public List<InquiryOrderRefundListDo> getListByOrderSn(String orderSn){
        return db().select().from(INQUIRY_ORDER_REFUND_LIST).where(INQUIRY_ORDER_REFUND_LIST.ORDER_SN.eq(orderSn)).fetchInto(InquiryOrderRefundListDo.class);
    }
}
