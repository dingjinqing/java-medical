package com.vpu.mp.service.shop.rebate;

import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.shop.rebate.InquiryOrderRebateDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.rebate.InquiryOrderRebateListParam;
import com.vpu.mp.service.pojo.shop.rebate.InquiryOrderRebateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author yangpengcheng
 * @date 2020/8/26
 **/
@Service
public class InquiryOrderRebateService extends ShopBaseService {

    @Autowired
    private InquiryOrderRebateDao inquiryOrderRebateDao;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    public PageResult<InquiryOrderRebateVo> getPageList(InquiryOrderRebateListParam param){
        beginAndEndOfDay(param);
        PageResult<InquiryOrderRebateVo> result=inquiryOrderRebateDao.getPageList(param);
        return result;
    }

    public void beginAndEndOfDay(InquiryOrderRebateListParam param){
        Timestamp startDate = param.getStartTime();
        Timestamp endDate = param.getEndTime();
        if (startDate != null ) {
            startDate = DateUtil.beginOfDay(startDate).toTimestamp();
            param.setStartTime(startDate);
        }if( endDate != null){
            endDate = DateUtil.endOfDay(endDate).toTimestamp();
            param.setEndTime(endDate);
        }
    }
}
