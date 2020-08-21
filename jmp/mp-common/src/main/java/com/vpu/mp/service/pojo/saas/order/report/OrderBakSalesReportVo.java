package com.vpu.mp.service.pojo.saas.order.report;

import com.vpu.mp.common.foundation.util.Page;
import com.vpu.mp.service.pojo.shop.order.report.MedicalOrderReportVo;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 孔德成
 * @date 2020/8/7 15:17
 */
@Data
public class OrderBakSalesReportVo {
    public Page page;
    public List<MedicalOrderReportVo> dataList;
    /**
     * 开始时间
     */
    private Timestamp startTime;
    /**
     * 结束时间
     */
    private Timestamp endTime;
}
