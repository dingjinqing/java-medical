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

    public static final String ACCESS_SOURCE = "access_source_session_cnt";
    public static final String VISIT_DURATION = "access_staytime_info";
    public static final String VISIT_DEPTH = "access_depth_info";

    private String name;
    private List<DistributionIndexItem> items;
}
