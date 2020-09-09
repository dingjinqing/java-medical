package com.vpu.mp.service.shop.rebate;

import cn.hutool.core.date.DateUtil;
import com.vpu.mp.common.foundation.excel.ExcelFactory;
import com.vpu.mp.common.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.common.foundation.excel.ExcelWriter;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.shop.rebate.InquiryOrderRebateDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.rebate.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

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
        PageResult<InquiryOrderRebateVo> result=inquiryOrderRebateDao.getPageList(param);
        return result;
    }

    /**
     * 报表导出
     * @param param
     * @param lang
     * @return
     */
    public Workbook listExport(InquiryOrderRebateListParam param,String lang){
        List<InquiryOrderRebateReportVo> list=inquiryOrderRebateDao.getList(param);
        list.stream().forEach(vo -> {
            if(InquiryOrderRebateConstant.TO_REBATE.equals(vo.getStatus())){
                vo.setStatusName(RebateReportConstant.WAIT_REBATE);
            }else if(InquiryOrderRebateConstant.REBATED.equals(vo.getStatus())){
                vo.setStatusName(RebateReportConstant.REBATED);
            }else if(InquiryOrderRebateConstant.REBATE_FAIL.equals(vo.getStatus())){
                vo.setStatusName(RebateReportConstant.REBATE_FAIL);
            }
        });
        Workbook workbook= ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(lang,workbook);
        excelWriter.writeModelList(list,InquiryOrderRebateReportVo.class);
        return workbook;
    }


}
