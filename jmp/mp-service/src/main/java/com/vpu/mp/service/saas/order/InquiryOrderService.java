package com.vpu.mp.service.saas.order;

import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import com.vpu.mp.dao.main.order.InquiryOrderDao;
import com.vpu.mp.db.main.tables.records.InquiryOrderRecord;
import com.vpu.mp.service.foundation.service.MainBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangpengcheng
 * @date 2020/8/14
 **/
@Service
@Slf4j
public class InquiryOrderService extends MainBaseService {
    @Autowired
    private InquiryOrderDao inquiryOrderDao;

    /**
     * 同步新增
     * @param list
     */
    public void inquiryOrderSynchronizeInsert(List<InquiryOrderDo> list){
        inquiryOrderDao.inquiryOrderSynchronizeInsert(list);
    }

    /**
     * 同步更新
     * @param list
     * @param shopId
     */
    public void inquiryOrderSynchronizeUpdate(List<InquiryOrderDo> list,Integer shopId){

        inquiryOrderDao.inquiryOrderSynchronizeUpdate(list,shopId);
    }



}
