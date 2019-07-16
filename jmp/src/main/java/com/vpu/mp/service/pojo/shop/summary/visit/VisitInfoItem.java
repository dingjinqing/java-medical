package com.vpu.mp.service.pojo.shop.summary.visit;

import lombok.Data;

/**
 * 访问分布 key 对应关系（用于出参）
 *
 * @author 郑保乐
 */
@Data
public class VisitInfoItem {

    /**
     * 统计项名称
     */
    private String name;
    /**
     * 统计项序号
     */
    private Integer key;
    /**
     * 数值
     */
    private Integer value;
    /**
     * 是否显示
     */
    private Integer isShow = 1;
}
