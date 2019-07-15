package com.vpu.mp.service.pojo.shop.summary;

import lombok.Data;

import java.util.List;

/**
 * 访问分布索引项
 *
 * @author 郑保乐
 */
@Data
public class DistributionIndex {

    /**
     * 访问来源
     */
    public static final String ACCESS_SOURCE = "access_source_session_cnt";
    /**
     * 停留时间
     */
    public static final String VISIT_DURATION = "access_staytime_info";
    /**
     * 平均访问深度
     */
    public static final String VISIT_DEPTH = "access_depth_info";

    /**
     * 统计数据名称
     */
    private String name;

    /**
     * 统计数据项
     */
    private List<DistributionIndexItem> items;
}
