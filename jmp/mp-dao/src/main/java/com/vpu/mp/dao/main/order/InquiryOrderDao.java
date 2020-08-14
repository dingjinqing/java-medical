package com.vpu.mp.dao.main.order;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import com.vpu.mp.dao.foundation.base.MainBaseDao;
import com.vpu.mp.db.main.tables.records.InquiryOrderRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import static com.vpu.mp.db.main.Tables.INQUIRY_ORDER;
/**
 * @author yangpengcheng
 * @date 2020/8/14
 **/
@Repository
public class InquiryOrderDao extends MainBaseDao {

    /**
     * 同步新订单
     * @param list
     */
    public void inquiryOrderSynchronizeInsert(List<InquiryOrderDo> list){
        List<InquiryOrderRecord> recordList=new ArrayList<>();
        for(InquiryOrderDo inquiryOrderDo:list){
            InquiryOrderRecord record=db().newRecord(INQUIRY_ORDER);
            FieldsUtil.assign(inquiryOrderDo,record);
            recordList.add(record);
        }
        db().batchInsert(recordList).execute();
    }

    /**
     * 同步更新订单
     * @param list
     */
    public void inquiryOrderSynchronizeUpdate(List<InquiryOrderDo> list,Integer shopId){
        List<InquiryOrderRecord> recordList=new ArrayList<>();
        for(InquiryOrderDo inquiryOrderDo:list){
            InquiryOrderRecord record=getListByShopIdAndOrderSn(shopId,inquiryOrderDo.getOrderSn());
            FieldsUtil.assign(inquiryOrderDo,record);
            recordList.add(record);
        }
        db().batchUpdate(recordList).execute();
    }

    /**
     * 根据shopId,orderSn查询
     * @param shopId
     * @param orderSn
     * @return
     */
    public InquiryOrderRecord getListByShopIdAndOrderSn(Integer shopId,String orderSn){
        return db().select().from(INQUIRY_ORDER).where(INQUIRY_ORDER.SHOP_ID.eq(shopId)
             .and(INQUIRY_ORDER.ORDER_SN.eq(orderSn))).fetchAnyInto(InquiryOrderRecord.class);
    }

}
