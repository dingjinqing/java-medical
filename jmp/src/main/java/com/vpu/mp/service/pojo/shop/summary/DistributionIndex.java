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

    /*[{"index":"access_source_session_cnt","item_list":[{"key":29,"value":1},{"key":8,"value":1},{"key":4,"value":1},{"key":2,"value":7},{"key":1,"value":2}]},{"index":"access_staytime_info","item_list":[{"key":8,"value":2},{"key":7,"value":1},{"key":6,"value":3},{"key":5,"value":2},{"key":4,"value":3},{"key":3,"value":1}]},{"index":"access_depth_info","item_list":[{"key":5,"value":4},{"key":4,"value":2},{"key":3,"value":1},{"key":2,"value":5}]}]*/
    private String name;
    private List<DistributionIndexItem> items;
}
