package com.vpu.mp.service.pojo.shop.summary;

import lombok.Data;

/**
 * 访问分布 key 对应关系（用于出参）
 *
 * @author 郑保乐
 */
@Data
public class VisitInfoItem {

    private String name;
    private Integer value;
    private Integer isShow = 1;
}
