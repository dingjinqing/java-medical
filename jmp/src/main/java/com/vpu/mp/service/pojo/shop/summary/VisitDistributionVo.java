package com.vpu.mp.service.pojo.shop.summary;

import lombok.Data;

import java.util.List;

/**
 * 访问分布出参
 *
 * @author 郑保乐
 */
@Data
public class VisitDistributionVo {

    private List<VisitInfoItem> accessDepthInfo;
    private List<VisitInfoItem> accessSourceSessionCnt;
    private List<VisitInfoItem> accessStayTimeInfo;
    private ChartXValueYKey visitDepth;
    private ChartXKeyYValue visitSource;
    private ChartXValueYKey visitStayTime;
}
