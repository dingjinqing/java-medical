package com.vpu.mp.service.pojo.shop.summary.visit;

import com.vpu.mp.service.pojo.shop.summary.ChartXKeyYValue;
import com.vpu.mp.service.pojo.shop.summary.ChartXValueYKey;
import lombok.Data;

import java.util.List;

/**
 * 访问分布出参
 *
 * @author 郑保乐
 */
@Data
public class VisitDistributionVo {

    /**
     * 平均访问深度
     */
    private List<VisitInfoItem> accessDepthInfo;
    /**
     * 访问来源
     */
    private List<VisitInfoItem> accessSourceSessionCnt;
    /**
     * 停留时长
     */
    private List<VisitInfoItem> accessStayTimeInfo;
    /**
     * 平均访问深度
     */
    private ChartXValueYKey visitDepth;
    /**
     * 访问来源
     */
    private ChartXKeyYValue visitSource;
    /**
     * 停留时长
     */
    private ChartXValueYKey visitStayTime;
}
